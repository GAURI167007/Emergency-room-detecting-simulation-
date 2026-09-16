package simulation;

import allocation.DoctorAllocationStrategy;
import allocation.SpecializationAllocator;
import exception.NoDoctorAvailableException;
import model.Doctor;
import model.EmergencyEvent;
import model.EventType;
import model.Patient;
import triage.DynamicTriageStrategy;
import triage.TriageStrategy;
import service.EmergencyQueue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ERSimulation {

    private final Map<Integer, Patient> patients;
    private final List<Doctor> doctors;
 private final EmergencyQueue emergencyQueue;

 private final TriageStrategy triageStrategy;
    private final DoctorAllocationStrategy allocationStrategy;

    private final PriorityBlockingQueue<EmergencyEvent> eventQueue;
      private final ExecutorService executorService;

    private final AtomicInteger activeTreatments;
    private volatile boolean patientArrivalCompleted;
 public ERSimulation(List<Doctor> doctors) {

        this.patients = new ConcurrentHashMap<>();

        this.doctors = new ArrayList<>(doctors);

        this.emergencyQueue = new EmergencyQueue();

        this.triageStrategy =
                new DynamicTriageStrategy();

        this.allocationStrategy =
                new SpecializationAllocator();

        this.eventQueue =
                new PriorityBlockingQueue<>(
                        100,
                        (event1, event2) ->
                                event1.getTimestamp()
                                        .compareTo(
                                                event2.getTimestamp()
                                        )
                );

        this.executorService =
                Executors.newFixedThreadPool(6);

        this.activeTreatments =
                new AtomicInteger(0);

        this.patientArrivalCompleted = false;
    }
       public void registerPatient(Patient patient) {

        patients.put(
                patient.getPatientId(),
                patient
        );
    }
public Patient getPatient(int patientId) {

        return patients.get(patientId);
    }

    public List<Doctor> getDoctors() {

        return doctors;
    }
    public Map<Integer, Patient> getPatients() {

    return patients;
}
    public EmergencyQueue getEmergencyQueue() {

        return emergencyQueue;
    }

    public void publishEvent(EmergencyEvent event) {
        eventQueue.offer(event);
    } public EmergencyEvent pollEvent(
            long timeout,
           
            TimeUnit unit)
           
         throws InterruptedException {

        return eventQueue.poll(timeout, unit);
    }
    public void markPatientArrivalCompleted() {

        patientArrivalCompleted = true;
    }
    public boolean isPatientArrivalCompleted() {

        return patientArrivalCompleted;
    }
    public int getActiveTreatments() {
        return activeTreatments.get();
    }
    public void startSimulation(
            List<Patient> simulationPatients) {

        for (Patient patient : simulationPatients) {

            registerPatient(patient);
        }
        EventProcessor eventProcessor =
                new EventProcessor(this);

        Thread eventProcessorThread =
                new Thread(
                        eventProcessor,
                        "Event-Processor"
                );

        eventProcessorThread.start();
        executorService.submit(
                new PatientArrivalTask(
                        this,
                        simulationPatients
                )
        );

        try {

            eventProcessorThread.join();

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            System.out.println(
                    "Simulation interrupted."
            );
        }

        executorService.shutdown();
    }public void handleEvent(EmergencyEvent event) {

        switch (event.getEventType()) {

            case PATIENT_ARRIVAL:

                handlePatientArrival(event);

                break;

            case PATIENT_DETERIORATION:

                handlePatientDeterioration(event);

                break;

            case TREATMENT_COMPLETE:

                handleTreatmentComplete(event);

                break;
            case DOCTOR_AVAILABLE:
                handleDoctorAvailable(event);
                break;
            case EMERGENCY_SURGE:
                System.out.println(
                        "\n!!! EMERGENCY  DETECTED !!!"
                );
                break;
        }
    }
    private synchronized void handlePatientArrival(
        EmergencyEvent event) {
        Patient patient =
                patients.get(event.getPatientId());

        if (patient == null) {
            return;
        }

        System.out.println(
                "\n[" + event.getTimestamp() + "] "
                        + "PATIENT ARRIVAL: "
                        + patient.getName()
        );

        triageStrategy.assessPatient(patient);

        System.out.println(
                "Triage -> "
                        + patient.getPriorityLevel()
                        + " | Score: "
                        + patient.getPriorityScore()
        );

        emergencyQueue.addPatient(patient);
        assignWaitingPatients();
    }
    private synchronized void handlePatientDeterioration(
            EmergencyEvent event) {

        Patient patient =
                patients.get(event.getPatientId());

        if (patient == null) {
            return;
        }
        if (patient.getStatus()
                != model.PatientStatus.WAITING) {
            return;
        }

        System.out.println(
                "\n!!! PATIENT DETERIORATION !!!"
        );
        System.out.println(
                patient.getName()
                        + " condition has deteriorated."
        );
        emergencyQueue.removePatient(patient);

        patient.deteriorate();

        triageStrategy.assessPatient(patient);

        patient.increasePriority(20);

        emergencyQueue.requeuePatient(patient);

        System.out.println(
                "New priority -> "
                        + patient.getPriorityLevel()
                        + " | Score: "
                        + patient.getPriorityScore()
        );

        assignWaitingPatients();
    }

    private synchronized void handleTreatmentComplete(
            EmergencyEvent event) {

        Patient patient =
                patients.get(event.getPatientId());

        if (patient == null) {
            return;
        }

        Doctor doctor =
                findDoctorTreating(
                        patient.getPatientId()
                );

        if (doctor != null) {

            doctor.completeTreatment();

            patient.setTreatmentEndTime(
                    event.getTimestamp()
            );

            activeTreatments.decrementAndGet();

            System.out.println(
                    "\n[" + event.getTimestamp() + "] "
                            + "TREATMENT COMPLETE"
            );

            System.out.println(
                    "Patient: "
                            + patient.getName()
            );

            System.out.println(
                    "Doctor: Dr. "
                            + doctor.getName()
            );

            publishEvent(
                    new EmergencyEvent(
                            EventType.DOCTOR_AVAILABLE,
                            event.getTimestamp(),
                            patient.getPatientId(),
                            "Doctor "
                                    + doctor.getName()
                                    + " is now available."
                    )
            );
        }
    }

    private synchronized void handleDoctorAvailable(
        EmergencyEvent event) {
          assignWaitingPatients();
    }
    private void assignWaitingPatients() {
        while (!emergencyQueue.isEmpty()) {

            Patient patient =
                    emergencyQueue.getNextPatient();

            if (patient == null) {
                return;
            }

            try {

                Doctor doctor =
                        allocationStrategy.allocateDoctor(
                                patient,
                                doctors
                        );

                emergencyQueue.removeNextPatient();

                patient.setTreatmentStartTime(
                        LocalDateTime.now()
                );
                activeTreatments.incrementAndGet();
                System.out.println(
                        "\n>>> DOCTOR HAS BEEN ASSIGNED"
                );
                System.out.println(
                        "Patient: "
                                + patient.getName()
                );

                System.out.println(
                        "Doctor: Dr. "
                                + doctor.getName()
                );

                System.out.println(
                        "Specialization: "
                                + doctor.getSpecialization()
                );

                executorService.submit(
                        new DoctorTask(
                                this,
                                doctor,
                                patient
                        )
                );

            } catch (NoDoctorAvailableException e) {

                System.out.println(
                        "\nNo suitable doctor currently available."
                );

                return;
            }
        }
    
}    private Doctor findDoctorTreating(
            int patientId) {

        for (Doctor doctor : doctors) {

            if (doctor.getCurrentPatientId()
                    == patientId) {

                return doctor;
            }
        }
        return null;
    } public boolean hasPendingWork() {
        return !patientArrivalCompleted
                || !eventQueue.isEmpty()
                || activeTreatments.get() > 0;
    }
}
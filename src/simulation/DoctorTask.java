package simulation;

import model.Doctor;
import model.EmergencyEvent;
import model.EventType;
import model.Patient;
import model.PriorityLevel;

import java.time.LocalDateTime;

public class DoctorTask
        implements Runnable {

    private final ERSimulation simulation;
    private final Doctor doctor;
    private final Patient patient;

    //  the program below will tell us like according to the priority level what is the condition type(urgent,moderate or noemal)
    public DoctorTask(
            ERSimulation simulation,
            Doctor doctor,
            Patient patient) {

        this.simulation = simulation;
        this.doctor = doctor;
        this.patient = patient;
    }

    @Override
    public void run() {
        try {
            long treatmentDuration =
                    calculateTreatmentDuration();
            System.out.println(
                    "Dr. " + doctor.getName()
                            + " treating "
                            + patient.getName()
                            + "..."
            );
            Thread.sleep(treatmentDuration);
            EmergencyEvent completionEvent =
                    new EmergencyEvent(
                            EventType.TREATMENT_COMPLETE,
                            LocalDateTime.now(),
                            patient.getPatientId(),
                            "Treatment completed."
                    );

            simulation.publishEvent(
                    completionEvent
            );
        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
    }
    private long calculateTreatmentDuration() {

        if (patient.getPriorityLevel()
                == PriorityLevel.CRITICAL) {

            return 1200;
        }

        if (patient.getPriorityLevel()
                == PriorityLevel.URGENT) {

            return 1000;
        }

        if (patient.getPriorityLevel()
                == PriorityLevel.MODERATE) {

            return 800;
        }

        return 600;
    }
}
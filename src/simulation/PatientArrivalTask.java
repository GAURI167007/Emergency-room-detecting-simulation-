package simulation;

import model.EmergencyEvent;
import model.EventType;
import model.Patient;

import java.time.LocalDateTime;
import java.util.List;

public class PatientArrivalTask
        implements Runnable {

    private final ERSimulation simulation;
    private final List<Patient> patients;

    public PatientArrivalTask(
            ERSimulation simulation,
            List<Patient> patients) {

        this.simulation = simulation;
        this.patients = patients;
    }

    @Override
    public void run() {

        LocalDateTime simulationTime =
                LocalDateTime.now();

        int patientNumber = 0;

        try {

            for (Patient patient : patients) {

                LocalDateTime arrivalTime =
                        simulationTime.plusSeconds(
                                patientNumber * 2L
                        );

                EmergencyEvent event =
                        new EmergencyEvent(
                                EventType.PATIENT_ARRIVAL,
                                arrivalTime,
                                patient.getPatientId(),
                                "Patient arrived at ER."
                        );

                simulation.publishEvent(event);

                patientNumber++;

                Thread.sleep(300);
            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        } finally {

            simulation.markPatientArrivalCompleted();
        }
    }
}
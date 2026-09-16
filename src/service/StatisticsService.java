package service;

import model.Doctor;
import model.Patient;
import model.PatientStatus;
import model.PriorityLevel;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public class StatisticsService {

    public void displayReport(
            Collection<Patient> patients,
            List<Doctor> doctors) {

        int totalPatients = patients.size();

        int critical = 0;
        int urgent = 0;
        int moderate = 0;
        int normal = 0;
        int deteriorated = 0;
        int completed = 0;

        long totalWaitingSeconds = 0;
        long totalTreatmentSeconds = 0;

        int waitingPatients = 0;
        int treatedPatients = 0;

        for (Patient patient : patients) {

            PriorityLevel priority =
                    patient.getPriorityLevel();

            switch (priority) {

                case CRITICAL:
                    critical++;
                    break;

                case URGENT:
                    urgent++;
                    break;

                case MODERATE:
                    moderate++;
                    break;

                case NORMAL:
                    normal++;
                    break;
            }

            if (patient.getDeteriorationCount() > 0) {
                deteriorated++;
            }

            if (patient.getStatus()
                    == PatientStatus.COMPLETED) {

                completed++;

                if (patient.getTreatmentStartTime() != null
                        && patient.getArrivalTime() != null) {

                    long waitingTime =
                            Duration.between(
                                    patient.getArrivalTime(),
                                    patient.getTreatmentStartTime()
                            ).toSeconds();

                    totalWaitingSeconds += waitingTime;
                    treatedPatients++;
                }

                if (patient.getTreatmentStartTime() != null
                        && patient.getTreatmentEndTime() != null) {

                    long treatmentTime =
                            Duration.between(
                                    patient.getTreatmentStartTime(),
                                    patient.getTreatmentEndTime()
                            ).toSeconds();

                    totalTreatmentSeconds += treatmentTime;
                }

            } else if (patient.getStatus()
                    == PatientStatus.WAITING) {

                waitingPatients++;
            }
        }

        double averageWaitingTime =
                treatedPatients == 0
                        ? 0
                        : (double) totalWaitingSeconds
                        / treatedPatients;

        double averageTreatmentTime =
                completed == 0
                        ? 0
                        : (double) totalTreatmentSeconds
                        / completed;

        displaySummary(
                totalPatients,
                critical,
                urgent,
                moderate,
                normal,
                completed,
                waitingPatients,
                deteriorated,
                averageWaitingTime,
                averageTreatmentTime,
                doctors
        );
    }

    private void displaySummary(
            int totalPatients,
            int critical,
            int urgent,
            int moderate,
            int normal,
            int completed,
            int waitingPatients,
            int deteriorated,
            double averageWaitingTime,
            double averageTreatmentTime,
            List<Doctor> doctors) {

        System.out.println();
        System.out.println(
                "=============================================="
        );
        System.out.println(
                "           ER SIMULATION REPORT"
        );
        System.out.println(
                "=============================================="
        );

        System.out.println(
                "Total Patients       : " + totalPatients
        );

        System.out.println(
                "Critical             : " + critical
        );

        System.out.println(
                "Urgent               : " + urgent
        );

        System.out.println(
                "Moderate             : " + moderate
        );

        System.out.println(
                "Normal               : " + normal
        );

        System.out.println(
                "Completed Treatments : " + completed
        );

        System.out.println(
                "Still Waiting        : " + waitingPatients
        );

        System.out.println(
                "Deteriorated         : " + deteriorated
        );

        System.out.printf(
                "Average Waiting Time : %.2f sec%n",
                averageWaitingTime
        );

        System.out.printf(
                "Average Treatment    : %.2f sec%n",
                averageTreatmentTime
        );

        System.out.println();
        System.out.println(
                "------------- DOCTOR LOAD -------------"
        );

        for (Doctor doctor : doctors) {

            System.out.println(
                    "Dr. "
                            + doctor.getName()
                            + " | "
                            + doctor.getSpecialization()
                            + " | Patients Treated: "
                            + doctor.getPatientsTreated()
            );
        }

        System.out.println(
                "=============================================="
        );
    }
}
package util;

import model.Doctor;
import model.Patient;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class FileManager {

    private static final String DATA_DIRECTORY = "data";

    private static final String PATIENT_DIRECTORY =
            DATA_DIRECTORY + File.separator + "patients";

    private static final String LOG_DIRECTORY =
            DATA_DIRECTORY + File.separator + "logs";

    private static final String REPORT_DIRECTORY =
            DATA_DIRECTORY + File.separator + "reports";

    public FileManager() {

        createDirectories();
    }

    private void createDirectories() {

        new File(PATIENT_DIRECTORY).mkdirs();
        new File(LOG_DIRECTORY).mkdirs();
        new File(REPORT_DIRECTORY).mkdirs();
    }

    public void savePatientHistory(
            Collection<Patient> patients) {

        String filePath =
                PATIENT_DIRECTORY
                        + File.separator
                        + "patient_history.txt";

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(filePath))) {

            writer.write(
                    "========== PATIENT HISTORY =========="
            );

            writer.newLine();
            writer.newLine();

            for (Patient patient : patients) {

                writer.write(
                        "Patient ID       : "
                                + patient.getPatientId()
                );

                writer.newLine();

                writer.write(
                        "Name             : "
                                + patient.getName()
                );

                writer.newLine();

                writer.write(
                        "Age              : "
                                + patient.getAge()
                );

                writer.newLine();

                writer.write(
                        "Symptoms         : "
                                + patient.getSymptoms()
                );

                writer.newLine();

                writer.write(
                        "Specialization   : "
                                + patient
                                .getRequiredSpecialization()
                );

                writer.newLine();

                writer.write(
                        "Priority         : "
                                + patient.getPriorityLevel()
                );

                writer.newLine();

                writer.write(
                        "Priority Score   : "
                                + patient.getPriorityScore()
                );

                writer.newLine();

                writer.write(
                        "Status           : "
                                + patient.getStatus()
                );

                writer.newLine();

                writer.write(
                        "Deteriorations   : "
                                + patient
                                .getDeteriorationCount()
                );

                writer.newLine();

                writer.write(
                        "Arrival Time     : "
                                + patient.getArrivalTime()
                );

                writer.newLine();

                writer.write(
                        "Treatment Start  : "
                                + patient
                                .getTreatmentStartTime()
                );

                writer.newLine();

                writer.write(
                        "Treatment End    : "
                                + patient
                                .getTreatmentEndTime()
                );

                writer.newLine();

                writer.write(
                        "--------------------------------------"
                );

                writer.newLine();
            }

            System.out.println(
                    "Patient history saved to: "
                            + filePath
            );

        } catch (IOException e) {

            System.out.println(
                    "Unable to save patient history: "
                            + e.getMessage()
            );
        }
    }

    public void saveSimulationLog(
            String logMessage) {

        String filePath =
                LOG_DIRECTORY
                        + File.separator
                        + "simulation.log";

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(
                                     filePath,
                                     true
                             ))) {

            writer.write(logMessage);
            writer.newLine();

        } catch (IOException e) {

            System.out.println(
                    "Unable to write simulation log: "
                            + e.getMessage()
            );
        }
    }

    public void saveSimulationReport(
            Collection<Patient> patients,
            List<Doctor> doctors) {

        String filePath =
                REPORT_DIRECTORY
                        + File.separator
                        + "simulation_report.txt";

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(filePath))) {

            int totalPatients = patients.size();

            int critical = 0;
            int urgent = 0;
            int moderate = 0;
            int normal = 0;
            int completed = 0;
            int deteriorated = 0;

            for (Patient patient : patients) {

                switch (patient.getPriorityLevel()) {

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

                if (patient.getStatus()
                        == model.PatientStatus.COMPLETED) {

                    completed++;
                }

                if (patient.getDeteriorationCount() > 0) {

                    deteriorated++;
                }
            }

            writer.write(
                    "========================================"
            );
            writer.newLine();

            writer.write(
                    "          ER SIMULATION REPORT"
            );
            writer.newLine();

            writer.write(
                    "========================================"
            );
            writer.newLine();

            writer.write(
                    "Total Patients : " + totalPatients
            );
            writer.newLine();

            writer.write(
                    "Critical       : " + critical
            );
            writer.newLine();

            writer.write(
                    "Urgent         : " + urgent
            );
            writer.newLine();

            writer.write(
                    "Moderate       : " + moderate
            );
            writer.newLine();

            writer.write(
                    "Normal         : " + normal
            );
            writer.newLine();

            writer.write(
                    "Completed      : " + completed
            );
            writer.newLine();

            writer.write(
                    "Deteriorated   : " + deteriorated
            );
            writer.newLine();

            writer.newLine();

            writer.write(
                    "------------- DOCTORS -------------"
            );
            writer.newLine();

            for (Doctor doctor : doctors) {

                writer.write(
                        "Dr. "
                                + doctor.getName()
                                + " | "
                                + doctor.getSpecialization()
                                + " | Patients Treated: "
                                + doctor.getPatientsTreated()
                );

                writer.newLine();
            }

            writer.write(
                    "========================================"
            );
            writer.newLine();

            System.out.println(
                    "Simulation report saved to: "
                            + filePath
            );

        } catch (IOException e) {

            System.out.println(
                    "Unable to save simulation report: "
                            + e.getMessage()
            );
        }
    }
}
import exception.InvalidPatientException;
import model.Doctor;
import model.Patient;
import model.VitalSigns;
import service.StatisticsService;
import simulation.ERSimulation;
import util.FileManager;
import util.InputValidator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
// this is what the user will see on starting the program
public class Main {
    private static final Scanner scanner =
         new Scanner(System.in);
    private static final List<Doctor> doctors =
            new ArrayList<>();
     private static ERSimulation simulation;
      private static final StatisticsService
            statisticsService =
         new StatisticsService();
    private static final FileManager fileManager =
            new FileManager();
    private static int nextPatientId = 1;
    public static void main(String[] args) {
        initializeDoctors();
        simulation = new ERSimulation(doctors);
        boolean running = true;
        System.out.println();
        System.out.println(
                "*********************************************"
        );
        System.out.println(
                "      EMERGENCY ROOM DECISION SIMULATOR"
        );
        System.out.println(
                "**************************************************"
        );
        while (running) {
            displayMenu();
            int choice = readInteger(
                    "Enter your choice (Enter integer corresponding to Menu): "
            );
            switch (choice) {
                case 1:
                    registerPatient();
                    break;
                case 2:
                    simulation
                            .getEmergencyQueue()
                            .displayQueue();
                    break;
                case 3:
                    displayDoctors();
                    break;
                case 4:
                    assignNextPatient();
                    break;
                case 5:
                    runDemoSimulation();
                    break;
                case 6:
                    displayStatistics();
                    break;
                case 7:
                    savePatientHistory();
                    break;
                case 8:
                    saveSimulationReport();
                    break;
                case 9:
                    running = false;

                    System.out.println(
                 "\nThank you for using the "
                                    + "ER Decision Simulator."
                    );
                    break;
                default:
                    System.out.println(
                            "\nInvalid choice."
                    );
            }
        }
        scanner.close();
    }
    private static void initializeDoctors() {
  doctors.add(
                new Doctor(
                        101,
                        "Sharma",
                        "Cardiology"
                )
        );
  doctors.add(
                new Doctor(
                        102,
                        "Mehta",
                        "Neurology"
                )
        );
        doctors.add(
          new Doctor(
                        103,
                        "Patel",
                        "General"
                )
        );
 doctors.add(
                new Doctor(
                   104,
                        "Verma",
                        "Pulmonology"
                )
        );
    }
    private static void displayMenu() {
        System.out.println();
        System.out.println(
                "==============================================" );
        System.out.println(
                "1. Register Patient" );
        System.out.println(
                "2. View Emergency Queue" );
        System.out.println(
                "3. View Doctors" );
        System.out.println(
                "4. Assign Next Patient");
        System.out.println(
                "5. Run Emergency Simulation");
        System.out.println(
                "6. View Statistics");
        System.out.println(
                "7. Save Patient History");
        System.out.println(
                "8. Save Simulation Report"  );
        System.out.println(
         "9. Exit"  );
        System.out.println(
                "==============================================");
    }
    private static void registerPatient() {

        System.out.println();
        System.out.println(
                "---------- PATIENT REGISTRATION ----------"
        );
        System.out.print(
                "Enter patient name: "
        );
        String name =
                scanner.nextLine().trim();

        int age = readInteger(
                "Enter patient age: "
        );

        System.out.print(
                "Enter symptoms: "
        );
        String symptoms =
                scanner.nextLine().trim();
        System.out.print(
                "Required specialization "
                        + "(Cardiology/Neurology/"
                        + "Pulmonology/General): "
        );
        String specialization =
                scanner.nextLine().trim();

        System.out.println();
        System.out.println(
                "Enter vital signs:"
        );
        int heartRate =
                readInteger("Heart rate: ");
        int systolicBP =
             readInteger("Systolic BP: ");

        int oxygenSaturation =
           readInteger("Oxygen saturation (%): ");
        double temperature =
                readDouble("Temperature: ");
        int respiratoryRate =
             readInteger("Respiratory rate: ");

        try {

            InputValidator.validatePatient(
                    name,
                    age,
                    symptoms,
                    specialization
            );

            VitalSigns vitalSigns =
                    new VitalSigns(
                            heartRate,
                         systolicBP,
                            oxygenSaturation,
                            temperature,
                        respiratoryRate
                    );

            Patient patient =
                    new Patient(
                        nextPatientId++,
                            name,
                    age,
                            symptoms,
                            specialization,
                            vitalSigns,
                            LocalDateTime.now()
                    );

            simulation.registerPatient(patient);
            simulation
                    .getEmergencyQueue()
                .addPatient(patient);
         System.out.println();
            System.out.println(
                    "Patient registered successfully!"
            );

            System.out.println(
                    "Patient ID: "                            + patient.getPatientId()

                        );

        System.out.println(
                    "Vital Signs: "
                            + patient.getVitalSigns()
            );
            System.out.println(
                    "\nRunning triage assessment..."
            );

            triagePatient(patient);
        } catch (InvalidPatientException e) {
    System.out.println(
              "\nRegistration failed: "
                            + e.getMessage()            );
   }
    }
    private static void triagePatient(            Patient patient) {

        triage.DynamicTriageStrategy
                triageStrategy =                new triage.DynamicTriageStrategy();


                triageStrategy.assessPatient(patient);
        System.out.println(
           "Priority Level: "
                        + patient.getPriorityLevel()
        );

        System.out.println(
                "Priority Score: "
                        + patient.getPriorityScore()
        );
    }
    private static void assignNextPatient() {
        if (simulation
                .getEmergencyQueue()
                .isEmpty()) {

            System.out.println(
                    "\nNo patients are waiting."
            );
            return;
        }
        Patient patient =
                simulation
                        .getEmergencyQueue()
                        .getNextPatient();
        if (patient == null) {
            return;
        }
        System.out.println(
                "\nNext patient:"
        );
        System.out.println(
                patient
        );
        System.out.println(
                "\nDoctor assignment is handled "
                        + "by the simulation engine."
        );
        System.out.println(
        "Use option 5 to run the "
               + "complete event-driven simulation."
        );
    }

    private static void displayDoctors() {
        System.out.println();
        System.out.println(
                "------------- DOCTORS -------------"
        );
        for (Doctor doctor : doctors) {
            System.out.println(doctor);
        }
        System.out.println(
                "------------------------------------"
        );
    }
    private static void displayStatistics() {
        statisticsService.displayReport(
                simulation
                   .getPatients()
                        .values(),
                simulation.getDoctors()
        );
    }
    private static void savePatientHistory() {

        fileManager.savePatientHistory(
                simulation
                        .getPatients()
                        .values()
        );
    }
    private static void saveSimulationReport() {
   fileManager.saveSimulationReport(
                simulation
                        .getPatients()
                        .values(),
                simulation.getDoctors()
        );
    }
 private static void runDemoSimulation() {

        System.out.println();
        System.out.println(
                "=============================================="
        );
        System.out.println(
                "        STARTING EMERGENCY SIMULATION"
        );
        System.out.println(
                "=============================================="
        );
 List<Patient> demoPatients =
                createDemoPatients();

        simulation.startSimulation(
                demoPatients
        );
 System.out.println();
        System.out.println(
                "Simulation finished."
        );
    }
    private static List<Patient>
    createDemoPatients() {
List<Patient> patients =
                new ArrayList<>();
LocalDateTime startTime =
                LocalDateTime.now();
 patients.add(
                new Patient(
                        nextPatientId++,
                        "Rahul",
                        67,
                        "Chest pain and breathing difficulty",
                        "Cardiology",
                        new VitalSigns(
                                128,
                        88,
                                89,
                                37.2,
                        32
                        ),
                        startTime
                )
        );  patients.add(
                new Patient(
                        nextPatientId++,
                        "Ananya",
                        24,
                        "Severe headache",
                        "Neurology",
                        new VitalSigns(
                            92,
                                118,
                             98,
                                37.0,
                               18
                        ),
                        startTime
                )
        );
        patients.add(
                new Patient(
                  nextPatientId++,
                   "Rohan",
                  72,
                "Breathing difficulty",
                        "Pulmonology",
                        new VitalSigns(
                                122,
                        94,
                           90,
                        38.1,
                                34
                        ),
                        startTime
                )
        );
        patients.add(
                new Patient(
                nextPatientId++,                        "Priya",
                35,
                "Minor fever",
                  "General",
                new VitalSigns(
                                82,
                         120,
                                98,
                                37.5,
                                16
                        ),
                        startTime
                )
        );

        patients.add(
                new Patient(
                 nextPatientId++,
                    "Arjun",
                55,
                          "Chest pain",
                         "Cardiology",
                         new VitalSigns(
                                110,
                                96,
                                94,
                                37.1,
                                22
                        ),
                        startTime
                )
        );
        return patients;
    }
    private static int readInteger(
          String message) {

while (true) {
            try {
         System.out.print(message);

         int value =
                        Integer.parseInt(
                                scanner.nextLine().trim()
                        );

                return value;
            } catch (NumberFormatException e) {
                System.out.println(
                        "Please enter a valid integer."
                );
            }
        }
    }
    private static double readDouble(
            String message) {
        while (true) {
            try {
                System.out.print(message);
                double value =
                        Double.parseDouble(
                                scanner.nextLine().trim()
                        );
                return value;
            } catch (NumberFormatException e) {
                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }
}
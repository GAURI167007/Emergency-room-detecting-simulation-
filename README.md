# Emergency-room-detecting-simulation

## Project Overview

The Emergency Room Decision Simulator is a Java-based simulation that models how an emergency room responds to patients with varying medical conditions and changing levels of urgency. Rather than simply maintaining a fixed queue of patients, the project focuses on how decisions can change as new patients arrive, conditions deteriorate, and doctors become available.
Patients are assessed using their symptoms and vital signs to calculate a priority score, which determines their position in the emergency queue. A patient's condition can also deteriorate while waiting, causing their priority to be reassessed and potentially changing the order in which they are treated.
Doctors are assigned to patients based on their areas of specialization. The simulation uses multithreading to model activities occurring concurrently, such as patient arrivals, doctor treatments, and event processing. Priority queues and concurrent data structures are used to manage shared simulation data safely while multiple operations are taking place.
The project also includes exception handling for situations such as invalid patient information and unavailable doctors. Statistics and reports are generated to provide information such as patient waiting times, treatment times, patient outcomes, and doctor workload.
An event-driven processing system coordinates important changes within the simulation, such as patient arrivals, treatment completion, doctor availability, and patient deterioration. This allows the emergency room environment to behave dynamically rather than following a fixed sequence of operations.
The project follows a modular structure, with separate packages for patient and doctor models, triage strategies, doctor allocation, simulation tasks, services, exceptions, and utility functions. This organization demonstrates practical applications of Object-Oriented Programming, Java Collections, Multithreading, Exception Handling, File Handling, and Concurrent Programming.

## Tech stack
- Java
- Object-Oriented Programming
- Collections Framework
- PriorityQueue
- Multithreading
- ExecutorService
- Runnable
- ConcurrentHashMap
- PriorityBlockingQueue
- Exception Handling
- File Handling
- Java Time API
  
 ## Project Structure
Emergency-Room-Decision-Simulator/
│
├── src/
│   ├── Main.java
│   │
│   ├── model/
│   │   ├── Patient.java
│   │   ├── Doctor.java
│   │   ├── Treatment.java
│   │   ├── VitalSigns.java
│   │   ├── EmergencyEvent.java
│   │   ├── EventType.java
│   │   ├── PatientStatus.java
│   │   └── PriorityLevel.java
│   │
│   ├── triage/
│   │   ├── TriageStrategy.java
│   │   └── DynamicTriageStrategy.java
│   │
│   ├── allocation/
│   │   ├── DoctorAllocationStrategy.java
│   │   └── SpecializationAllocator.java
│   │
│   ├── simulation/
│   │   ├── ERSimulation.java
│   │   ├── PatientArrivalTask.java
│   │   ├── DoctorTask.java
│   │   └── EventProcessor.java
│   │
│   ├── service/
│   │   ├── PatientService.java
│   │   ├── TreatmentService.java
│   │   ├── StatisticsService.java
│   │   └── EmergencyQueue.java
│   │
│   ├── exception/
│   │   ├── InvalidPatientException.java
│   │   └── NoDoctorAvailableException.java
│   │
│   └── util/
│       ├── FileManager.java
│       └── InputValidator.java
│
├── data/
│   ├── patients/
│   ├── logs/
│   └── reports/
│
├── README.md
└── .gitignore

Note: This project is an educational simulation. Its triage rules and priority calculations are simplified and are not intended for real-world medical or clinical decision-making.

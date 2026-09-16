package model;
import java.time.LocalDateTime;
public class Patient {
private final int patientId;  
private final String name;  
private final int age;
    private final String symptoms;
    private final String requiredSpecialization;
    private VitalSigns vitalSigns;
private PriorityLevel priorityLevel;
    private int priorityScore;
    private PatientStatus status;
    private LocalDateTime arrivalTime;
    private LocalDateTime treatmentStartTime;
    private LocalDateTime treatmentEndTime;
    private int deteriorationCount;
    public void setPriorityScore(int priorityScore) {
    this.priorityScore = priorityScore;
}
public void setPriorityLevel(PriorityLevel priorityLevel) {
    this.priorityLevel = priorityLevel;
}
    public Patient(
            int patientId,
            String name,
            int age,
            String symptoms,
            String requiredSpecialization,
            VitalSigns vitalSigns,
            LocalDateTime arrivalTime) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.symptoms = symptoms;
          this.requiredSpecialization = requiredSpecialization;
        this.vitalSigns = vitalSigns;
         this.arrivalTime = arrivalTime;

        this.priorityLevel = PriorityLevel.NORMAL;
        this.priorityScore = 0;
        this.status = PatientStatus.WAITING;
        this.deteriorationCount = 0;
    }
 public int getPatientId() {
        return patientId;
    }
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }public String getSymptoms() {
        return symptoms;
    }public String getRequiredSpecialization() {
        return requiredSpecialization;
    }public VitalSigns getVitalSigns() {
        return vitalSigns;
    }public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }public int getPriorityScore() {
        return priorityScore;
    }public PatientStatus getStatus() {
        return status;
    }
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }
    public LocalDateTime getTreatmentStartTime() {
        return treatmentStartTime;
    }
public LocalDateTime getTreatmentEndTime() {
        return treatmentEndTime;
    } public int getDeteriorationCount() {
        return deteriorationCount;
    }
    public void setPriority(PriorityLevel priorityLevel, int priorityScore) {
        this.priorityLevel = priorityLevel;
        this.priorityScore = priorityScore; }
    public void setStatus(PatientStatus status) {
        this.status = status;
    } public void setTreatmentStartTime(LocalDateTime treatmentStartTime) {
        this.treatmentStartTime = treatmentStartTime;
        this.status = PatientStatus.UNDER_TREATMENT;
    }
public void setTreatmentEndTime(LocalDateTime treatmentEndTime) {
        this.treatmentEndTime = treatmentEndTime;
        this.status = PatientStatus.COMPLETED;
    }public void deteriorate() {
        deteriorationCount++;
        status = PatientStatus.DETERIORATED;
    }
public void updateVitalSigns(VitalSigns vitalSigns) {
        this.vitalSigns = vitalSigns;
    }
    @Override
    public String toString() {

        return "Patient ID: " + patientId
                + " | Name: " + name
                + " | Age: " + age
                + " | Priority: " + priorityLevel
                + " | Score: " + priorityScore
                + " | Status: " + status;
    }public void increasePriority(int additionalScore) {
        priorityScore += additionalScore;

    if (priorityScore >= 70) {
        priorityLevel = PriorityLevel.CRITICAL;
    } else if (priorityScore >= 50) {
        priorityLevel = PriorityLevel.URGENT;
    } else if (priorityScore >= 30) {
        priorityLevel = PriorityLevel.MODERATE;
    } else {
        priorityLevel = PriorityLevel.NORMAL;
    }
}
}
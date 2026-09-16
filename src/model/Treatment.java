package model;

import java.time.LocalDateTime;

public class Treatment {

    private final int treatmentId;
    private final int patientId;
    private final int doctorId;

    private final String treatmentType;

    private final LocalDateTime startTime;
    private LocalDateTime endTime;
// this is for treeatment section
public Treatment(
            int treatmentId,
            int patientId,
            int doctorId,
            String treatmentType,
            LocalDateTime startTime) {

        this.treatmentId = treatmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.treatmentType = treatmentType;
        this.startTime = startTime;
    }
    public int getTreatmentId() {
        return treatmentId;
    }
 public int getPatientId() {
        return patientId;
    }
    public int getDoctorId() {
        return doctorId;
    }
 public String getTreatmentType() {
        return treatmentType;
    }
      public LocalDateTime getStartTime() {
        return startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
public void complete(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    @Override
    public String toString() {

        return "Treatment ID: " + treatmentId
                + " | Patient: " + patientId
                + " | Doctor: " + doctorId
                + " | Type: " + treatmentType
                + " | Start: " + startTime
                + " | End: " + endTime;
    }
}
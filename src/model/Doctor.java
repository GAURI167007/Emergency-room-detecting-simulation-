package model;
import java.util.ArrayList;
import java.util.List;
// tells doc availability and type of doc 
public class Doctor {
    private final int doctorId;
    private final String name;
    private final String specialization;
    private boolean available;
    private int patientsTreated;
    private int currentPatientId;
    private final List<Integer> treatedPatientIds;
    public Doctor(
            int doctorId,
            String name,
            String specialization) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
        this.available = true;
        this.patientsTreated = 0;
        this.currentPatientId = -1;
        this.treatedPatientIds = new ArrayList<>();
    }
public int getDoctorId() {
        return doctorId;
    }
public String getName() {
        return name;
    }
public String getSpecialization() {
        return specialization;
}public boolean isAvailable() {
        return available;
    }
public int getPatientsTreated() {
        return patientsTreated;
    } public int getCurrentPatientId() {
        return currentPatientId;
    }
public List<Integer> getTreatedPatientIds() {
        return treatedPatientIds;
    } public void setAvailable(boolean available) {
        this.available = available;
    }

    public void treatPatient(int patientId) {
        patientsTreated++;

        treatedPatientIds.add(patientId);
        currentPatientId = patientId;
        available = false;
    }
    public void completeTreatment() {
    currentPatientId = -1;
        available = true;
    }

    @Override
    public String toString() {
        return "Doctor ID: " + doctorId
                + " | Dr. " + name
                + " | Specialization: " + specialization
                + " | Available: " + available
                + " | Current Patient: " + currentPatientId
                + " | Patients Treated: " + patientsTreated;
    }
}
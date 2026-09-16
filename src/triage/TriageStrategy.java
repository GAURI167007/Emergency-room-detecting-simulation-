package triage;
import model.Patient;
public interface TriageStrategy {
    void assessPatient(Patient patient);
}
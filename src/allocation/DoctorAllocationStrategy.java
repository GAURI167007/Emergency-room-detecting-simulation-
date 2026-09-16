package allocation;
import exception.NoDoctorAvailableException;
import model.Doctor;
import model.Patient;
import java.util.List;
public interface DoctorAllocationStrategy {
    Doctor allocateDoctor(
            Patient patient,
            List<Doctor> doctors
    ) throws NoDoctorAvailableException;
}
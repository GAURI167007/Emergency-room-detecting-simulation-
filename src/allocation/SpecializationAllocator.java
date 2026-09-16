package allocation;

import exception.NoDoctorAvailableException;
import model.Doctor;
import model.Patient;

import java.util.List;

public class SpecializationAllocator
        implements DoctorAllocationStrategy {

    @Override
    public Doctor allocateDoctor(
            Patient patient,
            List<Doctor> doctors)
            throws NoDoctorAvailableException {

        String requiredSpecialization =
                patient.getRequiredSpecialization();

        // First preference: exact specialization
        for (Doctor doctor : doctors) {

            if (doctor.isAvailable()
                    && doctor.getSpecialization()
                    .equalsIgnoreCase(requiredSpecialization)) {

                doctor.treatPatient(patient.getPatientId());

                return doctor;
            }
        }

        // Second preference: General doctor
        for (Doctor doctor : doctors) {

            if (doctor.isAvailable()
                    && doctor.getSpecialization()
                    .equalsIgnoreCase("General")) {

                doctor.treatPatient(patient.getPatientId());

                return doctor;
            }
        }

        // No suitable doctor found
        throw new NoDoctorAvailableException(
                "No suitable doctor available for Patient "
                        + patient.getPatientId()
        );
    }
}
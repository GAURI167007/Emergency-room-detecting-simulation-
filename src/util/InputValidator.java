package util;

import exception.InvalidPatientException;

public class InputValidator {

    public static void validatePatient(
            String name,
            int age,
            String symptoms,
            String specialization)
            throws InvalidPatientException {

        if (name == null || name.trim().isEmpty()) {

            throw new InvalidPatientException(
                    "Patient name cannot be empty."
            );
        }

        if (age <= 0 || age > 120) {

            throw new InvalidPatientException(
                    "Patient age must be between 1 and 120."
            );
        }

        if (symptoms == null
                || symptoms.trim().isEmpty()) {

            throw new InvalidPatientException(
                    "Symptoms cannot be empty."
            );
        }

        if (specialization == null
                || specialization.trim().isEmpty()) {

            throw new InvalidPatientException(
                    "Required specialization cannot be empty."
            );
        }
    }
}
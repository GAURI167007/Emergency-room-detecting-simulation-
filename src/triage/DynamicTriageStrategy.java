package triage;

import model.Patient;
import model.PriorityLevel;
import model.VitalSigns;

public class DynamicTriageStrategy
        implements TriageStrategy {

    @Override
    public void assessPatient(Patient patient) {

        int score = 0;

        String symptoms =
                patient.getSymptoms()
                        .toLowerCase();

        VitalSigns vitals =
                patient.getVitalSigns();
// here the below code will help us to make conditions so that we can prioritize the patients on the basis of keywords used..

        if (containsAny(
                symptoms,
                "unconscious",
                "unresponsive",
                "not responding"
        )) {

            score += 50;
        }

        if (containsAny(
                symptoms,
                "severe bleeding",
                "heavy bleeding",
                "vomiting blood",
                "throwing up blood",
                "blood in vomit",
                "hematemesis"
        )) {

            score += 40;
        }

        if (containsAny(
                symptoms,
                "breathing difficulty",
                "difficulty breathing",
                "shortness of breath",
                "cannot breathe",
                "breathlessness"
        )) {

            score += 30;
        }

        if (containsAny(
                symptoms,
                "chest pain",
                "chest pressure"
        )) {

            score += 25;
        }

        if (containsAny(
                symptoms,
                "severe headache",
                "seizure",
                "stroke",
                "paralysis"
        )) {

            score += 25;
        }

        if (containsAny(
                symptoms,
                "severe abdominal pain",
                "severe pain"
        )) {

            score += 20;
        }


        if (vitals != null) {


            if (vitals.getOxygenSaturation() < 90) {

                score += 40;

            } else if (
                    vitals.getOxygenSaturation() < 94) {

                score += 20;
            }

        

            if (vitals.getHeartRate() > 140
                    || vitals.getHeartRate() < 45) {

                score += 25;

            } else if (
                    vitals.getHeartRate() > 120
                            || vitals.getHeartRate() < 50) {

                score += 15;
            }

            // BP
            if (vitals.getSystolicBP() < 90) {

                score += 30;

            } else if (
                    vitals.getSystolicBP() > 180) {

                score += 20;
            }

            // temperature of the person
            if (vitals.getTemperature() >= 104) {

                score += 20;

            } else if (
                    vitals.getTemperature() >= 102) {

                score += 10;
            }

    //  respiratory rate (usually 15-20 per minute)
            if (vitals.getRespiratoryRate() > 30
                    || vitals.getRespiratoryRate() < 8) {

                score += 25;

            } else if (
                    vitals.getRespiratoryRate() > 24) {

                score += 10;
            }
        }
// age of the person also is a factor

        if (patient.getAge() >= 75) {

            score += 15;

        } else if (patient.getAge() >= 65) {

            score += 10;
        }
// final priority and score

        PriorityLevel priority;
        if (score >= 70) {
        

            
            priority = PriorityLevel.CRITICAL;

        } else if (score >= 50) {

            priority = PriorityLevel.URGENT;

        } else if (score >= 30) {

            priority = PriorityLevel.MODERATE;

        } else {

            priority = PriorityLevel.NORMAL;
        }patient.setPriorityScore(score);
        patient.setPriorityLevel(priority);
    } private boolean containsAny(
            String text,
            String... keywords) {

        for (String keyword : keywords) {

            if (text.contains(keyword)) {

                return true;
            }
        }

        return false;
    }
}
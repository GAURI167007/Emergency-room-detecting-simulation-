package service;

import model.Patient;

import java.util.Comparator;
import java.util.PriorityQueue;

public class EmergencyQueue {

    private final PriorityQueue<Patient> queue;

    public EmergencyQueue() {

        queue = new PriorityQueue<>(
                Comparator.comparingInt(Patient::getPriorityScore)
                        .reversed()
                        .thenComparing(Patient::getArrivalTime)
        );
    }

    public void addPatient(Patient patient) {

        queue.offer(patient);

        System.out.println(
                "Patient " + patient.getName()
                        + " added to emergency queue."
        );
    }

    public Patient getNextPatient() {

        return queue.peek();
    }

    public Patient removeNextPatient() {

        return queue.poll();
    }

    public boolean removePatient(Patient patient) {

        return queue.remove(patient);
    }

    public void requeuePatient(Patient patient) {

        queue.remove(patient);
        queue.offer(patient);
    }

    public boolean isEmpty() {

        return queue.isEmpty();
    }

    public int size() {

        return queue.size();
    }

    public void displayQueue() {

        if (queue.isEmpty()) {

            System.out.println("\nEmergency queue is empty.");
            return;
        }

        PriorityQueue<Patient> temporaryQueue =
                new PriorityQueue<>(queue);

        System.out.println("\n========== EMERGENCY QUEUE ==========");

        int position = 1;

        while (!temporaryQueue.isEmpty()) {

            Patient patient = temporaryQueue.poll();

            System.out.println(
                    position + ". "
                            + patient.getName()
                            + " | ID: "
                            + patient.getPatientId()
                            + " | "
                            + patient.getPriorityLevel()
                            + " | Score: "
                            + patient.getPriorityScore()
                            + " | Status: "
                            + patient.getStatus()
            );

            position++;
        }

        System.out.println("=====================================");
    }
}
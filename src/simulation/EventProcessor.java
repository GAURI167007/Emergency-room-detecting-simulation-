package simulation;

import model.EmergencyEvent;

import java.util.concurrent.TimeUnit;

public class EventProcessor
        implements Runnable {

    private final ERSimulation simulation;

    public EventProcessor(
            ERSimulation simulation) {

        this.simulation = simulation;
    }

    @Override
    public void run() {

        while (simulation.hasPendingWork()) {

            try {

                EmergencyEvent event =
                        simulation.pollEvent(
                                200,
                                TimeUnit.MILLISECONDS
                        );

                if (event != null) {

                    simulation.handleEvent(event);
                }

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

                return;
            }
        }

        System.out.println(
                "\n========================================"
        );

        System.out.println(
                "       SIMULATION COMPLETED"
        );

        System.out.println(
                "========================================"
        );
    }
}
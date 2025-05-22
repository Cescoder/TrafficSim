/*
 * This program simulates a simple traffic system with multiple cars.
 * Each car has its own properties such as position, velocity, and acceleration.
 * The simulation updates the state of each car based on its interactions with other cars.
 * The simulation is displayed in a terminal view.
 * 
 * This code is written in Java and uses multithreading to run the simulation and the terminal view concurrently.
 * 
 * Author: Cescoder (https://github.com/Cescoder)
 * Co-author: gass-ita (https://github.com/gass-ita)
 */

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int carCount = 7; // Number of cars

        Car[] cars = new Car[carCount];

        // Initialize cars with random positions and velocities
        for (int i = 0; i < carCount; i++) {
            double position = (i - 1) * 10 + Math.random() * 5; // Random position
            double velocity = Math.random() * 20;
            double acceleration = 0;
            double desiredVelocity = 18 + Math.random() * 4; // 18 to 22
            double aMax = 1.5 + Math.random() * 1; // 1.5 to 2.5
            double b = 0.8 + Math.random() * 0.6; // 0.8 to 1.4
            double reactionTime = 0.4 + Math.random() * 0.3; // 0.4 to 0.7

            char symbol = (char) ('A' + i);
            cars[i] = new Car(position, velocity, acceleration, desiredVelocity, aMax, b, reactionTime, symbol);
        }

        Universe universe = new Universe(cars);
        TerminalView view = new TerminalView(150, carCount / 2, universe); // 50 characters wide

        // Start the simulation in a separate thread
        Thread simulationThread = new Thread(universe);
        simulationThread.start();
        // Start the terminal view in a separate thread
        Thread viewThread = new Thread(view);
        viewThread.start();
        // Wait for the threads to finish (they won't in this case)
        simulationThread.join();
        viewThread.join();
        // This will never be reached, but it's here to satisfy the main method's
        // signature
    }
}

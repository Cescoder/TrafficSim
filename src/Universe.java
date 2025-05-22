public class Universe implements Runnable {
    private Car[] cars;
    private double dt = 0.01; // base time step in seconds
    private double timeSpeed = 1; // simulation time speed multiplier
    private double simulationTime = 0.0;

    public Universe(Car[] cars) {
        this.cars = cars;
    }

    public void setTimeSpeed(double timeSpeed) {
        this.timeSpeed = timeSpeed;
    }

    public void run() {
        while (true) {
            long startTime = System.currentTimeMillis();

            // Use scaled time step for simulation
            double scaledDt = dt * timeSpeed;
            this.update(scaledDt);

            long endTime = System.currentTimeMillis();
            simulationTime = (endTime - startTime) / 1000.0; // actual time to compute update

            // Keep real-time pace based on unscaled dt
            long sleepTime = (long) ((dt - simulationTime) * 1000); // in milliseconds
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Rendering took too long, skipping frame.");
            }
        }
    }

    public void update(double dt) {
        for (int i = 0; i < cars.length; i++) {
            Car car = cars[i];
            Car carAhead = (i < cars.length - 1) ? cars[i + 1] : null;
            car.computeAcceleration(carAhead);
        }

        for (Car car : cars) {
            car.update(dt);
        }
    }

    public Car[] getCars() {
        return cars;
    }

    public double getDt() {
        return dt;
    }

    public double getSimulationTime() {
        return simulationTime;
    }

    public double getTimeSpeed() {
        return timeSpeed;
    }
}

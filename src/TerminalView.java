import java.util.Arrays;
import java.util.List;

public class TerminalView implements Runnable {
    private double dt = 0.5; // tempo di rendering
    private double renderingTime = 0.0; // tempo di rendering in secondi

    private final int l; // numero di caratteri da stampare in totale (larghezza terminale)
    private double charPerMeter; // caratteri per metro, calcolato dinamicamente
    private double x_0; // posizione reale del primo carattere sullo schermo

    private final int targetCar; // il veicolo da mantenere centrato
    private Universe universe;

    public TerminalView(int l, int targetCar, Universe universe) {
        this.l = l;
        this.targetCar = targetCar;
        this.universe = universe;
    }

    public void update(List<Car> cars) {
        if (cars.isEmpty() || targetCar == -1)
            return;

        // 1. Trova la distanza massima tra i veicoli
        double minPos = cars.stream().mapToDouble(Car::getPosition).min().orElse(0.0);
        double maxPos = cars.stream().mapToDouble(Car::getPosition).max().orElse(0.0);
        double maxDistance = maxPos - minPos;

        // 2. Calcola charPerMeter per adattare tutti i veicoli nella vista
        if (maxDistance == 0)
            maxDistance = 1.0; // evita divisione per zero
        this.charPerMeter = (double) l / maxDistance;

        // 3. Centra la vista sulla targetCar
        double centerPosition = cars.get(targetCar).getPosition();
        this.x_0 = centerPosition - (l / 2.0) / charPerMeter;
    }

    private void printInfo(List<Car> cars) {
        System.out.println("Universe state:");
        // System.out.printf("Time: %.2f\n", universe.getTime());
        System.out.printf("\tExpected Delta time: %.2f s\n", universe.getDt());
        System.out.printf("\tSimulation Time: %.2f s\n", universe.getSimulationTime());
        System.out.printf("\tTime Speed: %.2f\n", universe.getTimeSpeed());
        System.out.printf("View state:\n");
        System.out.printf("\tRendering Time: %.2f s\n", renderingTime);
        System.out.printf("\tWidth: %d c\n", this.getWidth());
        System.out.printf("\tChar per meter: %.2f c/m\n", this.getCharPerMeter());
        System.out.printf("\tX0: %.2f m\n", this.getX0());
        System.out.println("Car states:");
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            double percent = (car.getVelocity() / car.getDesiredVelocity()) * 100.0;
            String prefix = (i == targetCar) ? "*" : " ";
            System.out.printf(
                    "\t%s Car %s:\tPosition: %.2f m,\tVelocity: %.2f m/s (Cruise: %.2f m/s),\tAcceleration: %.2f m/s^2,\taMax: %.2f m/s^2,\tb: %.2f m/s^2,\t%.1f%% of cruise\n",
                    prefix,
                    car.getSymbol(),
                    car.getPosition(),
                    car.getVelocity(),
                    car.getDesiredVelocity(),
                    car.getAcceleration(),
                    car.getAMax(),
                    car.getB(),
                    percent);
        }
    }

    public void render(Universe universe) {
        List<Car> cars = Arrays.asList(universe.getCars());
        update(cars);
        printInfo(cars);
        char[] screen = new char[l];
        Arrays.fill(screen, '-');

        for (Car car : cars) {
            int screenPos = (int) ((car.getPosition() - x_0) * charPerMeter);
            if (screenPos >= 0 && screenPos < l) {
                screen[screenPos] = car.getSymbol();
            }
        }

        System.out.println(new String(screen));
    }

    public int getWidth() {
        return l;
    }

    public double getCharPerMeter() {
        return charPerMeter;
    }

    public double getX0() {
        return x_0;
    }

    @Override
    public void run() {
        for (;;) {
            // start counting time
            long startTime = System.currentTimeMillis();
            // clear the screen
            System.out.print("\033[H\033[2J");
            System.out.flush();
            // render the universe
            this.render(universe);
            long endTime = System.currentTimeMillis();
            renderingTime = (endTime - startTime) / 1000.0; // Convert to seconds
            // Sleep for the remaining time to maintain the desired frame rate
            long sleepTime = (long) ((dt - renderingTime) * 1000); // Convert to milliseconds
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
}

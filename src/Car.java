public class Car {
    private double velocity;
    private double acceleration;
    private double position;

    private double d0 = 2.0; // distanza minima
    private double reactionTime = 1.0; // tempo di reazione (s)

    private double desiredVelocity = 30.0; // velocità desiderata (m/s)
    private double aMax = 1.0; // accelerazione massima (m/s^2)
    private double b = 2.0; // decelerazione massima (m/s^2)

    // Carattere per la rappresentazione del veicolo
    private char symbol;

    public Car(double position_0, double velocity_0, double acceleration_0, double desiredVelocity, double aMax,
            double b, double reactionTime, char symbol) {
        this.velocity = velocity_0;
        this.acceleration = acceleration_0;
        this.position = position_0;
        this.desiredVelocity = desiredVelocity;
        this.aMax = aMax;
        this.reactionTime = reactionTime;
        this.b = b;

        this.symbol = symbol;
    }

    public void update(double dt) {
        this.position += this.velocity * dt + 0.5 * this.acceleration * dt * dt;
        this.velocity += this.acceleration * dt;
    }

    public void computeAcceleration(Car carAhead) {

        if (carAhead == null) {
            // Se non c'è nessuno davanti, accelera fino alla velocità desiderata
            double acc = aMax * (1 - Math.pow(velocity / desiredVelocity, 4));
            this.acceleration = acc;
            return;
        }

        double s = carAhead.getPosition() - this.position; // distanza
        double deltaV = this.velocity - carAhead.getVelocity(); // velocità relativa

        double sStar = d0 + this.velocity * reactionTime +
                (this.velocity * deltaV) / (2 * Math.sqrt(aMax * b));

        double acc = aMax * (1 - Math.pow(this.velocity / desiredVelocity, 4)
                - Math.pow(sStar / s, 2));

        this.acceleration = acc;
    }

    public double getPosition() {
        return position;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public double getDesiredVelocity() {
        return desiredVelocity;
    }

    public double getAMax() {
        return aMax;
    }

    public double getB() {
        return b;
    }

    public double getD0() {
        return d0;
    }

    public double getReactionTime() {
        return reactionTime;
    }

    public void setPosition(double position) {
        this.position = position;
    }

}

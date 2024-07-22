package org.firstinspires.ftc.teamcode;

public class PIDController {
    private double kP, kI, kD;
    private double integral, previousError;
    private double setpoint;

    public PIDController(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.integral = 0.0;
        this.previousError = 0.0;
        this.setpoint = 0.0;
    }

    public void setSetpoint(double setpoint) {
        this.setpoint = setpoint;
        this.integral = 0.0;
        this.previousError = 0.0;
    }

    public double calculate(double currentPosition) {
        double error = setpoint - currentPosition;
        integral += error;
        double derivative = error - previousError;
        previousError = error;
        return kP * error + kI * integral + kD * derivative;
    }
}

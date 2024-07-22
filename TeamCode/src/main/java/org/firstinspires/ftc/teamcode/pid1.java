package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="RobotControlWithLiftAndManualPID", group="Examples")
public class pid1 extends LinearOpMode {

    // Объявление моторных переменных
    private DcMotor motor1;
    private DcMotor motor2;
    private DcMotor motor3;
    private DcMotor motor4;
    private DcMotor motor5;
    private DcMotor motor6;

    // Коэффициенты PID-регулятора
    private static final double Kp = 1.0;
    private static final double Ki = 0.1;
    private static final double Kd = 0.1;

    private double previousError1 = 0;
    private double integral1 = 0;
    private double previousError2 = 0;
    private double integral2 = 0;
    private double previousError3 = 0;
    private double integral3 = 0;
    private double previousError4 = 0;
    private double integral4 = 0;

    // Флаги для управления лифтом
    private boolean liftUp = false;
    private boolean liftDown = false;

    @Override
    public void runOpMode() {

        motor1 = hardwareMap.get(DcMotor.class, "motor1");
        motor2 = hardwareMap.get(DcMotor.class, "motor2");
        motor3 = hardwareMap.get(DcMotor.class, "motor3");
        motor4 = hardwareMap.get(DcMotor.class, "motor4");
        motor5 = hardwareMap.get(DcMotor.class, "motor5");
        motor6 = hardwareMap.get(DcMotor.class, "motor6");


        motor1.setDirection(DcMotor.Direction.FORWARD);
        motor2.setDirection(DcMotor.Direction.FORWARD);
        motor3.setDirection(DcMotor.Direction.REVERSE);
        motor4.setDirection(DcMotor.Direction.REVERSE);
        motor5.setDirection(DcMotor.Direction.REVERSE);
        motor6.setDirection(DcMotor.Direction.FORWARD);


        motor5.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor6.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor5.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor6.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        while (opModeIsActive()) {

            double drive = -gamepad1.left_stick_y;
            double turn  = gamepad1.left_stick_x;


            double targetPower1 = drive - turn;
            double targetPower2 = drive - turn;
            double targetPower3 = drive + turn;
            double targetPower4 = drive + turn;


            double currentSpeed1 = motor1.getPower();
            double currentSpeed2 = motor2.getPower();
            double currentSpeed3 = motor3.getPower();
            double currentSpeed4 = motor4.getPower();


            double power1 = calculatePID(targetPower1, currentSpeed1, previousError1, integral1);
            double power2 = calculatePID(targetPower2, currentSpeed2, previousError2, integral2);
            double power3 = calculatePID(targetPower3, currentSpeed3, previousError3, integral3);
            double power4 = calculatePID(targetPower4, currentSpeed4, previousError4, integral4);


            motor1.setPower(power1);
            motor2.setPower(power2);
            motor3.setPower(power3);
            motor4.setPower(power4);


            previousError1 = targetPower1 - currentSpeed1;
            integral1 += previousError1;
            previousError2 = targetPower2 - currentSpeed2;
            integral2 += previousError2;
            previousError3 = targetPower3 - currentSpeed3;
            integral3 += previousError3;
            previousError4 = targetPower4 - currentSpeed4;
            integral4 += previousError4;


            if (gamepad1.right_trigger > 0) {

                int targetPosition = motor5.getCurrentPosition() + 10;
                motor5.setTargetPosition(targetPosition);
                motor6.setTargetPosition(targetPosition);
                motor5.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motor6.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motor5.setPower(0.5);
                motor6.setPower(0.5);
            } else if (gamepad1.left_trigger > 0) {

                int targetPosition = motor5.getCurrentPosition() - 10;
                motor5.setTargetPosition(targetPosition);
                motor6.setTargetPosition(targetPosition);
                motor5.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motor6.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motor5.setPower(0.5);
                motor6.setPower(0.5);
            } else if (gamepad1.a && !liftUp) {

                motor5.setTargetPosition(3600);
                motor6.setTargetPosition(3600);
                motor5.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motor6.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motor5.setPower(0.5);
                motor6.setPower(0.5);
                liftUp = true;
            } else if (gamepad1.b && !liftDown) {

                motor5.setTargetPosition(0);
                motor6.setTargetPosition(0);
                motor5.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motor6.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motor5.setPower(0.5);
                motor6.setPower(0.5);
                liftDown = true;
            }


            if (!gamepad1.a) {
                liftUp = false;
            }
            if (!gamepad1.b) {
                liftDown = false;
            }


            if (!gamepad1.a && !gamepad1.b && gamepad1.right_trigger == 0 && gamepad1.left_trigger == 0) {
                motor5.setPower(0);
                motor6.setPower(0);
                motor5.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motor6.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }


            telemetry.addData("Motor1 Power", power1);
            telemetry.addData("Motor2 Power", power2);
            telemetry.addData("Motor3 Power", power3);
            telemetry.addData("Motor4 Power", power4);
            telemetry.addData("Motor5 Position", motor5.getCurrentPosition());
            telemetry.addData("Motor6 Position", motor6.getCurrentPosition());
            telemetry.update();
        }
    }


    private double calculatePID(double targetPower, double currentSpeed, double previousError, double integral) {
        double error = targetPower - currentSpeed;
        integral += error;
        double derivative = error - previousError;
        return Kp * error + Ki * integral + Kd * derivative;
    }
}

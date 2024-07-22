package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "meow3", group = "Linear Opmode")
public class meow3 extends LinearOpMode {

    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor emz = null;
    private DcMotor skibidi = null;

    private static final int TARGET_POSITION = 1000;
    private static final int ZERO_POSITION = 0;

    private boolean setPositionButtonPressed = false;
    private boolean resetPositionButtonPressed = false;

    private boolean liftUp = false;
    private boolean liftDown = false;

    @Override
    public void runOpMode() {

        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        emz = hardwareMap.get(DcMotor.class, "emz");
        skibidi = hardwareMap.get(DcMotor.class, "skibidi");

        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);

        emz.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        skibidi.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        emz.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        skibidi.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        while (opModeIsActive()) {

            double leftTrigger = gamepad1.left_trigger;
            double rightTrigger = gamepad1.right_trigger;
            boolean dpadUp = gamepad1.dpad_up;
            boolean dpadDown = gamepad1.dpad_down;
            boolean leftBumper = gamepad1.left_bumper;
            boolean rightBumper = gamepad1.right_bumper;
            boolean setPositionButton = gamepad1.a;
            boolean resetPositionButton = gamepad1.b;


            double leftPower = 0.0;
            double rightPower = 0.0;

            if (dpadUp) {
                leftPower = 1.0;
                rightPower = 1.0;
            } else if (dpadDown) {
                leftPower = -1.0;
                rightPower = -1.0;
            } else if (leftBumper){
                leftPower = 0.8;
                rightPower = -0.8;
            } else if (rightBumper){
                leftPower = -0.8;
                rightPower = 0.8;
            }


            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);


            double liftPower = 0.0;

            if (gamepad1.right_trigger > 0) {
                int targetPosition = emz.getCurrentPosition() + 10;
                emz.setTargetPosition(targetPosition);
                skibidi.setTargetPosition(targetPosition);
                emz.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                skibidi.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                emz.setPower(0.5);
                skibidi.setPower(0.5);
            } else if (gamepad1.left_trigger > 0) {

                int targetPosition = emz.getCurrentPosition() - 10;
                emz.setTargetPosition(targetPosition);
                skibidi.setTargetPosition(targetPosition);
                emz.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                skibidi.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                emz.setPower(0.5);
                skibidi.setPower(0.5);
            } else if (gamepad1.a && !liftUp) {

                emz.setTargetPosition(3600);
                skibidi.setTargetPosition(3600);
                emz.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                skibidi.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                emz.setPower(0.5);
                skibidi.setPower(0.5);
                liftUp = true;
            } else if (gamepad1.b && !liftDown) {

                emz.setTargetPosition(0);
                skibidi.setTargetPosition(0);
                emz.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                skibidi.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                emz.setPower(0.5);
                skibidi.setPower(0.5);
                liftDown = true;
            }


            if (!gamepad1.a) {
                liftUp = false;
            }
            if (!gamepad1.b) {
                liftDown = false;
            }


            if (!gamepad1.a && !gamepad1.b && gamepad1.right_trigger == 0 && gamepad1.left_trigger == 0) {
                emz.setPower(0);
                skibidi.setPower(0);
                emz.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                skibidi.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }


            telemetry.addData("Left Power", leftPower);
            telemetry.addData("Right Power", rightPower);
            telemetry.addData("Lift Power", liftPower);
            telemetry.addData("EMZ Position", emz.getCurrentPosition());
            telemetry.addData("Skibidi Position", skibidi.getCurrentPosition());
            telemetry.update();
        }
    }
}
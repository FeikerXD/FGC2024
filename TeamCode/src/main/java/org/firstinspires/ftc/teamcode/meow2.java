package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;


@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="meow2", group = "Examples")
public class meow2 extends LinearOpMode {

    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private DcMotor emz;
    private DcMotor skibidi;

    private static final int TARGET_POSITION = 1000; // Пример целевой позиции
    private static final int ZERO_POSITION = 0;

    private static final PIDFCoefficients BASE_PID = new PIDFCoefficients(5.0, 0.0, 0.0, 0.0);
    private static final PIDFCoefficients LIFT_PID = new PIDFCoefficients(10.0, 0.0, 0.0, 0.0);


    @Override
    public void runOpMode() throws InterruptedException {
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
            } else {
                leftPower = leftTrigger;
                rightPower = rightTrigger;
            }

            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);

            double liftPower = 0.0;

            if (leftBumper) {
                liftPower = -1.0;
            } else if (rightBumper) {
                liftPower = 1.0;
            }

            emz.setPower(liftPower);
            skibidi.setPower(liftPower);

            if (setPositionButton) {
                emz.setTargetPosition(TARGET_POSITION);
                skibidi.setTargetPosition(TARGET_POSITION);

                emz.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                skibidi.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                emz.setPower(1.0);
                skibidi.setPower(1.0);
            }

            if (resetPositionButton) {
                emz.setTargetPosition(ZERO_POSITION);
                skibidi.setTargetPosition(ZERO_POSITION);

                emz.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                skibidi.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                emz.setPower(1.0);
                skibidi.setPower(1.0);
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


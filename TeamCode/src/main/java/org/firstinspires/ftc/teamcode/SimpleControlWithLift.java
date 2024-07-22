package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="RobotControlWithLift", group="Examples")
public class SimpleControlWithLift extends LinearOpMode {

    private DcMotor left_drive;
    private DcMotor right_drive;
    private DcMotor liftMotor1;
    private DcMotor liftMotor2;

    private boolean liftUp = false;
    private boolean liftDown = false;

    @Override
    public void runOpMode() {

        left_drive = hardwareMap.get(DcMotor.class, "leftDrive");
        right_drive = hardwareMap.get(DcMotor.class, "rightDrive");
        liftMotor1 = hardwareMap.get(DcMotor.class, "emz");
        liftMotor2 = hardwareMap.get(DcMotor.class, "skibidi");

        left_drive.setDirection(DcMotor.Direction.FORWARD);
        right_drive.setDirection(DcMotor.Direction.REVERSE);

        liftMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        while (opModeIsActive()) {
            double drive = -gamepad1.left_stick_y;
            double turn  = gamepad1.left_stick_x;

            double powerLeft = drive - turn;
            double powerRight = drive + turn;

            left_drive.setPower(powerLeft);
            right_drive.setPower(powerRight);

            double liftPower = 0.0;



            liftMotor1.setPower(liftPower);
            liftMotor2.setPower(liftPower);

            if (gamepad1.right_trigger > 0) {
                int targetPosition = liftMotor1.getCurrentPosition() + 10;
                moveLiftToPosition(targetPosition);
            } else if (gamepad1.left_trigger > 0) {
                int targetPosition = liftMotor1.getCurrentPosition() - 10;
                moveLiftToPosition(targetPosition);
            } else if (gamepad1.a && !liftUp) {
                int targetPosition = 3600;
                moveLiftToPosition(targetPosition);
                liftUp = true;
            } else if (gamepad1.b && !liftDown) {
                int targetPosition = 0;
                moveLiftToPosition(targetPosition);
                liftDown = true;
            }

            if (!gamepad1.a) {
                liftUp = false;
            }
            if (!gamepad1.b) {
                liftDown = false;
            }

            if (!gamepad1.a && !gamepad1.b && gamepad1.right_trigger == 0 && gamepad1.left_trigger == 0) {
                liftMotor1.setPower(0);
                liftMotor2.setPower(0);
            }

            telemetry.addData("Left Power", powerLeft);
            telemetry.addData("Right Power", powerRight);
            telemetry.addData("Lift Power", liftPower);
            telemetry.addData("Lift Motor1 Position", liftMotor1.getCurrentPosition());
            telemetry.addData("Lift Motor2 Position", liftMotor2.getCurrentPosition());
            telemetry.update();
        }
    }

    private void moveLiftToPosition(int targetPosition) {
        liftMotor1.setTargetPosition(targetPosition);
        liftMotor2.setTargetPosition(targetPosition);
        liftMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor1.setPower(0.5);
        liftMotor2.setPower(0.5);

        while (opModeIsActive() && liftMotor1.isBusy() && liftMotor2.isBusy()) {
            // Ожидание достижения целевой позиции
        }

        liftMotor1.setPower(0);
        liftMotor2.setPower(0);
        liftMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}

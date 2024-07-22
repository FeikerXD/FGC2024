package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TankDriveWithLift", group="Examples")
public class TankDriveWithLift extends LinearOpMode {

    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private DcMotor emz;
    private DcMotor skibidi;

    private static final int LIFT_POSITION_TARGET = 3600;

    @Override
    public void runOpMode() {

        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        emz = hardwareMap.get(DcMotor.class, "emz");
        skibidi = hardwareMap.get(DcMotor.class, "skibidi");

        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        emz.setDirection(DcMotor.Direction.FORWARD);
        skibidi.setDirection(DcMotor.Direction.FORWARD);

        emz.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        skibidi.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        emz.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        skibidi.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        while (opModeIsActive()) {
            double drive = -gamepad1.left_stick_y;
            double turn  = gamepad1.left_stick_x;

            double leftPower = drive + turn;
            double rightPower = drive - turn;

            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);

            double liftPower = gamepad1.right_trigger - gamepad1.left_trigger;

            if (gamepad1.a) {
                moveLiftToPosition(LIFT_POSITION_TARGET);
            } else if (gamepad1.b) {
                moveLiftToPosition(0);
            } else {
                emz.setPower(liftPower);
                skibidi.setPower(liftPower);
            }

            telemetry.addData("Left Power", leftPower);
            telemetry.addData("Right Power", rightPower);
            telemetry.addData("Lift Power", liftPower);
            telemetry.addData("Lift Motor EMZ Position", emz.getCurrentPosition());
            telemetry.addData("Lift Motor SKIBIDI Position", skibidi.getCurrentPosition());
            telemetry.update();
        }
    }

    private void moveLiftToPosition(int targetPosition) {
        emz.setTargetPosition(targetPosition);
        skibidi.setTargetPosition(targetPosition);
        emz.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        skibidi.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        emz.setPower(0.5);
        skibidi.setPower(0.5);

        while (opModeIsActive() && (emz.isBusy() || skibidi.isBusy())) {

        }

        emz.setPower(0);
        skibidi.setPower(0);
        emz.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        skibidi.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}

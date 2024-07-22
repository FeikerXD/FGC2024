package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="MotorControlExample", group="Examples")
public class MotorControlExample extends LinearOpMode {

    // Объявление моторных переменных
    private DcMotor motor1 ;
    private DcMotor motor2;
    private DcMotor motor3;
    private DcMotor motor4;
    private DcMotor motor5;
    private DcMotor motor6;
    double newTarget;
    double turnage;
    double ticks = 3600;

    @Override
    public void runOpMode() {

        // Инициализация моторов
        motor1 = hardwareMap.get(DcMotor.class, "motor_1");
        motor2 = hardwareMap.get(DcMotor.class, "motor_2");
        motor3 = hardwareMap.get(DcMotor.class, "motor_3");
        motor4 = hardwareMap.get(DcMotor.class, "motor_4");
        motor5 = hardwareMap.get(DcMotor.class,"Llift");
        motor6 = hardwareMap.get(DcMotor.class,"Rlift");

        // Устанавливаем направление моторов, если необходимо
        motor1.setDirection(DcMotor.Direction.FORWARD);
        motor2.setDirection(DcMotor.Direction.FORWARD);
        motor3.setDirection(DcMotor.Direction.REVERSE);
        motor4.setDirection(DcMotor.Direction.REVERSE);
        motor5.setDirection(DcMotor.Direction.REVERSE);
        motor6.setDirection(DcMotor.Direction.FORWARD);

        motor6.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor5.setMode(DcMotor.RunMode.RUN_USING_ENCODER);




        // Ожидание старта программы
        waitForStart();

        while (opModeIsActive()) {

            // Управление движением робота
            double drive = -gamepad1.left_stick_y; // Движение вперед/назад
            double turn  = gamepad1.right_stick_x; // Повороты
            double lift = gamepad1.right_stick_y;  // Лифт

            // Вычисление мощности для каждого мотора
            double motor1Power = (drive - turn);
            double motor2Power = (drive - turn);
            double motor3Power = drive + turn;
            double motor4Power = drive + turn;
            double motor56power = lift;



            // Установка мощности моторов
            motor1.setPower(motor1Power);
            motor2.setPower(motor2Power);
            motor3.setPower(motor3Power);
            motor4.setPower(motor4Power);
            motor5.setPower(motor56power);
            motor6.setPower(motor56power);



            // Отображение информации на драйверском экране
            telemetry.addData("Motor1 Power", motor1Power);
            telemetry.addData("Motor2 Power", motor2Power);
            telemetry.addData("Motor3 Power", motor3Power);
            telemetry.addData("Motor4 Power", motor4Power);
            telemetry.addData("Encoder", motor6.getCurrentPosition());
            telemetry.addData("Encoder2", motor5.getCurrentPosition());
            telemetry.addData("R2", gamepad1.right_trigger);
            telemetry.update();
        }
    }
}

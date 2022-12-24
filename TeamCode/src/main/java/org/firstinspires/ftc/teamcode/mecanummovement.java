package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="2022PowerPlay5", group="TeleOp")
public class mecanummovement extends LinearOpMode {
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor lsLeft = null;
    private DcMotor lsRight = null;
    //private Servo intakeClaw = null;
    //private Servo armturn = null;
    //private Servo afLeft = null;
    //private Servo afRight = null;
    private final double driveAdjuster = 1;
    @Override
    public void runOpMode()  throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        //frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        //backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        //frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        //backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        lsLeft = hardwareMap.dcMotor.get("lsLeft");
        lsRight = hardwareMap.dcMotor.get("lsRight");
        //intakeClaw = hardwareMap.servo.get("intakeClaw");
        //armturn = hardwareMap.servo.get("armturn");
        //afLeft = hardwareMap.servo.get("afLeft");
        //afRight = hardwareMap.servo.get("afRight");

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        lsLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();

        while (opModeIsActive()) {
            double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
            double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
            double rightX = gamepad1.right_stick_x;
            final double v1 = r * Math.cos(robotAngle) + rightX;
            final double v2 = r * Math.sin(robotAngle) - rightX;
            final double v3 = r * Math.sin(robotAngle) + rightX;
            final double v4 = r * Math.cos(robotAngle) - rightX;

            frontLeft.setPower(v1);
            frontRight.setPower(v2);
            backLeft.setPower(v3);
            backRight.setPower(v4);
            lsLeft.setPower(gamepad2.left_stick_y);
            lsRight.setPower(gamepad2.left_stick_y);


        /*   if(gamepad2.b) {
                intakeClaw.setPosition(90);

            }
            */
        /*    if (gamepad2.a){
                afLeft.setPosition(180);
                afRight.setPosition(180);
                wait(5000);
                armturn.setPosition(180);

         */

            }

        }
    }
}

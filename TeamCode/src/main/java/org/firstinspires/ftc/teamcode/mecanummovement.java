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
    private DcMotor LinearSlide1 = null;
    private DcMotor LinearSlide2 = null;
    private Servo intakeClaw = null;
    //private Servo armturn = null;
    //private Servo armflip1 = null;
    //private Servo armflip2 = null;
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
        LinearSlide1 = hardwareMap.dcMotor.get("LinearSlide1");
        LinearSlide2 = hardwareMap.dcMotor.get("LinearSlide2");
        intakeClaw = hardwareMap.servo.get("arm");
        //armturn = hardwareMap.servo.get("armturn");
        //armflip1 = hardwareMap.servo.get("armflip1");
        //armflip2 = hardwareMap.servo.get("armflip2");

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
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

            if(gamepad2.b) {
                arm.setPosition(90);

            }

            //Finds the hypotenous of the triangle created by the two joystick values. Used to find the absoulte direction to go in.
            //double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
            //Finds the robot's angle from the raw values of the joystick
            //double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
            //double rightX = gamepad1.right_stick_x;
            //final double v1 = r * Math.cos(robotAngle) + rightX;
            //final double v2 = r * Math.sin(robotAngle) - rightX;
            //final double v3 = r * Math.sin(robotAngle) + rightX;
            //final double v4 = r * Math.cos(robotAngle) - rightX;
            //if(gamepad1.left_stick_y > 0.15 || gamepad1.left_stick_y < -0.15 || gamepad1.left_stick_x > 0.15 || gamepad1.left_stick_x < -0.15) {
                //frontLeft.setPower(v1);
                //frontRight.setPower(v2);
                //backLeft.setPower(v3);
                //backRight.setPower(v4);
            //}
            //Spins the robot right
            //else if(gamepad1.right_bumper) {
                //frontLeft.setPower(0.5);
                //backLeft.setPower(0.5);
                //frontRight.setPower(-0.5);
                //backRight.setPower(-0.5);
            //}
            //Spins the robot left
            //else if(gamepad1.left_bumper) {
                //frontLeft.setPower(-0.5);
                //backLeft.setPower(-0.5);
                //frontRight.setPower(0.5);
                //backRight.setPower(0.5);
            //}
            //Stops all movement
            //else {
                //frontLeft.setPower(0);
                //backLeft.setPower(0);
                //frontRight.setPower(0);
                //backRight.setPower(0);
            //}

        }
    }
}

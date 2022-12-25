package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="2022PowerPlay5", group="TeleOp")
public class mecanummovement extends LinearOpMode {
    //Hardware robot = new Hardware();
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor lsLeft = null;
    private DcMotor lsRight = null;
    private Servo intakeClaw = null;
    private Servo armturn = null;
    private Servo afLeft = null;
    private Servo afRight = null;
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
        intakeClaw = hardwareMap.servo.get("intakeClaw");
        armturn = hardwareMap.servo.get("armturn");
        afLeft = hardwareMap.servo.get("afLeft");
        afRight = hardwareMap.servo.get("afRight");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        //backLeft.setDirection(DcMotor.Direction.REVERSE);
        //backRight.setDirection(DcMotor.Direction.REVERSE);
        lsLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            frontLeft.setPower(frontLeftPower);
            backLeft.setPower(backLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(backRightPower);
            lsLeft.setPower(-gamepad2.left_stick_y);
            lsRight.setPower(-gamepad2.left_stick_y);



            /*if(gamepad2.b) {
                intakeClaw.setPosition(90);

            }
            if(gamepad2.x) {
                intakeClaw.setPosition(-90);
            }
            */


        /*    if (gamepad2.a){
                afLeft.setPosition(180);
                afRight.setPosition(180);
                wait(5000);
                armturn.setPosition(180);

         */



        }
        if(gamepad2.left_bumper){
            intakeClaw.setPosition(35);

        }
        if (gamepad2.right_bumper){
            intakeClaw.setPosition(0);
        }
    }
}

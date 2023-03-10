package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.CRServo;
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
    private CRServo armturn = null;
    private CRServo afLeft = null;
    private CRServo afRight = null;
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
        armturn = hardwareMap.crservo.get("armturn");
        afLeft = hardwareMap.crservo.get("afLeft");
        afRight = hardwareMap.crservo.get("afRight");
        //afLeft.setDirection(CRServo.Direction.REVERSE);
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
            //armturn.setPower(-gamepad2.right_stick_x);


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
        afLeft.setPower(-gamepad2.right_stick_y);
        afRight.setPower(-gamepad2.right_stick_y);
        armturn.setPower(-gamepad2.right_stick_x);
        if (gamepad2.right_bumper) {
            afLeft.setPower(1);
            afRight.setPower(1);
            sleep(1000);
            afLeft.setPower(0);
            afRight.setPower(0);
            //  ArmUp(40000,1);
            //armturn.setPosition(0);
            //armturn.setPosition(0.5);
        }
        if(gamepad2.left_bumper){
            afLeft.setPower(-1);
            afRight.setPower(-1);
            sleep(1000);
            afLeft.setPower(0);
            afRight.setPower(0);

        }
        if (gamepad2.y){
            intakeClaw.setPosition(105);
        }
        if (gamepad2.a){
            intakeClaw.setPosition(0);
        }
    }
}

package org.firstinspires.ftc.teamcode;

//imports libraries
import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

//declares motors
@com.qualcomm.robotcore.eventloop.opmode.TeleOp (name = "Test")
public class Test extends LinearOpMode {

    protected CRServo afLeft;
    protected CRServo afRight;
    protected DcMotor lsLeft;
    protected DcMotor lsRight;
    protected CRServo armturn;
    protected Servo intakeClaw;
    protected int ArmUpPos = 0;
    protected float power = 0;
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        intakeClaw = hardwareMap.servo.get("intakeClaw");
        afLeft =  hardwareMap.crservo.get("afLeft");
        afRight = hardwareMap.crservo.get("afRight");
        armturn =  hardwareMap.crservo.get("armturn");
        lsLeft = hardwareMap.dcMotor.get("lsLeft");
        lsRight = hardwareMap.dcMotor.get("lsRight");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        while (opModeIsActive()){
            frontLeft.setDirection(DcMotor.Direction.REVERSE);
            //backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            //backRight.setDirection(DcMotorSimple.Direction.REVERSE);
            afLeft.setDirection(CRServo.Direction.REVERSE);
            lsLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            lsLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            lsRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            telemetry.addData("Speed and Power:", gamepad2.left_stick_y);
            lsLeft.setPower(-gamepad2.left_stick_y);
            lsRight.setPower(-gamepad2.left_stick_y);
            afLeft.setPower(-gamepad2.right_stick_x);
            afRight.setPower(-gamepad2.right_stick_x);
            armturn.setPower(-gamepad2.right_stick_y);
            if (gamepad2.right_bumper) {
                afLeft.setPower(-1);
                afRight.setPower(-1);
                sleep(1000);
                afLeft.setPower(0);
                afRight.setPower(0);
                //ArmUp(50000,1);
                armturn.setPower(0.5);
                sleep(400);
                armturn.setPower(0);
                lsLeft.setPower(1);
                lsRight.setPower(1);
                sleep(2250);
                lsLeft.setPower(0);
                lsRight.setPower(0);

                //intakeClaw.setPosition(0);
                //  ArmUp(40000,1);
                //armturn.setPosition(0);
                //armturn.setPosition(0.5);
                lsLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lsRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
             if(gamepad2.left_bumper){
                afLeft.setPower(-1);
                afRight.setPower(-1);
                sleep(1000);
                afLeft.setPower(0);
                afRight.setPower(0);

            }
             if (gamepad2.y){
                 intakeClaw.setPosition(180);
             }
             if (gamepad2.a){
                 intakeClaw.setPosition(0);
             }
             //frontRight.setPower(1);
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
            //lsLeft.setPower(-gamepad2.left_stick_y);
            //lsRight.setPower(-gamepad2.left_stick_y);

        }
    }




    private void ArmUp(int ArmUpTarget, double ArmSpeed) {
        ArmUpPos += ArmUpTarget;
        lsLeft.setTargetPosition(ArmUpPos);
        lsRight.setTargetPosition(ArmUpPos);
        lsLeft.setPower(ArmSpeed);
        lsRight.setPower(ArmSpeed);
        while (opModeIsActive() && lsLeft.isBusy() && lsRight.isBusy()) {
            idle();
        }
    }
}

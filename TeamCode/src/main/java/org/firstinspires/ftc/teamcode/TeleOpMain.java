package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.Encoder;

@TeleOp
public class TeleOpMain extends LinearOpMode {
    private DcMotor lsLeft;
    private DcMotor lsRight;
    private DcMotor lsTurn;
    private DcMotor lsIntake;
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor backLeft;
    private Servo intakeClaw;
    private Encoder right;
    private Encoder left;
    @Override
    public void runOpMode(){
        lsLeft = hardwareMap.dcMotor.get("lsLeft");
        lsRight = hardwareMap.dcMotor.get("lsRight");
        lsTurn = hardwareMap.dcMotor.get("lsTurn");
        lsIntake = hardwareMap.dcMotor.get("lsIntake");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        intakeClaw = hardwareMap.servo.get("intakeClaw");
        left = new Encoder(hardwareMap.get(DcMotorEx.class, "frontRight"));
        right = new Encoder(hardwareMap.get(DcMotorEx.class, "backLeft"));
        right.setDirection(Encoder.Direction.REVERSE);
        lsLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        lsTurn.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        while(opModeIsActive()){
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
            telemetry.addData(" Right Encoder Current Position",right.getCurrentPosition());
            telemetry.addData(" Left Encoder Current Position",left.getCurrentPosition());
            telemetry.addData(" Right Encoder Corrected Velocity",right.getCorrectedVelocity());
            telemetry.addData(" Left Encoder Corrected Velocity",left.getCorrectedVelocity());
            frontLeft.setPower(frontLeftPower);
            backLeft.setPower(backLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(backRightPower);
            lsLeft.setPower(-gamepad2.left_stick_y);
            lsRight.setPower(-gamepad2.left_stick_y);
            lsTurn.setPower(-gamepad2.right_stick_x);
            lsIntake.setPower(-gamepad2.right_stick_y * 0.8);
            telemetry.update();
            if(gamepad2.left_trigger >=0.1){
                intakeClaw.setPosition(1);
            }
            if(gamepad2.right_trigger >= 0.1){
                intakeClaw.setPosition(0.4);
            }
        }
    }
}

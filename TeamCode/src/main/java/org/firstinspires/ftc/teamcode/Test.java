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

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        intakeClaw = hardwareMap.servo.get("intakeClaw");
        afLeft =  hardwareMap.crservo.get("afLeft");
        afRight = hardwareMap.crservo.get("afRight");
        armturn =  hardwareMap.crservo.get("armturn");
        lsLeft = hardwareMap.dcMotor.get("lsLeft");
        lsRight = hardwareMap.dcMotor.get("lsRight");
        while (opModeIsActive()){
            afLeft.setDirection(CRServo.Direction.REVERSE);
            lsLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            lsLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            lsRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            telemetry.addData("Speed and Power:", gamepad2.left_stick_y);
            lsLeft.setPower(gamepad2.left_stick_y);
            lsRight.setPower(gamepad2.left_stick_y);
            afLeft.setPower(-gamepad2.right_stick_y);
            //afRight.setPower(-gamepad2.right_stick_y);
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




    private void ArmUp(int ArmUpTarget, double ArmSpeed) {
        ArmUpPos += ArmUpTarget;
        lsLeft.setTargetPosition(ArmUpTarget);
        lsRight.setTargetPosition(ArmUpTarget);
        lsLeft.setPower(ArmSpeed);
        lsRight.setPower(ArmSpeed);
        while (opModeIsActive() && lsLeft.isBusy() && lsRight.isBusy()) {
            idle();
        }
    }
}

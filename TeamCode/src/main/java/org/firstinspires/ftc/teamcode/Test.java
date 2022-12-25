package org.firstinspires.ftc.teamcode;

//imports libraries
import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

//declares motors
@com.qualcomm.robotcore.eventloop.opmode.TeleOp (name = "Test")
public class Test extends LinearOpMode {

    protected Servo afLeft;
    protected Servo afRight;
    protected DcMotor lsLeft;
    protected DcMotor lsRight;
    protected Servo armturn;
    protected int ArmUpPos = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        afLeft =  hardwareMap.servo.get("afLeft");
        afRight =hardwareMap.servo.get("afRight");
        armturn =  hardwareMap.servo.get("armturn");
        lsLeft = hardwareMap.dcMotor.get("lsLeft");
        lsRight = hardwareMap.dcMotor.get("lsRight");
        while (opModeIsActive()){
            afLeft.setDirection(Servo.Direction.REVERSE);
            lsLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            lsLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            lsRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            lsLeft.setPower(gamepad2.left_stick_y);
            lsRight.setPower(gamepad2.left_stick_y);
            if (gamepad2.right_bumper) {
                afLeft.setPosition(0);
                afLeft.setPosition(2);
                afRight.setPosition(0);
                afRight.setPosition(2);
                sleep(1000);
                ArmUp(1000,1);
                armturn.setPosition(0);
                armturn.setPosition(0.5);
            }
            if(gamepad2.left_bumper){
                afLeft.setPosition(-0.5);
                afRight.setPosition(-0.5);

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

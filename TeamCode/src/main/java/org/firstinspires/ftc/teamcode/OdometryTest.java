package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.Encoder;
@Autonomous
public class OdometryTest extends LinearOpMode {
    //private DcMotor lsLeft;
    //private DcMotor lsRight;
    //private DcMotor lsTurn;
    //private DcMotor lsIntake;
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor backLeft;
    private Servo intakeClaw;
    private Encoder parallel;
    private Encoder perpendicular;
    private Encoder parallel2;

    @Override
    public void runOpMode(){
        //lsLeft = hardwareMap.dcMotor.get("lsLeft");
        //lsRight = hardwareMap.dcMotor.get("lsRight");
        //lsTurn = hardwareMap.dcMotor.get("lsTurn");
        //lsIntake = hardwareMap.dcMotor.get("lsIntake");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        intakeClaw = hardwareMap.servo.get("intakeClaw");
        perpendicular = new Encoder(hardwareMap.get(DcMotorEx.class, "frontRight"));
        parallel = new Encoder(hardwareMap.get(DcMotorEx.class, "backLeft"));
        parallel2 = new Encoder(hardwareMap.get(DcMotorEx.class,"backRight"));
        perpendicular.setDirection(Encoder.Direction.REVERSE);
        parallel2.setDirection(Encoder.Direction.REVERSE);
        //lsLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        //lsTurn.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        while(opModeIsActive()){
            if(parallel.getCurrentPosition() >= 20000 && parallel2.getCurrentPosition() >=20000){
                frontLeft.setPower(0);
                frontRight.setPower(0);
                backLeft.setPower(0);
                backRight.setPower(0);

            } else{
                frontLeft.setPower(1);
                frontRight.setPower(1);
                backLeft.setPower(1);
                backRight.setPower(1);
            }

        }



    }

}

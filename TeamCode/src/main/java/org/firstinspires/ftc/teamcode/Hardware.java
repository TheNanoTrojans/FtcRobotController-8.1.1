package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import java.lang.String;
import java.lang.annotation.Target;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import java.util.ArrayList;



import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import java.util.concurrent.TimeUnit;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.List;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaCurrentGame;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TfodCustomModel;
import com.qualcomm.robotcore.robot.Robot;

public class Hardware extends LinearOpMode {
    public int ArmHorizontalPos;
    // create motors

    @Override
    public void runOpMode() {

    }

    public VuforiaCurrentGame vuforiaFreightFrenzy;
    //CarouselBlue carouselBlue = new CarouselBlue();
    //CarouselRed carouselRed = new CarouselRed();
    //ShippingBlue shippingBlue = new ShippingBlue();
    public Recognition recognition;
    public TfodCustomModel tfodCustomModel;

    public DcMotor frontLeft = null;
    public DcMotor frontRight = null;
    public DcMotor backLeft = null;
    public DcMotor backRight = null;
    public DcMotor lsLeft = null;
    public DcMotor lsRight = null;
    public Servo intakeClaw = null;
    public Servo armturn = null;
    public Servo afLeft = null;
    public Servo afRight = null;
    public int frontLeftPos;
    public int frontRightPos;
    public int backLeftPos;
    public int backRightPos;
    public int ArmUpPos;
    public int CarouselPos;
    public int IntakePos;


    /*    frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ArmUp1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        ArmUp1.setDirection(DcMotorSimple.Direction.REVERSE);
        carousel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftPos = 0;
        frontRightPos = 0;
        backLeftPos = 0;
        backRightPos = 0;
        ArmUpPos = 0;
        CarouselPos = 0;
        IntakePos=0;*/
    HardwareMap hwMap = null;
    public ElapsedTime runtime = new ElapsedTime();

    public Hardware() {
        intialize(hwMap);

    }

    private void intialize(HardwareMap ahwMap) {
        hwMap = ahwMap;
        afLeft = hardwareMap.get(Servo.class, "afLeft");
        afRight = hardwareMap.get(Servo.class, "afRight");
        intakeClaw = hardwareMap.get(Servo.class, "intakeClaw");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        armturn = hardwareMap.get(Servo.class, "armturn");
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //ArmUp1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //carousel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //ArmHorizontal.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
        //ArmUp1.setPower(0);
        //carousel.setPower(0);
        //intake.setPower(0);
        //ArmHorizontal.setPower(0);
        frontLeftPos = 0;
        frontRightPos = 0;
        backLeftPos = 0;
        backRightPos = 0;
        ArmUpPos = 0;
        CarouselPos = 0;
        IntakePos = 0;
    }

    public void drive(int frontLeftTarget, int backLeftTarget, int frontRightTarget, int backRightTarget, double speed) {
        this.frontLeftPos += frontLeftTarget;
        this.backLeftPos += backLeftTarget;
        this.frontRightPos += frontRightTarget;
        this.backRightPos += backRightTarget;

        this.frontLeft.setTargetPosition(this.frontLeftPos);
        this.backLeft.setTargetPosition(this.backLeftPos);
        this.frontRight.setTargetPosition(this.frontRightPos);
        this.backRight.setTargetPosition(this.backRightPos);
        this.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.frontLeft.setPower(speed);
        this.backLeft.setPower(speed);
        this.frontRight.setPower(speed);
        this.backRight.setPower(speed);
        while (opModeIsActive() && this.frontLeft.isBusy() && this.backLeft.isBusy() && this.frontRight.isBusy() && this.backRight.isBusy()) {
            idle();
        }
    }

    public void Inches(int frontLeftTarget, int backLeftTarget, int frontRightTarget, int backRightTarget, double speed) {
        frontLeftTarget = frontLeftTarget * 43;
        backLeftTarget = backLeftTarget * 43;
        frontRightTarget = frontRightTarget * 43;
        backRightTarget = backRightTarget * 43;
        this.frontLeftPos += frontLeftTarget;
        this.backLeftPos += backLeftTarget;
        this.frontRightPos += frontRightTarget;
        this.backRightPos += backRightTarget;

        this.frontLeft.setTargetPosition(this.frontLeftPos);
        this.backLeft.setTargetPosition(this.backLeftPos);
        this.frontRight.setTargetPosition(this.frontRightPos);
        this.backRight.setTargetPosition(this.backRightPos);
        this.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.frontLeft.setPower(speed);
        this.backLeft.setPower(speed);
        this.frontRight.setPower(speed);
        this.backRight.setPower(speed);
        while (opModeIsActive() && this.frontLeft.isBusy() && this.backLeft.isBusy() && this.frontRight.isBusy() && this.backRight.isBusy()) {
            idle();
        }
    }

    public void Strafe(int frontLeftTarget, int backLeftTarget, int frontRightTarget, int backRightTarget, double speed) {
        frontLeftTarget = frontLeftTarget * 49;
        backLeftTarget = backLeftTarget * 49;
        frontRightTarget = frontRightTarget * 49;
        backRightTarget = backRightTarget * 49;
        this.frontLeftPos += frontLeftTarget;
        this.backLeftPos += backLeftTarget;
        this.frontRightPos += frontRightTarget;
        this.backRightPos += backRightTarget;

        this.frontLeft.setTargetPosition(this.frontLeftPos);
        this.backLeft.setTargetPosition(this.backLeftPos);
        this.frontRight.setTargetPosition(this.frontRightPos);
        this.backRight.setTargetPosition(this.backRightPos);
        this.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.frontLeft.setPower(speed);
        this.backLeft.setPower(speed);
        this.frontRight.setPower(speed);
        this.backRight.setPower(speed);
        while (opModeIsActive() && this.frontLeft.isBusy() && this.backLeft.isBusy() && this.frontRight.isBusy() && this.backRight.isBusy()) {
            idle();
        }
    }
}


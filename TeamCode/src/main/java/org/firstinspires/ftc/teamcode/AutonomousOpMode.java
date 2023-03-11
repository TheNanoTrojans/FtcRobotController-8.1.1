package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.util.Encoder;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous
public class AutonomousOpMode extends LinearOpMode {
    private DcMotor lsLeft;
    private DcMotor lsRight;
    private DcMotor lsTurn;
    private DcMotor lsIntake;
    private Servo intakeClaw;
    private SleeveDetection sleeveDetection;
    private OpenCvCamera camera;
    private String color;
    private String webcamName = "Webcam 1";
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor backLeft;
    private Encoder parallel;
    private Encoder perpendicular;
    private Encoder parallel2;
    private double ChassisPos = 0;
    //private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
        sleeveDetection = new SleeveDetection();
        camera.setPipeline(sleeveDetection);
        lsLeft = hardwareMap.dcMotor.get("lsLeft");
        lsRight = hardwareMap.dcMotor.get("lsRight");
        lsTurn = hardwareMap.dcMotor.get("lsTurn");
        lsIntake = hardwareMap.dcMotor.get("lsIntake");
        intakeClaw = hardwareMap.servo.get("intakeClaw");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        lsLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lsRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lsTurn.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lsIntake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lsLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lsRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lsTurn.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lsIntake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lsLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        lsTurn.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        perpendicular = new Encoder(hardwareMap.get(DcMotorEx.class, "frontRight"));
        parallel = new Encoder(hardwareMap.get(DcMotorEx.class, "backLeft"));
        parallel2 = new Encoder(hardwareMap.get(DcMotorEx.class,"backRight"));
        perpendicular.setDirection(Encoder.Direction.REVERSE);
        parallel2.setDirection(Encoder.Direction.REVERSE);
        //lsLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        //lsTurn.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        //intakeClaw.setPosition(1);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(320,240, OpenCvCameraRotation.SIDEWAYS_LEFT);
            }

            @Override
            public void onError(int errorCode) {}
        });
        while(opModeInInit()){
            telemetry.addData("ROTATION: ", sleeveDetection.getPosition());
            if(sleeveDetection.getPosition() == SleeveDetection.SleeveColors.MAGENTA){
                color = "MAGENTA";
            } else if(sleeveDetection.getPosition() == SleeveDetection.SleeveColors.GREEN) {
                color = "GREEN";
            } else if(sleeveDetection.getPosition() == SleeveDetection.SleeveColors.YELLOW){
                color = "YELLOW";
            }
            telemetry.addData("COLOR:", color);
            telemetry.update();
        }


        waitForStart();
        //runtime.reset();
        // Multithreading example
        Thread lsTurnThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Code for controlling lsTurnMotor
                LsIntake(1500,1);
                sleep(750);
                intakeClaw.setPosition(1);
                //Turret(360,1);
            }
        });
        //lsTurnThread.start();

        Thread lsLeftRightThread = new Thread(new Runnable() {
            @Override
            public void run() {
                 //Code for controlling lsLeftMotor and lsRightMotor/
                linearSlide(500,1);
            }
        });
      //  lsLeftRightThread.start();

 //       Thread lsIntakeThread = new Thread(new Runnable() {
   //         @Override
     //       public void run() {
       //         // Code for controlling lsIntakeMotor
         //   }
        //});


        while(opModeIsActive()){
            if(parallel.getCurrentPosition() >= 100 && parallel2.getCurrentPosition() >=100){
                frontLeft.setPower(0);
                frontRight.setPower(0);
                backLeft.setPower(0);
                backRight.setPower(0);
                ChassisPos = 1;

            } else{
                telemetry.addData(" Parallel Right Encoder Current Position",parallel2.getCurrentPosition());
                telemetry.addData("Parallel Left Encoder Current Position",parallel.getCurrentPosition());
                frontLeft.setPower(1);
                frontRight.setPower(1);
                backLeft.setPower(1);
                backRight.setPower(1);
            }

            if(gamepad2.right_bumper){
                Turret(0,1);
                linearSlide(0,1);
                LsIntake(0,1);
            }
            if(ChassisPos==1){
                //linearSlide(600,1);
                ChassisPos =0;
                //Turret(-850,1);
                //lsLeftRightThread.start();
                //lsTurnThread.start();
                //Trajectory traj1 = drive.trajectoryBuilder(new Pose2d(-32,70,Math.toRadians(-90)))
                //      .lineTo(new Vector2d(5,5))
                //    .build();
                //   drive.setPoseEstimate(new Pose2d(-32,70,Math.toRadians(-90)));
                // drive.followTrajectory(traj1);


            }

        }
        // Code for controlling roadrunner
    }
    private void linearSlide(int TargetPosition, double Speed){
        lsLeft.setTargetPosition(TargetPosition);
        lsRight.setTargetPosition(TargetPosition);
        lsLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lsRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lsLeft.setPower(Speed);
        lsRight.setPower(Speed);

    }

    private void Turret(int TargetPosition,double Speed){
        lsTurn.setTargetPosition(TargetPosition);
        lsTurn.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lsTurn.setPower(Speed);
    }
    private void LsIntake(int TargetPos, double Speed){
        lsIntake.setTargetPosition(TargetPos);
        lsIntake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lsIntake.setPower(Speed);
    }
}

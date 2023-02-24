package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous
public class NewAuto2plus1 extends LinearOpMode {
    private DcMotor lsLeft;
    private DcMotor lsRight;
    private DcMotor lsTurn;
    private DcMotor lsIntake;
    private Servo intakeClaw;
    private SleeveDetection sleeveDetection;
    private OpenCvCamera camera;
    private String color;
    private String webcamName = "Webcam 1";
    @Override
    public void runOpMode(){
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
        sleeveDetection = new SleeveDetection();
        camera.setPipeline(sleeveDetection);
           lsLeft = hardwareMap.dcMotor.get("lsLeft");
           lsRight = hardwareMap.dcMotor.get("lsRight");
           lsTurn = hardwareMap.dcMotor.get("lsTurn");
           lsIntake = hardwareMap.dcMotor.get("lsIntake");
           intakeClaw = hardwareMap.servo.get("intakeClaw");
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
           intakeClaw.setPosition(1);
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
            telemetry.update();
            if(sleeveDetection.getPosition() == SleeveDetection.SleeveColors.MAGENTA){
                color = "MAGENTA";
            }
            telemetry.addData("COLOR:", color);
            telemetry.update();
        }
           waitForStart();
           if(opModeIsActive()){
               intakeClaw.setPosition(1);
               Trajectory traj1 = drive.trajectoryBuilder(new Pose2d(-35,70,Math.toRadians(-90)))
                       .lineTo(new Vector2d(-39,67))
                       .build();
               Trajectory myTraj1 = drive.trajectoryBuilder(traj1.end())
                       .lineTo(new Vector2d(-37,17.5))
                       .build();
               Trajectory myTraj7 = drive.trajectoryBuilder(new Pose2d(-37,17.5,Math.toRadians(180)))
                       .lineTo(new Vector2d(-37,20))
                       .build();
               Trajectory myTraj2 = drive.trajectoryBuilder(myTraj7.end())
                       .lineTo(new Vector2d(-63,19))
                       .build();


               Trajectory myTraj3 = drive.trajectoryBuilder(myTraj2.end())
                       .lineTo(new Vector2d(-19.5,19))
                       .build();
               Trajectory myTraj8 = drive.trajectoryBuilder(myTraj3.end())
                       .strafeTo(new Vector2d(-63,19))
                       .build();
               Trajectory myTraj6 = drive.trajectoryBuilder(new Pose2d(-26,21.5, Math.toRadians(-90)))
                       .lineTo(new Vector2d(-33,24))
                       .build();
               Trajectory myTraj4 = drive.trajectoryBuilder(myTraj6.end())
                       .lineToLinearHeading(new Pose2d(-40,22,Math.toRadians(180)))
                       .build();
               Trajectory myTraj5  = drive.trajectoryBuilder(myTraj8.end())
                       .lineTo(new Vector2d(-16,19))
                       .build();
               Trajectory parkMagenta = drive.trajectoryBuilder(myTraj8.end())
                       .lineTo(new Vector2d(-36,19))
                       .build();


               drive.setPoseEstimate(new Pose2d(-35,70,Math.toRadians(-90)));
               drive.followTrajectory(traj1);
               drive.followTrajectory(myTraj1);
               linearSlide(2600,1);
               sleep(1350);
               Turret(560,1);
               //sleep(1000);

               LsIntake(350,1);
               sleep(1000);
               linearSlide(2400,1);
               intakeClaw.setPosition(0.4);
               sleep(200);
               Turret(0,1);
               LsIntake(0,1);
               lsLeft.setPower(0);
               lsRight.setPower(0);
               //linearSlide(5800,1);
               sleep(400);
               //linearSlide(5400,1);
               intakeClaw.setPosition(0.5);
               sleep(200);
               Turret(0,1);
               LsIntake(0,1);
               sleep(200);
               linearSlide(350,1);
               drive.turn(Math.toRadians(-90));
               drive.followTrajectory(myTraj7);
               drive.followTrajectory(myTraj2);

               intakeClaw.setPosition(1);
               sleep(350);
               linearSlide(1200,1);
               sleep(500);

               //drive.followTrajectory(myTraj8);
               drive.followTrajectory(myTraj3);
               //drive.turn(Math.toRadians(90));
               linearSlide(2800,1);
               sleep(1150);
               Turret(610,1);
               //sleep(1000);

               LsIntake(290,1);
               sleep(1000);
               linearSlide(2600,1);
               intakeClaw.setPosition(0.4);
               sleep(200);
               Turret(0,1);
               LsIntake(0,1);
               lsLeft.setPower(0);
               lsRight.setPower(0);
               //linearSlide(5800,1);
               sleep(400);
               //linearSlide(5400,1);
               intakeClaw.setPosition(0.5);
               sleep(200);
               Turret(0,1);
               LsIntake(0,1);
               sleep(200);
               linearSlide(220,1);
               drive.followTrajectory(myTraj8);
               intakeClaw.setPosition(1);
               sleep(350);
               linearSlide(1200,1);
               if(sleeveDetection.getPosition() == SleeveDetection.SleeveColors.YELLOW){
                   //drive.followTrajectory(myTraj8);
                   drive.followTrajectory(myTraj5);
                   //drive.turn(Math.toRadians(90));
                   linearSlide(2800,1);
                   sleep(1150);
                   Turret(550,1);
                   //sleep(1000);

                   LsIntake(530,1);
                   sleep(1000);
                   linearSlide(2600,1);
                   intakeClaw.setPosition(0.4);
                   sleep(200);
                   Turret(0,1);
                   LsIntake(0,1);
                   lsLeft.setPower(0);
                   lsRight.setPower(0);
                   //linearSlide(5800,1);
                   sleep(400);
                   //linearSlide(5400,1);
                   intakeClaw.setPosition(0.5);
                   sleep(200);
                   Turret(0,1);
                   LsIntake(0,1);
                   sleep(200);
                   linearSlide(280,1);
               } else if(color == "MAGENTA"){
                   drive.followTrajectory(parkMagenta);
               } else if(sleeveDetection.getPosition()== SleeveDetection.SleeveColors.GREEN){
                   stop();
               }
               sleep(500);


/*
               drive.followTrajectory(myTraj6);

               //linearSlide(0,1);
               LsIntake(0,1);
               //Turret(0,1);
               drive.followTrajectory(myTraj4);
               linearSlide(280,1);
               drive.followTrajectory(myTraj5);
               intakeClaw.setPosition(1);
               sleep(250);
               linearSlide(6200,1);
               //drive.followTrajectory(myTraj8);
               drive.followTrajectory(myTraj3);
               drive.turn(-90);
               LsIntake(50,1);
               sleep(100);
               lsLeft.setPower(0);
               lsRight.setPower(0);

               //linearSlide(5800,1);
               //sleep(200);
               //linearSlide(5400,1);
               intakeClaw.setPosition(0.4);
               sleep(250);

               drive.followTrajectory(myTraj6);*/




           }


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

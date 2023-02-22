package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
@Autonomous
public class NewAuto2plus1 extends LinearOpMode {
    private DcMotor lsLeft;
    private DcMotor lsRight;
    private DcMotor lsTurn;
    private DcMotor lsIntake;
    private Servo intakeClaw;

    @Override
    public void runOpMode(){
           lsLeft = hardwareMap.dcMotor.get("lsLeft");
           lsRight = hardwareMap.dcMotor.get("lsRight");
           lsTurn = hardwareMap.dcMotor.get("lsTurn");
           lsIntake = hardwareMap.dcMotor.get("lsIntake");
           intakeClaw = hardwareMap.servo.get("intakeClaw");
           lsLeft.setDirection(DcMotorSimple.Direction.REVERSE);
           lsTurn.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
           SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
           waitForStart();
           if(opModeIsActive()){
               intakeClaw.setPosition(1);
               Trajectory traj1 = drive.trajectoryBuilder(new Pose2d(-35,70,Math.toRadians(-90)))
                       .lineTo(new Vector2d(-39,67))
                       .build();
               Trajectory myTraj1 = drive.trajectoryBuilder(traj1.end())
                       .lineTo(new Vector2d(-37,17.5))
                       .build();
               Trajectory myTraj7 = drive.trajectoryBuilder(myTraj1.end())
                       .lineTo(new Vector2d(-37,20))
                       .build();
               Trajectory myTraj2 = drive.trajectoryBuilder(myTraj7.end())
                       .lineToLinearHeading(new Pose2d(-61,18,Math.toRadians(180)))
                       .build();

              // Trajectory myTraj8 = drive.trajectoryBuilder(myTraj1.end())
                //       .splineTo(new Vector2d(-50,21.5), Math.toRadians(-25))
                  //     .build();
               Trajectory myTraj3 = drive.trajectoryBuilder(myTraj1.end())
                       .splineToLinearHeading(new Pose2d(-26,21.5,Math.toRadians(-90)),Math.toRadians(-25))
                       .build();
               Trajectory myTraj6 = drive.trajectoryBuilder(myTraj3.end())
                       .lineTo(new Vector2d(-33,24))
                       .build();
               Trajectory myTraj4 = drive.trajectoryBuilder(myTraj6.end())
                       .lineToLinearHeading(new Pose2d(-40,22,Math.toRadians(180)))
                       .build();
               Trajectory myTraj5  = drive.trajectoryBuilder(myTraj4.end())
                       .lineTo(new Vector2d(-61,18))
                       .build();
               drive.setPoseEstimate(new Pose2d(-35,70,Math.toRadians(-90)));
               drive.followTrajectory(traj1);
               drive.followTrajectory(myTraj1);
               linearSlide(6200,1);
               Turret(560,1);
               sleep(1000);

               LsIntake(110,1);
               sleep(300);
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
               linearSlide(50,1);
               drive.followTrajectory(myTraj7);
               drive.followTrajectory(myTraj2);
               intakeClaw.setPosition(1);
               sleep(250);
               linearSlide(4200,1);
               //drive.followTrajectory(myTraj8);
               drive.followTrajectory(myTraj3);
               LsIntake(50,1);
               sleep(100);
               lsLeft.setPower(0);
               lsRight.setPower(0);

               //linearSlide(5800,1);
               //sleep(200);
               //linearSlide(5400,1);
               intakeClaw.setPosition(0.4);
               sleep(250);

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
               LsIntake(50,1);
               sleep(100);
               lsLeft.setPower(0);
               lsRight.setPower(0);

               //linearSlide(5800,1);
               //sleep(200);
               //linearSlide(5400,1);
               intakeClaw.setPosition(0.4);
               sleep(250);

               drive.followTrajectory(myTraj6);




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

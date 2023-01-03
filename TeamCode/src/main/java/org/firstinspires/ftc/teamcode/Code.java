package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
@Autonomous(name = "Code", group = "Autonomous")
public class Code extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Trajectory myTrajectory = drive.trajectoryBuilder(new Pose2d(35,65))
                .splineTo(new Vector2d(0,55), Math.toRadians(0))
                //.splineTo(new Vector2d(0,0), Math.toRadians(0))
                        .build();

        waitForStart();
        if(isStopRequested()) return;

        drive.followTrajectory(myTrajectory);
        //drive.turn(Math.toRadians(90));
    }
}

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

        Trajectory myTrajectory = drive.trajectoryBuilder(new Pose2d(35,70,Math.toRadians(90)))
                .strafeTo(new Vector2d(0,55))

                .build();
                //.lineToLinearHeading(new Pose2d(0,55,Math.toRadians(90)))

        waitForStart();
        if(isStopRequested()) return;
        drive.setPoseEstimate(new Pose2d(35,70, Math.toRadians(90)));
        //drive.turn(Math.toRadians(90));
        drive.followTrajectory(myTrajectory);

        //drive.turn(Math.toRadians(90));
        //drive.followTrajectory(traj1);
    }
}

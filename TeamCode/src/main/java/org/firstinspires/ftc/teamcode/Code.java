package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
@Autonomous(name = "Test", group = "Autonomous")
public class Code extends LinearOpMode {
    protected CRServo afLeft;

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        afLeft =  hardwareMap.crservo.get("afLeft");
        while (opModeIsActive()){
            afLeft.setPower(gamepad2.right_stick_y);
        }

        //drive.turn(Math.toRadians(90));
        //drive.followTrajectory(traj1);
    }
}

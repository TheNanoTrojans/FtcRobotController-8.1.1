
package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.PoseStorage;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.StandardTrackingWheelLocalizer;



@TeleOp(group = "advanced")
public class TeleOpJustLocalizer extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize your own robot class
        Robot robot = new Robot(hardwareMap);

        // This is assuming you are using StandardTrackingWheelLocalizer.java
        // Switch this class to something else (Like TwoWheeTrackingLocalizer.java) if your
        // configuration differs
        SampleMecanumDrive myLocalizer = new SampleMecanumDrive(hardwareMap);

        // Retrieve our pose from the PoseStorage.currentPose static field
        // See AutoTransferPose.java for further details

        myLocalizer.setPoseEstimate(PoseStorage.currentPose);

        waitForStart();

 ///       if (isStopRequested()) return;

        while (opModeIsActive() && !isStopRequested()) {
            // Make sure to call myLocalizer.update() on *every* loop
            // Increasing loop time by utilizing bulk reads and minimizing writes will increase your
            // odometry accuracy
            myLocalizer.update();

            // Retrieve your pose
            Pose2d myPose = myLocalizer.getPoseEstimate();
            Trajectory myTrajectory = myLocalizer.trajectoryBuilder(myPose)
                    .lineToLinearHeading(new Pose2d(8,65, Math.toRadians(75)))
                    .build();
            // Print your pose to telemetry
            telemetry.addData("x", myPose.getX());
            telemetry.addData("y", myPose.getY());
            telemetry.addData("heading", myPose.getHeading());
            telemetry.update();

            // Teleop driving part
            // Mecanum example code from gm0
            // https://gm0.org/en/stable/docs/software/mecanum-drive.html
            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double rx = gamepad1.right_stick_x;

            // Set drive power
            robot.setDrivePower(x, y, rx);
            if(gamepad1.y){
                myLocalizer.followTrajectory(myTrajectory);
            }
        }
    }

    // Simple custom robot class
    // Holds the hardware for a basic mecanum drive
    class Robot {
        private DcMotorEx leftFront, leftRear, rightRear, rightFront;

        public Robot(HardwareMap hardwareMap) {
            // Initialize motors
            leftFront = hardwareMap.get(DcMotorEx.class, "frontLeft");
            leftRear = hardwareMap.get(DcMotorEx.class, "backLeft");
            rightRear = hardwareMap.get(DcMotorEx.class, "backRight");
            rightFront = hardwareMap.get(DcMotorEx.class, "frontRight");

            // Reverse right side motor directions
            // This may need to be flipped to the left side depending on your motor rotation direction
            leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
            //rightRear.setDirection(DcMotorSimple.Direction.REVERSE);

            // Turn on bulk reads to help optimize loop times
            for (LynxModule module : hardwareMap.getAll(LynxModule.class)) {
                module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
            }
        }

        // Mecanum example code from gm0
        // https://gm0.org/en/stable/docs/software/mecanum-drive.html
        public void setDrivePower(double x, double y, double rx) {
            double powerFrontLeft = y + x + rx;
            double powerFrontRight = y - x - rx;
            double powerBackLeft = y - x + rx;
            double powerBackRight = y + x - rx;

            if (Math.abs(powerFrontLeft) > 1 || Math.abs(powerBackLeft) > 1 ||
                    Math.abs(powerFrontRight) > 1 || Math.abs(powerBackRight) > 1) {
                // Find the largest power
                double max;
                max = Math.max(Math.abs(powerFrontLeft), Math.abs(powerBackLeft));
                max = Math.max(Math.abs(powerFrontRight), max);
                max = Math.max(Math.abs(powerBackRight), max);

                // Divide everything by max (it's positive so we don't need to worry
                // about signs)
                powerFrontLeft /= max;
                powerBackLeft /= max;
                powerFrontRight /= max;
                powerBackRight /= max;
            }

            leftFront.setPower(powerFrontLeft);
            rightFront.setPower(powerFrontRight);
            leftRear.setPower(powerBackLeft);
            rightRear.setPower(powerBackRight);
        }
    }//*
}

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.SleeveDetection;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "AutoRightBlue")
public class AutoRightBlue extends LinearOpMode {

    private SleeveDetection sleeveDetection;
    private OpenCvCamera camera;
    private String color;

    // Name of the Webcam to be set in the config
    private String webcamName = "Webcam 1";

    @Override
    public void runOpMode() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
        sleeveDetection = new SleeveDetection();
        camera.setPipeline(sleeveDetection);

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
        }
        if(opModeIsActive()){

            SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

            Trajectory myTrajectory = drive.trajectoryBuilder(new Pose2d(-35,70,Math.toRadians(180)))
                    .strafeTo(new Vector2d(-35,67))


                    .build();
            Trajectory myTrajectory1 = drive.trajectoryBuilder(myTrajectory.end())
                    .lineToLinearHeading(new Pose2d(8, 65, Math.toRadians(75)))
                    .build();
            Trajectory traj1 = drive.trajectoryBuilder(myTrajectory1.end())
                    .lineToLinearHeading(new Pose2d(-12,65,90))
                    .build();
            Trajectory traj4 = drive.trajectoryBuilder(traj1.end())
                            .strafeTo(new Vector2d(-12,45))
                            .build();
            drive.setPoseEstimate(new Pose2d(-35,70, Math.toRadians(180)));
            drive.followTrajectory(myTrajectory);
            drive.followTrajectory(myTrajectory1);
            afLeft.setPower(-1);
            afRight.setPower(-1);
            sleep(1350);
            afLeft.setPower(0);
            afRight.setPower(0);
            //ArmUp(50000,1);
            armturn.setPower(0.5);
            sleep(400);
            armturn.setPower(0);
            lsLeft.setPower(1);
            lsRight.setPower(1);
            sleep(2450);
            lsLeft.setPower(0);
            lsRight.setPower(0);

            //intakeClaw.setPosition(0);
            //  ArmUp(40000,1);
            //armturn.setPosition(0);
            //armturn.setPosition(0.5);
            lsLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lsRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            if(sleeveDetection.getPosition() == SleeveDetection.SleeveColors.GREEN){


                //.lineToLinearHeading(new Pose2d(0,55,Math.toRadians(90)))

                waitForStart();
                if(isStopRequested()) return;

                //drive.turn(Math.toRadians(90));
                //drive.followTrajectory(myTrajectory);
                drive.followTrajectory(traj1);
                drive.followTrajectory(traj4);
            }
            if (sleeveDetection.getPosition() == SleeveDetection.SleeveColors.MAGENTA){
                Trajectory traj2 = drive.trajectoryBuilder(myTrajectory1.end())
                        .strafeTo(new Vector2d(-36,65))
                        .strafeTo(new Vector2d(-36,45))

                        .build();
                waitForStart();
                if(isStopRequested()) return;

               // drive.followTrajectory(traj1);
                drive.followTrajectory(traj2);
            }
            if (sleeveDetection.getPosition() == SleeveDetection.SleeveColors.YELLOW){


                Trajectory traj3 = drive.trajectoryBuilder(myTrajectory1.end())
                        .strafeTo(new Vector2d(-60,65))
                        .build();
                Trajectory traj5 = drive.trajectoryBuilder(traj3.end())
                        .strafeTo(new Vector2d(-60,45))
                                .build();

                //drive.setPoseEstimate(new Pose2d(35,70, Math.toRadians(90)));
                //drive.followTrajectory(myTrajectory);
               // drive.followTrajectory(traj1);
                drive.followTrajectory(traj3);
                drive.followTrajectory(traj5);
            }
        }



        waitForStart();
    }
}

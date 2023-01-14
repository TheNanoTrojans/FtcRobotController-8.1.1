package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.SleeveDetection;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "AutoLeftBlue")
public class AutoLeftBlue extends LinearOpMode {

    private SleeveDetection sleeveDetection;
    private OpenCvCamera camera;
    private String color;
    PoseStorage PoseStorage1 =  new PoseStorage();
    
    // Name of the Webcam to be set in the config
    private String webcamName = "Webcam 1";
    //SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

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
            drive.update();
            Trajectory myTrajectory = drive.trajectoryBuilder(new Pose2d(35,70,Math.toRadians(180)))
                    .strafeTo(new Vector2d(8,65))

                    .build();
            Trajectory traj1 = drive.trajectoryBuilder(myTrajectory.end())
                    .strafeTo(new Vector2d(12,40))
                    .build();
            drive.setPoseEstimate(new Pose2d(35,70, Math.toRadians(180)));

            if(sleeveDetection.getPosition() == SleeveDetection.SleeveColors.GREEN){


                //.lineToLinearHeading(new Pose2d(0,55,Math.toRadians(90)))

                waitForStart();
                if(isStopRequested()) return;

                //drive.turn(Math.toRadians(90));
                drive.followTrajectory(myTrajectory);
                drive.turn(Math.toRadians(-105));
                drive.followTrajectory(traj1);
            }
            if (sleeveDetection.getPosition() == SleeveDetection.SleeveColors.MAGENTA){
                Trajectory traj2 = drive.trajectoryBuilder(traj1.end())
                        .strafeTo(new Vector2d(36,40))
                        .build();
                waitForStart();
                if(isStopRequested()) return;
                drive.followTrajectory(myTrajectory);
                drive.turn(Math.toRadians(-105));
                drive.followTrajectory(traj1);
                drive.followTrajectory(traj2);
            }
            if (sleeveDetection.getPosition() == SleeveDetection.SleeveColors.YELLOW){


                Trajectory traj3 = drive.trajectoryBuilder(traj1.end())
                        .strafeTo(new Vector2d(60,40))
                        .build();


                //drive.setPoseEstimate(new Pose2d(35,70, Math.toRadians(90)));
                drive.followTrajectory(myTrajectory);
                drive.turn(Math.toRadians(-105));
                drive.followTrajectory(traj1);
                drive.followTrajectory(traj3);
            }
            PoseStorage.currentPose = drive.getPoseEstimate();
        }



        waitForStart();
    }
}

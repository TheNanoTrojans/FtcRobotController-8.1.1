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

@Autonomous(name = "Signal Sleeve Test")
public class VisionTest extends LinearOpMode {

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

        if(opModeIsActive()){

            SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

            Trajectory myTrajectory = drive.trajectoryBuilder(new Pose2d(35,70,Math.toRadians(90)))
                    .strafeTo(new Vector2d(0,55))
                    .build();


            drive.setPoseEstimate(new Pose2d(35,70, Math.toRadians(90)));
            drive.followTrajectory(myTrajectory);
            if(sleeveDetection.getPosition() == SleeveDetection.SleeveColors.GREEN){

                Trajectory traj1 = drive.trajectoryBuilder(myTrajectory.end())
                        .strafeTo(new Vector2d(12,45))
                        .build();
                //.lineToLinearHeading(new Pose2d(0,55,Math.toRadians(90)))



                //drive.turn(Math.toRadians(90));
                drive.followTrajectory(traj1);
            }
            if (sleeveDetection.getPosition() == SleeveDetection.SleeveColors.MAGENTA){

                Trajectory traj2 = drive.trajectoryBuilder(myTrajectory.end())
                        .strafeTo(new Vector2d(36,54))
                        .build();
                drive.followTrajectory(traj2);
            }
            if(isStopRequested()) return;

        }



        waitForStart();
    }
}

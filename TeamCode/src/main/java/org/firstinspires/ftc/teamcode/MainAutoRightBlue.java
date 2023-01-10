package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Autonomous(name = "MainAutoRightBlue")
public class MainAutoRightBlue extends LinearOpMode {
    private SleeveDetection sleeveDetection;
    private OpenCvCamera camera;
    private String color;
    protected CRServo afLeft;
    protected CRServo afRight;
    protected DcMotor lsLeft;
    protected DcMotor lsRight;
    protected CRServo armturn;
    protected Servo intakeClaw;
    protected int ArmUpPos = 0;
    protected float power = 0;


    // Name of the Webcam to be set in the config
    private String webcamName = "Webcam 1";

    @Override
    public void runOpMode() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
        sleeveDetection = new SleeveDetection();
        camera.setPipeline(sleeveDetection);
        intakeClaw = hardwareMap.servo.get("intakeClaw");
        afLeft =  hardwareMap.crservo.get("afLeft");
        afRight = hardwareMap.crservo.get("afRight");
        armturn =  hardwareMap.crservo.get("armturn");
        lsLeft = hardwareMap.dcMotor.get("lsLeft");
        lsRight = hardwareMap.dcMotor.get("lsRight");

        // This is assuming you're using StandardTrackingWheelLoc
        //frontLeft.setDirection(DcMotor.Direction.REVERSE);
        //backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        //backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        afLeft.setDirection(CRServo.Direction.REVERSE);
        lsLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        lsLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        lsRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lsLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lsRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
            intakeClaw.setPosition(0.8);
            SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);


            Trajectory myTrajectory = drive.trajectoryBuilder(new Pose2d(-35,70,Math.toRadians(180)))
                    .strafeTo(new Vector2d(-35,10))


                    .build();
            Trajectory myTrajectory1 = drive.trajectoryBuilder(myTrajectory.end())
                    .strafeTo(new Vector2d(-65, 12))
                    .build();
            Trajectory traj1 = drive.trajectoryBuilder(myTrajectory1.end())
                    .strafeTo(new Vector2d(-12,35))
                    .build();

            drive.setPoseEstimate(new Pose2d(-35,70, Math.toRadians(180)));
            drive.followTrajectory(myTrajectory);
            drive.followTrajectory(myTrajectory1);
            afLeft.setPower(-1);
            afRight.setPower(-1);
            sleep(1700);
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
            intakeClaw.setPosition(0.5);

            sleep(2000);
            //intakeClaw.setPosition(0);
            //  ArmUp(40000,1);
            //armturn.setPosition(0);
            //armturn.setPosition(0.5);
            lsLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lsRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lsLeft.setPower(-1);
            lsRight.setPower(-1);
            sleep(2450);
            lsLeft.setPower(0);
            lsRight.setPower(0);
            intakeClaw.setPosition(0.8);
            armturn.setPower(-0.5);
            sleep(400);
            armturn.setPower(0);
            afLeft.setPower(1);
            afRight.setPower(1);
            sleep(1700);
            afLeft.setPower(0);
            afRight.setPower(0);
            if(sleeveDetection.getPosition() == SleeveDetection.SleeveColors.GREEN){


                //.lineToLinearHeading(new Pose2d(0,55,Math.toRadians(90)))

                waitForStart();
                if(isStopRequested()) return;

                //drive.turn(Math.toRadians(90));
                //drive.followTrajectory(myTrajectory);
                drive.followTrajectory(traj1);;
            }
            if (sleeveDetection.getPosition() == SleeveDetection.SleeveColors.MAGENTA){
                Trajectory traj2 = drive.trajectoryBuilder(myTrajectory1.end())
                        .splineTo(new Vector2d(-45,35), Math.toRadians(180))

                        .build();
                waitForStart();
                if(isStopRequested()) return;

                // drive.followTrajectory(traj1);
                drive.followTrajectory(traj2);
            }
            if (sleeveDetection.getPosition() == SleeveDetection.SleeveColors.YELLOW){


                Trajectory traj3 = drive.trajectoryBuilder(myTrajectory1.end())
                        .strafeTo(new Vector2d(-65,35))
                        .build();
                Trajectory traj5 = drive.trajectoryBuilder(traj3.end())
                        .strafeTo(new Vector2d(-60,45))
                        .build();

                //drive.setPoseEstimate(new Pose2d(35,70, Math.toRadians(90)));
                //drive.followTrajectory(myTrajectory);
                // drive.followTrajectory(traj1);
                drive.followTrajectory(traj3);
                //drive.followTrajectory(traj5);
            }
            PoseStorage.currentPose = drive.getPoseEstimate();
        }



        waitForStart();
    }
}



package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

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
    protected CRServo afLeft;
    protected CRServo afRight;
    protected DcMotor lsLeft;
    protected DcMotor lsRight;
    protected CRServo armturn;
    protected Servo intakeClaw;
    protected int ArmUpPos = 0;
    protected float power = 0;
    //SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);


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
        if(opModeInInit()){
            intakeClaw.setPosition(0.2);
        }
        if(isStopRequested()){
            intakeClaw.setPosition(1);
        }
        while(opModeInInit()){
            telemetry.addData("ROTATION: ", sleeveDetection.getPosition());
            telemetry.update();
        }
        if(opModeIsActive()){

            SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

            Trajectory myTrajectory = drive.trajectoryBuilder(new Pose2d(-37,70,Math.toRadians(180)))
                    .strafeTo(new Vector2d(-37,67))


                    .build();
            Trajectory myTrajectory2 = drive.trajectoryBuilder(myTrajectory.end())
                    .strafeTo(new Vector2d(-10,67))
                    .build();
            Trajectory myTrajectory1 = drive.trajectoryBuilder(myTrajectory2.end())
                    .splineToSplineHeading(new Pose2d(11.5 , 62, Math.toRadians(70)),0)
                    .build();
            Trajectory myTrajectory3 = drive.trajectoryBuilder(myTrajectory1.end())
                    .lineTo(new Vector2d(12,66))
                    .build();
            Trajectory myTrajectory4 = drive.trajectoryBuilder(myTrajectory3.end())
                    .lineToLinearHeading(new Pose2d(11.5 , 66, Math.toRadians(80)))
                    .build();
            Trajectory traj1 = drive.trajectoryBuilder(myTrajectory1.end())
                    .lineToLinearHeading(new Pose2d(-12,70,Math.toRadians(90)))
                    .build();

            Trajectory traj4 = drive.trajectoryBuilder(traj1.end())
                            .strafeTo(new Vector2d(-11.5,40 ))
                            .build();

            Trajectory traj2 = drive.trajectoryBuilder(traj4.end())
                    .strafeTo(new Vector2d(-36,43))
                    //.strafeTo(new Vector2d(-36,45))

                    .build();
            Trajectory traj3 = drive.trajectoryBuilder(myTrajectory1.end())
                    .lineToLinearHeading(new Pose2d(-59,64,Math.toRadians(90)))
                    .build();
            Trajectory traj5 = drive.trajectoryBuilder(traj3.end())
                    .strafeTo(new Vector2d(-59,45))
                    .build();
            Trajectory alignment = drive.trajectoryBuilder(new Pose2d(-37,70,Math.toRadians(180)))
                            .lineTo(new Vector2d(35.5,70))
                            .build();
            drive.setPoseEstimate(new Pose2d(-35,70, Math.toRadians(180)));
            intakeClaw.setPosition(1);

            //drive.turn(Math.toRadians(180) + 1e-6);
           /* afLeft.setPower(-1);
            afRight.setPower(-1);
            sleep(2000);
            afLeft.setPower(0);
            afRight.setPower(0);
            //ArmUp(50000,1);
            armturn.setPower(0.5);
            sleep(800);
            armturn.setPower(0);
            lsLeft.setPower(1);
            lsRight.setPower(1);
            sleep(2250);
            lsLeft.setPower(0);
            lsRight.setPower(0);
            sleep(100);
            intakeClaw.setPosition(0.2);

            sleep(500);
            //intakeClaw.setPosition(0);
            //  ArmUp(40000,1);
            //armturn.setPosition(0);
            //armturn.setPosition(0.5);
            lsLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lsRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lsLeft.setPower(-1);
            lsRight.setPower(-1);
            //afLeft.setPower(1);
            //afRight.setPower(1);
            //sleep(1000);
            //afLeft.setPower(0);
            //afRight.setPower(0);
            sleep(2250);
            lsLeft.setPower(0);
            lsRight.setPower(0);
            intakeClaw.setPosition(1);
            armturn.setPower(-0.5);
            sleep(550);
            armturn.setPower(0);
            afLeft.setPower(1);
            afRight.setPower(1);
            sleep(2000);
            afLeft.setPower(0);
            afRight.setPower(0);*/
            if(sleeveDetection.getPosition() == SleeveDetection.SleeveColors.YELLOW){


                //.lineToLinearHeading(new Pose2d(0,55,Math.toRadians(90)))

                waitForStart();
                if(isStopRequested()) return;
                //drive.followTrajectory(alignment);
               // stop();
                //drive.turn(Math.toRadians(90));
                //drive.followTrajectory(myTrajectory);
                drive.followTrajectory(myTrajectory);
                drive.followTrajectory(myTrajectory2);
                drive.followTrajectory(myTrajectory1);
                runaf();
                //drive.turn(Math.toRadians(-108));
                drive.followTrajectory(myTrajectory3);
                runArm();
                drive.followTrajectory(myTrajectory1);

                drive.followTrajectory(myTrajectory4);
                //stop();
                drive.followTrajectory(traj1);
                drive.followTrajectory(traj4);
                stop();
            } else if (sleeveDetection.getPosition() == SleeveDetection.SleeveColors.MAGENTA){

                waitForStart();
                if(isStopRequested()) return;
                drive.followTrajectory(myTrajectory);
                drive.followTrajectory(myTrajectory2);
                drive.followTrajectory(myTrajectory1);
                //drive.turn(Math.toRadians(-108));
                runaf();
                runArm();
                drive.followTrajectory(traj1);
                drive.followTrajectory(traj4);
                drive.followTrajectory(traj2);
            } else if (sleeveDetection.getPosition() == SleeveDetection.SleeveColors.GREEN){




                //drive.setPoseEstimate(new Pose2d(35,70, Math.toRadians(90)));
                //drive.followTrajectory(myTrajectory);
               // drive.followTrajectory(traj1);
               // drive.followTrajectory(traj3);
               // drive.followTrajectory(traj5);
                drive.followTrajectory(myTrajectory);
                //drive.turn(Math.toRadians(-107));
                drive.followTrajectory(myTrajectory2);

                drive.followTrajectory(myTrajectory1);

                runArm();

                drive.followTrajectory(traj3);
                drive.followTrajectory(traj5);
            }
            PoseStorage.currentPose = drive.getPoseEstimate();
        }



        waitForStart();
    }
    public void runaf(){
        afLeft.setPower(-1);
        afRight.setPower(-1);
        sleep(2000);
        afLeft.setPower(0);
        afRight.setPower(0);
        //ArmUp(50000,1);

    }
    private void runArm(){
        armturn.setPower(0.5);
        sleep(800);
        armturn.setPower(0);
        lsLeft.setPower(1);
        lsRight.setPower(1);
        sleep(2400);
        lsLeft.setPower(0);
        lsRight.setPower(0);
        sleep(100);

        intakeClaw.setPosition(0.2);

        sleep(500);
        //intakeClaw.setPosition(0);
        //  ArmUp(40000,1);
        //armturn.setPosition(0);
        //armturn.setPosition(0.5);
        lsLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lsRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lsLeft.setPower(-1);
        lsRight.setPower(-1);
        //afLeft.setPower(1);
        //afRight.setPower(1);
        //sleep(1000);
        //afLeft.setPower(0);
        //afRight.setPower(0);
        sleep(2500);
        lsLeft.setPower(0);
        lsRight.setPower(0);
        intakeClaw.setPosition(1);
        armturn.setPower(-0.5);
        sleep(700);
        armturn.setPower(0);
        afLeft.setPower(1);
        afRight.setPower(1);
        sleep(2000);
        afLeft.setPower(0);
        afRight.setPower(0);
    }

}

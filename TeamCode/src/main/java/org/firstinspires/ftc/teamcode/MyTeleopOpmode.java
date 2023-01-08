package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.localization.TwoTrackingWheelLocalizer;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.StandardTrackingWheelLocalizer;
import org.firstinspires.ftc.teamcode.drive.TwoWheelTrackingLocalizer;
@TeleOp(name = "My TeleOp OpMode")
public class MyTeleopOpmode extends LinearOpMode {
    protected CRServo afLeft;
    protected CRServo afRight;
    protected DcMotor lsLeft;
    protected DcMotor lsRight;
    protected CRServo armturn;
    protected Servo intakeClaw;
    protected int ArmUpPos = 0;
    protected float power = 0;
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;

    public void runOpMode() {
        // Insert whatever initialization your own code does
        intakeClaw = hardwareMap.servo.get("intakeClaw");
        afLeft =  hardwareMap.crservo.get("afLeft");
        afRight = hardwareMap.crservo.get("afRight");
        armturn =  hardwareMap.crservo.get("armturn");
        lsLeft = hardwareMap.dcMotor.get("lsLeft");
        lsRight = hardwareMap.dcMotor.get("lsRight");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        // This is assuming you're using StandardTrackingWheelLocalizer.java
        // Switch this class to something else (Like TwoWheeTrackingLocalizer.java) if your configuration is different
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        TwoTrackingWheelLocalizer myLocalizer = new TwoWheelTrackingLocalizer(hardwareMap, drive);

        // Set your initial pose to x: 10, y: 10, facing 90 degrees
        myLocalizer.setPoseEstimate(new Pose2d(10, 10, Math.toRadians(90)));
        Trajectory myTrajectory = drive.trajectoryBuilder(myLocalizer.getPoseEstimate())
                .strafeTo(new Vector2d(8,65))
                .build();


        waitForStart();

        while(opModeIsActive()) {
            // Make sure to call myLocalizer.update() on *every* loop
            // Increasing loop time by utilizing bulk reads and minimizing writes will increase your odometry accuracy
            myLocalizer.update();
            frontLeft.setDirection(DcMotor.Direction.REVERSE);
            //backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            //backRight.setDirection(DcMotorSimple.Direction.REVERSE);
            afLeft.setDirection(CRServo.Direction.REVERSE);
            lsLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            lsLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
            lsRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lsLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            lsRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            telemetry.addData("Speed and Power:", gamepad2.left_stick_y);
            lsLeft.setPower(-gamepad2.left_stick_y);
            lsRight.setPower(-gamepad2.left_stick_y);
            afLeft.setPower(-gamepad2.right_stick_y);
            afRight.setPower(-gamepad2.right_stick_y);
            armturn.setPower(-gamepad2.right_stick_x);
            if (gamepad2.right_bumper) {
                afLeft.setPower(-1);
                afRight.setPower(-1);
                sleep(750);
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
            }
            if(gamepad2.left_bumper){

                //ArmUp(50000,1);

                lsLeft.setPower(-1);
                lsRight.setPower(-1);
                sleep(2450);
                lsLeft.setPower(0);
                lsRight.setPower(0);
                intakeClaw.setPosition(1);
                armturn.setPower(-0.5);
                sleep(400);
                armturn.setPower(0);
                afLeft.setPower(1);
                afRight.setPower(1);
                sleep(750);
                afLeft.setPower(0);
                afRight.setPower(0);

                //intakeClaw.setPosition(0);
                //  ArmUp(40000,1);
                //armturn.setPosition(0);
                //armturn.setPosition(0.5);
                lsLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lsRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            }
            if (gamepad2.y){
                intakeClaw.setPosition(1);
            }
            if (gamepad2.a){
                intakeClaw.setPosition(0.2);
            }
            if (gamepad1.a){
                drive.followTrajectoryAsync(myTrajectory);
                if(gamepad1.b){
                    stop();
                }

            }
            // Retrieve your pose
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );
            Pose2d myPose = myLocalizer.getPoseEstimate();

            telemetry.addData("x", myPose.getX());
            telemetry.addData("y", myPose.getY());
            telemetry.addData("heading", myPose.getHeading());

            // Insert whatever teleop code you're using
        }
    }
}
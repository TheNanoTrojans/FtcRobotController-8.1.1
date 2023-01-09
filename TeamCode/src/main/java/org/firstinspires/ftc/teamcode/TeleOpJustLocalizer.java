package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.TwoTrackingWheelLocalizer;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.StandardTrackingWheelLocalizer;
import org.firstinspires.ftc.teamcode.drive.TwoWheelTrackingLocalizer;

/**
 * This opmode assumes you have your own robot class and simply wish to utilize Road Runner's
 * packaged localizer tools.
 */
@TeleOp(name = "RandomCode",group = "advanced")
public class TeleOpJustLocalizer extends LinearOpMode {
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
    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize your own robot class
        Robot robot = new Robot(hardwareMap);

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

        // This is assuming you are using StandardTrackingWheelLocalizer.java
        // Switch this class to something else (Like TwoWheeTrackingLocalizer.java) if your
        // configuration differs
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        TwoTrackingWheelLocalizer myLocalizer = new TwoWheelTrackingLocalizer(hardwareMap,drive);

        // Retrieve our pose from the PoseStorage.currentPose static field
        // See AutoTransferPose.java for further details
        myLocalizer.setPoseEstimate(PoseStorage.currentPose);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive() && !isStopRequested()) {
            // Make sure to call myLocalizer.update() on *every* loop
            // Increasing loop time by utilizing bulk reads and minimizing writes will increase your
            // odometry accuracy


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
            afLeft.setPower(gamepad2.right_stick_y *0.5);
            afRight.setPower(gamepad2.right_stick_y*0.5);
            armturn.setPower(-gamepad2.right_stick_x);
            if (gamepad2.right_bumper) {
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
                afLeft.setPower(-1);
                afRight.setPower(-1);
                sleep(350);
                afLeft.setPower(0);
                afRight.setPower(0);
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
                sleep(2250);
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

                //intakeClaw.setPosition(0);
                //  ArmUp(40000,1);
                //armturn.setPosition(0);
                //armturn.setPosition(0.5);
                lsLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lsRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            }
            if (gamepad2.y){
                intakeClaw.setPosition(0.8);
            }
            if (gamepad2.a){
                intakeClaw.setPosition(0.5);
            }
            myLocalizer.update();

            // Retrieve your pose
            Pose2d myPose = myLocalizer.getPoseEstimate();

            // Print your pose to telemetry
            telemetry.addData("x", myPose.getX());
            telemetry.addData("y", myPose.getY());
            telemetry.addData("heading", myPose.getHeading());
            telemetry.update();
            drive.setPoseEstimate(PoseStorage.currentPose);

            // Teleop driving part
            // Mecanum example code from gm0
            // https://gm0.org/en/stable/docs/software/mecanum-drive.html
            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double rx = gamepad1.right_stick_x;

            // Set drive power
            robot.setDrivePower(x, y, rx);
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
            rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
            rightRear.setDirection(DcMotorSimple.Direction.REVERSE);

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
    }
}

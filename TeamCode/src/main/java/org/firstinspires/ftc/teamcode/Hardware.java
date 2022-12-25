package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import java.lang.String;
import java.lang.annotation.Target;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import java.util.ArrayList;



import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import java.util.concurrent.TimeUnit;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.List;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaCurrentGame;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TfodCustomModel;
import com.qualcomm.robotcore.robot.Robot;

public class Hardware extends LinearOpMode{
    public int ArmHorizontalPos;
    // create motors

    @Override
    public void runOpMode()  {

    }
    public VuforiaCurrentGame vuforiaFreightFrenzy;
    //CarouselBlue carouselBlue = new CarouselBlue();
    //CarouselRed carouselRed = new CarouselRed();
    //ShippingBlue shippingBlue = new ShippingBlue();
    public Recognition recognition;
    public TfodCustomModel tfodCustomModel;

    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor lsLeft = null;
    private DcMotor lsRight = null;
    private Servo intakeClaw = null;
    private Servo armturn = null;
    private Servo afLeft = null;
    private Servo afRight = null;
    public int frontLeftPos ;
    public int frontRightPos ;
    public int backLeftPos ;
    public int backRightPos;
    public int ArmUpPos;
    public int CarouselPos;
    public int IntakePos;



    /*    frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ArmUp1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        ArmUp1.setDirection(DcMotorSimple.Direction.REVERSE);
        carousel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftPos = 0;
        frontRightPos = 0;
        backLeftPos = 0;
        backRightPos = 0;
        ArmUpPos = 0;
        CarouselPos = 0;
        IntakePos=0;*/
    HardwareMap hwMap = null;
    public ElapsedTime runtime = new ElapsedTime();
    public Hardware() {
        intialize(hwMap);

    }

    private void intialize(HardwareMap ahwMap) {
        hwMap = ahwMap;
        afLeft = hardwareMap.get(Servo.class, "afLeft");
        afRight = hardwareMap.get(DcMotor.class, "afRight");
        intakeClaw = hardwareMap.get(Servo.class, "intakeClaw");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        armturn = hardwareMap.get(Servo.class, "carousel");
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //ArmUp1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //carousel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //ArmHorizontal.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
        //ArmUp1.setPower(0);
        //carousel.setPower(0);
        //intake.setPower(0);
        //ArmHorizontal.setPower(0);
        frontLeftPos = 0;
        frontRightPos = 0;
        backLeftPos = 0;
        backRightPos = 0;
        ArmUpPos = 0;
        CarouselPos = 0;
        IntakePos=0/
    }
    public void drive(int frontLeftTarget, int backLeftTarget,int frontRightTarget,int backRightTarget, double speed) {
        this.frontLeftPos += frontLeftTarget;
        this.backLeftPos += backLeftTarget;
        this.frontRightPos += frontRightTarget;
        this.backRightPos += backRightTarget;

        this.frontLeft.setTargetPosition(this.frontLeftPos);
        this.backLeft.setTargetPosition(this.backLeftPos);
        this.frontRight.setTargetPosition(this.frontRightPos);
        this.backRight.setTargetPosition(this.backRightPos);
        this.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.frontLeft.setPower(speed);
        this.backLeft.setPower(speed);
        this.frontRight.setPower(speed);
        this.backRight.setPower(speed);
        while(opModeIsActive() && this.frontLeft.isBusy() && this.backLeft.isBusy() && this.frontRight.isBusy() && this.backRight.isBusy()) {
            idle();
        }
    }
    private void Inches(int frontLeftTarget , int backLeftTarget ,int frontRightTarget,int backRightTarget, double speed) {
        frontLeftTarget = frontLeftTarget * 43;
        backLeftTarget = backLeftTarget * 43;
        frontRightTarget = frontRightTarget * 43;
        backRightTarget = backRightTarget * 43;
        this.frontLeftPos += frontLeftTarget;
        this.backLeftPos += backLeftTarget;
        this.frontRightPos += frontRightTarget;
        this.backRightPos += backRightTarget;

        this.frontLeft.setTargetPosition(this.frontLeftPos );
        this.backLeft.setTargetPosition(this.backLeftPos );
        this.frontRight.setTargetPosition(this.frontRightPos );
        this.backRight.setTargetPosition(this.backRightPos );
        this.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.frontLeft.setPower(speed);
        this.backLeft.setPower(speed);
        this.frontRight.setPower(speed);
        this.backRight.setPower(speed);
        while(opModeIsActive() && this.frontLeft.isBusy() && this.backLeft.isBusy() && this.frontRight.isBusy() && this.backRight.isBusy()) {
            idle();
        }
    }
    private void Strafe(int frontLeftTarget, int backLeftTarget,int frontRightTarget,int backRightTarget, double speed) {
        frontLeftTarget = frontLeftTarget * 49;
        backLeftTarget = backLeftTarget * 49;
        frontRightTarget = frontRightTarget * 49;
        backRightTarget = backRightTarget * 49;
        this.frontLeftPos += frontLeftTarget;
        this.backLeftPos += backLeftTarget;
        this.frontRightPos += frontRightTarget;
        this.backRightPos += backRightTarget;

        this.frontLeft.setTargetPosition(this.frontLeftPos);
        this.backLeft.setTargetPosition(this.backLeftPos );
        this.frontRight.setTargetPosition(this.frontRightPos);
        this.backRight.setTargetPosition(this.backRightPos);
        this.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.frontLeft.setPower(speed);
        this.backLeft.setPower(speed);
        this.frontRight.setPower(speed);
        this.backRight.setPower(speed);
        while(opModeIsActive() && this.frontLeft.isBusy() && this.backLeft.isBusy() && this.frontRight.isBusy() && this.backRight.isBusy()) {
            idle();
        }
    }
    public void ArmUp(int ArmUpTarget, double ArmSpeed) {
        this.ArmUpPos += ArmUpTarget;
        this.ArmUp1.setTargetPosition(this.ArmUpPos);
        this.ArmUp1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.ArmUp1.setPower(ArmSpeed);
        while(opModeIsActive() && ArmUp1.isBusy()) {
            idle();
        }
    }
    public void Carousel(int CarouselTarget, double CarouselSpeed) {
        this.CarouselPos += CarouselTarget;
        this.carousel.setTargetPosition(this.CarouselPos);
        this.carousel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.carousel.setPower(CarouselSpeed);
        while(opModeIsActive() && this.carousel.isBusy()) {
            idle();
        }
    }
    public void Intake(int IntakeTarget, double IntakeSpeed) {
        this.IntakePos += IntakeTarget;
        this.intake.setTargetPosition(this.IntakePos);
        this.intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.intake.setPower(IntakeSpeed);
        while(opModeIsActive() && this.intake.isBusy()) {
            idle();
        }
    }
    public void CarouselBlueRight() {
        //move forward a bit
        this.drive(400,400,400,400,0.5);
        //turn
        this.drive(-660,-660,660,660,0.5);
        sleep(200);
        //go back
        this.drive(-1400, -1400,-1400,-1400, 0.4);
        sleep(200);
        //strafe to hit carousel
        this.drive(-125,125,125,-125,0.5);
        //runcarousel
        this.Carousel(4000, 0.45);
        sleep(200);
        //strafe towards shippinghub
        this.drive(1600,-1600,-1600,1600,0.75);
        sleep(500);
        //align
        this.drive(-200,-200,-200,-200,0.75);
        // go forward toward shippinghub
        this.drive(1150,1150,1150,1150,0.5);
        sleep(500);
        telemetry.addData("Moving Arm:","Right");
        //Movethis.ArmUp
        this.ArmUp(1350,1);
        sleep(500);
//      this.ArmUp1.setPower(1);
        //this.ArmUp2.setPower(1);
//      sleep(300);
//      this.ArmUp1.setPower(0);
        //this.ArmUp2.setPower(0);
        //MoveForwardTowards Hub
        this.drive(300,300,300,300, 0.45);
        sleep(1000);
        //outake
        this.Intake(-2400, 0.3);
        //intake.setPower(-0.5);
        //sleep(600);
        //intake.setPower(0);
        sleep(1000);

        //go back
        this.drive(-150,-150,-150,-150, 0.45);
        sleep(200);
        //move arm down
        this.ArmUp(-1200,0.5);

        // Arm Movement


        //this.ArmUp2.setPower(0);
        this.drive(-1300,-1300,-1300,-1300,0.5);
        this.drive(1200,-1200,-1200,1200,0.75);
        this.drive(-650,-650,-650,-650, 0.5);
        this.ArmUp(520,1);
//      this.ArmUp1.setPower(1);
        //this.ArmUp2.setPower(1);
//      sleep(125);
//      this.ArmUp1.setPower(0);
        //this.ArmUp2.setPower(0);
        this.drive(3600,3600,3600,3600,0.5);
        sleep(200);
        this.drive(-1800,1800,1800,-1800,0.5);
        sleep(200);
        this.drive(2000,2000,2000,2000,0.5);
        stop();
    }
    public void CarouselBlueLeft(){
        this.drive(400,400,400,400,0.5);
        //turn
        this.drive(-660,-660,660,660,0.5);
        sleep(200);
        //go back
        this.drive(-1400, -1400,-1400,-1400, 0.4);
        sleep(200);
        //strafe to hit carousel
        this.drive(-125,125,125,-125,0.5);
        //runcarousel
        this.Carousel(4000, 0.45);
        sleep(200);
        //strafe towards shippinghub
        this.drive(1600,-1600,-1600,1600,0.75);
        sleep(500);
        //align
        this.drive(-200,-200,-200,-200,0.75);
        // go forward toward shippinghub
        this.drive(1150,1150,1150,1150,0.5);
        sleep(500);
        telemetry.addData("Moving Arm:","Left");
        this.ArmUp(600,1);
        //this.ArmUp1.setPower(1);
        //this.ArmUp2.setPower(1);
        //sleep(100);
        //this.ArmUp1.setPower(0);
        //this.ArmUp2.setPower(0);
        sleep(200);
        this.drive(150,150,150,150,0.5);
        this.Intake(-2400,0.4);
        sleep(200);
        this.drive(-150,-150,-150,-150,0.5);
        this.ArmUp(-600,0.5);
        this.drive(-1300,-1300,-1300,-1300,0.5);
        this.drive(1200,-1200,-1200,1200,0.75);
        this.drive(-650,-650,-650,-650, 0.5);
        this.ArmUp(520,1);
//      this.ArmUp1.setPower(1);
        //this.ArmUp2.setPower(1);
//      sleep(125);
//      this.ArmUp1.setPower(0);
        //this.ArmUp2.setPower(0);
        this.drive(3600,3600,3600,3600,0.5);
        sleep(200);
        this.drive(-1800,1800,1800,-1800,0.5);
        sleep(200);
        this.drive(2000,2000,2000,2000,0.5);
        stop();
    }
    public void CarouselBlueCenter() {
        //move forward a bit
        this.drive(400,400,400,400,0.5);
        //turn
        this.drive(-660,-660,660,660,0.5);
        sleep(200);
        //go back
        this.drive(-1400, -1400,-1400,-1400, 0.4);
        sleep(200);
        //strafe to hit carousel
        this.drive(-125,125,125,-125,0.5);
        //runcarousel
        this.Carousel(4000, 0.45);
        sleep(200);
        //strafe towards shippinghub
        this.drive(1600,-1600,-1600,1600,0.75);
        sleep(500);
        //align
        this.drive(-200,-200,-200,-200,0.75);
        // go forward toward shippinghub
        this.drive(1150,1150,1150,1150,0.5);
        sleep(500);
        telemetry.addData("Moving Arm:","Center");
        this.ArmUp(1000,1);
        //this.ArmUp2.setPower(1);
        sleep(200);
        //this.ArmUp1.setPower(0);
        //this.ArmUp2.setPower(0);
        this.drive(150,150,150,150,0.5);
        this.Intake(-2400,0.35);
        sleep(200);
        this.drive(-150,-150,-150,-150,0.5);
        this.ArmUp(-1000,0.5);

        //this.ArmUp2.setPower(0);

        // Arm Movement


        //this.ArmUp2.setPower(0);
        this.drive(-1300,-1300,-1300,-1300,0.5);
        this.drive(1200,-1200,-1200,1200,0.75);
        this.drive(-650,-650,-650,-650, 0.5);
        this.ArmUp(520,1);
//      this.ArmUp1.setPower(1);
        //this.ArmUp2.setPower(1);
//      sleep(125);
//      this.ArmUp1.setPower(0);
        //this.ArmUp2.setPower(0);
        this.drive(3600,3600,3600,3600,0.5);
        sleep(200);
        this.drive(-1800,1800,1800,-1800,0.5);
        sleep(200);
        this.drive(2000,2000,2000,2000,0.5);
        stop();
    }
    public void enhanceCam(Camera webcam){
        ExposureControl myExposureControl = webcam.getControl(ExposureControl.class);
        myExposureControl.setMode(ExposureControl.Mode.Manual);
        GainControl myGainControl = webcam.getControl(GainControl.class);
        myExposureControl.setExposure(15,TimeUnit.MILLISECONDS);
        myGainControl.setGain(600);
    }
    public void runTFOD() {
        List<Recognition> recognitions;
        int index;
        this.vuforiaFreightFrenzy = new VuforiaCurrentGame();
        this.tfodCustomModel = new TfodCustomModel();
        // Sample TFOD Op Mode using a Custom Model
        // Initialize Vuforia to provide TFOD with camera
        // images.
        // The following block uses the device's back camera.
        this.vuforiaFreightFrenzy.initialize(
                "", // vuforiaLicenseKey
                this.hardwareMap.get(WebcamName.class, "Webcam 1"), // cameraName
                "", // webcamCalibrationFilename
                false, // useExtendedTracking
                true, // enableCameraMonitoring
                VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES, // cameraMonitorFeedback
                0, // dx
                0, // dy
                0, // dz
                AxesOrder.XZY, // axesOrder
                90, // firstAngle
                90, // secondAngle
                0, // thirdAngle
                true); // useCompetitionFieldTargetLocations
        // Initialize TFOD before waitForStart.
        // Use the Manage page to upload your custom model.
        // In the next block, replace WiffleBalls.tflite with
        //  the name of your custom model.
        List <String> myobjects = new ArrayList <String>();
        myobjects.add("ShipElement");
//    tfodCustomModel.setModelFromFile("Cup2022.tflite", JavaUtil.createListWith("Cup"));
        this.tfodCustomModel.setModelFromFile("Blue19947.tflite", myobjects);
        this.tfodCustomModel.initializeWithIsModelTensorFlow2(vuforiaFreightFrenzy, (float) 0.7, true, true, true);
        this.tfodCustomModel.setClippingMargins(0, 100, 0, 0);
        this.tfodCustomModel.activate();
        Camera cam = this.vuforiaFreightFrenzy.getVuforiaLocalizer().getCamera();
        this.enhanceCam(cam);
        tfodCustomModel.setZoom(1, 16 / 9);
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        // Wait for start command from robot.driver Station.
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
//sleep(2500);



            while (opModeIsActive()) {
                // Put loop blocks here.
                // Get a list of recognitions from TFOD.
                recognitions = tfodCustomModel.getRecognitions();
                // If list is empty, inform the user. Otherwise, go
                // through list and display info for each recognition.
                if (recognitions.size() == 0) {
                    telemetry.addData("TFOD", "No items detected.");

                } else {
                    index = 0;
                    // Iterate through list and call a function to
                    // display info for each recognized object.
                    for (Recognition recognition_item : recognitions) {
                        recognition = recognition_item;
                        // Display info.
                        CarouselBlue.displayInfo(index);
                        // Increment index.
                        index = index + 1;
                    }
                    sleep(1000);
                }
                telemetry.update();

            }

        }
        // Deactivate TFOD.
        this.tfodCustomModel.deactivate();

        this.vuforiaFreightFrenzy.close();
        this.tfodCustomModel.close();
    }
    public void ArmHorizontal(int ArmHorizontalTarget, double TurretSpeed) {
        ArmHorizontalPos += ArmHorizontalTarget;
        ArmHorizontal.setTargetPosition(ArmHorizontalPos);
        ArmHorizontal.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ArmHorizontal.setPower(TurretSpeed);
        while(opModeIsActive() && ArmHorizontal.isBusy()) {
            idle();
        }
    }
    public void ShippingBlueRight(){
        this.Strafe(24,-24,-24,24,0.5);
        sleep(500);
        this.Inches(16,16,16,16,0.5);
        sleep(500);
        telemetry.addData("Moving Arm:","Right");
        //MoveArmUP
        this.ArmUp(1350,1);
        sleep(500);
//      this.ArmUp1.setPower(1);
        //ArmUp2.setPower(1);
//      sleep(300);
//      ArmUp1.setPower(0);
        //ArmUp2.setPower(0);
        //MoveForwardTowards Hub
        this.drive(300,300,300,300, 0.45);
        sleep(200);
        //outake
        this.Intake(-1200, 0.3);
        //intake.setPower(-0.5);
        //sleep(600);
        //intake.setPower(0);
        sleep(1000);

        //go back
        this.drive(-300,-300,-300,-300, 0.45);
        sleep(200);
        //move this.Arm down
        this.ArmUp(-1400,0.5);
//      ArmUp1.setPower(-0.5);
        //ArmUp2.setPower(-0.5);
        //sleep(580);
        //ArmUp1.setPower(0);
        //ArmUp2.setPower(0);

        this.Inches(-16,-16,-16,-16,0.5);
        sleep(500);
        this.drive(-660,-660,660,660,0.5);
        sleep(500);
        this.Strafe(3,-3,-3,3,0.5);
        sleep(200);
        //Strafe(0.5,0.5,0.5,0.5,0.5);

        this.Inches(64,64,64,64,0.5);
        sleep(500);
        this.intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.intake.setPower(1);
        sleep(1250);
        this.intake.setPower(0);
        this.intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.Inches(-6,-6,-6,-6,0.5);
//    Intake(1000,1);
        sleep(700);
        this.Strafe(-4,4,4,-4,0.5);
        sleep(250);
        this.Inches(-55,-55,-55,-55,0.75);
        sleep(250);
        this.drive(660,660,-660,-660,0.5);
        sleep(250);
        //   Inches(3,3,3,3,0.5);
        //   sleep(500);
        this.Inches(-6,-6,-4,-4,0.5);
        sleep(250);
        this.Inches(16,16,16,16,0.75);
        sleep(250);
        telemetry.addData("Moving Arm:","Right");
        //Movethis.ArmUP
        this.ArmUp(900,1);
        //ArmUp2.setPower(1);
        sleep(200);
        //ArmUp1.setPower(0);
        //ArmUp2.setPower(0);
        this.Inches(2,2,2,2,0.75);
        this.Intake(-1200,0.35);
        sleep(200);
        this.Inches(-2,-2,-2,-2,0.75);
        this.ArmUp(-900,0.5);
//    Inches(-16,-16,-16,-16,0.5);
//    sleep(500);
        this.drive(-660,-660,660,660,0.5);
        sleep(500);
        this.Strafe(-17,17,17,-17,0.5);
        this.Inches(64,64,64,64,0.75);
        sleep(500);
        this.intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.intake.setPower(1);
        sleep(1250);
        this.intake.setPower(0);
        this.intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void ShippingBlueLeft(){
        this.Strafe(24,-24,-24,24,0.5);
        sleep(500);
        this.Inches(16,16,16,16,0.5);
        sleep(500);
        telemetry.addData("Moving Arm:","Left");
        ArmUp(600,1);
        //ArmUp1.setPower(1);
        //ArmUp2.setPower(1);
        //sleep(100);
        //ArmUp1.setPower(0);
        //ArmUp2.setPower(0);
        sleep(200);
        drive(50,50,50,50,0.5);
        Intake(-800,0.4);
        sleep(200);
        drive(-50,-50,-50,-50,0.5);
        ArmUp(-600,0.5);
//      ArmUp1.setPower(-0.5);
        //ArmUp2.setPower(-0.5);
        //sleep(580);
        //ArmUp1.setPower(0);
        //ArmUp2.setPower(0);

        this.Inches(-16,-16,-16,-16,0.5);
        sleep(500);
        this.drive(-660,-660,660,660,0.5);
        sleep(500);
        this.Strafe(3,-3,-3,3,0.5);
        sleep(200);
        //Strafe(0.5,0.5,0.5,0.5,0.5);

        this.Inches(64,64,64,64,0.5);
        sleep(500);
        this.intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.intake.setPower(1);
        sleep(1250);
        this.intake.setPower(0);
        this.intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.Inches(-6,-6,-6,-6,0.5);
//    Intake(1000,1);
        sleep(700);
        this.Strafe(-4,4,4,-4,0.5);
        sleep(250);
        this.Inches(-55,-55,-55,-55,0.75);
        sleep(250);
        this.drive(660,660,-660,-660,0.5);
        sleep(250);
        //   Inches(3,3,3,3,0.5);
        //   sleep(500);
        this.Inches(-6,-6,-4,-4,0.5);
        sleep(250);
        this.Inches(16,16,16,16,0.75);
        sleep(250);
        telemetry.addData("Moving Arm:","Left");
        ArmUp(900,1);
        //ArmUp2.setPower(1);
        sleep(200);
        //ArmUp1.setPower(0);
        //ArmUp2.setPower(0);
        Inches(2,2,2,2,0.75);
        Intake(-1200,0.35);
        sleep(200);
        Inches(-2,-2,-2,-2,0.75);
        ArmUp(-900,0.5);
//    Inches(-16,-16,-16,-16,0.5);
//    sleep(500);
        this.drive(-660,-660,660,660,0.5);
        sleep(500);
        this.Strafe(-17,17,17,-17,0.5);
        this.Inches(64,64,64,64,0.75);
        sleep(500);
        this.intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.intake.setPower(1);
        sleep(1250);
        this.intake.setPower(0);
        this.intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void ShippingBlueCenter(){
        this.Strafe(24,-24,-24,24,0.5);
        sleep(500);
        this.Inches(16,16,16,16,0.5);
        sleep(500);
        telemetry.addData("Moving Arm:","Center");
        this.ArmUp(950,1);
        //ArmUp2.setPower(1);
        sleep(200);
        //ArmUp1.setPower(0);
        //ArmUp2.setPower(0);
        this.Inches(2,2,2,2,0.5);
        this.Intake(-800,0.35);
        sleep(200);
        this.Inches(-2,-2,-2,-2,0.5);
        this.ArmUp(-950,0.5);

        this.Inches(-16,-16,-16,-16,0.5);
        sleep(500);
        this.drive(-660,-660,660,660,0.5);
        sleep(500);
        this.Strafe(3,-3,-3,3,0.5);
        sleep(200);
        //Strafe(0.5,0.5,0.5,0.5,0.5);

        this.Inches(64,64,64,64,0.5);
        sleep(500);
        this.intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.intake.setPower(1);
        sleep(1250);
        this.intake.setPower(0);
        this.intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.Inches(-6,-6,-6,-6,0.5);
//    Intake(1000,1);
        sleep(700);
        this.Strafe(-4,4,4,-4,0.5);
        sleep(250);
        this.Inches(-55,-55,-55,-55,0.75);
        sleep(250);
        this.drive(660,660,-660,-660,0.5);
        sleep(250);
        //   Inches(3,3,3,3,0.5);
        //   sleep(500);
        this.Inches(-6,-6,-4,-4,0.5);
        sleep(250);
        this.Inches(16,16,16,16,0.75);
        sleep(250);
        telemetry.addData("Moving Arm:","Center");
        this.ArmUp(950,1);
        //ArmUp2.setPower(1);
        sleep(200);
        //ArmUp1.setPower(0);
        //ArmUp2.setPower(0);
        this.Inches(2,2,2,2,0.75);
        this.Intake(-1200,0.35);
        sleep(200);
        this.Inches(-2,-2,-2,-2,0.75);
        this.ArmUp(-950,0.5);
//    Inches(-16,-16,-16,-16,0.5);
//    sleep(500);
        this.drive(-660,-660,660,660,0.5);
        sleep(500);
        this.Strafe(-17,17,17,-17,0.5);
        this.Inches(64,64,64,64,0.75);
        sleep(500);
        this.intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.intake.setPower(1);
        sleep(1250);
        this.intake.setPower(0);
        this.intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void CarouselRedRight() {
        //move forward a bit
        this.drive(700,700,700,700,0.5);
        //turn
        this.drive(-1900,1900,1900,-1900,0.5);
        sleep(200);
        //go back
        this.drive(-200, -200,-200,-200, 0.5);
        sleep(200);
        //strafe to hit carousel
//      this.drive(175,-175,-175,175,0.5);
        //runcarousel
        this.Carousel(-3500, 0.45);
        sleep(200);
        //strafe towards shippinghub
        this.drive(1600,1600,1600,1600,0.5);
        sleep(500);
        //align
        this.drive(660,660,-660,-660,0.5);
        // go forward toward shippinghub
        this.drive(1150,1150,1150,1150,0.5);
        sleep(500);
        telemetry.addData("Moving Arm:","Right");
        //MoveArmUP
        this.ArmUp(1350,1);
        sleep(500);
//      ArmUp1.setPower(1);
        //ArmUp2.setPower(1);
//      sleep(300);
//      ArmUp1.setPower(0);
        //ArmUp2.setPower(0);
        //MoveForwardTowards Hub
        this.drive(300,300,300,300, 0.45);
        sleep(1000);
        //outake
        this.Intake(-2400, 0.3);
        //intake.setPower(-0.5);
        //sleep(600);
        //intake.setPower(0);
        sleep(1000);

        //go back
        this.drive(-150,-150,-150,-150, 0.45);
        sleep(200);
        //move arm down
        this.ArmUp(-1200,0.5);
//      ArmUp1.setPower(-0.5);
        //ArmUp2.setPower(-0.5);
        //sleep(580);
        //ArmUp1.setPower(0);
        //ArmUp2.setPower(0);

        // Arm Movement

        this.drive(-1500,-1500,-1500,-1500,0.5);
        this.drive(400,-400,-400,400,0.5);
        stop();
    }
    public void CarouselRedLeft() {
        //move forward a bit
        this.drive(700,700,700,700,0.5);
        //turn
        this.drive(-1900,1900,1900,-1900,0.5);
        sleep(200);
        //go back
        this.drive(-200, -200,-200,-200, 0.5);
        sleep(200);
        //strafe to hit carousel
//      this.drive(175,-175,-175,175,0.5);
        //runcarousel
        this.Carousel(-3500, 0.45);
        sleep(200);
        //strafe towards shippinghub
        this.drive(1600,1600,1600,1600,0.5);
        sleep(500);
        //align
        this.drive(660,660,-660,-660,0.5);
        // go forward toward shippinghub
        this.drive(1150,1150,1150,1150,0.5);
        sleep(500);

        // Arm Movement
        telemetry.addData("Moving Arm:","Left");
        this.ArmUp(650,1);
        //ArmUp1.setPower(1);
        //ArmUp2.setPower(1);
        //sleep(100);
        //ArmUp1.setPower(0);
        //ArmUp2.setPower(0);
        sleep(200);
        this.drive(200,200,200,200,0.5);
        this.Intake(-2400,0.4);
        sleep(200);
        this.drive(-200,-200,-200,-200,0.5);
        this.ArmUp(-650,0.5);
        //ArmUp2.setPower(0);
        this.drive(-1500,-1500,-1500,-1500,0.5);
        this.drive(400,-400,-400,400,0.5);
        stop();
    }
    public void CarouselRedCenter() {
        //move forward a bit
        this.drive(700,700,700,700,0.5);
        //turn
        this.drive(-1900,1900,1900,-1900,0.5);
        sleep(200);
        //go back
        this.drive(-200, -200,-200,-200, 0.5);
        sleep(200);
        //strafe to hit carousel
//      this.drive(175,-175,-175,175,0.5);
        //runcarousel
        this.Carousel(-3500, 0.45);
        sleep(200);
        //strafe towards shippinghub
        this.drive(1600,1600,1600,1600,0.5);
        sleep(500);
        //align
        this.drive(660,660,-660,-660,0.5);
        // go forward toward shippinghub
        this.drive(1150,1150,1150,1150,0.5);
        sleep(500);
        telemetry.addData("Moving this.Arm:","Center");
        this.ArmUp(933,1);
        //ArmUp2.setPower(1);
        sleep(200);
        //ArmUp1.setPower(0);
        //ArmUp2.setPower(0);
        this.Intake(-2400,0.35);
        sleep(200);
        this.ArmUp(-933,0.5);
        this.drive(-1500,-1500,-1500,-1500,0.5);
        this.drive(400,-400,-400,400,0.5);
        stop();
    }
    public void ShippingRedRight() {
        // move left
        this.drive(-1200,1200,1200,-1200,0.75);
        sleep(500);
//    drive(300,300,300,300,0.75);
//    sleep(200);
        this.drive(700,700,700,700,0.75);
        sleep(200);
        telemetry.addData("Moving Arm:","Right");
        //MoveArmUP
        this.ArmUp(1350,1);
        sleep(500);
//      thi.ArmUp1.setPower(1);
        //this.ArmUp2.setPower(1);
//      sleep(300);
//      this.ArmUp1.setPower(0);
        //this.ArmUp2.setPower(0);
        //MoveForwardTowards Hub
        this.drive(150,150,150,150, 0.75);
        sleep(1000);
        //outake
        this.Intake(-2800, 0.3);
        //intake.setPower(-0.5);
        //sleep(600);
        //intake.setPower(0);
        sleep(1000);

        //go back
        this.drive(-150,-150,-150,-150, 0.75);
        sleep(200);
        //move arm down
        this.ArmUp(-1350,0.5);
//      this.ArmUp1.setPower(-0.5);
        //this.ArmUp2.setPower(-0.5);
        //sleep(580);
        //this.ArmUp1.setPower(0);
        //this.ArmUp2.setPower(0);
        sleep(200);
        this.drive(-850,-850,-850,-850,0.75);
        sleep(200);
        this.drive(700,700,-700,-700,0.75);
        sleep(500);
        this.drive(400,-400,-400,400,0.75);
        sleep(200);
        this.drive(-50,50,50,-50,0.75);
        sleep(200);
        this.drive(3000,3000,3000,3000,0.75);
        sleep(200);
        this.Intake(250,1);
        sleep(500);
        this.drive(-200,-200,-200,-200,0.75);
        sleep(100);
        this.drive(400,-400,-400,400,0.75);
        sleep(200);
        this.drive(-100,100,100,-100,0.75);
        sleep(200);
        this.drive(-2600,-2600,-2600,-2600,0.75);
        sleep(200);
/*    this.drive(-400,400,400,-400,0.75);
    sleep(200);
    this.drive(-775,-775,775,775,0.75);
    sleep(200);*/
        this.drive(-750,750,750,-750,0.75);
        sleep(200);
        this.ArmUp(800,1);
        sleep(200);
        this.ArmHorizontal(3000,1);
        //this.ArmUp1.setPower(1);
        //this.ArmUp2.setPower(1);
        //sleep(100);
        //this.ArmUp1.setPower(0);
        //this.ArmUp2.setPower(0);
        sleep(200);
        //this.drive(200,200,200,200,0.75);
        this.Intake(-2800,0.6);
        sleep(1200);
        //this.drive(-200,-200,-200,-200,0.75);
        this.ArmHorizontal(-3200,1);
        this.ArmUp(-800,0.5);
        sleep(200);
//    this.drive(-850,-850,-850,-850,0.75);
//    sleep(200);
//    this.drive(725,725,-725,-725,0.75);
        sleep(500);
        this.drive(800,-800,-800,800,0.75);
        sleep(200);
        this.drive(-50,50,50,-50,0.5);
        sleep(200);
        this.drive(3100,3100,3100,3100,0.75);
        sleep(200);
        this.intake.setPower(1);
        sleep(250);
        this.intake.setPower(0);
        sleep(500);
        this.drive(-200,-200,-200,-200,0.75);
        sleep(100);
        this.drive(400,-400,-400,400,0.75);
        sleep(200);
        this.drive(-100,100,100,-100,0.75);
        sleep(200);
        this.drive(-2600,-2600,-2600,-2600,0.75);
        sleep(200);
/*    this.drive(-400,400,400,-400,0.75);
    sleep(200);
    this.drive(-775,-775,775,775,0.75);
    sleep(200);*/
        this.drive(-750,750,750,-750,0.75);
        sleep(200);
        this.ArmUp(800,1);
        sleep(200);
        this.ArmHorizontal(3000,1);
        //this.ArmUp1.setPower(1);
        //this.ArmUp2.setPower(1);
        //sleep(100);
        //this.ArmUp1.setPower(0);
        //this.ArmUp2.setPower(0);
        sleep(200);
        //this.drive(200,200,200,200,0.75);
        this.Intake(-2800,0.6);
        sleep(1200);
        //this.drive(-200,-200,-200,-200,0.75);
        this.ArmHorizontal(-3200,1);
        this.ArmUp(-800,0.5);
        sleep(200);
//    this.drive(-850,-850,-850,-850,0.75);
//    sleep(200);
//    this.drive(725,725,-725,-725,0.75);
        sleep(500);
        this.drive(800,-800,-800,800,0.75);
        sleep(200);
        this.drive(-50,50,50,-50,0.5);
        sleep(200);
        this.drive(3100,3100,3100,3100,0.75);
        sleep(200);
        this.intake.setPower(1);
        sleep(250);
        this.intake.setPower(0);


        stop();
    }
    public void ShippingRedLeft() {
        // move left
        this.drive(-1200,1200,1200,-1200,0.75);
        sleep(500);
//    drive(300,300,300,300,0.75);
//    sleep(200);
        this.drive(700,700,700,700,0.75);
        sleep(200);
        telemetry.addData("Moving this.Arm:","Left");
        this.ArmUp(700,1);
        //this.ArmUp1.setPower(1);
        //this.ArmUp2.setPower(1);
        //sleep(100);
        //this.ArmUp1.setPower(0);
        //this.ArmUp2.setPower(0);
        sleep(500);
        //this.drive(200,200,200,200,0.75);
        this.Intake(-2800,0.5);
        sleep(500);
        //this.drive(-200,-200,-200,-200,0.75);
        this.ArmUp(-700,0.5);
        //this.ArmUp2.setPower(0);
        sleep(200);
        this.drive(-850,-850,-850,-850,0.75);
        sleep(200);
        this.drive(700,700,-700,-700,0.75);
        sleep(500);
        this.drive(400,-400,-400,400,0.75);
        sleep(200);
        this.drive(-50,50,50,-50,0.75);
        sleep(200);
        this.drive(3000,3000,3000,3000,0.75);
        sleep(200);
        this.Intake(250,1);
        sleep(500);
        this.drive(-200,-200,-200,-200,0.75);
        sleep(100);
        this.drive(400,-400,-400,400,0.75);
        sleep(200);
        this.drive(-100,100,100,-100,0.75);
        sleep(200);
        this.drive(-2600,-2600,-2600,-2600,0.75);
        sleep(200);
/*    this.drive(-400,400,400,-400,0.75);
    sleep(200);
    this.drive(-775,-775,775,775,0.75);
    sleep(200);*/
        this.drive(-750,750,750,-750,0.75);
        sleep(200);
        this.ArmUp(800,1);
        sleep(200);
        this.ArmHorizontal(3000,1);
        //this.ArmUp1.setPower(1);
        //this.ArmUp2.setPower(1);
        //sleep(100);
        //this.ArmUp1.setPower(0);
        //this.ArmUp2.setPower(0);
        sleep(200);
        //this.drive(200,200,200,200,0.75);
        this.Intake(-2800,0.6);
        sleep(1200);
        //this.drive(-200,-200,-200,-200,0.75);
        this.ArmHorizontal(-3200,1);
        this.ArmUp(-800,0.5);
        sleep(200);
//    this.drive(-850,-850,-850,-850,0.75);
//    sleep(200);
//    this.drive(725,725,-725,-725,0.75);
        sleep(500);
        this.drive(800,-800,-800,800,0.75);
        sleep(200);
        this.drive(-50,50,50,-50,0.5);
        sleep(200);
        this.drive(3100,3100,3100,3100,0.75);
        sleep(200);
        this.intake.setPower(1);
        sleep(250);
        this.intake.setPower(0);
        sleep(500);
        this.drive(-200,-200,-200,-200,0.75);
        sleep(100);
        this.drive(400,-400,-400,400,0.75);
        sleep(200);
        this.drive(-100,100,100,-100,0.75);
        sleep(200);
        this.drive(-2600,-2600,-2600,-2600,0.75);
        sleep(200);
/*    this.drive(-400,400,400,-400,0.75);
    sleep(200);
    this.drive(-775,-775,775,775,0.75);
    sleep(200);*/
        this.drive(-750,750,750,-750,0.75);
        sleep(200);
        this.ArmUp(800,1);
        sleep(200);
        this.ArmHorizontal(3000,1);
        //this.ArmUp1.setPower(1);
        //this.ArmUp2.setPower(1);
        //sleep(100);
        //this.ArmUp1.setPower(0);
        //this.ArmUp2.setPower(0);
        sleep(200);
        //this.drive(200,200,200,200,0.75);
        this.Intake(-2800,0.6);
        sleep(1200);
        //this.drive(-200,-200,-200,-200,0.75);
        this.ArmHorizontal(-3200,1);
        this.ArmUp(-800,0.5);
        sleep(200);
//    this.drive(-850,-850,-850,-850,0.75);
//    sleep(200);
//    this.drive(725,725,-725,-725,0.75);
        sleep(500);
        this.drive(800,-800,-800,800,0.75);
        sleep(200);
        this.drive(-50,50,50,-50,0.5);
        sleep(200);
        this.drive(3100,3100,3100,3100,0.75);
        sleep(200);
        this.intake.setPower(1);
        sleep(250);
        this.intake.setPower(0);


        stop();

    }
    public void ShippingRedCenter() {

    }


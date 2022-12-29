package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;

public class ServoTest {
    private CRServo afLeft;
    private CRServo afRight;
    public void runOpMode() throws InterruptedException {
        waitForStart();
        intakeClaw = hardwareMap.servo.get("intakeClaw");
        afLeft = hardwareMap.crservo.get("afLeft");
        afRight = hardwareMap.crservo.get("afRight");

    }
}

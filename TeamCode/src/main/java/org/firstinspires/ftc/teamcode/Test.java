package org.firstinspires.ftc.teamcode;

//imports libraries
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;

//declares motors
@com.qualcomm.robotcore.eventloop.opmode.TeleOp (name = "Test")
public class Test extends OpMode {

    protected Servo afLeft;
    @Override
    public void init() {
        afLeft =  hardwareMap.servo.get("afLeft");
    }

    @Override
    public void loop() {
        afLeft.setDirection(Servo.Direction.REVERSE);
        if (gamepad2.right_bumper) {
            afLeft.setPosition(45);
		
        }
        if(gamepad2.left_bumper){
            afLeft.setPosition(0);
        }
        }

    }

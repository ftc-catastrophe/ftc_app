package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a K9 robot.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Servo channel:  Servo to raise/lower arm: "arm"
 * Servo channel:  Servo to open/close beaconPusher: "beaconPusher"
 *
 * Note: the configuration of the servos is such that:
 *   As the arm servo approaches 0, the arm position moves up (away from the floor).
 *   As the beaconPusher servo approaches 0, the beaconPusher opens up (drops the game element).
 */
public class ChainDriveBot1
{
    /* Public OpMode members. */
    public DcMotor leftMotor = null;
    public DcMotor rightMotor = null;
    public Servo beaconPusher = null;

    public ColorSensor colorDown = null;
    public ColorSensor colorFrontLeft = null;
    public ColorSensor colorFrontRight = null;
    public OpticalDistanceSensor beaconDistance = null;

    public final static double BEACON_PUSHER_HOME = 0.45; // defines middle position for servo
    public final static double BEACON_PUSHER_SPEED = 0.01; // sets rate to move servo
    public final static double BEACON_PUSHER_MIN_RANGE  = 0.33; // sets furthest left for servo
    public final static double BEACON_PUSHER_MAX_RANGE = 0.65; // sets furthest right for servo

    /* Local OpMode members. */
    private HardwareMap hardwareMap = null;
    private ElapsedTime period  = new ElapsedTime();
    private Telemetry telemetry;

    /* Constructor */
    public ChainDriveBot1(Telemetry aTelemetry) {
        telemetry = aTelemetry;
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // save reference to HW Map
        hardwareMap = ahwMap;

        leftMotor = hardwareMap.dcMotor.get("left_drive");
        rightMotor = hardwareMap.dcMotor.get("right_drive");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        leftMotor.setPower(0);
        rightMotor.setPower(0);

        // Set all motors to run without encoders.
        // Our motor encoders aren't plugged in, so we should stick with RUN_WITHOUT_ENCODERS.
        // If we install our motor encoders and update our code to make use of them, we can
        // switch to RUN_WITH_ENCODERS.
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Define and initialize ALL installed servos
        beaconPusher = hardwareMap.servo.get("beacon_pusher");
        beaconPusher.setPosition(BEACON_PUSHER_HOME);

        colorDown = hardwareMap.colorSensor.get("color_down");
        colorFrontLeft = hardwareMap.colorSensor.get("color_front_left");
        colorFrontRight = hardwareMap.colorSensor.get("color_front_right");

        // Each color sensor needs a unique I2C address
        if (null != colorDown) {
            colorDown.setI2cAddress(I2cAddr.create8bit(0x40));
            colorDown.enableLed(false);
        } else {
            telemetry.addData("ChainDriveBot1.init()", "color_down sensor not found");
        }

        if (null != colorFrontLeft) {
            colorFrontLeft.setI2cAddress(I2cAddr.create8bit(0x3e));
            colorFrontLeft.enableLed(false);
        } else {
            telemetry.addData("ChainDriveBot1.Init()", "color_front_left sensor not found");
        }
        if (null != colorFrontRight) {
            colorFrontRight.setI2cAddress(I2cAddr.create8bit(0x3c));
            colorFrontRight.enableLed(false);
        } else {
            telemetry.addData("ChainDriveBot1.init()", "color_front_right sensor not found");
        }

        beaconDistance = hardwareMap.opticalDistanceSensor.get("beacon_distance");
    }

    /***
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  Length of wait cycle in mSec.
     */
    public void waitForTick(long periodMs) {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0) {
            try {
                Thread.sleep(remaining);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}

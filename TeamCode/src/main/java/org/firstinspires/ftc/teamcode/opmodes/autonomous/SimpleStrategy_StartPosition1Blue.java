/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.HardwareK9bot;


/**
 * Created by Thomas on 11/19/2016.
 */
@Autonomous(name="AutonomousMode1Blue", group="Autonomous")
public class SimpleStrategy_StartPosition1Blue extends LinearOpMode {
    {
    }
    /* Declare OpMode members. */
    HardwareK9bot robot           = new HardwareK9bot();              // Use a K9'shardware
    double          armPosition     = robot.ARM_HOME;                   // Servo safe position
    double          clawPosition    = robot.CLAW_HOME;                  // Servo safe position
    final double    CLAW_SPEED      = 0.01 ;                            // sets rate to move servo
    final double    ARM_SPEED       = 0.01 ;                            // sets rate to move servo

    @Override
    public void runOpMode() {
        double left;
        double right;

        long moveForward1Millisecond = 1000;
        long turnRightMilliseconds = 1000;
        long moveForward2Milliseconds = 2000;
        double motorSpeed = 0.5;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Say", "IN WHILE STATEMENT");
            telemetry.update();

            //move forward
            left = motorSpeed;
            right = motorSpeed;
            robot.frontLeftMotor.setPower(left);
            robot.frontRightMotor.setPower(right);
            try {
                robot.waitForTick(moveForward2Milliseconds);
                telemetry.addData("Say", "Forward");
                telemetry.update();
            }
            catch(Exception e)
            {
                telemetry.addData("Exception", e.getMessage());
            }
            // turn left

            left = -motorSpeed;
            right = motorSpeed;
            robot.frontLeftMotor.setPower(left);
            robot.frontRightMotor.setPower(right);
            try {
                robot.waitForTick(turnRightMilliseconds);
                telemetry.addData("Say", "Right");
                telemetry.update();
            }
            catch(Exception e)
            {
                telemetry.addData("Exception", e.getMessage());
            }
            // move forward

            left = motorSpeed;
            right = motorSpeed;
            robot.frontLeftMotor.setPower(left);
            robot.frontRightMotor.setPower(right);
            try {
                robot.waitForTick(moveForward2Milliseconds);
                telemetry.addData("Say", "Forward");
                telemetry.update();
            }
            catch(Exception e)
            {
                telemetry.addData("Exception", e.getMessage());
            }
            /*//turn right
            left = motorSpeed;
            right = -motorSpeed;
            robot.frontLeftMotor.setPower(left);
            robot.frontRightMotor.setPower(right);
            try {
                robot.waitForTick(turnRightMilliseconds);
                telemetry.addData("Say", "Left");
                telemetry.update();
            }
            catch(Exception e)
            {
                telemetry.addData("Exception", e.getMessage());
            }
            // move forward

            left = motorSpeed;
            right = motorSpeed;
            robot.frontLeftMotor.setPower(left);
            robot.frontRightMotor.setPower(right);
            try {
                robot.waitForTick(moveForward2Milliseconds);
                telemetry.addData("Say", "Forward");
                telemetry.update();
            }
            catch(Exception e)
            {
                telemetry.addData("Exception", e.getMessage());
            }*/
            // turn right
            telemetry.addData("left",  "%.2f", left);
            telemetry.addData("right", "%.2f", right);
            telemetry.update();

            // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
            robot.waitForTick(40);
            break;

        }
    }
}
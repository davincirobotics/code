//this is the package for the program, the package name has
//to be the same as the path from the root to the file,
//look at the folders you had to open to get to this file.
//that's what this package name entails
package org.firstinspires.ftc.teamcode;

//next up are the libraries used.
//you usually don't really have to mess around with these in
//a daily basis but if you started to use a component for
//which you don't yet have the library, you should go to its
//rev page and look for the library for it.
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.MecanumDrive;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.openftc.apriltag.AprilTagDetection;
//import org.openftc.easyopencv.OpenCvCamera;
//import org.openftc.easyopencv.OpenCvCameraFactory;
//import org.openftc.easyopencv.OpenCvCameraRotation;
//import org.openftc.easyopencv.OpenCvInternalCamera;


import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;


//this line sets up how the program will be displayed on the driver station
//@TeleOp makes it a teleop program (if it was autonomous it would be @Auto )
//name="Drive2" sets the name that shows up on the driver station
//grou="Linear Opmode" helps organize into groups but I don't think it matters tbh
@TeleOp(name="Drive2", group="Linear Opmode")

//this is just standard java class initialization 
//but the extends is because the class comes from another class?? think clones
//or something, you don't need to understand 'extends' just be aware of it
public class Drive2 extends LinearOpMode {
    // this is were you first declare what stuff you're using on the program
    // like the motors, sensors, even this runtime thing below that I'll explain 
    // later, for now just know it's a cronometer that starts running the moment
    // you start the program
    //
    // note how each component is declared as any variable, but instead of things
    // like 'int', 'float' or 'boolean' you have the specific kind of component it
    // is, such as 'DcMotor', 'Servo', 'ColorSensor'. these keywords aren't in java
    // by default, that's why we import that bunch of libraries on top.
    //
    // note how all the components are given the value "null"
    // this will be important later :)
    private ElapsedTime runtime = new ElapsedTime();


    private DcMotor frontLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor backRightDrive = null;
    private DcMotor rightSlide  = null;
    private DcMotor leftSlide = null;

    private ColorSensor color_sensor;

    private Servo mainArm = null;
    private Servo topArm = null;
    private Servo bottomArm = null;
    private Servo topClaw = null;
    private Servo bottomClaw = null;
    private Servo rack = null;


   //after you're done declaring the physical components, you can declare all the
   //variables that you think you'll need. all of these I've added for convenience,
   //though some of these may be obsolete. don't erase any of these until you're sure
   //they don't appear anywhere else in the code. a good trick for this is pressing
   //CTRL+F and looking up the variable name, it'll tell you how many instances of the
   //name are there in the code.

    public boolean gripperAngleUp;
    public boolean gripperClosed;

    public double slidesPower;
    public double armPower;

    ElapsedTime time = new ElapsedTime();

    public int phaseOneProgress=0;
    public int phaseTwoProgress=0;
    public int phaseThreeProgress=0;
    public int phaseFourProgress=0;
    public int specimenPickupProgress=0;
    public int specimenIntakeReadyProgress=0;
    public boolean pressedYBefore=false;
    public boolean pressedBBefore=false;
    public boolean pressedABefore=false;
    public boolean pressedXBefore=false;
    public boolean pressedLBBefore=false;
    public boolean pressedRBBefore=false;

    public boolean  slidesInUse = false;

    public boolean isDrivingInverted=false;
    public boolean GP1RBPressedBefore;

    public double wakeTime;


    //after we're done with the OpMode components, we can move on to the next phase.
    //I have no idea what @Override does, but it is not important. just leave the line
    //there for your and the team's safety.
    @Override

    //this following line is the function that runs when you press "INIT" in the
    //Driver Station. it will do everything below this function and then stop
    //right before the waitForStart() line.
    public void runOpMode() {

        //there's a bunch going on in this block so i'll make it simple.
        //remember the part where you declare the components earlier?
        //they were given the value "null" because they weren't in use yet
        //
        //now, you give them a value. the value you're giving them is the 
        //name that you have given them in the Driver Station "configure robot" page
        //you use hardwareMap because that's the thing where the info is stored
        //get() is the function to gather the data. 
        //DcMotor is the type of variable you're making the program look for
        //class is uhhhhhhhh don't worry about it
        //then you type the name, since it's a string of text it goes enclosed in
        //quoation marks.

        telemetry.addData("Status", "Initialized");
        telemetry.update();//btw this is how you print on the driver station


        // Initialize thep hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        frontLeftDrive  = hardwareMap.get(DcMotor.class, "Front Left");
        frontRightDrive = hardwareMap.get(DcMotor.class, "Front Right");
        backLeftDrive  = hardwareMap.get(DcMotor.class, "Back Left");
        backRightDrive = hardwareMap.get(DcMotor.class, "Back Right");

        mainArm = hardwareMap.get(Servo.class, "Arm");
        topArm = hardwareMap.get(Servo.class, "Top Arm");
        bottomArm = hardwareMap.get(Servo.class, "Bottom Arm");

        rightSlide = hardwareMap.get(DcMotor.class, "Right Slide");
        //leftSlide = hardwareMap.get(DcMotor.class, "Left Slide");

        topClaw = hardwareMap.get(Servo.class, "Top Claw");
        bottomClaw = hardwareMap.get(Servo.class, "Bottom Claw");
        rack = hardwareMap.get(Servo.class, "Rack");

        //color_sensor = hardwareMap.get(ColorSensor.class, "color_sensor");

        //distance = hardwareMap.get(DistanceSensor.class, "sensor_distance");

        
        //this part is a little weird but it's for reversing the rotation of
        //the motor. this is important because motors like the wheels need to
        //spin in a specific pattern for the motion like strafing or rotating.
        //therefore (go random fancy word), some motors are reversed, so that
        //positive power makes them go forward instead of backwards.

        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);

        //rack.setDirection(Servo.Direction.REVERSE);

        //this was for resetting the encoders, but it is useless now since we don't
        //use wheel encoders anymore. we now have the odometry pods

        //frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // armDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //armDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //leftSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //leftSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //armDrive.setDirection(DcMotor.Direction.FORWARD);
        //armSlide.setDirection(DcMotor.Direction.FORWARD);


        // armDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //armSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        //this one is for setting up what you want the motor to do when you set
        //the power to zero, if you want the motor to stop and retain that position, 
        //you type BRAKE like this line below, but if you want the motor to be free
        //you use COAST, i think? look up "zeropowerbehavior ftc" on google for the
        //2nd option, for I have forgotten it :(
        rightSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //leftSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");  //
        telemetry.update();


        //frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        //telemetry.addData("Path0",  "Starting at %7d", armDrive.getCurrentPosition());
        telemetry.update();


        boolean gripperPreviouslyPressed = false;
        boolean gripperAnglePreviouslyPressed = false;


        //so this is where I said the program would stop before you press the triangle
        //"PLAY" button on the driver station.
        waitForStart();
        //the driver now has pressed start, it'll go line by line and then start
        //the loop on "while (opModeIsActive()) {"
        runtime.reset(); //this starts the aforementioned cronometer

        mainArm.setPosition(1.0);//this is how you move a servo. they're all coded
        topArm.setPosition(0.5);//with a range between 1 and 0
        topClaw.setPosition(0.4);


        //boolean gripperAngleButtonOn = false;
        //boolean gripperButtonOn = false;

       /*gripperAngle.setPosition(0.345);//Quick fix cuz uhhhhh
       gripper.setPosition(0.95); //close
       gripperClosed=false;
       gripperAngleUp=true;*/

        //this is the main LOOP, it's a block of code that wll run once and again until
        //the end of time, i mean, until the driver stops the program.
        while (opModeIsActive()) {

            //this whole thing is for the driving direction switch, it just makes the wheel's
            //power negative when isDrivingInverted is set to true (you guessed it, a boolean)
            if (isDrivingInverted) {
                drive.setDrivePowers(new PoseVelocity2d(
                        new Vector2d(
                                gamepad1.left_stick_y,
                                gamepad1.left_stick_x
                        ),
                        -gamepad1.right_stick_x
                ));
            } else {
                drive.setDrivePowers(new PoseVelocity2d(
                        new Vector2d(
                                -gamepad1.left_stick_y,
                                -gamepad1.left_stick_x
                        ),
                        -gamepad1.right_stick_x
                ));}

            /*if (gamepad1.right_bumper) {
                isDrivingInverted=false;
            }
            if (gamepad1.left_bumper) {
                isDrivingInverted=true;
            }*/

            if (gamepad1.right_bumper) {
                if (!GP1RBPressedBefore){
                    GP1RBPressedBefore=true;
                    isDrivingInverted=!isDrivingInverted;
                }
            } else {
                GP1RBPressedBefore=false;
            }

            if (rightSlide.getCurrentPosition()<-2650) {
                slidesInUse = true;
            }else{
                slidesInUse=false;
            }

            slidesPower = -gamepad2.left_stick_y;
            if (!slidesInUse){
                rightSlide.setPower(slidesPower);
                //leftSlide.setPower(-slidesPower);
            }
            if (slidesInUse){
                rightSlide.setPower(slidesPower*0.4);
                //leftSlide.setPower(-slidesPower*0.4);
            }

            //sample pickup cycle
            if (phaseOneProgress<=2){
                if (gamepad2.y && !pressedYBefore){
                    mainArm.setPosition(0.65);
                    topClaw.setPosition(0.25);
                    topArm.setPosition(0.48);
                    rack.setPosition(1.0);
                    bottomClaw.setPosition(1.0);
                    bottomArm.setPosition(0.1);

                    pressedYBefore=true;
                    phaseOneProgress++;
                    wakeTime=runtime.seconds()+0.0;
                }
                if (runtime.seconds()>wakeTime && phaseOneProgress==1) {

                    //mainArm.setPosition(1.0); only for test
                    phaseOneProgress++;
                }
                if (phaseOneProgress==2) {phaseOneProgress=0;pressedYBefore=false;}
                //bottomArm.setPosition(0.05);
            }
            if (phaseTwoProgress<=2){
                if (gamepad2.b && !pressedBBefore)
                {   bottomClaw.setPosition(0.0);
                    bottomArm.setPosition(0);
                    pressedBBefore=true;
                    phaseTwoProgress++;
                    wakeTime=runtime.seconds()+0.5;}

                if (runtime.seconds()>wakeTime && phaseTwoProgress==1) {
                    bottomArm.setPosition(0.5);
                    phaseTwoProgress++;
                }
                if (phaseTwoProgress==2) {phaseTwoProgress=0;pressedBBefore=false;}
            }
            if (phaseThreeProgress<=2){
                if (gamepad2.a && !pressedABefore) {
                    rack.setPosition(0.0);

                    pressedABefore=true;
                    phaseThreeProgress++;
                    wakeTime=runtime.seconds()+1.2;
                }
                if (runtime.seconds()>wakeTime && phaseThreeProgress==1) {
                    bottomArm.setPosition(0.77);

                    phaseThreeProgress++;
                    wakeTime=runtime.seconds()+1.0;
                }
                if (runtime.seconds()>wakeTime && phaseThreeProgress==2) {
                    //bottomClaw.setPosition(1.0);

                    phaseThreeProgress=0;
                    pressedABefore=false;
                }
            }
            if (phaseFourProgress<=4){
                if (gamepad2.x && !pressedABefore) {
                    topArm.setPosition(0.5);

                    pressedABefore=true;
                    phaseFourProgress++;
                    wakeTime=runtime.seconds()+0.4;
                }
                if (runtime.seconds()>wakeTime && phaseFourProgress==1) {
                    topClaw.setPosition(0.0);

                    phaseFourProgress++;
                    wakeTime=runtime.seconds()+0.3;
                }
                if (runtime.seconds()>wakeTime && phaseFourProgress==2) {
                    /*slidesInUse=true;
                    rightSlide.setPower(1.0);
                    leftSlide.setPower(-1.0);*/
                    topArm.setPosition(0.48);
                    mainArm.setPosition(1.0);
                    bottomClaw.setPosition(1.0);

                    phaseFourProgress++;
                    wakeTime=runtime.seconds()+0.1;
                }
                if (runtime.seconds()>wakeTime && phaseFourProgress==3/* && leftSlide.getCurrentPosition()<=-300*/) {
                    /*rightSlide.setPower(0);
                    leftSlide.setPower(0);
                    slidesInUse=false;*/
                    topArm.setPosition(0.1);
                    mainArm.setPosition(0);

                    phaseFourProgress++;
                    wakeTime=runtime.seconds()+0.8;
                }
                if (runtime.seconds()>wakeTime && phaseFourProgress==4) {
                    topArm.setPosition(0.8);

                    phaseFourProgress=0;
                    pressedABefore=false;
                }
            }

            //drop sample
            if (gamepad2.right_stick_button) {
                topClaw.setPosition(0.3);
            }

            if (specimenIntakeReadyProgress<=2){
                if (gamepad2.right_bumper && !pressedRBBefore) {
                    topClaw.setPosition(0.2);

                    pressedRBBefore=true;
                    specimenIntakeReadyProgress++;
                    wakeTime=runtime.seconds()+0.3;
                }
                if (runtime.seconds()>wakeTime && specimenIntakeReadyProgress==1) {
                    topArm.setPosition(0);
                    mainArm.setPosition(1.0);

                    specimenIntakeReadyProgress++;
                    wakeTime=runtime.seconds()+1.0;
                }
                if (runtime.seconds()>wakeTime && specimenIntakeReadyProgress==2) {
                    topArm.setPosition(0.5);
                    topClaw.setPosition(0.4);

                    specimenIntakeReadyProgress=0;
                    pressedRBBefore=false;
                }
            }
            if (specimenPickupProgress<=3){
                if (gamepad2.left_bumper && !pressedLBBefore) {
                    topClaw.setPosition(0);

                    pressedLBBefore=true;
                    specimenPickupProgress++;
                    wakeTime=runtime.seconds()+0.4;
                }
                if (runtime.seconds()>wakeTime && specimenPickupProgress==1) {
                    topArm.setPosition(0);

                    specimenPickupProgress++;
                    wakeTime=runtime.seconds()+0.2;
                }
                if (runtime.seconds()>wakeTime && specimenPickupProgress==2) {
                    mainArm.setPosition(0.4);

                    specimenPickupProgress++;
                    wakeTime=runtime.seconds()+0.5;
                }
                if (runtime.seconds()>wakeTime && specimenPickupProgress==3) {
                    topArm.setPosition(1.0);

                    specimenPickupProgress=0;
                    pressedLBBefore=false;
                }
            }

            if (gamepad2.left_stick_button) { //level 1 ascent
                topArm.setPosition(0.65);
                mainArm.setPosition(0);
                topClaw.setPosition(0);
            }

            /*Arm Slide Control with le bumpahs
            if ((gamepad2.right_bumper == true)) {
                //Arm slide slides forth
                armSlideForth();
            }else{
                if ((gamepad2.left_bumper == true)) {
                    //Arm slide slides back
                    armSlideBack();
                }else{
                    if ((gamepad2.right_bumper == true)){
                        armUp();
                    }else{
                        armSlideStop();
                    }
                }
            }*/

            /*Slides up/down with D-Pad
            if (gamepad2.dpad_up) {
                slidesUp();
            }else{
                if (gamepad2.dpad_down) {
                    slidesDown();
                }else{
                    slidesStop();
                }}*/



            //armDrive.setPower(armPower*0.75);


                /*Gripper closes/opens with X
                if (gamepad2.x==true){
                    if (!gripperPreviouslyPressed) { gripperSwitch(); }
                    gripperPreviouslyPressed=true;
                } else { gripperPreviouslyPressed = false; }*/

                /*Gripper angle cycles up/down with A
                if (gamepad2.a==true){
                    if (!gripperAnglePreviouslyPressed) { gripperAngleSwitch(); }
                    gripperAnglePreviouslyPressed=true;
                } else { gripperAnglePreviouslyPressed = false; }*/

                /*Gripper Angle Cycle Through Three Things
                      if (gamepad2.y) {
                    gripperAnglePos(0);
                }else if (gamepad2.b) {
                    gripperAnglePos(1);
                }else if (gamepad2.a) {
                    gripperAnglePos(2);
                }*/
//telemetry.
            //Display Information
            //telemetry.addData("Path0",  "runtime is %7d",runtime.seconds());
            telemetry.addData("elapsedtime", "%.3f", runtime.seconds());
            telemetry.addData("waketime", "%.3f", wakeTime);
            telemetry.addLine(String.valueOf(wakeTime));
            telemetry.addData("Path0",  "slide at at %7d",rightSlide.getCurrentPosition());
            //telemetry.addData("Path0",  "Front Left at %7d",frontLeftDrive.getCurrentPosition());
            //telemetry.addData("Path0",  "Back Right at %7d",backRightDrive.getCurrentPosition());
            //telemetry.addData("Path0",  "Back Left at %7d",backLeftDrive.getCurrentPosition());
            // telemetry.addData("Path0",  "Left sticc at %7d", gamepad2.left_stick_y);
            //telemetry.addData("Path0",  "ls at %7d",gamepad2.left_stick_y);
            telemetry.addData("bottomarm at ", String.format("position=%.2f", bottomArm.getPosition()));
            //telemetry.addLine("Distance Sensor at "+distance.getDistance(DistanceUnit.CM));
            telemetry.addLine("welcome to the future ");
            telemetry.update();


            //armCurrentPosition=armDrive.getCurrentPosition();
        }
    }
}

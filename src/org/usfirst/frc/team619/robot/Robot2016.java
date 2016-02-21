/*
 * 
 *
 *
 * 
 * 
 */

package org.usfirst.frc.team619.robot;

import org.usfirst.frc.team619.hardware.CANTalon;
import org.usfirst.frc.team619.hardware.DualInputSolenoid;
import org.usfirst.frc.team619.hardware.LimitSwitch;
import org.usfirst.frc.team619.hardware.Talon;
import org.usfirst.frc.team619.logic.ThreadManager;
import org.usfirst.frc.team619.logic.mapping.RobotMappingThread;
import org.usfirst.frc.team619.logic.mapping.ShooterMappingThread;
import org.usfirst.frc.team619.logic.mapping.VisionThread;
import org.usfirst.frc.team619.subsystems.DriverStation;
import org.usfirst.frc.team619.subsystems.RobotShooter;
import org.usfirst.frc.team619.subsystems.Vision;
import org.usfirst.frc.team619.subsystems.drive.RobotDriveBase;
import org.usfirst.frc.team619.subsystems.sensor.SensorBase;

import edu.wpi.first.wpilibj.IterativeRobot;
<<<<<<< HEAD
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
=======
import edu.wpi.first.wpilibj.Timer;
>>>>>>> origin/master

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot2016 extends IterativeRobot {
    
	//declare all variables and objects here
	
	//Basics
	ThreadManager threadManager;
	DriverStation driverStation;
	
	//Logic
	VisionThread visionThread;
	RobotMappingThread driveThread;
	ShooterMappingThread shooterThread;
	
	//Subsystems
	SensorBase sensorBase;
	RobotDriveBase driveBase;
	RobotShooter robotShooter;
	Vision vision;
	
	//Hardware
	CANTalon leftMotor;
	CANTalon rightMotor;
	CANTalon leftMotor2;
	CANTalon rightMotor2;
<<<<<<< HEAD
	CANTalon dinkArm;
	CANTalon dankArm;
=======
	CANTalon shoot;
>>>>>>> origin/master
	
	LimitSwitch dankLimit;
	LimitSwitch dinkLimit;
	
	/*//For Linear Punch
	CANTalon liftMotor;
	CANTalon liftMotor2;
	CANTalon intake;
	
	DualInputSolenoid release;
	DualInputSolenoid switchMode;
	
	LimitSwitch frontLimit;
	LimitSwitch backLimit;
	LimitSwitch winchLimit;
	*/
	
	//For Flywheel
	CANTalon flyMotor;
	CANTalon flyMotor2;
	CANTalon kicker;
	CANTalon rotate;
	LimitSwitch kickLimit;

	//Control	
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	System.out.println("\n");// shows code is working
        System.out.println("//////////////////////////////////////////////////////");
        System.out.println("// Cavalier Robotics                     TEAM 619   //");
        System.out.println("// 2016 Robot                                       //");
        System.out.println("//////////////////////////////////////////////////////\n");
        
        //Create all robot subsystems (i.e. stuff from org.usfirst.frc.team619.subsystems)
        //If you are creating something not from org.usfirst.frc.team619.subsystems, YER DOING IT WRONG
        
        //threadManager
        threadManager = new ThreadManager();
        
        //driver station
        driverStation = new DriverStation(1, 2);
        
        //plug into pwm section on RoboRio
        
        //plug into DIO on RoboRio
        dankLimit = new LimitSwitch(1);
        dinkLimit = new LimitSwitch(2);
        kickLimit = new LimitSwitch(0);
        
        //plug into I2C on RoboRio
        
        //plug into Analog Input on RoboRio
        
        //plug into pneumatics module
        
        //CAN
<<<<<<< HEAD
        leftMotor = new CANTalon(2);
        leftMotor2 = new CANTalon(3);
        rightMotor = new CANTalon(0);
        rightMotor2 = new CANTalon(1);
        
        dinkArm = new CANTalon(4);
        dankArm = new CANTalon(5);
        flyMotor = new CANTalon(6);
        flyMotor2 = new CANTalon(7);
        kicker = new CANTalon(8);
        rotate = new CANTalon(9);
        
        //subsystems
        driveBase = new RobotDriveBase(leftMotor, rightMotor, leftMotor2, rightMotor2);
        robotShooter = new RobotShooter(dinkArm, dankArm, flyMotor, flyMotor2, kicker, //FlyWheel
        		rotate, dankLimit, dinkLimit, kickLimit);
        //ghengisShooter = new GhengisShooter(dinkArm, dankArm, liftMotor, liftMotor2, intake, //Linear Punch
        //		release, switchMode, frontLimit, backLimit, winchLimit);
        //ghengisShooter = new GhengisShooter(dinkArm, dankArm); //No shooting
        //ghengisShooter = new GhengisShooter(dinkArm, dankArm); //Flywheel, no movement
        sensorBase = new SensorBase();
        vision = new Vision();
=======
        leftMotor = new CANTalon(1);
        leftMotor2 = new CANTalon(2);
        rightMotor = new CANTalon(3);
        rightMotor2 = new CANTalon(4);
        shoot = new CANTalon(5);
        
        //subsystems
        driveBase = new RobotDriveBase(leftMotor, rightMotor, leftMotor2, rightMotor2, shoot);
        
>>>>>>> origin/master
    }

    /**
     *This function is called when autonomous is initialized
     */
    public void autonomousInit(){
    	threadManager.killAllThreads(); // DO NOT EVER REMOVE!!!
    }
    /**
     * This function is called when teleop is initialized
     */
    public void teleopInit(){
    	threadManager.killAllThreads(); // DO NOT EVER REMOVE!!!
    	
    	driveThread = new RobotMappingThread(vision, driveBase, driverStation, 15, threadManager);
    	shooterThread = new ShooterMappingThread(vision, robotShooter, driverStation, 15, threadManager);
    	visionThread = new VisionThread(sensorBase, vision, 15, threadManager);
    	
    	//sensorBase.startCamera("cam0");
    	sensorBase.startCamera();
    	driveThread.start();
    	shooterThread.start();
    	visionThread.start();
    }
    
    public void teleopPeriodic() {
    	
		SmartDashboard.putNumber("Tote hue min", vision.getHueLow());
		SmartDashboard.putNumber("Tote hue max", vision.getHueHigh());
		SmartDashboard.putNumber("Tote sat min", vision.getSatLow());
		SmartDashboard.putNumber("Tote sat max", vision.getSatHigh());
		SmartDashboard.putNumber("Tote val min", vision.getValueLow());
		SmartDashboard.putNumber("Tote val max", vision.getValueHigh());    	
    }
    /**
     * This function is called periodically during autonomous
     * In general you shouldn't use this
     */
    public void autonomousPeriodic() {
    	driveBase.setLeftWheels(1);
    	driveBase.setRightWheels(1);
    		Timer.delay(5);
    	driveBase.stop();

    }
    /**
     * This function is called periodically during operator control
     * In general you shouldn't use this
     */
    /**
     * This function is called periodically during test mode
     * In general you shouldn't use this
     */
    public void testPeriodic() {
    
    }
    public void disabledPeriodic(){
    	
    }
    public void disabledInit(){
    	threadManager.killAllThreads(); // DO NOT EVER REMOVE!!!
    	
    	// Close the camera through NIVision. This is only used for vision processing
    	try {
    		sensorBase.closeCamera();
    	}catch (Exception e) {}
    }
}

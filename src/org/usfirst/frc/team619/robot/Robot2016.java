package org.usfirst.frc.team619.robot;

import org.usfirst.frc.team619.hardware.CANTalon;
import org.usfirst.frc.team619.hardware.LimitSwitch;
import org.usfirst.frc.team619.hardware.PCMCompressor;
import org.usfirst.frc.team619.hardware.Solenoid;
import org.usfirst.frc.team619.hardware.Talon;
import org.usfirst.frc.team619.logic.ThreadManager;
import org.usfirst.frc.team619.logic.actions.AutoShoot;
import org.usfirst.frc.team619.logic.actions.AutoTest;
import org.usfirst.frc.team619.logic.mapping.CalibrationThread;
import org.usfirst.frc.team619.logic.mapping.RobotMappingThread;
import org.usfirst.frc.team619.logic.mapping.ShooterMappingThread;
import org.usfirst.frc.team619.logic.mapping.VisionThread;
import org.usfirst.frc.team619.subsystems.ClimberBase;
import org.usfirst.frc.team619.subsystems.DriverStation;
import org.usfirst.frc.team619.subsystems.RobotShooter;
import org.usfirst.frc.team619.subsystems.Vision;
import org.usfirst.frc.team619.subsystems.drive.RobotDriveBase;
import org.usfirst.frc.team619.subsystems.sensor.SensorBase;

import com.kauailabs.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SerialPort;

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
	CalibrationThread calibration;
	
	//Subsystems
	SensorBase sensorBase;
	RobotDriveBase driveBase;
	RobotShooter robotShooter;
	ClimberBase climbBase;
	Vision vision;
	
	//Autonomous
	AutoTest auto;
	AutoShoot autoShoot;
	
	//Hardware
	CANTalon leftMotor;
	CANTalon rightMotor;
	CANTalon leftMotor2;
	CANTalon rightMotor2;
	CANTalon dinkArm;
	CANTalon dankArm;
	CANTalon shoot;
	
	LimitSwitch dankLimit;
	LimitSwitch dinkLimit;
	
	//For Flywheel
	CANTalon flyMotor;
	CANTalon flyMotor2;
	CANTalon kicker;
	CANTalon rotate;
	LimitSwitch kickLimit;
	
	//Climber
	CANTalon winch;
	CANTalon winch2;
	Solenoid climberSol;
	Solenoid climberReset;

	//Control
	SerialPort port;
	IMUAdvanced imu;
	PCMCompressor compress;
	
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
        
        //plug into I2C on Robo		Rio
        
        //plug into Analog Input on RoboRio
        
        //plug into pneumatics module
    	compress = new PCMCompressor(1);
        climberSol = new Solenoid(1);
        climberReset = new Solenoid(0);
        
        //serial port
		try {
			port = new SerialPort(57600,SerialPort.Port.kMXP);
            imu = new IMUAdvanced(port);
		} catch(Exception ex) { }
        
        //CAN
		leftMotor = new CANTalon(6);
		leftMotor2 = new CANTalon(7);
		rightMotor = new CANTalon(8);
		rightMotor2 = new CANTalon(9);
        
		dinkArm = new CANTalon(10);
		dankArm = new CANTalon(11);
		rotate = new CANTalon(0);
		winch = new CANTalon(1);
		winch2 = new CANTalon(2);
		flyMotor = new CANTalon(3);
		flyMotor2 = new CANTalon(4);
		kicker = new CANTalon(5);
        
        //subsystems
        driveBase = new RobotDriveBase(leftMotor, rightMotor, leftMotor2, rightMotor2, imu);
        robotShooter = new RobotShooter(dinkArm, dankArm, flyMotor, flyMotor2, 
        		kicker, rotate, dankLimit, dinkLimit, kickLimit);
        climbBase = new ClimberBase(winch, winch2, climberSol, climberReset);
        sensorBase = new SensorBase();
        vision = new Vision();
        
        compress.start();
        robotShooter.calibrate();
        rotate.setForwardSoftLimit(robotShooter.setLimit(90));
        rotate.setReverseSoftLimit(robotShooter.setLimit(-20));
        sensorBase.startVisionCamera();
    }

    /**
     *This function is called when autonomous is initialized
     */
    public void autonomousInit(){
    	threadManager.killAllThreads(); // DO NOT EVER REMOVE!!!
    	
    	auto = new AutoTest(driveBase, robotShooter);
    	auto.run();
    }
    /**
     * This function is called when teleop is initialized
     */
    public void teleopInit(){
    	threadManager.killAllThreads(); // DO NOT EVER REMOVE!!!
    	
    	driveThread = new RobotMappingThread(vision, climbBase, driveBase, driverStation, 0, threadManager);
    	shooterThread = new ShooterMappingThread(vision, robotShooter, driverStation, 0, threadManager);
    	visionThread = new VisionThread(sensorBase, vision, 0, threadManager);
    	//calibration = new CalibrationThread(vision, robotShooter, driverStation, 0, threadManager);
    	
    	//calibration.start();
    	driveThread.start();
    	shooterThread.start();
    	visionThread.start();
    }
    
    public void teleopPeriodic() {
    	
    }
    /**
     * This function is called periodically during autonomous
     * In general you shouldn't use this
     */
    public void autonomousPeriodic() {
    	
    }
    /**
     * This function is called periodically during operator control
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
    }
}

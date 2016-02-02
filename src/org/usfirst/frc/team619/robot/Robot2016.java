/*
 * 
 *
 *
 * 
 * 
 */

package org.usfirst.frc.team619.robot;

import org.usfirst.frc.team619.hardware.CANTalon;
import org.usfirst.frc.team619.logic.ThreadManager;
import org.usfirst.frc.team619.logic.mapping.RobotMappingThread;
import org.usfirst.frc.team619.subsystems.DriverStation;
import org.usfirst.frc.team619.subsystems.drive.RobotDriveBase;

import edu.wpi.first.wpilibj.IterativeRobot;

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
	RobotMappingThread driveThread;
	
	//Subsystems
	RobotDriveBase driveBase;
	
	//Hardware
	CANTalon leftMotor;
	CANTalon rightMotor;
	CANTalon leftMotor2;
	CANTalon rightMotor2;
	
	//Control
	
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	System.out.println("\n");// shows code is working
        System.out.println("//////////////////////////////////////////////////////");
        System.out.println("// Cavalier Robotics                     TEAM 619   //");
        System.out.println("// 2015 Robot                                       //");
        System.out.println("//////////////////////////////////////////////////////\n");
        
        //Create all robot subsystems (i.e. stuff from org.usfirst.frc.team619.subsystems)
        //If you are creating something not from org.usfirst.frc.team619.subsystems, YER DOING IT WRONG
        
        //threadManager
        threadManager = new ThreadManager();
        
        //driver station
        driverStation = new DriverStation(1, 2);
        
        //plug into pwm section on RoboRio
        
        //plug into DIO on RoboRio
        
        //plug into I2C on RoboRio
        
        //plug into Analog Input on RoboRio
        
        //plug into pneumatics module
        
        //CAN
        leftMotor = new CANTalon(1);
        leftMotor2 = new CANTalon(2);
        rightMotor = new CANTalon(3);
        rightMotor2 = new CANTalon(4);
        
        //subsystems
        driveBase = new RobotDriveBase(leftMotor, rightMotor, leftMotor2, rightMotor2);
        
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
    	
    	driveThread = new RobotMappingThread(driveBase, driverStation, 0, threadManager);
    	
    	driveThread.start();
    }
    /**
     * This function is called periodically during autonomous
     * In general you shouldn't use this
     */
    public void autonomousPeriodic() {

    }
    /**
     * This function is called periodically during operator control
     * In general you shouldn't use this
     */
    public void teleopPeriodic() {
    	
        
    }
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

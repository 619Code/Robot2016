package org.usfirst.frc.team619.logic.mapping;

import org.usfirst.frc.team619.hardware.Joystick;
import org.usfirst.frc.team619.logic.RobotThread;
import org.usfirst.frc.team619.logic.ThreadManager;
import org.usfirst.frc.team619.subsystems.DriverStation;
import org.usfirst.frc.team619.subsystems.RobotShooter;
import org.usfirst.frc.team619.subsystems.Vision;
import org.usfirst.frc.team619.subsystems.sensor.SensorBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterMappingThread extends RobotThread {
	
	protected DriverStation driverStation;
	protected RobotShooter robotShooter;
	protected SensorBase sensorBase;
	protected Vision vision;
	
	private double scalePercent;
	private double angle;
	private boolean releasedSpeed;
	
	public ShooterMappingThread(Vision vision, RobotShooter robotShooter,
			DriverStation driverStation, int period, ThreadManager threadManager) {
		super(period, threadManager);
		this.driverStation = driverStation;
		this.robotShooter = robotShooter;
		this.vision = vision;
		scalePercent = 0.5;
		releasedSpeed = true;
	}

	protected void cycle() { //Should generally use shooter controller
		//Set Dink & Dank speed
		switch(driverStation.getLeftJoystick().getPOV()) {
		case -1: 
			releasedSpeed = true;
			break;
		case 45:
		case 315:
		case 0:
			if(releasedSpeed && scalePercent < 1.0) {
				scalePercent += 0.1;
			}
			releasedSpeed = false;
			break;
		case 135:
		case 225:
		case 180:
			if(releasedSpeed && scalePercent > 0.2) {
				scalePercent -= 0.1;
			}
			releasedSpeed = false;
			break;
		default:
			break;
		}
		
		double liftPercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.LEFT_AXIS_Y) * (-scalePercent); //Dink & Dank
		double rotatePercent  = driverStation.getLeftJoystick().getAxis(Joystick.Axis.RIGHT_AXIS_Y) * scalePercent; //Shooter
		SmartDashboard.putNumber("Arm Scale Percent", scalePercent);
		
		//Dink and Dank arms
		if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON4)) {
			if(robotShooter.getDinkAngle() > 10) {
				robotShooter.setDankArm(-0.5);
				robotShooter.setDinkArm(-0.5);
			}
		}else if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON9)) {
			if(robotShooter.getDinkAngle() < 140) {
				robotShooter.setDankArm(0.5);
				robotShooter.setDankArm(0.5);
			}
		}else {
			robotShooter.setDankArm(liftPercent);
			robotShooter.setDinkArm(liftPercent);
		}
		SmartDashboard.putNumber("Dink Angle", robotShooter.getDinkAngle());
		
		//Intake
		if(driverStation.getLeftJoystick().getAxis(Joystick.Axis.LEFT_TRIGGER) > 0)
			robotShooter.setFlyWheel(driverStation.getLeftJoystick().getAxis(Joystick.Axis.LEFT_TRIGGER));
		else
			robotShooter.setFlyWheel(-driverStation.getLeftJoystick().getAxis(Joystick.Axis.RIGHT_TRIGGER));
		
		
		//Manual kicker set and reset
		if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON1)) {
			robotShooter.kick();
		}else if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON6)) {
			robotShooter.resetKick();
		}else {
			robotShooter.stopKick();
		}
		
		//Control the shooter angle using either buttons or joystick
		if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON2)) {
			double distance = (vision.getLinearDistance() * 12) - vision.xOffset;
			
			angle = Math.atan((vision.totalHeight + vision.getCompensation()) / distance) * (180 / Math.PI);
			robotShooter.setAngle(angle);
			SmartDashboard.putNumber("Angle", angle);
		}else if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON3)){
			robotShooter.setAngle(90);
			SmartDashboard.putNumber("Angle", 90);
		}else if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON4)){
			robotShooter.setAngle(-10);
			SmartDashboard.putNumber("Angle", -10);
		}else if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON7))
			robotShooter.setAngle(50);
		else {
			robotShooter.setRotate(rotatePercent);
			SmartDashboard.putNumber("Angle", robotShooter.getAngle());
		}
		SmartDashboard.putNumber("Current Angle", robotShooter.getAngle());
		
		//Sets the current shooter and arm angle as 0
		if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON9)) {
			robotShooter.calibrate();
		}
	}
}

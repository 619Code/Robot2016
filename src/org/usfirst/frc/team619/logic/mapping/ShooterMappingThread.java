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
	private boolean kick;
	private boolean aim;
	
	public ShooterMappingThread(Vision vision, RobotShooter robotShooter,
			DriverStation driverStation, int period, ThreadManager threadManager) {
		super(period, threadManager);
		this.driverStation = driverStation;
		this.robotShooter = robotShooter;
		this.vision = vision;
		scalePercent = 0.5;
		releasedSpeed = true;
		kick = false;
		aim = false;
	}

	protected void cycle() { //Should generally use shooter controller
		//Set Dink & Dank speed
		switch(driverStation.getLeftJoystick().getPOV()) {
		default:
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
		case -1: 
			releasedSpeed = true;
			break;
		}
		
		double liftPercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.LEFT_AXIS_Y) * scalePercent; //Dink & Dank
		double rotatePercent  = driverStation.getLeftJoystick().getAxis(Joystick.Axis.RIGHT_AXIS_Y) * (scalePercent); //Shooter
		SmartDashboard.putNumber("Arm Scale Percent", scalePercent);
		
		//Dink and Dank arms
		//robotShooter.setDankArm(liftPercent);
		//robotShooter.setDinkArm(liftPercent);
		
		//Sets the current angle as 90
		if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON9)) {
			robotShooter.calibrate();
		}
		
		//Intake
		if(driverStation.getLeftJoystick().getAxis(Joystick.Axis.LEFT_TRIGGER) > 0) {
			robotShooter.setFlyWheel(driverStation.getLeftJoystick().getAxis(Joystick.Axis.LEFT_TRIGGER));
		}else {
			robotShooter.setFlyWheel(-driverStation.getLeftJoystick().getAxis(Joystick.Axis.RIGHT_TRIGGER));
		}
		
		//Manual kicker set and reset
		if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON1)) {
			robotShooter.kick();
		}else if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON6)) {
			robotShooter.resetKick();
		}else {
			robotShooter.stopKick();
		}

		if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON2)) {
			double distance = (vision.getLinearDistance() * 12) - vision.xOffset;
			
			angle = Math.atan((vision.totalHeight + vision.getCompensation()) / distance) * (180 / Math.PI);
			robotShooter.setAngle(angle);
			SmartDashboard.putNumber("Angle", angle);
		}else if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON3)){
			robotShooter.setAngle(90);
			SmartDashboard.putNumber("Angle", 90);
		}else {
			robotShooter.setRotate(rotatePercent);
			SmartDashboard.putNumber("Angle", robotShooter.getAngle());
		}
		SmartDashboard.putBoolean("On?", robotShooter.isKickLimit());
//		//Manual reset of kicker - hold LB and press shoot to move
//		if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON5)) {
//			aim = false;
//			kick = false;
//			if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON6)) {
//				robotShooter.resetKick();
//			}else {
//				robotShooter.stopKick();
//			}
//		}else if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON1)) {
//			kick = true;
//		}
//		if(kick) { //Kick and reset after button press
//			kick = robotShooter.shoot();
//		}
//		
//		//Driver Controller -- Same button as auto aim
//		if(driverStation.getRightJoystick().getButton(Joystick.Button.BUTTON1)) {
//			aim = true;
//		}
//		if(aim) {
//			angle = Math.atan((vision.castleHeight) / ((SmartDashboard.getNumber("Distance")) * 12)) * (180 / Math.PI);
//			SmartDashboard.putNumber("Desired Angle", angle);
//			aim = robotShooter.setAngle(angle);
//		}else if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON3)) { //Set shooter vertical
//			while(robotShooter.getRotate().getEncPosition() <= robotShooter.getRotate().getForwardSoftLimit()) {
//				robotShooter.setRotate(1);
//			}
//			aim = false;
//		}else {
//			robotShooter.setRotate(rotatePercent);
//		}		
//		angle = Math.atan((vision.castleHeight - vision.yOffset) / (vision.getDistance() * 12)) * (180 / Math.PI);
//		SmartDashboard.putNumber("Desired Angle", angle);
	}
}

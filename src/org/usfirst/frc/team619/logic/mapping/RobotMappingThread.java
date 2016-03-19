package org.usfirst.frc.team619.logic.mapping;

import org.usfirst.frc.team619.hardware.Joystick;
import org.usfirst.frc.team619.logic.RobotThread;
import org.usfirst.frc.team619.logic.ThreadManager;
import org.usfirst.frc.team619.subsystems.ClimberBase;
import org.usfirst.frc.team619.subsystems.DriverStation;
import org.usfirst.frc.team619.subsystems.RobotShooter;
import org.usfirst.frc.team619.subsystems.Vision;
import org.usfirst.frc.team619.subsystems.drive.RobotDriveBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotMappingThread extends RobotThread {
	
	protected DriverStation driverStation;
	protected RobotDriveBase driveBase;
	protected RobotShooter robotShooter;
	protected ClimberBase climbBase;
	protected Vision vision;
	
	private double leftScalePercent;
	private boolean releasedSpeed;
	private boolean releasedCam;
	
	public RobotMappingThread(Vision vision, ClimberBase climbBase, RobotDriveBase driveBase, DriverStation driverStation, int period, ThreadManager threadManager) {
		super(period, threadManager);
		this.driverStation = driverStation;
		this.driveBase = driveBase;
		this.climbBase = climbBase;
		this.vision = vision;
		leftScalePercent = 0.5;
		releasedSpeed = true;
	}
	
	protected void cycle() { //Should generally use driver controller
		//Change scale percent
		switch(driverStation.getRightJoystick().getPOV()) {
		default:
			break;
		case 45:
		case 315:
		case 0:
			if(releasedSpeed && leftScalePercent < 1.0) {
				leftScalePercent += 0.1;
			}
			releasedSpeed = false;
			break;
		case 135:
		case 225:
		case 180:
			if(releasedSpeed && leftScalePercent > 0.2) {
				leftScalePercent -= 0.1;
			}
			releasedSpeed = false;
			break;
		case -1: 
			releasedSpeed = true;
			break;
		}
		
		SmartDashboard.putNumber("Scale Percent", leftScalePercent);
		double leftPercent = driverStation.getRightJoystick().getAxis(Joystick.Axis.LEFT_AXIS_Y) * (leftScalePercent); //Left Wheels
		double rightPercent = driverStation.getRightJoystick().getAxis(Joystick.Axis.RIGHT_AXIS_Y) * (leftScalePercent); //Right Wheels
		
		//Do a 180
//		if(driverStation.getRightJoystick().getButton(Joystick.Button.BUTTON3)) {
//			double currentAngle = driveBase.getAngle();
//			double angle = currentAngle + 170;
//			
//			while(currentAngle < angle) {
//				if(driverStation.getRightJoystick().getButton(Joystick.Button.BUTTON2)) break;
//				currentAngle = driveBase.getAngle();
//				driveBase.setLeftWheels(leftPercent);
//				driveBase.setRightWheels(-rightPercent);
//			}
//			driveBase.stop();
//		}else
		if(driverStation.getRightJoystick().getButton(Joystick.Button.BUTTON1)) {
			driveBase.talonAim(vision.center(), leftScalePercent);
		}else {
			driveBase.setLeftTalons(leftPercent);
			driveBase.setRightTalons(rightPercent);
		}
		
		//Climber Solenoids
		if(driverStation.getRightJoystick().getButton(Joystick.Button.BUTTON5)) {
			climbBase.fireClimber();
		}else {
			climbBase.idleClimber();
		}
		if(driverStation.getRightJoystick().getButton(Joystick.Button.BUTTON6)) {
			climbBase.moveClimber();
		}else {
			climbBase.stopClimber();
		}
	}
}
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
	private double angle;
	private boolean releasedSpeed;
	private boolean releasedTurn;
	private boolean aim;
	private boolean fired;
	
	public RobotMappingThread(Vision vision, ClimberBase climbBase, RobotDriveBase driveBase, DriverStation driverStation, int period, ThreadManager threadManager) {
		super(period, threadManager);
		this.driverStation = driverStation;
		this.driveBase = driveBase;
		this.climbBase = climbBase;
		this.vision = vision;
		leftScalePercent = 0.5;
		releasedSpeed = true;
		releasedTurn = true;
		aim = false;
		fired = false;
	}
	
	protected void cycle() { //Should generally use driver controller
		//Change scale percent
		switch(driverStation.getRightJoystick().getPOV()) {
		case -1: 
			releasedSpeed = true;
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
		default:
			break;
		}
		
		SmartDashboard.putNumber("Scale Percent", leftScalePercent);
		double leftPercent = driverStation.getRightJoystick().getAxis(Joystick.Axis.LEFT_AXIS_Y) * (leftScalePercent); //Left Wheels
		double rightPercent = driverStation.getRightJoystick().getAxis(Joystick.Axis.RIGHT_AXIS_Y) * (leftScalePercent); //Right Wheels
		
		//Actuate winch
		double leftWinch = driverStation.getRightJoystick().getAxis(Joystick.Axis.LEFT_TRIGGER);
		double rightWinch = driverStation.getRightJoystick().getAxis(Joystick.Axis.RIGHT_TRIGGER);
		
		//Do a 180 (Needs work)
//		if(driverStation.getRightJoystick().getButton(Joystick.Button.BUTTON3)) {
//			double currentAngle = driveBase.getAngle();
//			if(releasedTurn) {
//				angle = currentAngle + 140;
//				releasedTurn = false;
//			}
//			
//			while(currentAngle < angle) {
//				if(driverStation.getRightJoystick().getButton(Joystick.Button.BUTTON2))
//					break;
//				currentAngle = driveBase.getAngle();
//				if(angle > 360)
//					if(currentAngle <= 140)
//						currentAngle += 360;
//				driveBase.setLeftWheels(-0.75);
//				driveBase.setRightWheels(0.75);
//			}
//			driveBase.setLeftWheels(1);
//			driveBase.setRightWheels(-1);
//		}else 
		if(driverStation.getRightJoystick().getButton(Joystick.Button.BUTTON1)) {
			driveBase.aim(vision.center(), leftScalePercent);
		}else {
			driveBase.setLeftWheels(leftPercent);
			driveBase.setRightWheels(rightPercent);
			releasedTurn = true;
		}
		if(Math.abs(vision.center() - 320) < 5)
			aim = true;
		else
			aim = false;
		SmartDashboard.putBoolean("Ready to shoot?", aim);
		
		//Fire pneumatics
		if(driverStation.getRightJoystick().getButton(Joystick.Button.BUTTON4)) {
			climbBase.extendClimber();
			fired = true;
		}else if(!fired)
			climbBase.resetClimber();
		else
			climbBase.stopClimber();
		
		//Set winch speed
		if(rightWinch > 0.2)
			climbBase.setWinch(rightWinch);
		else
			climbBase.setWinch(0);
	}
}
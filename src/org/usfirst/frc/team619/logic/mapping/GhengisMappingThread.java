package org.usfirst.frc.team619.logic.mapping;

import org.usfirst.frc.team619.hardware.Joystick;
import org.usfirst.frc.team619.logic.RobotThread;
import org.usfirst.frc.team619.logic.ThreadManager;
import org.usfirst.frc.team619.subsystems.DriverStation;
import org.usfirst.frc.team619.subsystems.GhengisShooter;
import org.usfirst.frc.team619.subsystems.Vision;
import org.usfirst.frc.team619.subsystems.drive.GhengisDriveBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GhengisMappingThread extends RobotThread {
	
	protected DriverStation driverStation;
	protected GhengisDriveBase driveBase;
	protected GhengisShooter ghengisShooter;
	protected Vision vision;
	
	private double center;
	private double leftScalePercent;
	private boolean isHue = true;
	private boolean isSat = false;
	private boolean isVal = false;
	private boolean released = true;
	private boolean released1 = true;
	private boolean releasedSpeed = true;
	
	public GhengisMappingThread(Vision vision, GhengisDriveBase driveBase, DriverStation driverStation, int period, ThreadManager threadManager) {
		super(period, threadManager);
		this.vision = vision;
		this.driverStation = driverStation;
		this.driveBase = driveBase;
		leftScalePercent = 0.5;
	}
	
	protected void cycle() {
		
		//Change scale percent
		switch(driverStation.getRightJoystick().getPOV()) {
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
		default:
			break;
		}
		
		SmartDashboard.putNumber("Scale Percent", leftScalePercent);
		double leftPercent = driverStation.getRightJoystick().getAxis(Joystick.Axis.LEFT_AXIS_Y) * leftScalePercent;
		double rightPercent = driverStation.getRightJoystick().getAxis(Joystick.Axis.RIGHT_AXIS_Y) * leftScalePercent;
		center = SmartDashboard.getNumber("Center");
		
		//Print target info
		if(center == -1) {
			SmartDashboard.putString("WARNING", "NO TARGET FOUND");
		}else {
			SmartDashboard.putString("WARNING", "TARGET AT " + center);
		}
		
		//Auto aiming and driving
		if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON1) && !(center == -1)) {
			driveBase.aim(center);
		}else {
			driveBase.setLeftWheels(leftPercent);
			driveBase.setRightWheels(rightPercent);
		}
		
		//Used for calibration of reflective tape
		if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON3)) { //Default
			isHue = true;
			isSat = false;
			isVal = false;
		}if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON4)) {
			isHue = false;
			isSat = true;
			isVal = false;
		}if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON2)) {
			isHue = false;
			isSat = false;
			isVal = true;
		}
		SmartDashboard.putBoolean("Edit Hue", isHue);
		SmartDashboard.putBoolean("Edit Sat", isSat);
		SmartDashboard.putBoolean("Edit Value", isVal);

		if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON8)) { //Edit vision values
			switch(driverStation.getRightJoystick().getPOV()) {
			case 45:
			case 315:
			case 0:
				if(released && isHue && vision.getHueHigh() < 255) {
					vision.setHueHigh(vision.getHueHigh()+5);
				}
				if(released && isSat && vision.getSatHigh() < 255) {
					vision.setSatHigh(vision.getSatHigh()+5);
				}
				if(released && isVal && vision.getValueHigh() < 255) {
					vision.setValueHigh(vision.getValueHigh()+5);
				}
				released = false;
				break;
			case 135:
			case 225:
			case 180:
				if(released && isHue && vision.getHueHigh() > 0) {
					vision.setHueHigh(vision.getHueHigh()-5);
				}
				if(released && isSat && vision.getSatHigh() > 0) {
					vision.setSatHigh(vision.getSatHigh()-5);
				}
				if(released && isVal && vision.getValueHigh() > 0) {
					vision.setValueHigh(vision.getValueHigh()-5);
				}
				released = false;
				break;
			case -1: 
				released = true;
				break;
			default:
				break;
			}
		}
		
		if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON7)) { //Edit vision values
			switch(driverStation.getRightJoystick().getPOV()) {
			case 45:
			case 315:
			case 0:
				if(released1 && isHue && vision.getHueLow() < 255) {
					vision.setHueLow(vision.getHueLow()+5);
				}
				if(released1 && isSat && vision.getSatLow() < 255) {
					vision.setSatLow(vision.getSatLow()+5);
				}
				if(released1 && isVal && vision.getValueLow() < 255) {
					vision.setValueLow(vision.getValueLow()+5);
				}
				released1 = false;
				break;
			case 135:
			case 225:
			case 180:
				if(released1 && isHue && vision.getHueLow() > 0) {
					vision.setHueLow(vision.getHueLow()-5);
				}
				if(released1 && isSat && vision.getSatLow() > 0) {
					vision.setSatLow(vision.getSatLow()-5);
				}
				if(released1 && isVal && vision.getValueLow() > 0) {
					vision.setValueLow(vision.getValueLow()-5);
				}
				released1 = false;
				break;
			case -1: 
				released1 = true;
				break;
			default:
				break;
			}
		}
		
	}

}

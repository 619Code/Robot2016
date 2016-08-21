package org.usfirst.frc.team619.logic.mapping;

import org.usfirst.frc.team619.hardware.Joystick;
import org.usfirst.frc.team619.logic.RobotThread;
import org.usfirst.frc.team619.logic.ThreadManager;
import org.usfirst.frc.team619.subsystems.DriverStation;
import org.usfirst.frc.team619.subsystems.RobotShooter;
import org.usfirst.frc.team619.subsystems.Vision;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CalibrationThread extends RobotThread {
	
	protected DriverStation driverStation;
	protected RobotShooter robotShooter;
	protected Vision vision;
	
	private boolean isHue;
	private boolean isSat;
	private boolean isVal;
	private boolean released;
	private boolean released1;
	private boolean releasedHSV;
	private String edit = "#######";
	private String hue;
	private String sat;
	private String val;
	
	public CalibrationThread(Vision vision, RobotShooter robotShooter, DriverStation driverStation, int period, ThreadManager threadManager) {
		super(period, threadManager);
		this.driverStation = driverStation;
		this.robotShooter = robotShooter;
		this.vision = vision;
		isHue = true;
		isSat = false;
		isVal = false;
		released = true;
		released1 = true;
		releasedHSV = true;
		hue = edit;
		sat = " ";
		val = " ";
	}

	protected void cycle() {
		//Used for calibration of reflective tape
		SmartDashboard.putString("Edit Hue?", hue);
		SmartDashboard.putString("Edit Sat?", sat);
		SmartDashboard.putString("Edit Value?", val);
		
		//Cycle HSV
		if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON10)) {
			if(isHue && releasedHSV) {
				isHue = false;
				isSat = true;
				hue  = " ";
				sat = edit;
			}else if(isSat && releasedHSV) {
				isSat = false;
				isVal = true;
				sat = " ";
				val = edit;
			}else if(isVal && releasedHSV) {
				isVal = false;
				isHue = true;
				val = " ";
				hue = edit;
			}
			releasedHSV = false;
		}else {
			releasedHSV = true;
		}
		
		//Change max value
		if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON8)) {
			switch(driverStation.getLeftJoystick().getPOV()) {
			case -1: 
				released = true;
				break;
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
			default:
				break;
			}
		}
		
		//Change min value
		if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON7)) {
			switch(driverStation.getLeftJoystick().getPOV()) {
			case -1: 
				released = true;
				break;
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
			default:
				break;
			}
		}
	}
}

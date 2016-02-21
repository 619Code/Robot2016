package org.usfirst.frc.team619.logic.mapping;

import org.usfirst.frc.team619.hardware.Joystick;
import org.usfirst.frc.team619.logic.RobotThread;
import org.usfirst.frc.team619.logic.ThreadManager;
import org.usfirst.frc.team619.subsystems.DriverStation;
import org.usfirst.frc.team619.subsystems.GenghisShooter;
import org.usfirst.frc.team619.subsystems.Vision;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterMappingThread extends RobotThread {
	
	protected DriverStation driverStation;
	protected GenghisShooter genghisShooter;
	protected Vision vision;
	
	private double time;
	private double time2;
	private double kickDelay;
	private double scalePercent;
	private boolean isHue = true;
	private boolean isSat = false;
	private boolean isVal = false;
	private boolean released = true;
	private boolean released1 = true;
	private boolean releasedSpeed = true;
	private boolean calibrate = false;
	private boolean firstPass = true;
	
	public ShooterMappingThread(Vision vision, GenghisShooter genghisShooter, DriverStation driverStation, int period, ThreadManager threadManager) {
		super(period, threadManager);
		this.driverStation = driverStation;
		this.genghisShooter = genghisShooter;
		this.vision = vision;
		scalePercent = 1.0;
	}

	protected void cycle() { //Should generally use shooter controller
		time2 = System.currentTimeMillis();
		
		//Set LP scale percent
		switch(driverStation.getLeftJoystick().getPOV()) {
		case 45:
		case 315:
		case 0:
			if(releasedSpeed && scalePercent < 1.0 && !driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON7) 
					&& !driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON8)) {
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
		default:
			break;
		}
		double liftPercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.RIGHT_AXIS_Y) * scalePercent;
		double rotatePercent  = driverStation.getLeftJoystick().getAxis(Joystick.Axis.LEFT_AXIS_Y) * (-0.5);
		SmartDashboard.putNumber("Arm Scale Percent", scalePercent);
		
		//Dink and Dank arms + limits
		if(genghisShooter.isDankLimit()) {
			if(liftPercent < 0) {
				genghisShooter.getDankArm().set(0);
			}else {
				genghisShooter.getDankArm().set(liftPercent);
			}
		}else {
			genghisShooter.getDankArm().set(liftPercent);
		}
		if(genghisShooter.isDinkLimit()) {
			if(liftPercent < 0) {
				genghisShooter.getDinkArm().set(0);
			}else {
				genghisShooter.getDinkArm().set(-liftPercent);
			}
		}else {
			genghisShooter.getDinkArm().set(-liftPercent);
		}
		
		//Intake
		if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON4)) {
			genghisShooter.setFlyWheel(-1);
		}else if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON1)) {
			genghisShooter.setFlyWheel(1);
		}else {
			genghisShooter.setFlyWheel(0);
		}
		
		/*
		//Kick the boulder
		if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON6)) {
			if(firstPass) {
				time = System.currentTimeMillis();
			}
		}*/
		
		//Kick the boulder into flywheels
		if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON5)) {
			genghisShooter.kick();
		}else if (driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON6)) {
			genghisShooter.resetKick();
		}else {
			genghisShooter.stopKick();
		}
		
		//rotate manipulator
		genghisShooter.setRotate(rotatePercent);
		
		/*
		//Move LP
		if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON5) && !ghengisShooter.getWinchLimit()) {
			ghengisShooter.switchToWinch();
			ghengisShooter.setLift(0.5);
		}else {
			if(ghengisShooter.isWinch()) {
				ghengisShooter.switchToLift();
			}else if(!ghengisShooter.getFrontLimit() && !ghengisShooter.getBackLimit()) {
				ghengisShooter.setLift(liftPercent);
			}
		}		
		
		//Shooting
		if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON6)) {
			ghengisShooter.getRelease().set(true);
		}else {
			if(!ghengisShooter.getRelease().isOn()) {
				ghengisShooter.getRelease().set(false);
			}
		}
		
		//Turn off solenoid after shot
		if(time - ghengisShooter.getRelease().getLastSetTime() > 100) {
			ghengisShooter.getRelease().setOff();
		}
		*/
		
		//Used for calibration of reflective tape
		if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON10)) { //Default
			if(isHue) {
				isHue = false;
				isSat = true;
			}else if(isSat) {
				isSat = false;
				isVal = true;
			}else if(isVal) {
				isVal = false;
				isHue = true;
			}
		}
		SmartDashboard.putBoolean("Edit Hue", isHue);
		SmartDashboard.putBoolean("Edit Sat", isSat);
		SmartDashboard.putBoolean("Edit Value", isVal);
		SmartDashboard.putBoolean("released1", released);
		SmartDashboard.putBoolean("released2", released1);

		if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON8)) { //Edit vision values
			switch(driverStation.getLeftJoystick().getPOV()) {
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
			switch(driverStation.getLeftJoystick().getPOV()) {
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

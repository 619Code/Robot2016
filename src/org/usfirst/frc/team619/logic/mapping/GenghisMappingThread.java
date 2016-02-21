package org.usfirst.frc.team619.logic.mapping;

import org.usfirst.frc.team619.hardware.Joystick;
import org.usfirst.frc.team619.logic.RobotThread;
import org.usfirst.frc.team619.logic.ThreadManager;
import org.usfirst.frc.team619.subsystems.DriverStation;
import org.usfirst.frc.team619.subsystems.GenghisShooter;
import org.usfirst.frc.team619.subsystems.Vision;
import org.usfirst.frc.team619.subsystems.drive.GenghisDriveBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GenghisMappingThread extends RobotThread {
	
	protected DriverStation driverStation;
	protected GenghisDriveBase driveBase;
	protected GenghisShooter genghisShooter;
	protected Vision vision;
	
	private double center;
	private double leftScalePercent;
	private boolean releasedSpeed = true;
	
	public GenghisMappingThread(Vision vision, GenghisDriveBase driveBase, DriverStation driverStation, int period, ThreadManager threadManager) {
		super(period, threadManager);
		this.vision = vision;
		this.driverStation = driverStation;
		this.driveBase = driveBase;
		leftScalePercent = 1.0;
	}
	
	protected void cycle() { //Should generally use driver controller
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
		if(driverStation.getRightJoystick().getButton(Joystick.Button.BUTTON1)) {
			driveBase.aim(center);
		}else {
			driveBase.setLeftWheels(leftPercent);
			driveBase.setRightWheels(rightPercent);
		}
		
	}

}
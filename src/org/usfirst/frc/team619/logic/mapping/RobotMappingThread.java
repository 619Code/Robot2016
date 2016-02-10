   package org.usfirst.frc.team619.logic.mapping;

import org.usfirst.frc.team619.hardware.Joystick;
import org.usfirst.frc.team619.logic.RobotThread;
import org.usfirst.frc.team619.logic.ThreadManager;
import org.usfirst.frc.team619.subsystems.DriverStation;
import org.usfirst.frc.team619.subsystems.drive.RobotDriveBase;


public class RobotMappingThread extends RobotThread {
	protected DriverStation driverStation;
	protected RobotDriveBase driveBase;

	
	public RobotMappingThread(RobotDriveBase driveBase, DriverStation driverStation, int period, ThreadManager threadManager) {
		super(period, threadManager);
		this.driverStation = driverStation;
		this.driveBase = driveBase;
	}
	
	protected void cycle() {
		double leftScalePercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Z);
		
		if(leftScalePercent < 0.3) {
			leftScalePercent = 0.3;
		}
		
		double leftPercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.AXIS_Y) * leftScalePercent;
		double rightPercent = driverStation.getRightJoystick().getAxis(Joystick.Axis.AXIS_Y) * leftScalePercent;

		driveBase.setLeftWheels(leftPercent);
		driveBase.setRightWheels(-rightPercent);
		
		if (driverStation.getRightJoystick().getButton(Joystick.Button.BUTTON1)) {
			driveBase.autoAim();
		}
		else {
			driveBase.setLeftWheels(leftPercent);
			driveBase.setRightWheels(-rightPercent);
		}
		}
	}




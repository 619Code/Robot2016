package org.usfirst.frc.team619.logic.mapping;

import org.usfirst.frc.team619.hardware.Joystick;
import org.usfirst.frc.team619.logic.RobotThread;
import org.usfirst.frc.team619.logic.ThreadManager;
import org.usfirst.frc.team619.subsystems.DriverStation;
import org.usfirst.frc.team619.subsystems.GhengisShooter;

public class ShooterMappingThread extends RobotThread {
	
	protected DriverStation driverStation;
	protected GhengisShooter ghengisShooter;
	
	public ShooterMappingThread(GhengisShooter ghengisShooter, DriverStation driverStation, int period, ThreadManager threadManager) {
		super(period, threadManager);
		this.driverStation = driverStation;
		this.ghengisShooter = ghengisShooter;
	}

	protected void cycle() {
		double triggerPercent = driverStation.getLeftJoystick().getAxis(Joystick.Axis.RIGHT_TRIGGER) * 0.5;
		
		//Controls dink and dank
		if(driverStation.getLeftJoystick().getAxis(Joystick.Axis.RIGHT_TRIGGER) > 0.5) {
			ghengisShooter.setArms(triggerPercent);
		}else if(driverStation.getLeftJoystick().getAxis(Joystick.Axis.LEFT_TRIGGER) > 0.5) {
			ghengisShooter.setArms(-triggerPercent);
		}
		
		//Shooting
		if(driverStation.getLeftJoystick().getButton(Joystick.Button.BUTTON6)) {
			ghengisShooter.fire();
		}
		
	}

}

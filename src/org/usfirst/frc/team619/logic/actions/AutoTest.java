package org.usfirst.frc.team619.logic.actions;

import org.usfirst.frc.team619.subsystems.RobotShooter;
import org.usfirst.frc.team619.subsystems.drive.RobotDriveBase;

import edu.wpi.first.wpilibj.Timer;

public class AutoTest {
	
	protected RobotDriveBase driveBase;
	protected RobotShooter robotShooter;
	
	private boolean isComplete = false;
	
	public AutoTest(RobotDriveBase driveBase, RobotShooter robotShooter) {
		this.robotShooter = robotShooter;
		this.driveBase = driveBase;
	}
	
	public boolean isComplete() {
		return isComplete;
	}

	public void run() {
		isComplete = true;
		
		driveBase.setLeftWheels(-0.5);
		driveBase.setRightWheels(-0.5);
		Timer.delay(3.5);
		driveBase.stop();
	}
}

package org.usfirst.frc.team619.logic.actions;

import org.usfirst.frc.team619.logic.ThreadManager;
import org.usfirst.frc.team619.subsystems.RobotShooter;
import org.usfirst.frc.team619.subsystems.Vision;
import org.usfirst.frc.team619.subsystems.drive.RobotDriveBase;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoShoot extends Action {
	
	protected RobotDriveBase driveBase;
	protected RobotShooter robotShooter;
	protected Vision vision;
	private boolean isComplete = false;

	public AutoShoot(Vision vision, RobotShooter robotShooter, RobotDriveBase driveBase,
			int waitForDependenciesPeriod, int runPeriod, ThreadManager threadManager) {
		super(waitForDependenciesPeriod, runPeriod, threadManager);
		this.driveBase = driveBase;
		this.robotShooter = robotShooter;
		this.vision = vision;
	}

	public boolean isComplete() {
		return isComplete;
	}

	protected void cycle() {
		boolean aim, kick;
		double angle;
		
		driveBase.setLeftWheels(1);
		driveBase.setRightWheels(1);
		Timer.delay(2);
		driveBase.stop();
		while(SmartDashboard.getNumber("Center") != -1) {
			driveBase.turn(-0.5);
		}
		driveBase.stop();
		robotShooter.setFlyWheel(1);
		
		double center = SmartDashboard.getNumber("Center");
		while(center < 315 || center > 325) {
			driveBase.aim(center, 0.5);
			center = SmartDashboard.getNumber("Center");
		}
		 
		angle = Math.atan((vision.castleHeight) / (SmartDashboard.getNumber("Distance") * 12)) * (180 / Math.PI);
		aim = robotShooter.setAngle(angle);
		while(aim) {
			aim = robotShooter.setAngle(angle);
		}
		
		kick = robotShooter.shoot();
		while(kick) {
			kick = robotShooter.shoot();
		}
		
		robotShooter.setFlyWheel(0);
		
		isComplete = true;
	}
	

}

package org.usfirst.frc.team619.subsystems;

import org.usfirst.frc.team619.hardware.CANTalon;
import org.usfirst.frc.team619.hardware.DualInputSolenoid;
import org.usfirst.frc.team619.hardware.LimitSwitch;

public class GhengisShooter {
	
	protected CANTalon dinkArm, dankArm;
	protected CANTalon liftMotor, liftMotor2, intake;
	protected DualInputSolenoid release, modeSwitch;
	protected LimitSwitch frontLimit, backLimit, winchLimit;
	
	private boolean isWinch;
	
	public GhengisShooter(int dinkArmID, int dankArmID) {
		dinkArm = new CANTalon(dinkArmID);
		dankArm = new CANTalon(dankArmID);
	}
	
	public GhengisShooter(CANTalon dinkArm, CANTalon dankArm) {
		this.dinkArm = dinkArm;
		this.dankArm = dankArm;
	}
	
	public GhengisShooter(int dinkArmID, int dankArmID, int liftMotorID, int liftMotorID2, int intakeID, 
			int solenoidID, int solenoidID2, int solenoidID3, int solenoidID4, int frontLimitID, int backLimitID, int winchLimitID) {
		dinkArm = new CANTalon(dinkArmID);
		dankArm = new CANTalon(dankArmID);
		liftMotor = new CANTalon(liftMotorID);
		liftMotor2 = new CANTalon(liftMotorID2);
		intake = new CANTalon(intakeID);
		release = new DualInputSolenoid(solenoidID, solenoidID2);
		modeSwitch = new DualInputSolenoid(solenoidID3, solenoidID4);
		frontLimit = new LimitSwitch(frontLimitID);
		backLimit = new LimitSwitch(backLimitID);
		winchLimit = new LimitSwitch(winchLimitID);
	}
	
	public GhengisShooter(CANTalon dinkArm, CANTalon dankArm, CANTalon liftMotor, CANTalon liftMotor2, CANTalon intake, 
			DualInputSolenoid release, DualInputSolenoid modeSwitch, LimitSwitch frontLimit, LimitSwitch backLimit, LimitSwitch winchLimit) {
		this.dinkArm = dinkArm;
		this.dankArm = dankArm;
		this.liftMotor = liftMotor;
		this.liftMotor2 = liftMotor2;
		this.intake = intake;
		this.release = release;
		this.modeSwitch = modeSwitch;
		this.frontLimit = frontLimit;
		this.backLimit = backLimit;
		this.winchLimit = winchLimit;
	}
	
	public CANTalon getDinkArm() {
		return dinkArm;
	}
	
	public CANTalon getDankArm() {
		return dankArm;
	}
	
	public CANTalon getLiftMotor() {
		return liftMotor;
	}
	
	public CANTalon getIntake() {
		return intake;
	}
	
	public DualInputSolenoid getRelease() {
		return release;
	}
	
	public DualInputSolenoid getSwitch() {
		return modeSwitch;
	}
	
	public boolean getFrontLimit() {
		return frontLimit.get();
	}
	
	public boolean getBackLimit() {
		return backLimit.get();
	}
	
	public boolean getWinchLimit() {
		return winchLimit.get();
	}
	
	public void switchToLift() {
		isWinch = false;
		modeSwitch.set(true);
	}
	
	public void switchToWinch() {
		isWinch = true;
		modeSwitch.set(false);
	}
	
	public boolean isWinch() {
		return isWinch;
	}
	
	public void setArms(double percent) {
		dinkArm.set(percent);
		dankArm.set(-percent);
	}
	
	public void setLift(double percent) {
		liftMotor.set(percent);
		liftMotor2.set(-percent);
	}
	
	public void setIntake() {
		intake.set(1);
	}
	
	public void setOuttake() {
		intake.set(-1);
	}
	
	public void stopIntake() {
		intake.set(0);
	}
	
	public void fire() {
		//enable firing mechanism
	}
}

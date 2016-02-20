package org.usfirst.frc.team619.subsystems;

import org.usfirst.frc.team619.hardware.CANTalon;
import org.usfirst.frc.team619.hardware.DualInputSolenoid;

public class GhengisShooter {
	
	protected CANTalon dinkArm, dankArm;
	protected CANTalon liftMotor, liftMotor2, intake;
	protected DualInputSolenoid release;
	
	public GhengisShooter(int dinkArmID, int dankArmID) {
		dinkArm = new CANTalon(dinkArmID);
		dankArm = new CANTalon(dankArmID);
	}
	
	public GhengisShooter(CANTalon dinkArm, CANTalon dankArm) {
		this.dinkArm = dinkArm;
		this.dankArm = dankArm;
	}
	
	public GhengisShooter(int dinkArmID, int dankArmID, int liftMotorID, int liftMotorID2, int intakeID, int solenoidID, int solenoidID2) {
		dinkArm = new CANTalon(dinkArmID);
		dankArm = new CANTalon(dankArmID);
		liftMotor = new CANTalon(liftMotorID);
		liftMotor2 = new CANTalon(liftMotorID2);
		intake = new CANTalon(intakeID);
		release = new DualInputSolenoid(solenoidID, solenoidID2);
	}
	
	public GhengisShooter(CANTalon dinkArm, CANTalon dankArm, CANTalon liftMotor, CANTalon liftMotor2, CANTalon intake, DualInputSolenoid release) {
		this.dinkArm = dinkArm;
		this.dankArm = dankArm;
		this.liftMotor = liftMotor;
		this.liftMotor2 = liftMotor2;
		this.intake = intake;
		this.release = release;
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
	
	public void setArms(double percent) {
		dinkArm.set(percent);
		dankArm.set(-percent);
	}
	
	public void moveLift(double percent) {
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

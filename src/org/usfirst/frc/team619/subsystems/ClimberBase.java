package org.usfirst.frc.team619.subsystems;

import org.usfirst.frc.team619.hardware.CANTalon;
import org.usfirst.frc.team619.hardware.Solenoid;

public class ClimberBase {
	
	protected CANTalon winch, winch2;
	protected Solenoid climberSol, climberReset;

	public ClimberBase(CANTalon winch, CANTalon winch2, Solenoid climberSol, Solenoid climberReset) {
		this.winch = winch;
		this.winch2 = winch2;
		this.climberSol = climberSol;
		this.climberReset = climberReset;
	}
	
	public CANTalon getWinch() {
		return winch;
	}
	
	public CANTalon getWinch2() {
		return winch2;
	}
	
	public Solenoid getClimberSolenoid() {
		return climberSol;
	}
	
	public Solenoid getResetSolenoid() {
		return climberReset;
	}
	
	public void setWinch(double percent) {
		winch.set(percent);
		winch2.set(percent);
	}

	public void extendClimber() {
		climberSol.set(true);
	}
	
	public void stopClimber() {
		climberSol.set(false);
		climberReset.set(false);
	}
	
	public void resetClimber() {
		climberReset.set(true);
	}
}

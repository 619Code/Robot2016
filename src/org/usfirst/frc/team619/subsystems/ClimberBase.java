package org.usfirst.frc.team619.subsystems;

import org.usfirst.frc.team619.hardware.CANTalon;
import org.usfirst.frc.team619.hardware.Solenoid;

public class ClimberBase {
	
	protected CANTalon winch, winch2;
	protected Solenoid angleSol, climberSol;

	public ClimberBase(CANTalon winch, CANTalon winch2, Solenoid angleSol, Solenoid climberSol) {
		this.winch = winch;
		this.winch2 = winch2;
		this.angleSol = angleSol;
		this.climberSol = climberSol;
	}
	
	public CANTalon getWinch() {
		return winch;
	}
	
	public CANTalon getWinch2() {
		return winch2;
	}
	
	public Solenoid getAngleSolenoid() {
		return angleSol;
	}
	
	public Solenoid getClimberSolenoid() {
		return climberSol;
	}
	
	public void setWinch(double percent) {
		winch.set(percent);
		winch2.set(-percent);
	}
	
	public void moveClimber() {
		angleSol.set(true);
	}
	
	public void stopClimber() {
		angleSol.set(false);
	}
	
	public void fireClimber() {
		climberSol.set(true);
	}
	
	public void idleClimber() {
		climberSol.set(false);
	}
}

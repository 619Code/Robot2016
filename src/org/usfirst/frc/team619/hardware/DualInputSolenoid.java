package org.usfirst.frc.team619.hardware;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class DualInputSolenoid {
	
	private DoubleSolenoid solenoid;
	
	private long lastSetTime;
	
	public DualInputSolenoid(int channelLeft, int channelRight) {
		solenoid = new DoubleSolenoid(channelLeft, channelRight);
		lastSetTime = System.currentTimeMillis();
	}
	
	public void set(boolean on) {
		if(on) {
			solenoid.set(DoubleSolenoid.Value.kForward);
		}else {
			solenoid.set(DoubleSolenoid.Value.kReverse);
		}
		lastSetTime = System.currentTimeMillis();
	}
	
	public void setOff() {
		solenoid.set(DoubleSolenoid.Value.kOff);
	}
	
	public boolean isForward() {
		return (solenoid.get() == DoubleSolenoid.Value.kForward);
	}
	
	public boolean isOn() {
		return !(solenoid.get() == DoubleSolenoid.Value.kOff);
	}
	
	public DoubleSolenoid getSolenoid() {
		return solenoid;
	}
	
	public long getLastSetTime() {
		return lastSetTime;
	}
	
}

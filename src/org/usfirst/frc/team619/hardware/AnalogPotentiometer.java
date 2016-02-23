package org.usfirst.frc.team619.hardware;

public class AnalogPotentiometer {

	private edu.wpi.first.wpilibj.AnalogPotentiometer pot;
	
	private int channel;
	private double range;
	private double offset;
	public final double DEFAULT_RANGE = 270.0;
	
	public AnalogPotentiometer(int channel){
		this.channel = channel;
		this.range = DEFAULT_RANGE;
		pot = new edu.wpi.first.wpilibj.AnalogPotentiometer(channel, range);
	}
	
	public AnalogPotentiometer(int channel, double range, double offset) {
		this.channel = channel;
		this.range = range;
		this.offset = offset;
		pot = new edu.wpi.first.wpilibj.AnalogPotentiometer(channel, range, offset);
	}
	
	public double get(){
		return pot.get();
	}
	
	public double getRange() {
		return range;
	}
	
	public double getOffset() {
		return offset;
	}
	
	public int getChannel(){
		return channel;
	}
	
}

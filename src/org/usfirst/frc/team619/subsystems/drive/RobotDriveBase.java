package org.usfirst.frc.team619.subsystems.drive;

import org.usfirst.frc.team619.hardware.Talon;

public class RobotDriveBase {
	
	private Talon leftMotor, rightMotor, leftMotor2, rightMotor2;
	
	public RobotDriveBase(int leftMotorID, int rightMotorID, int leftMotorID2, int rightMotorID2) {
		leftMotor = new Talon(leftMotorID);
		rightMotor = new Talon(rightMotorID);
		leftMotor2 = new Talon(leftMotorID2);
		rightMotor2 = new Talon(rightMotorID2);
	}
	
	public RobotDriveBase(Talon leftMotor, Talon rightMotor, Talon leftMotor2, Talon rightMotor2) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.leftMotor2 = leftMotor2;
		this.rightMotor2 = rightMotor2;
	}
	
	public Talon getLeftWheel() {
		return leftMotor;
	}
	
	public Talon getLeftWheel2() {
		return leftMotor2;
	}
	
	public Talon getRightWheel() {
		return rightMotor;
	}
	
	public Talon getRightWheel2() {
		return rightMotor2;
	}
	
	public void setLeftWheels(double leftPercent) {
		leftMotor.set(leftPercent);
		leftMotor2.set(leftPercent);
	}
	
	public void setRightWheels(double rightPercent) {
		rightMotor.set(rightPercent);
		rightMotor2.set(rightPercent);
	}
	
	public void stop() {
		leftMotor.set(0);
		leftMotor2.set(0);
		rightMotor.set(0);
		rightMotor2.set(0);
	}

}

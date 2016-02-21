package org.usfirst.frc.team619.subsystems.drive;

import org.usfirst.frc.team619.hardware.CANTalon;

public class RobotDriveBase {
	
	protected CANTalon leftMotor, rightMotor, leftMotor2, rightMotor2;
	
	public RobotDriveBase(int leftMotorID, int rightMotorID, int leftMotorID2, int rightMotorID2) {
		leftMotor = new CANTalon(leftMotorID);
		rightMotor = new CANTalon(rightMotorID);
		leftMotor2 = new CANTalon(leftMotorID2);
		rightMotor2 = new CANTalon(rightMotorID2);
	}
	
	public RobotDriveBase(CANTalon leftMotor, CANTalon rightMotor, CANTalon leftMotor2, CANTalon rightMotor2) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.leftMotor2 = leftMotor2;
		this.rightMotor2 = rightMotor2;
	}
	
	public CANTalon getLeftWheel() {
		return leftMotor;
	}
	
	public CANTalon getLeftWheel2() {
		return leftMotor2;
	}
	
	public CANTalon getRightWheel() {
		return rightMotor;
	}
	
	public CANTalon getRightWheel2() {
		return rightMotor2;
	}
	
	public void setLeftWheels(double leftPercent) {
		leftMotor.set(-leftPercent);
		leftMotor2.set(-leftPercent);
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

	public void turn(double percent) { //positive is right
		this.setLeftWheels(percent);
		this.setRightWheels(percent);
	}
	
	public void aim(double center) {
		double aimSpeed = 0;
		
		if(center > 325) {
			aimSpeed = 0.5;
		}else if(center < 315) {
			aimSpeed = -0.5;
		}
		setLeftWheels(-aimSpeed);
		setRightWheels(aimSpeed);
	}
	
	public void turnRight(double center) {
		leftMotor.set(-1);
		leftMotor2.set(-1);
		rightMotor.set(1);
		rightMotor2.set(1);
	}
	
	public void turnLeft(double center) {
		leftMotor.set(1);
		leftMotor2.set(1);
		rightMotor.set(-1);
		rightMotor2.set(-1);
	}
	
}

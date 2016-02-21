package org.usfirst.frc.team619.subsystems.drive;

import org.usfirst.frc.team619.hardware.CANTalon;

public class RobotDriveBase {
	
<<<<<<< HEAD:src/org/usfirst/frc/team619/subsystems/drive/GenghisDriveBase.java
	protected CANTalon leftMotor, rightMotor, leftMotor2, rightMotor2;
	
	public GenghisDriveBase(int leftMotorID, int rightMotorID, int leftMotorID2, int rightMotorID2) {
=======
<<<<<<< HEAD
	protected CANTalon leftMotor, rightMotor, leftMotor2, rightMotor2;
=======
	private CANTalon leftMotor, rightMotor, leftMotor2, rightMotor2, shoot;
	private double distance;
	private double center;
>>>>>>> origin/master
	
	public RobotDriveBase(int leftMotorID, int rightMotorID, int leftMotorID2, int rightMotorID2, int shootID) {
>>>>>>> origin/master:src/org/usfirst/frc/team619/subsystems/drive/RobotDriveBase.java
		leftMotor = new CANTalon(leftMotorID);
		rightMotor = new CANTalon(rightMotorID);
		leftMotor2 = new CANTalon(leftMotorID2);
		rightMotor2 = new CANTalon(rightMotorID2);
	}
	
<<<<<<< HEAD:src/org/usfirst/frc/team619/subsystems/drive/GenghisDriveBase.java
	public GenghisDriveBase(CANTalon leftMotor, CANTalon rightMotor, CANTalon leftMotor2, CANTalon rightMotor2) {
=======
	public RobotDriveBase(CANTalon leftMotor, CANTalon rightMotor, CANTalon leftMotor2, CANTalon rightMotor2, CANTalon shoot) {
>>>>>>> origin/master:src/org/usfirst/frc/team619/subsystems/drive/RobotDriveBase.java
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
	
<<<<<<< HEAD:src/org/usfirst/frc/team619/subsystems/drive/GenghisDriveBase.java
=======
<<<<<<< HEAD
>>>>>>> origin/master:src/org/usfirst/frc/team619/subsystems/drive/RobotDriveBase.java
	public void turn(double percent) { //positive is right
		this.setLeftWheels(percent);
		this.setRightWheels(percent);
	}
	
	public void aim(double center) {
		double aimSpeed = 0;
		
		if(center > 340) {
			aimSpeed = 0.4;
		}else if(center < 330) {
			aimSpeed = -0.4;
		}
		setLeftWheels(-aimSpeed);
		setRightWheels(aimSpeed);
	}
<<<<<<< HEAD:src/org/usfirst/frc/team619/subsystems/drive/GenghisDriveBase.java
=======
=======
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
	
	public void autoAim() {
		
		
		center = (camera.center() - 320)/640;
		
		while (center != 0) {

			if (center < 0) {
				turnRight(center);
			}
			else {
				turnLeft(center);
			}
			 
			
		}

	}
	
>>>>>>> origin/master
>>>>>>> origin/master:src/org/usfirst/frc/team619/subsystems/drive/RobotDriveBase.java
}

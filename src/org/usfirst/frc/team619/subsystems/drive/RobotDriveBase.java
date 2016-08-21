package org.usfirst.frc.team619.subsystems.drive;

import org.usfirst.frc.team619.hardware.CANTalon;

import com.kauailabs.nav6.frc.IMUAdvanced;

public class RobotDriveBase {
	
	protected CANTalon leftMotor, rightMotor, leftMotor2, rightMotor2;
	protected IMUAdvanced imu;
	
	public RobotDriveBase(int leftMotorID, int rightMotorID, int leftMotorID2, int rightMotorID2) {
		leftMotor = new CANTalon(leftMotorID);
		rightMotor = new CANTalon(rightMotorID);
		leftMotor2 = new CANTalon(leftMotorID2);
		rightMotor2 = new CANTalon(rightMotorID2);
	}
	
	public RobotDriveBase(CANTalon leftMotor, CANTalon rightMotor, CANTalon leftMotor2, CANTalon rightMotor2, IMUAdvanced imu) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.leftMotor2 = leftMotor2;
		this.rightMotor2 = rightMotor2;
		this.imu = imu;
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
	
	public IMUAdvanced getIMU() {
		return imu;
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
	
	/**
	 * Positive is right, negative is left
	 * 
	 * @param percent Motor speed
	 */
	public void turn(double percent) {
		this.setLeftWheels(percent);
		this.setRightWheels(-percent);
	}
	
	/**
	 * Turns robot at a specified speed until the target center
	 * is in the middle of the camera
	 * 
	 * @param center Target center in pixels
	 * @param speed Desired turn speed
	 */
	public void aim(double center, double speed) {
		double aimSpeed = 0;
		
		if(center > 323) {
			aimSpeed = 0.5 * speed;
		}else if(center < 317) {
			aimSpeed = -0.5 * speed;
		}
		setLeftWheels(-aimSpeed);
		setRightWheels(aimSpeed);
	}
	
	public float getAngle() {
		float angle = imu.getYaw() + 180;
		return angle;
	}
	
	public void zeroRobot() {
		imu.zeroYaw();
	}
}
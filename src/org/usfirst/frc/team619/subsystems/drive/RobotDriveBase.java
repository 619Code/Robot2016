package org.usfirst.frc.team619.subsystems.drive;

import org.usfirst.frc.team619.hardware.CANTalon;
import org.usfirst.frc.team619.hardware.Talon;

import com.kauailabs.nav6.frc.IMUVeryAdvanced;

public class RobotDriveBase {
	
	protected CANTalon leftMotor, rightMotor, leftMotor2, rightMotor2;
	protected Talon frontLeft, backLeft, frontRight, backRight;
	protected IMUVeryAdvanced imu;
	
	private static double FT_PER_SECOND2 = 32.174; 
	
	public RobotDriveBase(int leftMotorID, int rightMotorID, int leftMotorID2, int rightMotorID2) {
		leftMotor = new CANTalon(leftMotorID);
		rightMotor = new CANTalon(rightMotorID);
		leftMotor2 = new CANTalon(leftMotorID2);
		rightMotor2 = new CANTalon(rightMotorID2);
	}
	
	public RobotDriveBase(Talon frontLeft, Talon backLeft, Talon frontRight, Talon backRight) {
		this.frontLeft = frontLeft;
		this.backLeft = backLeft;
		this.frontRight = frontRight;
		this.backRight = backRight;
	}
	
	public RobotDriveBase(CANTalon leftMotor, CANTalon rightMotor, CANTalon leftMotor2, CANTalon rightMotor2, IMUVeryAdvanced imu) {
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
	
	public IMUVeryAdvanced getIMU() {
		return imu;
	}
	
	public void setLeftWheels(double leftPercent) {
		leftMotor.set(-leftPercent);
		leftMotor2.set(-leftPercent);
	}
	
	public void setLeftTalons(double leftPercent) {
		frontLeft.set(leftPercent);
		backLeft.set(-leftPercent);
	}
	
	public void setRightWheels(double rightPercent) {
		rightMotor.set(rightPercent);
		rightMotor2.set(rightPercent);
	}
	
	public void setRightTalons(double rightPercent) {
		//frontRight.set(rightPercent);
		//backRight.set(rightPercent);
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
		
		if(center > 325) {
			aimSpeed = 0.75 * speed;
		}else if(center < 315) {
			aimSpeed = 0.75 * speed;
		}
		setLeftWheels(-aimSpeed);
		setRightWheels(aimSpeed);
	}
	
	public void talonAim(double center, double speed) {
		double aimSpeed;
		
		if(center > 325) {
			aimSpeed = speed;
		}else if(center < 315) {
			aimSpeed = -speed;
		}else {
			aimSpeed = 0;
		}
		setLeftTalons(-aimSpeed);
		setRightTalons(aimSpeed);
	}
	
	public float getAngle() {
		return imu.getYaw();
	}
	
	public float getAvgXAccel() {
		return imu.getAverageWorldAccelX();
	}
	
	public float getAvgYAccel() {
		return imu.getAverageWorldAccelY();
	}
	
	public void zeroRobot() {
		imu.zeroYaw();
	}
	
	public double IMUInitTime() {
		return imu.getInitTime();
	}
	
	public double getAvgXVelocity() {
		double time, accel;
		
		time = (System.currentTimeMillis() - imu.getInitTime()) / 1000;
		accel = imu.getAverageWorldAccelX() * FT_PER_SECOND2;
		return accel * time;
	}
	
	public double getAvgYVelocity() {
		double time, accel;
		
		time = (System.currentTimeMillis() - imu.getInitTime()) / 1000;
		accel = imu.getAverageWorldAccelX() * FT_PER_SECOND2;
		return accel * time;
	}
	
	public double getAvgXPosition() {
		double time, accel;
		
		time = (System.currentTimeMillis() - imu.getInitTime()) / 1000;
		accel = imu.getAverageWorldAccelX() * FT_PER_SECOND2;
		return accel * (time * time);
	}
	
	public double getAvgYPosition() {
		double time, accel;
		
		time = (System.currentTimeMillis() - imu.getInitTime()) / 1000;
		accel = imu.getAverageWorldAccelX() * FT_PER_SECOND2;
		return accel * (time* time);
	}
}
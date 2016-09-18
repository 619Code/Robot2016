package org.usfirst.frc.team619.subsystems;

import org.usfirst.frc.team619.hardware.CANTalon;
import org.usfirst.frc.team619.hardware.LimitSwitch;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;

public class RobotShooter {
	
	protected CANTalon dinkArm, dankArm;
	protected CANTalon flyMotor, flyMotor2, kicker, rotate;
	protected LimitSwitch dankLimit, dinkLimit, kickLimit;
	
	//Encoder value for starting position
	private double zeroShooter = 0;
	private double zeroArms = 0;
	
	public RobotShooter(int dinkArmID, int dankArmID) {
		dinkArm = new CANTalon(dinkArmID);
		dankArm = new CANTalon(dankArmID);
	}
	
	public RobotShooter(CANTalon dinkArm, CANTalon dankArm) {
		this.dinkArm = dinkArm;
		this.dankArm = dankArm;
	}
	
	public RobotShooter(int dinkArmID, int dankArmID, int flyMotorID, int flyMotorID2, int kickerID, 
			int rotateID, int dankLimitID, int dinkLimitID, int kickLimitID) {
		dinkArm = new CANTalon(dinkArmID);
		dankArm = new CANTalon(dankArmID);
		flyMotor = new CANTalon(flyMotorID);
		flyMotor2 = new CANTalon(flyMotorID2);
		kicker = new CANTalon(kickerID);
		rotate = new CANTalon(rotateID);
		dankLimit = new LimitSwitch(dankLimitID);
		dinkLimit = new LimitSwitch(dinkLimitID);
		kickLimit = new LimitSwitch(kickLimitID);
	}
	
	public RobotShooter(CANTalon dinkArm, CANTalon dankArm, CANTalon flyMotor, CANTalon flyMotor2, CANTalon kicker, 
			CANTalon rotate, LimitSwitch dankLimit, LimitSwitch dinkLimit, LimitSwitch kickLimit) {
		this.dinkArm = dinkArm;
		this.dinkArm.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		this.dankArm = dankArm;
		this.flyMotor = flyMotor;
		this.flyMotor2 = flyMotor2;
		this.kicker = kicker;
		this.rotate = rotate;
		this.rotate.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		this.dankLimit = dankLimit;
		this.dinkLimit = dinkLimit;
		this.kickLimit = kickLimit;
	}
	
	public CANTalon getDinkArm() {
		return dinkArm;
	}
	
	public CANTalon getDankArm() {
		return dankArm;
	}
	
	public CANTalon getFlyWheel() {
		return flyMotor;
	}
	
	public CANTalon getRotate() {
		return rotate;
	}
	
	public CANTalon getKicker() {
		return kicker;
	}
	
	public void setDinkArm(double speed) {
		dinkArm.set(speed);
	}
	
	public void setDankArm(double speed) {
		dankArm.set(-speed);
	}
	public void setFlyWheel(double percent) {
		flyMotor.set(percent);
		flyMotor2.set(-percent);
	}
	
	public void setRotate(double percent) {
		rotate.set(-percent);
	}
	
	public void kick() {
		if(!kickLimit.get())
			kicker.set(0.5);
		else
			kicker.set(0);
	}
	
	public void resetKick() {
		kicker.set(-0.5);
	}
	
	public void stopKick() {
		kicker.set(0);
	}
	
	public boolean isDankLimit() {
		return dankLimit.get();
	}
	
	public boolean isDinkLimit() {
		return dinkLimit.get();
	}
	
	public boolean isKickLimit() {
		return kickLimit.get();
	}
	
	/**
	 * Sets the current position as 90 degrees.
	 * If not called, the shooter will be assumed to be vertical.
	 * 
	 * Should be called in the pit or when the shooter is expected to be at
	 * 90 degrees, such as at the beginning of a match. Use with caution.
	 */
	public void calibrate() {
		zeroShooter = rotate.getEncPosition() * 360 / 512;
		zeroShooter -= 92;
		zeroArms = dinkArm.getEncPosition() * 360 / (512 * 4);
		zeroArms += 148;
	}
	
	/**
	 *  Gets current angle in degrees using 4x Encoder
	 * @return Shooter angle
	 */
	public double getAngle() {
		double angle = rotate.getEncPosition() * 360 / 512;
		angle -= zeroShooter;
		return angle;
	}
	
	/**
	 * Returns current arm angles
	 * @return Arm angle
	 */
	public double getDinkAngle() {
		double angle = dinkArm.getEncPosition() * 360 / (512 * 4);
		angle -= zeroArms;
		return -angle;
	}
	
	/**
	 * Sets the shooter to specified angle
	 * **Must be in loop or shooter will not stop moving**
	 * 
	 * @param angle
	 * @return Returns true if still aiming, false if at target
	 */
	public boolean setAngle(double angle) {
		double currentAngle = getAngle();
		double speed = 0.75;
		angle = (int)angle;
		
		if(currentAngle > 15)
			speed *= 0.75;
		if(currentAngle > angle + 1) {
			setRotate(speed);
		}else if(currentAngle < angle - 1) {
			setRotate(-speed);
		}else {
			setRotate(0);
			return false;
		}
		return true;
	}
	
	/**
	 * Put this in the parameters for shooter TalonSRX soft limits
	 * 
	 * @param maxAngle Desired max/min in degrees
	 * @return maxAngle Angle normalized to 0* and in Encoder degrees
	 */
	public double setLimit(double maxAngle) {
		double limit, newMax;
		
		newMax = maxAngle * 360 / 512;
		limit = newMax + zeroShooter;
		return limit;
	}
	
	/**
	 * Put this in the parameters for Dink/Dank TalonSRX soft limits
	 * 
	 * @param maxAngle Desired max/min in degrees
	 * @return maxAngle Angle normalized to 0* and in Encoder degrees
	 */
	public double setArmLimit(double maxAngle) {
		double limit, newMax;
		
		newMax = maxAngle * 360 / (512 * 4);
		limit = newMax + zeroArms;
		return limit;
	}
}

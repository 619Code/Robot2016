package org.usfirst.frc.team619.subsystems;

import org.usfirst.frc.team619.hardware.CANTalon;
import org.usfirst.frc.team619.hardware.LimitSwitch;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;

public class RobotShooter {
	
	protected CANTalon dinkArm, dankArm;
	protected CANTalon flyMotor, flyMotor2, kicker, rotate;
	protected LimitSwitch dankLimit, dinkLimit, kickLimit;
	
	private boolean firstPass = true;
	private boolean firstPass2 = true;
	private int turns;
	private double kickDelay;
	private double time2;
	
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
		if(dinkLimit.get() && speed > 0) {
			dinkArm.set(0);
		}else {
			dinkArm.set(-speed);
		}
	}
	
	public void setDankArm(double speed) {
		if(dankLimit.get() && speed > 0) {
			dankArm.set(0);
		}else {
			dankArm.set(speed);
		}
	}
	public void setFlyWheel(double percent) {
		flyMotor.set(percent);
		flyMotor2.set(-percent);
	}
	
	public void setRotate(double percent) {
		rotate.set(percent);
	}
	
	public void kick() {
		kicker.set(1);
	}
	
	public void resetKick() {
		kicker.set(-0.5);
	}
	
	/**
	 * Kicks boulder then resets to original position
	 *  **Must be put in a loop or kicker will malfunction**
	 *  
	 * @return Return true if kicking, false if fully reset
	 */
	public boolean shoot() {
		double time = System.currentTimeMillis();
		if(firstPass) {
			time2 = System.currentTimeMillis();
			firstPass = false;
		}
		if(!isKickLimit() && firstPass2) { //Kick
			kick();
		}else if(firstPass2) {
			kickDelay = (time - time2) * 2;
			time2 = System.currentTimeMillis();
			firstPass2 = false;
		}
		if(time - time2 <= kickDelay && !firstPass2) { //Reset
			resetKick();
		}else if(!firstPass2) {
			stopKick();
			firstPass = true;
			firstPass2 = true;
			return false;
		}
		return true;
	}
	
	public double getKickDelay() {
		return kickDelay;
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
	 *  Gets current angle in degrees using 4x Encoder
	 * @return Return angle
	 */
	public double getAngle() {
		double angle = rotate.getEncPosition() * 360 / 512;
		//angle -= 352;
		angle -= 38;
		turns = 1;
		while(angle >= 360) {
			angle -= 360;
			turns++;
		}
		while(angle <= 0) {
			angle += 360;
			turns--;
		}
		return angle;
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
		angle = (int)angle;
		
		if(currentAngle > angle + 2) {
			setRotate(0.5);
		}else if(currentAngle < angle - 2) {
			setRotate(-0.5);
		}else {
			setRotate(0);
			return false;
		}
		return true;
	}
	
	/**
	 * Put this in the parameters for TalonSRX soft limits
	 * 
	 * @param maxAngle Desired max/min in degrees
	 * @return maxAngle Angle normalized to 0* and in Encoder degrees
	 */
	public double setLimit(double maxAngle) {
		double limit, newMax;
		
		//0 degrees = -11 on encoder
		newMax = maxAngle * 512 / 360;
		limit = newMax - 11;
		return limit;
	}
}

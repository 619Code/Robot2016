/*
 * Used for tracking retro reflective tape
 * 
 */

package org.usfirst.frc.team619.subsystems;

import org.usfirst.frc.team619.subsystems.ParticleReport;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

public class Vision {
	
	private double distance, linearDistance;
	private double right, left;
	private double top, bottom;
	private double center = -1;
	private double aspect, area, areaScore;
	private int hLow, hHigh;
	private int sLow, sHigh;
	private int vLow, vHigh;
	public final int castleHeight = 96; //Center of goal is 8ft
	public final int xOffset = 25; //Distance from camera to front of robot
	public final int yOffset = 12; //Camera hight from the ground in inches
	public final int totalHeight = castleHeight - yOffset;
	public final double targetWidth = 20; //Width of reflective tape
	public final double targetHeight = 14; //Height of reflective tape
	public final double targetRatio = targetWidth / targetHeight;
	
	//Comparator function for sorting particles. Returns true if particle 1 is larger
	public boolean CompareParticleSizes(ParticleReport particle1, ParticleReport particle2)
	{
		//we want descending sort order
		return particle1.PercentAreaToImageArea > particle2.PercentAreaToImageArea;
	}

	/**
	 * Converts a ratio with ideal value of 1 to a score. The resulting function is piecewise
	 * linear going from (0,0) to (1,100) to (2,0) and is 0 for all inputs outside the range 0-2
	 */
	public double ratioToScore(double ratio)
	{
		return (Math.max(0, Math.min(100*(1-Math.abs(1-ratio)), 100)));
	}

	public double AreaScore(ParticleReport report)
	{
		area = (report.BoundingRectBottom - report.BoundingRectTop) * (report.BoundingRectRight - report.BoundingRectLeft);
		//Tape is 12x20" edge so 240" bounding rect. With 2" wide tape it covers 80" of the rect.
		areaScore = ratioToScore((280/96)*report.Area/area);
		return areaScore;
	}
	
	public double getArea() {
		return area;
	}
	
	public double getAreaScore() {
		return areaScore;
	}

	/**
	 * Method to score if the aspect ratio of the particle appears to match the retro-reflective target. Target is 12"x20" so aspect should be 0.6
	 */
	public double AspectScore(ParticleReport report) {
		aspect = ratioToScore(((report.BoundingRectRight-report.BoundingRectLeft)/(report.BoundingRectBottom-report.BoundingRectTop)));
		return aspect;
	}
	
	public double getAspectRatio() {
		return aspect;
	}
	
	/**
	 * Computes the estimated distance to a target using the width of the particle in the image. For more information and graphics
	 * showing the math behind this approach see the Vision Processing section of the ScreenStepsLive documentation.
	 *
	 * @param frame The image to use for measuring the particle estimated rectangle
	 * @param report The Particle Analysis Report for the particle
	 * @return The estimated distance to the target in feet.
	 */
	public double computeDistance (Image frame, ParticleReport report) {
		double normalizedWidth, targetSize, ratio;
		NIVision.GetImageSizeResult size;
		
		size = NIVision.imaqGetImageSize(frame);
		ratio = (report.BoundingRectRight - report.BoundingRectLeft) / (report.BoundingRectTop - report.BoundingRectBottom);
		normalizedWidth = size.width / (report.BoundingRectRight - report.BoundingRectLeft);
		targetSize = 22; //size of target
		distance = targetSize * normalizedWidth / 12;
		return distance;
	}
	
	public double computeLinearDistance() {
		double distanceInches = (distance * 12);
		
		if(distanceInches < totalHeight) {
			linearDistance = (Math.sqrt((totalHeight*totalHeight) - (distanceInches*distanceInches))) / 12;
		}else {
			linearDistance = (Math.sqrt((distanceInches*distanceInches) - (totalHeight*totalHeight))) / 12;
		}
		return linearDistance;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public double getLinearDistance() {
		return linearDistance;
	}
	
	public double center (ParticleReport report) {
		center = (report.BoundingRectLeft + report.BoundingRectRight)/2;
		return center;
	}
	
	public double center() {
		return center;
	}
	
	public double leftBound (ParticleReport report) {
		left = report.BoundingRectLeft;
		return left;
	}
	
	public double leftBound() {
		return left;
	}
	
	public double rightBound (ParticleReport report) {
		right = report.BoundingRectRight;
		return right;
	}
	
	public double rightBound() {
		return right;
	}
	
	public double topBound (ParticleReport report) {
		top = report.BoundingRectTop;
		return top;
	}
	
	public double topBound() {
		return top;
	}
	
	public double bottomBound (ParticleReport report) {
		bottom = report.BoundingRectBottom;
		return bottom;
	}
	
	public double bottomBound() {
		return bottom;
	}
	
	//---------------------------------
	//Set filter settings
	//---------------------------------
	public void setHSV(int hueLow, int hueHigh, int satLow, int satHigh, int valLow, int valHigh) {
		setHueLow(hueLow);
		setHueHigh(hueHigh);
		setSatLow(satLow);
		setSatHigh(satHigh);
		setValueLow(valLow);
		setValueHigh(valHigh);
	}
	
	public void setHueLow(int hueLow) {
		hLow = hueLow;		
	}

	public void setHueHigh(int hueHigh) {
		hHigh = hueHigh;		
	}
	
	public void setSatLow(int satLow) {
		sLow = satLow;		
	}
	
	public void setSatHigh(int satHigh) {
		sHigh = satHigh;		
	}
	
	public void setValueLow(int valueLow) {
		vLow = valueLow;		
	}
	
	public void setValueHigh(int valueHigh) {
		vHigh = valueHigh;
	}
	
	public int getHueLow() {
		return hLow;
	}
	
	public int getHueHigh() {
		return hHigh;
	}
	
	public int getSatLow() {
		return sLow;
	}
	
	public int getSatHigh() {
		return sHigh;
	}
	
	public int getValueLow() {
		return vLow;
	}
	
	public int getValueHigh() {
		return vHigh;
	}
}

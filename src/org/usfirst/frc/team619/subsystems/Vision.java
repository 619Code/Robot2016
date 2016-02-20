/*
 * Used for tracking retro reflective tape
 * 
 */

package org.usfirst.frc.team619.subsystems;

import org.usfirst.frc.team619.subsystems.ParticleReport;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

public class Vision {
	
	private int hLow;
	private int hHigh;
	private int sLow;
	private int sHigh;
	private int vLow;
	private int vHigh;
	private double distanceInches;
	private double totalHeight;
	private final int castleHeight = 81;
	private final int cameraHeight = 11; // Camera hight from the ground in inches
	
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
		double boundingArea = (report.BoundingRectBottom - report.BoundingRectTop) * (report.BoundingRectRight - report.BoundingRectLeft);
		//Tape is 14x20" edge so 280" bounding rect. With 2" wide tape it covers 96" of the rect.
		return ratioToScore((280/88)*report.Area/boundingArea);
	}

	/**
	 * Method to score if the aspect ratio of the particle appears to match the retro-reflective target. Target is 14"x20" so aspect should be 0.7
	 */
	public double AspectScore(ParticleReport report)
	{
		return ratioToScore(((report.BoundingRectRight-report.BoundingRectLeft)/(report.BoundingRectBottom-report.BoundingRectTop)));
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
		double viewAngle;
		double normalizedWidth, targetWidth;
		NIVision.GetImageSizeResult size;

		size = NIVision.imaqGetImageSize(frame);
		normalizedWidth = 2*(report.BoundingRectRight - report.BoundingRectLeft)/size.width;
		targetWidth = 25.5;
		viewAngle = 90;

		return targetWidth/(normalizedWidth*12*Math.tan(viewAngle*Math.PI/(180*2)));
	}
	
	public double computeLinearDistance (double distance) {
		distanceInches = distance * 12;
		totalHeight = castleHeight - cameraHeight;
		
		if(distanceInches < totalHeight) {
			return Math.sqrt((totalHeight*totalHeight) - (distanceInches*distanceInches)) / 12;
		}
		return Math.sqrt((distanceInches*distanceInches) - (totalHeight*totalHeight)) / 12;
	}
	
	public double center (ParticleReport report) {
		return (report.BoundingRectLeft + report.BoundingRectRight)/2;
	}
	
	public double leftBound (ParticleReport report) {
		return report.BoundingRectLeft;
	}
	
	public double rightBound (ParticleReport report) {
		return report.BoundingRectRight;
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

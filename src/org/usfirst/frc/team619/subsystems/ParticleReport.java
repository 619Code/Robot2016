package org.usfirst.frc.team619.subsystems;

import java.util.Comparator;

public class ParticleReport implements Comparator<ParticleReport>, Comparable<ParticleReport> {
	double PercentAreaToImageArea;
	double Area;
	double BoundingRectLeft;
	double BoundingRectTop;
	double BoundingRectRight;
	double BoundingRectBottom;
	double Width;
			
	public int compareTo(ParticleReport r) {
		return (int)(r.Area - this.Area);
	}
			
	public int compare(ParticleReport r1, ParticleReport r2) {
		return (int)(r1.Area - r2.Area);
	}
	
	public double getAreaToImageArea() {
		return PercentAreaToImageArea;
	}
	
	public void setAreaToImageArea(double percent) {
		PercentAreaToImageArea = percent;
	}
	
	public void setArea(double area) {
		Area = area;
	}
	public void setLeftBounds(double leftBounds) {
		BoundingRectLeft = leftBounds;
	}
	
	public void setRightBounds(double rightBounds) {
		BoundingRectRight = rightBounds;
	}
	
	public void setTopBounds(double topBounds) {
		BoundingRectTop = topBounds;
	}
	
	public void setBottomBounds(double bottomBounds) {
		BoundingRectBottom = bottomBounds;
	}
	
	public double getArea() {
		return Area;
	}
	
	public double getLeftBounds() {
		return BoundingRectLeft;
	}
	
	public double getRightBounds() {
		return BoundingRectRight;
	}
	
	public double getTopBounds() {
		return BoundingRectTop;
	}
	
	public double getBottomBounds() {
		return BoundingRectBottom;
	}
}
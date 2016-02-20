package org.usfirst.frc.team619.subsystems;

import java.util.Comparator;

public class ParticleReport implements Comparator<ParticleReport>, Comparable<ParticleReport> {
	double PercentAreaToImageArea;
	double Area;
	double BoundingRectLeft;
	double BoundingRectTop;
	double BoundingRectRight;
	double BoundingRectBottom;
			
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
	
	public double getArea() {
		return Area;
	}
	
	public void setArea(double area) {
		Area = area;
	}
	
	public double getLeftBounds() {
		return BoundingRectLeft;
	}
	
	public void setLeftBounds(double leftBounds) {
		BoundingRectLeft = leftBounds;
	}
	
	public double getRightBounds() {
		return BoundingRectRight;
	}
	
	public void setRightBounds(double width) {
		BoundingRectRight = getLeftBounds() + width;
	}
	
	public double getTopBounds() {
		return BoundingRectTop;
	}
	
	public void setTopBounds(double topBounds) {
		BoundingRectTop = topBounds;
	}
	
	public double getBottomBounds() {
		return BoundingRectBottom;
	}
	
	public void setBottomBounds(double height) {
		BoundingRectBottom = getTopBounds() - height;
	}
}
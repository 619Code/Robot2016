package org.usfirst.frc.team619.logic.mapping;

import java.util.Vector;

import org.usfirst.frc.team619.logic.RobotThread;
import org.usfirst.frc.team619.logic.ThreadManager;
import org.usfirst.frc.team619.subsystems.ParticleReport;
import org.usfirst.frc.team619.subsystems.Vision;
import org.usfirst.frc.team619.subsystems.sensor.SensorBase;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionThread extends RobotThread {
	
	protected Vision vision;
	protected SensorBase sensorBase;
	
	private int m_id;
	private int index;
	private double start;
	private double AREA_MINIMUM = 0.1; //Default Area minimum for particle as a percentage of total image area
	private float areaMin = (float)AREA_MINIMUM;
	private Image frame; //Unfiltered image
	private Image binaryFrame; //filtered binary image
	private NIVision.Range GOAL_HUE_RANGE;	//Default hue range for green
	private NIVision.Range GOAL_SAT_RANGE;	//Default saturation range for green
	private NIVision.Range GOAL_VAL_RANGE;	//Default value range for green
	private NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	private NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);
	
	public VisionThread(SensorBase sensorBase, Vision vision, int period, ThreadManager threadManager) {
		super(period, threadManager);
		this.vision = vision;
		this.sensorBase = sensorBase;
		this.vision.setHSV(80, 120, 220, 255, 20, 80); //Find these values through testing. Corresponds with green
    	
		m_id = sensorBase.getSession();
		frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
		binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, AREA_MINIMUM, 100.0, 0, 0);
		
    	GOAL_HUE_RANGE = new NIVision.Range(vision.getHueLow(), vision.getHueHigh());	//Default hue range for green LEDs on reflective tape
    	GOAL_SAT_RANGE = new NIVision.Range(vision.getSatLow(), vision.getSatHigh());	//Default saturation range for green LEDs
    	GOAL_VAL_RANGE = new NIVision.Range(vision.getValueLow(), vision.getValueHigh());	//Default value range for green LEDs
	}
	
	protected void cycle(){
		start = System.currentTimeMillis();
		NIVision.IMAQdxGrab(m_id, frame, 1);
    	
		//Threshold the image looking for green
    	NIVision.imaqColorThreshold(binaryFrame, frame, 255, NIVision.ColorMode.HSV, GOAL_HUE_RANGE, GOAL_SAT_RANGE, GOAL_VAL_RANGE);
    		
		//Send particle count to dashboard
		index = NIVision.imaqCountParticles(binaryFrame, 1);
		SmartDashboard.putNumber("Masked particles", index);
		
		//CameraServer.getInstance().setImage(binaryFrame);

		//filter out small particles
		criteria[0].lower = areaMin;
		NIVision.imaqParticleFilter4(binaryFrame, binaryFrame, criteria, filterOptions, null);

		//Send particle count after filtering to dashboard
		index = NIVision.imaqCountParticles(binaryFrame, 1);
		SmartDashboard.putNumber("Filtered particles", index);
		
		if(index > 0)
		{
			//Measure particles and sort by particle size
			Vector<ParticleReport> particles = new Vector<ParticleReport>();
			for(int particleIndex = 0; particleIndex < index; particleIndex++)
			{
				ParticleReport par = new ParticleReport();
				par.setAreaToImageArea(NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA));
				par.setArea(NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA));
				par.setTopBounds(NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP));
				par.setLeftBounds(NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT));
				par.setBottomBounds(NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM));
				par.setRightBounds(NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT));
				particles.add(par);
			}
			particles.sort(null);
			
			//Use elementAt(0) for largest particle
			vision.AreaScore(particles.elementAt(0));
			vision.AspectScore(particles.elementAt(0));
			vision.computeDistance(binaryFrame, particles.elementAt(0));
			SmartDashboard.putNumber("Linear Distance", vision.computeLinearDistance());
			SmartDashboard.putNumber("Center", vision.center(particles.elementAt(0)));		
		}else {
			SmartDashboard.putNumber("Center", -1);
		}
    	
		start = System.currentTimeMillis() - start;
		//SmartDashboard.putNumber("Update Hz", 1000 / start);
	}
}
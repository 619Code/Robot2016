package org.usfirst.frc.team619.hardware;

import com.ni.vision.NIVision;

import edu.wpi.first.wpilibj.CameraServer;

public class Camera {

	CameraServer camera;
	
	private int session;
	String cameraName;
	
	/*this can be found by accessing the webdashboard with the camera plugged into Athena*/
	public Camera(String cameraName){
		this.cameraName = cameraName;
		camera = CameraServer.getInstance();
		camera.setQuality(50);
		camera.startAutomaticCapture(cameraName);
	}
	// Use ONLY with the setImage() method!!!
	public Camera() {
		session = NIVision.IMAQdxOpenCamera("cam0", 
				NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
        setExposure(0);
	}
	
	public void setExposure(int exposure) {
        long minv = NIVision.IMAQdxGetAttributeMinimumI64(session, "CameraAttributes::Exposure::Value");
        long maxv = NIVision.IMAQdxGetAttributeMaximumI64(session, "CameraAttributes::Exposure::Value");
        long val = minv + (long) (((double) (maxv - minv)) * (((double) exposure) / 100.0));
        
        NIVision.IMAQdxSetAttributeString(session, "CameraAttributes::Exposure::Mode", "Manual");
        NIVision.IMAQdxSetAttributeI64(session, "CameraAttributes::Exposure::Value", val);
	}
	
	public int getSession() {
		return session;
	}
	
	public void closeCamera() {
		NIVision.IMAQdxCloseCamera(session);
	}
	
	public boolean isOn(){
		return camera.isAutoCaptureStarted();
	}
	
	public int getQuality(){
		return camera.getQuality();
	}
	
	public String getName(){
		return cameraName;
	}
	
	public CameraServer getInstance(){
		return CameraServer.getInstance();
	}
	
	public void setOn(){
		camera.startAutomaticCapture(cameraName);
	}
	
	public void setQuality(int quality){
		camera.setQuality(quality);
	}
	
}

package org.usfirst.frc.team619.hardware;

import edu.wpi.first.wpilibj.Compressor;

public class PCMCompressor {

	Compressor comp;
	
	public PCMCompressor(int id){
		comp = new Compressor(id);
	}
	
	public Compressor getCompressor() {
		return comp;
	}
	
	public void start(){
		comp.start();
	}
	
	public void stop(){
		comp.stop();
	}
	
}

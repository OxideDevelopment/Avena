package me.justinp.avena.helpers;

import org.lwjgl.Sys;

public class Timer {

	private long lastTime;
	
	public Timer() {
		lastTime = getTime();
	}
	
	private long getTime() {
		return (Sys.getTime()* 1000) / Sys.getTimerResolution();
	}
	
	public int getDelta() {
		long currentTime = getTime();
		int delta = (int) (currentTime - lastTime);
		lastTime = currentTime;
		return delta;
	}
	
}

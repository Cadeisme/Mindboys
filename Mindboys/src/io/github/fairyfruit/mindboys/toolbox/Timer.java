package io.github.fairyfruit.mindboys.toolbox;

import org.lwjgl.glfw.GLFW;

/**
 * 
 * @author Gehrig Wilcox
 * @version 1.0
 * @since 2017-10-23
 *
 */
public class Timer {

	public static Timer instance;
	
	private double lastLoopTime;
	
	private float timeCount;
	
	private int fps;
	
	private int fpsCount;
	
	private int ups;
	
	private int upsCount;
	
	
	public Timer(){
		instance = this;
	}
	
	/**
	 * Initializes the timer
	 */
	public void init(){
		lastLoopTime = getTime();
	}
	
	/**
	 * Returns the time elapsed since <code>glfwInit()</code> in seconds
	 * 
	 * @return System time in seconds
	 */
	public double getTime(){
		return GLFW.glfwGetTime();
	}
	
	/**
	 * Returns the time that has passed since <code>getDelta()</code> was last called
	 * 
	 * @return Delta time in seconds
	 */
	public float getDelta(){
		double time = getTime();
		float delta = (float) (time - lastLoopTime);
		lastLoopTime = time;
		timeCount += delta;
		return delta;
	}
	
	/**
	 * Updates the FPS counter
	 */
	public void updateFPS(){
		fpsCount++;
	}
	
	/**
	 * Updates the UPS counter
	 */
	public void updateUPS(){
		upsCount++;
	}
	
	/**
	 * Updates FPS and UPS if a whole second has passed
	 */
	public void update(){
		if(timeCount > 1f){
			fps = fpsCount;
			fpsCount = 0;
			
			ups = upsCount;
			upsCount = 0;
			
			timeCount -= 1f;
		}
	}
	
	/**
	 * Getter for the FPS
	 * 
	 * @return Frames per second
	 */
	public int getFPS(){
		return fps > 0 ? fps : fpsCount;
	}
	
	/**
	 * Getter for the UPS
	 * 
	 * @return Updates per second
	 */
	public int getUPS(){
		return ups > 0 ? ups : upsCount;
	}
	
	/**
	 * Getter for the last loop time
	 * 
	 * @return System time of the last loop
	 */
	public double getLastLoopTime(){
		return lastLoopTime;
	}
	
	
}

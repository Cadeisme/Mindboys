package io.github.fairyfruit.mindboys.rendering;

import static org.lwjgl.opengl.GL11.*;

import io.github.fairyfruit.mindboys.toolbox.Timer;




/**
 * 
 * @author Gehrig Wilcox
 * @version 1.0
 * @since 2017-10-23
 *
 */
public class Renderer {
	
	public static void init(){
		glClearColor(1.0f, 0.0f, 0.0f, 0.0f); //Set clear color
	}
	
	public static void render(float alpha){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); //clear framebuffer
		

		//swap the color buffers
		Window.instance.swapBuffers();
		
		Timer.instance.updateFPS();
		
	}

}

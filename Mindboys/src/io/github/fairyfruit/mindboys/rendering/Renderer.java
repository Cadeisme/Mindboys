package io.github.fairyfruit.mindboys.rendering;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import io.github.fairyfruit.mindboys.rendering.shaders.ShaderProgram;
import io.github.fairyfruit.mindboys.toolbox.Timer;
import io.github.fairyfruit.mindboys.toolbox.Utils;




/**
 * 
 * @author Gehrig Wilcox
 * @version 1.0
 * @since 2017-10-23
 *
 */
public class Renderer {
	
	public static ShaderProgram shaderProgram;
	
	public static void init() throws Exception{
		
		shaderProgram = new ShaderProgram();
		
		shaderProgram.createVertexShader(Utils.loadResource("shaders/vertex.vs"));
		shaderProgram.createFragmentShader(Utils.loadResource("shaders/fragment.fs"));
		shaderProgram.link();
		
		glClearColor(1.0f, 0.0f, 0.0f, 0.0f); //Set clear color
	}
	
	public static void render(float alpha){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); //clear framebuffer
		

		//swap the color buffers
		Window.instance.swapBuffers();
		
		Timer.instance.updateFPS();
		
		
		//
	}
	
	public static void cleanup(){
		shaderProgram.cleanup();
	}

}

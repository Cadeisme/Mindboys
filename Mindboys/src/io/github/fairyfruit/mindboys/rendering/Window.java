package io.github.fairyfruit.mindboys.rendering;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.IntBuffer;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import io.github.fairyfruit.mindboys.main.Keymanager;
import io.github.fairyfruit.mindboys.settings.Settings;

/**
 * 
 * @author Gehrig Wilcox
 * @version 1.0
 * @since 2017-10-23
 *
 */
public class Window {

	public static Window instance;
	
	private long window;
	
	public Window(){
		
		instance = this;
		
		GLFWErrorCallback.createPrint(System.err).set();
		
		
		//Initialize GLFW
		if(!glfwInit()){
			throw new IllegalStateException("GLFW could not initialize");
		}
		
		//Configure GLFW
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);
		
		//create window
		window = glfwCreateWindow(Settings.width,Settings.height,Settings.WINDOW_NAME,NULL,NULL);
		if(window == NULL){
			throw new RuntimeException("Couldn't create GLFW window");
		}
		
		
		//Keymanager
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			
			if(key == Settings.keybindingEscape && action == GLFW_RELEASE){
				glfwSetWindowShouldClose(window, true);
			}
			
			
			
			int keyNum = 0;
			
			if(key == Settings.keybindingUp) keyNum = Keymanager.UP;
			if(key == Settings.keybindingDown) keyNum = Keymanager.DOWN;
			if(key == Settings.keybindingLeft) keyNum = Keymanager.LEFT;
			if(key == Settings.keybindingRight) keyNum = Keymanager.RIGHT;
			
			Keymanager.keys[keyNum] = action!=GLFW_RELEASE;
			
		});
		
		glfwSetWindowSizeCallback(window, (window, width, height) -> {
			MatrixHandlers.updateProjectionMatrix();
		});
		
		
		
		//Get the thread stack and push a new frame
		try(MemoryStack stack = stackPush()){
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*
			
			glfwGetWindowSize(window, pWidth, pHeight);
			
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			
			glfwSetWindowPos(window,
					(vidmode.width() - pWidth.get(0))/2,
					(vidmode.height() - pHeight.get(0))/2
			);
		} //stack frame is popped
		
		//make OpenGL context current
		glfwMakeContextCurrent(window);
		
		//Enable v-sync
		glfwSwapInterval(1);
		
		//make window visible
		glfwShowWindow(window);
		
	}
	
	public int getWidth(){
		
		try(MemoryStack stack = stackPush()){
		
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			glfwGetWindowSize(window, w, h);
			return w.get(0);
		
		}
	}
	
	public int getHeight(){
		
		try(MemoryStack stack = stackPush()){
			
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			glfwGetWindowSize(window, w, h);
			return h.get(0);
		
		}
	}
	
	public boolean shouldWindowClose(){
		return glfwWindowShouldClose(window);
	}
	
	public void swapBuffers(){
		glfwSwapBuffers(window);
	}
	
	public void pollEvents(){
		glfwPollEvents();
	}
	
	public void cleanup(){
		
		Renderer.cleanup();
		
		Callbacks.glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
}

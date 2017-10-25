package io.github.fairyfruit.mindboys.settings;

import org.lwjgl.glfw.GLFW;

/**
 * 
 * @author Gehrig Wilcox
 * @version 1.0
 * @since 2017-10-23
 *
 */

public class Settings {

	public static int width = 300;
	public static int height = 300;
	
	public static final String WINDOW_NAME = "Mindboys";
	
	public static final int UPS = 120;
	
	public static float FOV = (float) Math.toRadians(90);
	
	public static float Z_NEAR = 0.01f;
	public static float Z_FAR = 1000f;
	
	public static int keybindingUp = GLFW.GLFW_KEY_W;
	public static int keybindingDown = GLFW.GLFW_KEY_S;
	public static int keybindingLeft = GLFW.GLFW_KEY_A;
	public static int keybindingRight = GLFW.GLFW_KEY_D;
	public static int keybindingEscape = GLFW.GLFW_KEY_ESCAPE;
	
}

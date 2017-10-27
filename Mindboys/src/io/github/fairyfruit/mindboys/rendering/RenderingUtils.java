package io.github.fairyfruit.mindboys.rendering;

import org.lwjgl.opengl.GL11;

public class RenderingUtils {

	public static void enableWireframe(){
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
	}
	
}

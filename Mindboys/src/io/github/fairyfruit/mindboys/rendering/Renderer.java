package io.github.fairyfruit.mindboys.rendering;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import io.github.fairyfruit.mindboys.main.Game;
import io.github.fairyfruit.mindboys.rendering.materials.Texture;
import io.github.fairyfruit.mindboys.rendering.models.Mesh;
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
		
		shaderProgram.createUniform("modelViewMatrix");
		
		shaderProgram.createUniform("projectionMatrix");
		
		shaderProgram.createUniform("texture_sampler");
		
		glClearColor(1.0f, 0.0f, 0.0f, 0.0f); //Set clear color
	}
	
	public static void render(float alpha){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); //clear framebuffer
		
		shaderProgram.bind();
		
		MatrixHandlers.updateProjectionMatrix();
		
		shaderProgram.setUniform("projectionMatrix", MatrixHandlers.getProjectionMatrix());
		
		
		Matrix4f viewMatrix = MatrixHandlers.getViewMatrix(Game.instance.cam);
		
		
		Matrix4f modelViewMatrix = MatrixHandlers.getModelViewMatrix(Game.instance.go, viewMatrix);
		
		shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
		
		shaderProgram.setUniform("texture_sampler", 0);
		
		
		Game.instance.go.getMesh().prepareToRender();
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, Game.instance.go.getMesh().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		
		Game.instance.go.getMesh().cleanupAfterRender();
		
		
		shaderProgram.unbind();
		
		//swap the color buffers
		Window.instance.swapBuffers();
		
		Timer.instance.updateFPS();
		
		
		//
	}
	
	public static void cleanup(){
		Mesh.cleanup();
		Texture.cleanup();
		shaderProgram.cleanup();
	}

}

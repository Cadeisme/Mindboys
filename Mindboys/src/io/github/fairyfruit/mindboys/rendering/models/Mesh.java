package io.github.fairyfruit.mindboys.rendering.models;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

import io.github.fairyfruit.mindboys.rendering.materials.Material;
import io.github.fairyfruit.mindboys.rendering.materials.Texture;

public class Mesh {

	
	private final int vaoId;
	
	private final int vertVboId;
	
	private final int textCoordVboId;
	
	private final int normalVboId;
	
	private final int idxVboId;
	
	private final int vertexCount;
	
	private Material mat;
	
	public Mesh(float[] positions, float[] textCoords, float[] normals, int[] indices){
		FloatBuffer verticiesBuffer = null;
		FloatBuffer textCoordsBuffer = null;
		FloatBuffer normalBuffer = null;
		IntBuffer indicesBuffer = null;
		try{
			verticiesBuffer = MemoryUtil.memAllocFloat(positions.length);
			verticiesBuffer.put(positions).flip();
			
			textCoordsBuffer = MemoryUtil.memAllocFloat(textCoords.length);
			textCoordsBuffer.put(textCoords).flip();
			
			normalBuffer = MemoryUtil.memAllocFloat(normals.length);
			normalBuffer.put(normals).flip();
			
			indicesBuffer = MemoryUtil.memAllocInt(indices.length);
			indicesBuffer.put(indices).flip();
			
			vertexCount = indices.length;
			
			vaoId = VaoHandler.newVAO();
			VaoHandler.bind(vaoId);
			
			vertVboId = VboHandler.newVBO();
			VboHandler.bind(vertVboId);
			VboHandler.loadData(verticiesBuffer, 0, 3, false, 0, 0);
			
			textCoordVboId = VboHandler.newVBO();
			VboHandler.bind(textCoordVboId);
			VboHandler.loadData(textCoordsBuffer, 1, 2, false, 0, 0);
			
			normalVboId = VboHandler.newVBO();
			VboHandler.bind(normalVboId);
			VboHandler.loadData(normalBuffer, 2, 3, false, 0, 0);
			
			VboHandler.unbind();
			
			
			idxVboId = VboHandler.newVBO();
			VboHandler.bindIndices(idxVboId);
			VboHandler.loadIndices(indicesBuffer);
			
			
			VaoHandler.unbind();
		} finally {
			if (verticiesBuffer != null){
				MemoryUtil.memFree(verticiesBuffer);
			}
			if(textCoordsBuffer != null){
				MemoryUtil.memFree(textCoordsBuffer);
			}
			if(normalBuffer != null){
				MemoryUtil.memFree(normalBuffer);
			}
			if(indicesBuffer != null){
				MemoryUtil.memFree(indicesBuffer);
			}
		}
	}
	
	public void setMaterial(Material mat){
		this.mat = mat;
	}
	
	public Material getMaterial(){
		return mat;
	}
	
	public int getVaoId(){
		return vaoId;
	}
	
	public int getVertexCount(){
		return vertexCount;
	}
	
	public void prepareToRender(){
		VaoHandler.bind(vaoId);
		VaoHandler.enableArray(0);
		VaoHandler.enableArray(1);
		VaoHandler.enableArray(2);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mat.getTex().getId());
	}
	
	public void cleanupAfterRender(){
		VaoHandler.disableArray(0);
		VaoHandler.disableArray(1);
		VaoHandler.disableArray(2);
		VaoHandler.unbind();
		Texture.unbind();
	}
	
	public static void cleanup(){
		GL20.glDisableVertexAttribArray(0);
		
		VboHandler.cleanup();
		
		VaoHandler.cleanup();
	}
	
}

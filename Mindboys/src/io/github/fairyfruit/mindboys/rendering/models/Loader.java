package io.github.fairyfruit.mindboys.rendering.models;

import org.lwjgl.opengl.GL30;

public class Loader {
	
	public RawModel loadToVAO(float[] posistions){
		int vaoID = createVAO();
		storeDataInAttributeList(0,posistions);
		unbindVAO();
		return new RawModel(vaoID, posistions.length/3);
		
	}
	
	private int createVAO(){
		int vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	private void storeDataInAttributeList(int attributeNumber, float[] data){
		
	}
	
	private void unbindVAO(){
		GL30.glBindVertexArray(0);
	}

}

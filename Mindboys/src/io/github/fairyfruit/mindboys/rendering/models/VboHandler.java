package io.github.fairyfruit.mindboys.rendering.models;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

public class VboHandler {

	public static final ArrayList<Integer> vbos = new ArrayList<Integer>();
	
	public static int newVBO(){
		int vbo = GL15.glGenBuffers();
		vbos.add(vbo);
		return vbo;
	}
	
	public static void bind(int vbo){
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
	}
	
	public static void bindIndices(int vbo){
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vbo);
	}
	
	public static void unbind(){
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public static void unbindIndices(){
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public static void loadData(FloatBuffer b, int index, int size, boolean normalized, int stride, int pointer){
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, b, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, normalized, stride, pointer);
	}
	
	public static void loadIndices(IntBuffer b){
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, b, GL15.GL_STATIC_DRAW);
	}
	
	public static void destroy(int vbo){
		GL15.glDeleteBuffers(vbo);
	}
	
	public static void cleanup(){
		unbind();
		for(int v : vbos){
			destroy(v);
		}
		vbos.clear();
	}
}

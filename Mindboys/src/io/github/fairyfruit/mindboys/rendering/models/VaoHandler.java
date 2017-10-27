package io.github.fairyfruit.mindboys.rendering.models;

import java.util.ArrayList;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class VaoHandler {

	public static final ArrayList<Integer> vaos = new ArrayList<Integer>();
	
	public static int newVAO(){
		int vao = GL30.glGenVertexArrays();
		vaos.add(vao);
		return vao;
	}
	
	public static void bind(int vao){
		GL30.glBindVertexArray(vao);
	}
	
	public static void unbind(){
		GL30.glBindVertexArray(0);
	}
	
	public static void enableArray(int index){
		GL20.glEnableVertexAttribArray(index);
	}
	
	public static void disableArray(int index){
		GL20.glDisableVertexAttribArray(index);
	}
	
	public static void destroy(int vao){
		GL30.glDeleteVertexArrays(vao);
	}
	
	public static void cleanup(){
		unbind();
		for(int v : vaos){
			destroy(v);
		}
		vaos.clear();
	}
}

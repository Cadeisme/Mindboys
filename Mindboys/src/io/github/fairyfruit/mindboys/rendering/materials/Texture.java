package io.github.fairyfruit.mindboys.rendering.materials;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import io.github.fairyfruit.mindboys.toolbox.Utils;

public class Texture {

	public static Map<String,Texture> textures = new HashMap<String,Texture>();
	
	private final int id;
	
	private final int width;
	
	private final int height;
	
	private int numRows = 1;
	
	private int numCols = 1;
	
	
	public Texture(String fileName) throws IOException{
		
		this(Utils.ioResourceToByteBuffer(fileName, 1024));
	}
	
	public Texture(ByteBuffer imageData){
		try(MemoryStack stack = MemoryStack.stackPush()){
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer avChannels = stack.mallocInt(1);
			
			ByteBuffer decodedImage = STBImage.stbi_load_from_memory(imageData, w, h, avChannels, 4);
			
			this.width = w.get();
			this.height = h.get();
			
			this.id = GL11.glGenTextures();
			
			bind(id);
			
			GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT,1);
			

            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            
            loadData(width, height, decodedImage);
            
            GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		}
	}
	
	public int getNumCols(){
		return numCols;
	}
	
	public int getNumRows(){
		return numRows;
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public int getHeight(){
		return this.height;
	}
	
	public int getId(){
		return id;
	}
	
	public static void bind(int id){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}
	
	public static void unbind(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	public static void loadData(int width, int height, ByteBuffer pixels){
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
	}
	
	public static Texture getTexture(String file){
		
		Texture result = textures.get(file);
		
		if(result == null){
			try {
				result = new Texture(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public static void delete(Map.Entry<String, Texture> tex){
		GL11.glDeleteTextures(tex.getValue().id);
		textures.remove(tex.getKey(), tex.getValue());
	}
	
	public static void cleanup(){
		for(Map.Entry<String, Texture> tex : textures.entrySet()){
			delete(tex);
		}
	}
}

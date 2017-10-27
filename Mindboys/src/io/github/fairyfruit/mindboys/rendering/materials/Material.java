package io.github.fairyfruit.mindboys.rendering.materials;

import org.joml.Vector4f;

public class Material {

	public static final Vector4f DEFAULT_COLOR = new Vector4f(1.0f,1.0f,1.0f,1.0f);
	
	private final Vector4f ambient;
	
	private final Vector4f diffuse;
	
	private final Vector4f specular;
	
	private final Texture texture;
	
	private Texture normalMap;
	
	private final float reflectance;
	
	
	public Material(){
		this.ambient = DEFAULT_COLOR;
		this.diffuse = DEFAULT_COLOR;
		this.specular = DEFAULT_COLOR;
		this.texture = null;
		this.reflectance = 0.0f;
	}
	
	public Material(Vector4f ambient, Vector4f diffuse, Vector4f specular, String tex, float reflectance){
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
		this.reflectance = reflectance;
		
		if(tex!=null && tex.length() > 0 && Texture.textures.containsKey(tex)){
			this.texture = Texture.textures.get(tex);
		}else if(tex!=null && tex.length() > 0){
			this.texture = Texture.getTexture(tex);
		}else{
			this.texture = Texture.getTexture("error.png");
		}
	}
	
	public Texture getNormalMap(){
		return normalMap;
	}
	
	public void setNormalMap(Texture normalMap){
		this.normalMap = normalMap;
	}
	
	public boolean hasNormalMap(){
		return this.normalMap != null;
	}
	
	public Texture getTex(){
		return texture;
	}
	
	public Vector4f getAmbient(){
		return ambient;
	}
	
	public Vector4f getDiffuse(){
		return diffuse;
	}
	
	public Vector4f getSpecular(){
		return specular;
	}
	
	public float getReflectance(){
		return reflectance;
	}
	
}

package io.github.fairyfruit.mindboys.entities;

import org.joml.Vector3f;


/**
 * 
 * @author Gehrig Wilcox
 * @version 1.0
 * @since 2017-10-23
 *
 */
public class GameObject {
	
	private final Vector3f pos;
	private final Vector3f rot;
	private float scale;
	
	public GameObject(){
		this(0,0,0);
	}
	
	public GameObject(Vector3f pos){
		this(pos, new Vector3f(0,0,0), 1);
	}
	
	public GameObject(Vector3f pos, float scale){
		this(pos, new Vector3f(0,0,0), scale);
	}
	
	public GameObject(float x, float y, float z){
		this(new Vector3f(x,y,z));
	}
	
	public GameObject(Vector3f pos, Vector3f rot, float scale){
		this.pos = pos;
		this.rot = rot;
		this.scale = scale;
	}
	
	public float getScale(){
		return scale;
	}
	
	public void setScale(float scale){
		this.scale = scale;
	}
	
	public Vector3f getPos(){
		return pos;
	}
	
	public Vector3f getRot(){
		return rot;
	}
	
	public void setPos(float x, float y, float z){
		pos.x = x;
		pos.y = y;
		pos.z = z;
	}
	
	public void setRot(float x, float y, float z){
		rot.x = x;
		rot.y = y;
		rot.z = z;
	}
	
}

package io.github.fairyfruit.mindboys.rendering;

import org.joml.Vector3f;


/**
 * 
 * @author Gehrig Wilcox
 * @version 1.0
 * @since 2017-10-23
 *
 */
public class Camera {

	private final Vector3f position;
	
	private final Vector3f rotation;
	
	public Camera(){
		this(0,0,0);
	}
	
	public Camera(Vector3f position){
		this(position, new Vector3f(0,0,0));
	}
	
	public Camera(Vector3f position, Vector3f rotation){
		this.position = position;
		this.rotation = rotation;
	}
	
	public Camera(float x, float y, float z){
		this(new Vector3f(x,y,z));
	}
	
	public Vector3f getPosition(){
		return position;
	}
	
	public void setPosition(float x, float y, float z){
		position.x = x;
		position.y = y;
		position.z = z;
	}
	
	public void move(float x, float y, float z){
		
		if(z != 0){
			position.x -= (float)Math.sin(Math.toRadians(rotation.y)) * z;
			position.z += (float)Math.cos(Math.toRadians(rotation.y)) * z;
		}
		
		if(x != 0){
			position.x -= (float)Math.sin(Math.toRadians(rotation.y - 90)) * x;
			position.z += (float)Math.cos(Math.toRadians(rotation.y - 90)) * x;
		}
		
		position.y += y;
	}
	
	public Vector3f getRotation(){
		return rotation;
	}
	
	public void setRotation(float x, float y, float z){
		rotation.x = x;
		rotation.y = y;
		rotation.z = z;
	}
	
	public void moveRotation(float x, float y, float z){
		rotation.x += x;
		rotation.y += y;
		rotation.z += z;
	}
	
}

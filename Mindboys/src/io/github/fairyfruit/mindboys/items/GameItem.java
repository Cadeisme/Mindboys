package io.github.fairyfruit.mindboys.items;

import org.joml.Vector3f;

import io.github.fairyfruit.mindboys.rendering.models.Mesh;


public class GameItem {
	
	private Mesh[] meshes;
	
	private final Vector3f posistion;
	
	private float scale;
	
	private final Vector3f rotation;
	
	public GameItem(){
		posistion = new Vector3f(0,0,0);
		scale = 1;
		rotation = new Vector3f(0,0,0);
	}
	
	public GameItem(Mesh mesh){
		this();
		this.meshes = new Mesh[]{mesh};
	}
	
	public GameItem(Mesh[] meshes){
		this();
		this.meshes = meshes;
	}
	
	public Vector3f getPosistion(){
		return posistion;
	}
	
	public void setPosistion(float x, float y, float z){
		this.posistion.x = x;
		this.posistion.y = y;
		this.posistion.z = z;
	}
	
	public float getScale(){
		return scale;
	}
	
	public void setScale(float scale){
		this.scale = scale;
	}
	
	public Vector3f getRotation(){
		return rotation;
	}
	
	public void setRotation(float x, float y, float z){
		this.rotation.x = x;
		this.rotation.y = y;
		this.rotation.z = z;
	}
	
	public Mesh getMesh(){
		return meshes[0];
	}
	
	public Mesh[] getMeshes(){
		return meshes;
	}
	
	public void setMeshes(Mesh[] meshes) {
        this.meshes = meshes;
    }

    public void setMesh(Mesh mesh) {
        if (this.meshes != null) {
            for (Mesh currMesh : meshes) {
                currMesh.cleanup();
            }
        }
        this.meshes = new Mesh[]{mesh};
    }
	
}



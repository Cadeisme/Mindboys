package io.github.fairyfruit.mindboys.rendering;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import io.github.fairyfruit.mindboys.entities.GameObject;
import io.github.fairyfruit.mindboys.settings.Settings;

/**
 * 
 * @author Gehrig Wilcox
 * @version 1.0
 * @since 2017-10-23
 *
 */

public class MatrixHandlers {

	private static final Matrix4f projectionMatrix = new Matrix4f();
	private static final Matrix4f viewMatrix = new Matrix4f();
	private static final Matrix4f modelViewMatrix = new Matrix4f();
	
	
	
	public static void updateProjectionMatrix(){
		float aspectRatio = (float) Window.instance.getWidth() / Window.instance.getHeight();
		projectionMatrix.identity().perspective(Settings.FOV, aspectRatio, Settings.Z_NEAR, Settings.Z_FAR);
	}
		
	public static Matrix4f getProjectionMatrix(){
		return projectionMatrix;
	}
		
	public static Matrix4f getViewMatrix(Camera cam){
		Vector3f pos = cam.getPosition();
		Vector3f rot = cam.getRotation();
		
		viewMatrix.identity();
		
		viewMatrix.rotate((float)Math.toRadians(rot.x), new Vector3f(1,0,0))
		.rotate((float)Math.toRadians(rot.y), new Vector3f(0,1,0));
		viewMatrix.translate(-pos.x,-pos.y,-pos.z);
		return viewMatrix;
	}
		
	public static Matrix4f getModelViewMatrix(GameObject object, Matrix4f viewMatrix){
		Vector3f rotation = object.getRot();
		modelViewMatrix.identity().translate(object.getPos())
		.rotateX((float)Math.toRadians(-rotation.x))
		.rotateY((float)Math.toRadians(-rotation.y))
		.rotateZ((float)Math.toRadians(-rotation.z))
		.scale(object.getScale());
		Matrix4f viewCurr = new Matrix4f(viewMatrix);
		return viewCurr.mul(modelViewMatrix);
	}
}

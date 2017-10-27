package io.github.fairyfruit.mindboys.main;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import io.github.fairyfruit.mindboys.entities.GameObject;
import io.github.fairyfruit.mindboys.rendering.Camera;
import io.github.fairyfruit.mindboys.rendering.Renderer;
import io.github.fairyfruit.mindboys.rendering.Window;
import io.github.fairyfruit.mindboys.rendering.models.StaticMeshesLoader;
import io.github.fairyfruit.mindboys.settings.Settings;
import io.github.fairyfruit.mindboys.toolbox.Timer;


/**
 * 
 * @author Gehrig Wilcox
 * @version 1.0
 * @since 2017-10-23
 *
 */
public class Game {

	public static Game instance;
	
	public Camera cam;
	
	public GameObject go;
	
	public Game(){
		
		instance = this;
		
		new Window();
		
		new Timer();
		
		GL.createCapabilities();
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		try {
			Renderer.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		cam = new Camera();
		
		try {
			go = new GameObject(StaticMeshesLoader.load("lamp.obj", "")[0]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loop(){
		
		float delta;
		float accumulator = 0f;
		float interval = 1f / Settings.UPS;
		float alpha;
		
		while(!Window.instance.shouldWindowClose()){
			
			delta = Timer.instance.getDelta();
			accumulator += delta;
			
			Window.instance.pollEvents();
			
			while(accumulator >= interval){
				update();
				accumulator -= interval;
			}
			
			alpha = accumulator / interval;
			
			render(alpha);
			
			
			Timer.instance.update();
			
			System.out.println("FPS: " + Timer.instance.getFPS() + "\nUPS: " + Timer.instance.getUPS());
		}
	}
	
	
	private void update(){
		
		
		Timer.instance.updateUPS();
	}

	
	private void render(float alpha){
		Renderer.render(alpha);
	}
	
	
	public void cleanup(){
		Window.instance.cleanup();
	}
}

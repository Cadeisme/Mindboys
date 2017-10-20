package io.github.fairyfruit.mindboys.main;

import org.lwjgl.opengl.GL;

import io.github.fairyfruit.mindboys.rendering.Renderer;
import io.github.fairyfruit.mindboys.rendering.Window;
import io.github.fairyfruit.mindboys.settings.Settings;
import io.github.fairyfruit.mindboys.toolbox.Timer;

public class Game {

	public static Game instance;
	
	public Game(){
		
		instance = this;
		
		new Window();
		
		new Timer();
		
		GL.createCapabilities();
		
		Renderer.init();
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
			
			Renderer.render(alpha); //render
			
			
			Timer.instance.update();
			
			System.out.println("FPS: " + Timer.instance.getFPS() + "\nUPS: " + Timer.instance.getUPS());
		}
	}
	
	
	private void update(){
		
		
		Timer.instance.updateUPS();
	}
	
	
	public void cleanup(){
		Window.instance.cleanup();
	}
}

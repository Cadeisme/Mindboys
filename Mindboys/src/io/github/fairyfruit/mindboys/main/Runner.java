package io.github.fairyfruit.mindboys.main;


/**
 * 
 * @author Gehrig Wilcox
 * @version 1.0
 * @since 2017-10-23
 *
 */
public class Runner {

	
	public static void main(String[] args){
		
		new Game();
		
		Game.instance.loop();
		
		Game.instance.cleanup();
	}
}

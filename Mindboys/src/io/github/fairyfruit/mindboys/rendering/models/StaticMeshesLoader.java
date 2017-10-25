package io.github.fairyfruit.mindboys.rendering.models;

import org.lwjgl.assimp.AIScene;
import static org.lwjgl.assimp.Assimp.*;

public class StaticMeshesLoader {
	
	public static Mesh[] load(String resourcePath, String texturesDir) throws Exception{
		return load(resourcePath,texturesDir, aiProcess_JoinIdenticalVertices | aiProcess_Triangulate | aiProcess_FixInfacingNormals);
	}
	
	public static Mesh[]  load(String resourcePath, String texturesDir, int flags) throws Exception{
		AIScene aiScene = aiImportFile(resourcePath,flags);
		if(aiScene == null){
			throw new Exception("Error loading model");
		}
	}
	
}
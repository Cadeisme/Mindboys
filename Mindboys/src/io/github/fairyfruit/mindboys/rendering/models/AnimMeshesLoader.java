package io.github.fairyfruit.mindboys.rendering.models;

import static org.lwjgl.assimp.Assimp.*;

import org.lwjgl.assimp.AIScene;

public class AnimMeshesLoader extends StaticMeshesLoader{
	
	public static AnimGameItem loadAnimGameItem(String resourcePath, String texturesDir) throws Exception{
		return loadAnimGameItem(resourcePath, texturesDir, aiProcess_GenSmoothNormals | 
				aiProcess_JoinIdenticalVertices | aiProcess_Triangulate | aiProcess_FixInfacingNormals | aiProcess_LimitBoneWeights );
		
	}
	
	public static AnimGameItem loadAnimGameItem(String resourcePath, String texturesDir, int flags) throws Exception{
		AIScene aiScene = aiImportFile(resourcePath, flags);
		
		if(aiScene == null){
			throw new Exception("Error loading model");
		}
	}
	
	
}

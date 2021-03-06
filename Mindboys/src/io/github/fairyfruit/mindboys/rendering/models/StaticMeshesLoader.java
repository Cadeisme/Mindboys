package io.github.fairyfruit.mindboys.rendering.models;

import static org.lwjgl.assimp.Assimp.AI_MATKEY_COLOR_AMBIENT;
import static org.lwjgl.assimp.Assimp.AI_MATKEY_COLOR_DIFFUSE;
import static org.lwjgl.assimp.Assimp.AI_MATKEY_COLOR_SPECULAR;
import static org.lwjgl.assimp.Assimp.aiGetMaterialColor;
import static org.lwjgl.assimp.Assimp.aiImportFile;
import static org.lwjgl.assimp.Assimp.aiProcess_FixInfacingNormals;
import static org.lwjgl.assimp.Assimp.aiProcess_JoinIdenticalVertices;
import static org.lwjgl.assimp.Assimp.aiProcess_Triangulate;
import static org.lwjgl.assimp.Assimp.aiTextureType_DIFFUSE;
import static org.lwjgl.assimp.Assimp.aiTextureType_NONE;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector4f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIColor4D;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMaterial;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIString;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.assimp.Assimp;

import io.github.fairyfruit.mindboys.rendering.materials.Material;
import io.github.fairyfruit.mindboys.toolbox.Utils;

public class StaticMeshesLoader {
	
	
	
	public static Mesh[] load(String resourcePath, String texturesDir) throws Exception{
		
		return load(resourcePath,texturesDir, aiProcess_JoinIdenticalVertices | aiProcess_Triangulate | aiProcess_FixInfacingNormals);
		
	}
	
	
	
	public static Mesh[] load(String resourcePath, String texturesDir, int flags) throws Exception{
		
		
		AIScene aiScene = aiImportFile(Utils.getFilePath(resourcePath),flags);
		
		
		if(aiScene == null){
			throw new Exception("Error loading model");
		}
		
		
		int numMaterials = aiScene.mNumMaterials();
		
		PointerBuffer aiMaterials = aiScene.mMaterials();
		
		List<Material> materials = new ArrayList<>();
		
		for(int i = 0; i < numMaterials; i++){
			
			AIMaterial aiMaterial = AIMaterial.create(aiMaterials.get(i));
			
			processMaterial(aiMaterial, materials, texturesDir);
		}
		
		
		
		
		int numMeshes = aiScene.mNumMeshes();
		
		PointerBuffer aiMeshes = aiScene.mMeshes();
		
		Mesh[] meshes = new Mesh[numMeshes];
		
		
		for(int i = 0; i < numMeshes; i++){
			
			AIMesh aiMesh = AIMesh.create(aiMeshes.get(i));
			
			Mesh mesh = processMesh(aiMesh, materials);
			meshes[i] = mesh;
		}
		
		return meshes;
	}
	
	
	
	
	private static void processMaterial(AIMaterial aiMaterial, List<Material> materials, String texturesDir) throws Exception{
		
		AIColor4D color = AIColor4D.create();
		
		AIString path = AIString.calloc();
		
		
		Assimp.aiGetMaterialTexture(aiMaterial, aiTextureType_DIFFUSE, 0, path, 
				(IntBuffer) null, null, null, null, null, null);
		
		
		
		String textPath = path.dataString();
		
		Vector4f ambient = getMaterialColor(aiMaterial, color, AI_MATKEY_COLOR_AMBIENT);
		
		Vector4f diffuse = getMaterialColor(aiMaterial, color, AI_MATKEY_COLOR_DIFFUSE);
		
		Vector4f specular = getMaterialColor(aiMaterial, color, AI_MATKEY_COLOR_SPECULAR);
		
		
		Material material = new Material(ambient, diffuse, specular, textPath, 1.0f);
		
		materials.add(material);
		
		
	}
	
	
	
	private static Vector4f getMaterialColor(AIMaterial aiMaterial, 
			AIColor4D color, String matkey){
		
		if(aiGetMaterialColor(aiMaterial, matkey, aiTextureType_NONE, 0, color) == 0){
			return new Vector4f(color.r(),color.g(),color.b(),color.a());
		}
		
		return Material.DEFAULT_COLOR;
		
	}
	
	
	
	
	private static Mesh processMesh(AIMesh aiMesh, List<Material> materials){
		
		List<Float> vertices = new ArrayList<>();
		List<Float> textures = new ArrayList<>();
		List<Float> normals = new ArrayList<>();
		List<Integer> indices = new ArrayList<>();
		
		processVertices(aiMesh, vertices);
		processNormals(aiMesh, normals);
		processTextCoords(aiMesh, textures);
		processIndices(aiMesh, indices);
		
		
		
		Mesh mesh = new Mesh(Utils.listToArray(vertices),
				Utils.listToArray(textures),
				Utils.listToArray(normals),
				Utils.listIntToArray(indices));
		
		
		Material material;
		
		
		int materialIdx = aiMesh.mMaterialIndex();
		
		
		if(materialIdx >= 0 && materialIdx < materials.size()){
			
			material = materials.get(materialIdx);
			
		}else{
			
			material = new Material();
			
		}
		
		
		mesh.setMaterial(material);
		
		return mesh;
	}
	
	
	
	
	
	
	
	private static void processVertices(AIMesh aiMesh, List<Float> vertices){
		
		AIVector3D.Buffer aiVertices = aiMesh.mVertices();
		
		while(aiVertices.remaining() > 0){
			
			AIVector3D aiVertex = aiVertices.get();
			
			vertices.add(aiVertex.x());
			vertices.add(aiVertex.y());
			vertices.add(aiVertex.z());
		}
	}
	
	
	
	
	private static void processNormals(AIMesh aiMesh, List<Float> vertices){
		
		AIVector3D.Buffer aiNormals = aiMesh.mNormals();
		
		while(aiNormals != null && aiNormals.remaining() > 0){
			
			AIVector3D aiNormal = aiNormals.get();
			
			vertices.add(aiNormal.x());
			vertices.add(aiNormal.y());
			vertices.add(aiNormal.z());
		}
	}
	
	
	
	
	private static void processTextCoords(AIMesh aiMesh, List<Float> textures){
		
		AIVector3D.Buffer textCoords = aiMesh.mTextureCoords(0);
		
		int numTextCoords = textCoords != null ? textCoords.remaining() : 0;
		
		for(int i = 0; i < numTextCoords; i++){
			
			AIVector3D textCoord = textCoords.get();
			
			textures.add(textCoord.x());
			textures.add(1 - textCoord.y());
		}
	}
	
	
	
	
	private static void processIndices(AIMesh aiMesh, List<Integer> indices){
		
		int numFaces = aiMesh.mNumFaces();
		
		AIFace.Buffer aiFaces = aiMesh.mFaces();
		
		for(int i = 0; i < numFaces; i++){
			
			AIFace aiFace = aiFaces.get(i);
			
			IntBuffer buffer = aiFace.mIndices();
			
			while(buffer.remaining() > 0){
				
				indices.add(buffer.get());
			}
		}
	}
	
}
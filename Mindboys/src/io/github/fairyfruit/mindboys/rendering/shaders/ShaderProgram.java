package io.github.fairyfruit.mindboys.rendering.shaders;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

public class ShaderProgram {
	
	
	
	private final Map<String, Integer> uniforms;
	
	private final int programId;
	
	private int vertexShaderId, fragmentShaderId;
	
	
	
	
	public ShaderProgram() throws Exception{
		
		uniforms = new HashMap<>();
		
		programId = glCreateProgram();
		
		if(programId == 0){
			
			throw new Exception("Could not create shader");
			
		}
	}
	
	
	
	public void createVertexShader(String shaderCode) throws Exception{
		vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
	}
	
	
	public void createFragmentShader(String shaderCode) throws Exception{
		fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
	}
	
	
	
	protected int createShader(String shaderCode, int shaderType) throws Exception{
		
		int shaderId = glCreateShader(shaderType); //creates a shader of the input shaderType
		
		
		if(shaderId == 0){
			throw new Exception("Error creating " + shaderType + " shader");
		}
		
		
		glShaderSource(shaderId, shaderCode); //replaces source code in shader object
		glCompileShader(shaderId); //compiles the shader
		
		
		
		if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
        }
		
		
		
		 glAttachShader(programId, shaderId); //Attaches a shader object to the program object
		 
		 return shaderId;
	}
	
	
	
	
	
	
	public void createUniform(String uniformName) throws Exception{
		
		int uniformLocation = GL20.glGetUniformLocation(programId, uniformName);
		
		
		if(uniformLocation < 0){
			throw new Exception("Could not find uniform: " + uniformName);
		}
		
		
		uniforms.put(uniformName, uniformLocation);
	}
	
	
	
	public void setUniform(String uniformName, Matrix4f value){
		
		try(MemoryStack stack = MemoryStack.stackPush()){
			
			FloatBuffer fb = stack.mallocFloat(16);
			
			value.get(fb);
			
			GL20.glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
			
		}
	}
	
	
	public void setUniform(String uniformName, int value){
		GL20.glUniform1i(uniforms.get(uniformName), value);
	}
	
	
	
	
	
	public void link() throws Exception{
		
		glLinkProgram(programId); //creates a vertex and fragment executable
		
		
		if(glGetProgrami(programId, GL_LINK_STATUS) == 0){
			throw new Exception("Error linking Shader code: " + glGetProgramInfoLog(programId, 1024));
		}
		
		
		if(vertexShaderId !=0){
			glDetachShader(programId, vertexShaderId);
		}
		
		
		if(fragmentShaderId !=0){
			glDetachShader(programId, fragmentShaderId);
		}
		
		
		
		glValidateProgram(programId); //Remove when game reaches production stages
		
		
		if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
        }
		
		
	}
	
	
	
	
	
	public void bind(){
		glUseProgram(programId);
	}
	
	public void unbind(){
		glUseProgram(0);
	}
	
	public void cleanup(){
		unbind();
		if(programId !=0){
			glDeleteProgram(programId);
		}
	}
}

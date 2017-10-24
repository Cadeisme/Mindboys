package io.github.fairyfruit.mindboys.rendering.shaders;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {
	
	private final int programId;
	
	private int vertexShaderId, fragmentShaderId;
	
	public ShaderProgram() throws Exception{
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
		
		if(shaderType == 0){
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

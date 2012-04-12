package library.graphics;

import static android.opengl.GLES20.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import android.opengl.Matrix;
import android.util.Log;

public class Mesh extends Transform
{
	private int[] buffer;
	
	private int INDICES_COUNT;
	
	private int NORMAL_OFFSET;
	private int UV_OFFSET;
	
	private boolean isEmpty;
	private float hasTexture;
	
	private Texture2D texture;
	
	public Mesh()
	{
		isEmpty = true;
		
	}
	
	public Mesh(float[] vertices, float[] normals, float[] uv, int[] indices)
	{
		Log.e("Create Mesh", "Initialize");
		isEmpty = false;
		hasTexture = 0.0f;
		
		FloatBuffer vb = ByteBuffer.allocateDirect((vertices.length+normals.length+uv.length)*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		
		vb.put(vertices).put(normals).put(uv).position(0);
		
		NORMAL_OFFSET = vertices.length*4;
		UV_OFFSET = (vertices.length+normals.length)*4;
		
		IntBuffer ib = ByteBuffer.allocateDirect(indices.length*4).order(ByteOrder.nativeOrder()).asIntBuffer();
		ib.put(indices).position(0);
		
		INDICES_COUNT = ib.capacity();
		
		buffer = new int[2];
		
		glGenBuffers(buffer.length, buffer, 0);
		glBindBuffer(GL_ARRAY_BUFFER, buffer[0]);
		glBufferData(GL_ARRAY_BUFFER, vb.capacity()*4, vb, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, buffer[1]);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, ib.capacity()*4, ib, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		

//		vb.limit(0);
//		vb = null;
//		
//		ib.limit(0);
//		ib = null;
	}
	
	protected void prepare(int program, float[] modelView, float[] projection)
	{
		glBindBuffer(GL_ARRAY_BUFFER, buffer[0]);

		float[] mvInv = new float[16];
		Matrix.invertM(mvInv, 0, modelView, 0);
		float[] normalMatrix = new float[16];
		Matrix.transposeM(normalMatrix, 0, mvInv, 0);
		
		glUniform1f(glGetUniformLocation(program, "hasTexture"), hasTexture);
		
		if(hasTexture > 1.0f)
		{
			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, texture.buffer[0]);
			glUniform1i(glGetUniformLocation(program, "texture"), 0);
		}
		
		glUniformMatrix4fv(glGetUniformLocation(program, "modelViewMatrix"), 1, false, modelView, 0);
		glUniformMatrix4fv(glGetUniformLocation(program, "projectionMatrix"), 1, false, projection, 0);
		glUniformMatrix4fv(glGetUniformLocation(program, "normalMatrix"), 1, false, normalMatrix, 0);
		glUniformMatrix4fv(glGetUniformLocation(program, "transformationMatrix"), 1, false, worldTransform, 0);

		glEnableVertexAttribArray(glGetAttribLocation(program, "vertex"));
		glVertexAttribPointer(glGetAttribLocation(program, "vertex"), 3, GL_FLOAT, false, 0, 0);

		glEnableVertexAttribArray(glGetAttribLocation(program, "normal"));
		glVertexAttribPointer(glGetAttribLocation(program, "normal"), 3, GL_FLOAT, false, 0, NORMAL_OFFSET);
		
		glEnableVertexAttribArray(glGetAttribLocation(program, "uv"));
		glVertexAttribPointer(glGetAttribLocation(program, "uv"), 2, GL_FLOAT, false, 0, UV_OFFSET);
	}
	
	public void render(int program, float[] modelView, float[] projection)
	{
		if(!isEmpty)
		{
			prepare(program, modelView, projection);
			
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, buffer[1]);
			glDrawElements(GL_TRIANGLES, INDICES_COUNT, GL_UNSIGNED_INT, 0);
			
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
			
			glDisableVertexAttribArray(glGetAttribLocation(program, "vertex"));
			glDisableVertexAttribArray(glGetAttribLocation(program, "normal"));
			glDisableVertexAttribArray(glGetAttribLocation(program, "uv"));
			
			glBindTexture(GL_TEXTURE_2D, 0);
		}
		for (Transform child : children)
		{
			if(child instanceof Mesh)
			{
				((Mesh) child).render(program, modelView, projection);
			}
		}
	}
	
	public void setTexture2D(Texture2D texture)
	{
		hasTexture = 2.0f;
		this.texture = texture;
	}
}

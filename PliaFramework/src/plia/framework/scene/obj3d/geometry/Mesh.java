package plia.framework.scene.obj3d.geometry;

import static android.opengl.GLES20.GL_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_ELEMENT_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_STATIC_DRAW;
import static android.opengl.GLES20.glBindBuffer;
import static android.opengl.GLES20.glBufferData;
import static android.opengl.GLES20.glGenBuffers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Mesh extends Geometry
{
	private float[][] matrixPalette;
	private int matrixPaletteIndexOffset;
	
	public final int INDICES_COUNT;
	public final int NORMALS_OFFSET;
	public final int UV_OFFSET;
	
	private FloatBuffer fb;
	private IntBuffer ib;
	
	private float[] vertices;
	private float[] normals; 
	private float[] uv; 
	private int[] indices;
	
	public Mesh(float[] vertices, float[] normals, float[] uv, int[] indices)
	{
		super(Geometry.MESH);
		INDICES_COUNT = indices.length;
		NORMALS_OFFSET = vertices.length * 4;
		UV_OFFSET = (vertices.length + normals.length) * 4;
		
		init(vertices, normals, uv, indices);
	}
	
	protected Mesh(int type, float[] vertices, float[] normals, float[] uv, int[] indices)
	{
		super(type);
		INDICES_COUNT = indices.length;
		NORMALS_OFFSET = vertices.length * 4;
		UV_OFFSET = (vertices.length + normals.length) * 4;
		
		init(vertices, normals, uv, indices);
	}
	
	private void init(float[] vertices, float[] normals, float[] uv, int[] indices)
	{
		this.vertices = vertices;
		this.normals = normals;
		this.uv = uv;
		this.indices = indices;
	}
	
	public void resume()
	{
		int capacity = (vertices.length + vertices.length + uv.length) * 4;
		
		fb = ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder()).asFloatBuffer();
		fb.put(vertices).put(normals).put(uv).position(0);
		
		ib = ByteBuffer.allocateDirect(indices.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
		ib.put(indices).position(0);
		
		int[] buffers = new int[2];

		glGenBuffers(buffers.length, buffers, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
		glBufferData(GL_ARRAY_BUFFER, fb.capacity() * 4, fb, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, buffers[1]);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, ib.capacity() * 4, ib, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
		setBuffer(0, buffers[0]);
		setBuffer(1, buffers[1]);
	}
	
	public void setMatrixPalette(float[][] matrixPalette)
	{
		this.matrixPalette = matrixPalette;
	}
	
	public float[][] getMatrixPalette()
	{
		return matrixPalette;
	}
	
	public int getMatrixPaletteIndexOffset()
	{
		return matrixPaletteIndexOffset;
	}
	
	public void setMatrixPaletteIndexOffset(int matrixPaletteIndexOffset)
	{
		this.matrixPaletteIndexOffset = matrixPaletteIndexOffset;
	}
}

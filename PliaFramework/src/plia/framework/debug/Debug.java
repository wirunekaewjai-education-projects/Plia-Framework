package plia.framework.debug;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;

import plia.framework.math.Vector3;
import plia.framework.scene.BoundingPlane;
import plia.framework.scene.BoundingSphere;
import plia.framework.scene.Bounds;
import plia.framework.scene.Scene;
import plia.framework.scene.obj3d.shading.Color3;
import plia.framework.scene.obj3d.shading.Shader;
import plia.framework.scene.obj3d.shading.ShaderProgram;

public final class Debug
{
	private static float[] debugLineVerticesTemp = new float[6];
	private FloatBuffer debugLine;
	private DebugLineBox debugLineBox;
	private DebugLineSphere debugLineSphere;
	private DebugMeshSphere debugMeshSphere;
	
	private int[] buffers = new int[2];
	
	private static final float[] mvpTemp = new float[16];
	
	private Debug()
	{
		debugLine = ByteBuffer.allocateDirect(24).order(ByteOrder.nativeOrder()).asFloatBuffer();
		
		debugLineBox = DebugLineBox.getInstance();
		debugLineSphere = DebugLineSphere.getInstance();
		debugMeshSphere = DebugMeshSphere.getInstance();
	}
	
	public static void drawLine(Vector3 start, Vector3 end, Color3 color)
	{
		ShaderProgram sprogram = Shader.AMBIENT.getProgram(0);
		int program = sprogram.getProgramID();

		debugLineVerticesTemp[0] = start.x;
		debugLineVerticesTemp[1] = start.y;
		debugLineVerticesTemp[2] = start.z;
		
		debugLineVerticesTemp[3] = end.x;
		debugLineVerticesTemp[4] = end.y;
		debugLineVerticesTemp[5] = end.z;
		
		instance.debugLine.clear();
		instance.debugLine.put(debugLineVerticesTemp).position(0);
		
		GLES20.glUseProgram(program);
		
		int vh = GLES20.glGetAttribLocation(program, "vertex");
		
		GLES20.glEnableVertexAttribArray(vh);
		GLES20.glVertexAttribPointer(vh, 3, GLES20.GL_FLOAT, false, 0, instance.debugLine);
		
		GLES20.glUniform4f(GLES20.glGetUniformLocation(program, "color"), color.r, color.g, color.b, 1);
		
		float[] mvp = new float[16];
		Scene.getModelViewProjectionMatrix().copyTo(mvp);
		GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(program, "modelViewProjectionMatrix"), 1, false, mvp, 0);
		
		GLES20.glLineWidth(1);
		GLES20.glDrawArrays(GLES20.GL_LINES, 0, 2);
		
		GLES20.glDisableVertexAttribArray(vh);
	}
	
//	public static void drawBounds(Bounds bounds, float[] mvp, float r, float g, float b)
//	{
//		if(bounds instanceof BoundingBox)
//		{
//			instance.drawBoundingBox((BoundingBox) bounds, mvp, r, g, b);
//		}
//	}
	
//	private void drawBoundingBox(BoundingBox bounds, float[] mvp, float r, float g, float b)
//	{
//		int program = Shader.AMBIENT_SHADER.getShaderProgram(2);
//
//		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
//		GLES20.glUseProgram(program);
//
//		GLES20.glUniform3f(GLES20.glGetUniformLocation(program, "center"), bounds.getCenter().x, bounds.getCenter().y, bounds.getCenter().z);
//		GLES20.glUniform3f(GLES20.glGetUniformLocation(program, "size"), bounds.getSize().x, bounds.getSize().y, bounds.getSize().z);
//		GLES20.glUniform4f(GLES20.glGetUniformLocation(program, "color"), r, g, b, 1);
//		GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(program, "modelViewProjectionMatrix"), 1, false, mvp, 0);
//
//		int vh = GLES20.glGetAttribLocation(program, "vertex");
//
//		GLES20.glEnableVertexAttribArray(vh);
//		GLES20.glVertexAttribPointer(vh, 3, GLES20.GL_FLOAT, false, 0, debugLineBox.getVerticesBuffer());
//		
//		GLES20.glLineWidth(2);
//
//		GLES20.glDrawElements(GLES20.GL_LINES, debugLineBox.getIndicesBuffer().capacity(), GLES20.GL_UNSIGNED_INT, debugLineBox.getIndicesBuffer());
//		GLES20.glDisableVertexAttribArray(vh);
//	}
	
	private static void drawBoundingPlane(BoundingPlane bounds)
	{
		
	}
	
	private static void drawBoundingSphere(BoundingSphere bounds)
	{
		
	}

	private static Debug instance = new Debug();
	public static Debug getInstance()
	{
		return instance;
	}
}

package plia.debug;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;

//import plia.framework.math.Matrix3;
import plia.math.Matrix4;
import plia.math.Vector3;
import plia.scene.Collider;
import plia.scene.PlaneCollider;
import plia.scene.Scene;
import plia.scene.SphereCollider;
import plia.scene.shading.Color3;
import plia.scene.shading.Shader;
import plia.scene.shading.ShaderProgram;

public final class Debug
{
	private static float[] debugLineVerticesTemp = new float[6];
	private FloatBuffer debugLine;
//	private DebugLineBox debugLineBox;
//	private DebugLineSphere debugLineSphere;
//	private DebugMeshSphere debugMeshSphere;
//	
//	private int[] buffers = new int[2];
//	
//	private static final float[] mvpTemp = new float[16];
	
	private Debug()
	{
		debugLine = ByteBuffer.allocateDirect(24).order(ByteOrder.nativeOrder()).asFloatBuffer();
		
//		debugLineBox = DebugLineBox.getInstance();
//		debugLineSphere = DebugLineSphere.getInstance();
//		debugMeshSphere = DebugMeshSphere.getInstance();
	}
	
	public static void drawLine(Vector3 start, Vector3 end, Color3 color)
	{
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		
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

	public static void drawBounds(Collider collider, Color3 color)
	{
//		if(bounds instanceof BoundingBox)
//		{
//			instance.drawBoundingBox((BoundingBox) bounds, mvp, r, g, b);
//		}
		
		if(collider instanceof PlaneCollider)
		{
			drawBoundingPlane((PlaneCollider) collider, color);
		}
		else if(collider instanceof SphereCollider)
		{
			drawBoundingSphere((SphereCollider) collider, color);
		}
	}
	
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
	
	private static void drawBoundingPlane(PlaneCollider bounds, Color3 color)
	{
		Vector3 p0 = bounds.getPoint(0), p1 = bounds.getPoint(1), p2 = bounds.getPoint(2), p3 = bounds.getPoint(3);
		drawLine(p0, p1, color);
		drawLine(p1, p2, color);
		drawLine(p2, p3, color);
		drawLine(p3, p0, color);
	}
	
	private static void drawBoundingSphere(SphereCollider bounds, Color3 color)
	{
		float radius = bounds.getRadius();
		
		Matrix4 world = bounds.getWorldMatrix();
		Matrix4 sceneMV = Scene.getModelViewMatrix();
		Matrix4 sceneP = Scene.getProjectionMatrix();
		Matrix4 mvM = Matrix4.multiply(sceneMV, world);

		float[] mv = new float[16];
		float[] p = new float[16];
		mvM.copyTo(mv);
		sceneP.copyTo(p);
		
		Vector3 eye = Scene.getMainCamera().getWorldMatrix().getTranslation();
		Vector3 pos = world.getTranslation();
		
		drawLine(new Vector3(eye.x, eye.y, eye.z - 10), pos, new Color3(1, 0, 0));
		
		// Draw Wire-Sphere
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
				
		int wsphere_program = Shader.AMBIENT.getProgram(5).getProgramID();
		GLES20.glUseProgram(wsphere_program);
		
		int wsphere_vertex_handle = GLES20.glGetAttribLocation(wsphere_program, "vertex");
		int wsphere_color_handle = GLES20.glGetUniformLocation(wsphere_program, "color");
		int wsphere_mv_handle = GLES20.glGetUniformLocation(wsphere_program, "modelViewMatrix");
		int wsphere_p_handle = GLES20.glGetUniformLocation(wsphere_program, "projectionMatrix");
		int wsphere_eye = GLES20.glGetUniformLocation(wsphere_program, "eye");
		int wsphere_position = GLES20.glGetUniformLocation(wsphere_program, "position");
		int wsphere_radius = GLES20.glGetUniformLocation(wsphere_program, "radius");
		
		GLES20.glEnableVertexAttribArray(wsphere_vertex_handle);
		GLES20.glVertexAttribPointer(wsphere_vertex_handle, 3, GLES20.GL_FLOAT, false, 0, DebugLineSphere.getVB());

		GLES20.glUniform1f(wsphere_radius, radius);
		GLES20.glUniform3f(wsphere_eye, eye.x, eye.y, eye.z);
		GLES20.glUniform4f(wsphere_position, pos.x, pos.y, pos.z, 1);
		GLES20.glUniform4f(wsphere_color_handle, color.r, color.g, color.b, 1);
		GLES20.glUniformMatrix4fv(wsphere_mv_handle, 1, false, mv, 0);
		GLES20.glUniformMatrix4fv(wsphere_p_handle, 1, false, p, 0);

		GLES20.glLineWidth(1.5f);

		GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, 64);
		GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 64, 64);
		GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 128, 64);

		GLES20.glDisableVertexAttribArray(wsphere_vertex_handle);
		
		
		//
		int esphere_program = Shader.AMBIENT.getProgram(6).getProgramID();
		GLES20.glUseProgram(esphere_program);
		
		int esphere_vertex_handle = GLES20.glGetAttribLocation(esphere_program, "vertex");
		int esphere_color_handle = GLES20.glGetUniformLocation(esphere_program, "color");
		int esphere_mv_handle = GLES20.glGetUniformLocation(esphere_program, "modelViewMatrix");
		int esphere_p_handle = GLES20.glGetUniformLocation(esphere_program, "projectionMatrix");
		int esphere_eye = GLES20.glGetUniformLocation(esphere_program, "eye");
		int esphere_position = GLES20.glGetUniformLocation(esphere_program, "position");
		int esphere_radius = GLES20.glGetUniformLocation(esphere_program, "radius");
		
		// Draw Edge-Sphere
//		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
//		GLES20.glDisable(GLES20.GL_DEPTH_TEST);
//		GLES20.glEnable(GLES20.GL_BLEND);
//		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

		GLES20.glUniform1f(esphere_radius, radius);
		GLES20.glUniform3f(esphere_eye, eye.x, eye.y, eye.z);
		GLES20.glUniform4f(esphere_position, pos.x, pos.y, pos.z, 1);
		GLES20.glUniform4f(esphere_color_handle, color.r, color.g, color.b, 1);
		
		sceneMV.copyTo(mv);
		GLES20.glUniformMatrix4fv(esphere_mv_handle, 1, false, mv, 0);
		GLES20.glUniformMatrix4fv(esphere_p_handle, 1, false, p, 0);

		GLES20.glEnableVertexAttribArray(esphere_vertex_handle);
		GLES20.glVertexAttribPointer(esphere_vertex_handle, 3, GLES20.GL_FLOAT, false, 0, DebugMeshSphere.getVB());

		GLES20.glDrawElements(GLES20.GL_TRIANGLES, DebugMeshSphere.getInstance().getIndicesCount(), GLES20.GL_UNSIGNED_INT, DebugMeshSphere.getIB());

		GLES20.glDisableVertexAttribArray(esphere_vertex_handle);

//		GLES20.glDisable(GLES20.GL_BLEND);
//		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
	}

	private static Debug instance = new Debug();
	public static Debug getInstance()
	{
		return instance;
	}
}

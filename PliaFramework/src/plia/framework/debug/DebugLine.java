package plia.framework.debug;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

class DebugLine
{
	private FloatBuffer buffer;

	private DebugLine()
	{
		buffer = ByteBuffer.allocateDirect(24).order(ByteOrder.nativeOrder()).asFloatBuffer();
	}
	
	public void put(float startX, float startY, float startZ, float endX, float endY, float endZ)
	{
		buffer.clear().position(0);
		buffer.put(startX).put(startY).put(startZ).put(endX).put(endY).put(endZ).position(0);
	}
	
	public FloatBuffer getBuffer()
	{
		return buffer;
	}

	private static DebugLine instance = new DebugLine();
	static DebugLine getInstance()
	{
		return instance;
	}
}

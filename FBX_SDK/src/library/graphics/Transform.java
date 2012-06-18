package library.graphics;

import java.util.Vector;

import android.opengl.Matrix;

public class Transform
{
	// Fields
	private Vector3 _position = null;
	private Vector3 _angle = null;
	private Vector3 _scale = null;
	
	float[] worldTransform = null;
	float[] transform = null;
	float[] rotate = null;
	Quaternion a;
	Quaternion ax, ay, az;
	float rx, ry, rz;
	
	float roll, yaw, pitch;
	
	Vector<Transform> children = null;
	Transform parent = null;
	
	
	public Transform()
	{
		_position = new Vector3();
		_angle = new Vector3();
		_scale = new Vector3(1, 1, 1);
		
		worldTransform = new float[16];
		transform = new float[16];
		rotate = new float[16];

		Matrix.setIdentityM(worldTransform, 0);
		Matrix.setIdentityM(transform, 0);
		Matrix.setIdentityM(rotate, 0);
		
		ax = Quaternion.createFromAxisAngle(Vector3.left, 0);
		ay = Quaternion.createFromAxisAngle(Vector3.up, 0);
		az = Quaternion.createFromAxisAngle(Vector3.forward, 0);
		
		a = new Quaternion(0, 0, 0, 0);
		
		Matrix.setIdentityM(temp, 0);
		
		children = new Vector<Transform>();
		
		tempMatrix = new float[16];
		Matrix.setIdentityM(tempMatrix, 0);
	}
	
	float[] temp = new float[16];
	private float[] tempMatrix = null;
	void update()
	{

		float[] tmp = new float[16];
		
		Vector3 pos = getPosition();
		Matrix.setIdentityM(tmp, 0);
		Matrix.translateM(tmp, 0, pos.x, pos.y, pos.z);

		float[] aInv = new float[16];
		Matrix.invertM(aInv, 0, a.toMatrix(), 0);
		Matrix.multiplyMM(worldTransform, 0, tmp, 0, aInv, 0);
		
		if(parent != null)
		{
			Matrix.multiplyMM(worldTransform, 0, parent.worldTransform, 0, worldTransform, 0);
		}
		
		for (Transform child : children)
		{
			child.update();
		}
	}

	
	public void translate(float x, float y, float z)
	{
		Vector3 ford = getForward();
		Vector3 left = getLeft();
		Vector3 up = getUp();

		float dx = (x*left.x)+(y*up.x)+(z*ford.x);
		float dy = (x*left.y)+(y*up.y)+(z*ford.y);
		float dz = (x*left.z)+(y*up.z)+(z*ford.z);
		
		Matrix.translateM(transform, 0, dx, dy, dz);
	}

	public void rotate(float angle, float x , float y, float z)
	{
		roll += angle*x;
		yaw += angle*y;
		pitch += angle*z;
		
		a = Quaternion.createFromYawPitchRoll(yaw, pitch, roll);
	}
	
	public void scale(float x, float y, float z)
	{
		Matrix.scaleM(transform, 0, x, y, z);
	}
	
	public void setPosition(float x, float y, float z)
	{
		Matrix.setIdentityM(transform, 0);
		Matrix.translateM(transform, 0, x, y, z);
	}

	public void setAngle(float x, float y, float z)
	{
		roll = x;
		yaw = y;
		pitch = z;
		
		a = Quaternion.createFromYawPitchRoll(yaw, pitch, roll);
	}
	
	public Vector3 getPosition()
	{
		Matrix.multiplyMV(_pos, 0, transform, 0, _position_multiplier, 0);
		
		_position.x = _pos[0];
		_position.y = _pos[1];
		_position.z = _pos[2];
		
		return _position;
	}

	public Vector3 getEulerAngle()
	{
		
		return _angle;
	}

	public Vector3 getScale()
	{
		return _scale;
	}

	
	
	public boolean addChild(Transform child)
	{
		if(!children.contains(child))
		{
			children.add(child);
			
			child.parent = this;
			return true;
		}
		
		return false;
	}
	
	public boolean removeChild(Transform child)
	{
		if(children.contains(child))
		{
			children.remove(child);
			child.parent = null;
			return true;
		}
		
		return false;
	}
	
	
	
	
	// Direction
	private Vector3 getDirection(float[] dir)
	{
		float[] ford = new float[4];
		Matrix.multiplyMV(ford, 0, a.toMatrix(), 0, dir, 0);

		return new Vector3(ford);
	}

	public Vector3 getForward()
	{
		return getDirection(_forward);
	}

	public Vector3 getBackward()
	{
		return getDirection(_backward);
	}

	public Vector3 getUp()
	{
		return getDirection(_up);
	}

	public Vector3 getDown()
	{
		return getDirection(_down);
	}

	public Vector3 getLeft()
	{
		return getDirection(_left);
	}

	public Vector3 getRight()
	{
		return getDirection(_right);
	}

	// Classifier Fields
	private static float[] _pos = new float[16];
	
	private static final float[] _position_multiplier = { 0,0,0,1 };
	
	private static final float[] _forward = new float[] { 0, 0, 1, 0 };
	private static final float[] _backward = new float[] { 0, 0, -1, 0 };
	private static final float[] _left = new float[] { 1, 0, 0, 0 };
	private static final float[] _right = new float[] { -1, 0, 0, 0 };
	private static final float[] _up = new float[] { 0, 1, 0, 0 };
	private static final float[] _down = new float[] { 0, -1, 0, 0 };
}

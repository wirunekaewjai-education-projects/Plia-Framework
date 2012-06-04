package plia.framework.gameobject;

import java.util.ArrayList;

import plia.framework.gameobject.component.Bounds;
import plia.framework.math.Mathf;
import plia.framework.math.Matrix;
import plia.framework.math.Vector3;

public class Object3D extends Node<Object3D>
{
	// Transform
	private float tx, ty, tz;
	private float sx, sy, sz;

	private float rot11, rot12, rot13, rot21, rot22, rot23, rot31, rot32,
			rot33;

	private final float[] global = new float[16];
	
	private final ArrayList<Bounds> boundsList = new ArrayList<Bounds>();
	
	private static final Vector3 temp1 = new Vector3();
	private static final Vector3 temp2 = new Vector3();
	private static final Vector3 temp3 = new Vector3();

	public Object3D()
	{
		super();
		initialize();
	}
	
	public Object3D(String name)
	{
		super(name);
		initialize();
	}
	
	private void initialize()
	{
		this.sx = 1;
		this.sy = 1;
		this.sz = 1;

		this.rot11 = 1;
		this.rot22 = 1;
		this.rot33 = 1;

		this.global[0] = 1;
		this.global[5] = 1;
		this.global[10] = 1;
		this.global[15] = 1;
	}
	
	public void update()
	{
		// Update Local in this Method
		// TODO
		this.global[0] = this.rot11 * sx;
		this.global[1] = this.rot12 * sy;
		this.global[2] = this.rot13 * sz;

		this.global[4] = this.rot21 * sx;
		this.global[5] = this.rot22 * sy;
		this.global[6] = this.rot23 * sz;

		this.global[8] = this.rot31 * sx;
		this.global[9] = this.rot32 * sy;
		this.global[10] = this.rot33 * sz;
		
		this.global[12] = this.tx * sx;
		this.global[13] = this.ty * sy;
		this.global[14] = this.tz * sz;
		// //
		
		// Update Global in Other Method
		this.updateGlobal();
		// //
	}

	private void updateGlobal()
	{
		if(!isRoot)
		{
			Matrix.multiply(global, parent.global, global);
		}

		// Recursive Child to Update Global
		for (int i = 0; i < getChildCount(); i++)
		{
			Object3D child = getChild(i);
			child.updateGlobal();
		}
	}
	
	public void enqueue()
	{
//		Updater.getInstance().enqueue(this);
	}
	
	// Getter / Setter
	public float[] getGlobalTransform()
	{
		return global;
	}
	
	// // Trasform
	public Vector3 getPosition()
	{
		return new Vector3(tx, ty, tz);
	}
	
	public Vector3 getEulerAngles()
	{
		float ax, ay, az;

		if (rot12 > 0.998f)
		{
			// singularity at north pole
			ax = 0;
			ay = Mathf.atan2(rot31, rot22);
			az = Mathf.PI / 2;
		} else if (rot12 < -0.998f)
		{
			// singularity at south pole
			ax = 0;
			ay = Mathf.atan2(rot31, rot22);
			az = -Mathf.PI / 2;
		} else
		{
			ax = Mathf.atan2(-rot32, rot22);
			ay = Mathf.atan2(-rot13, rot11);
			az = Mathf.asin(rot12);
		}

		return new Vector3(ax, ay, az);
	}
	
	public Vector3 getScale()
	{
		return new Vector3(sx, sy, sz);
	}

	public Vector3 getForward()
	{
		return new Vector3(rot21, rot22, rot23);
	}

	public Vector3 getUp()
	{
		return new Vector3(rot31, rot32, rot33);
	}

	public Vector3 getRight()
	{
		return new Vector3(rot11, rot12, rot13);
	}
	
	public void setPosition(float x, float y, float z)
	{
		this.tx = x;
		this.ty = y;
		this.tz = z;
		
		this.enqueue();
	}
	
	public void setEulerAngles(float x, float y, float z)
	{
		// Heading ( Yaw :: Y Axis ) ; First
		float ch = Mathf.cos(y);
		float sh = Mathf.sin(y);

		// Attitude ( Pitch :: Z Axis ) ; Second
		float ca = Mathf.cos(z);
		float sa = Mathf.sin(z);

		// Bank ( Roll :: X Axis ) ; Last
		float cb = Mathf.cos(x);
		float sb = Mathf.sin(x);

		this.rot11 = (ch * ca);
		this.rot12 = sa;
		this.rot13 = (-sh * ca);

		this.rot21 = (sh * sb - ch * sa * cb);
		this.rot22 = (ca * cb);
		this.rot23 = (sh * sa * cb + ch * sb);

		this.rot31 = (ch * sa * sb + sh * cb);
		this.rot32 = (-ca * sb);
		this.rot33 = (-sh * sa * sb + ch * cb);
		
		this.enqueue();
	}
	
	public void setScale(float x, float y, float z)
	{
		this.sx = x;
		this.sy = y;
		this.sz = z;
		
		this.enqueue();
	}
	
	public void setForward(float x, float y, float z)
	{
		temp1.x = x;
		temp1.y = y;
		temp1.z = z;
		Vector3.normalize(temp2, temp1); // create new-forward
		
		if(temp2.x != rot21 && temp2.y != rot22 && temp2.z != rot23)
		{
			temp1.x = rot11;
			temp1.y = rot12;
			temp1.z = rot13; // create this.right
			
			// this.right cross new-forward
			Vector3.cross(temp3, temp1, temp2); // get new-up
			
			// new-forward cross new-up
			Vector3.cross(temp1, temp2, temp3); // get new-right
			
			this.rot11 = temp1.x;
			this.rot12 = temp1.y;
			this.rot13 = temp1.z;
	
			this.rot21 = temp2.x;
			this.rot22 = temp2.y;
			this.rot23 = temp2.z;
	
			this.rot31 = temp3.x;
			this.rot32 = temp3.y;
			this.rot33 = temp3.z;
			
			this.enqueue();
		}
	}

	public void setUp(float x, float y, float z)
	{
		temp1.x = x;
		temp1.y = y;
		temp1.z = z;
		Vector3.normalize(temp3, temp1); // create new-up
		
		if(temp3.x != rot31 && temp3.y != rot32 && temp3.z != rot33)
		{
			temp2.x = rot21;
			temp2.y = rot22;
			temp2.z = rot23; // create this.forward
			
			// this.forward cross new-up
			Vector3.cross(temp1, temp2, temp3); // get new-right
			
			// new-up cross new-right
			Vector3.cross(temp2, temp3, temp1); // get new-right
			
			this.rot11 = temp1.x;
			this.rot12 = temp1.y;
			this.rot13 = temp1.z;
	
			this.rot21 = temp2.x;
			this.rot22 = temp2.y;
			this.rot23 = temp2.z;
	
			this.rot31 = temp3.x;
			this.rot32 = temp3.y;
			this.rot33 = temp3.z;
			
			this.enqueue();
		}
	}

	public void setRight(float x, float y, float z)
	{
		temp2.x = x;
		temp2.y = y;
		temp2.z = z;
		Vector3.normalize(temp1, temp2); // create new-right
		
		if(temp1.x != rot11 && temp1.y != rot12 && temp1.z != rot13)
		{
			temp3.x = rot31;
			temp3.y = rot32;
			temp3.z = rot33; // create this.up
			
			// this.up cross new-right
			Vector3.cross(temp2, temp3, temp1); // get new-forward
			
			// new-right cross new-forward
			Vector3.cross(temp3, temp1, temp2); // get new-up
			
			this.rot11 = temp1.x;
			this.rot12 = temp1.y;
			this.rot13 = temp1.z;
	
			this.rot21 = temp2.x;
			this.rot22 = temp2.y;
			this.rot23 = temp2.z;
	
			this.rot31 = temp3.x;
			this.rot32 = temp3.y;
			this.rot33 = temp3.z;
			
			this.enqueue();
		}
	}
	
	public void setLookAt(float x, float y, float z)
	{
		float lx = x - tx;
		float ly = y - ty;
		float lz = z - tz;

		setForward(lx, ly, lz);
	}
	
	public void translate(float x, float y, float z)
	{
		tx += (x * rot11) + (y * rot21) + (z * rot31);
		ty += (x * rot12) + (y * rot22) + (z * rot32);
		tz += (x * rot13) + (y * rot23) + (z * rot33);
		
		this.enqueue();
	}

	public void translateWorld(float x, float y, float z)
	{
		tx += x;
		ty += y;
		tz += z;
		
		this.enqueue();
	}

	public void rotate(float x, float y, float z)
	{
		// Heading ( Yaw :: Y Axis ) ; First
		float ch = Mathf.cos(y);
		float sh = Mathf.sin(y);

		// Attitude ( Pitch :: Z Axis ) ; Second
		float ca = Mathf.cos(z);
		float sa = Mathf.sin(z);

		// Bank ( Roll :: X Axis ) ; Last
		float cb = Mathf.cos(x);
		float sb = Mathf.sin(x);

		float newRot11 = ch * ca;
		float newRot12 = sa;
		float newRot13 = -sh * ca;

		float newRot21 = sh * sb - ch * sa * cb;
		float newRot22 = ca * cb;
		float newRot23 = sh * sa * cb + ch * sb;

		float newRot31 = ch * sa * sb + sh * cb;
		float newRot32 = -ca * sb;
		float newRot33 = -sh * sa * sb + ch * cb;

		float result11 = (this.rot11 * newRot11) + (this.rot12 * newRot21) + (this.rot13 * newRot31);
		float result12 = (this.rot11 * newRot12) + (this.rot12 * newRot22) + (this.rot13 * newRot32);
		float result13 = (this.rot11 * newRot13) + (this.rot12 * newRot23) + (this.rot13 * newRot33);

		float result21 = (this.rot21 * newRot11) + (this.rot22 * newRot21) + (this.rot23 * newRot31);
		float result22 = (this.rot21 * newRot12) + (this.rot22 * newRot22) + (this.rot23 * newRot32);
		float result23 = (this.rot21 * newRot13) + (this.rot22 * newRot23) + (this.rot23 * newRot33);

		float result31 = (this.rot31 * newRot11) + (this.rot32 * newRot21) + (this.rot33 * newRot31);
		float result32 = (this.rot31 * newRot12) + (this.rot32 * newRot22) + (this.rot33 * newRot32);
		float result33 = (this.rot31 * newRot13) + (this.rot32 * newRot23) + (this.rot33 * newRot33);

		this.rot11 = result11;
		this.rot12 = result12;
		this.rot13 = result13;

		this.rot21 = result21;
		this.rot22 = result22;
		this.rot23 = result23;

		this.rot31 = result31;
		this.rot32 = result32;
		this.rot33 = result33;
		
		this.enqueue();
	}

	public void rotateAround(float x, float y, float z)
	{
		// Heading ( Yaw :: Y Axis ) ; First
		float ch = Mathf.cos(y);
		float sh = Mathf.sin(y);

		// Attitude ( Pitch :: Z Axis ) ; Second
		float ca = Mathf.cos(z);
		float sa = Mathf.sin(z);

		// Bank ( Roll :: X Axis ) ; Last
		float cb = Mathf.cos(x);
		float sb = Mathf.sin(x);

		float newRot11 = ch * ca;
		float newRot12 = sa;
		float newRot13 = -sh * ca;

		float newRot21 = sh * sb - ch * sa * cb;
		float newRot22 = ca * cb;
		float newRot23 = sh * sa * cb + ch * sb;

		float newRot31 = ch * sa * sb + sh * cb;
		float newRot32 = -ca * sb;
		float newRot33 = -sh * sa * sb + ch * cb;

		float result11 = (newRot11 * this.rot11) + (newRot12 * this.rot21) + (newRot13 * this.rot31);
		float result12 = (newRot11 * this.rot12) + (newRot12 * this.rot22) + (newRot13 * this.rot32);
		float result13 = (newRot11 * this.rot13) + (newRot12 * this.rot23) + (newRot13 * this.rot33);

		float result21 = (newRot21 * this.rot11) + (newRot22 * this.rot21) + (newRot23 * this.rot31);
		float result22 = (newRot21 * this.rot12) + (newRot22 * this.rot22) + (newRot23 * this.rot32);
		float result23 = (newRot21 * this.rot13) + (newRot22 * this.rot23) + (newRot23 * this.rot33);

		float result31 = (newRot31 * this.rot11) + (newRot32 * this.rot21) + (newRot33 * this.rot31);
		float result32 = (newRot31 * this.rot12) + (newRot32 * this.rot22) + (newRot33 * this.rot32);
		float result33 = (newRot31 * this.rot13) + (newRot32 * this.rot23) + (newRot33 * this.rot33);

		this.rot11 = result11;
		this.rot12 = result12;
		this.rot13 = result13;

		this.rot21 = result21;
		this.rot22 = result22;
		this.rot23 = result23;

		this.rot31 = result31;
		this.rot32 = result32;
		this.rot33 = result33;
		
		this.enqueue();
	}
	
	public int getBoundsCount()
	{
		return boundsList.size();
	}
	
	public Bounds getBounds(int index)
	{
		return boundsList.get(index);
	}
	
	public void addBounds(Bounds bounds)
	{
		this.boundsList.add(bounds);
	}
	
	public void removeBounds(Bounds bounds)
	{
		this.boundsList.remove(bounds);
	}
}


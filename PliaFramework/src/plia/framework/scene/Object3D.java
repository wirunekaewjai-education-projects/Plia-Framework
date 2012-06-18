package plia.framework.scene;

import plia.framework.math.Matrix3;
import plia.framework.math.Matrix4;
import plia.framework.math.Vector3;
import plia.framework.scene.obj3d.Camera;
import plia.framework.scene.obj3d.Light;
import plia.framework.scene.obj3d.Model;

public class Object3D extends Node<Object3D>
{
	protected Object3D parent = null;
	
	protected Vector3 localTranslation = new Vector3();
	protected Vector3 localScaling = new Vector3(1, 1, 1);
	protected Matrix3 localRotation = new Matrix3();
	
	private Matrix4 local = new Matrix4();
	private Matrix4 world = new Matrix4();
	
	protected boolean hasChanged = false;
	
	private Bounds bounds = null;

	protected Object3D()
	{
		
	}
	
	public Model asModel()
	{
		if(this instanceof Model)
		{
			return (Model) this;
		}
		
		return null;
	}
	
	public Camera asCamera()
	{
		if(this instanceof Camera)
		{
			return (Camera) this;
		}
		
		return null;
	}
	
	public Light asLight()
	{
		if(this instanceof Light)
		{
			return (Light) this;
		}
		
		return null;
	}
	
	public Bounds getBounds()
	{
		return bounds;
	}
	
	public void setBounds(Bounds bounds)
	{
		if(bounds != null)
		{
			removeChild(this.bounds);
			this.bounds = bounds;
			addChild(this.bounds);
		}
	}
	
	@Override
	protected void update()
	{
		this.updateTransform(false);
	}
	
	protected void updateTransform(boolean parentHasChanged)
	{
		if(isActive())
		{
			if(hasChanged)
			{
				this.local.m11 = localRotation.m11 * localScaling.x;
				this.local.m12 = localRotation.m12 * localScaling.y;
				this.local.m13 = localRotation.m13 * localScaling.z;

				this.local.m21 = localRotation.m21 * localScaling.x;
				this.local.m22 = localRotation.m22 * localScaling.y;
				this.local.m23 = localRotation.m23 * localScaling.z;

				this.local.m31 = localRotation.m31 * localScaling.x;
				this.local.m32 = localRotation.m32 * localScaling.y;
				this.local.m33 = localRotation.m33 * localScaling.z;
				
				this.local.m41 = localTranslation.x;
				this.local.m42 = localTranslation.y;
				this.local.m43 = localTranslation.z;

				this.hasChanged = false;
				
				parentHasChanged = true;
			}
				
			if(parentHasChanged && parent != null)
			{
				Matrix4.multiply(world, getWorldMatrix(), local);
			}
			
			for (int i = 0; i < childCount; i++)
			{
				Object3D child = (Object3D) children[i];
				child.updateTransform(parentHasChanged);
			}
		}
	}

	
	// Node
	public final boolean isRoot()
	{
		return parent == null;
	}

	public final Object3D getRoot()
	{
		return findRoot(this);
	}

	private Object3D findRoot(Object3D node)
	{
		Object3D parent = (Object3D) node.parent;
		
		if(parent != null)
		{
			return findRoot(parent);
		}
		
		return node;
	}
	
	
	public final Object3D getParent()
	{
		return (Object3D) parent;
	}

	@Override
	public final boolean addChild(Object3D child)
	{
		if(child.parent == null)
		{
			boolean b = super.addChild(child);
			if(b)
			{
				child.parent = this;
			}
			
			return b;
		}
		return false;
	}

	@Override
	public final boolean removeChild(Object3D child)
	{
		boolean b = super.removeChild(child);
		
		if(b)
		{
			child.parent = null;
		}
		
		return b;
	}

	public Matrix4 getWorldMatrix()
	{
		if(isRoot())
		{
			return local;
		}
		
		return world;
	}
	
	public Vector3 getPosition()
	{
		return new Vector3(localTranslation);
	}
	
	public Matrix3 getRotation()
	{
		return new Matrix3(localRotation);
	}
	
	public Vector3 getScale()
	{
		return new Vector3(localScaling);
	}
	
	public Vector3 getEulerAngles()
	{
		return localRotation.getEulerAngles();
	}
	
	public Vector3 getRight()
	{
		return localRotation.getRight();
	}
	
	public Vector3 getForward()
	{
		return localRotation.getForward();
	}
	
	public Vector3 getUp()
	{
		return localRotation.getUp();
	}
	
	public void setPosition(Vector3 position)
	{
		this.localTranslation.set(position);
		this.hasChanged = true;
	}
	
	public void setScale(Vector3 scale)
	{
		this.localScaling.set(scale);
		this.hasChanged = true;
	}
	
	public void setEulerAngles(Vector3 eulerAngles)
	{
		this.localRotation.setEulerAngles(eulerAngles.x, eulerAngles.y, eulerAngles.z);
		this.hasChanged = true;
	}
	
	public void setRight(Vector3 right)
	{
		this.localRotation.setRight(right);
		this.hasChanged = true;
	}
	
	public void setForward(Vector3 forward)
	{
		this.localRotation.setForward(forward);
		this.hasChanged = true;
	}
	
	public void setUp(Vector3 up)
	{
		this.localRotation.setUp(up);
		this.hasChanged = true;
	}
	
	public void setLookAt(Vector3 target)
	{
		this.localRotation.setForward(Vector3.subtract(target, localTranslation));
		this.hasChanged = true;
	}
	
	public void translate(float x, float y, float z)
	{
		this.localTranslation.x += (x * localRotation.m11) + (y * localRotation.m21) + (z * localRotation.m31);
		this.localTranslation.y += (x * localRotation.m12) + (y * localRotation.m22) + (z * localRotation.m32);
		this.localTranslation.z += (x * localRotation.m13) + (y * localRotation.m23) + (z * localRotation.m33);
		this.hasChanged = true;
	}
	
	public void translate(float x, float y, float z, boolean relativeWorld)
	{
		if(relativeWorld)
		{
			this.localTranslation.x += x;
			this.localTranslation.y += y;
			this.localTranslation.z += z;
		}
		else
		{
			this.localTranslation.x += (x * localRotation.m11) + (y * localRotation.m21) + (z * localRotation.m31);
			this.localTranslation.y += (x * localRotation.m12) + (y * localRotation.m22) + (z * localRotation.m32);
			this.localTranslation.z += (x * localRotation.m13) + (y * localRotation.m23) + (z * localRotation.m33);
		}
		
		this.hasChanged = true;
	}
	
	public void rotate(float x, float y, float z)
	{
		this.localRotation = Matrix3.createFromEulerAngles(x, y, z).multiply(localRotation);
		this.hasChanged = true;
	}
	
	public void rotate(float x, float y, float z, boolean relativeWorld)
	{
		if(relativeWorld)
		{
			Matrix3 rotation = Matrix3.createFromEulerAngles(x, y, z);
			this.localRotation = localRotation.multiply(rotation);
			this.localTranslation = rotation.multiply(localTranslation);
		}
		else
		{
			this.localRotation = Matrix3.createFromEulerAngles(x, y, z).multiply(localRotation);
		}
		
		this.hasChanged = true;
	}
}

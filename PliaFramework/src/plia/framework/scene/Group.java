package plia.framework.scene;

import plia.framework.core.AnimationPlayer;
import plia.framework.core.GameObject;
import plia.framework.math.Matrix3;
import plia.framework.math.Matrix4;
import plia.framework.math.Vector3;

public class Group extends Node<Group>
{
	protected Group parent = null;
	
	protected Vector3 localTranslation = new Vector3();
	protected Vector3 localScaling = new Vector3(1, 1, 1);
	protected Matrix3 localRotation = new Matrix3();
	
	private Matrix4 axisRotation = new Matrix4();
	
	private Matrix4 local = new Matrix4();
	private Matrix4 world = new Matrix4();
	private Matrix4 invParent = new Matrix4();
	
	protected boolean hasChanged = true;
	
	private Bounds bounds = null;

	public Group()
	{
		
	}
	
	public Group(String name)
	{
		super(name);
	}
	
	@Override
	protected void copyTo(GameObject gameObject)
	{
		super.copyTo(gameObject);
		
		Group group = (Group) gameObject;
		group.localTranslation.set(this.localTranslation);
		group.localRotation = this.localRotation.clone();
		group.localScaling.set(this.localScaling);
		group.world = this.world.clone();
		group.invParent = this.world.clone();
		group.axisRotation = this.axisRotation.clone();
		
		if(this.bounds != null)
		{
			group.bounds = this.bounds.instantiate();
		}
	}
	
	@Override
	public Group instantiate()
	{
		Group group = new Group();
		this.copyTo(group);
		return group;
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
		this.onUpdateHierarchy(false);
	}
	
	protected void onUpdateHierarchy(boolean parentHasChanged)
	{
		if(isActive())
		{
			if(hasAnimation && animation != null)
			{
				AnimationPlayer.getInstance().enqueue(animation);
			}

			if(this.hasChanged)
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
				Matrix4.multiply(world, parent.getWorldMatrix(), local);
				invParent = parent.getWorldMatrix().getInvert();
			}
			
			for (int i = 0; i < childCount; i++)
			{
				Group child = (Group) children[i];
				child.onUpdateHierarchy(parentHasChanged);
			}
		}
	}

	
	// Node
	public final boolean isRoot()
	{
		return parent == null;
	}

	public final Group getRoot()
	{
		return findRoot(this);
	}

	private Group findRoot(Group node)
	{
		Group parent = (Group) node.parent;
		
		if(parent != null)
		{
			return findRoot(parent);
		}
		
		return node;
	}
	
	
	public final Group getParent()
	{
		return (Group) parent;
	}

	@Override
	public final boolean addChild(Group child)
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
	
	public final void addChild(Group...children)
	{
		for (Group child : children)
		{
			addChild(child);
		}
	}

	@Override
	public final boolean removeChild(Group child)
	{
		boolean b = super.removeChild(child);
		
		if(b)
		{
			child.parent = null;
		}
		
		return b;
	}
	
	public Matrix4 getAxisRotation()
	{
		return axisRotation;
	}
	
	public void setAxisRotation(Matrix4 axisRotation)
	{
		this.axisRotation = axisRotation;
	}

	public Matrix4 getWorldMatrix()
	{
		if(parent == null)
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
	
	public void setPosition(float x, float y, float z)
	{
		this.localTranslation.set(x, y, z);
		this.hasChanged = true;
	}
	
	public void setScale(Vector3 scale)
	{
		this.localScaling.set(scale);
		this.hasChanged = true;
	}
	
	public void setScale(float x, float y, float z)
	{
		this.localScaling.set(x, y, z);
		this.hasChanged = true;
	}
	
	public void setEulerAngles(Vector3 eulerAngles)
	{
		this.localRotation.setEulerAngles(eulerAngles.x, eulerAngles.y, eulerAngles.z);
		this.hasChanged = true;
	}
	
	public void setEulerAngles(float x, float y, float z)
	{
		this.localRotation.setEulerAngles(x, y, z);
		this.hasChanged = true;
	}
	
	public void setRight(Vector3 right)
	{
		this.localRotation.setRight(right);
		this.hasChanged = true;
	}
	
	public void setRight(float x, float y, float z)
	{
		this.localRotation.setRight(x, y, z);
		this.hasChanged = true;
	}
	
	public void setForward(Vector3 forward)
	{
		this.localRotation.setForward(forward);
		this.hasChanged = true;
	}
	
	public void setForward(float x, float y, float z)
	{
		this.localRotation.setForward(x, y, z);
		this.hasChanged = true;
	}
	
	public void setUp(Vector3 up)
	{
		this.localRotation.setUp(up);
		this.hasChanged = true;
	}
	
	public void setUp(float x, float y, float z)
	{
		this.localRotation.setUp(x, y, z);
		this.hasChanged = true;
	}
	
	public void setLookAt(Vector3 target)
	{
		Vector3 forward = Vector3.subtract(target, localTranslation);

		this.hasChanged = true;
		
		forward = forward.getNormalized();

		this.localRotation.m21 = forward.x;
		this.localRotation.m22 = forward.y;
		this.localRotation.m23 = forward.z;
	}
	
	public void setLookAt(Group target)
	{
		this.localRotation.setForward(Vector3.subtract(target.localTranslation, localTranslation));
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
			Matrix4 world = getWorldMatrix();
			world.m41 += x;
			world.m42 += y;
			world.m43 += z;
			
			Matrix4 inv = Matrix4.multiply(invParent, world);
			
			this.localTranslation.x = inv.m41;
			this.localTranslation.y = inv.m42;
			this.localTranslation.z = inv.m43;
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
			Matrix4 rotation = Matrix4.createFromEulerAngles(x, y, z);
			Matrix4 world = getWorldMatrix();
			world.multiply(rotation);
			
			Matrix4 inv = Matrix4.multiply(invParent, world);
			
			this.localRotation = inv.toMatrix3();
			this.localTranslation.set(inv.getTranslation());
			
//			Matrix3 rotation = Matrix3.createFromEulerAngles(x, y, z);
//			this.localRotation = localRotation.multiply(rotation);
//			this.localTranslation = rotation.multiply(localTranslation);
		}
		else
		{
			this.localRotation = Matrix3.createFromEulerAngles(x, y, z).multiply(localRotation);
		}
		
		this.hasChanged = true;
	}
}

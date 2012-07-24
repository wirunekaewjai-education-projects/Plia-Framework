package plia.core.scene;

import plia.core.AnimationPlayer;
import plia.core.GameObject;
import plia.math.Matrix3;
import plia.math.Matrix4;
import plia.math.Vector3;

public class Group extends Node<Group>
{
	protected Group parent = null;
	
//	protected Vector3 localTranslation = new Vector3();
	protected Vector3 localScaling = new Vector3(1, 1, 1);
//	protected Matrix3 localRotation = new Matrix3();
	
	private Matrix4 axisRotation = new Matrix4();
	
//	private Matrix4 local = new Matrix4();
	private Matrix4 world = new Matrix4();
	Matrix4 invParent = new Matrix4();
	
	protected boolean hasChanged = true;
	
	Collider collider = null;

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
//		group.localTranslation.set(this.localTranslation);
//		group.localRotation = this.localRotation.clone();
		group.localScaling.set(this.localScaling);
		group.world = this.world.clone();
		group.invParent = this.invParent.clone();
		group.axisRotation = this.axisRotation.clone();
		
		if(this.collider != null)
		{
			group.setCollider((Collider) this.collider.instantiate());
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

	public Collider getCollider()
	{
		return collider;
	}
	
	public void setCollider(Collider collider)
	{
		if(collider != null)
		{
			removeChild(this.collider);
			this.collider = collider;
			addChild(this.collider);
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

//			if(this.hasChanged)
//			{
//				this.local.m11 = localRotation.m11 * localScaling.x;
//				this.local.m12 = localRotation.m12 * localScaling.y;
//				this.local.m13 = localRotation.m13 * localScaling.z;
//
//				this.local.m21 = localRotation.m21 * localScaling.x;
//				this.local.m22 = localRotation.m22 * localScaling.y;
//				this.local.m23 = localRotation.m23 * localScaling.z;
//
//				this.local.m31 = localRotation.m31 * localScaling.x;
//				this.local.m32 = localRotation.m32 * localScaling.y;
//				this.local.m33 = localRotation.m33 * localScaling.z;
//				
//				this.local.m41 = localTranslation.x;
//				this.local.m42 = localTranslation.y;
//				this.local.m43 = localTranslation.z;
//
//				this.hasChanged = false;
//				parentHasChanged = true;
//				
//				if(collider != null)
//				{
//					collider.calTerrainChanged = true;
//				}
//			}
//	
//			if(parent != null)
//			{
//				Matrix4.multiply(world, parent.getWorldMatrix(), local);
//				invParent = parent.getWorldMatrix().getInvert();
//			}
			
			if(this.hasChanged || parentHasChanged)
			{
				if(parent != null)
				{
					Matrix4 local = Matrix4.multiply(invParent, world);
					world = Matrix4.multiply(parent.world, local);
					
					invParent = parent.world.getInvert();
				}
				
				if(collider != null)
				{
					collider.calTerrainChanged = true;
				}
				
				this.hasChanged = false;
				parentHasChanged = true;
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
//		if(parent == null)
//		{
//			return local;
//		}
		
		return world;
	}
	
	public Vector3 getPosition()
	{
		return getWorldMatrix().getTranslation();
	}
	
	public Matrix3 getRotation()
	{
		return getWorldMatrix().toMatrix3();
	}
	
	public Vector3 getScale()
	{
		return new Vector3(localScaling);
	}
	
	public Vector3 getEulerAngles()
	{
		return getWorldMatrix().getEulerAngles();
	}
	
	public Vector3 getRight()
	{
		return getWorldMatrix().getRight();
	}
	
	public Vector3 getForward()
	{
		return getWorldMatrix().getForward();
	}
	
	public Vector3 getUp()
	{
		return getWorldMatrix().getUp();
	}
	
	public void setPosition(Vector3 position)
	{
		getWorldMatrix().setTranslation(position);
//		this.localTranslation.set(position);
		this.hasChanged = true;
	}
	
	public void setPosition(float x, float y, float z)
	{
		getWorldMatrix().setTranslation(x, y, z);
//		this.localTranslation.set(x, y, z);
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
		getWorldMatrix().setEulerAngles(eulerAngles.x, eulerAngles.y, eulerAngles.z);
//		this.localRotation.setEulerAngles(eulerAngles.x, eulerAngles.y, eulerAngles.z);
		this.hasChanged = true;
	}
	
	public void setEulerAngles(float x, float y, float z)
	{
		getWorldMatrix().setEulerAngles(x, y, z);
//		this.localRotation.setEulerAngles(x, y, z);
		this.hasChanged = true;
	}
	
	public void setRight(Vector3 right)
	{
		getWorldMatrix().setRight(right);
//		Matrix4 world = getWorldMatrix();
//		world.setRight(right);
//		
//		Matrix4 inv = Matrix4.multiply(invParent, world);
//		
//		this.localRotation.set(inv);
		this.hasChanged = true;
	}
	
	public void setRight(float x, float y, float z)
	{
		getWorldMatrix().setRight(x, y, z);
//		Matrix4 world = getWorldMatrix();
//		world.setRight(x, y, z);
//		
//		Matrix4 inv = Matrix4.multiply(invParent, world);
//		
//		this.localRotation.set(inv);
		this.hasChanged = true;
	}
	
	public void setForward(Vector3 forward)
	{
		getWorldMatrix().setForward(forward);
//		Matrix4 world = getWorldMatrix();
//		world.setForward(forward);
//		
//		Matrix4 inv = Matrix4.multiply(invParent, world);
//
//		this.localRotation.set(inv);
		this.hasChanged = true;
	}
	
	public void setForward(float x, float y, float z)
	{
		getWorldMatrix().setForward(x, y, z);
//		Matrix4 world = getWorldMatrix();
//		world.setForward(x, y, z);
//		
//		Matrix4 inv = Matrix4.multiply(invParent, world);
//
//		this.localRotation.set(inv);
		this.hasChanged = true;
	}
	
	public void setUp(Vector3 up)
	{
		getWorldMatrix().setUp(up);
//		Matrix4 world = getWorldMatrix();
//		world.setUp(up);
//		
//		Matrix4 inv = Matrix4.multiply(invParent, world);
//
//		this.localRotation.set(inv);
		this.hasChanged = true;
	}
	
	public void setUp(float x, float y, float z)
	{
		getWorldMatrix().setUp(x, y, z);
//		Matrix4 world = getWorldMatrix();
//		world.setUp(x, y, z);
//		
//		Matrix4 inv = Matrix4.multiply(invParent, world);
//
//		this.localRotation.set(inv);
		this.hasChanged = true;
	}
	
	public void setLookAt(Vector3 target)
	{
		Vector3 forward = Vector3.subtract(target, getPosition());

		this.hasChanged = true;
		
		forward = forward.getNormalized();

		getWorldMatrix().setForward(forward);
		
//		this.localRotation.m21 = forward.x;
//		this.localRotation.m22 = forward.y;
//		this.localRotation.m23 = forward.z;
	}
	
	public void setLookAt(Group target)
	{
		getWorldMatrix().setForward(Vector3.subtract(target.getPosition(), getPosition()));
//		this.localRotation.setForward(Vector3.subtract(target.localTranslation, localTranslation));
		this.hasChanged = true;
	}
	
	public void translate(float x, float y, float z)
	{
		if(Float.isNaN(x))
		{
			x = 0;
		}
		if(Float.isNaN(y))
		{
			y = 0;
		}
		if(Float.isNaN(z))
		{
			z = 0;
		}
		
		Matrix4 world = getWorldMatrix();
		
		world.m41 += (x * world.m11) + (y * world.m21) + (z * world.m31);
		world.m42 += (x * world.m12) + (y * world.m22) + (z * world.m32);
		world.m43 += (x * world.m13) + (y * world.m23) + (z * world.m33);
		

		this.hasChanged = true;
	}
	
	public void translate(float x, float y, float z, boolean relativeWorld)
	{
		if(Float.isNaN(x))
		{
			x = 0;
		}
		if(Float.isNaN(y))
		{
			y = 0;
		}
		if(Float.isNaN(z))
		{
			z = 0;
		}
		
		if(relativeWorld)
		{
			Matrix4 world = getWorldMatrix();
			
			world.m41 += x;
			world.m42 += y;
			world.m43 += z;
			
//			Matrix4 world = getWorldMatrix();
//			world.m41 += x;
//			world.m42 += y;
//			world.m43 += z;
//			
//			Matrix4 inv = Matrix4.multiply(invParent, world);
//			
//			this.localTranslation.x = inv.m41;
//			this.localTranslation.y = inv.m42;
//			this.localTranslation.z = inv.m43;
		}
		else
		{
			Matrix4 world = getWorldMatrix();
			
			world.m41 += (x * world.m11) + (y * world.m21) + (z * world.m31);
			world.m42 += (x * world.m12) + (y * world.m22) + (z * world.m32);
			world.m43 += (x * world.m13) + (y * world.m23) + (z * world.m33);
		}
		
		this.hasChanged = true;
	}
	
	public void rotate(float x, float y, float z)
	{
		if(Float.isNaN(x))
		{
			x = 0;
		}
		if(Float.isNaN(y))
		{
			y = 0;
		}
		if(Float.isNaN(z))
		{
			z = 0;
		}
		
		Matrix3 rot = Matrix3.createFromEulerAngles(x, y, z);
		Matrix3 w3 = getWorldMatrix().toMatrix3();
		
		Matrix3 nWorld = Matrix3.multiply(w3, rot);

		getWorldMatrix().set(nWorld);
		
//		Matrix3 rot = Matrix3.createFromEulerAngles(x, y, z).multiply(getWorldMatrix().toMatrix3());
//		getWorldMatrix().set(rot);
		
//		this.localRotation = Matrix3.createFromEulerAngles(x, y, z).multiply(localRotation);
		this.hasChanged = true;
	}
	
	public void rotate(float x, float y, float z, boolean relativeWorld)
	{
		if(Float.isNaN(x))
		{
			x = 0;
		}
		if(Float.isNaN(y))
		{
			y = 0;
		}
		if(Float.isNaN(z))
		{
			z = 0;
		}
		
		if(relativeWorld)
		{
//			Matrix4 rotation = Matrix4.createFromEulerAngles(x, y, z);
//			Matrix4 world = getWorldMatrix();
//			world.multiply(rotation);
//			
//			Matrix4 inv = Matrix4.multiply(invParent, world);
//			
//			this.localRotation = inv.toMatrix3();
//			this.localTranslation.set(inv.getTranslation());
			
//			Matrix3 rot = getWorldMatrix().toMatrix3().multiply(Matrix3.createFromEulerAngles(x, y, z));
//			getWorldMatrix().set(rot);
			
			Matrix4 rot = Matrix4.createFromEulerAngles(x, y, z);
			Matrix4 nWorld = Matrix4.multiply(rot, getWorldMatrix());
			getWorldMatrix().set(nWorld);
		}
		else
		{
			Matrix3 rot = Matrix3.createFromEulerAngles(x, y, z);
			Matrix3 w3 = getWorldMatrix().toMatrix3();
			
			Matrix3 nWorld = Matrix3.multiply(w3, rot);

			getWorldMatrix().set(nWorld);
		}
		
		this.hasChanged = true;
	}
}

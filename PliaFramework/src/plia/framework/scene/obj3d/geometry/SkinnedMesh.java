package plia.framework.scene.obj3d.geometry;

public class SkinnedMesh extends Mesh
{

	public SkinnedMesh(int normalsOffset, int uvOffset, int indicesCount)
	{
		super(Geometry.SKINNED_MESH, normalsOffset, uvOffset, indicesCount);
	}
	
}

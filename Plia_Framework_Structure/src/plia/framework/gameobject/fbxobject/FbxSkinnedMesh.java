package plia.framework.gameobject.fbxobject;

public class FbxSkinnedMesh extends FbxMesh
{
	private int[] skinnedBuffers = new int[2];
	private float[] skinnedMatrix;

	public FbxSkinnedMesh(float[] vertices, float[] normals, float[] uv, int[] indices, float[] boneWeights, short[] boneIndexes, int boneCount)
	{
		super(vertices, normals, uv, indices);
		
		this.skinnedMatrix = new float[boneCount];
	}

	public int getSkinnedMeshBuffers(int index)
	{
		return skinnedBuffers[index];
	}
	
	public float[] getSkinnedMatrix()
	{
		return skinnedMatrix;
	}
}

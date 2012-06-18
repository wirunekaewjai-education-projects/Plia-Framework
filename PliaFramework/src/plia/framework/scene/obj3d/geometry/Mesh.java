package plia.framework.scene.obj3d.geometry;

public class Mesh extends Geometry
{
	private float[][] matrixPalette;
	private int matrixPaletteIndexOffset;
	
	public final int INDICES_COUNT;
	public final int NORMALS_OFFSET;
	public final int UV_OFFSET;
	
	public Mesh(int normalsOffset, int uvOffset, int indicesCount)
	{
		INDICES_COUNT = indicesCount;
		NORMALS_OFFSET = normalsOffset;
		UV_OFFSET = uvOffset;
	}
	
	public void setMatrixPalette(float[][] matrixPalette)
	{
		this.matrixPalette = matrixPalette;
	}
	
	public float[][] getMatrixPalette()
	{
		return matrixPalette;
	}
	
	public int getMatrixPaletteIndexOffset()
	{
		return matrixPaletteIndexOffset;
	}
	
	public void setMatrixPaletteIndexOffset(int matrixPaletteIndexOffset)
	{
		this.matrixPaletteIndexOffset = matrixPaletteIndexOffset;
	}
}

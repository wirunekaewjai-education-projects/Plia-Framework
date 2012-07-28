package bvh.main;

import java.util.ArrayList;

import plia.core.scene.animation.Animation;
import plia.math.Matrix4;
import plia.math.Vector3;

public class Motion
{
	private ArrayList<Matrix4> matrixPalette = new ArrayList<Matrix4>();

	public void add(Vector3 lclR)
	{
		Matrix4 rx = new Matrix4();
		Matrix4 ry = new Matrix4();
		Matrix4 rz = new Matrix4();
		
		Matrix4.createRotationX(rx, lclR.x);
		Matrix4.createRotationY(ry, lclR.y);
		Matrix4.createRotationZ(rz, lclR.z);
		
		Matrix4 rot = Matrix4.multiply(Matrix4.multiply(rz, rx), ry);
		matrixPalette.add(rot);
	}
	
	public void add(Vector3 lclT, Vector3 lclR)
	{
		Matrix4 rx = new Matrix4();
		Matrix4 ry = new Matrix4();
		Matrix4 rz = new Matrix4();
		Matrix4 t = new Matrix4();
		
		Matrix4.createRotationX(rx, lclR.x);
		Matrix4.createRotationY(ry, lclR.y);
		Matrix4.createRotationZ(rz, lclR.z);
		Matrix4.createTranslation(t, lclT.x, lclT.y, lclT.z);
		
		Matrix4 matrix = Matrix4.multiply(Matrix4.multiply(Matrix4.multiply(rz, rx), ry), t);
	
		matrixPalette.add(matrix);
	}
	
	public int size()
	{
		return matrixPalette.size();
	}
	
	public Matrix4 get(int index)
	{
		return matrixPalette.get(Math.max(0, Math.min(index, matrixPalette.size()-1)));
	}
}

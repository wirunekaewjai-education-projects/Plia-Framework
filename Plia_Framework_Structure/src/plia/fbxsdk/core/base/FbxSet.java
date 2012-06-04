package plia.fbxsdk.core.base;

import java.util.Arrays;

import plia.fbxsdk.core.FbxObject;

public class FbxSet
{
	public final long[] keys;
	public final FbxObject[] values;
	public int index = 0;
	
	public FbxSet(int size)
	{
		keys = new long[size];
		values = new FbxObject[size];
	}
	
	public void put(long key, FbxObject value)
	{
		if(index < keys.length)
		{
			keys[index] = key;
			values[index] = value;
			index++;
		}
	}

	public FbxObject get(long key)
	{
		int i = Arrays.binarySearch(keys, key);
		if(i > -1)
		{
			return values[i];
		}
		return null;
	}
	
	public boolean containsKey(long key)
	{
		int i = Arrays.binarySearch(keys, key);
		return (i > -1);
	}
}

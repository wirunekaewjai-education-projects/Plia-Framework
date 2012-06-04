package plia.fbxsdk.fileio;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import plia.fbxsdk.core.FbxObject;
import plia.fbxsdk.core.base.FbxSet;
import plia.fbxsdk.core.base.FbxTimeMode;
import plia.fbxsdk.core.base.FbxTimeSpan;
import plia.fbxsdk.core.math.FbxVector3;
import plia.fbxsdk.scene.FbxScene;
import plia.fbxsdk.scene.animation.FbxAnimCurve;
import plia.fbxsdk.scene.animation.FbxAnimCurveNode;
import plia.fbxsdk.scene.geometry.FbxCluster;
import plia.fbxsdk.scene.geometry.FbxDeformer;
import plia.fbxsdk.scene.geometry.FbxGeometry;
import plia.fbxsdk.scene.geometry.FbxMesh;
import plia.fbxsdk.scene.geometry.FbxNode;
import plia.fbxsdk.scene.geometry.FbxNodeAttribute;
import plia.fbxsdk.scene.geometry.FbxSkeleton;
import plia.fbxsdk.scene.geometry.FbxSkin;

import android.util.Log;

public class FbxImporter
{
	// Temp
	private static int index;
	private static StringBuilder sb = new StringBuilder();
	
	public static FbxScene importScene(InputStream inputStream)
	{
		String[] list = null;
		try
		{
			InputStreamReader isr = new InputStreamReader(inputStream);
			char[] buffer = new char[inputStream.available()];
			isr.read(buffer);

			list = new String(buffer).split("\n");
		} 
		catch (IOException e)
		{
			Log.e("Error", e.getMessage());
		}

//		long sss = System.nanoTime();
		
		String line = "";
		index = 0;
		// Count
		int model_count = 0;
		int geometry_count = 0;
		int node_attribute_count = 0;
		int anim_curve_count = 0;
		int anim_curve_node_count = 0;
		int deformer_count = 0;
		
		FbxTimeSpan timeSpan = new FbxTimeSpan();
		FbxTimeMode timeMode = null;

		// Load Global
		while(true)
		{
			line = list[index++].trim();

			if(line.startsWith("; Object properties"))
			{
				break;
			}
			else if(line.startsWith("P: \"TimeMode"))
			{
				String[] t = line.split(",");
				timeMode = new FbxTimeMode(parseInt(t[t.length - 1]));
			}
			else if(line.contains("P: \"TimeSpanStart"))
			{
				String[] t = line.split(",");
				timeSpan.setStart(parseLong(t[t.length - 1]));
			}
			else if(line.contains("P: \"TimeSpanStop"))
			{
				String[] t = line.split(",");
				timeSpan.setStop(parseLong(t[t.length - 1]));
			}
			else if(line.startsWith("ObjectType:"))
			{
				line = line.split("\"")[1];
				String attribute = list[index++];
				int count = parseInt(attribute.split(": ")[1].trim());
				if(line.equals("Model"))
				{
					model_count = count;
				}
				else if(line.equals("Geometry"))
				{
					geometry_count = count;
				}
				else if(line.equals("NodeAttribute"))
				{
					node_attribute_count = count;
				}
				else if(line.equals("AnimationCurve"))
				{
					anim_curve_count = count;
				}
				else if(line.equals("AnimationCurveNode"))
				{
					anim_curve_node_count = count;
				}
				else if(line.equals("Deformer"))
				{
					deformer_count = count;
				}
			}
		}
		
		FbxSet geometries = new FbxSet(geometry_count);
		FbxSet nodes = new FbxSet(model_count);
		FbxSet nodeAttributes = new FbxSet(node_attribute_count);
		FbxSet deformers = new FbxSet(deformer_count);
		FbxSet animCurveNodes = new FbxSet(anim_curve_node_count);
		FbxSet animCurves = new FbxSet(anim_curve_count);

		// Load Geometries
		int current_geometry_count = 0;
		while(current_geometry_count < geometry_count)
		{
			line = list[index++].trim();
			
			if(line.startsWith("Geometry:"))
			{
				current_geometry_count += 1;

				long geometry_id = parseLong(line.substring(10).split(",")[0]);

				FbxMesh mesh = new FbxMesh(geometry_id);
				
				while(true)
				{
					line = list[index++].trim();
					
					if (line.startsWith("Vert"))
					{
						mesh.setVertices(readFloatAttribute(list));
					} 
					else if (line.startsWith("Poly"))
					{
						mesh.setIndices(readIntAttribute(list));
					} 
					else if (line.startsWith("Normals"))
					{
						mesh.setNormals(readFloatAttribute(list));
					} 
					else if (line.startsWith("UV:"))
					{
						mesh.setUV(readFloatAttribute(list));
					} 
					else if (line.startsWith("UVIndex"))
					{
						mesh.setUVIndices(readIntAttribute(list));
						break;
					}
				}
				
				put(geometries, geometry_id, mesh);
			}
		}

		// Load Nodes Attributes
		int current_node_attribute_count = 0;
		while(current_node_attribute_count < node_attribute_count)
		{
			line = list[index++].trim();
			
			if(line.startsWith("NodeAttribute: "))
			{
				current_node_attribute_count += 1;
				long node_attribute_id = parseLong(line.substring(15).split(",")[0]);
				
				int attributeType;
				FbxNodeAttribute nodeAttribute = null;
				
				String tempLine = line;
				index += 3;
				line = list[index++];
				
				if (line.contains("Skeleton"))
				{
					int skeletonType = 0;
					
					if(tempLine.contains("LimbNode"))
					{
						skeletonType = FbxSkeleton.LimbNode;
					}
					
					attributeType = FbxNodeAttribute.Skeleton;
					nodeAttribute = new FbxSkeleton(node_attribute_id, skeletonType);
				}
				else if (line.contains("Null"))
				{
					attributeType = FbxNodeAttribute.Null;
					
					nodeAttribute = new FbxNodeAttribute(node_attribute_id, attributeType);
				}
				else
				{
					attributeType = FbxNodeAttribute.Mesh;
				}
				
				put(nodeAttributes, node_attribute_id, nodeAttribute);
			}
		}
		
		// Load Nodes
		int current_node_count = 0;
		while(current_node_count < model_count)
		{
			line = list[index++].trim();
			
			if(line.startsWith("Model: "))
			{
				current_node_count += 1;
				
				StringBuilder[] item = new StringBuilder[2];
				item[0] = new StringBuilder();
				item[1] = new StringBuilder();
				int inner_index = 0;
				boolean begin = false;
				char temp = 0;

				for (int i = 0; i < line.length(); i++)
				{
					char c = line.charAt(i);

					if (temp == ':')
					{
						if (c == ' ')
						{
							begin = true;
							inner_index = 0;
							continue;
						} 
						else if (c == ':')
						{
							begin = true;
							inner_index = 1;
							continue;
						}
					}

					if (c == '"' || c == ',')
					{
						begin = false;

						if (inner_index == 1)
						{
							break;
						}
					}

					if (begin)
					{
						item[inner_index].append(c);
					}

					temp = c;
				}
				
				long nodeID = parseLong(item[0].toString().trim());
				String nodeName = item[1].toString();
				
				FbxNode node = new FbxNode(nodeID);
				node.name = nodeName;

				while(!(line = list[index++]).contains("}"))
				{
					if (line.contains("Lcl Translation"))
					{
						String[] t = line.split(",");
						node.setLclTranslation(new FbxVector3(parseFloat(t[t.length - 3]), parseFloat(t[t.length - 2]), parseFloat(t[t.length - 1])));
					} else if (line.contains("Lcl Rotation"))
					{
						String[] t = line.split(",");
						node.setLclRotation(new FbxVector3(parseFloat(t[t.length - 3]), parseFloat(t[t.length - 2]), parseFloat(t[t.length - 1])));
					} else if (line.contains("Lcl Scaling"))
					{
						String[] t = line.split(",");
						node.setLclScaling(new FbxVector3(parseFloat(t[t.length - 3]), parseFloat(t[t.length - 2]), parseFloat(t[t.length - 1])));
					}
				}

				put(nodes, nodeID, node);
			}
		}
		
		// Load Deformers
		int current_deformer_count = 0;
		while(current_deformer_count < deformer_count)
		{
			line = list[index++].trim();
			
			if (line.startsWith("Deformer: "))
			{
				current_deformer_count += 1;
				
				attributes = line.substring(10).split(",");
				long deformer_id = parseLong(attributes[0]);
				
				String tempType = attributes[attributes.length-1];
				
				if (tempType.contains("Skin"))
				{
					FbxSkin skin = new FbxSkin(deformer_id);
					put(deformers, deformer_id, skin);
				}
				else
				{
					FbxCluster cluster = new FbxCluster(deformer_id);
					
					while(true)
					{
						line = list[index++].trim();
						if (line.startsWith("Indexes"))
						{
							cluster.setIndices(readIntAttribute(list));
						} 
						else if (line.startsWith("Weights"))
						{
							cluster.setWeights(readFloatAttribute(list));
						} 
						else if (line.startsWith("Transform:"))
						{
							cluster.setTransform(readFloatAttribute(list));
						}
						else if (line.startsWith("TransformLink"))
						{
							cluster.setTransformLink(readFloatAttribute(list));
							break;
						}
					}

					put(deformers, deformer_id, cluster);
				}
			}
		}

		// Load Animation Curve Nodes
		int current_anim_curve_node_count = 0;
		while(current_anim_curve_node_count < anim_curve_node_count)
		{
			line = list[index++].trim();
			
			if (line.startsWith("AnimationCurveNode:"))
			{
				current_anim_curve_node_count += 1;
				long anim_curve_node_id = parseLong(line.substring(20).split(",")[0]);
				FbxAnimCurveNode animCurveNode = new FbxAnimCurveNode(anim_curve_node_id);
				put(animCurveNodes, anim_curve_node_id, animCurveNode);
			}
		}
		
		// Load Animation Curves
		int current_anim_curve_count = 0;
		while(current_anim_curve_count < anim_curve_count)
		{
			line = list[index++].trim();
			
			if	(line.startsWith("AnimationCurve:"))
			{
				current_anim_curve_count += 1;
				long anim_curve_id = parseLong(line.substring(16).split(",")[0]);
				FbxAnimCurve animCurve = new FbxAnimCurve(anim_curve_id);
				
				long[] times = null;
				float[] values = null;
				
				while(true)
				{
					line = list[index++].trim();
					if (line.startsWith("KeyTime:"))
					{
						times = readLongAttribute(list);
					}
					else if (line.startsWith("KeyValueFloat:"))
					{
						values = readFloatAttribute(list);
						break;
					}
				}
				
				animCurve.set(times, values);
				put(animCurves, anim_curve_id, animCurve);
			}
		}

		ArrayList<FbxNode> rootnodes = new ArrayList<FbxNode>();
		// Object Connections
		while(index < list.length)
		{
			line = list[index++].trim();
			if(line.endsWith("RootNode"))
			{
				line = list[index++].trim();
				String attribute = line.substring(8);
				
				long root_node_id = parseLong(attribute.substring(0, attribute.length()-2));
				
				FbxNode node = (FbxNode) get(nodes, root_node_id);
				rootnodes.add(node);

				index++;
			}
			else if (line.startsWith(";Model"))
			{
				if(line.split(",")[1].startsWith(" Model:"))
				{
					line = list[index++].trim();
					String[] attribute = line.substring(8).split(",");
					
					long child_node_id = parseLong(attribute[0]);
					long parent_node_id = parseLong(attribute[1]);
					
					FbxNode child_node = (FbxNode) get(nodes, child_node_id);
					FbxNode parent_node = (FbxNode) get(nodes, parent_node_id);

					parent_node.addChild(child_node);
					child_node.setParent(parent_node);
					
					index++;
				}
			}
			else if (line.startsWith(";Geometry"))
			{
				line = list[index++].trim();
				String[] attribute = line.substring(8).split(",");
				
				long geometry_id = parseLong(attribute[0]);
				long node_id = parseLong(attribute[1]);
				
				FbxNode node = (FbxNode) get(nodes, node_id);
				FbxGeometry geometry = (FbxGeometry) get(geometries, geometry_id);
				node.setNodeAttribute(geometry);
				
				index++;
			}
			else if (line.startsWith(";AnimCurveNode:"))
			{
				String[] tmp0 = line.split(",");
				if(tmp0[1].startsWith(" Model:"))
				{
					line = list[index++].trim();
					
					String[] attribute = line.substring(8).split(",");
					
					long anim_curve_node_id = parseLong(attribute[0]);
					long node_id = parseLong(attribute[1]);
					
					FbxNode node = (FbxNode) get(nodes, node_id);
					FbxAnimCurveNode animCurveNode = (FbxAnimCurveNode) get(animCurveNodes, anim_curve_node_id);

					// TODO
					char type = tmp0[0].charAt(tmp0[0].length()-1);
					if(type == 'T')
					{
						node.setLclTranslation(animCurveNode);
					}
					else if(type == 'R')
					{
						node.setLclRotation(animCurveNode);
					}
					else
					{
						node.setLclScaling(animCurveNode);
					}
					//
					
					index++;
				}
			}
			else if (line.startsWith(";NodeAtt"))
			{
				line = list[index++].trim();
				String[] attribute = line.substring(8).split(",");
				
				long node_attribute_id = parseLong(attribute[0]);
				long node_id = parseLong(attribute[1]);
				
				FbxNode node = (FbxNode) get(nodes, node_id);
				FbxNodeAttribute nodeAttribute = (FbxNodeAttribute) get(nodeAttributes, node_attribute_id);
				
				node.setNodeAttribute(nodeAttribute);
			}
			else if (line.startsWith(";Deform"))
			{
				line = list[index++].trim();
				String[] attribute = line.substring(8).split(",");
				
				long deformer_id = parseLong(attribute[0]);
				long geometry_id = parseLong(attribute[1]);
				
				FbxDeformer deformer = (FbxDeformer) get(deformers, deformer_id);
				FbxGeometry geometry = (FbxGeometry) get(geometries, geometry_id);

				// TODO
				geometry.addDeformer(deformer);
				//
				
				index++;
			}
			else if (line.startsWith(";AnimCurve:"))
			{
				index--;
				break;
			}
		}
		
		while(index < list.length)
		{
			line = list[index++].trim();
			
			if (line.startsWith(";AnimCurve:"))
			{
				for (int i = 0; i < 3; i++)
				{
					line = list[index++].trim();
					String[] first_line = line.substring(8).split(",");
					index += 2;
					line = list[index++].trim();
					String[] second_line = line.substring(8).split(",");
					index += 2;
					line = list[index++].trim();
					String[] third_line = line.substring(8).split(",");
					index += 2;
					
					long anim_curve_node_id = parseLong(first_line[1]);
					long x_id = parseLong(first_line[0]);
					long y_id = parseLong(second_line[0]);
					long z_id = parseLong(third_line[0]);
					
					FbxAnimCurveNode animCurveNode = (FbxAnimCurveNode) get(animCurveNodes, anim_curve_node_id);
					FbxAnimCurve animCurveX = (FbxAnimCurve) get(animCurves, x_id);
					FbxAnimCurve animCurveY = (FbxAnimCurve) get(animCurves, y_id);
					FbxAnimCurve animCurveZ = (FbxAnimCurve) get(animCurves, z_id);

					animCurveNode.set(animCurveX, animCurveY, animCurveZ);
				}
				
				index--;
			}
			else if (line.startsWith(";SubDef"))
			{
				line = list[index++].trim();
				String[] attribute = line.substring(8).split(",");
				
				long sub_deformer_id = parseLong(attribute[0]);
				long deformer_id = parseLong(attribute[1]);
				
				FbxCluster cluster = (FbxCluster) get(deformers, sub_deformer_id);
				FbxSkin deformer = (FbxSkin) get(deformers, deformer_id);

				deformer.addCluster(cluster);
				
				index++;
			}
			else if (line.endsWith(", SubDeformer::"))
			{
				line = list[index++].trim();
				String[] attribute = line.substring(8).split(",");
				
				long node_id = parseLong(attribute[0]);
				long sub_deformer_id = parseLong(attribute[1]);
				
				FbxNode associateModel = (FbxNode) get(nodes, node_id);
				FbxCluster cluster = (FbxCluster) get(deformers, sub_deformer_id);

				cluster.setAssociateModel(associateModel);

				index++;
			}
		}
		
		FbxScene scene = new FbxScene();

		for (FbxObject fbxObject : geometries.values)
		{
			scene.addGeometry((FbxGeometry) fbxObject);
		}
		
		for (FbxObject fbxObject : nodes.values)
		{
			scene.addNode((FbxNode) fbxObject);
		}
		
		FbxNode sceneRootNode = scene.getRootnodes();

		for (FbxNode rootnode : rootnodes)
		{
			sceneRootNode.addChild(rootnode);
		}

		scene.globalSetting().setTimeMode(timeMode);
		scene.globalSetting().setTimeSpan(timeSpan);
		
		list = null;

		return scene;
	}

	private static void put(FbxSet set, long key, FbxObject value)
	{
		if (set.index < set.keys.length)
		{
			set.keys[set.index] = key;
			set.values[set.index] = value;
			set.index++;
		}
	}
	
	private static FbxObject get(FbxSet set, long key)
	{
		for (int i = 0; i < set.keys.length; i++)
		{
			if(set.keys[i] == key)
			{
				return set.values[i];
			}
		}
		return null;
	}

	private static String[] attributes;

	private static float[] readFloatAttribute(String[] list)
	{
		String line = list[index++].trim();
		
		int indexOf = line.indexOf(":")+2;
		
		sb.append(line.substring(indexOf));
		
		while(true)
		{
			line = list[index++].trim();
			if(line.startsWith("}"))
			{
				break;
			}

			sb.append(line);
		}
		
		attributes = sb.toString().split(",");
		sb.delete(0, sb.length());

		float[] items = new float[attributes.length];
		for (int i = 0; i < items.length; i++)
		{
			items[i] = parseFloat(attributes[i]);
		}
		
		return items;
	}
	
	private static long[] readLongAttribute(String[] list)
	{
		String line = list[index++].trim();
		
		int indexOf = line.indexOf(":")+2;
		
		sb.append(line.substring(indexOf));
		
		while(true)
		{
			line = list[index++].trim();
			if(line.startsWith("}"))
			{
				break;
			}

			sb.append(line);
		}
		
		attributes = sb.toString().split(",");
		sb.delete(0, sb.length());

		long[] items = new long[attributes.length];
		for (int i = 0; i < items.length; i++)
		{
			items[i] = parseLong(attributes[i]);
		}
		
		return items;
	}
	
	private static int[] readIntAttribute(String[] list)
	{
		String line = list[index++].trim();
		
		int indexOf = line.indexOf(":")+2;
		
		sb.append(line.substring(indexOf));
		
		while(true)
		{
			line = list[index++].trim();
			if(line.startsWith("}"))
			{
				break;
			}

			sb.append(line);
		}
		
		attributes = sb.toString().split(",");
		sb.delete(0, sb.length());

		int[] items = new int[attributes.length];
		for (int i = 0; i < items.length; i++)
		{
			items[i] = parseInt(attributes[i]);
		}
		
		return items;
	}
	
	// Parsing Number
	private static int parseInt(String s)
	{
		int result = 0;
		
		int length = s.length();
		int end = 0;
		int d = 1;

		if(s.charAt(0) == '-')
		{
			end = 1;
			d = -1;
		}

		int start = length-1;
		
		int p = 1;
		
		for (int i = start; i >= end; i--)
		{
			char c = s.charAt(i);
			int value = 0;
			
			switch (c)
			{
				case '0': value = 0; break;
				case '1': value = 1; break;
				case '2': value = 2; break;
				case '3': value = 3; break;
				case '4': value = 4; break;
				case '5': value = 5; break;
				case '6': value = 6; break;
				case '7': value = 7; break;
				case '8': value = 8; break;
				case '9': value = 9; break;
				default: value = 0; break;
			}
			
			result += value * p;
			p *= 10;
		}
		return result * d;
	}
	
	private static long parseLong(String s)
	{
		long result = 0;
		
		int length = s.length();
		int end = 0;
		int d = 1;

		if(s.charAt(0) == '-')
		{
			end = 1;
			d = -1;
		}

		int start = length-1;
		
		long p = 1L;
		
		for (int i = start; i >= end; i--)
		{
			char c = s.charAt(i);
			if(c >= '0' && c <= '9')
			{
				int value = 0;
				
				switch (c)
				{
					case '0': value = 0; break;
					case '1': value = 1; break;
					case '2': value = 2; break;
					case '3': value = 3; break;
					case '4': value = 4; break;
					case '5': value = 5; break;
					case '6': value = 6; break;
					case '7': value = 7; break;
					case '8': value = 8; break;
					case '9': value = 9; break;
					default: value = 0; break;
				}
				
				result += value * p;
				p *= 10;
			}
		}
		return result * d;
	}
	
	private static float parseFloat(String s)
	{
		int indexOfDot = s.indexOf('.');

		int d = 1;
		
		int left = indexOfDot-1;
		int right;
		int first = 0;
		float result = 0;
		
		float lastPow = 1;
		
		if(s.charAt(0) == '-')
		{
			first += 1;
			d = -1;
		}
		
		int indexOfE = -1;
		int eStop = indexOfDot;
		if(indexOfDot > -1)
		{
			eStop = 0;
		}

		for (int i = s.length()-1; i > eStop; i--)
		{
			char c = s.charAt(i);
			if(c == 'e' || c == 'E')
			{
				indexOfE = i;
				break;
			}
		}
		
		if(indexOfE > -1)
		{
			right = indexOfE;
			/*
			right = indexOfE;
			
			int pow = 0;
			int dir = 1;
			float p = 1;

			for (int i = s.length()-1; i > indexOfE; i--)
			{
				char c = s.charAt(i);
				if (c == '-')
				{
					dir = -1;
				} else
				{
					if (c <= '9')
					{
						int value = 0;
						
						switch (c)
						{
							case '0': value = 0; break;
							case '1': value = 1; break;
							case '2': value = 2; break;
							case '3': value = 3; break;
							case '4': value = 4; break;
							case '5': value = 5; break;
							case '6': value = 6; break;
							case '7': value = 7; break;
							case '8': value = 8; break;
							case '9': value = 9; break;
							default: value = 0; break;
						}
						
						pow += value * p;
						p *= 10;
					}
				}
			}
			*/
			
			int pd = parseInt(s.substring(indexOfE+1));
//			int pd = pow*dir;
			
			switch (pd)
			{
				case -13:
				case -12:
				case -11:
				case -10:
				case -9:
				case -8:
				case -7: lastPow = 0; break;
				case -6: lastPow = 0.000001f; break;
				case -5: lastPow = 0.00001f; break;
				case -4: lastPow =  0.0001f; break;
				case -3: lastPow =  0.001f; break;
				case -2: lastPow =  0.01f; break;
				case -1: lastPow =  0.1f; break;
				case 0: lastPow =  1; break;
				case 1: lastPow =  10; break;
				case 2: lastPow =  100; break;
				case 3: lastPow =  1000; break;
				case 4: lastPow =  10000; break;
				case 5: lastPow =  100000; break;
				case 6: lastPow =  1000000; break;
				case 7: lastPow =  10000000; break;
				case 8: lastPow =  100000000; break;
				case 9: lastPow =  1000000000; break;
				case 10: lastPow = 10000000000f; break;
				case 11: lastPow = 100000000000f; break;
				case 12: lastPow = 1000000000000f; break;
				case 13: lastPow = 10000000000000f; break;
				case 14: lastPow = 100000000000000f; break;
				default: lastPow = (float) Math.pow(10, pd); break;
			}
			
		}
		else
		{
			right = s.length();
		}
		
		float p = 1;
		if(indexOfDot == -1)
		{
			for (int i = right-1; i > indexOfDot; i--)
			{
				char c = s.charAt(i);
				
				if(c > '9')
				{
					break;
				}
				else
				{
					int value = 0;
					
					switch (c)
					{
						case '0': value = 0; break;
						case '1': value = 1; break;
						case '2': value = 2; break;
						case '3': value = 3; break;
						case '4': value = 4; break;
						case '5': value = 5; break;
						case '6': value = 6; break;
						case '7': value = 7; break;
						case '8': value = 8; break;
						case '9': value = 9; break;
						default: value = 0; break;
					}
					
					result += value * p;
					p *= 10;
				}
			}
		}
		else
		{
			for (int i = left; i >= first; i--)
			{
				char c = s.charAt(i);
				if(c > '9')
				{
					break;
				}
				else
				{
					int value = 0;
					
					switch (c)
					{
						case '0': value = 0; break;
						case '1': value = 1; break;
						case '2': value = 2; break;
						case '3': value = 3; break;
						case '4': value = 4; break;
						case '5': value = 5; break;
						case '6': value = 6; break;
						case '7': value = 7; break;
						case '8': value = 8; break;
						case '9': value = 9; break;
						default: value = 0; break;
					}
	
					result += value * p;
					p *= 10;
				}
			}

			p = 10;
			for (int i = indexOfDot+1; i < right; i++)
			{
				char c = s.charAt(i);

				if(c > '9')
				{
					break;
				}
				else
				{
					int value = 0;
					
					switch (c)
					{
						case '0': value = 0; break;
						case '1': value = 1; break;
						case '2': value = 2; break;
						case '3': value = 3; break;
						case '4': value = 4; break;
						case '5': value = 5; break;
						case '6': value = 6; break;
						case '7': value = 7; break;
						case '8': value = 8; break;
						case '9': value = 9; break;
						default: value = 0; break;
					}
					
					result += value / p;
					p *= 10;
				}
			}
		}
		
		return result*d*lastPow;
	}
}

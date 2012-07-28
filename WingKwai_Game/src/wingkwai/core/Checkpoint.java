package wingkwai.core;

import java.util.ArrayList;

import plia.core.scene.Collider;
import plia.core.scene.PlaneCollider;
import plia.core.scene.SphereCollider;
//import plia.math.Vector3;

public class Checkpoint
{
	private ArrayList<PlaneCollider> colliders = new ArrayList<PlaneCollider>();
	private ArrayList<Player> players = new ArrayList<Player>();
	private int lab = 1;
	
//	private ArrayList<Float> distances = new ArrayList<Float>();
//	
//	private ArrayList<Float> distancesTmp = new ArrayList<Float>();
//	private ArrayList<Integer> ranks = new ArrayList<Integer>();

	public void update()
	{
		for (Player player : players)
		{
			int currentCheckpoint = player.getCurrentCheckpoint();
			int checkpointCount = player.getCheckpointCount();
			
			PlaneCollider chp = colliders.get(currentCheckpoint);
			SphereCollider spr = (SphereCollider) player.getVehicle().getObject().getCollider();

//			float dist = Vector3.distance(chp.getPosition(), spr.getPosition());
//			
//			if(distancesTmp.isEmpty())
//			{
//				distancesTmp.add(dist);
//				ranks.add(0);
//			}
//			else if(dist < distancesTmp.get(0))
//			{
//				distancesTmp.add(0, dist);
//				ranks.add(0, players.indexOf(player));
//			}
//			else if(dist >= distancesTmp.get(0))
//			{
//				distancesTmp.add(dist);
//				ranks.add(players.indexOf(player));
//			}
			
			if(Collider.intersect(chp, spr))
			{
				currentCheckpoint++;
				checkpointCount++;
				
				int rank = 1;
				
				for (Player player2 : players)
				{
					if(player != player2)
					{
						if(player2.getCheckpointCount() >= checkpointCount)
						{
							rank++;
						}
					}
				}
				
				player.setRank(rank);

				if(currentCheckpoint >= size())
				{
					currentCheckpoint -= size();
				}
				
				if(checkpointCount == (size() * player.getLab())+1)
				{
					player.setLab(player.getLab()+1);
				}
				
				if(checkpointCount > size() * lab)
				{
//					removed.add(players.indexOf(player));
					player.setEnd(true);
					
					int plab = player.getLab();
					if(plab > lab)
					{
						player.setLab(lab);
					}
				}
				
				player.setCheckpointCount(checkpointCount);
				player.setCurrentCheckpoint(currentCheckpoint);
			}
		}

//		for (Player player : players)
//		{
//			player.setRank(ranks.remove(0)+1);
//		}
//		
//		distancesTmp.clear();
		
		
//		for (int indx : removed)
//		{
//			players.remove(indx);
//		}
//		
//		removed.clear();
	}
	
	public void add(PlaneCollider collider)
	{
		colliders.add(collider);
	}
	
	public void remove(PlaneCollider collider)
	{
		colliders.remove(collider);
	}
	
	public void addPlayer(Player player)
	{
		players.add(player);
	}
	
	public void removePlayer(Player player)
	{
		players.remove(player);
	}
	
	public boolean isEnd()
	{
		return players.size() == 0;
	}
	
	public int size()
	{
		return colliders.size();
	}
	
	public PlaneCollider get(int index)
	{
		return colliders.get(index);
	}
	
	public void setLab(int lab)
	{
		this.lab = lab;
	}
	
	public int getLab()
	{
		return lab;
	}
}

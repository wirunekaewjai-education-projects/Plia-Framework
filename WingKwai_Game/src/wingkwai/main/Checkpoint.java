package wingkwai.main;

import java.util.ArrayList;

import plia.core.scene.Collider;
import plia.core.scene.PlaneCollider;
import plia.core.scene.SphereCollider;

public class Checkpoint
{
	private ArrayList<PlaneCollider> colliders = new ArrayList<PlaneCollider>();
	private ArrayList<Player> players = new ArrayList<Player>();

	public void update()
	{
		
		for (Player player : players)
		{
			int currentCheckpoint = player.getCurrentCheckpoint();
			int checkpointCount = player.getCheckpointCount();
			
			PlaneCollider chp = colliders.get(currentCheckpoint);
			SphereCollider spr = (SphereCollider) player.getVehicle().getObject().getCollider();
			
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
				
				if(checkpointCount > size())
				{
//					removed.add(players.indexOf(player));
					player.setEnd(true);
				}
				
				player.setCheckpointCount(checkpointCount);
				player.setCurrentCheckpoint(currentCheckpoint);
			}
		}
		
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
}

package wingkwai.main;


public class Player
{
	private Vehicle vehicle;
	private Item item;
	
	private long startTime = 0;
	private boolean isUseItem = false;
	
	private int rank = 1;
	
	private int currentCheckpoint = 0;
	private int checkpointCount = 0;
	
	private boolean isEnd = false;
	
	public Player(Vehicle vehicle)
	{
		this.vehicle = vehicle;
	}
	
	public Player instantiate()
	{
		Vehicle clone = vehicle.instantiate();
		Player pClone = new Player(clone);

		if(hasItem())
		{
			pClone.item = item;
		}
		
		return pClone;
	}
	
	public void update()
	{
		vehicle.update();
		
		if(isUseItem)
		{
			long currentMillis = System.currentTimeMillis() - startTime;
			
			if(currentMillis >= item.getTime() * 1000)
			{
				item.getOnItemEventListener().onEffectEnd(this);
				item = null;
				isUseItem = false;
				vehicle.setVelocityMultiplier(1 + ((rank-1) * RANK_SPEED));
				vehicle.setAngularVelocityMultiplier(1);
			}
		}
	}
	
	public Vehicle getVehicle()
	{
		return vehicle;
	}
	
	public int getRank()
	{
		return rank;
	}
	
	public void setRank(int rank)
	{
		int oldRank = this.rank;
		this.rank = rank;
		
		int dist = rank - oldRank;
		float value = dist * RANK_SPEED;
		vehicle.setVelocityMultiplier(vehicle.getVelocityMultiplier() + value);
	}
	
	public Item getItem()
	{
		return item;
	}
	
	public void setItem(Item item)
	{
		this.item = item;
	}
	
	public boolean hasItem()
	{
		return item != null;
	}
	
	public void useItem()
	{
		if(hasItem())
		{
			isUseItem = true;
			startTime = System.currentTimeMillis();
			
			vehicle.setVelocityMultiplier(item.getSpeed());
			vehicle.setAngularVelocityMultiplier(item.getControl());
			
			item.getOnItemEventListener().onEffectStart(this);
		}
	}
	
	public int getCheckpointCount()
	{
		return checkpointCount;
	}
	
	public int getCurrentCheckpoint()
	{
		return currentCheckpoint;
	}
	
	public void setCurrentCheckpoint(int currentCheckpoint)
	{
		this.currentCheckpoint = currentCheckpoint;
	}
	
	public void setCheckpointCount(int checkpointCount)
	{
		this.checkpointCount = checkpointCount;
	}
	
	public boolean isEnd()
	{
		return isEnd;
	}
	
	public void setEnd(boolean isEnd)
	{
		this.isEnd = isEnd;
	}
	
	private static float RANK_SPEED = 0.005f;
}

package wingkwai.main;


public class Player
{
	private Vehicle vehicle;
	private Item item;
	
	private long startTime = 0;
	private boolean isUseItem = false;
	
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
				vehicle.setVelocityMultiplier(1);
				vehicle.setAngularVelocityMultiplier(1);
			}
		}
	}
	
	public Vehicle getVehicle()
	{
		return vehicle;
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
}

package plia.framework;

import android.database.Observable;

public class Model extends Observable<Controller>
{
	public void notifyObserver(Controller observer, Object...objects)
	{
		observer.update(observer, objects);
	}
	
	public void notifyAllObserver(Object...objects)
	{
		for (Controller observer : mObservers)
		{
			observer.update(observer, objects);
		}
	}
}

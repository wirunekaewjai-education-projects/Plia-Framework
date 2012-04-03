package plia.framework;

import android.database.Observable;

public class Model extends Observable<ViewController>
{
	public void notifyObserver(ViewController observer, Object...objects)
	{
		observer.update(observer, objects);
	}
	
	public void notifyAllObserver(Object...objects)
	{
		for (ViewController observer : mObservers)
		{
			observer.update(observer, objects);
		}
	}
}

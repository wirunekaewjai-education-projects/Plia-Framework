package wingkwai.game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Database
{
	private static String MY_DATABASE_NAME = "MY_DATABASE";
	private static String MY_TABLE_NAME = "TABLE_DATANOTE";
	private Context context;
	private SQLiteDatabase myDB = null;

	public Database(Context context)
	{
		this.context = context;
		
		create();
	}

	private void create()
	{
		try
		{
			myDB = context.openOrCreateDatabase(MY_DATABASE_NAME, 0 /*MODE_PRIVATE*/, null);
			myDB.execSQL("CREATE TABLE IF NOT EXISTS "
					+ MY_TABLE_NAME
					+ " (ID INTEGER PRIMARY KEY,MATCHC INTEGER,WINC INTEGER,LOSEC INTEGER);");
		}
		catch (Exception e) 
		{
			Log.e("Error", e.toString());
		}
		finally
		{
			if(myDB != null)
			{
				myDB.close();
			}
		}
	}
	
	public void add(Profile profile)
	{
//		SQLiteDatabase myDB = null;
		
		try
		{
			myDB = context.openOrCreateDatabase(MY_DATABASE_NAME, 0, null);
//			myDB.openDatabase(MY_DATABASE_NAME, null, 0);
//			ContentValues cv = new ContentValues();
//			cv.put("MATCHC", profile.getMatch());
//			cv.put("WINC", profile.getWin());
//			cv.put("LOSEC", profile.getLose());
//			myDB.insert(MY_TABLE_NAME, null, cv);
			
			myDB.execSQL("insert into "+MY_TABLE_NAME
					+ " (ID, MATCHC, WINC, LOSEC)"
					+ " values(0, "+profile.getMatch()+", "+profile.getWin()+", "+profile.getLose()+")");
		}
		catch (Exception e) 
		{
			Log.e("Error", e.toString());
		}
		finally
		{
			if(myDB != null)
			{
				myDB.close();
			}
		}
	}

	public Profile get()
	{
//		SQLiteDatabase myDB = null;
		Cursor c = null;
		Profile p = null;
		
		try
		{
			myDB = context.openOrCreateDatabase(MY_DATABASE_NAME, 0, null);
//			SQLiteDatabase.openDatabase(MY_DATABASE_NAME, null, 0);
			c = myDB.rawQuery("SELECT * FROM "+MY_TABLE_NAME+";", null);
			
			if(c != null)
			{
				if(c.moveToFirst())
				{
					int m = c.getInt(1);
					int w = c.getInt(2);
					int l = c.getInt(3);
					
					p = new Profile(m, w, l);
				}
			}
		}
		catch (Exception e) 
		{
			Log.e("Error", e.toString());
		}
		finally
		{
			if(c != null)
			{
				c.close();
			}
			if(myDB != null)
			{
				myDB.close();
			}
			
			if(p == null)
			{
				p = new Profile();
			}
		}

		return p;
	}

	public void delete()
	{
		SQLiteDatabase myDB = null;
		
		try
		{
			myDB = context.openOrCreateDatabase(MY_DATABASE_NAME, 0, null);
			myDB.delete(MY_TABLE_NAME, "ID='"+0+"'", null);
		}
		catch (Exception e) 
		{
			Log.e("Error", e.toString());
		}
		finally
		{
			if(myDB != null)
			{
				myDB.close();
			}
		}
	}
}

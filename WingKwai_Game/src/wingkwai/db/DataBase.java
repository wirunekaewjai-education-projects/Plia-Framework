package wingkwai.db;

import java.util.Vector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataBase
{
	private static String MY_DATABASE_NAME = "MY_DATABASE";
	private static String MY_TABLE_NAME = "TABLE_DATANOTE";
	private Context context;
	
	public DataBase(Context context){
		this.context = context;
	}
	
	public void addProfile(Profile profile)
	{
		SQLiteDatabase myDB = null;
		
		Cursor c = null;
		try
		{
			myDB = context.openOrCreateDatabase(MY_DATABASE_NAME, 0 /*MODE_PRIVATE*/, null);
			myDB.execSQL("CREATE TABLE IF NOT EXISTS "
					+ MY_TABLE_NAME
					+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT,TITLE TEXT,DESCRIPTION TEXT);");
			
			ContentValues cv = new ContentValues();
			cv.put("MATCH", profile.getMatchCount());
			cv.put("WIN", profile.getWin());
			cv.put("LOSE", profile.getLose());
			myDB.insert(MY_TABLE_NAME, null, cv);
		}
		catch (Exception e)
		{
			Log.e("Error", e.toString());
		}
		finally
		{
			if (c!= null)
			{
				c.close();
			}
			
			if(myDB != null)
			{
				myDB.close();
			}
		}
		
    }

	public Vector<Profile> getAllProfile()
	{
		SQLiteDatabase myDB = null;
		Cursor c = null;
		Vector<Profile> vData = new Vector<Profile>();
		
		try
		{
			myDB = context.openOrCreateDatabase(MY_DATABASE_NAME, 0 /*MODE_PRIVATE*/, null);
			myDB.execSQL("CREATE TABLE IF NOT EXISTS "
					+ MY_TABLE_NAME
					+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT,TITLE TEXT,DESCRIPTION TEXT);");
			
			c = myDB.rawQuery("SELECT * FROM "+MY_TABLE_NAME+";", null);
			if(c != null)
			{
				if(c.moveToFirst())
				{
					do
					{
						int match = c.getInt(1);
						int win = c.getInt(2);
						int lose = c.getInt(3);
						vData.add(new Profile(match, win, lose));
					} while (c.moveToNext());
				}
			}
		}
		catch (Exception e)
		{
			Log.e("Error", e.toString());
		}
		finally
		{
			if (c!= null)
			{
				c.close();
			}
			
			if(myDB != null)
			{
				myDB.close();
			}
		}
		
		return vData;
	}
	
	public void DelDatabase(int id)
    {
		SQLiteDatabase myDB = null;
		
		try
		{
			myDB = context.openOrCreateDatabase(MY_DATABASE_NAME, 0 /*MODE_PRIVATE*/, null);
			myDB.delete(MY_TABLE_NAME, "ID='"+id+"'", null);
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
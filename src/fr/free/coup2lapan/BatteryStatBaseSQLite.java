/*******************************************************************************
 * This file is part of the Coup2Lap@an
 *
 * The Coup2Lap@an is free software;
 * you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation;
 * either version 2 of the License, or (at your option) any later version.
 * 
 * The Coup2Lap@an is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with the Coup2Lap@an;
 * if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *  
 *******************************************************************************/

package fr.free.coup2lapan;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BatteryStatBaseSQLite extends SQLiteOpenHelper {

	private static final String TABLE_BATTERYSTAT = "table_batterystat";
	private static final String COL_ID = "ID";
	private static final String COL_LEVEL = "LEVEL";
	private static final String COL_SCALE = "SCALE";
	private static final String COL_TEMPERATURE = "TEMPERATURE";
	private static final String COL_VOLTAGE = "VOLTAGE";
	private static final String COL_STATUS = "STATUS";
	private static final String COL_PLUGGED = "PLUGGED";
	private static final String COL_HEALTH = "HEALTH";
	private static final String COL_PRESENT = "PRESENT";
	private static final String COL_DATE = "DATE";
	private static final String COL_TECHNOLOGY = "TECHNOLOGY";

	private static final String CREATE_BDD = "CREATE TABLE "
			+ TABLE_BATTERYSTAT + " (" + COL_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_LEVEL
			+ " INTEGER NOT NULL, " + COL_SCALE + " INTEGER NOT NULL, "
			+ COL_LEVEL + " INTEGER NOT NULL, " + COL_TEMPERATURE
			+ " INTEGER NOT NULL, " + COL_VOLTAGE + " INTEGER NOT NULL, "
			+ COL_STATUS + " INTEGER NOT NULL, " + COL_PLUGGED
			+ " INTEGER NOT NULL, " + COL_HEALTH + " INTEGER NOT NULL, "
			+ COL_PRESENT + " BOOLEAN NOT NULL, " + COL_DATE
			+ " TEXT NOT NULL);" + COL_TECHNOLOGY + " TEXT NOT NULL);";

	public BatteryStatBaseSQLite(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_BDD);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
	 * .SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Gérer les montées de version de la base de données
		db.execSQL("DROP TABLE " + TABLE_BATTERYSTAT);
		onCreate(db);
	}

}

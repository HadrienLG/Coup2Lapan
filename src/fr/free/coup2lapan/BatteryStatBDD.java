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

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BatteryStatBDD {

	private static final int VERSION = 1;
	private static final String NOM_BDD = "batterySTAT.db";

	private static final String TABLE_BATTERYSTAT = "table_batterystat";
	private static final String COL_ID = "ID";
	private static final int NUM_COL_ID = 0;
	private static final String COL_LEVEL = "LEVEL";
	private static final int NUM_COL_LEVEL = 1;
	private static final String COL_SCALE = "SCALE";
	private static final int NUM_COL_SCALE = 2;
	private static final String COL_TEMPERATURE = "TEMPERATURE";
	private static final int NUM_COL_TEMPERATURE = 3;
	private static final String COL_VOLTAGE = "VOLTAGE";
	private static final int NUM_COL_VOLTAGE = 4;
	private static final String COL_STATUS = "STATUS";
	private static final int NUM_COL_STATUS = 5;
	private static final String COL_PLUGGED = "PLUGGED";
	private static final int NUM_COL_PLUGGED = 6;
	private static final String COL_HEALTH = "HEALTH";
	private static final int NUM_COL_HEALTH = 7;
	private static final String COL_PRESENT = "PRESENT";
	private static final int NUM_COL_PRESENT = 8;
	private static final String COL_DATE = "DATE";
	private static final int NUM_COL_DATE = 9;
	private static final String COL_TECHNOLOGY = "TECHNOLOGY";
	private static final int NUM_COL_TECHNOLOGY = 10;

	private SQLiteDatabase battbdd;
	private BatteryStatBaseSQLite batteryStat;

	public BatteryStatBDD(Context context) {
		batteryStat = new BatteryStatBaseSQLite(context, NOM_BDD, null, VERSION);
	}

	public void openForWrite() {
		battbdd = batteryStat.getWritableDatabase();
	}

	public void openForRead() {
		battbdd = batteryStat.getReadableDatabase();
	}

	public void close() {
		battbdd.close();
	}

	public SQLiteDatabase getBdd() {
		return battbdd;
	}

	public long insertBatteryStat(BatteryStat batterystat) {
		// On crée un content dans lequel on insère chaque valeur de l'objet
		// batterystat
		ContentValues content = new ContentValues();

		content.put(COL_LEVEL, batterystat.getLevel());
		content.put(COL_SCALE, batterystat.getScale());
		content.put(COL_TEMPERATURE, batterystat.getTemperature());
		content.put(COL_VOLTAGE, batterystat.getVoltage());
		content.put(COL_STATUS, batterystat.getStatus());
		content.put(COL_PLUGGED, batterystat.getPlugged());
		content.put(COL_HEALTH, batterystat.getHealth());
		content.put(COL_PRESENT, batterystat.getPresent());
		content.put(COL_DATE, batterystat.getDate());
		content.put(COL_TECHNOLOGY, batterystat.getTechnology());
		// on insère le content dans la table ad'hoc
		return battbdd.insert(TABLE_BATTERYSTAT, null, content);
	}

	public int removeBatteryStatById(int Id) {
		return battbdd.delete(TABLE_BATTERYSTAT, COL_ID + " = " + Id, null);
	}

	public BatteryStat cursorToBatteryStat(Cursor c) {
		if (c.getCount() == 0) {
			c.close();
			return null;
		}

		BatteryStat batterystat = new BatteryStat(c.getInt(NUM_COL_LEVEL),
				c.getInt(NUM_COL_SCALE), c.getInt(NUM_COL_TEMPERATURE),
				c.getInt(NUM_COL_VOLTAGE), c.getInt(NUM_COL_STATUS),
				c.getInt(NUM_COL_PLUGGED), c.getInt(NUM_COL_HEALTH),
				c.getInt(NUM_COL_PRESENT), c.getString(NUM_COL_DATE),
				c.getString(NUM_COL_TECHNOLOGY));
		c.close();
		return batterystat;
	}

	public ArrayList<BatteryStat> getAllBatteryStat() {
		Cursor c = battbdd.query(TABLE_BATTERYSTAT,
				new String[] { COL_ID, COL_LEVEL, COL_SCALE, COL_TEMPERATURE,
						COL_VOLTAGE, COL_STATUS, COL_PLUGGED, COL_HEALTH,
						COL_PRESENT, COL_DATE, COL_TECHNOLOGY }, null, null,
				null, null, COL_ID);
		if (c.getCount() == 0) {
			c.close();
			return null;
		}
		ArrayList<BatteryStat> batterystatList = new ArrayList<BatteryStat>();
		while (c.moveToNext()) {
			BatteryStat batterystat = new BatteryStat(c.getInt(NUM_COL_LEVEL),
					c.getInt(NUM_COL_SCALE), c.getInt(NUM_COL_TEMPERATURE),
					c.getInt(NUM_COL_VOLTAGE), c.getInt(NUM_COL_STATUS),
					c.getInt(NUM_COL_PLUGGED), c.getInt(NUM_COL_HEALTH),
					c.getInt(NUM_COL_PRESENT), c.getString(NUM_COL_DATE),
					c.getString(NUM_COL_TECHNOLOGY));
			batterystatList.add(batterystat);
		}
		c.close();
		return batterystatList;
	}

}

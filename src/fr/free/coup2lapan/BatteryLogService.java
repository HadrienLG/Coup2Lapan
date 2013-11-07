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

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

/**
 * 
 * The following code has been mostly written and inspired by the Android Battery Dog project
 * from Ferenc Hechler - ferenc_hechler@users.sourceforge.net
 *
 */

public class BatteryLogService extends Service {

	private final static String[] batteryEXTRAKeys = {"level", "scale", "voltage", "temperature", "plugged", "status", "health", "present", "technology"};
	public Locale localeFR = new Locale("fr","FR");
	private boolean isThreadRunning; //true if the thread is running, false neither
	private boolean doQuitThread; //used to wait for a quit signal
	private Intent newBatteryIntent;
	private File batteryLogFile;
	// exception seems caused by private String fileName = getString(R.string.fileName);
	private String fileName = "batteryLog.csv";


	@Override
	public void onCreate() {
		super.onCreate();
		
		fileName = getString(R.string.fileName);

		if(!isThreadRunning) {
			doQuitThread = false;
			Thread workThread = new Thread(batteryLogRun, "BatteryLogService"); //null suppressed
			workThread.start();

			registerReceiver(batteryStatusReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
			Toast.makeText(this, getString(R.string.service_started), Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * The function that runs in our worker thread
	 */
	Runnable batteryLogRun = new Runnable() {

		public void run() {
			isThreadRunning = true;
			while (!doQuitThread) {
				try {
					logBatteryState();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				synchronized (BatteryLogService.this) {
					try {
						BatteryLogService.this.wait();
					} catch (Exception ignore) {}
				}
			}
			// as the while loop is closed, the thread will stop
			isThreadRunning = false;
			// last log of the battery state
			try {
				logBatteryState();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	};


	@Override
	public void onDestroy() {
		doQuitThread = true;
		super.onDestroy();
		unregisterReceiver(batteryStatusReceiver);
		Toast.makeText(this, getString(R.string.service_stopped), Toast.LENGTH_SHORT).show();
	}


	/**
	 * Répond à la commande startService()
	 */
	/*
	@Override
	public void onStartCommand() {
		super.onStartCommand(intent, flags, startId)
		if(!isThreadRunning) {
			doQuitThread = false;
			Thread workThread = new Thread(batteryLogRun, "BatteryLogService"); //null suppressed
			workThread.start();

			registerReceiver(batteryStatusReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
			Toast.makeText(this, getString(R.string.service_started), Toast.LENGTH_SHORT).show();
		}

	}


	/**
	 * needed function for a class that extends Service
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}


	private BroadcastReceiver batteryStatusReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) { 
			try {
				newBatteryIntent = (Intent) intent.clone();
			}
			catch (Exception e) {
			}
		}
	};


	private void logBatteryState() throws Exception {
		if(newBatteryIntent == null) return;

		try{
			FileWriter outputFW = null;
			batteryLogFile = new File(getApplicationContext().getFilesDir(), fileName);
			boolean fileExists = batteryLogFile.createNewFile();

			// no overwrite, set append to true 
			outputFW = new FileWriter(batteryLogFile, true);

			if (!batteryLogFile.canWrite()) {
				outputFW.close();
				throw new Exception(fileName + " is not writable");
			}

			if(!fileExists) {
				String header = createHeadLine();
				outputFW.write(header);
				outputFW.write("\n");
			}

			String extras = createBatteryInfoLine(newBatteryIntent);
			outputFW.write(extras);
			outputFW.write("\n");

			// last operation, flush and close the FileWriter
			outputFW.flush();
			outputFW.close();

		} catch (Exception e){
		}
	}

	private String createHeadLine() {
		// create a headline for the log file to make it understandable by anyone
		StringBuffer result = new StringBuffer();
		result.append("Date;Time");
		for (String key : batteryEXTRAKeys)
			result.append(";").append(key);
		return result.toString();
	}

	private String createBatteryInfoLine(Intent batteryIntent) {
		// this code is an adaptation of BatteryDog code
		String currentDateString = DateFormat.getDateInstance(2,localeFR).format(new Date());
		String currentTimeString = DateFormat.getTimeInstance(2,localeFR).format(new Date());

		StringBuffer result = new StringBuffer();
		result.append(currentDateString).append(";").append(currentTimeString);
		Bundle extras = batteryIntent.getExtras();
		for (String key : batteryEXTRAKeys)
			result.append(";").append(extras.get(key));
		return result.toString();
	}

}

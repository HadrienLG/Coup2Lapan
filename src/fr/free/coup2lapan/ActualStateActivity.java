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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ActualStateActivity extends Activity {

	private TextView battstatus;
	private TextView battplugged;
	private TextView batthealth;
	private TextView battstateofcharge;
	private TextView batttemperature;
	private TextView batttension;
	private TextView batttechnology;
	private TextView curentDate;
	public Locale localeFR = new Locale("fr", "FR");

	private Button batteryLogStart;
	private Button batteryLogStop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actualstate_main);
		// Show the Up button in the action bar.
		setupActionBar();

		battstatus = (TextView) findViewById(R.id.textViewActual01);
		battplugged = (TextView) findViewById(R.id.textViewActual02);
		batthealth = (TextView) findViewById(R.id.textViewActual03);
		battstateofcharge = (TextView) findViewById(R.id.textViewActual04);
		batttemperature = (TextView) findViewById(R.id.textViewActual05);
		batttension = (TextView) findViewById(R.id.textViewActual06);
		batttechnology = (TextView) findViewById(R.id.textViewActual07);
		curentDate = (TextView) findViewById(R.id.textViewActual08);

		final Button refreshbutton = (Button) findViewById(R.id.buttonActualRefresh);
		refreshbutton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				actualBatteryStatus();
				saveBatteryInfo();
			}
		});

		batteryLogStart = ((Button) findViewById(R.id.actual_start_button));
		batteryLogStart.setOnClickListener(StartServiceListener);

		batteryLogStop = ((Button) findViewById(R.id.actual_stop_button));
		batteryLogStop.setOnClickListener(StopServiceListener);

	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public void onResume() {
		super.onResume();
		actualBatteryStatus();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity_actions, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_about:
			openAbout();
			return true;
		case R.id.action_settings:
			openSettings();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void openSettings() {
		// permet d'ouvrir l'activité settings
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

	public void openAbout() {
		// permet d'ouvrir l'activité about
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}

	public BatteryStat getBatteryStatus() {

		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent status = this.registerReceiver(null, ifilter);

		BatteryStat batterystat = new BatteryStat(status.getIntExtra(
				BatteryManager.EXTRA_LEVEL, 0), status.getIntExtra(
				BatteryManager.EXTRA_SCALE, 0), status.getIntExtra(
				BatteryManager.EXTRA_TEMPERATURE, 0), status.getIntExtra(
				BatteryManager.EXTRA_VOLTAGE, 0), status.getIntExtra(
				BatteryManager.EXTRA_STATUS, 0), status.getIntExtra(
				BatteryManager.EXTRA_PLUGGED, 0), status.getIntExtra(
				BatteryManager.EXTRA_HEALTH, 0), status.getExtras().getBoolean(
				BatteryManager.EXTRA_PRESENT) ? 1 : 0, DateFormat
				.getDateInstance(2, localeFR).format(new Date())
				+ DateFormat.getTimeInstance(2, localeFR).format(new Date()),
				status.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY));

		return batterystat;
	}

	public void actualBatteryStatus() {
		BatteryStat batterystat = getBatteryStatus();

		/**
		 * state of charge (SOC) In case of a scale of 100, the SOC is equal to
		 * level, otherwise it needs to be normalized
		 */
		int infobattsoc = 0;
		if (batterystat.getScale() == 100) {
			infobattsoc = batterystat.getLevel();
		} else {
			infobattsoc = 100 * batterystat.getLevel() / batterystat.getScale();
		}

		/**
		 * temperature The temperature is given in tenth of Celsius degree we
		 * convert it to float
		 */
		float temperaturefloat = (float) batterystat.getTemperature() / 10;

		/** Status */
		String infobattstatus = null;
		switch (batterystat.getStatus()) {
		case 1:
			infobattstatus = getString(R.string.batterystatus1);
			break;
		case 2:
			infobattstatus = getString(R.string.batterystatus2);
			break;
		case 3:
			infobattstatus = getString(R.string.batterystatus3);
			break;
		case 4:
			infobattstatus = getString(R.string.batterystatus4);
			break;
		case 5:
			infobattstatus = getString(R.string.batterystatus5);
			break;
		}

		/** plugged */
		String infobattplugged = getString(R.string.batterypluggedNON);
		switch (batterystat.getPlugged()) {
		case 1:
			infobattplugged = getString(R.string.batterypluggedAC);
			break;
		case 2:
			infobattplugged = getString(R.string.batterypluggedUSB);
			break;
		}

		/** health */
		String infobatthealth = null;
		switch (batterystat.getHealth()) {
		case 1:
			infobatthealth = getString(R.string.batteryhealth1);
			break;
		case 2:
			infobatthealth = getString(R.string.batteryhealth2);
			break;
		case 3:
			infobatthealth = getString(R.string.batteryhealth3);
			break;
		case 4:
			infobatthealth = getString(R.string.batteryhealth4);
			break;
		case 5:
			infobatthealth = getString(R.string.batteryhealth5);
			break;
		case 6:
			infobatthealth = getString(R.string.batteryhealth6);
			break;
		case 7:
			infobatthealth = getString(R.string.batteryhealth7);
			break;
		}

		if (batterystat.getPresent() == 1) {

			battstatus.setText(getString(R.string.labelstatus) + " : "
					+ infobattstatus);
			battplugged.setText(getString(R.string.labelplugged) + " : "
					+ infobattplugged);
			batthealth.setText(getString(R.string.labelhealth) + " : "
					+ infobatthealth);
			battstateofcharge.setText(getString(R.string.labelstateofcharge)
					+ " : " + infobattsoc + " "
					+ getString(R.string.unitestateofcharge));
			batttemperature.setText(getString(R.string.labeltemperature)
					+ " : " + temperaturefloat + " "
					+ getString(R.string.unitetemperature));
			batttension.setText(getString(R.string.labeltension) + " : "
					+ batterystat.getVoltage() + " "
					+ getString(R.string.unitetension));
			batttechnology.setText(getString(R.string.labeltechnology) + " : "
					+ batterystat.getTechnology());
			curentDate.setText("Dernière MAJ : " + batterystat.getDate());
		} else {
			battstatus.setText("Status : "
					+ getString(R.string.batterypresentfalse));
			battplugged.setText("Branchée : -");
			batthealth.setText("Santé : -");
			battstateofcharge.setText("Etat de charge : -");
			batttemperature.setText("Température : ");
			batttension.setText("Tension : -");
			batttechnology.setText("Technologie : -");
			curentDate.setText("Dernière MAJ : " + batterystat.getDate());
		}

	}

	public void saveBatteryInfo() {
		BatteryStat batterystat = getBatteryStatus();

		String batteryInfoLine = "\n" + batterystat.getDate()
				+ batterystat.getLevel() + batterystat.getScale()
				+ batterystat.getTemperature() + batterystat.getVoltage()
				+ batterystat.getStatus() + batterystat.getPlugged()
				+ batterystat.getHealth() + batterystat.getPresent()
				+ batterystat.getTechnology();

		//

		FileOutputStream fos;
		try {
			fos = openFileOutput("ACTUAL" + getString(R.string.fileName),
					Context.MODE_PRIVATE);
			fos.write(batteryInfoLine.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	OnClickListener StartServiceListener = new OnClickListener() {
		public void onClick(View v) {
			Context context = getApplicationContext();
			try {
				startService(new Intent(context, BatteryLogService.class));
			} catch (Exception e) {
				Toast.makeText(context, "Start Service failed",
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	OnClickListener StopServiceListener = new OnClickListener() {
		public void onClick(View v) {
			Context context = getApplicationContext();
			try {
				stopService(new Intent(context, BatteryLogService.class));
			} catch (Exception e) {
				Toast.makeText(context, "Stop Service failed",
						Toast.LENGTH_SHORT).show();
			}
		}
	};

}

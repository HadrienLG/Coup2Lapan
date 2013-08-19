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

import java.text.DateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actualstate_main);
		// Show the Up button in the action bar.
		setupActionBar();

		battstatus = (TextView)findViewById(R.id.textViewActual01);
		battplugged = (TextView)findViewById(R.id.textViewActual02);
		batthealth = (TextView)findViewById(R.id.textViewActual03);
		battstateofcharge = (TextView)findViewById(R.id.textViewActual04);
		batttemperature = (TextView)findViewById(R.id.textViewActual05);
		batttension = (TextView)findViewById(R.id.textViewActual06);
		batttechnology = (TextView)findViewById(R.id.textViewActual07);
		curentDate = (TextView)findViewById(R.id.textViewActual08);

		final Button refreshbutton = (Button) findViewById(R.id.buttonActualRefresh);
		refreshbutton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				getBatteryStatus();
			}
		});

		getBatteryStatus();

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
		getBatteryStatus();
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

	public void getBatteryStatus() {

		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent batteryStatus = this.registerReceiver(null, ifilter);

		int health= batteryStatus.getIntExtra(BatteryManager.EXTRA_HEALTH,0);
		int level= batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
		int plugged= batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED,0);
		boolean present= batteryStatus.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT);
		int scale= batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE,0);
		int status= batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS,0);
		String technology= batteryStatus.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
		int temperature= batteryStatus.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);
		int voltage= batteryStatus.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);

		//santé - health
		String infobatthealth = null;
		switch (health) {
		case 1 : infobatthealth = getString(R.string.batteryhealth1); break;
		case 2 : infobatthealth = getString(R.string.batteryhealth2); break;
		case 3 : infobatthealth = getString(R.string.batteryhealth3); break;
		case 4 : infobatthealth = getString(R.string.batteryhealth4); break;
		case 5 : infobatthealth = getString(R.string.batteryhealth5); break;
		case 6 : infobatthealth = getString(R.string.batteryhealth6); break;
		case 7 : infobatthealth = getString(R.string.batteryhealth7); break;
		}

		//Branchée - plugged
		String infobattplugged = getString(R.string.batterypluggedNON);
		switch (plugged) {
		case 1 : infobattplugged = getString(R.string.batterypluggedAC); break;
		case 2 : infobattplugged = getString(R.string.batterypluggedUSB); break;
		}

		//Status
		String infobattstatus = null;
		switch (status) {
		case 1 : infobattstatus = getString(R.string.batterystatus1); break;
		case 2 : infobattstatus = getString(R.string.batterystatus2); break;
		case 3 : infobattstatus = getString(R.string.batterystatus3); break;
		case 4 : infobattstatus = getString(R.string.batterystatus4); break;
		case 5 : infobattstatus = getString(R.string.batterystatus5); break;
		}

		/** Le state of charge est direct dans le cas où le niveau
		 * "level" est à 100, sinon il faut le normaliser 
		 */
		int infobattsoc = 0;
		if(scale == 100){
			infobattsoc = level;
		}
		else{
			infobattsoc = 100*level/scale;
		}
		
		/** La température est donnée en dixième de degré celsius
		 * On doit donc la convertir en float pour l'affichage 
		 */
		float temperaturefloat = (float)temperature / 10;

		if(present){
			battstatus.setText(getString(R.string.labelstatus) + " : " + infobattstatus);
			battplugged.setText(getString(R.string.labelplugged) + " : " + infobattplugged);
			batthealth.setText(getString(R.string.labelhealth) + " : " + infobatthealth);
			battstateofcharge.setText(getString(R.string.labelstateofcharge) + " : " + infobattsoc + " " + getString(R.string.unitestateofcharge));
			batttemperature.setText(getString(R.string.labeltemperature) + " : " + temperaturefloat + " " + getString(R.string.unitetemperature));
			batttension.setText(getString(R.string.labeltension) + " : " + voltage + " " + getString(R.string.unitetension));
			batttechnology.setText(getString(R.string.labeltechnology) + " : " + technology);
		}
		else{
			battstatus.setText("Status : "+getString(R.string.batterypresentfalse));
			battplugged.setText("Branchée : -");
			batthealth.setText("Santé : -");
			battstateofcharge.setText("Etat de charge : -");
			batttemperature.setText("Température : ");
			batttension.setText("Tension : -");
			batttechnology.setText("Technologie : -");
		}

		Toast.makeText(this, getString(R.string.actual_majcours), Toast.LENGTH_SHORT).show();
		
		String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
		// textView is the TextView view that should display it
		curentDate.setText(currentDateTimeString);

	}

}

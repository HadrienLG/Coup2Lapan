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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button batteryLogStart;
	private Button batteryLogStop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		batteryLogStart = ((Button) findViewById(R.id.button_main_2a));
		batteryLogStart.setOnClickListener(StartServiceListener);

		batteryLogStop = ((Button) findViewById(R.id.button_main_2b));
		batteryLogStop.setOnClickListener(StopServiceListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_about:
			openAbout();
			return true;
		case R.id.action_settings:
			openSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void openSettings() {
		// open the activity settings
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

	public void openAbout() {
		// open the activity about
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}

	/** Called when the user clicks button_main_1 */
	public void openActualStateActivity(View view) {
		// Do something in response to button
		Intent intent = new Intent(this, ActualStateActivity.class);
		startActivity(intent);
	}

	OnClickListener StartServiceListener = new OnClickListener() {
		public void onClick(View v) {
			Context context = getApplicationContext();
			try {
				startService(new Intent(context, BatteryLogService.class));
			} catch (Exception e) {
				Toast.makeText(context, "Start Service failed", Toast.LENGTH_SHORT).show();
			}
		}
	};

	OnClickListener StopServiceListener = new OnClickListener() {
		public void onClick(View v) {
			Context context = getApplicationContext();
			try {
				stopService(new Intent(context, BatteryLogService.class));
			} catch (Exception e) {
				Toast.makeText(context, "Stop Service failed", Toast.LENGTH_SHORT).show();
			}
		}
	};

	/** Called when the user clicks button_main_3 */
	public void openHistoricActivity(View view) {
		// Do something in response to button
		Intent intent = new Intent(this, HistoricActivity.class);
		startActivity(intent);
	}

}

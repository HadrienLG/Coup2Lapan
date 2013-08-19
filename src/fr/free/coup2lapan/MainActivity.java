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

import fr.free.coup2lapan.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/* Pour afficher un Up Button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		 */
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
		// permet d'ouvrir l'activité settings
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

	public void openAbout() {
		// permet d'ouvrir l'activité about
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}
	
	/** Called when the user clicks the Send button */
	public void openActualStateActivity(View view) {
	    // Do something in response to button
		Intent intent = new Intent(this, ActualStateActivity.class);
		startActivity(intent);
	}
	
	/** Called when the user clicks the Send button */
	public void openServiceActivity(View view) {
	    // Do something in response to button
		Intent intent = new Intent(this, ServiceActivity.class);
		startActivity(intent);
	}
	
	/** Called when the user clicks the Send button */
	public void openHistoricActivity(View view) {
	    // Do something in response to button
		Intent intent = new Intent(this, HistoricActivity.class);
		startActivity(intent);
	}
	
}

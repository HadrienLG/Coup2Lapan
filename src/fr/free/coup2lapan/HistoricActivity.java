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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HistoricActivity extends Activity {

	private TextView logHistoric;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historic_main);
		// Show the Up button in the action bar.
		setupActionBar();

		logHistoric = (TextView) findViewById(R.id.historic_log);

		final Button refreshbutton = (Button) findViewById(R.id.historic_refresh);
		refreshbutton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO read the log file and print XX last entries

				/*FileReader logFR = null;
				File batteryLogFile = new File(getApplicationContext().getFilesDir(), getString(R.string.fileName));
				try {
					logFR = new FileReader(batteryLogFile);
				    @SuppressWarnings("resource")
					BufferedReader logBR = new BufferedReader(logFR);
				    String line = logBR.readLine();
				    while (null != line) {
				    	logHistoric.append(line);
				    	logHistoric.append("\n");
				        line = logBR.readLine();
				    }
				} catch (IOException e) {
				    // TODO Auto-generated catch block
				    e.printStackTrace();
				} finally {
				    if (null != logFR) {
				        try {
				        	logFR.close();
				        } catch (IOException e) {
				            // ignore
				        }
				    }
				}*/

				try {
					InputStream in=openFileInput(getString(R.string.fileName));

					if (in!=null) {
						InputStreamReader tmp=new InputStreamReader(in);
						BufferedReader reader=new BufferedReader(tmp);
						String str;
						StringBuilder buf=new StringBuilder();

						while ((str = reader.readLine()) != null) {
							buf.append(str+"\n");
						}

						in.close();
						logHistoric.setText(buf.toString());
					}
				} catch (java.io.FileNotFoundException e) {
					Toast.makeText(getApplicationContext(), "Exception: "+e.toString(), Toast.LENGTH_LONG).show();
				}
				catch (Throwable t) {
					Toast.makeText(getApplicationContext(), "Exception: "+t.toString(), Toast.LENGTH_LONG).show();
				}

				//logHistoric.setText("TODO : Lire fichier de mesures");
			}
		});

		final Button cleanbutton = (Button) findViewById(R.id.historic_clean);
		cleanbutton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				logHistoric.setText("");
			}
		});
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

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

	/** Called when the user clicks button_historic_export */
	public void exportFileToExternal(View view) {
		// Do something in response to button
		Toast.makeText(getApplicationContext(), getString(R.string.exportinit), Toast.LENGTH_SHORT).show();

		//TODO copy internal file to external storage

	}

	public void openSettings() {
		// permet d'ouvrir l'activit� settings
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

	public void openAbout() {
		// permet d'ouvrir l'activit� about
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}

}

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

public class BatteryStat {

	private int level;
	private int scale;
	private int temperature;
	private int voltage;
	private int status;
	private int plugged;
	private int health;
	private int present;
	private String date;
	private String technology;

	public BatteryStat() {
		// constructeur vide
	}

	public BatteryStat(int level, int scale, int temperature, int voltage,
			int status, int plugged, int health, int present, String date, String technology) {
		// constructeur d'une statistique de batterie
		this.level = level;
		this.scale = scale;
		this.temperature = temperature;
		this.voltage = voltage;
		this.status = status;
		this.plugged = plugged;
		this.health = health;
		this.present = present;
		this.date = date;
		this.technology = technology;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getScale() {
		return scale;
	}
	
	public int getTemperature() {
		return temperature;
	}
	
	public int getVoltage() {
		return voltage;
	}
	
	public int getStatus() {
		return status;
	}
	
	public int getPlugged() {
		return plugged;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getPresent() {
		return present;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getTechnology() {
		return technology;
	}

}

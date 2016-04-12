package de.obercraft.aide.dto;

public class DisplayEntry {
	
	private String denizen;
	private String [] chits;
	private String [] dwellings;
	private String location;
	private int count = 1;
	private boolean selected = false;

	public DisplayEntry() {
		super();
	}

	public String getDenizen() {
		return denizen;
	}

	public void setDenizen(String denizen) {
		this.denizen = denizen;
	}

	public String[] getChits() {
		return chits;
	}

	public void setChits(String[] chits) {
		this.chits = chits;
	}

	public String[] getDwellings() {
		return dwellings;
	}

	public void setDwellings(String[] dwellings) {
		this.dwellings = dwellings;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	
	
	
}

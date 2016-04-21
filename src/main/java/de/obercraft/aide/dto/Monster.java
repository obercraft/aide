package de.obercraft.aide.dto;

public class Monster extends Denizen {

	private boolean red;
	private boolean flying;
	
	// for head and club
	private String extraAttackName;
	
	// for imp and demon
	private String frontSpell;

	public boolean isRed() {
		return red;
	}

	public void setRed(boolean red) {
		this.red = red;
	}

	public boolean isFlying() {
		return flying;
	}

	public void setFlying(boolean flying) {
		this.flying = flying;
	}

	public String getExtraAttackName() {
		return extraAttackName;
	}

	public void setExtraAttackName(String extraAttackName) {
		this.extraAttackName = extraAttackName;
	}

	public String getFrontSpell() {
		return frontSpell;
	}

	public void setFrontSpell(String frontSpell) {
		this.frontSpell = frontSpell;
	}
	
	
	
	
}

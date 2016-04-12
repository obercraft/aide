package de.obercraft.aide.dto;

public class Denizen {
	
	String id;
	String size;
	boolean armored = false;
	boolean flies = false;
	
	// fame
	private int F;
	
	// Notoriety
	private int N;
	
	// gold
	private int G;
	
	private Stats front;
	private Stats back;
	
	private Stats headFront;
	private Stats headBack;
	private Stats clubFront;
	private Stats clubBack;
	private Stats horseFront;
	private Stats horseBack;

	public Denizen() {
		super();	
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getSize() {
		return size;
	}


	public void setSize(String size) {
		this.size = size;
	}


	public boolean isArmored() {
		return armored;
	}


	public void setArmored(boolean armored) {
		this.armored = armored;
	}


	public boolean isFlies() {
		return flies;
	}


	public void setFlies(boolean flies) {
		this.flies = flies;
	}


	public int getF() {
		return F;
	}


	public void setF(int f) {
		F = f;
	}


	public int getN() {
		return N;
	}


	public void setN(int n) {
		N = n;
	}


	public Stats getFront() {
		return front;
	}


	public void setFront(Stats front) {
		this.front = front;
	}


	public Stats getBack() {
		return back;
	}


	public void setBack(Stats back) {
		this.back = back;
	}

	public Stats getHeadFront() {
		return headFront;
	}

	public void setHeadFront(Stats headFront) {
		this.headFront = headFront;
	}

	public Stats getHeadBack() {
		return headBack;
	}

	public void setHeadBack(Stats headBack) {
		this.headBack = headBack;
	}

	public Stats getClubFront() {
		return clubFront;
	}

	public void setClubFront(Stats clubFront) {
		this.clubFront = clubFront;
	}

	public Stats getClubBack() {
		return clubBack;
	}

	public void setClubBack(Stats clubBack) {
		this.clubBack = clubBack;
	}

	public Stats getHorseFront() {
		return horseFront;
	}

	public void setHorseFront(Stats horseFront) {
		this.horseFront = horseFront;
	}

	public Stats getHorseBack() {
		return horseBack;
	}

	public void setHorseBack(Stats horseBack) {
		this.horseBack = horseBack;
	}

	public int getG() {
		return G;
	}

	public void setG(int g) {
		G = g;
	}
	
}

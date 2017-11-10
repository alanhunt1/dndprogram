package initcheck;

import java.io.Serializable;

/**
 * The BattleRound is an object designed to hold the data for one character
 * during one round of battle. This data is used to generate statistics.
 * 
 * @author Alan Hunt
 * 
 */
public class BattleRound implements Serializable {

	static final long serialVersionUID = 1;
		
	String name;

	String playerId;

	Status status;

	int roundNumber;
	
	int damage;

	int kills;

	int stuns;

	int crits;

	int fumbles;

	int doubleDamages;

	int numHits;

	int numAttacks;

	int maxDamage;	

	public String toString() {
		return "Round "+roundNumber;
	}

	/**
	 * Get the MaxDamage value.
	 * 
	 * @return the MaxDamage value.
	 */
	public int getMaxDamage() {
		return maxDamage;
	}

	/**
	 * Set the MaxDamage value.
	 * 
	 * @param newMaxDamage
	 *            The new MaxDamage value.
	 */
	public void setMaxDamage(int newMaxDamage) {
		this.maxDamage = newMaxDamage;
	}

	/**
	 * Get the NumAttacks value.
	 * 
	 * @return the NumAttacks value.
	 */
	public int getNumAttacks() {
		return numAttacks;
	}

	/**
	 * Set the NumAttacks value.
	 * 
	 * @param newNumAttacks
	 *            The new NumAttacks value.
	 */
	public void setNumAttacks(int newNumAttacks) {
		this.numAttacks = newNumAttacks;
	}

	/**
	 * Get the NumHits value.
	 * 
	 * @return the NumHits value.
	 */
	public int getNumHits() {
		return numHits;
	}

	/**
	 * Set the NumHits value.
	 * 
	 * @param newNumHits
	 *            The new NumHits value.
	 */
	public void setNumHits(int newNumHits) {
		this.numHits = newNumHits;
	}

	/**
	 * Get the DoubleDamages value.
	 * 
	 * @return the DoubleDamages value.
	 */
	public int getDoubleDamages() {
		return doubleDamages;
	}

	/**
	 * Set the DoubleDamages value.
	 * 
	 * @param newDoubleDamages
	 *            The new DoubleDamages value.
	 */
	public void setDoubleDamages(int newDoubleDamages) {
		this.doubleDamages = newDoubleDamages;
	}

	/**
	 * Get the Fumbles value.
	 * 
	 * @return the Fumbles value.
	 */
	public int getFumbles() {
		return fumbles;
	}

	/**
	 * Set the Fumbles value.
	 * 
	 * @param newFumbles
	 *            The new Fumbles value.
	 */
	public void setFumbles(int newFumbles) {
		this.fumbles = newFumbles;
	}

	/**
	 * Get the Crits value.
	 * 
	 * @return the Crits value.
	 */
	public int getCrits() {
		return crits;
	}

	/**
	 * Set the Crits value.
	 * 
	 * @param newCrits
	 *            The new Crits value.
	 */
	public void setCrits(int newCrits) {
		this.crits = newCrits;
	}

	/**
	 * Get the Stuns value.
	 * 
	 * @return the Stuns value.
	 */
	public int getStuns() {
		return stuns;
	}

	/**
	 * Set the Stuns value.
	 * 
	 * @param newStuns
	 *            The new Stuns value.
	 */
	public void setStuns(int newStuns) {
		this.stuns = newStuns;
	}

	/**
	 * Get the Kills value.
	 * 
	 * @return the Kills value.
	 */
	public int getKills() {
		return kills;
	}

	/**
	 * Set the Kills value.
	 * 
	 * @param newKills
	 *            The new Kills value.
	 */
	public void setKills(int newKills) {
		this.kills = newKills;
	}

	/**
	 * Get the Damage value.
	 * 
	 * @return the Damage value.
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * Set the Damage value.
	 * 
	 * @param newDamage
	 *            The new Damage value.
	 */
	public void setDamage(int newDamage) {
		this.damage = newDamage;
	}

	/**
	 * Get the Status value.
	 * 
	 * @return the Status value.
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Set the Status value.
	 * 
	 * @param newStatus
	 *            The new Status value.
	 */
	public void setStatus(Status newStatus) {
		this.status = newStatus;
	}

	/**
	 * Get the PlayerId value.
	 * 
	 * @return the PlayerId value.
	 */
	public String getPlayerId() {
		return playerId;
	}

	/**
	 * Set the PlayerId value.
	 * 
	 * @param newPlayerId
	 *            The new PlayerId value.
	 */
	public void setPlayerId(String newPlayerId) {
		this.playerId = newPlayerId;
	}

	/**
	 * Get the Name value.
	 * 
	 * @return the Name value.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the Name value.
	 * 
	 * @param newName
	 *            The new Name value.
	 */
	public void setName(String newName) {
		this.name = newName;
	}

	public int getRoundNumber() {
		return roundNumber;
	}

	public void setRoundNumber(int roundNumber) {
		this.roundNumber = roundNumber;
	}

}

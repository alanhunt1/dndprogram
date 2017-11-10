package initcheck.database;

import java.io.Serializable;

public class PlayerArmor implements Serializable, Cloneable {

	
	private static final long serialVersionUID = 1L;

	private String id;

	private String playerId;

	private String armorId;

	private String armorType;

	private String description;

	String bonus = "0";

	String ability1;

	String ability2;

	String atRest;

	boolean masterwork;

	/**
	 * Get the Masterwork value.
	 * 
	 * @return the Masterwork value.
	 */
	public boolean isMasterwork() {
		return masterwork;
	}

	/**
	 * Set the Masterwork value.
	 * 
	 * @param newMasterwork
	 *            The new Masterwork value.
	 */
	public void setMasterwork(boolean newMasterwork) {
		this.masterwork = newMasterwork;
	}

	/**
	 * Get the AtRest value.
	 * 
	 * @return the AtRest value.
	 */
	public String getAtRest() {
		return atRest;
	}

	/**
	 * Set the AtRest value.
	 * 
	 * @param newAtRest
	 *            The new AtRest value.
	 */
	public void setAtRest(String newAtRest) {
		this.atRest = newAtRest;
	}

	/**
	 * Get the Ability2 value.
	 * 
	 * @return the Ability2 value.
	 */
	public String getAbility2() {
		return ability2;
	}

	/**
	 * Set the Ability2 value.
	 * 
	 * @param newAbility2
	 *            The new Ability2 value.
	 */
	public void setAbility2(String newAbility2) {
		this.ability2 = newAbility2;
	}

	/**
	 * Get the Ability1 value.
	 * 
	 * @return the Ability1 value.
	 */
	public String getAbility1() {
		return ability1;
	}

	/**
	 * Set the Ability1 value.
	 * 
	 * @param newAbility1
	 *            The new Ability1 value.
	 */
	public void setAbility1(String newAbility1) {
		this.ability1 = newAbility1;
	}

	/**
	 * Get the Bonus value.
	 * 
	 * @return the Bonus value.
	 */
	public String getBonus() {
		return bonus;
	}

	/**
	 * Set the Bonus value.
	 * 
	 * @param newBonus
	 *            The new Bonus value.
	 */
	public void setBonus(String newBonus) {
		this.bonus = newBonus;
	}

	public PlayerArmor() {

	}

	public String format(String s) {
		if (s == null || s.equals("null")) {
			return "";
		}
		return s;
	}

	public Object getClone() {
		Object o = null;
		try {
			o = this.clone();
		} catch (Exception e) {
		}
		return o;
	}

	/**
	 * Get the value of id
	 * 
	 * @return a <code>String</code> value
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the value of id
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setId(String s) {
		id = s;
	}

	/**
	 * Get the value of playerId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPlayerId() {
		return playerId;
	}

	/**
	 * Set the value of playerId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPlayerId(String s) {
		playerId = s;
	}

	/**
	 * Get the value of armorId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getArmorId() {
		return armorId;
	}

	/**
	 * Set the value of armorId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setArmorId(String s) {
		armorId = s;
	}

	/**
	 * Get the value of armorType
	 * 
	 * @return a <code>String</code> value
	 */
	public String getArmorType() {
		return armorType;
	}

	/**
	 * Set the value of armorType
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setArmorType(String s) {
		armorType = s;
	}

	/**
	 * Get the value of description
	 * 
	 * @return a <code>String</code> value
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the value of description
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setDescription(String s) {
		description = s;
	}

}

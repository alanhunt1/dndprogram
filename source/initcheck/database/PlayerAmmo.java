package initcheck.database;

import java.io.Serializable;
import java.util.Vector;

public class PlayerAmmo implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String playerid;

	private String weaponid;

	private String bonus;

	private String description;

	private String masterwork;

	private String notes;

	String quantity;

	/**
	 * Get the Quantity value.
	 * 
	 * @return the Quantity value.
	 */
	public String getQuantity() {
		return quantity;
	}

	/**
	 * Set the Quantity value.
	 * 
	 * @param newQuantity
	 *            The new Quantity value.
	 */
	public void setQuantity(String newQuantity) {
		this.quantity = newQuantity;
	}

	/**
	 * Get the Notes value.
	 * 
	 * @return the Notes value.
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Set the Notes value.
	 * 
	 * @param newNotes
	 *            The new Notes value.
	 */
	public void setNotes(String newNotes) {
		this.notes = newNotes;
	}

	/**
	 * Get the Masterwork value.
	 * 
	 * @return the Masterwork value.
	 */
	public String getMasterwork() {
		return masterwork;
	}

	/**
	 * Set the Masterwork value.
	 * 
	 * @param newMasterwork
	 *            The new Masterwork value.
	 */
	public void setMasterwork(String newMasterwork) {
		this.masterwork = newMasterwork;
	}

	public PlayerAmmo() {

	}

	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (playerid == null) {
			playerid = "";
		}

		if (weaponid == null) {
			weaponid = "";
		}

		if (bonus == null) {
			bonus = "";
		}

		if (description == null) {
			description = "";
		}

	}

	public Vector<String> validate() {
		Vector<String> v = new Vector<String>();
		checkStrings(v);
		checkNumbers(v);
		checkDates(v);
		return v;
	}

	private void checkStrings(Vector<String> errors) {
	}

	private void checkNumbers(Vector<String> errors) {
	}

	private void checkDates(Vector<String> errors) {
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
	 * Get the value of playerid
	 * 
	 * @return a <code>String</code> value
	 */
	public String getplayerid() {
		return playerid;
	}

	/**
	 * Set the value of playerid
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setplayerid(String s) {
		playerid = s;
	}

	/**
	 * Get the value of weaponid
	 * 
	 * @return a <code>String</code> value
	 */
	public String getweaponid() {
		return weaponid;
	}

	/**
	 * Set the value of weaponid
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setweaponid(String s) {
		weaponid = s;
	}

	/**
	 * Get the value of bonus
	 * 
	 * @return a <code>String</code> value
	 */
	public String getbonus() {
		return bonus;
	}

	/**
	 * Set the value of bonus
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setbonus(String s) {
		bonus = s;
	}

	/**
	 * Get the value of description
	 * 
	 * @return a <code>String</code> value
	 */
	public String getdescription() {
		return description;
	}

	/**
	 * Set the value of description
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setdescription(String s) {
		description = s;
	}

}

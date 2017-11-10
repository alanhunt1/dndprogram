package initcheck.character;

import initcheck.database.Weapon;

import java.io.Serializable;

public class WeaponSlot implements Serializable {

	private static final long serialVersionUID = 1L;

	Weapon weapon;

	String meleeAttack;

	String rangeAttack;

	/**
	 * Get the RangeAttack value.
	 * 
	 * @return the RangeAttack value.
	 */
	public String getRangeAttack() {
		return rangeAttack;
	}

	/**
	 * Set the RangeAttack value.
	 * 
	 * @param newRangeAttack
	 *            The new RangeAttack value.
	 */
	public void setRangeAttack(String newRangeAttack) {
		this.rangeAttack = newRangeAttack;
	}

	/**
	 * Get the MeleeAttack value.
	 * 
	 * @return the MeleeAttack value.
	 */
	public String getMeleeAttack() {
		return meleeAttack;
	}

	/**
	 * Set the MeleeAttack value.
	 * 
	 * @param newMeleeAttack
	 *            The new MeleeAttack value.
	 */
	public void setMeleeAttack(String newMeleeAttack) {
		this.meleeAttack = newMeleeAttack;
	}

	/**
	 * Get the Weapon value.
	 * 
	 * @return the Weapon value.
	 */
	public Weapon getWeapon() {
		return weapon;
	}

	/**
	 * Set the Weapon value.
	 * 
	 * @param newWeapon
	 *            The new Weapon value.
	 */
	public void setWeapon(Weapon newWeapon) {
		this.weapon = newWeapon;
	}

}

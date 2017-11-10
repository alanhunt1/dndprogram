package initcheck.database;

import java.io.Serializable;
import java.util.Vector;

public class MonsterAttacks implements Serializable, Cloneable, Exportable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String monsterId;

	private String attackType;

	private String numberOfAttacks;

	private String toHit;

	private String damage;

	boolean poison;

	/**
	 * Get the Poison value.
	 * 
	 * @return the Poison value.
	 */
	public boolean isPoison() {
		return poison;
	}

	/**
	 * Set the Poison value.
	 * 
	 * @param newPoison
	 *            The new Poison value.
	 */
	public void setPoison(boolean newPoison) {
		this.poison = newPoison;
	}

	public MonsterAttacks() {

	}

	public String toString() {
		String attack = numberOfAttacks + " " + attackType + " [" + toHit
				+ " hit / " + damage + " damage]";
		if (isPoison()) {
			attack += " Poison";
		}
		return attack;
	}

	public MonsterAttacks(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("MonsterId")) {
				setMonsterId(t.getTagBody());
			}
			if (t.getTagName().equals("AttackType")) {
				setAttackType(t.getTagBody());
			}
			if (t.getTagName().equals("NumberOfAttacks")) {
				setNumberOfAttacks(t.getTagBody());
			}
			if (t.getTagName().equals("ToHit")) {
				setToHit(t.getTagBody());
			}
			if (t.getTagName().equals("Damage")) {
				setDamage(t.getTagBody());
			}
			if (t.getTagName().equals("Poison")) {
				if (t.getTagBody().equals("true")) {
					setPoison(true);
				} else {
					setPoison(false);
				}
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<MonsterAttacks>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<MonsterId>" + monsterId + "</MonsterId>\n");
		sb.append("<AttackType>" + attackType + "</AttackType>\n");
		sb.append("<NumberOfAttacks>" + numberOfAttacks
				+ "</NumberOfAttacks>\n");
		sb.append("<ToHit>" + toHit + "</ToHit>\n");
		sb.append("<Damage>" + damage + "</Damage>\n");
		sb.append("<Poison>" + poison + "</Poison>\n");
		sb.append("</MonsterAttacks>\n");
		return sb.toString();
	}

	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (monsterId == null) {
			monsterId = "";
		}

		if (attackType == null) {
			attackType = "";
		}

		if (numberOfAttacks == null) {
			numberOfAttacks = "";
		}

		if (toHit == null) {
			toHit = "";
		}

		if (damage == null) {
			damage = "";
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
		if (monsterId != null && !monsterId.equals("")) {
			try {
				Integer.parseInt(monsterId);
			} catch (Exception e) {
				errors
						.add("monsterId is not a valid number.  Please enter a valid number.");
			}
		}
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
	 * Get the value of monsterId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getMonsterId() {
		return monsterId;
	}

	/**
	 * Set the value of monsterId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setMonsterId(String s) {
		monsterId = s;
	}

	/**
	 * Get the value of attackType
	 * 
	 * @return a <code>String</code> value
	 */
	public String getAttackType() {
		return attackType;
	}

	/**
	 * Set the value of attackType
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setAttackType(String s) {
		attackType = s;
	}

	/**
	 * Get the value of numberOfAttacks
	 * 
	 * @return a <code>String</code> value
	 */
	public String getNumberOfAttacks() {
		return numberOfAttacks;
	}

	/**
	 * Set the value of numberOfAttacks
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setNumberOfAttacks(String s) {
		numberOfAttacks = s;
	}

	/**
	 * Get the value of toHit
	 * 
	 * @return a <code>String</code> value
	 */
	public String getToHit() {
		return toHit;
	}

	/**
	 * Set the value of toHit
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setToHit(String s) {
		toHit = s;
	}

	/**
	 * Get the value of damage
	 * 
	 * @return a <code>String</code> value
	 */
	public String getDamage() {
		return damage;
	}

	/**
	 * Set the value of damage
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setDamage(String s) {
		damage = s;
	}

}

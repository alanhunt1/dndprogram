package initcheck.database;

import initcheck.utils.StrManip;

import java.io.Serializable;
import java.util.Vector;

public class DamageRecord implements Serializable, Cloneable {

	
	private static final long serialVersionUID = 1L;

	private String id;

	private String partyName;

	private String playerId;

	private String damage;

	private boolean oneshot = true;

	private String weaponType;

	private String battleId;

	String weaponUse;

	String playerName;

	/**
	 * Get the PlayerName value.
	 * 
	 * @return the PlayerName value.
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Set the PlayerName value.
	 * 
	 * @param newPlayerName
	 *            The new PlayerName value.
	 */
	public void setPlayerName(String newPlayerName) {
		this.playerName = newPlayerName;
	}

	public String toString() {
		return StrManip.pad(weaponType + "(" + weaponUse + ")", 30)
				+ StrManip.pad(damage, 4) + playerName;
	}

	/**
	 * Get the WeaponUse value.
	 * 
	 * @return the WeaponUse value.
	 */
	public String getWeaponUse() {
		return weaponUse;
	}

	/**
	 * Set the WeaponUse value.
	 * 
	 * @param newWeaponUse
	 *            The new WeaponUse value.
	 */
	public void setWeaponUse(String newWeaponUse) {
		this.weaponUse = newWeaponUse;
	}

	public DamageRecord() {

	}

	public DamageRecord(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("PartyName")) {
				setPartyName(t.getTagBody());
			}
			if (t.getTagName().equals("PlayerId")) {
				setPlayerId(t.getTagBody());
			}
			if (t.getTagName().equals("Damage")) {
				setDamage(t.getTagBody());
			}
			if (t.getTagName().equals("Oneshot")) {
				if (t.getTagBody().equals("true")) {
					setOneshot(true);
				} else {
					setOneshot(false);
				}
			}
			if (t.getTagName().equals("WeaponType")) {
				setWeaponType(t.getTagBody());
			}
			if (t.getTagName().equals("BattleId")) {
				setBattleId(t.getTagBody());
			}
			if (t.getTagName().equals("WeaponUse")) {
				setWeaponUse(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<DamageRecord>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<PartyName>" + partyName + "</PartyName>\n");
		sb.append("<PlayerId>" + playerId + "</PlayerId>\n");
		sb.append("<Damage>" + damage + "</Damage>\n");
		sb.append("<Oneshot>" + oneshot + "</Oneshot>\n");
		sb.append("<WeaponType>" + weaponType + "</WeaponType>\n");
		sb.append("<BattleId>" + battleId + "</BattleId>\n");
		sb.append("<WeaponUse>" + weaponUse + "</WeaponUse>\n");
		sb.append("</DamageRecord>\n");
		return sb.toString();
	}

	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (partyName == null) {
			partyName = "";
		}

		if (playerId == null) {
			playerId = "";
		}

		if (damage == null) {
			damage = "";
		}

		if (weaponType == null) {
			weaponType = "";
		}

		if (battleId == null) {
			battleId = "";
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
		if (playerId != null && !playerId.equals("")) {
			try {
				Integer.parseInt(playerId);
			} catch (Exception e) {
				errors
						.add("playerId is not a valid number.  Please enter a valid number.");
			}
		}
		if (damage != null && !damage.equals("")) {
			try {
				Integer.parseInt(damage);
			} catch (Exception e) {
				errors
						.add("damage is not a valid number.  Please enter a valid number.");
			}
		}
		if (battleId != null && !battleId.equals("")) {
			try {
				Integer.parseInt(battleId);
			} catch (Exception e) {
				errors
						.add("battleId is not a valid number.  Please enter a valid number.");
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
	 * Get the value of partyName
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPartyName() {
		return partyName;
	}

	/**
	 * Set the value of partyName
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPartyName(String s) {
		partyName = s;
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

	/**
	 * Get the value of oneshot
	 * 
	 * @return a <code>String</code> value
	 */
	public boolean isOneshot() {
		return oneshot;
	}

	/**
	 * Set the value of oneshot
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setOneshot(boolean s) {
		oneshot = s;
	}

	/**
	 * Get the value of weaponType
	 * 
	 * @return a <code>String</code> value
	 */
	public String getWeaponType() {
		return weaponType;
	}

	/**
	 * Set the value of weaponType
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setWeaponType(String s) {
		weaponType = s;
	}

	/**
	 * Get the value of battleId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getBattleId() {
		return battleId;
	}

	/**
	 * Set the value of battleId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setBattleId(String s) {
		battleId = s;
	}

}

package initcheck.database;

import java.io.Serializable;
import java.util.Vector;

public class WeaponViews implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String playerWeaponId;

	private String featId;

	String featName;

	String type = "FEAT";

	/**
	 * Get the Type value.
	 * 
	 * @return the Type value.
	 */
	public String getType() {
		return type;
	}

	/**
	 * Set the Type value.
	 * 
	 * @param newType
	 *            The new Type value.
	 */
	public void setType(String newType) {
		this.type = newType;
	}

	/**
	 * Get the FeatName value.
	 * 
	 * @return the FeatName value.
	 */
	public String getFeatName() {
		return featName;
	}

	/**
	 * Set the FeatName value.
	 * 
	 * @param newFeatName
	 *            The new FeatName value.
	 */
	public void setFeatName(String newFeatName) {
		this.featName = newFeatName;
	}

	public WeaponViews() {

	}

	public String toString() {
		return featName;
	}

	public WeaponViews(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("PlayerWeaponId")) {
				setPlayerWeaponId(t.getTagBody());
			}
			if (t.getTagName().equals("FeatId")) {
				setFeatId(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<WeaponViews>\n");

		sb.append("<PlayerWeaponId>" + playerWeaponId + "</PlayerWeaponId>\n");
		sb.append("<FeatId>" + featId + "</FeatId>\n");
		sb.append("</WeaponViews>\n");
		return sb.toString();
	}

	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (playerWeaponId == null) {
			playerWeaponId = "";
		}

		if (featId == null) {
			featId = "";
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
		if (playerWeaponId != null && !playerWeaponId.equals("")) {
			try {
				Integer.parseInt(playerWeaponId);
			} catch (Exception e) {
				errors.add("playerWeaponId is not a valid number");
			}
		}
		if (featId != null && !featId.equals("")) {
			try {
				Integer.parseInt(featId);
			} catch (Exception e) {
				errors.add("featId is not a valid number");
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
	 * Get the value of playerWeaponId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPlayerWeaponId() {
		return playerWeaponId;
	}

	/**
	 * Set the value of playerWeaponId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPlayerWeaponId(String s) {
		playerWeaponId = s;
	}

	/**
	 * Get the value of featId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getFeatId() {
		return featId;
	}

	/**
	 * Set the value of featId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setFeatId(String s) {
		featId = s;
	}

}

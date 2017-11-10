package initcheck.database;

import java.io.Serializable;

public class RacialAbility implements Serializable, Cloneable, Exportable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String raceId;

	private String abilityName;

	private String abilityType;

	private String abilityType2;

	private String abilityValue;

	private String description;

	public RacialAbility() {

	}

	public RacialAbility(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("RaceId")) {
				setRaceId(t.getTagBody());
			}
			if (t.getTagName().equals("AbilityName")) {
				setAbilityName(t.getTagBody());
			}
			if (t.getTagName().equals("AbilityType")) {
				setAbilityType(t.getTagBody());
			}
			if (t.getTagName().equals("AbilityType2")) {
				setAbilityType2(t.getTagBody());
			}
			if (t.getTagName().equals("AbilityValue")) {
				setAbilityValue(t.getTagBody());
			}
			if (t.getTagName().equals("Description")) {
				setDescription(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<RacialAbility>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<RaceId>" + raceId + "</RaceId>\n");
		sb.append("<AbilityName>" + abilityName + "</AbilityName>\n");
		sb.append("<AbilityType>" + abilityType + "</AbilityType>\n");
		sb.append("<AbilityType2>" + abilityType2 + "</AbilityType2>\n");
		sb.append("<AbilityValue>" + abilityValue + "</AbilityValue>\n");
		sb.append("<Description>" + description + "</Description>\n");
		sb.append("</RacialAbility>\n");
		return sb.toString();
	}

	public String toString() {
		String format = abilityName + " ";
		if (abilityType != null) {
			format += abilityType + " ";
		}
		if (abilityType2 != null) {
			format += abilityType2 + " ";
		}
		if (abilityValue != null) {
			format += abilityValue;
		}
		return format;
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
	 * Get the value of raceId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getRaceId() {
		return raceId;
	}

	/**
	 * Set the value of raceId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setRaceId(String s) {
		raceId = s;
	}

	/**
	 * Get the value of abilityName
	 * 
	 * @return a <code>String</code> value
	 */
	public String getAbilityName() {
		return abilityName;
	}

	/**
	 * Set the value of abilityName
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setAbilityName(String s) {
		abilityName = s;
	}

	/**
	 * Get the value of abilityType
	 * 
	 * @return a <code>String</code> value
	 */
	public String getAbilityType() {
		return abilityType;
	}

	/**
	 * Set the value of abilityType
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setAbilityType(String s) {
		abilityType = s;
	}

	/**
	 * Get the value of abilityType2
	 * 
	 * @return a <code>String</code> value
	 */
	public String getAbilityType2() {
		return abilityType2;
	}

	/**
	 * Set the value of abilityType2
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setAbilityType2(String s) {
		abilityType2 = s;
	}

	/**
	 * Get the value of abilityValue
	 * 
	 * @return a <code>String</code> value
	 */
	public String getAbilityValue() {
		return abilityValue;
	}

	/**
	 * Set the value of abilityValue
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setAbilityValue(String s) {
		abilityValue = s;
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

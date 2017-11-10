package initcheck.database;

import initcheck.utils.StrManip;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ClassAdvancement implements Serializable, Cloneable, Exportable {

	private String id;

	private String classId = "";

	private String level = "";

	private String baseAttack = "";

	private String fortSave = "";

	private String refSave = "";

	private String willSave = "";

	private String special = "";

	private String alternate;

	public ClassAdvancement(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("ClassId")) {
				setClassId(t.getTagBody());
			}
			if (t.getTagName().equals("Level")) {
				setLevel(t.getTagBody());
			}
			if (t.getTagName().equals("BaseAttack")) {
				setBaseAttack(t.getTagBody());
			}
			if (t.getTagName().equals("FortSave")) {
				setFortSave(t.getTagBody());
			}
			if (t.getTagName().equals("RefSave")) {
				setRefSave(t.getTagBody());
			}
			if (t.getTagName().equals("WillSave")) {
				setWillSave(t.getTagBody());
			}
			if (t.getTagName().equals("Special")) {
				setSpecial(t.getTagBody());
			}
			if (t.getTagName().equals("Alternate")) {
				setAlternate(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<ClassAdvancement>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<ClassId>" + classId + "</ClassId>\n");
		sb.append("<Level>" + level + "</Level>\n");
		sb.append("<BaseAttack>" + baseAttack + "</BaseAttack>\n");
		sb.append("<FortSave>" + fortSave + "</FortSave>\n");
		sb.append("<RefSave>" + refSave + "</RefSave>\n");
		sb.append("<WillSave>" + willSave + "</WillSave>\n");
		sb.append("<Special>" + special + "</Special>\n");
		sb.append("<Alternate>" + alternate + "</Alternate>\n");
		sb.append("</ClassAdvancement>\n");
		return sb.toString();
	}

	public ClassAdvancement() {

	}

	public String toString() {
		return StrManip.pad(level, 5) + StrManip.pad(baseAttack, 15)
				+ StrManip.pad(fortSave, 5) + StrManip.pad(refSave, 5)
				+ StrManip.pad(willSave, 5) + special;
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
	 * Get the value of classId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getClassId() {
		return classId;
	}

	/**
	 * Set the value of classId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setClassId(String s) {
		classId = s;
	}

	/**
	 * Get the value of level
	 * 
	 * @return a <code>String</code> value
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * Set the value of level
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setLevel(String s) {
		level = s;
	}

	/**
	 * Get the value of baseAttack
	 * 
	 * @return a <code>String</code> value
	 */
	public String getBaseAttack() {
		return baseAttack;
	}

	/**
	 * Set the value of baseAttack
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setBaseAttack(String s) {
		baseAttack = s;
	}

	/**
	 * Get the value of fortSave
	 * 
	 * @return a <code>String</code> value
	 */
	public String getFortSave() {
		return fortSave;
	}

	/**
	 * Set the value of fortSave
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setFortSave(String s) {
		fortSave = s;
	}

	/**
	 * Get the value of refSave
	 * 
	 * @return a <code>String</code> value
	 */
	public String getRefSave() {
		return refSave;
	}

	/**
	 * Set the value of refSave
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setRefSave(String s) {
		refSave = s;
	}

	/**
	 * Get the value of willSave
	 * 
	 * @return a <code>String</code> value
	 */
	public String getWillSave() {
		return willSave;
	}

	/**
	 * Set the value of willSave
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setWillSave(String s) {
		willSave = s;
	}

	/**
	 * Get the value of special
	 * 
	 * @return a <code>String</code> value
	 */
	public String getSpecial() {
		return special;
	}

	/**
	 * Set the value of special
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setSpecial(String s) {
		special = s;
	}

	/**
	 * Get the Alternate value.
	 * 
	 * @return the Alternate value.
	 */
	public String getAlternate() {
		return alternate;
	}

	/**
	 * Set the Alternate value.
	 * 
	 * @param newAlternate
	 *            The new Alternate value.
	 */
	public void setAlternate(String newAlternate) {
		this.alternate = newAlternate;
	}

}

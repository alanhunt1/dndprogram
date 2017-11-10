package initcheck.database;

import java.io.Serializable;
import java.util.Vector;

import initcheck.graphics.TiledListItem;

public class FeatEffects implements Serializable, Cloneable, TiledListItem {

	
	private static final long serialVersionUID = 1L;

	private String id;

	private String featId;

	private String effectType;

	private String effectAmount;

	private String effectCategory;

	private String conditional;

	private String misc;

	private String effectName;
	
	private String crossRefId;
	
	public String getCrossRefId() {
		return crossRefId;
	}

	public void setCrossRefId(String crossRefId) {
		this.crossRefId = crossRefId;
	}

	public String getEffectName() {
		return effectName;
	}

	public void setEffectName(String effectName) {
		this.effectName = effectName;
	}

	public String toString(){
		return effectName+" "+effectAmount;
	}
	
	public FeatEffects() {

	}

	public FeatEffects(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("FeatId")) {
				setFeatId(t.getTagBody());
			}
			if (t.getTagName().equals("EffectType")) {
				setEffectType(t.getTagBody());
			}
			if (t.getTagName().equals("EffectAmount")) {
				setEffectAmount(t.getTagBody());
			}
			if (t.getTagName().equals("EffectCategory")) {
				setEffectCategory(t.getTagBody());
			}
			if (t.getTagName().equals("Conditional")) {
				setConditional(t.getTagBody());
			}
			if (t.getTagName().equals("Misc")) {
				setMisc(t.getTagBody());
			}
			if (t.getTagName().equals("EffectName")) {
				setEffectName(t.getTagBody());
			}
			if (t.getTagName().equals("CrossRefId")) {
				setCrossRefId(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<FeatEffects>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<FeatId>" + featId + "</FeatId>\n");
		sb.append("<EffectType>" + effectType + "</EffectType>\n");
		sb.append("<EffectAmount>" + effectAmount + "</EffectAmount>\n");
		sb.append("<EffectCategory>" + effectCategory + "</EffectCategory>\n");
		sb.append("<Conditional>" + conditional + "</Conditional>\n");
		sb.append("<Misc>" + misc + "</Misc>\n");
		sb.append("<EffectName>" + effectName + "</EffectName>\n");
		sb.append("<CrossRefId>" + crossRefId + "</CrossRefId>\n");
		sb.append("</FeatEffects>\n");
		return sb.toString();
	}

	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (featId == null) {
			featId = "";
		}

		if (effectType == null) {
			effectType = "";
		}

		if (effectAmount == null) {
			effectAmount = "";
		}

		if (effectCategory == null) {
			effectCategory = "";
		}

		if (conditional == null) {
			conditional = "";
		}

		if (misc == null) {
			misc = "";
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
		if (featId != null && !featId.equals("")) {
			try {
				Integer.parseInt(featId);
			} catch (Exception e) {
				errors
						.add("featId is not a valid number.  Please enter a valid number.");
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

	/**
	 * Get the value of effectType
	 * 
	 * @return a <code>String</code> value
	 */
	public String getEffectType() {
		return effectType;
	}

	/**
	 * Set the value of effectType
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setEffectType(String s) {
		effectType = s;
	}

	/**
	 * Get the value of effectAmount
	 * 
	 * @return a <code>String</code> value
	 */
	public String getEffectAmount() {
		return effectAmount;
	}

	/**
	 * Set the value of effectAmount
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setEffectAmount(String s) {
		effectAmount = s;
	}

	/**
	 * Get the value of effectCategory
	 * 
	 * @return a <code>String</code> value
	 */
	public String getEffectCategory() {
		return effectCategory;
	}

	/**
	 * Set the value of effectCategory
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setEffectCategory(String s) {
		effectCategory = s;
	}

	/**
	 * Get the value of conditional
	 * 
	 * @return a <code>String</code> value
	 */
	public String getConditional() {
		return conditional;
	}

	/**
	 * Set the value of conditional
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setConditional(String s) {
		conditional = s;
	}

	/**
	 * Get the value of misc
	 * 
	 * @return a <code>String</code> value
	 */
	public String getMisc() {
		return misc;
	}

	/**
	 * Set the value of misc
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setMisc(String s) {
		misc = s;
	}

}

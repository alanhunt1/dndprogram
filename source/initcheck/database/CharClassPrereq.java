package initcheck.database;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CharClassPrereq extends Prereq implements Serializable, Cloneable,
		Exportable {

	private String id;

	private String prereqType;

	private String prereqName;

	private String prereqVal;

	String chained = "N";

	String charClassId;

	String chainedTo = "";

	public CharClassPrereq(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("PrereqType")) {
				setPrereqType(t.getTagBody());
			}
			if (t.getTagName().equals("PrereqName")) {
				setPrereqName(t.getTagBody());
			}
			if (t.getTagName().equals("PrereqVal")) {
				setPrereqVal(t.getTagBody());
			}
			if (t.getTagName().equals("ClassId")) {
				setCharClassId(t.getTagBody());
			}
			if (t.getTagName().equals("chained")) {
				setChained(t.getTagBody());
			}
			if (t.getTagName().equals("ChainedTo")) {
				setChainedTo(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<ClassPrereq>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<PrereqType>" + prereqType + "</PrereqType>\n");
		sb.append("<PrereqName>" + prereqName + "</PrereqName>\n");
		sb.append("<PrereqVal>" + prereqVal + "</PrereqVal>\n");
		sb.append("<ClassId>" + charClassId + "</ClassId>\n");
		sb.append("<chained>" + chained + "</chained>\n");
		sb.append("<ChainedTo>" + chainedTo + "</ChainedTo>\n");
		sb.append("</ClassPrereq>\n");
		return sb.toString();
	}

	/**
	 * Get the ChainedTo value.
	 * 
	 * @return the ChainedTo value.
	 */
	public String getChainedTo() {
		return chainedTo;
	}

	/**
	 * Set the ChainedTo value.
	 * 
	 * @param newChainedTo
	 *            The new ChainedTo value.
	 */
	public void setChainedTo(String newChainedTo) {
		this.chainedTo = newChainedTo;
	}

	/**
	 * Get the Chained value.
	 * 
	 * @return the Chained value.
	 */
	public String getChained() {
		return chained;
	}

	/**
	 * Set the Chained value.
	 * 
	 * @param newChained
	 *            The new Chained value.
	 */
	public void setChained(String newChained) {
		this.chained = newChained;
	}

	public String toString() {
		String format = "";
		if (chained.equals("Y")) {
			format += "OR ";
		}
		format += prereqType + " : " + prereqName;
		if (prereqVal != null) {
			format += prereqVal;
		}
		return format;
	}

	/**
	 * Get the CharClassId value.
	 * 
	 * @return the CharClassId value.
	 */
	public String getCharClassId() {
		return charClassId;
	}

	/**
	 * Set the CharClassId value.
	 * 
	 * @param newCharClassId
	 *            The new CharClassId value.
	 */
	public void setCharClassId(String newCharClassId) {
		this.charClassId = newCharClassId;
	}

	public CharClassPrereq() {

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
	 * Get the value of prereqType
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPrereqType() {
		return prereqType;
	}

	/**
	 * Set the value of prereqType
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPrereqType(String s) {
		prereqType = s;
	}

	/**
	 * Get the value of prereqName
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPrereqName() {
		return prereqName;
	}

	/**
	 * Set the value of prereqName
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPrereqName(String s) {
		prereqName = s;
	}

	/**
	 * Get the value of prereqVal
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPrereqVal() {
		return prereqVal;
	}

	/**
	 * Set the value of prereqVal
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPrereqVal(String s) {
		prereqVal = s;
	}

}

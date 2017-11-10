package initcheck.database;

import java.io.Serializable;

import initcheck.graphics.TiledListItem;

public class FeatPrereq extends Prereq implements Serializable, Cloneable,
		Exportable, TiledListItem {
	static final long serialVersionUID = 1;

	private String id;

	private String prereqType;

	private String prereqName;

	private String prereqVal;

	private String featId;

	String chained = "N";

	String chainedTo;

	String crossRefId;

	public FeatPrereq(String s) {
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
			if (t.getTagName().equals("FeatId")) {
				setFeatId(t.getTagBody());
			}
			if (t.getTagName().equals("Chained")) {
				setChained(t.getTagBody());
			}
			if (t.getTagName().equals("ChainedTo")) {
				setChainedTo(t.getTagBody());
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
		sb.append("<FeatPrereq>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<PrereqType>" + prereqType + "</PrereqType>\n");
		sb.append("<PrereqName>" + prereqName + "</PrereqName>\n");
		sb.append("<PrereqVal>" + prereqVal + "</PrereqVal>\n");
		sb.append("<FeatId>" + featId + "</FeatId>\n");
		sb.append("<Chained>" + chained + "</Chained>\n");
		sb.append("<ChainedTo>" + chainedTo + "</ChainedTo>\n");
		sb.append("<CrossRefId>" + crossRefId + "</CrossRefId>\n");
		sb.append("</FeatPrereq>\n");
		return sb.toString();
	}

	/**
	 * Get the CrossRefId value.
	 * 
	 * @return the CrossRefId value.
	 */
	public String getCrossRefId() {
		return crossRefId;
	}

	/**
	 * Set the CrossRefId value.
	 * 
	 * @param newCrossRefId
	 *            The new CrossRefId value.
	 */
	public void setCrossRefId(String newCrossRefId) {
		this.crossRefId = newCrossRefId;
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

	public FeatPrereq() {

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

package initcheck.database;

import java.io.Serializable;
import java.util.Vector;

import initcheck.graphics.TiledListItem;

public class CampaignBlackList implements Serializable, Cloneable, TiledListItem {

	private static final long serialVersionUID = 1L;

	private String id;

	private String campaignId;

	private String type;

	private String xrefId;

	private String sourceId;

	private String sourceName;
	
	private String xrefName;
	
	public String toString(){
		return type+ " : "+xrefName;
	}
	
	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getXrefName() {
		return xrefName;
	}

	public void setXrefName(String xrefName) {
		this.xrefName = xrefName;
	}

	public CampaignBlackList() {

	}

	public CampaignBlackList(String s) {
		readImport(s);
	}

	public void readImport(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("CampaignId")) {
				setCampaignId(t.getTagBody());
			}
			if (t.getTagName().equals("Type")) {
				setType(t.getTagBody());
			}
			if (t.getTagName().equals("XrefId")) {
				setXrefId(t.getTagBody());
			}
			if (t.getTagName().equals("SourceId")) {
				setSourceId(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<CampaignBlackList>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<CampaignId>" + campaignId + "</CampaignId>\n");
		sb.append("<Type>" + type + "</Type>\n");
		sb.append("<XrefId>" + xrefId + "</XrefId>\n");
		sb.append("<SourceId>" + sourceId + "</SourceId>\n");
		sb.append("</CampaignBlackList>\n");
		return sb.toString();
	}

	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (campaignId == null) {
			campaignId = "";
		}

		if (type == null) {
			type = "";
		}

		if (xrefId == null) {
			xrefId = "";
		}

		if (sourceId == null) {
			sourceId = "";
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
		if (campaignId != null && !campaignId.equals("")) {
			try {
				Integer.parseInt(campaignId);
			} catch (Exception e) {
				errors
						.add("campaignId is not a valid number.  Please enter a valid number.");
			}
		}
		if (xrefId != null && !xrefId.equals("")) {
			try {
				Integer.parseInt(xrefId);
			} catch (Exception e) {
				errors
						.add("xrefId is not a valid number.  Please enter a valid number.");
			}
		}
		if (sourceId != null && !sourceId.equals("")) {
			try {
				Integer.parseInt(sourceId);
			} catch (Exception e) {
				errors
						.add("sourceId is not a valid number.  Please enter a valid number.");
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
	 * Get the value of campaignId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getCampaignId() {
		return campaignId;
	}

	/**
	 * Set the value of campaignId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setCampaignId(String s) {
		campaignId = s;
	}

	/**
	 * Get the value of type
	 * 
	 * @return a <code>String</code> value
	 */
	public String getType() {
		return type;
	}

	/**
	 * Set the value of type
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setType(String s) {
		type = s;
	}

	/**
	 * Get the value of xrefId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getXrefId() {
		return xrefId;
	}

	/**
	 * Set the value of xrefId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setXrefId(String s) {
		xrefId = s;
	}

	/**
	 * Get the value of sourceId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getSourceId() {
		return sourceId;
	}

	/**
	 * Set the value of sourceId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setSourceId(String s) {
		sourceId = s;
	}

}

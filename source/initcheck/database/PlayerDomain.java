package initcheck.database;

import java.io.Serializable;
import java.util.Vector;

public class PlayerDomain implements Serializable, Cloneable {

	
	private static final long serialVersionUID = 1L;

	private String id;

	private String playerId;

	private String classId;

	private String domainId;

	String domainName;

	String shortDesc;

	/**
	 * Get the ShortDesc value.
	 * 
	 * @return the ShortDesc value.
	 */
	public String getShortDesc() {
		return shortDesc;
	}

	/**
	 * Set the ShortDesc value.
	 * 
	 * @param newShortDesc
	 *            The new ShortDesc value.
	 */
	public void setShortDesc(String newShortDesc) {
		this.shortDesc = newShortDesc;
	}

	/**
	 * Get the DomainName value.
	 * 
	 * @return the DomainName value.
	 */
	public String getDomainName() {
		return domainName;
	}

	/**
	 * Set the DomainName value.
	 * 
	 * @param newDomainName
	 *            The new DomainName value.
	 */
	public void setDomainName(String newDomainName) {
		this.domainName = newDomainName;
	}

	public String toString() {
		return domainName + " " + shortDesc;
	}

	public PlayerDomain() {

	}

	public PlayerDomain(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("PlayerId")) {
				setPlayerId(t.getTagBody());
			}
			if (t.getTagName().equals("ClassId")) {
				setClassId(t.getTagBody());
			}
			if (t.getTagName().equals("DomainId")) {
				setDomainId(t.getTagBody());
			}
			s = s.substring(tagName.length() + tagName.length() + 5
					+ tagBody.length(), s.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<PlayerDomain>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<PlayerId>" + playerId + "</PlayerId>\n");
		sb.append("<ClassId>" + classId + "</ClassId>\n");
		sb.append("<DomainId>" + domainId + "</DomainId>\n");
		sb.append("</PlayerDomain>\n");
		return sb.toString();
	}

	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (playerId == null) {
			playerId = "";
		}

		if (classId == null) {
			classId = "";
		}

		if (domainId == null) {
			domainId = "";
		}

	}

	public Vector<String> validate() {
		Vector<String>  v = new Vector<String> ();
		checkStrings(v);
		checkNumbers(v);
		checkDates(v);
		return v;
	}

	private void checkStrings(Vector<String> errors) {
	}

	private void checkNumbers(Vector<String>  errors) {
		if (playerId != null && !playerId.equals("")) {
			try {
				Integer.parseInt(playerId);
			} catch (Exception e) {
				errors.add("playerId is not a valid number");
			}
		}
		if (classId != null && !classId.equals("")) {
			try {
				Integer.parseInt(classId);
			} catch (Exception e) {
				errors.add("classId is not a valid number");
			}
		}
		if (domainId != null && !domainId.equals("")) {
			try {
				Integer.parseInt(domainId);
			} catch (Exception e) {
				errors.add("domainId is not a valid number");
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
	 * Get the value of domainId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getDomainId() {
		return domainId;
	}

	/**
	 * Set the value of domainId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setDomainId(String s) {
		domainId = s;
	}

}

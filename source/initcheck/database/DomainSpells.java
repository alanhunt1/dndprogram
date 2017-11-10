package initcheck.database;

import java.io.Serializable;

public class DomainSpells implements Serializable, Cloneable {

	
	private static final long serialVersionUID = 1L;

	private String id;

	private String domainId;

	private String spellId;

	public DomainSpells() {

	}

	public DomainSpells(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("DomainId")) {
				setDomainId(t.getTagBody());
			}
			if (t.getTagName().equals("SpellId")) {
				setSpellId(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<DomainSpells>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<DomainId>" + domainId + "</DomainId>\n");
		sb.append("<SpellId>" + spellId + "</SpellId>\n");
		sb.append("</DomainSpells>\n");
		return sb.toString();
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

	/**
	 * Get the value of spellId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getSpellId() {
		return spellId;
	}

	/**
	 * Set the value of spellId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setSpellId(String s) {
		spellId = s;
	}

}

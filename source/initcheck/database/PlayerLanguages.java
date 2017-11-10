package initcheck.database;

import java.io.Serializable;

import initcheck.graphics.TiledListItem;

public class PlayerLanguages implements Serializable, Cloneable, TiledListItem {

	private static final long serialVersionUID = 1L;

	private String id;

	private String playerId;

	private String language;

	public PlayerLanguages() {

	}

	public PlayerLanguages(String s) {
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
			if (t.getTagName().equals("Language")) {
				setLanguage(t.getTagBody());
			}
			s = s.substring(tagName.length() + tagName.length() + 5
					+ tagBody.length(), s.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<PlayerLanguage>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<Language>" + language + "</Language>\n");
		sb.append("</PlayerLanguage>\n");
		return sb.toString();
	}

	public String toString() {
		return language;
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
	 * Get the value of language
	 * 
	 * @return a <code>String</code> value
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Set the value of language
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setLanguage(String s) {
		language = s;
	}

}

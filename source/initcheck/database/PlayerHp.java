package initcheck.database;

import java.io.Serializable;
import java.util.Vector;

import initcheck.graphics.TiledListItem;

public class PlayerHp implements Serializable, Cloneable, TiledListItem {

	private static final long serialVersionUID = 1L;

	private String id;

	private String playerId;

	private String level;

	private String hitPoints;

	private String classId;

	String className;

	public PlayerHp(String s) {
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
			if (t.getTagName().equals("Level")) {
				setLevel(t.getTagBody());
			}
			if (t.getTagName().equals("HitPoints")) {
				setHitPoints(t.getTagBody());
			}
			if (t.getTagName().equals("ClassId")) {
				setClassId(t.getTagBody());
			}
			if (t.getTagName().equals("ClassName")) {
				setClassName(t.getTagBody());
			}
			s = s.substring(tagName.length() + tagName.length() + 5
					+ tagBody.length(), s.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<PlayerHp>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<Level>" + level + "</Level>\n");
		sb.append("<HitPoints>" + hitPoints + "</HitPoints>\n");
		sb.append("<ClassId>" + classId + "</ClassId>\n");
		sb.append("<ClassName>" + className + "</ClassName>\n");
		sb.append("</PlayerHp>\n");
		return sb.toString();
	}

	/**
	 * Get the ClassName value.
	 * 
	 * @return the ClassName value.
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * Set the ClassName value.
	 * 
	 * @param newClassName
	 *            The new ClassName value.
	 */
	public void setClassName(String newClassName) {
		this.className = newClassName;
	}

	public PlayerHp() {

	}

	public String toString() {
		return "level " + level + " (" + className + " ) : " + hitPoints
				+ " HP";
	}

	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (playerId == null) {
			playerId = "";
		}

		if (level == null) {
			level = "";
		}

		if (hitPoints == null) {
			hitPoints = "";
		}

		if (classId == null) {
			classId = "";
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
	 * Get the value of hitPoints
	 * 
	 * @return a <code>String</code> value
	 */
	public String getHitPoints() {
		return hitPoints;
	}

	/**
	 * Set the value of hitPoints
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setHitPoints(String s) {
		hitPoints = s;
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

}

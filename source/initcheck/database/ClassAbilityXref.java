package initcheck.database;

import java.io.Serializable;
import java.util.Vector;

@SuppressWarnings("serial")
public class ClassAbilityXref implements Serializable, Cloneable, Exportable {

	private String id;

	private String classId;

	private String classLevel;

	private String classAbilityId;

	public ClassAbilityXref() {

	}

	public ClassAbilityXref(String s) {
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
			if (t.getTagName().equals("ClassLevel")) {
				setClassLevel(t.getTagBody());
			}
			if (t.getTagName().equals("ClassAbilityId")) {
				setClassAbilityId(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<ClassAbilityXref>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<ClassId>" + classId + "</ClassId>\n");
		sb.append("<ClassLevel>" + classLevel + "</ClassLevel>\n");
		sb.append("<ClassAbilityId>" + classAbilityId + "</ClassAbilityId>\n");
		sb.append("</ClassAbilityXref>\n");
		return sb.toString();
	}

	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (classId == null) {
			classId = "";
		}

		if (classLevel == null) {
			classLevel = "";
		}

		if (classAbilityId == null) {
			classAbilityId = "";
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
		if (classId != null && !classId.equals("")) {
			try {
				Integer.parseInt(classId);
			} catch (Exception e) {
				errors.add("classId is not a valid number.  Please enter a valid number.");
			}
		}
		if (classLevel != null && !classLevel.equals("")) {
			try {
				Integer.parseInt(classLevel);
			} catch (Exception e) {
				errors.add("classLevel is not a valid number.  Please enter a valid number.");
			}
		}
		if (classAbilityId != null && !classAbilityId.equals("")) {
			try {
				Integer.parseInt(classAbilityId);
			} catch (Exception e) {
				errors.add("classAbilityId is not a valid number.  Please enter a valid number.");
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
	 * Get the value of classLevel
	 * 
	 * @return a <code>String</code> value
	 */
	public String getClassLevel() {
		return classLevel;
	}

	/**
	 * Set the value of classLevel
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setClassLevel(String s) {
		classLevel = s;
	}

	/**
	 * Get the value of classAbilityId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getClassAbilityId() {
		return classAbilityId;
	}

	/**
	 * Set the value of classAbilityId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setClassAbilityId(String s) {
		classAbilityId = s;
	}

}

package initcheck.database;

import java.io.Serializable;
import java.util.Vector;

import initcheck.graphics.TiledListItem;

public class ClassProficiency implements Serializable, Cloneable, Exportable, TiledListItem {

	
	private static final long serialVersionUID = 1L;

	private String id;

	private String idType;

	private String classId;

	private String profType;

	private String profSubtype;

	public ClassProficiency() {

	}

	public ClassProficiency(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("IdType")) {
				setIdType(t.getTagBody());
			}
			if (t.getTagName().equals("ClassId")) {
				setClassId(t.getTagBody());
			}
			if (t.getTagName().equals("ProfType")) {
				setProfType(t.getTagBody());
			}
			if (t.getTagName().equals("ProfSubtype")) {
				setProfSubtype(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<ClassProficiency>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<IdType>" + idType + "</IdType>\n");
		sb.append("<ClassId>" + classId + "</ClassId>\n");
		sb.append("<ProfType>" + profType + "</ProfType>\n");
		sb.append("<ProfSubtype>" + profSubtype + "</ProfSubtype>\n");
		sb.append("</ClassProficiency>\n");
		return sb.toString();
	}

	public String toString() {
		return profType + ": " + profSubtype;
	}

	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (idType == null) {
			idType = "";
		}

		if (classId == null) {
			classId = "";
		}

		if (profType == null) {
			profType = "";
		}

		if (profSubtype == null) {
			profSubtype = "";
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
				errors.add("classId is not a valid number");
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
	 * Get the value of idType
	 * 
	 * @return a <code>String</code> value
	 */
	public String getIdType() {
		return idType;
	}

	/**
	 * Set the value of idType
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setIdType(String s) {
		idType = s;
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
	 * Get the value of profType
	 * 
	 * @return a <code>String</code> value
	 */
	public String getProfType() {
		return profType;
	}

	/**
	 * Set the value of profType
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setProfType(String s) {
		profType = s;
	}

	/**
	 * Get the value of profSubtype
	 * 
	 * @return a <code>String</code> value
	 */
	public String getProfSubtype() {
		return profSubtype;
	}

	/**
	 * Set the value of profSubtype
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setProfSubtype(String s) {
		profSubtype = s;
	}

}

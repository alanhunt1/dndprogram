package initcheck.database;

import java.io.Serializable;
import java.util.Vector;

public class Alignment implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String alignment;

	private String description;

	public Alignment() {

	}

	public String getFullDescription(){
		return description;
	}
	
	public void readImport(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("Alignment")) {
				setAlignment(t.getTagBody());
			}
			if (t.getTagName().equals("Description")) {
				setDescription(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Alignment>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<Alignment>" + alignment + "</Alignment>\n");
		sb.append("<Description>" + description + "</Description>\n");
		sb.append("</Alignment>\n");
		return sb.toString();
	}

	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (alignment == null) {
			alignment = "";
		}

		if (description == null) {
			description = "";
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
	 * Get the value of alignment
	 * 
	 * @return a <code>String</code> value
	 */
	public String getAlignment() {
		return alignment;
	}

	/**
	 * Set the value of alignment
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setAlignment(String s) {
		alignment = s;
	}

	/**
	 * Get the value of description
	 * 
	 * @return a <code>String</code> value
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the value of description
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setDescription(String s) {
		description = s;
	}

}

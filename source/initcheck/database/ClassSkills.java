package initcheck.database;

import java.io.Serializable;

public class ClassSkills implements Serializable, Cloneable, Exportable {

	
	private static final long serialVersionUID = 1L;

	private String id;

	private String classId;

	private String skillId;

	public ClassSkills() {

	}

	public ClassSkills(String s) {
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
			if (t.getTagName().equals("SkillId")) {
				setSkillId(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<ClassSkills>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<ClassId>" + classId + "</ClassId>\n");
		sb.append("<SkillId>" + skillId + "</SkillId>\n");
		sb.append("</ClassSkills>\n");
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
	 * Get the value of skillId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getSkillId() {
		return skillId;
	}

	/**
	 * Set the value of skillId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setSkillId(String s) {
		skillId = s;
	}

}

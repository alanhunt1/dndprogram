package initcheck.database;

import java.io.Serializable;
import java.util.Vector;

import initcheck.graphics.TiledListItem;
import initcheck.utils.StrManip;

public class SpellLevel implements Serializable, Cloneable, Exportable, TiledListItem {

	private static final long serialVersionUID = 1L;

	private String id;

	private String classId;

	private String className;

	private String classLevel;

	private String level0;

	private String level1;

	private String level2;

	private String level3;

	private String level4;

	private String level5;

	private String level6;

	private String level7;

	private String level8;

	private String level9;
	
	boolean alternate;

	public SpellLevel(String s) {
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
			if (t.getTagName().equals("ClassName")) {
				setClassName(t.getTagBody());
			}
			if (t.getTagName().equals("ClassLevel")) {
				setClassLevel(t.getTagBody());
			}
			if (t.getTagName().equals("Level0")) {
				setLevel0(t.getTagBody());
			}
			if (t.getTagName().equals("Level1")) {
				setLevel1(t.getTagBody());
			}
			if (t.getTagName().equals("Level2")) {
				setLevel2(t.getTagBody());
			}
			if (t.getTagName().equals("Level3")) {
				setLevel3(t.getTagBody());
			}
			if (t.getTagName().equals("Level4")) {
				setLevel4(t.getTagBody());
			}
			if (t.getTagName().equals("Level5")) {
				setLevel5(t.getTagBody());
			}
			if (t.getTagName().equals("Level6")) {
				setLevel6(t.getTagBody());
			}
			if (t.getTagName().equals("Level7")) {
				setLevel7(t.getTagBody());
			}
			if (t.getTagName().equals("Level8")) {
				setLevel8(t.getTagBody());
			}
			if (t.getTagName().equals("Level9")) {
				setLevel9(t.getTagBody());
			}
			if (t.getTagName().equals("Alternate")) {
				if (t.getTagBody().equalsIgnoreCase("true")) {
					setAlternate(true);
				} else {
					setAlternate(false);
				}
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<SpellLevel>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<ClassId>" + classId + "</ClassId>\n");
		sb.append("<ClassName>" + className + "</ClassName>\n");
		sb.append("<ClassLevel>" + classLevel + "</ClassLevel>\n");
		sb.append("<Level0>" + level0 + "</Level0>\n");
		sb.append("<Level1>" + level1 + "</Level1>\n");
		sb.append("<Level2>" + level2 + "</Level2>\n");
		sb.append("<Level3>" + level3 + "</Level3>\n");
		sb.append("<Level4>" + level4 + "</Level4>\n");
		sb.append("<Level5>" + level5 + "</Level5>\n");
		sb.append("<Level6>" + level6 + "</Level6>\n");
		sb.append("<Level7>" + level7 + "</Level7>\n");
		sb.append("<Level8>" + level8 + "</Level8>\n");
		sb.append("<Level9>" + level9 + "</Level9>\n");
		sb.append("<Alternate>" + alternate + "</Alternate>\n");
		sb.append("</SpellLevel>\n");
		return sb.toString();
	}

	/**
	 * Get the Alternate value.
	 * 
	 * @return the Alternate value.
	 */
	public boolean isAlternate() {
		return alternate;
	}

	/**
	 * Set the Alternate value.
	 * 
	 * @param newAlternate
	 *            The new Alternate value.
	 */
	public void setAlternate(boolean newAlternate) {
		this.alternate = newAlternate;
	}

	public SpellLevel() {

	}

	public SpellLevel applyBonus(SpellLevel s) {
		level0 = level0 + "(" + s.getLevel0() + ")";
		level1 = level1 + "(" + s.getLevel1() + ")";
		level2 = level2 + "(" + s.getLevel2() + ")";
		level3 = level3 + "(" + s.getLevel3() + ")";
		level4 = level4 + "(" + s.getLevel4() + ")";
		level5 = level5 + "(" + s.getLevel5() + ")";
		level6 = level6 + "(" + s.getLevel6() + ")";
		level7 = level7 + "(" + s.getLevel7() + ")";
		level8 = level8 + "(" + s.getLevel8() + ")";
		level9 = level9 + "(" + s.getLevel9() + ")";
		return this;
	}

	public String getLevel(int level) {
		switch (level) {
		case 0:
			return level0;
		case 1:
			return level1;
		case 2:
			return level2;
		case 3:
			return level3;
		case 4:
			return level4;
		case 5:
			return level5;
		case 6:
			return level6;
		case 7:
			return level7;
		case 8:
			return level8;
		case 9:
			return level9;

		}
		return "-1";
	}

	public String listFormat() {
		StringBuffer format = new StringBuffer();

		format.append(StrManip.pad(className, 15));
		if (!level0.startsWith("-1")) {
			format.append(StrManip.pad(level0, 5));
		} else {
			format.append(StrManip.pad("-", 5));
		}
		if (!level1.startsWith("-1")) {
			format.append(StrManip.pad(level1, 5));
		} else {
			format.append(StrManip.pad("-", 5));
		}
		if (!level2.startsWith("-1")) {
			format.append(StrManip.pad(level2, 5));
		} else {
			format.append(StrManip.pad("-", 5));
		}
		if (!level3.startsWith("-1")) {
			format.append(StrManip.pad(level3, 5));
		} else {
			format.append(StrManip.pad("-", 5));
		}
		if (!level4.startsWith("-1")) {
			format.append(StrManip.pad(level4, 5));
		} else {
			format.append(StrManip.pad("-", 5));
		}
		if (!level5.startsWith("-1")) {
			format.append(StrManip.pad(level5, 5));
		} else {
			format.append(StrManip.pad("-", 5));
		}
		if (!level6.startsWith("-1")) {
			format.append(StrManip.pad(level6, 5));
		} else {
			format.append(StrManip.pad("-", 5));
		}
		if (!level7.startsWith("-1")) {
			format.append(StrManip.pad(level7, 5));
		} else {
			format.append(StrManip.pad("-", 5));
		}
		if (!level8.startsWith("-1")) {
			format.append(StrManip.pad(level8, 5));
		} else {
			format.append(StrManip.pad("-", 5));
		}
		if (!level9.startsWith("-1")) {
			format.append(StrManip.pad(level9, 5));
		} else {
			format.append(StrManip.pad("-", 5));
		}
		return format.toString();

	}

	public String toString() {
		return StrManip.pad("LEVEL " + classLevel + ": ", 10)
				+ StrManip.pad(level0, 3) + StrManip.pad(level1, 3)
				+ StrManip.pad(level2, 3) + StrManip.pad(level3, 3)
				+ StrManip.pad(level4, 3) + StrManip.pad(level5, 3)
				+ StrManip.pad(level6, 3) + StrManip.pad(level7, 3)
				+ StrManip.pad(level8, 3) + StrManip.pad(level9, 3);
	}

	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (classId == null) {
			classId = "";
		}

		if (className == null) {
			className = "";
		}

		if (classLevel == null) {
			classLevel = "";
		}

		if (level0 == null) {
			level0 = "";
		}

		if (level1 == null) {
			level1 = "";
		}

		if (level2 == null) {
			level2 = "";
		}

		if (level3 == null) {
			level3 = "";
		}

		if (level4 == null) {
			level4 = "";
		}

		if (level5 == null) {
			level5 = "";
		}

		if (level6 == null) {
			level6 = "";
		}

		if (level7 == null) {
			level7 = "";
		}

		if (level8 == null) {
			level8 = "";
		}

		if (level9 == null) {
			level9 = "";
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
	 * Get the value of className
	 * 
	 * @return a <code>String</code> value
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * Set the value of className
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setClassName(String s) {
		className = s;
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
	 * Get the value of level0
	 * 
	 * @return a <code>String</code> value
	 */
	public String getLevel0() {
		return level0;
	}

	/**
	 * Set the value of level0
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setLevel0(String s) {
		level0 = s;
	}

	/**
	 * Get the value of level1
	 * 
	 * @return a <code>String</code> value
	 */
	public String getLevel1() {
		return level1;
	}

	/**
	 * Set the value of level1
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setLevel1(String s) {
		level1 = s;
	}

	/**
	 * Get the value of level2
	 * 
	 * @return a <code>String</code> value
	 */
	public String getLevel2() {
		return level2;
	}

	/**
	 * Set the value of level2
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setLevel2(String s) {
		level2 = s;
	}

	/**
	 * Get the value of level3
	 * 
	 * @return a <code>String</code> value
	 */
	public String getLevel3() {
		return level3;
	}

	/**
	 * Set the value of level3
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setLevel3(String s) {
		level3 = s;
	}

	/**
	 * Get the value of level4
	 * 
	 * @return a <code>String</code> value
	 */
	public String getLevel4() {
		return level4;
	}

	/**
	 * Set the value of level4
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setLevel4(String s) {
		level4 = s;
	}

	/**
	 * Get the value of level5
	 * 
	 * @return a <code>String</code> value
	 */
	public String getLevel5() {
		return level5;
	}

	/**
	 * Set the value of level5
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setLevel5(String s) {
		level5 = s;
	}

	/**
	 * Get the value of level6
	 * 
	 * @return a <code>String</code> value
	 */
	public String getLevel6() {
		return level6;
	}

	/**
	 * Set the value of level6
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setLevel6(String s) {
		level6 = s;
	}

	/**
	 * Get the value of level7
	 * 
	 * @return a <code>String</code> value
	 */
	public String getLevel7() {
		return level7;
	}

	/**
	 * Set the value of level7
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setLevel7(String s) {
		level7 = s;
	}

	/**
	 * Get the value of level8
	 * 
	 * @return a <code>String</code> value
	 */
	public String getLevel8() {
		return level8;
	}

	/**
	 * Set the value of level8
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setLevel8(String s) {
		level8 = s;
	}

	/**
	 * Get the value of level9
	 * 
	 * @return a <code>String</code> value
	 */
	public String getLevel9() {
		return level9;
	}

	/**
	 * Set the value of level9
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setLevel9(String s) {
		level9 = s;
	}

}

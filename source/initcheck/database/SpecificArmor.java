package initcheck.database;

import initcheck.character.library.LibraryItem;
import initcheck.utils.StrManip;

import java.io.Serializable;
import java.util.Vector;

public class SpecificArmor implements Item, Serializable, Cloneable, Exportable,
		LibraryItem, Importable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String type;

	private String minorLevel;

	private String name;

	private String description;

	private String source;

	private String mediumLevel;

	private String majorLevel;

	public String listFormat(){
		return StrManip.pad(name, 30)+StrManip.pad(source, 20);
	}
	
	public SpecificArmor() {

	}

	public String listValue(){
		return "Specific Armor : "+name;
	}
	
	public String toString() {
		return name;
	}

	public SpecificArmor(String s) {
		readImport(s);
	}
	
	public void readImport(String s){
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("Type")) {
				setType(t.getTagBody());
			}
			if (t.getTagName().equals("MinorLevel")) {
				setMinorLevel(t.getTagBody());
			}
			if (t.getTagName().equals("Name")) {
				setName(t.getTagBody());
			}
			if (t.getTagName().equals("Description")) {
				setDescription(t.getTagBody());
			}
			if (t.getTagName().equals("Source")) {
				setSource(t.getTagBody());
			}
			if (t.getTagName().equals("MediumLevel")) {
				setMediumLevel(t.getTagBody());
			}
			if (t.getTagName().equals("MajorLevel")) {
				setMajorLevel(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<SpecificArmor>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<Type>" + type + "</Type>\n");
		sb.append("<MinorLevel>" + minorLevel + "</MinorLevel>\n");
		sb.append("<Name>" + name + "</Name>\n");
		sb.append("<Description>" + description + "</Description>\n");
		sb.append("<Source>" + source + "</Source>\n");
		sb.append("<MediumLevel>" + mediumLevel + "</MediumLevel>\n");
		sb.append("<MajorLevel>" + majorLevel + "</MajorLevel>\n");
		sb.append("</SpecificArmor>\n");
		return sb.toString();
	}

	public String existsAs(){
		SpecificArmorDAO db = new SpecificArmorDAO();
		Vector<SpecificArmor> v = db.selectSpecificArmor(this);
		if(v.size() > 0){
			return ((SpecificArmor)v.get(0)).getName();
		}
		return null;
	}
	
	public Vector<SpecificArmor> getValues(){
		SpecificArmorDAO db = new SpecificArmorDAO();
		return db.getSpecificArmor();
	}
	
	public void save(boolean overwrite){
		SpecificArmorDAO db = new SpecificArmorDAO();
		if (overwrite){
			db.addOrUpdateSpecificArmor(this);
		}else{
			db.addSpecificArmor(this);
		}
	}
	
	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (type == null) {
			type = "";
		}

		if (minorLevel == null) {
			minorLevel = "";
		}

		if (name == null) {
			name = "";
		}

		if (description == null) {
			description = "";
		}

		if (source == null) {
			source = "";
		}

		if (mediumLevel == null) {
			mediumLevel = "";
		}

		if (majorLevel == null) {
			majorLevel = "";
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
	 * Get the value of minorLevel
	 * 
	 * @return a <code>String</code> value
	 */
	public String getMinorLevel() {
		return minorLevel;
	}

	/**
	 * Set the value of minorLevel
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setMinorLevel(String s) {
		minorLevel = s;
	}

	/**
	 * Get the value of name
	 * 
	 * @return a <code>String</code> value
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the value of name
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setName(String s) {
		name = s;
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

	/**
	 * Get the value of source
	 * 
	 * @return a <code>String</code> value
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Set the value of source
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setSource(String s) {
		source = s;
	}

	/**
	 * Get the value of mediumLevel
	 * 
	 * @return a <code>String</code> value
	 */
	public String getMediumLevel() {
		return mediumLevel;
	}

	/**
	 * Set the value of mediumLevel
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setMediumLevel(String s) {
		mediumLevel = s;
	}

	/**
	 * Get the value of majorLevel
	 * 
	 * @return a <code>String</code> value
	 */
	public String getMajorLevel() {
		return majorLevel;
	}

	/**
	 * Set the value of majorLevel
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setMajorLevel(String s) {
		majorLevel = s;
	}

	public String getFullDescription() {
		// TODO Auto-generated method stub
		return description;
	}

	public String getDisplayType() {
		// TODO Auto-generated method stub
		return "Specific Armor";
	}

}

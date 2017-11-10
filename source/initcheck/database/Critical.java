package initcheck.database;

import initcheck.character.library.LibraryItem;

import java.io.Serializable;
import java.util.Vector;

public class Critical implements Serializable, Cloneable, Exportable,
		LibraryItem, Importable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String category;

	private String description;

	private String type;

	public String getName(){
		return category;
	}
	
	public void setName(String s){
		
	}
	
	public String listValue(){
		return "Critical : "+listFormat();
	}
	
	public Critical() {

	}

	public String listFormat() {
		if (description.length() < 100) {
			return pad(type, 10) + pad(category, 30) + description;
		}
		return pad(type, 10) + pad(category, 30)
				+ description.substring(0, 100);
	}

	public String pad(String s, int l) {

		if (s == null) {
			s = "";
		}

		if (s.length() > l) {
			return s.substring(0, l);
		}

		char[] chars = new char[l];
		for (int i = 0; i < s.length(); i++) {
			chars[i] = s.charAt(i);
		}
		for (int i = s.length(); i < l; i++) {
			chars[i] = ' ';
		}
		return new String(chars);
	}

	public String toString() {
		return type + " " + category + " " + description;
	}

	public String getFullDescription() {
		return description;
	}

	
	
	public Critical(String s) {
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
			if (t.getTagName().equals("Category")) {
				setCategory(t.getTagBody());
			}
			if (t.getTagName().equals("Description")) {
				setDescription(t.getTagBody());
			}
			if (t.getTagName().equals("Type")) {
				setType(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Critical>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<Category>" + category + "</Category>\n");
		sb.append("<Type>" + type + "</Type>\n");
		sb.append("<Description>" + description + "</Description>\n");
		sb.append("</Critical>\n");
		return sb.toString();
	}

	public String existsAs(){
		CriticalDAO db = new CriticalDAO();
		Vector<Critical> v = db.selectCritical(this);
		if(v.size() > 0){
			return ((Critical)v.get(0)).getName();
		}
		return null;
	}
	
	public Vector<Critical> getValues(){
		CriticalDAO db = new CriticalDAO();
		return db.getCriticals();
	}
	
	public void save(boolean overwrite){
		CriticalDAO db = new CriticalDAO();
		if (overwrite){
			db.addOrUpdateCritical(this);
		}else{
			db.addCritical(this);
		}
	}
	
	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (category == null) {
			category = "";
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
	 * Get the Type value.
	 * 
	 * @return the Type value.
	 */
	public String getType() {
		return type;
	}

	/**
	 * Set the Type value.
	 * 
	 * @param newType
	 *            The new Type value.
	 */
	public void setType(String newType) {
		this.type = newType;
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
	 * Get the value of category
	 * 
	 * @return a <code>String</code> value
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Set the value of category
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setCategory(String s) {
		category = s;
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

	public String getSource() {
		// TODO Auto-generated method stub
		return null;
	}

}

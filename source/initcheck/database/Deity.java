package initcheck.database;

import initcheck.character.library.LibraryItem;
import initcheck.utils.StrManip;

import java.io.Serializable;
import java.util.Vector;

public class Deity implements Serializable, Cloneable, Exportable, Importable, LibraryItem {

	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private String alignment;

	private String description;

	private String favoredWeapon;

	private String source;

	private String race;

	public Deity() {

	}

	public String listFormat(){
		return StrManip.pad(name, 30)+StrManip.pad(alignment, 20)+StrManip.pad(source, 20);
	}
	
	public String listValue(){
		return "Deity : "+name;
	}
	
	public String toString(){
		return name+" ["+alignment+"]";
	}
	
	public void readImport(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("Name")) {
				setName(t.getTagBody());
			}
			if (t.getTagName().equals("Alignment")) {
				setAlignment(t.getTagBody());
			}
			if (t.getTagName().equals("Description")) {
				setDescription(t.getTagBody());
			}
			if (t.getTagName().equals("FavoredWeapon")) {
				setFavoredWeapon(t.getTagBody());
			}
			if (t.getTagName().equals("Source")) {
				setSource(t.getTagBody());
			}
			if (t.getTagName().equals("Race")) {
				setRace(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Deity>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<Name>" + name + "</Name>\n");
		sb.append("<Alignment>" + alignment + "</Alignment>\n");
		sb.append("<Description>" + description + "</Description>\n");
		sb.append("<FavoredWeapon>" + favoredWeapon + "</FavoredWeapon>\n");
		sb.append("<Source>" + source + "</Source>\n");
		sb.append("<Race>" + race + "</Race>\n");
		sb.append("</Deity>\n");
		return sb.toString();
	}

	public String existsAs(){
		DeityDAO db = new DeityDAO();
		Vector<Deity> v = db.selectDeity(this);
		if(v.size() > 0){
			return ((Deity)v.get(0)).getName();
		}
		return null;
	}
	
	public Vector<Deity> getValues(){
		DeityDAO db = new DeityDAO();
		return db.getDeities();
	}
	
	public void save(boolean overwrite){
		DeityDAO db = new DeityDAO();
		if (overwrite){
			db.addOrUpdateDeity(this);
		}else{
			db.addDeity(this);
		}
	}
	
	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (name == null) {
			name = "";
		}

		if (alignment == null) {
			alignment = "";
		}

		if (description == null) {
			description = "";
		}

		if (favoredWeapon == null) {
			favoredWeapon = "";
		}

		if (source == null) {
			source = "";
		}

		if (race == null) {
			race = "";
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

	/**
	 * Get the value of favoredWeapon
	 * 
	 * @return a <code>String</code> value
	 */
	public String getFavoredWeapon() {
		return favoredWeapon;
	}

	/**
	 * Set the value of favoredWeapon
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setFavoredWeapon(String s) {
		favoredWeapon = s;
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
	 * Get the value of race
	 * 
	 * @return a <code>String</code> value
	 */
	public String getRace() {
		return race;
	}

	/**
	 * Set the value of race
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setRace(String s) {
		race = s;
	}

	public String getFullDescription() {

		return name + "\n\n" + description;
	}

}

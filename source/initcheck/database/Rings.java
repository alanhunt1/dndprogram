package initcheck.database;

import initcheck.character.library.LibraryItem;
import initcheck.utils.StrManip;

import java.io.Serializable;
import java.util.Vector;

public class Rings implements Serializable, Cloneable, LibraryItem, Exportable, Importable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String level;

	private String name;

	private String description;

	private String prereqs;

	private String cost;

	private String casterLevel;

	private String source;

	public String listFormat(){
		return StrManip.pad(name, 30)+StrManip.pad(source, 20);
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String listValue(){
		return "Ring " + name;
	}
	
	public Rings() {

	}

	public String toString() {
		return name;
	}

	public String getFullDescription() {
		return getDescription();
	}

	public Rings(String s) {
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
			if (t.getTagName().equals("level")) {
				setLevel(t.getTagBody());
			}
			if (t.getTagName().equals("name")) {
				setName(t.getTagBody());
			}
			if (t.getTagName().equals("description")) {
				setDescription(t.getTagBody());
			}
			if (t.getTagName().equals("Prereqs")) {
				setPrereqs(t.getTagBody());
			}
			if (t.getTagName().equals("Cost")) {
				setCost(t.getTagBody());
			}
			if (t.getTagName().equals("CasterLevel")) {
				setCasterLevel(t.getTagBody());
			}
			if (t.getTagName().equals("Source")) {
				setSource(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Rings>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<level>" + level + "</level>\n");
		sb.append("<name>" + name + "</name>\n");
		sb.append("<description>" + description + "</description>\n");
		sb.append("<Prereqs>" + prereqs + "</Prereqs>\n");
		sb.append("<Cost>" + cost + "</Cost>\n");
		sb.append("<CasterLevel>" + casterLevel + "</CasterLevel>\n");
		sb.append("<Source>" + source + "</Source>");
		sb.append("</Rings>\n");
		return sb.toString();
	}

	public String existsAs(){
		RingsDAO db = new RingsDAO();
		Vector<Rings> v = db.selectRings(this);
		if(v.size() > 0){
			return ((Rings)v.get(0)).getName();
		}
		return null;
	}
	
	public Vector<Rings> getValues(){
		RingsDAO db = new RingsDAO();
		return db.getRings();
	}
	
	public void save(boolean overwrite){
		RingsDAO db = new RingsDAO();
		if (overwrite){
			db.addOrUpdateRings(this);
		}else{
			db.addRings(this);
		}
	}
	
	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (level == null) {
			level = "";
		}

		if (name == null) {
			name = "";
		}

		if (description == null) {
			description = "";
		}

		if (prereqs == null) {
			prereqs = "";
		}

		if (cost == null) {
			cost = "";
		}

		if (casterLevel == null) {
			casterLevel = "";
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
	 * Get the value of prereqs
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPrereqs() {
		return prereqs;
	}

	/**
	 * Set the value of prereqs
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPrereqs(String s) {
		prereqs = s;
	}

	/**
	 * Get the value of cost
	 * 
	 * @return a <code>String</code> value
	 */
	public String getCost() {
		return cost;
	}

	/**
	 * Set the value of cost
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setCost(String s) {
		cost = s;
	}

	/**
	 * Get the value of casterLevel
	 * 
	 * @return a <code>String</code> value
	 */
	public String getCasterLevel() {
		return casterLevel;
	}

	/**
	 * Set the value of casterLevel
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setCasterLevel(String s) {
		casterLevel = s;
	}

}

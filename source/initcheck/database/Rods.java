package initcheck.database;

import initcheck.character.library.LibraryItem;
import initcheck.utils.StrManip;

import java.io.Serializable;
import java.util.Vector;

public class Rods implements Serializable, Cloneable, Exportable, LibraryItem, Importable, Item, TreasureItem {

	private static final long serialVersionUID = 1L;

	private String id;

	private String mlevel;

	private String name;

	private String description;

	private String casterLevel;

	private String prereqs;

	private String cost;

	private String source;
	
	boolean hasCharges;
	int charges;
	boolean intelligent;
	
	public String listFormat(){
		return StrManip.pad(name, 30)+StrManip.pad(source, 20);
	}
	
	public int getCharges() {
		return charges;
	}

	public void setCharges(int charges) {
		this.charges = charges;
	}

	public boolean isHasCharges() {
		return hasCharges;
	}

	public void setHasCharges(boolean hasCharges) {
		this.hasCharges = hasCharges;
	}

	public boolean isIntelligent() {
		return intelligent;
	}

	public void setIntelligent(boolean intelligent) {
		this.intelligent = intelligent;
	}

	public Rods() {

	}

	public String listValue(){
		return "Rod : "+name;
	}
	
	public String toString() {
		return name;
	}

	public String getFullDescription() {
		return description;
	}

	public Rods(String s) {
		readImport(s);
	}
	
	public void readImport(String s ){
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("Mlevel")) {
				setMlevel(t.getTagBody());
			}
			if (t.getTagName().equals("Name")) {
				setName(t.getTagBody());
			}
			if (t.getTagName().equals("Description")) {
				setDescription(t.getTagBody());
			}
			if (t.getTagName().equals("CasterLevel")) {
				setCasterLevel(t.getTagBody());
			}
			if (t.getTagName().equals("Prereqs")) {
				setPrereqs(t.getTagBody());
			}
			if (t.getTagName().equals("Cost")) {
				setCost(t.getTagBody());
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
		sb.append("<Rods>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<Mlevel>" + mlevel + "</Mlevel>\n");
		sb.append("<Name>" + name + "</Name>\n");
		sb.append("<Description>" + description + "</Description>\n");
		sb.append("<CasterLevel>" + casterLevel + "</CasterLevel>\n");
		sb.append("<Prereqs>" + prereqs + "</Prereqs>\n");
		sb.append("<Cost>" + cost + "</Cost>\n");
		sb.append("<Source>" + source + "</Source>");
		sb.append("</Rods>\n");
		return sb.toString();
	}

	public String existsAs(){
		RodsDAO db = new RodsDAO();
		Vector<Rods> v = db.selectRods(this);
		if(v.size() > 0){
			return ((Rods)v.get(0)).getName();
		}
		return null;
	}
	
	public Vector<Rods> getValues(){
		RodsDAO db = new RodsDAO();
		return db.getRods();
	}
	
	public void save(boolean overwrite){
		RodsDAO db = new RodsDAO();
		if (overwrite){
			db.addOrUpdateRods(this);
		}else{
			db.addRods(this);
		}
	}
	
	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (mlevel == null) {
			mlevel = "";
		}

		if (name == null) {
			name = "";
		}

		if (description == null) {
			description = "";
		}

		if (casterLevel == null) {
			casterLevel = "";
		}

		if (prereqs == null) {
			prereqs = "";
		}

		if (cost == null) {
			cost = "";
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
	 * Get the value of mlevel
	 * 
	 * @return a <code>String</code> value
	 */
	public String getMlevel() {
		return mlevel;
	}

	/**
	 * Set the value of mlevel
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setMlevel(String s) {
		mlevel = s;
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

	public String getDisplayType() {
		// TODO Auto-generated method stub
		return "Rod";
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}

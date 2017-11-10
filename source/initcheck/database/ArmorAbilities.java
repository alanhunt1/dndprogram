package initcheck.database;

import initcheck.character.library.LibraryItem;
import initcheck.utils.StrManip;

import java.io.Serializable;
import java.util.Vector;


public class ArmorAbilities extends Ability implements Serializable, Cloneable,
		Exportable, LibraryItem, Importable {

	private static final long serialVersionUID = -3835292986922148937L;

	private String id;

	private String abilityName;

	private String description;

	private String armorType;

	private String bonus;

	String casterLevel;

	String prereq;

	String cost;

	private String source;
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String listValue(){
		return "Armor Ability : "+abilityName;
	}
	
	/**
	 * Get the Cost value.
	 * 
	 * @return the Cost value.
	 */
	public String getCost() {
		return cost;
	}

	/**
	 * Set the Cost value.
	 * 
	 * @param newCost
	 *            The new Cost value.
	 */
	public void setCost(String newCost) {
		this.cost = newCost;
	}

	/**
	 * Get the Prereq value.
	 * 
	 * @return the Prereq value.
	 */
	public String getPrereq() {
		return prereq;
	}

	/**
	 * Set the Prereq value.
	 * 
	 * @param newPrereq
	 *            The new Prereq value.
	 */
	public void setPrereq(String newPrereq) {
		this.prereq = newPrereq;
	}

	/**
	 * Get the CasterLevel value.
	 * 
	 * @return the CasterLevel value.
	 */
	public String getCasterLevel() {
		return casterLevel;
	}

	/**
	 * Set the CasterLevel value.
	 * 
	 * @param newCasterLevel
	 *            The new CasterLevel value.
	 */
	public void setCasterLevel(String newCasterLevel) {
		this.casterLevel = newCasterLevel;
	}

	public String listFormat() {
		return StrManip.pad(abilityName, 25) + StrManip.pad(" (" + armorType + ")", 10) + 
		StrManip.pad(" + "+bonus, 5)+ StrManip.pad(source, 30);
	}
	
	public String getFullDescription() {
		return description;
	}

	public ArmorAbilities() {

	}

	public String toString() {
		return abilityName;
	}

	public ArmorAbilities(String s){
		readImport(s);
	}
	
	public void readImport(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("AbilityName")) {
				setAbilityName(t.getTagBody());
			}
			if (t.getTagName().equals("Description")) {
				setDescription(t.getTagBody());
			}
			if (t.getTagName().equals("ArmorType")) {
				setArmorType(t.getTagBody());
			}
			if (t.getTagName().equals("Bonus")) {
				setBonus(t.getTagBody());
			}
			if (t.getTagName().equals("Source")) {
				setSource(t.getTagBody());
			}
			if (t.getTagName().equals("CasterLevel")) {
				setCasterLevel(t.getTagBody());
			}
			if (t.getTagName().equals("Prereq")) {
				setPrereq(t.getTagBody());
			}
			if (t.getTagName().equals("Cost")) {
				setCost(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<ArmorAbilities>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<AbilityName>" + abilityName + "</AbilityName>\n");
		sb.append("<Description>" + description + "</Description>\n");
		sb.append("<ArmorType>" + armorType + "</ArmorType>\n");
		sb.append("<Bonus>" + bonus + "</Bonus>\n");
		sb.append("<Source>" + source + "</Source>\n");	
		sb.append("<CasterLevel>" + casterLevel + "</CasterLevel>\n");
		sb.append("<Prereq>" + prereq + "</Prereq>\n");
		sb.append("<Cost>" + cost + "</Cost>\n");	
		sb.append("</ArmorAbilities>\n");
		return sb.toString();
	}

	public void save(boolean overwrite){
		ArmorAbilitiesDAO db = new ArmorAbilitiesDAO();
		if (overwrite){
			db.addOrUpdateArmorAbilities(this);
		}else{
			db.addArmorAbilities(this);
		}
		
	}
	
	public Vector<ArmorAbilities> getValues(){
		ArmorAbilitiesDAO db = new ArmorAbilitiesDAO();
		return db.getArmorAbilities();
	}
	
	public String existsAs(){
		ArmorAbilitiesDAO db = new ArmorAbilitiesDAO();
		Vector<ArmorAbilities> v = db.selectArmorAbilities(this);
		if(v.size() > 0){
			return ((ArmorAbilities)v.get(0)).getAbilityName();
		}
		return null;
	}
	
	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (abilityName == null) {
			abilityName = "";
		}

		if (description == null) {
			description = "";
		}

		if (armorType == null) {
			armorType = "";
		}

		if (bonus == null) {
			bonus = "";
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
		if (StrManip.isNullOrEmpty(abilityName)){
			errors.add("Ability name is a required field");
		}
		if (StrManip.isNullOrEmpty(description)){
			errors.add("Description is a required field");
		}
	}

	private void checkNumbers(Vector<String> errors) {
		if (bonus != null && !bonus.equals("")) {
			try {
				Integer.parseInt(bonus);
			} catch (Exception e) {
				errors.add("Enhancement Cost is not a valid number.  Please enter an integer value.");
			}
		}else{
			errors.add("Enhancement Cost is a required field.");
		}
		
		if (cost != null && !cost.equals("")) {
			try {
				Integer.parseInt(cost);
			} catch (Exception e) {
				errors.add("Cost is not a valid number.  Please enter an integer value.");
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
	 * Get the value of abilityName
	 * 
	 * @return a <code>String</code> value
	 */
	public String getAbilityName() {
		return abilityName;
	}

	/**
	 * Set the value of abilityName
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setAbilityName(String s) {
		abilityName = s;
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
	 * Get the value of armorType
	 * 
	 * @return a <code>String</code> value
	 */
	public String getArmorType() {
		return armorType;
	}

	/**
	 * Set the value of armorType
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setArmorType(String s) {
		armorType = s;
	}

	/**
	 * Get the value of bonus
	 * 
	 * @return a <code>String</code> value
	 */
	public String getBonus() {
		return bonus;
	}

	/**
	 * Set the value of bonus
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setBonus(String s) {
		bonus = s;
	}

}

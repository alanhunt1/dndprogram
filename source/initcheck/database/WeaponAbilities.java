package initcheck.database;

import initcheck.character.library.LibraryItem;
import initcheck.utils.StrManip;

import java.io.Serializable;
import java.util.Vector;

public class WeaponAbilities extends Ability implements LibraryItem,
		Serializable, Cloneable, Exportable, Importable {

	private static final long serialVersionUID = 1L;

	private String abilityName;

	private String description;

	private String weaponType;

	private String id;

	String bonus;

	String casterLevel;

	String prereq;

	String cost;

	String damage = "";

	private String source;
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String listValue(){
		return "Weapon Ability : "+abilityName;
	}
	
	public void setName(String s){
		abilityName = s;
	}
	
	
	/**
	 * Get the Damage value.
	 * 
	 * @return the Damage value.
	 */
	public String getDamage() {
		return damage;
	}

	/**
	 * Set the Damage value.
	 * 
	 * @param newDamage
	 *            The new Damage value.
	 */
	public void setDamage(String newDamage) {
		this.damage = newDamage;
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

	/**
	 * Get the Bonus value.
	 * 
	 * @return the Bonus value.
	 */
	public String getBonus() {
		return bonus;
	}

	/**
	 * Set the Bonus value.
	 * 
	 * @param newBonus
	 *            The new Bonus value.
	 */
	public void setBonus(String newBonus) {
		this.bonus = newBonus;
	}

	public String listFormat() {
		return StrManip.pad(abilityName, 17) + StrManip.pad(" (" + weaponType + ")", 10) + 
		StrManip.pad(" + "+bonus, 5)+ StrManip.pad(source, 30);
	}

	public String getFullDescription() {
		return description;
	}

	public String getName() {
		return abilityName;
	}

	public WeaponAbilities() {

	}
	
	public void readImport(String s){
		Tag t = Tag.getTag(s); 
	     while (t != null) { 
	       String tagName = t.getTagName();
	       String tagBody = t.getTagBody();
	      if (t.getTagName().equals("AbilityName")){
	         setAbilityName(t.getTagBody());
	      }
	      if (t.getTagName().equals("Description")){
	         setDescription(t.getTagBody());
	      }
	      if (t.getTagName().equals("WeaponType")){
	         setWeaponType(t.getTagBody());
	      }
	      if (t.getTagName().equals("Id")){
	         setId(t.getTagBody());
	      }
	      if (t.getTagName().equals("Bonus")){
	         setBonus(t.getTagBody());
	      }
	      if (t.getTagName().equals("CasterLevel")){
	         setCasterLevel(t.getTagBody());
	      }
	      if (t.getTagName().equals("Prereq")){
	         setPrereq(t.getTagBody());
	      }
	      if (t.getTagName().equals("Cost")){
	         setCost(t.getTagBody());
	      }
	      if (t.getTagName().equals("Damage")){
	         setDamage(t.getTagBody());
	      }
	      if (t.getTagName().equals("Source")) {
				setSource(t.getTagBody());
			}
	     	s = s.substring((2*tagName.length())+5+tagBody.length(), s.length()); 
	      t = Tag.getTag(s); 
	      }

	}
	
	public String exportFormat(){ 
	      StringBuffer sb = new StringBuffer();
	      sb.append("<WeaponAbilities>\n");
	      sb.append("<AbilityName>"+abilityName+"</AbilityName>\n");
	      sb.append("<Description>"+description+"</Description>\n");
	      sb.append("<WeaponType>"+weaponType+"</WeaponType>\n");
	      sb.append("<Id>"+id+"</Id>\n");
	      sb.append("<Bonus>"+bonus+"</Bonus>\n");
	      sb.append("<CasterLevel>"+casterLevel+"</CasterLevel>\n");
	      sb.append("<Prereq>"+prereq+"</Prereq>\n");
	      sb.append("<Cost>"+cost+"</Cost>\n");
	      sb.append("<Damage>"+damage+"</Damage>\n");
	      sb.append("<Source>" + source + "</Source>\n");
	      sb.append("</WeaponAbilities>\n");
	      return sb.toString();
	   }

	public String existsAs(){
		WeaponAbilitiesDAO db = new WeaponAbilitiesDAO();
		Vector<WeaponAbilities> v = db.selectWeaponAbilities(this);
		if(v.size() > 0){
			return ((WeaponAbilities)v.get(0)).getName();
		}
		return null;
	}
	
	public Vector<WeaponAbilities> getValues(){
		WeaponAbilitiesDAO db = new WeaponAbilitiesDAO();
		return db.getWeaponAbilities();
	}
	
	public void save(boolean overwrite) {
		if (!overwrite) {
			setId(null);
		}
		WeaponAbilitiesDAO ddb = new WeaponAbilitiesDAO();
		id = ""+ddb.addOrUpdateWeaponAbilities(this);
		
	}

	public String toString() {
		return abilityName;
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
	 * Get the value of weaponType
	 * 
	 * @return a <code>String</code> value
	 */
	public String getWeaponType() {
		return weaponType;
	}

	/**
	 * Set the value of weaponType
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setWeaponType(String s) {
		weaponType = s;
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

}

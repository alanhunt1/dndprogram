package initcheck.database;

import initcheck.character.library.LibraryItem;
import initcheck.utils.StrManip;

import java.io.Serializable;
import java.util.Vector;

public class Wonderous implements Item, TreasureListItem, Serializable,
		Cloneable, LibraryItem, Exportable, Importable, TreasureItem {

	private static final long serialVersionUID = 1L;

	String name;

	String description;

	boolean hasCharges;

	int charges;

	boolean intelligent;

	String displayType = "wonderous";

	String casterLevel;

	String price;

	String spells;

	String id;

	String level;

	String weight;

	String source;
	
	public String listFormat(){
		return StrManip.pad(name, 40)+StrManip.pad(source, 25);
	}
	
	public String listValue(){
		return "Wonderous Item : "+name;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Wonderous() {

	}

	public Wonderous(String s) {
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
			if (t.getTagName().equals("price")) {
				setPrice(t.getTagBody());
			}
			if (t.getTagName().equals("casterLevel")) {
				setCasterLevel(t.getTagBody());
			}
			if (t.getTagName().equals("name")) {
				setName(t.getTagBody());
			}
			if (t.getTagName().equals("level")) {
				setLevel(t.getTagBody());
			}
			if (t.getTagName().equals("description")) {
				setDescription(t.getTagBody());
			}
			if (t.getTagName().equals("spells")) {
				setSpells(t.getTagBody());
			}
			if (t.getTagName().equals("weight")) {
				setWeight(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Wonderous>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<price>" + price + "</price>\n");
		sb.append("<casterLevel>" + casterLevel + "</casterLevel>\n");
		sb.append("<name>" + name + "</name>\n");
		sb.append("<level>" + level + "</level>\n");
		sb.append("<description>" + description + "</description>\n");
		sb.append("<spells>" + spells + "</spells>\n");
		sb.append("<weight>" + weight + "</weight>\n");
		sb.append("</Wonderous>\n");
		return sb.toString();
	}
	
	public String existsAs(){
		WonderousItemsDAO db = new WonderousItemsDAO();
		Vector<Wonderous> v = db.selectWonderousItems(this);
		if(v.size() > 0){
			return ((Wonderous)v.get(0)).getName();
		}
		return null;
	}
	
	public Vector<Wonderous> getValues(){
		WonderousItemsDAO db = new WonderousItemsDAO();
		return db.getWonderousItems();
	}
	
	public void save(boolean overwrite){
		WonderousItemsDAO db = new WonderousItemsDAO();
		if (overwrite){
			db.addOrUpdateWonderousItem(this);
		}else{
			db.addWonderousItems(this);
		}
	}

	/**
	 * Get the Weight value.
	 * 
	 * @return the Weight value.
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * Set the Weight value.
	 * 
	 * @param newWeight
	 *            The new Weight value.
	 */
	public void setWeight(String newWeight) {
		this.weight = newWeight;
	}

	/**
	 * Get the Level value.
	 * 
	 * @return the Level value.
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * Set the Level value.
	 * 
	 * @param newLevel
	 *            The new Level value.
	 */
	public void setLevel(String newLevel) {
		this.level = newLevel;
	}

	/**
	 * Get the Id value.
	 * 
	 * @return the Id value.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the Id value.
	 * 
	 * @param newId
	 *            The new Id value.
	 */
	public void setId(String newId) {
		this.id = newId;
	}

	/**
	 * Get the Spells value.
	 * 
	 * @return the Spells value.
	 */
	public String getSpells() {
		return spells;
	}

	/**
	 * Set the Spells value.
	 * 
	 * @param newSpells
	 *            The new Spells value.
	 */
	public void setSpells(String newSpells) {
		this.spells = newSpells;
	}

	/**
	 * Get the Price value.
	 * 
	 * @return the Price value.
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * Set the Price value.
	 * 
	 * @param newPrice
	 *            The new Price value.
	 */
	public void setPrice(String newPrice) {
		this.price = newPrice;
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
	 * Get the DisplayType value.
	 * 
	 * @return the DisplayType value.
	 */
	public String getDisplayType() {
		return displayType;
	}

	/**
	 * Set the DisplayType value.
	 * 
	 * @param newDisplayType
	 *            The new DisplayType value.
	 */
	public void setDisplayType(String newDisplayType) {
		this.displayType = newDisplayType;
	}

	/**
	 * Get the Intelligent value.
	 * 
	 * @return the Intelligent value.
	 */
	public boolean isIntelligent() {
		return intelligent;
	}

	/**
	 * Set the Intelligent value.
	 * 
	 * @param newIntelligent
	 *            The new Intelligent value.
	 */
	public void setIntelligent(boolean newIntelligent) {
		this.intelligent = newIntelligent;
	}

	public String toString() {
		return name;
	}

	/**
	 * Get the Charges value.
	 * 
	 * @return the Charges value.
	 */
	public int getCharges() {
		return charges;
	}

	/**
	 * Set the Charges value.
	 * 
	 * @param newCharges
	 *            The new Charges value.
	 */
	public void setCharges(int newCharges) {
		this.charges = newCharges;
	}

	/**
	 * Get the HasCharges value.
	 * 
	 * @return the HasCharges value.
	 */
	public boolean isHasCharges() {
		return hasCharges;
	}

	/**
	 * Set the HasCharges value.
	 * 
	 * @param newHasCharges
	 *            The new HasCharges value.
	 */
	public void setHasCharges(boolean newHasCharges) {
		this.hasCharges = newHasCharges;
	}

	public String getFullDescription() {
		return getDescription();
	}

	/**
	 * Get the Description value.
	 * 
	 * @return the Description value.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the Description value.
	 * 
	 * @param newDescription
	 *            The new Description value.
	 */
	public void setDescription(String newDescription) {
		this.description = newDescription;
	}

	/**
	 * Get the Name value.
	 * 
	 * @return the Name value.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the Name value.
	 * 
	 * @param newName
	 *            The new Name value.
	 */
	public void setName(String newName) {
		this.name = newName;
	}

}

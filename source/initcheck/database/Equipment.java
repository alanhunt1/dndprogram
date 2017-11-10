package initcheck.database;

import initcheck.character.InventoryItem;
import initcheck.character.library.LibraryItem;
import initcheck.utils.StrManip;

import java.io.Serializable;
import java.util.Vector;

public class Equipment implements Serializable, Cloneable, Exportable,
		LibraryItem, Importable, InventoryItem {

	private static final long serialVersionUID = 1L;

	private String id;

	private String playerId;

	private String itemName;

	private String weight;

	private String quantity;

	private String description;

	private String cost;

	private String notes;

	private boolean dropped;

	private boolean linked;

	private boolean tempMod;

	private String modType;

	private String type;
	
	private String source;
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String listValue(){
		return "Equipment : "+itemName;
	}
	
	public String getName(){
		return itemName;
	}
	
	public void setName(String s){
		itemName = s;
	}
	
 
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFullDescription() {
		return "Name : " + itemName + "\n\nWeight : " + weight + "\n\nCost : "
				+ cost + "\n\n" + notes + "\n\n" + description;
	}

	public String listFormat(){
		return StrManip.pad(quantity, 3)+" "+StrManip.pad(itemName,25)+" "+cost;
	}
	
	public String inventoryListFormat(){
		return StrManip.pad(quantity, 3)+" "+StrManip.pad(itemName,25)+" "+cost;
	}
	
	public Equipment(String s) {
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
			if (t.getTagName().equals("ItemName")) {
				setItemName(t.getTagBody());
			}
			if (t.getTagName().equals("Weight")) {
				setWeight(t.getTagBody());
			}
			if (t.getTagName().equals("Cost")) {
				setCost(t.getTagBody());
			}
			if (t.getTagName().equals("Notes")) {
				setNotes(t.getTagBody());
			}
			if (t.getTagName().equals("TempMod")) {
				setTempMod(t.getTagBody().equals("true"));
			}
			if (t.getTagName().equals("ModType")) {
				setModType(t.getTagBody());
			}
			if (t.getTagName().equals("Description")) {
				setDescription(t.getTagBody());
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
		sb.append("<Equipment>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<ItemName>" + itemName + "</ItemName>\n");
		sb.append("<Weight>" + weight + "</Weight>\n");
		sb.append("<Cost>" + cost + "</Cost>\n");
		sb.append("<Notes>" + notes + "</Notes>\n");
		sb.append("<TempMod>" + tempMod + "</TempMod>\n");
		sb.append("<ModType>" + modType + "</ModType>\n");
		sb.append("<Description>" + description + "</Description>\n");
		sb.append("<Source>" + source + "</Source>\n");
		sb.append("</Equipment>\n");

		return sb.toString();
	}

	public String existsAs(){
		EquipmentDAO db = new EquipmentDAO();
		Vector<Equipment> v = db.selectEquipment(this);
		if(v.size() > 0){
			return ((Equipment)v.get(0)).getName();
		}
		return null;
	}
	
	public Vector<Equipment> getValues(){
		EquipmentDAO db = new EquipmentDAO();
		return db.getEquipment();
	}
	
	public void save(boolean overwrite){
		EquipmentDAO db = new EquipmentDAO();
		if (overwrite){
			db.addOrUpdateEquipment(this);
		}else{
			db.addEquipment(this);
		}
	}
	
	/**
	 * Get the ModType value.
	 * 
	 * @return the ModType value.
	 */
	public String getModType() {
		return modType;
	}

	/**
	 * Set the ModType value.
	 * 
	 * @param newModType
	 *            The new ModType value.
	 */
	public void setModType(String newModType) {
		this.modType = newModType;
	}

	/**
	 * Get the TempMod value.
	 * 
	 * @return the TempMod value.
	 */
	public boolean isTempMod() {
		return tempMod;
	}

	/**
	 * Set the TempMod value.
	 * 
	 * @param newTempMod
	 *            The new TempMod value.
	 */
	public void setTempMod(boolean newTempMod) {
		this.tempMod = newTempMod;
	}

	/**
	 * Get the Linked value.
	 * 
	 * @return the Linked value.
	 */
	public boolean isLinked() {
		return linked;
	}

	/**
	 * Set the Linked value.
	 * 
	 * @param newLinked
	 *            The new Linked value.
	 */
	public void setLinked(boolean newLinked) {
		this.linked = newLinked;
	}

	/**
	 * Get the Dropped value.
	 * 
	 * @return the Dropped value.
	 */
	public boolean isDropped() {
		return dropped;
	}

	/**
	 * Set the Dropped value.
	 * 
	 * @param newDropped
	 *            The new Dropped value.
	 */
	public void setDropped(boolean newDropped) {
		this.dropped = newDropped;
	}

	/**
	 * Get the Notes value.
	 * 
	 * @return the Notes value.
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Set the Notes value.
	 * 
	 * @param newNotes
	 *            The new Notes value.
	 */
	public void setNotes(String newNotes) {
		this.notes = newNotes;
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

	public Equipment() {

	}

	public String toString() {
		return itemName;
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
	 * Get the value of playerId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPlayerId() {
		return playerId;
	}

	/**
	 * Set the value of playerId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPlayerId(String s) {
		playerId = s;
	}

	/**
	 * Get the value of itemName
	 * 
	 * @return a <code>String</code> value
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * Set the value of itemName
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setItemName(String s) {
		itemName = s;
	}

	/**
	 * Get the value of weight
	 * 
	 * @return a <code>String</code> value
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * Set the value of weight
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setWeight(String s) {
		weight = s;
	}

	/**
	 * Get the value of quantity
	 * 
	 * @return a <code>String</code> value
	 */
	public String getQuantity() {
		return quantity;
	}

	/**
	 * Set the value of quantity
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setQuantity(String s) {
		quantity = s;
	}

}

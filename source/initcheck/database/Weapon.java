package initcheck.database;

import java.io.Serializable;
import java.util.Vector;

import initcheck.character.InventoryItem;
import initcheck.character.library.LibraryItem;
import initcheck.graphics.TiledListItem;
import initcheck.utils.StrManip;

public class Weapon implements Serializable, Cloneable, Exportable, 
LibraryItem, Importable, InventoryItem, TiledListItem, TreasureItem {

	
	private static final long serialVersionUID = 1L;

	// generic info
	private String id;

	private String name;

	private String cost;

	private String damage;

	private String crit;

	private String range;

	private String weight;

	private String type;

	private String size;

	private String category;

	private String rangedmelee;

	private String source;

	private String notes1;

	private String notes2;

	private String exclude;

	private String featClass;

	// instance info
	private String bonus = "";

	private String description = "";

	private WeaponAbilities ability1 = null;

	private WeaponAbilities ability2 = null;

	private WeaponAbilities ability3 = null;

	private WeaponAbilities ability4 = null;

	private WeaponAbilities ability5 = null;

	private WeaponAbilities ability6 = null;
	
	private boolean intelligent = false;

	private String displayType = "weapon";

	private boolean masterwork = false;

	private String instanceId;

	private String use = "Normal";

	String notes = "";

	String quantity = "1";

	int order;

	boolean critOverride = false;

	String critStart;

	String critEnd;

	String critMult;

	String sizeOverride;

	String damageOverride;

	String playerId;

	boolean useMonk = false;

	boolean displaySheet = true;

	boolean displayItem = true;

	private boolean named = false;
	
	private String trueName;
	
	Vector<WeaponViews> featsApplied = new Vector<WeaponViews>();

	Materials material;

	boolean tempMod = false;

	private String modType;
	
	
	public String getModType() {
		return modType;
	}

	public void setModType(String modType) {
		this.modType = modType;
	}

	public boolean isTempMod() {
		return tempMod;
	}

	public void setTempMod(boolean tempMod) {
		this.tempMod = tempMod;
	}

	public String listValue(){
		return "Weapon : "+name;
	}
	
	public int getAbilityBonus(){
		int bonus = 0;
		if (ability1 != null){
			bonus += Integer.parseInt(ability1.getBonus());
		}
		if (ability2 != null){
			bonus += Integer.parseInt(ability2.getBonus());
		}
		if (ability3 != null){
			bonus += Integer.parseInt(ability3.getBonus());
		}
		if (ability4 != null){
			bonus += Integer.parseInt(ability4.getBonus());
		}
		if (ability5 != null){
			bonus += Integer.parseInt(ability5.getBonus());
		}
		if (ability6 != null){
			bonus += Integer.parseInt(ability6.getBonus());
		}
		
		return bonus;
	}
	
	public boolean equals(Weapon w) {
		return this.id.equals(w.getId())
				&& this.bonus.equals(w.getBonus())
				&& this.intelligent == (w.isIntelligent())
				&& this.useMonk == (w.useMonk())
				&& this.displaySheet == (w.isDisplaySheet())
				&& this.displayItem == (w.isDisplayItem())
				&& this.use.equals(w.getUse())
				&& ((this.ability1 == null && w.getAbility1().isNull()) || (this.ability1
						.getName().equals(w.getAbility1().getName())))
				&& ((this.ability2 == null && w.getAbility2().isNull()) || (this.ability2
						.getName().equals(w.getAbility2().getName())))
				&& ((this.ability3 == null && w.getAbility3().isNull()) || (this.ability3
						.getName().equals(w.getAbility3().getName())))
				&& ((this.ability4 == null && w.getAbility4().isNull()) || (this.ability4
						.getName().equals(w.getAbility4().getName())))
				&& ((this.ability5 == null && w.getAbility5().isNull()) || (this.ability5
						.getName().equals(w.getAbility5().getName())))
				&& ((this.ability6 == null && w.getAbility6().isNull()) || (this.ability6
						.getName().equals(w.getAbility6().getName())))		
				&& this.masterwork == w.isMasterwork()
				&& ((this.notes == null && w.getNotes() == null) || this.notes
						.equals(w.getNotes()))
				&& ((this.material == null && w.getMaterial() == null) || (this.material
						.getMaterialName().equals(w.getMaterial()
						.getMaterialName())));

	}

	public boolean isSpeedWeapon() {
		return ((ability1 != null && ability1.getName().equals("Speed"))
				|| (ability3 != null && ability3.getName().equals("Speed")) 
				|| (ability2 != null && ability2.getName().equals("Speed")) 
				|| (ability4 != null && ability4.getName().equals("Speed")) 
				|| (ability5 != null && ability5.getName().equals("Speed"))
				|| (ability6 != null && ability6.getName().equals("Speed")));

	}
	
	public boolean isKeenWeapon() {
		return ((ability1 != null && ability1.getName().equals("Keen"))
				|| (ability3 != null && ability3.getName().equals("Keen")) 
				|| (ability2 != null && ability2.getName().equals("Keen")) 
				|| (ability4 != null && ability4.getName().equals("Keen")) 
				|| (ability5 != null && ability5.getName().equals("Keen"))
				|| (ability6 != null && ability6.getName().equals("Keen")));

	}

	public boolean isDoubleDamageWeapon() {
		return ((ability1 != null && ability1.getName().equals("Double Damage"))
				|| (ability3 != null && ability3.getName().equals("Double Damage")) 
				|| (ability2 != null && ability2.getName().equals("Double Damage")) 
				|| (ability4 != null && ability4.getName().equals("Double Damage")) 
				|| (ability5 != null && ability5.getName().equals("Double Damage"))
				|| (ability6 != null && ability6.getName().equals("Double Damage")));

	}
	
	
	
	/**
	 * Get the Material value.
	 * 
	 * @return the Material value.
	 */
	public Materials getMaterial() {
		return material;
	}

	public String getAbilityDamage() {

		String dmg = "";
		if (ability1 != null && !StrManip.isNullZeroOrEmpty(ability1.getDamage())
				&& !ability1.getDamage().equals("")) {
			dmg += ability1.getDamage() + ",";
		}
		if (ability2 != null && !StrManip.isNullZeroOrEmpty(ability2.getDamage())
				&& !ability2.getDamage().equals("")) {
			dmg += ability2.getDamage() + ",";
		}
		if (ability3 != null && !StrManip.isNullZeroOrEmpty(ability3.getDamage())
				&& !ability3.getDamage().equals("")) {
			dmg += ability3.getDamage() + ",";
		}
		if (ability4 != null && !StrManip.isNullZeroOrEmpty(ability4.getDamage())
				&& !ability4.getDamage().equals("")) {
			dmg += ability4.getDamage() + ",";
		}
		if (ability5 != null && !StrManip.isNullZeroOrEmpty(ability5.getDamage())
				&& !ability5.getDamage().equals("")) {
			dmg += ability5.getDamage() + ",";
		}
		if (ability6 != null && !StrManip.isNullZeroOrEmpty(ability6.getDamage())
				&& !ability6.getDamage().equals("")) {
			dmg += ability6.getDamage();
		}
		if (dmg.length() > 0 && dmg.charAt(dmg.length() - 1) == ',') {
			dmg = dmg.substring(0, dmg.length() - 1);
		}
		return dmg;
	}

	/**
	 * Set the Material value.
	 * 
	 * @param newMaterial
	 *            The new Material value.
	 */
	public void setMaterial(Materials newMaterial) {
		this.material = newMaterial;
	}

	public String getToolTip() {
		String tipStr = "";
		for (int i = 0; i < featsApplied.size(); i++) {
			tipStr += featsApplied.get(i);
		}
		return tipStr;
	}

	public boolean isApplied(String featId) {

		for (int i = 0; i < featsApplied.size(); i++) {
			WeaponViews wv = (WeaponViews) featsApplied.get(i);
			if (wv.getFeatId().equals(featId)) {
				return true;
			}
		}
		return false;
	}

	public boolean isApplied(String featId, String type) {

		for (int i = 0; i < featsApplied.size(); i++) {
			WeaponViews wv = (WeaponViews) featsApplied.get(i);
			if (wv.getFeatId().equals(featId) && wv.getType().equals(type)) {
				return true;
			}
		}
		return false;
	}

	public void applyFeat(WeaponViews wv) {
		featsApplied.add(wv);
	}

	public void removeFeat(WeaponViews wv) {
		for (int i = 0; i < featsApplied.size(); i++) {
			WeaponViews wv2 = (WeaponViews) featsApplied.get(i);
			if (wv.getId() == wv2.getId()) {
				featsApplied.removeElementAt(i);
				break;
			}
		}
	}

	/**
	 * Get the FeatsApplied value.
	 * 
	 * @return the FeatsApplied value.
	 */
	public Vector<WeaponViews> getFeatsApplied() {
		return featsApplied;
	}

	/**
	 * Set the FeatsApplied value.
	 * 
	 * @param newFeatsApplied
	 *            The new FeatsApplied value.
	 */
	public void setFeatsApplied(Vector<WeaponViews> newFeatsApplied) {
		this.featsApplied = newFeatsApplied;
	}

	/**
	 * Get the DisplayItem value.
	 * 
	 * @return the DisplayItem value.
	 */
	public boolean isDisplayItem() {
		return displayItem;
	}

	/**
	 * Set the DisplayItem value.
	 * 
	 * @param newDisplayItem
	 *            The new DisplayItem value.
	 */
	public void setDisplayItem(boolean newDisplayItem) {
		this.displayItem = newDisplayItem;
	}

	/**
	 * Get the DisplaySheet value.
	 * 
	 * @return the DisplaySheet value.
	 */
	public boolean isDisplaySheet() {
		return displaySheet;
	}

	/**
	 * Set the DisplaySheet value.
	 * 
	 * @param newDisplaySheet
	 *            The new DisplaySheet value.
	 */
	public void setDisplaySheet(boolean newDisplaySheet) {
		this.displaySheet = newDisplaySheet;
	}

	/**
	 * Get the UseMonk value.
	 * 
	 * @return the UseMonk value.
	 */
	public boolean useMonk() {
		return useMonk;
	}

	/**
	 * Set the UseMonk value.
	 * 
	 * @param newUseMonk
	 *            The new UseMonk value.
	 */
	public void setUseMonk(boolean newUseMonk) {
		this.useMonk = newUseMonk;
	}

	/**
	 * Get the PlayerId value.
	 * 
	 * @return the PlayerId value.
	 */
	public String getPlayerId() {
		return playerId;
	}

	/**
	 * Set the PlayerId value.
	 * 
	 * @param newPlayerId
	 *            The new PlayerId value.
	 */
	public void setPlayerId(String newPlayerId) {
		this.playerId = newPlayerId;
	}

	/**
	 * Get the DamageOverride value.
	 * 
	 * @return the DamageOverride value.
	 */
	public String getDamageOverride() {
		return damageOverride;
	}

	/**
	 * Set the DamageOverride value.
	 * 
	 * @param newDamageOverride
	 *            The new DamageOverride value.
	 */
	public void setDamageOverride(String newDamageOverride) {
		this.damageOverride = newDamageOverride;
	}

	/**
	 * Get the SizeOverride value.
	 * 
	 * @return the SizeOverride value.
	 */
	public String getSizeOverride() {
		return sizeOverride;
	}

	/**
	 * Set the SizeOverride value.
	 * 
	 * @param newSizeOverride
	 *            The new SizeOverride value.
	 */
	public void setSizeOverride(String newSizeOverride) {
		this.sizeOverride = newSizeOverride;
	}

	public Weapon(String s) {
		readImport(s);
	}
	
	public void readImport(String s){
		WeaponAbilitiesDAO wadb = new WeaponAbilitiesDAO();
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();

			// core vars (for db weapons)
			if (t.getTagName().equals("Id")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("Name")) {
				setName(t.getTagBody());
			}
			if (t.getTagName().equals("Cost")) {
				setCost(t.getTagBody());
			}
			if (t.getTagName().equals("Damage")) {
				setDamage(t.getTagBody());
			}
			if (t.getTagName().equals("Crit")) {
				setCrit(t.getTagBody());
			}
			if (t.getTagName().equals("Range")) {
				setRange(t.getTagBody());
			}
			if (t.getTagName().equals("Weight")) {
				setWeight(t.getTagBody());
			}
			if (t.getTagName().equals("Type")) {
				setType(t.getTagBody());
			}
			if (t.getTagName().equals("Size")) {
				setSize(t.getTagBody());
			}
			if (t.getTagName().equals("Category")) {
				setCategory(t.getTagBody());
			}
			if (t.getTagName().equals("Rangedmelee")) {
				setRangedmelee(t.getTagBody());
			}
			if (t.getTagName().equals("Source")) {
				setSource(t.getTagBody());
			}
			if (t.getTagName().equals("Notes1")) {
				setNotes1(t.getTagBody());
			}
			if (t.getTagName().equals("Notes2")) {
				setNotes2(t.getTagBody());
			}
			if (t.getTagName().equals("Exclude")) {
				setExclude(t.getTagBody());
			}
			if (t.getTagName().equals("FeatClass")) {
				setFeatClass(t.getTagBody());
			}

			// Instance vars (for player weapons)
			if (t.getTagName().equals("Bonus")) {
				setBonus(t.getTagBody());
			}
			if (t.getTagName().equals("Description")) {
				setDescription(t.getTagBody());
			}
			if (t.getTagName().equals("Ability1")) {
				setAbility1(wadb.getWeaponAbility(t.getTagBody()));
			}
			if (t.getTagName().equals("Ability2")) {
				setAbility2(wadb.getWeaponAbility(t.getTagBody()));
			}
			if (t.getTagName().equals("Ability3")) {
				setAbility3(wadb.getWeaponAbility(t.getTagBody()));
			}
			if (t.getTagName().equals("Ability4")) {
				setAbility4(wadb.getWeaponAbility(t.getTagBody()));
			}
			if (t.getTagName().equals("Ability5")) {
				setAbility5(wadb.getWeaponAbility(t.getTagBody()));
			}
			if (t.getTagName().equals("Ability6")) {
				setAbility6(wadb.getWeaponAbility(t.getTagBody()));
			}
			if (t.getTagName().equals("Intelligent")) {
				if (t.getTagBody().equalsIgnoreCase("true")) {
					setIntelligent(true);
				} else {
					setIntelligent(false);
				}
			}
			if (t.getTagName().equals("Use")) {
				setUse(t.getTagBody());
			}
			if (t.getTagName().equals("Category")) {
				setCategory(t.getTagBody());
			}
			if (t.getTagName().equals("Masterwork")) {
				if (t.getTagBody().equalsIgnoreCase("true")) {
					setMasterwork(true);
				} else {
					setMasterwork(false);
				}
			}
			if (t.getTagName().equals("Notes")) {
				setNotes(t.getTagBody());
			}
			if (t.getTagName().equals("Order")) {
				setOrder(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("Quantity")) {
				setQuantity(t.getTagBody());
			}
			if (t.getTagName().equals("CritOverride")) {
				if (t.getTagBody().equalsIgnoreCase("true")) {
					setCritOverride(true);
				} else {
					setCritOverride(false);
				}
			}
			if (t.getTagName().equals("CritStart")) {
				setCritStart(t.getTagBody());
			}
			if (t.getTagName().equals("CritEnd")) {
				setCritEnd(t.getTagBody());
			}
			if (t.getTagName().equals("CritMult")) {
				setCritMult(t.getTagBody());
			}
			if (t.getTagName().equals("SizeOverride")) {
				setSizeOverride(t.getTagBody());
			}
			if (t.getTagName().equals("DamageOverride")) {
				setDamageOverride(t.getTagBody());
			}
			if (t.getTagName().equals("UseMonk")) {
				setUseMonk(t.getTagBody().equals("true"));
			}
			if (t.getTagName().equals("DisplaySheet")) {
				setDisplaySheet(t.getTagBody().equals("true"));
			}
			if (t.getTagName().equals("DisplayItem")) {
				setDisplayItem(t.getTagBody().equals("true"));
			}
			if (t.getTagName().equals("WeaponViews")) {
				WeaponViews wv = new WeaponViews(t.getTagBody());
				featsApplied.add(wv);
			}
			if (t.getTagName().equals("Materials")) {
				Materials m = new Materials(t.getTagBody());
				setMaterial(m);
			}
			s = s.substring(tagName.length() + tagName.length() + 5
					+ tagBody.length(), s.length());
			t = Tag.getTag(s);
		}

	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Weapon>\n");

		// core items
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<Name>" + name + "</Name>\n");
		sb.append("<Cost>" + cost + "</Cost>\n");
		sb.append("<Damage>" + damage + "</Damage>\n");
		sb.append("<Crit>" + crit + "</Crit>\n");
		sb.append("<Range>" + range + "</Range>\n");
		sb.append("<Weight>" + weight + "</Weight>\n");
		sb.append("<Type>" + type + "</Type>\n");
		sb.append("<Size>" + size + "</Size>\n");
		sb.append("<Category>" + category + "</Category>\n");
		sb.append("<Rangedmelee>" + rangedmelee + "</Rangedmelee>\n");
		sb.append("<Source>" + source + "</Source>\n");
		sb.append("<Notes1>" + notes1 + "</Notes1>\n");
		sb.append("<Notes2>" + notes2 + "</Notes2>\n");
		sb.append("<Exclude>" + exclude + "</Exclude>\n");
		sb.append("<FeatClass>" + featClass + "</FeatClass>\n");

		// instance items
		sb.append("<Bonus>" + bonus + "</Bonus>\n");
		sb.append("<Description>" + description + "</Description>\n");
		if (ability1 != null) {
			sb.append("<Ability1>" + ability1.getId() + "</Ability1>\n");
		}
		if (ability2 != null) {
			sb.append("<Ability2>" + ability2.getId() + "</Ability2>\n");
		}
		if (ability3 != null) {
			sb.append("<Ability3>" + ability3.getId() + "</Ability3>\n");
		}
		if (ability4 != null) {
			sb.append("<Ability4>" + ability4.getId() + "</Ability4>\n");
		}
		if (ability5 != null) {
			sb.append("<Ability5>" + ability5.getId() + "</Ability5>\n");
		}
		if (ability6 != null) {
			sb.append("<Ability6>" + ability6.getId() + "</Ability6>\n");
		}
		sb.append("<Intelligent>" + intelligent + "</Intelligent>\n");
		sb.append("<Category>" + category + "</Category>\n");
		sb.append("<DisplayType>" + displayType + "</DisplayType>\n");
		sb.append("<Masterwork>" + masterwork + "</Masterwork>\n");
		sb.append("<InstanceId>" + instanceId + "</InstanceId>\n");
		sb.append("<Use>" + use + "</Use>\n");
		sb.append("<Notes>" + notes + "</Notes>\n");
		sb.append("<Quantity>" + quantity + "</Quantity>\n");
		sb.append("<Order>" + order + "</Order>\n");
		sb.append("<CritOverride>" + critOverride + "</CritOverride>\n");
		sb.append("<CritStart>" + critStart + "</CritStart>\n");
		sb.append("<CritEnd>" + critEnd + "</CritEnd>\n");
		sb.append("<CritMult>" + critMult + "</CritMult>\n");
		if (sizeOverride != null && !sizeOverride.equals("")) {
			sb.append("<SizeOverride>" + sizeOverride + "</SizeOverride>\n");
		}
		if (damageOverride != null && !damageOverride.equals("")) {
			sb.append("<DamageOverride>" + damageOverride
					+ "</DamageOverride>\n");
		}
		sb.append("<UseMonk>" + useMonk + "</UseMonk>\n");
		sb.append("<DisplaySheet>" + displaySheet + "</DisplaySheet>\n");
		sb.append("<DisplayItem>" + displayItem + "</DisplayItem>\n");
		sb.append(material.exportFormat());
		for (int i = 0; i < featsApplied.size(); i++) {
			WeaponViews wv = (WeaponViews) featsApplied.get(i);
			sb.append(wv.exportFormat());
		}
		sb.append("</Weapon>\n");
		return sb.toString();
	}

	public String existsAs(){
		WeaponDAO db = new WeaponDAO();
		Vector<Weapon> v = db.selectWeapon(this);
		if(v.size() > 0){
			return ((Weapon)v.get(0)).getName();
		}
		return null;
	}
	
	public Vector<Weapon> getValues(){
		WeaponDAO db = new WeaponDAO();
		return db.getWeapons();
	}
	
	public void save(boolean overwrite){
		WeaponDAO db = new WeaponDAO();
		if (overwrite){
			db.addOrUpdateWeapon(this);
		}else{
			db.addWeapon(this);
		}
	}
	
	/**
	 * Get the CritMult value.
	 * 
	 * @return the CritMult value.
	 */
	public String getCritMult() {
		return critMult;
	}

	/**
	 * Set the CritMult value.
	 * 
	 * @param newCritMult
	 *            The new CritMult value.
	 */
	public void setCritMult(String newCritMult) {
		this.critMult = newCritMult;
	}

	/**
	 * Get the CritEnd value.
	 * 
	 * @return the CritEnd value.
	 */
	public String getCritEnd() {
		return critEnd;
	}

	/**
	 * Set the CritEnd value.
	 * 
	 * @param newCritEnd
	 *            The new CritEnd value.
	 */
	public void setCritEnd(String newCritEnd) {
		this.critEnd = newCritEnd;
	}

	/**
	 * Get the CritStart value.
	 * 
	 * @return the CritStart value.
	 */
	public String getCritStart() {
		return critStart;
	}

	/**
	 * Set the CritStart value.
	 * 
	 * @param newCritStart
	 *            The new CritStart value.
	 */
	public void setCritStart(String newCritStart) {
		this.critStart = newCritStart;
	}

	/**
	 * Get the CritOverride value.
	 * 
	 * @return the CritOverride value.
	 */
	public boolean isCritOverride() {
		return critOverride;
	}

	/**
	 * Set the CritOverride value.
	 * 
	 * @param newCritOverride
	 *            The new CritOverride value.
	 */
	public void setCritOverride(boolean newCritOverride) {
		this.critOverride = newCritOverride;
	}

	/**
	 * Get the Order value.
	 * 
	 * @return the Order value.
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * Set the Order value.
	 * 
	 * @param newOrder
	 *            The new Order value.
	 */
	public void setOrder(int newOrder) {
		this.order = newOrder;
	}

	/**
	 * Get the Quantity value.
	 * 
	 * @return the Quantity value.
	 */
	public String getQuantity() {
		return quantity;
	}

	/**
	 * Set the Quantity value.
	 * 
	 * @param newQuantity
	 *            The new Quantity value.
	 */
	public void setQuantity(String newQuantity) {
		this.quantity = newQuantity;
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
	 * Get the InstanceId value.
	 * 
	 * @return the InstanceId value.
	 */
	public String getInstanceId() {
		return instanceId;
	}

	/**
	 * Set the InstanceId value.
	 * 
	 * @param newInstanceId
	 *            The new InstanceId value.
	 */
	public void setInstanceId(String newInstanceId) {
		this.instanceId = newInstanceId;
	}

	/**
	 * Get the Use value.
	 * 
	 * @return the Use value.
	 */
	public String getUse() {
		return use;
	}

	/**
	 * Set the Use value.
	 * 
	 * @param newUse
	 *            The new Use value.
	 */
	public void setUse(String newUse) {
		this.use = newUse;
	}

	/**
	 * Get the FeatClass value.
	 * 
	 * @return the FeatClass value.
	 */
	public String getFeatClass() {
		return featClass;
	}

	/**
	 * Set the FeatClass value.
	 * 
	 * @param newFeatClass
	 *            The new FeatClass value.
	 */
	public void setFeatClass(String newFeatClass) {
		this.featClass = newFeatClass;
	}

	/**
	 * Get the Masterwork value.
	 * 
	 * @return the Masterwork value.
	 */
	public boolean isMasterwork() {
		return masterwork;
	}

	/**
	 * Set the Masterwork value.
	 * 
	 * @param newMasterwork
	 *            The new Masterwork value.
	 */
	public void setMasterwork(boolean newMasterwork) {
		this.masterwork = newMasterwork;
	}

	public String getDescription() {

		if (description == null || description.trim().equals("")) {

			return getFullDescription();
		}
		return description;
	}

	/**
	 * Get the Description value.
	 * 
	 * @return the Description value.
	 */
	public String getFullDescription() {
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(name + " : ");
		sbuf.append(" DMG " + damage + "\n");
		sbuf.append(" CRIT " + crit + "\n");
		sbuf.append(" RANGE " + range + "\n");
		sbuf.append(" TYPE " + category + " " + rangedmelee + "\n");
		sbuf.append(" SOURCE " + source + "\n");
		sbuf.append(" NOTES : " + notes1 + "\n");
		if (description != null) {
			sbuf.append(description);
		}
		if (ability1 != null) {
			sbuf.append("\n" + ability1.getDescription());
		}
		if (ability2 != null) {
			sbuf.append("\n" + ability2.getDescription());
		}
		if (ability3 != null) {
			sbuf.append("\n" + ability3.getDescription());
		}
		if (ability4 != null) {
			sbuf.append("\n" + ability4.getDescription());
		}
		if (ability5 != null) {
			sbuf.append("\n" + ability5.getDescription());
		}
		if (ability6 != null) {
			sbuf.append("\n" + ability6.getDescription());
		}
		return sbuf.toString();
	}

	public String getNotesAndAbilities() {
		StringBuffer sbuf = new StringBuffer();
		if (ability1 != null) {
			sbuf.append(ability1.getName() + " ");
		}
		if (ability2 != null) {
			sbuf.append(ability2.getName() + " ");
		}
		if (ability3 != null) {
			sbuf.append(ability3.getName() + " ");
		}
		if (ability4 != null) {
			sbuf.append(ability4.getName() + " ");
		}
		if (ability5 != null) {
			sbuf.append(ability5.getName() + " ");
		}
		if (ability6 != null) {
			sbuf.append(ability6.getName() + " ");
		}
		sbuf.append(notes);
		return sbuf.toString();
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

	public String getSheetFormat() {
		StringBuffer sbuf = new StringBuffer();
		
		if (material != null) {
			sbuf.append(material + " ");
		}
		sbuf.append(name);
		if (bonus != null && !bonus.equals("")) {
			sbuf.append(" +" + bonus);
		} else if (masterwork) {
			sbuf.append("(MW)");
		}
		return sbuf.toString();
	}

	public String toString() {
		StringBuffer sbuf = new StringBuffer();
		if (intelligent) {
			sbuf.append("Intelligent ");
		}
		sbuf.append(name);
		if (bonus != null && !bonus.equals("")) {
			sbuf.append(" +" + bonus);
		}
		sbuf.append("\n");
		if (ability1 != null && ability1.getName() != null) {
			sbuf.append(" of " + ability1.getName());
		}
		if (ability2 != null && ability2.getName() != null) {
			sbuf.append(", " + ability2.getName());
		}
		if (ability3 != null && ability3.getName() != null) {
			sbuf.append(", " + ability3.getName());
		}
		if (ability4 != null && ability4.getName() != null) {
			sbuf.append(", " + ability4.getName());
		}
		if (ability5 != null && ability5.getName() != null) {
			sbuf.append(", " + ability5.getName());
		}
		if (ability6 != null && ability6.getName() != null) {
			sbuf.append(", " + ability6.getName());
		}
		return sbuf.toString();
	}

	public String ammoFormat(){
		return quantity+" "+listFormat();
	}
	
	public String listFormat(){
		
		String nameStr = StrManip.pad(name, 40);
		
		return nameStr + StrManip.pad(source, 25);
		
	}
	
	public String inventoryListFormat(){
		
		
		String nameStr = StrManip.pad(quantity, 3)+StrManip.pad(name, 40);
		
		return nameStr + StrManip.pad(cost, 25);
		
	}
	
	public String playerListFormat() {
		StringBuffer sbuf = new StringBuffer();
		
		if (quantity != null){
			sbuf.append(StrManip.pad(quantity, 3)+" ");
		}
		if (sizeOverride != null && !sizeOverride.equals("")) {
			sbuf.append(sizeOverride + " ");
		}
		if (material != null && !material.getName().equals("Normal")) {
			sbuf.append(material + " ");
		}
		if (intelligent) {
			sbuf.append("Intelligent ");
		}
		sbuf.append(name);
		if (bonus != null && !bonus.equals("")) {
			sbuf.append(" +" + bonus);
		} else if (masterwork) {
			sbuf.append(" (MW)");
		}
		sbuf.append("\n");
		if (ability1 != null && ability1.getName() != null) {
			sbuf.append(" of " + ability1.getName());
		}
		if (ability2 != null && ability2.getName() != null) {
			sbuf.append(", " + ability2.getName());
		}
		if (ability3 != null && ability3.getName() != null) {
			sbuf.append(", " + ability3.getName());
		}
		if (ability4 != null && ability4.getName() != null) {
			sbuf.append(", " + ability4.getName());
		}
		if (ability5 != null && ability5.getName() != null) {
			sbuf.append(", " + ability5.getName());
		}
		if (ability6 != null && ability6.getName() != null) {
			sbuf.append(", " + ability6.getName());
		}
		String name = StrManip.pad(sbuf.toString(), 50);
		
		return name + StrManip.pad(use, 20);
	}

	/**
	 * Get the Ability3 value.
	 * 
	 * @return the Ability3 value.
	 */
	public WeaponAbilities getAbility3() {
		if (ability3 == null) {
			return new WeaponAbilities( );
		}
		return ability3;
	}

	/**
	 * Set the Ability3 value.
	 * 
	 * @param newAbility3
	 *            The new Ability3 value.
	 */
	public void setAbility3(WeaponAbilities newAbility3) {
		this.ability3 = newAbility3;
	}

	/**
	 * Get the Ability2 value.
	 * 
	 * @return the Ability2 value.
	 */
	public WeaponAbilities getAbility2() {
		if (ability2 == null) {
			return new WeaponAbilities();
		}
		return ability2;
	}

	/**
	 * Set the Ability2 value.
	 * 
	 * @param newAbility2
	 *            The new Ability2 value.
	 */
	public void setAbility2(WeaponAbilities newAbility2) {
		this.ability2 = newAbility2;
	}

	/**
	 * Get the Ability1 value.
	 * 
	 * @return the Ability1 value.
	 */
	public WeaponAbilities getAbility1() {
		if (ability1 == null) {
			return new WeaponAbilities( );
		}
		return ability1;
	}

	/**
	 * Set the Ability1 value.
	 * 
	 * @param newAbility1
	 *            The new Ability1 value.
	 */
	public void setAbility1(WeaponAbilities newAbility1) {
		this.ability1 = newAbility1;
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

	public Weapon() {
		Materials m = new Materials();
		m.setDefaults();
		material = m;
	}

	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (name == null) {
			name = "";
		}

		if (cost == null) {
			cost = "";
		}

		if (damage == null) {
			damage = "";
		}

		if (crit == null) {
			crit = "";
		}

		if (range == null) {
			range = "";
		}

		if (weight == null) {
			weight = "";
		}

		if (type == null) {
			type = "";
		}

		if (size == null) {
			size = "";
		}

		if (category == null) {
			category = "";
		}

		if (rangedmelee == null) {
			rangedmelee = "";
		}

		if (source == null) {
			source = "";
		}

		if (notes1 == null) {
			notes1 = "";
		}

		if (notes2 == null) {
			notes2 = "";
		}

		if (exclude == null) {
			exclude = "";
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
		if (damageOverride != null && damageOverride.indexOf("+") > 0){
			errors.add("Damage Override value cannot have any plus modifiers.");
		}
		if (damage.indexOf("+") > 0){
			errors.add("Damage value cannot have any plus modifiers.");
		}
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
	 * Get the value of damage
	 * 
	 * @return a <code>String</code> value
	 */
	public String getDamage() {
		return damage;
	}

	/**
	 * Set the value of damage
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setDamage(String s) {
		damage = s;
	}

	/**
	 * Get the value of crit
	 * 
	 * @return a <code>String</code> value
	 */
	public String getCrit() {
		String critStr = "";
		if (critStart != null && !critStart.equals("")) {
			critStr += critStart + "-" + critEnd + "/";
		} else {
			critStr += "20";
		}
		critStr += "x" + critMult;
		return critStr;
	}

	/**
	 * Set the value of crit
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setCrit(String s) {
		crit = s;
	}

	/**
	 * Get the value of range
	 * 
	 * @return a <code>String</code> value
	 */
	public String getRange() {
		return range;
	}

	/**
	 * Set the value of range
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setRange(String s) {
		range = s;
	}

	/**
	 * Get the value of weight
	 * 
	 * @return a <code>String</code> value
	 */
	public String getWeight() {
		double modWeight = 0;
		if (!StrManip.isNullOrEmpty(weight)) {
			try {
				modWeight = Double.parseDouble(weight);
			} catch (NumberFormatException nfe) {

			}
		}
		if (material != null) {
			double materialMod = Double.parseDouble(material.getWeightCalc());
			modWeight *= materialMod;
		}
		
		if (sizeOverride != null && !sizeOverride.equals("")){
			
			String[] sizes = { "Fine", "Diminutive", "Tiny", "Small", "Medium",
					"Large", "Huge", "Gargantuan", "Colossal" };
			int newsize = 0;
			int origsize = 0;
			for (int i = 0; i < 9; i++) {
				if (sizes[i].equals(size)) {
					origsize = i;
				}
				if (sizes[i].equals(sizeOverride)) {
					newsize = i;
				}
			}
			int diff = newsize - origsize;
			if (diff > 0){
				
				modWeight *= (2*diff);
			}
			else if (diff < 0){
				
				modWeight /= (2*(0-diff));
			}
		}
		
		if (masterwork) {
			modWeight *= 0.95;
		}
		
		return "" + modWeight;
	}

	public int getSizeIndex(){
		String[] sizes = { "Fine", "Diminutive", "Tiny", "Small", "Medium",
				"Large", "Huge", "Gargantuan", "Colossal" };
		int size = 0;
		int bigsize = 0;
		
		for (int i = 0; i < 9; i++) {
			if (sizes[i].equals(size)) {
				size = i;
			}
			if (sizes[i].equals(sizeOverride)) {
				bigsize = i;
			}
		}
		return Math.max(size, bigsize);
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
	 * Get the value of size
	 * 
	 * @return a <code>String</code> value
	 */
	public String getSize() {
		return size;
	}

	/**
	 * Set the value of size
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setSize(String s) {
		size = s;
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
	 * Get the value of rangedmelee
	 * 
	 * @return a <code>String</code> value
	 */
	public String getRangedmelee() {
		return rangedmelee;
	}

	/**
	 * Set the value of rangedmelee
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setRangedmelee(String s) {
		rangedmelee = s;
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
	 * Get the value of notes1
	 * 
	 * @return a <code>String</code> value
	 */
	public String getNotes1() {
		return notes1;
	}

	/**
	 * Set the value of notes1
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setNotes1(String s) {
		notes1 = s;
	}

	/**
	 * Get the value of notes2
	 * 
	 * @return a <code>String</code> value
	 */
	public String getNotes2() {
		return notes2;
	}

	/**
	 * Set the value of notes2
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setNotes2(String s) {
		notes2 = s;
	}

	/**
	 * Get the value of exclude
	 * 
	 * @return a <code>String</code> value
	 */
	public String getExclude() {
		return exclude;
	}

	/**
	 * Set the value of exclude
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setExclude(String s) {
		exclude = s;
	}

	public WeaponAbilities getAbility4() {
		return ability4;
	}

	public void setAbility4(WeaponAbilities ability4) {
		this.ability4 = ability4;
	}

	public WeaponAbilities getAbility5() {
		return ability5;
	}

	public void setAbility5(WeaponAbilities ability5) {
		this.ability5 = ability5;
	}

	public WeaponAbilities getAbility6() {
		return ability6;
	}

	public void setAbility6(WeaponAbilities ability6) {
		this.ability6 = ability6;
	}

	public boolean isNamed() {
		return named;
	}

	public void setNamed(boolean named) {
		this.named = named;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

}

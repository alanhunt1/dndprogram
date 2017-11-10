package initcheck.database;

import initcheck.InitLogger;
import initcheck.character.InventoryItem;
import initcheck.character.library.LibraryItem;
import initcheck.utils.StrManip;

import java.io.Serializable;
import java.util.Vector;


public class Armor implements Serializable, Cloneable, Item, TreasureListItem,
		Exportable, LibraryItem, Importable, InventoryItem, TreasureItem {
	
	private static final long serialVersionUID = -3178861690444572721L;

	InitLogger logger = new InitLogger(this, "defaultLog4j.cfg");

	// universal variables
	private String id;

	private String name;

	private String description;

	private String grade;

	private String cost;

	private String armorbonus = "0";

	private String maxdex;

	private String checkpenalty = "0";

	private String speed30 = "30";

	private String speed20 = "20";

	private String weight;

	private String arcanefail;

	private String type;

	private String source;
	
	// instance variables
	private ArmorAbilities ability1 = null;

	private ArmorAbilities ability2 = null;

	boolean intelligent;

	private String displayType = "armor";

	private String bonus = "0";

	private String atRest;

	private String instanceId;

	boolean masterwork;

	private String playerId;

	String notes;

	private Materials material;
	
	boolean tempMod = false;

	private String modType;
	
	private String quantity;
	
	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public boolean isTempMod() {
		return tempMod;
	}

	public void setTempMod(boolean isTempMod) {
		this.tempMod = isTempMod;
	}

	public String listValue(){
		return "Armor : "+name;
	}
	
	public boolean equals(Armor a) {
		return this.id.equals(a.getId())
				&& this.bonus.equals(a.getBonus())
				&& ((this.ability1 == null && a.getAbility1().isNull()) || (this.ability1
						.getName().equals(a.getAbility1().getName())))
				&& ((this.ability2 == null && a.getAbility2().isNull()) || (this.ability2
						.getName().equals(a.getAbility2().getName())))
				&& this.masterwork == a.isMasterwork()
				&& ((this.notes == null && a.getNotes() == null) || this.notes
						.equals(a.getNotes()))
				&& ((this.material == null && a.getMaterial() == null) || (this.material
						.getMaterialName().equals(a.getMaterial()
						.getMaterialName())));
	}

	public String getListing() {
		StringBuffer sb = new StringBuffer();
		sb.append("Name : " + name + "\n");
		sb.append("Grade : " + grade + "\n");
		sb.append("Cost : " + cost + "\n");
		sb.append("Armor Bonus : " + armorbonus + "\n");
		sb.append("Max Dex : " + maxdex + "\n");
		sb.append("Check Penalty : " + checkpenalty + "\n");
		sb.append("Speed 20 : " + speed20 + "\n");
		sb.append("Speed 30 : " + speed30 + "\n");
		sb.append("Weight : " + weight + "\n");
		sb.append("Arcane Fail Chance : " + arcanefail + "\n");
		sb.append("Type : " + type + "\n");
		sb.append("Description : " + description + "\n");
		return sb.toString();
	}

	public void setArmorDefaults() {
		id = "19";
		name = "None";
		description = "";
		grade = "Light";
		maxdex = "99";
		checkpenalty = "0";
		weight = "0";
		arcanefail = "0";
		type = "ARMOR";
		atRest = "N";
		masterwork = false;
		Materials m = new Materials();
		m.setDefaults();
		material = m;
		notes = "";
		intelligent = false;

	}

	public void setShieldDefaults() {
		id = "20";
		name = "None";
		description = "";
		grade = "Light";
		maxdex = "99";
		checkpenalty = "0";
		weight = "0";
		arcanefail = "0";
		type = "SHIELD";
		displayType = "Shield";
		atRest = "N";
		masterwork = false;
		Materials m = new Materials();
		m.setDefaults();
		material = m;
		notes = "";
		intelligent = false;
	}

	public void setRestArmorDefaults() {
		id = "19";
		name = "None";
		description = "";
		grade = "Light";
		maxdex = "99";
		checkpenalty = "0";
		weight = "0";
		arcanefail = "0";
		type = "ARMOR";
		atRest = "Y";
		masterwork = false;
		Materials m = new Materials();
		m.setDefaults();
		material = m;
		notes = "";
		intelligent = false;

	}

	/**
	 * Get the Material value.
	 * 
	 * @return the Material value.
	 */
	public Materials getMaterial() {
		return material;
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

	public Armor (String s){
		readImport(s);
	}
	
	public void readImport(String s) {
		// when we are importing armor, set the defaults just in case the
		// import file leaves something important out.
		setArmorDefaults();

		// read in the armor
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();

			if (t.getTagName().equals("ArmorId")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("ArmorType")) {
				setDisplayType(t.getTagBody());
			}
			if (t.getTagName().equals("Description")) {
				setDescription(t.getTagBody());
			}
			if (t.getTagName().equals("Bonus")) {
				setBonus(t.getTagBody());
			}
			if (t.getTagName().equals("ArmorAbilities")) {
				ArmorAbilities aa = new ArmorAbilities();
				aa.readImport(t.getTagBody());
				if (ability1 == null){
					setAbility1(aa);
				}else{
					setAbility2(aa);
				}
			}
		
			if (t.getTagName().equals("AtRest")) {
				setAtRest(t.getTagBody());
			}
			if (t.getTagName().equals("Name")) {
				setName(t.getTagBody());
			}
			if (t.getTagName().equals("InstanceId")) {
				setInstanceId(t.getTagBody());
			}
			if (t.getTagName().equals("PlayerId")) {
				setPlayerId(t.getTagBody());
			}
			if (t.getTagName().equals("ArcaneFail")) {
				setArcanefail(t.getTagBody());
			}
			if (t.getTagName().equals("Masterwork")) {
				setMasterwork(true);
			}
			if (t.getTagName().equals("Materials")) {
				Materials m = new Materials(t.getTagBody());
				logger.log("Found Material " + m);
				setMaterial(m);
			}
			if (t.getTagName().equals("Source")) {
				setSource(t.getTagBody());
			}
			s = s.substring(tagName.length() + tagName.length() + 5
					+ tagBody.length(), s.length());
			t = Tag.getTag(s);
		}

	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<PlayerArmor>\n");
		sb.append("<InstanceId>" + instanceId + "</InstanceId>\n");
		sb.append("<ArmorId>" + id + "</ArmorId>\n");
		sb.append("<ArmorType>" + displayType + "</ArmorType>\n");
		sb.append("<Description>" + description + "</Description>\n");
		sb.append("<Bonus>" + bonus + "</Bonus>\n");
		if (ability1 != null){
			sb.append(ability1.exportFormat());
		}
		if (ability2 != null){
			sb.append(ability2.exportFormat());
		}
		sb.append("<AtRest>" + atRest + "</AtRest>\n");
		sb.append("<Name>" + name + "</Name>\n");
		sb.append("<PlayerId>" + playerId + "</PlayerId>\n");
		if (material != null){
			sb.append(material.exportFormat());
		}
		sb.append("<ArcaneFail>" + arcanefail + "</ArcaneFail>\n");
		sb.append("<Source>" + source + "</Source>\n");
		if (masterwork){
			sb.append("<Masterwork>"+masterwork+"</Masterwork>\n");
		}
		sb.append("</PlayerArmor>\n");
		return sb.toString();
	}

	public String existsAs(){
		ArmorDAO db = new ArmorDAO();
		Vector<Armor> v = db.selectArmor(this);
		if(v.size() > 0){
			return ((Armor)v.get(0)).getName();
		}
		return null;
	}
	
	public Vector<Armor> getValues(){
		ArmorDAO db = new ArmorDAO();
		return db.getArmor();
	}
	
	public void save(boolean overwrite){
		ArmorDAO db = new ArmorDAO();
		if (overwrite){
			db.addOrUpdateArmor(this);
		}else{
			db.addArmor(this);
		}
	}
	
	public int getSpeed(int i) {
		if (i == 40) {
			if (Integer.parseInt(speed30) < 30) {
				return 30;
			}
		}
		if (i == 30) {
			return Integer.parseInt(speed30);
		}
		if (i == 20) {
			return Integer.parseInt(speed20);
		}
		return i;
	}

	/**
	 * Get the AtRest value.
	 * 
	 * @return the AtRest value.
	 */
	public String getAtRest() {
		return atRest;
	}

	/**
	 * Set the AtRest value.
	 * 
	 * @param newAtRest
	 *            The new AtRest value.
	 */
	public void setAtRest(String newAtRest) {
		this.atRest = newAtRest;
	}

	public String getProperties() {
		String propString = "";
		if (ability1 != null) {
			propString += ability1.getName();
		}
		if (ability2 != null) {
			propString += ", " + ability2.getName();
		}
		propString += notes;
		return propString;
	}

	public Armor() {

	}

	public String listFormat() {
		StringBuffer sbuf = new StringBuffer();
		if (quantity != null){
			sbuf.append(quantity+" ");
		}
		sbuf.append(StrManip.pad(name, 30));
		sbuf.append(StrManip.pad(source, 20));
		
		return sbuf.toString();
	}
	
	public String inventoryListFormat() {
		StringBuffer sbuf = new StringBuffer();
		
		sbuf.append(StrManip.pad(quantity, 4));
		sbuf.append(StrManip.pad(name, 30));
		sbuf.append(StrManip.pad(cost, 20));
		
		return sbuf.toString();
	}

	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (name == null) {
			name = "";
		}

		if (description == null) {
			description = "";
		}

		if (grade == null) {
			grade = "";
		}

		if (cost == null) {
			cost = "";
		}

		if (armorbonus == null) {
			armorbonus = "";
		}

		if (maxdex == null) {
			maxdex = "";
		}

		if (checkpenalty == null) {
			checkpenalty = "";
		}

		if (speed30 == null) {
			speed30 = "";
		}

		if (speed20 == null) {
			speed20 = "";
		}

		if (weight == null) {
			weight = "";
		}

		if (arcanefail == null) {
			arcanefail = "";
		}

		if (type == null) {
			type = "";
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
		if (StrManip.isNullOrEmpty(name)){
			errors.add("Name is a required field");
		}
		if (StrManip.isNullOrEmpty(description)){
			errors.add("Description is a required field");
		}
		if (StrManip.isNullOrEmpty(grade)){
			errors.add("Grade is a required field");
		}
		if (StrManip.isNullOrEmpty(cost)){
			errors.add("Cost is a required field");
		}
		if (StrManip.isNullOrEmpty(armorbonus)){
			errors.add("Armor Bonus is a required field");
		}
		if (StrManip.isNullOrEmpty(maxdex)){
			errors.add("Max Dex is a required field");
		}
		if (StrManip.isNullOrEmpty(checkpenalty)){
			errors.add("Check Penalty is a required field");
		}
		if (StrManip.isNullOrEmpty(speed20)){
			errors.add("Speed 20 is a required field");
		}
		if (StrManip.isNullOrEmpty(speed30)){
			errors.add("Speed 30 is a required field");
		}
		if (StrManip.isNullOrEmpty(weight)){
			errors.add("Weight is a required field");
		}
		if (StrManip.isNullOrEmpty(arcanefail)){
			errors.add("Arcane failure percentage is a required field");
		}
		if (StrManip.isNullOrEmpty(type)){
			errors.add("Type is a required field");
		}
		if (StrManip.isNullOrEmpty(source)){
			errors.add("Source is a required field");
		}
		
	}

	private void checkNumbers(Vector<String> errors) {
		if (!StrManip.isNullOrEmpty(cost)){
			try {
				Integer.parseInt(cost);
			}catch (NumberFormatException nfe){
				errors.add("Please enter a whole number for cost in GP.");
			}
		}
		if (!StrManip.isNullOrEmpty(armorbonus)){
			try {
				Integer.parseInt(armorbonus);
			}catch (NumberFormatException nfe){
				errors.add("Please enter a whole number for the armor bonus.");
			}
		}
		if (!StrManip.isNullOrEmpty(maxdex)){
			try {
				Integer.parseInt(maxdex);
			}catch (NumberFormatException nfe){
				errors.add("Please enter a whole number for maximum dex bonus.");
			}
		}
		if (!StrManip.isNullOrEmpty(checkpenalty)){
			try {
				Integer.parseInt(checkpenalty);
			}catch (NumberFormatException nfe){
				errors.add("Please enter an integer value for the armor check penalty.");
			}
		}
		if (!StrManip.isNullOrEmpty(speed20)){
			try {
				Integer.parseInt(speed20);
			}catch (NumberFormatException nfe){
				errors.add("Please enter an integer value for speed at 20 foot base");
			}
		}
		if (!StrManip.isNullOrEmpty(speed30)){
			try {
				Integer.parseInt(speed30);
			}catch (NumberFormatException nfe){
				errors.add("Please enter an integer value for speed at 30 foot base");
			}
		}
		if (!StrManip.isNullOrEmpty(weight)){
			try {
				Double.parseDouble(weight);
			}catch (NumberFormatException nfe){
				errors.add("Please enter a numeric value for weight.");
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

	public String getFullDescription() {
		StringBuffer sb = new StringBuffer();
		sb.append( name+"\r\n");

		

		sb.append("Grade : "+ grade+"\r\n");

		sb.append("Cost : "+ cost+"\r\n");

		sb.append("Armor Bonus : "+ armorbonus+"\r\n");

		sb.append("Max Dex Bonus : "+ maxdex+"\r\n");

		sb.append("Armor Check Penalty : "+ checkpenalty +"\r\n");

		//sb.append( speed30 );

		//sb.append( speed20 );

		sb.append("Weight "+ weight +"\r\n");

		sb.append("Arcane Failure Rate : " + arcanefail+"\r\n" );

		sb.append("Type : "+ type +"\r\n");

		sb.append("Source : "+ source +"\r\n");
		
		sb.append( description);
		
		return sb.toString();
	}

	/**
	 * Get the value of grade
	 * 
	 * @return a <code>String</code> value
	 */
	public String getGrade() {
		if (material != null && material.getMaterialName().equals("Mithril")) {
			if (grade.equals("Heavy")) {
				return "Medium";
			}
			if (grade.equals("Medium")) {
				return "Light";
			}
		}
		return grade;
	}

	/**
	 * Set the value of grade
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setGrade(String s) {
		grade = s;
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
	 * Get the value of armorbonus
	 * 
	 * @return a <code>String</code> value
	 */
	public String getArmorbonus() {
		int bonus = 0;
		try {
			bonus = Integer.parseInt(armorbonus);
		} catch (NumberFormatException nfe) {

		}

		// if the armor is adamantine, it gets a bonus from that material.
		if (material != null && material.getMaterialName().equals("Adamantine")) {
			if (grade.equals("Heavy")) {
				bonus += 3;
			} else if (grade.equals("Medium")) {
				bonus += 2;
			} else {
				bonus += 1;
			}
		}
		return "" + bonus;
	}

	/**
	 * Set the value of armorbonus
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setArmorbonus(String s) {
		armorbonus = s;
	}

	/**
	 * Get the value of maxdex
	 * 
	 * @return a <code>String</code> value
	 */
	public String getMaxdex() {
		return maxdex;
	}

	/**
	 * Set the value of maxdex
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setMaxdex(String s) {
		maxdex = s;
	}

	/**
	 * Get the value of checkpenalty
	 * 
	 * @return a <code>String</code> value
	 */
	public String getCheckpenalty() {
		if (ability1 != null && ability1.getName() != null
				&& ability1.getName().equals("Animated")) {
			checkpenalty = "0";
		}
		if (ability2 != null && ability2.getName() != null
				&& ability2.getName().equals("Animated")) {
			checkpenalty = "0";
		}

		if (bonus == null || bonus.equals("")) {
			bonus = "0";
		}
		if (checkpenalty == null || checkpenalty.equals("")) {
			checkpenalty = "0";
		}

		if (Integer.parseInt(bonus) > 0) {
			int modpenalty = Integer.parseInt(checkpenalty)
					+ Integer.parseInt(bonus);
			if (modpenalty > 0) {
				modpenalty = 0;
			}
			return "" + modpenalty;
		}

		if (masterwork) {
			return "" + (Integer.parseInt(checkpenalty) + 1);
		}

		return checkpenalty;
	}

	/**
	 * Set the value of checkpenalty
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setCheckpenalty(String s) {
		checkpenalty = s;
	}

	/**
	 * Get the value of speed30
	 * 
	 * @return a <code>String</code> value
	 */
	public String getSpeed30() {
		return speed30;
	}

	/**
	 * Set the value of speed30
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setSpeed30(String s) {
		speed30 = s;
	}

	/**
	 * Get the value of speed20
	 * 
	 * @return a <code>String</code> value
	 */
	public String getSpeed20() {
		return speed20;
	}

	/**
	 * Set the value of speed20
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setSpeed20(String s) {
		speed20 = s;
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
			} catch (Exception nfe) {

			}
		}
		if (material != null) {
			double materialMod = Double.parseDouble(material.getWeightCalc());
			modWeight *= materialMod;
		}
		if (masterwork) {
			modWeight *= 0.95;
		}
		return "" + modWeight;
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
	 * Get the value of arcanefail
	 * 
	 * @return a <code>String</code> value
	 */
	public String getArcanefail() {
		return arcanefail;
	}

	/**
	 * Set the value of arcanefail
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setArcanefail(String s) {
		arcanefail = s;
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
		StringBuffer sbuf = new StringBuffer();
		if (intelligent) {
			sbuf.append("Intelligent ");
		}
		sbuf.append(name);
		if (bonus != null && !bonus.equals("") && !bonus.equals("0")) {
			sbuf.append(" +" + bonus);
		}
		// sbuf.append("\n");
		if (material != null && !material.getMaterialName().equals("Normal")) {
			sbuf.append(" " + material);
		}
		if (ability1 != null && ability1.getName() != null
				&& !ability1.getName().equals("null")) {
			sbuf.append(" of " + ability1.getName());
		}
		if (ability2 != null && ability2.getName() != null
				&& !ability2.getName().equals("null")) {
			sbuf.append(", " + ability2.getName());
		}
		return sbuf.toString();
	}

	/**
	 * Get the Ability2 value.
	 * 
	 * @return the Ability2 value.
	 */
	public Ability getAbility2() {
		if (ability2 == null) {
			return new Ability();
		}
		return ability2;
	}

	/**
	 * Set the Ability2 value.
	 * 
	 * @param newAbility2
	 *            The new Ability2 value.
	 */
	public void setAbility2(ArmorAbilities newAbility2) {
		this.ability2 = newAbility2;
	}

	/**
	 * Get the Ability1 value.
	 * 
	 * @return the Ability1 value.
	 */
	public Ability getAbility1() {
		if (ability1 == null) {
			return new Ability();
		}
		return ability1;
	}

	/**
	 * Set the Ability1 value.
	 * 
	 * @param newAbility1
	 *            The new Ability1 value.
	 */
	public void setAbility1(ArmorAbilities newAbility1) {
		this.ability1 = newAbility1;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getModType() {
		return modType;
	}

	public void setModType(String modType) {
		this.modType = modType;
	}

}

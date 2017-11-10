package initcheck.database;

import initcheck.character.library.LibraryItem;
import initcheck.utils.StrManip;

import java.io.Serializable;
import java.util.Vector;

public class Spell implements Serializable, Cloneable, Exportable, LibraryItem, Importable {

	
	private static final long serialVersionUID = 1L;

	private String id;

	private String spellName;

	private String school;

	private String type;

	private String subType;

	private String level;

	private String components;

	private String time;

	private String range;

	private String effect;

	private String duration;

	private String savingThrow;

	private String resist;

	private String description;

	private String effectType;

	private String shortDesc;

	private String instanceId;
	
	private String source;

	private Vector<SpellClass> spellClasses  = new Vector<SpellClass>();

	private String spellClassDisplay = null;
	
	public String getSpellClassDisplay(){
		if (spellClassDisplay == null){
			spellClassDisplay = "";
			for (int i = 0; i < spellClasses.size(); i++){
				SpellClass sc = spellClasses.get(i);
				spellClassDisplay += sc.getSpellClass()+sc.getSpellLevel()+",";
			}
			if (!spellClassDisplay.equals("") && spellClassDisplay.charAt(spellClassDisplay.length() - 1) == ',') {
				spellClassDisplay = spellClassDisplay.substring(0, spellClassDisplay.length() - 1);
			}
		}
		return spellClassDisplay;
	}
	
	public String listFormat(){
		return StrManip.pad(spellName, 30)+StrManip.pad(getSpellClassDisplay(), 30)+StrManip.pad(source, 20);
	}
	
	public String listValue(){
		return "Spell : " + spellName;
	}
	
	public String getName(){
		return spellName;
	}
	
	public void setName(String s){
		spellName = s;
	}
	
	public Vector<SpellClass> getSpellClasses() {
		return spellClasses;
	}

	public void setSpellClasses(Vector<SpellClass> spellClasses) {
		this.spellClasses = spellClasses;
	}

	public String getFullDescription() {
		return description;
	}

	public String getDisplayFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append(spellName + "\n");
		sb.append(school + "," + type + "," + (subType == null ? "" : subType)
				+ "\n");
		sb.append(level + "\n");
		sb.append(components + "\n");
		sb.append(time + "\n");
		sb.append(range + effect + "\n");
		sb.append("Duration " + duration + "\n");
		sb.append("Save " + savingThrow + "\n");
		sb.append("Resist " + resist + "\n");
		sb.append(shortDesc + "\n");
		sb.append(description + "\n");
		return sb.toString();
	}

	public Spell() {

	}

	public Spell(String s) {
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
			if (t.getTagName().equals("SpellName")) {
				setSpellName(t.getTagBody());
			}
			if (t.getTagName().equals("School")) {
				setSchool(t.getTagBody());
			}
			if (t.getTagName().equals("Type")) {
				setType(t.getTagBody());
			}
			if (t.getTagName().equals("SubType")) {
				setSubType(t.getTagBody());
			}
			if (t.getTagName().equals("Level")) {
				setLevel(t.getTagBody());
			}
			if (t.getTagName().equals("Components")) {
				setComponents(t.getTagBody());
			}
			if (t.getTagName().equals("Time")) {
				setTime(t.getTagBody());
			}
			if (t.getTagName().equals("Range")) {
				setRange(t.getTagBody());
			}
			if (t.getTagName().equals("Effect")) {
				setEffect(t.getTagBody());
			}
			if (t.getTagName().equals("Duration")) {
				setDuration(t.getTagBody());
			}
			if (t.getTagName().equals("SavingThrow")) {
				setSavingThrow(t.getTagBody());
			}
			if (t.getTagName().equals("Resist")) {
				setResist(t.getTagBody());
			}
			if (t.getTagName().equals("Description")) {
				setDescription(t.getTagBody());
			}
			if (t.getTagName().equals("EffectType")) {
				setEffectType(t.getTagBody());
			}
			if (t.getTagName().equals("ShortDesc")) {
				setShortDesc(t.getTagBody());
			}
			if (t.getTagName().equals("Source")) {
				setSource(t.getTagBody());
			}
			if (t.getTagName().equals("SpellClass")) {
				spellClasses.add(new SpellClass(t.getTagBody()));
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Spell>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<SpellName>" + spellName + "</SpellName>\n");
		sb.append("<School>" + school + "</School>\n");
		sb.append("<Type>" + type + "</Type>\n");
		sb.append("<SubType>" + subType + "</SubType>\n");
		sb.append("<Level>" + level + "</Level>\n");
		sb.append("<Components>" + components + "</Components>\n");
		sb.append("<Time>" + time + "</Time>\n");
		sb.append("<Range>" + range + "</Range>\n");
		sb.append("<Effect>" + effect + "</Effect>\n");
		sb.append("<Duration>" + duration + "</Duration>\n");
		sb.append("<SavingThrow>" + savingThrow + "</SavingThrow>\n");
		sb.append("<Resist>" + resist + "</Resist>\n");
		sb.append("<Description>" + description + "</Description>\n");
		sb.append("<EffectType>" + effectType + "</EffectType>\n");
		sb.append("<ShortDesc>" + shortDesc + "</ShortDesc>\n");
		sb.append("<Source>" + source + "</Source>\n");
		for (int i = 0; i < spellClasses.size(); i++){
			sb.append(spellClasses.get(i).exportFormat());
		}
		sb.append("</Spell>\n");
		return sb.toString();
	}

	public String existsAs(){
		SpellDAO db = new SpellDAO();
		Vector<Spell> v = db.selectSpell(this);
		if(v.size() > 0){
			return ((Spell)v.get(0)).getName();
		}
		return null;
	}
	
	public Vector<Spell> getValues(){
		SpellDAO db = new SpellDAO();
		return db.getSpells();
	}
	
	public void save(boolean overwrite){
		SpellDAO db = new SpellDAO();
		
		if (overwrite){
			id = ""+db.addOrUpdateSpell(this);
		}
		
		SpellClassesDAO scdb = new SpellClassesDAO();
		scdb.clearSpellClasses(id);
		for (int i = 0; i < spellClasses.size(); i++){
			scdb.addSpellClasses(spellClasses.get(i));
		}
		
	}
	
	public String toString() {
		return spellName;
	}

	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (spellName == null) {
			spellName = "";
		}

		if (school == null) {
			school = "";
		}

		if (type == null) {
			type = "";
		}

		if (subType == null) {
			subType = "";
		}

		if (level == null) {
			level = "";
		}

		if (components == null) {
			components = "";
		}

		if (time == null) {
			time = "";
		}

		if (range == null) {
			range = "";
		}

		if (effect == null) {
			effect = "";
		}

		if (duration == null) {
			duration = "";
		}

		if (savingThrow == null) {
			savingThrow = "";
		}

		if (resist == null) {
			resist = "";
		}

		if (description == null) {
			description = "";
		}

		if (effectType == null) {
			effectType = "";
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
	 * Get the value of spellName
	 * 
	 * @return a <code>String</code> value
	 */
	public String getSpellName() {
		return spellName;
	}

	/**
	 * Set the value of spellName
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setSpellName(String s) {
		spellName = s;
	}

	/**
	 * Get the value of school
	 * 
	 * @return a <code>String</code> value
	 */
	public String getSchool() {
		return school;
	}

	/**
	 * Set the value of school
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setSchool(String s) {
		school = s;
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
	 * Get the value of subType
	 * 
	 * @return a <code>String</code> value
	 */
	public String getSubType() {
		return subType;
	}

	/**
	 * Set the value of subType
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setSubType(String s) {
		subType = s;
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
	 * Get the value of components
	 * 
	 * @return a <code>String</code> value
	 */
	public String getComponents() {
		return components;
	}

	/**
	 * Set the value of components
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setComponents(String s) {
		components = s;
	}

	/**
	 * Get the value of time
	 * 
	 * @return a <code>String</code> value
	 */
	public String getTime() {
		return time;
	}

	/**
	 * Set the value of time
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setTime(String s) {
		time = s;
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
	 * Get the value of effect
	 * 
	 * @return a <code>String</code> value
	 */
	public String getEffect() {
		return effect;
	}

	/**
	 * Set the value of effect
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setEffect(String s) {
		effect = s;
	}

	/**
	 * Get the value of duration
	 * 
	 * @return a <code>String</code> value
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * Set the value of duration
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setDuration(String s) {
		duration = s;
	}

	/**
	 * Get the value of savingThrow
	 * 
	 * @return a <code>String</code> value
	 */
	public String getSavingThrow() {
		return savingThrow;
	}

	/**
	 * Set the value of savingThrow
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setSavingThrow(String s) {
		savingThrow = s;
	}

	/**
	 * Get the value of resist
	 * 
	 * @return a <code>String</code> value
	 */
	public String getResist() {
		return resist;
	}

	/**
	 * Set the value of resist
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setResist(String s) {
		resist = s;
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
	 * Get the value of effectType
	 * 
	 * @return a <code>String</code> value
	 */
	public String getEffectType() {
		return effectType;
	}

	/**
	 * Set the value of effectType
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setEffectType(String s) {
		effectType = s;
	}

	/**
	 * Get the ShortDesc value.
	 * 
	 * @return the ShortDesc value.
	 */
	public String getShortDesc() {
		return shortDesc;
	}

	/**
	 * Set the ShortDesc value.
	 * 
	 * @param newShortDesc
	 *            The new ShortDesc value.
	 */
	public void setShortDesc(String newShortDesc) {
		this.shortDesc = newShortDesc;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}

package initcheck.database;

import java.io.Serializable;
import java.util.Vector;

import initcheck.character.library.LibraryItem;
import initcheck.graphics.TiledListItem;
import initcheck.utils.StrManip;

public class Skill implements Serializable, Cloneable, Exportable, LibraryItem, Importable, TiledListItem {

	
	private static final long serialVersionUID = 1L;

	String id;

	String skill;

	String ability;

	String description;

	String trainedOnly = "N";

	String class1;

	String class2;

	String class3;

	String class4;

	String class5;

	String armorCheck = "N";

	String ranks;

	String notes;

	String mods;

	String misc;

	String classId;

	String instanceId;

	String effectiveRanks;

	String crossClassOverride = "N";

	String synBonus;

	String synValue;

	String synRanks;

	String synBonus2;

	String synRanks2;

	String synValue2;

	String source;
	
	public String listValue(){
		return "Skill : "+skill;
	}
	
	public void setName(String s){
		skill = s;
	}
	
	public String getName(){
		return skill;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getFullDescription() {
		return description;
	}

	public Skill(String s) {
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
			if (t.getTagName().equals("SkillName")) {
				setSkill(t.getTagBody());
			}
			if (t.getTagName().equals("Ability")) {
				setAbility(t.getTagBody());
			}
			if (t.getTagName().equals("Description")) {
				setDescription(t.getTagBody());
			}
			if (t.getTagName().equals("TrainedOnly")) {
				setTrainedOnly(t.getTagBody());
			}
			if (t.getTagName().equals("Class1")) {
				setClass1(t.getTagBody());
			}
			if (t.getTagName().equals("Class2")) {
				setClass2(t.getTagBody());
			}
			if (t.getTagName().equals("Class3")) {
				setClass3(t.getTagBody());
			}
			if (t.getTagName().equals("Class4")) {
				setClass4(t.getTagBody());
			}
			if (t.getTagName().equals("Class5")) {
				setClass5(t.getTagBody());
			}
			if (t.getTagName().equals("ArmorCheck")) {
				setArmorCheck(t.getTagBody());
			}
			if (t.getTagName().equals("SynBonus")) {
				setSynBonus(t.getTagBody());
			}
			if (t.getTagName().equals("SynValue")) {
				setSynValue(t.getTagBody());
			}
			if (t.getTagName().equals("SynRanks")) {
				setSynRanks(t.getTagBody());
			}
			if (t.getTagName().equals("SynBonus2")) {
				setSynBonus2(t.getTagBody());
			}
			if (t.getTagName().equals("SynValue2")) {
				setSynValue2(t.getTagBody());
			}
			if (t.getTagName().equals("SynRanks2")) {
				setSynRanks2(t.getTagBody());
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
		sb.append("<Skill>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<SkillName>" + skill + "</SkillName>\n");
		sb.append("<Ability>" + ability + "</Ability>\n");
		sb.append("<Description>" + description + "</Description>\n");
		sb.append("<TrainedOnly>" + trainedOnly + "</TrainedOnly>\n");
		sb.append("<Class1>" + class1 + "</Class1>\n");
		sb.append("<Class2>" + class2 + "</Class2>\n");
		sb.append("<Class3>" + class3 + "</Class3>\n");
		sb.append("<Class4>" + class4 + "</Class4>\n");
		sb.append("<Class5>" + class5 + "</Class5>\n");
		sb.append("<ArmorCheck>" + armorCheck + "</ArmorCheck>\n");
		sb.append("<SynBonus>" + synBonus + "</SynBonus>\n");
		sb.append("<SynValue>" + synValue + "</SynValue>\n");
		sb.append("<SynRanks>" + synRanks + "</SynRanks>\n");
		sb.append("<SynBonus2>" + synBonus2 + "</SynBonus2>\n");
		sb.append("<SynValue2>" + synValue2 + "</SynValue2>\n");
		sb.append("<SynRanks2>" + synRanks2 + "</SynRanks2>\n");
		sb.append("<Source>" + source + "</Source>\n");
		sb.append("</Skill>\n");
		return sb.toString();
	}
	
	public String existsAs(){
		SkillDAO db = new SkillDAO();
		Vector<Skill> v = db.selectSkill(this);
		if(v.size() > 0){
			return ((Skill)v.get(0)).getName();
		}
		return null;
	}
	
	public Vector<Skill> getValues(){
		SkillDAO db = new SkillDAO();
		return db.getSkills();
	}
	
	public void save(boolean overwrite){
		SkillDAO db = new SkillDAO();
		if (overwrite){
			db.addOrUpdateSkill(this);
		}else{
			db.addSkill(this);
		}
	}

	/**
	 * Get the SynValue2 value.
	 * 
	 * @return the SynValue2 value.
	 */
	public String getSynValue2() {
		return synValue2;
	}

	/**
	 * Set the SynValue2 value.
	 * 
	 * @param newSynValue2
	 *            The new SynValue2 value.
	 */
	public void setSynValue2(String newSynValue2) {
		this.synValue2 = newSynValue2;
	}

	/**
	 * Get the SynRanks2 value.
	 * 
	 * @return the SynRanks2 value.
	 */
	public String getSynRanks2() {
		return synRanks2;
	}

	/**
	 * Set the SynRanks2 value.
	 * 
	 * @param newSynRanks2
	 *            The new SynRanks2 value.
	 */
	public void setSynRanks2(String newSynRanks2) {
		this.synRanks2 = newSynRanks2;
	}

	/**
	 * Get the SynBonus2 value.
	 * 
	 * @return the SynBonus2 value.
	 */
	public String getSynBonus2() {
		return synBonus2;
	}

	/**
	 * Set the SynBonus2 value.
	 * 
	 * @param newSynBonus2
	 *            The new SynBonus2 value.
	 */
	public void setSynBonus2(String newSynBonus2) {
		this.synBonus2 = newSynBonus2;
	}

	/**
	 * Get the SynRanks value.
	 * 
	 * @return the SynRanks value.
	 */
	public String getSynRanks() {
		return synRanks;
	}

	/**
	 * Set the SynRanks value.
	 * 
	 * @param newSynRanks
	 *            The new SynRanks value.
	 */
	public void setSynRanks(String newSynRanks) {
		this.synRanks = newSynRanks;
	}

	/**
	 * Get the SynValue value.
	 * 
	 * @return the SynValue value.
	 */
	public String getSynValue() {
		return synValue;
	}

	/**
	 * Set the SynValue value.
	 * 
	 * @param newSynValue
	 *            The new SynValue value.
	 */
	public void setSynValue(String newSynValue) {
		this.synValue = newSynValue;
	}

	/**
	 * Get the SynBonus value.
	 * 
	 * @return the SynBonus value.
	 */
	public String getSynBonus() {
		return synBonus;
	}

	/**
	 * Set the SynBonus value.
	 * 
	 * @param newSynBonus
	 *            The new SynBonus value.
	 */
	public void setSynBonus(String newSynBonus) {
		this.synBonus = newSynBonus;
	}

	/**
	 * Get the CrossClassOverride value.
	 * 
	 * @return the CrossClassOverride value.
	 */
	public String getCrossClassOverride() {
		return crossClassOverride;
	}

	/**
	 * Set the CrossClassOverride value.
	 * 
	 * @param newCrossClassOverride
	 *            The new CrossClassOverride value.
	 */
	public void setCrossClassOverride(String newCrossClassOverride) {
		this.crossClassOverride = newCrossClassOverride;
	}

	/**
	 * Get the EffectiveRanks value.
	 * 
	 * @return the EffectiveRanks value.
	 */
	public String getEffectiveRanks() {
		ClassSkillsDAO csdb = new ClassSkillsDAO();
		boolean crossClass = crossClassOverride != null
				&& crossClassOverride.equals("Y");
		String skid = id;
		if (skill.startsWith("Craft")) {
			skid = "9";
		}
		if (skill.startsWith("Profession")) {
			skid = "30";
		}
		if (crossClass || csdb.isClassSkill(classId, skid)) {
			return ranks;
		}
		if (ranks == null) {
			return "0";
		}
		return "" + (Double.parseDouble(ranks) / 2);
	}

	/**
	 * Set the EffectiveRanks value.
	 * 
	 * @param newEffectiveRanks
	 *            The new EffectiveRanks value.
	 */
	public void setEffectiveRanks(String newEffectiveRanks) {
		this.effectiveRanks = newEffectiveRanks;
	}

	public boolean isAvailableTo(String cclass) {
	

		if (class1 == null || class1.equals(cclass)) {
			return true;
		}
		if (class2 != null && class2.equals(cclass)) {
			return true;
		}
		if (class3 != null && class3.equals(cclass)) {
			return true;
		}
		if (class4 != null && class4.equals(cclass)) {
			return true;
		}
		if (class5 != null && class5.equals(cclass)) {
			return true;
		}
		return false;
	}

	public String getScore() {
		int ranksInt = 0;
		if (ranks != null) {
			ranksInt = Integer.parseInt(ranks);
		}

		int modsInt = 0;
		if (mods != null) {
			modsInt = Integer.parseInt(mods);
		}

		int miscInt = 0;
		if (misc != null) {
			miscInt = Integer.parseInt(misc);
		}

		return "" + ranksInt + modsInt + miscInt;
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
	 * Get the ClassId value.
	 * 
	 * @return the ClassId value.
	 */
	public String getClassId() {
		return classId;
	}

	/**
	 * Set the ClassId value.
	 * 
	 * @param newClassId
	 *            The new ClassId value.
	 */
	public void setClassId(String newClassId) {
		this.classId = newClassId;
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
	 * Get the Misc value.
	 * 
	 * @return the Misc value.
	 */
	public String getMisc() {
		return misc;
	}

	/**
	 * Set the Misc value.
	 * 
	 * @param newMisc
	 *            The new Misc value.
	 */
	public void setMisc(String newMisc) {
		this.misc = newMisc;
	}

	/**
	 * Get the Mods value.
	 * 
	 * @return the Mods value.
	 */
	public String getMods() {
		return mods;
	}

	/**
	 * Set the Mods value.
	 * 
	 * @param newMods
	 *            The new Mods value.
	 */
	public void setMods(String newMods) {
		this.mods = newMods;
	}

	/**
	 * Get the Ranks value.
	 * 
	 * @return the Ranks value.
	 */
	public String getRanks() {
		return ranks;
	}

	/**
	 * Set the Ranks value.
	 * 
	 * @param newRanks
	 *            The new Ranks value.
	 */
	public void setRanks(String newRanks) {
		this.ranks = newRanks;
	}

	public String listFormat(){
		return StrManip.pad(skill, 30) + StrManip.pad("(" + ability + ")", 15)+StrManip.pad(source, 20);
	}
	
	public String toString() {
		if (ranks == null) {
			return StrManip.pad(skill + "(" + ability + ")", 40);
		}
		String s = getEffectiveRanks();

		String ret = StrManip.pad(skill, 25)
				+ StrManip.pad(" (" + s + " ER)", 9);
		if (!s.equals(getRanks())) {
			ret += " CC";
		}
		return ret;
	}

	/**
	 * Get the ArmorCheck value.
	 * 
	 * @return the ArmorCheck value.
	 */
	public String getArmorCheck() {
		return armorCheck;
	}

	/**
	 * Set the ArmorCheck value.
	 * 
	 * @param newArmorCheck
	 *            The new ArmorCheck value.
	 */
	public void setArmorCheck(String newArmorCheck) {
		this.armorCheck = newArmorCheck;
	}

	/**
	 * Get the Class5 value.
	 * 
	 * @return the Class5 value.
	 */
	public String getClass5() {
		return class5;
	}

	/**
	 * Set the Class5 value.
	 * 
	 * @param newClass5
	 *            The new Class5 value.
	 */
	public void setClass5(String newClass5) {
		this.class5 = newClass5;
	}

	/**
	 * Get the Class4 value.
	 * 
	 * @return the Class4 value.
	 */
	public String getClass4() {
		return class4;
	}

	/**
	 * Set the Class4 value.
	 * 
	 * @param newClass4
	 *            The new Class4 value.
	 */
	public void setClass4(String newClass4) {
		this.class4 = newClass4;
	}

	/**
	 * Get the Class3 value.
	 * 
	 * @return the Class3 value.
	 */
	public String getClass3() {
		return class3;
	}

	/**
	 * Set the Class3 value.
	 * 
	 * @param newClass3
	 *            The new Class3 value.
	 */
	public void setClass3(String newClass3) {
		this.class3 = newClass3;
	}

	/**
	 * Get the Class2 value.
	 * 
	 * @return the Class2 value.
	 */
	public String getClass2() {
		return class2;
	}

	/**
	 * Set the Class2 value.
	 * 
	 * @param newClass2
	 *            The new Class2 value.
	 */
	public void setClass2(String newClass2) {
		this.class2 = newClass2;
	}

	/**
	 * Get the Class1 value.
	 * 
	 * @return the Class1 value.
	 */
	public String getClass1() {
		return class1;
	}

	/**
	 * Set the Class1 value.
	 * 
	 * @param newClass1
	 *            The new Class1 value.
	 */
	public void setClass1(String newClass1) {
		this.class1 = newClass1;
	}

	/**
	 * Get the TrainedOnly value.
	 * 
	 * @return the TrainedOnly value.
	 */
	public String getTrainedOnly() {
		return trainedOnly;
	}

	/**
	 * Set the TrainedOnly value.
	 * 
	 * @param newTrainedOnly
	 *            The new TrainedOnly value.
	 */
	public void setTrainedOnly(String newTrainedOnly) {
		this.trainedOnly = newTrainedOnly;
	}

	public Skill() {

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
	 * Get the value of skill
	 * 
	 * @return a <code>String</code> value
	 */
	public String getSkill() {
		return skill;
	}

	/**
	 * Set the value of skill
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setSkill(String s) {
		skill = s;
	}

	/**
	 * Get the value of ability
	 * 
	 * @return a <code>String</code> value
	 */
	public String getAbility() {
		return ability;
	}

	/**
	 * Set the value of ability
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setAbility(String s) {
		ability = s;
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

}

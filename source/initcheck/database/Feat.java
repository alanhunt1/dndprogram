package initcheck.database;

import initcheck.character.library.LibraryItem;
import initcheck.utils.StrManip;

import java.io.Serializable;
import java.util.Vector;

public class Feat implements Serializable, Cloneable, Exportable, LibraryItem, Importable {


	private static final long serialVersionUID = 1L;

	String id;

	String featName;

	String description;

	String type;

	String type2;

	String type3;

	String value;

	String featType;

	String attackBonus;

	String damageBonus;

	String rangeLimit;

	String override;

	String instanceId;

	String special = "N";

	String shortText;

	boolean saveFeat;

	boolean hpFeat;

	boolean acFeat;

	boolean statFeat;

	boolean initFeat;

	boolean skillFeat;

	boolean tempBonus;

	String tempType;
	
	int metamagicLevel;

	String metamagicPrefix;
	
	Vector<FeatPrereq> prerequisites = new Vector<FeatPrereq>();

	Vector<FeatEffects> effects = new Vector<FeatEffects>();
	
	String source;
	
	public String listValue(){
		return "Feat : "+featName;
	}
	
	public String getName(){
		return featName;
	}
	
	public void setName(String s){
		featName = s;
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

	/**
	 * Get the Prerequisites value.
	 * 
	 * @return the Prerequisites value.
	 */
	public Vector<FeatPrereq> getPrerequisites() {
		return prerequisites;
	}

	/**
	 * Set the Prerequisites value.
	 * 
	 * @param newPrerequisites
	 *            The new Prerequisites value.
	 */
	public void setPrerequisites(Vector<FeatPrereq> newPrerequisites) {
		this.prerequisites = newPrerequisites;
	}

	
	
	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Feat>\n");
		sb.append("<Id>" + instanceId + "</Id>\n");
		sb.append("<FeatName>" + featName + "</FeatName>\n");
		sb.append("<Description>" + description + "</Description>\n");
		sb.append("<Type>" + type + "</Type>\n");
		sb.append("<Type2>" + type2 + "</Type2>\n");
		sb.append("<Type3>" + type3 + "</Type3>\n");
		sb.append("<FeatType>" + featType + "</FeatType>\n");
		sb.append("<AttackBonus>" + attackBonus + "</AttackBonus>\n");
		sb.append("<DamageBonus>" + damageBonus + "</DamageBonus>\n");
		sb.append("<RangeLimit>" + rangeLimit + "</RangeLimit>\n");
		sb.append("<ShortText>" + shortText + "</ShortText>\n");
		sb.append("<HpFeat>" + hpFeat + "</HpFeat>\n");
		sb.append("<AcFeat>" + acFeat + "</AcFeat>\n");
		sb.append("<StatFeat>" + statFeat + "</StatFeat>\n");
		sb.append("<InitFeat>" + initFeat + "</InitFeat>\n");
		sb.append("<SaveFeat>" + saveFeat + "</SaveFeat>\n");
		sb.append("<SkillFeat>" + skillFeat + "</SkillFeat>\n");
		sb.append("<TempBonus>" + tempBonus + "</TempBonus>\n");
		sb.append("<TempType>" + tempType + "</TempType>\n");
		sb.append("<FeatId>" + id + "</FeatId>\n");
		sb.append("<FeatMod>" + value + "</FeatMod>\n");
		sb.append("<Override>" + override + "</Override>\n");
		sb.append("<Special>" + special + "</Special>\n");
		sb.append("<MetamagicLevel>"+metamagicLevel+"</MetamagicLevel>");
		sb.append("<MetamagicPrefix>"+metamagicPrefix+"</MetamagicPrefix>");
		FeatPrereqDAO fdb = new FeatPrereqDAO();
		Vector<FeatPrereq> prereq = fdb.getFeatPrereqs(id);
		for (int i = 0; i < prereq.size(); i++) {
			sb.append(((FeatPrereq) prereq.get(i)).exportFormat());
		}
		FeatEffectsDAO fedb = new FeatEffectsDAO();
		Vector<FeatEffects> effects = fedb.getFeatEffects(id);
		for (int i = 0; i < effects.size(); i++){
			sb.append(((FeatEffects) effects.get(i)).exportFormat());
		}
		sb.append("<Source>" + source + "</Source>\n");
		sb.append("</Feat>\n");
		return sb.toString();
	}

	public Feat(String s) {
		
		readImport(s);
	}
	
	public void readImport(String s){
		
		
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setInstanceId(t.getTagBody());
			}
			if (t.getTagName().equals("FeatName")) {
				setFeatName(t.getTagBody());
			}
			if (t.getTagName().equals("Description")) {
				setDescription(t.getTagBody());
			}
			if (t.getTagName().equals("Type")) {
				setType(t.getTagBody());
			}
			if (t.getTagName().equals("Type2")) {
				setType2(t.getTagBody());
			}
			if (t.getTagName().equals("Type3")) {
				setType3(t.getTagBody());
			}
			if (t.getTagName().equals("FeatType")) {
				setFeatType(t.getTagBody());
			}
			if (t.getTagName().equals("AttackBonus")) {
				setAttackBonus(t.getTagBody());
			}
			if (t.getTagName().equals("DamageBonus")) {
				setDamageBonus(t.getTagBody());
			}
			if (t.getTagName().equals("RangeLimit")) {
				setRangeLimit(t.getTagBody());
			}
			if (t.getTagName().equals("ShortText")) {
				setShortText(t.getTagBody());
			}
			if (t.getTagName().equals("HpFeat")) {
				setHpFeat(new Boolean(t.getTagBody()).booleanValue());
			}
			if (t.getTagName().equals("AcFeat")) {
				setAcFeat(new Boolean(t.getTagBody()).booleanValue());
			}
			if (t.getTagName().equals("StatFeat")) {
				setStatFeat(new Boolean(t.getTagBody()).booleanValue());
			}
			if (t.getTagName().equals("InitFeat")) {
				setInitFeat(new Boolean(t.getTagBody()).booleanValue());
			}
			if (t.getTagName().equals("SaveFeat")) {
				setSaveFeat(new Boolean(t.getTagBody()).booleanValue());
			}
			if (t.getTagName().equals("SkillFeat")) {
				setSkillFeat(new Boolean(t.getTagBody()).booleanValue());
			}
			if (t.getTagName().equals("TempBonus")) {
				setTempBonus(new Boolean(t.getTagBody()).booleanValue());
			}
			if (t.getTagName().equals("TempType")) {
				setTempType(t.getTagBody());
			}
			if (t.getTagName().equals("FeatId")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("FeatMod")) {
				setValue(t.getTagBody());
			}
			if (t.getTagName().equals("Override")) {
				setOverride(t.getTagBody());
			}
			if (t.getTagName().equals("Special")) {
				setSpecial(t.getTagBody());
			}
			if (t.getTagName().equals("Source")) {
				setSource(t.getTagBody());
			}
			if (t.getTagName().equals("MetamagicLevel")) {
				setMetamagicLevel(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("MetamagicPrefix")) {
				setMetamagicPrefix(t.getTagBody());
			}
			if (t.getTagName().equals("FeatPrereq")) {
				
				prerequisites.add(new FeatPrereq(t.getTagBody()));
			}
			if (t.getTagName().equals("FeatEffects")) {
				
				effects.add(new FeatEffects(t.getTagBody()));
			}
			s = s.substring(tagName.length() + tagName.length() + 5
					+ tagBody.length(), s.length());
			t = Tag.getTag(s);
		}
		
	}

	public String existsAs(){
		FeatDAO db = new FeatDAO();
		Feat f = db.getFeat(this.getId());
		if (f != null){
			return f.getName();
		}
		return null;
	}
	
	public Vector<Feat> getValues(){
		FeatDAO db = new FeatDAO();
		return db.getFeats();
	}
	
	public void save(boolean overwrite) {
		if (!overwrite) {
			setId(null);
		}
		FeatPrereqDAO fpdb = new FeatPrereqDAO();
		FeatDAO fdb = new FeatDAO();
		int newid = fdb.addOrUpdateFeat(this);
		
		// clear out and re-add the prereqs and effects; this is because
		// the feat ID will be different on the sending system than
		// in the core database, and the links need to be reestablished.
		fpdb.clearPrereqs("" + newid);
		for (int i = 0; i < prerequisites.size(); i++) {
			FeatPrereq fp = (FeatPrereq) prerequisites.get(i);
			fp.setFeatId("" + newid);
			fpdb.addOrUpdateFeatPrereq(fp);
		}
		FeatEffectsDAO fedb = new FeatEffectsDAO();
		fedb.clearEffects(""+newid);
		for (int i = 0; i < effects.size(); i++){
			FeatEffects fe = (FeatEffects) effects.get(i);
			fe.setFeatId(""+newid);
			fedb.addFeatEffects(fe);
		}
	}

	/**
	 * Get the TempType value.
	 * 
	 * @return the TempType value.
	 */
	public String getTempType() {
		return tempType;
	}

	/**
	 * Set the TempType value.
	 * 
	 * @param newTempType
	 *            The new TempType value.
	 */
	public void setTempType(String newTempType) {
		this.tempType = newTempType;
	}

	/**
	 * Get the TempBonus value.
	 * 
	 * @return the TempBonus value.
	 */
	public boolean isTempBonus() {
		return tempBonus;
	}

	/**
	 * Set the TempBonus value.
	 * 
	 * @param newTempBonus
	 *            The new TempBonus value.
	 */
	public void setTempBonus(boolean newTempBonus) {
		this.tempBonus = newTempBonus;
	}

	/**
	 * Get the SkillFeat value.
	 * 
	 * @return the SkillFeat value.
	 */
	public boolean isSkillFeat() {
		return skillFeat;
	}

	/**
	 * Set the SkillFeat value.
	 * 
	 * @param newSkillFeat
	 *            The new SkillFeat value.
	 */
	public void setSkillFeat(boolean newSkillFeat) {
		this.skillFeat = newSkillFeat;
	}

	/**
	 * Get the InitFeat value.
	 * 
	 * @return the InitFeat value.
	 */
	public boolean isInitFeat() {
		return initFeat;
	}

	/**
	 * Set the InitFeat value.
	 * 
	 * @param newInitFeat
	 *            The new InitFeat value.
	 */
	public void setInitFeat(boolean newInitFeat) {
		this.initFeat = newInitFeat;
	}

	/**
	 * Get the StatFeat value.
	 * 
	 * @return the StatFeat value.
	 */
	public boolean isStatFeat() {
		return statFeat;
	}

	/**
	 * Set the StatFeat value.
	 * 
	 * @param newStatFeat
	 *            The new StatFeat value.
	 */
	public void setStatFeat(boolean newStatFeat) {
		this.statFeat = newStatFeat;
	}

	/**
	 * Get the AcFeat value.
	 * 
	 * @return the AcFeat value.
	 */
	public boolean isAcFeat() {
		return acFeat;
	}

	/**
	 * Set the AcFeat value.
	 * 
	 * @param newAcFeat
	 *            The new AcFeat value.
	 */
	public void setAcFeat(boolean newAcFeat) {
		this.acFeat = newAcFeat;
	}

	/**
	 * Get the HpFeat value.
	 * 
	 * @return the HpFeat value.
	 */
	public boolean isHpFeat() {
		return hpFeat;
	}

	/**
	 * Set the HpFeat value.
	 * 
	 * @param newHpFeat
	 *            The new HpFeat value.
	 */
	public void setHpFeat(boolean newHpFeat) {
		this.hpFeat = newHpFeat;
	}

	/**
	 * Get the SaveFeat value.
	 * 
	 * @return the SaveFeat value.
	 */
	public boolean isSaveFeat() {
		return saveFeat;
	}

	/**
	 * Set the SaveFeat value.
	 * 
	 * @param newSaveFeat
	 *            The new SaveFeat value.
	 */
	public void setSaveFeat(boolean newSaveFeat) {
		this.saveFeat = newSaveFeat;
	}

	/**
	 * Get the ShortText value.
	 * 
	 * @return the ShortText value.
	 */
	public String getShortText() {
		return shortText;
	}

	/**
	 * Set the ShortText value.
	 * 
	 * @param newShortText
	 *            The new ShortText value.
	 */
	public void setShortText(String newShortText) {
		this.shortText = newShortText;
	}

	/**
	 * Get the Special value.
	 * 
	 * @return the Special value.
	 */
	public String getSpecial() {
		return special;
	}

	/**
	 * Set the Special value.
	 * 
	 * @param newSpecial
	 *            The new Special value.
	 */
	public void setSpecial(String newSpecial) {
		this.special = newSpecial;
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
	 * Get the Override value.
	 * 
	 * @return the Override value.
	 */
	public String getOverride() {
		return override;
	}

	/**
	 * Set the Override value.
	 * 
	 * @param newOverride
	 *            The new Override value.
	 */
	public void setOverride(String newOverride) {
		this.override = newOverride;
	}

	/**
	 * Get the RangeLimit value.
	 * 
	 * @return the RangeLimit value.
	 */
	public String getRangeLimit() {
		return rangeLimit;
	}

	/**
	 * Set the RangeLimit value.
	 * 
	 * @param newRangeLimit
	 *            The new RangeLimit value.
	 */
	public void setRangeLimit(String newRangeLimit) {
		this.rangeLimit = newRangeLimit;
	}

	/**
	 * Get the DamageBonus value.
	 * 
	 * @return the DamageBonus value.
	 */
	public String getDamageBonus() {
		return damageBonus;
	}

	/**
	 * Set the DamageBonus value.
	 * 
	 * @param newDamageBonus
	 *            The new DamageBonus value.
	 */
	public void setDamageBonus(String newDamageBonus) {
		this.damageBonus = newDamageBonus;
	}

	/**
	 * Get the AttackBonus value.
	 * 
	 * @return the AttackBonus value.
	 */
	public String getAttackBonus() {
		return attackBonus;
	}

	/**
	 * Set the AttackBonus value.
	 * 
	 * @param newAttackBonus
	 *            The new AttackBonus value.
	 */
	public void setAttackBonus(String newAttackBonus) {
		this.attackBonus = newAttackBonus;
	}

	/**
	 * Get the FeatType value.
	 * 
	 * @return the FeatType value.
	 */
	public String getFeatType() {
		return featType;
	}

	/**
	 * Set the FeatType value.
	 * 
	 * @param newFeatType
	 *            The new FeatType value.
	 */
	public void setFeatType(String newFeatType) {
		this.featType = newFeatType;
	}

	/**
	 * Get the Value value.
	 * 
	 * @return the Value value.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Set the Value value.
	 * 
	 * @param newValue
	 *            The new Value value.
	 */
	public void setValue(String newValue) {
		this.value = newValue;
	}

	public Feat() {

	}

	public String getSheetFormat() {
		StringBuffer format = new StringBuffer();
		if (special != null && special.equals("Y")) {
			format.append("SP: ");
		}
		format.append(featName);
		if (value != null && !value.equals("")) {
			format.append(" (" + value + ") ");
		}
		if (shortText != null && !shortText.equals("")) {
			format.append(" - " + shortText);
		}
		return format.toString();
	}

	public String toString() {
		String format = "";
		if (special != null && special.equals("Y")) {
			format += "SP: ";
		}
		format += featName;
		if (value != null && !value.equals("")) {
			format += "(" + value + ") ";
		}
		return format;
	}

	public String listFormat() {
		StringBuffer format = new StringBuffer();
		
		format.append(StrManip.pad(featName, 30));
		
		String typeStr = " [" + type;
		if (type2 != null && !type2.equals("")) {
			typeStr += ", " + type2;
		}
		if (type3 != null && !type2.equals("")) {
			typeStr += ", " + type3;
		}
		typeStr += "]";

		format.append(" "+StrManip.pad(typeStr, 20));
		format.append(" "+StrManip.pad(shortText, 40));
		format.append(" "+source);
		
		return format.toString();
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
	 * Get the value of featName
	 * 
	 * @return a <code>String</code> value
	 */
	public String getFeatName() {
		return featName;
	}

	/**
	 * Set the value of featName
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setFeatName(String s) {
		featName = s;
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
	 * Get the value of type2
	 * 
	 * @return a <code>String</code> value
	 */
	public String getType2() {
		return type2;
	}

	/**
	 * Set the value of type2
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setType2(String s) {
		type2 = s;
	}

	/**
	 * Get the value of type3
	 * 
	 * @return a <code>String</code> value
	 */
	public String getType3() {
		return type3;
	}

	/**
	 * Set the value of type3
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setType3(String s) {
		type3 = s;
	}

	public boolean isType(String s){
		return ( (type != null && type.equalsIgnoreCase(s))
				|| (type2 != null && type2.equalsIgnoreCase(s))
				|| (type3 != null && type3.equalsIgnoreCase(s)));
	}

	public int getMetamagicLevel() {
		return metamagicLevel;
	}

	public void setMetamagicLevel(int metamagicLevel) {
		this.metamagicLevel = metamagicLevel;
	}

	public String getMetamagicPrefix() {
		return metamagicPrefix;
	}

	public void setMetamagicPrefix(String metamagicPrefix) {
		this.metamagicPrefix = metamagicPrefix;
	}

	public Vector<FeatEffects> getEffects() {
		return effects;
	}

	public void setEffects(Vector<FeatEffects> effects) {
		this.effects = effects;
	}
	
}

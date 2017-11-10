package initcheck.database;

import initcheck.character.library.LibraryItem;
import initcheck.utils.StrManip;

import java.io.Serializable;
import java.util.Vector;

@SuppressWarnings("serial")
public class CharClass implements Serializable, Cloneable, Exportable,
		LibraryItem, Importable {

	private String id;

	private String charClass;

	private String description;

	private String hitdie;

	private String prestige;

	private String skillPoints;

	private String alternateAdvance;

	private boolean arcaneCaster;

	private boolean divineCaster;

	private boolean unpreparedCaster;

	private String turningLevelMod;
	
	private String source;
	
	private String rulesetId;
	
	Vector<ClassAdvancement> advancement = new Vector<ClassAdvancement>();

	Vector<CharClassPrereq> prereqs = new Vector<CharClassPrereq>();

	Vector<ClassProficiency> proficiencies = new Vector<ClassProficiency>();

	Vector<ClassSkills> skills = new Vector<ClassSkills>();

	Vector<SpellLevel> spellsKnown = new Vector<SpellLevel>();

	Vector<SpellLevel> spellsPerDay = new Vector<SpellLevel>();

	Vector<ClassAbilities> classAbilities = new Vector<ClassAbilities>();

	

	public String getRulesetId() {
		return rulesetId;
	}

	public void setRulesetId(String rulesetId) {
		this.rulesetId = rulesetId;
	}

	public void setName(String s){
		charClass = s;
	}
	
	public String getName(){
		return charClass;
	}
	
	public String listFormat(){
		return StrManip.pad(charClass, 30)+StrManip.pad(source, 20);
	}
	
	public String listValue(){
		return "Class : "+charClass;
	}
	
	/**
	 * Get the ClassAbilities value.
	 * 
	 * @return the ClassAbilities value.
	 */
	public Vector<ClassAbilities> getClassAbilities() {
		return classAbilities;
	}

	/**
	 * Set the ClassAbilities value.
	 * 
	 * @param newClassAbilities
	 *            The new ClassAbilities value.
	 */
	public void setClassAbilities(Vector<ClassAbilities> newClassAbilities) {
		this.classAbilities = newClassAbilities;
	}

	/**
	 * Get the SpellsPerDay value.
	 * 
	 * @return the SpellsPerDay value.
	 */
	public Vector<SpellLevel> getSpellsPerDay() {
		return spellsPerDay;
	}

	/**
	 * Set the SpellsPerDay value.
	 * 
	 * @param newSpellsPerDay
	 *            The new SpellsPerDay value.
	 */
	public void setSpellsPerDay(Vector<SpellLevel> newSpellsPerDay) {
		this.spellsPerDay = newSpellsPerDay;
	}

	/**
	 * Get the SpellsKnown value.
	 * 
	 * @return the SpellsKnown value.
	 */
	public Vector<SpellLevel> getSpellsKnown() {
		return spellsKnown;
	}

	/**
	 * Set the SpellsKnown value.
	 * 
	 * @param newSpellsKnown
	 *            The new SpellsKnown value.
	 */
	public void setSpellsKnown(Vector<SpellLevel> newSpellsKnown) {
		this.spellsKnown = newSpellsKnown;
	}

	public String getFullDescription() {
		return description;
	}

	/**
	 * Get the Skills value.
	 * 
	 * @return the Skills value.
	 */
	public Vector<ClassSkills> getSkills() {
		return skills;
	}

	/**
	 * Set the Skills value.
	 * 
	 * @param newSkills
	 *            The new Skills value.
	 */
	public void setSkills(Vector<ClassSkills> newSkills) {
		this.skills = newSkills;
	}

	/**
	 * Get the Proficiencies value.
	 * 
	 * @return the Proficiencies value.
	 */
	public Vector<ClassProficiency> getProficiencies() {
		return proficiencies;
	}

	/**
	 * Set the Proficiencies value.
	 * 
	 * @param newProficiencies
	 *            The new Proficiencies value.
	 */
	public void setProficiencies(Vector<ClassProficiency> newProficiencies) {
		this.proficiencies = newProficiencies;
	}

	/**
	 * Get the Prereqs value.
	 * 
	 * @return the Prereqs value.
	 */
	public Vector<CharClassPrereq> getPrereqs() {
		return prereqs;
	}

	/**
	 * Set the Prereqs value.
	 * 
	 * @param newPrereqs
	 *            The new Prereqs value.
	 */
	public void setPrereqs(Vector<CharClassPrereq> newPrereqs) {
		this.prereqs = newPrereqs;
	}

	/**
	 * Get the Advancement value.
	 * 
	 * @return the Advancement value.
	 */
	public Vector<ClassAdvancement> getAdvancement() {
		return advancement;
	}

	/**
	 * Set the Advancement value.
	 * 
	 * @param newAdvancement
	 *            The new Advancement value.
	 */
	public void setAdvancement(Vector<ClassAdvancement> newAdvancement) {
		this.advancement = newAdvancement;
	}

	
	
	public CharClass(String s) {
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
			if (t.getTagName().equals("CharClassName")) {
				setCharClass(t.getTagBody());
			}
			if (t.getTagName().equals("Description")) {
				setDescription(t.getTagBody());
			}
			if (t.getTagName().equals("Hitdie")) {
				setHitdie(t.getTagBody());
			}
			if (t.getTagName().equals("Prestige")) {
				setPrestige(t.getTagBody());
			}
			if (t.getTagName().equals("SkillPoints")) {
				setSkillPoints(t.getTagBody());
			}
			if (t.getTagName().equals("AlternateAdvance")) {
				setAlternateAdvance(t.getTagBody());
			}
			if (t.getTagName().equals("DivineCaster")) {
				setDivineCaster(t.getTagBody().equals("true"));
			}
			if (t.getTagName().equals("ArcaneCaster")) {
				setArcaneCaster(t.getTagBody().equals("true"));
			}
			if (t.getTagName().equals("UnpreparedCaster")) {
				setUnpreparedCaster(t.getTagBody().equals("true"));
			}
			if (t.getTagName().equals("ClassAdvancement")) {
				advancement.add(new ClassAdvancement(t.getTagBody()));
			}
			if (t.getTagName().equals("ClassSkills")) {
				skills.add(new ClassSkills(t.getTagBody()));
			}
			if (t.getTagName().equals("ClassProficiency")) {
				proficiencies.add(new ClassProficiency(t.getTagBody()));
			}
			if (t.getTagName().equals("ClassPrereq")) {
				prereqs.add(new CharClassPrereq(t.getTagBody()));
			}
			if (t.getTagName().equals("TurningLevelMod")) {
				setTurningLevelMod(t.getTagBody());
			}
			if (t.getTagName().equals("RulesetId")) {
				setRulesetId(t.getTagBody());
			}
			if (t.getTagName().equals("SpellLevel")) {
				SpellLevel sl = new SpellLevel(t.getTagBody());
				if (sl.isAlternate()) {
					spellsKnown.add(sl);
				} else {
					spellsPerDay.add(sl);
				}
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {

		StringBuffer sb = new StringBuffer();
		sb.append("<CharClass>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<CharClassName>" + charClass + "</CharClassName>\n");
		sb.append("<Description>" + description + "</Description>\n");
		sb.append("<Hitdie>" + hitdie + "</Hitdie>\n");
		sb.append("<Prestige>" + prestige + "</Prestige>\n");
		sb.append("<SkillPoints>" + skillPoints + "</SkillPoints>\n");
		sb.append("<AlternateAdvance>" + alternateAdvance
				+ "</AlternateAdvance>\n");
		sb.append("<DivineCaster>" + divineCaster + "</DivineCaster>\n");
		sb.append("<ArcaneCaster>" + arcaneCaster + "</ArcaneCaster>\n");
		sb.append("<UnpreparedCaster>" + unpreparedCaster
				+ "</UnpreparedCaster>\n");
		ClassAdvancementDAO cadb = new ClassAdvancementDAO();
		Vector<ClassAdvancement> adv = cadb.getClassAdvancement(id);
		for (int i = 0; i < adv.size(); i++) {
			sb.append(((ClassAdvancement) adv.get(i)).exportFormat());
		}
		Vector<ClassProficiency> prof = getProf();
		for (int i = 0; i < prof.size(); i++) {
			sb.append(((ClassProficiency) prof.get(i)).exportFormat());
		}
		ClassSkillsDAO csdb = new ClassSkillsDAO();
		Vector<ClassSkills> skills = csdb.getClassSkills(id);
		for (int i = 0; i < skills.size(); i++) {
			sb.append(((ClassSkills) skills.get(i)).exportFormat());
		}
		CharClassDAO ccdb = new CharClassDAO();
		Vector<CharClassPrereq> prereq = ccdb.getCharClassPrereqs(id);
		for (int i = 0; i < prereq.size(); i++) {
			sb.append(((CharClassPrereq) prereq.get(i)).exportFormat());
		}
		SpellLevelDAO sldb = new SpellLevelDAO();
		Vector<SpellLevel> known = sldb.getSpellsKnown(id);
		for (int i = 0; i < known.size(); i++) {
			sb.append(((SpellLevel) known.get(i)).exportFormat());
		}
		Vector<SpellLevel> perDay = sldb.getSpellsPerDay(id);
		for (int i = 0; i < perDay.size(); i++) {
			sb.append(((SpellLevel) perDay.get(i)).exportFormat());
		}
		sb.append("<TurningLevelMod>" + turningLevelMod + "</TurningLevelMod>\n");
		sb.append("<RulesetId>" + rulesetId + "</RulesetId>\n");
		sb.append("</CharClass>\n");
		return sb.toString();
	}

	public String existsAs(){
		CharClassDAO db = new CharClassDAO();
		Vector<CharClass> v = db.selectCharClass(this);
		if(v.size() > 0){
			return ((CharClass)v.get(0)).getName();
		}
		return null;
	}
	
	public Vector<CharClass> getValues(){
		CharClassDAO db = new CharClassDAO();
		return db.getClasses();
	}
	
	public void save(boolean overwrite) {
		if (!overwrite) {
			setId(null);
		}
		CharClassDAO ccdb = new CharClassDAO();
		int newid = ccdb.addOrUpdateCharClass(this);
		ccdb.clearPrereqs("" + newid);
		for (int i = 0; i < prereqs.size(); i++) {
			CharClassPrereq ccp = (CharClassPrereq) prereqs.get(i);
			ccp.setCharClassId("" + newid);
			ccp.setId(null);
			ccdb.addOrUpdateCharClassPrereq(ccp);
		}
		ClassSkillsDAO csdb = new ClassSkillsDAO();
		csdb.clearClassSkills("" + newid);
		for (int i = 0; i < skills.size(); i++) {
			ClassSkills cs = (ClassSkills) skills.get(i);
			cs.setClassId("" + newid);
			cs.setId(null);
			csdb.addClassSkills(cs);
		}
		ClassAdvancementDAO cadb = new ClassAdvancementDAO();
		cadb.clearClassAdvancement("" + newid);
		for (int i = 0; i < advancement.size(); i++) {
			ClassAdvancement ca = (ClassAdvancement) advancement.get(i);
			ca.setId(null);
			cadb.addClassAdvancement(ca);
		}
		ClassProficiencyDAO cpdb = new ClassProficiencyDAO();
		cpdb.clearClassProficiency("" + newid);
		for (int i = 0; i < proficiencies.size(); i++) {
			ClassProficiency cp = (ClassProficiency) proficiencies.get(i);
			cp.setId(null);
			cpdb.addClassProficiency(cp);
		}
		SpellLevelDAO sldb = new SpellLevelDAO();
		sldb.clearSpellLevels("" + newid);
		for (int i = 0; i < spellsKnown.size(); i++) {
			SpellLevel sl = (SpellLevel) spellsKnown.get(i);
			sl.setId(null);
			sldb.addSpellLevel(sl);
		}
		for (int i = 0; i < spellsPerDay.size(); i++) {
			SpellLevel sl = (SpellLevel) spellsPerDay.get(i);
			sl.setId(null);
			sldb.addSpellLevel(sl);
		}
	}

	public boolean hasProficiency(Weapon w) {
		if (proficiencies == null || proficiencies.size() == 0){
			proficiencies = getProf();
		}
		Vector<ClassProficiency> prof = proficiencies;
		for (int i = 0; i < prof.size(); i++) {
			ClassProficiency cp = (ClassProficiency) prof.get(i);
			if (cp.getProfSubtype().equals(w.getName())
					|| cp.getProfSubtype().equals(w.getFeatClass())
					|| cp.getProfSubtype().equalsIgnoreCase(
							"All " + w.getCategory())) {
				return true;
			}
		}
		return false;
	}

	public boolean hasProficiency(Armor s) {
		if (proficiencies == null || proficiencies.size() == 0){
			proficiencies = getProf();
		}
		Vector<ClassProficiency> prof = proficiencies;
		for (int i = 0; i < prof.size(); i++) {
			ClassProficiency cp = (ClassProficiency) prof.get(i);
			if (cp.getProfSubtype().equalsIgnoreCase(s.getName())
					|| cp.getProfSubtype().equals("All")
					|| cp.getProfSubtype().equalsIgnoreCase(
							"All " + s.getGrade())) {
				return true;
			}
		}
		return false;
	}

	public Vector<ClassProficiency> getProf() {
		ClassProficiencyDAO db = new ClassProficiencyDAO();
		return db.getClassProficiency(this);
	}

	public void setSkillPoints(String newSkillPoints) {
		this.skillPoints = newSkillPoints;
	}

	public CharClass() {

	}

	public String toString() {
		return charClass;
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
	 * Get the value of charClass
	 * 
	 * @return a <code>String</code> value
	 */
	public String getCharClass() {
		return charClass;
	}

	/**
	 * Set the value of charClass
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setCharClass(String s) {
		charClass = s;
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
	 * Get the value of hit die
	 * 
	 * @return a <code>String</code> value
	 */
	public String getHitdie() {
		return hitdie;
	}

	/**
	 * Set the value of hit die
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setHitdie(String s) {
		hitdie = s;
	}

	/**
	 * Get the value of prestige
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPrestige() {
		return prestige;
	}

	/**
	 * Set the value of prestige
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPrestige(String s) {
		prestige = s;
	}

	/**
	 * Get the UnpreparedCaster value.
	 * 
	 * @return the UnpreparedCaster value.
	 */
	public boolean isUnpreparedCaster() {
		return unpreparedCaster;
	}

	/**
	 * Set the UnpreparedCaster value.
	 * 
	 * @param newUnpreparedCaster
	 *            The new UnpreparedCaster value.
	 */
	public void setUnpreparedCaster(boolean newUnpreparedCaster) {
		this.unpreparedCaster = newUnpreparedCaster;
	}

	/**
	 * Get the DivineCaster value.
	 * 
	 * @return the DivineCaster value.
	 */
	public boolean isDivineCaster() {
		return divineCaster;
	}

	/**
	 * Set the DivineCaster value.
	 * 
	 * @param newDivineCaster
	 *            The new DivineCaster value.
	 */
	public void setDivineCaster(boolean newDivineCaster) {
		this.divineCaster = newDivineCaster;
	}

	/**
	 * Get the ArcaneCaster value.
	 * 
	 * @return the ArcaneCaster value.
	 */
	public boolean isArcaneCaster() {
		return arcaneCaster;
	}

	/**
	 * Set the ArcaneCaster value.
	 * 
	 * @param newArcaneCaster
	 *            The new ArcaneCaster value.
	 */
	public void setArcaneCaster(boolean newArcaneCaster) {
		this.arcaneCaster = newArcaneCaster;
	}

	/**
	 * Get the AlternateAdvance value.
	 * 
	 * @return the AlternateAdvance value.
	 */
	public String getAlternateAdvance() {
		return alternateAdvance;
	}

	/**
	 * Set the AlternateAdvance value.
	 * 
	 * @param newAlternateAdvance
	 *            The new AlternateAdvance value.
	 */
	public void setAlternateAdvance(String newAlternateAdvance) {
		this.alternateAdvance = newAlternateAdvance;
	}

	/**
	 * Get the SkillPoints value.
	 * 
	 * @return the SkillPoints value.
	 */
	public String getSkillPoints() {
		return skillPoints;
	}

	public String getTurningLevelMod() {
		return turningLevelMod;
	}

	public void setTurningLevelMod(String turningLevelMod) {
		this.turningLevelMod = turningLevelMod;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * Set the SkillPoints value.
	 * 
	 * @param newSkillPoints
	 *            The new SkillPoints value.
	 */
}

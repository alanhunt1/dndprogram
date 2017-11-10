package initcheck;

import java.io.Serializable;
import java.util.Vector;

import javax.swing.ImageIcon;

import initcheck.character.SkillSet;
import initcheck.database.InitDBC;
import initcheck.database.PlayerFeats;
import initcheck.graphics.TiledListItem;
import initcheck.status.StatusItem;
import initcheck.utils.StrManip;

public class Participant implements Serializable, Cloneable, Comparable, EncounterElement, TiledListItem {
	static final long serialVersionUID = 1;
	protected final static int DEFAULT = 0;

	// name
	protected int id = DEFAULT;
	protected String name = "";
	protected int initMod = DEFAULT;
	protected int initRoll = DEFAULT;

	// effects (stunned, etc)
	protected Status status;
	protected String dead = "FALSE";
	protected boolean hidden = false;

	// freeform notes
	protected String notes = "";

	// stats
	protected int strength = DEFAULT;
	protected int dexterity = DEFAULT;
	protected int wisdom = DEFAULT;
	protected int constitution = DEFAULT;
	protected int charisma = DEFAULT;
	protected int intelligence = DEFAULT;

	// temporary stat mods
	int strMod = DEFAULT;
	int dexMod = DEFAULT;
	int wisMod = DEFAULT;
	int conMod = DEFAULT;
	int chaMod = DEFAULT;
	int intMod = DEFAULT;

	// ac and saves
	protected int ac = DEFAULT;
	protected int fortSave = DEFAULT;
	protected int willSave = DEFAULT;
	protected int refSave = DEFAULT;
	protected int hitPoints = DEFAULT;
	protected Vector<PlayerFeats> featList = new Vector<PlayerFeats>();

	// protected Vector<PlayerSkills> skillList = new Vector<PlayerSkills>();

	protected SkillSet skillSet = new SkillSet();

	protected String ptype = "";

	protected String gender = "";

	protected String picture = "";

	protected String icon = "images/qmark.jpg";

	protected ImageIcon pictureObj;

	protected ImageIcon iconObj;

	public ImageIcon getPictureObj() {
		return pictureObj;
	}

	public void setPictureObj(ImageIcon pictureObj) {
		this.pictureObj = pictureObj;
	}

	public ImageIcon getIconObj() {
		return iconObj;
	}

	public void setIconObj(ImageIcon iconObj) {
		this.iconObj = iconObj;
	}

	protected String party = "";

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	String size;

	int currentHitPoints;

	int damageTaken;

	int roundDamage;

	double level = 0;

	int group = 0;

	String stunPercentage = "20";

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getType() {
		return getPType();
	}

	public String getStunPercentage() {
		return stunPercentage;
	}

	public void setStunPercentage(String stunPercentage) {
		this.stunPercentage = stunPercentage;
	}

	/**
	 * Get the RoundDamage value.
	 * 
	 * @return the RoundDamage value.
	 */
	public int getRoundDamage() {
		return roundDamage;
	}

	/**
	 * Set the RoundDamage value.
	 * 
	 * @param newRoundDamage
	 *            The new RoundDamage value.
	 */
	public void setRoundDamage(int newRoundDamage) {
		this.roundDamage = newRoundDamage;
	}

	/**
	 * Get the DamageTaken value.
	 * 
	 * @return the DamageTaken value.
	 */
	public int getDamageTaken() {
		return damageTaken;
	}

	/**
	 * Set the DamageTaken value.
	 * 
	 * @param newDamageTaken
	 *            The new DamageTaken value.
	 */
	public void setDamageTaken(int newDamageTaken) {
		this.damageTaken = newDamageTaken;
	}

	/**
	 * Get the CurrentHitPoints value.
	 * 
	 * @return the CurrentHitPoints value.
	 */
	public int getCurrentHitPoints() {
		return currentHitPoints;
	}

	/**
	 * Set the CurrentHitPoints value.
	 * 
	 * @param newCurrentHitPoints
	 *            The new CurrentHitPoints value.
	 */
	public void setCurrentHitPoints(int newCurrentHitPoints) {
		this.currentHitPoints = newCurrentHitPoints;
	}

	public String getDamageListFormat() {
		return StrManip.pad("TTL:" + damageTaken, 8) + StrManip.pad("RND:" + roundDamage, 8)
				+ StrManip.pad("HP:" + getPrecalcHP(), 7);
	}

	/**
	 * Get the Size value.
	 * 
	 * @return the Size value.
	 */
	public String getSize() {
		return size;
	}

	/**
	 * Set the Size value.
	 * 
	 * @param newSize
	 *            The new Size value.
	 */
	public void setSize(String newSize) {
		this.size = newSize;
	}

	public int getBonus(int stat) {
		int bonus = (stat / 2) - 5;

		return bonus;
	}

	public int getStat(String s) {
		if (s.equals("INTELLIGENCE")) {
			return intelligence;
		} else if (s.equals("DEXTERITY")) {
			return dexterity;
		} else if (s.equals("WISDOM")) {
			return wisdom;
		} else if (s.equals("STRENGTH")) {
			return strength;
		} else if (s.equals("CHARISMA")) {
			return charisma;
		} else if (s.equals("CONSTITUTION")) {
			return constitution;
		}
		return 0;
	}

	public int getTouchAc() {
		InitDBC db = new InitDBC();
		// to calculate touch ac, add all non-armor bonuses
		int ac = 10; // base
		ac += getBonus(dexterity); // add dex mod

		ac += db.getSizeMod(size);
		return ac;
	}

	/**
	 * Get the Gender value.
	 * 
	 * @return the Gender value.
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Set the Gender value.
	 * 
	 * @param newGender
	 *            The new Gender value.
	 */
	public void setGender(String newGender) {
		this.gender = newGender;
	}

	/**
	 * Get the IntBonus value.
	 * 
	 * @return the IntBonus value.
	 */
	public int getIntBonus() {
		return getBonus(intelligence);
	}

	/**
	 * Get the ChaBonus value.
	 * 
	 * @return the ChaBonus value.
	 */
	public int getChaBonus() {
		return getBonus(charisma);
	}

	/**
	 * Get the ConBonus value.
	 * 
	 * @return the ConBonus value.
	 */
	public int getConBonus() {
		return getBonus(constitution);
	}

	/**
	 * Get the WisBonus value.
	 * 
	 * @return the WisBonus value.
	 */
	public int getWisBonus() {
		return getBonus(wisdom);
	}

	/**
	 * Get the DexBonus value.
	 * 
	 * @return the DexBonus value.
	 */
	public int getDexBonus() {
		return getBonus(dexterity);
	}

	/**
	 * Get the StrBonus value.
	 * 
	 * @return the StrBonus value.
	 */
	public int getStrBonus() {
		return getBonus(strength);
	}

	public Participant() {
		status = new Status();
	}

	public void setInit(int i) {
		initRoll = i;
	}

	/*
	 * /** Get the SkillList value.
	 * 
	 * @return the SkillList value.
	 * 
	 * public Vector<PlayerSkills> getSkillList() { return skillList; }
	 * 
	 * 
	 * Set the SkillList value.
	 * 
	 * @param newSkillList The new SkillList value.
	 * 
	 * public void setSkillList(Vector<PlayerSkills> newSkillList) {
	 * this.skillList = newSkillList; }
	 */
	/**
	 * Get the FeatList value.
	 * 
	 * @return the FeatList value.
	 */
	public Vector<PlayerFeats> getFeatList() {
		return featList;
	}

	/**
	 * Set the FeatList value.
	 * 
	 * @param newFeatList
	 *            The new FeatList value.
	 */
	public void setFeatList(Vector<PlayerFeats> newFeatList) {
		this.featList = newFeatList;
	}

	public String extendedFormat() {
		String initStr = "" + initMod;
		if (initStr.length() < 2) {
			initStr += " ";
		}

		String outStr = getNamePad() + " " + initStr + " " + ac;

		for (int i = 0; i < status.getStatSize(); i++) {
			StatusItem si = status.getStatusItem(i);

			outStr += " " + si.getName();

			if (si.isTimed()) {
				outStr += " (" + si.getStatusMod() + ")";
			}
		}

		if (notes != null) {
			outStr += " " + notes;
		}
		return outStr;
	}

	public String serverNotesFormat() {
		String outStr = "";
		// String outStr = getNamePad();
		// outStr += StrManip.pad(getCurrentHitPoints()+"/"+getPrecalcHP(), 8);
		// outStr += getPrecalcAc()+" ";

		for (int i = 0; i < status.getStatSize(); i++) {
			StatusItem si = status.getStatusItem(i);

			outStr += " " + si.getName();

			if (si.isTimed()) {
				outStr += " (" + si.getStatusMod() + ")";
			}
		}

		if (notes != null) {
			outStr += " " + notes;
		}
		return outStr;
	}

	public String serverListFormat() {

		String outStr = getNamePad();
		outStr += StrManip.pad(getCurrentHitPoints() + "/" + getPrecalcHP(), 8);
		outStr += getPrecalcAc() + " ";

		for (int i = 0; i < status.getStatSize(); i++) {
			StatusItem si = status.getStatusItem(i);

			outStr += " " + si.getName();

			if (si.isTimed()) {
				outStr += " (" + si.getStatusMod() + ")";
			}
		}

		if (notes != null) {
			outStr += " " + notes;
		}
		return outStr;
	}

	public String toString() {
		String outStr = getNamePad();

		for (int i = 0; i < status.getStatSize(); i++) {
			StatusItem si = status.getStatusItem(i);

			outStr += " " + si.getName();

			if (si.isTimed()) {
				outStr += " (" + si.getStatusMod() + ")";
			}
		}

		if (notes != null) {
			outStr += " " + notes;
		}
		return outStr;
	}

	public boolean isDead() {
		return dead.equals("TRUE");
	}

	public void setDead(String d) {
		dead = d;
	}

	public String getPType() {
		return ptype;
	}

	public String getNotes() {
		return notes;
	}

	public int getInit() {
		return initRoll;
	}

	public int getMod() {
		return initMod;
	}

	public String getName() {
		return name;
	}

	public Status getStatus() {
		return status;
	}

	public int getStrength() {
		return strength;
	}

	public int getWisdom() {
		return wisdom;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public int getConstitution() {
		return constitution;
	}

	public int getCharisma() {
		return charisma;
	}

	public int getDexterity() {
		return dexterity;
	}

	public int getRefSave() {
		return refSave;
	}

	public int getWillSave() {
		return willSave;
	}

	public int getFortSave() {
		return fortSave;
	}

	public int getAC() {
		return ac;
	}

	public int getPrecalcAc() {
		return ac;
	}

	public int getHP() {

		return hitPoints;
	}

	public int getPrecalcHP() {
		return hitPoints;
	}

	public int getID() {
		return id;
	}

	public void addStatusItem(StatusItem i) throws StatusException {
		status.addStatusItem(i);
	}

	public void removeStatusItem(String name) {
		status.removeStatusItem(name);
	}

	public void modifyStatusItem(String name, int mod) {
		status.modifyStatusItem(name, mod);
	}

	public void incrementStatusItem(String name) {
		status.incrementStatusItem(name);
	}

	public void decrementStatusItem(String name) {
		status.decrementStatusItem(name);
	}

	public boolean advanceStatus() {
		return status.advanceStatus();
	}

	public void resetStatus() {
		status = new Status();
	}

	public String getStatusText() {
		return status.getStatusText();
	}

	public void setPType(String s) {
		ptype = s;
	}

	public void setNotes(String s) {
		notes = s;
	}

	public void setStrength(int i) {
		strength = i;
	}

	public void setWisdom(int i) {
		wisdom = i;
	}

	public void setIntelligence(int i) {
		intelligence = i;
	}

	public void setConstitution(int i) {
		constitution = i;
	}

	public void setCharisma(int i) {
		charisma = i;
	}

	public void setDexterity(int i) {
		dexterity = i;
	}

	public void setRefSave(int i) {
		refSave = i;
	}

	public void setWillSave(int i) {
		willSave = i;
	}

	public void setFortSave(int i) {
		fortSave = i;
	}

	public void setAC(int i) {
		ac = i;
	}

	public void setHP(int i) {

		hitPoints = i;
	}

	public void setID(int i) {
		id = i;
	}

	public void setMod(int i) {
		initMod = i;
	}

	public String getNamePad() {
		String outStr = name;
		for (int i = name.length(); i < 25; i++) {
			outStr += " ";
		}
		return outStr;
	}

	public int getChaMod() {
		return chaMod;
	}

	public void setChaMod(int chaMod) {
		this.chaMod = chaMod;
	}

	public int getConMod() {
		return conMod;
	}

	public void setConMod(int conMod) {
		this.conMod = conMod;
	}

	public int getDexMod() {
		return dexMod;
	}

	public void setDexMod(int dexMod) {
		this.dexMod = dexMod;
	}

	public int getIntMod() {
		return intMod;
	}

	public void setIntMod(int intMod) {
		this.intMod = intMod;
	}

	public int getStrMod() {
		return strMod;
	}

	public void setStrMod(int strMod) {
		this.strMod = strMod;
	}

	public int getWisMod() {
		return wisMod;
	}

	public void setWisMod(int wisMod) {
		this.wisMod = wisMod;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public SkillSet getSkillSet() {
		return skillSet;
	}

	public void setSkillSet(SkillSet skillSet) {
		this.skillSet = skillSet;
	}

	public double getLevel() {
		return level;
	}

	public void setLevel(double level) {
		this.level = level;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public int compareTo(Object arg0) {

		return 0 - (((Participant) arg0).getName()).compareTo(name);
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

}

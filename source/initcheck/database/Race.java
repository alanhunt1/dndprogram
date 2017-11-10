package initcheck.database;

import initcheck.character.library.LibraryItem;
import initcheck.utils.StrManip;

import java.io.Serializable;
import java.util.Vector;

public class Race implements Serializable, Cloneable, Exportable, LibraryItem, Importable {

	
	private static final long serialVersionUID = 1L;

	private String id;

	private String race;

	private String description;

	private String size;

	private String mod1;

	private String mod2;

	private String mod3;

	private String mod4;

	private String basespeed;

	private String favoredclass;

	private String source;
	
	private String levelAdjustment;
	
	Vector<RacialAbility> abilities = new Vector<RacialAbility>();

	public String listValue(){
		return "Race : "+race;
	}
	
	public String listFormat(){
		return StrManip.pad(race, 30)+StrManip.pad(source, 20);
	}
	
	public String getName(){
		return race;
	}
	
	public void setName(String s){
		race = s;
	}
	
	public String getFullDescription() {
		return description;
	}

	/**
	 * Get the Abilities value.
	 * 
	 * @return the Abilities value.
	 */
	public Vector<RacialAbility> getAbilities() {
		return abilities;
	}

	/**
	 * Set the Abilities value.
	 * 
	 * @param newAbilities
	 *            The new Abilities value.
	 */
	public void setAbilities(Vector<RacialAbility> newAbilities) {
		this.abilities = newAbilities;
	}

	public Race() {

	}

	public Race(String s) {
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
			if (t.getTagName().equals("RaceName")) {
				setRace(t.getTagBody());
			}
			if (t.getTagName().equals("Description")) {
				setDescription(t.getTagBody());
			}
			if (t.getTagName().equals("Size")) {
				setSize(t.getTagBody());
			}
			if (t.getTagName().equals("Basespeed")) {
				setBasespeed(t.getTagBody());
			}
			if (t.getTagName().equals("Favoredclass")) {
				setFavoredclass(t.getTagBody());
			}
			if (t.getTagName().equals("Source")) {
				setSource(t.getTagBody());
			}
			if (t.getTagName().equals("RacialAbility")) {
				abilities.add(new RacialAbility(t.getTagBody()));
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Race>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<RaceName>" + race + "</RaceName>\n");
		sb.append("<Description>" + description + "</Description>\n");
		sb.append("<Size>" + size + "</Size>\n");
		sb.append("<Basespeed>" + basespeed + "</Basespeed>\n");
		sb.append("<Favoredclass>" + favoredclass + "</Favoredclass>\n");
		sb.append("<Source>" + source + "</Source>\n");
		RacialAbilityDAO radb = new RacialAbilityDAO();
		Vector<RacialAbility> abil = radb.getRacialAbilities(race);
		for (int i = 0; i < abil.size(); i++) {
			sb.append(((RacialAbility) abil.get(i)).exportFormat());
		}
		sb.append("</Race>\n");
		return sb.toString();
	}

	public String existsAs(){
		RaceDAO db = new RaceDAO();
		Vector<Race> v = db.selectRace(this);
		if(v.size() > 0){
			return ((Race)v.get(0)).getName();
		}
		return null;
	}
	
	public Vector<Race> getValues(){
		RaceDAO db = new RaceDAO();
		return db.getRaces();
	}
	
	public void save(boolean overwrite) {
		if (!overwrite) {
			setId(null);
		}
		RacialAbilityDAO radb = new RacialAbilityDAO();
		RaceDAO rdb = new RaceDAO();
		int newid = rdb.addOrUpdateRace(this);
		radb.clearRacialAbilities("" + newid);
		for (int i = 0; i < abilities.size(); i++) {
			RacialAbility ra = (RacialAbility) abilities.get(i);
			ra.setRaceId("" + newid);
			radb.addRacialAbility(ra);
		}
	}

	public boolean hasProficiency(Weapon w) {
		Vector<RacialAbility> prof = getProf();
		for (int i = 0; i < prof.size(); i++) {
			RacialAbility ra = (RacialAbility) prof.get(i);
			if (ra.getAbilityName().equals("PROFICIENCY")) {
				if (ra.getAbilityType().equals("WEAPON")) {
					if (ra.getAbilityType2().equals(w.getName())
							|| ra.getAbilityType2().equals(w.getFeatClass())
							|| ra.getAbilityType2().equalsIgnoreCase(
									"All " + w.getCategory())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean hasProficiency(Armor s) {
		Vector<RacialAbility> prof = getProf();
		for (int i = 0; i < prof.size(); i++) {
			RacialAbility ra = (RacialAbility) prof.get(i);
			if (ra.getAbilityName().equals("PROFICIENCY")) {
				if (ra.getAbilityType().equals("ARMOR")) {
					if (ra.getAbilityType2().equals(s.getName())
							|| ra.getAbilityType2().equals("All")
							|| ra.getAbilityType2().equalsIgnoreCase(
									"All " + s.getGrade())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public Vector<RacialAbility> getProf() {
		RacialAbilityDAO radb = new RacialAbilityDAO();
		return radb.getRacialAbilities(this.race);
	}

	public String toString() {
		return race;
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
	 * Get the value of race
	 * 
	 * @return a <code>String</code> value
	 */
	public String getRace() {
		return race;
	}

	/**
	 * Set the value of race
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setRace(String s) {
		race = s;
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
	 * Get the value of mod1
	 * 
	 * @return a <code>String</code> value
	 */
	public String getMod1() {
		return mod1;
	}

	/**
	 * Set the value of mod1
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setMod1(String s) {
		mod1 = s;
	}

	/**
	 * Get the value of mod2
	 * 
	 * @return a <code>String</code> value
	 */
	public String getMod2() {
		return mod2;
	}

	/**
	 * Set the value of mod2
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setMod2(String s) {
		mod2 = s;
	}

	/**
	 * Get the value of mod3
	 * 
	 * @return a <code>String</code> value
	 */
	public String getMod3() {
		return mod3;
	}

	/**
	 * Set the value of mod3
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setMod3(String s) {
		mod3 = s;
	}

	/**
	 * Get the value of mod4
	 * 
	 * @return a <code>String</code> value
	 */
	public String getMod4() {
		return mod4;
	}

	/**
	 * Set the value of mod4
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setMod4(String s) {
		mod4 = s;
	}

	/**
	 * Get the value of basespeed
	 * 
	 * @return a <code>String</code> value
	 */
	public String getBasespeed() {
		return basespeed;
	}

	/**
	 * Set the value of basespeed
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setBasespeed(String s) {
		basespeed = s;
	}

	/**
	 * Get the value of favoredclass
	 * 
	 * @return a <code>String</code> value
	 */
	public String getFavoredclass() {
		return favoredclass;
	}

	/**
	 * Set the value of favoredclass
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setFavoredclass(String s) {
		favoredclass = s;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getLevelAdjustment() {
		return levelAdjustment;
	}

	public void setLevelAdjustment(String levelAdjustment) {
		this.levelAdjustment = levelAdjustment;
	}

}

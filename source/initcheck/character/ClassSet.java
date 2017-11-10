package initcheck.character;

import initcheck.database.Armor;
import initcheck.database.CharClass;
import initcheck.database.CharClassDAO;
import initcheck.database.ClassAbilities;
import initcheck.database.ClassAdvancement;
import initcheck.database.ClassProficiency;
import initcheck.database.ClassSkillsDAO;
import initcheck.database.Skill;
import initcheck.database.Weapon;

import java.io.Serializable;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

public class ClassSet implements Serializable {

	private static final long serialVersionUID = 1L;

	private Vector<ClassSlot> classVector = new Vector<ClassSlot>();

	public ClassSet() {

	}

	public boolean isClassSkill(Skill s) {
		boolean b = false;
		ClassSkillsDAO csdb = new ClassSkillsDAO();

		for (int i = 0; i < classVector.size(); i++) {
			CharClass cc = classVector.get(i).getClassName();
			String id = s.getId();
			if (cc.getCharClass().startsWith("Craft")) {
				id = "9";
			}
			if (cc.getCharClass().startsWith("Profession")) {
				id = "30";
			}
			if (csdb.isClassSkill(cc.getId(), id)) {
				b = true;
				break;
			}
		}
		return b;
	}

	public boolean hasClass(String s) {
		for (int i = 0; i < classVector.size(); i++) {
			CharClass cc = classVector.get(i).getClassName();
			if (cc.getCharClass().equals(s)) {
				return true;
			}
		}
		return false;
	}

	public int getClassLevel(String s) {
		for (int i = 0; i < classVector.size(); i++) {
			ClassSlot cs = (ClassSlot) classVector.get(i);
			CharClass cc = cs.getClassName();
			if (cc.getCharClass().equals(s) || cc.getId().equals(s)) {
				return cs.getLevel();
			}
		}
		return 0;
	}

	/**
	 * Get the Level value.
	 * 
	 * @return the Level value.
	 */
	public int getLevel() {
		int level = 0;
		for (int i = 0; i < classVector.size(); i++) {
			ClassSlot cs = (ClassSlot) classVector.get(i);
			level += cs.getLevel();
		}
		return level;
	}

	/**
	 * Get the BaseAttackBonus value.
	 * 
	 * @return the BaseAttackBonus value.
	 */
	public String getBaseAttackBonus(int mod) {
		int attack1 = 0;
		int attack2 = 0;
		int attack3 = 0;
		int attack4 = 0;
		int attack5 = 0;

		for (int i = 0; i < classVector.size(); i++) {
			ClassSlot cs = (ClassSlot) classVector.get(i);
			CharClassDAO db = new CharClassDAO();
			ClassAdvancement ca = db.getAdvancement(cs.getClassName(), cs
					.getLevel());
			String bab = ca.getBaseAttack();
			StringTokenizer split = new StringTokenizer(bab, "/");
			String token = "";
			if (split.hasMoreTokens()) {
				token = split.nextToken();
				if (token.startsWith("+")) {
					token = token.substring(1, token.length());
				}
				attack1 += Integer.parseInt(token);
			}
			if (split.hasMoreTokens()) {
				token = split.nextToken();
				if (token.startsWith("+")) {
					token = token.substring(1, token.length());
				}
				attack2 += Integer.parseInt(token);
			}
			if (split.hasMoreTokens()) {
				token = split.nextToken();
				if (token.startsWith("+")) {
					token = token.substring(1, token.length());
				}
				attack3 += Integer.parseInt(token);
			}
			if (split.hasMoreTokens()) {
				token = split.nextToken();
				if (token.startsWith("+")) {
					token = token.substring(1, token.length());
				}
				attack4 += Integer.parseInt(token);
			}
			if (split.hasMoreTokens()) {
				token = split.nextToken();
				if (token.startsWith("+")) {
					token = token.substring(1, token.length());
				}
				attack5 += Integer.parseInt(token);
			}
		}

		// if the combination of classes leads to a BAB that will allow
		// an extra attack, even if the individual classes do not, then 
		// add that extra attack.  This caps out at 4 attacks.
		if (classVector.size() > 1) {
			if (attack1 - 5 > 0) {
				attack2 = attack1 - 5;
			}
			if (attack2 - 5 > 0) {
				attack3 = attack2 - 5;
			}
			if (attack3 - 5 > 0) {
				attack4 = attack3 - 5;
			}	
		}

		String babStr = "+" + (attack1 + mod);
		if (attack2 > 0) {
			babStr += "/+" + (attack2 + mod);
		}
		if (attack3 > 0) {
			babStr += "/+" + (attack3 + mod);
		}
		if (attack4 > 0) {
			babStr += "/+" + (attack4 + mod);
		}
		if (attack5 > 0) {
			babStr += "/+" + (attack5 + mod);
		}

		return babStr;
	}

	public int getClassSkillsRemaining(String classId, int bonus, boolean rogueSkills) {
		int level = 0;
		int skills = 0;
	
		for (int i = 0; i < classVector.size(); i++) {

			// get the class related bonus skills
			ClassSlot cs = (ClassSlot) classVector.get(i);
			if (classId == null || cs.getClassName().getId().equals(classId)) {
				level += cs.getLevel();
				CharClass cc = cs.getClassName();
				int sp = Integer.parseInt(cc.getSkillPoints());
				if (rogueSkills){
					sp = 8;
				}
				

				if (i == 0) {
					int firstLevel = (4 * (sp + bonus));
					if (firstLevel < 4) {
						firstLevel = 4;
					}

					int otherLevels = ((cs.getLevel() - 1) * (sp + bonus));
					if (otherLevels < cs.getLevel() - 1) {
						otherLevels = cs.getLevel() - 1;
					}
					skills += firstLevel + otherLevels;
				} else {
					int otherLevels = ((cs.getLevel()) * (sp + bonus));
					if (otherLevels < cs.getLevel()) {
						otherLevels = cs.getLevel();
					}
					skills += otherLevels;

				}
				// add in any mods from the class slot
				skills += (cs.getSkillRankMod()*cs.getLevel());
			}
		}
		return skills;
	}

	public int getBonusFeats() {
		int level = 0;
		int feats = 0;
		for (int i = 0; i < classVector.size(); i++) {

			// get the class related bonus feats
			ClassSlot cs = (ClassSlot) classVector.get(i);
			level += cs.getLevel();

			CharClassDAO db = new CharClassDAO();
			for (int j = 1; j <= cs.getLevel(); j++) {
				ClassAdvancement ca = db.getAdvancement(cs.getClassName(), j);
				if (ca.getSpecial() != null
						&& (ca.getSpecial().indexOf("Bonus Feat") >= 0
								|| ca.getSpecial().indexOf("Bonus feat") >= 0 || ca
								.getSpecial().indexOf("Bonus metamagic feat") >= 0)) {
					feats++;
				}
			}
		}
		return feats;
	}

	public int getUncannyDodge() {

		int feats = 0;

		for (int i = 0; i < classVector.size(); i++) {

			// get the class related bonus feats
			ClassSlot cs = (ClassSlot) classVector.get(i);

			CharClassDAO db = new CharClassDAO();
			for (int j = 1; j <= cs.getLevel(); j++) {
				ClassAdvancement ca = db.getAdvancement(cs.getClassName(), j);
				if (ca.getSpecial() != null
						&& (ca.getSpecial().indexOf("Uncanny Dodge") >= 0
								|| ca.getSpecial().indexOf("Uncanny dodge") >= 0 || ca
								.getSpecial().indexOf("uncanny dodge") >= 0)) {
					feats++;
				}
			}
		}
		return feats;
	}
	
	public int getFavoredEnemies() {

		int feats = 0;

		for (int i = 0; i < classVector.size(); i++) {

			// get the class related bonus feats
			ClassSlot cs = (ClassSlot) classVector.get(i);

			CharClassDAO db = new CharClassDAO();
			for (int j = 1; j <= cs.getLevel(); j++) {
				ClassAdvancement ca = db.getAdvancement(cs.getClassName(), j);
				if (ca.getSpecial() != null
						&& (ca.getSpecial().indexOf("Favored Enemy") >= 0
								|| ca.getSpecial().indexOf("favored enemy") >= 0 || ca
								.getSpecial().indexOf("Favored enemy") >= 0)) {
					feats++;
				}
			}
		}
		return feats;
	}
	
	public int getAcBonus(){
		int feats = 0;
		for (int i = 0; i < classVector.size(); i++) {

			// get the class related bonus feats
			ClassSlot cs = (ClassSlot) classVector.get(i);

			CharClassDAO db = new CharClassDAO();
			for (int j = 1; j <= cs.getLevel(); j++) {
				ClassAdvancement ca = db.getAdvancement(cs.getClassName(), j);
				if (ca.getSpecial() != null
						&& (ca.getSpecial().indexOf("AC +1") >= 0
								|| ca.getSpecial().indexOf("ac +1") >= 0 || ca
								.getSpecial().indexOf("Ac +1") >= 0)) {
					feats++;
				}
			}
		}
		return feats;
	}
	
	public boolean hasAbility(String s){
		Vector<ClassAbilities> abilities = getAbilities();
		for (int i = 0; i < abilities.size(); i++){
			ClassAbilities ca = abilities.get(i);
			
			if (ca.getName().equalsIgnoreCase(s)){
				return true;
			}
		}
		return false;
	}

	public Vector<ClassAbilities> getAbilities() {
		Vector<ClassAbilities> abilities = new Vector<ClassAbilities>();

		for (int i = 0; i < classVector.size(); i++) {
			ClassSlot cs = (ClassSlot) classVector.get(i);
			CharClass cc = cs.getClassName();
		
			abilities.addAll(cc.getClassAbilities());
		}
		return abilities;
	}

	public ClassAdvancement getMonkAdvancement() {
		ClassAdvancement ca = null;
		CharClassDAO db = new CharClassDAO();
		for (int i = 0; i < classVector.size(); i++) {
			ClassSlot cs = (ClassSlot) classVector.get(i);
			CharClass cc = (cs.getClassName());
			if (cc.getCharClass().startsWith("Monk")) {

				ca = db.getAlternateAdvancement(cc, cs.getLevel());
			}
		}
		return ca;
	}

	public Vector<String> getClassAbilities(int bonus) {
		Vector<String> abilities = new Vector<String>();

		for (int i = 0; i < classVector.size(); i++) {

			// get the class related bonus feats
			ClassSlot cs = (ClassSlot) classVector.get(i);
			CharClass cc = cs.getClassName();
			CharClassDAO db = new CharClassDAO();
			int skillPoints = Integer.parseInt(cc.getSkillPoints());
			skillPoints += bonus;

			abilities
					.add("CLASS" + cc.getCharClass() + "(" + cc.getHitdie()
							+ " HP/Level)" + " (" + skillPoints
							+ " Skill Ranks/Level)");

			for (int j = 1; j <= cs.getLevel(); j++) {
				ClassAdvancement ca = db.getAdvancement(cs.getClassName(), j);
				String special = ca.getSpecial();
				StringTokenizer split = new StringTokenizer(special, ",");

				while (split.hasMoreTokens()) {
					String token = split.nextToken();
					abilities.add(j + ": " + token.trim());
				}
			}
		}
		return abilities;
	}

	public boolean hasProficiency(Weapon w) {
		boolean prof = false;

		// check class proficiencies
		for (int i = 0; i < classVector.size(); i++) {
			ClassSlot cs = (ClassSlot) classVector.get(i);
			CharClass cc = cs.getClassName();
			if (cc.hasProficiency(w)) {
				prof = true;
			}
		}
		return prof;
	}

	public boolean hasProficiency(Armor a) {
		boolean prof = false;

		// check class proficiencies
		for (int i = 0; i < classVector.size(); i++) {
			ClassSlot cs = (ClassSlot) classVector.get(i);
			CharClass cc = cs.getClassName();
			if (cc.hasProficiency(a)) {
				prof = true;
			}
		}
		return prof;
	}

	public Vector<ClassProficiency> getArmorProficiencies() {
		Vector<ClassProficiency> v = new Vector<ClassProficiency>();
		HashMap<String, String> profHash = new HashMap<String, String>();
		for (int i = 0; i < classVector.size(); i++) {
			ClassSlot cs = (ClassSlot) classVector.get(i);
			CharClass cc = cs.getClassName();
			Vector<ClassProficiency> prof = cc.getProf();
			for (int j = 0; j < prof.size(); j++) {
				ClassProficiency p = (ClassProficiency) prof.get(j);
				if (p.getProfType().equals("ARMOR")
						|| p.getProfType().equals("SHIELD")) {
					if (profHash.get(p.getProfType() + p.getProfSubtype()) == null) {
						profHash.put(p.getProfType() + p.getProfSubtype(), "");
						v.add(p);
					}

				}
			}
		}
		return v;
	}

	public Vector<ClassProficiency> getWeaponProficiencies() {
		Vector<ClassProficiency> v = new Vector<ClassProficiency>();
		HashMap<String, String> profHash = new HashMap<String, String>();
		for (int i = 0; i < classVector.size(); i++) {
			ClassSlot cs = classVector.get(i);
			CharClass cc = cs.getClassName();
			Vector<ClassProficiency> prof = cc.getProf();
			for (int j = 0; j < prof.size(); j++) {
				ClassProficiency p = (ClassProficiency) prof.get(j);
				if (p.getProfType().equals("WEAPON")) {
					if (profHash.get(p.getProfType() + p.getProfSubtype()) == null) {
						profHash.put(p.getProfType() + p.getProfSubtype(), "");
						v.add(p);
					}
				}
			}
		}
		return v;
	}

	public double getMaxRanks(Skill s) {
		ClassSkillsDAO csdb = new ClassSkillsDAO();
		double maxRanks = 0;
		boolean crossClass = true;

		for (int i = 0; i < classVector.size(); i++) {
			ClassSlot cs = classVector.get(i);
			CharClass cc = cs.getClassName();
			if (s.isAvailableTo(cc.getId())) {
				String id = s.getId();
				if (cc.getCharClass().startsWith("Craft")) {
					id = "9";
				}
				if (cc.getCharClass().startsWith("Profession")) {
					id = "30";
				}
				if (csdb.isClassSkill(cc.getId(), id)) {
					crossClass = false;
				}
				maxRanks += cs.getLevel();
			}
		}
		if (maxRanks > 0) {
			maxRanks += 3;
		}
		if (crossClass) {
			return maxRanks / 2;
		}
		return maxRanks;
	}

	public void add(ClassSlot c) {
		classVector.add(c);
	}

	public Vector<ClassSlot> getClassVector() {
		return classVector;
	}

	public void setClassVector(Vector<ClassSlot> classVector) {
		this.classVector = classVector;
	}

	public int size() {
		return classVector.size();
	}

	public ClassSlot get(int i) {
		return classVector.get(i);
	}
}

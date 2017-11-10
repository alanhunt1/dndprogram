package initcheck.database;

import initcheck.Participant;
import initcheck.Status;
import initcheck.character.library.LibraryItem;

import java.io.Serializable;
import java.util.Vector;

public class Monster extends Participant implements Cloneable, Serializable,
		LibraryItem, Exportable, Importable {

	private static final long serialVersionUID = 1L;

	protected String baseName;

	protected String fighting;

	protected int position;

	protected int group;

	protected String size = "";

	protected String type = "";

	protected String subType = "";

	protected String hitDie = "0";

	protected String initNotes = "none";

	protected String landSpeed = "0";

	protected String flySpeed = "0";

	protected String flyType = "none";

	protected String swimSpeed = "0";

	protected String climbSpeed = "0";

	protected String burrowSpeed = "0";

	protected String acNotes = "none";

	protected String attacks = "0";

	protected String damage = "0";

	protected String face = "0";

	protected String specialAttacks = "none";

	protected String specialQualities = "none";

	protected String skills = "none";

	protected String feats = "none";

	protected String terrain = "";

	protected String org = "none";

	protected String challengeRating = "0";

	protected String treasure = "normal";

	protected String alignment = "neutral";

	protected String combatNotes = "none";

	protected String genNotes = "none";

	protected String page = "0";

	protected String dieType = "0";

	protected String hitDice = "0";

	protected String advanced = "";

	protected String monstClass = "";

	protected String source = "";

	

	protected String stunPercentage = "20";

	protected String damageReduction;

	protected String drType;

	protected String poisonType;

	protected String poisonSaveType;

	protected String poisonSaveDc;

	protected String poisonInitialDie;

	protected String poisonSecondaryDie;

	protected String climateTerrain;

	Vector<MonsterAttacks> attackList = new Vector<MonsterAttacks>();

	public String listValue() {
		return "Monster : " + name;
	}

	public String listFormat() {
		return pad(name, 30) + " " + pad(size, 20) + " " + challengeRating
				+ " " + pad(source, 20);
	}

	public String pad(String s, int l) {

		if (s == null) {
			s = "";
		}

		if (s.length() > l) {
			return s.substring(0, l);
		}

		char[] chars = new char[l];
		for (int i = 0; i < s.length(); i++) {
			chars[i] = s.charAt(i);
		}
		for (int i = s.length(); i < l; i++) {
			chars[i] = ' ';
		}
		return new String(chars);
	}

	public String getDescription() {
		return "";
	}

	public String getFullDescription() {
		return "";
	}

	public Monster(String s) {
		picture = "images/qmark.jpg";
		readImport(s);
		
		setPType("MONSTER");
	}

	public void readImport(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();

			if (t.getTagName().equals("BaseName")) {
				setBaseName(t.getTagBody());
			}
			if (t.getTagName().equals("Name")) {
				setName(t.getTagBody());
			}
			if (t.getTagName().equals("Size")) {
				setSize(t.getTagBody());
			}
			if (t.getTagName().equals("Type")) {
				setType(t.getTagBody());
			}
			if (t.getTagName().equals("SubType")) {
				setSubType(t.getTagBody());
			}
			if (t.getTagName().equals("Hitdie")) {
				setHitDie(t.getTagBody());
			}
			if (t.getTagName().equals("HitPoints")) {
				setHP(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("InitMod")) {
				setMod(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("InitNotes")) {
				setInitNotes(t.getTagBody());
			}
			if (t.getTagName().equals("LandSpeed")) {
				setLandSpeed(t.getTagBody());
			}
			if (t.getTagName().equals("FlySpeed")) {
				setFlySpeed(t.getTagBody());
			}
			if (t.getTagName().equals("FlyType")) {
				setFlyType(t.getTagBody());
			}
			if (t.getTagName().equals("SwimSpeed")) {
				setSwimSpeed(t.getTagBody());
			}
			if (t.getTagName().equals("ClimbSpeed")) {
				setClimbSpeed(t.getTagBody());
			}
			if (t.getTagName().equals("BurrowSpeed")) {
				setBurrowSpeed(t.getTagBody());
			}
			if (t.getTagName().equals("Ac")) {
				setAC(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("AcNotes")) {
				setAcNotes(t.getTagBody());
			}
			if (t.getTagName().equals("Attacks")) {
				setAttacks(t.getTagBody());
			}
			if (t.getTagName().equals("Damage")) {
				setDamage(t.getTagBody());
			}
			if (t.getTagName().equals("FaceReach")) {
				setFace(t.getTagBody());
			}
			if (t.getTagName().equals("SpecialAttacks")) {
				setSpecialAttacks(t.getTagBody());
			}
			if (t.getTagName().equals("SpecialQualities")) {
				setSpecialQualities(t.getTagBody());
			}
			if (t.getTagName().equals("FortSave")) {
				setFortSave(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("ReflexSave")) {
				setRefSave(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("WillSave")) {
				setWillSave(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("Str")) {
				setStrength(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("Dex")) {
				setDexterity(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("Con")) {
				setConstitution(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("Intel")) {
				setIntelligence(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("Wis")) {
				setWisdom(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("Cha")) {
				setCharisma(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("Skills")) {
				setSkills(t.getTagBody());
			}
			if (t.getTagName().equals("Feats")) {
				setFeats(t.getTagBody());
			}
			if (t.getTagName().equals("ClimateTerrain")) {
				setClimateTerrain(t.getTagBody());
			}
			if (t.getTagName().equals("Org")) {
				setOrg(t.getTagBody());
			}
			if (t.getTagName().equals("ChallengeRating")) {
				setChallengeRating(t.getTagBody());
			}
			if (t.getTagName().equals("Treasure")) {
				setTreasure(t.getTagBody());
			}
			if (t.getTagName().equals("Alignment")) {
				setAlignment(t.getTagBody());
			}
			if (t.getTagName().equals("CombatNotes")) {
				setCombatNotes(t.getTagBody());
			}
			if (t.getTagName().equals("Notes")) {
				setNotes(t.getTagBody());
			}
			if (t.getTagName().equals("Page")) {
				setPage(t.getTagBody());
			}
			if (t.getTagName().equals("DieType")) {
				setDieType(t.getTagBody());
			}
			if (t.getTagName().equals("Hd")) {
				setHitDice(t.getTagBody());
			}
			if (t.getTagName().equals("Adv")) {
				setAdvanced(t.getTagBody());
			}
			if (t.getTagName().equals("Class")) {
				setMonstClass(t.getTagBody());
			}
			if (t.getTagName().equals("Source")) {
				setSource(t.getTagBody());
			}
			if (t.getTagName().equals("Picture")) {
				setPicture(t.getTagBody());
			}
			if (t.getTagName().equals("DamageReduction")) {
				setDamageReduction(t.getTagBody());
			}
			if (t.getTagName().equals("DrType")) {
				setDrType(t.getTagBody());
			}
			if (t.getTagName().equals("PoisonType")) {
				setPoisonType(t.getTagBody());
			}
			if (t.getTagName().equals("PoisonSaveType")) {
				setPoisonSaveType(t.getTagBody());
			}
			if (t.getTagName().equals("PoisonSaveDc")) {
				setPoisonSaveDc(t.getTagBody());
			}
			if (t.getTagName().equals("PoisonInitialDie")) {
				setPoisonInitialDie(t.getTagBody());
			}
			if (t.getTagName().equals("PoisonSecondaryDie")) {
				setPoisonSecondaryDie(t.getTagBody());
			}
			if (t.getTagName().equals("MonsterAttacks")) {
			
				attackList.add(new MonsterAttacks(t.getTagBody()));
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Monster>\n");
		sb.append("<BaseName>" + baseName + "</BaseName>\n");
		sb.append("<Name>" + name + "</Name>\n");
		sb.append("<Size>" + size + "</Size>\n");
		sb.append("<Type>" + type + "</Type>\n");
		sb.append("<SubType>" + subType + "</SubType>\n");
		sb.append("<Hitdie>" + hitDie + "</Hitdie>\n");
		sb.append("<HitPoints>" + hitPoints + "</HitPoints>\n");
		sb.append("<InitMod>" + initMod + "</InitMod>\n");
		sb.append("<InitNotes>" + initNotes + "</InitNotes>\n");
		sb.append("<LandSpeed>" + landSpeed + "</LandSpeed>\n");
		sb.append("<FlySpeed>" + flySpeed + "</FlySpeed>\n");
		sb.append("<FlyType>" + flyType + "</FlyType>\n");
		sb.append("<SwimSpeed>" + swimSpeed + "</SwimSpeed>\n");
		sb.append("<ClimbSpeed>" + climbSpeed + "</ClimbSpeed>\n");
		sb.append("<BurrowSpeed>" + burrowSpeed + "</BurrowSpeed>\n");
		sb.append("<Ac>" + ac + "</Ac>\n");
		sb.append("<AcNotes>" + acNotes + "</AcNotes>\n");
		sb.append("<Attacks>" + attacks + "</Attacks>\n");
		sb.append("<Damage>" + damage + "</Damage>\n");
		sb.append("<FaceReach>" + face + "</FaceReach>\n");
		sb.append("<SpecialAttacks>" + specialAttacks + "</SpecialAttacks>\n");
		sb.append("<SpecialQualities>" + specialQualities
				+ "</SpecialQualities>\n");
		sb.append("<FortSave>" + fortSave + "</FortSave>\n");
		sb.append("<ReflexSave>" + refSave + "</ReflexSave>\n");
		sb.append("<WillSave>" + willSave + "</WillSave>\n");
		sb.append("<Str>" + strength + "</Str>\n");
		sb.append("<Dex>" + dexterity + "</Dex>\n");
		sb.append("<Con>" + constitution + "</Con>\n");
		sb.append("<Intel>" + intelligence + "</Intel>\n");
		sb.append("<Wis>" + wisdom + "</Wis>\n");
		sb.append("<Cha>" + charisma + "</Cha>\n");
		sb.append("<Skills>" + skills + "</Skills>\n");
		sb.append("<Feats>" + feats + "</Feats>\n");
		sb.append("<ClimateTerrain>" + climateTerrain + "</ClimateTerrain>\n");
		sb.append("<Org>" + org + "</Org>\n");
		sb.append("<ChallengeRating>" + challengeRating
				+ "</ChallengeRating>\n");
		sb.append("<Treasure>" + treasure + "</Treasure>\n");
		sb.append("<Alignment>" + alignment + "</Alignment>\n");
		sb.append("<CombatNotes>" + combatNotes + "</CombatNotes>\n");
		sb.append("<Notes>" + notes + "</Notes>\n");
		sb.append("<Page>" + page + "</Page>\n");
		sb.append("<DieType>" + dieType + "</DieType>\n");
		sb.append("<Hd>" + hitDice + "</Hd>\n");
		sb.append("<Adv>" + advanced + "</Adv>\n");
		sb.append("<Class>" + monstClass + "</Class>\n");
		sb.append("<Source>" + source + "</Source>\n");
		sb.append("<Picture>" + picture + "</Picture>\n");
		sb.append("<DamageReduction>" + damageReduction
				+ "</DamageReduction>\n");
		sb.append("<DrType>" + drType + "</DrType>\n");
		sb.append("<PoisonType>" + poisonType + "</PoisonType>\n");
		sb.append("<PoisonSaveType>" + poisonSaveType + "</PoisonSaveType>\n");
		sb.append("<PoisonSaveDc>" + poisonSaveDc + "</PoisonSaveDc>\n");
		sb.append("<PoisonInitialDie>" + poisonInitialDie
				+ "</PoisonInitialDie>\n");
		sb.append("<PoisonSecondaryDie>" + poisonSecondaryDie
				+ "</PoisonSecondaryDie>\n");
		for (int i = 0; i < attackList.size(); i++) {
			sb.append(attackList.get(i).exportFormat());
		}
		sb.append("</Monster>\n");
		return sb.toString();
	}

	public String existsAs() {

		return null;
	}

	public Vector<Monster> getValues() {
		MonsterDAO db = new MonsterDAO();
		return db.getMonsters();
	}

	public void save(boolean overwrite) {
		MonsterDAO db = new MonsterDAO();

		if (overwrite) {
			id = 0;
		}
		id = db.addOrUpdateMonster(this);

		MonsterAttacksDAO adb = new MonsterAttacksDAO();
		adb.clearMonsterAttacks("" + id);
		for (int i = 0; i < attackList.size(); i++) {
			adb.addMonsterAttacks(attackList.get(i));
		}

	}

	public Monster() {
		picture = "images/qmark.jpg";
		setPType("MONSTER");
		
	}

	public Monster(String n, String f, int p, int i) {
		picture = "images/qmark.jpg";
		baseName = n;
		name = getNamePad(n);
		fighting = f;
		position = p;
		group = 0;
		initMod = i;
		setPType("MONSTER");
	}

	  public void convertNulls(){ 
	    
	      if (name == null) {
	        name = "";
	      }

	      if (size == null) {
	        size = "";
	      }

	      if (type == null) {
	        type = "";
	      }

	     

	      if (initNotes == null) {
	        initNotes = "";
	      }

	      if (landSpeed == null) {
	        landSpeed = "";
	      }

	      if (flySpeed == null) {
	        flySpeed = "";
	      }

	   
	      if (swimSpeed == null) {
	        swimSpeed = "";
	      }

	      if (climbSpeed == null) {
	        climbSpeed = "";
	      }

	      if (burrowSpeed == null) {
	        burrowSpeed = "";
	      }

	     

	      if (acNotes == null) {
	        acNotes = "";
	      }

	      if (attacks == null) {
	        attacks = "";
	      }

	      if (damage == null) {
	        damage = "";
	      }

	  

	      if (specialAttacks == null) {
	        specialAttacks = "";
	      }

	      if (specialQualities == null) {
	        specialQualities = "";
	      }

	    
	      if (skills == null) {
	        skills = "";
	      }

	      if (feats == null) {
	        feats = "";
	      }

	      if (climateTerrain == null) {
	        climateTerrain = "";
	      }

	      if (org == null) {
	        org = "";
	      }

	     
	      if (treasure == null) {
	        treasure = "";
	      }

	    

	      if (combatNotes == null) {
	        combatNotes = "";
	      }

	      if (notes == null) {
	        notes = "";
	      }

	      if (page == null) {
	        page = "";
	      }

	    
	      if (source == null) {
	        source = "";
	      }

	      if (picture == null) {
	        picture = "";
	      }

	      if (damageReduction == null) {
	        damageReduction = "";
	      }

	      if (drType == null) {
	        drType = "";
	      }

	      if (poisonType == null) {
	        poisonType = "";
	      }

	      if (poisonSaveType == null) {
	        poisonSaveType = "";
	      }

	      if (poisonSaveDc == null) {
	        poisonSaveDc = "";
	      }

	      if (poisonInitialDie == null) {
	        poisonInitialDie = "";
	      }

	      if (poisonSecondaryDie == null) {
	        poisonSecondaryDie = "";
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

		if (landSpeed != null && !landSpeed.equals("")) {
			try {
				Integer.parseInt(landSpeed);
			} catch (Exception e) {
				errors
						.add("landSpeed is not a valid number.  Please enter a valid number.");
			}
		}
		if (flySpeed != null && !flySpeed.equals("")) {
			try {
				Integer.parseInt(flySpeed);
			} catch (Exception e) {
				errors
						.add("flySpeed is not a valid number.  Please enter a valid number.");
			}
		}
		if (swimSpeed != null && !swimSpeed.equals("")) {
			try {
				Integer.parseInt(swimSpeed);
			} catch (Exception e) {
				errors
						.add("swimSpeed is not a valid number.  Please enter a valid number.");
			}
		}
		if (climbSpeed != null && !climbSpeed.equals("")) {
			try {
				Integer.parseInt(climbSpeed);
			} catch (Exception e) {
				errors
						.add("climbSpeed is not a valid number.  Please enter a valid number.");
			}
		}
		if (burrowSpeed != null && !burrowSpeed.equals("")) {
			try {
				Integer.parseInt(burrowSpeed);
			} catch (Exception e) {
				errors
						.add("burrowSpeed is not a valid number.  Please enter a valid number.");
			}
		}

		if (page != null && !page.equals("")) {
			try {
				Integer.parseInt(page);
			} catch (Exception e) {
				errors
						.add("page is not a valid number.  Please enter a valid number.");
			}
		}
		if (dieType != null && !dieType.equals("")) {
			try {
				Integer.parseInt(dieType);
			} catch (Exception e) {
				errors
						.add("dietype is not a valid number.  Please enter a valid number.");
			}
		}
		if (damageReduction != null && !damageReduction.equals("")) {
			try {
				Integer.parseInt(damageReduction);
			} catch (Exception e) {
				errors
						.add("damageReduction is not a valid number.  Please enter a valid number.");
			}
		}
		if (poisonSaveDc != null && !poisonSaveDc.equals("")) {
			try {
				Integer.parseInt(poisonSaveDc);
			} catch (Exception e) {
				errors
						.add("poisonSaveDc is not a valid number.  Please enter a valid number.");
			}
		}
	}

	private void checkDates(Vector<String> errors) {
	}

	public String getBaseName() {
		return baseName;
	}

	public String getListFormat() {
		return name + "CR:" + challengeRating;
	}

	// set methods
	public void setName(String n) {
		name = getNamePad(n);
	}

	public void setBaseName(String n) {
		baseName = n;
	}

	public void setOpponent(String f) {
		fighting = f;
	}

	public void setPosition(int i) {
		position = i;
	}

	public void setGroup(int i) {
		group = i;
	}

	public void setMod(int i) {
		initMod = i;
	}

	public void setInit(int i) {
		initRoll = i;
	}

	public void setSize(String s) {
		size = s;
	}

	public void setType(String s) {
		type = s;
	}

	public void setSubType(String s) {
		subType = s;
	}

	public void setHitDie(String s) {
		hitDie = s;
	}

	public void setInitNotes(String s) {
		initNotes = s;
	}

	public void setLandSpeed(String s) {
		landSpeed = s;
	}

	public void setFlySpeed(String s) {
		flySpeed = s;
	}

	public void setFlyType(String s) {
		flyType = s;
	}

	public void setSwimSpeed(String s) {
		swimSpeed = s;
	}

	public void setClimbSpeed(String s) {
		climbSpeed = s;
	}

	public void setBurrowSpeed(String s) {
		burrowSpeed = s;
	}

	public void setACNotes(String s) {
		acNotes = s;
	}

	public void setAttacks(String s) {
		attacks = s;
	}

	public void setDamage(String s) {
		damage = s;
	}

	public void setFace(String s) {
		face = s;
	}

	public void setSpecialAttacks(String s) {
		specialAttacks = s;
	}

	public void setSpecialQualities(String s) {
		specialQualities = s;
	}

	public void setSkills(String s) {
		skills = s;
	}

	public void setFeats(String s) {
		feats = s;
	}

	public void setTerrain(String s) {
		terrain = s;
	}

	public void setOrg(String s) {
		org = s;
	}

	public void setChallengeRating(String s) {
		challengeRating = s;
	}

	public void setTreasure(String s) {
		treasure = s;
	}

	public void setAlignment(String s) {
		alignment = s;
	}

	public void setCombatNotes(String s) {
		combatNotes = s;
	}

	public void setGenNotes(String s) {
		genNotes = s;
	}

	public void setPage(String s) {
		page = s;
	}

	public void setDieType(String s) {
		dieType = s;
	}

	public void setHitDice(String s) {
		hitDice = s;
	}

	public void setSource(String s) {
		source = s;
	}

	public void setPicture(String s) {
		picture = s;
	}

	// get methods
	public int getMod() {
		return initMod;
	}

	public int getInit() {
		return initRoll;
	}

	public int getGroup() {
		return group;
	}

	public String getNamePad(String s) {
		String outStr = s;
		for (int i = s.length(); i < 25; i++) {
			outStr += " ";
		}
		return outStr;
	}

	public String getSize() {
		return size;
	}

	public String getType() {
		return type;
	}

	public String getSubType() {
		return subType;
	}

	public String getHitDie() {
		return hitDie;
	}

	public String getInitNotes() {
		return initNotes;
	}

	public String getLandSpeed() {
		return landSpeed;
	}

	public String getFlySpeed() {
		return flySpeed;
	}

	public String getFlyType() {
		return flyType;
	}

	public String getSwimSpeed() {
		return swimSpeed;
	}

	public String getClimbSpeed() {
		return climbSpeed;
	}

	public String getBurrowSpeed() {
		return burrowSpeed;
	}

	public String getACNotes() {
		return acNotes;
	}

	public String getAttacks() {
		return attacks;
	}

	public String getDamage() {
		return damage;
	}

	public String getFace() {
		return face;
	}

	public String getSpecialAttacks() {
		return specialAttacks;
	}

	public String getSpecialQualities() {
		return specialQualities;
	}

	public String getSkills() {
		return skills;
	}

	public String getFeats() {
		return feats;
	}

	public String getTerrain() {
		return terrain;
	}

	public String getOrg() {
		return org;
	}

	public String getChallengeRating() {
		return challengeRating;
	}

	public double getLevel() {
		try {
			return Double.parseDouble(challengeRating);
		} catch (NumberFormatException nfe) {
			return 0;
		}
	}

	public String getTreasure() {
		return treasure;
	}

	public String getAlignment() {
		return alignment;
	}

	public String getCombatNotes() {
		return combatNotes;
	}

	public String getGenNotes() {
		return genNotes;
	}

	public String getPage() {
		return page;
	}

	public String getDieType() {
		return dieType;
	}

	public String getHitDice() {
		return hitDice;
	}

	public String getSource() {
		return source;
	}

	public String getPicture() {
		return picture;
	}

	@SuppressWarnings("unchecked")
	public Object clone() {
		try {

			
			Monster m = (Monster) super.clone();
			
			m.attackList = (Vector<MonsterAttacks>) attackList.clone();
			if (status != null) {
				m.status = (Status) status.clone();
			} else {
				m.status = new Status();
			}
			// m.featList = (Vector)featList.clone();
			// m.skillList = (Vector)skillList.clone();
			
			return (Object) m;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Get the Advanced value.
	 * 
	 * @return the Advanced value.
	 */
	public String getAdvanced() {
		return advanced;
	}

	/**
	 * Set the Advanced value.
	 * 
	 * @param newAdvanced
	 *            The new Advanced value.
	 */
	public void setAdvanced(String newAdvanced) {
		this.advanced = newAdvanced;
	}

	/**
	 * Get the MonstClass value.
	 * 
	 * @return the MonstClass value.
	 */
	public String getMonstClass() {
		return monstClass;
	}

	/**
	 * Set the MonstClass value.
	 * 
	 * @param newMonstClass
	 *            The new MonstClass value.
	 */
	public void setMonstClass(String newMonstClass) {
		this.monstClass = newMonstClass;
	}

	/**
	 * Get the AcNotes value.
	 * 
	 * @return the AcNotes value.
	 */
	public String getAcNotes() {
		return acNotes;
	}

	/**
	 * Set the AcNotes value.
	 * 
	 * @param newAcNotes
	 *            The new AcNotes value.
	 */
	public void setAcNotes(String newAcNotes) {
		this.acNotes = newAcNotes;
	}

	/**
	 * Get the value of damageReduction
	 * 
	 * @return a <code>String</code> value
	 */
	public String getDamageReduction() {
		return damageReduction;
	}

	/**
	 * Set the value of damageReduction
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setDamageReduction(String s) {
		damageReduction = s;
	}

	/**
	 * Get the value of drType
	 * 
	 * @return a <code>String</code> value
	 */
	public String getDrType() {
		return drType;
	}

	/**
	 * Set the value of drType
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setDrType(String s) {
		drType = s;
	}

	/**
	 * Get the value of poisonType
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPoisonType() {
		return poisonType;
	}

	/**
	 * Set the value of poisonType
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPoisonType(String s) {
		poisonType = s;
	}

	/**
	 * Get the value of poisonSaveType
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPoisonSaveType() {
		return poisonSaveType;
	}

	/**
	 * Set the value of poisonSaveType
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPoisonSaveType(String s) {
		poisonSaveType = s;
	}

	/**
	 * Get the value of poisonSaveDc
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPoisonSaveDc() {
		return poisonSaveDc;
	}

	/**
	 * Set the value of poisonSaveDc
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPoisonSaveDc(String s) {
		poisonSaveDc = s;
	}

	/**
	 * Get the value of poisonInitialDie
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPoisonInitialDie() {
		return poisonInitialDie;
	}

	/**
	 * Set the value of poisonInitialDie
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPoisonInitialDie(String s) {
		poisonInitialDie = s;
	}

	/**
	 * Get the value of poisonSecondaryDie
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPoisonSecondaryDie() {
		return poisonSecondaryDie;
	}

	/**
	 * Set the value of poisonSecondaryDie
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPoisonSecondaryDie(String s) {
		poisonSecondaryDie = s;
	}

	/**
	 * Get the StunPercentage value.
	 * 
	 * @return the StunPercentage value.
	 */
	public String getStunPercentage() {
		return stunPercentage;
	}

	/**
	 * Set the StunPercentage value.
	 * 
	 * @param newStunPercentage
	 *            The new StunPercentage value.
	 */
	public void setStunPercentage(String newStunPercentage) {
		this.stunPercentage = newStunPercentage;
	}

	/**
	 * Get the AttackList value.
	 * 
	 * @return the AttackList value.
	 */
	public Vector<MonsterAttacks> getAttackList() {
		return attackList;
	}

	/**
	 * Set the AttackList value.
	 * 
	 * @param newAttackList
	 *            The new AttackList value.
	 */
	public void setAttackList(Vector<MonsterAttacks> newAttackList) {
		this.attackList = newAttackList;
	}

	/**
	 * Get the ClimateTerrain value.
	 * 
	 * @return the ClimateTerrain value.
	 */
	public String getClimateTerrain() {
		return climateTerrain;
	}

	/**
	 * Set the ClimateTerrain value.
	 * 
	 * @param newClimateTerrain
	 *            The new ClimateTerrain value.
	 */
	public void setClimateTerrain(String newClimateTerrain) {
		this.climateTerrain = newClimateTerrain;
	}

	public String getId() {
		// TODO Auto-generated method stub
		return "" + getID();
	}

	public void setId(String s) {
		id = Integer.parseInt(s);
	}
}

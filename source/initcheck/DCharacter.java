package initcheck;

import java.io.Serializable;
import java.util.StringTokenizer;
import java.util.Vector;

import initcheck.character.Calculation;
import initcheck.character.ClassSet;
import initcheck.character.ClassSlot;
import initcheck.character.JumpBlock;
import initcheck.character.MoneyBlock;
import initcheck.character.SaveBlock;
import initcheck.character.StatBlock;
import initcheck.character.TempModSet;
import initcheck.database.Armor;
import initcheck.database.Campaign;
import initcheck.database.CampaignDAO;
import initcheck.database.CampaignJournals;
import initcheck.database.CampaignJournalsDAO;
import initcheck.database.CharClass;
import initcheck.database.ClassAbilities;
import initcheck.database.ClassAdvancement;
import initcheck.database.ClassProficiency;
import initcheck.database.DamageRecordDAO;
import initcheck.database.Exportable;
import initcheck.database.Feat;
import initcheck.database.FeatEffects;
import initcheck.database.FeatEffectsDAO;
import initcheck.database.InitDBC;
import initcheck.database.LoadLimits;
import initcheck.database.LoadLimitsDAO;
import initcheck.database.MonsterKillsDAO;
import initcheck.database.Party;
import initcheck.database.PartyDAO;
import initcheck.database.PlayerAmmoDAO;
import initcheck.database.PlayerBattlesDAO;
import initcheck.database.PlayerClassDAO;
import initcheck.database.PlayerDomain;
import initcheck.database.PlayerDomainDAO;
import initcheck.database.PlayerFavoredEnemy;
import initcheck.database.PlayerFavoredEnemyDAO;
import initcheck.database.PlayerFeats;
import initcheck.database.PlayerFeatsDAO;
import initcheck.database.PlayerHp;
import initcheck.database.PlayerHpDAO;
import initcheck.database.PlayerItems;
import initcheck.database.PlayerItemsDAO;
import initcheck.database.PlayerKillsDAO;
import initcheck.database.PlayerLanguages;
import initcheck.database.PlayerLanguagesDAO;
import initcheck.database.PlayerSkills;
import initcheck.database.PlayerSkillsDAO;
import initcheck.database.PlayerSpells;
import initcheck.database.PlayerSpellsDAO;
import initcheck.database.PlayerTempMod;
import initcheck.database.PlayerTempModDAO;
import initcheck.database.PlayerWeaponsDAO;
import initcheck.database.Prereq;
import initcheck.database.Race;
import initcheck.database.RaceDAO;
import initcheck.database.RacialAbility;
import initcheck.database.RacialAbilityDAO;
import initcheck.database.Skill;
import initcheck.database.SkillDAO;
import initcheck.database.SpellLevel;
import initcheck.database.SpellLevelDAO;
import initcheck.database.Tag;
import initcheck.database.Weapon;
import initcheck.database.WeaponViews;
import initcheck.database.WeaponViewsDAO;
import initcheck.graphics.TiledListItem;
import initcheck.graphics.TiledListString;
import initcheck.utils.StrManip;

public class DCharacter extends Participant implements Serializable, Exportable, TiledListItem {

	static final long serialVersionUID = 1;

	InitLogger logger = new InitLogger(this, "defaultLog4j.cfg");

	// fields
	String race = "";

	String alignment = "";

	String party = "";

	String marchOrder;

	String sleepShift;

	int xp;

	String height;

	String weight;

	String age;

	String eyes;

	String hair;

	String fullName;

	String miscArmorText;

	String playerNotes;

	int baseMovementOverride;

	int miscArmor;

	String damageReduction = "-/-";

	String spellResist = "-";

	String playerName = "";

	String deity;

	int naturalArmor;

	public int baseMovement = 30;

	String sizeOverride;

	String modNotes;

	String rulesetId;
	
	String rulesetName;
	
	// objects
	ClassSet classSet = new ClassSet();

	Vector<PlayerItems> items = new Vector<PlayerItems>();

	Vector<Weapon> weapons = new Vector<Weapon>();

	Vector<Weapon> ammo = new Vector<Weapon>();

	Vector<PlayerHp> hpList = new Vector<PlayerHp>();

	TempModSet tempModSet = new TempModSet();

	//Vector tempMods = new Vector();
	
	Vector<PlayerDomain> domains = new Vector<PlayerDomain>();

	Vector<String> droppedLocations = new Vector<String>();

	Vector<PlayerLanguages> languages = new Vector<PlayerLanguages>();

	Vector<PlayerFavoredEnemy> favoredEnemies = new Vector<PlayerFavoredEnemy>();

	Vector<PlayerSpells> spellsKnown = new Vector<PlayerSpells>();

	Vector<UsedSpell> divineSpellsUsed = new Vector<UsedSpell>();

	Vector<UsedSpell> arcaneSpellsUsed = new Vector<UsedSpell>();

	Vector<PlayerSpells> spellsMemorized = new Vector<PlayerSpells>();

	Vector<SpellLevel> divineSpellsPerDay = null;

	Armor armor = null;

	Armor shield = null;

	Armor restArmor = null;

	StatBlock stats = new StatBlock();

	SaveBlock saves = new SaveBlock();

	MoneyBlock money = new MoneyBlock();

	JumpBlock jump = new JumpBlock();

	int precalcAc = 0;

	String partyId = null;
	
	Campaign campaign = null;
	
	String journal = null;
	
	public String getRulesetId() {
		return rulesetId;
	}

	public void setRulesetId(String rulesetId) {
		this.rulesetId = rulesetId;
	}

	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getRulesetName() {
		return rulesetName;
	}

	public void setRulesetName(String rulesetName) {
		this.rulesetName = rulesetName;
	}

	public void importInfo(String s) {
		
		Tag t = Tag.getTag(s);

		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			
			// import the static elements
			if (t.getTagName().equals("Id")) {
				setID(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("Name")) {
				setName(t.getTagBody());
			}
			if (t.getTagName().equals("Race")) {
				setRace(t.getTagBody());
			}
			if (t.getTagName().equals("Alignment")) {
				setAlignment(t.getTagBody());
			}
			if (t.getTagName().equals("Party")) {
				setParty(t.getTagBody());
			}
			if (t.getTagName().equals("Gender")) {
				setGender(t.getTagBody());
			}
			if (t.getTagName().equals("PlayerName")) {
				setPlayerName(t.getTagBody());
			}
			if (t.getTagName().equals("XP")) {
				setXp(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("Deity")) {
				setDeity(t.getTagBody());
			}
			if (t.getTagName().equals("NaturalArmor")) {
				setNaturalArmor(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("Height")) {
				setHeight(t.getTagBody());
			}
			if (t.getTagName().equals("Weight")) {
				setWeight(t.getTagBody());
			}
			if (t.getTagName().equals("Age")) {
				setAge(t.getTagBody());
			}
			if (t.getTagName().equals("Eyes")) {
				setEyes(t.getTagBody());
			}
			if (t.getTagName().equals("Hair")) {
				setHair(t.getTagBody());
			}
			if (t.getTagName().equals("BaseMovementOverride")) {
				setBaseMovementOverride(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("MiscArmor")) {
				setMiscArmor(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("FullName")) {
				setFullName(t.getTagBody());
			}
			if (t.getTagName().equals("MiscArmorText")) {
				setMiscArmorText(t.getTagBody());
			}
			if (t.getTagName().equals("PlayerNotes")) {
				setPlayerNotes(t.getTagBody());
			}
			if (t.getTagName().equals("DamageReduction")) {
				setDamageReduction(t.getTagBody());
			}
			if (t.getTagName().equals("SpellResist")) {
				setSpellResist(t.getTagBody());
			}
			if (t.getTagName().equals("MarchOrder")) {
				setMarchOrder(t.getTagBody());
			}
			if (t.getTagName().equals("SleepShift")) {
				setSleepShift(t.getTagBody());
			}
			if (t.getTagName().equals("SizeOverride")) {
				setSizeOverride(t.getTagBody());
			}
			if (t.getTagName().equals("ModNotes")) {
				setModNotes(t.getTagBody());
			}
			if (t.getTagName().equals("Picture")) {
				setPicture(t.getTagBody());
			}
			if (t.getTagName().equals("Icon")) {
				setIcon(t.getTagBody());
			}
			if (t.getTagName().equals("PartyId")) {
				setPartyId(t.getTagBody());
			}
			if (t.getTagName().equals("Journal")) {
				System.out.println("Read Journal");
				setJournal(t.getTagBody());
			}
			// now the vectors
			if (t.getTagName().equals("ClassSlot")) {
				ClassSlot cs = new ClassSlot(t.getTagBody());
				classSet.add(cs);
			}

			if (t.getTagName().equals("PlayerItems")) {
				PlayerItems pi = new PlayerItems(t.getTagBody());

				items.add(pi);
			}

			if (t.getTagName().equals("Weapon")) {
				
				Weapon w = new Weapon(t.getTagBody());
				if (w.getCategory().equals("Ammunition")) {
					ammo.add(w);
				} else {
					weapons.add(w);
				}
			}

			if (t.getTagName().equals("PlayerHp")) {
				
				PlayerHp ph = new PlayerHp(t.getTagBody());
				hpList.add(ph);
			}

			if (t.getTagName().equals("PlayerTempMod")) {
				PlayerTempMod ptm = new PlayerTempMod(t.getTagBody());
				tempModSet.add(ptm);
			}

			if (t.getTagName().equals("PlayerDomain")) {
				PlayerDomain pd = new PlayerDomain(t.getTagBody());
				domains.add(pd);
			}

			if (t.getTagName().equals("PlayerFeats")) {
				PlayerFeats f = new PlayerFeats(t.getTagBody());
				featList.add(f);
			}

			if (t.getTagName().equals("PlayerSkill")) {
				PlayerSkills sk = new PlayerSkills(t.getTagBody());
				skillSet.add(sk);
			}

			if (t.getTagName().equals("DroppedLocation")) {
				droppedLocations.add(t.getTagBody());
			}

			if (t.getTagName().equals("PlayerLanguage")) {
				PlayerLanguages pl = new PlayerLanguages(t.getTagBody());
				languages.add(pl);
			}

			if (t.getTagName().equals("PlayerFavoredEnemy")) {
				PlayerFavoredEnemy pl = new PlayerFavoredEnemy(t.getTagBody());
				favoredEnemies.add(pl);
			}

			if (t.getTagName().equals("PlayerSpells")) {
				PlayerSpells ps = new PlayerSpells(t.getTagBody());
				spellsKnown.add(ps);
			}

			if (t.getTagName().equals("PlayerSpellsMemorized")) {
				PlayerSpells ps = new PlayerSpells(t.getTagBody());
				spellsMemorized.add(ps);
			}

			if (t.getTagName().equals("PlayerArmor")) {
				logger.log("READING IN ARMOR");
				Armor a = new Armor(t.getTagBody());
				if (a.getId() != null && !a.getId().equals("")
						&& !a.getId().equals("null")) {
					if (a.getAtRest().equals("Y")) {
						logger.log("REST ARMOR READ");
						restArmor = a;
					} else if (a.getDisplayType().equalsIgnoreCase("SHIELD")) {
						logger.log("SHIELD READ");
						shield = a;
					} else {
						logger.log("ARMOR READ " + a.getMaterial());
						armor = a;
					}
				}
			}

			// finally the blocks

			if (t.getTagName().equals("StatBlock")) {
				stats = new StatBlock(t.getTagBody());
			}

			if (t.getTagName().equals("SaveBlock")) {
				saves = new SaveBlock(t.getTagBody());
			}

			if (t.getTagName().equals("MoneyBlock")) {
				money = new MoneyBlock(t.getTagBody());
			}

			
			int idx = tagName.length() + tagName.length() + 5
					+ tagBody.length();

			s = s.substring(idx, s.length());
			t = Tag.getTag(s);
		}
	}

	public void writeImportCharacter(boolean overwrite) {

		InitDBC db = new InitDBC();
		String oldId = "";
		// if the user chose to overwrite the player, remove the original
		// from the database.
		if (overwrite) {
			
			oldId = db.deletePlayer(name, party);
		}

		// see if the party already exists in the database. If not, then add it.
		Vector<String> parties = db.getParties();
		boolean found = false;
		for (int i = 0; i < parties.size(); i++) {
			if (((String) parties.get(i)).equals(party)) {
				found = true;
			}
		}
		if (!found) {
			db.addParty(party);

		}

		// add the imported player to the DB, and get the new ID
		
		int id = db.addPlayer(this);
		setID(id);
		if (armor != null) {
			armor.setPlayerId("" + id);
		}
		if (restArmor != null) {
			restArmor.setPlayerId("" + id);
		}
		if (shield != null) {
			shield.setPlayerId("" + id);
		}

		// update the various historical/stat tables with the new ID, since
		// we do a full replace.
		if (overwrite) {
			PlayerKillsDAO pkdb = new PlayerKillsDAO();
			pkdb.updateId(oldId, "" + id);
			PlayerBattlesDAO pbdb = new PlayerBattlesDAO();
			pbdb.updateId(oldId, "" + id);
			MonsterKillsDAO mkdb = new MonsterKillsDAO();
			mkdb.updateId(oldId, "" + id);
			DamageRecordDAO drdb = new DamageRecordDAO();
			drdb.updateId(oldId, "" + id);
		}

	

		if (id > 0) {

			db.updatePlayer(this);
		

			// insert classes
			PlayerClassDAO pcdb = new PlayerClassDAO();
			for (int i = 0; i < classSet.size(); i++) {
				pcdb.addPlayerClass(classSet.get(i), id);
			}

			// insert items
			PlayerItemsDAO pidb = new PlayerItemsDAO();
			for (int i = 0; i < items.size(); i++) {
				PlayerItems pi = (PlayerItems) items.get(i);
				pi.setPlayerId("" + getID());
				pidb.addPlayerItems(pi);
			}

			// insert weapons
			PlayerWeaponsDAO pwdb = new PlayerWeaponsDAO();
			WeaponViewsDAO wvdb = new WeaponViewsDAO();
			for (int i = 0; i < weapons.size(); i++) {
				Weapon w = weapons.get(i);
				int wid = pwdb.addPlayerWeapons(w, "" + getID());
				for (int j = 0; j < w.getFeatsApplied().size(); j++) {
					WeaponViews wv = (WeaponViews) (w.getFeatsApplied().get(j));
					wv.setPlayerWeaponId("" + wid);
					wvdb.addWeaponViews(wv);
				}
			}

			// insert ammo
			PlayerAmmoDAO padb = new PlayerAmmoDAO();
			for (int i = 0; i < ammo.size(); i++) {
				Weapon w = (Weapon) ammo.get(i);
				padb.addPlayerAmmo(w, "" + getID());
			}

			// insert hitpoint rolls
			PlayerHpDAO phpdb = new PlayerHpDAO();
			for (int i = 0; i < hpList.size(); i++) {
				PlayerHp php = (PlayerHp) hpList.get(i);
				php.setPlayerId("" + getID());
				phpdb.addPlayerHp(php);
			}

			// insert temp mods
			PlayerTempModDAO ptmdb = new PlayerTempModDAO();
			for (int i = 0; i < tempModSet.size(); i++) {
				PlayerTempMod ptm = (PlayerTempMod) tempModSet.get(i);
				ptm.setPlayerId("" + getID());
				ptmdb.addPlayerTempMod(ptm);
			}

			// insert domains
			PlayerDomainDAO pddb = new PlayerDomainDAO();
			for (int i = 0; i < domains.size(); i++) {
				PlayerDomain pd = (PlayerDomain) domains.get(i);
				pd.setPlayerId("" + getID());
				pddb.addPlayerDomain(pd);
			}

			// insert feats
			PlayerFeatsDAO pfdb = new PlayerFeatsDAO();
			for (int i = 0; i < featList.size(); i++) {
				Feat f = (Feat) featList.get(i);
				pfdb.addPlayerFeats(f, "" + getID());
			}

			// insert skills
			PlayerSkillsDAO psdb = new PlayerSkillsDAO();
			for (int i = 0; i < skillSet.size(); i++) {
				Skill sk = (Skill) skillSet.get(i);
				psdb.addPlayerSkills(sk, "" + getID());
			}

			// insert dropped locations
			for (int i = 0; i < droppedLocations.size(); i++) {
				pidb.dropLocation("" + getID(), (String) droppedLocations
						.get(i));
			}

			// insert languages
			PlayerLanguagesDAO pldb = new PlayerLanguagesDAO();
			for (int i = 0; i < languages.size(); i++) {
				PlayerLanguages pl = (PlayerLanguages) languages.get(i);
				pl.setPlayerId("" + getID());
				pldb.addPlayerLanguages(pl);
			}

			// insert spells
			PlayerSpellsDAO pspdb = new PlayerSpellsDAO();
			for (int i = 0; i < spellsKnown.size(); i++) {
				PlayerSpells sp = (PlayerSpells) spellsKnown.get(i);
				sp.setPlayerId("" + getID());
				pspdb.addPlayerSpells(sp);
			}

			// insert favored enemies
			PlayerFavoredEnemyDAO pfedb = new PlayerFavoredEnemyDAO();
			for (int i = 0; i < favoredEnemies.size(); i++) {
				PlayerFavoredEnemy pfe = (PlayerFavoredEnemy) favoredEnemies
						.get(i);
				pfe.setPlayerId("" + getID());
				pfedb.addPlayerFavoredEnemy(pfe);
			}
			
			// update journal
			if (journal != null){
				System.out.println("Writing Journal");
				CampaignJournalsDAO cjdb = new CampaignJournalsDAO();
				CampaignJournals cj = cjdb.getCampaignJournals(""+getID());
				cj.setJournalText(journal);
				
				if (cj.getJournalId() == null){
					System.out.println("Journal Id was not null");
					cj.setPlayerId(""+getID());
					cj.setCampaignId(getCampaign().getId());
					cj.setPartyId(getPartyId());
				}
				cjdb.addOrUpdateCampaignJournals(cj);
			}
		}

	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();

		// store all of the simple attributes.
		sb.append("<DCharacter>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<Name>" + name + "</Name>\n");
		sb.append("<Race>" + race + "</Race>\n");
		sb.append("<Alignment>" + alignment + "</Alignment>\n");
		sb.append("<Party>" + party + "</Party>\n");
		sb.append("<MarchOrder>" + marchOrder + "</MarchOrder>\n");
		sb.append("<SleepShift>" + sleepShift + "</SleepShift>\n");
		sb.append("<XP>" + xp + "</XP>\n");
		sb.append("<Height>" + height + "</Height>\n");
		sb.append("<Weight>" + weight + "</Weight>\n");
		sb.append("<Age>" + age + "</Age>\n");
		sb.append("<Eyes>" + eyes + "</Eyes>\n");
		sb.append("<Hair>" + hair + "</Hair>\n");
		sb.append("<FullName>" + fullName + "</FullName>\n");
		sb.append("<MiscArmorText>" + miscArmorText + "</MiscArmorText>\n");
		sb.append("<PlayerNotes>" + playerNotes + "</PlayerNotes>\n");
		sb.append("<BaseMovementOverride>" + baseMovementOverride
				+ "</BaseMovementOverride>\n");
		sb.append("<MiscArmor>" + miscArmor + "</MiscArmor>\n");
		sb.append("<DamageReduction>" + damageReduction
				+ "</DamageReduction>\n");
		sb.append("<SpellResist>" + spellResist + "</SpellResist>\n");
		sb.append("<PlayerName>" + playerName + "</PlayerName>\n");
		sb.append("<Deity>" + deity + "</Deity>\n");
		sb.append("<Gender>" + gender + "</Gender>");
		sb.append("<NaturalArmor>" + naturalArmor + "</NaturalArmor>\n");
		sb.append("<SizeOverride>" + sizeOverride + "</SizeOverride>\n");
		sb.append("<ModNotes>" + modNotes + "</ModNotes>\n");
		sb.append("<Picture>" + picture + "</Picture>\n");
		sb.append("<Icon>" + icon + "</Icon>\n");
		sb.append("<PartyId>" + partyId + "</PartyId>\n");
		
		// grab the (potentially) updated journal file to write out
		CampaignJournalsDAO cjdb = new CampaignJournalsDAO();
		CampaignJournals cj = cjdb.getCampaignJournals(""+getID());
			
		if (cj.getJournalId() != null){
			journal = cj.getJournalText();
		}
			
		sb.append("<Journal>" + journal + "</Journal>\n");
		
		// store the object attributes.
		// note, JumpBlock is entirely calculated, so there is no need to store
		// it.
		for (int i = 0; i < classSet.size(); i++) {
			sb.append(((ClassSlot) classSet.get(i)).exportFormat());
		}

		for (int i = 0; i < items.size(); i++) {
			sb.append(((PlayerItems) items.get(i)).exportFormat());
		}

		for (int i = 0; i < weapons.size(); i++) {
			sb.append(((Weapon) weapons.get(i)).exportFormat());
		}

		for (int i = 0; i < ammo.size(); i++) {
			sb.append(((Weapon) ammo.get(i)).exportFormat());
		}

		for (int i = 0; i < hpList.size(); i++) {
			sb.append(((PlayerHp) hpList.get(i)).exportFormat());
		}

		for (int i = 0; i < tempModSet.size(); i++) {
			sb.append(((PlayerTempMod) tempModSet.get(i)).exportFormat());
		}

		for (int i = 0; i < domains.size(); i++) {
			sb.append(((PlayerDomain) domains.get(i)).exportFormat());
		}

		for (int i = 0; i < featList.size(); i++) {
			sb.append(((PlayerFeats) featList.get(i)).exportFormat());
		}

		for (int i = 0; i < skillSet.size(); i++) {
			sb.append(((PlayerSkills) skillSet.get(i)).exportFormat());
		}

		for (int i = 0; i < spellsKnown.size(); i++) {
			sb.append(((PlayerSpells) spellsKnown.get(i)).exportFormat());
		}

		for (int i = 0; i < spellsMemorized.size(); i++) {
			sb.append(((PlayerSpells) spellsMemorized.get(i))
					.exportFormat("PlayerSpellsMemorized"));
		}

		for (int i = 0; i < droppedLocations.size(); i++) {
			sb.append("<DroppedLocation>");
			sb.append((String) droppedLocations.get(i));
			sb.append("</DroppedLocation>\n");
		}

		for (int i = 0; i < languages.size(); i++) {
			PlayerLanguages pl = (PlayerLanguages) languages.get(i);
			if (!pl.getLanguage().equals("Common")) {
				sb.append(pl.exportFormat());
			}
		}

		for (int i = 0; i < favoredEnemies.size(); i++) {
			sb.append(((PlayerFavoredEnemy) favoredEnemies.get(i))
					.exportFormat());
		}

		if (armor != null) {
			sb.append(armor.exportFormat());
		}

		if (shield != null) {
			sb.append(shield.exportFormat());
		}

		if (restArmor != null) {
			sb.append(restArmor.exportFormat());
		}

		sb.append(stats.exportFormat());
		sb.append(saves.exportFormat());
		sb.append(money.exportFormat());

		sb.append("</DCharacter>\n");

		return sb.toString();
	}

	/**
	 * Get the DroppedLocations value.
	 * 
	 * @return the DroppedLocations value.
	 */
	public Vector<String> getDroppedLocations() {
		return droppedLocations;
	}

	public Vector<TiledListString> getDroppedLocationsList() {
		Vector<TiledListString> v = new Vector<TiledListString>();
		for (String s:droppedLocations){
			v.add(new TiledListString(s));
		}
		return v;
	}

	
	/**
	 * Set the DroppedLocations value.
	 * 
	 * @param newDroppedLocations
	 *            The new DroppedLocations value.
	 */
	public void setDroppedLocations(Vector<String> newDroppedLocations) {
		this.droppedLocations = newDroppedLocations;
	}

	/**
	 * Get the Domains value.
	 * 
	 * @return the Domains value.
	 */
	public Vector<PlayerDomain> getDomains() {
		return domains;
	}

	/**
	 * Set the Domains value.
	 * 
	 * @param newDomains
	 *            The new Domains value.
	 */
	public void setDomains(Vector<PlayerDomain> newDomains) {
		this.domains = newDomains;
	}

	/**
	 * Get the TempMods value.
	 * 
	 * @return the TempMods value.
	 */
	public TempModSet getTempMods() {
		return tempModSet;
	}

	/**
	 * Set the TempMods value.
	 * 
	 * @param newTempMods
	 *            The new TempMods value.
	 */
	public void setTempMods(TempModSet newTempMods) {
		this.tempModSet = newTempMods;
	}

	/**
	 * Get the SleepShift value.
	 * 
	 * @return the SleepShift value.
	 */
	public String getSleepShift() {
		return sleepShift;
	}

	/**
	 * Set the SleepShift value.
	 * 
	 * @param newSleepShift
	 *            The new SleepShift value.
	 */
	public void setSleepShift(String newSleepShift) {
		this.sleepShift = newSleepShift;
	}

	/**
	 * Get the MarchOrder value.
	 * 
	 * @return the MarchOrder value.
	 */
	public String getMarchOrder() {
		return marchOrder;
	}

	/**
	 * Set the MarchOrder value.
	 * 
	 * @param newMarchOrder
	 *            The new MarchOrder value.
	 */
	public void setMarchOrder(String newMarchOrder) {
		this.marchOrder = newMarchOrder;
	}

	/**
	 * Get the HpList value.
	 * 
	 * @return the HpList value.
	 */
	public Vector<PlayerHp> getHpList() {
		return hpList;
	}

	/**
	 * Set the HpList value.
	 * 
	 * @param newHpList
	 *            The new HpList value.
	 */
	public void setHpList(Vector<PlayerHp> newHpList) {
		this.hpList = newHpList;
	}

	public int getStun() {
		
		int hp = Integer.parseInt(getHpCalc().getDisplayValue());
		
		
		int stun = hp / 5;
		if (hp % 5 != 0) {
			stun++;
		}
		return stun;
	}

	public Calculation getHpCalc() {

		Calculation calc = new Calculation();
		int hpVal = 0;
		int conBonus = getBonus(getStat("CONSTITUTION"));
		int ttlBonus = 0;
		int ttlFeat = 0;
		int featBonus = 0;
		int total = 0;

		// calculate bonus for heart of the mountain feat.
		if (hasFeat("230")) {
			featBonus = 2;
			total += 2;
		}
		for (int i = 0; i < hpList.size(); i++) {
			PlayerHp hp = (PlayerHp) hpList.get(i);
			hpVal += Integer.parseInt(hp.getHitPoints());
			ttlBonus += conBonus;
			ttlFeat += featBonus;
		}

		total += hpVal;
		total += ttlBonus;
		total += ttlFeat;

		// add bonus for toughness feats
		int toughness = 3 * getFeatCount("32");
		total += toughness;
		int imptoughness = 5 * getFeatCount("537");
		total += imptoughness;
		int suptoughness = 8 * getFeatCount("670");
		total += suptoughness;
		int ulttoughness = 12 * getFeatCount("691");
		total += ulttoughness;
		int dwtoughness = 6 * getFeatCount("1200");
		total += dwtoughness;
		int drtoughness = 12 * getFeatCount("1298");
		total += drtoughness;
		int gitoughness = 9 * getFeatCount("1076");
		total += gitoughness;

		calc.addElement("Hit Points Rolled : " + hpVal);
		calc.addElement("Constitution Bonus : " + ttlBonus);
		if (toughness > 0) {
			calc.addElement("Toughness : " + toughness);
		}
		if (imptoughness > 0) {
			calc.addElement("Improved Toughness : " + imptoughness);
		}
		if (suptoughness > 0) {
			calc.addElement("Superior Toughness : " + suptoughness);
		}
		if (ulttoughness > 0) {
			calc.addElement("Ultimate Toughness : " + ulttoughness);
		}
		if (dwtoughness > 0) {
			calc.addElement("Dwarven Toughness : " + dwtoughness);
		}
		if (drtoughness > 0) {
			calc.addElement("Dragon's Toughness : " + drtoughness);
		}
		if (gitoughness > 0) {
			calc.addElement("Giant's Toughness : " + gitoughness);
		}
		if (featBonus > 0) {
			calc.addElement("Heart of The Mountain : " + featBonus);
		}

		calc.setDisplayValue("" + total);
		return calc;
	}

	/**
	 * Get the Ammo value.
	 * 
	 * @return the Ammo value.
	 */
	public Vector<Weapon> getAmmo() {
		return ammo;
	}

	/**
	 * Set the Ammo value.
	 * 
	 * @param newAmmo
	 *            The new Ammo value.
	 */
	public void setAmmo(Vector<Weapon> newAmmo) {
		this.ammo = newAmmo;
	}

	/**
	 * Get the SpellResist value.
	 * 
	 * @return the SpellResist value.
	 */
	public String getSpellResist() {
		return spellResist;
	}

	/**
	 * Set the SpellResist value.
	 * 
	 * @param newSpellResist
	 *            The new SpellResist value.
	 */
	public void setSpellResist(String newSpellResist) {
		this.spellResist = newSpellResist;
		if (spellResist == null || spellResist.equals("null")) {
			spellResist = "-";
		}
	}

	/**
	 * Get the DamageReduction value.
	 * 
	 * @return the DamageReduction value.
	 */
	public String getDamageReduction() {
		return damageReduction;
	}

	/**
	 * Set the DamageReduction value.
	 * 
	 * @param newDamageReduction
	 *            The new DamageReduction value.
	 */
	public void setDamageReduction(String newDamageReduction) {
		this.damageReduction = newDamageReduction;
		if (damageReduction == null || damageReduction.equals("null")) {
			damageReduction = "-/-";
		}
	}

	/**
	 * Get the Money value.
	 * 
	 * @return the Money value.
	 */
	public MoneyBlock getMoney() {
		return money;
	}

	/**
	 * Set the Money value.
	 * 
	 * @param newMoney
	 *            The new Money value.
	 */
	public void setMoney(MoneyBlock newMoney) {
		this.money = newMoney;
	}

	/**
	 * Get the PlayerNotes value.
	 * 
	 * @return the PlayerNotes value.
	 */
	public String getPlayerNotes() {
		return playerNotes;
	}

	/**
	 * Set the PlayerNotes value.
	 * 
	 * @param newPlayerNotes
	 *            The new PlayerNotes value.
	 */
	public void setPlayerNotes(String newPlayerNotes) {
		this.playerNotes = newPlayerNotes;
	}

	/**
	 * Get the MiscArmorText value.
	 * 
	 * @return the MiscArmorText value.
	 */
	public String getMiscArmorText() {
		return miscArmorText;
	}

	/**
	 * Set the MiscArmorText value.
	 * 
	 * @param newMiscArmorText
	 *            The new MiscArmorText value.
	 */
	public void setMiscArmorText(String newMiscArmorText) {
		this.miscArmorText = newMiscArmorText;
	}

	/**
	 * Get the FullName value.
	 * 
	 * @return the FullName value.
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Set the FullName value.
	 * 
	 * @param newFullName
	 *            The new FullName value.
	 */
	public void setFullName(String newFullName) {
		this.fullName = newFullName;
	}

	/**
	 * Get the Hair value.
	 * 
	 * @return the Hair value.
	 */
	public String getHair() {
		return hair;
	}

	/**
	 * Set the Hair value.
	 * 
	 * @param newHair
	 *            The new Hair value.
	 */
	public void setHair(String newHair) {
		this.hair = newHair;
	}

	/**
	 * Get the Eyes value.
	 * 
	 * @return the Eyes value.
	 */
	public String getEyes() {
		return eyes;
	}

	/**
	 * Set the Eyes value.
	 * 
	 * @param newEyes
	 *            The new Eyes value.
	 */
	public void setEyes(String newEyes) {
		this.eyes = newEyes;
	}

	/**
	 * Get the Age value.
	 * 
	 * @return the Age value.
	 */
	public String getAge() {
		return age;
	}

	/**
	 * Set the Age value.
	 * 
	 * @param newAge
	 *            The new Age value.
	 */
	public void setAge(String newAge) {
		this.age = newAge;
	}

	/**
	 * Get the Weight value.
	 * 
	 * @return the Weight value.
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * Set the Weight value.
	 * 
	 * @param newWeight
	 *            The new Weight value.
	 */
	public void setWeight(String newWeight) {
		this.weight = newWeight;
	}

	/**
	 * Get the MiscArmor value.
	 * 
	 * @return the MiscArmor value.
	 */
	public int getMiscArmor() {
		return miscArmor;
	}

	/**
	 * Set the MiscArmor value.
	 * 
	 * @param newMiscArmor
	 *            The new MiscArmor value.
	 */
	public void setMiscArmor(int newMiscArmor) {
		this.miscArmor = newMiscArmor;
	}

	/**
	 * Get the BaseMovementOverride value.
	 * 
	 * @return the BaseMovementOverride value.
	 */
	public int getBaseMovementOverride() {
		return baseMovementOverride;
	}

	/**
	 * Set the BaseMovementOverride value.
	 * 
	 * @param newBaseMovementOverride
	 *            The new BaseMovementOverride value.
	 */
	public void setBaseMovementOverride(int newBaseMovementOverride) {
		this.baseMovementOverride = newBaseMovementOverride;
	}

	/**
	 * Get the Jump value.
	 * 
	 * @return the Jump value.
	 */
	public JumpBlock getJump() {
		return jump;
	}

	/**
	 * Set the Jump value.
	 * 
	 * @param newJump
	 *            The new Jump value.
	 */
	public void setJump(JumpBlock newJump) {
		this.jump = newJump;
	}

	/**
	 * Get the Height value.
	 * 
	 * @return the Height value.
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * Set the Height value.
	 * 
	 * @param newHeight
	 *            The new Height value.
	 */
	public void setHeight(String newHeight) {
		this.height = newHeight;
	}

	/**
	 * Get the Saves value.
	 * 
	 * @return the Saves value.
	 */
	public SaveBlock getSaves() {
		return saves;
	}

	/**
	 * Set the Saves value.
	 * 
	 * @param newSaves
	 *            The new Saves value.
	 */
	public void setSaves(SaveBlock newSaves) {
		this.saves = newSaves;
	}

	/**
	 * Get the Stats value.
	 * 
	 * @return the Stats value.
	 */
	public StatBlock getStats() {
		return stats;
	}

	/**
	 * Set the Stats value.
	 * 
	 * @param newStats
	 *            The new Stats value.
	 */
	public void setStats(StatBlock newStats) {
		this.stats = newStats;
	}

	/**
	 * Get the NaturalArmor value.
	 * 
	 * @return the NaturalArmor value.
	 */
	public int getNaturalArmor() {
		return naturalArmor;
	}

	/**
	 * Set the NaturalArmor value.
	 * 
	 * @param newNaturalArmor
	 *            The new NaturalArmor value.
	 */
	public void setNaturalArmor(int newNaturalArmor) {
		this.naturalArmor = newNaturalArmor;
	}

	/**
	 * Get the Languanges value.
	 * 
	 * @return the Languanges value.
	 */
	public Vector<PlayerLanguages> getLanguages() {
		return languages;
	}

	/**
	 * Set the Languanges value.
	 * 
	 * @param newLanguanges
	 *            The new Languanges value.
	 */
	public void setLanguages(Vector<PlayerLanguages> newLanguages) {
		this.languages = newLanguages;
	}

	public boolean isDropped(String location) {
		for (int i = 0; i < droppedLocations.size(); i++) {
			if (((String) droppedLocations.get(i)).equals(location)) {
				return true;
			}
		}
		return false;
	}

	public int calcWeightCarried() {
		double weight = 0;
		for (int i = 0; i < items.size(); i++) {
			PlayerItems item = (PlayerItems) items.get(i);
			if (!isDropped(item.getLocation())) {
				weight += (Double.parseDouble(item.getWeight()) * Double
						.parseDouble(item.getQuantity()));
			}
		}
		for (int i = 0; i < weapons.size(); i++) {
			Weapon w = (Weapon) weapons.get(i);
			if (w.isDisplayItem()) {
				weight += (Double.parseDouble(w.getWeight()) * Double
						.parseDouble(w.getQuantity()));
				;
			}
		}
		for (int i = 0; i < ammo.size(); i++) {
			Weapon w = (Weapon) ammo.get(i);
			weight += Double.parseDouble(w.getWeight());
		}
		if (armor != null && armor.getName() != null) {
			weight += Double.parseDouble(armor.getWeight());
		}
		if (shield != null && shield.getName() != null) {
			weight += Double.parseDouble(shield.getWeight());
		}

		return new Double(weight).intValue();
	}

	public Vector<PlayerItems> getLinkedItems() {
		Vector<PlayerItems> v = new Vector<PlayerItems>();

		for (int i = 0; i < weapons.size(); i++) {
			Weapon w = (Weapon) weapons.get(i);
			if (w.isDisplayItem()) {
				PlayerItems pi = new PlayerItems();
				pi.setLinked(true);
				pi.setWeight(w.getWeight());
				pi.setItemName(w.getName());
				pi.setCost(w.getCost());
				pi.setQuantity(w.getQuantity());
				pi.setLocation("Weapon");
				v.add(pi);
			}
		}

		for (int i = 0; i < ammo.size(); i++) {
			Weapon w = (Weapon) ammo.get(i);
			PlayerItems pi = new PlayerItems();
			pi.setLinked(true);
			pi.setWeight(w.getWeight());
			pi.setItemName(w.getName());
			pi.setCost(w.getCost());
			pi.setQuantity(""+(Double.parseDouble(w.getQuantity())/20));
			pi.setLocation("Weapon");
			v.add(pi);
		}

		if (armor != null && armor.getName() != null
				&& !armor.getName().equals("None")) {

			PlayerItems pi = new PlayerItems();
			pi.setLinked(true);
			pi.setWeight(armor.getWeight());
			pi.setItemName(armor.getName());
			pi.setCost(armor.getCost());
			pi.setQuantity("1");
			pi.setLocation("Armor");
			v.add(pi);
		}

		if (shield != null && shield.getName() != null
				&& !shield.getName().equals("None")) {
			PlayerItems pi = new PlayerItems();
			pi.setLinked(true);
			pi.setWeight(shield.getWeight());
			pi.setItemName(shield.getName());
			pi.setCost(shield.getCost());
			pi.setQuantity("1");
			pi.setLocation("Shield");
			v.add(pi);

		}

		return v;
	}

	public String getSize() {
		if (sizeOverride != null && !sizeOverride.equals("")
				&& !sizeOverride.equals("null")) {
			return sizeOverride;
		}
		return size;
	}

	public LoadLimits getLoadLimits() {
		LoadLimitsDAO lldb = new LoadLimitsDAO();
		return lldb.getLoadLimits(getStat("STRENGTH"), getSize());
	}

	public Vector<SpellLevel> getAllSpellsPerDay() {
		Vector<SpellLevel> v = new Vector<SpellLevel>();
		SpellLevel sl = new SpellLevel();
		sl.setClassName("Spell Level");
		sl.setLevel0("0");
		sl.setLevel1("1");
		sl.setLevel2("2");
		sl.setLevel3("3");
		sl.setLevel4("4");
		sl.setLevel5("5");
		sl.setLevel6("6");
		sl.setLevel7("7");
		sl.setLevel8("8");
		sl.setLevel9("9");
		v.add(sl);
		v.addAll(getArcaneSpellsPerDay());
		v.addAll(getDivineSpellsPerDay());
		return v;
	}

	public Vector<SpellLevel> getAllSpellsAvailable() {
		Vector<SpellLevel> v = new Vector<SpellLevel>();
		SpellLevel sl = new SpellLevel();
		sl.setClassName("Spell Level");
		sl.setLevel0("0");
		sl.setLevel1("1");
		sl.setLevel2("2");
		sl.setLevel3("3");
		sl.setLevel4("4");
		sl.setLevel5("5");
		sl.setLevel6("6");
		sl.setLevel7("7");
		sl.setLevel8("8");
		sl.setLevel9("9");
		v.add(sl);
		v.addAll(getArcaneSpellsAvailable());
		return v;
	}

	public Vector<CharClass> getCasterClasses() {
		Vector<CharClass> casterClasses = new Vector<CharClass>();
		for (int i = 0; i < classSet.size(); i++) {
			ClassSlot cs = classSet.get(i);
			CharClass cc = cs.getClassName();
			if (cc.isArcaneCaster() || cc.isDivineCaster()) {
				casterClasses.add(cc);
			}
		}
		return casterClasses;
	}

	public void markArcaneSpellUsed(int spellLevel) {
		boolean found = false;
		for (int i = 0; i < arcaneSpellsUsed.size(); i++) {
			UsedSpell us = arcaneSpellsUsed.get(i);
			if (us.getLevel() == spellLevel) {
				us.setCount(us.getCount() + 1);
				found = true;
				break;
			}
		}
		if (!found) {
			UsedSpell us = new UsedSpell();
			us.setCount(1);
			us.setLevel(spellLevel);
			us.setType("Arcane");
			arcaneSpellsUsed.add(us);
		}

	}

	public void markDivineSpellUsed(int spellLevel) {
		boolean found = false;
		for (int i = 0; i < divineSpellsUsed.size(); i++) {
			UsedSpell us = divineSpellsUsed.get(i);
			if (us.getLevel() == spellLevel) {
				us.setCount(us.getCount() + 1);
				found = true;
				break;
			}
		}
		if (!found) {
			UsedSpell us = new UsedSpell();
			us.setCount(1);
			us.setLevel(spellLevel);
			us.setType("Divine");
			divineSpellsUsed.add(us);
		}
	}

	public void markDivineSpellUnused(int spellLevel) {

		for (int i = 0; i < divineSpellsUsed.size(); i++) {
			UsedSpell us = divineSpellsUsed.get(i);
			if (us.getLevel() == spellLevel) {
				if (us.getCount() > 0) {
					us.setCount(us.getCount() - 1);
				}

				break;
			}
		}

	}

	public int getDivineSpellsUsed(int spellLevel) {
		int count = 0;
		for (int i = 0; i < divineSpellsUsed.size(); i++) {
			UsedSpell us = divineSpellsUsed.get(i);
			if (us.getLevel() == spellLevel) {
				count = us.getCount();
				break;
			}
		}
		return count;
	}

	public Vector<SpellLevel> getArcaneSpellsAvailable() {
		Vector<SpellLevel> v = new Vector<SpellLevel>();
		SpellLevelDAO sldb = new SpellLevelDAO();
		SpellLevel bonus = sldb.getBonusSpells(getStat("CHARISMA"));

		for (int i = 0; i < classSet.size(); i++) {
			ClassSlot cs = (ClassSlot) classSet.get(i);
			CharClass cc = cs.getClassName();
			if (cc.isArcaneCaster() && cc.isUnpreparedCaster()) {
				Vector<SpellLevel> spells = sldb.getSpellsKnown(cc.getId(), ""
						+ Math.max(cs.getLevel(), cs.getVirtualLevel()));
				if (spells.size() > 0) {
					SpellLevel s = (SpellLevel) spells.get(0);
					s = s.applyBonus(bonus);
					v.add(s);
				}
			}
		}
		return v;
	}

	public Vector<SpellLevel> getArcaneSpellsPerDay() {
		logger.log("calling from acpd");
		Vector<SpellLevel> v = new Vector<SpellLevel>();
		SpellLevelDAO sldb = new SpellLevelDAO();
		SpellLevel bonus = sldb.getBonusSpells(getStat("INTELLIGENCE"));
		SpellLevel chaBonus = sldb.getBonusSpells(getStat("CHARISMA"));
		for (int i = 0; i < classSet.size(); i++) {
			ClassSlot cs = classSet.get(i);
			CharClass cc = cs.getClassName();
			if (cc.isArcaneCaster()) {

				Vector<SpellLevel> spells = sldb.getSpellsPerDay(cc.getId(), ""
						+ Math.max(cs.getLevel(), cs.getVirtualLevel()));
				if (spells.size() > 0) {
					SpellLevel s = (SpellLevel) spells.get(0);
					if (cc.isUnpreparedCaster()) {
						s = s.applyBonus(chaBonus);
					} else {
						s = s.applyBonus(bonus);
					}
					v.add(s);
				}
			}
		}
		return v;
	}

	public int getTurnUndeadLevel(){
		int level = 0;
		for (int i = 0; i < classSet.size(); i++) {
			ClassSlot cs = classSet.get(i);
			CharClass cc = cs.getClassName();
			if (cc.isDivineCaster()) {
				level += cs.getLevel();
				if (cc.getTurningLevelMod() != null){
				 level += Integer.parseInt(cc.getTurningLevelMod());
				}
			}
		}
		return level;
	}
	
	public Vector<SpellLevel> getDivineSpellsPerDay() {
		if (divineSpellsPerDay == null) {
			logger.log("calling from dspd");
			Vector<SpellLevel> v = new Vector<SpellLevel>();
			SpellLevelDAO sldb = new SpellLevelDAO();
			SpellLevel bonus = sldb.getBonusSpells(getStat("WISDOM"));
			for (int i = 0; i < classSet.size(); i++) {
				ClassSlot cs = classSet.get(i);
				CharClass cc = cs.getClassName();
				if (cc.isDivineCaster()) {
					Vector<SpellLevel> spells = sldb.getSpellsPerDay(cc.getId(), ""
							+ Math.max(cs.getLevel(), cs.getVirtualLevel()));
					if (spells.size() > 0) {
						SpellLevel s = (SpellLevel) spells.get(0);
						s = s.applyBonus(bonus);
						v.add(s);
					}
				}
			}
			divineSpellsPerDay = v;
			return v;
		}
		return divineSpellsPerDay;
	}

	public boolean canCastAtLevel(String charClass, int level) {
		logger.log("calling from ccal");
		boolean canCast = false;

		SpellLevelDAO sldb = new SpellLevelDAO();
		for (int i = 0; i < classSet.size(); i++) {
			ClassSlot cs = classSet.get(i);
			CharClass cc = cs.getClassName();
			if (cc.getCharClass().equals(charClass)) {
				Vector<SpellLevel> spells = sldb.getSpellsPerDay(cc.getId(), ""
						+ Math.max(cs.getLevel(), cs.getVirtualLevel()));
				if (spells.size() > 0) {
					SpellLevel s = spells.get(0);
					canCast = (!s.getLevel(level).equals("-1"));

				}
			}
		}
		return canCast;
	}

	/**
	 * Get the RestArmor value.
	 * 
	 * @return the RestArmor value.
	 */
	public Armor getRestArmor() {
		return restArmor;
	}

	/**
	 * Set the RestArmor value.
	 * 
	 * @param newRestArmor
	 *            The new RestArmor value.
	 */
	public void setRestArmor(Armor newRestArmor) {
		this.restArmor = newRestArmor;
	}

	public Race getRaceObject() {
		RaceDAO rdb = new RaceDAO();
		return rdb.getRace(race);
	}

	public Vector<RacialAbility> getRacialAbilities() {

		RacialAbilityDAO radb = new RacialAbilityDAO();
		Vector<RacialAbility> v = radb.getRacialAbilities(race);

		return v;
	}

	public int getSizeMod() {
		InitDBC db = new InitDBC();
		return db.getSizeMod(getSize());
	}

	public Calculation getBaseAc() {
		Calculation calc = new Calculation();

		int ac = 10;
		calc.addElement("Base Ac : " + ac);
		
		calc.setDisplayValue("" + ac);
		return calc;
	}

	public int getFlatFootedAc(){
		return getAC()-getMaxDex();
	}
	
	public Calculation getTouchCalc(){
		return getTouchCalc(armor);
	}
	
	public Calculation getRestTouchCalc(){
		return getTouchCalc(restArmor);
	}
	
	public Calculation getTouchCalc(Armor a){

		Calculation calc = getBaseAc();
		int ac = Integer.parseInt(calc.getDisplayValue());
		
		InitDBC db = new InitDBC();
		// if the are not wearing armor or using a shield, check for the
		// unarmored feats
		if ((a == null || a.getName() == null
				|| a.getName().equals("No Armor") || a.getName()
				.equalsIgnoreCase("Mage Armor"))
				&& (shield == null || shield.getName() == null || shield
						.getName().equals("No Shield"))) {

			
			int level = new Double(getLevel()).intValue();
			// unarmored mastery
			if (hasFeat("414")) {
				int bonus = level;
				calc.addElement("Unarmored Mastery : " + bonus);
				ac += bonus;
			}
			// unarmored specialization
			else if (hasFeat("415")) {
				int bonus = level - (1 + (level / 4));
				calc.addElement("Unarmored Spec : " + bonus);
				ac += bonus;
			}
			// unarmored focus
			else if (hasFeat("413")) {
				int bonus = (level / 2);
				calc.addElement("Unarmored Focus : " + bonus);
				ac += bonus;
			}
			// unarmored proficiency
			else if (hasFeat("416")) {
				int bonus = 0;
				for (int i = 0; i <= level; i++) {
					if (i % 3 == 2) {
						bonus++;
					}
				}
				calc.addElement("Unarmored Prof : " + bonus);
				ac += bonus;
			}
		}
		
		ac += getMaxDex();// getBonus(getStats().getDex()); // add dex mod
		calc.addElement("Dex Bonus : "+getMaxDex()); 
		if (classSet.hasClass("Monk")) {
			ac += getBonus(getStat("WISDOM"));
			calc.addElement("Monk Wisdom Bonus : "+getBonus(getStat("WISDOM")));
			ClassAdvancement ca = classSet.getMonkAdvancement();
			int monkAc =  Integer.parseInt(ca.getRefSave().substring(1,
					ca.getRefSave().length()));
			ac += monkAc;
			calc.addElement("Monk AC : "+monkAc);
		}
		if (classSet.hasClass("Forsaker")) {
			if (classSet.getClassLevel("Forsaker") >= 3) {
				ac += getBonus(getStat("CONSTITUTION"));
			}
			calc.addElement("Forsaker Con Bonus : "+getBonus(getStat("CONSTITUTION"))); 
		}
	
		int sizeMod = db.getSizeMod(getSize());
		ac += sizeMod;
		calc.addElement("Size Mod : "+sizeMod);
		
		ac += getMiscArmor();
		calc.addElement("Misc Armor : "+getMiscArmor());
		
		ac += tempModSet.getTempTouchAc();
		calc.addElement("Temp Mods : "+tempModSet.getTempTouchAc());
		
		calc.setDisplayValue(""+ac);
		return calc;
	}
	
	public int getTouchAc() {
		
		return Integer.parseInt(getTouchCalc().getDisplayValue());
		
	}

	public int getPrecalcAc() {
		return precalcAc;
	}

	public void setPrecalcAc(int i) {
		precalcAc = i;
		super.setAC(i);
	}

	public int getAC() {
		int ac = 0;
		try {
			ac = Integer.parseInt(getArmorCalc().getDisplayValue());
		} catch (Exception e) {
			logger.log("error", "Exception calculating AC : " + e);
		}
		return ac;
	}

	public int getHP() {
		int hp = 0;
		try {
			hp = Integer.parseInt(getHpCalc().getDisplayValue());
		} catch (Exception e) {

		}
		return hp;
	}

	public Calculation getTurnAttempts(){
		int attempts = 3;
		Calculation c = new Calculation();
		c.addElement("Base : 3/day");
		
		attempts += getBonus(getStat("CHARISMA"));
		c.addElement("Charisma Bonus : "+getBonus(getStat("CHARISMA")));
		
		
		int bonus = getFeatCount("150")*4;
		c.addElement("Extra Turning : "+bonus);
		attempts += bonus;
		
		c.setDisplayValue(""+attempts);
		
		return c;
	}
	
	public Calculation getTurnCheck(){
		Calculation c = new Calculation();
		
		int checkBonus = 0;
		c.addElement("Base : 1d20");
		
		int chaBonus = getBonus(getStat("CHARISMA"));
		checkBonus += chaBonus;
		c.addElement("Charisma Bonus : "+chaBonus);
		
		if (hasFeat("914")){
			checkBonus -= 2;
			c.addElement("Empowered Turning : -2");
		}
		
		if (hasFeat("916")){
			int casterLevel = getTurnUndeadLevel();
			checkBonus += casterLevel;
			c.addElement("Heightened Turning : "+casterLevel);
		}
			
		if (hasFeat("167")){
			checkBonus += 4;
			c.addElement("Powerful Turning : "+4);
		}
		
		if (hasFeat("89")){
			checkBonus += 4;
			c.addElement("Steely Stare : "+4);
		}
		
		c.setDisplayValue("1d20+"+checkBonus);
		
		return c;
	}
	
	public Calculation getTurnDamage(){
		Calculation c = new Calculation();
		int checkBonus = 0;
		int baseDie = 2;
		String base = ("2d6");
		c.addElement("Base : "+base);
		
		int casterLevel = getTurnUndeadLevel();
		checkBonus += casterLevel;
		c.addElement("Caster Level : "+checkBonus);
		
		int chaBonus = getBonus(getStat("CHARISMA"));
		checkBonus += chaBonus;
		c.addElement("Charisma Bonus : "+chaBonus);
		
		if (hasFeat("914")){
			baseDie += 2;
			c.addElement("Empowered Turning : +2d6");
		}
		
		if (hasFeat("149")){
			baseDie += 1;
			c.addElement("Enhanced Turning : +1d6");
		}
		
		if (hasFeat("159")){
			checkBonus += 2;
			c.addElement("Master of Undeath : "+2);
		}
		
		if (hasFeat("916")){
			
			checkBonus -= casterLevel;
			c.addElement("Heightened Turning : -"+casterLevel);
		}
		
		c.setDisplayValue(baseDie+"d6+"+checkBonus);
		
		return c;
	}
	
	public int getNumberOfAttacks() {
		int attacks = 1;
		if (weapons.size() > 0) {
			Weapon w = (Weapon) weapons.get(0);
			String hitString = "";
			if (w.getRangedmelee().equalsIgnoreCase("MELEE")) {
				hitString = calcMeleeToHit(w).getDisplayValue();
			} else {
				hitString = calcRangedToHit(w).getDisplayValue();
			}
			int index = 0;
			while (hitString.indexOf("/", index) > 0) {
				attacks++;
				index = hitString.indexOf("/", index);
			}
		}
		return attacks;
	}

	public Calculation calcCritRange(Weapon w){
		Calculation calc = new Calculation();
		
		String baseStr = w.getCritStart();
		int base = 20;
		if (baseStr != null && !baseStr.equals("")){
			base = Integer.parseInt(baseStr);
		}
		calc.addElement("Base : "+w.getCritStart()	);
		int adjustment = 21 - base;
		
		
		// add improved critical
		if (w.isApplied("1309")){
			base -= adjustment;
			calc.addElement("Improved Critical "+(adjustment));
		}
		
		// add Eric House Rule improved critical
		if (w.isApplied("328")){
			base -= 1;
			calc.addElement("Improved Critical 1");
		}
		
		// add keen weapon
		if (w.isKeenWeapon()){
			base -= adjustment;
			calc.addElement("Keen "+(adjustment));
		}
			
		// add master assassin
		if (hasFeat("446")){
			base -= 1;
			calc.addElement("Assassin 1");
		}
		
		// add master assassin
		if (hasFeat("576")){
			base -= 1;
			calc.addElement("Master Assassin 1");
		}
			
		calc.setDisplayValue(base+"-20");
		
		return calc;
	}
	
	public Calculation calcCritMult(Weapon w){
		Calculation calc = new Calculation();
		
		String baseStr = w.getCritMult();
		
		int base = 2;
		if (baseStr != null && !baseStr.equals("")){
			base = Integer.parseInt(baseStr);
		}
		calc.addElement("Base "+base);
		
		// add bo master
		if (hasFeat("821") && w.getFeatClass().equals("Quarterstaff")){
			base += 1;
			calc.addElement("Bo Master : +1");
		}
		
		// add elite weapon mastery
		if (hasFeat("302")){
			base += 1;
			calc.addElement("Elite Weapon Mastery : +1");
		}

		// if the weapon is mercurial, get the modified base damage
		if (w.getMaterial().getName().equals("Mercurial")){
			if (w.getSizeIndex() == 4) {
				
				base += 1;
				calc.addElement("Mercurial : +1");
				
			}
			else if (w.getSizeIndex() > 4) {
				
				base += 2;
				calc.addElement("Mercurial : +2");
				
			}
		}
		calc.setDisplayValue("x"+base);
		
		return calc;
	}
	
	public Calculation calcDamage(Weapon w) {

		String type = w.getRangedmelee();
		Calculation calc = new Calculation();

		int adjust = 0;
		String damage = w.getDamage();

		// check for a damage override on the weapon
		if (w.getDamageOverride() != null && !w.getDamageOverride().equals("")) {
			damage = w.getDamageOverride();
		}

		// if it is an unarmed monk strike, use the damage from the monk
		// advancement table.
		else if (w.getName().startsWith("Unarmed strike")
				&& classSet.hasClass("Monk")) {
			ClassAdvancement ca = classSet.getMonkAdvancement();
			String dmgStr = ca.getFortSave();

			if (getSize().equals("Small")) {
				damage = dmgStr.substring(0, dmgStr.indexOf("/"));
			} else if (getSize().equals("Medium")) {
				damage = dmgStr.substring(dmgStr.indexOf("/") + 1, dmgStr
						.length());
			}
			calc.addElement("Monk Base " + damage);
		} else if (classSet.hasClass("Fighter Base") && hasFeat("2989")) {
			int monkFeats = 0;
			for (Feat f:featList) {
				if (f.isType("MONK")) {
					monkFeats += 1;
				}
			}
			int dieSizeIncrease = monkFeats/3;
			damage = "1d6";
			if (dieSizeIncrease > 0) {
				InitDBC db = new InitDBC();
				damage = db.getDamage(damage, dieSizeIncrease, getCampaign().getId());
			}
			calc.addElement("Monk Track Base " + damage);
		} else {
		
			calc.addElement("Base " + damage);
		}
		
		// if the weapon itself is oversize or undersize, get the modified base damage
		if (w.getSizeOverride() != null && !w.getSizeOverride().equals("")) {
			InitDBC db = new InitDBC();
			damage = db.getDamage(damage, w.getSize(), w.getSizeOverride(),getCampaign().getId());
			calc.addElement("Size Override, Base " + damage);
		}

		
		// SIZE INCREASE FEATS
		int sizeIncrease = 0;
		String tempDamage = "";
		
		// if they have weapon mastery, increase the damage die by one
		if (hasFeat("424", w.getFeatClass())) {
			InitDBC db = new InitDBC();
			sizeIncrease ++;
			tempDamage = db.getDamage(damage, sizeIncrease, getCampaign().getId());
			calc.addElement("Mastery, Base " + tempDamage);
		}

		// if they have barroom brawler feats applied, modify base damage
		if (w.isApplied("265")) {
				InitDBC db = new InitDBC();
				sizeIncrease++;
				tempDamage = db.getDamage(damage, sizeIncrease,getCampaign().getId());
				calc.addElement("Barroom Brawler, Base " + tempDamage);
		}
		
		// if they have natural weapon feats applied, modify base damage
		if (w.isApplied("883")) {
				InitDBC db = new InitDBC();
				sizeIncrease++;
				tempDamage = db.getDamage(damage, sizeIncrease,getCampaign().getId());
				calc.addElement("Natural Weapon, Base " + tempDamage);
		}
		
		// if they have improved natural weapon feats applied, modify base damage
		if (w.isApplied("880")) {
				InitDBC db = new InitDBC();
				sizeIncrease++;
				tempDamage = db.getDamage(damage, sizeIncrease,getCampaign().getId());
				calc.addElement("Improved Natural Weapon, Base " + tempDamage);
		}	
		
		// if the weapon is mercurial, get the modified base damage
		if (w.getMaterial().getName().equals("Mercurial")){
			if (w.getSizeIndex() >= 4){
				InitDBC db = new InitDBC();
				sizeIncrease++;
				tempDamage = db.getDamage(damage, sizeIncrease,getCampaign().getId());
				calc.addElement("Mercurial, Base " + tempDamage);
			}
		}
		
		// if they have warshaper class levels, modify base damage
		if (w.getUse().startsWith(("Natural")) && classSet.hasClass("Warshaper")) {
				InitDBC db = new InitDBC();
				sizeIncrease++;
				tempDamage = db.getDamage(damage, sizeIncrease,getCampaign().getId());
				calc.addElement("Warshaper, Base " + tempDamage);
		}	
		
		if (sizeIncrease > 0){
			InitDBC db = new InitDBC();
			damage = db.getDamage(damage, sizeIncrease,getCampaign().getId());
		}
		
		

		// if it is a magic weapon, add the bonus to damage
		if (w.getBonus() != null && !w.getBonus().equals("")) {
			adjust += Integer.parseInt(w.getBonus());
			calc.addElement("Enhancement +" + w.getBonus());
		}
		// if it is a melee weapon, add the strength bonus to damage
		if (type.equalsIgnoreCase("MELEE")) {
			double strBonus = getBonus(getStat("STRENGTH"));
			if (w.getUse().equals("Two Handed")) {
				if (hasFeat("409")) {
					strBonus = strBonus * 2;
					calc.addElement("Two Handed Power Strike +" + strBonus);
				} else {
					strBonus = strBonus * 1.5;
					calc.addElement("Two Handed +" + strBonus);
				}
			} else if (w.getUse().equals("Secondary")) {
				if (!hasFeat("352")) {
					strBonus = (strBonus / 2) + (strBonus % 2);
					calc.addElement("Off Hand Strength +" + strBonus);
				} else {

					calc.addElement("Strength +" + strBonus);
				}
			} else {
				calc.addElement("Strength +" + strBonus);
			}
			adjust += strBonus;

			// if they specialized in this weapon, add 2 to the damage
			if (hasSpecialization(w)) {
				calc.addElement("Specialization +2");
				adjust += 2;
			}

			// greater specialization
			if (hasFeat("1303")) {
				calc.addElement("Greater Specialization +2");
				adjust += 2;
			}
		}

		// add adjustment for sword mastery
		if (hasFeat("1263") && w.getFeatClass().equalsIgnoreCase("Sword")) {
			adjust += 1;
			calc.addElement("Sword Mastery +1");
		}

		// add adjustment for axe mastery
		if (hasFeat("263") && w.getFeatClass().equalsIgnoreCase("Axe")) {
			adjust += 1;
			calc.addElement("Axe Mastery +1");
		}

		// add adjustment for hammer mastery
		if (hasFeat("319") && w.getFeatClass().equalsIgnoreCase("Hammer")) {
			adjust += 1;
			calc.addElement("Hammer Mastery +1");
		}

		// calculate STR damage for mighty bows.
		if (w.getName().startsWith("Mighty")) {
			double strBonus = getBonus(getStat("STRENGTH"));
			int mightyBonus = Integer.parseInt(w.getName().substring(
					w.getName().indexOf("+") + 1, w.getName().indexOf(")")));
			if (strBonus < mightyBonus) {
				adjust += strBonus;
				calc.addElement("Mighty +" + strBonus);
			} else {
				adjust += mightyBonus;
				calc.addElement("Mighty +" + mightyBonus);
			}
		}

		// add adjustment for point blank shot, if applied.
		if (w.isApplied("110")) {
			adjust += 1;
			calc.addElement("Point Blank Shot +1");
		}

		// add adjustment for improved point blank shot, if applied.
		if (w.isApplied("102")) {
			adjust += 2;
			calc.addElement("Improved PB Shot +2");
		}

		// add adjustment for master archer, if applied.
		if (w.isApplied("104")) {
			adjust += 1;
			calc.addElement("Master Archer +1");
		}

		// if they specialized in this weapon, add 2 to the damage
		if (type.equalsIgnoreCase("RANGED")) {
			if (w.isApplied("425")) {
				calc.addElement("Specialization +2");
				adjust += 2;
			}
			// greater specialization
			if (w.isApplied("1303")) {
				calc.addElement("Greater Specialization +2");
				adjust += 2;
			}
		}

		// add adjustment for weapon of power
		if (w.isApplied("223")) {
			adjust += 2;
			calc.addElement("Weapon of Strength +2");
		}

		if (w.isApplied("909")) {
			calc.addElement("Divine Might " + getBonus(getStat("CHARISMA")));
			adjust += getBonus(getStat("CHARISMA"));
		}

		// add temp mods
		int tmpDmg = tempModSet.getTempDamage();
		if (tmpDmg > 0) {
			adjust += tmpDmg;
			calc.addElement("Temp Mod +" + tmpDmg);
		}

		int tempCond = tempModSet.getTempConditionalDamage(w);
		if (tempCond != 0) {
			adjust += tempCond;
			calc.addElement("Items " + tempCond);
		}

		// TODO : check out ability damage mods
		String abDamage = w.getAbilityDamage();
		if (!abDamage.equals("") && !abDamage.equals("0")) {
			damage += "(" + w.getAbilityDamage() + ")";
		}

		if (w.isDoubleDamageWeapon()) {
			adjust += adjust;
			String numDice = damage.substring(0, damage.indexOf("d"));
			int doubleDice = (Integer.parseInt(numDice) * 2);
			damage = doubleDice + damage.substring(damage.indexOf("d"));
			calc.addElement("Double Damage");
		}

		calc.setDisplayValue(damage + "+" + adjust);
		return calc;
	}

	public Calculation calcRangedToHit(Weapon w) {
		return calcRangedToHit(w, true);
	}
	
	
	public Calculation calcRangedToHit(Weapon w, boolean simplify) {
		Calculation calc = new Calculation();
		int adjust = 0;

		String type = w.getRangedmelee();
		String toHit = "";
		if (type.equalsIgnoreCase("RANGED") || !w.getRange().equals("-")) {

			String attackBonus = getRangedAttackBonus();
			if (w.getFeatClass().equalsIgnoreCase("Unarmed")) {
				attackBonus = getUnarmedAttackBonus();
				calc.addElement("Monk Weapon");
			}

			calc.addElement("Base " + attackBonus);

			// check proficiency
			if (!hasProficiency(w)) {
				adjust += -4;
				calc.addElement("Not Proficient -4");
			}

			// add ajustments for magic/masterwork
			if (w.getBonus() != null && !w.getBonus().equals("")) {
				adjust += Integer.parseInt(w.getBonus());
				calc.addElement("Enhancement +" + w.getBonus());
			} else if (w.isMasterwork()) {
				adjust += 1;
				calc.addElement("Masterwork +1");
			}

			// add adjustment for focus
			if (hasFocus(w)) {
				calc.addElement("Focus +1");
				adjust += 1;
			}
			// add adjustment for improved weapon focus
			if (hasImprovedFocus(w)) {
				adjust += 2;
				calc.addElement("Improved Focus +2");
			}

			// add adjustment for improved weapon focus
			if (hasSuperiorFocus(w)) {
				adjust += 2;
				calc.addElement("Superior Focus +2");
			}

			// add adjustment for axe mastery
			if (hasFeat("263") && w.getFeatClass().equalsIgnoreCase("Axe")) {
				adjust += 1;
				calc.addElement("Axe Mastery +1");
			}

			// add adjustment for knife fighter
			if (w.isApplied("344")) {
				int kf = getFeatCount("344");
				int value = 2 * kf;
				adjust += value;
				calc.addElement("Knife Fighter + " + value);
			}

			// add adjustment for temp attacks
			int tempAttack = tempModSet.getTempAttack();
			if (tempAttack > 0) {
				for (int i = 0; i < tempAttack; i++) {
					attackBonus = addAttack(attackBonus);

				}
				calc.addElement("Temporary Attacks " + tempAttack);
			}

			// add adjustment for Manyshot, if applied.
			if (w.isApplied("1126")) {
				adjust += -2;
				attackBonus = addAttack(attackBonus);
				int hbab = getHighestAttack(getBaseAttackBonus());
				if (hbab >= 11) {
					attackBonus = addAttack(attackBonus);
				}
				if (hbab >= 16) {
					attackBonus = addAttack(attackBonus);
				}
				calc.addElement("Manyshot -2");
			}

			// add adjustment for rapid shot, if applied.
			if (w.isApplied("116")) {
				adjust += -2;
				attackBonus = addAttack(attackBonus);

				calc.addElement("Rapid Shot -2");

				// if they have improved rapid shot, add the two back in.
				if (hasFeat("1097")) {
					adjust += 2;
					calc.addElement("Improved Rapid Shot +2");
				}
			}

			// add adjustment for speed weapon
			if (w.isSpeedWeapon()) {
				attackBonus = addAttack(attackBonus);
				calc.addElement("Speed Attack");
			}

			// add adjustment for point blank shot, if applied.
			if (w.isApplied("110")) {
				adjust += 1;
				calc.addElement("Point Blank Shot +1");
			}

			// add adjustment for improved point blank shot, if applied.
			if (w.isApplied("102")) {
				adjust += -1;
				calc.addElement("Improved PB Shot -1");
			}

			// add adjustment for weapon of power
			if (w.isApplied("223")) {
				adjust += 2;
				calc.addElement("Weapon of Strength");
			}

			// add adjustment for master archer, if applied.
			if (w.isApplied("104")) {
				adjust += 2;
				calc.addElement("Master Archer +2");
			}

			int temp = tempModSet.getTempRanged();
			if (temp != 0) {
				// adjust += temp;
				calc.addElement("Temp Modifiers " + temp);
			}

			int tempCond = tempModSet.getTempConditionalRanged(w);
			if (tempCond != 0) {
				adjust += tempCond;
				calc.addElement("Items " + tempCond);
			}

			toHit += adjustAttack(attackBonus, adjust);
		}
		if (simplify){
			toHit = simplifyAttacks(toHit);
		}
		calc.setDisplayValue(toHit);
		return calc;
	}

	public int calcAverageDamage(int ac) {
		if (weapons == null || weapons.size() < 1) {
			return 0;
		}

		Weapon w = (Weapon) weapons.get(0);
		int damage = calcAverageDamage(w, ac);

		if (w.getUse().equalsIgnoreCase("Primary")) {
			for (int i = 1; i < weapons.size(); i++) {
				w = (Weapon) weapons.get(i);
				if (w.getUse().equalsIgnoreCase("Secondary")) {
					damage += calcAverageDamage(w, ac);
					break;
				}
			}
		}

		if (w.getUse().equalsIgnoreCase("Double Toss 1")) {
			for (int i = 1; i < weapons.size(); i++) {
				w = (Weapon) weapons.get(i);
				if (w.getUse().equalsIgnoreCase("Double Toss 2")) {
					damage += calcAverageDamage(w, ac);
					break;
				}
			}
		}

		if (w.getUse().equalsIgnoreCase("Natural Weapon 1")) {
			for (int i = 1; i < weapons.size(); i++) {
				w = (Weapon) weapons.get(i);
				if (w.getUse().equalsIgnoreCase("Natural Weapon 2")) {
					damage += calcAverageDamage(w, ac);
					break;
				}
			}
			for (int i = 1; i < weapons.size(); i++) {
				w = (Weapon) weapons.get(i);
				if (w.getUse().equalsIgnoreCase("Natural Weapon 3")) {
					damage += calcAverageDamage(w, ac);
					break;
				}
			}
		}

		return damage;

	}

	public int calcAverageDamage(Weapon w, int ac) {

		String attacks = "";

		int bonus = 0;

		int numDice = 1;
		int abilityDamage = 0;

		// determine what type of weapon we are using, melee or ranged,
		// and get the appropriate to-hit numbers.
		if (w.getRangedmelee().equalsIgnoreCase("MELEE")) {
			attacks = calcMeleeToHit(w).getDisplayValue();
		} else {
			attacks = calcRangedToHit(w, false).getDisplayValue();
		}

		// split that value into the separate attacks.
		String[] attackArray = attacks.split("/");

		// get the damage formula for that weapon
		String damage = calcDamage(w).getDisplayValue();
		String die = damage;

		// if it does subdual or special damage, we can't calculate a value,
		// so just quit.
		if (damage.indexOf("*") >= 0) {
			return 0;
		}

		// check for damage done by weapon abilities (flaming, shock, etc.)
		if (damage.indexOf("(") >= 0) {

			String abDamage = damage.substring(damage.indexOf("(") + 1, damage
					.indexOf(")"));

			damage = (damage.substring(0, damage.indexOf("(")))
					+ (damage.substring(damage.indexOf(")") + 1, damage
							.length()));

			String[] parts = abDamage.split(",");
			for (int i = 0; i < parts.length; i++) {
				String token = parts[i];
				if (token.indexOf("d") > 0) {
					numDice = Integer.parseInt(token.substring(0, token
							.indexOf("d")));
				}
				die = damage.substring(token.indexOf("d") + 1, token.length());
				int dieSize = Integer.parseInt(die);
				abilityDamage += dieSize * numDice;
			}
		}

		if (damage.indexOf("+") >= 0) {
			bonus = Integer.parseInt(damage.substring(damage.indexOf("+") + 1,
					damage.length()));
			die = damage
					.substring(damage.indexOf("d") + 1, damage.indexOf("+"));

		}

		if (damage.indexOf("/") >= 0) {
			die = damage
					.substring(damage.indexOf("d") + 1, damage.indexOf("/"));
		}

		int dieSize = Integer.parseInt(die);

		if (damage.indexOf("d") > 0) {
			numDice = Integer
					.parseInt(damage.substring(0, damage.indexOf("d")));
		}

		int maxDmg = dieSize * numDice;
		maxDmg += abilityDamage;
		double avgDmg = 0;

		for (int i = 0; i < attackArray.length; i++) {
			String currAttack = attackArray[i].substring(1, attackArray[i]
					.length());
			int attackInt = Integer.parseInt(currAttack);
			int rollDiff = ac - attackInt;
			if (rollDiff <= 0) {
				avgDmg += (maxDmg / 2) + bonus;
			} else if (rollDiff >= 20) {
				avgDmg += ((maxDmg / 2) + bonus) * 0.05;
			} else {
				avgDmg += ((maxDmg / 2) + bonus) * (0.05 * (20 - rollDiff));
			}

		}

		return new Double(avgDmg).intValue();
	}

	public Calculation calcMeleeToHit(Weapon w) {
		Calculation calc = new Calculation();
		int adjust = 0;

		String type = w.getRangedmelee();
		String toHit = "";
		adjust = 0;
		if (type.equalsIgnoreCase("MELEE")) {

			// temp mods are already calculated in the call below
			String attackBonus = getMeleeAttackBonus();

			// add adjustment for monk attacks
			if (w.getFeatClass().equalsIgnoreCase("Unarmed") && w.useMonk()) {
				attackBonus = getUnarmedAttackBonus();
				if (hasFinesse(w)) {
					calc.addElement("Monk/Finesse Base " + attackBonus);
				} else {
					calc.addElement("Monk Base " + attackBonus);
				}
			}
			// add adjustment for finesse
			else if (hasFinesse(w)) {

				if (getBonus(getStat("DEXTERITY")) > getBonus(getStat("STRENGTH"))) {
					attackBonus = getRangedAttackBonus();
				}
				calc.addElement("Finesse Base " + attackBonus);
			} else {
				calc.addElement("Base " + attackBonus);
			}

			// check proficiency
			if (!hasProficiency(w)) {
				adjust += -4;
				calc.addElement("Not Proficient -4");
			}

			// add adjustments for magic enhancement and masterwork
			// (which don't stack, so check magic first).
			if (w.getBonus() != null && !w.getBonus().equals("")) {
				adjust += Integer.parseInt(w.getBonus());
				calc.addElement("Enhancement +"
						+ Integer.parseInt(w.getBonus()));
			} else if (w.isMasterwork()) {
				adjust += 1;
				calc.addElement("Masterwork +1");
			}

			// add adjustment for weapon focus
			if (hasFocus(w)) {
				adjust += 1;
				calc.addElement("Focus +1");
			}

			// add adjustment for improved weapon focus
			if (hasImprovedFocus(w)) {
				adjust += 2;
				calc.addElement("Improved Focus +2");
			}

			// add adjustment for sword mastery
			if (hasFeat("1263") && w.getFeatClass().equalsIgnoreCase("Sword")) {
				adjust += 1;
				calc.addElement("Sword Mastery +1");
			}

			// add adjustment for axe mastery
			if (hasFeat("263") && w.getFeatClass().equalsIgnoreCase("Axe")) {
				adjust += 1;
				calc.addElement("Axe Mastery +1");
			}

			// add adjustment for hammer mastery
			if (hasFeat("319") && w.getFeatClass().equalsIgnoreCase("Hammer")) {
				adjust += 1;
				calc.addElement("Hammer Mastery +1");
			}

			// add adjustment for weapon of power
			if (w.isApplied("223")) {
				adjust += 2;
				calc.addElement("Weapon of Strength +2");
			}

			// add adjustment for dual weapon primary
			if (w.getUse().equalsIgnoreCase("Primary")
					|| w.getUse().equalsIgnoreCase("Double Toss 1")) {
				adjust -= 6;
				calc.addElement("Primary -6");
				if (hasLightSecondary()) {
					adjust += 2;
					calc.addElement("Light Secondary +2");
				}
				// two weapon fighting
				if (hasFeat("411") || classSet.hasClass("Ranger")) {
					adjust += 2;
					calc.addElement("Two Weapon Fighting +2");
				}

			}

			// add adjustment for dual weapon off hand
			if (w.getUse().equalsIgnoreCase("Secondary")
					|| w.getUse().equalsIgnoreCase("Double Toss 2")) {
				adjust -= 10;
				calc.addElement("Secondary -10");
				if (hasLightSecondary()) {
					adjust += 2;
					calc.addElement("Light Secondary +2");
				}
				// two weapon fighting
				if (hasFeat("411") || classSet.hasClass("Ranger")) {
					adjust += 2;
					calc.addElement("Two Weapon Fighting +2");
				}
				// ambidex
				if (hasFeat("432") || classSet.hasClass("Ranger")) {
					adjust += 4;
					calc.addElement("Ambidexterity +4");
				}

				if (w.getUse().equalsIgnoreCase("Secondary")) {
					// reduce the number of attacks to one, unless
					// they have the improved two weapon fighting feat,
					// and in that case, reduce the number of attacks to two.
					int firstAttack = getAttack(attackBonus, 1);
					
					int secondAttack = getAttack(attackBonus, 2);
					attackBonus = "+" + firstAttack;
					if (hasFeat("972") && secondAttack > 0) {
						attackBonus += "/+" + secondAttack;
						calc.addElement("Improved Two Weapon Fighting");
					} else {
						calc.addElement("Secondary Weapon - One Attack");
					}
				}

			}

			// add adjustment for knife fighter
			if (w.isApplied("344")) {
				int kf = getFeatCount("344");
				int value = 2 * kf;
				adjust += value;
				calc.addElement("Knife Fighter + " + value);
			}

			// add adjustment for temp attacks
			int tempAttack = tempModSet.getTempAttack();
			if (tempAttack > 0) {
				for (int i = 0; i < tempAttack; i++) {
					attackBonus = addAttack(attackBonus);

				}
				calc.addElement("Temporary Attacks " + tempAttack);
			}

			// add adjustment for rapid shot, if applied.
			if (w.isApplied("116")) {
				adjust += -2;
				attackBonus = addAttack(attackBonus);

				calc.addElement("Rapid Shot -2");
			}

			// add adjustment for speed weapon
			if (w.isSpeedWeapon()) {
				attackBonus = addAttack(attackBonus);
				calc.addElement("Speed Attack");
			}

			// add adjustment for flurry of blows
			if (w.isApplied("20")) {

				if (classSet.getClassLevel("Monk") < 5) {
					attackBonus = addAttack(attackBonus);
					adjust += -2;
					calc.addElement("Flurry Of Blows -2");
				} else if (classSet.getClassLevel("Monk") < 9) {
					attackBonus = addAttack(attackBonus);
					adjust += -1;
					calc.addElement("Flurry Of Blows -1");
				} else if (classSet.getClassLevel("Monk") < 11) {
					attackBonus = addAttack(attackBonus);
					calc.addElement("Flurry Of Blows");
				} else if (classSet.getClassLevel("Monk") >= 9) {
					attackBonus = addAttack(attackBonus);
					attackBonus = addAttack(attackBonus);
					calc.addElement("Greater Flurry");
				}

			}

			// add adjustment frenzied attack
			if (w.isApplied("311")) {
				adjust += -4;
				attackBonus = addAttack(attackBonus);

				calc.addElement("Frenzied Attack -4");
			}

			// add adjustment for monkey grip
			if (w.isApplied("931")) {
				adjust += -1;
				calc.addElement("Monkey Grip -2");
			}

			// add adjustment lightening fists
			if (w.isApplied("933")) {
				adjust += -5;
				attackBonus = addAttack(attackBonus);
				attackBonus = addAttack(attackBonus);

				calc.addElement("Lightening Fists -5");
			}

			int temp = tempModSet.getTempMelee();
			if (temp != 0) {
				// adjust += temp;
				calc.addElement("Temp Modifiers " + temp);
			}

			int tempCond = tempModSet.getTempConditionalMelee(w);
			if (tempCond != 0) {
				adjust += tempCond;
				calc.addElement("Items " + tempCond);
			}

			toHit += adjustAttack(attackBonus, adjust);
		}
		calc.setDisplayValue(toHit);
		return calc;
	}

	public int getClassLevel(String s){
		return classSet.getClassLevel(s);
	}
	
	public int getSizeIndex(String s){
		String[] sizes = { "Fine", "Diminutive", "Tiny", "Small", "Medium",
				"Large", "Huge", "Gargantuan", "Colossal" };
		int size = 0;
		
		
		for (int i = 0; i < 9; i++) {
			if (sizes[i].equals(s)) {
				size = i;
			}
		
		}
		return size;
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
	
	
	public boolean hasLightSecondary() {
		for (int i = 0; i < weapons.size(); i++) {
			Weapon w = (Weapon) weapons.get(i);
			// return true if they have a secondary weapon that is either
			// tiny or small, or medium with the superior two weapon fighting
			// feat.
			if (w.getUse().equalsIgnoreCase("Secondary")
					&& (w.getSize().equals("Small")
							|| w.getSize().equals("Tiny") || (hasFeat("399") && w
							.getSize().equals("Medium")))) {
				return true;
			}
		}
		return false;
	}

	public int getHighestAttack(String bab) {
		StringTokenizer split = new StringTokenizer(bab, "/");
		String token = "";
		if (split.hasMoreTokens()) {
			token = split.nextToken();
			token = token.substring(1, token.length());
			return Integer.parseInt(token);
		}

		return 0;
	}

	public int getAttack(String bab, int number) {
		StringTokenizer split = new StringTokenizer(bab, "/");
		String token = "";
		int count = 1;
		int attack = 0;
		while (split.hasMoreTokens() && count <= number) {
			token = split.nextToken();
			token = token.substring(1, token.length());
			attack = Integer.parseInt(token);
			count++;
		}

		return attack;
	}

	public String addAttack(String bab) {
		StringTokenizer split = new StringTokenizer(bab, "/");
		String token = "";
		if (split.hasMoreTokens()) {
			token = split.nextToken();
		}

		return token + "/" + bab;
	}

	public String simplifyAttacks(String bab){
		String simple = "";
		StringTokenizer split = new StringTokenizer(bab, "/");
		String token = "";
		String prevToken = "";
		int count = 1;
		int atk = 0;
		while (split.hasMoreTokens()) {
			token = split.nextToken();
			
			if (token.equals(prevToken) && !prevToken.equals("")){
				count++;
			}else if (!prevToken.equals("")){
				if (count > 1){
					simple += "["+count+"x"+prevToken+"]/";
				}else{
					simple += prevToken+"/";
				}
				count = 1;
			}
			prevToken = token;
			atk++;
		}
		
		simple += token;
		
		return simple;
	}
	
	public String adjustAttack(String bab, int mod) {

		if (mod == 0) {
			return bab;
		}

		StringTokenizer split = new StringTokenizer(bab, "/");
		String token = "";
		String babStr = "+";
		while (split.hasMoreTokens()) {
			token = split.nextToken();
			token = token.substring(1, token.length());
			int attack = Integer.parseInt(token);
			babStr += (attack + mod);
			if (split.hasMoreTokens()) {
				babStr += "/+";
			}
		}

		return babStr;
	}

	public Calculation getArmorCalc() {
		Calculation c = getArmorCalc(armor); 
		setPrecalcAc(Integer.parseInt(c.getDisplayValue()));
		return c;
	}
	
	public Calculation getRestArmorCalc() {
		return getArmorCalc(restArmor);
	}
	
	public Calculation getArmorCalc(Armor a) {

		InitDBC db = new InitDBC();

		Calculation calc = getBaseAc();

		// base AC
		int ac = Integer.parseInt(calc.getDisplayValue());

		// add dex mod
		ac += getMaxDex();
		calc.addElement("Dex Bonus : " + getMaxDex());

		// if the character has monk levels, add the wisdom and monk level
		// bonuses.
		if (classSet.hasClass("Monk")) {
			int wisBonus = getBonus(getStat("WISDOM"));
			ac += wisBonus;
			calc.addElement("Wis Bonus : " + wisBonus);

			ClassAdvancement ca = classSet.getMonkAdvancement();
			int monkAc = Integer.parseInt(ca.getRefSave().substring(1,
					ca.getRefSave().length()));
			ac += monkAc;
			calc.addElement("Monk AC : +" + monkAc);
		}

		// if the character has forsaker levels, add the con bonus after level
		// 3.
		if (classSet.hasClass("Forsaker")) {
			if (classSet.getClassLevel("Forsaker") >= 3) {
				int conBonus = getBonus(getStat("CONSTITUTION"));
				ac += conBonus;
				calc.addElement("Forsaker AC : +" + conBonus);
			}
		}

		// add the armor mod for size
		int sizeMod = db.getSizeMod(getSize());
		ac += sizeMod;
		calc.addElement("Size Mod : " + sizeMod);

		// if the are not wearing armor or using a shield, check for the
		// unarmored
		// feats
		if ((a == null || a.getName() == null
				|| a.getName().equals("No Armor") || a.getName()
				.equalsIgnoreCase("Mage Armor"))
				&& (shield == null || shield.getName() == null || shield
						.getName().equals("No Shield"))) {

			
			int level = new Double(getLevel()).intValue();
			// unarmored mastery
			if (hasFeat("414")) {
				int bonus = level;
				calc.addElement("Unarmored Mastery : " + bonus);
				ac += bonus;
			}
			// unarmored specialization
			else if (hasFeat("415")) {
				int bonus = level - (1 + (level / 4));
				calc.addElement("Unarmored Spec : " + bonus);
				ac += bonus;
			}
			// unarmored focus
			else if (hasFeat("413")) {
				int bonus = (level / 2);
				calc.addElement("Unarmored Focus : " + bonus);
				ac += bonus;
			}
			// unarmored proficiency
			else if (hasFeat("416")) {
				int bonus = 0;
				for (int i = 0; i <= level; i++) {
					if (i % 3 == 2) {
						bonus++;
					}
				}
				calc.addElement("Unarmored Prof : " + bonus);
				ac += bonus;
			}
		}
		
		// add the bonuses for armor and armor related feats.
		if (a != null) {
			try {
				int armorbonus = Integer.parseInt(a.getArmorbonus())
						+ Integer.parseInt(a.getBonus());
				calc.addElement("Armor : " + armorbonus);
				ac += armorbonus;
			} catch (NumberFormatException nfe) {
				calc.addElement("Error in armor calc");
			}

			// armor focus
			if (hasFeat("252") && a.getGrade().equals("Heavy")) {
				calc.addElement("Armor Focus (Heavy) : 1");
				ac += 1;
			}
			if (hasFeat("253") && a.getGrade().equals("Light")) {
				calc.addElement("Armor Focus (Light) : 1");
				ac += 1;
			}
			if (hasFeat("254") && a.getGrade().equals("Medium")) {
				calc.addElement("Armor Focus (Medium) : 1");
				ac += 1;
			}

			// armor specialization
			if (hasFeat("259") && a.getGrade().equals("Heavy")) {
				calc.addElement("Armor Specialization (Heavy) : 2");
				ac += 2;
			}
			if (hasFeat("260") && a.getGrade().equals("Light")) {
				calc.addElement("Armor Specialization (Light) : 2");
				ac += 2;
			}
			if (hasFeat("261") && a.getGrade().equals("Medium")) {
				calc.addElement("Armor Specialization (Medium) : 2");
				ac += 2;
			}

			// armor mastery
			if (hasFeat("255") && a.getGrade().equals("Heavy")) {
				calc.addElement("Armor Mastery (Heavy) : 3");
				ac += 3;
			}
			if (hasFeat("256") && a.getGrade().equals("Light")) {
				calc.addElement("Armor Mastery (Light) : 3");
				ac += 3;
			}
			if (hasFeat("257") && a.getGrade().equals("Medium")) {
				calc.addElement("Armor Mastery (Medium) : 3");
				ac += 3;
			}
		}

		// add bonuses for shield and shield related feats
		if (shield != null && shield.getName() != null) {
			try {
				int shieldbonus = Integer.parseInt(shield.getArmorbonus())
						+ Integer.parseInt(shield.getBonus());
				calc.addElement("Shield : " + shieldbonus);
				ac += shieldbonus;
			} catch (NumberFormatException nfe) {
				calc.addElement("Error in shield calc");
			}

			// shield focus
			if (hasFeat("381")) {
				calc.addElement("Shield Focus : 1");
				ac += 1;
			}
			if (hasFeat("382")) {
				calc.addElement("Shield Specialization : 2");
				ac += 2;
			}

			if (hasFeat("385")) {
				calc.addElement("Shield Mastery : 3");
				ac += 3;
			}

		}

		// add natural armor
		int natarmor = getNaturalArmor();
		ac += natarmor;
		calc.addElement("Natural Armor : " + natarmor);

		// add misc armor
		int miscarmor = getMiscArmor();
		ac += miscarmor;
		calc.addElement("Misc Armor : " + miscarmor);

		// add any temporary mods from equipment, spells, etc.
		
		if (a == armor){
			calc.addAllElements(tempModSet.getTempAcCalc());
			ac += tempModSet.getTempAc();
		}else if (a == restArmor){
			calc.addAllElements(tempModSet.getTempRestAcCalc());
			ac += tempModSet.getTempRestAc();
		}
		calc.addAllElements(tempModSet.getTempNatAcCalc());
		ac += tempModSet.getTempNatAc();

		int ud = getUncannyDodge();
		if (ud > 0) {
			calc.addElement("Class Ability Bonus " + ud);
			ac += ud;
		}

		if (hasFeat("344")) {
			int acbonus = 2 * getFeatCount("344");
			calc.addElement("Knife Fighter + " + acbonus);
			ac += acbonus;
		}
		calc.setDisplayValue("" + ac);
		
		return calc;
	}

	
	/**
	 * Get the Weapons value.
	 * 
	 * @return the Weapons value.
	 */
	public Vector<Weapon> getWeapons() {
		return weapons;
	}

	/**
	 * Set the Weapons value.
	 * 
	 * @param newWeapons
	 *            The new Weapons value.
	 */
	public void setWeapons(Vector<Weapon> newWeapons) {
		this.weapons = newWeapons;
	}

	/**
	 * Get the Items value.
	 * 
	 * @return the Items value.
	 */
	public Vector<PlayerItems> getItems() {

		return items;
	}

	/**
	 * Set the Items value.
	 * 
	 * @param newItems
	 *            The new Items value.
	 */
	public void setItems(Vector<PlayerItems> newItems) {
		this.items = newItems;
	}

	/**
	 * Get the Shield value.
	 * 
	 * @return the Shield value.
	 */
	public Armor getShield() {
		return shield;
	}

	/**
	 * Set the Shield value.
	 * 
	 * @param newShield
	 *            The new Shield value.
	 */
	public void setShield(Armor newShield) {
		this.shield = newShield;
	}

	/**
	 * Get the Armor value.
	 * 
	 * @return the Armor value.
	 */
	public Armor getArmor() {
		return armor;
	}

	/**
	 * Set the Armor value.
	 * 
	 * @param newArmor
	 *            The new Armor value.
	 */
	public void setArmor(Armor newArmor) {
		this.armor = newArmor;
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

	public String listFormat() {
		String outStr = pad(name, 16);
		if (canLevelUp()) {
			outStr = "*" + outStr.substring(0, outStr.length() - 1);
		}
		outStr += pad("[" + (getNextLevel() - getXp()) + "]", 10);

		
		return outStr + pad(playerName, 12) + party;

	}

	public ClassSet getClassSet() {
		return classSet;
	}

	public void setClassSet(ClassSet classSet) {
		this.classSet = classSet;
	}

	public String getRanks(String s) {
		return getRankCalculation(s).getDisplayValue();
	}

	public Calculation getRankCalculation(String s) {

		// get the basic (player assigned) skill ranks
		Calculation calc = skillSet.getRankCalculation(s);

		double ranks = Double.parseDouble(calc.getDisplayValue());

		SkillDAO sdb = new SkillDAO();

		String skillId = sdb.getSkillByName(s).getId();

		// add the racial abilities to the ranks.
		Vector<RacialAbility> v = getRacialAbilities();
		for (int i = 0; i < v.size(); i++) {
			RacialAbility ra = (RacialAbility) v.get(i);
			if (ra.getAbilityName().equals("SKILL")) {

				String skillName = ra.getAbilityType();

				if (skillName.indexOf("(") > 0) {
					skillName = skillName.substring(0, skillName.indexOf("("));
				}

				if (skillName.equalsIgnoreCase(s)) {
					ranks += Integer.parseInt(ra.getAbilityValue());
					calc.addElement("Racial Bonus " + ra.getAbilityValue());
				}
			}
		}

		// check for feat bonuses
		for (int j = 0; j < featList.size(); j++) {

			Feat f = (Feat) featList.get(j);
			FeatEffectsDAO fedb = new FeatEffectsDAO();
			f.setEffects(fedb.getFeatEffects(f.getId()));

			for (int k = 0; k < f.getEffects().size(); k++) {
				FeatEffects fe = (FeatEffects) f.getEffects().get(k);

				if (fe.getEffectType().equals("SKILL")
						&& fe.getCrossRefId().equals(skillId)) {
					calc.addElement(f.getFeatName() + " +"
							+ fe.getEffectAmount());
					ranks += Integer.parseInt(fe.getEffectAmount());
				}
			}
		}

		calc.setDisplayValue("" + ranks);

		return calc;
	}

	public Calculation getScore(String s) {

		// get the base ranks
		Calculation calc = getRankCalculation(s);

		// add in the mod/misc values
		calc = skillSet.getScore(calc, s);

		double ranks = Double.parseDouble(calc.getDisplayValue());

		// add in the stat bonus, if there is an associated stat
		SkillDAO sdb = new SkillDAO();
		Skill sk = sdb.getSkillByName(s);
		if (sk.getAbility() != null && !sk.getAbility().equals("") && !sk.getAbility().equals("NONE")){
			int bonus = getBonus(getStat(sk.getAbility()));
			ranks += bonus;
			calc.addElement("Stat Bonus : " + bonus);
		}

		//		 add any temporary mods from equipment, spells, etc.
		if (sk.getAbility().equals("STRENGTH")){
			int tempStr = tempModSet.getTempStrCheck();
			calc.addElement("Temp Mod : "
					+ tempStr);
			ac += tempStr;
		}
		if (sk.getAbility().equals("CONSTITUTION")){
			int tempStr = tempModSet.getTempConCheck();
			calc.addElement("Temp Mod : "
					+ tempStr);
			ac += tempStr;
		}
		if (sk.getAbility().equals("INTELLIGENCE")){
			int tempStr = tempModSet.getTempIntCheck();
			calc.addElement("Temp Mod : "
					+ tempStr);
			ac += tempStr;
		}
		if (sk.getAbility().equals("DEXTERITY")){
			int tempStr = tempModSet.getTempDexCheck();
			calc.addElement("Temp Mod : "
					+ tempStr);
			ac += tempStr;
		}
		if (sk.getAbility().equals("WISDOM")){
			int tempStr = tempModSet.getTempWisCheck();
			calc.addElement("Temp Mod : "
					+ tempStr);
			ac += tempStr;
		}
		if (sk.getAbility().equals("CHARISMA")){
			int tempStr = tempModSet.getTempChaCheck();
			calc.addElement("Temp Mod : "
					+ tempStr);
			ac += tempStr;
		}
	
		
		calc.setDisplayValue("" + ranks);

		return calc;
	}

	public String getLoad() {

		int carried = calcWeightCarried();
		LoadLimits ll = getLoadLimits();
		String loadStr = "Light";

		int load = Integer.parseInt(ll.getLightLoad());
		if (load < carried) {
			load = Integer.parseInt(ll.getMediumLoad());
			if (load < carried) {
				loadStr = "Heavy";
			} else {
				loadStr = "Medium";
			}
		}

		return loadStr;
	}

	public int getMaxDex() {
		int dex = getBonus(getStat("DEXTERITY"));
		String load = getLoad();
		if (load.equals("Medium")) {
			if (dex > 3) {
				dex = 3;
			}
		} else if (load.equals("Heavy")) {
			if (dex > 1) {
				dex = 1;
			}
		}
		return dex;
	}

	public double getMaxRanks(Skill s) {
		return classSet.getMaxRanks(s);
	}

	public boolean hasHeavyArmor() {
		if (armor == null || armor.getGrade() == null) {
			return false;
		}
		return (!armor.getGrade().equals("Heavy"));
	}

	public Vector<Feat> getHpFeats() {
		Vector<Feat> hpFeats = new Vector<Feat>();
		for (int i = 0; i < getFeatList().size(); i++) {
			Feat f = (Feat) getFeatList().get(i);
			if (f.isHpFeat()) {
				hpFeats.add(f);
			}
		}
		return hpFeats;
	}

	public boolean hasFinesse(Weapon w) {
		String fc = w.getFeatClass();
		for (int i = 0; i < getFeatList().size(); i++) {
			Feat f = (Feat) getFeatList().get(i);
			if (f.getId().equals("422")) {
				if (f.getValue().equals(fc)) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean hasUnarmedFinesse() {
		String fc = "Unarmed";
		for (int i = 0; i < getFeatList().size(); i++) {
			Feat f = (Feat) getFeatList().get(i);
			if (f.getId().equals("422")) {
				if (f.getValue().equals(fc)) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean hasMastery(Weapon w) {
		String fc = w.getFeatClass();
		for (int i = 0; i < getFeatList().size(); i++) {
			Feat f = (Feat) getFeatList().get(i);
			if (f.getId().equals("424")) {
				if (f.getValue().equals(fc)) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean hasSpecialization(Weapon w) {
		String fc = w.getFeatClass();
		for (int i = 0; i < getFeatList().size(); i++) {
			Feat f = (Feat) getFeatList().get(i);
			if (f.getId().equals("425")) {
				if (f.getValue().equals(fc)) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean hasSuperiorFocus(Weapon w) {
		String fc = w.getFeatClass();
		for (int i = 0; i < getFeatList().size(); i++) {
			Feat f = (Feat) getFeatList().get(i);
			if (f.getId().equals("974")) {
				if (f.getValue().equals(fc)) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean hasImprovedFocus(Weapon w) {
		String fc = w.getFeatClass();
		for (int i = 0; i < getFeatList().size(); i++) {
			Feat f = (Feat) getFeatList().get(i);
			if (f.getId().equals("339")) {
				if (f.getValue().equals(fc)) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean hasFocus(Weapon w) {
		String fc = w.getFeatClass();
		for (int i = 0; i < getFeatList().size(); i++) {
			Feat f = (Feat) getFeatList().get(i);
			if (f.getId().equals("423")) {
				if (f.getValue().equals(fc)) {
					return true;
				}
			}
		}

		return false;
	}

	public int getFeatCount(String id) {
		int featCount = 0;
		for (int i = 0; i < getFeatList().size(); i++) {
			Feat f = (Feat) getFeatList().get(i);
			if (f.getId().equals(id)) {
				featCount++;
			}
		}
		return featCount;
	}

	public boolean hasFeat(String id, String qual) {
		for (int i = 0; i < getFeatList().size(); i++) {
			Feat f = (Feat) getFeatList().get(i);
			if (f.getId().equals(id) && f.getValue().equalsIgnoreCase(qual)) {
				return true;
			}
		}

		return false;
	}

	public boolean hasFeat(String id) {
		for (int i = 0; i < getFeatList().size(); i++) {
			Feat f = (Feat) getFeatList().get(i);
			if (f.getId().equals(id)) {
				return true;
			}
		}

		return false;
	}

	public int domainsAvailable() {
		int domct = 0;
		if (classSet.hasClass("Cleric")) {
			domct += 2;
		}
		if (classSet.getClassLevel("Contemplative") > 0) {
			domct += 1;
		}
		if (classSet.getClassLevel("Contemplative") > 5) {
			domct += 1;
		}
		if (classSet.getClassLevel("Divine Oracle") > 0) {
			domct += 1;
		}
		if (classSet.getClassLevel("War Priest") > 0) {
			domct += 1;
		}
		if (classSet.getClassLevel("War Priest") > 3) {
			domct += 1;
		}
		// tertiary domain feat
		if (hasFeat("202")) {
			domct += 1;
		}
		// divine protector feat
		if (hasFeat("826")) {
			domct += 1;
		}

		return domct - domains.size();
	}

	public boolean hasDomainAccess() {

		// divine protector feat
		if (classSet.getClassLevel("Cleric") > 0 || hasFeat("826")) {
			return true;
		}

		return false;
	}

	public boolean hasFavoredEnemies() {
		return classSet.hasClass("Ranger");
	}
	
	public boolean canSmite() {
		return classSet.hasClass("Paladin") || classSet.hasClass("Blackguard")
				|| hasDomain("Destruction");
	}

	public boolean canLayHands() {
		return classSet.hasClass("Paladin");
	}

	public boolean hasDomain(String s) {
		for (int i = 0; i < domains.size(); i++) {
			PlayerDomain pd = (PlayerDomain) domains.get(i);
			if (pd.getDomainName().equals(s) || pd.getDomainId().equals(s)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasSneakAttack() {

		return classSet.hasClass("Rogue") || classSet.hasClass("Assassin")
				|| classSet.hasClass("Order Of The Bow Initiate") || hasFeat("3014");

	}

	public String getSmite() {
		int level = 0;

		// get the class level. This may need to be expanded for other
		// classes than paladin.
		for (int i = 0; i < classSet.size(); i++) {
			ClassSlot cs = classSet.get(i);
			CharClass cc = cs.getClassName();
			if (cc.getCharClass().equals("Paladin")
					|| cc.getCharClass().equals("Blackguard")
					|| cc.getCharClass().equals("Cleric")) {
				level += cs.getLevel();
			}
		}

		// calculate the number of times you can smite per day. That is
		// one, plus however many times you took the "extra smite" feat.
		int smiteCount = getFeatCount("915");
		smiteCount++;

		// smite bonus is cha mod to hit, smite class level to damage.
		return "+" + getBonus(getStat("CHARISMA")) + " / +" + level + " "
				+ smiteCount + "x/day";
	}

	public Calculation getLayHands() {
		Calculation calc = new Calculation();
		int level = 0;

		// get the class level. This may need to be expanded for other
		// classes than paladin.
		for (int i = 0; i < classSet.size(); i++) {
			ClassSlot cs = (ClassSlot) classSet.get(i);
			CharClass cc = cs.getClassName();
			if (cc.getCharClass().equals("Paladin")) {
				level += cs.getLevel();
			}
		}
		int amt = level * getBonus(getStat("CHARISMA"));
		calc.addElement("Paladin : " + amt);
		calc.setDisplayValue("" + amt);
		return calc;
	}

	public Calculation getSneakAttack() {

		Calculation calc = new Calculation();
		String sneakAttack = "";
		String sneakAttack2 = "";
		String dieType = "d6";
		int dice = 0;
		int level = 0;
		for (int i = 0; i < classSet.size(); i++) {
			ClassSlot cs = (ClassSlot) classSet.get(i);
			CharClass cc = cs.getClassName();
			if (cc.getCharClass().equals("Rogue")
					|| cc.getCharClass().equals("Assassin")
					|| cc.getCharClass().equals("Order Of The Bow Initiate")) {
				level += cs.getLevel();
			}
		}
		if (hasFeat("3014")){
			level += getRogueFeatCount();
			
		}
		

		dice = ((level / 2) + 1);
		calc.addElement("Base " + dice + " dice");

		// add extra sneak attack for deadly edge.
		int edgeCount = getFeatCount("860");
		dice += edgeCount;
		if (edgeCount > 0) {
			calc.addElement("Deadly Edge + " + edgeCount + " dice");
		}

		// add extra die count for improved/superior sneak attack.
		if (hasFeat("863")) {
			dieType = "d8";
			calc.addElement("Improved Sneak Attack : d8");
		}
		if (hasFeat("867")) {
			dieType = "2d6";
			calc.addElement("Superior Sneak Attack : 2d6");
		}

		if (level > 0) {
			sneakAttack = "Melee Attack +4 / Damage +" + dice + "(" + dieType
					+ ") + " + (4 * (dice + getBonus(getStat("STRENGTH"))));
			sneakAttack2 = "Ranged Attack +4 / Damage +" + dice + "(" + dieType
					+ ") + " + (4 * (dice));
		}

		calc.setDisplayValue(sneakAttack);
		calc.setDisplayValue2(sneakAttack2);
		return calc;
	}

	public Vector<ClassProficiency> getWeaponProficiencies() {
		Vector<ClassProficiency> weaponProf = new Vector<ClassProficiency>();

		weaponProf.addAll(classSet.getWeaponProficiencies());

		Vector<RacialAbility> raceProf = getRaceObject().getProf();
		for (int i = 0; i < raceProf.size(); i++) {
			RacialAbility ra = (RacialAbility) raceProf.get(i);
			if (ra.getAbilityName().equals("PROFICIENCY")
					&& ra.getAbilityType().equals("WEAPON")) {
				ClassProficiency cp = new ClassProficiency();
				cp.setProfType(ra.getAbilityType());
				cp.setProfSubtype(ra.getAbilityType2());

				weaponProf.add(cp);
			}
		}
		return weaponProf;
	}

	public Vector<ClassProficiency> getArmorProficiencies() {
		Vector<ClassProficiency> weaponProf = new Vector<ClassProficiency>();
		weaponProf.addAll(classSet.getArmorProficiencies());
		Vector<RacialAbility> raceProf = getRaceObject().getProf();
		for (int i = 0; i < raceProf.size(); i++) {
			RacialAbility ra = (RacialAbility) raceProf.get(i);
			if (ra.getAbilityName().equals("PROFICIENCY")
					&& (ra.getAbilityType().equals("ARMOR") || ra
							.getAbilityType().equals("SHIELD"))) {
				ClassProficiency cp = new ClassProficiency();
				cp.setProfType(ra.getAbilityType());
				cp.setProfSubtype(ra.getAbilityType2());

				weaponProf.add(cp);
			}
		}
		return weaponProf;
	}

	public int getLevelPointsRemaining() {
		return (new Double(getLevel()).intValue() / 4) - stats.getLevelPoints();
	}

	public boolean hasProficiency(Armor a) {
		boolean prof = false;

		if (classSet.hasProficiency(a)) {
			return true;
		}

		// check racial proficiencies
		if (getRaceObject().hasProficiency(a)) {
			return true;
		}

		// check feats
		if (hasFeat("443") && a.getGrade().equals("Light")) {
			return true;
		}
		if (hasFeat("444") && a.getGrade().equals("Medium")) {
			return true;
		}
		if (hasFeat("442") && a.getGrade().equals("Heavy")) {
			return true;
		}

		return prof;
	}

	public boolean hasProficiency(Weapon w) {
		boolean prof = false;

		if (w.getCategory().equals("Unarmed")) {
			return true;
		}

		if (classSet.hasProficiency(w)) {
			return true;
		}

		// check racial proficiencies
		if (getRaceObject().hasProficiency(w)) {
			return true;
		}

		// check feats
		if (hasFeat("897", w.getFeatClass())) {
			return true;
		}

		// martial weapon proficiency
		if (hasFeat("574", w.getFeatClass())) {
			return true;
		}

		if (hasFeat("304", w.getName())) {
			return true;
		}

		return prof;

	}

	public Vector<String> getClassAbilities() {
		return classSet.getClassAbilities(getBonus(getStat("INT")));
	}

	public int getNextLevel() {
		String radj = getRaceObject().getLevelAdjustment();
		int ra = 0;
		if (radj != null && !radj.equals("")){
			ra = Integer.parseInt(radj);
		}
		
		int level = new Double(getLevel()).intValue();
		int next = 1;

		for (int i = 1; i <= level+ra; i++) {
			next += i;
		}
		next--;
		next = next * 1000;

		return next;
	}

	public int getCurrLevel() {
		String radj = getRaceObject().getLevelAdjustment();
		int ra = 0;
		if (radj != null && !radj.equals("")){
			ra = Integer.parseInt(radj);
		}
		
		int level = new Double(getLevel()).intValue();
		level--;
		int next = 1;

		for (int i = 1; i <= level+ra; i++) {
			next += i;
		}
		next--;
		next = next * 1000;

		return next;
	}
	
	public boolean canLevelUp() {
		
		String radj = getRaceObject().getLevelAdjustment();
		int ra = 0;
		if (radj != null && !radj.equals("")){
			ra = Integer.parseInt(radj);
		}
		
		int level = new Double(getLevel()).intValue();
		int next = 1;

		for (int i = 1; i <= level+ra; i++) {
			next += i;
		}
		next--;
		next = next * 1000;

		return (next <= xp);
	}

	public Calculation getLanguagesRemaining() {

		Calculation calc = new Calculation();

		// start with your intelligence bonus
		int remaining = getBonus(getStat("INTELLIGENCE"));

		// but don't subtract anything for a negative intelligence
		if (remaining < 0) {
			remaining = 0;
		}
		calc.addElement("Base " + remaining);

		int racial = 0;
		// next add any racial language ability
		Vector<RacialAbility> v = getRacialAbilities();
		for (int i = 0; i < v.size(); i++) {
			RacialAbility ra = (RacialAbility) v.get(i);

			if (ra.getAbilityName().equals("SPEAKS")) {
				racial++;
			}
		}
		if (racial == 0) {
			racial++;
		}
		remaining += racial;
		calc.addElement("Racial " + racial);

		// next add any points in the speaks language skill
		int skill = new Double(Double.parseDouble(getScore("Speak Language").getDisplayValue()))
				.intValue();
		calc.addElement("Skill " + skill);
		remaining += skill;

		// now remove one for each language known, unless it is "special"
		for (int i = 0; i < languages.size(); i++) {
			PlayerLanguages pl = (PlayerLanguages) languages.get(i);
			if (!pl.getLanguage().startsWith("SP")) {
				remaining--;
			}
		}

		
		calc.setDisplayValue("" + remaining);
		return calc;
	}

	public int getUncannyDodge() {
		// add up the uncanny dodge bonuses from player classes.
		// update - don't do that, get ac bonus instead.
		return classSet.getAcBonus();
	}

	public Calculation getFeatsRemaining() {
		Calculation calc = new Calculation();
		
		// calculate the available number of feats (start with one, the
		// beginning feat).
		int feats = 1;
		calc.addElement("Inital Feat : 1");
		
		// calculate the number of bonus feats from char classes
		int classFeats = classSet.getBonusFeats();
		feats += classFeats; 
		calc.addElement("Class Feats : "+classFeats);
		
		// get the racial feats, if any
		int racialFeats = 0;
		Vector<RacialAbility> v = getRacialAbilities();
		for (int i = 0; i < v.size(); i++) {
			RacialAbility ra = (RacialAbility) v.get(i);
			if (ra.getAbilityType().equals("FEAT(S)")) {
				if (ra.getAbilityType2().equals("AT 1st LEVEL")) {
					feats += Integer.parseInt(ra.getAbilityValue());
					racialFeats  += Integer.parseInt(ra.getAbilityValue());
				}
				if (ra.getAbilityType2().equals("PER LEVEL")) {
					feats += new Double(getLevel()).intValue();
					racialFeats += new Double(getLevel()).intValue();
				}
			}
		}
		if (racialFeats > 0){
			calc.addElement("Racial Feats : "+racialFeats);
		}
		
		// add one feat per 3 levels
		feats += new Double(getLevel()).intValue() / 3;
		calc.addElement("Level Feats : "+new Double(getLevel()).intValue() / 3);
		
		// look for any temp mods granting feats
		int tempFeats = 0;
		for (int i = 0; i < tempModSet.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) tempModSet.get(i);
		
			if (!StrManip.isNullOrEmpty(ptm.getFeatMod())) {
				int featMod = Integer.parseInt(ptm.getFeatMod());
				if (featMod != 0){
					feats+= featMod;
					tempFeats += featMod;
				}
			}
		}
		calc.addElement("Temp Mods : "+tempFeats);
		
		// now subtract all the used ones, ignoring any marked "Special"
		for (int i = 0; i < featList.size(); i++) {
			Feat f = (Feat) featList.get(i);
			if (!f.getSpecial().equals("Y")) {
				feats--;
			}
		}

		
		calc.setDisplayValue(""+feats);
		return calc;
	}

	public int getSkillsRemaining() {
		return getSkillsRemaining(null);
	}

	public int getSkillsRemaining(String classId) {

		int bonus = getBonus(getStat("INTELLIGENCE"));

		int skills = classSet.getClassSkillsRemaining(classId, bonus, hasFeat("3014"));
		Vector<RacialAbility> v = getRacialAbilities();
		
		// If this is the first class, apply any racial points that 
		// accrue at first level
		if (classId == null
				|| classId.equals(((ClassSlot) classSet.get(0)).getClassName()
						.getId())) {
			

			for (int i = 0; i < v.size(); i++) {
				RacialAbility ra = (RacialAbility) v.get(i);
				if (ra.getAbilityType().equals("SKILL POINT(S)")) {
					if (ra.getAbilityType2().equals("AT 1st LEVEL")) {
						skills += Integer.parseInt(ra.getAbilityValue());
					}
				}
			}
		}
		
		// apply any racial points that apply per level regardless of which
		// class it is.
		for (int i = 0; i < v.size(); i++) {
			RacialAbility ra = (RacialAbility) v.get(i);
			if (ra.getAbilityType().equals("SKILL POINT(S)")) {
				if (ra.getAbilityType2().equals("PER LEVEL")) {
					skills += (new Double(classSet.getClassLevel(classId)).intValue() * Integer.parseInt(ra
							.getAbilityValue()));
				}
			}
		}	
		
		return skills;

	}

	public int getFavoredEnemiesRemaining(){
		int fe = classSet.getFavoredEnemies();
		if (favoredEnemies != null){
			fe -= favoredEnemies.size();
		}
		return fe;
	}
	
	public int getRestAc() {
		
		return Integer.parseInt(getRestArmorCalc().getDisplayValue());
				
	}

	public int getRestTouchAc() {
		
		return Integer.parseInt(getRestTouchCalc().getDisplayValue());
		
	}

	public int getStat(String s) {
		if (s.equals("INTELLIGENCE")) {
			return stats.getInt() + tempModSet.getTempInt() + intMod;
		} else if (s.equals("DEXTERITY")) {
			return stats.getDex() + tempModSet.getTempDex() + dexMod;
		} else if (s.equals("WISDOM")) {
			return stats.getWis() + tempModSet.getTempWis() + wisMod;
		} else if (s.equals("STRENGTH")) {
			return stats.getStr() + tempModSet.getTempStr() + strMod;
		} else if (s.equals("CHARISMA")) {
			return stats.getCha() + tempModSet.getTempCha() + chaMod;
		} else if (s.equals("CONSTITUTION")) {
			return stats.getCon() + tempModSet.getTempCon() + conMod;
		}
		return 0;
	}

	public Calculation calcWillSave() {
		int willSave = 0;
		Calculation calc = new Calculation();

		// add the base + misc
		willSave = saves.getBaseWill();
		calc.addElement("Base : " + willSave);

		// add the ability score
		willSave += saves.getAbilWill();
		calc.addElement("Ability Score : " + saves.getAbilWill());

		// add the misc amount
		willSave += saves.getMiscWill();
		calc.addElement("Misc : " + saves.getMiscWill());

		// add temp modifiers
		willSave += tempModSet.getTempWill();
		calc.addElement("Temp Mods : " + tempModSet.getTempWill());

		// add racial modifiers
		Vector<RacialAbility> v = getRacialAbilities();
		for (int i = 0; i < v.size(); i++) {
			RacialAbility ra = (RacialAbility) v.get(i);
			if (ra.getAbilityName().equals("SAVING THROW")) {
				if (ra.getAbilityType().equals("WILL")) {
					willSave += Integer.parseInt(ra.getAbilityValue());
					calc.addElement("Racial Bonus : " + ra.getAbilityValue());
				}
			}
		}

		if (classSet.hasClass("Paladin")) {
			willSave += getBonus(getStat("CHARISMA"));
			calc.addElement("Divine Grace : " + getBonus(getStat("CHARISMA")));
		}

		if (!classSet.hasClass("Paladin") && classSet.hasClass("Blackguard")) {
			willSave += getBonus(getStat("CHARISMA"));
			calc.addElement("Dark Blessing : " + getBonus(getStat("CHARISMA")));
		}

		// bullheaded feat
		if (hasFeat("1002")) {
			willSave += 1;
			calc.addElement("Bullheaded : 1");
		}

		// discipline feat
		if (hasFeat("1036")) {
			willSave += 1;
			calc.addElement("Discipline : 1");
		}

		// experienced adventurer feat
		if (hasFeat("495")) {
			willSave += 1;
			calc.addElement("Experienced Adventurer : 1");
		}

		// flexible feat
		if (hasFeat("509")) {
			willSave += 1;
			calc.addElement("Flexible : 1");
		}

		// fortress feat
		if (hasFeat("513")) {
			willSave += getBonus(getStat("INTELLIGENCE"));
			calc.addElement("Fortress of Mind : "
					+ getBonus(getStat("INTELLIGENCE")));
		}

		// indefeat feat
		if (hasFeat("542")) {
			willSave += 1;
			calc.addElement("Indefatigable : 1");
		}

		// luck of heros feat
		if (hasFeat("1121")) {
			willSave += 1;
			calc.addElement("Luck of Heroes : 1");
		}

		// Consumption feat
		if (hasFeat("243")) {
			int bonus = getFeatCount("243") * 3;
			willSave += bonus;
			calc.addElement("Consumption : " + bonus);
		}

		// Corruption feat
		if (hasFeat("244")) {
			int bonus = getFeatCount("244") * 2;
			willSave += bonus;
			calc.addElement("Corruption : " + bonus);
		}

		// iron will feat
		if (hasFeat("549")) {
			willSave += 2;
			calc.addElement("Iron Will : 2");
		}

		// will of stone
		if (hasFeat("427")) {
			willSave += 3;
			calc.addElement("Will of Stone : 3");
		}

		// draconic will feat
		if (hasFeat("478")) {
			willSave += 3;
			calc.addElement("Draconic Will : 3");
		}

		// sociopath feat
		if (hasFeat("651")) {
			willSave += 3;
			calc.addElement("Sociopath : 3");
		}

		calc.setDisplayValue("" + willSave);
		return calc;
	}

	public Calculation calcReflexSave() {
		int refSave = 0;
		Calculation calc = new Calculation();

		// add the base + misc
		refSave = saves.getBaseRef();
		calc.addElement("Base : " + refSave);

		// add the ability score
		refSave += saves.getAbilRef();
		calc.addElement("Ability Score : " + saves.getAbilRef());

		// add the misc amount
		refSave += saves.getMiscRef();
		calc.addElement("Misc : " + saves.getMiscRef());

		// add temp modifiers
		refSave += tempModSet.getTempRef();
		calc.addElement("Temp Mods : " + tempModSet.getTempRef());

		// add racial modifiers
		Vector<RacialAbility> v = getRacialAbilities();
		for (int i = 0; i < v.size(); i++) {
			RacialAbility ra = (RacialAbility) v.get(i);
			if (ra.getAbilityName().equals("SAVING THROW")) {
				if (ra.getAbilityType().equals("REFLEX")) {
					willSave += Integer.parseInt(ra.getAbilityValue());
					calc.addElement("Racial Bonus : " + ra.getAbilityValue());
				}
			}
		}

		if (classSet.hasClass("Paladin")) {
			refSave += getBonus(getStat("CHARISMA"));
			calc.addElement("Divine Grace : " + getBonus(getStat("CHARISMA")));
		}

		if (!classSet.hasClass("Paladin") && classSet.hasClass("Blackguard")) {
			refSave += getBonus(getStat("CHARISMA"));
			calc.addElement("Dark Blessing : " + getBonus(getStat("CHARISMA")));
		}

		// athletic feat
		if (hasFeat("448")) {
			refSave += 1;
			calc.addElement("Athletic : 1");
		}

		// danger sense feat
		if (hasFeat("474")) {
			refSave += getBonus(getStat("WISDOM"));
			calc.addElement("Danger Sense : " + getBonus(getStat("WISDOM")));
		}

		// experienced adventurer feat
		if (hasFeat("495")) {
			refSave += 1;
			calc.addElement("Experienced Adventurer : 1");
		}

		// flexible feat
		if (hasFeat("509")) {
			refSave += 1;
			calc.addElement("Flexible : 1");
		}

		// Scoundrel's luck feat
		if (hasFeat("635")) {
			refSave += getBonus(getStat("CHARISMA"));
			calc.addElement("Scoundrel's Luck : "
					+ getBonus(getStat("CHARISMA")));
		}

		// snakeblood feat
		if (hasFeat("1238")) {
			refSave += 1;
			calc.addElement("Snakeblood : 1");
		}

		// luck of heros feat
		if (hasFeat("1121")) {
			refSave += 1;
			calc.addElement("Luck of Heroes : 1");
		}

		// lightening reflex feat
		if (hasFeat("566")) {
			refSave += 2;
			calc.addElement("Lightening Reflexes : 2");
		}

		// astonishing reflex feat
		if (hasFeat("447")) {
			refSave += 2;
			calc.addElement("Astonishing Reflexes : 2");
		}

		// Consumption feat
		if (hasFeat("243")) {
			int bonus = getFeatCount("243") * 3;
			refSave += bonus;
			calc.addElement("Consumption : " + bonus);
		}

		// Corruption feat
		if (hasFeat("244")) {
			int bonus = getFeatCount("244") * 2;
			refSave += bonus;
			calc.addElement("Corruption : " + bonus);
		}

		calc.setDisplayValue("" + refSave);
		return calc;
	}

	public Calculation calcFortitudeSave() {
		int fortSave = 0;
		Calculation calc = new Calculation();

		// add the base + misc
		fortSave = saves.getBaseFort();
		calc.addElement("Base : " + fortSave);

		// add the ability score
		fortSave += saves.getAbilFort();
		calc.addElement("Ability Score : " + saves.getAbilFort());

		// add the misc amount
		fortSave += saves.getMiscFort();
		calc.addElement("Misc : " + saves.getMiscFort());

		// add temp modifiers
		fortSave += tempModSet.getTempFort();
		calc.addElement("Temp Mods : " + tempModSet.getTempFort());

		// add racial modifiers
		Vector<RacialAbility> v = getRacialAbilities();
		for (int i = 0; i < v.size(); i++) {
			RacialAbility ra = (RacialAbility) v.get(i);

			if (ra.getAbilityName().equals("SAVING THROW")) {
				if (ra.getAbilityType().equals("FORTITUDE")) {
					willSave += Integer.parseInt(ra.getAbilityValue());
					calc.addElement("Racial Bonus : " + ra.getAbilityValue());
				}
			}
		}

		if (classSet.hasClass("Paladin")) {
			fortSave += getBonus(getStat("CHARISMA"));
			calc.addElement("Divine Grace : " + getBonus(getStat("CHARISMA")));
		}

		if (!classSet.hasClass("Paladin") && classSet.hasClass("Blackguard")) {
			fortSave += getBonus(getStat("CHARISMA"));
			calc.addElement("Dark Blessing : " + getBonus(getStat("CHARISMA")));
		}

		// athletic feat
		if (hasFeat("448")) {
			fortSave += 1;
			calc.addElement("Athletic : 1");
		}

		// experienced adventurer feat
		if (hasFeat("495")) {
			fortSave += 1;
			calc.addElement("Experienced Adventurer : 1");
		}

		// indefeat feat
		if (hasFeat("542")) {
			fortSave += 1;
			calc.addElement("Indefatigable : 1");
		}

		// painful ecstasy feat
		if (hasFeat("165")) {
			fortSave += 1;
			calc.addElement("Painful Ecstasy : 1");
		}

		// roughneck feat
		if (hasFeat("632")) {
			fortSave += getBonus(getStat("STRENGTH"));
			calc.addElement("Roughneck : " + getBonus(getStat("STRENGTH")));
		}

		// survival instincts feat
		if (hasFeat("672")) {
			fortSave += 1;
			calc.addElement("Survival Instincts : 1");
		}

		// luck of heros feat
		if (hasFeat("1121")) {
			fortSave += 1;
			calc.addElement("Luck of Heroes : 1");
		}

		// great fortitude feat
		if (hasFeat("518")) {
			fortSave += 2;
			calc.addElement("Great Fortitude : 2");
		}

		// incredible fortitude feat
		if (hasFeat("541")) {
			fortSave += 3;
			calc.addElement("Incredible Fortitude : 3");
		}

		// Consumption feat
		if (hasFeat("243")) {
			int bonus = getFeatCount("243") * 3;
			fortSave += bonus;
			calc.addElement("Consumption : " + bonus);
		}

		// Corruption feat
		if (hasFeat("244")) {
			int bonus = getFeatCount("244") * 2;
			fortSave += bonus;
			calc.addElement("Corruption : " + bonus);
		}

		calc.setDisplayValue("" + fortSave);
		return calc;
	}

	public boolean hasPrereq(Prereq fp) {
		boolean passes = false;
		String type = fp.getPrereqType();
		// check stat requirements
		if (type.equalsIgnoreCase("STAT")) {
			if (getStat(fp.getPrereqName()) >= Integer.parseInt(fp
					.getPrereqVal().trim())) {
				passes = true;
			}
		}
		// check the count of feats of a particular class
		else if (type.equalsIgnoreCase("FEAT_CLASS")){
			int featCount = 0;
			for (int i = 0; i < getFeatList().size(); i++) {
				Feat f = (Feat) getFeatList().get(i);
			
				if (f.getType().equalsIgnoreCase(fp.getPrereqName()) ||
					f.getType2().equalsIgnoreCase(fp.getPrereqName()) ||
					f.getType3().equalsIgnoreCase(fp.getPrereqName())){
					featCount++;					
				}
			}
			if (featCount >= Integer.parseInt(fp.getPrereqVal())){
				passes = true;
			}
		}
		// check feat requirements
		else if (type.equalsIgnoreCase("FEAT")) {
			// check both the character selected feats
			for (int i = 0; i < getFeatList().size(); i++) {
				Feat f = (Feat) getFeatList().get(i);

				if (f.getId().equals(fp.getCrossRefId())
						|| f.getFeatName().equalsIgnoreCase(fp.getPrereqName())) {
					passes = true;
					break;
				}
			}
			// and those conferred as class abilities
			Vector<ClassAbilities> v = classSet.getAbilities();
			for (int i = 0; i < v.size(); i++) {
				ClassAbilities ca = (ClassAbilities) (v.get(i));
				if (ca.getName().equalsIgnoreCase(fp.getPrereqName())) {
					passes = true;
					break;
				}
			}

		} else if (type.equalsIgnoreCase("LEVEL")) {
			passes = true;

		} else if (type.equalsIgnoreCase("CLASS")) {
			for (int i = 0; i < classSet.size(); i++) {
				ClassSlot cs = (ClassSlot) classSet.get(i);
				if (cs.getClassName().getCharClass().equalsIgnoreCase(
						fp.getPrereqName())) {
					passes = true;
				}
			}
		} else if (type.equalsIgnoreCase("MISC")) {
			if (fp.getPrereqName().equals("BAB")) {
				String bab = getBaseAttackBonus();
				int minBab = Integer.parseInt(fp.getPrereqVal());
				int actBab = 0;
				if (bab.indexOf("/") > 0) {
					actBab = Integer.parseInt(bab
							.substring(1, bab.indexOf("/")));
				} else {
					actBab = Integer.parseInt(bab.substring(1, bab.length()));
				}
				passes = actBab >= minBab;
			}
		}
		else if (type.equalsIgnoreCase("ABILITY")) {
			
				
				passes = classSet.hasAbility(fp.getPrereqName());
			
		} else if (type.equalsIgnoreCase("ALIGNMENT")) {
			passes = (getAlignment().equalsIgnoreCase(fp.getPrereqName()) || getAlignment()
					.indexOf(
							fp.getPrereqName().substring(
									fp.getPrereqName().indexOf("ANY ") + 4,
									fp.getPrereqName().length())) >= 0);
		} else if (type.equalsIgnoreCase("RACE")) {
			if (race.equalsIgnoreCase(fp.getPrereqType())) {
				passes = true;
			}
		} else if (type.equalsIgnoreCase("SKILL")) {
			String skill = fp.getPrereqName();
			if (skill.lastIndexOf("(") > 0){
				skill = skill.substring(0, skill.lastIndexOf("("));
			}
			try {
				if (Double.parseDouble(getRanks(skill)) >= Integer.parseInt(fp.getPrereqVal())){
					passes = true;
				}
			}catch (NumberFormatException nfe){
				nfe.printStackTrace();
			}
		}else if (type.equalsIgnoreCase("Min Size")) {
			if (getSizeIndex() >= getSizeIndex(fp.getPrereqName())){
				passes = true;
			}
		}else if (type.equalsIgnoreCase("Max Size")) {
			if (getSizeIndex() <= getSizeIndex(fp.getPrereqName())){
				passes = true;
			}
		}
		else {
			passes = true;
		}

		return passes;
	}

	public String getFullMoveModifier() {
		String mod = "*4";
		if ((armor != null && armor.getGrade() != null && armor.getGrade()
				.equalsIgnoreCase("Heavy"))
				|| getLoad().equals("Heavy")) {
			mod = "*3";
		}
		// if they have the run feat, fullmove is *5
		else if (hasFeat("633")) {
			mod = "*5";
		}
		return mod;
	}

	public Calculation getFullMovement() {

		Calculation calc = new Calculation();

		if ((armor != null && armor.getGrade() != null && armor.getGrade()
				.equalsIgnoreCase("Heavy"))
				|| getLoad().equals("Heavy")) {
			calc.addElement("Heavy Armor/Load : base*3");
			calc.setDisplayValue("" + (getBaseMovement() * 3));
		}
		// if they have the run feat, fullmove is *5
		else if (hasFeat("633")) {
			calc.setDisplayValue("" + (getBaseMovement() * 5));
			calc.addElement("Run Feat : base*5");
		} else {
			calc.setDisplayValue("" + (getBaseMovement() * 4));
			calc.addElement("baseMove * 4");
		}

		return calc;

	}

	public Calculation getFiveFootStep() {

		Calculation calc = new Calculation();

		int add = 0;
		if (getBaseMovement() % 20 > 0) {
			add = 5;
		}
		calc.addElement("Base : CEIL(baseMv/20)*5");
		calc.setDisplayValue("" + ((getBaseMovement() / 20) * 5 + add));

		return calc;
	}

	public int getArcaneFail() {
		int arcaneFail = 0;
		if (armor != null && armor.getArcanefail() != null) {
			arcaneFail += Integer.parseInt(armor.getArcanefail());
		}
		if (shield != null && shield.getArcanefail() != null) {
			arcaneFail += Integer.parseInt(shield.getArcanefail());
		}
		return arcaneFail;
	}

	public Calculation getArmorCheckPenalty() {
		Calculation calc = new Calculation();
		int checkPenalty = 0;
		int shieldPenalty = 0;
		if (armor != null && armor.getCheckpenalty() != null) {
			checkPenalty += Integer.parseInt(armor.getCheckpenalty());
			calc.addElement("Armor Base Penalty " + checkPenalty);

			// reduce the check penalty for armor focus
			if (armor.getGrade().equals("Heavy") && hasFeat("252")) {
				checkPenalty++;
				calc.addElement("Armor Focus (Heavy) : +1");
			}
			if (armor.getGrade().equals("Light") && hasFeat("253")) {
				checkPenalty++;
				calc.addElement("Armor Focus (Light) : +1");
			}
			if (armor.getGrade().equals("Medium") && hasFeat("254")) {
				checkPenalty++;
				calc.addElement("Armor Focus (Medium) : +1");
			}

			// reduce the penalty for armor mastery
			if (armor.getGrade().equals("Heavy") && hasFeat("255")) {
				checkPenalty += 3;
				calc.addElement("Armor Mastery (Heavy) : +3");
			}
			if (armor.getGrade().equals("Light") && hasFeat("256")) {
				checkPenalty += 3;
				calc.addElement("Armor Mastery (Light) : +3");
			}
			if (armor.getGrade().equals("Medium") && hasFeat("257")) {
				checkPenalty += 3;
				calc.addElement("Armor Mastery (Medium) : +3");
			}

			// reduce the penalty for armor specialization
			if (armor.getGrade().equals("Heavy") && hasFeat("259")) {
				checkPenalty += 2;
				calc.addElement("Armor Specialization (Heavy) : +2");
			}
			if (armor.getGrade().equals("Light") && hasFeat("260")) {
				checkPenalty += 3;
				calc.addElement("Armor Specialization (Light) : +2");
			}
			if (armor.getGrade().equals("Medium") && hasFeat("261")) {
				checkPenalty += 3;
				calc.addElement("Armor Specialization (Medium) : +2");
			}

			if (armor.isMasterwork()) {
				calc.addElement("Masterwork armor : +1");
			}

			// check penalty cannot be positive
			if (checkPenalty > 0) {
				checkPenalty = 0;
			}
		}
		if (shield != null && shield.getCheckpenalty() != null) {
			shieldPenalty = Integer.parseInt(shield.getCheckpenalty());
			calc.addElement("Shield Base " + shieldPenalty);
			// reduce the penalty for sheild focus
			if (hasFeat("381")) {
				shieldPenalty++;
				calc.addElement("Shield Focus : +1");
			}

			// reduce the penalty for sheild mastery
			if (hasFeat("382")) {
				shieldPenalty += 3;
				calc.addElement("Shield Mastery : +3");
			}

			// reduce the penalty for sheild specialization
			if (hasFeat("385")) {
				shieldPenalty += 2;
				calc.addElement("Shield Specialization : +2");
			}

			if (shield.isMasterwork()) {
				calc.addElement("Masterwork Shield : +1");
			}

			// check penalty cannot be positive
			if (shieldPenalty > 0) {
				shieldPenalty = 0;
			}
		}
		checkPenalty += shieldPenalty;

		calc.setDisplayValue("" + checkPenalty);
		return calc;
	}

	/**
	 * Get the BaseMovement value.
	 * 
	 * @return the BaseMovement value.
	 */
	public int getBaseMovement() {

		return Integer.parseInt(calcBaseMovement().getDisplayValue());

	}

	public Calculation calcBaseMovement() {
		
		Calculation calc = new Calculation();

		// basemovement is set to the base movement rate for your racial type,
		// or the monk speed for your highest monk level, whichever is greater.
		int baseSpeed = baseMovement;
		int move = baseSpeed;

		calc.addElement("Base Movement : " + baseSpeed);

		if (baseMovementOverride != 0) {
			
			baseSpeed = baseMovementOverride;
			move = baseSpeed;
			calc.addElement("Override : " + baseSpeed);
		} else {
			
			// if you are wearing heavy armor, your speed will decrease
			if (armor != null) {
				baseSpeed = armor.getSpeed(move);
				calc.addElement("Armor Adjusted : " + baseSpeed);
			}

			// if you are carrying a heavy load, your speed will also decrease
			int loadSpeed = LoadLimits.getSpeed(move, getLoad());

			if (loadSpeed < baseSpeed) {
				baseSpeed = loadSpeed;
				calc.addElement("Load Adjusted : " + baseSpeed);
			}

			
			if (classSet.hasClass("Barbarian") && armor != null
					&& armor.getGrade() != null
					&& !armor.getGrade().equals("Heavy")
					&& !getLoad().equals("Heavy")) {

				
				calc.addElement("Fast Movement : +10");
				baseSpeed += 10;
			}

		
			// add adjustment for rapid movement feat
			if (hasFeat("620")) {
				int rapidSpeed = 5 * getFeatCount("620");
				baseSpeed += rapidSpeed;
				calc.addElement("Rapid Movement : +" + rapidSpeed);
			}
			// fast movement 1
			if (hasFeat("2993")) {
				baseSpeed += 5;
				calc.addElement("Fast Movement 1 : +5 ");
			}
			// fast movement 2
			if (hasFeat("2994")) {
				baseSpeed += 10;
				calc.addElement("Fast Movement 2 : +10 ");
			}
			// fast movement 3
			if (hasFeat("2995")) {
				baseSpeed += 15;
				calc.addElement("Fast Movement 3 : +15 ");
			}
			// fast movement 4
			if (hasFeat("2996")) {
				baseSpeed += 30;
				calc.addElement("Fast Movement 4 : +30 ");
			}

		}
		
		calc.setDisplayValue("" + baseSpeed);

		return calc;
	}

	/**
	 * Set the BaseMovement value.
	 * 
	 * @param newBaseMovement
	 *            The new BaseMovement value.
	 */
	public void setBaseMovement(int newBaseMovement) {
		this.baseMovement = newBaseMovement;
	}

	public String getRangedAttackBonus(int i) {
		return getBaseAttackBonus(i + getBonus(getStat("DEXTERITY"))
				+ getSizeMod());
	}

	public String getMeleeAttackBonus(int i) {
		return getBaseAttackBonus(i + getBonus(getStat("STRENGTH"))
				+ getSizeMod() + tempModSet.getTempMelee());

	}

	public String getRangedAttackBonus() {
		return getBaseAttackBonus(getBonus(getStat("DEXTERITY")) + getSizeMod()
				+ tempModSet.getTempRanged());
	}

	public String getMeleeAttackBonus() {
		return getBaseAttackBonus(getBonus(getStat("STRENGTH")) + getSizeMod()
				+ tempModSet.getTempMelee());
	}

	public String getBaseAttackBonus() {
		return getBaseAttackBonus(0);
	}

	public String getUnarmedAttackBonus() {
		int attack1 = 0;
		int attack2 = 0;
		int attack3 = 0;
		int attack4 = 0;
		int attack5 = 0;
		int attack6 = 0;
		
		int mod = getBonus(getStat("STRENGTH"));
		if (hasUnarmedFinesse()) {
			mod = getBonus(getStat("DEXTERITY"));
		}

		// if this is a monk, then use the special monk unarmed calc.
		if (classSet.hasClass("Monk")) {

			ClassAdvancement ca = classSet.getMonkAdvancement();

			String bab = ca.getBaseAttack();
			StringTokenizer split = new StringTokenizer(bab, "/");
			String token = "";
			if (split.hasMoreTokens()) {
				token = split.nextToken();
				token = token.substring(1, token.length());
				attack1 += Integer.parseInt(token);
			}
			if (split.hasMoreTokens()) {
				token = split.nextToken();
				token = token.substring(1, token.length());
				attack2 += Integer.parseInt(token);
			}
			if (split.hasMoreTokens()) {
				token = split.nextToken();
				token = token.substring(1, token.length());
				attack3 += Integer.parseInt(token);
			}
			if (split.hasMoreTokens()) {
				token = split.nextToken();
				token = token.substring(1, token.length());
				attack4 += Integer.parseInt(token);
			}
			if (split.hasMoreTokens()) {
				token = split.nextToken();
				token = token.substring(1, token.length());
				attack5 += Integer.parseInt(token);
			}

			if (attack1 - 5 > 0) {
				attack2 = attack1 - 5;
			}
			if (attack2 - 5 > 0) {
				attack3 = attack2 - 5;
			}
			if (attack3 - 5 > 0) {
				attack4 = attack3 - 5;
			}
			if (attack4 - 5 > 0) {
				attack5 = attack4 - 5;
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
		}else if (classSet.hasClass("Fighter Base") && hasFeat("2989")) {
			
			//int monkFeats = 0;
			attack1 = classSet.getClassLevel("Fighter Base");
			
			//for (Feat f:featList) {
			//	if (f.isType("MONK")) {
			//		monkFeats += 1;
			//	}
		//	}
			//attack1 = monkFeats;
		
			if (attack1 - 3 > 0) {
				attack2 = attack1 - 3;
			}
			if (attack2 - 3 > 0) {
				attack3 = attack2 - 3;
			}
			if (attack3 - 3 > 0) {
				attack4 = attack3 - 3;
			}
			if (attack4 - 3 > 0) {
				attack5 = attack4 - 3;
			}
			if (attack5 - 3 > 0) {
				attack6 = attack5 - 3;
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
			if (attack6 > 0) {
				babStr += "/+" + (attack6 + mod);
			}
			return babStr;
		}else {
			
		}
		
		

		// otherwise, just use the meelee bonus
		return getMeleeAttackBonus();
	}

	/**
	 * Get the BaseAttackBonus value.
	 * 
	 * @return the BaseAttackBonus value.
	 */
	public String getBaseAttackBonus(int mod) {
		return classSet.getBaseAttackBonus(mod);
	}

	/**
	 * Get the Deity value.
	 * 
	 * @return the Deity value.
	 */
	public String getDeity() {
		return deity;
	}

	/**
	 * Set the Deity value.
	 * 
	 * @param newDeity
	 *            The new Deity value.
	 */
	public void setDeity(String newDeity) {
		this.deity = newDeity;
		if (deity == null || deity.equals("null")) {
			deity = "";
		}
	}

	/**
	 * Get the PlayerName value.
	 * 
	 * @return the PlayerName value.
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Set the PlayerName value.
	 * 
	 * @param newPlayerName
	 *            The new PlayerName value.
	 */
	public void setPlayerName(String newPlayerName) {
		this.playerName = newPlayerName;
	}

	/**
	 * Get the Xp value.
	 * 
	 * @return the Xp value.
	 */
	public int getXp() {
		return xp;
	}

	/**
	 * Set the Xp value.
	 * 
	 * @param newXp
	 *            The new Xp value.
	 */
	public void setXp(int newXp) {
		this.xp = newXp;
	}

	/**
	 * Get the Level value.
	 * 
	 * @return the Level value.
	 */
	public double getLevel() {
		return new Double(classSet.getLevel());
	}

	public void setLevel(int i) {

	}

	public DCharacter() {
		picture = "images/chars/party.jpg";
		icon = "images/qmark.jpg";
		setPType("PLAYER");
		gender = "M";
		sleepShift = "0";
		marchOrder = "0";
	}

	public DCharacter(int i, String s) {
		picture = "images/chars/party.jpg";
		icon = "images/qmark.jpg";
		initMod = i;
		name = s;
		setPType("PLAYER");
		gender = "M";
		sleepShift = "0";
		marchOrder = "0";
	}

	public void setInitMod(int i) {
		initMod = i;
	}

	public int getInitMod() {
		return initMod;
	}
	
	public void setName(String s) {
		name = s;
	}

	public int getMod() {
		
		return Integer.parseInt(calcMod().getDisplayValue());
	}

	public Calculation calcMod() {
		Calculation calc = new Calculation();
		int mod = getBonus(getStat("DEXTERITY"));
		calc.addElement("Base " + mod);
		mod += initMod;
		calc.addElement("Mod "+initMod);
		if (hasFeat("333")) {
			mod += 4;
			calc.addElement("Improved Init +4");
		}
		if (hasFeat("317")) {
			mod += 4;
			calc.addElement("Greater Improved Init +4");
		}
		if (hasFeat("618")) {
			mod += 2;
			calc.addElement("Quickening +2");
		}
		calc.setDisplayValue("" + mod);
		return calc;
	}

	public String getName() {
		return name;
	}

	public String getParty() {
		return party;
	}

	public void setInit(int i) {
		initRoll = i;
	}

	public int getInit() {
		return initRoll;
	}

	public String getRace() {
		return race;
	}

	public String getAlignment() {
		return alignment;
	}

	public void setRace(String s) {
		race = s;
	}

	public void setAlignment(String s) {
		alignment = s;
	}

	public void setParty(String s) {
		party = s;
	}

	public String getNamePad(String s) {
		String outStr = s;
		for (int i = s.length(); i < 25; i++) {
			outStr += " ";
		}
		return outStr;
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

	/**
	 * Get the ModNotes value.
	 * 
	 * @return the ModNotes value.
	 */
	public String getModNotes() {
		return modNotes;
	}

	/**
	 * Set the ModNotes value.
	 * 
	 * @param newModNotes
	 *            The new ModNotes value.
	 */
	public void setModNotes(String newModNotes) {
		this.modNotes = newModNotes;
	}

	/**
	 * Get the Picture value.
	 * 
	 * @return the Picture value.
	 */
	public String getPicture() {
		return picture;
	}

	/**
	 * Set the Picture value.
	 * 
	 * @param newPicture
	 *            The new Picture value.
	 */
	public void setPicture(String newPicture) {
		this.picture = newPicture;
	}

	/**
	 * Get the FavoredEnemies value.
	 * 
	 * @return the FavoredEnemies value.
	 */
	public Vector<PlayerFavoredEnemy> getFavoredEnemies() {
		return favoredEnemies;
	}

	/**
	 * Set the FavoredEnemies value.
	 * 
	 * @param newFavoredEnemies
	 *            The new FavoredEnemies value.
	 */
	public void setFavoredEnemies(Vector<PlayerFavoredEnemy> newFavoredEnemies) {
		this.favoredEnemies = newFavoredEnemies;
	}

	/**
	 * Get the SpellsKnown value.
	 * 
	 * @return the SpellsKnown value.
	 */
	public Vector<PlayerSpells> getSpellsKnown() {
		return spellsKnown;
	}

	/**
	 * Set the SpellsKnown value.
	 * 
	 * @param newSpellsKnown
	 *            The new SpellsKnown value.
	 */
	public void setSpellsKnown(Vector<PlayerSpells> newSpellsKnown) {
		this.spellsKnown = newSpellsKnown;
	}

	public Vector<PlayerSpells> getSpellsMemorized() {
		return spellsMemorized;
	}

	public void setSpellsMemorized(Vector<PlayerSpells> spellsMemorized) {
		this.spellsMemorized = spellsMemorized;
	}

	public Vector<PlayerSpells> getSpellsMemorized(String level, String cclass) {

		Vector<PlayerSpells> v = new Vector<PlayerSpells>();
		for (int i = 0; i < spellsMemorized.size(); i++) {
			PlayerSpells ps = (PlayerSpells) spellsMemorized.get(i);
			if (ps.getLevel().indexOf(cclass + " " + level) >= 0) {
				v.add(ps);
			}
		}
		return v;
	}

	public Vector<PlayerSpells> getSpellsKnown(String level, String cclass) {
		logger.log("Getting spells for "+level+" "+cclass);
		Vector<PlayerSpells> v = new Vector<PlayerSpells>();
		for (int i = 0; i < spellsKnown.size(); i++) {
			PlayerSpells ps = (PlayerSpells) spellsKnown.get(i);
			logger.log("Checking SPell "+ ps);
			if (ps.getSpellLevel().equals(level)
					&& ps.getSpellClass().equals(cclass)) {
				logger.log("********* ADDING SPELL ***********");
				v.add(ps);
			}
		}
		return v;
	}

	public int getRogueFeatCount() {
		int count = 0;
		Vector<PlayerFeats> feats = getFeatList();
		Vector<Feat> mmf = new Vector<Feat>();
		for (int i = 0; i < feats.size(); i++) {
			Feat f = (Feat) feats.get(i);

			if (f.isType("Rogue") || f.isType("Sneak Attack")) {
				count++;
			}
		}

		return count;
	}
	
	public Vector<Feat> getMetaMagicFeats() {

		Vector<PlayerFeats> feats = getFeatList();
		Vector<Feat> mmf = new Vector<Feat>();
		for (int i = 0; i < feats.size(); i++) {
			Feat f = (Feat) feats.get(i);

			if (f.isType("Metamagic")) {
				mmf.add(f);
			}
		}

		return mmf;
	}

	public void writeImportClass(String s) {
		// TODO Auto-generated method stub
		
	}

	public Campaign getCampaign() {
		if (campaign == null) {
			CampaignDAO db = new CampaignDAO();
			PartyDAO pdb = new PartyDAO();
			Party p = new Party();
			p.setName(party);
			Vector<Party> parties = pdb.selectParty(p);
			p = parties.get(0);
			Campaign c = new Campaign();
			c.setId(p.getCampaignId());
			Vector<Campaign> campaigns = db.selectCampaign(c);
			if (campaigns.size() > 0){
				campaign = campaigns.get(0);
			}
		}
		if (campaign == null){
			campaign = new Campaign();
		}
		return campaign;
	}
	
}

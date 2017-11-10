package initcheck.database;

import initcheck.character.library.LibraryItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

public class MonsterDAO extends InitBaseDAO implements LibraryItemDAO {

	// private static Vector monsterAttacks = null;
	private static HashMap<String, Vector<MonsterAttacks>> monsterAttacks = null;

	public int addOrUpdateMonster(Monster o) {
		int i = -1;
		if (o.getID() != 0) {
			updateMonster(o);
		} else {
			addMonster(o);
		}
		try {
			// open the connection
			dbs.open();
			String command = "SELECT MAX(ID) as MM3E FROM MATERIAL_SOURCE";

			ResultSet result = dbs.executeSQLQuery(command);
			if (result.next()) {
				i = result.getInt("MAXID");
			}

		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}

		// close the connection
		dbs.close();
		return i;
	}

	public static void reset() {
		monsterAttacks = null;
	}

	public MonsterDAO() {

	}

	public Vector<Monster> getMonsters() {
		return getMonsters(null, null);
	}

	public Vector<Monster> getMonsters(String s, String cr) {
		return getMonsters(s, cr, "NAME");
	}

	public void deleteMonster(Monster m) {
		try {
			dbs.open();

			// build the SQL statement
			String delString = "DELETE FROM MM3E WHERE " + "ID=" + m.getID();

			// execute the delete
			dbs.executeSQLCommand(delString);

		} catch (Exception e) {
			logger.log(e.toString());
		} finally {
			dbs.close();
			resetConnection();
		}
	}

	public void updateMonster(Monster o) {

		String updateString = "update mm3e set ";
		if (o.getName() != null) {
			updateString += "NAME='" + dbs.escapeQuotes(o.getName()) + "',";
		}
		if (o.getSize() != null) {
			updateString += "SIZE='" + dbs.escapeQuotes(o.getSize()) + "',";
		}
		if (o.getType() != null) {
			updateString += "TYPE='" + dbs.escapeQuotes(o.getType()) + "',";
		}
		if (o.getSubType() != null) {
			updateString += "SUBTYPE='" + dbs.escapeQuotes(o.getSubType()) + "',";
		}
		if (o.getHitDie() != null) {
			updateString += "HITDIE='" + dbs.escapeQuotes(o.getHitDie()) + "',";
		}

		updateString += "HP=" + o.getHP() + ",";
		updateString += "INIT=" + o.getInit() + ",";

		if (o.getInitNotes() != null) {
			updateString += "INIT_NOTES='" + dbs.escapeQuotes(o.getInitNotes()) + "',";
		}

		if (o.getLandSpeed() != null) {
			if (!o.getLandSpeed().equals("")) {
				updateString += "LAND_SPEED=" + dbs.escapeQuotes(o.getLandSpeed()) + ",";
			} else {
				updateString += "LAND_SPEED=null,";
			}
		}
		if (o.getFlySpeed() != null) {
			if (!o.getFlySpeed().equals("")) {
				updateString += "FLY_SPEED=" + dbs.escapeQuotes(o.getFlySpeed()) + ",";
			} else {
				updateString += "FLY_SPEED=null,";
			}
		}
		if (o.getFlyType() != null) {
			updateString += "FLYTYPE='" + dbs.escapeQuotes(o.getFlyType()) + "',";
		}
		if (o.getSwimSpeed() != null) {
			if (!o.getSwimSpeed().equals("")) {
				updateString += "SWIM_SPEED=" + dbs.escapeQuotes(o.getSwimSpeed()) + ",";
			} else {
				updateString += "SWIM_SPEED=null,";
			}
		}
		if (o.getClimbSpeed() != null) {
			if (!o.getClimbSpeed().equals("")) {
				updateString += "CLIMB_SPEED=" + dbs.escapeQuotes(o.getClimbSpeed()) + ",";
			} else {
				updateString += "CLIMB_SPEED=null,";
			}
		}
		if (o.getBurrowSpeed() != null) {
			if (!o.getBurrowSpeed().equals("")) {
				updateString += "BURROW_SPEED=" + dbs.escapeQuotes(o.getBurrowSpeed()) + ",";
			} else {
				updateString += "BURROW_SPEED=null,";
			}
		}

		updateString += "AC=" + o.getAC() + ",";

		if (o.getAcNotes() != null) {
			updateString += "AC_NOTES='" + dbs.escapeQuotes(o.getAcNotes()) + "',";
		}
		if (o.getAttacks() != null) {
			updateString += "ATTACKS='" + dbs.escapeQuotes(o.getAttacks()) + "',";
		}
		if (o.getDamage() != null) {
			updateString += "DAMAGE='" + dbs.escapeQuotes(o.getDamage()) + "',";
		}
		if (o.getFace() != null) {
			updateString += "FACE_REACH='" + dbs.escapeQuotes(o.getFace()) + "',";
		}
		if (o.getSpecialAttacks() != null) {
			updateString += "SPECIAL_ATTACKS='" + dbs.escapeQuotes(o.getSpecialAttacks()) + "',";
		}
		if (o.getSpecialQualities() != null) {
			updateString += "SPECIAL_QUALITIES='" + dbs.escapeQuotes(o.getSpecialQualities()) + "',";
		}

		updateString += "FORT='" + o.getFortSave() + "',";

		updateString += "REFLEX='" + o.getRefSave() + "',";

		updateString += "WILL='" + o.getWillSave() + "',";

		updateString += "STR='" + o.getStrength() + "',";
		updateString += "DEX='" + o.getDexterity() + "',";
		updateString += "CON='" + o.getConstitution() + "',";
		updateString += "INTEL='" + o.getIntelligence() + "',";
		updateString += "WIS='" + o.getWisdom() + "',";
		updateString += "CHA='" + o.getCharisma() + "',";

		if (o.getSkills() != null) {
			updateString += "SKILLS='" + dbs.escapeQuotes(o.getSkills()) + "',";
		}
		if (o.getFeats() != null) {
			updateString += "FEATS='" + dbs.escapeQuotes(o.getFeats()) + "',";
		}
		if (o.getClimateTerrain() != null) {
			updateString += "CLIMATE_TERRAIN='" + dbs.escapeQuotes(o.getClimateTerrain()) + "',";
		}
		if (o.getOrg() != null) {
			updateString += "ORG='" + dbs.escapeQuotes(o.getOrg()) + "',";
		}
		if (o.getChallengeRating() != null) {
			updateString += "CHALLENGE=" + dbs.escapeQuotes(o.getChallengeRating()) + ",";
		}
		if (o.getTreasure() != null) {
			updateString += "TREASURE='" + dbs.escapeQuotes(o.getTreasure()) + "',";
		}
		if (o.getAlignment() != null) {
			updateString += "ALIGN='" + dbs.escapeQuotes(o.getAlignment()) + "',";
		}
		if (o.getCombatNotes() != null) {
			updateString += "COMBAT_NOTES='" + dbs.escapeQuotes(o.getCombatNotes()) + "',";
		}
		if (o.getGenNotes() != null && !o.getGenNotes().equals("")) {
			updateString += "NOTES='" + dbs.escapeQuotes(o.getGenNotes()) + "',";
		}
		if (o.getPage() != null) {
			if (!o.getPage().equals("")) {
				updateString += "PAGE=" + dbs.escapeQuotes(o.getPage()) + ",";
			} else {
				updateString += "PAGE=null,";
			}
		}
		if (o.getDieType() != null) {
			if (!o.getDieType().equals("")) {
				updateString += "DIETYPE=" + dbs.escapeQuotes(o.getDieType()) + ",";
			} else {
				updateString += "DIETYPE=null,";
			}
		}
		if (o.getHitDice() != null) {
			updateString += "HD=" + dbs.escapeQuotes(o.getHitDice()) + ",";
		}

		if (o.getSource() != null) {
			updateString += "SOURCE='" + dbs.escapeQuotes(o.getSource()) + "',";
		}
		if (o.getPicture() != null) {
			updateString += "PICTURE='" + dbs.escapeQuotes(o.getPicture()) + "',";
		}
		if (o.getIcon() != null) {
			updateString += "ICON='" + dbs.escapeQuotes(o.getIcon()) + "',";
		}
		if (o.getDamageReduction() != null) {
			if (!o.getDamageReduction().equals("")) {
				updateString += "DAMAGE_REDUCTION=" + dbs.escapeQuotes(o.getDamageReduction()) + ",";
			} else {
				updateString += "DAMAGE_REDUCTION=null,";
			}
		}
		if (o.getDrType() != null) {
			updateString += "DR_TYPE='" + dbs.escapeQuotes(o.getDrType()) + "',";
		}
		if (o.getPoisonType() != null) {
			updateString += "POISON_TYPE='" + dbs.escapeQuotes(o.getPoisonType()) + "',";
		}
		if (o.getPoisonSaveType() != null) {
			updateString += "POISON_SAVE_TYPE='" + dbs.escapeQuotes(o.getPoisonSaveType()) + "',";
		}
		if (o.getPoisonSaveDc() != null) {
			if (!o.getPoisonSaveDc().equals("")) {
				updateString += "POISON_SAVE_DC=" + dbs.escapeQuotes(o.getPoisonSaveDc()) + ",";
			} else {
				updateString += "POISON_SAVE_DC=null,";
			}
		}
		if (o.getPoisonInitialDie() != null) {
			updateString += "POISON_INITIAL_DIE='" + dbs.escapeQuotes(o.getPoisonInitialDie()) + "',";
		}
		if (o.getPoisonSecondaryDie() != null) {
			updateString += "POISON_SECONDARY_DIE='" + dbs.escapeQuotes(o.getPoisonSecondaryDie()) + "',";
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE ID = " + o.getID() + " ";
		try {
			dbs.open();
			logger.log("Executing update " + updateString);
			dbs.executeSQLCommand(updateString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
			resetConnection();
		}

	}

	/**
	 * Add a monster to the database
	 */
	public void addMonster(Monster o) {
		String valueString = "";
		String insertString = "INSERT INTO mm3e (";

		if (o.getName() != null) {
			insertString += "NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getName()) + "',";
		}
		if (o.getSize() != null) {
			insertString += "SIZE,";
			valueString += "'" + dbs.escapeQuotes(o.getSize()) + "',";
		}
		if (o.getType() != null) {
			insertString += "TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getType()) + "',";
		}
		if (o.getSubType() != null) {
			insertString += "SUBTYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getSubType()) + "',";
		}
		if (o.getHitDie() != null) {
			insertString += "HITDIE,";
			valueString += "'" + dbs.escapeQuotes(o.getHitDie()) + "',";
		}

		insertString += "HP,";
		valueString += o.getHP() + ",";

		insertString += "INIT,";
		valueString += o.getInit() + ",";

		if (o.getInitNotes() != null) {
			insertString += "INIT_NOTES,";
			valueString += "'" + dbs.escapeQuotes(o.getInitNotes()) + "',";
		}
		if (o.getLandSpeed() != null) {
			insertString += "LAND_SPEED,";
			if (o.getLandSpeed().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getLandSpeed()) + ",";

			}
		}
		if (o.getFlySpeed() != null) {
			insertString += "FLY_SPEED,";
			if (o.getFlySpeed().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getFlySpeed()) + ",";

			}
		}
		if (o.getFlyType() != null) {
			insertString += "FLYTYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getFlyType()) + "',";
		}
		if (o.getSwimSpeed() != null) {
			insertString += "SWIM_SPEED,";
			if (o.getSwimSpeed().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getSwimSpeed()) + ",";

			}
		}
		if (o.getClimbSpeed() != null) {
			insertString += "CLIMB_SPEED,";
			if (o.getClimbSpeed().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getClimbSpeed()) + ",";

			}
		}
		if (o.getBurrowSpeed() != null) {
			insertString += "BURROW_SPEED,";
			if (o.getBurrowSpeed().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getBurrowSpeed()) + ",";

			}
		}

		insertString += "AC,";
		valueString += o.getAC() + ",";

		if (o.getAcNotes() != null) {
			insertString += "AC_NOTES,";
			valueString += "'" + dbs.escapeQuotes(o.getAcNotes()) + "',";
		}
		if (o.getAttacks() != null) {
			insertString += "ATTACKS,";
			valueString += "'" + dbs.escapeQuotes(o.getAttacks()) + "',";
		}
		if (o.getDamage() != null) {
			insertString += "DAMAGE,";
			valueString += "'" + dbs.escapeQuotes(o.getDamage()) + "',";
		}
		if (o.getFace() != null) {
			insertString += "FACE_REACH,";
			valueString += "'" + dbs.escapeQuotes(o.getFace()) + "',";
		}
		if (o.getSpecialAttacks() != null) {
			insertString += "SPECIAL_ATTACKS,";
			valueString += "'" + dbs.escapeQuotes(o.getSpecialAttacks()) + "',";
		}
		if (o.getSpecialQualities() != null) {
			insertString += "SPECIAL_QUALITIES,";
			valueString += "'" + dbs.escapeQuotes(o.getSpecialQualities()) + "',";
		}

		insertString += "FORT,";
		valueString += "'" + o.getFortSave() + "',";

		insertString += "REFLEX,";
		valueString += "'" + o.getRefSave() + "',";

		insertString += "WILL,";
		valueString += "'" + o.getWillSave() + "',";

		insertString += "STR,";
		valueString += "'" + o.getStrength() + "',";

		insertString += "DEX,";
		valueString += "'" + o.getDexterity() + "',";

		insertString += "CON,";
		valueString += "'" + o.getConstitution() + "',";

		insertString += "INTEL,";
		valueString += "'" + o.getIntelligence() + "',";

		insertString += "WIS,";
		valueString += "'" + o.getWisdom() + "',";

		insertString += "CHA,";
		valueString += "'" + o.getCharisma() + "',";

		if (o.getSkills() != null) {
			insertString += "SKILLS,";
			valueString += "'" + dbs.escapeQuotes(o.getSkills()) + "',";
		}
		if (o.getFeats() != null) {
			insertString += "FEATS,";
			valueString += "'" + dbs.escapeQuotes(o.getFeats()) + "',";
		}
		if (o.getClimateTerrain() != null) {
			insertString += "CLIMATE_TERRAIN,";
			valueString += "'" + dbs.escapeQuotes(o.getClimateTerrain()) + "',";
		}
		if (o.getOrg() != null) {
			insertString += "ORG,";
			valueString += "'" + dbs.escapeQuotes(o.getOrg()) + "',";
		}
		if (o.getChallengeRating() != null) {
			insertString += "CHALLENGE,";
			if (o.getChallengeRating().equals("")) {
				valueString += "null,";
			} else {
				valueString += "" + dbs.escapeQuotes(o.getChallengeRating()) + ",";

			}

		}
		if (o.getTreasure() != null) {
			insertString += "TREASURE,";
			valueString += "'" + dbs.escapeQuotes(o.getTreasure()) + "',";
		}
		if (o.getAlignment() != null) {
			insertString += "ALIGN,";
			valueString += "'" + dbs.escapeQuotes(o.getAlignment()) + "',";
		}
		if (o.getCombatNotes() != null) {
			insertString += "COMBAT_NOTES,";
			valueString += "'" + dbs.escapeQuotes(o.getCombatNotes()) + "',";
		}
		if (o.getGenNotes() != null) {
			insertString += "NOTES,";
			valueString += "'" + dbs.escapeQuotes(o.getNotes()) + "',";
		}
		if (o.getPage() != null) {
			insertString += "PAGE,";
			if (o.getPage().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getPage()) + ",";

			}
		}
		if (o.getDieType() != null) {
			insertString += "DIETYPE,";
			if (o.getDieType().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getDieType()) + ",";

			}
		}
		if (o.getHitDice() != null) {
			insertString += "HD,";
			valueString += "" + dbs.escapeQuotes(o.getHitDice()) + ",";
		}

		if (o.getSource() != null) {
			insertString += "SOURCE,";
			valueString += "'" + dbs.escapeQuotes(o.getSource()) + "',";
		}
		if (o.getPicture() != null) {
			insertString += "PICTURE,";
			valueString += "'" + dbs.escapeQuotes(o.getPicture()) + "',";
		}
		if (o.getIcon() != null) {
			insertString += "ICON,";
			valueString += "'" + dbs.escapeQuotes(o.getIcon()) + "',";
		}
		if (o.getDamageReduction() != null) {
			insertString += "DAMAGE_REDUCTION,";
			if (o.getDamageReduction().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getDamageReduction()) + ",";

			}
		}
		if (o.getDrType() != null) {
			insertString += "DR_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getDrType()) + "',";
		}
		if (o.getPoisonType() != null) {
			insertString += "POISON_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getPoisonType()) + "',";
		}
		if (o.getPoisonSaveType() != null) {
			insertString += "POISON_SAVE_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getPoisonSaveType()) + "',";
		}
		if (o.getPoisonSaveDc() != null) {
			insertString += "POISON_SAVE_DC,";
			if (o.getPoisonSaveDc().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getPoisonSaveDc()) + ",";

			}
		}
		if (o.getPoisonInitialDie() != null) {
			insertString += "POISON_INITIAL_DIE,";
			valueString += "'" + dbs.escapeQuotes(o.getPoisonInitialDie()) + "',";
		}
		if (o.getPoisonSecondaryDie() != null) {
			insertString += "POISON_SECONDARY_DIE,";
			valueString += "'" + dbs.escapeQuotes(o.getPoisonSecondaryDie()) + "',";
		}
		if (insertString.charAt(insertString.length() - 1) == ',') {
			insertString = insertString.substring(0, insertString.length() - 1);
		}
		if (valueString.charAt(valueString.length() - 1) == ',') {
			valueString = valueString.substring(0, valueString.length() - 1);
		}
		insertString += ") VALUES (";
		insertString += valueString;
		insertString += ")";

		try {
			dbs.open();
			logger.log("Executing Insert" + insertString);
			dbs.executeSQLCommand(insertString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
			resetConnection();
		}

	}

	public Vector<LibraryItem> getItems(LibraryItem i) {
		String queryString = "SELECT * FROM MM3E WHERE SOURCE = '" + i.getSource() + "' " + " ORDER BY NAME";
		Vector<LibraryItem> results = new Vector<LibraryItem>();
		results.addAll(getMonstersFromSql(queryString));
		return results;
	}

	public Vector<Monster> getMonsters(String s, String cr, String order) {

		// cache the monster attack list, if it isn't already.
		// this will avoid upmteen subqueries, or a nasty join.
		if (monsterAttacks == null) {
			loadMonsterAttacks();
		}

		String queryString;
		if (s == null) {
			if (cr == null) {
				queryString = "SELECT * FROM MM3E";
			} else {
				queryString = "SELECT * FROM MM3E WHERE CHALLENGE = " + cr + " OR CHALLENGE LIKE '" + cr + ".%'";
			}
		} else {
			if (s.startsWith("TYPE:")) {
				String searchStr = s.substring(s.indexOf(":") + 1);
				queryString = "SELECT * FROM MM3E WHERE TYPE = '" + searchStr + "'";
			} else if (s.startsWith("ALIGN:")) {
				String searchStr = s.substring(s.indexOf(":") + 1);
				queryString = "SELECT * FROM MM3E WHERE ALIGN LIKE '%" + searchStr + "%'";
			} else {
				queryString = "SELECT * FROM MM3E WHERE NAME LIKE '%" + s + "%'";
			}
		}

		if (order != null) {
			queryString += " ORDER BY " + order;
		}

		logger.log(queryString);
		return getMonstersFromSql(queryString);
	}

	public Vector<Monster> getMonstersFromSql(String queryString) {
		Vector<Monster> os = null;
		try {
			ResultSet result;

			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(queryString);
			} else {
				result = dbs2.executeSQLQuery(queryString);
			}

			// ResultSet rs2 = null;

			os = new Vector<Monster>();

			// PreparedStatement ps = dbs3
			// .prepareStatement("SELECT * FROM MONSTER_ATTACKS WHERE MONSTER_ID
			// = ?");

			while (result.next()) {

				Monster c = new Monster();

				c.setID(result.getInt("ID"));
				c.setName(result.getString("NAME"));
				c.setSize(result.getString("SIZE"));
				c.setType(result.getString("TYPE"));
				c.setSubType(result.getString("SUBTYPE"));
				c.setHitDie(result.getString("HITDIE"));
				c.setHP(result.getInt("HP"));

				c.setMod(result.getInt("INIT"));
				c.setInit(c.getMod());
				c.setInitNotes(result.getString("INIT_NOTES"));
				c.setLandSpeed(result.getString("LAND_SPEED"));
				c.setFlySpeed(result.getString("FLY_SPEED"));
				c.setFlyType(result.getString("FLYTYPE"));
				c.setSwimSpeed(result.getString("SWIM_SPEED"));
				c.setClimbSpeed(result.getString("CLIMB_SPEED"));
				c.setBurrowSpeed(result.getString("BURROW_SPEED"));
				c.setAC(result.getInt("AC"));
				c.setACNotes(result.getString("AC_NOTES"));
				c.setAttacks(result.getString("ATTACKS"));
				c.setDamage(result.getString("DAMAGE"));
				c.setFace(result.getString("FACE_REACH"));
				c.setSpecialAttacks(result.getString("SPECIAL_ATTACKS"));
				c.setSpecialQualities(result.getString("SPECIAL_QUALITIES"));
				c.setFortSave(result.getInt("FORT"));
				c.setRefSave(result.getInt("REFLEX"));
				c.setWillSave(result.getInt("WILL"));
				c.setStrength(result.getInt("STR"));
				c.setDexterity(result.getInt("DEX"));
				c.setConstitution(result.getInt("CON"));
				c.setIntelligence(result.getInt("INTEL"));
				c.setWisdom(result.getInt("WIS"));
				c.setCharisma(result.getInt("CHA"));
				String skillStr = result.getString("SKILLS");
				c.setSkills(skillStr);
				String featStr = result.getString("FEATS");
				c.setFeats(featStr);
				c.setTerrain(result.getString("CLIMATE_TERRAIN"));
				c.setOrg(result.getString("ORG"));
				c.setChallengeRating(result.getString("CHALLENGE"));
				c.setTreasure(result.getString("TREASURE"));
				c.setAlignment(result.getString("ALIGN"));
				c.setCombatNotes(result.getString("COMBAT_NOTES"));
				c.setGenNotes(result.getString("NOTES"));
				c.setPage(result.getString("PAGE"));
				c.setDieType(result.getString("DIETYPE"));
				c.setHitDice(result.getString("HD"));
				c.setSource(result.getString("SOURCE"));
				c.setPicture(result.getString("PICTURE"));
				c.setDamageReduction(result.getString("DAMAGE_REDUCTION"));
				c.setDrType(result.getString("DR_TYPE"));
				c.setPoisonType(result.getString("POISON_TYPE"));
				c.setPoisonSaveType(result.getString("POISON_SAVE_TYPE"));
				c.setPoisonSaveDc(result.getString("POISON_SAVE_DC"));
				c.setPoisonInitialDie(result.getString("POISON_INITIAL_DIE"));
				c.setPoisonSecondaryDie(result.getString("POISON_SECONDARY_DIE"));
				c.setCurrentHitPoints(c.getHP());
				c.setIcon(result.getString("ICON"));
				Vector<MonsterAttacks> v = getMonsterAttacks(c.getID());

				c.setAttackList(v);

				os.add(c);
			}

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());

		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		} finally {
			if (dbs2 == null) {
				dbs.close();
			} else {
				dbs2.cleanup();
			}
		}
		return os;
	}

	private void loadMonsterAttacks() {
		monsterAttacks = new HashMap<String, Vector<MonsterAttacks>>(600);

		MonsterAttacksDAO madb = new MonsterAttacksDAO();
		Vector<MonsterAttacks> v = madb.getMonsterAttacks();
		String id = "";
		Vector<MonsterAttacks> newVector = new Vector<MonsterAttacks>(10);
		for (int i = 0; i < v.size(); i++) {
			MonsterAttacks ma = (MonsterAttacks) v.get(i);
			if (!ma.getMonsterId().equals(id)) {
				if (!id.equals("")) {
					monsterAttacks.put(id, newVector);
				}
				newVector = new Vector<MonsterAttacks>(10);
				newVector.add(ma);
				id = ma.getMonsterId();
			} else {
				newVector.add(ma);
			}
		}
		monsterAttacks.put(id, newVector);
	}

	private Vector<MonsterAttacks> getMonsterAttacks(int id) {

		Vector<MonsterAttacks> o = monsterAttacks.get("" + id);
		if (o != null) {
			return o;
		}
		return new Vector<MonsterAttacks>();
	}

}

package initcheck.database;

import initcheck.DCharacter;
import initcheck.DCharacterListing;
import initcheck.character.ClassSlot;
import initcheck.character.JumpBlock;
import initcheck.character.LoadPartyProgressPanel;
import initcheck.character.MoneyBlock;
import initcheck.character.SaveBlock;
import initcheck.character.StatBlock;
import initcheck.utils.RandomRoll;
import initcheck.utils.StrManip;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class InitDBC extends InitBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	static Vector<SizeMod> sizeMods = null;

	// the secondary connection to the database
	// private DBSession dbs2 = new DBSession(username, dataSource,
	// password, driverName);

	// constructor
	public InitDBC() {
		logger.log("Creating new InitDBC");
	}

	// ---------------------------------------------------------------------
	// addPlayer
	// Adds a character to the general pool
	// ---------------------------------------------------------------------
	public int addPlayer(DCharacter c) {
		int i = -1;
		try {
			
			// open the connection
			dbs.open();
			
			
			// build the SQL statement
			String insertString = "INSERT INTO PLAYER (NAME, RACE,  "
					+ "PARTY) " + "VALUES (" + "'"
					+ dbs.escapeQuotes(c.getName()) + "'," + "'"
					+ dbs.escapeQuotes(c.getRace()) + "'," + "'"
					+ dbs.escapeQuotes(c.getParty()) + "'" + ")";

			// execute the delete
			

			logger.log("Executing Insert " + insertString);
			dbs.executeSQLCommand(insertString);

			String queryString = "SELECT MAX(ID) as MAXID FROM PLAYER";

			ResultSet result = dbs.executeSQLQuery(queryString);

			if (result.next()) {
				i = result.getInt("MAXID");
			}

		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		} finally {
			// close the connection
			dbs.close();
		}

		return i;
	}

	public void clearPlayers() {
		try {
			// open the connection
			dbs.open();

			// build the SQL statement
			String delString = "DELETE FROM PLAYER ";
			dbs.executeSQLCommand(delString);

			// cascades handled by DB

		} catch (Exception uhe) {
		}
		// close the connection
		dbs.close();
	}

	public void deletePlayer(int id) {

		try {
			// open the connection
			dbs.open();

			// build the SQL statement
			String delString = "DELETE FROM PLAYER WHERE " + "ID=" + id;

			// execute the delete - cascades handled by DB.
			dbs.executeSQLCommand(delString);
		} catch (Exception uhe) {

		} finally {
			// close the connection
			dbs.close();
		}
	}

	public String deletePlayer(String name, String party) {
		Vector<String> v = new Vector<String>();
		String id = "";
		try {
			dbs.open();
			String queryString = "SELECT ID FROM PLAYER WHERE NAME='" + name
					+ "' AND PARTY='" + party + "'";
			
			ResultSet result = dbs.executeSQLQuery(queryString);

			while (result.next()) {
				id = result.getString("ID");
				v.add(id);

			}

		} catch (Exception uhe) {

		} finally {
			// close the connection
			dbs.close();
		}

		for (int i = 0; i < v.size(); i++) {
			deletePlayer(Integer.parseInt((String) v.get(i)));
		}
		return id;
	}

	public void updatePartyXp(String party, String xp){
		String modifyString = "UPDATE PLAYER SET XP = XP + "+xp+" WHERE PARTY_ID = "+party;
		try {
			// open the connection
			dbs.open();
			dbs.executeSQLCommand(modifyString);
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		} finally {
			dbs.close();
		}
	}
	
	// ---------------------------------------------------------------------
	// updatePlayer
	// changes the entry for a specified book
	// ---------------------------------------------------------------------
	public String updatePlayer(DCharacter c) {
		String errorString = "";

		String modifyString = "UPDATE PLAYER SET ";
		try {
			// open the connection
			dbs.open();

			// build the SQL statement

			boolean first = true;

			if (c.getName() != null && !c.getName().equals("")) {
				modifyString += "NAME='" + dbs.escapeQuotes(c.getName()) + "' ";
				first = false;
			}
			if (c.getRace() != null && !c.getRace().equals("")) {
				if (!first) {
					first = false;
					modifyString += " , ";
				}
				modifyString += "RACE='" + dbs.escapeQuotes(c.getRace()) + "' ";
			}

			if (c.getAlignment() != null && !c.getAlignment().equals("")) {
				if (!first) {
					first = false;
					modifyString += " , ";
				}
				modifyString += "ALIGNMENT='"
						+ dbs.escapeQuotes(c.getAlignment()) + "' ";
			}

			if (!first) {
				first = false;
				modifyString += " , ";
			}

			modifyString += "HP=" + c.getHP() + ", ";
			modifyString += "REF=" + c.getRefSave() + ", ";
			modifyString += "FORT=" + c.getFortSave() + ", ";
			modifyString += "WILL=" + c.getWillSave() + ", ";

			modifyString += "CLEVEL=" + c.getLevel() + ", ";
			modifyString += "GENDER='" + c.getGender() + "', ";
			modifyString += "DEITY='" + dbs.escapeQuotes(c.getDeity()) + "', ";
			modifyString += "PLAYER_NAME='"
					+ dbs.escapeQuotes(c.getPlayerName()) + "',";
			modifyString += "XP=" + c.getXp() + ",";
			modifyString += "NATURAL_ARMOR=" + c.getNaturalArmor() + ",";
			modifyString += "MISC_ARMOR=" + c.getMiscArmor() + ",";
			modifyString += "MISC_ARMOR_TEXT='"
					+ dbs.escapeQuotes(c.getMiscArmorText()) + "',";
			modifyString += "SIZE_OVERRIDE='"
					+ dbs.escapeQuotes(c.getSizeOverride()) + "',";
			modifyString += "MOD_NOTES='" + dbs.escapeQuotes(c.getModNotes())
					+ "',";
			modifyString += "PICTURE='" + dbs.escapeQuotes(c.getPicture())
					+ "',";
			modifyString += "ICON='" + dbs.escapeQuotes(c.getIcon()) + "'";

			if (c.getParty() != null && !c.getParty().equals("")) {
				modifyString += ", PARTY='" + dbs.escapeQuotes(c.getParty())
						+ "', ";
			}

			logger.log("UPDATING STATS");
			StatBlock o = c.getStats();
			// base stats
			modifyString += "STR=" + o.getBaseStr() + ", ";
			modifyString += "WIS=" + o.getBaseWis() + ", ";
			modifyString += "DEX=" + o.getBaseDex() + ", ";
			modifyString += "CON=" + o.getBaseCon() + ", ";
			modifyString += "CHA=" + o.getBaseCha() + ", ";
			modifyString += "INTEL=" + o.getBaseInt() + ", ";

			// purchased points every four levels
			modifyString += "LEVEL_STR="
					+ dbs.escapeQuotes("" + o.getLevelStr()) + ",";
			modifyString += "LEVEL_DEX="
					+ dbs.escapeQuotes("" + o.getLevelDex()) + ",";
			modifyString += "LEVEL_INT="
					+ dbs.escapeQuotes("" + o.getLevelInt()) + ",";
			modifyString += "LEVEL_CON="
					+ dbs.escapeQuotes("" + o.getLevelCon()) + ",";
			modifyString += "LEVEL_WIS="
					+ dbs.escapeQuotes("" + o.getLevelWis()) + ",";
			modifyString += "LEVEL_CHA="
					+ dbs.escapeQuotes("" + o.getLevelCha()) + ",";

			// miscellaneous bonuses (rings, amulets, etc)
			modifyString += "MISC_STR=" + dbs.escapeQuotes("" + o.getMiscStr())
					+ ",";
			modifyString += "MISC_DEX=" + dbs.escapeQuotes("" + o.getMiscDex())
					+ ",";
			modifyString += "MISC_CON=" + dbs.escapeQuotes("" + o.getMiscCon())
					+ ",";
			modifyString += "MISC_INT=" + dbs.escapeQuotes("" + o.getMiscInt())
					+ ",";
			modifyString += "MISC_WIS=" + dbs.escapeQuotes("" + o.getMiscWis())
					+ ",";
			modifyString += "MISC_CHA=" + dbs.escapeQuotes("" + o.getMiscCha())
					+ ",";

			// permanent bonuses (tomes, manuals, wishes)
			modifyString += "BOOK_STR=" + dbs.escapeQuotes("" + o.getBookStr())
					+ ",";
			modifyString += "BOOK_DEX=" + dbs.escapeQuotes("" + o.getBookDex())
					+ ",";
			modifyString += "BOOK_CON=" + dbs.escapeQuotes("" + o.getBookCon())
					+ ",";
			modifyString += "BOOK_INT=" + dbs.escapeQuotes("" + o.getBookInt())
					+ ",";
			modifyString += "BOOK_WIS=" + dbs.escapeQuotes("" + o.getBookWis())
					+ ",";
			modifyString += "BOOK_CHA=" + dbs.escapeQuotes("" + o.getBookCha())
					+ ",";

			// description field for misc bonuses
			modifyString += "MISC_STR_TEXT='"
					+ dbs.escapeQuotes(o.getMiscStrText()) + "',";
			modifyString += "MISC_DEX_TEXT='"
					+ dbs.escapeQuotes(o.getMiscDexText()) + "',";
			modifyString += "MISC_CON_TEXT='"
					+ dbs.escapeQuotes(o.getMiscConText()) + "',";
			modifyString += "MISC_INT_TEXT='"
					+ dbs.escapeQuotes(o.getMiscIntText()) + "',";
			modifyString += "MISC_WIS_TEXT='"
					+ dbs.escapeQuotes(o.getMiscWisText()) + "',";
			modifyString += "MISC_CHA_TEXT='"
					+ dbs.escapeQuotes(o.getMiscChaText()) + "',";
			logger.log("UPDATING SAVES");
			SaveBlock sav = c.getSaves();
			
			modifyString += "MISC_FORT="
					+ dbs.escapeQuotes("" + sav.getMiscFort()) + ",";
			modifyString += "MISC_REF="
					+ dbs.escapeQuotes("" + sav.getMiscRef()) + ",";
			modifyString += "MISC_WILL="
					+ dbs.escapeQuotes("" + sav.getMiscWill()) + ",";

		
			modifyString += "MISC_FORT_TEXT='"
					+ dbs.escapeQuotes(sav.getMiscFortText()) + "',";
			modifyString += "MISC_REF_TEXT='"
					+ dbs.escapeQuotes(sav.getMiscRefText()) + "',";
			modifyString += "MISC_WILL_TEXT='"
					+ dbs.escapeQuotes(sav.getMiscWillText()) + "',";

			modifyString += "HEIGHT='" + dbs.escapeQuotes(c.getHeight()) + "',";
			modifyString += "WEIGHT='" + dbs.escapeQuotes(c.getWeight()) + "',";
			modifyString += "AGE='" + dbs.escapeQuotes(c.getAge()) + "',";
			modifyString += "HAIR_COLOR='" + dbs.escapeQuotes(c.getHair())
					+ "',";
			modifyString += "EYE_COLOR='" + dbs.escapeQuotes(c.getEyes())
					+ "',";
			modifyString += "FULL_NAME='" + dbs.escapeQuotes(c.getFullName())
					+ "',";
			modifyString += "PLAYER_NOTES='"
					+ dbs.escapeQuotes(c.getPlayerNotes()) + "',";
			modifyString += "DAMAGE_REDUCTION='"
					+ dbs.escapeQuotes(c.getDamageReduction()) + "',";
			modifyString += "SPELL_RESIST='"
					+ dbs.escapeQuotes(c.getSpellResist()) + "',";
			modifyString += "MARCH_ORDER="
					+ dbs.escapeQuotes(c.getMarchOrder()) + ",";
			modifyString += "SLEEP_SHIFT="
					+ dbs.escapeQuotes(c.getSleepShift()) + ",";
			modifyString += "PARTY_ID=" + dbs.escapeQuotes(c.getPartyId())
					+ ",";
			modifyString += "INIT=" + c.getInitMod() + ",";

			logger.log("UPDATING MONEY");
			MoneyBlock money = c.getMoney();
			modifyString += "CP=" + money.getCp() + ",";
			modifyString += "SP=" + money.getSp() + ",";
			modifyString += "GP=" + money.getGp() + ",";
			modifyString += "PP=" + money.getPp() + ",";
			modifyString += "STORED_GOLD=" + money.getStoredGold() + ",";

			logger.log("UPDATING BASE");
			modifyString += "BASE_MOVE=" + c.getBaseMovementOverride();

			modifyString += " WHERE ID=" + c.getID();

			// execute the update
			
			dbs.executeSQLCommand(modifyString);

			logger.log("UPDATING ARMOR");
			// update the armor
			PlayerArmorDAO padb = new PlayerArmorDAO();
			Armor a = c.getArmor();
			if (a != null && a.getName() != null) {
				logger.log("UPDATING REG ARMOR");
				padb.addOrUpdatePlayerArmor(a, "" + c.getID(), "Armor");
			}
			Armor s = c.getShield();
			if (s != null && s.getName() != null) {
				logger.log("UPDATING SHIELD");
				padb.addOrUpdatePlayerArmor(s, "" + c.getID(), "Shield");
			}

			Armor r = c.getRestArmor();
			if (r != null && r.getName() != null) {
				logger.log("UPDATING REST ARMOR");
				padb.addOrUpdatePlayerArmor(r, "" + c.getID(), "Armor", true);
			}
			logger.log("DONE");
		} catch (Exception uhe) {
			logger.log("Trying to run " + modifyString);
			logger.log("error", "ERROR ENCOUNTERED IN UPDATE PLAYER : "
					+ uhe.toString());
			errorString = "A database error occurred while trying to update the player.\nThis is because the programmer is an idiot, or you are.\n Go ahead and blame the programmer, since he'll probably blame you.";

		} finally {
			// close the connection
			dbs.close();
		}
		return errorString;
	}

	public void addParty(String s) {
		try {
			// open the connection
			dbs.open();

			// build the SQL statement
			String insertString = "INSERT INTO PARTY (PARTY_NAME, PARTY_TYPE) VALUES ('"
					+ s + "', 'PC')";

			// execute the delete
			logger.log("Executing add " + insertString);

			dbs.executeSQLCommand(insertString);
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}

		// close the connection
		dbs.close();
		resetConnection();
	}

	// ---------------------------------------------------------------------
	// getParties
	// retrieves all the entries in the database
	// ---------------------------------------------------------------------
	public Vector<String> getPlayerNames() {
		Vector<String> os = null;
		try {
			// open the connection
			dbs.open();

			String queryString = "SELECT DISTINCT PLAYER_NAME FROM PLAYER";
			ResultSet result = dbs.executeSQLQuery(queryString);

			os = new Vector<String>();
			os.add("All Players");

			while (result.next()) {
				os.add(result.getString("PLAYER_NAME"));
			}

			dbs.close();

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		return os;
	}

	// ---------------------------------------------------------------------
	// getParties
	// retrieves all the entries in the database
	// ---------------------------------------------------------------------
	public Vector<String> getParties() {

		return getParties(false);
	}

	public Vector<String> getParties(boolean showDmParties) {
		Vector<String> os = null;
		try {
			// open the connection
			dbs.open();

			String queryString = "SELECT * FROM PARTY ";
			if (!showDmParties) {
				queryString += " WHERE PARTY_TYPE = 'PC'";
			}

			ResultSet result = dbs.executeSQLQuery(queryString);

			os = new Vector<String>();
			os.add("Select Party");
			os.add("All Parties");

			while (result.next()) {
				os.add(result.getString("PARTY_NAME"));
			}

			dbs.close();

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		return os;
	}

	public Vector<String> getParties(String id, boolean showDmParties) {
		Vector<String> os = null;
		try {
			// open the connection
			dbs.open();

			String queryString = "SELECT * FROM PARTY WHERE CAMPAIGN_ID = "
					+ id;
			if (!showDmParties) {
				queryString += " AND PARTY_TYPE = 'PC'";
			}

			ResultSet result = dbs.executeSQLQuery(queryString);

			os = new Vector<String>();
			os.add("Select Party");
			os.add("All Parties");

			while (result.next()) {
				os.add(result.getString("PARTY_NAME"));
			}

			dbs.close();

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		return os;
	}

	public String getCriticalEffects(String type, int roll) {
		String retval = null;
		try {
			// open the connection
			dbs.open();

			String queryString = "SELECT * FROM CRITICAL_EFFECTS WHERE "
					+ "RANGE_START <= " + roll + " AND RANGE_END >= " + roll
					+ " AND " + "EFFECT_TYPE='" + type + "'";

			ResultSet result = dbs.executeSQLQuery(queryString);

			if (result.next()) {
				retval = result.getString("EFFECT_DESC");
			}

			dbs.close();

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		return retval;
	}

	public String getDamage(String damage, String sizeOrig, String sizeNew,
			String campaign) {

		String[] sizes = { "Fine", "Diminutive", "Tiny", "Small", "Medium",
				"Large", "Huge", "Gargantuan", "Colossal" };
		int newsize = 0;
		int origsize = 0;
		for (int i = 0; i < 9; i++) {
			if (sizes[i].equals(sizeOrig)) {
				origsize = i;
			}
			if (sizes[i].equals(sizeNew)) {
				newsize = i;
			}
		}
		return getDamage(damage, newsize - origsize, campaign);
	}

	public String getDamageIndex(int diff) {
		String index = "DAMAGE";
		switch (diff) {
		case -6:
			index = "MINUS6";
			break;
		case -5:
			index = "MINUS5";
			break;
		case -4:
			index = "MINUS4";
			break;
		case -3:
			index = "MINUS3";
			break;
		case -2:
			index = "MINUS2";
			break;
		case -1:
			index = "MINUS1";
			break;
		case 0:
			index = "DAMAGE";
			break;
		case 1:
			index = "PLUS1";
			break;
		case 2:
			index = "PLUS2";
			break;
		case 3:
			index = "PLUS3";
			break;
		case 4:
			index = "PLUS4";
			break;
		case 5:
			index = "PLUS5";
			break;
		case 6:
			index = "PLUS6";
			break;
		}
		return index;
	}

	public String getDamage(String damage, int diff, String campaign) {
		String dmg = "0";
		String index = getDamageIndex(diff);
		ResultSet result = null;
		try {
			
			
			String queryString = "SELECT * FROM WEAPON_SIZE_INCREASE WHERE DAMAGE='"
					+ damage + "' AND CAMPAIGN_ID = " + campaign + 
					" UNION SELECT * FROM WEAPON_SIZE_INCREASE WHERE DAMAGE='"+ damage + "' AND CAMPAIGN_ID IS NULL ";
			
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(queryString);
			} else {
				result = dbs2.executeSQLQuery(queryString);
			}

			if (result.next()) {
				dmg = result.getString(index);
			} else {
				queryString = "SELECT * FROM WEAPON_SIZE_INCREASE WHERE PLUS1='"
						+ damage + "' AND CAMPAIGN_ID = " + campaign + 
						" UNION SELECT * FROM WEAPON_SIZE_INCREASE WHERE PLUS1='"+ damage + "' AND CAMPAIGN_ID IS NULL ";
						;
				if (dbs2 == null) {

					result = dbs.executeSQLQuery(queryString);
				} else {
					result = dbs2.executeSQLQuery(queryString);
				}
				if (result.next()) {
					dmg = result.getString(getDamageIndex(diff + 1));
				} else {
					queryString = "SELECT * FROM WEAPON_SIZE_INCREASE WHERE PLUS2='"
							+ damage + "' AND CAMPAIGN_ID = " + campaign +
							" UNION SELECT * FROM WEAPON_SIZE_INCREASE WHERE PLUS2='"+ damage + "' AND CAMPAIGN_ID IS NULL ";
							;
					if (dbs2 == null) {

						result = dbs.executeSQLQuery(queryString);
					} else {
						result = dbs2.executeSQLQuery(queryString);
					}
					if (result.next()) {
						dmg = result.getString(getDamageIndex(diff + 1));
					}
				}
			}

		} catch (Exception e) {
			logger.log(e.toString());
		} finally {
			if (dbs2 == null) {
				dbs.close();
			} else {
				dbs2.cleanup();
			}
		}
		return dmg;
	}

	public String getPlayerNotes(String monster) {
		String os = null;
		try {
			// open the connection
			dbs.open();
			String queryString = "SELECT * FROM PLAYER_NOTES WHERE MONSTER_ID='"
					+ monster + "'";
			ResultSet result = dbs.executeSQLQuery(queryString);
			if (result.next()) {
				os = result.getString("PLAYER_NOTES");
			}

		} catch (Exception e) {

		} finally {
			dbs.close();
		}
		return os;
	}

	public void setPlayerNotes(String monster, String notes, String name) {
		try {
			// open the connection
			dbs.open();

			// build the SQL statement
			String insertString = "INSERT INTO PLAYER_NOTES (MONSTER_ID, PLAYER_NOTES, MONSTER_NAME) VALUES ('"
					+ monster + "','" + notes + "','" + name + "')";

			// execute the delete
			logger.log("Executing add " + insertString);

			dbs.executeSQLCommand(insertString);
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}

		// close the connection
		dbs.close();
	}

	public void updatePlayerNotes(String monster, String notes, String name) {
		try {
			// open the connection
			dbs.open();

			// build the SQL statement
			String insertString = "UPDATE PLAYER_NOTES SET MONSTER_NAME='"
					+ name + "'," + "PLAYER_NOTES='" + notes
					+ "' WHERE MONSTER_ID='" + monster + "'";

			// execute the delete
			logger.log("Executing add " + insertString);

			dbs.executeSQLCommand(insertString);
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}

		// close the connection
		dbs.close();
	}

	public Vector<String> getMonsterTypes() {
		Vector<String> os = new Vector<String>();
		try {
			// open the connection
			dbs.open();
			String queryString = "SELECT * FROM MONSTER_TYPES";
			ResultSet result = dbs.executeSQLQuery(queryString);
			while (result.next()) {
				os.add(result.getString("MONSTER_TYPE"));
			}

		} catch (Exception e) {

		} finally {
			dbs.close();
		}
		return os;
	}

	public Vector<String> getMonsterSubTypes() {
		Vector<String> os = new Vector<String>();
		try {
			// open the connection
			dbs.open();
			String queryString = "SELECT * FROM MONSTER_SUB_TYPES";
			ResultSet result = dbs.executeSQLQuery(queryString);
			while (result.next()) {
				os.add(result.getString("MONSTER_SUB_TYPE"));
			}

		} catch (Exception e) {

		} finally {
			dbs.close();
		}
		return os;
	}

	public Vector<String> getClimates() {
		Vector<String> os = new Vector<String>();
		try {
			// open the connection
			dbs.open();
			String queryString = "SELECT * FROM CLIMATES";
			ResultSet result = dbs.executeSQLQuery(queryString);
			while (result.next()) {
				os.add(result.getString("CLIMATE"));
			}

		} catch (Exception e) {

		} finally {
			dbs.close();
		}
		return os;
	}

	public Vector<String> getTerrainTypes() {
		Vector<String> os = new Vector<String>();
		try {
			// open the connection
			dbs.open();
			String queryString = "SELECT * FROM TERRAIN";
			ResultSet result = dbs.executeSQLQuery(queryString);
			while (result.next()) {
				os.add(result.getString("TERRAIN_TYPE"));
			}

		} catch (Exception e) {

		} finally {
			dbs.close();
		}
		return os;
	}

	public Vector<String> getMonsterSizes() {
		Vector<String> os = new Vector<String>();
		try {
			String selectString = "SELECT * FROM MONSTER_SIZES";

			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}

			while (result.next()) {
				os.add(result.getString("MONSTER_SIZE"));
			}

		} catch (Exception e) {
			logger.log("Error in InitDBC.getMonsterSizes() " + e);
		} finally {
			if (dbs2 == null) {
				dbs.close();
			}
		}
		return os;
	}

	public void shiftParty(String party, String newParty) {
		try {
			// open the connection
			dbs.open();
			String queryString = "UPDATE PLAYER SET PARTY='" + newParty
					+ "' WHERE PARTY='" + party + "'";
			dbs.executeSQLCommand(queryString);

		} catch (Exception e) {

		} finally {
			dbs.close();
		}
	}

	public void removeParty(String party) {
		try {

			// open the connection
			dbs.open();
			String queryString = "DELETE FROM PARTY WHERE PARTY_NAME='" + party
					+ "'";
			dbs.executeSQLCommand(queryString);
			queryString = "SELECT ID FROM PLAYER WHERE PARTY='" + party + "'";
			ResultSet result = dbs.executeSQLQuery(queryString);
			while (result.next()) {
				deletePlayer(result.getInt("ID"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbs.close();
		}
	}

	public int getPartySize(String party) {
		int i = 0;
		try {
			// open the connection
			dbs.open();
			String queryString = "SELECT COUNT(*) as COUNT FROM PLAYER WHERE PARTY = "
					+ "'" + party + "'";
			ResultSet result = dbs.executeSQLQuery(queryString);
			if (result.next()) {
				i = result.getInt("COUNT");
			}

		} catch (Exception e) {

		} finally {
			dbs.close();
		}
		return i;
	}

	public Vector<DCharacterListing> getPartyNames(String s, String p) {

		Vector<DCharacterListing> os = new Vector<DCharacterListing>();
		try {
			// open the connection
			dbs.open();

			String queryString = "SELECT DISTINCT NAME, ID FROM PLAYER";
			if (s != null && !s.equals("All Parties")) {
				queryString += " WHERE PARTY = '" + s + "'";
			}
			ResultSet result = dbs.executeSQLQuery(queryString);

			os = new Vector<DCharacterListing>();
			DCharacterListing all = new DCharacterListing();
			all.setName("All Players");
			all.setId("0");
			os.add(all);

			while (result.next()) {
				DCharacterListing curr = new DCharacterListing();
				curr.setName(result.getString("NAME"));
				curr.setId(result.getString("ID"));
				os.add(curr);
			}

			dbs.close();

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		return os;
	}

	public Vector<DCharacter> getParty(String s, String p) {
		DCharacter dc = new DCharacter();
		if (!s.equals("All Parties")) {
			dc.setParty(s);
		}

		if (!p.equals("All Players")) {
			dc.setPlayerName(p);
		}
		return getPlayers(dc);
	}

	public Vector<DCharacter> getParty(String c, String s, String p,
			LoadPartyProgressPanel lpp, boolean dmMode) {

		Vector<DCharacter> results = new Vector<DCharacter>();
		DCharacter dc = new DCharacter();
		Vector<Party> parties = new Vector<Party>();
		PartyDAO db = new PartyDAO();
		Party party = new Party();
		if (!dmMode) {
			party.setPartyType("PC");
		}
		party.setCampaignId(c);
		parties = db.selectParty(party);

		if (!s.equals("All Parties")) {
			dc.setParty(s);
			if (!p.equals("All Players")) {
				dc.setPlayerName(p);
			}
			return getPlayers(dc, lpp);
		} else {
			for (int i = 0; i < parties.size(); i++) {
				party = parties.get(i);
				dc.setParty(party.getPartyName());
				if (!p.equals("All Players")) {
					dc.setPlayerName(p);
				}
				results.addAll(getPlayers(dc, lpp));
			}
		}
		return results;
	}

	public Vector<String> getPartyCharacterNames(String c, String s, String p) {
		Vector<String> v = new Vector<String>();
		try {
			// open the connection
			dbs.open();

			String queryString = "SELECT NAME FROM PLAYER ";
			boolean first = true;

			if (!s.equals("All Parties")) {

				queryString += " WHERE PARTY = '" + s + "' ";
				first = false;
			}

			if (!p.equals("All Players")) {
				if (!first) {
					queryString += " AND ";
				} else {
					queryString += " WHERE ";
				}
				queryString += " PLAYER_NAME = '" + p + "'";
			}

			ResultSet result = dbs.executeSQLQuery(queryString);

			while (result.next()) {
				v.add(result.getString("NAME"));
			}

			dbs.close();

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		return v;
	}

	public DCharacter getPlayer(int id) {
		DCharacter dc = new DCharacter();
		dc.setID(id);
		Vector<DCharacter> v = getPlayers(dc);
		if (v.size() > 0) {
			return (DCharacter) v.get(0);
		}
		return null;
	}

	public DCharacter getPlayer(DCharacter dc) {
		Vector<DCharacter> v = getPlayers(dc);
		if (v.size() > 0) {
			return (DCharacter) v.get(0);
		}
		return null;
	}

	public Vector<DCharacter> getPlayers(DCharacter d) {
		return getPlayers(d, null);
	}

	public Vector<DCharacter> getPlayers(DCharacter d,
			LoadPartyProgressPanel lpp) {
		Vector<DCharacter> os = null;
		try {
			// open the connection
			dbs.open();
			// dbs2.open();
			// Make the query and get the results
			String queryString = "SELECT p.*, r.Size, r.BaseSpeed "
					+ " FROM PLAYER p, RACE r WHERE p.RACE = r.Race ";

			if (d != null) {
				if (!d.getParty().equals("")
						&& !d.getParty().equals("All Parties")) {
					queryString += " AND PARTY = '" + d.getParty() + "'";
				}
				if (!StrManip.isNullOrEmpty(d.getPartyId())) {
					queryString += " AND PARTY_ID = " + d.getPartyId() + "";
				}
				if (d.getName() != null && !d.getName().equals("")) {
					queryString += " AND NAME = '" + d.getName() + "'";
				}
				if (!d.getPlayerName().equals("")
						&& !d.getPlayerName().equals("All Players")) {
					queryString += " AND PLAYER_NAME = '" + d.getPlayerName()
							+ "'";
				}
				if (d.getStrength() != 0) {
					queryString += " AND STR = " + d.getStrength() + "";
				}
				if (d.getID() != 0) {
					queryString += " AND p.ID = " + d.getID() + "";
				}
			}
			queryString += " ORDER BY p.PARTY, p.NAME";

			
			ResultSet result = dbs.executeSQLQuery(queryString);
			logger.log("Made Query");
			os = new Vector<DCharacter>();

			while (result.next()) {

				DCharacter c = new DCharacter();

				c.setID(result.getInt("ID"));
				c.setName(result.getString("NAME"));
				c.setFullName(result.getString("FULL_NAME"));

				if (lpp != null) {
					lpp.signal(c.getName());
					lpp.incValue();
				}

				c.setRace(result.getString("RACE"));
				c.setAlignment(result.getString("ALIGNMENT"));
				c.setInitMod(result.getInt("INIT"));
				c.setHP(result.getInt("HP"));

				// c.setAC(result.getInt("AC"));
				c.setRefSave(result.getInt("REF"));
				c.setFortSave(result.getInt("FORT"));
				c.setWillSave(result.getInt("WILL"));
				c.setLevel(result.getDouble("CLEVEL"));
				c.setParty(result.getString("PARTY"));
				c.setGender(result.getString("GENDER"));
				c.setDeity(result.getString("DEITY"));
				c.setPlayerName(result.getString("PLAYER_NAME"));
				c.setXp(result.getInt("XP"));

				c.setSizeOverride(result.getString("SIZE_OVERRIDE"));
				c.setModNotes(result.getString("MOD_NOTES"));
				c.setPicture(result.getString("PICTURE"));
				c.setIcon(result.getString("ICON"));
				c.setSize(result.getString("Size"));

				c.setNaturalArmor(result.getInt("NATURAL_ARMOR"));
				c.setMiscArmor(result.getInt("MISC_ARMOR"));

				// create the stat block. These are the non modified
				// stat components. They take into account all permanent
				// stats, but not any temporary ones, like spells, poison,
				// etc.
				StatBlock sb = new StatBlock(c.getRace(), c.getSize());
				sb.setBaseStr(result.getInt("STR"));
				sb.setBaseWis(result.getInt("WIS"));
				sb.setBaseDex(result.getInt("DEX"));
				sb.setBaseCon(result.getInt("CON"));
				sb.setBaseCha(result.getInt("CHA"));
				sb.setBaseInt(result.getInt("INTEL"));

				sb.setLevelStr(result.getInt("LEVEL_STR"));
				sb.setLevelWis(result.getInt("LEVEL_WIS"));
				sb.setLevelDex(result.getInt("LEVEL_DEX"));
				sb.setLevelCon(result.getInt("LEVEL_CON"));
				sb.setLevelCha(result.getInt("LEVEL_CHA"));
				sb.setLevelInt(result.getInt("LEVEL_INT"));

				sb.setMiscStr(result.getInt("MISC_STR"));
				sb.setMiscWis(result.getInt("MISC_WIS"));
				sb.setMiscDex(result.getInt("MISC_DEX"));
				sb.setMiscCon(result.getInt("MISC_CON"));
				sb.setMiscCha(result.getInt("MISC_CHA"));
				sb.setMiscInt(result.getInt("MISC_INT"));

				sb.setMiscStrText(result.getString("MISC_STR_TEXT"));
				sb.setMiscWisText(result.getString("MISC_WIS_TEXT"));
				sb.setMiscDexText(result.getString("MISC_DEX_TEXT"));
				sb.setMiscConText(result.getString("MISC_CON_TEXT"));
				sb.setMiscChaText(result.getString("MISC_CHA_TEXT"));
				sb.setMiscIntText(result.getString("MISC_INT_TEXT"));

				sb.setBookStr(result.getInt("BOOK_STR"));
				sb.setBookWis(result.getInt("BOOK_WIS"));
				sb.setBookDex(result.getInt("BOOK_DEX"));
				sb.setBookCon(result.getInt("BOOK_CON"));
				sb.setBookCha(result.getInt("BOOK_CHA"));
				sb.setBookInt(result.getInt("BOOK_INT"));

				c.setStats(sb);

				PlayerClassDAO pcdb = new PlayerClassDAO();
				c.getClassSet().setClassVector(
						pcdb.getPlayerClasses("" + c.getID(), dbs2));

				boolean monk = false;
				ClassAbilitiesDAO cladb = new ClassAbilitiesDAO();

				// run through the classes to extract the abilities, and also
				// to check for special cases.
				for (int i = 0; i < c.getClassSet().getClassVector().size(); i++) {
					ClassSlot cs = (ClassSlot) c.getClassSet().getClassVector()
							.get(i);
					CharClass cc = cs.getClassName();

					cc.setClassAbilities(cladb.getClassAbilities(cc, cs
							.getLevel()));

					// if they are a monk, adjust speed and unarmed attacks.
					if (cs.getClassName().getCharClass().equalsIgnoreCase(
							"Monk")) {
						monk = true;
						CharClassDAO db = new CharClassDAO(dbs2);
						ClassAdvancement ca = db.getAlternateAdvancement(cs
								.getClassName(), cs.getLevel());
						String move = ca.getWillSave();
						String speed = "";
						if (c.getSize().equals("Small")) {
							speed = move.substring(0, move.indexOf("/"));
						} else {
							speed = move.substring(move.indexOf("/") + 1, move
									.length());
						}
						logger.log("GETTING BASE MOVE");
						c.setBaseMovement(Integer.parseInt(speed));
						logger.log("GOT IT");
						break;
					}
				}
				if (!monk) {
					c.setBaseMovement(result.getInt("BaseSpeed"));
				}

				SaveBlock sv = new SaveBlock(c);
				sv.setMiscFort(result.getInt("MISC_FORT"));
				sv.setMiscRef(result.getInt("MISC_REF"));
				sv.setMiscWill(result.getInt("MISC_WILL"));
				sv.setMiscFortText(result.getString("MISC_FORT_TEXT"));
				sv.setMiscRefText(result.getString("MISC_REF_TEXT"));
				sv.setMiscWillText(result.getString("MISC_WILL_TEXT"));

				c.setHeight(result.getString("HEIGHT"));
				c.setWeight(result.getString("WEIGHT"));
				c.setAge(result.getString("AGE"));
				c.setEyes(result.getString("EYE_COLOR"));
				c.setHair(result.getString("HAIR_COLOR"));

				c.setMiscArmorText(result.getString("MISC_ARMOR_TEXT"));
				c.setBaseMovementOverride(result.getInt("BASE_MOVE"));
				c.setPlayerNotes(result.getString("PLAYER_NOTES"));
				c.setDamageReduction(result.getString("DAMAGE_REDUCTION"));
				c.setSpellResist(result.getString("SPELL_RESIST"));
				c.setMarchOrder(result.getString("MARCH_ORDER"));
				c.setSleepShift(result.getString("SLEEP_SHIFT"));
				c.setPartyId(result.getString("PARTY_ID"));
				c.setSaves(sv);

				c.setMoney(new MoneyBlock(result.getInt("CP"), result
						.getInt("SP"), result.getInt("GP"),
						result.getInt("PP"), result.getInt("STORED_GOLD")));

				// pass in an open connection to each of the child tables, so
				// that
				// we increase the access speed.
				PartyDAO pdb = new PartyDAO();
				Party p = pdb.getParty(c.getPartyId());
				
				CampaignDAO cdb = new CampaignDAO();
				Campaign cmp = cdb.getCampaign(p.getCampaignId());
				
				c.setRulesetId(cmp.getRulesetId());
				c.setRulesetName(cmp.getRulesetName());
				
				PlayerLanguagesDAO pldb = new PlayerLanguagesDAO(dbs2);
				c.setLanguages(pldb.getPlayerLanguages("" + c.getID()));

				PlayerFavoredEnemyDAO pfedb = new PlayerFavoredEnemyDAO(dbs2);
				c.setFavoredEnemies(pfedb.getFavoredEnemies("" + c.getID()));

				PlayerFeatsDAO pfdb = new PlayerFeatsDAO(dbs2);
				c.setFeatList(pfdb.getPlayerFeats("" + c.getID()));

				PlayerArmorDAO padb = new PlayerArmorDAO(dbs2);
				c.setArmor(padb.getPlayerArmor("" + c.getID()));
				c.setShield(padb.getPlayerShield("" + c.getID()));
				c.setRestArmor(padb.getPlayerAtRestArmor("" + c.getID()));

				PlayerSkillsDAO psdb = new PlayerSkillsDAO(dbs2);

				c.getSkillSet().setSkillList(
						psdb.getPlayerSkills("" + c.getID()));

				JumpBlock jb = new JumpBlock(c);
				c.setJump(jb);

				PlayerWeaponsDAO pwdb = new PlayerWeaponsDAO(dbs2);
				c.setWeapons(pwdb.getPlayerWeapons("" + c.getID()));

				PlayerAmmoDAO pamdb = new PlayerAmmoDAO(dbs2);
				c.setAmmo(pamdb.getPlayerAmmo("" + c.getID()));

				// applyFeats(c);

				PlayerItemsDAO pidb = new PlayerItemsDAO(dbs2);
				c.setItems(pidb.getPlayerItems("" + c.getID()));
				c.setDroppedLocations(pidb.getDroppedLocations("" + c.getID()));

				PlayerHpDAO phpdb = new PlayerHpDAO(dbs2);
				c.setHpList(phpdb.getPlayerHp("" + c.getID()));

				PlayerTempModDAO ptdb = new PlayerTempModDAO(dbs2);
				c.setTempMods(ptdb.getPlayerTempMods("" + c.getID()));

				PlayerDomainDAO pddb = new PlayerDomainDAO(dbs2);
				c.setDomains(pddb.getPlayerDomains("" + c.getID()));

				PlayerSpellsDAO pspdb = new PlayerSpellsDAO();
				c.setSpellsKnown(pspdb.getPlayerSpells("" + c.getID()));

				PlayerSpellsMemorizedDAO psmdb = new PlayerSpellsMemorizedDAO();
				c.setSpellsMemorized(psmdb.getPlayerSpellsMemorized(""
						+ c.getID()));

				c.setCurrentHitPoints(Integer.parseInt(c.getHpCalc()
						.getDisplayValue()));

				dbs2.cleanup();
				c.setPrecalcAc(c.getAC());

				os.add(c);

			}

		} catch (SQLException e) {
			logger.log("ERROR in getchars: " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR in getchars : " + uhe.toString());
			
		} finally {
			dbs.close();
			// dbs2.close();
		}
		return os;

	}

	// public void applyFeats(DCharacter c) {
	// Vector<PlayerFeats> feats = c.getFeatList();
	// Vector<Weapon> weapons = c.getWeapons();

	// }

	public int getXP(String plevel, String clevel) {
		try {
			// open the connection
			dbs.open();

			int adjustedCr = Integer.parseInt(clevel);
			int mod = 1;
			while (adjustedCr > 20){
				adjustedCr -= 2;
				mod = mod*2;
			}
			
			
			String queryString = "SELECT * FROM XP_TABLE WHERE PARTY_LEVEL='"
					+ plevel + "' AND CR='" + adjustedCr + "'";

			// logger.log("Executing Query :"+queryString);

			ResultSet result = dbs.executeSQLQuery(queryString);

			if (result.next()) {
				logger.log("Getting XP");
				int xp = Integer.parseInt(result.getString("XP"));
				// logger.log("Returning "+xp);
				dbs.close();
				return xp*mod;
			}
		} catch (SQLException e) {
			logger.log("ERROR : " + e);
			dbs.close();
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe);
			dbs.close();
		}
		// logger.log("NO entry found");
		return -1;
	}

	public Treasure getTreasure(String el, String type, String roll) {
		Treasure t = new Treasure();
		try {
			// open the connection
			dbs.open();

			String queryString = "SELECT * FROM TREASURE_TYPES WHERE "
					+ "RANGE_START <= " + roll + " AND RANGE_END >= " + roll
					+ " AND " + "TREASURE_TYPE='" + type
					+ "' AND ENCOUNTER_LEVEL = " + el;

			ResultSet result = dbs.executeSQLQuery(queryString);

			if (result.next()) {
				t.setAmount(result.getString("TREASURE_AMT"));
				t.setTreasure(result.getString("TREASURE"));
			} else {
				
			}

			dbs.close();

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		return t;
	}

	public Treasure getGoodsTreasure(String roll, String type) {
		Treasure t = null;
		try {
			// open the connection
			dbs.open();

			String queryString = "SELECT * FROM GOODS_TREASURE WHERE "
					+ "RANGE_START <= " + roll + " AND RANGE_END >= " + roll
					+ " AND " + "TREASURE_TYPE='" + type + "'";

			ResultSet result = dbs.executeSQLQuery(queryString);

			if (result.next()) {
				t = new Treasure();
				t.setAmount(result.getString("VALUE_AMT"));
				t.setTreasure(result.getString("VALUE"));
			}

			dbs.close();

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		return t;
	}

	public Treasure getMundaneTreasure(String roll) {
		Treasure t = null;
		try {
			// open the connection
			dbs.open();

			String queryString = "SELECT * FROM MUNDANE_TREASURE WHERE "
					+ "RANGE_START <= " + roll + " AND RANGE_END >= " + roll;

			ResultSet result = dbs.executeSQLQuery(queryString);

			if (result.next()) {
				t = new Treasure();
				t.setAmount(result.getString("AMOUNT"));
				t.setTreasure(result.getString("NAME"));
				t.setCategory(result.getString("TYPE"));
				if (t.getCategory() == null) {
					t.setCategory("");
				}
			}

			dbs.close();

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		return t;
	}

	public String getRandomMagicCategory(String roll, String type) {
		String s = "";
		try {
			// open the connection
			dbs.open();

			String queryString = "SELECT * FROM RANDOM_MAGIC WHERE "
					+ " RANGE_START <= " + roll + " AND RANGE_END >= " + roll
					+ " AND TYPE='" + type + "'";

			ResultSet result = dbs.executeSQLQuery(queryString);

			if (result.next()) {
				s = result.getString("CATEGORY");
			}

			dbs.close();

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		return s;
	}

	public Vector<Weapon> getExoticWeapons() {
		Vector<Weapon> v = new Vector<Weapon>();
		try {
			// open the connection
			dbs.open();

			String queryString = "SELECT * FROM WEAPONS WHERE "
					+ "category = 'EXOTIC'";

			ResultSet result = dbs.executeSQLQuery(queryString);

			while (result.next()) {
				Weapon w = new Weapon();
				w.setName(result.getString("NAME"));
				w.setType(result.getString("RANGEDMELEE"));

				v.add(w);
			}

			dbs.close();

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		return v;
	}

	public Vector<Weapon> getRangedWeapons() {
		Vector<Weapon> v = new Vector<Weapon>();
		try {
			// open the connection
			dbs.open();

			String queryString = "SELECT * FROM WEAPONS WHERE "
					+ "category <> 'Exotic' and RANGEDMELEE = 'Ranged'";

			ResultSet result = dbs.executeSQLQuery(queryString);

			while (result.next()) {
				Weapon w = new Weapon();
				w.setName(result.getString("NAME"));
				w.setType(result.getString("RANGEDMELEE"));
				v.add(w);
			}

			dbs.close();

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		return v;
	}

	public Vector<Weapon> getMeleeWeapons() {
		Vector<Weapon> v = new Vector<Weapon>();
		try {
			// open the connection
			dbs.open();

			String queryString = "SELECT * FROM WEAPONS WHERE "
					+ "category <> 'Exotic' and RANGEDMELEE = 'Melee'";

			ResultSet result = dbs.executeSQLQuery(queryString);

			while (result.next()) {
				Weapon w = new Weapon();
				w.setName(result.getString("NAME"));
				w.setType(result.getString("RANGEDMELEE"));
				v.add(w);
			}

			dbs.close();

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		return v;
	}

	public Armor getMagicArmor(String roll, String type) {
		Armor a = new Armor();
		try {
			// open the connection
			dbs.open();

			String queryString = "SELECT * FROM MAGIC_ARMOR WHERE "
					+ " RANGE_START <= " + roll + " AND RANGE_END >= " + roll
					+ " AND CLASS='" + type + "'";

			ResultSet result = dbs.executeSQLQuery(queryString);

			if (result.next()) {
				a.setBonus(result.getString("BONUS"));
				a.setType(result.getString("CATEGORY"));
			}

			dbs.close();

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		return a;
	}

	public String getArmorType(String roll, String type) {
		String s = "";
		try {
			// open the connection
			dbs.open();

			String queryString = "SELECT * FROM RANDOM_ARMOR_TYPES WHERE "
					+ " RANGE_START <= " + roll + " AND RANGE_END >= " + roll
					+ " AND TYPE='" + type + "'";

			ResultSet result = dbs.executeSQLQuery(queryString);

			if (result.next()) {
				s = result.getString("NAME");
			} else {
				
			}

			dbs.close();

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		return s;
	}

	public Ability getAbility(String roll, String level, String type) {
		Ability a = new Ability();
		try {
			// open the connection
			dbs.open();

			String queryString = "SELECT * FROM MAGIC_ARMOR_ABILITIES WHERE "
					+ " RANGE_START <= " + roll + " AND RANGE_END >= " + roll
					+ " AND TYPE='" + type + "' and LEVEL = '" + level + "'";

			ResultSet result = dbs.executeSQLQuery(queryString);

			if (result.next()) {
				a.setName(result.getString("ABILITY"));
				a.setDescription(result.getString("DESCRIPTION"));
			} else {
				
			}

			dbs.close();

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		return a;
	}

	public WeaponAbilities getWeaponAbility(String roll, String level,
			String type) {
		WeaponAbilities a = new WeaponAbilities();
		try {
			// open the connection
			dbs.open();

			String queryString = "SELECT * FROM MAGIC_WEAPON_ABILITIES WHERE "
					+ " RANGE_START <= " + roll + " AND RANGE_END >= " + roll
					+ " AND TYPE='" + type + "' and LEVEL = '" + level + "'";

			ResultSet result = dbs.executeSQLQuery(queryString);

			if (result.next()) {
				a.setName(result.getString("ABILITY"));
				a.setDescription(result.getString("DESCRIPTION"));
			} else {
				
			}

			dbs.close();

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		return a;
	}

	public SpecificArmor getSpecificArmor(String roll, String level, String type) {
		SpecificArmor a = new SpecificArmor();
		try {
			// open the connection
			dbs.open();

			int rollInt = Integer.parseInt(roll);
			// open the connection
			dbs.open();
			String rating = "common";
			if (rollInt > 40 && rollInt <= 70) {
				rating = "uncommon";
			} else if (rollInt > 70 && rollInt <= 90) {
				rating = "rare";
			} else if (rollInt > 90) {
				rating = "very rare";
			}

			String queryString = "SELECT * FROM SPECIFIC_ARMOR WHERE " + level
					+ "_LEVEL = '" + rating + "' AND TYPE = '" + type + "'";


			ResultSet result = dbs.executeSQLQuery(queryString);

			Vector<SpecificArmor> armors = new Vector<SpecificArmor>();
			while (result.next()) {
				SpecificArmor sw = new SpecificArmor();
				sw.setName(result.getString("NAME"));
				sw.setDescription(result.getString("DESCRIPTION"));
				sw.setSource(result.getString("SOURCE"));
				armors.add(sw);
			}

			a = armors.get(RandomRoll.getRandom(armors.size()));

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		} finally {
			dbs.close();
		}
		return a;
	}

	public SpecificWeapon getSpecificWeapon(String roll, String level) {
		SpecificWeapon a = new SpecificWeapon();
		try {
			int rollInt = Integer.parseInt(roll);
			// open the connection
			dbs.open();
			String rating = "common";
			if (rollInt > 40 && rollInt <= 70) {
				rating = "uncommon";
			} else if (rollInt > 70 && rollInt <= 90) {
				rating = "rare";
			} else if (rollInt > 90) {
				rating = "very rare";
			}

			String queryString = "SELECT * FROM SPECIFIC_WEAPON WHERE " + level
					+ "_LEVEL = '" + rating + "'";

			

			ResultSet result = dbs.executeSQLQuery(queryString);

			Vector<SpecificWeapon> weapons = new Vector<SpecificWeapon>();
			while (result.next()) {
				SpecificWeapon sw = new SpecificWeapon();
				sw.setName(result.getString("NAME"));
				sw.setDescription(result.getString("DESCRIPTION"));
				sw.setSource(result.getString("SOURCE"));
				weapons.add(sw);
			}

			a = weapons.get(RandomRoll.getRandom(weapons.size()));

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		} finally {
			dbs.close();
		}
		return a;
	}

	public Ring getMagicRing(String roll, String level) {
		Ring a = new Ring();
		try {
			// open the connection
			dbs.open();

			String queryString = "SELECT * FROM MAGIC_RINGS WHERE "
					+ " RANGE_START <= " + roll + " AND RANGE_END >= " + roll
					+ " AND LEVEL = '" + level + "'";

			ResultSet result = dbs.executeSQLQuery(queryString);

			if (result.next()) {
				a.setName(result.getString("NAME"));
				a.setDescription(result.getString("DESCRIPTION"));
			}

			dbs.close();

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		return a;
	}

	public Rods getMagicRod(String roll, String level) {
		Rods a = new Rods();
		RodsDAO db = new RodsDAO();
		a.setMlevel(level);
		Vector<Rods> v = db.selectRods(a);

		a = v.get(RandomRoll.getRandom(v.size()));
		return a;
	}

	public Staffs getMagicStaff(String roll, String level) {
		Staffs a = new Staffs();
		StaffsDAO db = new StaffsDAO();
		a.setMlevel(level);
		Vector<Staffs> v = db.selectStaffs(a);
		a = v.get(RandomRoll.getRandom(v.size()));

		return a;
	}

	public Wand getMagicWand(String roll, String level) {
		Wand a = new Wand();
		try {
			// open the connection
			dbs.open();

			String queryString = "SELECT * FROM MAGIC_WANDS WHERE "
					+ " RANGE_START <= " + roll + " AND RANGE_END >= " + roll
					+ " AND LEVEL = '" + level + "'";

			ResultSet result = dbs.executeSQLQuery(queryString);

			if (result.next()) {
				a.setName(result.getString("NAME"));
				a.setDescription(result.getString("DESCRIPTION"));
			}

			dbs.close();

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		return a;
	}

	public Weapon getMagicWeapon(String roll, String type) {
		Weapon a = new Weapon();
		try {
			// open the connection
			dbs.open();

			String queryString = "SELECT * FROM MAGIC_WEAPON WHERE "
					+ " RANGE_START <= " + roll + " AND RANGE_END >= " + roll
					+ " AND LEVEL='" + type + "'";

			ResultSet result = dbs.executeSQLQuery(queryString);

			if (result.next()) {
				a.setBonus(result.getString("BONUS"));
				a.setType(result.getString("TYPE"));
			}

			dbs.close();

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		return a;
	}

	public Wonderous getWonderousItem(String roll, String type) {
		Wonderous a = new Wonderous();
		WonderousItemsDAO db = new WonderousItemsDAO();
		a.setLevel(type);
		Vector<Wonderous> v = db.selectWonderousItems(a);

		a = v.get(RandomRoll.getRandom(v.size()));
		return a;
	}

	public Potion getPotion(String roll, String type) {
		Potion a = new Potion();
		try {
			// open the connection
			dbs.open();

			String queryString = "SELECT * FROM POTIONS WHERE "
					+ " RANGE_START <= " + roll + " AND RANGE_END >= " + roll
					+ " AND LEVEL='" + type + "'";

			ResultSet result = dbs.executeSQLQuery(queryString);

			if (result.next()) {
				a.setName(result.getString("NAME"));
				a.setDescription(result.getString("DESCRIPTION"));
			}

			dbs.close();

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		return a;
	}

	public int getScrollLevel(String roll, String type) {
		int level = 0;
		try {
			// open the connection
			dbs.open();

			String queryString = "SELECT * FROM SCROLL_LEVEL WHERE "
					+ " RANGE_START <= " + roll + " AND RANGE_END >= " + roll
					+ " AND LEVEL='" + type + "'";

			ResultSet result = dbs.executeSQLQuery(queryString);

			if (result.next()) {
				level = result.getInt("spell_level");
			}

			dbs.close();

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		return level;
	}

	public String getScrollSpell(String roll, int type, String spellType) {
		String spell = "";
		try {
			// open the connection
			dbs.open();

			String queryString = "SELECT * FROM SCROLL_SPELLS WHERE "
					+ " RANGE_START <= " + roll + " AND RANGE_END >= " + roll
					+ " AND LEVEL='" + type + "' AND TYPE = '" + spellType
					+ "'";

			ResultSet result = dbs.executeSQLQuery(queryString);

			if (result.next()) {
				spell = result.getString("NAME");
			}

			dbs.close();

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		return spell;
	}

	public int getSizeMod(String id) {

		if (sizeMods == null) {
			sizeMods = new Vector<SizeMod>();
			try {
				String selectString = "SELECT * FROM SIZE ";

				ResultSet result = null;
				if (dbs2 == null) {
					dbs.open();
					result = dbs.executeSQLQuery(selectString);
				} else {
					result = dbs2.executeSQLQuery(selectString);
				}

				while (result.next()) {
					String s = result.getString("SIZE");
					int i = result.getInt("AC_MOD");
					SizeMod sm = new SizeMod();
					sm.setSize(s);
					sm.setMod(i);
					sizeMods.add(sm);
				}

			} catch (SQLException e) {
				logger.log("ERROR : " + e.toString());
			} catch (Exception uhe) {
				logger.log("ERROR : " + uhe.toString());
			} finally {
				if (dbs2 == null) {
					dbs.close();
				}
			}
		}

		for (int i = 0; i < sizeMods.size(); i++) {
			SizeMod sm = sizeMods.get(i);
			if (sm.getSize().equals(id)) {
				return sm.getMod();
			}
		}
		return 0;
	}

	public void p(String s) {
		logger.log(s);
	}

}

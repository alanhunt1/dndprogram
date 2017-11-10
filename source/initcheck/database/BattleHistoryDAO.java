package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class BattleHistoryDAO extends InitBaseDAO {

	public BattleHistoryDAO() {

	}

	public int getBattleId() {
		int i = 0;
		try {
			// open the connection
			dbs.open();
			String command = "SELECT MAX(ID) as MAXID FROM BATTLE_HISTORY";
			
			ResultSet result = dbs.executeSQLQuery(command);
			if (result.next()) {
				i = result.getInt("MAXID");
			}

		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}

		// close the connection
		dbs.close();
		return i + 1;
	}

	public int addOrUpdateBattleHistory(BattleHistory o) {
		int i = -1;
		if (o.getId() != null) {
			updateBattleHistory(o);
		} else {
			addBattleHistory(o);
		}
		try {
			// open the connection
			dbs.open();
			String command = "SELECT MAX(ID) as MAXID FROM BATTLE_HISTORY";
			
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

	public void addBattleHistory(BattleHistory o) {
		String valueString = "";
		String insertString = "INSERT INTO BATTLE_HISTORY (";

		if (o.getParty() != null) {
			insertString += "PARTY,";
			valueString += "'" + dbs.escapeQuotes(o.getParty()) + "',";
		}
		if (o.getMembers() != null) {
			insertString += "MEMBERS,";
			valueString += "'" + dbs.escapeQuotes(o.getMembers()) + "',";
		}

		insertString += "BATTLE_DATE,";
		valueString += "now(),";

		if (o.getPartyLevel() != null) {
			insertString += "PARTY_LEVEL,";
			valueString += "'" + dbs.escapeQuotes(o.getPartyLevel()) + "',";
		}
		if (o.getEncounterLevel() != null) {
			insertString += "ENCOUNTER_LEVEL,";
			valueString += "'" + dbs.escapeQuotes(o.getEncounterLevel()) + "',";
		}

		if (o.getMaxDamage() != null) {
			insertString += "MAX_DAMAGE,";
			valueString += "'" + dbs.escapeQuotes(o.getMaxDamage()) + "',";
		}

		if (o.getMaxRoundDamage() != null) {
			insertString += "MAX_ROUND_DAMAGE,";
			valueString += "'" + dbs.escapeQuotes(o.getMaxRoundDamage()) + "',";
		}

		if (o.getTotalDamage() != null) {
			insertString += "TOTAL_DAMAGE,";
			valueString += "'" + dbs.escapeQuotes(o.getTotalDamage()) + "',";
		}

		if (o.getTotalTaken() != null) {
			insertString += "TOTAL_TAKEN,";
			valueString += "'" + dbs.escapeQuotes(o.getTotalTaken()) + "',";
		}

		if (o.getNumRounds() != null) {
			insertString += "NUM_ROUNDS,";
			valueString += "" + dbs.escapeQuotes(o.getNumRounds()) + ",";
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
		}
	}

	public void updateBattleHistory(BattleHistory o) {
		String updateString = "update BATTLE_HISTORY set ";
		if (o.getParty() != null) {
			updateString += "PARTY='" + dbs.escapeQuotes(o.getParty()) + "',";
		}
		if (o.getMembers() != null) {
			updateString += "MEMBERS='" + dbs.escapeQuotes(o.getMembers())
					+ "',";
		}
		if (o.getBattleDate() != null) {
			updateString += "BATTLE_DATE='"
					+ dbs.escapeQuotes(o.getBattleDate()) + "',";
		}
		if (o.getPartyLevel() != null) {
			updateString += "PARTY_LEVEL='"
					+ dbs.escapeQuotes(o.getPartyLevel()) + "',";
		}
		if (o.getEncounterLevel() != null) {
			updateString += "ENCOUNTER_LEVEL='"
					+ dbs.escapeQuotes(o.getEncounterLevel()) + "',";
		}
		if (o.getTotalDamage() != null) {
			updateString += "TOTAL_DAMAGE='"
					+ dbs.escapeQuotes(o.getTotalDamage()) + "',";
		}
		if (o.getTotalTaken() != null) {
			updateString += "TOTAL_TAKEN='"
					+ dbs.escapeQuotes(o.getTotalTaken()) + "',";
		}
		if (o.getMaxDamage() != null) {
			updateString += "MAX_DAMAGE='" + dbs.escapeQuotes(o.getMaxDamage())
					+ "',";
		}
		if (o.getMaxRoundDamage() != null) {
			updateString += "MAX_ROUND_DAMAGE='"
					+ dbs.escapeQuotes(o.getMaxRoundDamage()) + "',";
		}
		if (o.getNumRounds() != null) {
			if (!o.getNumRounds().equals("")) {
				updateString += "NUM_ROUNDS="
						+ dbs.escapeQuotes(o.getNumRounds()) + ",";
			} else {
				updateString += "NUM_ROUNDS=null,";
			}
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + " ";
		try {
			dbs.open();
			dbs.executeSQLCommand(updateString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}

	public Vector<BattleHistory> selectBattleHistory(BattleHistory o) {
		String selectString = "SELECT * FROM BATTLE_HISTORY  ";
		boolean first = true;
		if (o.getId() != null && !o.getId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ID = '" + dbs.escapeQuotes(o.getId()) + "' ";
		}
		if (o.getParty() != null && !o.getParty().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " PARTY = '" + dbs.escapeQuotes(o.getParty())
					+ "' ";
		}
		if (o.getMembers() != null && !o.getMembers().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " MEMBERS = '" + dbs.escapeQuotes(o.getMembers())
					+ "' ";
		}
		if (o.getBattleDate() != null && !o.getBattleDate().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " BATTLE_DATE = '"
					+ dbs.escapeQuotes(o.getBattleDate()) + "' ";
		}
		if (o.getPartyLevel() != null && !o.getPartyLevel().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " PARTY_LEVEL = '"
					+ dbs.escapeQuotes(o.getPartyLevel()) + "' ";
		}
		if (o.getEncounterLevel() != null && !o.getEncounterLevel().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ENCOUNTER_LEVEL = '"
					+ dbs.escapeQuotes(o.getEncounterLevel()) + "' ";
		}
		Vector<BattleHistory> v = new Vector<BattleHistory>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				BattleHistory obj = new BattleHistory();

				obj.setId(result.getString("ID"));
				obj.setParty(result.getString("PARTY"));
				obj.setMembers(result.getString("MEMBERS"));
				obj.setBattleDate(result.getString("BATTLE_DATE"));
				obj.setPartyLevel(result.getString("PARTY_LEVEL"));
				obj.setEncounterLevel(result.getString("ENCOUNTER_LEVEL"));
				obj.setMaxDamage(result.getString("MAX_DAMAGE"));
				obj.setMaxRoundDamage(result.getString("MAX_ROUND_DAMAGE"));
				obj.setTotalDamage(result.getString("TOTAL_DAMAGE"));
				obj.setTotalTaken(result.getString("TOTAL_TAKEN"));
				obj.setNumRounds(result.getString("NUM_ROUNDS"));
				v.add(obj);
			}
		} catch (SQLException sqle) {
			logger.log("error", sqle.toString());
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
		return v;
	}

	public Vector<String> getParties() {
		String selectString = "SELECT DISTINCT(PARTY) FROM BATTLE_HISTORY";
		Vector<String> v = new Vector<String>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				v.add(result.getString("PARTY"));
			}
		} catch (SQLException sqle) {
			logger.log("error", sqle.toString());
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
		return v;
	}

	public void deleteBattleHistory(BattleHistory o) {
		String deleteString = "delete from BATTLE_HISTORY  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + " ";

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}
}

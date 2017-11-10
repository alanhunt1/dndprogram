package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class MonsterKillsDAO extends InitBaseDAO {

	public MonsterKillsDAO() {

	}

	public void updateId(String oldId, String id) {
		String sqlString = "UPDATE MONSTER_KILLS SET PLAYER_ID = " + id
				+ " WHERE " + "PLAYER_ID = " + oldId;

		try {
			dbs.open();
			dbs.executeSQLCommand(sqlString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}

	public void addMonsterKills(MonsterKills o) {
		String valueString = "";
		String insertString = "INSERT INTO MONSTER_KILLS (";

		if (o.getMonsterId() != null) {
			insertString += "MONSTER_ID,";
			if (o.getMonsterId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getMonsterId()) + ",";

			}
		}
		if (o.getMonsterName() != null) {
			insertString += "MONSTER_NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getMonsterName()) + "',";
		}
		if (o.getPlayerId() != null) {
			insertString += "PLAYER_ID,";
			if (o.getPlayerId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getPlayerId()) + ",";

			}
		}
		if (o.getPlayerName() != null) {
			insertString += "PLAYER_NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getPlayerName()) + "',";
		}
		if (o.getBattleId() != null) {
			insertString += "BATTLE_ID,";
			if (o.getBattleId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getBattleId()) + ",";

			}
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

	public void updateMonsterKills(MonsterKills o) {
		String updateString = "update MONSTER_KILLS set ";
		if (o.getMonsterId() != null) {
			if (!o.getMonsterId().equals("")) {
				updateString += "MONSTER_ID="
						+ dbs.escapeQuotes(o.getMonsterId()) + ",";
			} else {
				updateString += "MONSTER_ID=null,";
			}
		}
		if (o.getMonsterName() != null) {
			updateString += "MONSTER_NAME='"
					+ dbs.escapeQuotes(o.getMonsterName()) + "',";
		}
		if (o.getPlayerId() != null) {
			if (!o.getPlayerId().equals("")) {
				updateString += "PLAYER_ID="
						+ dbs.escapeQuotes(o.getPlayerId()) + ",";
			} else {
				updateString += "PLAYER_ID=null,";
			}
		}
		if (o.getPlayerName() != null) {
			updateString += "PLAYER_NAME='"
					+ dbs.escapeQuotes(o.getPlayerName()) + "',";
		}
		if (o.getBattleId() != null) {
			if (!o.getBattleId().equals("")) {
				updateString += "BATTLE_ID="
						+ dbs.escapeQuotes(o.getBattleId()) + ",";
			} else {
				updateString += "BATTLE_ID=null,";
			}
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + "  ";
		try {
			dbs.open();
			dbs.executeSQLCommand(updateString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}

	public Vector<MonsterKills> getMonsterKills(String playerId) {
		MonsterKills mk = new MonsterKills();
		mk.setPlayerId(playerId);
		return selectMonsterKills(mk);
	}

	public Vector<MonsterKills> selectMonsterKills(MonsterKills o) {
		String selectString = "SELECT * FROM MONSTER_KILLS  ";
		boolean first = true;
		if (o.getId() != null && !o.getId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ID = " + dbs.escapeQuotes(o.getId()) + " ";
		}
		if (o.getMonsterId() != null && !o.getMonsterId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " MONSTER_ID = "
					+ dbs.escapeQuotes(o.getMonsterId()) + " ";
		}
		if (o.getMonsterName() != null && !o.getMonsterName().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " MONSTER_NAME = '"
					+ dbs.escapeQuotes(o.getMonsterName()) + "' ";
		}
		if (o.getPlayerId() != null && !o.getPlayerId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " PLAYER_ID = " + dbs.escapeQuotes(o.getPlayerId())
					+ " ";
		}
		if (o.getPlayerName() != null && !o.getPlayerName().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " PLAYER_NAME = '"
					+ dbs.escapeQuotes(o.getPlayerName()) + "' ";
		}
		if (o.getBattleId() != null && !o.getBattleId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " BATTLE_ID = " + dbs.escapeQuotes(o.getBattleId())
					+ " ";
		}
		selectString += " ORDER BY MONSTER_ID ";

		Vector<MonsterKills> v = new Vector<MonsterKills>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				MonsterKills obj = new MonsterKills();

				obj.setId(result.getString("ID"));
				obj.setMonsterId(result.getString("MONSTER_ID"));
				obj.setMonsterName(result.getString("MONSTER_NAME"));
				obj.setPlayerId(result.getString("PLAYER_ID"));
				obj.setPlayerName(result.getString("PLAYER_NAME"));
				obj.setBattleId(result.getString("BATTLE_ID"));
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

	public void deleteMonsterKills(MonsterKills o) {
		String deleteString = "delete from MONSTER_KILLS  ";
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

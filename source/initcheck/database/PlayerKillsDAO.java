package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PlayerKillsDAO extends InitBaseDAO {

	public PlayerKillsDAO() {

	}

	public void updateId(String oldId, String id) {
		String sqlString = "UPDATE PLAYER_KILLS SET PLAYER_ID = " + id
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

	public int getLifetimeKills(String playerId) {
		String selectString = "SELECT COUNT('x') as KILLCOUNT FROM PLAYER_KILLS "
				+ " WHERE PLAYER_ID = " + playerId;

		int kills = 0;
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			if (result.next()) {
				kills = result.getInt("KILLCOUNT");
			}
		} catch (SQLException sqle) {
			logger.log("error", sqle.toString());
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
		return kills;
	}

	public Vector<String> getKillStatus(String battleId) {
		String selectString = "select player_name, count('x') as killcount  "
				+ "from player_kills where battle_id = " + battleId
				+ " group by player_name " + "order by count('x') desc";

		Vector<String> v = new Vector<String>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				v.add(result.getString("PLAYER_NAME") + " "
						+ result.getString("KILLCOUNT"));
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

	public void addPlayerKills(PlayerKills o) {
		String valueString = "";
		String insertString = "INSERT INTO PLAYER_KILLS (";
		if (o.getId() != null) {
			insertString += "ID,";
			valueString += "'" + dbs.escapeQuotes(o.getId()) + "',";
		}
		if (o.getPlayerId() != null) {
			insertString += "PLAYER_ID,";
			if (o.getPlayerId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getPlayerId()) + ",";

			}
		}
		if (o.getMonsterId() != null) {
			insertString += "MONSTER_ID,";
			if (o.getMonsterId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getMonsterId()) + ",";

			}
		}

		if (o.getPlayerName() != null) {
			insertString += "PLAYER_NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getPlayerName()) + "',";
		}

		if (o.getMonsterName() != null) {
			insertString += "MONSTER_NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getMonsterName()) + "',";
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

	public void updatePlayerKills(PlayerKills o) {
		String updateString = "update PLAYER_KILLS set ";
		if (o.getPlayerId() != null) {
			if (!o.getPlayerId().equals("")) {
				updateString += "PLAYER_ID="
						+ dbs.escapeQuotes(o.getPlayerId()) + ",";
			} else {
				updateString += "PLAYER_ID=null,";
			}
		}
		if (o.getMonsterId() != null) {
			if (!o.getMonsterId().equals("")) {
				updateString += "MONSTER_ID="
						+ dbs.escapeQuotes(o.getMonsterId()) + ",";
			} else {
				updateString += "MONSTER_ID=null,";
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

	public Vector<PlayerKills> getLifetimeKillListing(String playerId) {
		PlayerKills pk = new PlayerKills();
		pk.setPlayerId(playerId);
		return selectPlayerKills(pk);
	}

	public Vector<PlayerKills> selectPlayerKills(PlayerKills o) {
		String selectString = "SELECT * FROM PLAYER_KILLS  ";
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

		selectString += " ORDER BY MONSTER_ID";

		Vector<PlayerKills> v = new Vector<PlayerKills>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				PlayerKills obj = new PlayerKills();

				obj.setId(result.getString("ID"));
				obj.setPlayerId(result.getString("PLAYER_ID"));
				obj.setMonsterId(result.getString("MONSTER_ID"));
				obj.setPlayerName(result.getString("PLAYER_NAME"));
				obj.setMonsterName(result.getString("MONSTER_NAME"));
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

	public void deletePlayerKills(PlayerKills o) {
		String deleteString = "delete from PLAYER_KILLS  ";
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

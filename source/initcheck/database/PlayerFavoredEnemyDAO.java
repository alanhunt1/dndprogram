package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PlayerFavoredEnemyDAO extends InitBaseDAO {

	DBSession dbs2 = null;

	public PlayerFavoredEnemyDAO() {

	}

	public PlayerFavoredEnemyDAO(DBSession dbs2) {
		this.dbs2 = dbs2;
	}

	public void addPlayerFavoredEnemy(PlayerFavoredEnemy o) {
		String valueString = "";
		String insertString = "INSERT INTO PLAYER_FAVORED_ENEMY (";
		if (o.getId() != null) {
			insertString += "ID,";
			valueString += "" + dbs.escapeQuotes(o.getId()) + ",";
		}
		if (o.getPlayerId() != null) {
			insertString += "PLAYER_ID,";
			if (o.getPlayerId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getPlayerId()) + ",";

			}
		}
		if (o.getClassId() != null) {
			insertString += "CLASS_ID,";
			if (o.getClassId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getClassId()) + ",";

			}
		}
		if (o.getFavoredEnemy() != null) {
			insertString += "FAVORED_ENEMY,";
			valueString += "'" + dbs.escapeQuotes(o.getFavoredEnemy()) + "',";
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
			if (dbs2 == null) {
				dbs.open();
				dbs.executeSQLCommand(insertString);
			} else {
				dbs2.executeSQLCommand(insertString);
			}

			logger.log("Executing Insert" + insertString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			if (dbs2 == null) {
				dbs.close();
			}
			resetConnection();
		}
	}

	public void updatePlayerFavoredEnemy(PlayerFavoredEnemy o) {
		String updateString = "update PLAYER_FAVORED_ENEMY set ";
		if (o.getPlayerId() != null) {
			if (!o.getPlayerId().equals("")) {
				updateString += "PLAYER_ID="
						+ dbs.escapeQuotes(o.getPlayerId()) + ",";
			} else {
				updateString += "PLAYER_ID=null,";
			}
		}
		if (o.getClassId() != null) {
			if (!o.getClassId().equals("")) {
				updateString += "CLASS_ID=" + dbs.escapeQuotes(o.getClassId())
						+ ",";
			} else {
				updateString += "CLASS_ID=null,";
			}
		}
		if (o.getFavoredEnemy() != null) {
			updateString += "FAVORED_ENEMY='"
					+ dbs.escapeQuotes(o.getFavoredEnemy()) + "'";
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + "  ";
		try {
			if (dbs2 == null) {
				dbs.open();
				dbs.executeSQLCommand(updateString);
			} else {
				dbs2.executeSQLCommand(updateString);
			}

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			if (dbs2 == null) {
				dbs.close();
			}
			resetConnection();
		}
	}

	public Vector<PlayerFavoredEnemy> getFavoredEnemies(String playerId) {
		PlayerFavoredEnemy o = new PlayerFavoredEnemy();
		o.setPlayerId(playerId);
		return selectPlayerFavoredEnemy(o);
	}

	public Vector<PlayerFavoredEnemy> selectPlayerFavoredEnemy(PlayerFavoredEnemy o) {
		String selectString = "SELECT * FROM PLAYER_FAVORED_ENEMY  ";
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
		if (o.getClassId() != null && !o.getClassId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " CLASS_ID = " + dbs.escapeQuotes(o.getClassId())
					+ " ";
		}
		if (o.getFavoredEnemy() != null && !o.getFavoredEnemy().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " FAVORED_ENEMY = '"
					+ dbs.escapeQuotes(o.getFavoredEnemy()) + "' ";
		}
		Vector<PlayerFavoredEnemy> v = new Vector<PlayerFavoredEnemy>();
		try {
			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}

			while (result.next()) {
				PlayerFavoredEnemy obj = new PlayerFavoredEnemy();

				obj.setId(result.getString("ID"));
				obj.setPlayerId(result.getString("PLAYER_ID"));
				obj.setClassId(result.getString("CLASS_ID"));
				obj.setFavoredEnemy(result.getString("FAVORED_ENEMY"));
				v.add(obj);
			}
		} catch (SQLException sqle) {
			logger.log("error", sqle.toString());
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			if (dbs2 == null) {
				dbs.close();
			}
		}
		return v;
	}

	public void deletePlayerFavoredEnemy(PlayerFavoredEnemy o) {
		String deleteString = "delete from PLAYER_FAVORED_ENEMY  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + " ";

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
		}
	}
}

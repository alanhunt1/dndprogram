package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PlayerHpDAO extends InitBaseDAO {

	public PlayerHpDAO() {

	}

	public PlayerHpDAO(DBSession dbs2) {
		this.dbs2 = dbs2;
	}

	public void addPlayerHp(PlayerHp o) {
		String valueString = "";
		String insertString = "INSERT INTO PLAYER_HP (";

		if (o.getPlayerId() != null) {
			insertString += "PLAYER_ID,";
			valueString += dbs.escapeQuotes(o.getPlayerId()) + ",";
		}
		if (o.getLevel() != null) {
			insertString += "CLEVEL,";
			valueString += dbs.escapeQuotes(o.getLevel()) + ",";
		}
		if (o.getHitPoints() != null) {
			insertString += "HIT_POINTS,";
			valueString += dbs.escapeQuotes(o.getHitPoints()) + ",";
		}
		if (o.getClassId() != null) {
			insertString += "CLASS_ID,";
			valueString += dbs.escapeQuotes(o.getClassId()) + ",";
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

			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
		}
	}

	public void updatePlayerHp(PlayerHp o) {
		String updateString = "update PLAYER_HP set ";

		if (o.getPlayerId() != null) {
			updateString += "PLAYER_ID=" + dbs.escapeQuotes(o.getPlayerId())
					+ ",";
		}
		if (o.getLevel() != null) {
			updateString += "CLEVEL=" + dbs.escapeQuotes(o.getLevel()) + ",";
		}
		if (o.getHitPoints() != null) {
			updateString += "HIT_POINTS=" + dbs.escapeQuotes(o.getHitPoints())
					+ ",";
		}
		if (o.getClassId() != null) {
			updateString += "CLASS_ID=" + dbs.escapeQuotes(o.getClassId());
		}

		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE ID = " + dbs.escapeQuotes(o.getId());

		try {
			
			dbs.open();
			dbs.executeSQLCommand(updateString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
		}
	}

	public Vector<PlayerHp> getPlayerHp(String id) {
		PlayerHp php = new PlayerHp();
		php.setPlayerId(id);
		return selectPlayerHp(php);
	}

	public Vector<PlayerHp> selectPlayerHp(PlayerHp o) {
		String selectString = "SELECT php.*, c.CharClass FROM PLAYER_HP php, CLASS c  ";
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
		if (o.getLevel() != null && !o.getLevel().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " CLEVEL = " + dbs.escapeQuotes(o.getLevel()) + " ";
		}
		if (o.getHitPoints() != null && !o.getHitPoints().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " HIT_POINTS = "
					+ dbs.escapeQuotes(o.getHitPoints()) + " ";
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
		selectString += " AND c.ID = php.CLASS_ID ";

		Vector<PlayerHp> v = new Vector<PlayerHp>();
		try {

			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}

			while (result.next()) {
				PlayerHp obj = new PlayerHp();

				obj.setId(result.getString("ID"));
				obj.setPlayerId(result.getString("PLAYER_ID"));
				obj.setLevel(result.getString("CLEVEL"));
				obj.setHitPoints(result.getString("HIT_POINTS"));
				obj.setClassId(result.getString("CLASS_ID"));
				obj.setClassName(result.getString("CHARCLASS"));
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

	public void clearPlayerClassHp(String playerId, String classId) {
		String deleteString = "delete from PLAYER_HP  ";
		deleteString += " WHERE  PLAYER_ID=" + playerId + " AND CLASS_ID = "
				+ classId;

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

	public void deletePlayerHp(PlayerHp o) {
		String deleteString = "delete from PLAYER_HP  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getId());

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

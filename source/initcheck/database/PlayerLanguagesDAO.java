package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PlayerLanguagesDAO extends InitBaseDAO {

	DBSession dbs2 = null;

	public PlayerLanguagesDAO() {

	}

	public PlayerLanguagesDAO(DBSession dbs2) {
		this.dbs2 = dbs2;
	}

	public void addPlayerLanguages(PlayerLanguages o) {
		String valueString = "";
		String insertString = "INSERT INTO PLAYER_LANGUAGES (";

		if (o.getPlayerId() != null) {
			insertString += "PLAYER_ID,";
			valueString += dbs.escapeQuotes(o.getPlayerId()) + ",";
		}
		if (o.getLanguage() != null) {
			insertString += "LANGUAGE,";
			valueString += "'" + dbs.escapeQuotes(o.getLanguage()) + "',";
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

	public Vector<PlayerLanguages> getPlayerLanguages(String id) {
		PlayerLanguages pl = new PlayerLanguages();
		pl.setPlayerId(id);
		return selectPlayerLanguages(pl);
	}

	public Vector<PlayerLanguages> selectPlayerLanguages(PlayerLanguages o) {
		String selectString = "SELECT * FROM PLAYER_LANGUAGES  ";
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
		if (o.getLanguage() != null && !o.getLanguage().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " LANGUAGE = '" + dbs.escapeQuotes(o.getLanguage())
					+ "' ";
		}
		Vector<PlayerLanguages> v = new Vector<PlayerLanguages>();
		try {
			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}
			// add common automatically
			PlayerLanguages common = new PlayerLanguages();
			common.setLanguage("Common");
			common.setPlayerId(o.getPlayerId());
			v.add(common);
			while (result.next()) {
				PlayerLanguages obj = new PlayerLanguages();

				obj.setId(result.getString("ID"));
				obj.setPlayerId(result.getString("PLAYER_ID"));
				obj.setLanguage(result.getString("LANGUAGE"));
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

	public void deletePlayerLanguages(PlayerLanguages o) {
		String deleteString = "delete from PLAYER_LANGUAGES  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + "";

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

package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PlayerDomainDAO extends InitBaseDAO {

	public PlayerDomainDAO() {

	}

	public PlayerDomainDAO(DBSession dbs2) {
		this.dbs2 = dbs2;
	}

	public void addPlayerDomain(PlayerDomain o) {
		String valueString = "";
		String insertString = "INSERT INTO PLAYER_DOMAINS (";

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
		if (o.getDomainId() != null) {
			insertString += "DOMAIN_ID,";
			if (o.getDomainId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getDomainId()) + ",";

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
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
		}
	}

	public void updatePlayerDomain(PlayerDomain o) {
		String updateString = "update PLAYER_DOMAINS set ";
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
		if (o.getDomainId() != null) {
			updateString += "DOMAIN_ID=" + dbs.escapeQuotes(o.getDomainId())
					+ "";
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + "";

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

	public Vector<PlayerDomain> getPlayerDomains(String id) {
		PlayerDomain o = new PlayerDomain();
		o.setPlayerId(id);
		return selectPlayerDomain(o);
	}

	public Vector<PlayerDomain> selectPlayerDomain(PlayerDomain o) {
		String selectString = "SELECT pd.*, d.DOMAIN_NAME, d.description FROM PLAYER_DOMAINS pd, DOMAINS d   WHERE pd.DOMAIN_ID = d.ID";
		boolean first = false;
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
		if (o.getDomainId() != null && !o.getDomainId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " DOMAIN_ID = " + dbs.escapeQuotes(o.getDomainId())
					+ "";
		}
		Vector<PlayerDomain> v = new Vector<PlayerDomain>();
		try {

			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}

			while (result.next()) {
				PlayerDomain obj = new PlayerDomain();

				obj.setId(result.getString("ID"));
				obj.setPlayerId(result.getString("PLAYER_ID"));
				obj.setClassId(result.getString("CLASS_ID"));
				obj.setDomainId(result.getString("DOMAIN_ID"));
				obj.setDomainName(result.getString("DOMAIN_NAME"));
				obj.setShortDesc(result.getString("DESCRIPTION"));
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

	public void deletePlayerDomain(PlayerDomain o) {
		String deleteString = "delete from PLAYER_DOMAINS  ";
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

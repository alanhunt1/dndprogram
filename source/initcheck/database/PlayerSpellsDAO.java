package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PlayerSpellsDAO extends InitBaseDAO {

	public PlayerSpellsDAO() {

	}

	public void addPlayerSpells(PlayerSpells o) {
		String valueString = "";
		String insertString = "INSERT INTO PLAYER_SPELLS (";
		
		if (o.getPlayerId() != null) {
			insertString += "PLAYER_ID,";
			if (o.getPlayerId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getPlayerId()) + ",";

			}
		}
		if (o.getSpellId() != null) {
			insertString += "SPELL_ID,";
			if (o.getSpellId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getSpellId()) + ",";

			}
		}
		if (o.getSpellClass() != null) {
			insertString += "SPELL_CLASS,";
			valueString += "'" + dbs.escapeQuotes(o.getSpellClass()) + "',";
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

	public void addPlayerSpells(Spell o, String playerId, String spellClass) {
		String valueString = "";
		String insertString = "INSERT INTO PLAYER_SPELLS (";

		if (playerId != null) {
			insertString += "PLAYER_ID,";
			if (playerId.equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(playerId) + ",";

			}
		}
		if (o.getId() != null) {
			insertString += "SPELL_ID,";
			if (o.getId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getId()) + ",";

			}
		}
		if (spellClass != null) {
			insertString += "SPELL_CLASS,";
			valueString += "'" + dbs.escapeQuotes(spellClass) + "',";
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

	public void updatePlayerSpells(PlayerSpells o) {
		String updateString = "update PLAYER_SPELLS set ";
		if (o.getPlayerId() != null) {
			if (!o.getPlayerId().equals("")) {
				updateString += "PLAYER_ID="
						+ dbs.escapeQuotes(o.getPlayerId()) + ",";
			} else {
				updateString += "PLAYER_ID=null,";
			}
		}
		if (o.getSpellId() != null) {
			if (!o.getSpellId().equals("")) {
				updateString += "SPELL_ID=" + dbs.escapeQuotes(o.getSpellId())
						+ "";
			} else {
				updateString += "SPELL_ID=null";
			}
		}
		if (o.getSpellClass() != null) {
			updateString += "SPELL_CLASS='"+o.getSpellClass()+"',";
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + " ";
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

	public boolean exists(PlayerSpells o) {
		return selectPlayerSpells(o).size() > 0;
	}

	public Vector<PlayerSpells> getPlayerSpells(String playerId) {
		PlayerSpells ps = new PlayerSpells();
		ps.setPlayerId(playerId);
		return selectPlayerSpells(ps);
	}

	public Vector<PlayerSpells> selectPlayerSpells(PlayerSpells o) {
		String selectString = "SELECT s.*, ps.id as PSID, ps.PLAYER_ID, ps.SPELL_ID, ps.SPELL_CLASS, sl.SPELL_LEVEL FROM SPELLS s, PLAYER_SPELLS ps, SPELL_CLASSES sl WHERE s.id = ps.SPELL_ID and s.id = sl.SPELL_ID and ps.SPELL_CLASS = sl.SPELL_CLASS ";
		boolean first = false;
		if (o.getId() != null && !o.getId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ps.ID = " + dbs.escapeQuotes(o.getId()) + " ";
		}
		if (o.getPlayerId() != null && !o.getPlayerId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ps.PLAYER_ID = "
					+ dbs.escapeQuotes(o.getPlayerId()) + " ";
		}
		if (o.getSpellId() != null && !o.getSpellId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ps.SPELL_ID = "
					+ dbs.escapeQuotes(o.getSpellId()) + " ";
		}
		selectString += " ORDER BY sl.SPELL_LEVEL, s.SPELL_NAME ";
		Vector<PlayerSpells> v = new Vector<PlayerSpells>();
		try {
			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}

			while (result.next()) {
				PlayerSpells obj = new PlayerSpells();

				obj.setPsid(result.getString("PSID"));
				obj.setSpellName(result.getString("SPELL_NAME"));
				obj.setSchool(result.getString("SCHOOL"));
				obj.setType(result.getString("TYPE"));
				obj.setSubType(result.getString("SUB_TYPE"));
				obj.setLevel(result.getString("CLEVEL"));
				obj.setComponents(result.getString("COMPONENTS"));
				obj.setTime(result.getString("CTIME"));
				obj.setRange(result.getString("CRANGE"));
				obj.setEffect(result.getString("EFFECT"));
				obj.setDuration(result.getString("DURATION"));
				obj.setSavingThrow(result.getString("SAVING_THROW"));
				obj.setResist(result.getString("RESIST"));
				obj.setDescription(result.getString("DESCRIPTION"));
				obj.setEffectType(result.getString("EFFECT_TYPE"));
				obj.setShortDesc(result.getString("SHORT_DESC"));
				obj.setId(result.getString("ID"));
				obj.setPlayerId(result.getString("PLAYER_ID"));
				obj.setSpellId(result.getString("SPELL_ID"));
				obj.setSpellClass(result.getString("SPELL_CLASS"));
				obj.setSpellLevel(result.getString("SPELL_LEVEL"));
				v.add(obj);
			}
		} catch (SQLException sqle) {
			logger.log("error", sqle.toString());
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			if (dbs2 == null) {
				dbs.close();
			} else {
				dbs2.cleanup();
			}
		}
		return v;
	}

	public void clearPlayerSpells(String playerId, String cclass, String level) {
		String deleteString = "delete from PLAYER_SPELLS  ";
		deleteString += " WHERE PLAYER_ID = " + playerId + "  AND SPELL_CLASS = '"+cclass+"'"+
		"AND "+level+" = (SELECT MAX(SPELL_LEVEL) FROM SPELL_CLASSES sc WHERE sc.SPELL_ID = SPELL_ID and sc.SPELL_CLASS = '"+cclass+"' )";

		try {
			dbs.open();
			logger.log("Running delete "+deleteString);
			dbs.executeSQLCommand(deleteString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
		}
	}
	
	public void clearPlayerSpells(String playerId) {
		String deleteString = "delete from PLAYER_SPELLS  ";
		deleteString += " WHERE PLAYER_ID = " + playerId;

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

	public void deletePlayerSpells(PlayerSpells o) {
		String deleteString = "delete from PLAYER_SPELLS  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getPsid()) + " ";

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

package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PlayerFeatsDAO extends InitBaseDAO {

	DBSession dbs2 = null;

	public PlayerFeatsDAO() {

	}

	public PlayerFeatsDAO(DBSession dbs2) {
		this.dbs2 = dbs2;
	}

	public void addPlayerFeats(Feat f, String id) {
		String valueString = "";
		String insertString = "INSERT INTO PLAYER_FEATS (";

		insertString += "FEAT_ID,";
		valueString += f.getId() + ",";

		insertString += "PLAYER_ID,";
		valueString += id + ",";

		if (f.getValue() != null) {
			insertString += "FEAT_MOD,";
			valueString += "'" + f.getValue() + "',";
		}
		if (f.getOverride() != null) {
			insertString += "OVERRIDE,";
			valueString += "'" + f.getOverride() + "',";
		}
		if (f.getSpecial() != null) {
			insertString += "SPECIAL,";
			valueString += "'" + f.getSpecial() + "',";
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

	public boolean isUsed(String featId){
		String selectString = "SELECT * FROM PLAYER_FEATS WHERE FEAT_ID = "+featId;
		try {

			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}

			if (result.next()) {
				return true;
			}
		} catch (SQLException sqle) {
			logger.log("error", sqle.toString());
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			if (dbs2 == null) {
				dbs.close();
			} else {
				dbs.cleanup();
			}
		}
		return false;
	}
	
	public Vector<PlayerFeats> getPlayerFeats(String playerId) {
		String selectString = "SELECT f.*, pf.OVERRIDE, pf.FEAT_MOD, pf.ID as PFID, "
				+ " pf.SPECIAL FROM FEATS f, PLAYER_FEATS pf WHERE pf.PLAYER_ID="
				+ playerId + " AND pf.FEAT_ID = f.ID";
		Vector<PlayerFeats> v = new Vector<PlayerFeats>();
		try {

			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}

			while (result.next()) {
				PlayerFeats obj = new PlayerFeats();

				obj.setId(result.getString("ID"));
				obj.setFeatName(result.getString("FEAT_NAME"));
				obj.setDescription(result.getString("DESCRIPTION"));
				obj.setType(result.getString("TYPE"));
				obj.setType2(result.getString("TYPE2"));
				obj.setType3(result.getString("TYPE3"));
				obj.setFeatType(result.getString("FEAT_TYPE"));
				obj.setAttackBonus(result.getString("ATTACK_BONUS"));
				obj.setDamageBonus(result.getString("DAMAGE_BONUS"));
				obj.setRangeLimit(result.getString("RANGE_LIMIT"));
				obj.setShortText(result.getString("SHORT_TEXT"));
				obj.setSaveFeat(result.getBoolean("SAVE_FEAT"));
				obj.setStatFeat(result.getBoolean("STAT_FEAT"));
				obj.setHpFeat(result.getBoolean("HP_FEAT"));
				obj.setAcFeat(result.getBoolean("AC_FEAT"));
				obj.setInitFeat(result.getBoolean("INIT_FEAT"));
				obj.setSkillFeat(result.getBoolean("SKILL_FEAT"));
				obj.setTempBonus(result.getBoolean("TEMP_BONUS"));
				obj.setTempType(result.getString("TEMP_TYPE"));

				obj.setInstanceId(result.getString("PFID"));
				obj.setValue(result.getString("FEAT_MOD"));
				obj.setOverride(result.getString("OVERRIDE"));
				obj.setSpecial(result.getString("SPECIAL"));
				obj.setSource(result.getString("SOURCE"));

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
				dbs.cleanup();
			}
		}

		return v;

	}

	public void updatePlayerFeats(Feat o, String playerId) {
		String updateString = "update PLAYER_FEATS set ";

		if (o.getValue() != null) {
			updateString += "FEAT_MOD='" + dbs.escapeQuotes(o.getValue())
					+ "',";
		}
		if (o.getOverride() != null) {
			updateString += "OVERRIDE='" + dbs.escapeQuotes(o.getOverride())
					+ "',";
		}
		if (o.getSpecial() != null) {
			updateString += "SPECIAL='" + dbs.escapeQuotes(o.getSpecial())
					+ "'";
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE PLAYER_ID = " + dbs.escapeQuotes(playerId)
				+ " AND FEAT_ID = " + o.getId();

		try {
			dbs.open();
			logger.log("Executing update " + updateString);
			dbs.executeSQLCommand(updateString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		}
	}

	public void updatePlayerFeats(PlayerFeats o) {
		String updateString = "update PLAYER_FEATS set ";
		if (o.getId() != null) {
			updateString += "FEAT_ID=" + dbs.escapeQuotes(o.getId()) + ",";
		}
		if (o.getValue() != null) {
			updateString += "FEAT_MOD='" + dbs.escapeQuotes(o.getValue())
					+ "',";
		}
		if (o.getOverride() != null) {
			updateString += "OVERRIDE='" + dbs.escapeQuotes(o.getOverride())
					+ "',";
		}
		if (o.getSpecial() != null) {
			updateString += "SPECIAL='" + dbs.escapeQuotes(o.getSpecial())
					+ "'";
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE ID = " + dbs.escapeQuotes(o.getInstanceId())
				+ "";

		try {
			dbs.open();
			logger.log("Executing update " + updateString);
			dbs.executeSQLCommand(updateString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		}
	}

	public void deletePlayerFeats(Feat o) {
		String deleteString = "delete from PLAYER_FEATS  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getInstanceId());

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

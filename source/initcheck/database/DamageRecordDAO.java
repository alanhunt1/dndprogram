package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class DamageRecordDAO extends InitBaseDAO {

	public DamageRecordDAO() {

	}

	public void addOrUpdateDamageRecord(DamageRecord o) {
		if (o.getId() != null) {
			updateDamageRecord(o);
		} else {
			addDamageRecord(o);
		}
	}

	public void updateId(String oldId, String id) {
		String sqlString = "UPDATE DAMAGE_RECORD SET PLAYER_ID = " + id
				+ " WHERE " + "PLAYER_ID = " + oldId;

		try {
			dbs.open();
			dbs.executeSQLCommand(sqlString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
			resetConnection();
		}
	}

	public void addDamageRecord(DamageRecord o) {
		String valueString = "";
		String insertString = "INSERT INTO DAMAGE_RECORD (";
		if (o.getId() != null) {
			insertString += "ID,";
			valueString += "'" + dbs.escapeQuotes(o.getId()) + "',";
		}
		if (o.getPartyName() != null) {
			insertString += "PARTY_NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getPartyName()) + "',";
		}
		if (o.getPlayerId() != null) {
			insertString += "PLAYER_ID,";
			if (o.getPlayerId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getPlayerId()) + ",";

			}
		}
		if (o.getDamage() != null) {
			insertString += "DAMAGE,";
			if (o.getDamage().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getDamage()) + ",";

			}
		}

		insertString += "ONESHOT,";
		if (o.isOneshot()) {
			valueString += "true,";
		} else {
			valueString += "false,";
		}

		if (o.getWeaponType() != null) {
			insertString += "WEAPON_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getWeaponType()) + "',";
		}
		if (o.getBattleId() != null) {
			insertString += "BATTLE_ID,";
			if (o.getBattleId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getBattleId()) + ",";

			}
		}
		if (o.getWeaponUse() != null) {
			insertString += "WEAPON_USE,";
			if (o.getWeaponUse().equals("")) {
				valueString += "null,";
			} else {
				valueString += "'" + dbs.escapeQuotes(o.getWeaponUse()) + "',";

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
			resetConnection();
		}
	}

	public void updateDamageRecord(DamageRecord o) {
		String updateString = "update DAMAGE_RECORD set ";
		if (o.getPartyName() != null) {
			updateString += "PARTY_NAME='" + dbs.escapeQuotes(o.getPartyName())
					+ "',";
		}
		if (o.getPlayerId() != null) {
			if (!o.getPlayerId().equals("")) {
				updateString += "PLAYER_ID="
						+ dbs.escapeQuotes(o.getPlayerId()) + ",";
			} else {
				updateString += "PLAYER_ID=null,";
			}
		}
		if (o.getDamage() != null) {
			if (!o.getDamage().equals("")) {
				updateString += "DAMAGE=" + dbs.escapeQuotes(o.getDamage())
						+ ",";
			} else {
				updateString += "DAMAGE=null,";
			}
		}

		updateString += "ONESHOT=" + o.isOneshot() + ",";

		if (o.getWeaponType() != null) {
			updateString += "WEAPON_TYPE='"
					+ dbs.escapeQuotes(o.getWeaponType()) + "',";
		}
		if (o.getBattleId() != null) {
			if (!o.getBattleId().equals("")) {
				updateString += "BATTLE_ID="
						+ dbs.escapeQuotes(o.getBattleId()) + ",";
			} else {
				updateString += "BATTLE_ID=null,";
			}
		}
		if (o.getWeaponUse() != null) {
			updateString += "WEAPON_USE='" + dbs.escapeQuotes(o.getWeaponUse())
					+ "',";
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
			resetConnection();
		}
	}

	public Vector<DamageRecord> getPartyRecords(String party) {
		DamageRecord dr = new DamageRecord();
		dr.setPartyName(party);
		dr.setOneshot(true);
		return selectDamageRecord(dr);
	}

	public Vector<DamageRecord> getPartyRoundRecords(String party) {

		DamageRecord dr = new DamageRecord();
		dr.setPartyName(party);
		dr.setOneshot(false);
		return selectDamageRecord(dr);
	}

	public Vector<DamageRecord> getAlltimeRecords() {
		DamageRecord dr = new DamageRecord();
		dr.setOneshot(true);
		dr.setPartyName("Alltime");
		return selectDamageRecord(dr);
	}

	public Vector<DamageRecord> getAlltimeRoundRecords() {
		DamageRecord dr = new DamageRecord();
		dr.setOneshot(false);
		dr.setPartyName("Alltime");
		return selectDamageRecord(dr);

	}

	public Vector<DamageRecord> selectDamageRecord(DamageRecord o) {
		String selectString = "SELECT dr.*, p.NAME FROM DAMAGE_RECORD dr, PLAYER p WHERE dr.PLAYER_ID = p.ID ";
		boolean first = false;
		if (o.getId() != null && !o.getId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ID = '" + dbs.escapeQuotes(o.getId()) + "' ";
		}
		if (o.getPartyName() != null && !o.getPartyName().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " PARTY_NAME = '"
					+ dbs.escapeQuotes(o.getPartyName()) + "' ";
		}
		if (o.getPlayerId() != null && !o.getPlayerId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " PLAYER_ID = '"
					+ dbs.escapeQuotes(o.getPlayerId()) + "' ";
		}
		if (o.getDamage() != null && !o.getDamage().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " DAMAGE = '" + dbs.escapeQuotes(o.getDamage())
					+ "' ";
		}

		if (!first) {
			selectString += " AND ";
		} else {
			selectString += " WHERE ";
			first = false;
		}
		selectString += " ONESHOT = " + o.isOneshot() + " ";

		if (o.getWeaponType() != null && !o.getWeaponType().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " WEAPON_TYPE = '"
					+ dbs.escapeQuotes(o.getWeaponType()) + "' ";
		}
		if (o.getBattleId() != null && !o.getBattleId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " BATTLE_ID = '"
					+ dbs.escapeQuotes(o.getBattleId()) + "' ";
		}

		Vector<DamageRecord> v = new Vector<DamageRecord>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				DamageRecord obj = new DamageRecord();

				obj.setId(result.getString("ID"));
				obj.setPartyName(result.getString("PARTY_NAME"));
				obj.setPlayerId(result.getString("PLAYER_ID"));
				obj.setDamage(result.getString("DAMAGE"));
				obj.setOneshot(result.getBoolean("ONESHOT"));
				obj.setWeaponType(result.getString("WEAPON_TYPE"));
				obj.setBattleId(result.getString("BATTLE_ID"));
				obj.setWeaponUse(result.getString("WEAPON_USE"));
				obj.setPlayerName(result.getString("NAME"));
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

	public void deleteDamageRecord(DamageRecord o) {
		String deleteString = "delete from DAMAGE_RECORD  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + " ";

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
			resetConnection();
		}
	}
}

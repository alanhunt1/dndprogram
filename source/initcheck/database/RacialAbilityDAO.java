package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

public class RacialAbilityDAO extends InitBaseDAO {

	public static HashMap<String, Vector<RacialAbility>> raceMap = null;

	public RacialAbilityDAO() {

	}

	public void addRacialAbility(RacialAbility o) {
		String valueString = "";
		String insertString = "INSERT INTO RACIAL_ABILITY (";
		
		if (o.getRaceId() != null) {
			insertString += "RACE_ID,";
			valueString += dbs.escapeQuotes(o.getRaceId()) + ",";
		}
		if (o.getAbilityName() != null) {
			insertString += "ABILITY_NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getAbilityName()) + "',";
		}
		if (o.getAbilityType() != null) {
			insertString += "ABILITY_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getAbilityType()) + "',";
		}
		if (o.getAbilityType2() != null) {
			insertString += "ABILITY_TYPE2,";
			valueString += "'" + dbs.escapeQuotes(o.getAbilityType2()) + "',";
		}
		if (o.getAbilityValue() != null) {
			insertString += "ABILITY_VALUE,";
			valueString += "'" + dbs.escapeQuotes(o.getAbilityValue()) + "',";
		}
		if (o.getDescription() != null) {
			insertString += "DESCRIPTION,";
			valueString += "'" + dbs.escapeQuotes(o.getDescription()) + "',";
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
			raceMap = null;
		}
	}

	public void updateRacialAbility(RacialAbility o) {
		String updateString = "update RACIAL_ABILITY set ";
		if (o.getRaceId() != null) {
			updateString += "RACE_ID=" + dbs.escapeQuotes(o.getRaceId()) + ",";
		}
		if (o.getAbilityName() != null) {
			updateString += "ABILITY_NAME='"
					+ dbs.escapeQuotes(o.getAbilityName()) + "',";
		}
		if (o.getAbilityType() != null) {
			updateString += "ABILITY_TYPE='"
					+ dbs.escapeQuotes(o.getAbilityType()) + "',";
		}
		if (o.getAbilityType2() != null) {
			updateString += "ABILITY_TYPE2='"
					+ dbs.escapeQuotes(o.getAbilityType2()) + "',";
		}
		if (o.getAbilityValue() != null) {
			updateString += "ABILITY_VALUE='"
					+ dbs.escapeQuotes(o.getAbilityValue()) + "',";
		}
		if (o.getDescription() != null) {
			updateString += "DESCRIPTION='"
					+ dbs.escapeQuotes(o.getDescription()) + "'";
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE ID = '" + dbs.escapeQuotes(o.getId()) + "'";

		try {
			dbs.open();
			dbs.executeSQLCommand(updateString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
			raceMap = null;
		}
	}

	public Vector<RacialAbility> selectRacialAbility(RacialAbility o) {
		String selectString = "SELECT * FROM RACIAL_ABILITY  ";
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
		if (o.getRaceId() != null && !o.getRaceId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " RACE_ID = '" + dbs.escapeQuotes(o.getRaceId())
					+ "' ";
		}
		if (o.getAbilityName() != null && !o.getAbilityName().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ABILITY_NAME = '"
					+ dbs.escapeQuotes(o.getAbilityName()) + "' ";
		}
		if (o.getAbilityType() != null && !o.getAbilityType().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ABILITY_TYPE = '"
					+ dbs.escapeQuotes(o.getAbilityType()) + "' ";
		}
		if (o.getAbilityType2() != null && !o.getAbilityType2().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ABILITY_TYPE2 = '"
					+ dbs.escapeQuotes(o.getAbilityType2()) + "' ";
		}
		if (o.getAbilityValue() != null && !o.getAbilityValue().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ABILITY_VALUE = '"
					+ dbs.escapeQuotes(o.getAbilityValue()) + "' ";
		}
		if (o.getDescription() != null && !o.getDescription().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " DESCRIPTION = '"
					+ dbs.escapeQuotes(o.getDescription()) + "' ";
		}
		Vector<RacialAbility> v = new Vector<RacialAbility>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				RacialAbility obj = new RacialAbility();

				obj.setId(result.getString("ID"));
				obj.setRaceId(result.getString("RACE_ID"));
				obj.setAbilityName(result.getString("ABILITY_NAME"));
				obj.setAbilityType(result.getString("ABILITY_TYPE"));
				obj.setAbilityType2(result.getString("ABILITY_TYPE2"));
				obj.setAbilityValue(result.getString("ABILITY_VALUE"));
				obj.setDescription(result.getString("DESCRIPTION"));
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

	
	public Vector<RacialAbility> getRacialAbilities(String race) {

		if (raceMap == null) {
			raceMap = new HashMap<String,Vector<RacialAbility>>();
		}

		Vector<RacialAbility> o = raceMap.get(race);

		if (o == null) {
			String selectString = "SELECT ra.* FROM RACIAL_ABILITY ra, RACE r WHERE "
					+ "r.RACE = '" + race + "' AND ra.RACE_ID = r.ID";
			Vector<RacialAbility> v = new Vector<RacialAbility>();
			try {
				ResultSet result = null;
				if (dbs2 == null) {
					dbs.open();
					result = dbs.executeSQLQuery(selectString);
				} else {
					result = dbs2.executeSQLQuery(selectString);
				}

				while (result.next()) {
					RacialAbility obj = new RacialAbility();

					obj.setId(result.getString("ID"));
					obj.setRaceId(result.getString("RACE_ID"));
					obj.setAbilityName(result.getString("ABILITY_NAME"));
					obj.setAbilityType(result.getString("ABILITY_TYPE"));
					obj.setAbilityType2(result.getString("ABILITY_TYPE2"));
					obj.setAbilityValue(result.getString("ABILITY_VALUE"));
					obj.setDescription(result.getString("DESCRIPTION"));
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
			raceMap.put(race, v);
			o = v;
		}
		return  o;
	}

	public void clearRacialAbilities(String raceId) {
		String deleteString = "delete from RACIAL_ABILITY  ";
		deleteString += " WHERE RACE_ID = " + raceId;

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
			raceMap = null;
		}
	}

	public void deleteRacialAbility(RacialAbility o) {
		String deleteString = "delete from RACIAL_ABILITY  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getId());

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
			raceMap = null;
		}
	}
}

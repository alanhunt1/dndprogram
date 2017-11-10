package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PlayerSkillsDAO extends InitBaseDAO {

	public PlayerSkillsDAO() {

	}

	public PlayerSkillsDAO(DBSession dbs2) {
		this.dbs2 = dbs2;
	}

	public int addPlayerSkills(Skill f, String id) {

		int sid = -1;

		String valueString = "";
		String insertString = "INSERT INTO PLAYER_SKILLS (";

		insertString += "SKILL_ID,";
		valueString += f.getId() + ",";

		insertString += "PLAYER_ID,";
		valueString += id + ",";

		if (f.getRanks() != null) {
			insertString += "RANKS,";
			valueString += f.getRanks() + ",";
		}

		if (f.getMisc() != null && !f.getMisc().equals("")) {
			insertString += "MISC,";
			valueString += f.getMisc() + ",";
		}

		if (f.getMods() != null && !f.getMods().equals("")) {
			insertString += "MODS,";
			valueString += f.getMods() + ",";
		}

		if (f.getClassId() != null) {
			insertString += "CLASS_ID,";
			valueString += f.getClassId() + ",";
		}

		if (f.getCrossClassOverride() != null) {
			insertString += "CROSS_CLASS_OVERRIDE,";
			valueString += "'" + f.getCrossClassOverride() + "',";
		}

		insertString += "NOTES,";
		valueString += "'" + dbs.escapeQuotes(f.getNotes()) + "',";

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
			ResultSet results = dbs
					.executeSQLQuery("SELECT MAX(ID) AS MAXID FROM PLAYER_SKILLS");
			if (results.next()) {
				sid = results.getInt("MAXID");
			}
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
			resetConnection();
		}

		return sid;
	}

	public Vector<PlayerSkills> getPlayerSkills(String playerId) {
		return getPlayerSkills(playerId, null);
	}

	public Vector<PlayerSkills> getPlayerSkills(String playerId, String classId) {

		String selectString = "SELECT s.*, ps.id as instanceId, ps.ranks, "
				+ " ps.class_id, ps.mods, ps.misc, ps.notes, ps.cross_class_override FROM SKILLS s, PLAYER_SKILLS ps "
				+ " WHERE ps.PLAYER_ID=" + playerId + " AND s.id = ps.skill_id";

		if (classId != null) {
			selectString += " AND ps.class_id = " + classId;
		}

		selectString += " ORDER BY s.SKILL ";

		Vector<PlayerSkills> v = new Vector<PlayerSkills>();
		try {

			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}

			while (result.next()) {
				PlayerSkills obj = new PlayerSkills();

				obj.setId(result.getString("ID"));
				obj.setSkill(result.getString("Skill"));
				obj.setAbility(result.getString("Ability"));
				obj.setDescription(result.getString("Description"));
				obj.setTrainedOnly(result.getString("TrainedOnly"));
				obj.setClass1(result.getString("Class1"));
				obj.setClass2(result.getString("Class2"));
				obj.setClass3(result.getString("Class3"));
				obj.setClass4(result.getString("Class4"));
				obj.setClass5(result.getString("Class5"));
				obj.setArmorCheck(result.getString("ArmorCheck"));
				obj.setRanks(result.getString("ranks"));
				obj.setNotes(result.getString("notes"));
				obj.setMods(result.getString("mods"));
				obj.setMisc(result.getString("misc"));
				obj.setClassId(result.getString("class_id"));
				obj.setInstanceId(result.getString("instanceId"));
				obj.setCrossClassOverride(result
						.getString("cross_class_override"));
				obj.setSynBonus(result.getString("SYN_BONUS"));
				obj.setSynValue(result.getString("SYN_VALUE"));
				obj.setSynRanks(result.getString("SYN_RANKS"));
				obj.setSynBonus2(result.getString("SYN_BONUS2"));
				obj.setSynValue2(result.getString("SYN_VALUE2"));
				obj.setSynRanks2(result.getString("SYN_RANKS2"));

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

	public void clearPlayerSkills(String playerId) {
		try {
			// open the connection
			dbs.open();
			String command = "DELETE FROM PLAYER_SKILLS WHERE PLAYER_ID="
					+ playerId;
			dbs.executeSQLCommand(command);
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		} finally {
			resetConnection();
		}

		// close the connection
		dbs.close();
	}

	public void clearPlayerClassSkills(String playerId, String classId) {
		try {
			// open the connection
			dbs.open();
			String command = "DELETE FROM PLAYER_SKILLS WHERE PLAYER_ID="
					+ playerId + " AND CLASS_ID = " + classId;
			dbs.executeSQLCommand(command);
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		} finally {
			resetConnection();
		}

		// close the connection
		dbs.close();
	}

	public void updatePlayerSkills(Skill o) {
		String updateString = "update PLAYER_SKILLS set ";
		if (o.getId() != null) {
			updateString += "SKILL_ID=" + dbs.escapeQuotes(o.getId()) + ",";
		}
		if (o.getRanks() != null) {
			updateString += "RANKS=" + dbs.escapeQuotes(o.getRanks()) + ",";
		}
		if (o.getNotes() != null) {
			updateString += "NOTES='" + dbs.escapeQuotes(o.getNotes()) + "',";
		}
		if (o.getMods() != null) {
			updateString += "MODS=" + dbs.escapeQuotes(o.getMods()) + ",";
		}
		if (o.getMisc() != null) {
			updateString += "MISC=" + dbs.escapeQuotes(o.getMisc()) + ",";
		}
		if (o.getClassId() != null) {
			updateString += "CLASS_ID=" + dbs.escapeQuotes(o.getClassId())
					+ ",";
		}
		if (o.getCrossClassOverride() != null) {
			updateString += "CROSS_CLASS_OVERRIDE='"
					+ dbs.escapeQuotes(o.getCrossClassOverride()) + "',";
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE ID = " + dbs.escapeQuotes(o.getInstanceId())
				+ "";

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

	public Vector<PlayerSkills> selectPlayerSkills(Skill o) {
		String selectString = "SELECT * FROM PLAYER_SKILLS  ";
		boolean first = true;
		if (o.getId() != null && !o.getId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SKILL_ID = " + dbs.escapeQuotes(o.getId());
		}
		if (o.getRanks() != null && !o.getRanks().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " RANKS = '" + dbs.escapeQuotes(o.getRanks())
					+ "' ";
		}
		if (o.getClassId() != null && !o.getClassId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " CLASS_ID = '" + dbs.escapeQuotes(o.getClassId())
					+ "' ";
		}
		Vector<PlayerSkills> v = new Vector<PlayerSkills>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				PlayerSkills obj = new PlayerSkills();

				obj.setInstanceId(result.getString("ID"));
				obj.setId(result.getString("SKILL_ID"));
				// obj.setPlayerId(result.getString("PLAYER_ID"));
				obj.setRanks(result.getString("RANKS"));
				obj.setNotes(result.getString("NOTES"));
				obj.setMods(result.getString("MODS"));
				obj.setMisc(result.getString("MISC"));
				obj.setClassId(result.getString("CLASS_ID"));
				obj.setCrossClassOverride(result
						.getString("CROSS_CLASS_OVERRIDE"));
				obj.setSynBonus(result.getString("SYN_BONUS"));
				obj.setSynValue(result.getString("SYN_VALUE"));
				obj.setSynRanks(result.getString("SYN_RANKS"));
				obj.setSynBonus2(result.getString("SYN_BONUS2"));
				obj.setSynValue2(result.getString("SYN_VALUE2"));
				obj.setSynRanks2(result.getString("SYN_RANKS2"));
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

	public void deletePlayerSkills(Skill o) {
		String deleteString = "delete from PLAYER_SKILLS  ";
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

	public void deletePlayerSkills(Skill o, String playerId) {
		String deleteString = "delete from PLAYER_SKILLS  ";
		deleteString += " WHERE SKILL_ID = " + dbs.escapeQuotes(o.getId())
				+ " AND PLAYER_ID = " + playerId;
		
		try {
			dbs.open();
			logger.log("Executing delete " + deleteString);
			dbs.executeSQLCommand(deleteString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
		}
	}
}

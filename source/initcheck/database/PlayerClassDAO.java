package initcheck.database;

import initcheck.character.ClassSlot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PlayerClassDAO extends InitBaseDAO {

	public PlayerClassDAO() {

	}

	public int addOrUpdatePlayerClass(PlayerClass o) {
		int i = -1;
		if (o.getId() != null) {
			updatePlayerClass(o);
		} else {
			addPlayerClass(o);
		}
		try {
			// open the connection
			dbs.open();
			String command = "SELECT MAX(ID) as MAXID FROM PLAYER_CLASS";
			
			ResultSet result = dbs.executeSQLQuery(command);
			if (result.next()) {
				i = result.getInt("MAXID");
			}

		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		// close the connection
		dbs.close();
		return i;
	}

	public void addPlayerClass(PlayerClass o) {
		String valueString = "";
		String insertString = "INSERT INTO PLAYER_CLASS (";
	
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
		if (o.getClassLevel() != null) {
			insertString += "CLASS_LEVEL,";
			if (o.getClassLevel().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getClassLevel()) + ",";

			}
		}
		if (o.getVirtualLevel() != null) {
			insertString += "VIRTUAL_LEVEL,";
			if (o.getVirtualLevel().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getVirtualLevel()) + ",";

			}
		}
		if (o.getSkillRankOverride() != null) {
			insertString += "SKILL_RANK_OVERRIDE,";
			if (o.getSkillRankOverride().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getSkillRankOverride()) + ",";

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

	public void updatePlayerClass(PlayerClass o) {
		String updateString = "update PLAYER_CLASS set ";
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
		if (o.getClassLevel() != null) {
			if (!o.getClassLevel().equals("")) {
				updateString += "CLASS_LEVEL="
						+ dbs.escapeQuotes(o.getClassLevel()) + ",";
			} else {
				updateString += "CLASS_LEVEL=null,";
			}
		}
		if (o.getVirtualLevel() != null) {
			if (!o.getVirtualLevel().equals("")) {
				updateString += "VIRTUAL_LEVEL="
						+ dbs.escapeQuotes(o.getVirtualLevel()) + ",";
			} else {
				updateString += "VIRTUAL_LEVEL=null,";
			}
		}
		if (o.getSkillRankOverride() != null) {
			if (!o.getSkillRankOverride().equals("")) {
				updateString += "SKILL_RANK_OVERRIDE="
						+ dbs.escapeQuotes(o.getSkillRankOverride()) + ",";
			} else {
				updateString += "SKILL_RANK_OVERRIDE=null,";
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

	public Vector<PlayerClass> selectPlayerClass(PlayerClass o) {
		String selectString = "SELECT * FROM PLAYER_CLASS  ";
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
			selectString += " PLAYER_ID = '"
					+ dbs.escapeQuotes(o.getPlayerId()) + "' ";
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
		if (o.getClassLevel() != null && !o.getClassLevel().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " CLASS_LEVEL = '"
					+ dbs.escapeQuotes(o.getClassLevel()) + "' ";
		}
		if (o.getVirtualLevel() != null && !o.getVirtualLevel().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " VIRTUAL_LEVEL = '"
					+ dbs.escapeQuotes(o.getVirtualLevel()) + "' ";
		}
		if (o.getSkillRankOverride() != null
				&& !o.getSkillRankOverride().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SKILL_RANK_OVERRIDE = '"
					+ dbs.escapeQuotes(o.getSkillRankOverride()) + "' ";
		}
		Vector<PlayerClass> v = new Vector<PlayerClass>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				PlayerClass obj = new PlayerClass();

				obj.setId(result.getString("ID"));
				obj.setPlayerId(result.getString("PLAYER_ID"));
				obj.setClassId(result.getString("CLASS_ID"));
				obj.setClassLevel(result.getString("CLASS_LEVEL"));
				obj.setVirtualLevel(result.getString("VIRTUAL_LEVEL"));
				obj.setSkillRankOverride(result
						.getString("SKILL_RANK_OVERRIDE"));
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

	public void deletePlayerClass(PlayerClass o) {
		String deleteString = "delete from PLAYER_CLASS  ";
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
	
	public void clearPlayerClasses(int id) {
		try {
			dbs.open();

			// build the SQL statement
			String delString = "DELETE FROM PLAYER_CLASS WHERE " + "PLAYER_ID="
					+ id;

			// execute the delete
			dbs.executeSQLCommand(delString);

		} catch (Exception e) {
			logger.log(e.toString());
		} finally {
			dbs.close();
			resetConnection();
		}
	}

	public void deletePlayerClass(ClassSlot cs) {
		try {
			dbs.open();

			// build the SQL statement
			String delString = "DELETE FROM PLAYER_CLASS WHERE " + "ID="
					+ cs.getInstanceId();

			// execute the delete
			dbs.executeSQLCommand(delString);

		} catch (Exception e) {
			logger.log(e.toString());
		} finally {
			dbs.close();
			resetConnection();
		}
	}

	public void updatePlayerClass(ClassSlot cs, int id) {
		try {
			dbs.open();

			// build the SQL statement
			String command = "UPDATE PLAYER_CLASS SET PLAYER_ID=" + id + ", "
					+ " CLASS_ID=" + cs.getClassName().getId() + ", "
					+ " CLASS_LEVEL=" + cs.getLevel() + ", "
					+ " VIRTUAL_LEVEL = " + cs.getVirtualLevel() + ", " +
					" SKILL_RANK_OVERRIDE = "+ cs.getSkillRankMod()+" WHERE ID="
					+ cs.getInstanceId();
			
			System.out.println("UPDATED PLAYER CLASS SET VIRTUAL LEVEL = "+cs.getVirtualLevel());
		
			dbs.executeSQLCommand(command);

		} catch (Exception e) {
			logger.log(e.toString());
		} finally {
			dbs.close();
			resetConnection();
		}
	}

	public void addPlayerClass(ClassSlot cs, int id) {
		try {
			dbs.open();

			// build the SQL statement
			String command = "INSERT INTO PLAYER_CLASS (PLAYER_ID, CLASS_ID, CLASS_LEVEL) "
					+ "VALUES ("
					+ id
					+ ","
					+ cs.getClassName().getId()
					+ ","
					+ cs.getLevel() + ")";

			
			// execute the insert
			dbs.executeSQLCommand(command);

		} catch (Exception e) {
			logger.log(e.toString());
		} finally {
			dbs.close();
			resetConnection();
		}
	}

	public Vector<ClassSlot> getPlayerClasses(String id) {
		return getPlayerClasses(id, null);
	}

	public Vector<ClassSlot> getPlayerClasses(String id, DBSession dbses) {
		Vector<ClassSlot> os = new Vector<ClassSlot>();
		try {
			// open the connection
			if (dbses == null) {
				// dbs2.open();
			}

			String queryString = "SELECT c.*, p.id as PCID, p.class_level, "+
				"p.virtual_level, p.SKILL_RANK_OVERRIDE FROM PLAYER_CLASS p, CLASS c "
					+ " WHERE p.PLAYER_ID = "
					+ id
					+ " AND p.CLASS_ID = c.ID ORDER BY p.id";

			ResultSet result = null;
			if (dbses == null) {
				// dbs2.open();
				result = dbs2.executeSQLQuery(queryString);
			} else {
				result = dbses.executeSQLQuery(queryString);
			}

			ClassAbilitiesDAO cadb = new ClassAbilitiesDAO();
			
			while (result.next()) {

				ClassSlot c = new ClassSlot();

				CharClass obj = new CharClass();
				int level = result.getInt("class_level");

				obj.setId(result.getString("ID"));
				obj.setCharClass(result.getString("CharClass"));
				obj.setDescription(result.getString("Description"));
				obj.setHitdie(result.getString("Hitdie"));
				obj.setPrestige(result.getString("Prestige"));
				obj.setSkillPoints(result.getString("SKILL_POINTS"));
				obj.setDivineCaster(result.getBoolean("DIVINE_CASTER"));
				obj.setArcaneCaster(result.getBoolean("ARCANE_CASTER"));
				obj.setUnpreparedCaster(result.getBoolean("UNPREPARED_CASTER"));
				obj.setClassAbilities(cadb.getClassAbilities(obj));
				c.setSkillRankMod(result.getInt("SKILL_RANK_OVERRIDE"));
				c.setClassName(obj);
				c.setLevel(level);
				c.setInstanceId(result.getString("PCID"));
				c.setVirtualLevel(result.getInt("virtual_level"));
				
				os.add(c);
			}
			if (dbses == null) {
				// dbs2.close();
			}
		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		return os;
	}

	
}
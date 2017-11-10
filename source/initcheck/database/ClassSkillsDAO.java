package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

public class ClassSkillsDAO extends InitBaseDAO {

	private static HashMap<String, Boolean> classSkillMap = null;

	public ClassSkillsDAO() {

	}

	public ClassSkillsDAO(DBSession dbs) {
		dbs2 = dbs;
	}

	public void addClassSkills(ClassSkills o) {
		String valueString = "";
		String insertString = "INSERT INTO CLASS_SKILLS (";

		if (o.getClassId() != null) {
			insertString += "CLASS_ID,";
			valueString += "" + dbs.escapeQuotes(o.getClassId()) + ",";
		}
		if (o.getSkillId() != null) {
			insertString += "SKILL_ID,";
			valueString += "" + dbs.escapeQuotes(o.getSkillId()) + ",";
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
			classSkillMap = null;
		}
	}

	public void updateClassSkills(ClassSkills o) {
		String updateString = "update CLASS_SKILLS set ";
		if (o.getClassId() != null) {
			updateString += "CLASS_ID=" + dbs.escapeQuotes(o.getClassId())
					+ ",";
		}
		if (o.getSkillId() != null) {
			updateString += "SKILL_ID=" + dbs.escapeQuotes(o.getSkillId()) + "";
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
			classSkillMap = null;
		}
	}

	public Vector<ClassSkills> getClassSkills(String classId) {
		ClassSkills c = new ClassSkills();
		c.setClassId(classId);
		return selectClassSkills(c);
	}

	public Vector<ClassSkills> selectClassSkills(ClassSkills o) {
		String selectString = "SELECT * FROM CLASS_SKILLS  ";
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
		if (o.getSkillId() != null && !o.getSkillId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SKILL_ID = " + dbs.escapeQuotes(o.getSkillId())
					+ " ";
		}
		Vector<ClassSkills> v = new Vector<ClassSkills>();
		try {

			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}

			while (result.next()) {
				ClassSkills obj = new ClassSkills();

				obj.setId(result.getString("ID"));
				obj.setClassId(result.getString("CLASS_ID"));
				obj.setSkillId(result.getString("SKILL_ID"));
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

	
	public boolean isClassSkill(String classId, String skillId) {
		if (classSkillMap == null) {
			classSkillMap = new HashMap<String, Boolean>();
		}
		Object o = classSkillMap.get(classId + skillId);

		if (o == null) {
			ClassSkills cs = new ClassSkills();
			cs.setClassId(classId);
			cs.setSkillId(skillId);
			Vector<ClassSkills> v = selectClassSkills(cs);
			classSkillMap.put(classId + skillId, (v.size() > 0));
			o = v.size() > 0;
		}
		return (Boolean) o;
	}

	public void clearClassSkills(String classid) {
		String deleteString = "delete from CLASS_SKILLS  ";
		deleteString += " WHERE CLASS_ID = " + classid;

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
			classSkillMap = null;
		}
	}

	public void deleteClassSkills(ClassSkills o) {
		String deleteString = "delete from CLASS_SKILLS  ";
		deleteString += " WHERE SKILL_ID = " + dbs.escapeQuotes(o.getId()) + "";

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
			classSkillMap = null;
		}
	}
	
	public void deleteClassSkills(Skill o, String classId) {
		String deleteString = "delete from CLASS_SKILLS  ";
		deleteString += " WHERE SKILL_ID = " + dbs.escapeQuotes(o.getId()) + 
		"AND CLASS_ID = "+classId;

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
			classSkillMap = null;
		}
	}
}

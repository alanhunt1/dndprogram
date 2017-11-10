package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ClassAdvancementDAO extends InitBaseDAO {

	public ClassAdvancementDAO() {

	}

	public ClassAdvancementDAO(DBSession dbs2) {
		this.dbs2 = dbs2;
	}

	public void addClassAdvancement(ClassAdvancement o) {
		String valueString = "";
		String insertString = "INSERT INTO CLASS_ADVANCEMENT (";
		
		if (o.getClassId() != null) {
			insertString += "CLASS_ID,";
			valueString += dbs.escapeQuotes(o.getClassId()) + ",";
		}
		if (o.getLevel() != null) {
			insertString += "CLEVEL,";
			valueString += dbs.escapeQuotes(o.getLevel()) + ",";
		}
		if (o.getBaseAttack() != null) {
			insertString += "BASE_ATTACK,";
			valueString += "'" + dbs.escapeQuotes(o.getBaseAttack()) + "',";
		}
		if (o.getFortSave() != null) {
			insertString += "FORT_SAVE,";
			valueString += "'" + dbs.escapeQuotes(o.getFortSave()) + "',";
		}
		if (o.getRefSave() != null) {
			insertString += "REF_SAVE,";
			valueString += "'" + dbs.escapeQuotes(o.getRefSave()) + "',";
		}
		if (o.getWillSave() != null) {
			insertString += "WILL_SAVE,";
			valueString += "'" + dbs.escapeQuotes(o.getWillSave()) + "',";
		}
		if (o.getSpecial() != null) {
			insertString += "SPECIAL,";
			valueString += "'" + dbs.escapeQuotes(o.getSpecial()) + "',";
		}
		if (o.getAlternate() != null) {
			insertString += "ALTERNATE,";
			valueString += "'" + dbs.escapeQuotes(o.getAlternate()) + "',";
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
			
			dbs.executeSQLCommand(insertString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
		}
	}

	public void updateClassAdvancement(ClassAdvancement o) {
		String updateString = "update CLASS_ADVANCEMENT set ";
		if (o.getClassId() != null) {
			updateString += "CLASS_ID=" + dbs.escapeQuotes(o.getClassId())
					+ ",";
		}
		if (o.getLevel() != null) {
			updateString += "CLEVEL=" + dbs.escapeQuotes(o.getLevel()) + ",";
		}
		if (o.getBaseAttack() != null) {
			updateString += "BASE_ATTACK='"
					+ dbs.escapeQuotes(o.getBaseAttack()) + "',";
		}
		if (o.getFortSave() != null) {
			updateString += "FORT_SAVE='" + dbs.escapeQuotes(o.getFortSave())
					+ "',";
		}
		if (o.getRefSave() != null) {
			updateString += "REF_SAVE='" + dbs.escapeQuotes(o.getRefSave())
					+ "',";
		}
		if (o.getWillSave() != null) {
			updateString += "WILL_SAVE='" + dbs.escapeQuotes(o.getWillSave())
					+ "',";
		}
		if (o.getSpecial() != null) {
			updateString += "SPECIAL='" + dbs.escapeQuotes(o.getSpecial())
					+ "',";
		}
		if (o.getAlternate() != null) {
			updateString += "ALTERNATE='" + dbs.escapeQuotes(o.getAlternate())
					+ "'";
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

	public Vector<ClassAdvancement> selectClassAdvancement(ClassAdvancement o) {
		String selectString = "SELECT * FROM CLASS_ADVANCEMENT  ";
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
		if (o.getLevel() != null && !o.getLevel().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " CLEVEL = " + dbs.escapeQuotes(o.getLevel()) + " ";
		}
		if (o.getBaseAttack() != null && !o.getBaseAttack().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " BASE_ATTACK = '"
					+ dbs.escapeQuotes(o.getBaseAttack()) + "' ";
		}
		if (o.getFortSave() != null && !o.getFortSave().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " FORT_SAVE = '"
					+ dbs.escapeQuotes(o.getFortSave()) + "' ";
		}
		if (o.getRefSave() != null && !o.getRefSave().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " REF_SAVE = '" + dbs.escapeQuotes(o.getRefSave())
					+ "' ";
		}
		if (o.getWillSave() != null && !o.getWillSave().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " WILL_SAVE = '"
					+ dbs.escapeQuotes(o.getWillSave()) + "' ";
		}
		if (o.getSpecial() != null && !o.getSpecial().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SPECIAL = '" + dbs.escapeQuotes(o.getSpecial())
					+ "' ";
		}
		if (o.getAlternate() != null && !o.getAlternate().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ALTERNATE = '"
					+ dbs.escapeQuotes(o.getAlternate()) + "' ";
		}
		selectString += " ORDER BY CLASS_ADVANCEMENT.CLEVEL ";

		Vector<ClassAdvancement> v = new Vector<ClassAdvancement>();
		try {
			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}

			while (result.next()) {
				ClassAdvancement obj = new ClassAdvancement();

				obj.setId(result.getString("ID"));
				obj.setClassId(result.getString("CLASS_ID"));
				obj.setLevel(result.getString("CLEVEL"));
				obj.setBaseAttack(result.getString("BASE_ATTACK"));
				obj.setFortSave(result.getString("FORT_SAVE"));
				obj.setRefSave(result.getString("REF_SAVE"));
				obj.setWillSave(result.getString("WILL_SAVE"));
				obj.setSpecial(result.getString("SPECIAL"));
				obj.setAlternate(result.getString("ALTERNATE"));

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

	public Vector<ClassAdvancement> getClassAdvancement(String classId) {
		ClassAdvancement ca = new ClassAdvancement();
		ca.setClassId(classId);
		return selectClassAdvancement(ca);
	}

	public void clearClassAdvancement(String classid) {
		String deleteString = "delete from CLASS_ADVANCEMENT  ";
		deleteString += " WHERE CLASS_ID = " + classid;

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

	public void deleteClassAdvancement(ClassAdvancement o) {
		String deleteString = "delete from CLASS_ADVANCEMENT  ";
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

package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class SpellClassesDAO extends InitBaseDAO {

	public SpellClassesDAO() {

	}

	public void addSpellClasses(SpellClass o) {
		String valueString = "";
		String insertString = "INSERT INTO SPELL_CLASSES (";
		if (o.getId() != null) {
			insertString += "ID,";
			valueString += "'" + dbs.escapeQuotes(o.getId()) + "',";
		}
		if (o.getSpellId() != null) {
			insertString += "SPELL_ID,";
			if (o.getSpellId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getSpellId()) + ",";

			}
		}
		if (o.getSpellLevel() != null) {
			insertString += "SPELL_LEVEL,";
			if (o.getSpellLevel().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getSpellLevel()) + ",";

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

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}

	public void updateSpellClasses(SpellClass o) {
		String updateString = "update SPELL_CLASSES set ";
		if (o.getSpellId() != null) {
			if (!o.getSpellId().equals("")) {
				updateString += "SPELL_ID=" + dbs.escapeQuotes(o.getSpellId())
						+ ",";
			} else {
				updateString += "SPELL_ID=null,";
			}
		}
		if (o.getSpellLevel() != null) {
			if (!o.getSpellLevel().equals("")) {
				updateString += "SPELL_LEVEL="
						+ dbs.escapeQuotes(o.getSpellLevel()) + ",";
			} else {
				updateString += "SPELL_LEVEL=null,";
			}
		}
		if (o.getSpellClass() != null) {
			updateString += "SPELL_CLASS='"
					+ dbs.escapeQuotes(o.getSpellClass()) + "',";
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE ID = '" + dbs.escapeQuotes(o.getId()) + "'  ";
		try {
			dbs.open();
			dbs.executeSQLCommand(updateString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}

	public Vector<SpellClass> getSpellClasses(){
		return selectSpellClasses(new SpellClass());
	}
	
	public Vector<SpellClass> selectSpellClasses(SpellClass o) {
		String selectString = "SELECT * FROM SPELL_CLASSES  ";
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
		if (o.getSpellId() != null && !o.getSpellId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SPELL_ID = '" + dbs.escapeQuotes(o.getSpellId())
					+ "' ";
		}
		if (o.getSpellLevel() != null && !o.getSpellLevel().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SPELL_LEVEL = '"
					+ dbs.escapeQuotes(o.getSpellLevel()) + "' ";
		}
		if (o.getSpellClass() != null && !o.getSpellClass().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SPELL_CLASS = '"
					+ dbs.escapeQuotes(o.getSpellClass()) + "' ";
		}
		Vector<SpellClass> v = new Vector<SpellClass>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				SpellClass obj = new SpellClass();

				obj.setId(result.getString("ID"));
				obj.setSpellId(result.getString("SPELL_ID"));
				obj.setSpellLevel(result.getString("SPELL_LEVEL"));
				obj.setSpellClass(result.getString("SPELL_CLASS"));
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

	public void clearSpellClasses(String spellId) {
		String deleteString = "delete from SPELL_CLASSES  ";
		deleteString += " WHERE SPELL_ID = " + dbs.escapeQuotes(spellId) + " ";

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}
	
	public void deleteSpellClasses(SpellClass o) {
		String deleteString = "delete from SPELL_CLASSES  ";
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
}
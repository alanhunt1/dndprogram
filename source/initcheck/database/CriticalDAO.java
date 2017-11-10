package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class CriticalDAO extends InitBaseDAO {

	public CriticalDAO() {

	}

	public int addOrUpdateCritical(Critical o) {
		int i = -1;
		if (o.getId() != null) {
			updateCritical(o);
		} else {
			addCritical(o);
		}
		try {
			// open the connection
			dbs.open();
			String command = "SELECT MAX(EFFECT_ID) as MAXID FROM CRITICAL_EFFECTS";

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

	public void addCritical(Critical o) {
		String valueString = "";
		String insertString = "INSERT INTO CRITICAL_EFFECTS (";

		if (o.getCategory() != null) {
			insertString += "Category,";
			valueString += "'" + dbs.escapeQuotes(o.getCategory()) + "',";
		}
		if (o.getDescription() != null) {
			insertString += "EFFECT_DESC,";
			valueString += "'" + dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getType() != null) {
			insertString += "EFFECT_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getType()) + "',";
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

	public void updateCritical(Critical o) {
		String updateString = "update CRITICAL_EFFECTS set ";
		if (o.getCategory() != null) {
			updateString += "Category='" + dbs.escapeQuotes(o.getCategory())
					+ "',";
		}
		if (o.getDescription() != null) {
			updateString += "EFFECT_DESC='"
					+ dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getType() != null) {
			updateString += "EFFECT_TYPE='" + dbs.escapeQuotes(o.getType())
					+ "',";
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE EFFECT_ID = " + dbs.escapeQuotes(o.getId());
		try {
			dbs.open();
			dbs.executeSQLCommand(updateString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}

	public Vector<Critical> getCriticals() {
		Critical f = new Critical();
		return selectCritical(f);
	}

	public Vector<Critical> selectCritical(Critical o) {
		String selectString = "SELECT * FROM CRITICAL_EFFECTS  ";
		boolean first = true;
		if (o.getId() != null && !o.getId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " EFFECT_ID = " + dbs.escapeQuotes(o.getId()) + " ";
		}
		if (o.getCategory() != null && !o.getCategory().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " Category = '" + dbs.escapeQuotes(o.getCategory())
					+ "' ";
		}
		if (o.getDescription() != null && !o.getDescription().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " EFFECT_DESC = '"
					+ dbs.escapeQuotes(o.getDescription()) + "' ";
		}
		if (o.getType() != null && !o.getType().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " EFFECT_Type = '" + dbs.escapeQuotes(o.getType())
					+ "' ";
		}
		selectString += " ORDER BY EFFECT_TYPE, CATEGORY";

		Vector<Critical> v = new Vector<Critical>();
		try {
			dbs.open();

			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				Critical obj = new Critical();

				obj.setId(result.getString("EFFECT_ID"));
				obj.setCategory(result.getString("Category"));
				obj.setType(result.getString("EFFECT_TYPE"));
				obj.setDescription(result.getString("EFFECT_DESC"));

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

	public void deleteCritical(Critical o) {
		String deleteString = "delete from CRITICAL_EFFECTS  ";
		deleteString += " WHERE EFFECT_ID = " + dbs.escapeQuotes(o.getId())
				+ " ";

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

package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class FumbleDAO extends InitBaseDAO {

	public FumbleDAO() {

	}

	public int addOrUpdateFumble(Fumble o) {
		int i = -1;
		if (o.getId() != null) {
			updateFumble(o);
		} else {
			addFumble(o);
		}
		try {
			// open the connection
			dbs.open();
			String command = "SELECT MAX(ID) as MAXID FROM FUMBLES";

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

	public void addFumble(Fumble o) {
		String valueString = "";
		String insertString = "INSERT INTO FUMBLES (";

		if (o.getCategory() != null) {
			insertString += "Category,";
			valueString += "'" + dbs.escapeQuotes(o.getCategory()) + "',";
		}
		if (o.getDescription() != null) {
			insertString += "Description,";
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

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}

	public void updateFumble(Fumble o) {
		String updateString = "update FUMBLES set ";
		if (o.getCategory() != null) {
			updateString += "Category='" + dbs.escapeQuotes(o.getCategory())
					+ "',";
		}
		if (o.getDescription() != null) {
			updateString += "Description='"
					+ dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE ID = " + dbs.escapeQuotes(o.getId());
		try {
			dbs.open();
			dbs.executeSQLCommand(updateString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}

	public Vector<Fumble> getFumbles() {
		Fumble f = new Fumble();
		return selectFumble(f);
	}

	public Vector<Fumble> selectFumble(Fumble o) {
		String selectString = "SELECT * FROM FUMBLES  ";
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
			selectString += " Description = '"
					+ dbs.escapeQuotes(o.getDescription()) + "' ";
		}
		selectString += " ORDER BY CATEGORY";

		Vector<Fumble> v = new Vector<Fumble>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				Fumble obj = new Fumble();

				obj.setId(result.getString("ID"));
				obj.setCategory(result.getString("Category"));
				obj.setDescription(result.getString("Description"));
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

	public void deleteFumble(Fumble o) {
		String deleteString = "delete from FUMBLES  ";
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

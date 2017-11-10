package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ItemLocationDAO extends InitBaseDAO {

	public ItemLocationDAO() {

	}

	public void addItemLocation(ItemLocation o) {
		String valueString = "";
		String insertString = "INSERT INTO ITEM_LOCATIONS (";

		if (o.getLocation() != null) {
			insertString += "LOCATION,";
			valueString += "'" + dbs.escapeQuotes(o.getLocation()) + "',";
		}
		if (o.getDroppable() != null) {
			insertString += "DROPPABLE,";
			valueString += "'" + dbs.escapeQuotes(o.getDroppable()) + "',";
		}
		if (o.getWeightless() != null) {
			insertString += "WEIGHTLESS,";
			valueString += "'" + dbs.escapeQuotes(o.getWeightless()) + "',";
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

	public void updateItemLocation(ItemLocation o) {
		String updateString = "update ITEM_LOCATIONS set ";
		if (o.getLocation() != null) {
			updateString += "LOCATION='" + dbs.escapeQuotes(o.getLocation())
					+ "',";
		}
		if (o.getDroppable() != null) {
			updateString += "DROPPABLE='" + dbs.escapeQuotes(o.getDroppable())
					+ "',";
		}
		if (o.getWeightless() != null) {
			updateString += "WEIGHTLESS='"
					+ dbs.escapeQuotes(o.getWeightless()) + "'";
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

	public Vector<ItemLocation> getItemLocations() {
		ItemLocation o = new ItemLocation();
		return selectItemLocation(o);
	}

	public Vector<ItemLocation> selectItemLocation(ItemLocation o) {
		String selectString = "SELECT * FROM ITEM_LOCATIONS  ";
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
		if (o.getLocation() != null && !o.getLocation().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " LOCATION = '" + dbs.escapeQuotes(o.getLocation())
					+ "' ";
		}
		if (o.getDroppable() != null && !o.getDroppable().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " DROPPABLE = '"
					+ dbs.escapeQuotes(o.getDroppable()) + "' ";
		}
		if (o.getWeightless() != null && !o.getWeightless().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " WEIGHTLESS = '"
					+ dbs.escapeQuotes(o.getWeightless()) + "' ";
		}
		Vector<ItemLocation> v = new Vector<ItemLocation>();
		try {
			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}

			while (result.next()) {
				ItemLocation obj = new ItemLocation();

				obj.setId(result.getString("ID"));
				obj.setLocation(result.getString("LOCATION"));
				obj.setDroppable(result.getString("DROPPABLE"));
				obj.setWeightless(result.getString("WEIGHTLESS"));
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

	public void deleteItemLocation(ItemLocation o) {
		String deleteString = "delete from ITEM_LOCATIONS  ";
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

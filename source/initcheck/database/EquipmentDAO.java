package initcheck.database;

import initcheck.character.library.LibraryItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class EquipmentDAO extends InitBaseDAO implements LibraryItemDAO {

	public EquipmentDAO() {

	}

	public int addOrUpdateEquipment(Equipment o) {
		int i = -1;
		if (o.getId() != null) {
			updateEquipment(o);
			i = Integer.parseInt(o.getId());
		} else {
			addEquipment(o);
			try {
				// open the connection
				dbs.open();
				String command = "SELECT MAX(ID) MAXID FROM EQUIPMENT";
				
				ResultSet result = dbs.executeSQLQuery(command);
				if (result.next()) {
					i = result.getInt("MAXID");
				}

			} catch (Exception uhe) {
				logger.log("ERROR : " + uhe.toString());
			}
		}

		// close the connection
		dbs.close();
		return i;
	}

	public void addEquipment(Equipment o) {
		String valueString = "";
		String insertString = "INSERT INTO EQUIPMENT (";

		if (o.getItemName() != null) {
			insertString += "ITEM_NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getItemName()) + "',";
		}
		if (o.getWeight() != null) {
			insertString += "WEIGHT,";
			valueString += "'" + dbs.escapeQuotes(o.getWeight()) + "',";
		}

		if (o.getDescription() != null) {
			insertString += "DESCRIPTION,";
			valueString += "'" + dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getCost() != null) {
			insertString += "COST,";
			valueString += "'" + dbs.escapeQuotes(o.getCost()) + "',";
		}
		if (o.getNotes() != null) {
			insertString += "NOTES,";
			valueString += "'" + dbs.escapeQuotes(o.getNotes()) + "',";
		}
		if (o.getModType() != null) {
			insertString += "MOD_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getModType()) + "',";
		}
		if (o.getType() != null) {
			insertString += "ITEM_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getType()) + "',";
		}
		
		if (o.getSource() != null) {
			insertString += "SOURCE,";
			valueString += "'" + dbs.escapeQuotes(o.getSource()) + "',";
		}

		insertString += "TEMP_MOD,";
		valueString += o.isTempMod() + ",";

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

	public void updateEquipment(Equipment o) {
		String updateString = "update EQUIPMENT set ";

		if (o.getItemName() != null) {
			updateString += "ITEM_NAME='" + dbs.escapeQuotes(o.getItemName())
					+ "',";
		}
		if (o.getWeight() != null) {
			updateString += "WEIGHT='" + dbs.escapeQuotes(o.getWeight()) + "',";
		}

		if (o.getDescription() != null) {
			updateString += "DESCRIPTION='"
					+ dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getCost() != null) {
			updateString += "COST='" + dbs.escapeQuotes(o.getCost()) + "',";
		}
		if (o.getNotes() != null) {
			updateString += "NOTES='" + dbs.escapeQuotes(o.getNotes()) + "',";
		}
		if (o.getModType() != null) {
			updateString += "MOD_TYPE='" + dbs.escapeQuotes(o.getModType())
					+ "',";
		}
		if (o.getType() != null) {
			updateString += "ITEM_TYPE='" + dbs.escapeQuotes(o.getType())
					+ "',";
		}
		if (o.getSource() != null) {
			updateString += "SOURCE='" + dbs.escapeQuotes(o.getSource())
					+ "',";
		}
		updateString += "TEMP_MOD=" + o.isTempMod() + ",";

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

	public Vector<Equipment> getEquipment() {
		Equipment pi = new Equipment();
		return selectEquipment(pi);
	}
	
	public Vector<LibraryItem> getItems(LibraryItem i){
		String queryString = "SELECT * FROM EQUIPMENT WHERE SOURCE = '"+i.getSource()+"' "+
		" ORDER BY ITEM_NAME";
		Vector<LibraryItem> results = new Vector<LibraryItem>();
		results.addAll(getEquipmentFromSql(queryString));
		return results;
	}

	public Vector<Equipment> getEquipment(String s) {
		Equipment pi = new Equipment();
		pi.setType(s);
		return selectEquipment(pi);
	}
	
	public Vector<Equipment> selectEquipment(Equipment o) {
		String selectString = "SELECT * FROM EQUIPMENT  ";
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

		if (o.getItemName() != null && !o.getItemName().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ITEM_NAME = '"
					+ dbs.escapeQuotes(o.getItemName()) + "' ";
		}
		
		if (o.getType() != null && !o.getType().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ITEM_TYPE = '"
					+ dbs.escapeQuotes(o.getType()) + "' ";
		}
		
		if (o.getWeight() != null && !o.getWeight().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " WEIGHT = '" + dbs.escapeQuotes(o.getWeight())
					+ "' ";
		}
		selectString += " ORDER BY ITEM_NAME";
		return getEquipmentFromSql(selectString);
	}
	
	public Vector<Equipment> getEquipmentFromSql(String selectString){
		Vector<Equipment> v = new Vector<Equipment>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				Equipment obj = new Equipment();

				obj.setId(result.getString("ID"));
				obj.setItemName(result.getString("ITEM_NAME"));
				obj.setWeight(result.getString("WEIGHT"));
				obj.setDescription(result.getString("DESCRIPTION"));
				obj.setCost(result.getString("COST"));
				obj.setNotes(result.getString("NOTES"));
				obj.setModType(result.getString("MOD_TYPE"));
				obj.setTempMod(result.getBoolean("TEMP_MOD"));
				obj.setType(result.getString("ITEM_TYPE"));
				obj.setSource(result.getString("SOURCE"));
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

	public boolean exists(Equipment o) {
		return selectEquipment(o).size() > 0;
	}

	public boolean exists(String s) {
		try {
			dbs.open();
			ResultSet result = dbs
					.executeSQLQuery("select 'x' from equipment where item_name='"
							+ s + "'");
			if (result.next()) {
				return true;
			}
		} catch (SQLException sqle) {
			logger.log("error", sqle.toString());
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
		return false;
	}

	public void deleteEquipment(Equipment o) {
		String deleteString = "delete from EQUIPMENT  ";
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

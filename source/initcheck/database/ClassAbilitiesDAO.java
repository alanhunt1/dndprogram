package initcheck.database;

import initcheck.character.library.LibraryItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ClassAbilitiesDAO extends InitBaseDAO implements LibraryItemDAO {

	public ClassAbilitiesDAO() {

	}
	
	public ClassAbilitiesDAO(DBSession dbs2) {
		this.dbs2 = dbs2;
	}
	
	public int addOrUpdateClassAbilities(ClassAbilities o) {
		int i = -1;
		if (o.getId() != null) {
			updateClassAbilities(o);
		} else {
			addClassAbilities(o);
		}
		try {
			// open the connection
			dbs.open();
			String command = "SELECT MAX(ID) as MAXID FROM CLASS_ABILITIES";
			
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

	public void addClassAbilities(ClassAbilities o) {
		String valueString = "";
		String insertString = "INSERT INTO CLASS_ABILITIES (";
		if (o.getId() != null) {
			insertString += "ID,";
			valueString += "'" + dbs.escapeQuotes(o.getId()) + "',";
		}
		if (o.getName() != null) {
			insertString += "NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getName()) + "',";
		}
		if (o.getDescription() != null) {
			insertString += "DESCRIPTION,";
			valueString += "'" + dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getSource() != null) {
			insertString += "SOURCE,";
			valueString += "'" + dbs.escapeQuotes(o.getSource()) + "',";
		}
		if (o.getRulesetId() != null) {
			insertString += "RULESET_ID,";
			valueString += "'" + dbs.escapeQuotes(o.getRulesetId()) + "',";
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
			resetConnection();
		}
	}

	public void updateClassAbilities(ClassAbilities o) {
		String updateString = "update CLASS_ABILITIES set ";
		if (o.getName() != null) {
			updateString += "NAME='" + dbs.escapeQuotes(o.getName()) + "',";
		}
		if (o.getDescription() != null) {
			updateString += "DESCRIPTION='"
					+ dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getSource() != null) {
			updateString += "SOURCE='"
					+ dbs.escapeQuotes(o.getSource()) + "',";
		}
		if (o.getRulesetId() != null) {
			updateString += "RULESET_ID='"
					+ dbs.escapeQuotes(o.getRulesetId()) + "',";
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + " ";
		try {
			dbs.open();
			dbs.executeSQLCommand(updateString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
			resetConnection();
		}
	}

	
	
	public Vector<ClassAbilities> getClassAbilities(CharClass cc) {
		String selectString = "SELECT ca.*, cax.class_level, cl.charclass from "
				+ "CLASS_ABILITIES ca, CLASS_ABILITY_XREF cax, CLASS cl "
				+ "where cl.id = "
				+ cc.getId()
				+ " and "
				+ "cax.class_id = "
				+ cc.getId() + " and " + "ca.id = cax.class_ability_id ";

		Vector<ClassAbilities> v = new Vector<ClassAbilities>();
		try {
			ResultSet result;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}

			
			while (result.next()) {
				ClassAbilities obj = new ClassAbilities();

				obj.setId(result.getString("ID"));
				obj.setName(result.getString("NAME"));
				obj.setDescription(result.getString("DESCRIPTION"));
				obj.setSource(result.getString("Source"));
				obj.setRulesetId(result.getString("RULESET_ID"));
				v.add(obj);
			}
		} catch (SQLException sqle) {
			logger.log("error", sqle.toString());
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			if (dbs2 == null) {
				dbs.close();
			}else{
				dbs2.cleanup();
			}
		}
		return v;

	}

	public Vector<ClassAbilities> getClassAbilities(CharClass cc, int level) {
		String selectString = "SELECT ca.*, cax.class_level, cl.charclass from "
				+ "CLASS_ABILITIES ca, CLASS_ABILITY_XREF cax, CLASS cl "
				+ "where cl.id = "
				+ cc.getId()
				+ " and "
				+ "cax.class_id = "
				+ cc.getId()
				+ " and cax.class_level <= "
				+ level
				+ " and "
				+ "ca.id = cax.class_ability_id ";

		Vector<ClassAbilities> v = new Vector<ClassAbilities>();
		try {
			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}
			while (result.next()) {
				ClassAbilities obj = new ClassAbilities();

				obj.setId(result.getString("ID"));
				obj.setName(result.getString("NAME"));
				obj.setDescription(result.getString("DESCRIPTION"));
				obj.setSource(result.getString("Source"));
				obj.setRulesetId(result.getString("RULESET_ID"));
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

	public Vector<ClassAbilities> getClassAbilities() {
		ClassAbilities ca = new ClassAbilities();
		return selectClassAbilities(ca);
	}

	public Vector<LibraryItem> getItems(LibraryItem i){
		
		Vector<LibraryItem> results = new Vector<LibraryItem>();
		results.addAll(selectClassAbilities(new ClassAbilities()));
		return results;
	}
	
	public Vector<ClassAbilities> getClassAbilities(String keyword) {
		String selectString = "SELECT * FROM CLASS_ABILITIES WHERE NAME LIKE "
				+ " '%" + keyword + "%' OR DESCRIPTION LIKE '%" + keyword
				+ "%'";
		Vector<ClassAbilities> v = new Vector<ClassAbilities>();
		try {
			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}
			
			while (result.next()) {
				ClassAbilities obj = new ClassAbilities();

				obj.setId(result.getString("ID"));
				obj.setName(result.getString("NAME"));
				obj.setDescription(result.getString("DESCRIPTION"));
				obj.setSource(result.getString("Source"));
				obj.setRulesetId(result.getString("RULESET_ID"));
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

	public Vector<ClassAbilities> selectClassAbilities(ClassAbilities o) {
		String selectString = "SELECT * FROM CLASS_ABILITIES  ";
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
		if (o.getName() != null && !o.getName().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " NAME = '" + dbs.escapeQuotes(o.getName()) + "' ";
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
		if (o.getSource() != null && !o.getSource().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SOURCE = '"
					+ dbs.escapeQuotes(o.getSource()) + "' ";
		}
		if (o.getRulesetId() != null && !o.getRulesetId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " RULESET_ID = '"
					+ dbs.escapeQuotes(o.getRulesetId()) + "' ";
		}
		Vector<ClassAbilities> v = new Vector<ClassAbilities>();
		try {
			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}
			
			while (result.next()) {
				ClassAbilities obj = new ClassAbilities();

				obj.setId(result.getString("ID"));
				obj.setName(result.getString("NAME"));
				obj.setDescription(result.getString("DESCRIPTION"));
				obj.setSource(result.getString("Source"));
				obj.setRulesetId(result.getString("RULESET_ID"));
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

	public void deleteClassAbilities(ClassAbilities o) {
		String deleteString = "delete from CLASS_ABILITIES  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + " ";

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
			resetConnection();
		}
	}
}

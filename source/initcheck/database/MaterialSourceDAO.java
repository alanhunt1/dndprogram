package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class MaterialSourceDAO extends InitBaseDAO {

	public MaterialSourceDAO() {

	}

	public int addOrUpdateMaterialSource(MaterialSource o) {
		int i = -1;
		if (o.getSourceId() != null) {
			updateMaterialSource(o);
		} else {
			addMaterialSource(o);
		}
		try {
			// open the connection
			dbs.open();
			String command = "SELECT MAX(SOURCE_ID) as MAXID FROM MATERIAL_SOURCE";
			
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
	
	public void addMaterialSource(MaterialSource o) {
		String valueString = "";
		String insertString = "INSERT INTO MATERIAL_SOURCE (";
		
		if (o.getSourceName() != null) {
			insertString += "SOURCE_NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getSourceName()) + "',";
		}
		if (o.getDndVersion() != null) {
			insertString += "DND_VERSION,";
			valueString += "'" + dbs.escapeQuotes(o.getDndVersion()) + "',";
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

	
	
	public void updateMaterialSource(MaterialSource o) {
		String updateString = "update MATERIAL_SOURCE set ";
		if (o.getSourceName() != null) {
			updateString += "SOURCE_NAME='"
					+ dbs.escapeQuotes(o.getSourceName()) + "',";
		}
		if (o.getDndVersion() != null) {
			updateString += "DND_VERSION='"
					+ dbs.escapeQuotes(o.getDndVersion()) + "',";
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE SOURCE_ID = "
				+ dbs.escapeQuotes(o.getSourceId()) + "  ";
		try {
			dbs.open();
			dbs.executeSQLCommand(updateString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}

	public Vector<MaterialSource> getMaterialSources(){
		MaterialSource ms = new MaterialSource();
		ms.setName(null);
		ms.setDndVersion(null);
		return selectMaterialSource(ms);
	}
	
	public Vector<MaterialSource> selectMaterialSource(MaterialSource o) {
		String selectString = "SELECT * FROM MATERIAL_SOURCE  ";
		boolean first = true;
		if (o.getSourceId() != null && !o.getSourceId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SOURCE_ID = '"
					+ dbs.escapeQuotes(o.getSourceId()) + "' ";
		}
		if (o.getSourceName() != null && !o.getSourceName().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SOURCE_NAME = '"
					+ dbs.escapeQuotes(o.getSourceName()) + "' ";
		}
		if (o.getDndVersion() != null && !o.getDndVersion().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " DND_VERSION = '"
					+ dbs.escapeQuotes(o.getDndVersion()) + "' ";
		}
		Vector<MaterialSource> v = new Vector<MaterialSource>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				MaterialSource obj = new MaterialSource();

				obj.setSourceId(result.getString("SOURCE_ID"));
				obj.setSourceName(result.getString("SOURCE_NAME"));
				obj.setDndVersion(result.getString("DND_VERSION"));
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

	public void deleteMaterialSource(MaterialSource o) {
		String deleteString = "delete from MATERIAL_SOURCE  ";
		deleteString += " WHERE SOURCE_ID = "
				+ dbs.escapeQuotes(o.getSourceId()) + " ";

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
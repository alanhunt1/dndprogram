package initcheck.database;

import initcheck.character.library.LibraryItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class MaterialsDAO extends InitBaseDAO implements LibraryItemDAO {

	public MaterialsDAO() {

	}

	public int addOrUpdateMaterials(Materials o) {
		int i = -1;
		if (o.getId() != null) {
			updateMaterials(o);
		} else {
			addMaterials(o);
		}
		try {
			// open the connection
			dbs.open();
			String command = "SELECT MAX(ID) as MAXID FROM MATERIALS";
			
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

	public void addMaterials(Materials o) {
		String valueString = "";
		String insertString = "INSERT INTO MATERIALS (";

		if (o.getMaterialName() != null) {
			insertString += "MATERIAL_NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getMaterialName()) + "',";
		}
		if (o.getDescription() != null) {
			insertString += "DESCRIPTION,";
			valueString += "'" + dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getWeightCalc() != null) {
			insertString += "WEIGHT_CALC,";
			valueString += "'" + dbs.escapeQuotes(o.getWeightCalc()) + "',";
		}
		if (o.getSource() != null) {
			insertString += "SOURCE,";
			valueString += "'" + dbs.escapeQuotes(o.getSource()) + "',";
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

	public void updateMaterials(Materials o) {
		String updateString = "update MATERIALS set ";
		if (o.getMaterialName() != null) {
			updateString += "MATERIAL_NAME='"
					+ dbs.escapeQuotes(o.getMaterialName()) + "',";
		}
		if (o.getDescription() != null) {
			updateString += "DESCRIPTION='"
					+ dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getWeightCalc() != null) {
			updateString += "WEIGHT_CALC='"
					+ dbs.escapeQuotes(o.getWeightCalc()) + "',";
		}
		if (o.getSource() != null) {
			updateString += "SOURCE='"
					+ dbs.escapeQuotes(o.getSource()) + "',";
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE ID = " + dbs.escapeQuotes(o.getId());
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

	public void deleteMaterials(Materials o) {
		String deleteString = "delete from MATERIALS  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getId());

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

	public Vector<Materials> getMaterials() {
		Materials o = new Materials();
		return selectMaterials(o);
	}

	public Vector<LibraryItem> getItems(LibraryItem i){
		String queryString = "SELECT * FROM MATERIALS WHERE SOURCE = '"+i.getSource()+"' "+
		" ORDER BY MATERIAL_NAME";
		Vector<LibraryItem> results = new Vector<LibraryItem>();
		results.addAll(getMaterialsFromSql(queryString));
		return results;
	}
	
	public Vector<Materials> getMaterials (String keyword){
		
		String queryString = "SELECT * FROM MATERIALS ";

		if (keyword != null && !keyword.equals("")) {
			queryString += "WHERE DESCRIPTION like '%" + keyword
					+ "%' OR MATERIAL_NAME LIKE '%" + keyword + "%'";
		}

		queryString += " ORDER BY MATERIAL_NAME";
		
		return getMaterialsFromSql(queryString);
		
	}
	
	public Vector<Materials> getMaterials (String keyword, Campaign c){
		
		String queryString = "SELECT * FROM MATERIALS ";
		boolean first = true;
		if (keyword != null && !keyword.equals("")) {
			queryString += "WHERE DESCRIPTION like '%" + keyword
					+ "%' OR MATERIAL_NAME LIKE '%" + keyword + "%'";
			first = false;
		}
		Vector<CampaignSource> v = c.getSources();
		if(v.size() > 0){
			if (first){
				queryString += " WHERE (";
				first = false;
			}else{
				queryString += " AND ( ";
			}
			for (int i = 0; i < v.size()-1; i++){
				CampaignSource cs = v.get(i);
				queryString += "SOURCE='"+cs.getName()+"' OR ";
			}
			CampaignSource cs = v.get(v.size()-1);
			queryString += "SOURCE='"+cs.getName()+"'  OR EXISTS (SELECT 'x' FROM CAMPAIGN_WHITE_LIST WHERE CAMPAIGN_ID = "+
		c.getId()+ " AND XREF_ID = MATERIALS.ID AND TYPE = 'Material')";
			
			queryString += ") ";
		}
		queryString += " AND NOT EXISTS (SELECT 'x' FROM CAMPAIGN_BLACK_LIST WHERE CAMPAIGN_ID = "+
		c.getId()+ " AND XREF_ID = MATERIALS.ID AND TYPE = 'Material')";
		queryString += " ORDER BY MATERIAL_NAME";
		
		return getMaterialsFromSql(queryString);
		
	}

	public Vector<Materials> selectMaterials(Materials o) {
		String selectString = "SELECT * FROM MATERIALS  ";
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
		if (o.getMaterialName() != null && !o.getMaterialName().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " MATERIAL_NAME = '"
					+ dbs.escapeQuotes(o.getMaterialName()) + "' ";
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
		if (o.getWeightCalc() != null && !o.getWeightCalc().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " WEIGHT_CALC = '"
					+ dbs.escapeQuotes(o.getWeightCalc()) + "' ";
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
		selectString += " ORDER BY MATERIAL_NAME ";
		return getMaterialsFromSql(selectString);
	}
	
	public Vector<Materials> getMaterialsFromSql(String selectString) {
		Vector<Materials> v = new Vector<Materials>();
		try {
			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}

			while (result.next()) {
				Materials obj = new Materials();

				obj.setId(result.getString("ID"));
				obj.setMaterialName(result.getString("MATERIAL_NAME"));
				obj.setDescription(result.getString("DESCRIPTION"));
				obj.setWeightCalc(result.getString("WEIGHT_CALC"));
				obj.setSource(result.getString("SOURCE"));
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
}

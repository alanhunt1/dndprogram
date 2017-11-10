package initcheck.database;

import initcheck.character.library.LibraryItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class SpecificWeaponDAO extends InitBaseDAO implements LibraryItemDAO {

	public SpecificWeaponDAO() {

	}

	public int addOrUpdateSpecificWeapon(SpecificWeapon o) {
		int i = -1;
		if (o.getId() != null) {
			updateSpecificWeapon(o);
		} else {
			addSpecificWeapon(o);
		}
		try {
			// open the connection
			dbs.open();
			String command = "SELECT MAX(ID) as MAXID FROM SPECIFIC_WEAPON";
			
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

	public void addSpecificWeapon(SpecificWeapon o) {
		String valueString = "";
		String insertString = "INSERT INTO SPECIFIC_WEAPON (";
		if (o.getId() != null) {
			insertString += "ID,";
			valueString += "'" + dbs.escapeQuotes(o.getId()) + "',";
		}
		if (o.getMinorLevel() != null) {
			insertString += "MINOR_LEVEL,";
			valueString += "'" + dbs.escapeQuotes(o.getMinorLevel()) + "',";
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
		if (o.getMediumLevel() != null) {
			insertString += "MEDIUM_LEVEL,";
			valueString += "'" + dbs.escapeQuotes(o.getMediumLevel()) + "',";
		}
		if (o.getMajorLevel() != null) {
			insertString += "MAJOR_LEVEL,";
			valueString += "'" + dbs.escapeQuotes(o.getMajorLevel()) + "',";
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

	public void updateSpecificWeapon(SpecificWeapon o) {
		String updateString = "update SPECIFIC_WEAPON set ";
		if (o.getMinorLevel() != null) {
			updateString += "MINOR_LEVEL='"
					+ dbs.escapeQuotes(o.getMinorLevel()) + "',";
		}
		if (o.getName() != null) {
			updateString += "NAME='" + dbs.escapeQuotes(o.getName()) + "',";
		}
		if (o.getDescription() != null) {
			updateString += "DESCRIPTION='"
					+ dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getSource() != null) {
			updateString += "SOURCE='" + dbs.escapeQuotes(o.getSource()) + "',";
		}
		if (o.getMediumLevel() != null) {
			updateString += "MEDIUM_LEVEL='"
					+ dbs.escapeQuotes(o.getMediumLevel()) + "',";
		}
		if (o.getMajorLevel() != null) {
			updateString += "MAJOR_LEVEL='"
					+ dbs.escapeQuotes(o.getMajorLevel()) + "',";
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
		}
	}

	public Vector<SpecificWeapon> getSpecificWeapons(){
		return selectSpecificWeapon(new SpecificWeapon());
	}
	
	public Vector<LibraryItem> getItems(LibraryItem i){
		String queryString = "SELECT * FROM SPECIFIC_WEAPON WHERE SOURCE = '"+i.getSource()+"' "+
		" ORDER BY NAME";
		Vector<LibraryItem> results = new Vector<LibraryItem>();
		results.addAll(getSpecificWeaponsFromSql(queryString));
		return results;
	}
	
	public Vector<SpecificWeapon> getSpecificWeapons (String keyword){
		
		String queryString = "SELECT * FROM SPECIFIC_WEAPON ";

		if (keyword != null && !keyword.equals("")) {
			queryString += "WHERE DESCRIPTION like '%" + keyword
					+ "%' OR NAME LIKE '%" + keyword + "%'";
		}

		queryString += " ORDER BY NAME";
		
		return getSpecificWeaponsFromSql(queryString);
		
	}
	
	public Vector<SpecificWeapon> getSpecificWeapons (String keyword, Campaign c){
		
		String queryString = "SELECT * FROM SPECIFIC_Weapon ";
		boolean first = true;
		if (keyword != null && !keyword.equals("")) {
			queryString += "WHERE DESCRIPTION like '%" + keyword
					+ "%' OR NAME LIKE '%" + keyword + "%'";
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
		c.getId()+ " AND XREF_ID = SPECIFIC_WEAPON.ID AND TYPE = 'Specific Weapon')";
			
			queryString += ") ";
		}
		queryString += " AND NOT EXISTS (SELECT 'x' FROM CAMPAIGN_BLACK_LIST WHERE CAMPAIGN_ID = "+
		c.getId()+ " AND XREF_ID = SPECIFIC_WEAPON.ID AND TYPE = 'Specific Weapon')";
		queryString += " ORDER BY NAME";
		
		return getSpecificWeaponsFromSql(queryString);
		
	}
	
	
	public Vector<SpecificWeapon> selectSpecificWeapon(SpecificWeapon o) {
		String selectString = "SELECT * FROM SPECIFIC_WEAPON  ";
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
		if (o.getMinorLevel() != null && !o.getMinorLevel().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " MINOR_LEVEL = '"
					+ dbs.escapeQuotes(o.getMinorLevel()) + "' ";
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
			selectString += " SOURCE = '" + dbs.escapeQuotes(o.getSource())
					+ "' ";
		}
		if (o.getMediumLevel() != null && !o.getMediumLevel().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " MEDIUM_LEVEL = '"
					+ dbs.escapeQuotes(o.getMediumLevel()) + "' ";
		}
		if (o.getMajorLevel() != null && !o.getMajorLevel().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " MAJOR_LEVEL = '"
					+ dbs.escapeQuotes(o.getMajorLevel()) + "' ";
		}
		return getSpecificWeaponsFromSql(selectString);
	}
	
	public Vector<SpecificWeapon> getSpecificWeaponsFromSql(String selectString){
		Vector<SpecificWeapon> v = new Vector<SpecificWeapon>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				SpecificWeapon obj = new SpecificWeapon();

				obj.setId(result.getString("ID"));
				obj.setMinorLevel(result.getString("MINOR_LEVEL"));
				obj.setName(result.getString("NAME"));
				obj.setDescription(result.getString("DESCRIPTION"));
				obj.setSource(result.getString("SOURCE"));
				obj.setMediumLevel(result.getString("MEDIUM_LEVEL"));
				obj.setMajorLevel(result.getString("MAJOR_LEVEL"));
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

	public void deleteSpecificWeapon(SpecificWeapon o) {
		String deleteString = "delete from SPECIFIC_WEAPON  ";
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
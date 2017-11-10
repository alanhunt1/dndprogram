package initcheck.database;

import initcheck.character.library.LibraryItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class DeityDAO extends InitBaseDAO implements LibraryItemDAO {

	public DeityDAO() {

	}

	public int addOrUpdateDeity(Deity o) {
		int i = -1;
		if (o.getId() != null) {
			updateDeity(o);
		} else {
			addDeity(o);
		}
		try {
			// open the connection
			dbs.open();
			String command = "SELECT MAX(ID) as MAXID FROM DEITIES";
			
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
	
	public void addDeity(Deity o) {
		String valueString = "";
		String insertString = "INSERT INTO DEITIES (";
		if (o.getId() != null) {
			insertString += "ID,";
			valueString += "'" + dbs.escapeQuotes(o.getId()) + "',";
		}
		if (o.getName() != null) {
			insertString += "NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getName()) + "',";
		}
		if (o.getAlignment() != null) {
			insertString += "ALIGNMENT,";
			valueString += "'" + dbs.escapeQuotes(o.getAlignment()) + "',";
		}
		if (o.getDescription() != null) {
			insertString += "DESCRIPTION,";
			valueString += "'" + dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getFavoredWeapon() != null) {
			insertString += "FAVORED_WEAPON,";
			valueString += "'" + dbs.escapeQuotes(o.getFavoredWeapon()) + "',";
		}
		if (o.getSource() != null) {
			insertString += "SOURCE,";
			valueString += "'" + dbs.escapeQuotes(o.getSource()) + "',";
		}
		if (o.getRace() != null) {
			insertString += "RACE,";
			valueString += "'" + dbs.escapeQuotes(o.getRace()) + "',";
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

	public void updateDeity(Deity o) {
		String updateString = "update DEITIES set ";
		if (o.getName() != null) {
			updateString += "NAME='" + dbs.escapeQuotes(o.getName()) + "',";
		}
		if (o.getAlignment() != null) {
			updateString += "ALIGNMENT='" + dbs.escapeQuotes(o.getAlignment())
					+ "',";
		}
		if (o.getDescription() != null) {
			updateString += "DESCRIPTION='"
					+ dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getFavoredWeapon() != null) {
			updateString += "FAVORED_WEAPON='"
					+ dbs.escapeQuotes(o.getFavoredWeapon()) + "',";
		}
		if (o.getSource() != null) {
			updateString += "SOURCE='" + dbs.escapeQuotes(o.getSource()) + "',";
		}
		if (o.getRace() != null) {
			updateString += "RACE='" + dbs.escapeQuotes(o.getRace()) + "',";
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + "  ";
		try {
			dbs.open();
			dbs.executeSQLCommand(updateString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}

	public Vector<Deity> getDeities() {
		return selectDeity(new Deity());
	}
	
	public Vector<LibraryItem> getItems(LibraryItem i){
		String queryString = "SELECT * FROM DEITIES WHERE SOURCE = '"+i.getSource()+"' "+
		" ORDER BY NAME ";
		Vector<LibraryItem> results = new Vector<LibraryItem>();
		results.addAll(getDeitiesFromSql(queryString));
		return results;
	}
	
	public Vector<Deity> getDeities(String keyword) {
		String selectString = "SELECT * FROM Deities WHERE NAME " + "LIKE '%"
				+ keyword + "%' OR DESCRIPTION LIKE '%" + keyword
				+ "%' ORDER BY NAME";

		return getDeitiesFromSql(selectString);
	}

	public Vector<Deity> getDeities(String keyword, Campaign c){
		
		String queryString = "SELECT * FROM Deities	 ";
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
			queryString += "SOURCE='"+cs.getName()+"' OR EXISTS (SELECT 'x' FROM CAMPAIGN_WHITE_LIST WHERE CAMPAIGN_ID = "+
		c.getId()+ " AND XREF_ID = DEITIES.ID AND TYPE = 'Deity')";
			
			queryString += ") ";
		}
		queryString += " AND NOT EXISTS (SELECT 'x' FROM CAMPAIGN_BLACK_LIST WHERE CAMPAIGN_ID = "+
		c.getId()+ " AND XREF_ID = DEITIES.ID AND TYPE = 'Deity')";
		queryString += " ORDER BY NAME";
		
		return getDeitiesFromSql(queryString);
		
	}
	public Vector<Deity> selectDeity(Deity o) {
		String selectString = "SELECT * FROM DEITIES  ";
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
		if (o.getName() != null && !o.getName().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " NAME = '" + dbs.escapeQuotes(o.getName()) + "' ";
		}
		if (o.getAlignment() != null && !o.getAlignment().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ALIGNMENT = '"
					+ dbs.escapeQuotes(o.getAlignment()) + "' ";
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
		if (o.getFavoredWeapon() != null && !o.getFavoredWeapon().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " FAVORED_WEAPON = '"
					+ dbs.escapeQuotes(o.getFavoredWeapon()) + "' ";
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
		if (o.getRace() != null && !o.getRace().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " RACE = '" + dbs.escapeQuotes(o.getRace()) + "' ";
		}
		return getDeitiesFromSql(selectString);
	}
	
	public Vector<Deity> getDeitiesFromSql(String selectString) {
		Vector<Deity> v = new Vector<Deity>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				Deity obj = new Deity();

				obj.setId(result.getString("ID"));
				obj.setName(result.getString("NAME"));
				obj.setAlignment(result.getString("ALIGNMENT"));
				obj.setDescription(result.getString("DESCRIPTION"));
				obj.setFavoredWeapon(result.getString("FAVORED_WEAPON"));
				obj.setSource(result.getString("SOURCE"));
				obj.setRace(result.getString("RACE"));
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

	public void deleteDeity(Deity o) {
		String deleteString = "delete from DEITIES  ";
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
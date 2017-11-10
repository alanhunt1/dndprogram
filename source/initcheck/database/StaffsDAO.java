package initcheck.database;

import initcheck.character.library.LibraryItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class StaffsDAO extends InitBaseDAO implements LibraryItemDAO {

	public StaffsDAO() {

	}

	public int addOrUpdateStaffs(Staffs o) {
		int i = -1;
		if (o.getId() != null) {
			updateStaffs(o);
		} else {
			addStaffs(o);
		}
		try {
			// open the connection
			dbs.open();
			String command = "SELECT MAX(ID) as MAXID STAFFS";

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

	public void addStaffs(Staffs o) {
		String valueString = "";
		String insertString = "INSERT INTO STAFFS (";

		if (o.getMlevel() != null) {
			insertString += "MLEVEL,";
			valueString += "'" + dbs.escapeQuotes(o.getMlevel()) + "',";
		}
		if (o.getName() != null) {
			insertString += "NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getName()) + "',";
		}
		if (o.getDescription() != null) {
			insertString += "DESCRIPTION,";
			valueString += "'" + dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getCasterLevel() != null) {
			insertString += "CASTER_LEVEL,";
			valueString += "'" + dbs.escapeQuotes(o.getCasterLevel()) + "',";
		}
		if (o.getPrereqs() != null) {
			insertString += "PREREQS,";
			valueString += "'" + dbs.escapeQuotes(o.getPrereqs()) + "',";
		}
		if (o.getCost() != null) {
			insertString += "COST,";
			valueString += "'" + dbs.escapeQuotes(o.getCost()) + "',";
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

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}

	public void updateStaffs(Staffs o) {
		String updateString = "update STAFFS set ";
		if (o.getMlevel() != null) {
			updateString += "MLEVEL='" + dbs.escapeQuotes(o.getMlevel()) + "',";
		}
		if (o.getName() != null) {
			updateString += "NAME='" + dbs.escapeQuotes(o.getName()) + "',";
		}
		if (o.getDescription() != null) {
			updateString += "DESCRIPTION='"
					+ dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getCasterLevel() != null) {
			updateString += "CASTER_LEVEL='"
					+ dbs.escapeQuotes(o.getCasterLevel()) + "',";
		}
		if (o.getPrereqs() != null) {
			updateString += "PREREQS='" + dbs.escapeQuotes(o.getPrereqs())
					+ "',";
		}
		if (o.getCost() != null) {
			updateString += "COST='" + dbs.escapeQuotes(o.getCost()) + "',";
		}
		if (o.getSource() != null) {
			updateString += "SOURCE='" + dbs.escapeQuotes(o.getSource()) + "',";
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE ID =" + dbs.escapeQuotes(o.getId()) + "  ";
		try {
			dbs.open();
			dbs.executeSQLCommand(updateString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}

	public Vector<LibraryItem> getItems(LibraryItem i){
		String queryString = "SELECT * FROM STAFFS WHERE SOURCE = '"+i.getSource()+"' "+
		" ORDER BY NAME ";
		Vector<LibraryItem> results = new Vector<LibraryItem>();
		results.addAll(getStaffsFromSql(queryString));
		return results;
	}
	
	public Vector<Staffs> getStaffs(String keyword) {
		String selectString = "SELECT * FROM STAFFS WHERE NAME " + "LIKE '%"
				+ keyword + "%' OR DESCRIPTION LIKE '%" + keyword
				+ "%' ORDER BY NAME";
		return getStaffsFromSql(selectString);
		
	}
	
	public Vector<Staffs> getStaffs (String keyword, Campaign c){
		
		String queryString = "SELECT * FROM staffS	 ";
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
		c.getId()+ " AND XREF_ID = STAFFS.ID AND TYPE = 'Staff')";
			
			queryString += ") ";
		}
		queryString += " AND NOT EXISTS (SELECT 'x' FROM CAMPAIGN_BLACK_LIST WHERE CAMPAIGN_ID = "+
		c.getId()+ " AND XREF_ID = STAFFS.ID AND TYPE = 'Staff')";
		queryString += " ORDER BY NAME";
		
		return getStaffsFromSql(queryString);
		
	}
	

	public Vector<Staffs> getStaffs() {
		return selectStaffs(new Staffs());
	}

	public Vector<Staffs> selectStaffs(Staffs o) {
		String selectString = "SELECT * FROM STAFFS  ";
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
		if (o.getMlevel() != null && !o.getMlevel().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " MLEVEL = '" + dbs.escapeQuotes(o.getMlevel())
					+ "' ";
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
		if (o.getCasterLevel() != null && !o.getCasterLevel().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " CASTER_LEVEL = '"
					+ dbs.escapeQuotes(o.getCasterLevel()) + "' ";
		}
		if (o.getPrereqs() != null && !o.getPrereqs().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " PREREQS = '" + dbs.escapeQuotes(o.getPrereqs())
					+ "' ";
		}
		if (o.getCost() != null && !o.getCost().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " COST = '" + dbs.escapeQuotes(o.getCost()) + "' ";
		}
		selectString += " ORDER BY NAME";
		return getStaffsFromSql(selectString);
	}
	public Vector<Staffs> getStaffsFromSql(String selectString) {
		Vector<Staffs> v = new Vector<Staffs>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				Staffs obj = new Staffs();

				obj.setId(result.getString("ID"));
				obj.setMlevel(result.getString("MLEVEL"));
				obj.setName(result.getString("NAME"));
				obj.setDescription(result.getString("DESCRIPTION"));
				obj.setCasterLevel(result.getString("CASTER_LEVEL"));
				obj.setPrereqs(result.getString("PREREQS"));
				obj.setCost(result.getString("COST"));
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

	public void deleteStaffs(Staffs o) {
		String deleteString = "delete from STAFFS  ";
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

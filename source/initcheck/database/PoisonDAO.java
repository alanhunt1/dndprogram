package initcheck.database;

import initcheck.character.library.LibraryItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PoisonDAO extends InitBaseDAO implements LibraryItemDAO {

	public PoisonDAO() {

	}

	public int addOrUpdatePoison(Poison o) {
		int i = -1;
		
		if (o.getId() != null) {
			updatePoison(o);
			i = Integer.parseInt(o.getId());
		} else {
			addPoison(o);
			try {
				// open the connection
				dbs.open();
				String command = "SELECT MAX(ID) MAXID FROM POISON";
				
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
	
	public void addPoison(Poison o) {
		String valueString = "";
		String insertString = "INSERT INTO POISON (";
		if (o.getId() != null) {
			insertString += "ID,";
			valueString += "'" + dbs.escapeQuotes(o.getId()) + "',";
		}
		if (o.getName() != null) {
			insertString += "NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getName()) + "',";
		}
		if (o.getType() != null) {
			insertString += "TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getType()) + "',";
		}
		if (o.getDc() != null) {
			insertString += "DC,";
			if (o.getDc().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getDc()) + ",";

			}
		}
		if (o.getInitial() != null) {
			insertString += "INITIAL,";
			valueString += "'" + dbs.escapeQuotes(o.getInitial()) + "',";
		}
		if (o.getSecondary() != null) {
			insertString += "SECONDARY,";
			valueString += "'" + dbs.escapeQuotes(o.getSecondary()) + "',";
		}
		if (o.getPrice() != null) {
			insertString += "PRICE,";
			valueString += "'" + dbs.escapeQuotes(o.getPrice()) + "',";
		}
		if (o.getSource() != null) {
			insertString += "Source,";
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

	public void updatePoison(Poison o) {
		String updateString = "update POISON set ";
		if (o.getName() != null) {
			updateString += "NAME='" + dbs.escapeQuotes(o.getName()) + "',";
		}
		if (o.getType() != null) {
			updateString += "TYPE='" + dbs.escapeQuotes(o.getType()) + "',";
		}
		if (o.getDc() != null) {
			if (!o.getDc().equals("")) {
				updateString += "DC=" + dbs.escapeQuotes(o.getDc()) + ",";
			} else {
				updateString += "DC=null,";
			}
		}
		if (o.getInitial() != null) {
			updateString += "INITIAL='" + dbs.escapeQuotes(o.getInitial())
					+ "',";
		}
		if (o.getSecondary() != null) {
			updateString += "SECONDARY='" + dbs.escapeQuotes(o.getSecondary())
					+ "',";
		}
		if (o.getPrice() != null) {
			updateString += "PRICE='" + dbs.escapeQuotes(o.getPrice()) + "',";
		}
		if (o.getSource() != null) {
			updateString += "Source='"
					+ dbs.escapeQuotes(o.getSource()) + "',";
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
	
	public Vector<Poison> getPoisons(){
		return selectPoison(new Poison());
	}
	
	public Vector<LibraryItem> getItems(LibraryItem i){
		String queryString = "SELECT * FROM POISON WHERE SOURCE = '"+i.getSource()+"' "+
		" ORDER BY NAME ";
		Vector<LibraryItem> results = new Vector<LibraryItem>();
		results.addAll(getPoisonsFromSql(queryString));
		return results;
	}
	
	public Vector<Poison> getPoisons(String keyword) {
		String selectString = "SELECT * FROM Poison WHERE NAME " + "LIKE '%"
				+ keyword + "%' OR DESCRIPTION LIKE '%" + keyword
				+ "%' ORDER BY NAME";

		return getPoisonsFromSql(selectString);
	}

	public Vector<Poison> getPoisons (String keyword, Campaign c){
		
		String queryString = "SELECT * FROM Poison	 ";
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
		c.getId()+ " AND XREF_ID = POISON.ID AND TYPE = 'Poison')";
			
			queryString += ") ";
		}
		queryString += " AND NOT EXISTS (SELECT 'x' FROM CAMPAIGN_BLACK_LIST WHERE CAMPAIGN_ID = "+
		c.getId()+ " AND XREF_ID = POISON.ID AND TYPE = 'Poison')";
		queryString += " ORDER BY NAME";
		
		return getPoisonsFromSql(queryString);
		
	}
	
	public Vector<Poison> selectPoison(Poison o) {
		String selectString = "SELECT * FROM POISON  ";
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
		if (o.getType() != null && !o.getType().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " TYPE = '" + dbs.escapeQuotes(o.getType()) + "' ";
		}
		if (o.getDc() != null && !o.getDc().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " DC = '" + dbs.escapeQuotes(o.getDc()) + "' ";
		}
		if (o.getInitial() != null && !o.getInitial().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " INITIAL = '" + dbs.escapeQuotes(o.getInitial())
					+ "' ";
		}
		if (o.getSecondary() != null && !o.getSecondary().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SECONDARY = '"
					+ dbs.escapeQuotes(o.getSecondary()) + "' ";
		}
		if (o.getPrice() != null && !o.getPrice().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " PRICE = '" + dbs.escapeQuotes(o.getPrice())
					+ "' ";
		}
		return getPoisonsFromSql(selectString);
	}
	
	public Vector<Poison> getPoisonsFromSql(String selectString){
		Vector<Poison> v = new Vector<Poison>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				Poison obj = new Poison();

				obj.setId(result.getString("ID"));
				obj.setName(result.getString("NAME"));
				obj.setType(result.getString("TYPE"));
				obj.setDc(result.getString("DC"));
				obj.setInitial(result.getString("INITIAL"));
				obj.setSecondary(result.getString("SECONDARY"));
				obj.setPrice(result.getString("PRICE"));
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

	public void deletePoison(Poison o) {
		String deleteString = "delete from POISON  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + "";

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
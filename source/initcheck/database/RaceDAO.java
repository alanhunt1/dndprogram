package initcheck.database;

import initcheck.character.library.LibraryItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class RaceDAO extends InitBaseDAO implements LibraryItemDAO {

	private static Vector<Race> raceVector = null;

	public RaceDAO() {

	}

	public int addOrUpdateRace(Race o) {
		int id = -1;
		if (o.getId() != null) {
			id = Integer.parseInt(o.getId());
			updateRace(o);
		} else {
			id = addRace(o);
		}
		return id;
	}

	public Vector<Race> getRaces() {
		if (raceVector != null) {
			return raceVector;
		}
		raceVector = selectRace(new Race());
		return raceVector;
	}

	public int addRace(Race o) {
		int id = -1;
		String valueString = "";
		String insertString = "INSERT INTO RACE (";
		if (o.getId() != null) {
			insertString += "ID,";
			valueString += dbs.escapeQuotes(o.getId()) + ",";
		}
		if (o.getRace() != null) {
			insertString += "Race,";
			valueString += "'" + dbs.escapeQuotes(o.getRace()) + "',";
		}
		if (o.getDescription() != null) {
			insertString += "Description,";
			valueString += "'" + dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getSize() != null) {
			insertString += "Size,";
			valueString += "'" + dbs.escapeQuotes(o.getSize()) + "',";
		}

		if (o.getBasespeed() != null) {
			insertString += "BaseSpeed,";
			valueString += o.getBasespeed() + ",";
		}
		if (o.getFavoredclass() != null) {
			insertString += "FavoredClass,";
			valueString += "'" + dbs.escapeQuotes(o.getFavoredclass()) + "',";
		}
		if (o.getSource() != null) {
			insertString += "Source,";
			valueString += "'" + dbs.escapeQuotes(o.getSource()) + "',";
		}
		if (o.getLevelAdjustment() != null) {
			insertString += "LEVEL_ADJUSTMENT,";
			valueString += "'" + dbs.escapeQuotes(o.getLevelAdjustment()) + "',";
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

			String command = "SELECT MAX(ID) AS MAXID FROM RACE";
			ResultSet result = dbs.executeSQLQuery(command);
			if (result.next()) {
				id = result.getInt("MAXID");
			}
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
			raceVector = null;
		}
		return id;
	}

	public void updateRace(Race o) {
		String updateString = "update RACE set ";
		if (o.getRace() != null) {
			updateString += "Race='" + dbs.escapeQuotes(o.getRace()) + "',";
		}
		if (o.getDescription() != null) {
			updateString += "Description='"
					+ dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getSize() != null) {
			updateString += "Size='" + dbs.escapeQuotes(o.getSize()) + "',";
		}

		if (o.getBasespeed() != null) {
			updateString += "BaseSpeed=" + dbs.escapeQuotes(o.getBasespeed())
					+ ",";
		}
		if (o.getFavoredclass() != null) {
			updateString += "FavoredClass='"
					+ dbs.escapeQuotes(o.getFavoredclass()) + "',";
		}
		if (o.getSource() != null) {
			updateString += "Source='"
					+ dbs.escapeQuotes(o.getSource()) + "',";
		}
		if (o.getLevelAdjustment() != null) {
			updateString += "LEVEL_ADJUSTMENT='"
					+ dbs.escapeQuotes(o.getLevelAdjustment()) + "',";
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
			raceVector = null;
		}
	}

	public boolean exists(Race o) {
		Race r = new Race();
		r.setId(o.getId());
		return selectRace(r).size() > 0;
	}

	public Race getRace(String name) {
		Race obj = new Race();
		if (raceVector != null) {
			for (int i = 0; i < raceVector.size(); i++) {
				Race r = (Race) raceVector.get(i);
				if (r.getRace().equals(name)) {
					return r;
				}
			}
			return obj;
		}

		String selectString = "SELECT * FROM RACE WHERE RACE='" + name + "'";
		Vector<Race> v = getRaceFromSql(selectString);
		
		if (v.size() > 0){
			obj = v.get(0);
		}
		
		return obj;
	}

	public  Vector<Race> getRaceBySource (String source){
		String selectString = "SELECT * FROM RACE WHERE SOURCE='" + source + "'";
		return getRaceFromSql(selectString);
	}
	
	public Vector<LibraryItem> getItems(LibraryItem i){
		String queryString = "SELECT * FROM RACE WHERE SOURCE = '"+i.getSource()+"' "+
		" ORDER BY RACE";
		Vector<LibraryItem> results = new Vector<LibraryItem>();
		results.addAll(getRaceFromSql(queryString));
		return results;
	}
	
	public Vector<Race> getRaces (String keyword){
		
		String queryString = "SELECT * FROM RACE ";

		if (keyword != null && !keyword.equals("")) {
			queryString += "WHERE DESCRIPTION like '%" + keyword
					+ "%' OR RACE LIKE '%" + keyword + "%'";
		}

		queryString += " ORDER BY RACE";
		
		return getRaceFromSql(queryString);
		
	}
	
	public Vector<Race> getRaces (String keyword, Campaign c){
		
		String queryString = "SELECT * FROM RACE ";
		boolean first = true;	
		if (keyword != null && !keyword.equals("")) {
			queryString += "WHERE DESCRIPTION like '%" + keyword
					+ "%' OR RACE LIKE '%" + keyword + "%'";
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
		c.getId()+ " AND XREF_ID = RACE.ID AND TYPE = 'Race')";
			
			queryString += ") ";
		}
		queryString += " AND NOT EXISTS (SELECT 'x' FROM CAMPAIGN_BLACK_LIST WHERE CAMPAIGN_ID = "+
		c.getId()+ " AND XREF_ID = RACE.ID AND TYPE = 'Race')";
		queryString += " ORDER BY RACE";
		
		return getRaceFromSql(queryString);
		
	}
	
	public Vector<Race> selectRace(Race o) {
		String selectString = "SELECT * FROM RACE  ";
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
		if (o.getRace() != null && !o.getRace().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " Race = '" + dbs.escapeQuotes(o.getRace()) + "' ";
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
		if (o.getSize() != null && !o.getSize().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " Size = '" + dbs.escapeQuotes(o.getSize()) + "' ";
		}

		if (o.getBasespeed() != null && !o.getBasespeed().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " BaseSpeed = '"
					+ dbs.escapeQuotes(o.getBasespeed()) + "' ";
		}
		if (o.getFavoredclass() != null && !o.getFavoredclass().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " FavoredClass = '"
					+ dbs.escapeQuotes(o.getFavoredclass()) + "' ";
		}
		if (o.getSource() != null && !o.getSource().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " Source = '"
					+ dbs.escapeQuotes(o.getSource()) + "' ";
		}
		selectString += " ORDER BY RACE";
		
		return getRaceFromSql(selectString);
	}
		
	public Vector<Race> getRaceFromSql(String selectString) {
		Vector<Race> v = new Vector<Race>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				Race obj = new Race();

				obj.setId(result.getString("ID"));
				obj.setRace(result.getString("Race"));
				obj.setDescription(result.getString("Description"));
				obj.setSize(result.getString("Size"));
				obj.setBasespeed(result.getString("BaseSpeed"));
				obj.setFavoredclass(result.getString("FavoredClass"));
				obj.setSource(result.getString("SOURCE"));
				obj.setLevelAdjustment(result.getString("LEVEL_ADJUSTMENT"));
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

	public void deleteRace(Race o) {
		String deleteString = "delete from RACE  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getId());

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
			raceVector = null;
		}
	}
}

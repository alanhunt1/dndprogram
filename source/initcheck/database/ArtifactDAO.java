package initcheck.database;

import initcheck.character.library.LibraryItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ArtifactDAO extends InitBaseDAO implements LibraryItemDAO {

	public ArtifactDAO() {

	}

	public int addOrUpdateArtifact(Artifact o) {
		int i = -1;
		if (o.getId() != null) {
			updateArtifacts(o);
		} else {
			addArtifacts(o);
		}
		try {
			// open the connection
			dbs.open();
			String command = "SELECT MAX(ID) as MAXID FROM ARTIFACTS";
			
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
	
	
	public void addArtifacts(Artifact o) {
		String valueString = "";
		String insertString = "INSERT INTO ARTIFACTS (";
		if (o.getId() != null) {
			insertString += "ID,";
			valueString += "'" + dbs.escapeQuotes(o.getId()) + "',";
		}
		if (o.getName() != null) {
			insertString += "NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getName()) + "',";
		}
		if (o.getWeight() != null) {
			insertString += "WEIGHT,";
			if (o.getWeight().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getWeight()) + ",";

			}
		}
		if (o.getDescription() != null) {
			insertString += "DESCRIPTION,";
			valueString += "'" + dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getType() != null) {
			insertString += "TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getType()) + "',";
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

	public void updateArtifacts(Artifact o) {
		String updateString = "update ARTIFACTS set ";
		if (o.getName() != null) {
			updateString += "NAME='" + dbs.escapeQuotes(o.getName()) + "',";
		}
		if (o.getWeight() != null) {
			if (!o.getWeight().equals("")) {
				updateString += "WEIGHT=" + dbs.escapeQuotes(o.getWeight())
						+ ",";
			} else {
				updateString += "WEIGHT=null,";
			}
		}
		if (o.getDescription() != null) {
			updateString += "DESCRIPTION='"
					+ dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getType() != null) {
			updateString += "TYPE='"
					+ dbs.escapeQuotes(o.getType()) + "',";
		}
		if (o.getSource() != null) {
			updateString += "SOURCE='"
					+ dbs.escapeQuotes(o.getSource()) + "',";
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

	public Vector<Artifact> getArtifacts() {
		return selectArtifacts(new Artifact());
	}
	
	public Vector<LibraryItem> getItems(LibraryItem i){
		String queryString = "SELECT * FROM ARTIFACTS WHERE SOURCE = '"+i.getSource()+"' "+
		" ORDER BY NAME";
		Vector<LibraryItem> results = new Vector<LibraryItem>();
		results.addAll(getArtifactsFromSql(queryString));
		return results;
	}
	
	public Vector<Artifact> getArtifacts (String keyword){
		
		String queryString = "SELECT * FROM ArtifactS ";

		if (keyword != null && !keyword.equals("")) {
			queryString += "WHERE DESCRIPTION like '%" + keyword
					+ "%' OR NAME LIKE '%" + keyword + "%'";
		}

		queryString += " ORDER BY NAME";
		
		return getArtifactsFromSql(queryString);
		
	}
	
	public Vector<Artifact> getArtifacts (String keyword, Campaign c){
		
		String queryString = "SELECT * FROM ArtifactS ";
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
			queryString += "SOURCE='"+cs.getName()+"'  OR  EXISTS (SELECT 'x' FROM CAMPAIGN_WHITE_LIST WHERE CAMPAIGN_ID = "+
		c.getId()+ " AND XREF_ID = ARTIFACTS.ID AND TYPE = 'Artifact')";
			
			queryString += ") ";
		}
		queryString += " AND NOT EXISTS (SELECT 'x' FROM CAMPAIGN_BLACK_LIST WHERE CAMPAIGN_ID = "+
		c.getId()+ " AND XREF_ID = ARTIFACTS.ID AND TYPE = 'Artifact')";
		
		queryString += " ORDER BY NAME";
		
		return getArtifactsFromSql(queryString);
		
	}
	
	public Vector<Artifact> selectArtifacts(Artifact o) {
		String selectString = "SELECT * FROM ARTIFACTS  ";
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
		return getArtifactsFromSql(selectString);
	}
	public Vector<Artifact> getArtifactsFromSql(String selectString) {
		Vector<Artifact> v = new Vector<Artifact>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				Artifact obj = new Artifact();

				obj.setId(result.getString("ID"));
				obj.setName(result.getString("NAME"));
				obj.setWeight(result.getString("WEIGHT"));
				obj.setDescription(result.getString("DESCRIPTION"));
				obj.setType(result.getString("TYPE"));
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

	public void deleteArtifacts(Artifact o) {
		String deleteString = "delete from ARTIFACTS  ";
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
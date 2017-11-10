package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PartyDAO extends InitBaseDAO {

	public PartyDAO() {

	}

	public int addOrUpdateParty(Party o) {
		int i = -1;
		if (o.getId() != null) {
			updateParty(o);
		} else {
			addParty(o);
		}
		try {
			// open the connection
			dbs.open();
			String command = "SELECT MAX(ID) as MAXID FROM PARTY";
			
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

	public void addParty(Party o) {
		String valueString = "";
		String insertString = "INSERT INTO PARTY (";
		if (o.getId() != null) {
			insertString += "ID,";
			valueString += "'" + dbs.escapeQuotes(o.getId()) + "',";
		}
		if (o.getPartyName() != null) {
			insertString += "PARTY_NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getPartyName()) + "',";
		}
		if (o.getPartyColor() != null) {
			insertString += "PARTY_COLOR,";
			valueString += "'" + dbs.escapeQuotes(o.getPartyColor()) + "',";
		}
		if (o.getPartyLocation() != null) {
			insertString += "PARTY_LOCATION,";
			valueString += "'" + dbs.escapeQuotes(o.getPartyLocation()) + "',";
		}
		if (o.getPartyNotes() != null) {
			insertString += "PARTY_NOTES,";
			valueString += "'" + dbs.escapeQuotes(o.getPartyNotes()) + "',";
		}
		if (o.getPartyType() != null) {
			insertString += "PARTY_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getPartyType()) + "',";
		}
		if (o.getCampaignId() != null) {
			insertString += "CAMPAIGN_ID,";
			if (o.getCampaignId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getCampaignId()) + ",";

			}
		}
		if (o.getElapsedTime() != null) {
			insertString += "ELAPSED_TIME,";
			valueString += "'" + dbs.escapeQuotes(o.getElapsedTime()) + "',";
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

	public void updateParty(Party o) {
		String updateString = "update PARTY set ";
		if (o.getPartyName() != null) {
			updateString += "PARTY_NAME='" + dbs.escapeQuotes(o.getPartyName())
					+ "',";
		}
		if (o.getPartyColor() != null) {
			updateString += "PARTY_COLOR='"
					+ dbs.escapeQuotes(o.getPartyColor()) + "',";
		}
		if (o.getPartyLocation() != null) {
			updateString += "PARTY_LOCATION='"
					+ dbs.escapeQuotes(o.getPartyLocation()) + "',";
		}
		if (o.getPartyNotes() != null) {
			updateString += "PARTY_NOTES='"
					+ dbs.escapeQuotes(o.getPartyNotes()) + "',";
		}
		if (o.getPartyType() != null) {
			updateString += "PARTY_TYPE='" + dbs.escapeQuotes(o.getPartyType())
					+ "',";
		}
		if (o.getCampaignId() != null) {
			if (!o.getCampaignId().equals("")) {
				updateString += "CAMPAIGN_ID="
						+ dbs.escapeQuotes(o.getCampaignId()) + ",";
			} else {
				updateString += "CAMPAIGN_ID=null,";
			}
		}
		if (o.getElapsedTime() != null) {
			updateString += "ELAPSED_TIME='" + dbs.escapeQuotes(o.getElapsedTime())
					+ "',";
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

	public Vector<Party> getParties(){
		return selectParty(new Party());
	}
	
	public Party getParty(String id) {
		Party p = new Party();
		p.setId(id);
		Vector<Party> v = selectParty(p);
		if (v.size() > 0){
			return v.get(0);
		}
		return null;
	}
	
	public Vector<Party> selectParty(Party o) {
		String selectString = "SELECT * FROM PARTY  ";
		boolean first = true;
		if (o.getId() != null && !o.getId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ID = " + dbs.escapeQuotes(o.getId()) + "";
		}
		if (o.getPartyName() != null && !o.getPartyName().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " PARTY_NAME = '"
					+ dbs.escapeQuotes(o.getPartyName()) + "' ";
		}
		if (o.getPartyColor() != null && !o.getPartyColor().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " PARTY_COLOR = '"
					+ dbs.escapeQuotes(o.getPartyColor()) + "' ";
		}
		if (o.getPartyLocation() != null && !o.getPartyLocation().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " PARTY_LOCATION = '"
					+ dbs.escapeQuotes(o.getPartyLocation()) + "' ";
		}
		if (o.getPartyNotes() != null && !o.getPartyNotes().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " PARTY_NOTES = '"
					+ dbs.escapeQuotes(o.getPartyNotes()) + "' ";
		}
		if (o.getPartyType() != null && !o.getPartyType().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " PARTY_TYPE = '"
					+ dbs.escapeQuotes(o.getPartyType()) + "' ";
		}
		if (o.getCampaignId() != null && !o.getCampaignId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " CAMPAIGN_ID = "
					+ dbs.escapeQuotes(o.getCampaignId()) + " ";
		}
		selectString += " ORDER BY PARTY_NAME ";
		
		Vector<Party> v = new Vector<Party>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				Party obj = new Party();

				obj.setId(result.getString("ID"));
				obj.setPartyName(result.getString("PARTY_NAME"));
				obj.setPartyColor(result.getString("PARTY_COLOR"));
				obj.setPartyLocation(result.getString("PARTY_LOCATION"));
				obj.setPartyNotes(result.getString("PARTY_NOTES"));
				obj.setPartyType(result.getString("PARTY_TYPE"));
				obj.setCampaignId(result.getString("CAMPAIGN_ID"));
				obj.setElapsedTime(result.getString("ELAPSED_TIME"));
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

	public void deleteParty(Party o) {
		String deleteString = "delete from PARTY  ";
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
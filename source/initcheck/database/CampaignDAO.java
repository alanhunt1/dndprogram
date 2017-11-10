package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class CampaignDAO extends InitBaseDAO {

	public CampaignDAO() {

	}

	public int addOrUpdateCampaign(Campaign o) {
		int i = -1;
		if (o.getId() != null) {
			updateCampaign(o);
			i = Integer.parseInt(o.getId());
		} else {
			addCampaign(o);
			try {
				// open the connection
				dbs.open();
				String command = "SELECT MAX(ID) as MAXID FROM CAMPAIGNS";
				
				ResultSet result = dbs.executeSQLQuery(command);
				if (result.next()) {
					i = result.getInt("MAXID");
				}

			} catch (Exception uhe) {
				logger.log("ERROR : " + uhe.toString());
			} finally {
				// close the connection
				dbs.close();
			}
		}
		
		
		return i;
	}

	public void addCampaign(Campaign o) {
		String valueString = "";
		String insertString = "INSERT INTO CAMPAIGNS (";
		if (o.getId() != null) {
			insertString += "ID,";
			valueString += "'" + dbs.escapeQuotes(o.getId()) + "',";
		}
		if (o.getName() != null) {
			insertString += "NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getName()) + "',";
		}
		if (o.getNotes() != null) {
			insertString += "NOTES,";
			valueString += "'" + dbs.escapeQuotes(o.getNotes()) + "',";
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
		}
	}

	public void updateCampaign(Campaign o) {
		String updateString = "update CAMPAIGNS set ";
		if (o.getName() != null) {
			updateString += "NAME='" + dbs.escapeQuotes(o.getName()) + "',";
		}
		if (o.getNotes() != null) {
			updateString += "NOTES='" + dbs.escapeQuotes(o.getNotes()) + "',";
		}
		if (o.getRulesetId() != null) {
			updateString += "RULESET_ID='" + dbs.escapeQuotes(o.getRulesetId()) + "',";
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

	public Vector<Campaign> getCampaigns() {
		return selectCampaign(new Campaign());
	}
	
	public Campaign getCampaign(String id) {
		Campaign c = new Campaign();
		c.setId(id);
		Vector<Campaign> v = selectCampaign(c);
		if (v.size() > 0){
			return v.get(0);
		}
		return null;
	}
	
	public Vector<Campaign> selectCampaign(Campaign o) {
		String selectString = "SELECT * FROM CAMPAIGNS, RULESETS WHERE CAMPAIGNS.RULESET_ID = RULESETS.RULESET_ID   ";
		boolean first = false;
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
		if (o.getNotes() != null && !o.getNotes().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " NOTES = '" + dbs.escapeQuotes(o.getNotes())
					+ "' ";
		}
		if (o.getRulesetId() != null && !o.getRulesetId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " RULESET_ID = '" + dbs.escapeQuotes(o.getRulesetId())
					+ "' ";
		}
		Vector<Campaign> v = new Vector<Campaign>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			CampaignSourceDAO csdb = new CampaignSourceDAO();
			CampaignWhiteListDAO cwldb = new CampaignWhiteListDAO();
			CampaignBlackListDAO cbldb = new CampaignBlackListDAO();
			
			while (result.next()) {
				Campaign obj = new Campaign();

				obj.setId(result.getString("ID"));
				obj.setName(result.getString("NAME"));
				obj.setNotes(result.getString("NOTES"));
				obj.setRulesetId(result.getString("RULESET_ID"));
				obj.setRulesetName(result.getString("RULESET_NAME"));
				obj.setSources(csdb.getCampaignSources(obj.getId()));
				obj.setWhiteList(cwldb.getCampaignWhiteList(obj.getId()));
				obj.setBlackList(cbldb.getCampaignBlackList(obj.getId()));
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

	public void deleteCampaign(Campaign o) {
		String deleteString = "delete from CAMPAIGNS  ";
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
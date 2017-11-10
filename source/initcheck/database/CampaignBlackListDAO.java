package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class CampaignBlackListDAO extends InitBaseDAO {

	public CampaignBlackListDAO() {

	}

	public int addOrUpdateCampaignBlackList(CampaignBlackList o) {
		int i = -1;
		if (o.getId() != null) {
			updateCampaignBlackList(o);
		} else {
			addCampaignBlackList(o);
		}
		try {
			// open the connection
			dbs.open();
			String command = "SELECT MAX(ID) as MAXID FROM CAMPAIGN_BLACK_LIST";
			
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

	public void addCampaignBlackList(CampaignBlackList o) {
		String valueString = "";
		String insertString = "INSERT INTO CAMPAIGN_BLACK_LIST (";
		if (o.getId() != null) {
			insertString += "ID,";
			valueString += "'" + dbs.escapeQuotes(o.getId()) + "',";
		}
		if (o.getCampaignId() != null) {
			insertString += "CAMPAIGN_ID,";
			if (o.getCampaignId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getCampaignId()) + ",";

			}
		}
		if (o.getType() != null) {
			insertString += "TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getType()) + "',";
		}
		if (o.getXrefId() != null) {
			insertString += "XREF_ID,";
			if (o.getXrefId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getXrefId()) + ",";

			}
		}
		if (o.getSourceId() != null) {
			insertString += "SOURCE_ID,";
			if (o.getSourceId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getSourceId()) + ",";

			}
		}
		if (o.getXrefName() != null) {
			insertString += "XREF_NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getXrefName()) + "',";
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

	public void updateCampaignBlackList(CampaignBlackList o) {
		String updateString = "update CAMPAIGN_BLACK_LIST set ";
		if (o.getCampaignId() != null) {
			if (!o.getCampaignId().equals("")) {
				updateString += "CAMPAIGN_ID="
						+ dbs.escapeQuotes(o.getCampaignId()) + ",";
			} else {
				updateString += "CAMPAIGN_ID=null,";
			}
		}
		if (o.getType() != null) {
			updateString += "TYPE='" + dbs.escapeQuotes(o.getType()) + "',";
		}
		if (o.getXrefId() != null) {
			if (!o.getXrefId().equals("")) {
				updateString += "XREF_ID=" + dbs.escapeQuotes(o.getXrefId())
						+ ",";
			} else {
				updateString += "XREF_ID=null,";
			}
		}
		if (o.getSourceId() != null) {
			if (!o.getSourceId().equals("")) {
				updateString += "SOURCE_ID="
						+ dbs.escapeQuotes(o.getSourceId()) + ",";
			} else {
				updateString += "SOURCE_ID=null,";
			}
		}
		if (o.getXrefName() != null) {
			updateString += "XREF_NAME='" + dbs.escapeQuotes(o.getXrefName()) + "',";
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
	
	public Vector<CampaignBlackList> getCampaignBlackList(String campaignId){
		CampaignBlackList cwl = new CampaignBlackList();
		cwl.setCampaignId(campaignId);
		return selectCampaignBlackList(cwl);
	}

	public Vector<CampaignBlackList> selectCampaignBlackList(CampaignBlackList o) {
		String selectString = "SELECT * FROM CAMPAIGN_BLACK_LIST  ";
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
		if (o.getType() != null && !o.getType().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " TYPE = '" + dbs.escapeQuotes(o.getType()) + "' ";
		}
		if (o.getXrefId() != null && !o.getXrefId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " XREF_ID = " + dbs.escapeQuotes(o.getXrefId())
					+ " ";
		}
		if (o.getSourceId() != null && !o.getSourceId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SOURCE_ID = "
					+ dbs.escapeQuotes(o.getSourceId()) + " ";
		}
		Vector<CampaignBlackList> v = new Vector<CampaignBlackList>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				CampaignBlackList obj = new CampaignBlackList();

				obj.setId(result.getString("ID"));
				obj.setCampaignId(result.getString("CAMPAIGN_ID"));
				obj.setType(result.getString("TYPE"));
				obj.setXrefId(result.getString("XREF_ID"));
				obj.setSourceId(result.getString("SOURCE_ID"));
				obj.setXrefName(result.getString("XREF_NAME"));
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

	public void deleteCampaignBlackList(CampaignBlackList o) {
		String deleteString = "delete from CAMPAIGN_BLACK_LIST  ";
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
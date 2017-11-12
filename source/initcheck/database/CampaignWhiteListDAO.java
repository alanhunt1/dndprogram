package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class CampaignWhiteListDAO extends InitBaseDAO {

	public CampaignWhiteListDAO() {

	}
	public CampaignWhiteListDAO(DBSession dbs2) {
		this.dbs2 = dbs2;
	}
	
	public int addOrUpdateCampaignWhiteList(CampaignWhiteList o) {
		int i = -1;
		if (o.getId() != null) {
			updateCampaignWhiteList(o);
		} else {
			addCampaignWhiteList(o);
		}
		try {
			// open the connection
			dbs.open();
			String command = "SELECT MAX(ID) as MAXID FROM CAMPAIGN_WHITE_LIST";
			
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

	public void addCampaignWhiteList(CampaignWhiteList o) {
		String valueString = "";
		String insertString = "INSERT INTO CAMPAIGN_WHITE_LIST (";
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

	public void updateCampaignWhiteList(CampaignWhiteList o) {
		String updateString = "update CAMPAIGN_WHITE_LIST set ";
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

	public Vector<CampaignWhiteList> getCampaignWhiteList(String campaignId){
		CampaignWhiteList cwl = new CampaignWhiteList();
		cwl.setCampaignId(campaignId);
		return selectCampaignWhiteList(cwl);
	}
	
	public Vector<CampaignWhiteList> selectCampaignWhiteList(CampaignWhiteList o) {
		String selectString = "SELECT * FROM CAMPAIGN_WHITE_LIST  ";
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
		Vector<CampaignWhiteList> v = new Vector<CampaignWhiteList>();
		try {
			ResultSet result;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}
			while (result.next()) {
				CampaignWhiteList obj = new CampaignWhiteList();

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
			if (dbs2 == null) {
				dbs.close();
			} else {
				dbs2.cleanup();
			}
		}
		return v;
	}

	public void deleteCampaignWhiteList(CampaignWhiteList o) {
		String deleteString = "delete from CAMPAIGN_WHITE_LIST  ";
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
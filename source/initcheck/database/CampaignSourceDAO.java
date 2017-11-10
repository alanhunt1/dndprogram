package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class CampaignSourceDAO extends InitBaseDAO {

	public CampaignSourceDAO() {

	}

	public int addOrUpdateCampaignSource(CampaignSource o) {
		int i = -1;
		if (o.getId() != null) {
			updateCampaignSource(o);
		} else {
			addCampaignSource(o);
		}
		try {
			// open the connection
			dbs.open();
			String command = "SELECT MAX(ID) as MAXID FROM CAMPAIGN_SOURCES";
			
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

	public void addCampaignSource(CampaignSource o) {
		String valueString = "";
		String insertString = "INSERT INTO CAMPAIGN_SOURCES (";

		if (o.getCampaignId() != null) {
			insertString += "campaign_id,";
			if (o.getCampaignId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getCampaignId()) + ",";

			}
		}
		if (o.getSourceId() != null) {
			insertString += "source_id,";
			if (o.getSourceId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getSourceId()) + ",";

			}
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

	
	
	public void updateCampaignSource(CampaignSource o) {
		String updateString = "update CAMPAIGN_SOURCES set ";
		if (o.getCampaignId() != null) {
			if (!o.getCampaignId().equals("")) {
				updateString += "campaign_id="
						+ dbs.escapeQuotes(o.getCampaignId()) + ",";
			} else {
				updateString += "campaign_id=null,";
			}
		}
		if (o.getSourceId() != null) {
			if (!o.getSourceId().equals("")) {
				updateString += "source_id="
						+ dbs.escapeQuotes(o.getSourceId()) + ",";
			} else {
				updateString += "source_id=null,";
			}
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE id = " + dbs.escapeQuotes(o.getId()) + " ";
		try {
			dbs.open();
			dbs.executeSQLCommand(updateString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}

	public Vector<CampaignSource> getCampaignSources(String campaignId) {
		CampaignSource cs = new CampaignSource();
		cs.setCampaignId(campaignId);
		return selectCampaignSource(cs);
	}
	
	public Vector<CampaignSource> selectCampaignSource(CampaignSource o) {
		String selectString = "SELECT CS.*, MS.SOURCE_NAME, MS.DND_VERSION FROM CAMPAIGN_SOURCES CS, "+
		"MATERIAL_SOURCE MS "+
		 "WHERE CS.SOURCE_ID = MS.SOURCE_ID";
		
		boolean first = false;
		
		if (o.getId() != null && !o.getId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " CS.id = " + dbs.escapeQuotes(o.getId()) + "";
		}
		if (o.getCampaignId() != null && !o.getCampaignId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " CS.campaign_id = "
					+ dbs.escapeQuotes(o.getCampaignId()) + " ";
		}
		if (o.getSourceId() != null && !o.getSourceId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " CS.source_id = "
					+ dbs.escapeQuotes(o.getSourceId()) + " ";
		}
		Vector<CampaignSource> v = new Vector<CampaignSource>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				CampaignSource obj = new CampaignSource();

				obj.setId(result.getString("id"));
				obj.setCampaignId(result.getString("campaign_id"));
				obj.setSourceId(result.getString("source_id"));
				obj.setName(result.getString("SOURCE_NAME")+" ("+result.getString("DND_VERSION")+")");
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

	public void clear(String campaignId){
		String deleteString = "delete from CAMPAIGN_SOURCES  ";
		deleteString += " WHERE campaign_id = " + dbs.escapeQuotes(campaignId) + " ";

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}
	
	public void deleteCampaignSource(CampaignSource o) {
		String deleteString = "delete from CAMPAIGN_SOURCES  ";
		deleteString += " WHERE id = " + dbs.escapeQuotes(o.getId()) + " ";

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
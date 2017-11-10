package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class CampaignJournalsDAO extends InitBaseDAO {

	public CampaignJournalsDAO() {

	}

	public int addOrUpdateCampaignJournals(CampaignJournals o) {
		int i = -1;
		if (o.getJournalId() != null) {
			System.out.println("UPdating Journal");
			updateCampaignJournals(o);
		} else {
			System.out.println("Adding Journal");
			addCampaignJournals(o);
		}
		try {
			// open the connection
			dbs.open();
			String command = "SELECT MAX(JOURNAL_ID) as MAXID FROM CAMPAIGN_JOURNALS";
			
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

	public void addCampaignJournals(CampaignJournals o) {
		String valueString = "";
		String insertString = "INSERT INTO CAMPAIGN_JOURNALS (";
		
		if (o.getCampaignId() != null) {
			insertString += "CAMPAIGN_ID,";
			if (o.getCampaignId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getCampaignId()) + ",";

			}
		}
		if (o.getPartyId() != null) {
			insertString += "PARTY_ID,";
			if (o.getPartyId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getPartyId()) + ",";

			}
		}
		if (o.getPlayerId() != null) {
			insertString += "PLAYER_ID,";
			if (o.getPlayerId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getPlayerId()) + ",";

			}
		}
		if (o.getJournalText() != null) {
			insertString += "JOURNAL_TEXT,";
			valueString += "'" + dbs.escapeQuotes(o.getJournalText()) + "',";
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

	public void updateCampaignJournals(CampaignJournals o) {
		String updateString = "update CAMPAIGN_JOURNALS set ";
		if (o.getCampaignId() != null) {
			if (!o.getCampaignId().equals("")) {
				updateString += "CAMPAIGN_ID="
						+ dbs.escapeQuotes(o.getCampaignId()) + ",";
			} else {
				updateString += "CAMPAIGN_ID=null,";
			}
		}
		if (o.getPartyId() != null) {
			if (!o.getPartyId().equals("")) {
				updateString += "PARTY_ID=" + dbs.escapeQuotes(o.getPartyId())
						+ ",";
			} else {
				updateString += "PARTY_ID=null,";
			}
		}
		if (o.getPlayerId() != null) {
			if (!o.getPlayerId().equals("")) {
				updateString += "PLAYER_ID="
						+ dbs.escapeQuotes(o.getPlayerId()) + ",";
			} else {
				updateString += "PLAYER_ID=null,";
			}
		}
		if (o.getJournalText() != null) {
			updateString += "JOURNAL_TEXT='"
					+ dbs.escapeQuotes(o.getJournalText()) + "',";
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE JOURNAL_ID = "
				+ dbs.escapeQuotes(o.getJournalId()) + "  ";
		try {
			dbs.open();
			dbs.executeSQLCommand(updateString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}

	public CampaignJournals getCampaignJournals(String playerId){
		String selectString = "SELECT cj.*, p.NAME, pr.PARTY_NAME FROM CAMPAIGN_JOURNALS cj, PLAYER p, PARTY pr  "
			+" WHERE cj.player_id = p.id AND cj.party_ID = pr.id AND cj.PLAYER_ID = "+playerId;
		CampaignJournals obj = new CampaignJournals();	
		try {
			dbs.open();
			logger.log("Running query "+selectString);
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				

				obj.setJournalId(result.getString("JOURNAL_ID"));
				obj.setCampaignId(result.getString("CAMPAIGN_ID"));
				obj.setPartyId(result.getString("PARTY_ID"));
				obj.setPlayerId(result.getString("PLAYER_ID"));
				obj.setJournalText(result.getString("JOURNAL_TEXT"));
				obj.setPlayerName(result.getString("NAME"));
				obj.setPartyName(result.getString("PARTY_NAME"));
				
			}
		} catch (SQLException sqle) {
			logger.log("error", sqle.toString());
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
		return obj;
	}
	
	public Vector<CampaignJournals> selectCampaignJournals(CampaignJournals o) {
		String selectString = "SELECT cj.*, p.NAME, pr.PARTY_NAME FROM CAMPAIGN_JOURNALS cj, PLAYER p, PARTY pr  "
			+" WHERE cj.player_id = p.id AND cj.party_ID = pr.id";
		boolean first = false;
		if (o.getJournalId() != null && !o.getJournalId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " JOURNAL_ID = "
					+ dbs.escapeQuotes(o.getJournalId()) + " ";
		}
		if (o.getCampaignId() != null && !o.getCampaignId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " cj.CAMPAIGN_ID = "
					+ dbs.escapeQuotes(o.getCampaignId()) + " ";
		}
		if (o.getPartyId() != null && !o.getPartyId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " cj.PARTY_ID = " + dbs.escapeQuotes(o.getPartyId())
					+ " ";
		}
		if (o.getPlayerId() != null && !o.getPlayerId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " cj.PLAYER_ID = "
					+ dbs.escapeQuotes(o.getPlayerId()) + " ";
		}
		
		Vector<CampaignJournals> v = new Vector<CampaignJournals>();
		try {
			dbs.open();
			logger.log("Running query "+selectString);
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				CampaignJournals obj = new CampaignJournals();

				obj.setJournalId(result.getString("JOURNAL_ID"));
				obj.setCampaignId(result.getString("CAMPAIGN_ID"));
				obj.setPartyId(result.getString("PARTY_ID"));
				obj.setPlayerId(result.getString("PLAYER_ID"));
				obj.setJournalText(result.getString("JOURNAL_TEXT"));
				obj.setPlayerName(result.getString("NAME"));
				obj.setPartyName(result.getString("PARTY_NAME"));
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

	public void deleteCampaignJournals(CampaignJournals o) {
		String deleteString = "delete from CAMPAIGN_JOURNALS  ";
		deleteString += " WHERE JOURNAL_ID = "
				+ dbs.escapeQuotes(o.getJournalId()) + " ";

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
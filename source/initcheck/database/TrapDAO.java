package initcheck.database;

import initcheck.character.library.LibraryItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TrapDAO extends InitBaseDAO implements LibraryItemDAO {

	public TrapDAO() {

	}

	public int addOrUpdateTrap(Trap o) {
		int i = -1;
		
		if (o.getTrapId() != null) {
			updateTrap(o);
			i = Integer.parseInt(o.getTrapId());
		} else {
			addTrap(o);
			try {
				// open the connection
				dbs.open();
				String command = "SELECT MAX(TRAP_ID) AS MAXID FROM TRAP_TABLE";
				
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
	
	public void addTrap(Trap o) {
		String valueString = "";
		String insertString = "INSERT INTO TRAP_TABLE (";
		if (o.getTrapId() != null) {
			insertString += "TRAP_ID,";
			valueString += "'" + dbs.escapeQuotes(o.getTrapId()) + "',";
		}
		if (o.getTrapName() != null) {
			insertString += "TRAP_NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getTrapName()) + "',";
		}
		if (o.getSpotDc() != null) {
			insertString += "SPOT_DC,";
			if (o.getSpotDc().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getSpotDc()) + ",";

			}
		}
		if (o.getTrapDescription() != null) {
			insertString += "TRAP_DESCRIPTION,";
			valueString += "'" + dbs.escapeQuotes(o.getTrapDescription())
					+ "',";
		}
		if (o.getDisableDc() != null) {
			insertString += "DISABLE_DC,";
			if (o.getDisableDc().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getDisableDc()) + ",";

			}
		}
		if (o.getCr() != null) {
			insertString += "CR,";
			if (o.getCr().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getCr()) + ",";

			}
		}
		if (o.getTrapType() != null) {
			insertString += "TRAP_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getTrapType()) + "',";
		}
		if (o.getTriggerType() != null) {
			insertString += "TRIGGER_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getTriggerType()) + "',";
		}
		if (o.getResetType() != null) {
			insertString += "RESET_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getResetType()) + "',";
		}
		
		insertString += "BYPASS,";
		valueString += o.isBypass()+",";
		
		if (o.getBypassDetectDc() != null) {
			insertString += "BYPASS_DETECT_DC,";
			if (o.getBypassDetectDc().equals("")) {
				valueString += "null,";
			} else {
		
				valueString += "" + dbs.escapeQuotes(o.getBypassDetectDc()) + ",";
		
			}
		}
		if (o.getBypassUseDc() != null) {
			insertString += "BYPASS_USE_DC,";
		
			if (o.getBypassUseDc().equals("")) {
				valueString += "null,";
			} else {
			
			valueString += "" + dbs.escapeQuotes(o.getBypassUseDc()) + ",";
			}
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

	
	
	public void updateTrap(Trap o) {
		String updateString = "update TRAP_TABLE set ";
		if (o.getTrapName() != null) {
			updateString += "TRAP_NAME='" + dbs.escapeQuotes(o.getTrapName())
					+ "',";
		}
		if (o.getSpotDc() != null) {
			if (!o.getSpotDc().equals("")) {
				updateString += "SPOT_DC=" + dbs.escapeQuotes(o.getSpotDc())
						+ ",";
			} else {
				updateString += "SPOT_DC=null,";
			}
		}
		if (o.getTrapDescription() != null) {
			updateString += "TRAP_DESCRIPTION='"
					+ dbs.escapeQuotes(o.getTrapDescription()) + "',";
		}
		if (o.getDisableDc() != null) {
			if (!o.getDisableDc().equals("")) {
				updateString += "DISABLE_DC="
						+ dbs.escapeQuotes(o.getDisableDc()) + ",";
			} else {
				updateString += "DISABLE_DC=null,";
			}
		}
		if (o.getCr() != null) {
			if (!o.getCr().equals("")) {
				updateString += "CR=" + dbs.escapeQuotes(o.getCr()) + ",";
			} else {
				updateString += "CR=null,";
			}
		}
		if (o.getTrapType() != null) {
			updateString += "TRAP_TYPE='" + dbs.escapeQuotes(o.getTrapType())
					+ "',";
		}
		if (o.getTriggerType() != null) {
			updateString += "TRIGGER_TYPE='"
					+ dbs.escapeQuotes(o.getTriggerType()) + "',";
		}
		if (o.getResetType() != null) {
			updateString += "RESET_TYPE='" + dbs.escapeQuotes(o.getResetType())
					+ "',";
		}
		
		if (o.isBypass()){
			updateString += "BYPASS=true,";
		}else{
			updateString += "BYPASS=false,";
		}
		
		if (o.getBypassDetectDc() != null) {
			if (!o.getBypassDetectDc().equals("")) {
				updateString += "BYPASS_DETECT_DC=" + dbs.escapeQuotes(o.getBypassDetectDc()) + ",";
			} else {
				updateString += "BYPASS_DETECT_DC=null,";
			}
		}
		
		if (o.getBypassUseDc() != null) {
			if (!o.getBypassUseDc().equals("")) {
				updateString += "BYPASS_USE_DC=" + dbs.escapeQuotes(o.getBypassDetectDc()) + ",";
			} else {
				updateString += "BYPASS_USE_DC=null,";
			}
		}
		if (o.getSource() != null) {
			updateString += "SOURCE='" + dbs.escapeQuotes(o.getSource())
					+ "',";
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE TRAP_ID = " + dbs.escapeQuotes(o.getTrapId())
				+ "  ";
		try {
			dbs.open();
		
			dbs.executeSQLCommand(updateString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}

	public Vector<Trap> getTraps(){
		return selectTrap(new Trap());
	}
	
	public Vector<LibraryItem> getItems(LibraryItem i){
		String queryString = "SELECT * FROM TRAP_TABLE WHERE SOURCE = '"+i.getSource()+"' "+
		" ORDER BY TRAP_NAME ";
		Vector<LibraryItem> results = new Vector<LibraryItem>();
		results.addAll(getTrapsFromSql(queryString));
		return results;
	}
	
	public Vector<Trap> getTraps(String keyword) {
		String selectString = "SELECT * FROM TRAP_TABLE WHERE TRAP_NAME " + "LIKE '%"
				+ keyword + "%' OR TRAP_DESCRIPTION LIKE '%" + keyword
				+ "%' ORDER BY TRAP_NAME";

		return getTrapsFromSql(selectString);
	}

	public Vector<Trap> getTraps (String keyword, Campaign c){
		
		String queryString = "SELECT * FROM TRAP_TABLE	 ";
		boolean first = true;
		if (keyword != null && !keyword.equals("")) {
			queryString += "WHERE TRAP_DESCRIPTION like '%" + keyword
					+ "%' OR TRAP_NAME LIKE '%" + keyword + "%'";
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
		c.getId()+ " AND XREF_ID = TRAP_TABLE.TRAP_ID AND TYPE = 'Trap')";
			
			queryString += ") ";
		}
		queryString += " AND NOT EXISTS (SELECT 'x' FROM CAMPAIGN_BLACK_LIST WHERE CAMPAIGN_ID = "+
		c.getId()+ " AND XREF_ID = TRAP_TABLE.TRAP_ID AND TYPE = 'Trap')";
		queryString += " ORDER BY TRAP_NAME";
		
		return getTrapsFromSql(queryString);
		
	}
	
	public Vector<Trap> selectTrap(Trap o) {
		String selectString = "SELECT * FROM TRAP_TABLE  ";
		boolean first = true;
		if (o.getTrapId() != null && !o.getTrapId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " TRAP_ID = '" + dbs.escapeQuotes(o.getTrapId())
					+ "' ";
		}
		if (o.getTrapName() != null && !o.getTrapName().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " TRAP_NAME = '"
					+ dbs.escapeQuotes(o.getTrapName()) + "' ";
		}
		if (o.getSpotDc() != null && !o.getSpotDc().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SPOT_DC = '" + dbs.escapeQuotes(o.getSpotDc())
					+ "' ";
		}
		if (o.getTrapDescription() != null
				&& !o.getTrapDescription().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " TRAP_DESCRIPTION = '"
					+ dbs.escapeQuotes(o.getTrapDescription()) + "' ";
		}
		if (o.getDisableDc() != null && !o.getDisableDc().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " DISABLE_DC = '"
					+ dbs.escapeQuotes(o.getDisableDc()) + "' ";
		}
		if (o.getCr() != null && !o.getCr().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " CR = '" + dbs.escapeQuotes(o.getCr()) + "' ";
		}
		if (o.getTrapType() != null && !o.getTrapType().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " TRAP_TYPE = '"
					+ dbs.escapeQuotes(o.getTrapType()) + "' ";
		}
		if (o.getTriggerType() != null && !o.getTriggerType().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " TRIGGER_TYPE = '"
					+ dbs.escapeQuotes(o.getTriggerType()) + "' ";
		}
		if (o.getResetType() != null && !o.getResetType().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " RESET_TYPE = '"
					+ dbs.escapeQuotes(o.getResetType()) + "' ";
		}
		return getTrapsFromSql(selectString);
	}
	
	public Vector<Trap> getTrapsFromSql(String selectString){
		Vector<Trap> v = new Vector<Trap>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				Trap obj = new Trap();

				obj.setTrapId(result.getString("TRAP_ID"));
				obj.setTrapName(result.getString("TRAP_NAME"));
				obj.setSpotDc(result.getString("SPOT_DC"));
				obj.setTrapDescription(result.getString("TRAP_DESCRIPTION"));
				obj.setDisableDc(result.getString("DISABLE_DC"));
				obj.setCr(result.getString("CR"));
				obj.setTrapType(result.getString("TRAP_TYPE"));
				obj.setTriggerType(result.getString("TRIGGER_TYPE"));
				obj.setResetType(result.getString("RESET_TYPE"));
				obj.setBypass(result.getBoolean("BYPASS"));
				obj.setBypassDetectDc(result.getString("BYPASS_DETECT_DC"));
				obj.setBypassUseDc(result.getString("BYPASS_USE_DC"));
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

	public void deleteTrap(Trap o) {
		String deleteString = "delete from TRAP_TABLE  ";
		deleteString += " WHERE TRAP_ID = " + dbs.escapeQuotes(o.getTrapId())
				+ " ";

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
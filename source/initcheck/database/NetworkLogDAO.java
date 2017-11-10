package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class NetworkLogDAO extends InitBaseDAO {

	public NetworkLogDAO() {

	}

	public int addOrUpdateNetworkLog(NetworkLog o) {
		int i = -1;
		if (o.getLogId() != null) {
			logger.log("Updating LOG");
			updateNetworkLog(o);
			i = Integer.parseInt(o.getLogId());
		} else {
			addNetworkLog(o);

			try {
				// open the connection
				dbs.open();
				String command = "SELECT MAX(LOG_ID) as MAXID FROM NETWORK_LOG";
				
				ResultSet result = dbs.executeSQLQuery(command);
				logger.log("GETTING ID");
				if (result.next()) {
					i = result.getInt("MAXID");
				}
				logger.log("GOT IT");
			} catch (Exception uhe) {
				logger.log("ERROR : " + uhe.toString());
			}
		}
		// close the connection
		dbs.close();
		return i;
	}

	public void addNetworkLog(NetworkLog o) {
		String valueString = "";
		String insertString = "INSERT INTO NETWORK_LOG (";
		if (o.getLogId() != null) {
			insertString += "LOG_ID,";
			valueString += "'" + dbs.escapeQuotes(o.getLogId()) + "',";
		}
		if (o.getOperation() != null) {
			insertString += "OPERATION,";
			valueString += "'" + dbs.escapeQuotes(o.getOperation()) + "',";
		}
		if (o.getStartTime() != null) {
			insertString += "START_TIME,";
			valueString += "'" + dbs.escapeQuotes(o.getStartTime()) + "',";
		}
		if (o.getEndTime() != null) {
			insertString += "END_TIME,";
			valueString += "'" + dbs.escapeQuotes(o.getEndTime()) + "',";
		}
		if (o.getSendTime() != null) {
			insertString += "SEND_TIME,";
			valueString += "'" + dbs.escapeQuotes(o.getSendTime()) + "',";
		}
		if (o.getLagTime() != null) {
			insertString += "LAG_TIME,";
			valueString += "'" + dbs.escapeQuotes(o.getLagTime()) + "',";
		}
		if (o.getTaskTime() != null) {
			insertString += "TASK_TIME,";
			valueString += "'" + dbs.escapeQuotes(o.getTaskTime()) + "',";
		}
		if (o.getMachineId() != null) {
			insertString += "MACHINE_ID,";
			valueString += "'" + dbs.escapeQuotes(o.getMachineId()) + "',";
		}
		if (o.getTaskLevel() != null) {
			insertString += "TASK_LEVEL,";
			if (o.getTaskLevel().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getTaskLevel()) + ",";

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

	public void updateNetworkLog(NetworkLog o) {
		String updateString = "update NETWORK_LOG set ";
		if (o.getOperation() != null) {
			updateString += "OPERATION='" + dbs.escapeQuotes(o.getOperation())
					+ "',";
		}
		if (o.getStartTime() != null) {
			updateString += "START_TIME='" + dbs.escapeQuotes(o.getStartTime())
					+ "',";
		}
		if (o.getEndTime() != null) {
			updateString += "END_TIME='" + dbs.escapeQuotes(o.getEndTime())
					+ "',";
		}
		if (o.getSendTime() != null) {
			updateString += "SEND_TIME='" + dbs.escapeQuotes(o.getSendTime())
					+ "',";
		}
		if (o.getLagTime() != null) {
			updateString += "LAG_TIME='" + dbs.escapeQuotes(o.getLagTime())
					+ "',";
		}
		if (o.getTaskTime() != null) {
			updateString += "TASK_TIME='" + dbs.escapeQuotes(o.getTaskTime())
					+ "',";
		}
		if (o.getMachineId() != null) {
			updateString += "MACHINE_ID='" + dbs.escapeQuotes(o.getMachineId())
					+ "',";
		}
		if (o.getTaskLevel() != null) {
			if (!o.getTaskLevel().equals("")) {
				updateString += "TASK_LEVEL="
						+ dbs.escapeQuotes(o.getTaskLevel()) + ",";
			} else {
				updateString += "TASK_LEVEL=null,";
			}
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE LOG_ID = " + dbs.escapeQuotes(o.getLogId())
				+ "  ";
		try {
			dbs.open();
			logger.log("Executing Update" + updateString);
			dbs.executeSQLCommand(updateString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}

	public Vector<NetworkLog> selectNetworkLog(NetworkLog o) {
		String selectString = "SELECT * FROM NETWORK_LOG  ";
		boolean first = true;
		if (o.getLogId() != null && !o.getLogId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " LOG_ID = " + dbs.escapeQuotes(o.getLogId())
					+ " ";
		}
		if (o.getOperation() != null && !o.getOperation().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " OPERATION = '"
					+ dbs.escapeQuotes(o.getOperation()) + "' ";
		}
		if (o.getStartTime() != null && !o.getStartTime().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " START_TIME = '"
					+ dbs.escapeQuotes(o.getStartTime()) + "' ";
		}
		if (o.getEndTime() != null && !o.getEndTime().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " END_TIME = '" + dbs.escapeQuotes(o.getEndTime())
					+ "' ";
		}
		if (o.getMachineId() != null && !o.getMachineId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " MACHINE_ID = '"
					+ dbs.escapeQuotes(o.getMachineId()) + "' ";
		}
		if (o.getTaskLevel() != null && !o.getTaskLevel().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " TASK_LEVEL = '"
					+ dbs.escapeQuotes(o.getTaskLevel()) + "' ";
		}
		selectString += " ORDER BY START_TIME DESC";
		Vector<NetworkLog> v = new Vector<NetworkLog>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				NetworkLog obj = new NetworkLog();

				obj.setLogId(result.getString("LOG_ID"));
				obj.setOperation(result.getString("OPERATION"));
				obj.setStartTime(result.getString("START_TIME"));
				obj.setEndTime(result.getString("END_TIME"));
				obj.setMachineId(result.getString("MACHINE_ID"));
				obj.setTaskLevel(result.getString("TASK_LEVEL"));
				obj.setTaskTime(result.getString("TASK_TIME"));
				obj.setSendTime(result.getString("SEND_TIME"));
				obj.setLagTime(result.getString("LAG_TIME"));
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

	public void wipeLog() {
		String deleteString = "delete from NETWORK_LOG  ";
		

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}
	
	public void deleteNetworkLog(NetworkLog o) {
		String deleteString = "delete from NETWORK_LOG  ";
		deleteString += " WHERE LOG_ID = " + dbs.escapeQuotes(o.getLogId())
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
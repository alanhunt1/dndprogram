package initcheck.database;

import initcheck.character.library.LibraryItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ServicesDAO extends InitBaseDAO implements LibraryItemDAO {

	public ServicesDAO() {

	}

	public int addOrUpdateServices(Services o) {
		int i = -1;
		if (o.getServiceId() != null) {
			updateServices(o);
			i = Integer.parseInt(o.getServiceId());
		} else {
			addServices(o);
			try {
				// open the connection
				dbs.open();
				String command = "SELECT MAX(SERVICE_ID) MAXID FROM Services";
				
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
	
	public boolean exists(Services o) {
		return selectServices(o).size() > 0;
	}
	
	public void addServices(Services o) {
		String valueString = "";
		String insertString = "INSERT INTO SERVICES (";
		if (o.getServiceId() != null) {
			insertString += "SERVICE_ID,";
			valueString += "'" + dbs.escapeQuotes(o.getServiceId()) + "',";
		}
		if (o.getServiceName() != null) {
			insertString += "SERVICE_NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getServiceName()) + "',";
		}
		if (o.getServiceCost() != null) {
			insertString += "SERVICE_COST,";
			valueString += "'" + dbs.escapeQuotes(o.getServiceCost()) + "',";
		}
		if (o.getServiceDescription() != null) {
			insertString += "SERVICE_DESCRIPTION,";
			valueString += "'" + dbs.escapeQuotes(o.getServiceDescription())
					+ "',";
		}
		if (o.getServiceType() != null) {
			insertString += "SERVICE_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getServiceType()) + "',";
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

	public void updateServices(Services o) {
		String updateString = "update SERVICES set ";
		if (o.getServiceName() != null) {
			updateString += "SERVICE_NAME='"
					+ dbs.escapeQuotes(o.getServiceName()) + "',";
		}
		if (o.getServiceCost() != null) {
			updateString += "SERVICE_COST='"
					+ dbs.escapeQuotes(o.getServiceCost()) + "',";
		}
		if (o.getServiceDescription() != null) {
			updateString += "SERVICE_DESCRIPTION='"
					+ dbs.escapeQuotes(o.getServiceDescription()) + "',";
		}
		if (o.getServiceType() != null) {
			updateString += "SERVICE_TYPE='"
					+ dbs.escapeQuotes(o.getServiceType()) + "',";
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE SERVICE_ID = '"
				+ dbs.escapeQuotes(o.getServiceId()) + "'  ";
		try {
			dbs.open();
			dbs.executeSQLCommand(updateString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}

	public Vector<Services> getServices(){
		return selectServices(new Services());
	}
	
	public Vector<LibraryItem> getItems(LibraryItem i){
		String queryString = "SELECT * FROM SERVICES WHERE SOURCE = '"+i.getSource()+"' "+
		" ORDER BY SERVICE_NAME";
		Vector<LibraryItem> results = new Vector<LibraryItem>();
		results.addAll(getServicesFromSql(queryString));
		return results;
	}
	
	public Vector<Services> selectServices(Services o) {
		String selectString = "SELECT * FROM SERVICES  ";
		boolean first = true;
		if (o.getServiceId() != null && !o.getServiceId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SERVICE_ID = '"
					+ dbs.escapeQuotes(o.getServiceId()) + "' ";
		}
		if (o.getServiceName() != null && !o.getServiceName().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SERVICE_NAME = '"
					+ dbs.escapeQuotes(o.getServiceName()) + "' ";
		}
		if (o.getServiceCost() != null && !o.getServiceCost().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SERVICE_COST = '"
					+ dbs.escapeQuotes(o.getServiceCost()) + "' ";
		}
		if (o.getServiceDescription() != null
				&& !o.getServiceDescription().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SERVICE_DESCRIPTION = '"
					+ dbs.escapeQuotes(o.getServiceDescription()) + "' ";
		}
		if (o.getServiceType() != null && !o.getServiceType().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SERVICE_TYPE = '"
					+ dbs.escapeQuotes(o.getServiceType()) + "' ";
		}
		return getServicesFromSql(selectString);
	}
	
	public Vector<Services> getServicesFromSql(String selectString){
		Vector<Services> v = new Vector<Services>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				Services obj = new Services();

				obj.setServiceId(result.getString("SERVICE_ID"));
				obj.setServiceName(result.getString("SERVICE_NAME"));
				obj.setServiceCost(result.getString("SERVICE_COST"));
				obj.setServiceDescription(result
						.getString("SERVICE_DESCRIPTION"));
				obj.setServiceType(result.getString("SERVICE_TYPE"));
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

	public void deleteServices(Services o) {
		String deleteString = "delete from SERVICES  ";
		deleteString += " WHERE SERVICE_ID = '"
				+ dbs.escapeQuotes(o.getServiceId()) + "' ";

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
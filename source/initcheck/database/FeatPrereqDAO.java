package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class FeatPrereqDAO extends InitBaseDAO {

	public FeatPrereqDAO() {

	}

	public int addOrUpdateFeatPrereq(FeatPrereq f) {
		int i = -1;
		try {
			// open the connection
			dbs.open();
			String command = "";

			command = "INSERT INTO FEAT_PREREQ (PREREQ_TYPE, PREREQ_NAME, "
					+ "PREREQ_VAL, FEAT_ID, CHAINED, CHAINED_TO, CROSS_REF_ID) VALUES ('"
					+ dbs.escapeQuotes(f.getPrereqType()) + "'," + "'"
					+ dbs.escapeQuotes(f.getPrereqName()) + "','"
					+ f.getPrereqVal() + "','" + f.getFeatId() + "', '"
					+ f.getChained() + "'," + f.getChainedTo() + ","
					+ f.getCrossRefId() + ")";

			logger.log("EXECUTING COMMAND " + command);
			dbs.executeSQLCommand(command);

			command = "SELECT MAX(ID) AS MAXID FROM FEAT_PREREQ";
			logger.log("EXECUTING COMMAND " + command);
			ResultSet result = dbs.executeSQLQuery(command);
			if (result.next()) {
				i = result.getInt("MAXID");
				logger.log("ID IS " + i);
			} else {
				logger.log("NOTHING FOUND");
			}

			// if they were not chained, update the chained reference
			// to point to themselves, for ordering purposes.
			if (f.getChainedTo() == null || f.getChainedTo().equals("")){
				command = "UPDATE FEAT_PREREQ SET CHAINED_TO = "+i+" WHERE ID = "+i;
				logger.log("EXECUTING COMMAND " + command);
				dbs.executeSQLCommand(command);
			}
			
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		} finally {
			resetConnection();
		}

		// close the connection
		dbs.close();
		return i;
	}
	
	public Vector<FeatPrereq> getFeatPrereqs(String id) {
		Vector<FeatPrereq> v = new Vector<FeatPrereq>();
		try {
			// open the connection
			dbs.open();

			String queryString = "SELECT * FROM FEAT_PREREQ ";

			if (id != null) {
				queryString += " WHERE FEAT_ID = " + id;
			}

			queryString += " ORDER BY ID DESC";
			
			
			ResultSet result = dbs.executeSQLQuery(queryString);
			
			while (result.next()) {

				FeatPrereq obj = new FeatPrereq();
				obj.setId(result.getString("ID"));
				obj.setPrereqType(result.getString("PREREQ_TYPE"));
				obj.setPrereqName(result.getString("PREREQ_NAME"));
				obj.setPrereqVal(result.getString("PREREQ_VAL"));
				obj.setFeatId(result.getString("FEAT_ID"));
				obj.setChained(result.getString("CHAINED"));
				obj.setChainedTo(result.getString("CHAINED_TO"));
				obj.setCrossRefId(result.getString("CROSS_REF_ID"));
				v.add(obj);
				
			}

			dbs.close();

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		return v;
	}

	public void addFeatPrereq(FeatPrereq o) {
		String valueString = "";
		String insertString = "INSERT INTO FEAT_PREREQ (";
		
		if (o.getPrereqType() != null) {
			insertString += "PREREQ_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getPrereqType()) + "',";
		}
		if (o.getPrereqName() != null) {
			insertString += "PREREQ_NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getPrereqName()) + "',";
		}
		if (o.getPrereqVal() != null) {
			insertString += "PREREQ_VAL,";
			valueString += "'" + dbs.escapeQuotes(o.getPrereqVal()) + "',";
		}
		if (o.getFeatId() != null) {
			insertString += "FEAT_ID,";
			if (o.getFeatId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getFeatId()) + ",";

			}
		}
		if (o.getChained() != null) {
			insertString += "CHAINED,";
			valueString += "'" + dbs.escapeQuotes(o.getChained()) + "',";
		}
		if (o.getChainedTo() != null) {
			insertString += "CHAINED_TO,";
			if (o.getChainedTo().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getChainedTo()) + ",";

			}
		}
		if (o.getCrossRefId() != null) {
			insertString += "CROSS_REF_ID,";
			if (o.getCrossRefId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getCrossRefId()) + ",";

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

	public void updateFeatPrereq(FeatPrereq o) {
		String updateString = "update FEAT_PREREQ set ";
		if (o.getPrereqType() != null) {
			updateString += "PREREQ_TYPE='"
					+ dbs.escapeQuotes(o.getPrereqType()) + "',";
		}
		if (o.getPrereqName() != null) {
			updateString += "PREREQ_NAME='"
					+ dbs.escapeQuotes(o.getPrereqName()) + "',";
		}
		if (o.getPrereqVal() != null) {
			updateString += "PREREQ_VAL='" + dbs.escapeQuotes(o.getPrereqVal())
					+ "',";
		}
		if (o.getFeatId() != null) {
			if (!o.getFeatId().equals("")) {
				updateString += "FEAT_ID=" + dbs.escapeQuotes(o.getFeatId())
						+ ",";
			} else {
				updateString += "FEAT_ID=null,";
			}
		}
		if (o.getChained() != null) {
			updateString += "CHAINED='" + dbs.escapeQuotes(o.getChained())
					+ "',";
		}
		if (o.getChainedTo() != null) {
			if (!o.getChainedTo().equals("")) {
				updateString += "CHAINED_TO="
						+ dbs.escapeQuotes(o.getChainedTo()) + ",";
			} else {
				updateString += "CHAINED_TO=null,";
			}
		}
		if (o.getCrossRefId() != null) {
			if (!o.getCrossRefId().equals("")) {
				updateString += "CROSS_REF_ID="
						+ dbs.escapeQuotes(o.getCrossRefId()) + ",";
			} else {
				updateString += "CROSS_REF_ID=null,";
			}
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

	public Vector<FeatPrereq> selectFeatPrereq(FeatPrereq o) {
		String selectString = "SELECT * FROM FEAT_PREREQ  ";
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
		if (o.getPrereqType() != null && !o.getPrereqType().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " PREREQ_TYPE = '"
					+ dbs.escapeQuotes(o.getPrereqType()) + "' ";
		}
		if (o.getPrereqName() != null && !o.getPrereqName().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " PREREQ_NAME = '"
					+ dbs.escapeQuotes(o.getPrereqName()) + "' ";
		}
		if (o.getPrereqVal() != null && !o.getPrereqVal().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " PREREQ_VAL = '"
					+ dbs.escapeQuotes(o.getPrereqVal()) + "' ";
		}
		if (o.getFeatId() != null && !o.getFeatId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " FEAT_ID = '" + dbs.escapeQuotes(o.getFeatId())
					+ "' ";
		}
		if (o.getChained() != null && !o.getChained().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " CHAINED = '" + dbs.escapeQuotes(o.getChained())
					+ "' ";
		}
		if (o.getChainedTo() != null && !o.getChainedTo().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " CHAINED_TO = '"
					+ dbs.escapeQuotes(o.getChainedTo()) + "' ";
		}
		if (o.getCrossRefId() != null && !o.getCrossRefId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " CROSS_REF_ID = '"
					+ dbs.escapeQuotes(o.getCrossRefId()) + "' ";
		}
		Vector<FeatPrereq> v = new Vector<FeatPrereq>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				FeatPrereq obj = new FeatPrereq();

				obj.setId(result.getString("ID"));
				obj.setPrereqType(result.getString("PREREQ_TYPE"));
				obj.setPrereqName(result.getString("PREREQ_NAME"));
				obj.setPrereqVal(result.getString("PREREQ_VAL"));
				obj.setFeatId(result.getString("FEAT_ID"));
				obj.setChained(result.getString("CHAINED"));
				obj.setChainedTo(result.getString("CHAINED_TO"));
				obj.setCrossRefId(result.getString("CROSS_REF_ID"));
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

	public void clearPrereqs(String featId) {
		try {
			// open the connection
			dbs.open();
			String command = "DELETE FROM FEAT_PREREQ WHERE FEAT_ID=" + featId;

			
			dbs.executeSQLCommand(command);
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}

		// close the connection
		dbs.close();
	}
	
	public void deleteFeatPrereq(FeatPrereq o) {
		String deleteString = "delete from FEAT_PREREQ  ";
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
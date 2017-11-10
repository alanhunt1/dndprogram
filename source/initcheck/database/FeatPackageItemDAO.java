package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class FeatPackageItemDAO extends InitBaseDAO {

	public FeatPackageItemDAO() {

	}

	public void addFeatPackage(FeatPackageItem o) {
		String valueString = "";
		String insertString = "INSERT INTO FEAT_PACKAGE_ITEM (";

		if (o.getFeatId() != null) {
			insertString += "FEAT_ID,";
			if (o.getFeatId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getFeatId()) + ",";

			}
		}
		if (o.getPackageId() != null) {
			insertString += "PACKAGE_ID,";
			valueString += "" + dbs.escapeQuotes(o.getPackageId()) + ",";
		}
		if (o.getFeatOrder() != null) {
			insertString += "FEAT_ORDER,";
			if (o.getFeatOrder().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getFeatOrder()) + ",";

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
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		}
	}

	public void updateFeatPackage(FeatPackageItem o) {
		String updateString = "update FEAT_PACKAGE_ITEM set ";
		if (o.getFeatId() != null) {
			if (!o.getFeatId().equals("")) {
				updateString += "FEAT_ID=" + dbs.escapeQuotes(o.getFeatId())
						+ ",";
			} else {
				updateString += "FEAT_ID=null,";
			}
		}
		if (o.getPackageId() != null) {
			if (!o.getPackageId().equals("")) {
				updateString += "PACKAGE_ID="
						+ dbs.escapeQuotes(o.getPackageId()) + ",";
			} else {
				updateString += "PACKAGE_ID=null,";
			}
		}
		if (o.getFeatOrder() != null) {
			if (!o.getFeatOrder().equals("")) {
				updateString += "FEAT_ORDER="
						+ dbs.escapeQuotes(o.getFeatOrder()) + "";
			} else {
				updateString += "FEAT_ORDER=null";
			}
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + " ";
		try {
			dbs.open();
			dbs.executeSQLCommand(updateString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		}
	}

	public Vector<FeatPackageItem> getPackageFeats(String packageId) {
		if (packageId == null) {
			return new Vector<FeatPackageItem>();
		}
		FeatPackageItem fpi = new FeatPackageItem();
		fpi.setPackageId(packageId);
		return selectFeatPackage(fpi);
	}

	public Vector<FeatPackageItem> selectFeatPackage(FeatPackageItem o) {
		String selectString = "SELECT fpi.*, f.feat_name FROM FEAT_PACKAGE_ITEM fpi, FEATS f WHERE fpi.feat_id = f.id ";
		boolean first = false;
		if (o.getId() != null && !o.getId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ID = " + dbs.escapeQuotes(o.getId()) + "";
		}
		if (o.getFeatId() != null && !o.getFeatId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " FEAT_ID = " + dbs.escapeQuotes(o.getFeatId())
					+ " ";
		}
		if (o.getPackageId() != null && !o.getPackageId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " PACKAGE_ID = "
					+ dbs.escapeQuotes(o.getPackageId()) + " ";
		}
		if (o.getFeatOrder() != null && !o.getFeatOrder().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " FEAT_ORDER = "
					+ dbs.escapeQuotes(o.getFeatOrder()) + " ";
		}

		Vector<FeatPackageItem> v = new Vector<FeatPackageItem>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				FeatPackageItem obj = new FeatPackageItem();
				obj.setFeatName(result.getString("FEAT_NAME"));
				obj.setId(result.getString("ID"));
				obj.setFeatId(result.getString("FEAT_ID"));
				obj.setPackageId(result.getString("PACKAGE_ID"));
				obj.setFeatOrder(result.getString("FEAT_ORDER"));
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

	public void deleteFeatPackage(FeatPackage o) {
		String deleteString = "delete from FEAT_PACKAGE_ITEM  ";
		deleteString += " WHERE PACKAGE_ID = " + dbs.escapeQuotes(o.getId())
				+ " ";

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		}
	}

	public void deleteFeatPackage(FeatPackageItem o) {
		String deleteString = "delete from FEAT_PACKAGE_ITEM  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + " ";

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		}
	}
}

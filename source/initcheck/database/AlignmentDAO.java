package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class AlignmentDAO extends InitBaseDAO {

	public AlignmentDAO() {

	}

	public Vector<Alignment> selectAlignment(Alignment o) {
		String selectString = "SELECT * FROM ALIGNMENT  ";
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
		if (o.getAlignment() != null && !o.getAlignment().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " Alignment = '"
					+ dbs.escapeQuotes(o.getAlignment()) + "' ";
		}
		if (o.getDescription() != null && !o.getDescription().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " Description = '"
					+ dbs.escapeQuotes(o.getDescription()) + "' ";
		}
		Vector<Alignment> v = new Vector<Alignment>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				Alignment obj = new Alignment();

				obj.setId(result.getString("ID"));
				obj.setAlignment(result.getString("Alignment"));
				obj.setDescription(result.getString("Description"));
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
}
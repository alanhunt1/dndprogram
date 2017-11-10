package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

public class RoomDescriptionDAO extends InitBaseDAO {

	static HashMap<String, Vector<RoomDescription>> descHash = new HashMap<String, Vector<RoomDescription>>();

	private Random r = new Random();

	public RoomDescriptionDAO() {

	}

	public void addRoomDescription(RoomDescription o) {
		String valueString = "";
		String insertString = "INSERT INTO ROOM_DESCRIPTION (";

		if (o.getDescType() != null) {
			insertString += "DESC_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getDescType()) + "',";
		}
		if (o.getDescription() != null) {
			insertString += "DESCRIPTION,";
			valueString += "'" + dbs.escapeQuotes(o.getDescription()) + "',";
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
			descHash = new HashMap<String, Vector<RoomDescription>>();
			dbs.close();
		}
	}

	@SuppressWarnings("unchecked")
	public String getRandomDescription(String type) {
		String desc = "";
		// see if the descriptions have already been loaded for this type
		Vector descVector = (Vector) descHash.get(type);

		// if so, pull a random description
		if (descVector != null && descVector.size() > 0) {
			int idx = getRandom(descVector.size() - 1);
			desc = ((RoomDescription) descVector.get(idx)).getDescription();

			// remove the description from the vector so it doesn't get
			// reused too often in the dungeon.
			descVector.remove(idx);

		} else {
			RoomDescription rd = new RoomDescription();
			rd.setDescType(type);
			Vector v = selectRoomDescription(rd);
			if (v.size() > 0) {

				int idx = getRandom(v.size() - 1);
				desc = ((RoomDescription) v.get(idx)).getDescription();

				// remove the description from the vector so it doesn't get
				// reused too often in the dungeon.
				v.remove(idx);
				descHash.put(type, v);
			}
		}
		return desc;
	}

	private int getRandom(int i) {
		if (i < 1){
			i = 1;
		}
		
		int j = r.nextInt(i);
		if (j == 0) {
			return 1;
		}
		return j + 1;
	}

	public void updateRoomDescription(RoomDescription o) {
		String updateString = "update ROOM_DESCRIPTION set ";
		if (o.getDescType() != null) {
			updateString += "DESC_TYPE='" + dbs.escapeQuotes(o.getDescType())
					+ "',";
		}
		if (o.getDescription() != null) {
			updateString += "DESCRIPTION='"
					+ dbs.escapeQuotes(o.getDescription()) + "'";
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + " ";
		try {
			dbs.open();
			dbs.executeSQLCommand(updateString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			descHash = new HashMap<String, Vector<RoomDescription>>();
			dbs.close();
		}
	}

	public Vector<RoomDescription> selectRoomDescription(RoomDescription o) {
		String selectString = "SELECT * FROM ROOM_DESCRIPTION  ";
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
		if (o.getDescType() != null && !o.getDescType().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " DESC_TYPE = '"
					+ dbs.escapeQuotes(o.getDescType()) + "' ";
		}
		if (o.getDescription() != null && !o.getDescription().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " DESCRIPTION = '"
					+ dbs.escapeQuotes(o.getDescription()) + "' ";
		}
		Vector<RoomDescription> v = new Vector<RoomDescription>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				RoomDescription obj = new RoomDescription();

				obj.setId(result.getString("ID"));
				obj.setDescType(result.getString("DESC_TYPE"));
				obj.setDescription(result.getString("DESCRIPTION"));
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

	public void deleteRoomDescription(RoomDescription o) {
		String deleteString = "delete from ROOM_DESCRIPTION  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + " ";

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			descHash = new HashMap<String, Vector<RoomDescription>>();
			dbs.close();
		}
	}
}

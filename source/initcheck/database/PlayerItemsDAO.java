package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PlayerItemsDAO extends InitBaseDAO {

	public PlayerItemsDAO() {

	}

	public PlayerItemsDAO(DBSession dbs2) {
		this.dbs2 = dbs2;
	}

	public int addPlayerItems(PlayerItems o) {
		int i = -1;
		String valueString = "";
		String insertString = "INSERT INTO PLAYER_ITEMS (";

		if (o.getPlayerId() != null) {
			insertString += "PLAYER_ID,";
			valueString += dbs.escapeQuotes(o.getPlayerId()) + ",";
		}
		if (o.getItemName() != null) {
			insertString += "ITEM_NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getItemName()) + "',";
		}
		if (o.getWeight() != null) {
			insertString += "WEIGHT,";
			valueString += "'" + dbs.escapeQuotes(o.getWeight()) + "',";
		}
		if (o.getQuantity() != null) {
			insertString += "QUANTITY,";
			valueString += "'" + dbs.escapeQuotes(o.getQuantity()) + "',";
		}
		if (o.getLocation() != null) {
			insertString += "LOCATION,";
			valueString += "'" + dbs.escapeQuotes(o.getLocation()) + "',";
		}
		if (o.getCost() != null) {
			insertString += "COST,";
			valueString += "'" + dbs.escapeQuotes(o.getCost()) + "',";
		}
		if (o.getNotes() != null) {
			insertString += "NOTES,";
			valueString += "'" + dbs.escapeQuotes(o.getNotes()) + "',";
		}
		if (o.getModType() != null) {
			insertString += "MOD_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getModType()) + "',";
		}

		insertString += "POSITION,";
		valueString += getMaxItemPos(o.getPlayerId()) + ",";

		insertString += "DROPPED,";
		valueString += o.isDropped() + ",";

		insertString += "HAS_MOD,";
		valueString += o.isHasMod() + ",";

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

			insertString = "SELECT MAX(ID) AS MAXID FROM PLAYER_ITEMS";
			ResultSet result = dbs.executeSQLQuery(insertString);
			if (result.next()) {
				i = result.getInt("MAXID");
			} else {

			}

			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
		}

		return i;
	}

	public void updatePlayerItems(PlayerItems o) {
		String updateString = "update PLAYER_ITEMS set ";
		if (o.getPlayerId() != null) {
			updateString += "PLAYER_ID=" + dbs.escapeQuotes(o.getPlayerId())
					+ ",";
		}
		if (o.getItemName() != null) {
			updateString += "ITEM_NAME='" + dbs.escapeQuotes(o.getItemName())
					+ "',";
		}
		if (o.getWeight() != null) {
			updateString += "WEIGHT='" + dbs.escapeQuotes(o.getWeight()) + "',";
		}
		if (o.getQuantity() != null) {
			updateString += "QUANTITY='" + dbs.escapeQuotes(o.getQuantity())
					+ "',";
		}
		if (o.getLocation() != null) {
			updateString += "LOCATION='" + dbs.escapeQuotes(o.getLocation())
					+ "',";
		}
		if (o.getCost() != null) {
			updateString += "COST='" + dbs.escapeQuotes(o.getCost()) + "',";
		}
		if (o.getNotes() != null) {
			updateString += "NOTES='" + dbs.escapeQuotes(o.getNotes()) + "',";
		}
		if (o.getModType() != null) {
			updateString += "MOD_TYPE='" + dbs.escapeQuotes(o.getModType())
					+ "',";
		}

		updateString += "POSITION=" + o.getPosition() + ",";

		updateString += "DROPPED=" + o.isDropped() + ",";
		updateString += "HAS_MOD=" + o.isHasMod() + ",";

		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}

		updateString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + "";

		try {
			dbs.open();
			dbs.executeSQLCommand(updateString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
		}
	}

	public Vector<PlayerItems> getPlayerItems(String playerId) {
		PlayerItems pi = new PlayerItems();
		pi.setPlayerId(playerId);
		return selectPlayerItems(pi);
	}

	public Vector<PlayerItems> selectPlayerItems(PlayerItems o) {
		String selectString = "SELECT * FROM PLAYER_ITEMS  ";
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
		if (o.getPlayerId() != null && !o.getPlayerId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " PLAYER_ID = " + dbs.escapeQuotes(o.getPlayerId())
					+ " ";
		}
		if (o.getItemName() != null && !o.getItemName().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ITEM_NAME = '"
					+ dbs.escapeQuotes(o.getItemName()) + "' ";
		}
		if (o.getWeight() != null && !o.getWeight().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " WEIGHT = '" + dbs.escapeQuotes(o.getWeight())
					+ "' ";
		}
		if (o.getQuantity() != null && !o.getQuantity().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " QUANTITY = '" + dbs.escapeQuotes(o.getQuantity())
					+ "' ";
		}
		Vector<PlayerItems> v = new Vector<PlayerItems>();
		selectString += " ORDER BY POSITION";
		try {
			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}

			while (result.next()) {
				PlayerItems obj = new PlayerItems();

				obj.setId(result.getString("ID"));
				obj.setPlayerId(result.getString("PLAYER_ID"));
				obj.setItemName(result.getString("ITEM_NAME"));
				obj.setWeight(result.getString("WEIGHT"));
				obj.setQuantity(result.getString("QUANTITY"));
				obj.setLocation(result.getString("LOCATION"));
				obj.setCost(result.getString("COST"));
				obj.setNotes(result.getString("NOTES"));
				obj.setModType(result.getString("MOD_TYPE"));
				obj.setDropped(result.getBoolean("DROPPED"));
				obj.setHasMod(result.getBoolean("HAS_MOD"));
				obj.setPosition(result.getInt("POSITION"));
				v.add(obj);
			}
		} catch (SQLException sqle) {
			logger.log("error", sqle.toString());
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			if (dbs2 == null) {
				dbs.close();
			}
		}
		return v;
	}

	public Vector<String> getDroppedLocations(String id) {
		Vector<String> v = new Vector<String>();
		String selectString = "SELECT * FROM DROPPED_ITEMS WHERE PLAYER_ID = "
				+ id;
		try {
			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}

			while (result.next()) {
				v.add(result.getString("LOCATION"));
			}
		} catch (SQLException sqle) {
			logger.log("error", sqle.toString());
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			if (dbs2 == null) {
				dbs.close();
			}
		}
		return v;
	}

	public String getMaxItemPos(String id) {
		String pos = "1";
		String selectString = "SELECT MAX(POSITION) AS MAXPOS FROM PLAYER_ITEMS WHERE PLAYER_ID = "
				+ id;
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			if (result.next()) {
				int curpos = result.getInt("MAXPOS");
				curpos++;
				pos = "" + curpos;
			}
		} catch (SQLException sqle) {
			logger.log("error", sqle.toString());
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
		return pos;
	}

	public void updateOrder(PlayerItems o, String playerId, int change) {
		Vector<PlayerItems> v = getPlayerItems(playerId);

		for (int i = 0; i < v.size(); i++) {
			PlayerItems w = (PlayerItems) v.get(i);

			if (w.getPosition() == o.getPosition()) {

				w.setPosition(w.getPosition() + change);
				
				updatePlayerItems(w);
			} else if ((change < 0 && w.getPosition() + 1 == o.getPosition())
					|| (change > 0 && w.getPosition() - 1 == o.getPosition())) {
				w.setPosition(w.getPosition() - change);
				
				updatePlayerItems(w);
			}
		}
	}

	public void dropLocation(String id, String location) {
		try {
			// open the connection
			dbs.open();
			String command = "INSERT INTO DROPPED_ITEMS (PLAYER_ID, LOCATION) VALUES ("
					+ id + ",'" + location + "')";
			
			dbs.executeSQLCommand(command);
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		} finally {
			resetConnection();
		}

		// close the connection
		dbs.close();
	}

	public void undropLocation(String id, String location) {
		try {
			// open the connection
			dbs.open();
			String command = "DELETE FROM DROPPED_ITEMS WHERE " + "PLAYER_ID="
					+ id + " AND LOCATION='" + location + "'";
			
			dbs.executeSQLCommand(command);
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		} finally {
			resetConnection();
		}

		// close the connection
		dbs.close();
	}

	public void clearPlayerItems(String playerId) {
		try {
			// open the connection
			dbs.open();
			String command = "DELETE FROM PLAYER_ITEMS WHERE PLAYER_ID="
					+ playerId;
			dbs.executeSQLCommand(command);
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		} finally {
			resetConnection();
		}

		// close the connection
		dbs.close();
	}

	public void deletePlayerItems(PlayerItems o) {
		String deleteString = "delete from PLAYER_ITEMS  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + "";

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
		}
	}
}

package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PlayerAmmoDAO extends InitBaseDAO {

	public PlayerAmmoDAO() {

	}

	public PlayerAmmoDAO(DBSession dbs2) {
		this.dbs2 = dbs2;
	}

	public int addPlayerAmmo(Weapon w, String playerId) {

		PlayerAmmo pw = new PlayerAmmo();
		pw.setplayerid(playerId);
		pw.setweaponid(w.getId());
		pw.setbonus(w.getBonus());
		pw.setdescription(w.getDescription());
		pw.setQuantity(w.getQuantity());
		if (w.isMasterwork()) {
			pw.setMasterwork("Y");
		}
		pw.setNotes(w.getNotes());

		return addPlayerAmmo(pw);

	}

	public void updatePlayerAmmo(Weapon w, String playerId) {

		PlayerAmmo pw = new PlayerAmmo();
		pw.setId(w.getInstanceId());
		pw.setplayerid(playerId);
		pw.setweaponid(w.getId());
		pw.setbonus(w.getBonus());
		pw.setdescription(w.getDescription());
		pw.setQuantity(w.getQuantity());
		if (w.isMasterwork()) {
			pw.setMasterwork("Y");
		}

		pw.setNotes(w.getNotes());
		updatePlayerAmmo(pw);

	}

	public int addPlayerAmmo(PlayerAmmo o) {
		int i = -1;
		String valueString = "";
		String insertString = "INSERT INTO PLAYER_AMMO (";
		if (o.getId() != null) {
			insertString += "ID,";
			valueString += "'" + dbs.escapeQuotes(o.getId()) + "',";
		}
		if (o.getplayerid() != null) {
			insertString += "player_id,";
			valueString += dbs.escapeQuotes(o.getplayerid()) + ",";
		}
		if (o.getweaponid() != null) {
			insertString += "weapon_id,";
			valueString += dbs.escapeQuotes(o.getweaponid()) + ",";
		}
		if (o.getbonus() != null) {
			insertString += "bonus,";
			valueString += "'" + dbs.escapeQuotes(o.getbonus()) + "',";
		}
		if (o.getdescription() != null) {
			insertString += "description,";
			valueString += "'" + dbs.escapeQuotes(o.getdescription()) + "',";
		}

		if (o.getMasterwork() != null) {
			insertString += "masterwork,";
			valueString += "'" + dbs.escapeQuotes(o.getMasterwork()) + "',";
		}
		if (o.getQuantity() != null) {
			insertString += "QUANTITY,";
			valueString += dbs.escapeQuotes(o.getQuantity()) + ",";
		}

		if (o.getNotes() != null) {
			insertString += "NOTES,";
			valueString += "'" + dbs.escapeQuotes(o.getNotes()) + "',";
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
			
			dbs.executeSQLCommand(insertString);
		
			ResultSet result = dbs
					.executeSQLQuery("SELECT MAX(ID) as MAXID FROM PLAYER_AMMO");
			if (result.next()) {
				i = result.getInt("MAXID");
			}

		} catch (Exception e) {
			
			logger.log("error", e.toString());
		} finally {
			dbs.close();
			resetConnection();
		}

		return i;
	}

	public void updatePlayerAmmo(PlayerAmmo o) {
		String updateString = "update PLAYER_AMMO set ";
		if (o.getplayerid() != null) {
			updateString += "player_id=" + dbs.escapeQuotes(o.getplayerid())
					+ ",";
		}
		if (o.getweaponid() != null) {
			updateString += "weapon_id=" + dbs.escapeQuotes(o.getweaponid())
					+ ",";
		}
		if (o.getbonus() != null) {
			updateString += "bonus='" + dbs.escapeQuotes(o.getbonus()) + "',";
		}
		if (o.getdescription() != null) {
			updateString += "description='"
					+ dbs.escapeQuotes(o.getdescription()) + "',";
		}
		if (o.getMasterwork() != null) {
			updateString += "masterwork='"
					+ dbs.escapeQuotes(o.getMasterwork()) + "',";
		}
		if (o.getNotes() != null) {
			updateString += "NOTES='" + dbs.escapeQuotes(o.getNotes()) + "',";
		}
		if (o.getQuantity() != null) {
			updateString += "QUANTITY=" + dbs.escapeQuotes(o.getQuantity())
					+ ",";
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE ID = " + dbs.escapeQuotes(o.getId());

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

	public Vector<Weapon> getPlayerAmmo(String playerId) {
		String selectString = "SELECT w.*, pw.bonus, pw.description,"
				+ " pw.masterwork, pw.quantity, "
				+ " pw.id as INSTANCE_ID, pw.NOTES "
				+ " FROM WEAPONS w, PLAYER_AMMO pw " + "WHERE pw.PLAYER_ID="
				+ playerId + " AND w.id = pw.weapon_id";

		Vector<Weapon> v = new Vector<Weapon>();
		try {

			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}

			while (result.next()) {
				Weapon obj = new Weapon();

				obj.setId(result.getString("ID"));
				obj.setName(result.getString("NAME"));
				obj.setCost(result.getString("COST"));
				obj.setDamage(result.getString("DAMAGE"));
				obj.setCrit(result.getString("CRIT"));
				obj.setRange(result.getString("RANGE"));
				obj.setWeight(result.getString("WEIGHT"));
				obj.setType(result.getString("TYPE"));
				obj.setSize(result.getString("SIZE"));
				obj.setCategory(result.getString("CATEGORY"));
				obj.setRangedmelee(result.getString("RANGEDMELEE"));
				obj.setSource(result.getString("SOURCE"));
				obj.setNotes1(result.getString("NOTES1"));
				obj.setNotes2(result.getString("NOTES2"));
				obj.setExclude(result.getString("EXCLUDE"));
				obj.setBonus(result.getString("bonus"));
				obj.setFeatClass(result.getString("FEAT_CLASS"));
				obj.setQuantity(result.getString("QUANTITY"));
				obj.setDescription(result.getString("description"));
				obj.setInstanceId(result.getString("INSTANCE_ID"));

				String mw = result.getString("masterwork");
				if (mw != null && mw.equals("Y")) {
					obj.setMasterwork(true);
				} else {
					obj.setMasterwork(false);
				}
				obj.setNotes(result.getString("NOTES"));
				v.add(obj);
			}
		} catch (SQLException sqle) {
			logger.log("error", sqle.toString());
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			if (dbs2 == null) {
				dbs.close();
			} else {
				dbs2.cleanup();
			}
		}
		return v;
	}

	public void clearPlayerAmmo(String playerId) {
		try {
			// open the connection
			dbs.open();
			String command = "DELETE FROM PLAYER_AMMO WHERE PLAYER_ID="
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

	public void deletePlayerAmmo(Weapon w) {
		String deleteString = "delete from PLAYER_AMMO  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(w.getInstanceId());

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

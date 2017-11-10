package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class WeaponViewsDAO extends InitBaseDAO {
	boolean useSecondary = true;

	public WeaponViewsDAO() {

	}

	public WeaponViewsDAO(boolean useSecondary) {
		this.useSecondary = useSecondary;
	}

	public void addWeaponViews(WeaponViews o) {
		String valueString = "";
		String insertString = "INSERT INTO WEAPON_VIEWS (";

		if (o.getPlayerWeaponId() != null) {
			insertString += "PLAYER_WEAPON_ID,";
			if (o.getPlayerWeaponId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getPlayerWeaponId()) + ",";

			}
		}
		if (o.getFeatId() != null) {
			insertString += "FEAT_ID,";
			if (o.getFeatId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getFeatId()) + ",";

			}
		}

		if (o.getType() != null) {
			insertString += "ID_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getType()) + "',";
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
		} finally {
			resetConnection();
		}
	}

	public Vector<WeaponViews> getWeaponViews(String playerWeaponId) {

		String selectString = "SELECT WEAPON_VIEWS.*, FEATS.FEAT_NAME FROM WEAPON_VIEWS, FEATS "
				+ "WHERE PLAYER_WEAPON_ID="
				+ playerWeaponId
				+ " AND WEAPON_VIEWS.FEAT_ID=FEATS.ID AND ID_TYPE = 'FEAT'";

		Vector<WeaponViews> v = new Vector<WeaponViews>();
		try {
			ResultSet result;
			if (!useSecondary) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs3.executeSQLQuery(selectString);
			}

			while (result.next()) {
				WeaponViews obj = new WeaponViews();

				obj.setId(result.getString("ID"));
				obj.setPlayerWeaponId(result.getString("PLAYER_WEAPON_ID"));
				obj.setFeatId(result.getString("FEAT_ID"));
				obj.setFeatName(result.getString("FEAT_NAME"));
				v.add(obj);
			}

			selectString = "SELECT WEAPON_VIEWS.*, PLAYER_TEMP_MOD.NAME FROM WEAPON_VIEWS, PLAYER_TEMP_MOD "
					+ "WHERE PLAYER_WEAPON_ID="
					+ playerWeaponId
					+ " AND WEAPON_VIEWS.FEAT_ID=PLAYER_TEMP_MOD.ID AND ID_TYPE = 'ITEM'";

			if (!useSecondary) {
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs3.executeSQLQuery(selectString);
			}

			while (result.next()) {
				WeaponViews obj = new WeaponViews();

				obj.setId(result.getString("ID"));
				obj.setPlayerWeaponId(result.getString("PLAYER_WEAPON_ID"));
				obj.setFeatId(result.getString("FEAT_ID"));
				obj.setFeatName(result.getString("NAME"));
				v.add(obj);
			}

			selectString = "SELECT WEAPON_VIEWS.*, CLASS_ABILITIES.NAME FROM WEAPON_VIEWS, CLASS_ABILITIES "
					+ "WHERE PLAYER_WEAPON_ID="
					+ playerWeaponId
					+ " AND WEAPON_VIEWS.FEAT_ID=CLASS_ABILITIES.ID AND ID_TYPE = 'ABILITY'";

			if (!useSecondary) {
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs3.executeSQLQuery(selectString);
			}

			while (result.next()) {
				WeaponViews obj = new WeaponViews();

				obj.setId(result.getString("ID"));
				obj.setPlayerWeaponId(result.getString("PLAYER_WEAPON_ID"));
				obj.setFeatId(result.getString("FEAT_ID"));
				obj.setFeatName(result.getString("NAME"));
				v.add(obj);
			}

		} catch (SQLException sqle) {
			logger.log("error", sqle.toString());
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			if (!useSecondary) {
				dbs.close();
			} else {
				dbs3.cleanup();
			}
		}
		return v;
	}

	public void deleteWeaponViews(WeaponViews o) {
		String deleteString = "delete from WEAPON_VIEWS  ";
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

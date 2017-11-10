package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class WeaponSizeIncreaseDAO extends InitBaseDAO {

	public WeaponSizeIncreaseDAO() {

	}

	public int addOrUpdateWeaponSizeIncrease(WeaponSizeIncrease o) {
		int i = -1;
		if (o.getId() != null) {
			updateWeaponSizeIncrease(o);
		} else {
			addWeaponSizeIncrease(o);
		}
		try {
			// open the connection
			dbs.open();
			String command = "SELECT MAX(ID) as MAXID FROM WEAPON_SIZE_INCREASE";
			
			ResultSet result = dbs.executeSQLQuery(command);
			if (result.next()) {
				i = result.getInt("MAXID");
			}

		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		// close the connection
		dbs.close();
		return i;
	}
	
	public void addCampaignCustomization(String campaignId){
		Vector<WeaponSizeIncrease>v = getDefaultWeaponSizeIncrease();
		for (int i = 0; i < v.size(); i++){
			WeaponSizeIncrease ws = v.get(i);
			ws.setcampaignid(campaignId);
			addWeaponSizeIncrease(ws);
		}
	}

	public void addWeaponSizeIncrease(WeaponSizeIncrease o) {
		String valueString = "";
		String insertString = "INSERT INTO WEAPON_SIZE_INCREASE (";
		
		if (o.getdamage() != null) {
			insertString += "damage,";
			valueString += "'" + dbs.escapeQuotes(o.getdamage()) + "',";
		}
		if (o.getminus1() != null) {
			insertString += "minus1,";
			valueString += "'" + dbs.escapeQuotes(o.getminus1()) + "',";
		}
		if (o.getminus2() != null) {
			insertString += "minus2,";
			valueString += "'" + dbs.escapeQuotes(o.getminus2()) + "',";
		}
		if (o.getminus3() != null) {
			insertString += "minus3,";
			valueString += "'" + dbs.escapeQuotes(o.getminus3()) + "',";
		}
		if (o.getminus4() != null) {
			insertString += "minus4,";
			valueString += "'" + dbs.escapeQuotes(o.getminus4()) + "',";
		}
		if (o.getminus5() != null) {
			insertString += "minus5,";
			valueString += "'" + dbs.escapeQuotes(o.getminus5()) + "',";
		}
		if (o.getminus6() != null) {
			insertString += "minus6,";
			valueString += "'" + dbs.escapeQuotes(o.getminus6()) + "',";
		}
		if (o.getplus1() != null) {
			insertString += "plus1,";
			valueString += "'" + dbs.escapeQuotes(o.getplus1()) + "',";
		}
		if (o.getplus2() != null) {
			insertString += "plus2,";
			valueString += "'" + dbs.escapeQuotes(o.getplus2()) + "',";
		}
		if (o.getplus3() != null) {
			insertString += "plus3,";
			valueString += "'" + dbs.escapeQuotes(o.getplus3()) + "',";
		}
		if (o.getplus4() != null) {
			insertString += "plus4,";
			valueString += "'" + dbs.escapeQuotes(o.getplus4()) + "',";
		}
		if (o.getplus5() != null) {
			insertString += "plus5,";
			valueString += "'" + dbs.escapeQuotes(o.getplus5()) + "',";
		}
		if (o.getplus6() != null) {
			insertString += "plus6,";
			valueString += "'" + dbs.escapeQuotes(o.getplus6()) + "',";
		}
		if (o.getcampaignid() != null) {
			insertString += "campaign_id,";
			if (o.getcampaignid().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getcampaignid()) + ",";

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

	public void updateWeaponSizeIncrease(WeaponSizeIncrease o) {
		String updateString = "update WEAPON_SIZE_INCREASE set ";
		if (o.getdamage() != null) {
			updateString += "damage='" + dbs.escapeQuotes(o.getdamage()) + "',";
		}
		if (o.getminus1() != null) {
			updateString += "minus1='" + dbs.escapeQuotes(o.getminus1()) + "',";
		}
		if (o.getminus2() != null) {
			updateString += "minus2='" + dbs.escapeQuotes(o.getminus2()) + "',";
		}
		if (o.getminus3() != null) {
			updateString += "minus3='" + dbs.escapeQuotes(o.getminus3()) + "',";
		}
		if (o.getminus4() != null) {
			updateString += "minus4='" + dbs.escapeQuotes(o.getminus4()) + "',";
		}
		if (o.getminus5() != null) {
			updateString += "minus5='" + dbs.escapeQuotes(o.getminus5()) + "',";
		}
		if (o.getminus6() != null) {
			updateString += "minus6='" + dbs.escapeQuotes(o.getminus6()) + "',";
		}
		if (o.getplus1() != null) {
			updateString += "plus1='" + dbs.escapeQuotes(o.getplus1()) + "',";
		}
		if (o.getplus2() != null) {
			updateString += "plus2='" + dbs.escapeQuotes(o.getplus2()) + "',";
		}
		if (o.getplus3() != null) {
			updateString += "plus3='" + dbs.escapeQuotes(o.getplus3()) + "',";
		}
		if (o.getplus4() != null) {
			updateString += "plus4='" + dbs.escapeQuotes(o.getplus4()) + "',";
		}
		if (o.getplus5() != null) {
			updateString += "plus5='" + dbs.escapeQuotes(o.getplus5()) + "',";
		}
		if (o.getplus6() != null) {
			updateString += "plus6='" + dbs.escapeQuotes(o.getplus6()) + "',";
		}
		if (o.getcampaignid() != null) {
			if (!o.getcampaignid().equals("")) {
				updateString += "campaign_id="
						+ dbs.escapeQuotes(o.getcampaignid()) + ",";
			} else {
				updateString += "campaign_id=null,";
			}
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE ID = " + dbs.escapeQuotes(o.getId());
		try {
			dbs.open();
			dbs.executeSQLCommand(updateString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}

	public Vector<WeaponSizeIncrease> selectWeaponSizeIncrease(
			WeaponSizeIncrease o) {
		String selectString = "SELECT * FROM WEAPON_SIZE_INCREASE  ";
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
		if (o.getdamage() != null && !o.getdamage().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " damage = '" + dbs.escapeQuotes(o.getdamage())
					+ "' ";
		}
		if (o.getminus1() != null && !o.getminus1().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " minus1 = '" + dbs.escapeQuotes(o.getminus1())
					+ "' ";
		}
		if (o.getminus2() != null && !o.getminus2().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " minus2 = '" + dbs.escapeQuotes(o.getminus2())
					+ "' ";
		}
		if (o.getminus3() != null && !o.getminus3().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " minus3 = '" + dbs.escapeQuotes(o.getminus3())
					+ "' ";
		}
		if (o.getminus4() != null && !o.getminus4().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " minus4 = '" + dbs.escapeQuotes(o.getminus4())
					+ "' ";
		}
		if (o.getminus5() != null && !o.getminus5().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " minus5 = '" + dbs.escapeQuotes(o.getminus5())
					+ "' ";
		}
		if (o.getminus6() != null && !o.getminus6().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " minus6 = '" + dbs.escapeQuotes(o.getminus6())
					+ "' ";
		}
		if (o.getplus1() != null && !o.getplus1().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " plus1 = '" + dbs.escapeQuotes(o.getplus1())
					+ "' ";
		}
		if (o.getplus2() != null && !o.getplus2().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " plus2 = '" + dbs.escapeQuotes(o.getplus2())
					+ "' ";
		}
		if (o.getplus3() != null && !o.getplus3().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " plus3 = '" + dbs.escapeQuotes(o.getplus3())
					+ "' ";
		}
		if (o.getplus4() != null && !o.getplus4().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " plus4 = '" + dbs.escapeQuotes(o.getplus4())
					+ "' ";
		}
		if (o.getplus5() != null && !o.getplus5().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " plus5 = '" + dbs.escapeQuotes(o.getplus5())
					+ "' ";
		}
		if (o.getplus6() != null && !o.getplus6().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " plus6 = '" + dbs.escapeQuotes(o.getplus6())
					+ "' ";
		}
		if (o.getcampaignid() != null && !o.getcampaignid().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " campaign_id = "
					+ dbs.escapeQuotes(o.getcampaignid()) + " ";
		}
		return getWeaponSizeIncreaseFromSql(selectString);
	}
	
	public Vector<WeaponSizeIncrease> getDefaultWeaponSizeIncrease(){
		String selectString = "SELECT * FROM WEAPON_SIZE_INCREASE WHERE CAMPAIGN_ID IS NULL";
		return getWeaponSizeIncreaseFromSql(selectString);
	}
	
	public Vector<WeaponSizeIncrease> getWeaponSizeIncreaseFromSql (String selectString){
		Vector<WeaponSizeIncrease> v = new Vector<WeaponSizeIncrease>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				WeaponSizeIncrease obj = new WeaponSizeIncrease();

				obj.setId(result.getString("ID"));
				obj.setdamage(result.getString("damage"));
				obj.setminus1(result.getString("minus1"));
				obj.setminus2(result.getString("minus2"));
				obj.setminus3(result.getString("minus3"));
				obj.setminus4(result.getString("minus4"));
				obj.setminus5(result.getString("minus5"));
				obj.setminus6(result.getString("minus6"));
				obj.setplus1(result.getString("plus1"));
				obj.setplus2(result.getString("plus2"));
				obj.setplus3(result.getString("plus3"));
				obj.setplus4(result.getString("plus4"));
				obj.setplus5(result.getString("plus5"));
				obj.setplus6(result.getString("plus6"));
				obj.setcampaignid(result.getString("campaign_id"));
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

	public void deleteWeaponSizeIncrease(WeaponSizeIncrease o) {
		String deleteString = "delete from WEAPON_SIZE_INCREASE  ";
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
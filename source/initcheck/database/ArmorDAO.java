package initcheck.database;

import initcheck.character.library.LibraryItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ArmorDAO extends InitBaseDAO implements LibraryItemDAO {

	private static Vector<Armor> armorVector = null;

	private static Vector<Armor> shieldVector = null;

	public ArmorDAO() {

	}

	public ArmorDAO(DBSession dbs) {
		dbs2 = dbs;
	}

	public int addOrUpdateArmor(Armor o) {
		int i = -1;
		if (o.getId() != null) {
			updateArmor(o);
		} else {
			addArmor(o);
		}
		try {
			String command = "SELECT ID FROM ARMOR WHERE NAME='" + o.getName()
					+ "'";
			logger.log("EXECUTING COMMAND " + command);
			ResultSet result;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(command);
			} else {
				result = dbs2.executeSQLQuery(command);
			}
			if (result.next()) {
				i = result.getInt("ID");
			}

		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		} finally {
			if (dbs2 == null) {
				dbs.close();
			}
		}

		return i;
	}

	public void addArmor(Armor o) {
		String valueString = "";
		String insertString = "INSERT INTO ARMOR (";
		
		if (o.getName() != null) {
			insertString += "name,";
			valueString += "'" + dbs.escapeQuotes(o.getName()) + "',";
		}
		if (o.getDescription() != null) {
			insertString += "description,";
			valueString += "'" + dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getGrade() != null) {
			insertString += "grade,";
			valueString += "'" + dbs.escapeQuotes(o.getGrade()) + "',";
		}
		if (o.getCost() != null) {
			insertString += "cost,";
			valueString += "'" + dbs.escapeQuotes(o.getCost()) + "',";
		}
		if (o.getArmorbonus() != null) {
			insertString += "armor_bonus,";
			valueString += "'" + dbs.escapeQuotes(o.getArmorbonus()) + "',";
		}
		if (o.getMaxdex() != null) {
			insertString += "max_dex,";
			valueString += "'" + dbs.escapeQuotes(o.getMaxdex()) + "',";
		}
		if (o.getCheckpenalty() != null) {
			insertString += "check_penalty,";
			valueString += "'" + dbs.escapeQuotes(o.getCheckpenalty()) + "',";
		}
		if (o.getSpeed30() != null) {
			insertString += "speed_30,";
			valueString += "'" + dbs.escapeQuotes(o.getSpeed30()) + "',";
		}
		if (o.getSpeed20() != null) {
			insertString += "speed_20,";
			valueString += "'" + dbs.escapeQuotes(o.getSpeed20()) + "',";
		}
		if (o.getWeight() != null) {
			insertString += "weight,";
			valueString += "'" + dbs.escapeQuotes(o.getWeight()) + "',";
		}
		if (o.getArcanefail() != null) {
			insertString += "arcane_fail,";
			valueString += "'" + dbs.escapeQuotes(o.getArcanefail()) + "',";
		}
		if (o.getType() != null) {
			insertString += "type,";
			valueString += "'" + dbs.escapeQuotes(o.getType()) + "',";
		}
		if (o.getSource() != null) {
			insertString += "source,";
			valueString += "'" + dbs.escapeQuotes(o.getSource()) + "',";
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
			resetConnection();
			armorVector = null;
			shieldVector = null;
		}
	}

	public void updateArmor(Armor o) {
		String updateString = "update ARMOR set ";
		if (o.getName() != null) {
			updateString += "name='" + dbs.escapeQuotes(o.getName()) + "',";
		}
		if (o.getDescription() != null) {
			updateString += "description='"
					+ dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getGrade() != null) {
			updateString += "grade='" + dbs.escapeQuotes(o.getGrade()) + "',";
		}
		if (o.getCost() != null) {
			updateString += "cost='" + dbs.escapeQuotes(o.getCost()) + "',";
		}
		if (o.getArmorbonus() != null) {
			updateString += "armor_bonus='"
					+ dbs.escapeQuotes(o.getArmorbonus()) + "',";
		}
		if (o.getMaxdex() != null) {
			updateString += "max_dex='" + dbs.escapeQuotes(o.getMaxdex())
					+ "',";
		}
		if (o.getCheckpenalty() != null) {
			updateString += "check_penalty='"
					+ dbs.escapeQuotes(o.getCheckpenalty()) + "',";
		}
		if (o.getSpeed30() != null) {
			updateString += "speed_30='" + dbs.escapeQuotes(o.getSpeed30())
					+ "',";
		}
		if (o.getSpeed20() != null) {
			updateString += "speed_20='" + dbs.escapeQuotes(o.getSpeed20())
					+ "',";
		}
		if (o.getWeight() != null) {
			updateString += "weight='" + dbs.escapeQuotes(o.getWeight()) + "',";
		}
		if (o.getArcanefail() != null) {
			updateString += "arcane_fail='"
					+ dbs.escapeQuotes(o.getArcanefail()) + "',";
		}
		if (o.getType() != null) {
			updateString += "type='" + dbs.escapeQuotes(o.getType()) + "',";
		}
		if (o.getSource() != null) {
			updateString += "source='" + dbs.escapeQuotes(o.getSource()) + "',";
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
			armorVector = null;
			shieldVector = null;
		}
	}

	public boolean exists(Armor o) {
		return selectArmor(o).size() > 0;
	}

	public Vector<LibraryItem> getItems(LibraryItem i){
		String queryString = "SELECT * FROM ARMOR WHERE SOURCE = '"+i.getSource()+"' "+
		" ORDER BY TYPE, NAME ";
		Vector<LibraryItem> results = new Vector<LibraryItem>();
		results.addAll(getArmorFromSql(queryString));
		return results;
	}
	
	public Vector<Armor> getArmor() {
		String selectString = "SELECT * FROM ARMOR ORDER BY TYPE, NAME";
		return getArmorFromSql(selectString);
	}

	public Vector<Armor> getArmorType(String s) {

		if (s.equals("ARMOR") && armorVector != null) {
			return armorVector;
		}

		if (s.equals("SHIELD") && shieldVector != null) {
			return shieldVector;
		}

		String selectString = "SELECT * FROM ARMOR WHERE TYPE = '" + s
				+ "' ORDER BY  NAME";
		
		
		Vector<Armor> v = getArmorFromSql(selectString);
		
		if (s.equals("ARMOR")) {
			armorVector = v;
		} else if (s.equals("SHIELD")) {
			shieldVector = v;
		}

		return v;
	}

	public Vector<Armor> selectArmor(Armor o) {
		String selectString = "SELECT * FROM ARMOR  ";
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
		if (o.getName() != null && !o.getName().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " name = '" + dbs.escapeQuotes(o.getName()) + "' ";
		}
		if (o.getDescription() != null && !o.getDescription().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " description = '"
					+ dbs.escapeQuotes(o.getDescription()) + "' ";
		}
		if (o.getGrade() != null && !o.getGrade().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " grade = '" + dbs.escapeQuotes(o.getGrade())
					+ "' ";
		}
		if (o.getCost() != null && !o.getCost().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " cost = '" + dbs.escapeQuotes(o.getCost()) + "' ";
		}
		// if (o.getArmorbonus() != null && !o.getArmorbonus().equals("")){
		// if (!first){
		// selectString += " AND ";
		// } else {
		// selectString += " WHERE ";
		// first = false;
		// }
		// selectString += " armor_bonus =
		// '"+dbs.escapeQuotes(o.getArmorbonus())+"' ";
		// }
		if (o.getMaxdex() != null && !o.getMaxdex().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " max_dex = '" + dbs.escapeQuotes(o.getMaxdex())
					+ "' ";
		}
		// if (o.getCheckpenalty() != null && !o.getCheckpenalty().equals("")){
		// if (!first){
		// selectString += " AND ";
		// } else {
		// selectString += " WHERE ";
		// first = false;
		// }
		// selectString += " check_penalty =
		// '"+dbs.escapeQuotes(o.getCheckpenalty())+"' ";
		// }
		if (o.getSpeed30() != null && !o.getSpeed30().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " speed_30 = '" + dbs.escapeQuotes(o.getSpeed30())
					+ "' ";
		}
		if (o.getSpeed20() != null && !o.getSpeed20().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " speed_20 = '" + dbs.escapeQuotes(o.getSpeed20())
					+ "' ";
		}
		// if (o.getWeight() != null && !o.getWeight().equals("")){
		// if (!first){
		// selectString += " AND ";
		// } else {
		// selectString += " WHERE ";
		// first = false;
		// }
		// selectString += " weight = '"+dbs.escapeQuotes(o.getWeight())+"' ";
		// }
		if (o.getArcanefail() != null && !o.getArcanefail().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " arcane_fail = '"
					+ dbs.escapeQuotes(o.getArcanefail()) + "' ";
		}
		if (o.getType() != null && !o.getType().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " type = '" + dbs.escapeQuotes(o.getType()) + "' ";
		}
		if (o.getSource() != null && !o.getSource().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " source = '" + dbs.escapeQuotes(o.getSource()) + "' ";
		}
	
		return getArmorFromSql(selectString);
	}
	
	public Vector<Armor> getArmor(String keyword) {
		String queryString = "SELECT * FROM ARMOR ";

		if (keyword != null && !keyword.equals("")) {
			queryString += "WHERE DESCRIPTION like '%" + keyword
					+ "%' OR NAME LIKE '%" + keyword + "%'";
		}

		queryString += " ORDER BY NAME";
		
		return getArmorFromSql(queryString);
	}
	
	public Vector<Armor> getArmor(String keyword, Campaign c) {
		String queryString = "SELECT * FROM ARMOR ";
		boolean first = true;
		if (keyword != null && !keyword.equals("")) {
			queryString += "WHERE DESCRIPTION like '%" + keyword
					+ "%' OR NAME LIKE '%" + keyword + "%'";
			first = false;
		}
		Vector<CampaignSource> v = c.getSources();
		if(v.size() > 0){
			if (first){
				queryString += " WHERE (";
				first = false;
			}else{
				queryString += " AND ( ";
			}
			for (int i = 0; i < v.size()-1; i++){
				CampaignSource cs = v.get(i);
				queryString += "SOURCE='"+cs.getName()+"' OR ";
			}
			CampaignSource cs = v.get(v.size()-1);
			queryString += "SOURCE='"+cs.getName()+"' OR  EXISTS (SELECT 'x' FROM CAMPAIGN_WHITE_LIST WHERE CAMPAIGN_ID = "+
		c.getId()+ " AND XREF_ID = ARMOR.ID AND TYPE = 'Armor')";
			
			queryString += ") ";
		}
		queryString += " AND NOT EXISTS (SELECT 'x' FROM CAMPAIGN_BLACK_LIST WHERE CAMPAIGN_ID = "+
		c.getId()+ " AND XREF_ID = ARMOR.ID AND TYPE = 'Armor')";
		queryString += " ORDER BY NAME";
		
		return getArmorFromSql(queryString);
	}
	
	
	public Vector<Armor> getArmorFromSql(String selectString) {
		
		Vector<Armor> v = new Vector<Armor>();
		try {

			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}

			while (result.next()) {
				Armor obj = new Armor();

				obj.setId(result.getString("ID"));
				obj.setName(result.getString("name"));
				obj.setDescription(result.getString("description"));
				obj.setGrade(result.getString("grade"));
				obj.setCost(result.getString("cost"));
				obj.setArmorbonus(result.getString("armor_bonus"));
				obj.setMaxdex(result.getString("max_dex"));
				obj.setCheckpenalty(result.getString("check_penalty"));
				obj.setSpeed30(result.getString("speed_30"));
				obj.setSpeed20(result.getString("speed_20"));
				obj.setWeight(result.getString("weight"));
				obj.setArcanefail(result.getString("arcane_fail"));
				obj.setType(result.getString("type"));
				obj.setSource(result.getString("SOURCE"));
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

	public void deleteArmor(Armor o) {
		String deleteString = "delete from ARMOR  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getId());

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
			armorVector = null;
			shieldVector = null;
		}
	}
}

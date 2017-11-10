package initcheck.database;

import initcheck.character.library.LibraryItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ArmorAbilitiesDAO extends InitBaseDAO implements LibraryItemDAO {

	private static Vector<ArmorAbilities> abilities = null;
	private static Vector<ArmorAbilities> shieldAbilities = null;

	public ArmorAbilitiesDAO() {

	}

	public int addOrUpdateArmorAbilities(ArmorAbilities o) {
		int i = -1;
		if (o.getId() != null) {
			updateArmorAbilities(o);
		} else {
			addArmorAbilities(o);
		}
		try {
			// open the connection
			dbs.open();
			String command = "SELECT MAX(ID) as MAXID FROM ARMOR_ABILITIES";
			
			ResultSet result = dbs.executeSQLQuery(command);
			if (result.next()) {
				i = result.getInt("MAXID");
			}

		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}

		// close the connection
		dbs.close();
		resetConnection();
		abilities = null;
		return i;
	}

	public void addArmorAbilities(ArmorAbilities o) {
		String valueString = "";
		String insertString = "INSERT INTO ARMOR_ABILITIES (";
		if (o.getId() != null) {
			insertString += "ID,";
			valueString += "'" + dbs.escapeQuotes(o.getId()) + "',";
		}
		if (o.getAbilityName() != null) {
			insertString += "ABILITY_NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getAbilityName()) + "',";
		}
		if (o.getDescription() != null) {
			insertString += "DESCRIPTION,";
			valueString += "'" + dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getArmorType() != null) {
			insertString += "ARMOR_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getArmorType()) + "',";
		}
		if (o.getBonus() != null) {
			insertString += "BONUS,";
			if (o.getBonus().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getBonus()) + ",";

			}
		}
		if (o.getCasterLevel() != null) {
			insertString += "CASTER_LEVEL,";
			valueString += "'" + dbs.escapeQuotes(o.getCasterLevel()) + "',";
		}
		if (o.getPrereq() != null) {
			insertString += "PREREQ,";
			valueString += "'" + dbs.escapeQuotes(o.getPrereq()) + "',";
		}
		if (o.getCost() != null) {
			insertString += "COST,";
			valueString += "'" + dbs.escapeQuotes(o.getCost()) + "',";
		}
		if (o.getSource() != null) {
			insertString += "SOURCE,";
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
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
		}
	}

	public void updateArmorAbilities(ArmorAbilities o) {
		String updateString = "update ARMOR_ABILITIES set ";
		if (o.getAbilityName() != null) {
			updateString += "ABILITY_NAME='"
					+ dbs.escapeQuotes(o.getAbilityName()) + "',";
		}
		if (o.getDescription() != null) {
			updateString += "DESCRIPTION='"
					+ dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getArmorType() != null) {
			updateString += "ARMOR_TYPE='" + dbs.escapeQuotes(o.getArmorType())
					+ "',";
		}
		if (o.getCasterLevel() != null) {
			updateString += "CASTER_LEVEL='"
					+ dbs.escapeQuotes(o.getCasterLevel()) + "',";
		}
		if (o.getPrereq() != null) {
			updateString += "PREREQ='" + dbs.escapeQuotes(o.getPrereq()) + "',";
		}
		if (o.getCost() != null) {
			updateString += "COST='" + dbs.escapeQuotes(o.getCost()) + "',";
		}
		if (o.getSource() != null) {
			updateString += "SOURCE='" + dbs.escapeQuotes(o.getSource())
					+ "',";
		}
		if (o.getBonus() != null) {
			if (!o.getBonus().equals("")) {
				updateString += "BONUS=" + dbs.escapeQuotes(o.getBonus()) + "";
			} else {
				updateString += "BONUS=null";
			}
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
			abilities = null;
		}
	}

	public Vector<ArmorAbilities> searchArmorAbilities(String s) {
		String selectString = "SELECT * FROM ARMOR_ABILITIES WHERE DESCRIPTION LIKE '"
				+ s + "'";

		Vector<ArmorAbilities> v = new Vector<ArmorAbilities>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				ArmorAbilities obj = new ArmorAbilities();

				obj.setId(result.getString("ID"));
				obj.setAbilityName(result.getString("ABILITY_NAME"));
				obj.setDescription(result.getString("DESCRIPTION"));
				obj.setArmorType(result.getString("ARMOR_TYPE"));
				obj.setBonus(result.getString("BONUS"));
				obj.setCasterLevel(result.getString("CASTER_LEVEL"));
				obj.setPrereq(result.getString("PREREQ"));
				obj.setCost(result.getString("COST"));
				obj.setSource(result.getString("SOURCE"));
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

	public Vector<ArmorAbilities> getArmorAbilitiesByType(String s) {
		if ("ARMOR".equals(s)){
			if (abilities != null) {
		
				return abilities;
			}
			
			ArmorAbilities a = new ArmorAbilities();
			a.setArmorType(s);
			abilities = selectArmorAbilities(a);
			return abilities;
		}else{
			if (shieldAbilities != null) {
				
				return shieldAbilities;
			}
			
			ArmorAbilities a = new ArmorAbilities();
			a.setArmorType(s);
			shieldAbilities = selectArmorAbilities(a);
			return shieldAbilities;
		}
	}

	public Vector<ArmorAbilities> getArmorAbilities() {
		return selectArmorAbilities(new ArmorAbilities());
	}

	public Vector<LibraryItem> getItems(LibraryItem i){
		String queryString = "SELECT * FROM ARMOR_ABILITIES WHERE SOURCE = '"+i.getSource()+"' "+
		" ORDER BY ABILITY_NAME";
		Vector<LibraryItem> results = new Vector<LibraryItem>();
		results.addAll(getArmorAbilitiesFromSql(queryString));
		return results;
	}
	
	public Vector<ArmorAbilities> getArmorAbilities (String keyword){
		
		String queryString = "SELECT * FROM Armor_ABILITIES ";

		if (keyword != null && !keyword.equals("")) {
			queryString += "WHERE DESCRIPTION like '%" + keyword
					+ "%' OR ABILITY_NAME LIKE '%" + keyword + "%'";
		}

		queryString += " ORDER BY ABILITY_NAME";
		
		return getArmorAbilitiesFromSql(queryString);
		
	}
	
	public Vector<ArmorAbilities> getArmorAbilities (String keyword, Campaign c){
		
		String queryString = "SELECT * FROM Armor_ABILITIES ";
		boolean first = true;
		if (keyword != null && !keyword.equals("")) {
			queryString += "WHERE DESCRIPTION like '%" + keyword
					+ "%' OR ABILITY_NAME LIKE '%" + keyword + "%'";
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
		c.getId()+ " AND XREF_ID = ARMOR_ABILITIES.ID AND TYPE = 'Armor Ability')";
				
			queryString += ") ";
		}
		queryString += " AND NOT EXISTS (SELECT 'x' FROM CAMPAIGN_BLACK_LIST WHERE CAMPAIGN_ID = "+
		c.getId()+ " AND XREF_ID = ARMOR_ABILITIES.ID AND TYPE = 'Armor Ability')";
		
		queryString += " ORDER BY ABILITY_NAME";
		
		return getArmorAbilitiesFromSql(queryString);
		
	}
	
	public ArmorAbilities getArmorAbilitiesById(String id){
		ArmorAbilities a = new ArmorAbilities();
		a.setId(id);
		Vector<ArmorAbilities> v = selectArmorAbilities(a);
		if (v.size() > 0){
			return v.firstElement();
		}
		System.out.println("DIDN'T FIND THE ABILITY");
		return null;
	}
	
	public Vector<ArmorAbilities> selectArmorAbilities(ArmorAbilities o) {
		String selectString = "SELECT * FROM ARMOR_ABILITIES  ";
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
		if (o.getAbilityName() != null && !o.getAbilityName().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ABILITY_NAME = '"
					+ dbs.escapeQuotes(o.getAbilityName()) + "' ";
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
		if (o.getArmorType() != null && !o.getArmorType().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ARMOR_TYPE = '"
					+ dbs.escapeQuotes(o.getArmorType()) + "' ";
		}
		if (o.getBonus() != null && !o.getBonus().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " BONUS = '" + dbs.escapeQuotes(o.getBonus())
					+ "' ";
		}
		
		System.out.println("Running SQL "+selectString);
		
		return getArmorAbilitiesFromSql(selectString);
	}
	
	public Vector<ArmorAbilities> getArmorAbilitiesFromSql(String selectString) {
		Vector<ArmorAbilities> v = new Vector<ArmorAbilities>();
		try {
			
			ResultSet result = null;
			//if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			//} else {
			//	result = dbs2.executeSQLQuery(selectString);
			//}

			while (result.next()) {
				ArmorAbilities obj = new ArmorAbilities();

				obj.setId(result.getString("ID"));
				obj.setAbilityName(result.getString("ABILITY_NAME"));
				obj.setDescription(result.getString("DESCRIPTION"));
				obj.setArmorType(result.getString("ARMOR_TYPE"));
				obj.setBonus(result.getString("BONUS"));
				obj.setCasterLevel(result.getString("CASTER_LEVEL"));
				obj.setPrereq(result.getString("PREREQ"));
				obj.setCost(result.getString("COST"));
				obj.setSource(result.getString("SOURCE"));
				obj.setName(obj.getAbilityName());
				
				v.add(obj);
			}
		} catch (SQLException sqle) {
			logger.log("error", sqle.toString());
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			//if (dbs2 == null) {
				dbs.close();
			//} else {
			//	dbs2.cleanup();
			//}
		}

		return v;
	}

	public void deleteArmorAbilities(ArmorAbilities o) {
		String deleteString = "delete from ARMOR_ABILITIES  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getId());

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

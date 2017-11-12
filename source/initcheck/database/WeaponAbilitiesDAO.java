package initcheck.database;

import initcheck.character.library.LibraryItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

public class WeaponAbilitiesDAO extends InitBaseDAO implements LibraryItemDAO {

	boolean useSecondary = true;

	private static Vector<WeaponAbilities> meleeVector = null;

	private static Vector<WeaponAbilities> rangedVector = null;

	private static HashMap<String, Object> abilityHash = null;

	@SuppressWarnings("unused")
	private static Vector<WeaponAbilities> abilVector = null;
	
	
	public WeaponAbilitiesDAO() {

	}

	public WeaponAbilitiesDAO(boolean useSecondary) {
		this.useSecondary = useSecondary;
	}

	public int addOrUpdateWeaponAbilities(WeaponAbilities o) {
		int i = -1;
		if (o.getId() != null) {
			updateWeaponAbilities(o);
		} else {
			addWeaponAbilities(o);
		}
		try {
			// open the connection
			dbs.open();
			String command = "SELECT MAX(ID) as MAXID FROM WEAPON_ABILITIES";
			
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

	public void addWeaponAbilities(WeaponAbilities o) {
		String valueString = "";
		String insertString = "INSERT INTO WEAPON_ABILITIES (";
		if (o.getAbilityName() != null) {
			insertString += "ABILITY_NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getAbilityName()) + "',";
		}
		if (o.getDescription() != null) {
			insertString += "DESCRIPTION,";
			valueString += "'" + dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getWeaponType() != null) {
			insertString += "WEAPON_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getWeaponType()) + "',";
		}
		if (o.getBonus() != null) {
			insertString += "BONUS,";
			valueString += "'" + dbs.escapeQuotes(o.getBonus()) + "',";
		}
		if (o.getId() != null) {
			insertString += "ID,";
			valueString += "'" + dbs.escapeQuotes(o.getId()) + "',";
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
		if (o.getDamage() != null) {
			insertString += "DAMAGE,";
			valueString += "'" + dbs.escapeQuotes(o.getDamage()) + "',";
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
			rangedVector = null;
			meleeVector = null;
		}
	}

	public void updateWeaponAbilities(WeaponAbilities o) {
		String updateString = "update WEAPON_ABILITIES set ";
		if (o.getAbilityName() != null) {
			updateString += "ABILITY_NAME='"
					+ dbs.escapeQuotes(o.getAbilityName()) + "',";
		}
		if (o.getDescription() != null) {
			updateString += "DESCRIPTION='"
					+ dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getWeaponType() != null) {
			updateString += "WEAPON_TYPE='"
					+ dbs.escapeQuotes(o.getWeaponType()) + "',";
		}
		if (o.getBonus() != null) {
			updateString += "BONUS='" + dbs.escapeQuotes(o.getBonus()) + "',";
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
		if (o.getDamage() != null) {
			updateString += "DAMAGE='" + dbs.escapeQuotes(o.getDamage()) + "',";
		}
		if (o.getSource() != null) {
			updateString += "SOURCE='" + dbs.escapeQuotes(o.getSource())
					+ "',";
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
			rangedVector = null;
			meleeVector = null;
		}
	}

	@SuppressWarnings("unchecked")
	public WeaponAbilities getWeaponAbility(String id) {
		if (abilityHash == null) {
			abilityHash = new HashMap();
		}
		Object o = abilityHash.get(id);
		if (o == null) {
			WeaponAbilities w = new WeaponAbilities();
			
			w.setId(id);
			Vector v = selectWeaponAbilities(w);
			if (v.size() > 0) {				
				o = v.get(0);
				abilityHash.put(id, o);
			}
		}
		return (WeaponAbilities)o;
	}

	public Vector<WeaponAbilities> getWeaponAbilities() {
		WeaponAbilities w = new WeaponAbilities();
		return selectWeaponAbilities(w);
	}

	public Vector<LibraryItem> getItems(LibraryItem i){
		String queryString = "SELECT * FROM WEAPON_ABILITIES WHERE SOURCE = '"+i.getSource()+"' "+
		" ORDER BY ABILITY_NAME";
		Vector<LibraryItem> results = new Vector<LibraryItem>();
		results.addAll(getWeaponAbilitiesFromSql(queryString));
		return results;
	}
	
	public Vector<WeaponAbilities> getWeaponAbilitiesByType(String s) {
		if (s.equalsIgnoreCase("Ranged") && rangedVector != null) {
			return rangedVector;
		}
		if (s.equalsIgnoreCase("Melee") && meleeVector != null) {
			return meleeVector;
		}

		WeaponAbilities w = new WeaponAbilities();
		w.setWeaponType(s);

		if (s.equalsIgnoreCase("Ranged")) {
			rangedVector = selectWeaponAbilities(w);
			return rangedVector;
		}

		if (s.equalsIgnoreCase("Melee")) {
			meleeVector = selectWeaponAbilities(w);
			return meleeVector;
		}

		return selectWeaponAbilities(w);

	}

	public Vector<WeaponAbilities> getWeaponAbilities (String keyword){
		
		String queryString = "SELECT * FROM WEAPON_ABILITIES ";

		if (keyword != null && !keyword.equals("")) {
			queryString += "WHERE DESCRIPTION like '%" + keyword
					+ "%' OR ABILITY_NAME LIKE '%" + keyword + "%'";
		}

		queryString += " ORDER BY ABILITY_NAME";
		
		return getWeaponAbilitiesFromSql(queryString);
		
	}
	
	public Vector<WeaponAbilities> getWeaponAbilities (String keyword, Campaign c){
		
		String queryString = "SELECT * FROM WEAPON_ABILITIES ";
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
			queryString += "SOURCE='"+cs.getName()+"'  OR EXISTS (SELECT 'x' FROM CAMPAIGN_WHITE_LIST WHERE CAMPAIGN_ID = "+
		c.getId()+ " AND XREF_ID = WEAPON_ABILITIES.ID AND TYPE = 'Weapon Ability')";
			
			queryString += ") ";
		}
		queryString += " AND NOT EXISTS (SELECT 'x' FROM CAMPAIGN_BLACK_LIST WHERE CAMPAIGN_ID = "+
		c.getId()+ " AND XREF_ID = WEAPON_ABILITIES.ID AND TYPE = 'Weapon Ability')";
		queryString += " ORDER BY ABILITY_NAME";
		
		return getWeaponAbilitiesFromSql(queryString);
		
	}
	
	public Vector<WeaponAbilities> selectWeaponAbilities(WeaponAbilities o) {
		String selectString = "SELECT * FROM WEAPON_ABILITIES  ";
		boolean first = true;
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
		if (o.getWeaponType() != null && !o.getWeaponType().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " WEAPON_TYPE = '"
					+ dbs.escapeQuotes(o.getWeaponType()) + "' ";
		}
		if (o.getId() != null && !o.getId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ID = " + dbs.escapeQuotes(o.getId());
		}
		
		return getWeaponAbilitiesFromSql(selectString);
	}
	
	public Vector<WeaponAbilities> getWeaponAbilitiesFromSql(String selectString) {
		Vector<WeaponAbilities> v = new Vector<WeaponAbilities>();
		try {
			ResultSet result;
			if (useSecondary) {
				result = dbs3.executeSQLQuery(selectString);
			} else {
				if (dbs2 == null) {
					dbs.open();
					result = dbs.executeSQLQuery(selectString);
				} else {
					result = dbs2.executeSQLQuery(selectString);
				}

			}

			while (result.next()) {
				WeaponAbilities obj = new WeaponAbilities();

				obj.setAbilityName(result.getString("ABILITY_NAME"));
				obj.setDescription(result.getString("DESCRIPTION"));
				obj.setWeaponType(result.getString("WEAPON_TYPE"));
				obj.setId(result.getString("ID"));
				obj.setBonus(result.getString("BONUS"));
				obj.setCasterLevel(result.getString("CASTER_LEVEL"));
				obj.setPrereq(result.getString("PREREQ"));
				obj.setCost(result.getString("COST"));
				obj.setDamage(result.getString("DAMAGE"));
				obj.setSource(result.getString("SOURCE"));
				v.add(obj);
			}
		} catch (SQLException sqle) {
			logger.log("error", sqle.toString());
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			if (!useSecondary) {
				if (dbs2 == null) {
					dbs.close();
				} else {
					dbs2.cleanup();
				}
			}else{
				dbs3.close();
			}
		}
		return v;
	}

	public void deleteWeaponAbilities(WeaponAbilities o) {
		String deleteString = "delete from WEAPON_ABILITIES  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + "";

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
			rangedVector = null;
			meleeVector = null;
		}
	}
}

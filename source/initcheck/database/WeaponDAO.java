package initcheck.database;

import initcheck.character.library.LibraryItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class WeaponDAO extends InitBaseDAO implements LibraryItemDAO {

	private static Vector<Weapon> weapons = null;

	public WeaponDAO() {

	}

	public int addOrUpdateWeapon(Weapon o) {
		int i = -1;
		if (exists(o)) {
			updateWeapon(o);
			i = Integer.parseInt(o.getId());
		} else {
			addWeapon(o);
			try {
				// open the connection
				dbs.open();
				String command = "SELECT MAX(ID) MAXID FROM WEAPONS";
				
				ResultSet result = dbs.executeSQLQuery(command);
				if (result.next()) {
					i = result.getInt("MAXID");
				}

			} catch (Exception uhe) {
				logger.log("ERROR : " + uhe.toString());
			}
		}
		
		// close the connection
		dbs.close();
		
		return i;
	}

	public void addWeapon(Weapon o) {
		String valueString = "";
		String insertString = "INSERT INTO WEAPONS (";
		if (o.getId() != null) {
			insertString += "ID,";
			valueString += dbs.escapeQuotes(o.getId()) + ",";
		}
		if (o.getName() != null) {
			insertString += "NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getName()) + "',";
		}
		if (o.getCost() != null) {
			insertString += "COST,";
			valueString += "'" + dbs.escapeQuotes(o.getCost()) + "',";
		}
		if (o.getDamage() != null) {
			insertString += "DAMAGE,";
			valueString += "'" + dbs.escapeQuotes(o.getDamage()) + "',";
		}
		if (o.getCrit() != null) {
			insertString += "CRIT,";
			valueString += "'" + dbs.escapeQuotes(o.getCrit()) + "',";
		}
		if (o.getRange() != null) {
			insertString += "RANGE,";
			valueString += "'" + dbs.escapeQuotes(o.getRange()) + "',";
		}
		if (o.getWeight() != null) {
			insertString += "WEIGHT,";
			valueString += "'" + dbs.escapeQuotes(o.getWeight()) + "',";
		}
		if (o.getType() != null) {
			insertString += "TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getType()) + "',";
		}
		if (o.getSize() != null) {
			insertString += "SIZE,";
			valueString += "'" + dbs.escapeQuotes(o.getSize()) + "',";
		}
		if (o.getCategory() != null) {
			insertString += "CATEGORY,";
			valueString += "'" + dbs.escapeQuotes(o.getCategory()) + "',";
		}
		if (o.getRangedmelee() != null) {
			insertString += "RANGEDMELEE,";
			valueString += "'" + dbs.escapeQuotes(o.getRangedmelee()) + "',";
		}
		if (o.getSource() != null) {
			insertString += "SOURCE,";
			valueString += "'" + dbs.escapeQuotes(o.getSource()) + "',";
		}
		if (o.getNotes1() != null) {
			insertString += "NOTES1,";
			valueString += "'" + dbs.escapeQuotes(o.getNotes1()) + "',";
		}
		if (o.getNotes2() != null) {
			insertString += "NOTES2,";
			valueString += "'" + dbs.escapeQuotes(o.getNotes2()) + "',";
		}
		if (o.getExclude() != null) {
			insertString += "EXCLUDE,";
			valueString += "'" + dbs.escapeQuotes(o.getExclude()) + "',";
		}
		if (o.getFeatClass() != null) {
			insertString += "FEAT_CLASS,";
			valueString += "'" + dbs.escapeQuotes(o.getFeatClass()) + "',";
		}
		if (o.getCritStart() != null && !o.getCritStart().equals("")) {
			insertString += "CRIT_START,";
			valueString += "" + dbs.escapeQuotes(o.getCritStart()) + ",";
		} else {
			insertString += "CRIT_START,";
			valueString += "null,";
		}
		if (o.getCritEnd() != null && !o.getCritEnd().equals("")) {
			insertString += "CRIT_END,";
			valueString += "" + dbs.escapeQuotes(o.getCritEnd()) + ",";
		} else {
			insertString += "CRIT_END,";
			valueString += "null,";
		}
		if (o.getCritMult() != null && !o.getCritMult().equals("")) {
			insertString += "CRIT_MULT,";
			valueString += "" + dbs.escapeQuotes(o.getCritMult()) + ",";
		} else {
			insertString += "CRIT_MULT,";
			valueString += "null,";
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
			weapons = null;
			resetConnection();
		}
	}

	public void updateWeapon(Weapon o) {
		String updateString = "update WEAPONS set ";
		if (o.getName() != null) {
			updateString += "NAME='" + dbs.escapeQuotes(o.getName()) + "',";
		}
		if (o.getCost() != null) {
			updateString += "COST='" + dbs.escapeQuotes(o.getCost()) + "',";
		}
		if (o.getDamage() != null) {
			updateString += "DAMAGE='" + dbs.escapeQuotes(o.getDamage()) + "',";
		}
		if (o.getCrit() != null) {
			updateString += "CRIT='" + dbs.escapeQuotes(o.getCrit()) + "',";
		}
		if (o.getRange() != null) {
			updateString += "RANGE='" + dbs.escapeQuotes(o.getRange()) + "',";
		}
		if (o.getWeight() != null) {
			updateString += "WEIGHT='" + dbs.escapeQuotes(o.getWeight()) + "',";
		}
		if (o.getType() != null) {
			updateString += "TYPE='" + dbs.escapeQuotes(o.getType()) + "',";
		}
		if (o.getSize() != null) {
			updateString += "SIZE='" + dbs.escapeQuotes(o.getSize()) + "',";
		}
		if (o.getCategory() != null) {
			updateString += "CATEGORY='" + dbs.escapeQuotes(o.getCategory())
					+ "',";
		}
		if (o.getRangedmelee() != null) {
			updateString += "RANGEDMELEE='"
					+ dbs.escapeQuotes(o.getRangedmelee()) + "',";
		}
		if (o.getSource() != null) {
			updateString += "SOURCE='" + dbs.escapeQuotes(o.getSource()) + "',";
		}
		if (o.getNotes1() != null) {
			updateString += "NOTES1='" + dbs.escapeQuotes(o.getNotes1()) + "',";
		}
		if (o.getNotes2() != null) {
			updateString += "NOTES2='" + dbs.escapeQuotes(o.getNotes2()) + "',";
		}
		if (o.getExclude() != null) {
			updateString += "EXCLUDE='" + dbs.escapeQuotes(o.getExclude())
					+ "',";
		}
		if (o.getFeatClass() != null) {
			updateString += "FEAT_CLASS='" + dbs.escapeQuotes(o.getFeatClass())
					+ "',";
		}
		if (o.getCritStart() != null && !o.getCritStart().equals("")) {
			updateString += "CRIT_START=" + dbs.escapeQuotes(o.getCritStart())
					+ ",";
		} else {
			updateString += "CRIT_START=null,";
		}
		if (o.getCritEnd() != null && !o.getCritEnd().equals("")) {
			updateString += "CRIT_END=" + dbs.escapeQuotes(o.getCritEnd())
					+ ",";
		} else {
			updateString += "CRIT_END=null,";
		}
		if (o.getCritMult() != null) {
			updateString += "CRIT_MULT=" + dbs.escapeQuotes(o.getCritMult())
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
			weapons = null;
			resetConnection();
		}
	}
	
	public Vector<LibraryItem> getItems(LibraryItem i){
		String queryString = "SELECT * FROM WEAPONS WHERE SOURCE = '"+i.getSource()+"' "+
		" ORDER BY NAME";
		Vector<LibraryItem> results = new Vector<LibraryItem>();
		results.addAll(getWeaponsFromSql(queryString));
		return results;
	}
	

	public Vector<Weapon> selectWeapon(Weapon o) {
		String selectString = "SELECT * FROM WEAPONS  ";
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
		if (o.getName() != null && !o.getName().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " NAME = '" + dbs.escapeQuotes(o.getName()) + "' ";
		}
		if (o.getCost() != null && !o.getCost().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " COST = '" + dbs.escapeQuotes(o.getCost()) + "' ";
		}
		if (o.getDamage() != null && !o.getDamage().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " DAMAGE = '" + dbs.escapeQuotes(o.getDamage())
					+ "' ";
		}

		if (o.getRange() != null && !o.getRange().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " RANGE = '" + dbs.escapeQuotes(o.getRange())
					+ "' ";
		}
		// if (o.getWeight() != null && !o.getWeight().equals("")){
		// if (!first){
		// selectString += " AND ";
		// } else {
		// selectString += " WHERE ";
		// first = false;
		// }
		// selectString += " WEIGHT = '"+dbs.escapeQuotes(o.getWeight())+"' ";
		// }
		if (o.getType() != null && !o.getType().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " TYPE = '" + dbs.escapeQuotes(o.getType()) + "' ";
		}
		if (o.getSize() != null && !o.getSize().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SIZE = '" + dbs.escapeQuotes(o.getSize()) + "' ";
		}
		if (o.getCategory() != null && !o.getCategory().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " CATEGORY = '" + dbs.escapeQuotes(o.getCategory())
					+ "' ";
		}
		if (o.getRangedmelee() != null && !o.getRangedmelee().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " RANGEDMELEE = '"
					+ dbs.escapeQuotes(o.getRangedmelee()) + "' ";
		}
		if (o.getSource() != null && !o.getSource().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SOURCE = '" + dbs.escapeQuotes(o.getSource())
					+ "' ";
		}
		if (o.getNotes1() != null && !o.getNotes1().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " NOTES1 = '" + dbs.escapeQuotes(o.getNotes1())
					+ "' ";
		}
		if (o.getNotes2() != null && !o.getNotes2().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " NOTES2 = '" + dbs.escapeQuotes(o.getNotes2())
					+ "' ";
		}
		if (o.getExclude() != null && !o.getExclude().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " EXCLUDE = '" + dbs.escapeQuotes(o.getExclude())
					+ "' ";
		}
		if (o.getFeatClass() != null && !o.getFeatClass().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " FEAT_CLASS = '"
					+ dbs.escapeQuotes(o.getFeatClass()) + "' ";
		}
		selectString += " ORDER BY NAME ";
		
		return getWeaponsFromSql(selectString);
	}
	
	public Vector<Weapon> getWeaponsFromSql(String selectString) {
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
				obj.setFeatClass(result.getString("FEAT_CLASS"));
				obj.setCritStart(result.getString("CRIT_START"));
				obj.setCritEnd(result.getString("CRIT_END"));
				obj.setCritMult(result.getString("CRIT_MULT"));
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

	public boolean exists(Weapon w) {
		String selectString = "SELECT 'x' FROM WEAPONS WHERE ID = " + w.getId();
		boolean exists = false;
		try {

			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}

			if (result.next()) {
				exists = true;
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
		return exists;
	}

	public Vector<String> getWeaponClasses() {
		String selectString = "SELECT distinct(FEAT_CLASS) FROM WEAPONS WHERE CATEGORY <> 'Ammunition' ORDER BY FEAT_CLASS";
		Vector<String> v = new Vector<String>();
		try {

			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}

			while (result.next()) {
				v.add(result.getString("FEAT_CLASS"));
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

	public Vector<Weapon> getAmmunition() {
		String selectString = "SELECT * FROM WEAPONS WHERE CATEGORY = 'Ammunition' ORDER BY NAME";
	
		return getWeaponsFromSql(selectString);
	}
		
	public Vector<Weapon> getWeapons(String keyword) {
		
		String queryString = "SELECT * FROM WEAPONS ";
		
		if (keyword != null && !keyword.equals("")) {
			queryString += "WHERE DESCRIPTION like '%" + keyword
					+ "%' OR NAME LIKE '%" + keyword + "%'";
		}

		queryString += " ORDER BY NAME";
		
		return getWeaponsFromSql(queryString);
	}	
	
	public Vector<Weapon> getWeapons(String keyword, Campaign c) {
		
		String queryString = "SELECT * FROM WEAPONS ";
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
			queryString += "SOURCE='"+cs.getName()+"'";
			
			queryString += ") ";
		}
		queryString += " ORDER BY NAME";
		
		return getWeaponsFromSql(queryString);
	}	

	public Vector<Weapon> getWeapons() {
		if (weapons != null) {
			return weapons;
		}

		String selectString = "SELECT * FROM WEAPONS WHERE CATEGORY <> 'Ammunition' ORDER BY NAME";
		Vector<Weapon> v = getWeaponsFromSql(selectString);

		weapons = v;
		return v;
	}

	public void deleteWeapon(Weapon o) {
		String deleteString = "delete from WEAPONS  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + "";

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			weapons = null;
			resetConnection();
		}
	}
}

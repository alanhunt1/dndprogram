package initcheck.database;

import initcheck.character.library.LibraryItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class WonderousItemsDAO extends InitBaseDAO implements LibraryItemDAO {

	public WonderousItemsDAO() {

	}

	public int addOrUpdateWonderousItem(Wonderous o) {
		int i = -1;
		if (o.getId() != null) {
			updateWonderousItems(o);
		} else {
			addWonderousItems(o);
		}
		try {
			// open the connection
			dbs.open();
			String command = "SELECT MAX(ID) as MAXID FROM WONDEROUS_ITEMS";
			
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

	public void addWonderousItems(Wonderous o) {
		String valueString = "";
		String insertString = "INSERT INTO WONDEROUS_ITEMS (";
		if (o.getId() != null) {
			insertString += "ID,";
			valueString += "'" + dbs.escapeQuotes(o.getId()) + "',";
		}
		if (o.getPrice() != null) {
			insertString += "price,";
			if (o.getPrice().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getPrice()) + ",";

			}
		}
		if (o.getCasterLevel() != null) {
			insertString += "caster_level,";
			if (o.getCasterLevel().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getCasterLevel()) + ",";

			}
		}
		if (o.getName() != null) {
			insertString += "name,";
			valueString += "'" + dbs.escapeQuotes(o.getName()) + "',";
		}
		if (o.getLevel() != null) {
			insertString += "ilevel,";
			valueString += "'" + dbs.escapeQuotes(o.getLevel()) + "',";
		}
		if (o.getDescription() != null) {
			insertString += "description,";
			valueString += "'" + dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getSpells() != null) {
			insertString += "spells,";
			valueString += "'" + dbs.escapeQuotes(o.getSpells()) + "',";
		}
		if (o.getWeight() != null) {
			insertString += "weight,";
			valueString += "'" + dbs.escapeQuotes(o.getWeight()) + "',";
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
		}
	}

	public void updateWonderousItems(Wonderous o) {
		String updateString = "update WONDEROUS_ITEMS set ";
		if (o.getPrice() != null) {
			if (!o.getPrice().equals("")) {
				updateString += "price=" + dbs.escapeQuotes(o.getPrice()) + ",";
			} else {
				updateString += "price=null,";
			}
		}
		if (o.getCasterLevel() != null) {
			if (!o.getCasterLevel().equals("")) {
				updateString += "caster_level="
						+ dbs.escapeQuotes(o.getCasterLevel()) + ",";
			} else {
				updateString += "caster_level=null,";
			}
		}
		if (o.getName() != null) {
			updateString += "name='" + dbs.escapeQuotes(o.getName()) + "',";
		}
		if (o.getLevel() != null) {
			updateString += "ilevel='" + dbs.escapeQuotes(o.getLevel()) + "',";
		}
		if (o.getDescription() != null) {
			updateString += "description='"
					+ dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getSpells() != null) {
			updateString += "spells='" + dbs.escapeQuotes(o.getSpells()) + "',";
		}
		if (o.getWeight() != null) {
			updateString += "weight='" + dbs.escapeQuotes(o.getWeight()) + "',";
		}
		if (o.getSource() != null) {
			updateString += "source='" + dbs.escapeQuotes(o.getSource()) + "',";
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + "";
		try {
			dbs.open();
			logger.log("Executing update : " + updateString);
			dbs.executeSQLCommand(updateString);
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
			resetConnection();
		}
	}

	public Vector<LibraryItem> getItems(LibraryItem i){
		String queryString = "SELECT * FROM WONDEROUS_ITEMS WHERE SOURCE = '"+i.getSource()+"' "+
		" ORDER BY NAME";
		Vector<LibraryItem> results = new Vector<LibraryItem>();
		results.addAll(getWonderousItemsFromSql(queryString));
		return results;
	}
	
	public Vector<Wonderous> getWonderousItems(String keyword) {
		String selectString = "SELECT * FROM WONDEROUS_ITEMS WHERE NAME "
				+ "LIKE '%" + keyword + "%' OR DESCRIPTION LIKE '%" + keyword
				+ "%' ORDER BY NAME";

		return getWonderousItemsFromSql(selectString);
		
	}
	
	public Vector<Wonderous> getWonderousItems (String keyword, Campaign c){
		
		String queryString = "SELECT * FROM WONDEROUS_ITEMS ";
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
			queryString += "SOURCE='"+cs.getName()+"'  OR EXISTS (SELECT 'x' FROM CAMPAIGN_WHITE_LIST WHERE CAMPAIGN_ID = "+
		c.getId()+ " AND XREF_ID = WONDEROUS_ITEMS.ID AND TYPE = 'Wonderous Item')";
			
			queryString += ") ";
		}
		queryString += " AND NOT EXISTS (SELECT 'x' FROM CAMPAIGN_BLACK_LIST WHERE CAMPAIGN_ID = "+
		c.getId()+ " AND XREF_ID = WONDEROUS_ITEMS.ID AND TYPE = 'Wonderous Item')";
		queryString += " ORDER BY NAME";
		
		return getWonderousItemsFromSql(queryString);
		
	}

	public Vector<Wonderous> getWonderousItems() {
		return selectWonderousItems(new Wonderous());
	}

	public Vector<Wonderous> selectWonderousItems(Wonderous o) {
		String selectString = "SELECT * FROM WONDEROUS_ITEMS  ";
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
		if (o.getPrice() != null && !o.getPrice().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " price = '" + dbs.escapeQuotes(o.getPrice())
					+ "' ";
		}
		if (o.getCasterLevel() != null && !o.getCasterLevel().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " caster_level = '"
					+ dbs.escapeQuotes(o.getCasterLevel()) + "' ";
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
		if (o.getLevel() != null && !o.getLevel().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ilevel = '" + dbs.escapeQuotes(o.getLevel())
					+ "' ";
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
		if (o.getSpells() != null && !o.getSpells().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " spells = '" + dbs.escapeQuotes(o.getSpells())
					+ "' ";
		}
		if (o.getSource() != null && !o.getSource().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " source = '" + dbs.escapeQuotes(o.getSource())
					+ "' ";
		}
		
		selectString += " ORDER BY NAME";
		return getWonderousItemsFromSql(selectString);
	}
	public Vector<Wonderous> getWonderousItemsFromSql(String selectString) {
		
		Vector<Wonderous> v = new Vector<Wonderous>();

		try {
			ResultSet result;

			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}

			while (result.next()) {
				Wonderous obj = new Wonderous();

				obj.setId(result.getString("ID"));
				obj.setPrice(result.getString("price"));
				obj.setCasterLevel(result.getString("caster_level"));
				obj.setName(result.getString("name"));
				obj.setLevel(result.getString("ilevel"));
				obj.setDescription(result.getString("description"));
				obj.setSpells(result.getString("spells"));
				obj.setWeight(result.getString("weight"));
				obj.setSource(result.getString("source"));
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

	public void deleteWonderousItems(Wonderous o) {
		String deleteString = "delete from WONDEROUS_ITEMS  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + " ";

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
			resetConnection();
		}
	}
}

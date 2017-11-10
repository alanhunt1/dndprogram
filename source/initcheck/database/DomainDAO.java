package initcheck.database;

import initcheck.character.library.LibraryItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class DomainDAO extends InitBaseDAO implements LibraryItemDAO {

	public DomainDAO() {

	}

	public int addOrUpdateDomain(Domain o) {
		if (o.getId() == null) {
			return addDomain(o);
		}
		updateDomain(o);
		return Integer.parseInt(o.getId());
	}

	public int addDomain(Domain o) {
		int i = -1;

		String valueString = "";
		String insertString = "INSERT INTO DOMAINS (";

		if (o.getDomainName() != null) {
			insertString += "DOMAIN_NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getDomainName()) + "',";
		}
		if (o.getDomainPower() != null) {
			insertString += "DOMAIN_POWER,";
			valueString += "'" + dbs.escapeQuotes(o.getDomainPower()) + "',";
		}
		if (o.getDescription() != null) {
			insertString += "DESCRIPTION,";
			valueString += "'" + dbs.escapeQuotes(o.getDescription()) + "',";
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

			insertString = "SELECT MAX(ID) as MAXID FROM DOMAIN ";
			ResultSet result = dbs.executeSQLQuery(insertString);
			if (result.next()) {
				i = Integer.parseInt(result.getString("MAXID"));
			}

			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
		}
		return i;
	}

	public void updateDomain(Domain o) {
		String updateString = "update DOMAINS set ";
		if (o.getDomainName() != null) {
			updateString += "DOMAIN_NAME='"
					+ dbs.escapeQuotes(o.getDomainName()) + "',";
		}
		if (o.getDomainPower() != null) {
			updateString += "DOMAIN_POWER='"
					+ dbs.escapeQuotes(o.getDomainPower()) + "',";
		}
		if (o.getDescription() != null) {
			updateString += "DESCRIPTION='"
					+ dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getSource() != null) {
			updateString += "SOURCE='"
				+ dbs.escapeQuotes(o.getSource()) + "',";
		}
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

	public Vector<Spell> getDomainSpells(Domain o) {
		return getDomainSpells(o.getId());
	}

	public Vector<Spell> getDomainSpells(String id) {
		String selectString = "SELECT s.*, d.ID as DOMID FROM SPELLS s, DOMAIN_SPELLS d "
				+ "WHERE s.ID = d.SPELL_ID AND d.DOMAIN_ID = " + id;

		selectString += " ORDER BY SPELL_NAME";
		Vector<Spell>  v = new Vector<Spell> ();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				Spell obj = new Spell();

				obj.setId(result.getString("ID"));
				obj.setSpellName(result.getString("SPELL_NAME"));
				obj.setSchool(result.getString("SCHOOL"));
				obj.setType(result.getString("TYPE"));
				obj.setSubType(result.getString("SUB_TYPE"));
				obj.setLevel(result.getString("CLEVEL"));
				obj.setComponents(result.getString("COMPONENTS"));
				obj.setTime(result.getString("CTIME"));
				obj.setRange(result.getString("CRANGE"));
				obj.setEffect(result.getString("EFFECT"));
				obj.setDuration(result.getString("DURATION"));
				obj.setSavingThrow(result.getString("SAVING_THROW"));
				obj.setResist(result.getString("RESIST"));
				obj.setDescription(result.getString("DESCRIPTION"));
				obj.setEffectType(result.getString("EFFECT_TYPE"));
				obj.setInstanceId(result.getString("DOMID"));
				
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

	public boolean exists(Domain o) {
		return selectDomain(o).size() > 0;
	}

	public Vector<Domain> getDomains() {
		Domain o = new Domain();
		return selectDomain(o);
	}

	public Vector<LibraryItem> getItems(LibraryItem i){
		String queryString = "SELECT * FROM DOMAINS WHERE SOURCE = '"+i.getSource()+"' "+
		" ORDER BY DOMAIN_NAME";
		Vector<LibraryItem> results = new Vector<LibraryItem>();
		results.addAll(getDomainsFromSql(queryString));
		return results;
	}
	
	public Vector<Domain> getDomains (String keyword){
		
		String queryString = "SELECT * FROM DOMAINS ";

		if (keyword != null && !keyword.equals("")) {
			queryString += "WHERE DESCRIPTION like '%" + keyword
					+ "%' OR DOMAIN_NAME LIKE '%" + keyword + "%'";
		}

		queryString += " ORDER BY DOMAIN_NAME";
		
		return getDomainsFromSql(queryString);
		
	}
	
	public Vector<Domain> getDomains (String keyword, Campaign c){
		
		String queryString = "SELECT * FROM DOMAINS ";
		boolean first = true;
		if (keyword != null && !keyword.equals("")) {
			queryString += "WHERE DESCRIPTION like '%" + keyword
					+ "%' OR DOMAIN_NAME LIKE '%" + keyword + "%'";
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
			queryString += "SOURCE='"+cs.getName()+"'  OR  EXISTS (SELECT 'x' FROM CAMPAIGN_WHITE_LIST WHERE CAMPAIGN_ID = "+
		c.getId()+ " AND XREF_ID = DOMAINS.ID AND TYPE = 'Domain')";
			
			queryString += ") ";
		}
		queryString += " AND NOT EXISTS (SELECT 'x' FROM CAMPAIGN_BLACK_LIST WHERE CAMPAIGN_ID = "+
		c.getId()+ " AND XREF_ID = DOMAINS.ID AND TYPE = 'Domain')";
		queryString += " ORDER BY DOMAIN_NAME";
		
		return getDomainsFromSql(queryString);
		
	}
	
	
	public Vector<Domain> selectDomain(Domain o) {
		String selectString = "SELECT * FROM DOMAINS  ";
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
		if (o.getDomainName() != null && !o.getDomainName().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " DOMAIN_NAME = '"
					+ dbs.escapeQuotes(o.getDomainName()) + "' ";
		}
		if (o.getDomainPower() != null && !o.getDomainPower().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " DOMAIN_POWER = '"
					+ dbs.escapeQuotes(o.getDomainPower()) + "' ";
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
		return getDomainsFromSql(selectString);
	}
	
	public Vector<Domain> getDomainsFromSql(String selectString) {
		Vector<Domain> v = new Vector<Domain>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				Domain obj = new Domain();

				obj.setId(result.getString("ID"));
				obj.setDomainName(result.getString("DOMAIN_NAME"));
				obj.setDomainPower(result.getString("DOMAIN_POWER"));
				obj.setDescription(result.getString("DESCRIPTION"));
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

	public void deleteDomain(Domain o) {
		String deleteString = "delete from DOMAINS  ";
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

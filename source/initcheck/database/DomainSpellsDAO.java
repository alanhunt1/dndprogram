package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class DomainSpellsDAO extends InitBaseDAO {

	public DomainSpellsDAO() {

	}

	public void addDomainSpells(DomainSpells o) {
		String valueString = "";
		String insertString = "INSERT INTO DOMAIN_SPELLS (";

		if (o.getDomainId() != null) {
			insertString += "DOMAIN_ID,";
			valueString += "" + dbs.escapeQuotes(o.getDomainId()) + ",";
		}
		if (o.getSpellId() != null) {
			insertString += "SPELL_ID,";
			valueString += "" + dbs.escapeQuotes(o.getSpellId()) + ",";
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

	public void updateDomainSpells(DomainSpells o) {
		String updateString = "update DOMAIN_SPELLS set ";
		if (o.getDomainId() != null) {
			updateString += "DOMAIN_ID=" + dbs.escapeQuotes(o.getDomainId())
					+ ",";
		}
		if (o.getSpellId() != null) {
			updateString += "SPELL_ID=" + dbs.escapeQuotes(o.getSpellId()) + "";
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

	public Vector<DomainSpells> selectDomainSpells(DomainSpells o) {
		String selectString = "SELECT * FROM DOMAIN_SPELLS  ";
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
		if (o.getDomainId() != null && !o.getDomainId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " DOMAIN_ID = " + dbs.escapeQuotes(o.getDomainId())
					+ " ";
		}
		if (o.getSpellId() != null && !o.getSpellId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SPELL_ID = " + dbs.escapeQuotes(o.getSpellId())
					+ " ";
		}
		Vector<DomainSpells> v = new Vector<DomainSpells>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				DomainSpells obj = new DomainSpells();

				obj.setId(result.getString("ID"));
				obj.setDomainId(result.getString("DOMAIN_ID"));
				obj.setSpellId(result.getString("SPELL_ID"));
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

	public boolean isDomainSpell(String DomainId, String spellId) {
		DomainSpells cs = new DomainSpells();
		cs.setDomainId(DomainId);
		cs.setSpellId(spellId);
		Vector<DomainSpells> v = selectDomainSpells(cs);
		return v.size() > 0;
	}

	public void clearDomainSpells(String domainId) {
		String deleteString = "delete from DOMAIN_SPELLS  ";
		deleteString += " WHERE DOMAIN_ID = " + domainId;

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

	public void deleteDomainSpells(Spell o) {
		String deleteString = "delete from DOMAIN_SPELLS  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getInstanceId())
				+ "";

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

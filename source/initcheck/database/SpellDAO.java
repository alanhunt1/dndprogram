package initcheck.database;

import initcheck.character.library.LibraryItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

public class SpellDAO extends InitBaseDAO implements LibraryItemDAO{

	private static HashMap<String, Vector<SpellLevel>> levelHash = null;

	private static HashMap<String, Vector<SpellClass>> classHash = null;

	public SpellDAO() {

	}

	public int addOrUpdateSpell(Spell o) {
		int i = -1;
		if (o.getId() != null) {
			updateSpell(o);
		} else {
			addSpell(o);
		}
		try {
			// open the connection
			dbs.open();
			String command = "SELECT MAX(ID) as MAXID FROM SPELLS";
			
			ResultSet result = dbs.executeSQLQuery(command);
			if (result.next()) {
				i = result.getInt("MAXID");
			}

		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}

		// close the connection
		dbs.close();
		levelHash = null;
		return i;
	}

	public void addSpell(Spell o) {
		String valueString = "";
		String insertString = "INSERT INTO SPELLS (";

		if (o.getSpellName() != null) {
			insertString += "SPELL_NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getSpellName()) + "',";
		}
		if (o.getSchool() != null) {
			insertString += "SCHOOL,";
			valueString += "'" + dbs.escapeQuotes(o.getSchool()) + "',";
		}
		if (o.getType() != null) {
			insertString += "TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getType()) + "',";
		}
		if (o.getSubType() != null) {
			insertString += "SUB_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getSubType()) + "',";
		}
		if (o.getLevel() != null) {
			insertString += "CLEVEL,";
			valueString += "'" + dbs.escapeQuotes(o.getLevel()) + "',";
		}
		if (o.getComponents() != null) {
			insertString += "COMPONENTS,";
			valueString += "'" + dbs.escapeQuotes(o.getComponents()) + "',";
		}
		if (o.getTime() != null) {
			insertString += "CTIME,";
			valueString += "'" + dbs.escapeQuotes(o.getTime()) + "',";
		}
		if (o.getRange() != null) {
			insertString += "CRANGE,";
			valueString += "'" + dbs.escapeQuotes(o.getRange()) + "',";
		}
		if (o.getEffect() != null) {
			insertString += "EFFECT,";
			valueString += "'" + dbs.escapeQuotes(o.getEffect()) + "',";
		}
		if (o.getDuration() != null) {
			insertString += "DURATION,";
			valueString += "'" + dbs.escapeQuotes(o.getDuration()) + "',";
		}
		if (o.getSavingThrow() != null) {
			insertString += "SAVING_THROW,";
			valueString += "'" + dbs.escapeQuotes(o.getSavingThrow()) + "',";
		}
		if (o.getResist() != null) {
			insertString += "RESIST,";
			valueString += "'" + dbs.escapeQuotes(o.getResist()) + "',";
		}
		if (o.getDescription() != null) {
			insertString += "DESCRIPTION,";
			valueString += "'" + dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getEffectType() != null) {
			insertString += "EFFECT_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getEffectType()) + "',";
		}
		if (o.getShortDesc() != null) {
			insertString += "SHORT_DESC,";
			valueString += "'" + dbs.escapeQuotes(o.getShortDesc()) + "',";
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
			
			dbs.executeSQLCommand(insertString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
			e.printStackTrace();
		} finally {
			resetConnection();
		}
	}

	public void updateSpell(Spell o) {
		String updateString = "update SPELLS set ";
		if (o.getSpellName() != null) {
			updateString += "SPELL_NAME='" + dbs.escapeQuotes(o.getSpellName())
					+ "',";
		}
		if (o.getSchool() != null) {
			updateString += "SCHOOL='" + dbs.escapeQuotes(o.getSchool()) + "',";
		}
		if (o.getType() != null) {
			updateString += "TYPE='" + dbs.escapeQuotes(o.getType()) + "',";
		}
		if (o.getSubType() != null) {
			updateString += "SUB_TYPE='" + dbs.escapeQuotes(o.getSubType())
					+ "',";
		}
		if (o.getLevel() != null) {
			updateString += "CLEVEL='" + dbs.escapeQuotes(o.getLevel()) + "',";
		}
		if (o.getComponents() != null) {
			updateString += "COMPONENTS='"
					+ dbs.escapeQuotes(o.getComponents()) + "',";
		}
		if (o.getTime() != null) {
			updateString += "CTIME='" + dbs.escapeQuotes(o.getTime()) + "',";
		}
		if (o.getRange() != null) {
			updateString += "CRANGE='" + dbs.escapeQuotes(o.getRange()) + "',";
		}
		if (o.getEffect() != null) {
			updateString += "EFFECT='" + dbs.escapeQuotes(o.getEffect()) + "',";
		}
		if (o.getDuration() != null) {
			updateString += "DURATION='" + dbs.escapeQuotes(o.getDuration())
					+ "',";
		}
		if (o.getSavingThrow() != null) {
			updateString += "SAVING_THROW='"
					+ dbs.escapeQuotes(o.getSavingThrow()) + "',";
		}
		if (o.getResist() != null) {
			updateString += "RESIST='" + dbs.escapeQuotes(o.getResist()) + "',";
		}
		if (o.getDescription() != null) {
			updateString += "DESCRIPTION='"
					+ dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getEffectType() != null) {
			updateString += "EFFECT_TYPE='"
					+ dbs.escapeQuotes(o.getEffectType()) + "',";
		}
		if (o.getShortDesc() != null) {
			updateString += "SHORT_DESC='" + dbs.escapeQuotes(o.getShortDesc())
					+ "',";
		}
		if (o.getSource() != null) {
			updateString += "SOURCE='" + dbs.escapeQuotes(o.getSource())
					+ "',";
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

	@SuppressWarnings("unchecked")
	public Vector<Spell> getSpells(String level, String cclass) {
		if (levelHash == null) {
			levelHash = new HashMap();
		}

		String levelString = cclass + " " + level;
		Object o = levelHash.get(levelString);
		if (o == null) {
			String selectString = "SELECT s.* FROM SPELLS s, SPELL_CLASSES sc "+
			"WHERE sc.SPELL_CLASS LIKE '%"
					+ cclass + "%' AND sc.SPELL_LEVEL = "+level+" AND s.id = sc.spell_id ORDER BY SPELL_NAME";
			Vector v = selectSpells(selectString);
			levelHash.put(levelString, v);
			o = v;
		}
		return (Vector) o;
	}

	public Vector<Spell> getSpells() {
		Spell s = new Spell();
		return selectSpell(s);
	}

	public Vector<LibraryItem> getItems(LibraryItem i){
		String queryString = "SELECT * FROM SPELLS WHERE SOURCE = '"+i.getSource()+"' "+
		" ORDER BY SPELL_NAME";
		Vector<LibraryItem> results = new Vector<LibraryItem>();
		results.addAll(selectSpells(queryString));
		return results;
	}
	
	public Vector<Spell> getSpells(String s) {
		String selectString = "SELECT * FROM SPELLS WHERE DESCRIPTION LIKE '%"
				+ s + "%' OR SPELL_NAME LIKE '%"
				+ s + "%' ORDER BY SPELL_NAME";
		return selectSpells(selectString);
	}

	public Vector<Spell> getSpells (String keyword, Campaign c){
		
		String queryString = "SELECT * FROM SPELLS ";
		boolean first = true;
		if (keyword != null && !keyword.equals("")) {
			queryString += "WHERE DESCRIPTION like '%" + keyword
					+ "%' OR SPELL_NAME LIKE '%" + keyword + "%'";
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
			queryString += "SOURCE='"+cs.getName()+"' OR "+
			"EXISTS (SELECT 'x' FROM CAMPAIGN_WHITE_LIST WHERE CAMPAIGN_ID = "+
					c.getId()+ " AND XREF_ID = SPELLS.ID AND TYPE = 'Spell')";
			
			
			queryString += ") ";
		}
		queryString += " AND NOT EXISTS (SELECT 'x' FROM CAMPAIGN_BLACK_LIST WHERE CAMPAIGN_ID = "+
		c.getId()+ " AND XREF_ID = SPELLS.ID AND TYPE = 'Spell')";
		
		queryString += " ORDER BY SPELL_NAME";
		
		return selectSpells(queryString);
		
	}
	
	
	public boolean exists(Spell o) {
		return selectSpell(o).size() > 0;
	}

	public Vector<Spell> selectSpell(Spell o) {
		String selectString = "SELECT * FROM SPELLS  ";
		boolean first = true;
		if (o.getId() != null && !o.getId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ID = " + dbs.escapeQuotes(o.getId()) + "";
		}
		if (o.getSpellName() != null && !o.getSpellName().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SPELL_NAME = '"
					+ dbs.escapeQuotes(o.getSpellName()) + "' ";
		}
		if (o.getSchool() != null && !o.getSchool().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SCHOOL = '" + dbs.escapeQuotes(o.getSchool())
					+ "' ";
		}
		if (o.getType() != null && !o.getType().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " TYPE = '" + dbs.escapeQuotes(o.getType()) + "' ";
		}
		if (o.getSubType() != null && !o.getSubType().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SUB_TYPE = '" + dbs.escapeQuotes(o.getSubType())
					+ "' ";
		}
		if (o.getLevel() != null && !o.getLevel().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " CLEVEL = '" + dbs.escapeQuotes(o.getLevel())
					+ "' ";
		}
		if (o.getComponents() != null && !o.getComponents().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " COMPONENTS = '"
					+ dbs.escapeQuotes(o.getComponents()) + "' ";
		}
		if (o.getTime() != null && !o.getTime().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " CTIME = '" + dbs.escapeQuotes(o.getTime()) + "' ";
		}
		if (o.getRange() != null && !o.getRange().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " CRANGE = '" + dbs.escapeQuotes(o.getRange())
					+ "' ";
		}
		if (o.getEffect() != null && !o.getEffect().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " EFFECT = '" + dbs.escapeQuotes(o.getEffect())
					+ "' ";
		}
		if (o.getDuration() != null && !o.getDuration().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " DURATION = '" + dbs.escapeQuotes(o.getDuration())
					+ "' ";
		}
		if (o.getSavingThrow() != null && !o.getSavingThrow().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SAVING_THROW = '"
					+ dbs.escapeQuotes(o.getSavingThrow()) + "' ";
		}
		if (o.getResist() != null && !o.getResist().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " RESIST = '" + dbs.escapeQuotes(o.getResist())
					+ "' ";
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
		if (o.getEffectType() != null && !o.getEffectType().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " EFFECT_TYPE = '"
					+ dbs.escapeQuotes(o.getEffectType()) + "' ";
		}
		
		if (o.getSource() != null && !o.getSource().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SOURCE = '"
					+ dbs.escapeQuotes(o.getSource()) + "' ";
		}

		selectString += " ORDER BY SPELL_NAME";
		return selectSpells(selectString);
	}

	private Vector<Spell> selectSpells(String selectString) {
		Vector<Spell> v = new Vector<Spell>();
		try {

			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}

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
				obj.setShortDesc(result.getString("SHORT_DESC"));
				obj.setSpellClasses(getSpellClasses(obj.getId()));
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
			} else {
				dbs2.cleanup();
			}
		}
		return v;
	}

	public void resetClassHash() {
		classHash = null;
	}
	
	@SuppressWarnings("unchecked")
	public Vector<SpellClass> getSpellClasses(String id) {
		
		Vector<SpellClass> classes = new Vector<SpellClass>();
		if (classHash == null) {
			
			classHash = new HashMap();
			SpellClassesDAO scdb = new SpellClassesDAO();
			Vector v = scdb.getSpellClasses();
			for (int i = 0; i < v.size(); i++) {
				SpellClass sc = (SpellClass) v.get(i);
				Object prehash = classHash.get(sc.getSpellId());
				if (prehash != null) {
					((Vector) prehash).add(sc);
					// classHash.put(id, prehash);
				} else {
					Vector v2 = new Vector();
					v2.add(sc);
					classHash.put(sc.getSpellId(), v2);
				}
			}

		}
		Object v = classHash.get(id);
		if (v != null) {
			classes = (Vector) v;
		}

		return classes;
	}

	public void deleteSpell(Spell o) {
		String deleteString = "delete from SPELLS  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + "";

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
			levelHash = null;
		}
	}
}

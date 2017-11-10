package initcheck.database;

import initcheck.character.library.LibraryItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

public class CharClassDAO extends InitBaseDAO implements LibraryItemDAO {

	private static Vector<CharClass> classVector = null;

	private static HashMap<String, ClassAdvancement> levelHash = null;

	public CharClassDAO() {

	}

	public CharClassDAO(DBSession dbs2) {
		this.dbs2 = dbs2;
	}

	public int addOrUpdateCharClass(CharClass o) {
		int i = -1;
		if (o.getId() != null) {
			i = Integer.parseInt(o.getId());
			updateCharClass(o);
		} else {
			addCharClass(o);
			try {
				// open the connection
				dbs.open();
				String command = "SELECT MAX(ID) AS MAXID FROM CLASS";
				
				logger.log("EXECUTING COMMAND " + command);
				ResultSet result = dbs.executeSQLQuery(command);
				if (result.next()) {
					i = result.getInt("MAXID");
				}

			} catch (Exception uhe) {
				logger.log("ERROR : " + uhe.toString());
			} finally {
				// close the connection
				dbs.close();
			}
		}
		

		return i;
	}

	public Vector<Skill> getClassSkills(CharClass cclass) {
		logger.log("GETTING CLASS SKILLS FROM CHARCLASS");
		Vector<Skill> skills = new Vector<Skill>();

		ClassSkillsDAO csdb = new ClassSkillsDAO();
		ClassSkills cs = new ClassSkills();
		cs.setClassId(cclass.getId());
		Vector<ClassSkills> v = csdb.selectClassSkills(cs);

		SkillDAO sdb = new SkillDAO();

		for (int i = 0; i < v.size(); i++) {
			Skill sk = sdb.getSkill(((ClassSkills) v.get(i)).getSkillId());
			skills.add(sk);
		}

		return skills;
	}


	public ClassAdvancement getAdvancement(CharClass cclass, int level) {
		if (levelHash == null) {
			levelHash = new HashMap<String,ClassAdvancement>();
		}
		String hashKey = cclass.getId() + level;
		Object o = levelHash.get(hashKey);
		if (o == null) {
			ClassAdvancementDAO cadb;
			if (dbs2 == null) {
				cadb = new ClassAdvancementDAO();
			} else {
				cadb = new ClassAdvancementDAO(dbs2);
			}
			ClassAdvancement ca = new ClassAdvancement();
			ca.setClassId(cclass.getId());
			ca.setLevel("" + level);
			ca.setAlternate("N");
			Vector<ClassAdvancement> v2 = cadb.selectClassAdvancement(ca);
			ca = (ClassAdvancement) v2.get(0);
			levelHash.put(hashKey, ca);
			o = ca;
		}
		return (ClassAdvancement) o;
	}

	public ClassAdvancement getAlternateAdvancement(CharClass cclass, int level) {

		ClassAdvancementDAO cadb;
		if (dbs2 == null) {
			cadb = new ClassAdvancementDAO();
		} else {
			cadb = new ClassAdvancementDAO(dbs2);
		}

		ClassAdvancement ca = new ClassAdvancement();
		ca.setClassId(cclass.getId());
		ca.setLevel("" + level);
		ca.setAlternate("Y");
		Vector<ClassAdvancement> v2 = cadb.selectClassAdvancement(ca);
		ca = (ClassAdvancement) v2.get(0);
		return ca;
	}

	public Vector<CharClassPrereq> getCharClassPrereqs(String id) {
		Vector<CharClassPrereq> v = new Vector<CharClassPrereq>();
		try {
			// open the connection
			dbs.open();

			String queryString = "SELECT * FROM CLASS_PREREQ WHERE CLASS_ID = '"
					+ id + "'";
			
			ResultSet result = dbs.executeSQLQuery(queryString);
			
			while (result.next()) {

				CharClassPrereq obj = new CharClassPrereq();
				obj.setId(result.getString("ID"));
				obj.setPrereqType(result.getString("PREREQ_TYPE"));
				obj.setPrereqName(result.getString("PREREQ_NAME"));
				obj.setPrereqVal(result.getString("PREREQ_VAL"));
				obj.setCharClassId(result.getString("CLASS_ID"));
				obj.setChained(result.getString("CHAINED"));
				obj.setChainedTo(result.getString("CHAINED_TO"));

				v.add(obj);
			}

			dbs.close();

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		return v;
	}

	public void clearPrereqs(String featId) {
		try {
			// open the connection
			dbs.open();
			String command = "DELETE FROM CLASS_PREREQ WHERE CLASS_ID='"
					+ featId + "'";

			logger.log("EXECUTING COMMAND " + command);
			dbs.executeSQLCommand(command);
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}

		// close the connection
		dbs.close();
	}

	public int addOrUpdateCharClassPrereq(CharClassPrereq f) {
		int i = -1;

		try {
			// open the connection
			dbs.open();
			String command = "";

			command = "INSERT INTO CLASS_PREREQ (PREREQ_TYPE, PREREQ_NAME, "
					+ "PREREQ_VAL, CLASS_ID, CHAINED, CHAINED_TO) VALUES ('"
					+ dbs.escapeQuotes(f.getPrereqType()) + "'," + "'"
					+ dbs.escapeQuotes(f.getPrereqName()) + "','"
					+ f.getPrereqVal() + "','" + f.getCharClassId() + "','"
					+ f.getChained() + "','" + f.getChainedTo() + "')";

			logger.log("EXECUTING COMMAND " + command);
			dbs.executeSQLCommand(command);

			command = "SELECT MAX(ID) AS MAXID FROM CLASS_PREREQ";
			logger.log("EXECUTING COMMAND " + command);
			ResultSet result = dbs.executeSQLQuery(command);
			if (result.next()) {
				i = result.getInt("MAXID");
				
			} else {
				
			}

		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		} finally {
			dbs.close();
			resetConnection();
		}

		return i;
	}

	public void addCharClass(CharClass o) {
		String valueString = "";
		String insertString = "INSERT INTO CLASS (";
		
		if (o.getCharClass() != null) {
			insertString += "CharClass,";
			valueString += "'" + dbs.escapeQuotes(o.getCharClass()) + "',";
		}
		if (o.getDescription() != null) {
			insertString += "Description,";
			valueString += "'" + dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getHitdie() != null) {
			insertString += "Hitdie,";
			valueString += "'" + dbs.escapeQuotes(o.getHitdie()) + "',";
		}
		if (o.getPrestige() != null) {
			insertString += "Prestige,";
			valueString += "'" + dbs.escapeQuotes(o.getPrestige()) + "',";
		}
		if (o.getSkillPoints() != null) {
			insertString += "SKILL_POINTS,";
			valueString += "'" + dbs.escapeQuotes(o.getSkillPoints()) + "',";
		}
		if (o.getAlternateAdvance() != null) {
			insertString += "ALTERNATE_ADVANCE,";
			valueString += "'" + dbs.escapeQuotes(o.getAlternateAdvance())
					+ "',";
		}

		insertString += "ARCANE_CASTER,";
		valueString += o.isArcaneCaster() + ",";

		insertString += "DIVINE_CASTER,";
		valueString += o.isDivineCaster() + ",";

		insertString += "UNPREPARED_CASTER,";
		valueString += o.isUnpreparedCaster() + ",";

		if (o.getTurningLevelMod() != null && !o.getTurningLevelMod().equals("")) {
			insertString += "TURNING_LEVEL_MOD,";
			valueString += "" + dbs.escapeQuotes(o.getTurningLevelMod()) + ",";
		}
		if (o.getRulesetId() != null && !o.getRulesetId().equals("")) {
			insertString += "RULESET_ID,";
			valueString += "" + dbs.escapeQuotes(o.getRulesetId()) + ",";
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
			classVector = null;
			levelHash = null;
		}
	}

	public void updateCharClass(CharClass o) {
		String updateString = "update CLASS set ";
		if (o.getCharClass() != null) {
			updateString += "CharClass='" + dbs.escapeQuotes(o.getCharClass())
					+ "',";
		}
		if (o.getDescription() != null) {
			updateString += "Description='"
					+ dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getHitdie() != null) {
			updateString += "Hitdie='" + dbs.escapeQuotes(o.getHitdie()) + "',";
		}
		if (o.getPrestige() != null) {
			updateString += "Prestige='" + dbs.escapeQuotes(o.getPrestige())
					+ "',";
		}
		if (o.getSkillPoints() != null) {
			updateString += "SKILL_POINTS='"
					+ dbs.escapeQuotes(o.getSkillPoints()) + "',";
		}
		if (o.getAlternateAdvance() != null) {
			updateString += "ALTERNATE_ADVANCE='"
					+ dbs.escapeQuotes(o.getAlternateAdvance()) + "',";
		}

		updateString += "ARCANE_CASTER=" + o.isArcaneCaster() + ",";
		updateString += "DIVINE_CASTER=" + o.isDivineCaster() + ",";
		updateString += "UNPREPARED_CASTER=" + o.isUnpreparedCaster() + ",";

		if (o.getTurningLevelMod() != null && !o.getTurningLevelMod().equals("")) {
			updateString += "TURNING_LEVEL_MOD="
					+ dbs.escapeQuotes(o.getTurningLevelMod()) + ",";
		}
		if (o.getRulesetId() != null && !o.getRulesetId().equals("")) {
			updateString += "RULESET_ID="
					+ dbs.escapeQuotes(o.getRulesetId()) + ",";
		}
		if (o.getSource() != null) {
			updateString += "SOURCE='"
					+ dbs.escapeQuotes(o.getSource()) + "',";
		}
		
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}

		updateString += " WHERE ID = " + dbs.escapeQuotes(o.getId());

		try {
			dbs.open();
			logger.log("EXECUTING " + updateString);
			dbs.executeSQLCommand(updateString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
			classVector = null;
			levelHash = null;
		}
	}

	public Vector<CharClass> getClasses() {
		if (classVector != null) {
			return classVector;
		}
		classVector = selectCharClass(new CharClass(), "CHARCLASS");
		return classVector;
	}

	public Vector<LibraryItem> getItems(LibraryItem i){
		String queryString = "SELECT * FROM CLASS WHERE SOURCE = '"+i.getSource()+"' "+
		" ORDER BY CHARCLASS";
		Vector<LibraryItem> results = new Vector<LibraryItem>();
		results.addAll(getCharClassFromSql(queryString));
		return results;
	}
	
	public Vector<CharClass> getClasses (String keyword){
		
		String queryString = "SELECT * FROM CLASS ";

		if (keyword != null && !keyword.equals("")) {
			queryString += "WHERE DESCRIPTION like '%" + keyword
					+ "%' OR CHARCLASS LIKE '%" + keyword + "%'";
		}

		queryString += " ORDER BY CHARCLASS";
		
		return getCharClassFromSql(queryString);
		
	}
	
	public Vector<CharClass> getClasses (String keyword, Campaign c){
		
		String queryString = "SELECT * FROM CLASS ";
		boolean first = true;

		if (keyword != null && !keyword.equals("")) {
			queryString += "WHERE DESCRIPTION like '%" + keyword
					+ "%' OR CHARCLASS LIKE '%" + keyword + "%'";
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
		queryString += " AND NOT EXISTS (SELECT 'x' FROM CAMPAIGN_BLACK_LIST WHERE CAMPAIGN_ID = "+
		c.getId()+ " AND XREF_ID = CLASS.ID AND TYPE = 'Class')";
		
		queryString += " ORDER BY CHARCLASS";
		
		return getCharClassFromSql(queryString);
		
	}
	
	public Vector<CharClass> selectCharClass(CharClass o) {
		return selectCharClass(o, "CHARCLASS");
	}

	public boolean exists(CharClass o) {
		CharClass c = new CharClass();
		c.setId(o.getId());
		return selectCharClass(c, "CHARCLASS").size() > 0;
	}

	public Vector<CharClass> selectCharClass(CharClass o, String order) {
		String selectString = "SELECT * FROM CLASS  ";
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
		if (o.getCharClass() != null && !o.getCharClass().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " CharClass = '"
					+ dbs.escapeQuotes(o.getCharClass()) + "' ";
		}
		if (o.getDescription() != null && !o.getDescription().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " Description = '"
					+ dbs.escapeQuotes(o.getDescription()) + "' ";
		}
		if (o.getHitdie() != null && !o.getHitdie().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " Hitdie = '" + dbs.escapeQuotes(o.getHitdie())
					+ "' ";
		}
		if (o.getPrestige() != null && !o.getPrestige().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " Prestige = '" + dbs.escapeQuotes(o.getPrestige())
					+ "' ";
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
		if (o.getRulesetId() != null && !o.getRulesetId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " RULESET_ID = '" + dbs.escapeQuotes(o.getRulesetId())
					+ "' ";
		}
		if (order != null && !order.equals("")) {
			selectString += " ORDER BY " + order;
		}
	
		return getCharClassFromSql(selectString);
	}
	
	public Vector<CharClass> getCharClassFromSql(String selectString) {
		
		Vector<CharClass> v = new Vector<CharClass>();
		try {
			
			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}
			
			while (result.next()) {
				CharClass obj = new CharClass();

				obj.setId(result.getString("ID"));
				obj.setCharClass(result.getString("CharClass"));
				obj.setDescription(result.getString("Description"));
				obj.setHitdie(result.getString("Hitdie"));
				obj.setPrestige(result.getString("Prestige"));
				obj.setSkillPoints(result.getString("SKILL_POINTS"));
				obj.setAlternateAdvance(result.getString("ALTERNATE_ADVANCE"));
				obj.setDivineCaster(result.getBoolean("DIVINE_CASTER"));
				obj.setArcaneCaster(result.getBoolean("ARCANE_CASTER"));
				obj.setUnpreparedCaster(result.getBoolean("UNPREPARED_CASTER"));
				obj.setTurningLevelMod(result.getString("TURNING_LEVEL_MOD"));
				obj.setRulesetId(result.getString("RULESET_ID"));
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

	public CharClass getCharClass(String id) {
		String selectString = "SELECT * FROM CLASS WHERE ID = " + id;
		Vector<CharClass> v = getCharClassFromSql(selectString);
		
		if (v.size() > 0){
			return v.get(0);
		}
		return new CharClass();
	}

	public void deleteCharClass(CharClass o) {
		String deleteString = "delete from CLASS  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getId());

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
			classVector = null;
			levelHash = null;
		}
	}
}

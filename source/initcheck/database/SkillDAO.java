package initcheck.database;

import initcheck.character.library.LibraryItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class SkillDAO extends InitBaseDAO implements LibraryItemDAO {

	private static Vector<Skill> skillVector = null;

	public SkillDAO() {

	}

	public void addOrUpdateSkill(Skill o) {
		if (o.getId() != null) {
			updateSkill(o);
		} else {
			addSkill(o);
		}
	}

	public void addSkill(Skill o) {
		String valueString = "";
		String insertString = "INSERT INTO SKILLS (";
		if (o.getId() != null) {
			insertString += "ID,";
			valueString += "'" + dbs.escapeQuotes(o.getId()) + "',";
		}
		if (o.getSkill() != null) {
			insertString += "Skill,";
			valueString += "'" + dbs.escapeQuotes(o.getSkill()) + "',";
		}
		if (o.getAbility() != null) {
			insertString += "Ability,";
			valueString += "'" + dbs.escapeQuotes(o.getAbility()) + "',";
		}
		if (o.getDescription() != null) {
			insertString += "Description,";
			valueString += "'" + dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getTrainedOnly() != null) {
			insertString += "TrainedOnly,";
			valueString += "'" + dbs.escapeQuotes(o.getTrainedOnly()) + "',";
		}
		if (o.getClass1() != null) {
			insertString += "Class1,";
			valueString += "'" + dbs.escapeQuotes(o.getClass1()) + "',";
		}
		if (o.getClass2() != null) {
			insertString += "Class2,";
			valueString += "'" + dbs.escapeQuotes(o.getClass2()) + "',";
		}
		if (o.getClass3() != null) {
			insertString += "Class3,";
			valueString += "'" + dbs.escapeQuotes(o.getClass3()) + "',";
		}
		if (o.getClass4() != null) {
			insertString += "Class4,";
			valueString += "'" + dbs.escapeQuotes(o.getClass4()) + "',";
		}
		if (o.getClass5() != null) {
			insertString += "Class5,";
			valueString += "'" + dbs.escapeQuotes(o.getClass5()) + "',";
		}
		if (o.getArmorCheck() != null) {
			insertString += "ArmorCheck,";
			valueString += "'" + dbs.escapeQuotes(o.getArmorCheck()) + "',";
		}

		
		insertString += "SYN_BONUS,";
		valueString += dbs.escapeQuotes(o.getSynBonus()) + ",";

		insertString += "SYN_VALUE,";
		valueString += dbs.escapeQuotes(o.getSynValue()) + ",";

		insertString += "SYN_RANKS,";
		valueString += dbs.escapeQuotes(o.getSynRanks()) + ",";

		insertString += "SYN_BONUS2,";
		valueString += dbs.escapeQuotes(o.getSynBonus2()) + ",";

		insertString += "SYN_VALUE2,";
		valueString += dbs.escapeQuotes(o.getSynValue2()) + ",";

		insertString += "SYN_RANKS2,";
		valueString += dbs.escapeQuotes(o.getSynRanks2()) + ",";

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
			skillVector = null;
		}
	}

	public void updateSkill(Skill o) {
		String updateString = "update SKILLS set ";
		if (o.getSkill() != null) {
			updateString += "Skill='" + dbs.escapeQuotes(o.getSkill()) + "',";
		}
		if (o.getAbility() != null) {
			updateString += "Ability='" + dbs.escapeQuotes(o.getAbility())
					+ "',";
		}
		if (o.getDescription() != null) {
			updateString += "Description='"
					+ dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getTrainedOnly() != null) {
			updateString += "TrainedOnly='"
					+ dbs.escapeQuotes(o.getTrainedOnly()) + "',";
		}
		if (o.getClass1() != null) {
			updateString += "Class1='" + dbs.escapeQuotes(o.getClass1()) + "',";
		}
		if (o.getClass2() != null) {
			updateString += "Class2='" + dbs.escapeQuotes(o.getClass2()) + "',";
		}
		if (o.getClass3() != null) {
			updateString += "Class3='" + dbs.escapeQuotes(o.getClass3()) + "',";
		}
		if (o.getClass4() != null) {
			updateString += "Class4='" + dbs.escapeQuotes(o.getClass4()) + "',";
		}
		if (o.getClass5() != null) {
			updateString += "Class5='" + dbs.escapeQuotes(o.getClass5()) + "',";
		}
		if (o.getArmorCheck() != null) {
			updateString += "ArmorCheck='"
					+ dbs.escapeQuotes(o.getArmorCheck()) + "',";
		}

		updateString += "SYN_BONUS=" + dbs.escapeQuotes(o.getSynBonus()) + ",";
		updateString += "SYN_VALUE=" + dbs.escapeQuotes(o.getSynValue()) + ",";
		updateString += "SYN_RANKS=" + dbs.escapeQuotes(o.getSynRanks()) + ",";
		updateString += "SYN_BONUS2=" + dbs.escapeQuotes(o.getSynBonus2())
				+ ",";
		updateString += "SYN_VALUE2=" + dbs.escapeQuotes(o.getSynValue2())
				+ ",";
		updateString += "SYN_RANKS2=" + dbs.escapeQuotes(o.getSynRanks2())
				+ ",";

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
			logger.log("Executing Update " + updateString);
			dbs.executeSQLCommand(updateString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
			skillVector = null;
		}
	}

	public Skill getSkillByName(String s) {
		Skill newSkill = new Skill();
		newSkill.setSkill(s);
		newSkill.setTrainedOnly(null);
		newSkill.setArmorCheck(null);
		Vector<Skill> v = selectSkill(newSkill);
		if (v.size() > 0) {
			return (Skill) (v.get(0));
		} else {
			return newSkill;
		}
	}
	
	public Skill getSkill(String id) {
		Skill newSkill = new Skill();
		newSkill.setId(id);
		newSkill.setTrainedOnly(null);
		newSkill.setArmorCheck(null);
		Vector<Skill> v = selectSkill(newSkill);
		if (v.size() > 0) {
			return (Skill) (v.get(0));
		} else {
			return newSkill;
		}
	}

	public Vector<LibraryItem> getItems(LibraryItem i){
		String queryString = "SELECT * FROM SKILLS WHERE SOURCE = '"+i.getSource()+"' "+
		" ORDER BY SKILL";
		Vector<LibraryItem> results = new Vector<LibraryItem>();
		results.addAll(getSkillsFromSql(queryString));
		return results;
	}
	
	public Vector<Skill> getSkills(String keyword, Campaign c) {
		boolean first = true;
		String queryString = "SELECT * FROM SKILLS ";
		
		if (keyword != null && !keyword.equals("")) {
			queryString += "WHERE SKILL LIKE '%"
				+ keyword + "%' OR DESCRIPTION LIKE '%" + keyword
				+ "%' ";
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
		c.getId()+ " AND XREF_ID = SKILLS.ID AND TYPE = 'Skill')";
			
			queryString += ") ";
		}
		queryString += " AND NOT EXISTS (SELECT 'x' FROM CAMPAIGN_BLACK_LIST WHERE CAMPAIGN_ID = "+
		c.getId()+ " AND XREF_ID = SKILLS.ID AND TYPE = 'Skill')";
		queryString += " ORDER BY SKILL";


		return getSkillsFromSql(queryString);
	}
	
	public Vector<Skill> getSkills(String keyword) {
		String selectString = "SELECT * FROM SKILLS WHERE SKILL " + "LIKE '%"
				+ keyword + "%' OR DESCRIPTION LIKE '%" + keyword
				+ "%' ORDER BY SKILL";

		
		return getSkillsFromSql(selectString);
	}

	public Vector<Skill> getSkills() {
		if (skillVector != null) {
			return skillVector;
		}
		String selectString = "SELECT * FROM SKILLS ORDER BY SKILL";

		Vector<Skill> v = getSkillsFromSql(selectString);
		skillVector = v;
		return v;
	}

	public Vector<Skill> getSkillsFromSql(String selectString) {
		Vector<Skill> v = new Vector<Skill>();
		try {	

			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}

			while (result.next()) {

				Skill obj = new Skill();

				obj.setId(result.getString("ID"));
				obj.setSkill(result.getString("Skill"));
				obj.setAbility(result.getString("Ability"));
				obj.setDescription(result.getString("Description"));
				obj.setTrainedOnly(result.getString("TrainedOnly"));
				obj.setClass1(result.getString("Class1"));
				obj.setClass2(result.getString("Class2"));
				obj.setClass3(result.getString("Class3"));
				obj.setClass4(result.getString("Class4"));
				obj.setClass5(result.getString("Class5"));
				obj.setArmorCheck(result.getString("ArmorCheck"));
				obj.setSynBonus(result.getString("SYN_BONUS"));
				obj.setSynValue(result.getString("SYN_VALUE"));
				obj.setSynRanks(result.getString("SYN_RANKS"));
				obj.setSynBonus2(result.getString("SYN_BONUS2"));
				obj.setSynValue2(result.getString("SYN_VALUE2"));
				obj.setSynRanks2(result.getString("SYN_RANKS2"));
				obj.setSource(result.getString("SOURCE"));
				v.add(obj);
			}

		} catch (SQLException e) {
			logger.log("ERROR : " + e.toString());
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		} finally {
			if (dbs2 == null) {
				dbs.close();
			} else {
				dbs2.cleanup();
			}
		}
		return v;
	}
	
	public boolean exists(Skill o) {
		return selectSkill(o).size() > 0;
	}

	public Vector<Skill> selectSkill(Skill o) {
		String selectString = "SELECT * FROM SKILLS  ";
		boolean first = true;
		if (o.getId() != null && !o.getId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ID = " + dbs.escapeQuotes(o.getId());
		}
		if (o.getSkill() != null && !o.getSkill().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " Skill = '" + dbs.escapeQuotes(o.getSkill())
					+ "' ";
		}
		if (o.getAbility() != null && !o.getAbility().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " Ability = '" + dbs.escapeQuotes(o.getAbility())
					+ "' ";
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
		if (o.getTrainedOnly() != null && !o.getTrainedOnly().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " TrainedOnly = '"
					+ dbs.escapeQuotes(o.getTrainedOnly()) + "' ";
		}
		if (o.getClass1() != null && !o.getClass1().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " Class1 = '" + dbs.escapeQuotes(o.getClass1())
					+ "' ";
		}
		if (o.getClass2() != null && !o.getClass2().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " Class2 = '" + dbs.escapeQuotes(o.getClass2())
					+ "' ";
		}
		if (o.getClass3() != null && !o.getClass3().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " Class3 = '" + dbs.escapeQuotes(o.getClass3())
					+ "' ";
		}
		if (o.getClass4() != null && !o.getClass4().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " Class4 = '" + dbs.escapeQuotes(o.getClass4())
					+ "' ";
		}
		if (o.getClass5() != null && !o.getClass5().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " Class5 = '" + dbs.escapeQuotes(o.getClass5())
					+ "' ";
		}
		if (o.getArmorCheck() != null && !o.getArmorCheck().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ArmorCheck = '"
					+ dbs.escapeQuotes(o.getArmorCheck()) + "' ";
		}
		if (o.getSource() != null && !o.getSource().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " Source = '"
					+ dbs.escapeQuotes(o.getSource()) + "' ";
		}
		selectString += " ORDER BY SKILL";

		return getSkillsFromSql(selectString);
		
		
	}

	public void deleteSkill(Skill o) {
		String deleteString = "delete from SKILLS  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getId());

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
			skillVector = null;
		}
	}
}

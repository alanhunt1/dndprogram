package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

public class SpellLevelDAO extends InitBaseDAO {

	private static HashMap<Integer, SpellLevel> bonusHash = null;

	public SpellLevelDAO() {

	}

	public void addSpellLevel(SpellLevel o) {
		String valueString = "";
		String insertString = "INSERT INTO SPELLS_PER_DAY (";
		if (o.getId() != null) {
			insertString += "ID,";
			valueString += "" + dbs.escapeQuotes(o.getId()) + ",";
		}
		if (o.getClassId() != null) {
			insertString += "CLASS_ID,";
			valueString += "" + dbs.escapeQuotes(o.getClassId()) + ",";
		}
		if (o.getClassName() != null) {
			insertString += "CLASS_NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getClassName()) + "',";
		}
		if (o.getClassLevel() != null) {
			insertString += "CLASS_LEVEL,";
			valueString += "" + dbs.escapeQuotes(o.getClassLevel()) + ",";
		}
		if (o.getLevel0() != null) {
			insertString += "LEVEL_0,";
			valueString += "" + dbs.escapeQuotes(o.getLevel0()) + ",";
		}
		if (o.getLevel1() != null) {
			insertString += "LEVEL_1,";
			valueString += "" + dbs.escapeQuotes(o.getLevel1()) + ",";
		}
		if (o.getLevel2() != null) {
			insertString += "LEVEL_2,";
			valueString += "" + dbs.escapeQuotes(o.getLevel2()) + ",";
		}
		if (o.getLevel3() != null) {
			insertString += "LEVEL_3,";
			valueString += "" + dbs.escapeQuotes(o.getLevel3()) + ",";
		}
		if (o.getLevel4() != null) {
			insertString += "LEVEL_4,";
			valueString += "" + dbs.escapeQuotes(o.getLevel4()) + ",";
		}
		if (o.getLevel5() != null) {
			insertString += "LEVEL_5,";
			valueString += "" + dbs.escapeQuotes(o.getLevel5()) + ",";
		}
		if (o.getLevel6() != null) {
			insertString += "LEVEL_6,";
			valueString += "" + dbs.escapeQuotes(o.getLevel6()) + ",";
		}
		if (o.getLevel7() != null) {
			insertString += "LEVEL_7,";
			valueString += "" + dbs.escapeQuotes(o.getLevel7()) + ",";
		}
		if (o.getLevel8() != null) {
			insertString += "LEVEL_8,";
			valueString += "" + dbs.escapeQuotes(o.getLevel8()) + ",";
		}
		if (o.getLevel9() != null) {
			insertString += "LEVEL_9,";
			valueString += "" + dbs.escapeQuotes(o.getLevel9()) + ",";
		}
		insertString += "ALTERNATE,";
		valueString += o.isAlternate() + ",";

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

	public void updateSpellLevel(SpellLevel o) {
		String updateString = "update SPELLS_PER_DAY set ";
		if (o.getClassId() != null) {
			updateString += "CLASS_ID=" + dbs.escapeQuotes(o.getClassId())
					+ ",";
		}
		if (o.getClassName() != null) {
			updateString += "CLASS_NAME='" + dbs.escapeQuotes(o.getClassName())
					+ "',";
		}
		if (o.getClassLevel() != null) {
			updateString += "CLASS_LEVEL="
					+ dbs.escapeQuotes(o.getClassLevel()) + ",";
		}
		if (o.getLevel0() != null) {
			updateString += "LEVEL_0=" + dbs.escapeQuotes(o.getLevel0()) + ",";
		}
		if (o.getLevel1() != null) {
			updateString += "LEVEL_1=" + dbs.escapeQuotes(o.getLevel1()) + ",";
		}
		if (o.getLevel2() != null) {
			updateString += "LEVEL_2=" + dbs.escapeQuotes(o.getLevel2()) + ",";
		}
		if (o.getLevel3() != null) {
			updateString += "LEVEL_3=" + dbs.escapeQuotes(o.getLevel3()) + ",";
		}
		if (o.getLevel4() != null) {
			updateString += "LEVEL_4=" + dbs.escapeQuotes(o.getLevel4()) + ",";
		}
		if (o.getLevel5() != null) {
			updateString += "LEVEL_5=" + dbs.escapeQuotes(o.getLevel5()) + ",";
		}
		if (o.getLevel6() != null) {
			updateString += "LEVEL_6=" + dbs.escapeQuotes(o.getLevel6()) + ",";
		}
		if (o.getLevel7() != null) {
			updateString += "LEVEL_7=" + dbs.escapeQuotes(o.getLevel7()) + ",";
		}
		if (o.getLevel8() != null) {
			updateString += "LEVEL_8=" + dbs.escapeQuotes(o.getLevel8()) + ",";
		}
		if (o.getLevel9() != null) {
			updateString += "LEVEL_9=" + dbs.escapeQuotes(o.getLevel9()) + ",";
		}

		updateString += "ALTERNATE=" + o.isAlternate() + "";

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
		}
	}

	public Vector<SpellLevel> getSpellsPerDay(String classId) {
		logger.log("Calling from 4");
		SpellLevel o = new SpellLevel();
		o.setAlternate(false);
		o.setClassId(classId);

		return selectSpellLevel(o);
	}

	public Vector<SpellLevel> getSpellsKnown(String classId) {
		logger.log("Calling from 3");
		SpellLevel o = new SpellLevel();
		o.setAlternate(true);
		o.setClassId(classId);

		return selectSpellLevel(o);
	}

	public Vector<SpellLevel> getSpellsPerDay(String classId, String level) {
		logger.log("Calling from 1");
		SpellLevel o = new SpellLevel();
		o.setAlternate(false);
		o.setClassId(classId);
		o.setClassLevel(level);
		return selectSpellLevel(o);
	}

	public Vector<SpellLevel> getSpellsKnown(String classId, String level) {
		logger.log("Calling from 2");
		SpellLevel o = new SpellLevel();
		o.setAlternate(true);
		o.setClassId(classId);
		o.setClassLevel(level);
		return selectSpellLevel(o);
	}

	public Vector<SpellLevel> selectSpellLevel(SpellLevel o) {
		String selectString = "SELECT * FROM SPELLS_PER_DAY  ";
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
		if (o.getClassId() != null && !o.getClassId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " CLASS_ID = " + dbs.escapeQuotes(o.getClassId())
					+ " ";
		}
		if (o.getClassName() != null && !o.getClassName().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " CLASS_NAME = '"
					+ dbs.escapeQuotes(o.getClassName()) + "' ";
		}
		if (o.getClassLevel() != null && !o.getClassLevel().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " CLASS_LEVEL = "
					+ dbs.escapeQuotes(o.getClassLevel()) + " ";
		}
		if (o.getLevel0() != null && !o.getLevel0().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " LEVEL_0 = " + dbs.escapeQuotes(o.getLevel0())
					+ " ";
		}
		if (o.getLevel1() != null && !o.getLevel1().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " LEVEL_1 = " + dbs.escapeQuotes(o.getLevel1())
					+ " ";
		}
		if (o.getLevel2() != null && !o.getLevel2().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " LEVEL_2 = " + dbs.escapeQuotes(o.getLevel2())
					+ " ";
		}
		if (o.getLevel3() != null && !o.getLevel3().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " LEVEL_3 = " + dbs.escapeQuotes(o.getLevel3())
					+ " ";
		}
		if (o.getLevel4() != null && !o.getLevel4().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " LEVEL_4 = " + dbs.escapeQuotes(o.getLevel4())
					+ " ";
		}
		if (o.getLevel5() != null && !o.getLevel5().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " LEVEL_5 = " + dbs.escapeQuotes(o.getLevel5())
					+ " ";
		}
		if (o.getLevel6() != null && !o.getLevel6().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " LEVEL_6 = " + dbs.escapeQuotes(o.getLevel6())
					+ " ";
		}
		if (o.getLevel7() != null && !o.getLevel7().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " LEVEL_7 = " + dbs.escapeQuotes(o.getLevel7())
					+ " ";
		}
		if (o.getLevel8() != null && !o.getLevel8().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " LEVEL_8 = " + dbs.escapeQuotes(o.getLevel8())
					+ " ";
		}
		if (o.getLevel9() != null && !o.getLevel9().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " LEVEL_9 = " + dbs.escapeQuotes(o.getLevel9())
					+ " ";
		}

		if (!first) {
			selectString += " AND ";
		} else {
			selectString += " WHERE ";
			first = false;
		}
		selectString += " ALTERNATE = " + o.isAlternate();
		selectString += " ORDER BY CLASS_LEVEL ";

		Vector<SpellLevel> v = new Vector<SpellLevel>();
		try {

			ResultSet result;

		
			
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
		

			while (result.next()) {
				SpellLevel obj = new SpellLevel();

				obj.setId(result.getString("ID"));
				obj.setClassId(result.getString("CLASS_ID"));
				obj.setClassName(result.getString("CLASS_NAME"));
				obj.setClassLevel(result.getString("CLASS_LEVEL"));
				obj.setLevel0(result.getString("LEVEL_0"));
				obj.setLevel1(result.getString("LEVEL_1"));
				obj.setLevel2(result.getString("LEVEL_2"));
				obj.setLevel3(result.getString("LEVEL_3"));
				obj.setLevel4(result.getString("LEVEL_4"));
				obj.setLevel5(result.getString("LEVEL_5"));
				obj.setLevel6(result.getString("LEVEL_6"));
				obj.setLevel7(result.getString("LEVEL_7"));
				obj.setLevel8(result.getString("LEVEL_8"));
				obj.setLevel9(result.getString("LEVEL_9"));
				obj.setAlternate(result.getBoolean("ALTERNATE"));
				v.add(obj);
			}
		} catch (SQLException sqle) {
			logger.log("error", sqle.toString());
			resetConnection();
		} catch (Exception e) {
			logger.log("error", e.toString());
			
			rebuildDatabase();
		} finally {
			try {
				logger.log("CLEANING UP");
				if (dbs2 == null) {
					if (dbs != null) {
						dbs.close();
					}else{
						logger.log("DBS IS BAD");
					}
				} else {
					dbs2.cleanup();
				}
			} catch (Exception e) {
				logger.log("REBUILDING DATABASE");
				rebuildDatabase();
			}

		}
		logger.log("RETURNING STUFF");
		return v;
	}

	
	public SpellLevel getBonusSpells(int bonus) {

		if (bonusHash == null) {
			bonusHash = new HashMap<Integer, SpellLevel>();
		}

		Object o = bonusHash.get(bonus);

		if (o == null) {

			String selectString = "SELECT * FROM BONUS_SPELLS WHERE ABILITY_START <= "
					+ bonus + " AND ABILITY_END >= " + bonus;

			SpellLevel obj = new SpellLevel();
			try {

				ResultSet result;

				if (dbs2 == null) {
					dbs.open();
					result = dbs.executeSQLQuery(selectString);
				} else {
					result = dbs2.executeSQLQuery(selectString);
				}

				if (result.next()) {

					obj.setId(result.getString("ID"));
					obj.setLevel0(result.getString("LEVEL0"));
					obj.setLevel1(result.getString("LEVEL1"));
					obj.setLevel2(result.getString("LEVEL2"));
					obj.setLevel3(result.getString("LEVEL3"));
					obj.setLevel4(result.getString("LEVEL4"));
					obj.setLevel5(result.getString("LEVEL5"));
					obj.setLevel6(result.getString("LEVEL6"));
					obj.setLevel7(result.getString("LEVEL7"));
					obj.setLevel8(result.getString("LEVEL8"));
					obj.setLevel9(result.getString("LEVEL9"));

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
			bonusHash.put(bonus, obj);
			o = obj;

		}
		return (SpellLevel) o;
	}

	public void clearSpellLevels(String classid) {
		String deleteString = "delete from SPELLS_PER_DAY  ";
		deleteString += " WHERE CLASS_ID = " + classid;

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

	public void deleteSpellLevel(SpellLevel o) {
		String deleteString = "delete from SPELLS_PER_DAY  ";
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

package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class MonsterAttacksDAO extends InitBaseDAO {

	public MonsterAttacksDAO() {

	}

	public void addMonsterAttacks(MonsterAttacks o) {
		String valueString = "";
		String insertString = "INSERT INTO MONSTER_ATTACKS (";
		if (o.getId() != null) {
			insertString += "ID,";
			valueString += "'" + dbs.escapeQuotes(o.getId()) + "',";
		}
		if (o.getMonsterId() != null) {
			insertString += "MONSTER_ID,";
			if (o.getMonsterId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getMonsterId()) + ",";

			}
		}
		if (o.getAttackType() != null) {
			insertString += "ATTACK_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getAttackType()) + "',";
		}
		if (o.getNumberOfAttacks() != null) {
			insertString += "NUMBER_OF_ATTACKS,";
			valueString += "'" + dbs.escapeQuotes(o.getNumberOfAttacks())
					+ "',";
		}
		if (o.getToHit() != null) {
			insertString += "TO_HIT,";
			valueString += "'" + dbs.escapeQuotes(o.getToHit()) + "',";
		}
		if (o.getDamage() != null) {
			insertString += "DAMAGE,";
			valueString += "'" + dbs.escapeQuotes(o.getDamage()) + "',";
		}
		if (o.isPoison()) {
			insertString += "POISON,";
			valueString += "true";
		} else {
			insertString += "POISON,";
			valueString += "false";
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
			MonsterDAO.reset();
		}
	}

	public void updateMonsterAttacks(MonsterAttacks o) {
		String updateString = "update MONSTER_ATTACKS set ";
		if (o.getMonsterId() != null) {
			if (!o.getMonsterId().equals("")) {
				updateString += "MONSTER_ID="
						+ dbs.escapeQuotes(o.getMonsterId()) + ",";
			} else {
				updateString += "MONSTER_ID=null,";
			}
		}
		if (o.getAttackType() != null) {
			updateString += "ATTACK_TYPE='"
					+ dbs.escapeQuotes(o.getAttackType()) + "',";
		}
		if (o.getNumberOfAttacks() != null) {
			updateString += "NUMBER_OF_ATTACKS='"
					+ dbs.escapeQuotes(o.getNumberOfAttacks()) + "',";
		}
		if (o.getToHit() != null) {
			updateString += "TO_HIT='" + dbs.escapeQuotes(o.getToHit()) + "',";
		}
		if (o.getDamage() != null) {
			updateString += "DAMAGE='" + dbs.escapeQuotes(o.getDamage()) + "',";
		}
		if (o.isPoison()) {
			updateString += "POISON=true,";
		} else {
			updateString += "POISON=false,";
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + " ";
		try {
			dbs.open();
			

			dbs.executeSQLCommand(updateString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
			resetConnection();
			MonsterDAO.reset();
		}
	}

	public Vector<MonsterAttacks> getMonsterAttacks(){
		return selectMonsterAttacks(new MonsterAttacks());
	}
	
	public Vector<MonsterAttacks> getMonsterAttacks(String id){
		MonsterAttacks ma = new MonsterAttacks();
		ma.setMonsterId(id);
		return selectMonsterAttacks(ma);
	}
	
	public Vector<MonsterAttacks> selectMonsterAttacks(MonsterAttacks o) {
		String selectString = "SELECT * FROM MONSTER_ATTACKS  ";
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
		if (o.getMonsterId() != null && !o.getMonsterId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " MONSTER_ID = "
					+ dbs.escapeQuotes(o.getMonsterId()) + " ";
		}
		if (o.getAttackType() != null && !o.getAttackType().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ATTACK_TYPE = '"
					+ dbs.escapeQuotes(o.getAttackType()) + "' ";
		}
		if (o.getNumberOfAttacks() != null
				&& !o.getNumberOfAttacks().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " NUMBER_OF_ATTACKS = '"
					+ dbs.escapeQuotes(o.getNumberOfAttacks()) + "' ";
		}
		if (o.getToHit() != null && !o.getToHit().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " TO_HIT = '" + dbs.escapeQuotes(o.getToHit())
					+ "' ";
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
		selectString += " ORDER BY MONSTER_ID ";
		
		Vector<MonsterAttacks> v = new Vector<MonsterAttacks>();
		try {

			ResultSet result;

			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}

			while (result.next()) {
				MonsterAttacks obj = new MonsterAttacks();

				obj.setId(result.getString("ID"));
				obj.setMonsterId(result.getString("MONSTER_ID"));
				obj.setAttackType(result.getString("ATTACK_TYPE"));
				obj.setNumberOfAttacks(result.getString("NUMBER_OF_ATTACKS"));
				obj.setToHit(result.getString("TO_HIT"));
				obj.setDamage(result.getString("DAMAGE"));
				obj.setPoison(result.getBoolean("POISON"));
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

	public void clearMonsterAttacks(String monsterId){
		String deleteString = "delete from MONSTER_ATTACKS  ";
		deleteString += " WHERE MONSTER_ID = " + dbs.escapeQuotes(monsterId) + " ";

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
			resetConnection();
			MonsterDAO.reset();
		}
	}
	
	public void deleteMonsterAttacks(MonsterAttacks o) {
		String deleteString = "delete from MONSTER_ATTACKS  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + " ";

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
			resetConnection();
			MonsterDAO.reset();
		}
	}
}

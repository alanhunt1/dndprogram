package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PlayerArmorDAO extends InitBaseDAO {
	// InitLogger logger = new InitLogger(this, "defaultLog4j.cfg");

	public PlayerArmorDAO() {

	}

	public PlayerArmorDAO(DBSession dbs2) {
		this.dbs2 = dbs2;
	}

	public void addOrUpdatePlayerArmor(Armor a, String playerId, String type) {
		addOrUpdatePlayerArmor(a, playerId, type, false);
	}

	public void addOrUpdatePlayerArmor(Armor a, String playerId, String type,
			boolean atRest) {

		Vector<Armor> v = selectPlayerArmor(a);
		if (v.size() > 0) {
			
			if (a.getInstanceId() != null && !a.getInstanceId().equals("")) {
				updatePlayerArmor(a);
			} else {
				if (a.getId() != null && !a.getId().equals("")) {
					logger.log("NO INSTANCE FOUND");
					addPlayerArmor(a);
				}
			}
		} else {
			logger.log("NO ARMOR FOUND IN DB!");
			if (a.getId() != null && !a.getId().equals("")) {
				addPlayerArmor(a);
			} else {
				logger.log("ERROR, NO ID ASSIGNED TO ARMOR");
			}
		}
	}

	public void addPlayerArmor(Armor o) {
		String valueString = "";
		String insertString = "INSERT INTO PLAYER_ARMOR (";

		if (o.getPlayerId() != null) {
			insertString += "PLAYER_ID,";
			valueString += dbs.escapeQuotes(o.getPlayerId()) + ",";
		}
		if (o.getId() != null) {
			insertString += "ARMOR_ID,";
			valueString += dbs.escapeQuotes(o.getId()) + ",";
		}
		if (o.getDisplayType() != null) {
			insertString += "ARMOR_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getDisplayType()) + "',";
		}
		if (o.getBonus() != null) {
			insertString += "BONUS,";
			valueString += "'" + dbs.escapeQuotes(o.getBonus()) + "',";
		}
		if (o.getAbility1() != null) {
			insertString += "ABILITY1,";
			valueString += "'" + dbs.escapeQuotes(o.getAbility1().getId())
					+ "',";
		}
		if (o.getAbility2() != null) {
			insertString += "ABILITY2,";
			valueString += "'" + dbs.escapeQuotes(o.getAbility2().getId())
					+ "',";
		}
		if (o.isMasterwork()) {
			insertString += "masterwork,";
			valueString += "true,";
		} else {
			insertString += "masterwork,";
			valueString += "false,";
		}
		if (o.getAtRest() != null) {
			insertString += "AT_REST,";
			valueString += "'" + dbs.escapeQuotes(o.getAtRest()) + "',";
		}
		if (o.getNotes() != null) {
			insertString += "DESCRIPTION,";
			valueString += "'" + dbs.escapeQuotes(o.getNotes()) + "',";
		}
		logger.log("Getting material");
		insertString += "MATERIAL,";
		valueString += o.getMaterial().getId();
		logger.log("Got it");
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

	public void updatePlayerArmor(Armor o) {
		String updateString = "update PLAYER_ARMOR set ";
		if (o.getPlayerId() != null) {
			updateString += "PLAYER_ID=" + dbs.escapeQuotes(o.getPlayerId())
					+ ",";
		}
		if (o.getId() != null) {
			updateString += "ARMOR_ID=" + dbs.escapeQuotes(o.getId()) + ",";
		}
		if (o.getDisplayType() != null) {
			updateString += "ARMOR_TYPE='"
					+ dbs.escapeQuotes(o.getDisplayType()) + "',";
		}

		if (o.getBonus() != null) {
			updateString += "BONUS='" + dbs.escapeQuotes(o.getBonus()) + "',";
		}
		if (o.getAbility1() != null) {
			updateString += "ABILITY1='"
					+ dbs.escapeQuotes(o.getAbility1().getId()) + "',";
		}
		if (o.getAbility2() != null) {
			updateString += "ABILITY2='"
					+ dbs.escapeQuotes(o.getAbility2().getId()) + "',";
		}
		if (o.getNotes() != null) {
			updateString += "DESCRIPTION='" + dbs.escapeQuotes(o.getNotes())
					+ "',";
		}
		if (o.isMasterwork()) {
			updateString += "masterwork=true,";
		} else {
			updateString += "masterwork=false,";
		}

		if (o.getMaterial() != null) {
			updateString += "MATERIAL=" + o.getMaterial().getId() + ",";
		}

		if (o.getAtRest() != null) {
			updateString += "AT_REST='" + dbs.escapeQuotes(o.getAtRest())
					+ "',";
		}

		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}

		updateString += " WHERE ID = " + dbs.escapeQuotes(o.getInstanceId());

		try {
			dbs.open();
			logger.log("EXECUTING UPDATE " + updateString);
			dbs.executeSQLCommand(updateString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
		}
	}

	public Vector<Armor> selectPlayerArmor(Armor o) {
		String selectString = "SELECT * FROM PLAYER_ARMOR  ";
		boolean first = true;

		if (o.getPlayerId() != null && !o.getPlayerId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " PLAYER_ID = " + dbs.escapeQuotes(o.getPlayerId())
					+ " ";
		}

		if (o.getType() != null && !o.getType().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ARMOR_TYPE = '" + dbs.escapeQuotes(o.getType())
					+ "' ";
		}

		if (o.getAtRest() != null && !o.getAtRest().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " AT_REST = '" + dbs.escapeQuotes(o.getAtRest())
					+ "' ";
		}
		Vector<Armor> v = new Vector<Armor>();
		try {
			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}

			while (result.next()) {
				Armor obj = new Armor();

				obj.setInstanceId(result.getString("ID"));
				obj.setPlayerId(result.getString("PLAYER_ID"));
				obj.setId(result.getString("ARMOR_ID"));
				obj.setType(result.getString("ARMOR_TYPE"));
				obj.setNotes(result.getString("DESCRIPTION"));
				obj.setBonus(result.getString("BONUS"));
				// obj.setAbility1(result.getString("ABILITY1"));
				// obj.setAbility2(result.getString("ABILITY2"));
				obj.setAtRest(result.getString("AT_REST"));
				obj.setMasterwork(result.getBoolean("masterwork"));
				v.add(obj);
			}
		} catch (SQLException sqle) {
			logger.log("error", sqle.toString());
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			if (dbs2 == null) {
				dbs.close();
			}
		}
		return v;
	}

	public Armor getPlayerAtRestArmor(String playerId) {
		Armor obj = new Armor();
		String selectString = "SELECT a.*, pa.bonus, pa.ability1, pa.ability2, pa.AT_REST, pa.description as PAD, pa.masterwork, pa.material, pa.id as INSTANCE_ID, m.material_name, m.description as MDESC, m.weight_calc  FROM ARMOR a, PLAYER_ARMOR pa, MATERIALS m WHERE pa.PLAYER_ID = "
				+ playerId
				+ " AND pa.ARMOR_TYPE='ARMOR' AND pa.ARMOR_ID = a.ID and pa.AT_REST = 'Y' and pa.material = m.ID";
		try {
			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}
			ArmorAbilitiesDAO wadb = new ArmorAbilitiesDAO();
			if (result.next()) {

				obj.setId(result.getString("ID"));
				obj.setName(result.getString("name"));
				obj.setDescription(result.getString("description"));
				obj.setGrade(result.getString("grade"));
				obj.setCost(result.getString("cost"));
				obj.setArmorbonus(result.getString("armor_bonus"));
				obj.setMaxdex(result.getString("max_dex"));
				obj.setCheckpenalty(result.getString("check_penalty"));
				obj.setSpeed30(result.getString("speed_30"));
				obj.setSpeed20(result.getString("speed_20"));
				obj.setWeight(result.getString("weight"));
				obj.setArcanefail(result.getString("arcane_fail"));
				obj.setType(result.getString("type"));
				obj.setBonus(result.getString("BONUS"));
				obj.setAtRest(result.getString("AT_REST"));
				obj.setNotes(result.getString("PAD"));
				obj.setMasterwork(result.getBoolean("masterwork"));

				obj.setInstanceId(result.getString("INSTANCE_ID"));

				Materials m = new Materials();
				m.setId(result.getString("material"));
				m.setDescription(result.getString("MDESC"));
				m.setWeightCalc(result.getString("WEIGHT_CALC"));
				m.setMaterialName(result.getString("MATERIAL_NAME"));
				obj.setMaterial(m);

				String ab1 = result.getString("ability1");
				if (ab1 != null && !ab1.equals("") && !ab1.equals("null")) {
					
					obj.setAbility1(wadb.getArmorAbilitiesById(ab1));
				}

				String ab2 = result.getString("ability2");
				if (ab2 != null && !ab2.equals("") && !ab2.equals("null")) {
					obj.setAbility2(wadb.getArmorAbilitiesById(ab2));
				}

			} else {
				obj.setRestArmorDefaults();
				obj.setPlayerId(playerId);
			}
		} catch (SQLException sqle) {
			logger.log("error", sqle.toString());
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			if (dbs2 == null) {
				dbs.close();
			}
		}
		return obj;
	}

	public Armor getPlayerArmor(String playerId) {
		Armor obj = new Armor();
		String selectString = "SELECT a.*, pa.bonus, pa.ability1, pa.ability2, pa.description as PAD, pa.AT_REST, pa.masterwork, pa.material, pa.id as INSTANCE_ID , m.material_name, m.description as MDESC, m.weight_calc FROM ARMOR a, PLAYER_ARMOR pa, MATERIALS m WHERE pa.PLAYER_ID = "
				+ playerId
				+ " AND pa.ARMOR_TYPE='ARMOR' AND pa.ARMOR_ID = a.ID and pa.AT_REST = 'N' and pa.material = m.ID";
		try {
			ResultSet result = null;
			logger.log("EXECUTING QUERY " + selectString);
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}
			ArmorAbilitiesDAO aadb = new ArmorAbilitiesDAO();
			if (result.next()) {

				obj.setId(result.getString("ID"));
				obj.setName(result.getString("name"));
				obj.setDescription(result.getString("description"));
				obj.setGrade(result.getString("grade"));
				obj.setCost(result.getString("cost"));
				obj.setArmorbonus(result.getString("armor_bonus"));
				obj.setMaxdex(result.getString("max_dex"));
				obj.setCheckpenalty(result.getString("check_penalty"));
				obj.setSpeed30(result.getString("speed_30"));
				obj.setSpeed20(result.getString("speed_20"));
				obj.setWeight(result.getString("weight"));
				obj.setArcanefail(result.getString("arcane_fail"));
				obj.setType(result.getString("type"));
				obj.setBonus(result.getString("BONUS"));
				obj.setAtRest(result.getString("AT_REST"));
				obj.setNotes(result.getString("PAD"));
				obj.setMasterwork(result.getBoolean("masterwork"));

				obj.setInstanceId(result.getString("INSTANCE_ID"));
				Materials m = new Materials();
				m.setId(result.getString("material"));
				m.setDescription(result.getString("MDESC"));
				m.setWeightCalc(result.getString("WEIGHT_CALC"));
				m.setMaterialName(result.getString("MATERIAL_NAME"));
				obj.setMaterial(m);

				String ab1 = result.getString("ability1");
				System.out.println("GOT ABILITY"+ab1);
				if (ab1 != null && !ab1.equals("") && !ab1.equals("null")) {
					ArmorAbilities aa = aadb.getArmorAbilitiesById(ab1);
					if (aa == null){
						System.out.println("null ability");
					}
					obj.setAbility1(aa);
					System.out.println("ability one was "+obj.getAbility1().getName());
				}

				String ab2 = result.getString("ability2");
				if (ab2 != null && !ab2.equals("") && !ab2.equals("null")) {
					obj.setAbility2(aadb.getArmorAbilitiesById(ab2));
				}

			} else {
				obj.setArmorDefaults();
				obj.setPlayerId(playerId);
			}
		} catch (SQLException sqle) {
			logger.log("error", sqle.toString());
			sqle.printStackTrace();
		} catch (Exception e) {
			logger.log("error", e.toString());
			e.printStackTrace();
		} finally {
			if (dbs2 == null) {
				dbs.close();
			}
		}
		return obj;
	}

	public Armor getPlayerShield(String playerId) {
		Armor obj = new Armor();
		String selectString = "SELECT a.* , pa.bonus, pa.ability1, pa.ability2, pa.description as PAD, pa.masterwork, pa.material, pa.id as INSTANCE_ID, m.material_name, m.description as MDESC, m.weight_calc  FROM ARMOR a, PLAYER_ARMOR pa, MATERIALS m WHERE pa.PLAYER_ID = "
				+ playerId
				+ " AND pa.ARMOR_TYPE='SHIELD' AND pa.ARMOR_ID = a.ID and pa.material = m.ID";

		try {
			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}
			ArmorAbilitiesDAO wadb = new ArmorAbilitiesDAO();
			if (result.next()) {

				obj.setId(result.getString("ID"));
				obj.setName(result.getString("name"));
				obj.setDescription(result.getString("description"));
				obj.setGrade(result.getString("grade"));
				obj.setCost(result.getString("cost"));
				obj.setArmorbonus(result.getString("armor_bonus"));
				obj.setMaxdex(result.getString("max_dex"));
				obj.setCheckpenalty(result.getString("check_penalty"));
				obj.setSpeed30(result.getString("speed_30"));
				obj.setSpeed20(result.getString("speed_20"));
				obj.setWeight(result.getString("weight"));
				obj.setArcanefail(result.getString("arcane_fail"));
				obj.setType(result.getString("type"));
				obj.setBonus(result.getString("BONUS"));
				obj.setNotes(result.getString("PAD"));
				obj.setDisplayType("Shield");
				Materials m = new Materials();
				m.setId(result.getString("material"));
				m.setDescription(result.getString("MDESC"));
				m.setWeightCalc(result.getString("WEIGHT_CALC"));
				m.setMaterialName(result.getString("MATERIAL_NAME"));
				obj.setMaterial(m);

				obj.setInstanceId(result.getString("INSTANCE_ID"));
				obj.setMasterwork(result.getBoolean("masterwork"));
				String ab1 = result.getString("ability1");
				if (ab1 != null && !ab1.equals("") && !ab1.equals("null")) {
					
					obj.setAbility1(wadb.getArmorAbilitiesById(ab1));
				}

				String ab2 = result.getString("ability2");
				if (ab2 != null && !ab2.equals("") && !ab2.equals("null")) {
					obj.setAbility2(wadb.getArmorAbilitiesById(ab2));
				}

			} else {
				obj.setShieldDefaults();
				obj.setPlayerId(playerId);
			}
		} catch (SQLException sqle) {
			logger.log("error", sqle.toString());
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			if (dbs2 == null) {
				dbs.close();
			}
		}
		return obj;
	}

	public void deletePlayerArmor(Armor o) {
		String deleteString = "delete from PLAYER_ARMOR  ";
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

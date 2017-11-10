package initcheck.database;

import initcheck.character.TempModSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class PlayerTempModDAO extends InitBaseDAO {

	public PlayerTempModDAO() {

	}

	public PlayerTempModDAO(DBSession dbs2) {
		this.dbs2 = dbs2;
	}

	public void addPlayerTempMod(PlayerTempMod o) {
		o.convertNulls();
		o.convertEmpty();
		String valueString = "";
		String insertString = "INSERT INTO PLAYER_TEMP_MOD (";

		if (o.getPlayerId() != null) {
			insertString += "PLAYER_ID,";
			valueString += dbs.escapeQuotes(o.getPlayerId()) + ",";
		}
		if (o.getName() != null) {
			insertString += "NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getName()) + "',";
		}
		if (o.getModType() != null) {
			insertString += "MOD_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getModType()) + "',";
		}
		if (o.getLinkType() != null) {
			insertString += "LINK_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getLinkType()) + "',";
		}
		if (o.getLinkId() != null) {
			insertString += "LINK_ID,";
			valueString += "'" + dbs.escapeQuotes(o.getLinkId()) + "',";
		}
		if (o.getStrMod() != null) {
			insertString += "STR_MOD,";
			if (o.getStrMod().equals("")) {
				valueString += "null,";
			} else {
				valueString += "" + dbs.escapeQuotes(o.getStrMod()) + ",";
			}
		}
		if (o.getConMod() != null) {
			insertString += "CON_MOD,";
			if (o.getConMod().equals("")) {
				valueString += "null,";
			} else {
				valueString += "" + dbs.escapeQuotes(o.getConMod()) + ",";
			}
		}
		if (o.getDexMod() != null) {
			insertString += "DEX_MOD,";
			if (o.getDexMod().equals("")) {
				valueString += "null,";
			} else {
				valueString += "" + dbs.escapeQuotes(o.getDexMod()) + ",";
			}
		}
		if (o.getIntMod() != null) {
			insertString += "INT_MOD,";
			if (o.getIntMod().equals("")) {
				valueString += "null,";
			} else {
				valueString += "" + dbs.escapeQuotes(o.getIntMod()) + ",";
			}
		}
		if (o.getWisMod() != null) {
			insertString += "WIS_MOD,";
			if (o.getWisMod().equals("")) {
				valueString += "null,";
			} else {
				valueString += "" + dbs.escapeQuotes(o.getWisMod()) + ",";
			}
		}
		if (o.getChaMod() != null) {
			insertString += "CHA_MOD,";
			valueString += "" + dbs.escapeQuotes(o.getChaMod()) + ",";
		}
		
		if (o.getStrCheckMod() != null) {
			insertString += "STR_CHECK_MOD,";
			if (o.getStrCheckMod().equals("")) {
				valueString += "null,";
			} else {
				valueString += "" + dbs.escapeQuotes(o.getStrCheckMod()) + ",";
			}
		}
		if (o.getConCheckMod() != null) {
			insertString += "CON_CHECK_MOD,";
			if (o.getConCheckMod().equals("")) {
				valueString += "null,";
			} else {
				valueString += "" + dbs.escapeQuotes(o.getConCheckMod()) + ",";
			}
		}
		if (o.getDexCheckMod() != null) {
			insertString += "DEX_CHECK_MOD,";
			if (o.getDexCheckMod().equals("")) {
				valueString += "null,";
			} else {
				valueString += "" + dbs.escapeQuotes(o.getDexCheckMod()) + ",";
			}
		}
		if (o.getIntCheckMod() != null) {
			insertString += "INT_CHECK_MOD,";
			if (o.getIntCheckMod().equals("")) {
				valueString += "null,";
			} else {
				valueString += "" + dbs.escapeQuotes(o.getIntCheckMod()) + ",";
			}
		}
		if (o.getWisCheckMod() != null) {
			insertString += "WIS_CHECK_MOD,";
			if (o.getWisCheckMod().equals("")) {
				valueString += "null,";
			} else {
				valueString += "" + dbs.escapeQuotes(o.getWisCheckMod()) + ",";
			}
		}
		if (o.getChaCheckMod() != null) {
			insertString += "CHA_CHECK_MOD,";
			if (o.getChaCheckMod().equals("")) {
				valueString += "null,";
			} else {
				valueString += "" + dbs.escapeQuotes(o.getChaCheckMod()) + ",";
			}
		}
		if (o.getNatMod() != null) {
			insertString += "NAT_MOD,";
			valueString += "" + dbs.escapeQuotes(o.getNatMod()) + ",";
		}
		if (o.getAcMod() != null) {
			insertString += "AC_MOD,";
			valueString += "" + dbs.escapeQuotes(o.getAcMod()) + ",";
		}
		if (o.getRestMod() != null) {
			insertString += "REST_MOD,";
			valueString += "" + dbs.escapeQuotes(o.getRestMod()) + ",";
		}
		if (o.getTouchMod() != null) {
			insertString += "TOUCH_MOD,";
			valueString += "" + dbs.escapeQuotes(o.getTouchMod()) + ",";
		}
		if (o.getMeleeMod() != null) {
			insertString += "MELEE_MOD,";
			valueString += "" + dbs.escapeQuotes(o.getMeleeMod()) + ",";
		}
		if (o.getRangeMod() != null) {
			insertString += "RANGE_MOD,";
			valueString += "" + dbs.escapeQuotes(o.getRangeMod()) + ",";
		}
		if (o.getDamageMod() != null) {
			insertString += "DAMAGE_MOD,";
			valueString += "" + dbs.escapeQuotes(o.getDamageMod()) + ",";
		}
		if (o.getFortMod() != null) {
			insertString += "FORT_MOD,";
			valueString += "" + dbs.escapeQuotes(o.getFortMod()) + ",";
		}
		if (o.getRefMod() != null) {
			insertString += "REF_MOD,";
			valueString += "" + dbs.escapeQuotes(o.getRefMod()) + ",";
		}
		if (o.getWillMod() != null) {
			insertString += "WILL_MOD,";
			valueString += "" + dbs.escapeQuotes(o.getWillMod()) + ",";
		}
		if (o.getFeatMod() != null) {
			insertString += "FEAT_MOD,";
			valueString += "" + dbs.escapeQuotes(o.getFeatMod()) + ",";
		}
		if (o.isConditional()) {
			insertString += "CONDITIONAL,";
			valueString += "true,";
		} else {
			insertString += "CONDITIONAL,";
			valueString += "false,";
		}

		if (o.getAttackMod() != null) {
			insertString += "ATTACK_MOD,";
			if (o.getAttackMod().equals("")) {
				valueString += "null,";
			} else {
				valueString += "" + dbs.escapeQuotes(o.getAttackMod()) + ",";
			}
			
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

	public void updatePlayerTempMod(PlayerTempMod o) {
		o.convertNulls();
		o.convertEmpty();
		String updateString = "update PLAYER_TEMP_MOD set ";
		if (o.getPlayerId() != null) {
			updateString += "PLAYER_ID=" + dbs.escapeQuotes(o.getPlayerId())
					+ ",";
		}
		if (o.getName() != null) {
			updateString += "NAME='" + dbs.escapeQuotes(o.getName()) + "',";
		}
		if (o.getModType() != null) {
			updateString += "MOD_TYPE='" + dbs.escapeQuotes(o.getModType())
					+ "',";
		}
		if (o.getLinkType() != null) {
			updateString += "LINK_TYPE='" + dbs.escapeQuotes(o.getLinkType())
					+ "',";
		}
		if (o.getLinkId() != null) {
			updateString += "LINK_ID='" + dbs.escapeQuotes(o.getLinkId())
					+ "',";
		}
		if (o.getStrMod() != null) {
			updateString += "STR_MOD=" + dbs.escapeQuotes(o.getStrMod()) + ",";
		}
		if (o.getConMod() != null) {
			updateString += "CON_MOD=" + dbs.escapeQuotes(o.getConMod()) + ",";
		}
		if (o.getDexMod() != null) {
			updateString += "DEX_MOD=" + dbs.escapeQuotes(o.getDexMod()) + ",";
		}
		if (o.getIntMod() != null) {
			updateString += "INT_MOD=" + dbs.escapeQuotes(o.getIntMod()) + ",";
		}
		if (o.getWisMod() != null) {
			updateString += "WIS_MOD=" + dbs.escapeQuotes(o.getWisMod()) + ",";
		}
		if (o.getChaMod() != null) {
			updateString += "CHA_MOD=" + dbs.escapeQuotes(o.getChaMod()) + ",";
		}
		if (o.getStrCheckMod() != null) {
			updateString += "STR_CHECK_MOD=" + dbs.escapeQuotes(o.getStrCheckMod()) + ",";
		}
		if (o.getConCheckMod() != null) {
			updateString += "CON_CHECK_MOD=" + dbs.escapeQuotes(o.getConCheckMod()) + ",";
		}
		if (o.getDexCheckMod() != null) {
			updateString += "DEX_CHECK_MOD=" + dbs.escapeQuotes(o.getDexCheckMod()) + ",";
		}
		if (o.getIntCheckMod() != null) {
			updateString += "INT_CHECK_MOD=" + dbs.escapeQuotes(o.getIntCheckMod()) + ",";
		}
		if (o.getWisCheckMod() != null) {
			updateString += "WIS_CHECK_MOD=" + dbs.escapeQuotes(o.getWisCheckMod()) + ",";
		}
		if (o.getChaCheckMod() != null) {
			updateString += "CHA_CHECK_MOD=" + dbs.escapeQuotes(o.getChaCheckMod()) + ",";
		}
		if (o.getNatMod() != null) {
			updateString += "NAT_MOD=" + dbs.escapeQuotes(o.getNatMod()) + ",";
		}
		if (o.getAcMod() != null) {
			updateString += "AC_MOD=" + dbs.escapeQuotes(o.getAcMod()) + ",";
		}
		if (o.getRestMod() != null) {
			updateString += "REST_MOD=" + dbs.escapeQuotes(o.getRestMod())
					+ ",";
		}
		if (o.getTouchMod() != null) {
			updateString += "TOUCH_MOD=" + dbs.escapeQuotes(o.getTouchMod())
					+ ",";
		}
		if (o.getMeleeMod() != null) {
			updateString += "MELEE_MOD=" + dbs.escapeQuotes(o.getMeleeMod())
					+ ",";
		}
		if (o.getRangeMod() != null) {
			updateString += "RANGE_MOD=" + dbs.escapeQuotes(o.getRangeMod())
					+ ",";
		}
		if (o.getDamageMod() != null) {
			updateString += "DAMAGE_MOD=" + dbs.escapeQuotes(o.getDamageMod())
					+ ",";
		}
		if (o.getFortMod() != null) {
			updateString += "FORT_MOD=" + dbs.escapeQuotes(o.getFortMod())
					+ ",";
		}
		if (o.getRefMod() != null) {
			updateString += "REF_MOD=" + dbs.escapeQuotes(o.getRefMod()) + ",";
		}
		if (o.getWillMod() != null) {
			updateString += "WILL_MOD=" + dbs.escapeQuotes(o.getWillMod())
					+ ",";
		}
		if (o.getFeatMod() != null) {
			updateString += "FEAT_MOD=" + dbs.escapeQuotes(o.getFeatMod())
					+ ",";
		}
		if (o.isConditional()) {
			updateString += "conditional=true,";
		} else {
			updateString += "conditional=false,";
		}
		if (o.getAttackMod() != null) {
			updateString += "Attack_MOD=" + dbs.escapeQuotes(o.getAttackMod())
					+ ",";
		}
		
		
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + "";

		try {
			logger.log("Exceuting update"+updateString);
			dbs.open();
			dbs.executeSQLCommand(updateString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			resetConnection();
		}
	}

	public TempModSet getPlayerTempMods(String id) {
		PlayerTempMod o = new PlayerTempMod();
		o.setPlayerId(id);
		Vector<PlayerTempMod> v =  selectPlayerTempMod(o);
		return new TempModSet(v);
	}

	public Vector<PlayerTempMod> selectPlayerTempMod(PlayerTempMod o) {
		String selectString = "SELECT * FROM PLAYER_TEMP_MOD  ";
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
		if (o.getName() != null && !o.getName().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " NAME = '" + dbs.escapeQuotes(o.getName()) + "' ";
		}
		if (o.getModType() != null && !o.getModType().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " MOD_TYPE = '" + dbs.escapeQuotes(o.getModType())
					+ "' ";
		}
		if (o.getLinkType() != null && !o.getLinkType().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " LINK_TYPE = '"
					+ dbs.escapeQuotes(o.getLinkType()) + "' ";
		}
		if (o.getLinkId() != null && !o.getLinkId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " LINK_ID = '" + dbs.escapeQuotes(o.getLinkId())
					+ "' ";
		}
		if (o.getStrMod() != null && !o.getStrMod().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " STR_MOD = " + dbs.escapeQuotes(o.getStrMod())
					+ "";
		}
		if (o.getConMod() != null && !o.getConMod().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " CON_MOD = " + dbs.escapeQuotes(o.getConMod())
					+ "";
		}
		if (o.getDexMod() != null && !o.getDexMod().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " DEX_MOD = " + dbs.escapeQuotes(o.getDexMod())
					+ "";
		}
		if (o.getIntMod() != null && !o.getIntMod().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " INT_MOD = " + dbs.escapeQuotes(o.getIntMod())
					+ "";
		}
		if (o.getWisMod() != null && !o.getWisMod().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " WIS_MOD = " + dbs.escapeQuotes(o.getWisMod())
					+ "";
		}
		if (o.getChaMod() != null && !o.getChaMod().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " CHA_MOD = " + dbs.escapeQuotes(o.getChaMod())
					+ "";
		}
		if (o.getNatMod() != null && !o.getNatMod().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " NAT_MOD = " + dbs.escapeQuotes(o.getNatMod())
					+ "";
		}
		if (o.getAcMod() != null && !o.getAcMod().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " AC_MOD = " + dbs.escapeQuotes(o.getAcMod()) + "";
		}
		if (o.getRestMod() != null && !o.getRestMod().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " REST_MOD = " + dbs.escapeQuotes(o.getRestMod())
					+ "";
		}
		if (o.getTouchMod() != null && !o.getTouchMod().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " TOUCH_MOD = " + dbs.escapeQuotes(o.getTouchMod())
					+ "";
		}
		if (o.getMeleeMod() != null && !o.getMeleeMod().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " MELEE_MOD = " + dbs.escapeQuotes(o.getMeleeMod())
					+ "";
		}
		if (o.getRangeMod() != null && !o.getRangeMod().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " RANGE_MOD = " + dbs.escapeQuotes(o.getRangeMod())
					+ "";
		}
		if (o.getDamageMod() != null && !o.getDamageMod().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " DAMAGE_MOD = "
					+ dbs.escapeQuotes(o.getDamageMod()) + "";
		}
		if (o.getFortMod() != null && !o.getFortMod().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " FORT_MOD = " + dbs.escapeQuotes(o.getFortMod())
					+ "";
		}
		if (o.getRefMod() != null && !o.getRefMod().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " REF_MOD = " + dbs.escapeQuotes(o.getRefMod())
					+ "";
		}
		if (o.getWillMod() != null && !o.getWillMod().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " WILL_MOD = " + dbs.escapeQuotes(o.getWillMod())
					+ "";
		}
		
		selectString += " ORDER BY MOD_TYPE";
		
		Vector<PlayerTempMod> v = new Vector<PlayerTempMod>();
		try {
			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {		
				result = dbs2.executeSQLQuery(selectString);
			}

			while (result.next()) {
				PlayerTempMod obj = new PlayerTempMod();

				obj.setId(result.getString("ID"));
				obj.setPlayerId(result.getString("PLAYER_ID"));
				obj.setName(result.getString("NAME"));
				obj.setModType(result.getString("MOD_TYPE"));
				obj.setLinkType(result.getString("LINK_TYPE"));
				obj.setLinkId(result.getString("LINK_ID"));
				obj.setStrMod(result.getString("STR_MOD"));
				obj.setConMod(result.getString("CON_MOD"));
				obj.setDexMod(result.getString("DEX_MOD"));
				obj.setIntMod(result.getString("INT_MOD"));
				obj.setWisMod(result.getString("WIS_MOD"));
				obj.setChaMod(result.getString("CHA_MOD"));
				obj.setNatMod(result.getString("NAT_MOD"));
				obj.setAcMod(result.getString("AC_MOD"));
				obj.setRestMod(result.getString("REST_MOD"));
				obj.setTouchMod(result.getString("TOUCH_MOD"));
				obj.setMeleeMod(result.getString("MELEE_MOD"));
				obj.setRangeMod(result.getString("RANGE_MOD"));
				obj.setDamageMod(result.getString("DAMAGE_MOD"));
				obj.setFortMod(result.getString("FORT_MOD"));
				obj.setRefMod(result.getString("REF_MOD"));
				obj.setWillMod(result.getString("WILL_MOD"));
				obj.setConditional(result.getBoolean("CONDITIONAL"));
				obj.setAttackMod(result.getString("ATTACK_MOD"));
				obj.setStrCheckMod(result.getString("STR_CHECK_MOD"));
				obj.setConCheckMod(result.getString("CON_CHECK_MOD"));
				obj.setDexCheckMod(result.getString("DEX_CHECK_MOD"));
				obj.setIntCheckMod(result.getString("INT_CHECK_MOD"));
				obj.setWisCheckMod(result.getString("WIS_CHECK_MOD"));
				obj.setChaCheckMod(result.getString("CHA_CHECK_MOD"));
				obj.setFeatMod(result.getString("FEAT_MOD"));
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
		
		// post processing to find conflicts in the selected mods
		// first hash them out by type to see which have potential duplicates
		Hashtable<String, Vector<PlayerTempMod>> modHash = new Hashtable<String, Vector<PlayerTempMod>>();
		for (PlayerTempMod curr:v){
			if(modHash.get(curr.getModType()) == null){
				modHash.put(curr.getModType(), new Vector<PlayerTempMod>());
			}
			Vector <PlayerTempMod> hashVals = modHash.get(curr.getModType());
			hashVals.add(curr);
		}
		
		// then loop through and check each type for conflicting mods
		Enumeration<String> modTypes = modHash.keys();
		while (modTypes.hasMoreElements()){
			String key = modTypes.nextElement();
			Vector<PlayerTempMod> keyVector = modHash.get(key);
			for (PlayerTempMod curr:keyVector){
				curr.checkConflict(keyVector);
			}
		}
		
		return v;
	}

	public void deletePlayerTempMod(PlayerTempMod o) {
		String deleteString = "delete from PLAYER_TEMP_MOD  ";
		if (o.getId() != null && !o.getId().equals("")) {
			deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + "";
		} else {
			deleteString += " WHERE PLAYER_ID=" + o.getPlayerId()
					+ " AND LINK_TYPE='" + o.getLinkType() + "' AND LINK_ID='"
					+ o.getLinkId() + "'";
		}
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

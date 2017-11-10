package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PlayerWeaponsDAO extends InitBaseDAO {

	public PlayerWeaponsDAO() {

	}

	public PlayerWeaponsDAO(DBSession dbs2) {
		this.dbs2 = dbs2;
	}

	public int addPlayerWeapons(Weapon w, String playerId) {
		w.setPlayerId(playerId);
		return addPlayerWeapons(w);
	}

	public void updatePlayerWeapons(Weapon w, String playerId) {
		w.setPlayerId(playerId);
		updatePlayerWeapons(w);
	}

	public int addPlayerWeapons(Weapon o) {
		int i = -1;
		try {
			dbs.open();
			int j = 0;
			ResultSet result = dbs
					.executeSQLQuery("SELECT MAX(CORDER) as MAXORDER FROM PLAYER_WEAPONS WHERE PLAYER_ID="
							+ o.getPlayerId());
			if (result.next()) {
				j = result.getInt("MAXORDER");
			}

			String valueString = "";
			String insertString = "INSERT INTO PLAYER_WEAPONS (";
			if (o.getId() != null) {
				insertString += "weapon_id,";
				valueString += dbs.escapeQuotes(o.getId()) + ",";
			}
			if (o.getPlayerId() != null) {
				insertString += "player_id,";
				valueString += dbs.escapeQuotes(o.getPlayerId()) + ",";
			}
			if (o.getBonus() != null) {
				insertString += "bonus,";
				valueString += "'" + dbs.escapeQuotes(o.getBonus()) + "',";
			}
			if (o.getDescription() != null) {
				insertString += "description,";
				valueString += "'" + dbs.escapeQuotes(o.getDescription())
						+ "',";
			}
			if (o.getAbility1() != null) {
				insertString += "ability1,";
				valueString += "'" + dbs.escapeQuotes(o.getAbility1().getId())
						+ "',";
			}
			if (o.getAbility2() != null) {
				insertString += "ability2,";
				valueString += "'" + dbs.escapeQuotes(o.getAbility2().getId())
						+ "',";
			}
			if (o.getAbility3() != null) {
				insertString += "ability3,";
				valueString += "'" + dbs.escapeQuotes(o.getAbility3().getId())
						+ "',";
			}
			if (o.getAbility4() != null) {
				insertString += "ability4,";
				valueString += "'" + dbs.escapeQuotes(o.getAbility4().getId())
						+ "',";
			}
			if (o.getAbility5() != null) {
				insertString += "ability5,";
				valueString += "'" + dbs.escapeQuotes(o.getAbility5().getId())
						+ "',";
			}
			if (o.getAbility6() != null) {
				insertString += "ability6,";
				valueString += "'" + dbs.escapeQuotes(o.getAbility6().getId())
						+ "',";
			}
			if (o.isIntelligent()) {
				insertString += "intelligent,";
				valueString += "true,";
			} else {
				insertString += "intelligent,";
				valueString += "false,";
			}
			if (o.isMasterwork()) {
				insertString += "masterwork,";
				valueString += "true,";
			} else {
				insertString += "masterwork,";
				valueString += "false,";
			}
			if (o.getUse() != null) {
				insertString += "use,";
				valueString += "'" + dbs.escapeQuotes(o.getUse()) + "',";
			}
			if (o.getNotes() != null) {
				insertString += "NOTES,";
				valueString += "'" + dbs.escapeQuotes(o.getNotes()) + "',";
			}
			if (o.isCritOverride()) {
				if (o.getCritStart() != null) {
					insertString += "CRIT_START,";
					valueString += "" + dbs.escapeQuotes(o.getCritStart())
							+ ",";
				}
				if (o.getCritEnd() != null) {
					insertString += "CRIT_END,";
					valueString += "" + dbs.escapeQuotes(o.getCritEnd()) + ",";
				}
				if (o.getCritMult() != null) {
					insertString += "CRIT_MULT,";
					valueString += "" + dbs.escapeQuotes(o.getCritMult()) + ",";
				}
			}
			if (o.getSizeOverride() != null) {
				insertString += "SIZE_OVERRIDE,";
				valueString += "'" + dbs.escapeQuotes(o.getSizeOverride())
						+ "',";
			}
			if (o.getDamageOverride() != null) {
				insertString += "DAMAGE_OVERRIDE,";
				valueString += "'" + dbs.escapeQuotes(o.getDamageOverride())
						+ "',";
			}
			if (o.getQuantity() != null) {
				insertString += "QUANTITY,";
				valueString += "'" + dbs.escapeQuotes(o.getQuantity()) + "',";
			}
			if (o.useMonk()) {
				insertString += "USE_MONK,";
				valueString += "true,";
			} else {
				insertString += "USE_MONK,";
				valueString += "false,";
			}
			if (o.isNamed()) {
				insertString += "NAMED,";
				valueString += "true,";
			} else {
				insertString += "NAMED,";
				valueString += "false,";
			}
			if (o.isDisplaySheet()) {
				insertString += "DISPLAY_SHEET,";
				valueString += "true,";
			} else {
				insertString += "DISPLAY_SHEET,";
				valueString += "false,";
			}
			if (o.isDisplayItem()) {
				insertString += "DISPLAY_ITEMS,";
				valueString += "true,";
			} else {
				insertString += "DISPLAY_ITEMS,";
				valueString += "false,";
			}

			if (o.getMaterial() != null) {
				insertString += "MATERIAL,";
				valueString += o.getMaterial().getId() + ",";
			}

			if (o.getTrueName() != null) {
				insertString += "TRUE_NAME,";
				valueString += "'"+o.getTrueName() + "',";
			}

			
			insertString += "CORDER,";
			valueString += (j + 1) + ",";

			if (insertString.charAt(insertString.length() - 1) == ',') {
				insertString = insertString.substring(0,
						insertString.length() - 1);
			}
			if (valueString.charAt(valueString.length() - 1) == ',') {
				valueString = valueString
						.substring(0, valueString.length() - 1);
			}
			insertString += ") VALUES (";
			insertString += valueString;
			insertString += ")";

			
			dbs.executeSQLCommand(insertString);
			
			result = dbs
					.executeSQLQuery("SELECT MAX(ID) as MAXID FROM PLAYER_WEAPONS");
			if (result.next()) {
				i = result.getInt("MAXID");
			}

		} catch (Exception e) {
		
			logger.log("error", e.toString());
		} finally {
			dbs.close();
			resetConnection();
		}

		return i;
	}

	public void updateOrder(Weapon o, String playerId, int change) {
		Vector<Weapon> v = getPlayerWeapons(playerId);

		for (int i = 0; i < v.size(); i++) {
			Weapon w = (Weapon) v.get(i);

			if (w.getOrder() == o.getOrder()) {

				w.setOrder(w.getOrder() + change);
				
				updatePlayerWeapons(w);
			} else if ((change < 0 && w.getOrder() + 1 == o.getOrder())
					|| (change > 0 && w.getOrder() - 1 == o.getOrder())) {
				w.setOrder(w.getOrder() - change);
				
				updatePlayerWeapons(w);
			}
		}
	}

	public void updatePlayerWeapons(Weapon o) {
		String updateString = "update PLAYER_WEAPONS set ";
		if (o.getPlayerId() != null) {
			updateString += "player_id=" + dbs.escapeQuotes(o.getPlayerId())
					+ ",";
		}
		if (o.getId() != null) {
			updateString += "weapon_id=" + dbs.escapeQuotes(o.getId()) + ",";
		}
		if (o.getBonus() != null) {
			updateString += "bonus='" + dbs.escapeQuotes(o.getBonus()) + "',";
		}
		if (o.getDescription() != null) {
			updateString += "description='"
					+ dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getAbility1() != null) {
			updateString += "ability1='"
					+ dbs.escapeQuotes(o.getAbility1().getId()) + "',";
		}
		if (o.getAbility2() != null) {
			updateString += "ability2='"
					+ dbs.escapeQuotes(o.getAbility2().getId()) + "',";
		}
		if (o.getAbility3() != null) {
			updateString += "ability3='"
					+ dbs.escapeQuotes(o.getAbility3().getId()) + "',";
		}
		if (o.getAbility4() != null) {
			updateString += "ability4='"
					+ dbs.escapeQuotes(o.getAbility4().getId()) + "',";
		}
		if (o.getAbility5() != null) {
			updateString += "ability5='"
					+ dbs.escapeQuotes(o.getAbility5().getId()) + "',";
		}
		if (o.getAbility6() != null) {
			updateString += "ability6='"
					+ dbs.escapeQuotes(o.getAbility6().getId()) + "',";
		}
		if (o.isIntelligent()) {
			updateString += "intelligent=true,";
		} else {
			updateString += "intelligent=false,";
		}
		if (o.isMasterwork()) {
			updateString += "masterwork=true,";
		} else {
			updateString += "masterwork=false,";
		}
		if (o.isNamed()) {
			updateString += "named=true,";
		} else {
			updateString += "named=false,";
		}
		if (o.getUse() != null) {
			updateString += "use='" + dbs.escapeQuotes(o.getUse()) + "',";
		}
		if (o.getNotes() != null) {
			updateString += "NOTES='" + dbs.escapeQuotes(o.getNotes()) + "',";
		}
		updateString += "CORDER=" + o.getOrder() + ",";
		if (o.isCritOverride()) {
			if (o.getCritStart() != null) {
				updateString += "CRIT_START="
						+ dbs.escapeQuotes(o.getCritStart()) + ",";
			}
			if (o.getCritEnd() != null) {
				updateString += "CRIT_END=" + dbs.escapeQuotes(o.getCritEnd())
						+ ",";
			}
			if (o.getCritMult() != null) {
				updateString += "CRIT_MULT="
						+ dbs.escapeQuotes(o.getCritMult()) + ",";
			}
		} else {
			updateString += "CRIT_START=null,CRIT_END=null,CRIT_MULT=null,";
		}			
		
		if (o.getSizeOverride() != null) {
			updateString += "SIZE_OVERRIDE='"
					+ dbs.escapeQuotes(o.getSizeOverride()) + "',";
		}
		if (o.getDamageOverride() != null) {
			updateString += "DAMAGE_OVERRIDE='"
					+ dbs.escapeQuotes(o.getDamageOverride()) + "',";
		}
		if (o.getQuantity() != null) {
			updateString += "QUANTITY='" + dbs.escapeQuotes(o.getQuantity())
					+ "',";
		}
		if (o.useMonk()) {
			updateString += "USE_MONK=true,";
		} else {
			updateString += "USE_MONK=false,";
		}
		if (o.isDisplaySheet()) {
			updateString += "DISPLAY_SHEET=true,";
		} else {
			updateString += "DISPLAY_SHEET=false,";
		}
		if (o.isDisplayItem()) {
			updateString += "DISPLAY_ITEMS=true,";
		} else {
			updateString += "DISPLAY_ITEMS=false,";
		}

		if (o.getMaterial() != null) {
			updateString += "MATERIAL=" + o.getMaterial().getId() + ",";
		}
		if (o.getTrueName() != null) {
			updateString += "TRUE_NAME='" + dbs.escapeQuotes(o.getTrueName())
					+ "',";
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}

		updateString += " WHERE ID = " + dbs.escapeQuotes(o.getInstanceId());

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

	public Vector<Weapon> getPlayerWeapons(String playerId) {
		String selectString = "SELECT w.*, pw.bonus, pw.description,"
				+ " pw.ability1, pw.ability2, pw.ability3, pw.ability4, pw.ability5, pw.ability6, pw.intelligent, pw.masterwork, "
				+ " pw.id as INSTANCE_ID, pw.use, pw.NOTES, pw.corder, pw.crit_start as pcs, pw.named, pw.true_name,"
				+ " pw.crit_end as pce, pw.crit_mult as pcm, pw.SIZE_OVERRIDE, pw.DAMAGE_OVERRIDE, pw.use_monk, pw.DISPLAY_SHEET, pw.DISPLAY_ITEMS, pw.quantity, pw.material, m.MATERIAL_NAME, m.DESCRIPTION as MDESC, m.WEIGHT_CALC "
				+ " FROM WEAPONS w, PLAYER_WEAPONS pw, MATERIALS m "
				+ "WHERE pw.PLAYER_ID="
				+ playerId
				+ " AND w.id = pw.weapon_id and pw.material = m.ID ORDER BY pw.corder";

		Vector<Weapon> v = new Vector<Weapon>();
		try {

			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				
				result = dbs.executeSQLQuery(selectString);
			} else {
				
				result = dbs2.executeSQLQuery(selectString);
			}

			while (result.next()) {
				Weapon obj = new Weapon();

				obj.setId(result.getString("ID"));
				obj.setName(result.getString("NAME"));
				obj.setCost(result.getString("COST"));
				obj.setDamage(result.getString("DAMAGE"));
				obj.setCritStart(result.getString("CRIT_START"));
				obj.setCritEnd(result.getString("CRIT_END"));
				obj.setCritMult(result.getString("CRIT_MULT"));
				obj.setRange(result.getString("RANGE"));
				obj.setWeight(result.getString("WEIGHT"));
				obj.setType(result.getString("TYPE"));
				obj.setSize(result.getString("SIZE"));
				obj.setCategory(result.getString("CATEGORY"));
				obj.setRangedmelee(result.getString("RANGEDMELEE"));
				obj.setSource(result.getString("SOURCE"));
				obj.setNotes1(result.getString("NOTES1"));
				obj.setNotes2(result.getString("NOTES2"));
				obj.setExclude(result.getString("EXCLUDE"));
				obj.setBonus(result.getString("bonus"));
				obj.setFeatClass(result.getString("FEAT_CLASS"));
				obj.setOrder(result.getInt("CORDER"));
				obj.setSizeOverride(result.getString("SIZE_OVERRIDE"));
				obj.setDamageOverride(result.getString("DAMAGE_OVERRIDE"));
				obj.setDisplaySheet(result.getBoolean("DISPLAY_SHEET"));
				obj.setDisplayItem(result.getBoolean("DISPLAY_ITEMS"));
				obj.setQuantity(result.getString("QUANTITY"));
			
				// check for player override of crit range/multiplier
				String critStart = result.getString("PCS");
				String critEnd = result.getString("PCE");
				String critMult = result.getString("PCM");

				if (critStart != null && !critStart.equals("")
						&& !critStart.equals("0")) {
					obj.setCritStart(critStart);
					obj.setCritOverride(true);
				}
				if (critEnd != null && !critEnd.equals("")
						&& !critEnd.equals("0")) {
					obj.setCritEnd(critEnd);
					obj.setCritOverride(true);
				}
				if (critMult != null && !critMult.equals("")
						&& !critMult.equals("0")) {
					obj.setCritMult(critMult);
					obj.setCritOverride(true);
				}

				obj.setDescription(result.getString("description"));
				WeaponAbilitiesDAO wadb = new WeaponAbilitiesDAO(true);

				String ab1 = result.getString("ability1");
				if (ab1 != null && !ab1.equals("") && !ab1.equals("null")) {
					
					obj.setAbility1(wadb.getWeaponAbility(ab1));
				}

				String ab2 = result.getString("ability2");
				if (ab2 != null && !ab2.equals("") && !ab2.equals("null")) {
					obj.setAbility2(wadb.getWeaponAbility(ab2));
				}

				String ab3 = result.getString("ability3");
				if (ab3 != null && !ab3.equals("") && !ab3.equals("null")) {
					obj.setAbility3(wadb.getWeaponAbility(ab3));
				}

				String ab4 = result.getString("ability4");
				if (ab4 != null && !ab4.equals("") && !ab4.equals("null")) {
					
					obj.setAbility4(wadb.getWeaponAbility(ab4));
				}

				String ab5 = result.getString("ability5");
				if (ab5 != null && !ab5.equals("") && !ab5.equals("null")) {
					obj.setAbility5(wadb.getWeaponAbility(ab5));
				}

				String ab6 = result.getString("ability6");
				if (ab6 != null && !ab6.equals("") && !ab6.equals("null")) {
					obj.setAbility6(wadb.getWeaponAbility(ab6));
				}
				
				obj.setInstanceId(result.getString("INSTANCE_ID"));
				

				obj.setIntelligent(result.getBoolean("intelligent"));
				obj.setMasterwork(result.getBoolean("masterwork"));

				obj.setUse(result.getString("USE"));
				obj.setNotes(result.getString("NOTES"));
				obj.setUseMonk(result.getBoolean("use_monk"));

				Materials m = new Materials();
				m.setId(result.getString("material"));
				m.setDescription(result.getString("MDESC"));
				m.setWeightCalc(result.getString("WEIGHT_CALC"));
				m.setMaterialName(result.getString("MATERIAL_NAME"));
				obj.setMaterial(m);

				obj.setNamed(result.getBoolean("named"));
				obj.setTrueName(result.getString("TRUE_NAME"));
				
				WeaponViewsDAO wvdb = new WeaponViewsDAO(true);
				Vector<WeaponViews> af = wvdb.getWeaponViews(obj.getInstanceId());
				obj.setFeatsApplied(af);

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

	public void clearPlayerWeapons(String playerId) {
		try {
			// open the connection
			dbs.open();
			String command = "DELETE FROM PLAYER_WEAPONS WHERE PLAYER_ID="
					+ playerId;
			dbs.executeSQLCommand(command);
		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		} finally {
			resetConnection();
		}

		// close the connection
		dbs.close();
	}

	public void deletePlayerWeapons(Weapon w) {
		String deleteString = "delete from PLAYER_WEAPONS  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(w.getInstanceId());

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

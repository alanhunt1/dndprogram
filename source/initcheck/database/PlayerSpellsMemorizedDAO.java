package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PlayerSpellsMemorizedDAO extends InitBaseDAO {

	public PlayerSpellsMemorizedDAO() {

	}
	public PlayerSpellsMemorizedDAO(DBSession dbs2) {
		this.dbs2 = dbs2;
	}
	public void addPlayerSpellsMemorized(Spell o, String playerId, String spellClass) {
		String valueString = "";
		String insertString = "INSERT INTO PLAYER_SPELLS_MEMORIZED (";
		
		if (playerId != null) {
			insertString += "PLAYER_ID,";
			if (playerId.equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(playerId) + ",";

			}
		}
		if (o.getId() != null) {
			insertString += "SPELL_ID,";
			if (o.getId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getId()) + ",";

			}
		}
		if (spellClass != null) {
			insertString += "SPELL_CLASS,";
			valueString += "'" + dbs.escapeQuotes(spellClass) + "',";
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
		}
	}

	public void updatePlayerSpellsMemorized(PlayerSpells o) {
		String updateString = "update PLAYER_SPELLS_MEMORIZED set ";
		if (o.getPlayerId() != null) {
			if (!o.getPlayerId().equals("")) {
				updateString += "PLAYER_ID="
						+ dbs.escapeQuotes(o.getPlayerId()) + ",";
			} else {
				updateString += "PLAYER_ID=null,";
			}
		}
		if (o.getSpellId() != null) {
			updateString += "SPELL_ID='" + dbs.escapeQuotes(o.getSpellId())
					+ "',";
		}
		if (o.getSpellClass() != null) {
			updateString += "SPELL_CLASS='"+o.getSpellClass()+"',";
		}
		if (o.isUsed()) {
			updateString += "USED=true,";
		}else{
			updateString += "USED=false,";
		}
		
		if (o.getMetamagic().size() > 0){
			Feat f = (Feat)o.getMetamagic().get(0);
			updateString += "METAMAGIC_ID_1="+f.getId()+",";
		}
		if (o.getMetamagic().size() > 1){
			Feat f = (Feat)o.getMetamagic().get(1);
			updateString += "METAMAGIC_ID_2="+f.getId()+",";
		}
		if (o.getMetamagic().size() > 2){
			Feat f = (Feat)o.getMetamagic().get(2);
			updateString += "METAMAGIC_ID_3="+f.getId()+",";
		}
		if (o.getMetamagic().size() > 3){
			Feat f = (Feat)o.getMetamagic().get(3);
			updateString += "METAMAGIC_ID_4="+f.getId()+",";
		}
		if (o.getMetamagic().size() > 4){
			Feat f = (Feat)o.getMetamagic().get(4);
			updateString += "METAMAGIC_ID_5="+f.getId()+",";
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
		}
	}
	
	public Vector<PlayerSpells> getPlayerSpellsMemorized(String playerId) {
		PlayerSpells ps = new PlayerSpells();
		ps.setPlayerId(playerId);
		return selectPlayerSpellsMemorized(ps);
	}
	
	public Vector<PlayerSpells> selectPlayerSpellsMemorized(PlayerSpells o) {
		String selectString = "SELECT s.SPELL_NAME, right(s.CLEVEL, 1)as CLEVEL, ps.*, sl.SPELL_LEVEL "+
		"FROM SPELLS s, PLAYER_SPELLS_MEMORIZED ps, SPELL_CLASSES sl "+
		"WHERE s.ID = ps.SPELL_ID and s.id = sl.SPELL_ID and ps.SPELL_CLASS = sl.SPELL_CLASS";
		boolean first = false;
		if (o.getId() != null && !o.getId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ID = '" + dbs.escapeQuotes(o.getId()) + "' ";
		}
		if (o.getPlayerId() != null && !o.getPlayerId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " PLAYER_ID = "
					+ dbs.escapeQuotes(o.getPlayerId()) + " ";
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
		selectString += " ORDER BY right(CLEVEL,1), SPELL_NAME";
		
		Vector<PlayerSpells> v = new Vector<PlayerSpells>();
		
		try {
			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}

			while (result.next()) {
				PlayerSpells obj = new PlayerSpells();
				obj.setSpellName(result.getString("SPELL_NAME"));
				obj.setLevel(result.getString("CLEVEL"));
				obj.setId(result.getString("ID"));
				obj.setPlayerId(result.getString("PLAYER_ID"));
				obj.setSpellId(result.getString("SPELL_ID"));
				obj.setUsed(result.getBoolean("USED"));
				obj.setSpellClass(result.getString("SPELL_CLASS"));
				obj.setSpellLevel(result.getString("SPELL_LEVEL"));
				int metamagicIncrease = 0;
				String mm1 = result.getString("METAMAGIC_ID_1");
				FeatDAO fdb = new FeatDAO();
				if (mm1 != null){
					Feat f = fdb.getFeat(mm1);
					if (f != null){
						obj.addMetamagic(f);
						metamagicIncrease += f.getMetamagicLevel();
					}
				}
				String mm2 = result.getString("METAMAGIC_ID_2");
				
				if (mm2 != null){
					Feat f = fdb.getFeat(mm2);
					if (f != null){
						obj.addMetamagic(f);
						metamagicIncrease += f.getMetamagicLevel();
					}
				}
				String mm3 = result.getString("METAMAGIC_ID_3");
				if (mm3 != null){
					Feat f = fdb.getFeat(mm3);
					if (f != null){
						obj.addMetamagic(f);
						metamagicIncrease += f.getMetamagicLevel();
					}
				}
				String mm4 = result.getString("METAMAGIC_ID_4");
				if (mm4 != null){
					Feat f = fdb.getFeat(mm4);
					if (f != null){
					obj.addMetamagic(f);
					metamagicIncrease += f.getMetamagicLevel();
					}
				}
				String mm5 = result.getString("METAMAGIC_ID_5");
				if (mm5 != null){
					Feat f = fdb.getFeat(mm5);
					if (f != null){
					obj.addMetamagic(f);
					metamagicIncrease += f.getMetamagicLevel();
					}
				}
				if (metamagicIncrease > 0){
					int spellLevel = Integer.parseInt(obj.getSpellLevel());
					spellLevel += metamagicIncrease;
					obj.setSpellLevel(""+spellLevel);
				}
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

	public void deletePlayerSpellsMemorized(PlayerSpells o) {
		String deleteString = "delete from PLAYER_SPELLS_MEMORIZED  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + " ";

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}
}
package initcheck.database;

import initcheck.character.library.LibraryItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class FeatDAO extends InitBaseDAO implements LibraryItemDAO {

	private static Vector<Feat> feats = null;

	public FeatDAO() {

	}

	public int addOrUpdateFeat(Feat o) {
		int i = -1;
		boolean added = false;
		
		// try and update the feat if it has a feat ID attached.
		if (o.getId() != null) {
			int numRows = updateFeat(o);
			added = numRows > 0;
			i = Integer.parseInt(o.getId());
		} 
		
		// if the update was from an imported feat that had a nonexistant id,
		// then add it instead.
		if (!added) {
			addFeat(o);
			try {
				
				dbs.open();
				String command = "SELECT MAX(ID) as MAXID FROM FEATS";
				
				ResultSet result = dbs.executeSQLQuery(command);
				if (result.next()) {
					i = result.getInt("MAXID");
				}

			} catch (Exception uhe) {
				logger.log("ERROR : " + uhe.toString());
			} finally {
				dbs.close();			
			}
		}
		
		resetConnection();
		feats = null;
		return i;
	}

	public void addFeat(Feat o) {
		String valueString = "";
		String insertString = "INSERT INTO FEATS (";
		if (o.getId() != null) {
			insertString += "ID,";
			valueString += "'" + dbs.escapeQuotes(o.getId()) + "',";
		}
		if (o.getFeatName() != null) {
			insertString += "FEAT_NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getFeatName()) + "',";
		}
		if (o.getDescription() != null) {
			insertString += "DESCRIPTION,";
			valueString += "'" + dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getType() != null) {
			insertString += "TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getType()) + "',";
		}
		if (o.getType2() != null) {
			insertString += "TYPE2,";
			valueString += "'" + dbs.escapeQuotes(o.getType2()) + "',";
		}
		if (o.getType3() != null) {
			insertString += "TYPE3,";
			valueString += "'" + dbs.escapeQuotes(o.getType3()) + "',";
		}
		if (o.getFeatType() != null) {
			insertString += "FEAT_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getFeatType()) + "',";
		}
		if (o.getAttackBonus() != null) {
			insertString += "ATTACK_BONUS,";
			valueString += "'" + dbs.escapeQuotes(o.getAttackBonus()) + "',";
		}
		if (o.getDamageBonus() != null) {
			insertString += "DAMAGE_BONUS,";
			valueString += "'" + dbs.escapeQuotes(o.getDamageBonus()) + "',";
		}
		if (o.getRangeLimit() != null) {
			insertString += "RANGE_LIMIT,";
			valueString += "'" + dbs.escapeQuotes(o.getRangeLimit()) + "',";
		}
		if (o.getShortText() != null) {
			insertString += "SHORT_TEXT,";
			valueString += "'" + dbs.escapeQuotes(o.getShortText()) + "',";
		}

		insertString += "HP_FEAT,";
		valueString += o.isHpFeat() + ",";

		insertString += "AC_FEAT,";
		valueString += o.isAcFeat() + ",";

		insertString += "STAT_FEAT,";
		valueString += o.isStatFeat() + ",";

		insertString += "INIT_FEAT,";
		valueString += o.isInitFeat() + ",";

		insertString += "SAVE_FEAT,";
		valueString += o.isSaveFeat() + ",";

		insertString += "SKILL_FEAT,";
		valueString += o.isSkillFeat() + ",";

		insertString += "TEMP_BONUS,";
		valueString += o.isTempBonus() + ",";

		if (o.getTempType() != null) {
			insertString += "TEMP_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getTempType()) + "',";
		}

		insertString += "METAMAGIC_LEVEL,";
		valueString += o.getMetamagicLevel() + ",";

		if (o.getMetamagicPrefix() != null) {
			insertString += "METAMAGIC_PREFIX,";
			valueString += "'" + dbs.escapeQuotes(o.getMetamagicPrefix())
					+ "',";
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

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}

	public int updateFeat(Feat o) {
		int rows = 0;
		String updateString = "update FEATS set ";
		if (o.getFeatName() != null) {
			updateString += "FEAT_NAME='" + dbs.escapeQuotes(o.getFeatName())
					+ "',";
		}
		if (o.getDescription() != null) {
			updateString += "DESCRIPTION='"
					+ dbs.escapeQuotes(o.getDescription()) + "',";
		}
		if (o.getType() != null) {
			updateString += "TYPE='" + dbs.escapeQuotes(o.getType()) + "',";
		}else{
			updateString += "TYPE='',";
		}
		if (o.getType2() != null) {
			updateString += "TYPE2='" + dbs.escapeQuotes(o.getType2()) + "',";
		}else{
			updateString += "TYPE2='',";
		}
		if (o.getType3() != null) {
			updateString += "TYPE3='" + dbs.escapeQuotes(o.getType3()) + "',";
		}else{
			updateString += "TYPE3='',";
		}
		if (o.getFeatType() != null) {
			updateString += "FEAT_TYPE='" + dbs.escapeQuotes(o.getFeatType())
					+ "',";
		}
		if (o.getAttackBonus() != null) {
			updateString += "ATTACK_BONUS='"
					+ dbs.escapeQuotes(o.getAttackBonus()) + "',";
		}
		if (o.getDamageBonus() != null) {
			updateString += "DAMAGE_BONUS='"
					+ dbs.escapeQuotes(o.getDamageBonus()) + "',";
		}
		if (o.getRangeLimit() != null) {
			updateString += "RANGE_LIMIT='"
					+ dbs.escapeQuotes(o.getRangeLimit()) + "',";
		}
		if (o.getShortText() != null) {
			updateString += "SHORT_TEXT='" + dbs.escapeQuotes(o.getShortText())
					+ "',";
		}

		updateString += "HP_FEAT=" + o.isHpFeat() + ",";

		updateString += "AC_FEAT=" + o.isAcFeat() + ",";

		updateString += "STAT_FEAT=" + o.isStatFeat() + ",";

		updateString += "INIT_FEAT=" + o.isInitFeat() + ",";

		updateString += "SAVE_FEAT=" + o.isSaveFeat() + ",";

		updateString += "SKILL_FEAT=" + o.isSkillFeat() + ",";

		updateString += "TEMP_BONUS=" + o.isTempBonus() + ",";

		if (o.getTempType() != null) {
			updateString += "TEMP_TYPE='" + dbs.escapeQuotes(o.getTempType())
					+ "',";
		}

		updateString += "METAMAGIC_LEVEL=" + o.getMetamagicLevel() + ",";

		if (o.getMetamagicPrefix() != null) {
			updateString += "METAMAGIC_PREFIX='"
					+ dbs.escapeQuotes(o.getMetamagicPrefix()) + "',";
		}
		if (o.getSource() != null) {
			updateString += "SOURCE='" + dbs.escapeQuotes(o.getSource()) + "',";
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + "  ";
		try {
			dbs.open();
			
			rows = dbs.executeSQLCommand(updateString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
		return rows;
	}

	public Vector<LibraryItem> getItems(LibraryItem i){
		String queryString = "SELECT * FROM FEATS WHERE SOURCE = '"+i.getSource()+"' "+
		" ORDER BY FEAT_NAME";
		Vector<LibraryItem> results = new Vector<LibraryItem>();
		results.addAll(getFeatsFromQuery(queryString));
		return results;
	}
	
	public Vector<Feat> getFeats() {
		if (feats != null) {
				return feats;
		}
		String queryString = "SELECT * FROM FEATS ORDER BY FEAT_NAME";
		feats =  getFeatsFromQuery(queryString);
		return feats;
	}
	
	public Vector<Feat> getFeatsFromQuery(String queryString) {

		Vector<Feat> v = new Vector<Feat>();
		try {
			
			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(queryString);
			} else {
				result = dbs2.executeSQLQuery(queryString);
			}

			while (result.next()) {

				Feat obj = new Feat();

				obj.setId(result.getString("ID"));
				obj.setFeatName(result.getString("FEAT_NAME"));
				obj.setDescription(result.getString("DESCRIPTION"));
				obj.setType(result.getString("TYPE"));
				obj.setType2(result.getString("TYPE2"));
				obj.setType3(result.getString("TYPE3"));
				obj.setFeatType(result.getString("FEAT_TYPE"));
				obj.setAttackBonus(result.getString("ATTACK_BONUS"));
				obj.setDamageBonus(result.getString("DAMAGE_BONUS"));
				obj.setRangeLimit(result.getString("RANGE_LIMIT"));
				obj.setShortText(result.getString("SHORT_TEXT"));
				obj.setSaveFeat(result.getBoolean("SAVE_FEAT"));
				obj.setStatFeat(result.getBoolean("STAT_FEAT"));
				obj.setHpFeat(result.getBoolean("HP_FEAT"));
				obj.setAcFeat(result.getBoolean("AC_FEAT"));
				obj.setInitFeat(result.getBoolean("INIT_FEAT"));
				obj.setSkillFeat(result.getBoolean("SKILL_FEAT"));
				obj.setTempBonus(result.getBoolean("TEMP_BONUS"));
				obj.setTempType(result.getString("TEMP_TYPE"));
				obj.setMetamagicLevel(result.getInt("METAMAGIC_LEVEL"));
				obj.setMetamagicPrefix(result.getString("METAMAGIC_PREFIX"));
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

	public boolean featExists(String name) {
		boolean exists = false;
		try {

			String queryString = "SELECT * FROM FEATS WHERE FEAT_NAME='" + name
					+ "' " + " OR FEAT_NAME = '" + name.toUpperCase() + "'";

			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(queryString);
			} else {
				result = dbs2.executeSQLQuery(queryString);
			}

			if (result.next()) {
				exists = true;
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

		return exists;
	}

	public boolean exists(Feat f) {
		boolean exists = false;
		try {

			String queryString = "SELECT * FROM FEATS WHERE ID=" + f.getId();

			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(queryString);
			} else {
				result = dbs2.executeSQLQuery(queryString);
			}

			if (result.next()) {
				exists = true;
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

		return exists;
	}

	public Feat getFeat(String id) {
			String queryString = "SELECT * FROM FEATS WHERE ID=" + id;
			Vector<Feat> v= getFeatsFromQuery(queryString);
			if (v.size() > 0){
				return (Feat)v.get(0);
			}
			return null;
	}
			
	public Vector<Feat> getFeats(String keyword, Campaign c) {
		String queryString = "SELECT * FROM FEATS ";
		boolean first = true;
		if (keyword != null && !keyword.equals("")) {
			queryString += "WHERE (DESCRIPTION like '%" + keyword
					+ "%' OR FEAT_NAME LIKE '%" + keyword + "%')";
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
			queryString += "SOURCE='"+cs.getName()+"' OR EXISTS (SELECT 'x' FROM CAMPAIGN_WHITE_LIST WHERE CAMPAIGN_ID = "+
		c.getId()+ " AND XREF_ID = FEATS.ID AND TYPE = 'Feat')";
			
			queryString += ") ";
		}
		queryString += " AND NOT EXISTS (SELECT 'x' FROM CAMPAIGN_BLACK_LIST WHERE CAMPAIGN_ID = "+
		c.getId()+ " AND XREF_ID = FEATS.ID AND TYPE = 'Feat')";
		queryString += " ORDER BY FEAT_NAME";

		return getFeatsFromQuery(queryString);
	}
	
	public Vector<Feat> getFeats(String keyword) {
		

			String queryString = "SELECT * FROM FEATS ";

			if (keyword != null && !keyword.equals("")) {
				queryString += "WHERE DESCRIPTION like '%" + keyword
						+ "%' OR FEAT_NAME LIKE '%" + keyword + "%'";
			}

			queryString += " ORDER BY FEAT_NAME";

			return getFeatsFromQuery(queryString);
	}		

	public Vector<Feat> getFeatsBySource(String source) {
		

		String queryString = "SELECT * FROM FEATS WHERE SOURCE='"+source+"'";
		
		

		queryString += " ORDER BY FEAT_NAME";

		return getFeatsFromQuery(queryString);
	}		

	
	public void deleteFeat(Feat f) {

		try {
			// open the connection
			dbs.open();

			// build the SQL statement
			String delString = "DELETE FROM FEATS WHERE " + "ID=" + f.getId();

			// execute the delete
			dbs.executeSQLCommand(delString);
		} catch (Exception uhe) {
		}
		// close the connection
		dbs.close();
		resetConnection();
		feats = null;
	}

}

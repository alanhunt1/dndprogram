package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

public class FeatEffectsDAO extends InitBaseDAO {

	static HashMap<String, Vector<FeatEffects>> effectHash = null;
	
	public FeatEffectsDAO() {

	}

	public void addFeatEffects(FeatEffects o) {
		String valueString = "";
		String insertString = "INSERT INTO FEAT_EFFECTS (";
		
		if (o.getFeatId() != null) {
			insertString += "FEAT_ID,";
			if (o.getFeatId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getFeatId()) + ",";

			}
		}
		if (o.getCrossRefId() != null) {
			insertString += "CROSS_REF_ID,";
			if (o.getCrossRefId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getCrossRefId()) + ",";

			}
		}
		if (o.getEffectType() != null) {
			insertString += "EFFECT_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getEffectType()) + "',";
		}
		if (o.getEffectAmount() != null) {
			insertString += "EFFECT_AMOUNT,";
			valueString += "'" + dbs.escapeQuotes(o.getEffectAmount()) + "',";
		}
		if (o.getEffectCategory() != null) {
			insertString += "EFFECT_CATEGORY,";
			valueString += "'" + dbs.escapeQuotes(o.getEffectCategory()) + "',";
		}
		if (o.getConditional() != null) {
			insertString += "CONDITIONAL,";
			valueString += "'" + dbs.escapeQuotes(o.getConditional()) + "',";
		}
		if (o.getMisc() != null) {
			insertString += "MISC,";
			valueString += "'" + dbs.escapeQuotes(o.getMisc()) + "',";
		}
		if (o.getEffectName() != null) {
			insertString += "EFFECT_NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getEffectName()) + "',";
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
			effectHash = null;
		}
	}

	public void updateFeatEffects(FeatEffects o) {
		String updateString = "update FEAT_EFFECTS set ";
		if (o.getFeatId() != null) {
			if (!o.getFeatId().equals("")) {
				updateString += "FEAT_ID=" + dbs.escapeQuotes(o.getFeatId())
						+ ",";
			} else {
				updateString += "FEAT_ID=null,";
			}
		}
		if (o.getCrossRefId() != null) {
			if (!o.getCrossRefId().equals("")) {
				updateString += "CROSS_REF_ID=" + dbs.escapeQuotes(o.getCrossRefId())
						+ ",";
			} else {
				updateString += "CROSS_REF_ID=null,";
			}
		}
		if (o.getEffectType() != null) {
			updateString += "EFFECT_TYPE='"
					+ dbs.escapeQuotes(o.getEffectType()) + "',";
		}
		if (o.getEffectAmount() != null) {
			updateString += "EFFECT_AMOUNT='"
					+ dbs.escapeQuotes(o.getEffectAmount()) + "',";
		}
		if (o.getEffectCategory() != null) {
			updateString += "EFFECT_CATEGORY='"
					+ dbs.escapeQuotes(o.getEffectCategory()) + "',";
		}
		if (o.getConditional() != null) {
			updateString += "CONDITIONAL='"
					+ dbs.escapeQuotes(o.getConditional()) + "',";
		}
		if (o.getMisc() != null) {
			updateString += "MISC='" + dbs.escapeQuotes(o.getMisc()) + "',";
		}
		if (o.getEffectName() != null) {
			updateString += "EFFECT_NAME='" + dbs.escapeQuotes(o.getEffectName()) + "',";
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
			effectHash = null;
		}
	}

	public Vector<FeatEffects> getFeatEffects(String id){
		// check the cache
		if (effectHash != null){
			Vector<FeatEffects> v = (Vector<FeatEffects>)effectHash.get(id);
			if (v != null){
				return v;
			}
		}else{
			effectHash = new HashMap<String, Vector<FeatEffects>>();
		}
		
		FeatEffects fe = new FeatEffects();
		fe.setFeatId(id);
		Vector<FeatEffects> ans = selectFeatEffects(fe);
		effectHash.put(id, ans);
		return ans;
	}
	
	public Vector<FeatEffects> selectFeatEffects(FeatEffects o) {
		String selectString = "SELECT * FROM FEAT_EFFECTS  ";
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
		if (o.getFeatId() != null && !o.getFeatId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " FEAT_ID = " + dbs.escapeQuotes(o.getFeatId())
					+ "";
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
		if (o.getEffectAmount() != null && !o.getEffectAmount().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " EFFECT_AMOUNT = '"
					+ dbs.escapeQuotes(o.getEffectAmount()) + "' ";
		}
		if (o.getEffectCategory() != null && !o.getEffectCategory().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " EFFECT_CATEGORY = '"
					+ dbs.escapeQuotes(o.getEffectCategory()) + "' ";
		}
		if (o.getConditional() != null && !o.getConditional().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " CONDITIONAL = '"
					+ dbs.escapeQuotes(o.getConditional()) + "' ";
		}
		if (o.getMisc() != null && !o.getMisc().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " MISC = '" + dbs.escapeQuotes(o.getMisc()) + "' ";
		}
		Vector<FeatEffects> v = new Vector<FeatEffects>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				FeatEffects obj = new FeatEffects();

				obj.setId(result.getString("ID"));
				obj.setFeatId(result.getString("FEAT_ID"));
				obj.setEffectType(result.getString("EFFECT_TYPE"));
				obj.setEffectAmount(result.getString("EFFECT_AMOUNT"));
				obj.setEffectCategory(result.getString("EFFECT_CATEGORY"));
				obj.setConditional(result.getString("CONDITIONAL"));
				obj.setMisc(result.getString("MISC"));
				obj.setEffectName(result.getString("EFFECT_NAME"));
				obj.setCrossRefId(result.getString("CROSS_REF_ID"));
				v.add(obj);
			}
		} catch (SQLException sqle) {
			logger.log("error", sqle.toString());
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
		return v;
	}

	public void clearEffects(String featId){
		String deleteString = "delete from FEAT_EFFECTS  ";
		deleteString += " WHERE FEAT_ID = " + featId + " ";

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
			effectHash = null;
		}
	}
	
	public void deleteFeatEffects(FeatEffects o) {
		String deleteString = "delete from FEAT_EFFECTS  ";
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
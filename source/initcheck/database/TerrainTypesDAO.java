package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TerrainTypesDAO extends InitBaseDAO {

	public TerrainTypesDAO() {

	}

	public int addOrUpdateTerrainTypes(TerrainTypes o) {
		int i = -1;
		if (o.getId() != null) {
			updateTerrainTypes(o);
		} else {
			addTerrainTypes(o);
		}
		try {
			// open the connection
			dbs.open();
			String command = "SELECT MAX(ID) as MAXID FROM TERRAIN_TYPES";
			
			ResultSet result = dbs.executeSQLQuery(command);
			if (result.next()) {
				i = result.getInt("MAXID");
			}

		} catch (Exception uhe) {
			logger.log("ERROR : " + uhe.toString());
		}
		// close the connection
		dbs.close();
		return i;
	}

	public void addTerrainTypes(TerrainTypes o) {
		String valueString = "";
		String insertString = "INSERT INTO TERRAIN_TYPES (";
		if (o.getId() != null) {
			insertString += "ID,";
			valueString += "'" + dbs.escapeQuotes(o.getId()) + "',";
		}
		if (o.getTerrainName() != null) {
			insertString += "TERRAIN_NAME,";
			valueString += "'" + dbs.escapeQuotes(o.getTerrainName()) + "',";
		}
		if (o.getMapType() != null) {
			insertString += "MAP_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getMapType()) + "',";
		}
		if (o.getTerrainTypeId() != null) {
			insertString += "TERRAIN_TYPE_ID,";
			if (o.getTerrainTypeId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getTerrainTypeId()) + ",";

			}
		}
		if (o.getIcon() != null) {
			insertString += "ICON,";
			valueString += "'" + dbs.escapeQuotes(o.getIcon()) + "',";
		}
		if (o.getRenderType() != null) {
			insertString += "RENDER_TYPE,";
			valueString += "'" + dbs.escapeQuotes(o.getRenderType()) + "',";
		}
		if (o.getMovementRate() != null) {
			insertString += "MOVEMENT_RATE,";
			if (o.getMovementRate().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getMovementRate()) + ",";

			}
		}
		if (o.getPrimaryColor() != null) {
			insertString += "PRIMARY_COLOR,";
			valueString += "'" + dbs.escapeQuotes(o.getPrimaryColor()) + "',";
		}
		if (o.getPattern() != null) {
			insertString += "PATTERN,";
			valueString += "'" + dbs.escapeQuotes(o.getPattern()) + "',";
		}
		if (o.getSecondaryColor() != null) {
			insertString += "SECONDARY_COLOR,";
			valueString += "'" + dbs.escapeQuotes(o.getSecondaryColor()) + "',";
		}
	
			insertString += "POINT,";
			valueString += "" + o.isPoint()+ ",";
		
			insertString += "LINE,";
			valueString += "" + o.isLine()+ ",";
		
			insertString += "AREA,";
			valueString += "" + o.isArea()+ ",";
		
			insertString += "FLOOD,";
			valueString += "" + o.isFlood()+ ",";
		
		if (o.getImage1() != null) {
			insertString += "IMAGE1,";
			valueString += "'" + dbs.escapeQuotes(o.getImage1()) + "',";
		}
		if (o.getImage2() != null) {
			insertString += "IMAGE2,";
			valueString += "'" + dbs.escapeQuotes(o.getImage2()) + "',";
		}
		if (o.getImage3() != null) {
			insertString += "IMAGE3,";
			valueString += "'" + dbs.escapeQuotes(o.getImage3()) + "',";
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

	public void updateTerrainTypes(TerrainTypes o) {
		String updateString = "update TERRAIN_TYPES set ";
		if (o.getTerrainName() != null) {
			updateString += "TERRAIN_NAME='"
					+ dbs.escapeQuotes(o.getTerrainName()) + "',";
		}
		if (o.getMapType() != null) {
			updateString += "MAP_TYPE='" + dbs.escapeQuotes(o.getMapType())
					+ "',";
		}
		if (o.getTerrainTypeId() != null) {
			if (!o.getTerrainTypeId().equals("")) {
				updateString += "TERRAIN_TYPE_ID="
						+ dbs.escapeQuotes(o.getTerrainTypeId()) + ",";
			} else {
				updateString += "TERRAIN_TYPE_ID=null,";
			}
		}
		if (o.getIcon() != null) {
			updateString += "ICON='" + dbs.escapeQuotes(o.getIcon()) + "',";
		}
		if (o.getRenderType() != null) {
			updateString += "RENDER_TYPE='"
					+ dbs.escapeQuotes(o.getRenderType()) + "',";
		}
		if (o.getMovementRate() != null) {
			if (!o.getMovementRate().equals("")) {
				updateString += "MOVEMENT_RATE="
						+ dbs.escapeQuotes(o.getMovementRate()) + ",";
			} else {
				updateString += "MOVEMENT_RATE=null,";
			}
		}
		if (o.getPrimaryColor() != null) {
			updateString += "PRIMARY_COLOR='"
					+ dbs.escapeQuotes(o.getPrimaryColor()) + "',";
		}
		if (o.getPattern() != null) {
			updateString += "PATTERN='" + dbs.escapeQuotes(o.getPattern())
					+ "',";
		}
		if (o.getSecondaryColor() != null) {
			updateString += "SECONDARY_COLOR='"
					+ dbs.escapeQuotes(o.getSecondaryColor()) + "',";
		}
		
			updateString += "POINT=" + o.isPoint() + ",";
		
			updateString += "LINE=" + o.isLine() + ",";
		
			updateString += "AREA=" + o.isArea() + ",";
		
			updateString += "FLOOD=" + o.isFlood() + ",";
		
		if (o.getImage1() != null) {
			updateString += "IMAGE1='" + dbs.escapeQuotes(o.getImage1()) + "',";
		}
		if (o.getImage2() != null) {
			updateString += "IMAGE2='" + dbs.escapeQuotes(o.getImage2()) + "',";
		}
		if (o.getImage3() != null) {
			updateString += "IMAGE3='" + dbs.escapeQuotes(o.getImage3()) + "',";
		}
		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + "  ";
		try {
			dbs.open();
			dbs.executeSQLCommand(updateString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}

	public Vector<TerrainTypes> selectTerrainTypes(TerrainTypes o) {
		String selectString = "SELECT * FROM TERRAIN_TYPES  ";
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
		if (o.getTerrainName() != null && !o.getTerrainName().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " TERRAIN_NAME = '"
					+ dbs.escapeQuotes(o.getTerrainName()) + "' ";
		}
		if (o.getMapType() != null && !o.getMapType().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " MAP_TYPE = '" + dbs.escapeQuotes(o.getMapType())
					+ "' ";
		}
		if (o.getTerrainTypeId() != null && !o.getTerrainTypeId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " TERRAIN_TYPE_ID = '"
					+ dbs.escapeQuotes(o.getTerrainTypeId()) + "' ";
		}
		if (o.getIcon() != null && !o.getIcon().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ICON = '" + dbs.escapeQuotes(o.getIcon()) + "' ";
		}
		if (o.getRenderType() != null && !o.getRenderType().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " RENDER_TYPE = '"
					+ dbs.escapeQuotes(o.getRenderType()) + "' ";
		}
		if (o.getMovementRate() != null && !o.getMovementRate().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " MOVEMENT_RATE = '"
					+ dbs.escapeQuotes(o.getMovementRate()) + "' ";
		}
		if (o.getPrimaryColor() != null && !o.getPrimaryColor().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " PRIMARY_COLOR = '"
					+ dbs.escapeQuotes(o.getPrimaryColor()) + "' ";
		}
		if (o.getPattern() != null && !o.getPattern().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " PATTERN = '" + dbs.escapeQuotes(o.getPattern())
					+ "' ";
		}
		if (o.getSecondaryColor() != null && !o.getSecondaryColor().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " SECONDARY_COLOR = '"
					+ dbs.escapeQuotes(o.getSecondaryColor()) + "' ";
		}
		
		
		if (o.getImage1() != null && !o.getImage1().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " IMAGE1 = '" + dbs.escapeQuotes(o.getImage1())
					+ "' ";
		}
		if (o.getImage2() != null && !o.getImage2().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " IMAGE2 = '" + dbs.escapeQuotes(o.getImage2())
					+ "' ";
		}
		if (o.getImage3() != null && !o.getImage3().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " IMAGE3 = '" + dbs.escapeQuotes(o.getImage3())
					+ "' ";
		}
		return getTerrainTypesFromSql(selectString);
	}
	
	public Vector<TerrainTypes> getTerrainTypes(String mapType){
		String selectString = "SELECT * FROM TERRAIN_TYPES WHERE MAP_TYPE = '"+mapType+"'";
		return getTerrainTypesFromSql(selectString);
	}
	
	public Vector<TerrainTypes> getTerrainTypesFromSql(String selectString){
		Vector<TerrainTypes> v = new Vector<TerrainTypes>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				TerrainTypes obj = new TerrainTypes();

				obj.setId(result.getString("ID"));
				obj.setTerrainName(result.getString("TERRAIN_NAME"));
				obj.setMapType(result.getString("MAP_TYPE"));
				obj.setTerrainTypeId(result.getString("TERRAIN_TYPE_ID"));
				obj.setIcon(result.getString("ICON"));
				obj.setRenderType(result.getString("RENDER_TYPE"));
				obj.setMovementRate(result.getString("MOVEMENT_RATE"));
				obj.setPrimaryColor(result.getString("PRIMARY_COLOR"));
				obj.setPattern(result.getString("PATTERN"));
				obj.setSecondaryColor(result.getString("SECONDARY_COLOR"));
				obj.setPoint(result.getBoolean("POINT"));
				obj.setLine(result.getBoolean("LINE"));
				obj.setArea(result.getBoolean("AREA"));
				obj.setFlood(result.getBoolean("FLOOD"));
				obj.setImage1(result.getString("IMAGE1"));
				obj.setImage2(result.getString("IMAGE2"));
				obj.setImage3(result.getString("IMAGE3"));
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

	public void deleteTerrainTypes(TerrainTypes o) {
		String deleteString = "delete from TERRAIN_TYPES  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + "";

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
package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class LoadLimitsDAO extends InitBaseDAO {

	private static Vector<LoadLimits> loadVector = null;

	public LoadLimitsDAO() {
		selectLoadLimits(new LoadLimits());
	}

	private LoadLimits getLimits(int str){
			
		LoadLimits ll = null;
		for (int i = 0; i < loadVector.size(); i++){
			ll = loadVector.get(i);
			if (Integer.parseInt(ll.getStrength()) == str){
				return ll;
			}
		}
		return ll;
	}
	
	public LoadLimits getLoadLimits(int str, String size) {
			
		if (size == null) {
			size = "";
		}

		LoadLimits ll = new LoadLimits();

		// if your strength is "off the chart", adjust the values
		// accordingly
		if (str > 29) {
			int overage = str - 20;
			int indexStr = 20 + (str % 10);		
			ll.setStrength("" + indexStr);			
			Double mult = new Double(Math.pow(4.0, (overage / 10)));
			ll = (LoadLimits) (getLimits(indexStr).getClone());
			ll.adjustLimits(mult.intValue());
		} else {
			ll = (LoadLimits) (getLimits(str).getClone());
		}

		// then make adjustments for size
		if (size.equals("Fine")) {
			ll.decreaseLimits(8);
		} else if (size.equals("Diminutive")) {
			ll.decreaseLimits(4);
		} else if (size.equals("Tiny")) {
			ll.decreaseLimits(2);
		} else if (size.equals("Small")) {
			ll.adjustLimits(3);
			ll.decreaseLimits(4);
		} else if (size.equals("Large")) {
			ll.adjustLimits(2);
		} else if (size.equals("Huge")) {
			ll.adjustLimits(4);
		} else if (size.equals("Gargantuan")) {
			ll.adjustLimits(8);
		} else if (size.equals("Colossal")) {
			ll.adjustLimits(16);
		}
		
		return ll;
	}

	public Vector<LoadLimits> selectLoadLimits(LoadLimits o) {
		if (loadVector == null) {
			loadVector = new Vector<LoadLimits>();

			String selectString = "SELECT * FROM LOAD_LIMITS  ";
			boolean first = true;
			if (o.getStrength() != null && !o.getStrength().equals("")) {
				if (!first) {
					selectString += " AND ";
				} else {
					selectString += " WHERE ";
					first = false;
				}
				selectString += " STRENGTH = "
						+ dbs.escapeQuotes(o.getStrength()) + " ";
			}

			Vector<LoadLimits> v = new Vector<LoadLimits>();
			try {
				ResultSet result = null;
				if (dbs2 == null) {
					dbs.open();
					result = dbs.executeSQLQuery(selectString);
				} else {
					result = dbs2.executeSQLQuery(selectString);
				}

				while (result.next()) {
					LoadLimits obj = new LoadLimits();

					obj.setStrength(result.getString("STRENGTH"));
					obj.setLightLoad(result.getString("LIGHT_LOAD"));
					obj.setMediumLoad(result.getString("MEDIUM_LOAD"));
					obj.setHeavyLoad(result.getString("HEAVY_LOAD"));

					int heavy = Integer.parseInt(obj.getHeavyLoad());
					obj.setLift("" + heavy);
					obj.setLiftGround("" + (heavy * 2));
					obj.setPushDrag("" + (heavy * 5));

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
			loadVector = v;
		}
		return loadVector;
	}
}

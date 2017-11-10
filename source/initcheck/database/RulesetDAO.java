package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class RulesetDAO extends InitBaseDAO  {
	public Vector<Ruleset> selectRuleset(Ruleset o) {
		String selectString = "SELECT * FROM RULESETS  ";
		Vector<Ruleset> v = new Vector<Ruleset>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			System.out.println("RAN RULESET SELECT");
			while (result.next()) {
				Ruleset obj = new Ruleset();

				obj.setRulesetId(result.getString("RULESET_ID"));
				obj.setRulesetName(result.getString("RULESET_NAME"));
			System.out.println("Adding ruleset");
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
}

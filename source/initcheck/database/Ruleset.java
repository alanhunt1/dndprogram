package initcheck.database;

public class Ruleset {
	public String RulesetId;
	public String RulesetName;
	
	public String getRulesetId() {
		return RulesetId;
	}
	public void setRulesetId(String rulesetId) {
		RulesetId = rulesetId;
	}
	public String getRulesetName() {
		return RulesetName;
	}
	public void setRulesetName(String rulesetName) {
		RulesetName = rulesetName;
	}
	
	public String toString(){
		return RulesetName;
	}
}

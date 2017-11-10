package initcheck.status;

public class StatCurse extends StatusItem {

	private static final long serialVersionUID = 1L;

	public StatCurse(int i) {
		statusMod = i;
		setDefaults();
	}

	public String getName() {
		return "Curse";
	}

	public void setDefaults() {
		stacks = false;
		incap = false;
		timed = true;
		name = "Curse";
		description = "Enemy Attacks are at -1/+0";
		attackModOpp = -1;
	}
}

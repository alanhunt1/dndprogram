package initcheck.status;

public class StatBless extends StatusItem {

	private static final long serialVersionUID = 1L;

	public StatBless(int i) {
		statusMod = i;
		setDefaults();
	}

	public String getName() {
		return "Bless";
	}

	public void setDefaults() {
		stacks = true;
		incap = false;
		timed = true;
		name = "Bless";
		description = "Attacks are at +1/+0";
		attackMod = 1;

	}
}

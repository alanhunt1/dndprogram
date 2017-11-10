package initcheck.status;

public class StatSlowed extends StatusItem {

	private static final long serialVersionUID = 1L;

	public StatSlowed(int i) {
		statusMod = i;
		setDefaults();
	}

	public String getName() {
		return "Slow";
	}

	public void setDefaults() {
		stacks = false;
		incap = false;
		timed = true;
		name = "Slow";
		description = "Attacks and movement are at half normal rate";
	}
}

package initcheck.status;

public class StatHasted extends StatusItem {

	private static final long serialVersionUID = 1L;

	public StatHasted(int i) {
		statusMod = i;
		setDefaults();
	}

	public String getName() {
		return "Haste";
	}

	public void setDefaults() {
		stacks = false;
		incap = false;
		timed = true;
		name = "Haste";
		description = "Attacks and movement are at double normal rate";
	}
}

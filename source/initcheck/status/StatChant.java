package initcheck.status;

public class StatChant extends StatusItem {

	private static final long serialVersionUID = 1L;

	public StatChant(int i) {
		statusMod = i;
		setDefaults();
	}

	public String getName() {
		return "Chant";
	}

	public void setDefaults() {
		stacks = true;
		incap = false;
		timed = true;
		name = "Chant";
		description = "Attacks are at +1/+1";
		attackMod = 1;
		damageMod = 1;

	}
}

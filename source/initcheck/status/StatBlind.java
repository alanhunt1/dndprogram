package initcheck.status;

public class StatBlind extends StatusItem {

	private static final long serialVersionUID = 1L;

	public StatBlind() {
		statusMod = 1;
		setDefaults();
	}

	public StatBlind(int i) {
		statusMod = i;
		setDefaults();
	}

	public StatBlind(int i, boolean s) {
		statusMod = i;
		setDefaults();
		sequential = s;
	}

	public String getName() {
		return "Blind";
	}

	public void setDefaults() {
		incap = true;
		timed = true;
		name = "Blind";
		description = "Creature is blind : attack at -4";
	}
}

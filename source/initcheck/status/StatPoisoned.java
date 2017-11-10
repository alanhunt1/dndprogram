package initcheck.status;

public class StatPoisoned extends StatusItem {

	private static final long serialVersionUID = 1L;

	public StatPoisoned() {
		statusMod = 1;
		setDefaults();
	}

	public StatPoisoned(int i) {
		statusMod = i;
		setDefaults();
	}

	public StatPoisoned(int i, boolean s) {
		statusMod = i;
		setDefaults();
		sequential = s;
	}

	public void setDefaults() {
		incap = false;
		timed = false;
		name = "Poisoned";
		description = "Creature is poisoned";
	}
}

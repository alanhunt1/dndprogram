package initcheck.status;

public class StatUnconscious extends StatusItem {

	private static final long serialVersionUID = 1L;

	public StatUnconscious() {
		statusMod = 1;
		setDefaults();
	}

	public StatUnconscious(int i) {
		statusMod = i;
		setDefaults();
	}

	public StatUnconscious(int i, boolean s) {
		statusMod = i;
		setDefaults();
		sequential = s;
	}

	public void setDefaults() {
		incap = true;
		timed = true;
		name = "Unconscious";
		description = "Creature is unable to move or attack.";
	}
}

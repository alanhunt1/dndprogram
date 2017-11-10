package initcheck.status;

public class StatAsleep extends StatusItem {

	private static final long serialVersionUID = 1L;

	public StatAsleep() {
		statusMod = 1;
		setDefaults();
	}

	public StatAsleep(int i) {
		statusMod = i;
		setDefaults();
	}

	public StatAsleep(int i, boolean s) {
		statusMod = i;
		setDefaults();
		sequential = s;
	}

	public void setDefaults() {
		incap = true;
		timed = true;
		name = "Asleep";
		description = "Creature is unable to move or attack.";
	}
}

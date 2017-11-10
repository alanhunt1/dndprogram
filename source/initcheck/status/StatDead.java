package initcheck.status;

public class StatDead extends StatusItem {

	private static final long serialVersionUID = 1L;

	public StatDead() {
		statusMod = 1;
		setDefaults();
	}

	public void setDefaults() {
		fatal = true;
		timed = false;
		name = "Dead";
		incap = true;
		description = "He's DEAD, Jim";
	}
}

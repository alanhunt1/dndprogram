package initcheck.status;

public class StatSilence extends StatusItem {

	private static final long serialVersionUID = 1L;

	public StatSilence(int i) {
		statusMod = i;
		setDefaults();
	}

	public String getName() {
		return "Silence";
	}

	public void setDefaults() {
		stacks = true;
		incap = false;
		timed = true;
		name = "Silence";
		description = "All creatures in the area of effect are silenced.  This disallows casting of spells that have a verbal component.";
	}
}

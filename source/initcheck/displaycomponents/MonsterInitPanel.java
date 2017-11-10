package initcheck.displaycomponents;

import initcheck.database.Monster;
import initcheck.graphics.TiledGridPanel;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class MonsterInitPanel extends TiledGridPanel {

	private static final long serialVersionUID = 1L;

	// hit fields
	private JTextField init = new JTextField(10);

	private JTextField initNotes = new JTextField(10);

	private JLabel initLabel = new JLabel("Init Modifier");

	private JLabel initNotesLabel = new JLabel("Init Notes");

	public MonsterInitPanel() {
		super("images/rockLighter.jpg");
		initValues();
	}

	public MonsterInitPanel(Monster m) {
		super("images/rockLighter.jpg");
		initValues();
		if (m != null) {
			init.setText("" + m.getInit());
			initNotes.setText("" + m.getInitNotes());
		}
	}

	public MonsterInitPanel(String init, String initNotes) {
		super("images/rockLighter.jpg");
		initValues();
		this.init.setText(init);
		this.initNotes.setText(initNotes);

	}

	public void setMonster(Monster m) {
		if (m != null) {
			init.setText("" + m.getInit());
			initNotes.setText("" + m.getInitNotes());
		}
	}

	public String getInit() {
		return init.getText();
	}

	public String getInitNotes() {
		return initNotes.getText();
	}

	private void initValues() {

		setWeightX(1.0);
		setWeightY(1.0);

		doLayout(initLabel, 0, ypos);
		doLayout(init, 1, ypos);

		incYPos();

		doLayout(initNotesLabel, 0, ypos);
		doLayout(initNotes, 1, ypos);

	}
}

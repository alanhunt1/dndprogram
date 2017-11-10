package initcheck.displaycomponents;

import initcheck.database.Monster;
import initcheck.graphics.TiledPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class MonsterAcPanel extends TiledPanel {

	private static final long serialVersionUID = 1L;

	// hit fields
	private JTextField ac = new JTextField(10);

	private JTextField acNotes = new JTextField(10);

	private JLabel acLabel = new JLabel("AC");

	private JLabel acNotesLabel = new JLabel("AC Notes");

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints c = new GridBagConstraints();

	public MonsterAcPanel() {
		super("images/rockLighter.jpg");
		acValues();
	}

	public MonsterAcPanel(Monster m) {
		super("images/rockLighter.jpg");
		acValues();
		if (m != null) {
			ac.setText("" + m.getAC());
			acNotes.setText("" + m.getACNotes());
		}
	}

	public MonsterAcPanel(String ac, String acNotes) {
		super("images/rockLighter.jpg");
		acValues();
		this.ac.setText(ac);
		this.acNotes.setText(acNotes);

	}

	public void setMonster(Monster m) {

		if (m != null) {
			ac.setText("" + m.getAC());
			acNotes.setText("" + m.getACNotes());
		}
	}

	public String getAC() {
		return ac.getText();
	}

	public String getACNotes() {
		return acNotes.getText();
	}

	private void acValues() {

		setLayout(gridbag);
		c.fill = GridBagConstraints.BOTH;

		// the ac label
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		gridbag.setConstraints(acLabel, c);
		add(acLabel);

		// the ac field
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		gridbag.setConstraints(ac, c);
		add(ac);

		// the acNotes label
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		gridbag.setConstraints(acNotesLabel, c);
		add(acNotesLabel);

		// the acNotes field
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		gridbag.setConstraints(acNotes, c);
		add(acNotes);

	}
}

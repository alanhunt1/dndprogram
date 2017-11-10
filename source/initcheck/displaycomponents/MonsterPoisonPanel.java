package initcheck.displaycomponents;

import initcheck.database.Monster;
import initcheck.graphics.TiledGridPanel;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class MonsterPoisonPanel extends TiledGridPanel {

	
	private static final long serialVersionUID = 1L;

	// hit fields
	private JTextField poisonType = new JTextField(3);

	private JTextField poisonSaveType = new JTextField(3);

	private JTextField poisonSaveDc = new JTextField(3);

	private JTextField poisonInitialDie = new JTextField(3);

	private JTextField poisonSecondaryDie = new JTextField(3);

	private JLabel poisonTypeLabel = new JLabel("Poison Type");

	private JLabel poisonSaveTypeLabel = new JLabel("Save Type");

	private JLabel poisonSaveDcLabel = new JLabel("DC");

	private JLabel poisonInitialDieLabel = new JLabel("Initial Poison");

	private JLabel poisonSecondaryDieLabel = new JLabel("Secondary Poison");

	public MonsterPoisonPanel() {
		super("images/rockLighter.jpg");
		initValues();
	}

	public MonsterPoisonPanel(Monster m) {
		super("images/rockLighter.jpg");
		initValues();
		if (m != null) {
			poisonType.setText(m.getPoisonType());
			poisonSaveType.setText(m.getPoisonSaveType());
			poisonSaveDc.setText("" + m.getPoisonSaveDc());
			poisonInitialDie.setText("" + m.getPoisonInitialDie());
			poisonSecondaryDie.setText("" + m.getPoisonSecondaryDie());

		}
	}

	public MonsterPoisonPanel(String poisonType, String poisonSaveType,
			String poisonSaveDc, String poisonInitialDie,
			String poisonSecondaryDie) {
		super("images/rockLighter.jpg");
		initValues();
		this.poisonType.setText(poisonType);
		this.poisonSaveType.setText(poisonSaveType);
		this.poisonSaveDc.setText(poisonSaveDc);
		this.poisonInitialDie.setText(poisonInitialDie);
		this.poisonSecondaryDie.setText(poisonSecondaryDie);

	}

	public void setMonster(Monster m) {
		if (m != null) {
			poisonType.setText(m.getPoisonType());
			poisonSaveType.setText("" + m.getPoisonSaveType());
			poisonSaveDc.setText("" + m.getPoisonSaveDc());
			poisonInitialDie.setText("" + m.getPoisonInitialDie());
			poisonSecondaryDie.setText("" + m.getPoisonSecondaryDie());

		}
	}

	public String getPoisonType() {
		return poisonType.getText();
	}

	public String getPoisonSaveType() {
		return poisonSaveType.getText();
	}

	public String getPoisonSaveDc() {
		return poisonSaveDc.getText();
	}

	public String getPoisonInitialDie() {
		return poisonInitialDie.getText();
	}

	public String getPoisonSecondaryDie() {
		return poisonSecondaryDie.getText();
	}

	private void initValues() {

		setWeightX(1.0);
		setWeightY(1.0);

		doLayout(poisonTypeLabel, 0, ypos);
		doLayout(poisonType, 1, ypos);

		incYPos();

		doLayout(poisonSaveTypeLabel, 0, ypos);
		doLayout(poisonSaveType, 1, ypos);

		incYPos();

		doLayout(poisonSaveDcLabel, 0, ypos);
		doLayout(poisonSaveDc, 1, ypos);

		incYPos();

		doLayout(poisonInitialDieLabel, 0, ypos);
		doLayout(poisonInitialDie, 1, ypos);

		incYPos();

		doLayout(poisonSecondaryDieLabel, 0, ypos);
		doLayout(poisonSecondaryDie, 1, ypos);

	}
}

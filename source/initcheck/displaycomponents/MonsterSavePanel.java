package initcheck.displaycomponents;

import initcheck.database.Monster;
import initcheck.graphics.TiledGridPanel;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MonsterSavePanel extends TiledGridPanel {

	
	private static final long serialVersionUID = 1L;

	// hit fields
	private JTextField refsave = new JTextField(2);

	private JTextField willsave = new JTextField(2);

	private JTextField fortsave = new JTextField(2);

	private JTextField dr = new JTextField(3);

	private JTextField drType = new JTextField(3);

	private JLabel refsaveLabel = new JLabel("Ref");

	private JLabel willsaveLabel = new JLabel("Will");

	private JLabel fortsaveLabel = new JLabel("Fort");

	private JLabel drLabel = new JLabel("DR");

	public MonsterSavePanel() {
		super("images/rockLighter.jpg");
		initValues();
	}

	public MonsterSavePanel(Monster m) {
		super("images/rockLighter.jpg");
		initValues();
		if (m != null) {
			refsave.setText("" + m.getRefSave());
			willsave.setText("" + m.getWillSave());
			fortsave.setText("" + m.getFortSave());
			dr.setText(m.getDamageReduction());
			drType.setText(m.getDrType());
		}
	}

	public MonsterSavePanel(String refsave, String willsave, String fortsave) {
		super("images/rockLighter.jpg");
		initValues();
		this.refsave.setText(refsave);
		this.willsave.setText(willsave);
		this.fortsave.setText(fortsave);

	}

	public void setMonster(Monster m) {
		if (m != null) {
			refsave.setText("" + m.getRefSave());
			willsave.setText("" + m.getWillSave());
			fortsave.setText("" + m.getFortSave());
			dr.setText(m.getDamageReduction());
			drType.setText(m.getDrType());
		}
	}

	public String getRefSave() {
		return refsave.getText();
	}

	public String getWillSave() {
		return willsave.getText();
	}

	public String getFortSave() {
		return fortsave.getText();
	}

	public String getDamageReduction() {
		return dr.getText();
	}

	public String getDrType() {
		return drType.getText();
	}

	private void initValues() {

		setWeightX(1.0);
		setWeightY(1.0);

		doLayout(refsaveLabel, 0, ypos);
		doLayout(refsave, 1, ypos);

		

		doLayout(willsaveLabel, 2, ypos);
		doLayout(willsave, 3, ypos);

		

		doLayout(fortsaveLabel, 4, ypos);
		doLayout(fortsave, 5, ypos);

		incYPos();

		doLayout(drLabel, 0, ypos);
		JPanel drPanel = new JPanel();
		drPanel.setOpaque(false);
		drPanel.add(dr);
		drPanel.add(new JLabel("/"));
		drPanel.add(drType);
		doLayout(drPanel, 1, ypos,5,1);
	}
}

package initcheck.displaycomponents;

import initcheck.database.Monster;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.ToolTipLabel;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class MonsterHitPanel extends TiledGridPanel {

	private static final long serialVersionUID = 1L;

	// hit fields
	private JTextField hitdie = new JTextField(4);

	private JTextField hp = new JTextField(4);

	private JTextField dietype = new JTextField(4);

	private JTextField hitdice = new JTextField(4);

	private JLabel hitdieLabel = new JLabel("Formula");

	private JLabel hpLabel = new JLabel("Avg HP");

	private ToolTipLabel dietypeLabel = new ToolTipLabel("Die Type");

	private JLabel hitdiceLabel = new JLabel("Hit Dice");

	public MonsterHitPanel() {
		super("images/rockLighter.jpg");
		initValues();
	}

	public MonsterHitPanel(Monster m) {
		super("images/rockLighter.jpg");
		initValues();
		if (m != null) {
			hitdie.setText(m.getHitDie());
			hp.setText("" + m.getHP());
			dietype.setText(m.getDieType());
			hitdice.setText(m.getHitDice());

		}
	}

	public MonsterHitPanel(String hitdie, String hp, String dietype,
			String hitdice) {
		super("images/rockLighter.jpg");
		initValues();
		this.hitdie.setText(hitdie);
		this.hp.setText(hp);
		this.dietype.setText(dietype);
		this.hitdice.setText(hitdice);
	}

	public void setMonster(Monster m) {
		if (m != null) {
			hitdie.setText(m.getHitDie());
			hp.setText("" + m.getHP());
			dietype.setText(m.getDieType());
			hitdice.setText(m.getHitDice());

		}
	}

	public String getHitDie() {
		return hitdie.getText();
	}

	public String getHP() {
		return hp.getText();
	}

	public String getDieType() {
		return dietype.getText();
	}

	public String getHitDice() {
		return hitdice.getText();
	}

	private void initValues() {

		setWeightX(1.0);
		setWeightY(1.0);

		doLayout(hitdieLabel, 0, ypos);
		doLayout(hitdie, 1, ypos);

		doLayout(hpLabel, 2, ypos);
		doLayout(hp, 3, ypos);
		incYPos();
		dietypeLabel.setToolTipText("Integer Value : eg 8 for d8");
		doLayout(dietypeLabel, 0, ypos);
		doLayout(dietype, 1, ypos);

		doLayout(hitdiceLabel, 2, ypos);
		doLayout(hitdice, 3, ypos);
		incYPos();

		setWeightX(0);
		setWeightY(0);

	}
}

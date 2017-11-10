package initcheck.displaycomponents;

import initcheck.database.Monster;
import initcheck.graphics.TiledGridPanel;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class MonsterAttackPanel extends TiledGridPanel {

	private static final long serialVersionUID = 1L;

	// hit fields
	private JTextField attack = new JTextField(20);

	private JTextField damage = new JTextField(20);

	private JTextField reach = new JTextField(20);

	private JLabel attackLabel = new JLabel("Attacks");

	private JLabel damageLabel = new JLabel("Damage");

	private JLabel reachLabel = new JLabel("Reach");

	public MonsterAttackPanel() {
		super("images/rockLighter.jpg");
		initValues();
	}

	public MonsterAttackPanel(Monster m) {
		super("images/rockLighter.jpg");
		initValues();
		if (m != null) {
			attack.setText("" + m.getAttacks());
			damage.setText("" + m.getDamage());
			reach.setText("" + m.getFace());
		}
	}

	public MonsterAttackPanel(String attack, String damage, String reach) {
		super("images/rockLighter.jpg");
		initValues();
		this.attack.setText(attack);
		this.damage.setText(damage);
		this.reach.setText(reach);

	}

	public void setMonster(Monster m) {
		if (m != null) {
			attack.setText("" + m.getAttacks());
			damage.setText("" + m.getDamage());
			reach.setText("" + m.getFace());
		}
	}

	public String getAttack() {
		return attack.getText();
	}

	public String getDamage() {
		return damage.getText();
	}

	public String getReach() {
		return reach.getText();
	}

	private void initValues() {

		setWeightX(1.0);
		setWeightY(1.0);

		doLayout(attackLabel, 0, ypos);
		doLayout(attack, 1, ypos);

		incYPos();

		doLayout(damageLabel, 0, ypos);
		doLayout(damage, 1, ypos);

		incYPos();

		doLayout(reachLabel, 0, ypos);
		doLayout(reach, 1, ypos);

	}
}

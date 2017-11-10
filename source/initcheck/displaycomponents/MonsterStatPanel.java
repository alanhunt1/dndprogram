package initcheck.displaycomponents;

import initcheck.database.Monster;
import initcheck.graphics.TiledGridPanel;

import javax.swing.JLabel;
import javax.swing.JTextField;


public class MonsterStatPanel extends TiledGridPanel {

	private static final long serialVersionUID = 1L;

	// hit fields
	private JTextField str = new JTextField(2);

	private JTextField dex = new JTextField(2);

	private JTextField con = new JTextField(2);

	private JTextField wis = new JTextField(2);

	private JTextField intel = new JTextField(2);

	private JTextField cha = new JTextField(2);

	private JLabel strLabel = new JLabel("STR");

	private JLabel dexLabel = new JLabel("DEX");

	private JLabel conLabel = new JLabel("CON");

	private JLabel wisLabel = new JLabel("WIS");

	private JLabel intelLabel = new JLabel("INT");

	private JLabel chaLabel = new JLabel("CHA");

	public MonsterStatPanel() {
		super("images/rockLighter.jpg");
		initValues();
	}

	public MonsterStatPanel(Monster m) {
		super("images/rockLighter.jpg");
		initValues();
		if (m != null) {
			str.setText("" + m.getStrength());
			dex.setText("" + m.getDexterity());
			con.setText("" + m.getConstitution());
			wis.setText("" + m.getWisdom());
			intel.setText("" + m.getIntelligence());
			cha.setText("" + m.getCharisma());
		}
	}

	public MonsterStatPanel(String str, String dex, String con, String wis,
			String intel, String cha) {
		super("images/rockLighter.jpg");
		initValues();
		this.str.setText(str);
		this.dex.setText(dex);
		this.con.setText(con);
		this.wis.setText(wis);
		this.intel.setText(intel);
		this.cha.setText(cha);
	}

	public void setMonster(Monster m) {
		if (m != null) {
			str.setText("" + m.getStrength());
			dex.setText("" + m.getDexterity());
			con.setText("" + m.getConstitution());
			wis.setText("" + m.getWisdom());
			intel.setText("" + m.getIntelligence());
			cha.setText("" + m.getCharisma());
		}
	}

	public String getStr() {
		return str.getText();
	}

	public String getDex() {
		return dex.getText();
	}

	public String getCon() {
		return con.getText();
	}

	public String getWis() {
		return wis.getText();
	}

	public String getIntel() {
		return intel.getText();
	}

	public String getCha() {
		return cha.getText();
	}

	private void initValues() {

		setWeightX(1.0);
		setWeightY(1.0);

		doLayout(strLabel, 0, ypos);
		doLayout(str, 1, ypos);

		incYPos();

		doLayout(dexLabel, 0, ypos);
		doLayout(dex, 1, ypos);

		incYPos();

		doLayout(conLabel, 0, ypos);
		doLayout(con, 1, ypos);

		incYPos();

		doLayout(wisLabel, 0, ypos);
		doLayout(wis, 1, ypos);

		incYPos();

		doLayout(intelLabel, 0, ypos);
		doLayout(intel, 1, ypos);

		incYPos();

		doLayout(chaLabel, 0, ypos);
		doLayout(cha, 1, ypos);

	}
}

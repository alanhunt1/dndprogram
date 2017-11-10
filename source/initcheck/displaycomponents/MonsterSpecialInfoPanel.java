package initcheck.displaycomponents;

import initcheck.database.Monster;
import initcheck.graphics.TiledGridPanel;

import javax.swing.JLabel;
import javax.swing.JTextArea;

public class MonsterSpecialInfoPanel extends TiledGridPanel {

	// hit fields

	private static final long serialVersionUID = 1L;

	private JTextArea specialattacks = new JTextArea(2, 60);

	private JTextArea specialqualities = new JTextArea(2, 60);

	private JTextArea feats = new JTextArea(2, 60);

	private JTextArea skills = new JTextArea(2, 60);

	private JLabel specialattacksLabel = new JLabel("Special Attacks");

	private JLabel specialqualitiesLabel = new JLabel("Special Qualities");

	private JLabel featsLabel = new JLabel("Feats");

	private JLabel skillsLabel = new JLabel("Skills");

	public MonsterSpecialInfoPanel() {
		super("images/rockLighter.jpg");
		initValues();
	}

	public MonsterSpecialInfoPanel(Monster m) {
		super("images/rockLighter.jpg");
		initValues();
		if (m != null) {
			specialattacks.setText(m.getSpecialAttacks());
			specialqualities.setText(m.getSpecialQualities());
			feats.setText(m.getFeats());
			skills.setText(m.getSkills());
		}
	}

	public MonsterSpecialInfoPanel(String specialattacks,
			String specialqualities, String feats, String skills) {
		super("images/rockLighter.jpg");
		initValues();
		this.specialattacks.setText(specialattacks);
		this.specialqualities.setText(specialqualities);
		this.feats.setText(feats);
		this.skills.setText(skills);
	}

	public void setMonster(Monster m) {
		if (m != null) {
			specialattacks.setText(m.getSpecialAttacks());
			specialqualities.setText(m.getSpecialQualities());
			feats.setText(m.getFeats());
			skills.setText(m.getSkills());
		}
	}

	public String getSpecialattacks() {
		return specialattacks.getText();
	}

	public String getSpecialqualities() {
		return specialqualities.getText();
	}

	public String getFeats() {
		return feats.getText();
	}

	public String getSkills() {
		return skills.getText();
	}

	private void initValues() {
		specialattacks.setLineWrap(true);
		specialqualities.setLineWrap(true);
		specialattacks.setWrapStyleWord(true);
		specialqualities.setWrapStyleWord(true);
		feats.setLineWrap(true);
		skills.setLineWrap(true);

		setWeightX(0.0);
		setWeightY(0.0);
		setPadX(0);
		setPadY(0);

		doLayout(specialattacksLabel, 0, ypos);
		incYPos();
		doLayout(specialattacks, 0, ypos);
		incYPos();
		doLayout(specialqualitiesLabel, 0, ypos);
		incYPos();
		doLayout(specialqualities, 0, ypos);
		incYPos();
		doLayout(featsLabel, 0, ypos);
		incYPos();
		doLayout(feats, 0, ypos);
		incYPos();
		doLayout(skillsLabel, 0, ypos);
		incYPos();
		doLayout(skills, 0, ypos);

	}
}

package initcheck.displaycomponents;

import initcheck.character.chooser.TerrainChooser;
import initcheck.database.Monster;
import initcheck.graphics.TiledGridPanel;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class MonsterOrgPanel extends TiledGridPanel {

	
	private static final long serialVersionUID = 1L;

	// hit fields
	private JTextField org = new JTextField(30);

	private JTextField challengerating = new JTextField(4);

	private JTextField treasure = new JTextField(30);

	private JTextField alignment = new JTextField(10);

	private TerrainChooser terrain = new TerrainChooser();

	private JLabel orgLabel = new JLabel("Organization");

	private JLabel challengeratingLabel = new JLabel("CR");

	private JLabel treasureLabel = new JLabel("Treasure");

	private JLabel alignmentLabel = new JLabel("Alignment");

	private JLabel terrainLabel = new JLabel("Terrain");

	public MonsterOrgPanel() {
		super("images/rockLighter.jpg");
		initValues();
	}

	public MonsterOrgPanel(Monster m) {
		super("images/rockLighter.jpg");
		initValues();
		if (m != null) {
			org.setText(m.getOrg());
			challengerating.setText("" + m.getChallengeRating());
			treasure.setText(m.getTreasure());
			alignment.setText(m.getAlignment());
			terrain.setSelectedItem((String) m.getTerrain());
		}
	}

	public MonsterOrgPanel(String org, String challengerating, String treasure,
			String alignment, String terrain) {
		super("images/rockLighter.jpg");
		initValues();
		this.org.setText(org);
		this.challengerating.setText(challengerating);
		this.treasure.setText(treasure);
		this.alignment.setText(alignment);
		this.terrain.setSelectedItem((String) terrain);
	}

	public void setMonster(Monster m) {
		if (m != null) {
			org.setText(m.getOrg());
			challengerating.setText("" + m.getChallengeRating());
			treasure.setText(m.getTreasure());
			alignment.setText(m.getAlignment());
			terrain.setSelectedItem((String) m.getTerrain());
		}
	}

	public String getOrg() {
		return org.getText();
	}

	public String getChallengeRating() {
		return challengerating.getText();
	}

	public String getTreasure() {
		return treasure.getText();
	}

	public String getAlignment() {
		return alignment.getText();
	}

	public String getTerrain() {
		return (String) terrain.getSelectedItem();
	}

	private void initValues() {

		
		terrain.setSelectedIndex(0);

		setWeightX(1.0);
		setWeightY(1.0);

		doLayout(orgLabel, 0, ypos);
		doLayout(org, 1, ypos,5,1);

	
		incYPos();

		doLayout(treasureLabel, 0, ypos);
		doLayout(treasure, 1, ypos,5,1);

		incYPos();
		doLayout(challengeratingLabel, 0, ypos);
		doLayout(challengerating, 1, ypos);
		doLayout(alignmentLabel, 2, ypos);
		doLayout(alignment, 3, ypos);
		doLayout(terrainLabel, 4, ypos);
		doLayout(terrain, 5, ypos);

	}
}

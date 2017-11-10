package initcheck.displaycomponents;

import initcheck.database.Monster;
import initcheck.graphics.TiledGridPanel;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class MonsterSpeedPanel extends TiledGridPanel {

	
	private static final long serialVersionUID = 1L;

	// hit fields
	private JTextField landspeed = new JTextField(3);

	private JTextField flyspeed = new JTextField(3);

	private JTextField swimspeed = new JTextField(3);

	private JTextField climbspeed = new JTextField(3);

	private JTextField burrowspeed = new JTextField(3);

	private JTextField flytype = new JTextField(5);

	private JLabel landspeedLabel = new JLabel("LAND");

	private JLabel flyspeedLabel = new JLabel("FLY");

	private JLabel swimspeedLabel = new JLabel("SWIM");

	private JLabel climbspeedLabel = new JLabel("CLIMB");

	private JLabel burrowspeedLabel = new JLabel("BURROW");

	private JLabel flytypeLabel = new JLabel("TYPE");

	public MonsterSpeedPanel() {
		super("images/rockLighter.jpg");
		initValues();
	}

	public MonsterSpeedPanel(Monster m) {
		super("images/rockLighter.jpg");
		initValues();
		if (m != null) {
			landspeed.setText("" + m.getLandSpeed());
			flyspeed.setText("" + m.getFlySpeed());
			swimspeed.setText("" + m.getSwimSpeed());
			climbspeed.setText("" + m.getClimbSpeed());
			burrowspeed.setText("" + m.getBurrowSpeed());
			flytype.setText("" + m.getFlyType());
		}
	}

	public MonsterSpeedPanel(String landspeed, String flyspeed,
			String swimspeed, String climbspeed, String burrowspeed,
			String flytype) {
		super("images/rockLighter.jpg");
		initValues();
		this.landspeed.setText(landspeed);
		this.flyspeed.setText(flyspeed);
		this.swimspeed.setText(swimspeed);
		this.climbspeed.setText(climbspeed);
		this.burrowspeed.setText(burrowspeed);
		this.flytype.setText(flytype);
	}

	public void setMonster(Monster m) {
		if (m != null) {
			landspeed.setText("" + m.getLandSpeed());
			flyspeed.setText("" + m.getFlySpeed());
			swimspeed.setText("" + m.getSwimSpeed());
			climbspeed.setText("" + m.getClimbSpeed());
			burrowspeed.setText("" + m.getBurrowSpeed());
			flytype.setText("" + m.getFlyType());
		}
	}

	public String getLandspeed() {
		return landspeed.getText();
	}

	public String getFlyspeed() {
		return flyspeed.getText();
	}

	public String getSwimspeed() {
		return swimspeed.getText();
	}

	public String getClimbspeed() {
		return climbspeed.getText();
	}

	public String getBurrowspeed() {
		return burrowspeed.getText();
	}

	public String getFlytype() {
		return flytype.getText();
	}

	private void initValues() {

		setWeightX(1.0);
		setWeightY(1.0);

		doLayout(landspeedLabel, 0, ypos);
		doLayout(landspeed, 1, ypos);
		doLayout(swimspeedLabel, 2, ypos);
		doLayout(swimspeed, 3, ypos);
		
		incYPos();

		doLayout(flyspeedLabel, 0, ypos);
		doLayout(flyspeed, 1, ypos);
		doLayout(flytypeLabel, 2, ypos);
		doLayout(flytype, 3, ypos);

		incYPos();

		doLayout(climbspeedLabel, 0, ypos);
		doLayout(climbspeed, 1, ypos);

		doLayout(burrowspeedLabel, 2, ypos);
		doLayout(burrowspeed, 3, ypos);



		

	}
}

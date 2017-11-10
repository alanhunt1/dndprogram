package initcheck.character.itembuilder;

import initcheck.PanelButton;
import initcheck.character.GridPanel;
import initcheck.character.chooser.WeaponAmmoChooser;
import initcheck.database.Weapon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BuildMundaneWeaponPanel extends GridPanel {

	private static final long serialVersionUID = 1L;

	WeaponAmmoChooser ac = new WeaponAmmoChooser();

	JTextField bonus = new JTextField(3);

	JCheckBox masterwork = new JCheckBox("Masterwork");

	JCheckBox hurry = new JCheckBox("Hurry");

	JLabel marketPriceLabel = new JLabel("");

	JLabel craftDcLabel = new JLabel("");

	JLabel abilityCostLabel = new JLabel("");

	JLabel xpCostLabel = new JLabel("");

	JLabel timeLabel = new JLabel("");

	int abBonus = 0;

	int baseCost = 0;

	int casterLevel;

	boolean upgrade = false;

	int origBonus;

	int origCost;

	int origAbBonus;

	private Random rnd = new Random();

	public BuildMundaneWeaponPanel() {

	}

	public void init() {

		setWeightX(1.0);
		setWeightY(1.0);

		doLayout(new JLabel("Base Armor"), 0);
		doLayout(ac, 1);
		incYPos();
		doLayout(new JLabel("Craft Ranks"), 0);
		bonus.setText("1");
		doLayout(bonus, 1);
		incYPos();

		doLayout(masterwork, 0);
		incYPos();

		doLayout(hurry, 0);
		incYPos();

		PanelButton buildIt = new PanelButton("Build It!");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(buildIt);
		doLayout(buttonPanel, 0, ypos, 2, 1);

		incYPos();

		doLayout(new JLabel("Market Price"), 0);
		doLayout(marketPriceLabel, 1);

		incYPos();
		doLayout(new JLabel("DC"), 0);
		doLayout(craftDcLabel, 1);

		incYPos();
		doLayout(new JLabel("Ruin Cost"), 0);
		doLayout(xpCostLabel, 1);

		incYPos();
		doLayout(new JLabel("GP Cost"), 0);
		doLayout(abilityCostLabel, 1);

		incYPos();
		doLayout(new JLabel("Days Required"), 0);
		doLayout(timeLabel, 1);

		buildIt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calcCost();
			}
		});

	}

	public void calcCost() {

		long baseCost = 0;
		long completed = 0;
		int numDays = 0;

		Weapon a = (Weapon) ac.getSelectedItem();
		int dc = 10;

		// calculate the difficulty for the piece being built
		if (a.getCategory().equals("Ammunition")
				|| a.getRangedmelee().equals("Melee")) {

			if (a.getCategory().equals("Ammunition")) {
				dc = 8;
			} else if (a.getCategory().equals("Simple")) {
				dc = 12;
			} else if (a.getCategory().equals("Martial")) {
				dc = 15;
			} else if (a.getCategory().equals("Exotic")) {
				dc = 18;
			}
		} else {
			if (a.getFeatClass().equals("Crossbow")) {
				dc = 15;
			} else if (a.getFeatClass().equals("Bow")) {
				dc = 12;
				if (a.getName().indexOf("Composite") >= 0) {
					dc = 15;
				}
				if (a.getName().indexOf("+1") >= 0) {
					dc = 17;
				}
				if (a.getName().indexOf("+2") >= 0) {
					dc = 19;
				}
				if (a.getName().indexOf("+3") >= 0) {
					dc = 21;
				}
				if (a.getName().indexOf("+4") >= 0) {
					dc = 23;
				}
			}
		}

		// find out what the weapon costs (book value)
		String costStr = a.getCost();
		if (!costStr.equals("")) {
			if (costStr.indexOf("g") > 0) {
				costStr = costStr.substring(0, costStr.indexOf("g")).trim();
			}
			baseCost += Long.parseLong(costStr);
		}

		// add in the masterwork cost if applicable.
		if (masterwork.isSelected()) {
			baseCost += 300;
			if (dc < 20) {
				dc = 20;
			}
		}

		if (hurry.isSelected()) {
			dc += 10;
		}

		craftDcLabel.setText("" + dc);
		marketPriceLabel.setText("" + baseCost);

		// convert the base cost to copper pieces for a daily progress check.
		baseCost = baseCost * 70;

		// now calculate 1/3 of the base cost for raw materials
		baseCost = baseCost / 3;

		long ruinedCost = baseCost / 2;
		long totalRuined = 0;

		// run a check for each day until it is finished
		while (completed < baseCost) {
			int check = getRandom(20) + Integer.parseInt(bonus.getText());

			if (check >= dc) {
				completed += check * dc;
			} else if (check + 5 < dc) {
				totalRuined += ruinedCost;
			}
			numDays++;
		}
		xpCostLabel.setText("" + totalRuined / 70);
		abilityCostLabel.setText("" + (baseCost + totalRuined) / 70);
		timeLabel.setText("" + numDays);
	}

	private int getRandom(int i) {
		int j = rnd.nextInt(i);
		if (j == 0) {
			return 1;
		}
		return j + 1;
	}

}

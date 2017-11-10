package initcheck.character.itembuilder;

import initcheck.InitImage;
import initcheck.PanelButton;
import initcheck.character.chooser.ArmorShieldChooser;
import initcheck.database.Armor;
import initcheck.graphics.TiledGridPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BuildMundaneArmorPanel extends TiledGridPanel {

	private static final long serialVersionUID = 1L;

	ArmorShieldChooser ac = new ArmorShieldChooser();

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

	public BuildMundaneArmorPanel() {
		super(InitImage.lightRocks);
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
		masterwork.setOpaque(false);
		hurry.setOpaque(false);
		doLayout(masterwork, 0);
		incYPos();

		doLayout(hurry, 0);
		incYPos();

		PanelButton buildIt = new PanelButton("Build It!");
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
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

		Armor a = (Armor) ac.getSelectedItem();

		// calculate the difficulty for the piece being built
		int dc = 10 + Integer.parseInt(a.getArmorbonus());

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
			baseCost += 150;
			dc = 20;
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

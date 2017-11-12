package initcheck.character.itembuilder;

import initcheck.PanelButton;
import initcheck.character.GenericListCellRenderer;
import initcheck.character.GridPanel;
import initcheck.character.chooser.ArmorAbilityChooser;
import initcheck.character.chooser.ArmorChooser;
import initcheck.database.Armor;
import initcheck.database.ArmorAbilities;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class BuildArmorPanel extends GridPanel {

	private static final long serialVersionUID = 1L;

	JComboBox<?> ac = new ArmorChooser();

	JTextField bonus = new JTextField(3);

	ArmorAbilityChooser aac = new ArmorAbilityChooser("ARMOR");

	ArmorAbilityChooser aac2 = new ArmorAbilityChooser("ARMOR");

	ArmorAbilityChooser aac3 = new ArmorAbilityChooser("ARMOR");

	ArmorAbilityChooser aac4 = new ArmorAbilityChooser("ARMOR");

	ArmorAbilityChooser aac5 = new ArmorAbilityChooser("ARMOR");

	JLabel baseCostLabel = new JLabel("");

	JLabel enhancementCostLabel = new JLabel("");

	JLabel abilityBonusLabel = new JLabel("");

	JLabel abilityCostLabel = new JLabel("");

	JLabel xpCostLabel = new JLabel("");

	JLabel timeLabel = new JLabel("");

	JLabel prereqLabel = new JLabel("");

	JList<String> prereqList = new JList<String>();

	Vector<String> prereqs = new Vector<String>();

	int abBonus = 0;

	int baseCost = 0;

	JScrollPane preScroll = new JScrollPane(prereqList);

	int casterLevel;

	private JTextArea description = new JTextArea(5, 40);

	private JScrollPane descScroll = new JScrollPane(description);

	boolean upgrade = false;

	int origBonus;

	int origCost;

	int origAbBonus;

	public BuildArmorPanel() {

	}

	public BuildArmorPanel(Armor a) {
		((ArmorChooser) ac).setSelectedArmor(a);
		bonus.setText(a.getBonus());

		if ((a.getAbility1() != null) && a.getAbility1().getName() != null) {
			
			aac.setSelectedAbility(a.getAbility1());
		}
		if ((a.getAbility2() != null) && a.getAbility2().getName() != null) {
			aac2.setSelectedAbility(a.getAbility2());
		}
		

		calcCost();

		upgrade = true;
		origBonus = Integer.parseInt(bonus.getText());
		origCost = baseCost;
		origAbBonus = abBonus;
	}

	public void init() {

		description.setLineWrap(true);
		description.setWrapStyleWord(true);

		prereqList.setVisibleRowCount(5);
		prereqList.setCellRenderer(new GenericListCellRenderer());

		setWeightX(1.0);
		setWeightY(1.0);

		doLayout(descScroll, 2, 1, 8);

		setWeightX(0.0);
		setWeightY(0.0);

		doLayout(new JLabel("Base Armor"), 0);
		doLayout(ac, 1);
		incYPos();
		doLayout(new JLabel("Enhancement Bonus"), 0);
		if (!upgrade) {
			bonus.setText("1");
		}
		doLayout(bonus, 1);
		incYPos();
		doLayout(new JLabel("Ability 1"), 0);
		doLayout(aac, 1);
		incYPos();
		doLayout(new JLabel("Ability 2"), 0);
		doLayout(aac2, 1);
		incYPos();
		doLayout(new JLabel("Ability 3"), 0);
		doLayout(aac3, 1);
		incYPos();
		doLayout(new JLabel("Ability 4"), 0);
		doLayout(aac4, 1);
		incYPos();
		doLayout(new JLabel("Ability 5"), 0);
		doLayout(aac5, 1);
		incYPos();
		PanelButton buildIt = new PanelButton("Build It!");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(buildIt);
		doLayout(buttonPanel, 0, ypos, 2, 1);

		incYPos();
		JPanel prePanel = new JPanel(new GridLayout(1, 2));
		prePanel.add(new JLabel("Prerequisites"));
		prePanel.add(preScroll);
		doLayout(prePanel, 2, 1, 6);

		incYPos();
		doLayout(new JLabel("Market Price"), 0);
		doLayout(baseCostLabel, 1);

		incYPos();
		doLayout(new JLabel("Enhancement Bonus"), 0);
		doLayout(enhancementCostLabel, 1);

		incYPos();
		doLayout(new JLabel("Ability Bonus"), 0);
		doLayout(abilityBonusLabel, 1);

		incYPos();
		doLayout(new JLabel("GP Cost"), 0);
		doLayout(abilityCostLabel, 1);

		incYPos();
		doLayout(new JLabel("XP Cost"), 0);
		doLayout(xpCostLabel, 1);

		incYPos();
		doLayout(new JLabel("Days Required"), 0);
		doLayout(timeLabel, 1);

		buildIt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calcCost();
			}
		});

		aac.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateDescription(((ArmorAbilities) aac.getSelectedItem())
						.getDescription());
			}
		});

		aac2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateDescription(((ArmorAbilities) aac2.getSelectedItem())
						.getDescription());
			}
		});

		aac3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateDescription(((ArmorAbilities) aac3.getSelectedItem())
						.getDescription());
			}
		});

		aac4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateDescription(((ArmorAbilities) aac4.getSelectedItem())
						.getDescription());
			}
		});

		aac5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateDescription(((ArmorAbilities) aac5.getSelectedItem())
						.getDescription());
			}
		});

	}

	public void updateDescription(String s) {
		description.setText(s);
	}

	public void calcCost() {

		abBonus = 0;

		Armor a = (Armor) ac.getSelectedItem();
		String costStr = a.getCost();
		baseCost = 150;

		casterLevel = (Integer.parseInt(bonus.getText()) * 3);
		prereqs = new Vector<String>();

		if (!costStr.equals("")) {
			if (costStr.indexOf("g") > 0) {
				costStr = costStr.substring(0, costStr.indexOf("g")).trim();
			}
			baseCost += Integer.parseInt(costStr);
		}

		// baseCostLabel.setText(""+baseCost);

		ArmorAbilities ab = (ArmorAbilities) aac.getSelectedItem();
		if (ab.getId() != null) {
			calcAbilityCosts(ab);
		}

		ArmorAbilities ab2 = (ArmorAbilities) aac2.getSelectedItem();
		if (ab2.getId() != null) {
			calcAbilityCosts(ab2);
		}

		ArmorAbilities ab3 = (ArmorAbilities) aac3.getSelectedItem();
		if (ab3.getId() != null) {
			calcAbilityCosts(ab3);
		}

		ArmorAbilities ab4 = (ArmorAbilities) aac4.getSelectedItem();
		if (ab4.getId() != null) {
			calcAbilityCosts(ab4);
		}

		ArmorAbilities ab5 = (ArmorAbilities) aac5.getSelectedItem();
		if (ab5.getId() != null) {
			calcAbilityCosts(ab5);
		}

		// abilityBonusLabel.setText(""+abBonus);
		int enhancementCost = Integer.parseInt(bonus.getText());
		int totalBonus = abBonus + Integer.parseInt(bonus.getText());
		int cost = 1000 * (totalBonus * totalBonus);
		cost += baseCost;
		baseCostLabel.setText("" + cost);
		baseCost = cost;

		if (upgrade) {
			abilityBonusLabel.setText("" + abBonus + " ("
					+ (abBonus - origAbBonus) + ")");
			enhancementCostLabel.setText(bonus.getText() + " ("
					+ (enhancementCost - origBonus) + ")");
			abilityCostLabel.setText("" + cost / 2 + " ("
					+ ((cost / 2) - (origCost / 2)) + ")");
			xpCostLabel.setText("" + cost / 25 + " ("
					+ ((cost / 25) - (origCost / 25)) + ")");
			timeLabel.setText("" + cost / 1000 + " ("
					+ ((cost / 1000) - (origCost / 1000)) + ")");
		} else {
			abilityBonusLabel.setText("" + abBonus);
			enhancementCostLabel.setText(bonus.getText());
			abilityCostLabel.setText("" + cost / 2);
			xpCostLabel.setText("" + cost / 25);
			timeLabel.setText("" + cost / 1000);
		}

		// enhancementCostLabel.setText(bonus.getText());
		// abilityCostLabel.setText(""+cost/2);
		// xpCostLabel.setText(""+cost/25);
		// timeLabel.setText(""+cost/1000);

		prereqs.add("CL " + casterLevel);

		prereqList.setListData(prereqs);
	}

	public void calcAbilityCosts(ArmorAbilities ab) {
		abBonus += Integer.parseInt(ab.getBonus());
		String pStr = ab.getPrereq();
		String[] pArray = pStr.split(",");
		for (int i = 0; i < pArray.length; i++) {
			String pre = pArray[i].trim();
			if (!prereqs.contains(pre)) {
				prereqs.add(pre);
			}
		}
		String cls = ab.getCasterLevel();
		if (cls != null && !cls.equals("")) {
			int cl = Integer.parseInt(cls);
			if (cl > casterLevel) {
				casterLevel = cl;
			}
		}

	}

}

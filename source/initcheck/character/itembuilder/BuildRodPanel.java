package initcheck.character.itembuilder;

import initcheck.PanelButton;
import initcheck.character.GenericListCellRenderer;
import initcheck.character.GridPanel;
import initcheck.character.chooser.RodChooser;
import initcheck.database.Rods;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class BuildRodPanel extends GridPanel {

	private static final long serialVersionUID = 1L;

	JComboBox ac = new RodChooser();

	JLabel baseCostLabel = new JLabel("");

	JLabel enhancementCostLabel = new JLabel("");

	JLabel abilityBonusLabel = new JLabel("");

	JLabel abilityCostLabel = new JLabel("");

	JLabel xpCostLabel = new JLabel("");

	JLabel timeLabel = new JLabel("");

	JLabel prereqLabel = new JLabel("");

	JList prereqList = new JList();

	Vector<String> prereqs = new Vector<String>();

	int baseCost = 0;

	JScrollPane preScroll = new JScrollPane(prereqList);

	int casterLevel;

	private JTextArea description = new JTextArea(5, 40);

	private JScrollPane descScroll = new JScrollPane(description);

	public BuildRodPanel() {

	}

	public void init() {

		description.setLineWrap(true);
		description.setWrapStyleWord(true);

		prereqList.setVisibleRowCount(5);
		prereqList.setCellRenderer(new GenericListCellRenderer());

		setWeightX(1.0);
		setWeightY(1.0);

		doLayout(descScroll, 2, 1, 2);

		setWeightX(0.0);
		setWeightY(0.0);

		doLayout(new JLabel("Item "), 0);
		doLayout(ac, 1);
		incYPos();

		PanelButton buildIt = new PanelButton("Build It!");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(buildIt);
		doLayout(buttonPanel, 0, ypos, 2, 1);

		incYPos();
		doLayout(new JLabel("Market Price"), 0);
		doLayout(baseCostLabel, 1);

		incYPos();
		doLayout(new JLabel("GP Cost"), 0);
		doLayout(abilityCostLabel, 1);

		incYPos();
		doLayout(new JLabel("XP Cost"), 0);
		doLayout(xpCostLabel, 1);

		incYPos();
		doLayout(new JLabel("Days Required"), 0);
		doLayout(timeLabel, 1);

		incYPos();
		doLayout(new JLabel("Prerequisites"), 0);
		doLayout(preScroll, 1);

		buildIt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calcCost();
			}
		});

		ac.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateDescription(((Rods) ac.getSelectedItem())
						.getDescription());
			}
		});
	}

	public void updateDescription(String s) {
		description.setText(s);
	}

	public void calcCost() {

		Rods a = (Rods) ac.getSelectedItem();
		String costStr = a.getCost();
		baseCost = 0;

		prereqs = new Vector<String>();

		String pStr = a.getPrereqs();
		if (pStr != null && !pStr.equals("")) {
			String[] pArray = pStr.split(",");
			for (int i = 0; i < pArray.length; i++) {
				String pre = pArray[i].trim();
				if (!prereqs.contains(pre)) {
					prereqs.add(pre);
				}
			}
		}

		if (costStr != null && !costStr.equals("")) {
			if (costStr.indexOf("g") > 0) {
				costStr = costStr.substring(0, costStr.indexOf("g")).trim();
			}
			baseCost += Integer.parseInt(costStr);
		}

		int cost = baseCost;
		baseCostLabel.setText("" + cost);

		abilityCostLabel.setText("" + cost / 2);
		xpCostLabel.setText("" + cost / 25);
		timeLabel.setText("" + cost / 1000);

		prereqList.setListData(prereqs);
	}

}

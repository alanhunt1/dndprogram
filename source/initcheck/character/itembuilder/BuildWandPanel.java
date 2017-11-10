package initcheck.character.itembuilder;

import initcheck.PanelButton;
import initcheck.character.GridPanel;
import initcheck.character.chooser.SpellClassChooser;
import initcheck.character.chooser.WandSpellChooser;
import initcheck.database.Spell;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class BuildWandPanel extends GridPanel {

	private static final long serialVersionUID = 1L;

	WandSpellChooser spellChooser = new WandSpellChooser("Sorceror/Wizard");

	JLabel baseCostLabel = new JLabel("");

	JLabel enhancementCostLabel = new JLabel("");

	JLabel abilityBonusLabel = new JLabel("");

	JLabel abilityCostLabel = new JLabel("");

	JLabel xpCostLabel = new JLabel("");

	JLabel timeLabel = new JLabel("");

	int baseCost = 0;

	private JTextArea description = new JTextArea(5, 40);

	private JScrollPane descScroll = new JScrollPane(description);

	SpellClassChooser classChooser = new SpellClassChooser();

	JTextField casterLevel = new JTextField();

	boolean changing = false;

	public BuildWandPanel() {

	}

	public void init() {

		description.setLineWrap(true);
		description.setWrapStyleWord(true);

		setWeightX(1.0);
		setWeightY(1.0);

		doLayout(descScroll, 2, 1, 2);

		setWeightX(0.0);
		setWeightY(0.0);

		doLayout(new JLabel("Caster Class"), 0);
		doLayout(classChooser, 1);
		incYPos();

		doLayout(new JLabel("Caster Level"), 0);
		doLayout(casterLevel, 1);
		incYPos();

		doLayout(new JLabel("Spell "), 0);
		doLayout(spellChooser, 1);
		incYPos();

		PanelButton buildIt = new PanelButton("Build It!");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(buildIt);
		doLayout(buttonPanel, 0, ypos, 3, 1);

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

		buildIt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calcCost();
			}
		});

		classChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateSpellList();
			}
		});

		spellChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!changing) {
					updateDescription(((Spell) spellChooser.getSelectedItem())
							.getDescription());
				}
			}
		});

	}

	public void updateSpellList() {
		changing = true;
		spellChooser.setPotionClass(getSelectedClass());
		changing = false;
	}

	private String getSelectedClass() {
		String cs = (String) classChooser.getSelectedItem();
		return cs;
	}

	public void updateDescription(String s) {

		description.setText(s);

	}

	public void calcCost() {

		Spell a = (Spell) spellChooser.getSelectedItem();
		baseCost = getCost(a.getLevel());

		int cost = baseCost;
		baseCostLabel.setText("" + cost);

		abilityCostLabel.setText("" + cost / 2);
		xpCostLabel.setText("" + cost / 25);
		timeLabel.setText("" + cost / 1000);

	}

	private int getCost(String level) {
		int lvl = 0;
		int cl = Integer.parseInt(casterLevel.getText());

		if (level.indexOf(getSelectedClass() + " 0") >= 0) {
			lvl = 0;
		} else if (level.indexOf(getSelectedClass() + " 1") >= 0) {
			lvl = 1;
		} else if (level.indexOf(getSelectedClass() + " 2") >= 0) {
			lvl = 2;
		} else if (level.indexOf(getSelectedClass() + " 3") >= 0) {
			lvl = 3;
		} else {
			lvl = 4;
		}

		if (lvl == 0) {
			return 375 * cl;
		}

		return 750 * cl * lvl;

	}

}

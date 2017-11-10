package initcheck;

import initcheck.graphics.TiledPanel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class CureRoller extends TiledPanel {
	static final long serialVersionUID = 1;

	private Vector<String> rollVector;

	private Random rnd = new Random();

	private JTextField cureLight = new JTextField(3);

	private JTextField cureModerate = new JTextField(3);

	private JTextField cureSerious = new JTextField(3);

	private JTextField cureCritical = new JTextField(3);

	private JTextField percentile = new JTextField(3);

	private JTextField heal = new JTextField(3);

	private int buttonWidth = 85;
	
	private PanelButton cureLightButton = new PanelButton("Cure Light", buttonWidth);

	private PanelButton cureModerateButton = new PanelButton("Cure Moderate",
			buttonWidth);

	private PanelButton cureSeriousButton = new PanelButton("Cure Serious", buttonWidth);

	private PanelButton cureCriticalButton = new PanelButton("Cure Critical",
			buttonWidth);

	private PanelButton percentileButton = new PanelButton("Percentile", buttonWidth);

	private JList rollResult = new JList();

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints c = new GridBagConstraints();

	public CureRoller(ImageIcon bg) {
		super(bg);
		cureLight.setText("1");
		cureModerate.setText("1");
		cureSerious.setText("1");
		cureCritical.setText("1");
		percentile.setText("1");
		heal.setText("1");

		setLayout(gridbag);
		c.fill = GridBagConstraints.HORIZONTAL;

		JScrollPane scrollPane = new JScrollPane(rollResult);
		scrollPane.setPreferredSize(new Dimension(20, 50));

		// the cure light button
		c.gridx = 0;
		c.gridy = 0;
		gridbag.setConstraints(cureLightButton, c);
		add(cureLightButton);

		// the cure light entry
		c.gridx = 1;
		c.gridy = 0;
		gridbag.setConstraints(cureLight, c);
		add(cureLight);

		// the cure moderate button
		c.gridx = 0;
		c.gridy = 1;
		gridbag.setConstraints(cureModerateButton, c);
		add(cureModerateButton);

		// the cure moderate entry
		c.gridx = 1;
		c.gridy = 1;
		gridbag.setConstraints(cureModerate, c);
		add(cureModerate);

		// the cure serious button
		c.gridx = 0;
		c.gridy = 2;
		gridbag.setConstraints(cureSeriousButton, c);
		add(cureSeriousButton);

		// the cure serious entry
		c.gridx = 1;
		c.gridy = 2;
		gridbag.setConstraints(cureSerious, c);
		add(cureSerious);

		// the cure critical button
		c.gridx = 0;
		c.gridy = 3;
		gridbag.setConstraints(cureCriticalButton, c);
		add(cureCriticalButton);

		// the cure critical entry
		c.gridx = 1;
		c.gridy = 3;
		gridbag.setConstraints(cureCritical, c);
		add(cureCritical);

		// the percentile button
		c.gridx = 0;
		c.gridy = 4;
		gridbag.setConstraints(percentileButton, c);
		add(percentileButton);

		// the cure critical entry
		c.gridx = 1;
		c.gridy = 4;
		gridbag.setConstraints(percentile, c);
		add(percentile);

		// the rolls
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 2;
		c.gridy = 0;
		c.gridheight = 5;
		c.weightx = 1.0;
		// c.weighty = 1.0;
		gridbag.setConstraints(scrollPane, c);
		add(scrollPane);

		cureLightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rollDice(1);
			}
		});

		cureModerateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rollDice(2);
			}
		});

		cureSeriousButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rollDice(3);
			}
		});

		cureCriticalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rollDice(4);
			}
		});

		percentileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rollDie(100);
			}
		});

		setBorder(BorderFactory.createEmptyBorder(0, // top
				0, // left
				20, // bottom
				20) // right
		);

	}

	private void rollDie(int dieType) {
		try {
			rollVector = new Vector<String>();

			int numCures = Integer.parseInt(percentile.getText());
			for (int j = 0; j < numCures; j++) {
				int roll = getRandom(dieType);
				rollVector.add("" + roll);
			}
			rollResult.setListData(rollVector);
		} catch (NumberFormatException nfe) {
			@SuppressWarnings("unused") MessageDialog md = new MessageDialog("Error",
					"You must enter a number in the percentile box");
		}
	}

	private void rollDice(int cureDice) {
		rollVector = new Vector<String>();
		int numCures = 0;
		try {
			if (cureDice == 1) {
				numCures = Integer.parseInt(cureLight.getText());
			} else if (cureDice == 2) {
				numCures = Integer.parseInt(cureModerate.getText());
			} else if (cureDice == 3) {
				numCures = Integer.parseInt(cureSerious.getText());
			} else {
				numCures = Integer.parseInt(cureCritical.getText());
			}
		} catch (NumberFormatException nfe) {
			@SuppressWarnings("unused") MessageDialog md = new MessageDialog("Error",
					"You must enter a number in the cure box");
		}

		for (int j = 0; j < numCures; j++) {
			int total = 0;

			for (int i = 0; i < cureDice; i++) {
				int roll = getRandom(8);
				int roll2 = getRandom(8);
				int roll3 = getRandom(8);
				if (roll > roll2) {
					if (roll > roll3) {
						total += roll;
					} else {
						total += roll3;
					}
				} else {
					if (roll2 > roll3) {
						total += roll2;
					} else {
						total += roll3;
					}
				}
			}

			rollVector.add("" + total);

		}
		rollResult.setListData(rollVector);
	}

	public int getRandom(int i) {
		int j = rnd.nextInt(i);
		// if (j == 0){
		// return 1;
		// }
		return j + 1;

	}
}

package initcheck.server;

import initcheck.PanelButton;
import initcheck.graphics.TiledPanel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class CritStatPanel extends TiledPanel {

	static final long serialVersionUID = 1;

	JLabel critLabel = new JLabel("Criticals : 0");

	JLabel fumbleLabel = new JLabel("Fumbles : 0");

	JLabel doubleLabel = new JLabel("Double D's : 0");

	JLabel attackLabel = new JLabel("Attacks : 0");

	PanelButton critPlus = new PanelButton("+");

	PanelButton critMinus = new PanelButton("-");

	PanelButton fumblePlus = new PanelButton("+");

	PanelButton fumbleMinus = new PanelButton("-");

	PanelButton doublePlus = new PanelButton("+");

	PanelButton doubleMinus = new PanelButton("-");

	PanelButton attackPlus = new PanelButton("+");

	PanelButton attackMinus = new PanelButton("-");

	int crits;

	int fumbles;

	int doubles;

	int attacks;

	public CritStatPanel() {

		super();
		FlowLayout fl = new FlowLayout();
		fl.setVgap(2);
		setLayout(fl);

		add(critLabel);
		add(critPlus);
		add(critMinus);
		add(fumbleLabel);
		add(fumblePlus);
		add(fumbleMinus);
		add(doubleLabel);
		add(doublePlus);
		add(doubleMinus);
		add(attackLabel);
		add(attackPlus);
		add(attackMinus);

		critLabel.setForeground(Color.white);
		fumbleLabel.setForeground(Color.white);
		doubleLabel.setForeground(Color.white);
		attackLabel.setForeground(Color.white);

		critPlus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCrits(crits + 1);
			}
		});

		critMinus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCrits(crits - 1);
			}
		});

		fumblePlus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFumbles(fumbles + 1);
			}
		});

		fumbleMinus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFumbles(fumbles - 1);
			}
		});

		doublePlus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDoubles(doubles + 1);
			}
		});

		doubleMinus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDoubles(doubles - 1);
			}
		});

		attackPlus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setAttacks(attacks + 1);
			}
		});

		attackMinus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setAttacks(attacks - 1);
			}
		});

	}

	public void resetStats() {
		setCrits(0);
		setFumbles(0);
		setDoubles(0);
		setAttacks(0);
	}

	/**
	 * Get the Doubles value.
	 * 
	 * @return the Doubles value.
	 */
	public int getDoubles() {
		return doubles;
	}

	/**
	 * Set the Doubles value.
	 * 
	 * @param newDoubles
	 *            The new Doubles value.
	 */
	public void setDoubles(int newDoubles) {
		this.doubles = newDoubles;
		doubleLabel.setText("Double D's : " + doubles);
	}

	/**
	 * Get the Fumbles value.
	 * 
	 * @return the Fumbles value.
	 */
	public int getFumbles() {
		return fumbles;
	}

	/**
	 * Set the Fumbles value.
	 * 
	 * @param newFumbles
	 *            The new Fumbles value.
	 */
	public void setFumbles(int newFumbles) {
		this.fumbles = newFumbles;
		fumbleLabel.setText("Fumbles : " + fumbles);
	}

	/**
	 * Get the Crits value.
	 * 
	 * @return the Crits value.
	 */
	public int getCrits() {
		return crits;
	}

	/**
	 * Set the Crits value.
	 * 
	 * @param newCrits
	 *            The new Crits value.
	 */
	public void setCrits(int newCrits) {
		this.crits = newCrits;
		critLabel.setText("Criticals : " + crits);
	}

	/**
	 * Get the Attacks value.
	 * 
	 * @return the Attacks value.
	 */
	public int getAttacks() {
		return attacks;
	}

	/**
	 * Set the Attacks value.
	 * 
	 * @param newAttacks
	 *            The new Attacks value.
	 */
	public void setAttacks(int newAttacks) {
		this.attacks = newAttacks;
		attackLabel.setText("Attacks : " + attacks);
	}

}

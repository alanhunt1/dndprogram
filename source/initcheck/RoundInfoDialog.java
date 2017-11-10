package initcheck;

import initcheck.character.GridPanel;
import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class RoundInfoDialog extends JDialog implements ListSelectionListener {

	private JList roundList = new JList();

	private JScrollPane listScroll = new JScrollPane(roundList);

	private TiledPanel contents = new TiledPanel();

	private GridPanel inputPanel = new GridPanel();

	private JSpinner numAttacks = new JSpinner();

	private JSpinner numFumbles = new JSpinner();

	private JSpinner numCrits = new JSpinner();

	private JSpinner numDoubleD = new JSpinner();

	JLabel characterNameLabel = new JLabel();
	
	private PanelButton save = new PanelButton("Save");

	public RoundInfoDialog(InitServer owner, DCharacter dc) {

		setTitle("Round Stats for "+dc.getName());
		characterNameLabel.setText(dc.getName());
		Vector<BattleRound> roundInfo = owner.getBattle().getRoundInfo(dc);

		roundList.setListData(roundInfo);
		roundList.addListSelectionListener(this);

		if (roundInfo.size() > 0) {
			roundList.setSelectedIndex(roundInfo.size() - 1);
		}
		
		inputPanel.doLayout(characterNameLabel,0,0,2,1);
		inputPanel.incYPos();
		
		inputPanel.doLayout(new JLabel("Number of Attacks"));
		inputPanel.doLayout(numAttacks, 1);
		inputPanel.incYPos();

		inputPanel.doLayout(new JLabel("Number of Fumbles"));
		inputPanel.doLayout(numFumbles, 1);
		inputPanel.incYPos();

		inputPanel.doLayout(new JLabel("Number of Crits"));
		inputPanel.doLayout(numCrits, 1);
		inputPanel.incYPos();

		inputPanel.doLayout(new JLabel("Number of Double Ds"));
		inputPanel.doLayout(numDoubleD, 1);
		inputPanel.incYPos();

		inputPanel.doLayout(save, 0, inputPanel.ypos, 2, 1);

		contents.setLayout(new BorderLayout());
		contents.add(listScroll, BorderLayout.CENTER);
		contents.add(inputPanel, BorderLayout.SOUTH);

		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveValues();
				dispose();
			}
		});

		getContentPane().add(contents);
		pack();
		setVisible(true);
	}

	public void saveValues() {
		BattleRound br = (BattleRound) roundList.getSelectedValue();

		br.setNumAttacks(((Integer) numAttacks.getValue()).intValue());		
		br.setFumbles(((Integer) numFumbles.getValue()).intValue());
		br.setCrits(((Integer) numCrits.getValue()).intValue());
		br.setDoubleDamages(((Integer) numDoubleD.getValue()).intValue());
	}

	public void valueChanged(ListSelectionEvent e) {

		try {
			BattleRound br = (BattleRound) roundList.getSelectedValue();
			numAttacks.setValue(br.getNumAttacks());
			numFumbles.setValue(br.getFumbles());
			numCrits.setValue(br.getCrits());
			numDoubleD.setValue(br.getDoubleDamages());

		} catch (Exception ex) {

			ex.printStackTrace();
		}
	}
}

package initcheck;

import initcheck.character.GridPanel;

import javax.swing.JLabel;

public class PartyStatPanel extends GridPanel {
	
	private JLabel totalHeal = new JLabel();
	
	private JLabel maxHeal = new JLabel();
	
	private JLabel minHeal = new JLabel();
	
	private static final long serialVersionUID = 1L;

	public PartyStatPanel(PartyStats p){
		super();
		setWeightY(1);
		doLayout(new JLabel("Max Party Healing Capacity "), 0, ypos);
		doLayout(totalHeal,1,ypos);
		incYPos();
		doLayout(new JLabel("Min Party Healing Capacity"), 0, ypos);
		doLayout(minHeal, 1, ypos);
		incYPos();
		doLayout(new JLabel("Maximum Single Heal"), 0, ypos);
		doLayout(maxHeal, 1, ypos);
		
		setPartyStats(p);
			
	}
	
	public void setPartyStats(PartyStats p){
		
		if (p != null){
			totalHeal.setText(""+p.getTotalCures());
			maxHeal.setText(""+p.getMaxCure());
			minHeal.setText(""+p.getMinTotalCure());
		}
	}
	
}

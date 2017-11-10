package initcheck.treasure;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class TreasurePopupMenu extends JPopupMenu implements ActionListener {
	static final long serialVersionUID = 1;

	// the popup menu

	private JMenuItem partyTreasure;
	
	
	private TreasureDialog owner;

	public TreasurePopupMenu(TreasureDialog owner) {

		super();

		this.owner = owner;

		
		partyTreasure = new JMenuItem("Add to party treasure");
		partyTreasure.addActionListener(this);
		add(partyTreasure);
		
		
		
	}

	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());

		if (source == partyTreasure) {
			owner.addToPartyTreasure();
		} 

	}
}

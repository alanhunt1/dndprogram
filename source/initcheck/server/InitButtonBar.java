package initcheck.server;


import initcheck.InitServer;
import initcheck.PanelButton;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class InitButtonBar extends JPanel implements ActionListener {
	static final long serialVersionUID = 1;
		InitServer owner;

		private PanelButton removeButton = new PanelButton("Remove", 65);
		private PanelButton killButton = new PanelButton("Kill", 65);
		private PanelButton reviveButton = new PanelButton("Revive", 65);
		private PanelButton stunButton = new PanelButton("Stun", 65);
		private PanelButton unStunButton = new PanelButton("Unstun", 65);
		private PanelButton advanceButton = new PanelButton("Next", 65);
		private PanelButton spellButton = new PanelButton("Spells", 65);
		private PanelButton upButton = new PanelButton("^", 40);
		private PanelButton downButton = new PanelButton("v", 40);
		private PanelButton currentButton = new PanelButton("Curr", 65);
		
		public InitButtonBar(InitServer owner){
				super();
				this.owner = owner;
								
				unStunButton.addActionListener(this);
				advanceButton.addActionListener(this);
				stunButton.addActionListener(this);
				reviveButton.addActionListener(this);
				removeButton.addActionListener(this);
				killButton.addActionListener(this);
				spellButton.addActionListener(this);
				upButton.addActionListener(this);
				downButton.addActionListener(this);
				currentButton.addActionListener(this);
				
				add(spellButton);
				add(removeButton);
				add(killButton);
				add(reviveButton);
				add(stunButton);
				add(unStunButton);
				add(advanceButton);
				add(currentButton);
				add(upButton);
				add(downButton);
		}
		
		public void actionPerformed(ActionEvent e) {
				PanelButton source = (PanelButton)(e.getSource());
				owner.getFrame().setCursor(new Cursor(Cursor.WAIT_CURSOR));
				if (source == removeButton){
						owner.getList().removeListItem();
				}else if (source == killButton){
						owner.getList().markListItemDead();
				}else if (source == reviveButton){
						owner.getList().markListItemAlive();
				}else if (source == stunButton){
						owner.getList().markListItemStunned();
				}else if (source == unStunButton){
						owner.getList().markListItemUnStunned();
				}else if (source == advanceButton){
						owner.getList().advanceList();
				}else if (source == spellButton){
						owner.showSpellDialog();
				}else if (source == upButton){
						owner.getList().moveListItemUp();
				}else if (source == downButton){
						owner.getList().moveListItemDown();
				}else if (source == currentButton){
						owner.getList().goToCurrent();
				}
				owner.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
}

package initcheck;

import initcheck.character.chooser.PartyChooser;
import initcheck.character.chooser.RaceChooser;
import initcheck.database.Party;
import initcheck.graphics.TiledDialog;
import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;


public class PlayerAddDialog extends TiledDialog {
	static final long serialVersionUID = 1;
		JPanel contents = new JPanel();
		PlayerManager owner;
		PanelButton addButton = new PanelButton("Add");
		PanelButton cancelButton = new PanelButton("Cancel");
		PartyChooser partyChooser = new PartyChooser();
		RaceChooser raceChooser = new RaceChooser();
		
		RoundJTextField name = new RoundJTextField(30);
		
		public PlayerAddDialog(final PlayerManager owner){
				
				this.owner = owner;
				partyChooser.setSelectedParty(owner.getCurrentParty());
				contents.setLayout(new BorderLayout());
				TiledPanel infoPanel = new TiledPanel(InitImage.lightRocks);
				infoPanel.add(name);
				infoPanel.add(partyChooser);
				infoPanel.add(raceChooser);
				contents.add(infoPanel, BorderLayout.CENTER);
				
				JPanel buttonPanel = new JPanel();
				buttonPanel.add(addButton);
				
				setMainWindow(contents);
				buttonPanel.setOpaque(false);
				setButtonBar(buttonPanel);

				addButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										addPlayer();
								}
						});


				cancelButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										setVisible(false);
								}
						});

		}
		
		private void addPlayer(){
				DCharacter dc = new DCharacter();
				dc.setName(name.getText());
				dc.setParty(((Party)partyChooser.getSelectedItem()).getPartyName());
				dc.setPartyId(((Party)partyChooser.getSelectedItem()).getId());
				dc.setRace((String)raceChooser.getSelectedItem());
				owner.addCharacter(dc);
				dispose();
		}
		
		

}

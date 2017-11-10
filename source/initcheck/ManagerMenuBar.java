package initcheck;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ManagerMenuBar extends JMenuBar implements ActionListener{
	static final long serialVersionUID = 1;
		// the server
		PlayerManager owner;

		// the file menu
		private JMenu menu  = new JMenu("File");
		private JMenu exportMenu = new JMenu("Export");
		private JMenu broadcastMenu = new JMenu("Broadcast");
		private JMenu viewMenu = new JMenu("View");
		private JMenu toolsMenu = new JMenu("Tools");
		
		private JMenuItem itemBuilder = new JMenuItem("Item Builder");
		
		private JMenuItem updateChecker = new JMenuItem("Check For Updates");
		private JMenuItem upload = new JMenuItem("Upload Changes");

		private JMenu charExportMenu = new JMenu("Characters");
		
		
		private JMenuItem importPlayers = null;	
		private JMenuItem exit = null;	
		
		private JMenuItem export = new JMenuItem("Export All");
		private JMenuItem exportParty = new JMenuItem("Export Party");
		private JMenuItem exportSelected = new JMenuItem("Export Selected");	
		
		
		
		private JMenuItem exportTab = new JMenuItem("Export All");
		private JMenuItem exportSelectedTab = new JMenuItem("Export Selected");
		
		
		private JMenuItem viewComparison = new JMenuItem("Player Statistics");
		private JMenuItem viewBattle = new JMenuItem("Battle Stats");
		private JMenuItem viewPreferences = new JMenuItem("Preferences");
		private JMenuItem dmMode = new JMenuItem("DM Mode");
		
		private JMenuItem broadcast = new JMenuItem("Broadcast Selected");
		private JMenuItem broadcastServer = new JMenuItem("Broadcast Selected To Server");
		private JMenuItem broadcastTest = new JMenuItem("Broadcast Test");
		
		public ManagerMenuBar(PlayerManager owner){
				super();
				this.owner = owner;

				//Build the file menu.
				menu.setMnemonic(KeyEvent.VK_F);
				this.add(menu);
				
				//Build the export menu.
				exportMenu.setMnemonic(KeyEvent.VK_E);
				this.add(exportMenu);
				
				exportMenu.add(exportTab);
				exportMenu.add(exportSelectedTab);
				exportMenu.add(charExportMenu);
			

				exportTab.addActionListener(this);
				exportSelectedTab.addActionListener(this);
				
			
				export.addActionListener(this);
				charExportMenu.add(export);
				
				exportParty.addActionListener(this);
				charExportMenu.add(exportParty);

				exportSelected.addActionListener(this);
				charExportMenu.add(exportSelected);
				
				importPlayers = new JMenuItem("Import Data");
				importPlayers.addActionListener(this);
				menu.add(importPlayers);
				
				//Build the broadcast menu
				this.add(broadcastMenu);
				broadcastMenu.add(broadcast);
				broadcast.addActionListener(this);
				broadcastMenu.add(broadcastServer);
				broadcastServer.addActionListener(this);
				broadcastMenu.add(broadcastTest);
				broadcastTest.addActionListener(this);
				//Build the view menu.
				viewMenu.setMnemonic(KeyEvent.VK_V);
				this.add(viewMenu);
				
				viewComparison.addActionListener(this);
				viewMenu.add(viewComparison);
				
				viewBattle.addActionListener(this);
				viewMenu.add(viewBattle);

				viewPreferences.addActionListener(this);
				viewMenu.add(viewPreferences);
				
				
				
				//Build the tools menu.
				toolsMenu.setMnemonic(KeyEvent.VK_T);
				this.add(toolsMenu);

				itemBuilder.addActionListener(this);
				toolsMenu.add(itemBuilder);
				
				updateChecker.addActionListener(this);
				toolsMenu.add(updateChecker);
				
				upload.addActionListener(this);
				toolsMenu.add(upload);
				
				dmMode.addActionListener(this);
				toolsMenu.add(dmMode);

				
				//exit
				exit = new JMenuItem("Exit");
				exit.addActionListener(this);
				menu.add(exit);

		}
		
		public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
				if (source == exit){
						System.exit(0);
				}else if (source == export){
						owner.export();
				}else if (source == exportParty){
						owner.exportParty();
				}else if (source == exportSelected){
						owner.exportSelected();
				}else if (source == importPlayers){
						owner.importPlayers();
				}else if (source == viewComparison){
						owner.showComparisonDialog();
				}else if (source == viewBattle){
						owner.showBattleDialog();
				}else if (source == itemBuilder){
						owner.showItemBuilder();
				}else if (source == updateChecker){
						owner.checkForUpdates();
				}else if (source == upload){
						owner.uploadExport();
				}else if (source == viewPreferences){
						owner.showPreferences();
				}else if (source == dmMode){
						owner.toggleDmMode();
				}else if (source == exportTab){
						owner.exportCurrentTab();
				}else if (source == exportSelectedTab){
						owner.exportSelectedItems();
				}else if (source == broadcast){
						owner.broadcastSelected();
				}else if (source == broadcastServer){
					owner.broadcastToServerSelected();
				}else if (source == broadcastTest){
					owner.broadcastTest();
				}
		}
}

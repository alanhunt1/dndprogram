package initcheck.server;

import initcheck.InitServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class InitMenuBar extends JMenuBar implements ActionListener {

	static final long serialVersionUID = 1;

	// the server
	InitServer owner;

	// the file menu
	private JMenu menu;

	private JMenuItem exit;

	private JMenuItem save;

	private JMenuItem loadMap;

	private JMenuItem print;

	private JMenuItem loadGame;

	// the add menu
	private JMenu addMenu;

	private JMenuItem addPlayers;

	private JMenuItem addMonsters;

	
	
	private JMenuItem removeMonsters;

	private JMenuItem removePlayers;

	private JMenuItem removeAllMonsters;

	// the modifier menu

	// the options menu
	private JMenu optionMenu;

	private JMenuItem autoSkip;

	private JMenuItem attackWarning;

	private JMenuItem strokeDM;

	private JMenuItem thirsty;

	private JMenuItem hungry;

	private JMenuItem attention;

	private JMenuItem preferences;

	// the display menu
	private JMenu displayMenu;

	private JMenuItem xpCalc;

	private JMenuItem treasCalc;

	private JMenuItem highScore;

	private JMenuItem refresh;

	private JMenuItem stats;

	private JMenuItem netStats;
	
	// the tools menu
	private JMenu toolsMenu;

	private JMenuItem sendMessage;

	private JMenuItem sendPictures;

	private JMenuItem importSound;

	private JMenuItem importImage;

	private JMenuItem clearMemory;

	private JMenuItem createEncounter;

	private JMenuItem runEncounter;

	// the campaign notes menu
	private JMenu campaignMenu;

	private JMenuItem loadFile;

	private JMenuItem addText;
	
	private JMenuItem addMap;
	
	private JMenuItem addTown;
	
	private JMenuItem removeTab;
	
	private JMenuItem addEncounter;
	
	private JMenuItem saveAll;
	
	private JMenuItem saveAs;
	
	private JMenuItem renameTab;
	
	public InitMenuBar(InitServer owner) {
		super();
		this.owner = owner;

		// Build the file menu.
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		this.add(menu);

		// print
		print = new JMenuItem("Print");
		print.addActionListener(this);
		menu.add(print);

		// save
		save = new JMenuItem("Save");
		save.addActionListener(this);
		menu.add(save);

		// save
		loadMap = new JMenuItem("Load Map");
		loadMap.addActionListener(this);
		menu.add(loadMap);

		loadGame = new JMenuItem("Load Battle");
		loadGame.addActionListener(this);
		menu.add(loadGame);

		// exit
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		menu.add(exit);

		// build the add menu
		addMenu = new JMenu("Add");
		this.add(addMenu);

		addPlayers = new JMenuItem("Set Players");
		addPlayers.addActionListener(this);
		addMenu.add(addPlayers);

		removePlayers = new JMenuItem("Remove Players");
		removePlayers.addActionListener(this);
		addMenu.add(removePlayers);

		addMonsters = new JMenuItem("Set Encounter");
		addMonsters.addActionListener(this);
		addMenu.add(addMonsters);

	
		
		removeMonsters = new JMenuItem("Remove Monsters");
		removeMonsters.addActionListener(this);
		addMenu.add(removeMonsters);

		removeAllMonsters = new JMenuItem("Remove All Monsters");
		removeAllMonsters.addActionListener(this);
		addMenu.add(removeAllMonsters);

		// build the options menu
		optionMenu = new JMenu("Options");
		this.add(optionMenu);

		autoSkip = new JMenuItem("Disable Client");
		autoSkip.addActionListener(this);
		optionMenu.add(autoSkip);

		attackWarning = new JMenuItem("Disable Attack Warnings");
		attackWarning.addActionListener(this);
		optionMenu.add(attackWarning);

		strokeDM = new JMenuItem("Stroke My Ego");
		strokeDM.addActionListener(this);
		optionMenu.add(strokeDM);

		thirsty = new JMenuItem("I'm Thirsty!");
		thirsty.addActionListener(this);
		optionMenu.add(thirsty);

		hungry = new JMenuItem("I'm Hungry!");
		hungry.addActionListener(this);
		optionMenu.add(hungry);

		attention = new JMenuItem("I want attention!");
		attention.addActionListener(this);
		optionMenu.add(attention);

		preferences = new JMenuItem("Preferences");
		preferences.addActionListener(this);
		optionMenu.add(preferences);

		// build the display menu
		displayMenu = new JMenu("Display");
		this.add(displayMenu);

		xpCalc = new JMenuItem("XP Calculator");
		xpCalc.addActionListener(this);
		displayMenu.add(xpCalc);

		treasCalc = new JMenuItem("Treasure Calculator");
		treasCalc.addActionListener(this);
		displayMenu.add(treasCalc);

		highScore = new JMenuItem("High Scores");
		highScore.addActionListener(this);
		displayMenu.add(highScore);

		stats = new JMenuItem("Battle Stats");
		stats.addActionListener(this);
		displayMenu.add(stats);

		refresh = new JMenuItem("Refresh");
		refresh.addActionListener(this);
		displayMenu.add(refresh);

		netStats = new JMenuItem("Network Stats");
		netStats.addActionListener(this);
		displayMenu.add(netStats);
		
		// build the tools menu
		toolsMenu = new JMenu("Tools");
		this.add(toolsMenu);

		sendMessage = new JMenuItem("Send Message");
		sendMessage.addActionListener(this);
		toolsMenu.add(sendMessage);

		sendPictures = new JMenuItem("Send Pictures");
		sendPictures.addActionListener(this);
		toolsMenu.add(sendPictures);

		importSound = new JMenuItem("Import Sounds");
		importSound.addActionListener(this);
		toolsMenu.add(importSound);

		importImage = new JMenuItem("Import Images");
		importImage.addActionListener(this);
		toolsMenu.add(importImage);

		createEncounter = new JMenuItem("Create Encounter");
		createEncounter.addActionListener(this);
		toolsMenu.add(createEncounter);

		runEncounter = new JMenuItem("Automate Battle");
		runEncounter.addActionListener(this);
		toolsMenu.add(runEncounter);

		clearMemory = new JMenuItem("Clear Memory");
		clearMemory.addActionListener(this);
		toolsMenu.add(clearMemory);

		// build the tools menu
		campaignMenu = new JMenu("Campaign Notes");
		this.add(campaignMenu);
		
		loadFile = new JMenuItem("Load Campaign File");
		loadFile.addActionListener(this);
		campaignMenu.add(loadFile);
		
		addText = new JMenuItem("Add Text Tab");
		addText.addActionListener(this);
		campaignMenu.add(addText);
		
		addMap = new JMenuItem("Add Map Tab");
		addMap.addActionListener(this);
		campaignMenu.add(addMap);
		
		addTown = new JMenuItem("Add Town Tab");
		addTown.addActionListener(this);
		campaignMenu.add(addTown);
		
		removeTab = new JMenuItem("Remove Current Tab");
		removeTab.addActionListener(this);
		campaignMenu.add(removeTab);
	
		addEncounter = new JMenuItem("Add Encounter Tab");
		addEncounter.addActionListener(this);
		campaignMenu.add(addEncounter);
		
		saveAll = new JMenuItem("Save All");
		saveAll.addActionListener(this);
		saveAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		campaignMenu.add(saveAll);
		
		saveAs = new JMenuItem("Save As");
		saveAs.addActionListener(this);
		campaignMenu.add(saveAs);
		
		renameTab = new JMenuItem("Rename Tab");
		renameTab.addActionListener(this);
		campaignMenu.add(renameTab);
		
		
		
	}

	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());
		if (source == exit) {
			owner.cleanup();
			System.exit(0);
		} else if (source == save) {
			owner.backupToFile();
		} else if (source == loadMap) {
			owner.loadMap();
		} else if (source == print) {
			owner.printList();
		} else if (source == addPlayers) {
			owner.showPlayerDialog();
		} else if (source == addMonsters) {
			owner.showMonsterDialog();
		} else if (source == removeMonsters) {
			owner.removeMonsters();
		} else if (source == removePlayers) {
			owner.removePlayers();
		} else if (source == removeAllMonsters) {
			owner.removeAllMonsters();
		} else if (source == autoSkip) {
			owner.toggleClient();
			toggleClient();
		} else if (source == attackWarning) {
			owner.setShowAttackWarnings(toggleAttackWarning());
		} else if (source == strokeDM) {
			owner.strokeTheDM();
		} else if (source == thirsty) {
			owner.sendCokeRequest();
		} else if (source == hungry) {
			owner.sendSnackRequest();
		} else if (source == attention) {
			owner.sendAttentionRequest();
		} else if (source == preferences) {
			owner.showPreferences();
		} else if (source == xpCalc) {
			owner.showXPDialog();
		} else if (source == treasCalc) {
			owner.showTreasureDialog();
		} else if (source == highScore) {
			owner.showHighScore();
		} else if (source == stats) {
			owner.compileStats();
		} else if (source == netStats) {
			owner.showNetStats();
		} else if (source == refresh) {
			owner.refreshAll();
		} else if (source == sendMessage) {
			owner.showSendMessageDialog();
		} else if (source == sendPictures) {
			owner.sendPictures();
		} else if (source == importSound) {
			owner.showImportSoundDialog();
		} else if (source == importImage) {
			owner.showImportImageDialog();
		} else if (source == clearMemory) {
			System.gc();
		} else if (source == createEncounter) {
			owner.showCreateEncounterDialog();
		} else if (source == runEncounter) {
			owner.automateCurrentEncounter();
		} else if (source == loadGame) {
			owner.loadBattle();
		} else if (source == loadFile) {
			owner.getCampaignNotesPanel().loadAllTabs();
		} else if (source == addText) {
			owner.getCampaignNotesPanel().addNewTextTab();
		} else if (source == addMap) {
			owner.getCampaignNotesPanel().addNewMapTab();
		} else if (source == addTown) {
			owner.getCampaignNotesPanel().addNewTownTab();
		}else if (source == removeTab) {
			owner.getCampaignNotesPanel().removeTab();
		} else if (source == addEncounter) {
			owner.getCampaignNotesPanel().addNewEncounterTab();
		} else if (source == saveAll) {
			owner.getCampaignNotesPanel().saveAllTabs();
		}else if (source == saveAs) {
			owner.getCampaignNotesPanel().saveAs();
		}else if (source == renameTab) {
			String inputValue = JOptionPane
			.showInputDialog("What would you like the name of this panel to be?");
			owner.getCampaignNotesPanel().renameCurrentTab(inputValue);
		}

	}

	private boolean toggleAttackWarning() {
		if (attackWarning.getText().equals("Disable Attack Warnings")) {
			attackWarning.setText("Enable Attack Warnings");
			return false;
		} else {
			attackWarning.setText("Disable Attack Warnings");
			return true;
		}
	}

	private void toggleClient() {
		if (autoSkip.getText().equals("Disable Client")) {
			autoSkip.setText("Enable Client");
		} else {
			autoSkip.setText("Disable Client");
			owner.sendClientList();

		}
	}
}

package initcheck.dungeon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class DungeonMenuBar extends JMenuBar implements ActionListener {

	private static final long serialVersionUID = 1L;

	// the server
	DungeonGUI owner;

	// the file menu
	private JMenu menu;

	private JMenuItem exit;

	private JMenuItem save;

	private JMenuItem print;

	// the add menu
	private JMenu addMenu;

	private JMenuItem addPlayers;

	private JMenuItem addMonsters;

	private JMenuItem removeMonsters;

	private JMenuItem removePlayers;

	private JMenuItem removeAllMonsters;

	// the options menu
	private JMenu optionMenu;

	private JMenuItem autoSkip;

	private JMenuItem strokeDM;

	private JMenuItem thirsty;

	private JMenuItem hungry;

	private JMenuItem attention;

	private JMenuItem preferences;

	// the display menu
	private JMenu displayMenu;

	private JMenuItem finder;

	private JMenuItem hitSheet;

	private JMenuItem xpCalc;

	private JMenuItem playerMod;

	private JMenuItem refresh;

	private JMenuItem monsterMod;

	private JMenuItem map;

	// the tools menu
	private JMenu toolsMenu;

	private JMenuItem sendMessage;

	private JMenuItem importSound;

	private JMenuItem importImage;

	private JMenuItem clearMemory;

	private JMenuItem createEncounter;

	public DungeonMenuBar(DungeonGUI owner) {
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

		addMonsters = new JMenuItem("Set Monsters");
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

		finder = new JMenuItem("Monster/Player Finder");
		finder.addActionListener(this);
		// displayMenu.add(finder);

		hitSheet = new JMenuItem("Monster Hit Sheet");
		hitSheet.addActionListener(this);
		// displayMenu.add(hitSheet);

		xpCalc = new JMenuItem("XP Calculator");
		xpCalc.addActionListener(this);
		displayMenu.add(xpCalc);

		playerMod = new JMenuItem("Player Database");
		playerMod.addActionListener(this);
		displayMenu.add(playerMod);

		monsterMod = new JMenuItem("Monster Database");
		monsterMod.addActionListener(this);
		displayMenu.add(monsterMod);

		map = new JMenuItem("Map");
		map.addActionListener(this);
		displayMenu.add(map);

		refresh = new JMenuItem("Refresh");
		refresh.addActionListener(this);
		displayMenu.add(refresh);

		// build the tools menu
		toolsMenu = new JMenu("Tools");
		this.add(toolsMenu);

		sendMessage = new JMenuItem("Send Message");
		sendMessage.addActionListener(this);
		toolsMenu.add(sendMessage);

		importSound = new JMenuItem("Import Sounds");
		importSound.addActionListener(this);
		toolsMenu.add(importSound);

		importImage = new JMenuItem("Import Images");
		importImage.addActionListener(this);
		toolsMenu.add(importImage);

		createEncounter = new JMenuItem("Create Encounter");
		createEncounter.addActionListener(this);
		toolsMenu.add(createEncounter);

		clearMemory = new JMenuItem("Clear Memory");
		clearMemory.addActionListener(this);
		toolsMenu.add(clearMemory);
	}

	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());
		if (source == exit) {
			// owner.cleanup();
			System.exit(0);
		} else if (source == save) {
			// owner.backupToFile();
		} else if (source == print) {
			// owner.printList();
		} else if (source == addPlayers) {
			// owner.showPlayerDialog();
		} else if (source == addMonsters) {
			// owner.showMonsterDialog();
		} else if (source == removeMonsters) {
			// owner.removeMonsters();
		} else if (source == removePlayers) {
			// owner.removePlayers();
		} else if (source == removeAllMonsters) {
			// owner.removeAllMonsters();
		} else if (source == autoSkip) {
			// owner.toggleClient();
			toggleClient();
		} else if (source == strokeDM) {
			// owner.strokeTheDM();
		} else if (source == thirsty) {
			// owner.sendCokeRequest();
		} else if (source == hungry) {
			// owner.sendSnackRequest();
		} else if (source == attention) {
			// owner.sendAttentionRequest();
		} else if (source == preferences) {
			// owner.showPreferences();
		} else if (source == finder) {
			// owner.showAlternateList();
		} else if (source == hitSheet) {
			// owner.showHitSheet();
		} else if (source == xpCalc) {
			// owner.showXPDialog();
		} else if (source == playerMod) {
			// owner.editPlayers();
		} else if (source == monsterMod) {
			// owner.editMonsters();
		} else if (source == map) {
			// owner.showMap();
		} else if (source == refresh) {
			// owner.refreshAll();
		} else if (source == sendMessage) {
			// owner.showSendMessageDialog();
		} else if (source == importSound) {
			// owner.showImportSoundDialog();
		} else if (source == importImage) {
			// owner.showImportImageDialog();
		} else if (source == clearMemory) {
			System.gc();
		} else if (source == createEncounter) {
			// owner.showCreateEncounterDialog();
		}

	}

	private void toggleClient() {
		if (autoSkip.getText().equals("Disable Client")) {
			autoSkip.setText("Enable Client");
		} else {
			autoSkip.setText("Disable Client");
			// owner.sendClientList();

		}
	}
}

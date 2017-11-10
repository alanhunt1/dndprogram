package initcheck;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.PropertyConfigurator;

import com.jgoodies.looks.plastic.PlasticTabbedPaneUI;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.SkyRed;

// java imports
import initcheck.character.PlayerStatDialog;
import initcheck.character.printsheets.DMSheetPrinter;
import initcheck.database.BattleHistory;
import initcheck.database.BattleHistoryDAO;
import initcheck.database.DamageRecord;
import initcheck.database.DamageRecordDAO;
import initcheck.database.InitDBC;
import initcheck.database.Monster;
import initcheck.database.MonsterKills;
import initcheck.database.MonsterKillsDAO;
import initcheck.database.PlayerBattles;
import initcheck.database.PlayerBattlesDAO;
import initcheck.database.PlayerKills;
import initcheck.database.PlayerKillsDAO;
import initcheck.database.Weapon;
import initcheck.dungeon.ClientServerDungeonGUI;
import initcheck.dungeon.Dungeon;
import initcheck.dungeon.Square;
import initcheck.dungeon.ThumbnailPanel;
import initcheck.graphics.Skin;
import initcheck.graphics.Skinnable;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledPanel;
import initcheck.images.ImageSelectionDialog;
import initcheck.io.GroupListener;
import initcheck.io.InitPacket;
import initcheck.io.NetworkMonitorDialog;
import initcheck.io.ServerMessageSender;
import initcheck.preferences.PreferencesDialog;
import initcheck.server.AlternateList;
import initcheck.server.BattleSimulator;
import initcheck.server.CampaignNotesPanel;
import initcheck.server.ConfirmHighScoreDialog;
import initcheck.server.CritRoller;
import initcheck.server.CritStatPanel;
import initcheck.server.DMStrokeDialog;
import initcheck.server.EncounterTab;
import initcheck.server.HighScoreDialog;
import initcheck.server.IndividSpellDialog;
import initcheck.server.InitButtonBar;
import initcheck.server.InitCheckEngine;
import initcheck.server.InitList;
import initcheck.server.InitMenuBar;
import initcheck.server.InitPopupMenu;
import initcheck.server.MemoryManagementThread;
import initcheck.server.MonsterHitSheet;
import initcheck.server.MonsterInfoPanel;
import initcheck.server.NetworkPopupMenu;
import initcheck.server.RoundCounterWidget;
import initcheck.server.WhoDunnitDialog;
import initcheck.server.XPDialog;
import initcheck.sounds.SoundImportDialog;
import initcheck.status.StatusItem;
import initcheck.treasure.TreasureDialog;
import initcheck.utils.StrManip;

/**
 * Describe class <code>InitServer</code> here.
 * 
 * @author <a href="mailto:ahunt@cse.buffalo.edu">Alan M. Hunt</a>
 * @version 1.0
 */
public class InitServer extends InitBase implements InitProgram,
		MonsterDialogParent, PlayerDialogParent {

	int type = SERVER; 

	private String glChannel = "";
	
	PreferencesDialog preferencesDialog = new PreferencesDialog(this);
	GroupListener gl; 
	GroupListener serverSync;
	
	// vectors for multiple groups
	private Vector<InitCheckEngine> engineVector = new Vector<InitCheckEngine>();

	private Vector<InitList> initListVector = new Vector<InitList>();

	private Vector<MonsterHitSheet> hitSheetVector = new Vector<MonsterHitSheet>();

	private Vector<AlternateList> altListVector = new Vector<AlternateList>();

	private ArrayList<Square> groupPositionList = new ArrayList<Square>();

	private Vector<Battle> battleVector = new Vector<Battle>();

	private Vector<String> clients = new Vector<String>();

	private Vector<InitPacket> clientMessages = new Vector<InitPacket>();
	
	public Vector<InitPacket> getClientMessages() {
		return clientMessages;
	}

	
	public void setClientMessages(Vector<InitPacket> clientMessages) {
		this.clientMessages = clientMessages;
	}

	// the client connection listener
	private static ServerMessageSender clientTalker = null;

	//private static ClientListenerManager clientListener = null;

	//private static ClientRequestProcessorThread crpt = null;
	
	public void  writeToClients(){
		for (InitPacket ip:clientMessages){
			gl.addMesg(ip);
		}
		clientMessages.removeAllElements();
		//clientListener.writeToClients();
	}
	
	// the main components

	private InitMenuBar menuBar;

	JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

	// the popup menu
	private InitPopupMenu rClickMenu;

	private NetworkPopupMenu networkPopupMenu;
	
	// graphics
	private ImageIcon backgroundImage = InitImage.steel;

	private ImageIcon dieImage = new ImageIcon("images/diceMod.gif");

	private ImageIcon dieOnImage = new ImageIcon("images/diceOngif.gif");

	private ImageIcon networkImage = new ImageIcon("images/networkonsm.jpg");

	private ImageIcon networkOffImage = new ImageIcon("images/networkoffsm.jpg");

	private JLabel networkLabel = new JLabel(networkOffImage);

	private JLabel networkStatusLabel = new JLabel(
			"Connection Idle                ");

	private int trafficCount = 0;

	private CritStatPanel csp = new CritStatPanel();

	JPanel hsPanel = new JPanel(new BorderLayout());

	// buttons
	private RolloverButton rollButton = new RolloverButton(dieOnImage, dieImage);

	// dialogs
	private PlayerDialog pDlg = null;

	private SpellDialog spellDialog = null;

	private IndividSpellDialog iSpellDialog = null;

	private XPDialog xpDialog = null;

	private TreasureDialog treasureDialog = null;

	private DieRollerPanel dr = null;

	private CureRoller cp = null;

	private CritRoller cr = null;

	private SendMessageDialog smd = null;

	@SuppressWarnings("unused")
	private SoundImportDialog sid = null;

	@SuppressWarnings("unused")
	private ImageSelectionDialog isd = null;

	@SuppressWarnings("unused")
	private CreateEncounterDialog ced = null;

	private BattleProgressDialog bpd = null;

	// the map
	private ClientServerDungeonGUI map = null;

	private MonsterInfoPanel monsterInfoPanel = new MonsterInfoPanel(this);

	private RoundCounterWidget roundCounterWidget = new RoundCounterWidget(this);;

	//private GroupStatusPanel groupStatusPanel;

	private TiledGridPanel wrapperPanel;

	private JTabbedPane tabbedPane = new JTabbedPane();

	private JTabbedPane initListPane = new JTabbedPane(JTabbedPane.LEFT);

	// memory tracking components
	private long usedMemory = 0;

	private JLabel usedMemoryLabel = new JLabel("0");

	private long totalMemory = 0;

	@SuppressWarnings("unused")
	private Font font;

	private InitLogger logger = new InitLogger(this);

	private ChatPanel chp = null;

	private Random rnd = new Random();

	String party;

	public int currentGroup = 0;

	int oldGroup = 0;

	int tabIndex = 0;

	JPanel clientPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));

	String id = "DM ";

	DMSheetPrinter dms = new DMSheetPrinter();

	JScrollPane dmsScroll = new JScrollPane(dms);

	JComponent contents;

	private PartyStats partyStats = new PartyStats();
	
	private PartyStatPanel partyStatPanel = new PartyStatPanel(partyStats);
	
	private Vector<DamageRecord> damageRecords;

	@SuppressWarnings("unused")
	private Vector<DamageRecord> damageRoundRecords;

	private boolean showAttackWarnings = true;
	
	private NetworkMessagePanel networkMessagePanel = new NetworkMessagePanel();
	
	private CampaignNotesPanel campaignNotesPanel = new CampaignNotesPanel(this);
	
	private EncounterTab encounterPanel = new EncounterTab(null);
	
	public void addThumbnail(ThumbnailPanel tp){
		
	}
	
	
	public void setShowAttackWarnings(boolean b) {
		showAttackWarnings = b;
	}

	public boolean getShowAttackWarnings() {
		return showAttackWarnings;
	}

	public void validateClientList(){
		clientTalker.sendPing();
	}
	
	public void removeClient(int i) {
		if (clients.size() > i) {
			clients.remove(i);
		}
		refreshClientPanel();
	}

	public int getPartySize() {
		int i = (getEngine().getDB().getParty(party)).size();
		return i;
	}

	public double getPartyLevel() {
		double levelTotal = 0;
		Vector<Participant> v = getEngine().getDB().getPlayerGroup(currentGroup);
		for (int i = 0; i < v.size(); i++) {
			DCharacter d = (DCharacter)v.get(i);
			levelTotal += d.getLevel();
		}
		System.out.println("Calculating Party Level - total is "+levelTotal);
		double partyLevel = levelTotal
				/ new Double(v.size()).doubleValue();
		return partyLevel;
	}

	public double getMonsterLevel() {
		double levelTotal = 0;
		Vector<Participant> v = getEngine().getMonsters();
		for (int i = 0; i < v.size(); i++) {
			Participant d = (Participant) (v.get(i));
			levelTotal += d.getLevel();
		}
		double partyLevel = new Double(levelTotal).doubleValue()
				/ new Double(v.size()).doubleValue();
		return partyLevel;
	}

	/**
	 * Get the Id value.
	 * 
	 * @return the Id value.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the Id value.
	 * 
	 * @param newId
	 *            The new Id value.
	 */
	public void setId(String newId) {
		this.id = newId;
	}

	/**
	 * Describe <code>sendClientRefresh</code> method here.
	 * 
	 */
	public void sendClientRefresh() {
		if (clientTalker != null) {
			Vector<Participant> v = getEngine().getSortedList();
			clientTalker.sendData(v);
		
		}
		if (map != null) {
			map.sendMap();
		}
	}

	/**
	 * Get the TabIndex value.
	 * 
	 * @return the TabIndex value.
	 */
	public int getTabIndex() {
		return tabIndex;
	}

	/**
	 * Set the TabIndex value.
	 * 
	 * @param newTabIndex
	 *            The new TabIndex value.
	 */
	public void setTabIndex(int newTabIndex) {
		this.tabIndex = newTabIndex;
	}

	/**
	 * Describe <code>showPreferences</code> method here.
	 * 
	 */
	public void showPreferences() {

		if (preferencesDialog == null) {

			preferencesDialog = new PreferencesDialog(this);
		}
		preferencesDialog.setVisible(true);
	}

	/**
	 * Get the CurrentGroup value.
	 * 
	 * @return the CurrentGroup value.
	 */
	public int getCurrentGroup() {
		return currentGroup;
	}

	/**
	 * Set the CurrentGroup value.
	 * 
	 * @param newCurrentGroup
	 *            The new CurrentGroup value.
	 */
	public void setCurrentGroup(int newCurrentGroup) {
		if (map != null) {
			Square currPos = map.getCurrentPosition();

			if (groupPositionList.size() > currentGroup) {
				groupPositionList.set(currentGroup, currPos);
			} else {
				groupPositionList.add(currPos);
			}

			this.currentGroup = newCurrentGroup;
			Square newPos = (Square) (currPos.clone());
			if (groupPositionList.size() > currentGroup) {
				newPos = groupPositionList.get(currentGroup);
			} else {
				groupPositionList.add(newPos);
			}
			map.setCurrentGroup(currentGroup);
			map.setCurrentPosition(newPos.getX(), newPos.getY(), newPos.getZ());
			clientTalker.sendCurrentGroup(currentGroup);
		}
	}

	public void processWhine(String whine) {
		
		playSound(getSound("treasurewhine"));
		@SuppressWarnings("unused")
		MessageDialog md = new MessageDialog("Boo Hoo Hoo",
				"The Players Snivel : \n" + whine);
	}

	/**
	 * Get the Party value.
	 * 
	 * @return the Party value.
	 */
	public String getParty() {
		return party;
	}

	/**
	 * Set the Party value.
	 * 
	 * @param newParty
	 *            The new Party value.
	 */
	public void setParty(String newParty) {
		this.party = newParty;
	}

	/**
	 * Describe <code>setInitTab</code> method here.
	 * 
	 */
	public void setInitTab() {
		initListPane.setSelectedIndex(tabIndex);
		getList().advanceList();
	}

	public void selectParty(){
		String inputValue = JOptionPane.showInputDialog("Enter party name to select");
		getList().selectParty(inputValue);
	}
	
	/**
	 * Describe <code>mergeParties</code> method here.
	 * 
	 */
	public void mergeParties() {
		int a = currentGroup;

		String inputValue = JOptionPane
				.showInputDialog("Which party do you wish to merge into?");
		int b = 0;
		try {
			b = Integer.parseInt(inputValue);
		} catch (NumberFormatException nfe) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error!",
					"You must pick an integer value from the tabs on the left.");
			return;
		}
		// get the characters from the merging tab
		Vector<DCharacter> chars = (engineVector.get(a)).getDB()
				.getCharacters();

		// add them to the merge to tab
		(engineVector.get(b - 1)).getDB().getCharacters().addAll(chars);

		for (int i = 0; i < chars.size(); i++) {
			(engineVector.get(b - 1))
					.insertCharacter((DCharacter) chars.get(i));
		}

		// switch to the merge to tab
		initListPane.setSelectedIndex(b - 1);

		// wipe out the merging tab
		engineVector.remove(a);
		hitSheetVector.remove(a);
		initListVector.remove(a);
		initListPane.remove(a);
		groupPositionList.remove(a);
		battleVector.remove(a);
		
		if (tabIndex >= a) {
			tabIndex = tabIndex - 1;
		}

		if (tabIndex == initListVector.size()) {
			tabIndex = 0;
		}

		playSound(getSound("merge parties"));
		frame.validate();
		map.mergeVisibility(b - 1, a);

		for (int i = 0; i < initListVector.size(); i++) {
			initListPane.setTitleAt(i, "" + (i + 1));
		}
		backupToFile();
	}

	/**
	 * Describe <code>transferCharacters</code> method here.
	 * 
	 */
	public void transferCharacters() {
		Vector<Participant> participants = getList().getSelectedParticipants();
		Vector<DCharacter> chars = new Vector<DCharacter>();
		Vector<Participant> monsters = new Vector<Participant>();
		for (int i = 0; i < participants.size(); i++) {
			try {
				if (participants.get(i).getPType().equalsIgnoreCase("Monster")) {
					monsters.add(participants.get(i));
				} else {
					chars.add((DCharacter) participants.get(i));
				}
			} catch (Exception e) {

			}
		}
		String inputValue = JOptionPane
				.showInputDialog("Which party do you want to transfer to?");

		int idx = 0;
		try {
			idx = Integer.parseInt(inputValue);
		} catch (NumberFormatException nfe) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error",
					"You must select an party number from the tabs on the right.");
		}

		(engineVector.get(idx - 1)).getDB().getCharacters().addAll(chars);
		for (int i = 0; i < chars.size(); i++) {
			(engineVector.get(idx - 1)).insertCharacter((DCharacter) chars
					.get(i));

		}

		getList().removeListItem();

		initListPane.setSelectedIndex(idx - 1);

		for (int i = 0; i < monsters.size(); i++) {
			addMonster((Participant) monsters.get(i));
		}
		for (int i = 0; i < chars.size(); i++) {
			(engineVector.get(idx - 1)).insertCharacter((Participant) monsters
					.get(i));

		}

		backupToFile();
	}

	/**
	 * Describe <code>splitParty</code> method here.
	 * 
	 */
	public void splitParty() {

	

		// get the selected characters to split off
		Vector<DCharacter> chars = getList().getSelectedCharacters();
		

		// create a new engine to hold them
		InitCheckEngine newEngine = new InitCheckEngine();

		// add the players and create the sorted list
		newEngine.addPlayers(chars);
		newEngine.doInit();

		// create a new initlist to display them
		InitList newList = new InitList(this);

		// remove the characters from the old engine and list
		getList().removeListItem();

		// increment the group index to display the newly split group
		oldGroup = currentGroup;
		setCurrentGroup(engineVector.size());

		

		// add the new engine and list to the vector of available
		engineVector.add(newEngine);
		initListVector.add(newList);
		hitSheetVector.add(new MonsterHitSheet(this, currentGroup,
				backgroundImage));
		
		Square currPos = map.getCurrentPosition();
		Square newPos = (Square) (currPos.clone());
		
		groupPositionList.add(newPos);
		battleVector.add(currentGroup, new Battle(""));
		
		

		// read in the new sorted list
		Vector<Participant> v = getEngine().getSortedList();

		// and display the data
		getList().setListData(v);
		getList().setSelectedIndex(0);

		// now add the new list to a new tab and display it all
		JScrollPane initScroll = new JScrollPane(getList());
		initListPane.addTab("" + engineVector.size(), initScroll);
		
		changeHitSheet(oldGroup, currentGroup);

		refreshAltList();

		initListPane.setSelectedIndex(engineVector.size() - 1);
		frame.validate();
		playSound(getSound("split party"));
		backupToFile();
	}

	/**
	 * The only constructor is an empty one. All of the work for layout is done
	 * in the createComponents method.
	 */
	public InitServer(String glCluster) {
		// remove the old log file
		File f = new File("ServerLog.txt");
		f.delete();

		// configure logging
		PropertyConfigurator.configure("defaultLog4J.cfg");
		logger.log("STARTING UP INIT SERVER");

		// load up the all time damage records
		DamageRecordDAO drdb = new DamageRecordDAO();

		damageRecords = drdb.getAlltimeRecords();
		damageRoundRecords = drdb.getAlltimeRoundRecords();
		
		gl = new GroupListener(GroupListener.SERVER_MODE, this, glCluster);
		
		
	}

	public void handleTabChange(){
		if (isMapShowing()){
			
			map.updateMap();
		}
	}
	
	/**
	 * Describe <code>createComponents</code> method here.
	 * 
	 * @return a <code>Component</code> value
	 */
	public JComponent createComponents() {

		tabbedPane.setOpaque(false);
		//tabbedPane.setUI(new TestTabUI());
		tabbedPane.setForeground(InitColor.woodText);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				handleTabChange();
			}
		});
		initListPane.setOpaque(false);
		//initListPane.setUI(new TestTabUI());
		initListPane.setForeground(InitColor.woodText);
		initListPane.setBorder(BorderFactory.createEmptyBorder());
		
		
		
		JPanel netPanel = new JPanel(new BorderLayout());

		usedMemoryLabel.setForeground(InitColor.lightRed);
		// preferencesDialog.readFromFile();

		// create the menu bar
		menuBar = new InitMenuBar(this);
		frame.setJMenuBar(menuBar);

		// create the popup menu
		rClickMenu = new InitPopupMenu(this);
		networkPopupMenu = new NetworkPopupMenu(this);
		
		
		// create the group status panel
		//groupStatusPanel = new GroupStatusPanel(this);

		// put the main list into a scrolling panel

		// set the action for the roll button (represented by the red
		// die on the upper left of the server screen)
		rollButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rollInit();
			}
		});

		// create the input panel. This holds the top bar widgets,
		// like the roll button, monster info buttons, group status, and
		// round counter.
		TiledPanel inputPanel = new TiledPanel();
		inputPanel.setLayout(new BorderLayout());

		TiledPanel rollPanel = new TiledPanel();
		rollPanel.add(rollButton);
		rollPanel.setOpaque(false);

		inputPanel.add(rollPanel, BorderLayout.WEST);

		TiledPanel infoPanel = new TiledPanel();

		infoPanel.add(monsterInfoPanel);
		//infoPanel.add(groupStatusPanel);
		infoPanel.add(roundCounterWidget);
		infoPanel.add(usedMemoryLabel);
		infoPanel.setOpaque(false);

		inputPanel.add(infoPanel, BorderLayout.EAST);

		// create the button bar. This holds all of the butotns
		// at the bottom of the list.
		InitButtonBar modPanel = new InitButtonBar(this);

		TiledPanel pane = new TiledPanel(backgroundImage);
		pane.setBorder(BorderFactory.createEmptyBorder(20, // top
				20, // left
				10, // bottom
				20) // right
				);

		pane.setLayout(new BorderLayout());
		inputPanel.setOpaque(false);
		pane.add(inputPanel, BorderLayout.NORTH);

		for (int i = 0; i < initListVector.size(); i++) {
			InitList il = (InitList) initListVector.get(i);

			JScrollPane scrollPane = new JScrollPane(il);
			scrollPane.setOpaque(false);
			initListPane.addTab("" + (i + 1), scrollPane);
		}

		initListPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				setGroup();
			}
		});

		pane.add(initListPane, BorderLayout.CENTER);
		modPanel.setOpaque(false);
		pane.add(modPanel, BorderLayout.SOUTH);

		// create the top level component. wrapperPanel holds all
		// of the rest of the components, and is inserted directly
		// into the main application frame.
		wrapperPanel = new TiledGridPanel(false);
		wrapperPanel.setWeightX(0.6);
		wrapperPanel.setWeightY(0.7);
		
		wrapperPanel.doLayout(pane, 0, 0);

		hsPanel.add(getHitSheet(), BorderLayout.CENTER);
		hsPanel.add(csp, BorderLayout.SOUTH);

		wrapperPanel.setWeightX(0.0);
		wrapperPanel.setWeightY(0.3);		
		wrapperPanel.doLayout(hsPanel, 0, 1, 2, 2);

		dr = new DieRollerPanel(this, backgroundImage);
		wrapperPanel.setWeightY(0.0);				
		wrapperPanel.doLayout(dr, 2, 0);

		cp = new CureRoller(backgroundImage);
		wrapperPanel.doLayout(cp, 2, 1);

		cr = new CritRoller(this, backgroundImage);
		wrapperPanel.doLayout(cr, 2, 2);

		AlternateList al = new AlternateList(this, getEngine().getMonsters(),
				backgroundImage);
		altListVector.add(al);

		wrapperPanel.setWeightX(0.5);
		wrapperPanel.setWeightY(0.0);
		
		wrapperPanel.doLayout(al, 1, 0);
		wrapperPanel.setWeightX(0.0);
		

		tabbedPane.addTab("Main", wrapperPanel);
		tabbedPane.addTab("Monsters", encounterPanel);
		
		TiledPanel partyInfoPanel = new TiledPanel();
		partyInfoPanel.add(partyStatPanel);
		partyInfoPanel.add(dmsScroll);
		
		tabbedPane.addTab("DM Sheet", partyInfoPanel);

		
		tabbedPane.addTab("Network", networkMessagePanel);
		tabbedPane.addTab("Campaign Notes", campaignNotesPanel);
		
		netPanel.add(tabbedPane, BorderLayout.CENTER);

		iconPanel.setBackground(Color.darkGray);
		clientPanel.setBackground(Color.darkGray);
		iconPanel.add(clientPanel);
		iconPanel.add(networkStatusLabel);
		iconPanel.add(networkLabel);
		netPanel.add(iconPanel, BorderLayout.SOUTH);

		networkLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				validateClientList();
			}
			//			 check all the clicks for a right click, and trigger the
			// popup menu if you see one.
			public void mousePressed(MouseEvent e) {
				maybeShowPopup(e);
			}

			public void mouseReleased(MouseEvent e) {
				maybeShowPopup(e);
			}

			private void maybeShowPopup(MouseEvent e) {
				if (e.isPopupTrigger()) {
					getNetworkPopupMenu().show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		
		// return the top level container.
		return netPanel;

	}

	public void clearTraffic(){
		trafficCount = 0;
		networkLabel.setIcon(networkOffImage);
		networkStatusLabel.setText("Connection Idle                ");	
	}
	
	public void startTraffic(String s) {

		trafficCount++;
		networkStatusLabel.setText(s+ "[" + trafficCount + "]");
		networkMessagePanel.addMessage(s);
		
		networkLabel.setIcon(networkImage);
		iconPanel.paintImmediately(0, 0, new Double(iconPanel.getSize()
				.getWidth()).intValue(), new Double(iconPanel.getSize()
				.getHeight()).intValue());
	}

	public void stopTraffic() {
		if (trafficCount > 0){
			trafficCount--;
		}
		if (trafficCount == 0) {
			networkLabel.setIcon(networkOffImage);
			networkStatusLabel.setText("Connection Idle                ");
		}
	}

	/**
	 * Describe <code>setGroup</code> method here.
	 * 
	 */
	public void setGroup() {
		oldGroup = currentGroup;
		setCurrentGroup(initListPane.getSelectedIndex());
	
		if (oldGroup != currentGroup) {
			refreshAltList();
			changeHitSheet(oldGroup, currentGroup);
			getList().setListData(getEngine().getSortedList());

			Vector<Participant> v = getEngine().getSortedList();
			clientTalker.sendData(v);
		}
	}

	public void updateDMSheet() {
		dms.setChars(getEngine().getDB().getCharacters());
	}

	public void loadMapFromFile(String fileName){
		if (map != null) {
			map.loadDungeon(fileName);
		}
		tabbedPane.setSelectedIndex(tabbedPane.indexOfTab("Map"));
		frame.validate();
	}
	
	public void followMapLink(String link){
		map.saveDungeon();
		String [] linkParts = link.split(":");
		map.loadDungeon("./saves/"+linkParts[0]);
		
		if (linkParts.length > 1){
			
			String [] coords = linkParts[1].split(",");
			int x = Integer.parseInt(coords[0]);
			int y = Integer.parseInt(coords[1]);
			int z = Integer.parseInt(coords[2]);
			System.out.println("Going to coordinates "+x+","+y+","+z);
			map.setCurrentPosition(x, y, z);
			map.updateMap();
		}
	}
	
	public void loadMap() {
		if (map != null) {

			String mesg = "Do you want to save the current map before loading a new one?";

			int answer = JOptionPane.showConfirmDialog(null, mesg,
					"Save Current Map?", JOptionPane.YES_NO_OPTION);

			if (answer == JOptionPane.YES_OPTION) {
				map.saveDungeon();
			}
			map.loadDungeon();
		}
	}

	/**
	 * Describe <code>loadMap</code> method here.
	 * 
	 * @param party
	 *            a <code>String</code> value
	 */
	public void loadMap(String party) {
		addMapPanel();
		
	}

	/**
	 * Describe <code>saveMap</code> method here.
	 * 
	 */
	public void saveMap() {
		if (map != null) {
			map.saveMap();

		}
	}

	/**
	 * Describe <code>showChat</code> method here.
	 * 
	 * @param s
	 *            a <code>String</code> value
	 * @param id
	 *            a <code>String</code> value
	 */
	public void showChat(String s, String id) {
		// make sure that the chat panel exists
		if (chp == null) {
			addChatPanel();
		}

		// if you are not currently showing the panel,
		// alert that a message has arrived
		if (tabbedPane.getSelectedIndex() != tabbedPane.indexOfTab("Clients")) {
			playSound(getSound("message arrived"));
		}

		// send the message to the panel
		chp.addMessage(s, id);
	}

	/**
	 * Describe <code>sendChatMessage</code> method here.
	 * 
	 * @param s
	 *            a <code>String</code> value
	 */
	public void sendChatMessage(String s) {
		clientTalker.sendChatMessage(s, id);
	}

	/**
	 * Describe <code>addChatPanel</code> method here.
	 * 
	 */
	public void addChatPanel() {
		if (tabbedPane.indexOfTab("Clients") < 0) {
			chp = new ChatPanel(this);
			tabbedPane.addTab("Clients", chp);
		}
	}

	/**
	 * Describe <code>addMapPanel</code> method here.
	 * 
	 */
	public void addMapPanel() {
		if (tabbedPane.indexOfTab("Map") < 0) {
			map = new ClientServerDungeonGUI(this, backgroundImage,
					ClientServerDungeonGUI.DISPLAY_SERVER);
			map.setMode(ClientServerDungeonGUI.DISPLAY_SERVER);
			tabbedPane.addTab("Map", map);
			map.init();
			map.setBorder(BorderFactory.createEmptyBorder(20, // top
					20, // left
					10, // bottom
					20) // right
					);
		}
	}

	public void testClients() {
		//clientTalker.sendTest();
	}

	// refresh the panel that shows what clients are logged on to the network.
	private void refreshClientPanel() {
		clientPanel.removeAll();
		for (int i = 0; i < clients.size(); i++) {
			JLabel label = new JLabel((String) clients.get(i));
			label.setBackground(InitColor.red);
			label.setForeground(Color.white);
			label.setOpaque(true);
			clientPanel.add(label);
		}
		iconPanel.paintImmediately(0, 0, new Double(iconPanel.getSize()
				.getWidth()).intValue(), new Double(iconPanel.getSize()
				.getHeight()).intValue());
		clientTalker.sendNetworkList(clients);
	}

	/**
	 * Describe <code>addClient</code> method here.
	 * 
	 * @param id
	 *            a <code>String</code> value
	 */
	public void addClient(String id) {
		clients.add(id);
		refreshClientPanel();
		displayMessage("Client " + id + " Has Connected");
		if (chp == null) {
			addChatPanel();
		}
		Vector<Participant> v = getEngine().getSortedList();
	
		clientTalker.sendData(v);
	}

	public void removeClient(String id){
		
		clients.remove(id);
		refreshClientPanel();
	}
	
	/**
	 * Describe <code>getClientListener</code> method here.
	 * 
	 * @return a <code>ClientListenerThread</code> value
	 */
	public ServerMessageSender getClientTalker() {
		return clientTalker;
	}

	/**
	 * Describe <code>getList</code> method here.
	 * 
	 * @return an <code>InitList</code> value
	 */
	public InitList getList() {
		return (InitList) initListVector.get(currentGroup);
	}

	public Battle getBattle() {
		return (Battle) battleVector.get(currentGroup);
	}

	/**
	 * Describe <code>updateClientListPosition</code> method here.
	 * 
	 * @param selectedIndex
	 *            an <code>int</code> value
	 */
	public void updateClientListPosition(int selectedIndex) {
		if (clientTalker != null) {
			clientTalker.updatePosition(selectedIndex);
		}
	}

	/**
	 * Describe <code>getEngine</code> method here.
	 * 
	 * @return an <code>InitCheckEngine</code> value
	 */
	public InitCheckEngine getEngine() {
		return (InitCheckEngine) engineVector.get(currentGroup);
	}

	/**
	 * Describe <code>resetRound</code> method here.
	 * 
	 */
	public void resetRound() {
		// compile the stats from the last battle
		//compileStats();
		
		
		// head back to the first tab
		initListPane.setSelectedIndex(0);
		setTabIndex(0);

		// reset the "next player" index
		getList().setListIndex(0);
		getList().setSelectedIndex(0);

		// reset the round counter, and the display
		roundCounterWidget.resetRound();

		// reset the party and monster spell mods
		//groupStatusPanel.resetStatus();

		// send the updates to the client
		//clientTalker.updatePlayerMod(groupStatusPanel.getPartyMod().toString());
		//clientTalker.updateMonsterMod(groupStatusPanel.getMonsterMod()
		//		.toString());
		clientTalker.updateRound(roundCounterWidget.getRound());
	}

	/**
	 * Describe <code>getHitSheet</code> method here.
	 * 
	 * @param i
	 *            an <code>int</code> value
	 * @return a <code>MonsterHitSheet</code> value
	 */
	public MonsterHitSheet getHitSheet(int i) {
	
		return (MonsterHitSheet) hitSheetVector.get(i);
	}

	/**
	 * Describe <code>getHitSheet</code> method here.
	 * 
	 * @return a <code>MonsterHitSheet</code> value
	 */
	public MonsterHitSheet getHitSheet() {
		
		return (MonsterHitSheet) hitSheetVector.get(currentGroup);
	}

	public void endBattle(){
		@SuppressWarnings("unused") MessageDialog md = new MessageDialog("Game Over Man!","Don't forget to run the battle stats!");
		
	}
	
	public void showNetStats(){
		NetworkMonitorDialog.display();
	}
	
	public void compileStats() {
		triggerAdvanceUpdate();

		Vector<Vector<Object>> hsData = getHitSheetData();
		Vector<Participant> monsters = getEngine().getMonsters();
		Vector<DCharacter> chars = getEngine().getCharacters();
		Battle battle = getBattle();
		Vector<PlayerBattles> statVector = new Vector<PlayerBattles>();

		PlayerKillsDAO pkdb = new PlayerKillsDAO();
		BattleHistoryDAO bhdb = new BattleHistoryDAO();
		PlayerBattlesDAO pbdb = new PlayerBattlesDAO();
		MonsterKillsDAO mkdb = new MonsterKillsDAO();

		BattleHistory bh = new BattleHistory();

		bh.setParty(getParty());
		bh.setPartyLevel("" + getPartyLevel());
		bh.setEncounterLevel("" + getMonsterLevel());

		String memberStr = "";
		for (int i = 0; i < chars.size(); i++) {
			memberStr += (chars.get(i)).getName() + ",";
		}
		memberStr = memberStr.substring(0, memberStr.length() - 1);
		bh.setMembers(memberStr);
		bh.setNumRounds("" + roundCounterWidget.getRound());

		int battleId = bhdb.addOrUpdateBattleHistory(bh);
		bh.setId("" + battleId);

		Vector<PlayerKills> killVector = new Vector<PlayerKills>();
		Vector<String> idVector = new Vector<String>();
		for (int j = 0; j < hsData.size(); j++) {
			// read in a row
			Vector<?> entry = (Vector<?>) hsData.get(j);
			String monsterName = (String) entry.get(0);
			String playerName = (String) entry.get(4);

			Participant m = null;
			DCharacter dc = null;
			for (int i = 0; i < monsters.size(); i++) {
				m = monsters.get(i);
				if (m.getName() != null && m.getName().equals(monsterName)) {
					break;
				}
			}
			for (int i = 0; i < chars.size(); i++) {
				dc = (DCharacter)chars.get(i);

				if (dc.getName() != null && dc.getName().equals(playerName)) {
					break;
				}
			}
			PlayerKills pk = new PlayerKills();
			pk.setPlayerId("" + dc.getID());
			pk.setMonsterId("" + m.getID());
			pk.setPlayerName(playerName);
			pk.setMonsterName(monsterName);

			killVector.add(pk);

			pk.setBattleId("" + battleId);
			pkdb.addPlayerKills(pk);
		}

		BattleStatDialog bsd = new BattleStatDialog("Battle Stats", "");
		bsd.addMessage("Average Party Level : "
				+ StrManip.formatDecimal("" + getPartyLevel()));
		bsd.addMessage("Average Monster CR : "
				+ StrManip.formatDecimal("" + getMonsterLevel()));

		int dmg = 0;
		for (int i = 0; i < monsters.size(); i++) {
			Participant m = monsters.get(i);
			dmg += m.getDamageTaken();
		}
		bsd.addMessage("Total points damage done " + dmg);
		bh.setTotalDamage("" + dmg);
		dmg = 0;

		int bestHit = 0;
		String bestHitPlayer = "";
		int bestRound = 0;
		String bestRoundPlayer = "";
		int bestTotal = 0;
		String bestTotalPlayer = "";
		double bestRatio = 0.0;
		String bestRatioPlayer = "";
		int bestStuns = 0;
		String bestStunsPlayer = "";

		for (int i = 0; i < chars.size(); i++) {
			DCharacter m = (DCharacter) chars.get(i);
			PlayerBattles pb = battle.getBattleStats("" + m.getID());
			pb.setPlayerName(m.getName());
			pb.setPlayerId("" + m.getID());
			pb.setBattleId("" + battleId);
			pb.setTotalTaken("" + (m.getDamageTaken()));
			idVector.add("" + m.getID());
			pbdb.addPlayerBattles(pb);
			statVector.add(pb);
			if (Integer.parseInt(pb.getMaxDamage()) >= bestHit) {
				if (Integer.parseInt(pb.getMaxDamage()) == bestHit) {
					bestHitPlayer += "," + m.getName();
				} else {
					bestHit = Integer.parseInt(pb.getMaxDamage());
					bestHitPlayer = m.getName();
				}
			}
			if (Integer.parseInt(pb.getMaxRoundDamage()) >= bestRound) {
				if (Integer.parseInt(pb.getMaxRoundDamage()) == bestRound) {
					bestRoundPlayer += "," + m.getName();
				} else {
					bestRound = Integer.parseInt(pb.getMaxRoundDamage());
					bestRoundPlayer = m.getName();
				}
			}
			if (Integer.parseInt(pb.getTotalDamage()) >= bestTotal) {
				if (Integer.parseInt(pb.getTotalDamage()) == bestTotal) {
					bestTotalPlayer += "," + m.getName();
				} else {
					bestTotal = Integer.parseInt(pb.getTotalDamage());
					bestTotalPlayer = m.getName();
				}
			}
			if (Integer.parseInt(pb.getNumStuns()) >= bestStuns) {
				if (Integer.parseInt(pb.getNumStuns()) == bestStuns) {
					bestStunsPlayer += "," + m.getName();
				} else {
					bestStuns = Integer.parseInt(pb.getNumStuns());
					bestStunsPlayer = m.getName();
				}
			}
			if (Double.parseDouble(pb.getHitPercentage()) >= bestRatio) {
				if (Double.parseDouble(pb.getHitPercentage()) == bestRatio) {
					bestRatioPlayer += "," + m.getName();
				} else {
					bestRatio = Double.parseDouble(pb.getHitPercentage());
					bestRatioPlayer = m.getName();
				}
			}

			dmg += m.getDamageTaken();
		}
		bsd.addMessage("Total points damage taken " + dmg);
		bh.setTotalTaken("" + dmg);
		double ratio = new Double(monsters.size()).doubleValue()
				/ new Double(chars.size()).doubleValue();
		bsd.addMessage("Monster to Player ratio : "
				+ StrManip.formatDecimal("" + ratio) + " to 1");
		bsd.addMessage("");
		bsd.addMessage("High Scores");
		bsd.addMessage("---------");
		bsd.addMessage("Best Hit : " + bestHitPlayer + " (" + bestHit + ")");
		bsd.addMessage("Best Round : " + bestRoundPlayer + " (" + bestRound
				+ ")");
		bsd.addMessage("Best Total : " + bestTotalPlayer + " (" + bestTotal
				+ ")");
		bsd.addMessage("Most Stuns : " + bestStunsPlayer + " (" + bestStuns
				+ ")");
		bsd.addMessage("Best Hit Ratio : " + bestRatioPlayer + " (" + bestRatio
				+ ")");
		bsd.addMessage("");
		bsd.addMessage("Top Kills");
		bsd.addMessage("---------");
		Vector<String> kills = pkdb.getKillStatus("" + battleId);
		for (int i = 0; i < kills.size() && i < 3; i++) {
			bsd.addMessage((String) kills.get(i));
		}

		Vector<MonsterKills> monsterKills = battle.getMonsterKills();
		for (int i = 0; i < monsterKills.size(); i++) {
			MonsterKills mk = (MonsterKills) monsterKills.get(i);
			mkdb.addMonsterKills(mk);
		}

		clientTalker.sendBattleHistory(bh, killVector, idVector, getBattle()
				.getMonsterKills(), statVector);
		clientTalker.sendBattleStats(bsd.getMessages());

		bhdb.addOrUpdateBattleHistory(bh);
		
		// re-set the battle
		battleVector.setElementAt(new Battle(getParty()),currentGroup);
	}

	public void updateDamageRecords() {
		DamageRecordDAO drdb = new DamageRecordDAO();
		damageRecords = drdb.getAlltimeRecords();
	}

	public Vector<DamageRecord> getDamageRecords() {
		return damageRecords;
	}

	public void showHighScore() {
		HighScoreDialog hsd = new HighScoreDialog(this);

		hsd.setHighScores(damageRecords);
		hsd.pack();
		hsd.setVisible(true);
	}

	public void registerDamage(int dmg, String name) {
		
		Vector<Participant> v = getEngine().getSortedList();
		for (int i = 0; i < v.size(); i++) {
			Participant p = v.get(i);
			if (p.getName().trim().equals(name.trim())) {
				
				if (p.getPType().equalsIgnoreCase("Monster")) {
					return;
				}
				DCharacter d = (DCharacter) p;
				
				if (d.getWeapons().size() > 0) {
					Weapon w = (Weapon) (d.getWeapons().get(0));
					checkDamageRecords(w, d, dmg);
				}
				break;
			}
		}
	}

	public void checkDamageRecords(Weapon w, DCharacter dc, int dmg) {
		boolean foundRecord = false;
		//DamageRecordDAO drdb = new DamageRecordDAO();

		

		// see if there is an all time damage record.
		for (int i = 0; i < damageRecords.size(); i++) {
			DamageRecord dr = (DamageRecord) damageRecords.get(i);
			if (w.getFeatClass().equals(dr.getWeaponType())
					&& w.getUse().equals(dr.getWeaponUse())) {
				foundRecord = true;
				if (dmg > Integer.parseInt(dr.getDamage())) {
					dr.setPlayerId("" + dc.getID());
					dr.setBattleId(getBattle().getBattleId());
					dr.setDamage("" + dmg);
					dr.setPlayerName(dc.getName());
					
					ConfirmHighScoreDialog csd = new ConfirmHighScoreDialog(this,dc,getBattle().getBattleId());
					csd.setDamageRecord(dr);
				}
			}
		}
		// if not, create one
		if (!foundRecord) {
			DamageRecord dr = new DamageRecord();
			dr.setPartyName("Alltime");
			dr.setPlayerId("" + dc.getID());
			dr.setDamage("" + dmg);
			dr.setOneshot(true);
			dr.setWeaponType(w.getFeatClass());
			dr.setBattleId(getBattle().getBattleId());
			dr.setWeaponUse(w.getUse());
			dr.setPlayerName(dc.getName());
			ConfirmHighScoreDialog csd = new ConfirmHighScoreDialog(this,dc,getBattle().getBattleId());
			csd.setDamageRecord(dr);
			
		}
		
		foundRecord = false;

		// see if there is a party damage record

	}

	public void setDamageRecord(DamageRecord dr){
		DamageRecordDAO drdb = new DamageRecordDAO();
		drdb.addOrUpdateDamageRecord(dr);
		damageRecords = drdb.getAlltimeRecords();
		clientTalker.sendHighScore(dr);
	}
	
	/**
	 * Describe <code>showXPDialog</code> method here.
	 * 
	 */
	public void showXPDialog() {

		if (xpDialog == null) {
			xpDialog = new XPDialog(this);
		}
		xpDialog.setVisible(true);
		
		//gl.addMesg("TESTING");
	}

	/**
	 * Describe <code>showXPDialog</code> method here.
	 * 
	 */
	public void showTreasureDialog() {

		if (treasureDialog == null) {
			treasureDialog = new TreasureDialog(this);
		}
		treasureDialog.setVisible(true);
	}

	/**
	 * Describe <code>setMap</code> method here.
	 * 
	 * @param d
	 *            a <code>Dungeon</code> value
	 */
	public void setMap(Dungeon d) {
		clientTalker.sendMap(d);
		
	}

	/**
	 * Describe <code>setPlayerNotes</code> method here.
	 * 
	 * @param x
	 *            an <code>int</code> value
	 * @param y
	 *            an <code>int</code> value
	 * @param z
	 *            an <code>int</code> value
	 * @param s
	 *            a <code>String</code> value
	 */
	public void setPlayerNotes(int x, int y, int z, String s) {
		map.setPlayerNotes(x, y, z, s);
	}

	/**
	 * Describe <code>sendPlayerNotes</code> method here.
	 * 
	 * @param x
	 *            an <code>int</code> value
	 * @param y
	 *            an <code>int</code> value
	 * @param z
	 *            an <code>int</code> value
	 * @param s
	 *            a <code>String</code> value
	 */
	public void sendPlayerNotes(int x, int y, int z, String s) {
		clientTalker.sendPlayerNotes(x, y, z, s);
	}

	/**
	 * Describe <code>updateMap</code> method here.
	 * 
	 * @param x
	 *            an <code>int</code> value
	 * @param y
	 *            an <code>int</code> value
	 * @param z
	 *            an <code>int</code> value
	 */
	public void updateMap(int x, int y, int z) {
		clientTalker.updateMap(x, y, z);
	}

	/**
	 * Describe <code>showImportSoundDialog</code> method here.
	 * 
	 */
	public void showImportSoundDialog() {
		sid = new SoundImportDialog(this);
	}

	/**
	 * Describe <code>showImportImageDialog</code> method here.
	 * 
	 */
	public void showImportImageDialog() {
		isd = new ImageSelectionDialog(this);
	}

	/**
	 * Describe <code>showImportImageDialog</code> method here.
	 * 
	 */
	public void showCreateEncounterDialog() {
		ced = new CreateEncounterDialog(this);
	}

	/**
	 * Describe <code>addGroupSpell</code> method here.
	 * 
	 * @param si
	 *            a <code>StatusItem</code> value
	 * @param affected
	 *            an <code>int</code> value
	 */
	public void addGroupSpell(StatusItem si, int affected) {
		//groupStatusPanel.addGroupSpell(si, affected);
		//clientTalker.updatePlayerMod(groupStatusPanel.getPartyMod().toString());
		//clientTalker.updateMonsterMod(groupStatusPanel.getMonsterMod()
		//		.toString());
	}

	/**
	 * Describe <code>clearGroupSpells</code> method here.
	 * 
	 */
	public void clearGroupSpells() {
		//groupStatusPanel.clearGroupSpells();
	}

	/**
	 * Describe <code>newRound</code> method here.
	 * 
	 */
	public void newRound() {

		int tabCount = initListPane.getTabCount();
		if (currentGroup != tabCount - 1) {
			currentGroup++;
			
			initListPane.setSelectedIndex(currentGroup);
			setTabIndex(tabIndex + 1);
			getList().setListIndex(0);
			playSound(getSound("switch parties"));
		} else {
			if (tabCount > 0) {
				setTabIndex(0);
				initListPane.setSelectedIndex(0);
			}
			getList().setListIndex(0);

			roundCounterWidget.incrementRound();
			//groupStatusPanel.advanceStatus();
			//clientTalker.updatePlayerMod(groupStatusPanel.getPartyMod()
			//		.toString());
			//clientTalker.updateMonsterMod(groupStatusPanel.getMonsterMod()
			//		.toString());
			clientTalker.updateRound(roundCounterWidget.getRound());
			sendSound("new round");
			if (bpd != null) {
				updateBattleProgress("Starting round "
						+ roundCounterWidget.getRound());
			}
		}

		//Vector<Participant> v = getEngine().getSortedList();
		//clientTalker.sendData(v);
		// getList().setListIndex(0);
		clientTalker.updatePosition(0);
		if (getList().processListItem(0)) {
			getList().advanceList();
		}
		//System.gc();
	}

	public String getCurrentParticipantName() {
		int currentIdx = getList().getListIndex();
		Participant p = (Participant) (getEngine().getSortedList()
				.get(currentIdx));
		return p.getName();
	}

	public void editRoundInfo() {
		Participant p = (Participant) (getEngine().getSortedList()
				.get(getList().getSelectedIndex()));
		if (!p.getPType().equalsIgnoreCase("Monster")) {
			@SuppressWarnings("unused")
			RoundInfoDialog rid = new RoundInfoDialog(this, (DCharacter) p);
		}
	}

	public void triggerAdvanceUpdate() {

		// if there are no participants, bag out.
		if (getEngine().getSortedList().size() == 0) {
			return;
		}

		// get the currently marked participant on the list - not the one
		// highlighted, necessarily, but the one that is marked as "active".
		int currentIdx = getList().getListIndex();
		Participant p = (Participant) (getEngine().getSortedList()
				.get(currentIdx));

		// get the amount of damage done since the last "advance" of the list.
		int dmg = getHitSheet().getCurrentDamage();

		// if we're doing this for a player, calculate stats.
		if (p.getPType() != null && !p.getPType().equalsIgnoreCase("MONSTER") && !p.getPType().equalsIgnoreCase("Opponent")) {
			DCharacter dc = (DCharacter) p;
			BattleRound br = new BattleRound();

			br.setName(dc.getName());
			br.setPlayerId("" + dc.getID());
			br.setRoundNumber(roundCounterWidget.getRound());
			br.setStatus((Status) dc.getStatus().clone());
			br.setDamage(dmg);
			br.setKills(getList().getKills());
			br.setStuns(getList().getStuns());
			br.setCrits(csp.getCrits());
			br.setFumbles(csp.getFumbles());
			br.setDoubleDamages(csp.getDoubles());
			br.setNumHits(getHitSheet().getCurrentHits());
			br.setMaxDamage(getHitSheet().getMaxDamage());
			br.setNumAttacks(csp.getAttacks());

			
			if (bpd != null) {
				bpd.addMessage("RECORDING ROUND");
				bpd.addMessage(br.toString());
			}
			getBattle().addAttack(br);
			
			// check to see if attacks match up
			if ((!dc.getStatus().isStunned() && !dc.isDead()) && csp.getAttacks() < 1
					&& showAttackWarnings) {
				
				//@SuppressWarnings("unused")
				//MessageDialog md = new MessageDialog("Alert", dc.getName()
				//		+ " was not marked as attacking!  Did you forget?  You can enter the number of attacks below.", this);		
				@SuppressWarnings("unused") RoundInfoDialog rid = new RoundInfoDialog(this, (DCharacter) p);		
			}
		}

		// reset the counters for the next participant
		getHitSheet().resetStats();
		getList().resetStats();
		csp.resetStats();
	}

	public void setAttackCount(int i){
		csp.setAttacks(i);
	}
	
	public CritStatPanel getCritStatPanel() {
		return csp;
	}

	public void loadBattle() {
		readFromFile("saves\\OldSave.ser");
	}

	public void readFromFile() {
		readFromFile("saves\\SaveFile.ser");
	}

	/**
	 * Describe <code>readFromFile</code> method here.
	 * 
	 */
	@SuppressWarnings( { "unchecked" })
	public void readFromFile(String file) {
		int index = 0;
		try {
			logger.log("Opening File Streams");

			FileInputStream istream = new FileInputStream(file);
			ObjectInputStream p = new ObjectInputStream(istream);

			logger.log("Getting state info");
			ServerState ss = (ServerState) p.readObject();
			roundCounterWidget.setRound(ss.getRound());
			setCurrentGroup(ss.getCurrentGroup());
			int numGroups = ss.getEngineSize();

			logger.log("setting list index " + index);
			oldGroup = ss.getOldGroup();
			party = ss.getParty();
			tabIndex = ss.getTabIndex();
			index = ss.getListIndex();

			logger.log("reading engine info");
			engineVector = new Vector<InitCheckEngine>();
			for (int i = 0; i < numGroups; i++) {

				InitCheckEngine ice = (InitCheckEngine) p.readObject();
				engineVector.add(ice);
				InitList il = new InitList(this);
				il.setListData(ice.getSortedList());
				initListVector.add(il);

				Vector<MonsterGroup> monsterGroups = ice.getMonsterGroups();
				
				Vector<Participant> monsters = ice.getMonsters();
				for (int j = 0; j < monsterGroups.size(); j++) {
					String mg = monsterGroups.get(j).serverDisplayFormat();
					
					for (int k = 0; k < monsters.size(); k++) {
						Participant par = (Participant) monsters.get(k);
						if (par.getPType().equalsIgnoreCase("Monster")) {
							Monster m = (Monster) monsters.get(k);

							
							if (mg.startsWith(m.getBaseName())) {
								addToMonsterInfoPanel(m);
								k = monsters.size();
							}
						}
					}
				}
			}

			// set up the hitsheet(s) of monsters. We can't serialize the
			// hitsheets directly, because of the callback to InitServer.
			// so, we serialize the components and rebuild.
			logger.log("reading hitsheet info");
			hitSheetVector = new Vector<MonsterHitSheet>();
			for (int i = 0; i < numGroups; i++) {
				Vector<String> columns = (Vector<String>) p.readObject();
			
				Vector<Vector<Object>> rowData = (Vector<Vector<Object>>) p.readObject();
				HitSheetModel hsm = new HitSheetModel(rowData, columns);
				Vector<Participant> monsters = (Vector<Participant>) p.readObject();
				MonsterHitSheet mhs = new MonsterHitSheet(this, hsm, monsters);

				hitSheetVector.add(mhs);
			}

			// load the battle information
			battleVector = new Vector<Battle>();

			for (int i = 0; i < numGroups; i++) {
				
				battleVector.add((Battle) p.readObject());
				
			}
			
			// load the monster info
			Vector<Encounter> ve = new Vector<Encounter>();
			ve.add(getEngine().getEncounter());
			encounterPanel.setModel(ve);
		
			
			istream.close();

		} catch (Exception e) {
			logger.log("Error " + e);
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog(getFrame(),
					"Error Loading Saved Data",
					"The following error occurred while loading saved game data : "
							+ e);

		}

		// populate the list with the current character set.
		getList().setListData(getEngine().getSortedList());

		// set the initial list index. This will highlight the current
		// character, in the case that there were saved characters.
		getList().setListIndex(index);

		dms.setChars(getEngine().getCharacters());
		
		// calculate the party stats
		partyStats.calcStats(getEngine().getDB().getCharacters());
		partyStatPanel.setPartyStats(partyStats);
	}

	/**
	 * This method is responsible for checking for a saved state on startup. If
	 * it detects a backup file (created by closing the program abnormally, or
	 * explicitly saving the state on shutdown) it will prompt the user to
	 * either load or delete the saved state.
	 * 
	 */
	public void checkForBackup() {
		File ft = new File("saves\\SaveFile.ser");
		if (ft.length() > 0) {
			String mesg = "A backup file was found.  Do you wish to load "
					+ "the information from that session?  Answering no will "
					+ "delete the file.";

			int answer = JOptionPane.showConfirmDialog(null, mesg,
					"Load Backup File?", JOptionPane.YES_NO_OPTION);

			if (answer == JOptionPane.YES_OPTION) {
				readFromFile();
			} else {
				initListVector.add(new InitList(this));
				engineVector.add(new InitCheckEngine());
				hitSheetVector.add(new MonsterHitSheet(this, currentGroup,
						backgroundImage));
				battleVector.add(new Battle(""));
				ft.renameTo(new File("saves\\OldSave.ser"));
				// for some reason, the rename creates the new file, but does
				// not delete the old. So, clean it up.
				File fd = new File("saves\\SaveFile.ser");
				fd.delete();

			}
		} else {
			initListVector.add(new InitList(this));
			engineVector.add(new InitCheckEngine());
			hitSheetVector.add(new MonsterHitSheet(this, currentGroup,
					backgroundImage));
			battleVector.add(new Battle(""));
		}
	}

	/**
	 * Describe <code>backupToFile</code> method here.
	 * 
	 */
	public void backupToFile() {

		// delete the saved state file if it already exists.
		File ft = new File("saves\\SaveFile.ser");
		if (ft.length() > 0) {
			ft.delete();
		}

		// assemble the state variables
		ServerState ss = new ServerState();
		ss.setRound(roundCounterWidget.getRound());
		ss.setListIndex(getList().getListIndex());
		ss.setOldGroup(oldGroup);
		ss.setCurrentGroup(currentGroup);
		ss.setEngineSize(engineVector.size());
		ss.setParty(party);
		ss.setTabIndex(tabIndex);

		// write out to file
		try {
			FileOutputStream ostream = new FileOutputStream(
					"saves\\SaveFile.ser");
			ObjectOutputStream p = new ObjectOutputStream(ostream);
			logger.log("Saving Server State");
			p.writeObject(ss);

			logger.log("Saving Engine");
			// write out the player groups
			for (int i = 0; i < engineVector.size(); i++) {
				p.writeObject(engineVector.get(i));
			}

			logger.log("Saving Hit Sheet");
			// write out the monster hit sheets
			for (int i = 0; i < engineVector.size(); i++) {
				MonsterHitSheet mhs = hitSheetVector.get(i);

				p.writeObject(mhs.getColumns());
				p.writeObject(mhs.getRowData());
				p.writeObject(mhs.getMonsters());

			}

			logger.log("Saving Battles");
			// write out the battles
			for (int i = 0; i < engineVector.size(); i++) {
				p.writeObject(battleVector.get(i));
			}

			
			
			p.flush();
			ostream.close();
			
			// save the map
			if (map.getFileName() != null) {
				map.saveDungeon(map.getFileName());
			}
			
			
			
			
		} catch (java.io.IOException e) {
			logger.log(e.toString());
		}

	}

	/**
	 * Describe <code>cleanup</code> method here.
	 * 
	 */
	public void cleanup() {
		File ft = new File("saves\\SaveFile.ser");

		String mesg = "Do you want to save the state for next game?";

		int answer = JOptionPane.showConfirmDialog(null, mesg, "Save State?",
				JOptionPane.YES_NO_OPTION);

		if (answer == JOptionPane.YES_OPTION) {
			backupToFile();
		} else {
			ft.renameTo(new File("saves\\OldSave.ser"));
			// for some reason, the rename creates the new file, but does
			// not delete the old. So, clean it up.
			File fd = new File("saves\\SaveFile.ser");
			fd.delete();

		}
		playShutDownSound();
		saveMap();

	}

	public CampaignNotesPanel getCampaignNotesPanel(){
		return campaignNotesPanel;
	}
	
	
	
	public void alertToNetworkError(String e) {
		// ignore the connection reset error that we get when a client
		// disconnects
		if (e.indexOf("Connection reset") < 0) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog(getFrame(), "Network Error", e);
		}
	}

	/**
	 * Describe <code>spellAlert</code> method here.
	 * 
	 * @param i
	 *            an <code>int</code> value
	 */
	public void spellAlert(int i) {
		if (i == 0) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog(getFrame(),
					"Monster Spell Has Ended", "One or more of the monster"
							+ " spells has ended");
		} else {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog(getFrame(),
					"Player Spell Has Ended", "One or more of the player"
							+ " spells has ended");
		}
	}

	/**
	 * Describe <code>showSpellDialog</code> method here.
	 * 
	 */
	public void showSpellDialog() {
		spellDialog = new SpellDialog(this);
		spellDialog.setVisible(true);
	}

	/**
	 * Describe <code>showIndividSpellDialog</code> method here.
	 * 
	 */
	public void showIndividSpellDialog() {
		iSpellDialog = new IndividSpellDialog(this);
		iSpellDialog.setVisible(true);
	}

	/**
	 * Describe <code>showPlayerDialog</code> method here.
	 * 
	 */
	public void showPlayerDialog() {
		logger.log("Showing dialog");
		pDlg = new PlayerDialog(this, getEngine().getCharacters());
		pDlg.setLocationRelativeTo(null);
		// pDlg.setLocation(100, 100);
		pDlg.setVisible(true);

	}

	
	/**
	 * Describe <code>editPlayers</code> method here.
	 * 
	 */
	public void editPlayers() {
		// this is disabled, because editing of players should only be done
		// through the manager now.
	}

	/**
	 * Describe <code>editMonsters</code> method here.
	 * 
	 */
	public void editMonsters() {
		MonsterDialog mDlg2 = new MonsterDialog(this);
		mDlg2.setLocationRelativeTo(null);
		mDlg2.setVisible(true);
	}

	/**
	 * Describe <code>removeFromAltList</code> method here.
	 * 
	 * @param v
	 *            a <code>Vector</code> value
	 */
	public void removeFromAltList() {
		if (getAltList() != null) {
			getAltList().removeMonsters();
		}
	}

	/**
	 * Describe <code>removeFromHitSheet</code> method here.
	 * 
	 * @param v
	 *            a <code>Vector</code> value
	 */
	public void removeFromHitSheet(Vector<Participant> v) {
		getHitSheet().removeMonsters(v);
		refreshHitSheet();
	}

	/**
	 * Describe <code>refreshAll</code> method here.
	 * 
	 */
	public void refreshAll() {
		refreshHitSheet();
	}

	public void repaintScreen() {
		contents.paintImmediately(0, 0, new Double(contents.getSize()
				.getWidth()).intValue(), new Double(contents.getSize()
				.getHeight()).intValue());
	}

	/**
	 * Describe <code>addToHitSheet</code> method here.
	 * 
	 * @param v
	 *            a <code>Vector</code> value
	 */
	public void addToHitSheet(Vector<Participant> v) {
		if (getHitSheet() != null) {
		
			getHitSheet().addMonsters(v);
			refreshHitSheet();
		}
	}
	
	
	public void updateBattleProgress(String s) {
		if (bpd == null) {
			bpd = new BattleProgressDialog("Battle Progress", "");
		}
		bpd.addMessage(s);
	}

	public void automateCurrentEncounter() {

		BattleSimulator bs = new BattleSimulator(this);
		if (bpd != null) {
			bpd.dispose();
			bpd = null;
		}
		bs.simulateBattle();

	}

	public int getRandom(int i) {
		int j = rnd.nextInt(i);
		return j + 1;
	}

	public void assignRandomOpponent() {
		getHitSheet().assignRandomOpponent();
	}

	/**
	 * <code>refreshHitSheet</code> Will rebuild the hitsheet when new
	 * monsters or players are added to the init list. This will make sure that
	 * both the monster list and player dropdowns are current.
	 * 
	 */
	public void refreshHitSheet() {
		if (getHitSheet() != null) {

			// the only way I found that works to have the darn thing repaint is
			// to remove it from the panel, change it, and re-add and validate.
			// you shouldn't have to do that, but that's life, I guess.
			hsPanel.remove(getHitSheet());
			HitSheetModel hsm = getHitSheet().getModel();
			hitSheetVector.set(currentGroup, new MonsterHitSheet(this, hsm));
			hsPanel.add(getHitSheet(), BorderLayout.CENTER);
			frame.validate();
		}
	}

	/**
	 * Describe <code>changeHitSheet</code> method here.
	 * 
	 * @param old
	 *            an <code>int</code> value
	 * @param curr
	 *            an <code>int</code> value
	 */
	public void changeHitSheet(int old, int curr) {
		// the only way I found that works to have the darn thing repaint is
		// to remove it from the panel, change it, and re-add and validate.
		// you shouldn't have to do that, but that's life, I guess.

		

		hsPanel.remove(getHitSheet(old));
		HitSheetModel hsm = getHitSheet().getModel();
		hitSheetVector.set(currentGroup, new MonsterHitSheet(this, hsm));
		hsPanel.add(getHitSheet(), BorderLayout.CENTER);
		frame.validate();
	}

	/**
	 * Describe <code>getAltList</code> method here.
	 * 
	 * @return an <code>AlternateList</code> value
	 */
	public AlternateList getAltList() {
		return (AlternateList) altListVector.get(0);
	}

	/**
	 * Describe <code>refreshHitSheet</code> method here.
	 * 
	 */
	public void refreshAltList() {
		if (getAltList() != null) {
			// refresh the list
			getAltList().refreshMonsters();
			// repaint the screen
			wrapperPanel.invalidate();
			frame.validate();
		}
	}

	/**
	 * Describe <code>resetHitSheet</code> method here.
	 * 
	 */
	public void resetHitSheet() {
		logger.log("RESETTING HIT SHEET");
		if (getHitSheet() != null) {
			getHitSheet().removeMonsters();
			refreshHitSheet();
		}
	}

	/**
	 * Describe <code>showStatusDialog</code> method here.
	 * 
	 */
	public void showStatusDialog() {
		
		int idx = getList().getSelectedIndex();
		if (idx < 0)
			return;

		Vector<Participant> v = getEngine().getSortedList();
		if (v.get(idx).getPType().equalsIgnoreCase("Monster")) {
			return;
		}
		DCharacter p = (DCharacter) v.get(idx);
		frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		PlayerStatDialog d = new PlayerStatDialog(this, p, idx, PlayerStatDialog.VIEW_ONLY);
		d.setSkin(skin);
		d.pack();
		d.setLocationRelativeTo(null);
		d.setVisible(true);
		frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * Describe <code>showSendMessageDialog</code> method here.
	 * 
	 */
	public void showSendMessageDialog() {
		if (smd == null) {
			smd = new SendMessageDialog(this, "Send Message To Players", "");
		} else {
			smd.clear();
			smd.setVisible(true);
		}
	}

	/**
	 * Describe <code>showMonsterDialog</code> method here.
	 * 
	 */
	public void showMonsterDialog() {
		MonsterDialog d = new MonsterDialog(this);
		d.setLocationRelativeTo(null);
		d.setVisible(true);
	}

	/**
	 * Describe <code>removeMonsters</code> method here.
	 * 
	 */
	public void removeMonsters() {
		@SuppressWarnings("unused")
		RemoveMonsterDialog rmd = new RemoveMonsterDialog(this,
				"Remove Monsters");
	}

	/**
	 * Describe <code>removePlayers</code> method here.
	 * 
	 */
	public void removePlayers() {
		@SuppressWarnings("unused")
		RemovePlayerDialog rpd = new RemovePlayerDialog(this, "Remove Players");
	}

	/**
	 * Describe <code>removeAllMonsters</code> method here.
	 * 
	 */
	public void removeAllMonsters() {
		logger.log("REMOVING ALL MONSTERS");
		
		resetHitSheet();
		
		getEngine().clearMonsters();
		
		removeFromAltList();
		
		Vector<Participant> v = getEngine().getSortedList();
		getList().setListData(v);
		wrapperPanel.invalidate();
		if (clientTalker != null) {
			clientTalker.sendData(v);
		}

		// remove the info button
		monsterInfoPanel.removeMonsters();
		monsterInfoPanel.invalidate();
		encounterPanel.setModel(new Vector<Encounter>());
		frame.validate();

	}

	/**
	 * Describe <code>removePlayerSet</code> method here.
	 * 
	 * @param i
	 *            an <code>int</code> value
	 */
	public void removePlayerSet(int i) {

		Vector<Participant> mg = getEngine().getPlayerGroup(i);

		getEngine().removeParticipants(mg);
		getEngine().clearPlayers(i);

		Vector<Participant> v = getEngine().getSortedList();

		getList().setListData(v);

		if (clientTalker != null) {
			clientTalker.sendData(v);
			
		}

		refreshHitSheet();
	}

	/**
	 * Describe <code>removeMonsterSet</code> method here.
	 * 
	 * @param i
	 *            an <code>int</code> value
	 */
	public void removeMonsterSet(int i) {

		// get the group of monsters to remove
		Vector<Participant> mg = getEngine().getMonsterGroup(i);

		// remove them from the engine
		getEngine().removeParticipants(mg);

		// and the hit sheet
		removeFromHitSheet(mg);
		
		// and the alternate view list
		removeFromAltList();
		
		// and rebuild the list
		getEngine().clearMonsters(i);
		Vector<Participant> v = getEngine().getSortedList();
		getList().setListData(v);

		// send the updated list to the client
		if (clientTalker != null) {
			clientTalker.sendData(v);
		}

		// remove the info button
		monsterInfoPanel.removeMonster(i);

		// redraw the screen
		monsterInfoPanel.invalidate();
		
		Vector<Encounter> ve = new Vector<Encounter>();
		ve.add(getEngine().getEncounter());
		encounterPanel.setModel(ve);
		
		frame.validate();
	}

	/**
	 * Describe <code>strokeTheDM</code> method here.
	 * 
	 */
	public void strokeTheDM() {
		@SuppressWarnings("unused")
		DMStrokeDialog dlg = new DMStrokeDialog(this, "Wow!");
	}

	/**
	 * Describe <code>sendCokeRequest</code> method here.
	 * 
	 */
	public void sendCokeRequest() {
		clientTalker.sendCokeRequest();
		@SuppressWarnings("unused")
		MessageDialog dlg = new MessageDialog(getFrame(), "Request Sent",
				"Sent Coke Request.");
	}

	/**
	 * Describe <code>sendSnackRequest</code> method here.
	 * 
	 */
	public void sendSnackRequest() {
		clientTalker.sendSnackRequest();
		@SuppressWarnings("unused")
		MessageDialog dlg = new MessageDialog(getFrame(), "Request Sent",
				"Sent Snack Request.");
	}

	/**
	 * Describe <code>displayMessage</code> method here.
	 * 
	 * @param s
	 *            a <code>String</code> value
	 */
	public void displayMessage(String s) {
		@SuppressWarnings("unused")
		MessageDialog dlg = new MessageDialog("Message For You, Sir", s);
	}

	/**
	 * Describe <code>setSelectedMonster</code> method here.
	 * 
	 * @param o
	 *            an <code>Object</code> value
	 */
	public void setSelectedMonster(Object o) {
		getList().setSelectedValue(o, true);
	}

	/**
	 * Describe <code>getMonsterGroups</code> method here.
	 * 
	 * @return a <code>Vector</code> value
	 */
	public Vector<MonsterGroup> getMonsterGroups() {
		return getEngine().getMonsterGroups();
	}

	/**
	 * Describe <code>getPlayerGroups</code> method here.
	 * 
	 * @return a <code>Vector</code> value
	 */
	public Vector<String> getPlayerGroups() {
		return getEngine().getPlayerGroups();
	}

	/**
	 * <code>toggleClient</code> switches the client access methods on and
	 * off. If it is set to "disabled", then no data will be sent to the client.
	 */
	public void toggleClient() {
		clientTalker.toggleAccess();
	}

	/**
	 * Describe <code>runEncounter</code> method here.
	 * 
	 * @param m
	 *            a <code>Vector</code> value
	 * @param newEncounter
	 *            a <code>boolean</code> value
	 */
	public void runEncounter(Encounter e, boolean newEncounter) {
		if (newEncounter) {
			removeAllMonsters();
		}
		Vector<MonsterGroup> m = e.getMonsterGroups();
		for (int i = 0; i < m.size(); i++) {
			MonsterGroup mg = (MonsterGroup) m.get(i);
			setMonsters("" + mg.getCount(), (Monster) mg.getM().clone(), true);
		}
		logger.log("Adding Opponents");
		addOpponents(e.getNpcOpponents());
		
		showListTab();
	}

	public void saveEncounter(Encounter e){
		
	}
	
	/**
	 * Give the focus to the initList tab (as opposed to the map or chat tabs).
	 * 
	 */
	public void showListTab() {
		tabbedPane.setSelectedIndex(0);
		clientTalker.sendTabChange("LISTTAB");
	}

	public void sendPictures() {
		Vector<Monster> v = getEngine().getMonsterPictures();
		for (int i = 0; i < v.size(); i++) {
			Monster m = (Monster) v.get(i);
			if (clientTalker != null) {
				clientTalker.sendMonsterPicture(m.getPicture(), m.getName(), ""
						+ m.getID());
			}
		}
	}

	public void addOpponents(Vector<DCharacter> opponents) {
		
		
			getEngine().addOpponents(opponents);
			// cast to a participant vector for hitsheet
			Vector<Participant> hsv = new Vector<Participant>();
			hsv.addAll(opponents);
			addToHitSheet(hsv);

			// refresh the monster and player finder list with the new info
			refreshAltList();

			// refresh the main list
			Vector<Participant> v = getEngine().getSortedList();
			getList().setListData(v);
			getList().setSelectedIndex(0);

			if (clientTalker != null) {
				clientTalker.sendData(v);
			}
			
	}
	
	/**
	 * Describe <code>setMonsters</code> method here.
	 * 
	 * @param num
	 *            a <code>String</code> value
	 * @param m
	 *            a <code>Monster</code> value
	 * @param add
	 *            a <code>boolean</code> value
	 */
	public void setMonsters(String num, Monster m, boolean add) {

		// check for the picture of the monster. If there is no
		// picture associated with it, set the picture to be the default
		// picture.
		String picture = m.getPicture();
		if (picture == null || picture.equals("") || picture.equals("NONE")) {
			picture = "images\\qmark.jpg";
		}

		// either add the monsters to the current set, or replace the
		// current set with the new monsters, depending on the flag
		if (add) {
			getEngine().addMonsters(Integer.parseInt(num), m);
			addToHitSheet(getEngine().getCurrentGroup());
		} else {
			getEngine().createMonsters(Integer.parseInt(num), m);
			addToHitSheet(getEngine().getCurrentGroup());
		}

		// refresh the monster and player finder list with the new info
		refreshAltList();

		// refresh the main list
		Vector<Participant> v = getEngine().getSortedList();
		getList().setListData(v);
		getList().setSelectedIndex(0);

		// if there are clients connected, send the new list and the
		// picture of the monster.
		if (clientTalker != null) {
			clientTalker.sendData(v);
			clientTalker.sendMonsterPicture(picture, m.getName(), ""
					+ m.getID());
		}

		Vector<Encounter> ve = new Vector<Encounter>();
		ve.add(getEngine().getEncounter());
		encounterPanel.setModel(ve);
		
		// add the monster button to the monster info panel
		monsterInfoPanel.addMonster(m);

		// redraw the screen
		monsterInfoPanel.invalidate();
		frame.validate();
	}

	/**
	 * Adds a monster to the current group widgets. This is primarily used by
	 * the "transfer" function between groups to take a particular monster
	 * instance and insert it into the current group.
	 * 
	 * @param m
	 *            the <code>Monster</code> to add.
	 */
	public void addMonster(Participant m) {
		getEngine().addMonster(m);
		Vector<Participant> v1 = new Vector<Participant>();
		v1.add(m);
		addToHitSheet(v1);
		refreshAltList();
		Vector<Participant> v = getEngine().getSortedList();
		getList().setListData(v);
		clientTalker.sendData(v);
	}

	/**
	 * This updates the hit points of a particular monster. This is used to keep
	 * the engine dataset current with changes applied in the hitsheet, so that
	 * if a monster is transferred from one group to another, he will have the
	 * correct hit points.
	 * 
	 * @param name
	 *            a <code>String</code> value
	 * @param hp
	 *            an <code>int</code> value
	 */
	public void setMonsterHP(String name, int hp) {
		Vector<Participant> v = getEngine().getSortedList();
		for (int i = 0; i < v.size(); i++) {
			Participant p = (Participant) v.get(i);
			if (p.getName().equals(name)) {
				p.setCurrentHitPoints(hp);
				break;
			}
		}
	}

	public int getMonsterHP(String name) {
		int hp = 0;
		Vector<Participant> v = getEngine().getSortedList();
		for (int i = 0; i < v.size(); i++) {
			Participant p = (Participant) v.get(i);
			if (p.getName().equals(name)) {
				return p.getHP();

			}
		}
		return hp;
	}

	/**
	 * This marks a particular monster as dead in the current monster list. this
	 * is called by the hit sheet, when the monsters hit points drop below zero.
	 * It's a convienience function to keep the user from having to mark the
	 * hitpoints, then also mark the monster dead.
	 * 
	 * @param name
	 *            a <code>String</code> value
	 */
	public void killMonster(String name) {
		if (bpd != null) {
			updateBattleProgress(name + " Is dead.");
		}
		Vector<Participant> v = getEngine().getSortedList();
		for (int i = 0; i < v.size(); i++) {
			Participant p = (Participant) v.get(i);
			if (p.getName().equals(name)) {
				
				getList().setSelectedIndex(i);
				getList().markListItemDead();

				break;
			}

		}
	}

	/**
	 * Just like killMonster, but for stuns.
	 * 
	 * @param name
	 *            a <code>String</code> value
	 */
	public void stunMonster(String name, boolean increment) {
		if (bpd != null) {
			updateBattleProgress(name + " Is stunned.");
		}
		Vector<Participant> v = getEngine().getSortedList();
		for (int i = 0; i < v.size(); i++) {
			Participant p = (Participant) v.get(i);
			if (p.getName().trim().equals(name.trim())) {
				if (increment || !p.getStatus().isStunned()) {
					getList().setSelectedIndex(i);
					getList().markListItemStunned();
				}
				break;
			}
		}
	}

	/**
	 * Like stunMonster, but prevents Monsters from being stunned, so that stuns
	 * initiated from a client won't affect monsters, like that sneaky Cort
	 * tries to do.
	 * 
	 * @param name
	 *            a <code>String</code> value
	 */
	public void stunPlayer(String name, boolean increment) {
		Vector<Participant> v = getEngine().getSortedList();
		for (int i = 0; i < v.size(); i++) {
			Participant p = (Participant) v.get(i);
			if (p.getName().trim().equals(name.trim())) {
				if (p.getPType().equalsIgnoreCase("Monster")) {
					return;
				}
				if (increment || !p.getStatus().isStunned()) {
					getList().setSelectedIndex(i);
					getList().markListItemStunned();
				}
				break;
			}
		}
	}

	public void unstunPlayer(String name) {
		Vector<Participant> v = getEngine().getSortedList();
		for (int i = 0; i < v.size(); i++) {
			Participant p = (Participant) v.get(i);
			if (p.getName().trim().equals(name.trim())) {
				if (p.getPType().equalsIgnoreCase("Monster")) {
					return;
				}

				getList().setSelectedIndex(i);
				getList().markListItemUnStunned();

				break;
			}
		}
	}

	public void recordPlayerDeath(String name, int id, String monsterName,
			int monsterId) {

		MonsterKills mk = new MonsterKills();
		mk.setMonsterName(monsterName);
		mk.setMonsterId("" + monsterId);
		mk.setPlayerName(name);
		mk.setPlayerId("" + id);
		mk.setBattleId(getBattle().getBattleId());
		// add the kill to the battle. It will get written out to
		// the database when the battle is over.
		getBattle().addMonsterKill(mk);
	}

	public void recordPlayerDeath(String name, int id) {
		// if more than one type of monster is in the battle,
		// ask which one killed the player so that it can
		// be added to the "nemesis" stat calculation.
		if (monsterInfoPanel.getMonsterButtons().size() > 1) {
			@SuppressWarnings("unused")
			WhoDunnitDialog wd = new WhoDunnitDialog(this, "They Killed "
					+ name + ", Those Bastards!", monsterInfoPanel
					.getMonsterButtons(), name, id);

			return;
		}
		String monster = "Unknown";
		int monsterId = 0;
		
		if (getEngine().getMonsters().size() > 0){
			Participant m = (Participant) (getEngine().getMonsters().get(0));		
			monster = m.getName();
			id = m.getID();
		}
		recordPlayerDeath(name, id, monster, monsterId);
	}

	public void killPlayer(String name) {
		Vector<Participant> v = getEngine().getSortedList();
		for (int i = 0; i < v.size(); i++) {
			Participant p = (Participant) v.get(i);
			if (p.getName().trim().equals(name.trim())) {
				if (p.getPType().equalsIgnoreCase("Monster")) {
					return;
				}
				getList().setSelectedIndex(i);
				getList().markListItemDead();
				break;
			}
		}
	}

	public void setPlayerStatus(String name, StatusItem status) {
		Vector<Participant>v = getEngine().getSortedList();
		for (int i = 0; i < v.size(); i++) {
			Participant p = (Participant) v.get(i);
			if (p.getName().trim().equals(name.trim())) {
				if (p.getPType().equalsIgnoreCase("Monster")) {
					return;
				}
				getList().setSelectedIndex(i);
				getList().setStatus(status);
				break;
			}
		}
	}

	public void revivePlayer(String name) {
		Vector<Participant> v = getEngine().getSortedList();
		for (int i = 0; i < v.size(); i++) {
			Participant p = (Participant) v.get(i);
			if (p.getName().trim().equals(name.trim())) {
				if (p.getPType().equalsIgnoreCase("Monster")) {
					return;
				}
				getList().setSelectedIndex(i);
				getList().markListItemAlive();

				break;
			}
		}
	}

	public void updatePlayer(String ac, String hp, String name) {
		logger.log("Trying to update "+name);
		Vector<Participant> v = getEngine().getSortedList();
		int idx = getList().getSelectedIndex();
		for (int i = 0; i < v.size(); i++) {
			Participant p = (Participant) v.get(i);
			logger.log("Checking "+p.getName());
			if (p.getName().trim().equalsIgnoreCase(name.trim())) {

				if (p.getPType() != null && p.getPType().equalsIgnoreCase("Player")) {
					logger.log("SETTING UPDATES");
					DCharacter d = (DCharacter) p;
					d.setPrecalcAc(Integer.parseInt(ac));
					d.setHP(Integer.parseInt(hp));
					logger.log("HP IN WAS "+hp);
					logger.log("SET HP TO "+d.getHP()+" "+p.getHP());
				}
				
				getList().setListData(v);
				getList().setSelectedIndex(idx);
				break;
			}
		}
	}
	
	public void damagePlayer(String name, String dmg) {
		logger.log("Got damage of " + dmg);
		Vector<Participant> v = getEngine().getSortedList();
		int idx = getList().getSelectedIndex();
		for (int i = 0; i < v.size(); i++) {
			Participant p = (Participant) v.get(i);
			if (p.getName().trim().equals(name.trim())) {

				if (p.getPType() != null && p.getPType().equalsIgnoreCase("Player")) {
					DCharacter d = (DCharacter) p;
					d.setDamageTaken(Integer.parseInt(dmg));
					d.setRoundDamage(d.getRoundDamage()+Integer.parseInt(dmg));
					d.setCurrentHitPoints(d.getCurrentHitPoints()
							- Integer.parseInt(dmg));
					logger.log("Set Hitpoints of " + name + " to "
							+ d.getCurrentHitPoints());
				}
				
				getList().setListData(v);
				getList().setSelectedIndex(idx);
				break;
			}
		}
		// redestribute the list to the clients.
		// clientTalker.sendDamage(name, dmg);
	}

	public void modStat(String name, String stat, String modStr) {
		Vector<Participant> v = getEngine().getSortedList();
		int idx = getList().getSelectedIndex();
		for (int i = 0; i < v.size(); i++) {
			Participant p = (Participant) v.get(i);
			if (p.getName().trim().equals(name.trim())) {

				if (p.getPType() == null || p.getPType().equalsIgnoreCase("Monster")) {
					return;
				}
				DCharacter dc = (DCharacter) p;
				int mod = Integer.parseInt(modStr);

				if (stat.equals("STR")) {
					dc.setStrMod(dc.getStrMod() + mod);
				} else if (stat.equals("CON")) {
					dc.setConMod(dc.getConMod() + mod);
				} else if (stat.equals("DEX")) {
					dc.setDexMod(dc.getDexMod() + mod);
				} else if (stat.equals("CHA")) {
					dc.setChaMod(dc.getChaMod() + mod);
				} else if (stat.equals("WIS")) {
					dc.setWisMod(dc.getWisMod() + mod);
				} else if (stat.equals("INT")) {
					dc.setIntMod(dc.getIntMod() + mod);
				}
				// recalcuate the AC, because a dex change will
				// change it, or a charisma change with divine shield.
				dc.getArmorCalc();
				// recalculate the HP, because a con mod will change
				// the hit point total
				dc.getHpCalc();
				getList().setListData(v);
				getList().setSelectedIndex(idx);
				break;
			}
		}
		// redestribute the list to the clients.
		// clientTalker.sendData(v);
	}

	public void healPlayer(String name, String dmg) {
		Vector<Participant> v = getEngine().getSortedList();
		int idx = getList().getSelectedIndex();
		for (int i = 0; i < v.size(); i++) {
			Participant p = (Participant) v.get(i);
			if (p.getName().trim().equals(name.trim())) {

				if (p.getPType() == null || p.getPType().equalsIgnoreCase("Monster")) {
					return;
				}
				DCharacter d = (DCharacter) p;
				d.setCurrentHitPoints(Integer.parseInt(dmg));
				if (d.isDead() && (d.getCurrentHitPoints() > 0)) {
					d.removeStatusItem("Dead");
					d.setDead("FALSE");
				}
				getList().setListData(v);
				getList().setSelectedIndex(idx);
				break;
			}
		}
		// redestribute the list to the clients.
		//clientTalker.sendHeal(name, dmg);
	}

	public void addPartyXP(String name, String xp) {
		Vector<Participant> v = getEngine().getSortedList();
		
		for (int i = 0; i < v.size(); i++) {
			Participant p = (Participant) v.get(i);
			if (p.getName().trim().equals(name.trim())) {

				if (p.getPType() == null || p.getPType().equalsIgnoreCase("Monster")) {
					return;
				}
				DCharacter d = (DCharacter) p;
				
				InitDBC db = new InitDBC();
				db.updatePartyXp(d.getPartyId(), xp);
				break;
			}
		}
		
	}
	
	public void addPlayerXP() {
		List<Object> selChars = getList().getSelectedValuesList();
		if (!selChars.isEmpty()) {
			InitDBC db = new InitDBC();
			String xpStr = JOptionPane
					.showInputDialog("How many XP do you want to add to the selected players?");
			try {
				int xp = Integer.parseInt(xpStr);
				for (Object o:selChars) {
					DCharacter curr = (DCharacter) o;
					int currXp = curr.getXp();
					int newXp = currXp + xp;
					curr.setXp(newXp);
					db.updatePlayer(curr);
				}
			} catch (NumberFormatException nfe) {
				@SuppressWarnings("unused") MessageDialog md = new MessageDialog("Error",
						"The XP number must be an integer.");
			}
		}
		
		
	}
	
	public void addPlayerXP(String name, String xp) {
		Vector<Participant> v = getEngine().getSortedList();
		
		for (int i = 0; i < v.size(); i++) {
			Participant p = (Participant) v.get(i);
			if (p.getName().trim().equals(name.trim())) {

				if (p.getPType() == null || p.getPType().equalsIgnoreCase("Monster")) {
					return;
				}
				DCharacter d = (DCharacter) p;
				d.setXp(d.getXp()+Integer.parseInt(xp));
				InitDBC db = new InitDBC();
				db.updatePlayer(d);
				break;
			}
		}
		
	}

	
	

	/**
	 * Describe <code>addToMonsterInfoPanel</code> method here.
	 * 
	 * @param m
	 *            a <code>Monster</code> value
	 */
	public void addToMonsterInfoPanel(Monster m) {
	
		// add the monster button to the monster info panel
		monsterInfoPanel.addMonster(m);

		// redraw the screen
		monsterInfoPanel.invalidate();
		frame.validate();
	}

	/**
	 * This sends the current initiative list to the client, and also sends the
	 * start battle sound.
	 * 
	 */
	public void sendClientList() {
		Vector<Participant> v = getEngine().getSortedList();

		if (clientTalker != null) {
			clientTalker.sendData(v);
			sendSound("startbattle");
		}
	}

	/**
	 * Describe <code>setPlayers</code> method here.
	 * 
	 */
	public void setPlayers() {
		Vector<Participant> v = getEngine().getSortedList();
		partyStats.calcStats(getEngine().getDB().getCharacters());
		partyStatPanel.setPartyStats(partyStats);
		getList().setListData(v);
		getList().setSelectedIndex(0);
		refreshHitSheet();
		if (clientTalker != null) {
			clientTalker.sendData(v);
			
		}
	}

	/**
	 * Describe <code>showMonsterInfoDialog</code> method here.
	 * 
	 * @param m
	 *            a <code>Monster</code> value
	 */
	public void showMonsterInfoDialog(Monster m) {
		encounterPanel.setSelectedMonster(m);
		tabbedPane.setSelectedIndex(tabbedPane.indexOfTab("Monsters"));
		
		//MonsterStatDialog msd = new MonsterStatDialog(m, null);
		//msd.pack();
		//msd.setVisible(true);
	}

	/**
	 * Describe <code>printList</code> method here.
	 * 
	 */
	public void printList() {
		getList().printList();
	}

	/**
	 * Describe <code>sendAttentionRequest</code> method here.
	 * 
	 */
	public void sendAttentionRequest() {
		sendSound("attention");
	}

	/**
	 * Describe <code>doAutoRoll</code> method here.
	 * 
	 */
	public void doAutoRoll() {
		dr.notifyAutoRoll();
	}

	/**
	 * Describe <code>rollInit</code> method here.
	 * 
	 */
	public void rollInit() {
		getEngine().doInit();
		Vector<Participant> v = getEngine().getSortedList();
		getList().setListData(v);
		getList().setSelectedIndex(0);
		clientTalker.sendData(v);

	}

	/**
	 * Describe <code>getHitSheetData</code> method here.
	 * 
	 * @return a <code>Vector</code> value
	 */
	public Vector<Vector<Object>> getHitSheetData() {
		if (getHitSheet() != null) {
			return getHitSheet().getRowData();
		}
		return null;
	}

	public NetworkPopupMenu getNetworkPopupMenu(){
		return networkPopupMenu;
	}
	
	/**
	 * Describe <code>getPopupMenu</code> method here.
	 * 
	 * @return an <code>InitPopupMenu</code> value
	 */
	public InitPopupMenu getPopupMenu() {
		return rClickMenu;
	}

	/**
	 * Describe <code>sendMessage</code> method here.
	 * 
	 * @param s
	 *            a <code>String</code> value
	 */
	public void sendMessage(String s) {
		sendSound("message");
		clientTalker.sendMessage(s);
	}

	/**
	 * Describe <code>soundOn</code> method here.
	 * 
	 * @return a <code>boolean</code> value
	 */
	public boolean soundOn() {
		if (preferencesDialog == null) {
			return true;
		}
		return preferencesDialog.soundOn();
	}

	/**
	 * Describe <code>sendSound</code> method here.
	 * 
	 * @param s
	 *            a <code>String</code> value
	 */
	public void sendSound(String s) {

		clientTalker.sendSound(s);

	}

	public String getSound(String s) {
		String sound = preferencesDialog.getSound(s);
		return sound;
	}

	private void playShutDownSound() {

		// this sound gets handled specially, because when we are shutting down,
		// the app gets killed before it can finish playing the clip. So,
		// play the clip and pause for a while to make sure it finishes.
		try {
			playSound(getSound("shutdown"));
			Thread.sleep(1500);
		} catch (Exception e) {
			logger.log(e.toString());
		}
	}

	/**
	 * Describe <code>setUsedMemory</code> method here.
	 * 
	 * @param used
	 *            a <code>long</code> value
	 */
	public void setUsedMemory(long used) {
		usedMemory = used / 1000000;
		usedMemoryLabel.setText("" + usedMemory + "M/" + totalMemory + "M");
	}

	public void updateMonsterStun(String name) {
		getHitSheet().updateMonsterStun(name);
	}

	/**
	 * Describe <code>setTotalMemory</code> method here.
	 * 
	 * @param total
	 *            a <code>long</code> value
	 */
	public void setTotalMemory(long total) {
		totalMemory = total / 1000000;
	}

	/**
	 * Describe <code>setFont</code> method here.
	 * 
	 * @param f
	 *            a <code>Font</code> value
	 */
	public void setFont(Font f) {
		font = f;
	}

	/**
	 * Describe <code>isMapShowing</code> method here.
	 * 
	 * @return a <code>boolean</code> value
	 */
	public boolean isMapShowing() {
		
		if (tabbedPane != null) {

			return tabbedPane.getSelectedIndex() == tabbedPane
					.indexOfTab("Map");
		}
		return false;
	}

	public void queueClientRequest(Object o){
	
	//	crpt.queueRequest(o);
	}
	
	public void setSkin(Skin s){	
		if (!s.getName().equals(skin.getName())){
			Dimension dim = frame.getSize();
			frame.invalidate();
			
			if (!s.isUseGraphics()){				
				tabbedPane.setUI(new PlasticTabbedPaneUI());			
				tabbedPane.setForeground(Color.BLACK);				
			}else{
				//tabbedPane.setUI(new TestTabUI());
				tabbedPane.setForeground(InitColor.woodText);
			}
			
			//contents.setPaintBackground(s.isTilePanels());
			//playerList.setPaintBackground(s.isTileLists());
			
			for (int i = 0; i < tabbedPane.getTabCount(); i++){
				Component c = tabbedPane.getComponentAt(i);
				try {
					Skinnable sk = (Skinnable)c;
					sk.setSkin(s);
				}catch(ClassCastException ce){
					
				}
			}
			
	        frame.validate();
	        frame.setSize(dim);
	        skin = s;
		}
	}
	
	public void setTitle(String s){
		
	}
	
	/**
	 * Describe <code>main</code> method here.
	 * 
	 * @param args
	 *            a <code>String[]</code> value
	 */
	public static void main(String[] args) {
		
		for (String s:args){
			System.out.println(s);
		}
		
		PlasticXPLookAndFeel.setCurrentTheme(new SkyRed());
		// MetalLookAndFeel.setCurrentTheme(new InitTheme());
		try {
			//UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
			 for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			        if ("Nimbus".equals(info.getName())) {
			        	System.out.println("FOUND NIMBUS");
			            UIManager.setLookAndFeel(info.getClassName());
			            break;
			        }
			    }
			 UIManager.put("nimbusBase", InitColor.red);
			 UIManager.put("nimbusOrange", InitColor.red);
			 UIManager.put("nimbusSelection", InitColor.red);
			 UIManager.put("nimbusSelectionBackground", InitColor.red);
				UIManager.put("nimbusBlueGrey", InitColor.fadedRed);
				UIManager.put("control", InitColor.fadedRed);
				UIManager.put("TabbedPane.background", InitColor.red);
				//UIManager.put("nimbusLightBackground",InitColor.fadedRed);
				UIManager.put("ComboBox.background", InitColor.fadedRed);
		} catch (Exception e) {
			e.printStackTrace();
		}

		UIDefaults defaults = UIManager.getDefaults();

		defaults.put("ScrollBar.thumb", InitColor.red);
		defaults.put("ScrollBar.thumbHighlight", InitColor.hlight);
		defaults.put("ScrollBar.thumbLightShadow", InitColor.lhlight);
		defaults.put("ScrollBar.thumbShadow", InitColor.shadow);
		defaults.put("Button.pressed", InitColor.lightRed);
		defaults.put("Button.focus", InitColor.lightRed);
		defaults.put("ComboBox.selectedBackground", InitColor.red);
		defaults.put("TabbedPane.tabsOpaque", false);
		defaults.put("TabbedPane.contentBorderInsets", new Insets(15,15,15,15));
		
		// Create the top-level container and add contents to it.

		final InitServer app = new InitServer(args[0]);
		app.setFrame(new JFrame("Initiative Calculator 2.0"));

		// check for a saved state
		app.checkForBackup();

		// create the main screen
		app.contents = app.createComponents();
		app.getFrame().getContentPane().add(app.contents, BorderLayout.CENTER);

		// Finish setting up the frame, and show it. Make sure that you trap the
		// window killing "x" to save things.
		app.getFrame().addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				app.cleanup();
				System.exit(0);

			}
		});

		// start the broadcast thread
		clientTalker = new ServerMessageSender(app);
		//clientTalker.start();

		// start the receive thread
		//clientListener = new ClientListenerManager(app);
		//clientListener.start();

		// start ping listener
		//ClientPingManager cpt = new ClientPingManager(app);
		//cpt.start();

		//crpt = new ClientRequestProcessorThread(app);
		//crpt.start();
		
		// start the memory tracking thread
		MemoryManagementThread memoryManager = new MemoryManagementThread(app);
		memoryManager.start();

		// load the appropriate map, if we loaded a saved game that had one.
		app.addMapPanel();
		
		app.setSkin(app.skin);
		app.getFrame().pack();

		// show the interface
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension labelSize = app.getFrame().getSize();

		if (labelSize.height > screenSize.height) {
			app.getFrame().setSize(labelSize.width, screenSize.height - 10);
		}

		app.getFrame().setLocation(
				screenSize.width / 2 - (labelSize.width / 2),
				screenSize.height / 2 - (labelSize.height / 2));

		app.getFrame().setVisible(true);

		// play the start up sound

		app.playSound(app.getSound("startup"));

		// stroke the DM's ego.
		app.strokeTheDM();
	}

	public void updateTime(int minutes) {
		clientTalker.sendTime(minutes);
		
	}
	
	public void updateTotalTime(int minutes) {
		clientTalker.sendTotalTime(minutes);
		
	}
	
	public void broadcastXpList(ArrayList<String>s, Hashtable<String, String>bonusHash, String baseXPperPlayer){
		clientTalker.sendXpList(s, bonusHash, baseXPperPlayer);
	}
	
	public void setChars(Vector<DCharacter> chars, String party){
		getEngine().getDB().addPlayers(chars);
		setParty(party);
		updateEncounter(chars);
	}
	
	public void setOpponents(Vector<DCharacter> chars){
		addOpponents(chars);
		updateEncounter(chars);
	}
	
	public void updateEncounter(Vector<DCharacter> chars){
		Vector<Participant> ev = new Vector<Participant>();
		ev.addAll(chars);
		getEngine().insertParticipants(ev);
		updateDMSheet();
		setPlayers();
		refreshHitSheet();
		backupToFile();
		repaintScreen();
	}
	
}

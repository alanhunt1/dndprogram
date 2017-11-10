package initcheck;

import initcheck.client.ClientInitList;
import initcheck.client.ClientMenuBar;
import initcheck.client.ClientPopupMenu;
import initcheck.client.PlayerDamageDialog;
import initcheck.client.PlayerDamagePanel;
import initcheck.client.PlayerProfilePanel;
import initcheck.database.BattleHistory;
import initcheck.database.BattleHistoryDAO;
import initcheck.database.DamageRecord;
import initcheck.database.DamageRecordDAO;
import initcheck.database.MonsterKills;
import initcheck.database.MonsterKillsDAO;
import initcheck.database.PlayerBattles;
import initcheck.database.PlayerBattlesDAO;
import initcheck.database.PlayerKills;
import initcheck.database.PlayerKillsDAO;
import initcheck.dungeon.ClientServerDungeonGUI;
import initcheck.dungeon.Dungeon;
import initcheck.dungeon.DungeonGUI;
import initcheck.dungeon.Square;
import initcheck.dungeon.ThumbnailPanel;
import initcheck.graphics.TiledGridPanel;
import initcheck.io.ClientMessageSender;
import initcheck.io.GroupListener;
import initcheck.io.NetworkMonitorDialog;
import initcheck.preferences.PreferencesDialog;
import initcheck.status.StatusItem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputAdapter;

import org.apache.log4j.PropertyConfigurator;

import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.SkyRed;

public class InitClient extends InitBase implements InitProgram {

	public GroupListener gl;
	private ClientMessageSender dst = new ClientMessageSender(this);
	
	private PanelButton currentButton = new PanelButton("Curr");
	private SoundDialog cd = new SoundDialog(this);

	private SoundDialog sd = new SoundDialog(this);

	private JMenuBar menuBar;

	private ClientInitList initList = new ClientInitList(this);

	private JScrollPane listScroll = new JScrollPane(initList);

	private JTextField hostname;

	private JTextField clientName = new JTextField("CLIENT");

	private JTextField fileName = new JTextField(30);

	private PanelButton browse = new PanelButton("Browse", 80);

	private PanelButton connect = new PanelButton("Connect", 80);

	private PanelButton refresh = new PanelButton("Refresh", 80);

	// graphics
	private ImageIcon backgroundImage = new ImageIcon("images/rock043.jpg");

	private ImageIcon networkImage = new ImageIcon("images/networkon.jpg");

	private ImageIcon networkOffImage = new ImageIcon("images/networkoff.jpg");

	private JLabel networkLabel = new JLabel(networkOffImage);

	private JLabel networkStatusLabel = new JLabel("Connection Idle");

	private int trafficCount = 0;

	JPanel networkPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

	private JLabel round = new JLabel("Round : 1");

	private JLabel partyModCounter = new JLabel("PARTY :+0/+0/+0");

	private JLabel monsterModCounter = new JLabel("MONSTERS :+0/+0/+0");

	private String party;

	private ClientServerDungeonGUI map = null;

	private JTabbedPane tabbedPane = new JTabbedPane();

	// IO Variables
	//private DataSenderThread dst = null;

	//private ObjectReceiverThread th = null;

	//private Socket initSocket = null;

	//private Socket outSocket = null;

	private String id = "CLIENT";

	private boolean connectFailure = false;

	private int connectAttempts = 0;

	private int maxConnectAttempts = 3;

	private InitLogger logger = new InitLogger(this);

	private ChatPanel cp = new ChatPanel(this);

	private PlayerDamagePanel hitSheet = new PlayerDamagePanel(this);

	int currentGroup = 0;

	private Vector<Square> groupPositionList = new Vector<Square>();

	private Vector<Participant> charVector = new Vector<Participant>();

	JPanel clientPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));

	PreferencesDialog preferencesDialog;

	JComponent contents;

	private NetworkMessagePanel networkMessagePanel = new NetworkMessagePanel();
	
	PlayerDamageDialog pdd = null;
	
	private PlayerProfilePanel ppp = new PlayerProfilePanel();
	
	private boolean hadNameArg = false;
	
	//NioSocketConnector connector = new NioSocketConnector();
	
	public void addThumbnail(ThumbnailPanel tp){
		
	}
	
	public void showHitSheetDialog(){
		if (pdd == null){
			pdd = new PlayerDamageDialog(hitSheet, this);
		}else{
			pdd.setVisible(true);
		}
	}
	
	public void hideHitSheetDialog(){
		tabbedPane.addTab("Hit Sheet", hitSheet);
		pdd = null;
	}
	
	public void setTab(String s) {
		if (s.equals("LISTTAB")) {
			tabbedPane.setSelectedIndex(0);
		}
	}

	public void updatePlayer(String ac, String hp, String name) {
		logger.log("Trying to update "+name);
		Vector<Participant> v = charVector;
		int idx = initList.getSelectedIndex();
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
				
				initList.setListData(v);
				initList.setSelectedIndex(idx);
				break;
			}
		}
	}
	
	public void damagePlayer(String name, String dmg) {
		logger.log("Got damage of " + dmg);
		Vector<Participant> v = charVector;
		int idx = initList.getSelectedIndex();
		for (int i = 0; i < v.size(); i++) {
			Participant p = (Participant) v.get(i);
			if (p.getName().trim().equals(name.trim())) {

				
				p.setRoundDamage(p.getRoundDamage()+Integer.parseInt(dmg));
				p.setDamageTaken(Integer.parseInt(dmg));
				p.setCurrentHitPoints(p.getCurrentHitPoints()
						- Integer.parseInt(dmg));
				logger.log("Set Hitpoints of " + name + " to "
						+ p.getCurrentHitPoints());
				initList.setListData(v);
				initList.setSelectedIndex(idx);
				break;
			}
		}
		processChars(charVector);
	}
	
	public void healPlayer(String name, String dmg) {
		Vector<Participant> v = charVector;
		int idx = initList.getSelectedIndex();
		for (int i = 0; i < v.size(); i++) {
			Participant p = (Participant) v.get(i);
			if (p.getName().trim().equals(name.trim())) {

				if (p.getPType().equalsIgnoreCase("Monster")) {
					return;
				}
				DCharacter d = (DCharacter) p;
				d.setCurrentHitPoints(Integer.parseInt(dmg));
				initList.setListData(v);
				initList.setSelectedIndex(idx);
				break;
			}
		}
		// redestribute the list to the clients.
		processChars(charVector);
	}
	
	public void modPlayerStat(String name, String stat, int mod) {
		Vector<Participant> v = charVector;
		int idx = initList.getSelectedIndex();
		for (int i = 0; i < v.size(); i++) {
			Participant p = (Participant) v.get(i);
			if (p.getName().trim().equals(name.trim())) {


				if (p.getPType().equalsIgnoreCase("Monster")) {
					return;
				}
				
				DCharacter d = (DCharacter) p;
				
				if (stat.equals("STR")) {
					d.setStrMod(d.getStrMod() + mod);
				} else if (stat.equals("CON")) {
					d.setConMod(d.getConMod() + mod);
				} else if (stat.equals("DEX")) {
					d.setDexMod(d.getDexMod() + mod);
				} else if (stat.equals("CHA")) {
					d.setChaMod(d.getChaMod() + mod);
				} else if (stat.equals("WIS")) {
					d.setWisMod(d.getWisMod() + mod);
				} else if (stat.equals("INT")) {
					d.setIntMod(d.getIntMod() + mod);
				}
				
				
				initList.setListData(v);
				initList.setSelectedIndex(idx);
				break;
			}
		}
		// redestribute the list to the clients.
		processChars(charVector);
		
	}
	
	public void setFont(Font f) {

	}

	public void setNetworkList(Vector<String> v) {

		clientPanel.removeAll();
		for (int i = 0; i < v.size(); i++) {
			JLabel label = new JLabel((String) v.get(i));
			label.setBackground(InitColor.red);
			label.setForeground(Color.white);
			label.setOpaque(true);
			clientPanel.add(label);
		}
		networkPanel.paintImmediately(0, 0, new Double(networkPanel.getSize()
				.getWidth()).intValue(), new Double(networkPanel.getSize()
				.getHeight()).intValue());

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

	public void doCommand(int[] positions, String command) {
		initList.doCommand(positions, command);
	}

	/**
	 * Get the Id value.
	 * 
	 * @return the Id value.
	 */
	public String getId() {
		if (clientName.getText() != null && !clientName.getText().equals("")) {
			return clientName.getText();
		}
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

	public DungeonGUI getMap(){
		return map;
	}
	
	/**
	 * Set the current group. This affects what is shown on the map, since
	 * multiple groups can be exploring the same map from different positions.
	 * 
	 * @param newCurrentGroup
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
				newPos = (Square) groupPositionList.get(currentGroup);
			} else {
				groupPositionList.add(newPos);
			}
			map.setCurrentGroup(currentGroup);
			map.setCurrentPosition(newPos.getX(), newPos.getY(), newPos.getZ());

		}
	}

	public void repaintScreen() {
		contents.paintImmediately(0, 0, new Double(contents.getSize()
				.getWidth()).intValue(), new Double(contents.getSize()
				.getHeight()).intValue());
	}

	public void sendStat(String name, StatusItem status) {
		dst.sendStatus(name, status);
	}

	public void modStat(String name, String stat, int mod) {
		dst.sendStatMod(name, stat, mod);
	}

	public void whineToServer() {
		dst.sendWhine("The Treasure Sucks, Dude.");
	}

	public void recordHighScore(DamageRecord dr) {
		DamageRecordDAO drdb = new DamageRecordDAO();
		drdb.addOrUpdateDamageRecord(dr);
		@SuppressWarnings("unused")
		MessageDialog md = new MessageDialog("New Damage Record!", dr
				.toString());

		playSound(getSound("highscore"));
	}

	public void showBattleStats(Vector<String> v) {
		BattleStatDialog bsd = new BattleStatDialog("Client Battle Stats", "");
		for (int i = 0; i < v.size(); i++) {
			bsd.addMessage((String) v.get(i));
		}
		bsd.setVisible(true);
	}

	public void receiveHistory(BattleHistory bh, Vector<PlayerKills> kills, Vector<DCharacter> players,
			Vector<MonsterKills> monsterKills, Vector<PlayerBattles> statVector) {
		PlayerKillsDAO pkdb = new PlayerKillsDAO();
		BattleHistoryDAO bhdb = new BattleHistoryDAO();
		PlayerBattlesDAO pbdb = new PlayerBattlesDAO();
		MonsterKillsDAO mkdb = new MonsterKillsDAO();

		bh.setId(null);
		int battleId = bhdb.addOrUpdateBattleHistory(bh);

		for (int i = 0; i < statVector.size(); i++) {
			PlayerBattles pb = (PlayerBattles) statVector.get(i);
			// pb.setPlayerId((String)players.get(i));
			pb.setBattleId("" + battleId);
			pbdb.addPlayerBattles(pb);
		}

		for (int i = 0; i < kills.size(); i++) {
			PlayerKills pk = (PlayerKills) kills.get(i);
			pk.setBattleId("" + battleId);
			pkdb.addPlayerKills(pk);
		}

		for (int i = 0; i < monsterKills.size(); i++) {
			MonsterKills mk = (MonsterKills) monsterKills.get(i);
			mk.setBattleId("" + battleId);
			mkdb.addMonsterKills(mk);
		}
	}

	public Graphics getGraphics() {
		return initList.getGraphics();
	}

	public void removeAllMonsters() {

	}

	

	public boolean isMapShowing() {
		return tabbedPane.getSelectedIndex() == 1;
	}

	public void addMapPanel() {

		if (tabbedPane.indexOfTab("Map") < 0) {
			map = new ClientServerDungeonGUI(this, backgroundImage,
					ClientServerDungeonGUI.DISPLAY_CLIENT);
			map.setMode(ClientServerDungeonGUI.DISPLAY_CLIENT);

			tabbedPane.insertTab("Map", null, map, "Map", 1);
			// tabbedPane.setSelectedIndex(1);

			map.init();
			map.setFont(defaultFont);
			map.setBorder(BorderFactory.createEmptyBorder(20, // top
					20, // left
					10, // bottom
					20) // right
					);

		}

	}

	/**
	 * Get the CharVector value.
	 * 
	 * @return the CharVector value.
	 */
	public Vector<Participant> getCharVector() {
		return charVector;
	}

	public void changePosition(int idx, int newIdx) {
		Participant p = ((Participant) charVector.get(idx));
		charVector.removeElementAt(idx);
		charVector.add(newIdx, p);
	}
	
	public void removeParticipants(Vector<Participant> v) {
		logger.log("Removing participants");
		for (int i = 0; i < v.size(); i++) {
			Participant p = (Participant) (v.get(i));
			for (int j = 0; j < charVector.size(); j++) {
				Participant p2 = (Participant) (charVector.get(j));
				if (p.getName().equals(p2.getName())) {
					charVector.removeElementAt(j);
					
					break;
				}
			}
		}
	}
	
	
	/**
	 * Set the CharVector value.
	 * 
	 * @param newCharVector
	 *            The new CharVector value.
	 */
	public void setCharVector(Vector<Participant> newCharVector) {
		this.charVector = newCharVector;

	}

	public void refreshFromServer() {
		dst.updateFromServer();
	}

	public void setMap(Dungeon d) {
		logger.log("Setting Dungeon From Server");
		map.getDrawingBoard().setDungeon(d);
		map.getDrawingBoard().resizeImage();
		repaintScreen();
		map.updateMap();
		map.setMapScale(30);
	}

	public void updateMap(int x, int y, int z) {
		map.updateMap(x, y, z);

	}

	public void setStatus(int[] positions, StatusItem status) {
		initList.setStatus(positions, status);
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

	public InitClient(String clientName) {
		
		this.id = clientName;
		
		// remove the old log file
		File f = new File("ClientLog.txt");
		try {
			f.delete();
		} catch (Exception e) {
			logger.log("error", "Unable to delete old Client Log " + e);
		}

		// set the base type
		setType(CLIENT);

		// configure logging
		PropertyConfigurator.configure("ClientLog4j.cfg");
		logger.log("STARTING UP INIT CLIENT");

		// initialize the preferences
		preferencesDialog = new PreferencesDialog(this);
	}

	public void sendMessage(String s) {
		if (dst != null) {
			dst.sendMessage(s, id);
		}
	}

	public void setRoundCounter(String s) {
		round.setText("Round : " + s);
	
			initList.processListItem(0);
		
	}

	public void setPartyModCounter(String s) {
		partyModCounter.setText("PARTY :" + s);
	}

	public void setMonsterModCounter(String s) {
		monsterModCounter.setText("MONSTERS :" + s);
	}

	public void showNetStats(){
		NetworkMonitorDialog.display();
	}
	
	
	public void writePrefsToFile() {
		try {
			// write out the hit sheet
			FileOutputStream ostream = new FileOutputStream(
					"saves/clientprefs.ser");
			ObjectOutputStream p = new ObjectOutputStream(ostream);

			p.writeObject(defaultFont);
			p.writeObject(hostname.getText());
			p.writeObject(clientName.getText());
			p.flush();
			ostream.close();
		} catch (java.io.IOException e) {
			logger.log(e.toString());
		}
	}

	public void readPrefsFromFile() {
		try {
			// read in the hit sheet
			FileInputStream istream = new FileInputStream(
					"saves/clientprefs.ser");
			ObjectInputStream p = new ObjectInputStream(istream);
			setDefaultFont((Font) p.readObject());
			setListFont(defaultFont);
			hostname.setText((String) p.readObject());
			if (!hadNameArg){
				System.out.println("Setting name from pref file");
				clientName.setText((String) p.readObject());
			}
			istream.close();

		} catch (Exception e) {
			logger.log(e.toString());
		}
	}

	public void setListFont(Font f) {
		initList.setFont(f);
		initList.invalidate();
		if (menuBar != null) {
			menuBar.setFont(f);
		}
		round.setFont(f);
		// partyModCounter.setFont(f);
		// monsterModCounter.setFont(f);
		map.setFont(f);
		round.invalidate();

		frame.validate();
		defaultFont = f;

	}

	public Font getDefaultFont() {
		return defaultFont;
	}

	public void showMonster(String p, String n, String id) {

		@SuppressWarnings("unused")
		MonsterPictureDialog md = new MonsterPictureDialog(this, p, n);
	}

	public void showMessage(String s) {
		@SuppressWarnings("unused")
		MessageDialog md = new MessageDialog(getFrame(), "Message From The DM",
				s);
	}

	public void sendServerStun(String name) {
		dst.sendStun(name);
	}

	public void sendServerStun() {
		String name = ((Participant) initList.getSelectedValue()).getName();
		dst.sendStun(name);
	}

	public void sendServerUnStun() {
		String name = ((Participant) initList.getSelectedValue()).getName();
		dst.sendUnStun(name);
	}

	public void sendServerKill(String name) {
		dst.sendKill(name);
	}

	public void sendServerKill() {
		String name = ((Participant) initList.getSelectedValue()).getName();
		dst.sendKill(name);
	}

	public void sendServerRevive() {
		String name = ((Participant) initList.getSelectedValue()).getName();
		dst.sendRevive(name);
	}

	

	public void buildPopup(MouseEvent e) {
		ClientPopupMenu rClickMenu = new ClientPopupMenu(this);
		rClickMenu.show(e.getComponent(), e.getX(), e.getY());
		rClickMenu.setFont(defaultFont);
	}

	public void showStatusDialog() {
		int idx = initList.getSelectedIndex();
		if (idx < 0)
			return;

		if (((Participant) (initList.getSelectedValue())).getPType()
				.equalsIgnoreCase("Monster")) {
			return;
		}

		DCharacter p = (DCharacter) initList.getSelectedValue();
		StatusDialog d = new StatusDialog(this, p, idx);
		d.setVisible(true);
	}

	public void updateDamage(String name, int dmg) {
		damagePlayer(name, ""+dmg);
		dst.sendDamageUpdate(name, dmg);
		
	}

	public void healDamage(String name, int newHp) {
		healPlayer(name, ""+newHp);
		dst.sendHealUpdate(name, newHp);
	}


	public void stunPlayer(String name, boolean increment) {
		Vector<Participant> v = charVector;
		for (int i = 0; i < v.size(); i++) {
			Participant p = (Participant) v.get(i);
			if (p.getName().trim().equals(name.trim())) {
				if (p.getPType().equalsIgnoreCase("Monster")) {
					return;
				}
				if (increment || !p.getStatus().isStunned()) {
					initList.setSelectedIndex(i);
					initList.markListItemStunned();
				}
				break;
			}
		}
	}
	
	public JComponent createComponents() {

		setFrame(new JFrame("InitClient"));

		getFrame().addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				shutdown();
			}
		});

		initList.addMouseListener(new MouseInputAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					buildPopup(e);
				} else if (e.getClickCount() == 2) {
					showStatusDialog();
				}
				
			}
		});

		initList.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				Participant p = (Participant)initList.getSelectedValue();
				if (p != null){
					ppp.setParticipant(p);
				}
			}
		});
		
		// create the menu bar
		menuBar = new ClientMenuBar(this);
		menuBar.setFont(new Font("Arial", Font.BOLD, 20));
		frame.setJMenuBar(menuBar);

		round.setFont(new Font("Arial", Font.BOLD, 20));
		// round.setForeground(Color.blue);
		partyModCounter.setFont(new Font("Arial", Font.BOLD, 20));
		// partyModCounter.setForeground(Color.blue);
		monsterModCounter.setFont(new Font("Arial", Font.BOLD, 20));
		// monsterModCounter.setForeground(Color.blue);

		/*
		 * An easy way to put space between a top-level container and its
		 * contents is to put the contents in a JPanel that has an "empty"
		 * border.
		 */

		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		JPanel serverPanel = new JPanel();

		hostname = new JTextField(20);
		JLabel serverLabel = new JLabel("Server Network Address");
		serverLabel.setForeground(Color.white);
		JLabel nameLabel = new JLabel("Client Name");
		
		nameLabel.setForeground(Color.white);
		serverPanel.add(nameLabel);
		clientName.setText(id);
		serverPanel.add(clientName);
		serverPanel.add(serverLabel);
		serverPanel.add(hostname);
		serverPanel.add(connect);
		serverPanel.add(refresh);
		serverPanel.add(currentButton);
		JPanel filePanel = new JPanel();
		filePanel.add(fileName);
		filePanel.add(browse);

		connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connectToServer();
			}
		});

		currentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initList.goToCurrent();
			}
		});
		
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshView();
			}
		});

		initList.setFont(new Font("Courier", Font.BOLD, 24));
		serverPanel.setOpaque(false);
		listScroll.setOpaque(false);
		
		TiledGridPanel listPanel = new TiledGridPanel();
		

		listPanel.doLayout(serverPanel, 0, listPanel.ypos, 2, 1);
		listPanel.incYPos();
		listPanel.setWeightX(0.9);
		listPanel.setWeightY(1);
		listPanel.doLayout(listScroll, 0, listPanel.ypos);
		listPanel.setWeightX(0.1);
		listPanel.setWeightY(1);
		listPanel.setPadX(25);
		listPanel.doLayout(ppp, 1, listPanel.ypos);
		listPanel.setWeightX(0);
		listPanel.setWeightY(0);
		JPanel counterPanel = new JPanel();
		counterPanel.setLayout(new BoxLayout(counterPanel, BoxLayout.X_AXIS));
		counterPanel.add(round);
		counterPanel.add(Box.createHorizontalGlue());
		counterPanel.add(partyModCounter);
		counterPanel.add(Box.createHorizontalGlue());
		counterPanel.add(monsterModCounter);
		counterPanel.setOpaque(true);

		networkPanel.setBackground(Color.darkGray);
		clientPanel.setBackground(Color.darkGray);
		networkPanel.add(clientPanel);
		networkPanel.add(networkStatusLabel);
		networkPanel.add(networkLabel);

		JPanel bottomPanel = new JPanel(new BorderLayout());
		listPanel.incYPos();
		listPanel.doLayout(counterPanel, 0, listPanel.ypos, 2, 1);
		bottomPanel.add(networkPanel, BorderLayout.SOUTH);

		pane.add(tabbedPane, BorderLayout.CENTER);
		pane.add(bottomPanel, BorderLayout.SOUTH);

		listPanel.setBorder(BorderFactory.createEmptyBorder(20, // top
				20, // left
				20, // bottom
				20) // right
				);

		tabbedPane.addTab("Main", listPanel);

		addMapPanel();
		tabbedPane.addTab("Hit Sheet", hitSheet);
		tabbedPane.addTab("Chat", cp);
		tabbedPane.addTab("Network", networkMessagePanel);
		// return the top level container.
		return pane;

	}

	public void connectToServer() {
		//dst = new DataSenderThread(this, outSocket);
		//dst.start();
		
		// watch out for multiple connects
		if (gl == null){
		gl = new GroupListener(GroupListener.CLIENT_MODE, this, hostname.getText() );
		gl.setChannelName(clientName.getText());
		//if (dst != null && !dst.sendTest()) {
		//	return;
		//}
		//if (setSocket()) {

		//	writePrefsToFile();
		//	logger.log("SENDING SERVER HELLO");
		dst.sendHello();
		}
		
		//	logger.log("SENT");
		//	displayMessage("Connected to server");
		//	refreshFromServer();
		//}
		repaintScreen();
	}

	public void refreshView() {

		int idx = initList.getSelectedIndex();
		initList.setListData(charVector);
		setPosition(idx);

	}

	public void displayMessage(String s) {
		@SuppressWarnings("unused")
		MessageDialog dlg = new MessageDialog(getFrame(),
				"Message For You, Sir", s);
	}

	public void sendChatMessage(String s) {
		sendMessage(s);
	}

	private void init() {
		fileName.requestFocus();
	}

	public void setPlayerNotes(int x, int y, int z, String s) {
		map.setPlayerNotes(x, y, z, s);
	}

	public void sendPlayerNotes(int x, int y, int z, String s) {
		dst.sendPlayerNotes(x, y, z, s);
	}

	public void showMonsterNotes() {
		MonsterNotesDialog mnd = new MonsterNotesDialog(this);
		mnd.setVisible(true);
	}

	public void startTraffic(String s) {
		trafficCount++;
		networkMessagePanel.addMessage(s);
		networkStatusLabel.setText(s + "[" + trafficCount + "]");
		networkLabel.setIcon(networkImage);
		networkPanel.paintImmediately(0, 0, new Double(networkPanel.getSize()
				.getWidth()).intValue(), new Double(networkPanel.getSize()
				.getHeight()).intValue());
	}

	public void stopTraffic() {
		trafficCount--;
		if (trafficCount < 0) {
			trafficCount = 0;
		}
		if (trafficCount == 0) {
			networkLabel.setIcon(networkOffImage);
			networkStatusLabel.setText("Connection Idle");
		}
	}

	public void reconnect() {
		if (connectAttempts < maxConnectAttempts && setSocket()
				&& !connectFailure) {
			writePrefsToFile();
			dst.sendHello();
			displayMessage("Connected to server");
		} else {
			connectFailure = true;
			displayMessage("Lost server connection");
		}
	}

	private boolean setSocket() {

		
		
		
		boolean errors = false;
		String host = hostname.getText();
		//try {

			//initSocket = new Socket(host, 9999);
			//if (th != null) {
		//		th.shutdown();
		//	}
			//th = new ObjectReceiverThread(this, initSocket);
			//th.start();

			//outSocket = new Socket(host, 7777);
			//if (dst != null) {
			//	dst.shutdown();
			//}
			
			//dst = new DataSenderThread(this, outSocket);
			//dst.start();
			
			// pingSocket = new Socket(host, 8888);
			// if (spt != null){
			// spt.shutdown();
			// }
			// spt = new ServerPingThread(this, pingSocket);
			// spt.start();

		//} /*catch (UnknownHostException e) {
			errors = true;
			logger.log("Don't know about host: " + host);
			@SuppressWarnings("unused")
			MessageDialog dlg = new MessageDialog(getFrame(), "Unknown Host",
					"Unknown Host " + host);
		/*} catch (IOException e) {
			errors = true;
		
			logger.log("Couldn't get connection to: " + host);
			@SuppressWarnings("unused")
			MessageDialog dlg = new MessageDialog(getFrame(),
					"Connection Error", "Unable to connect to " + host);
		}*/
		connectAttempts++;
		if (!errors) {
			connectFailure = false;
			connectAttempts = 0;
		}
		return (!errors);

	}

	public void processUpdate(Vector<Participant> v) {
		charVector = v;
		initList.setListData(charVector);
		processChars(v);
	}

	public void processChars(Vector<Participant> v) {
		logger.log("SETTING CHARS ON HITSHEET");
		hitSheet.setChars(v);

	}

	public void advanceList(int i) {
		initList.advanceList(i);

	}

	public void clearRoundDamage(int id) {
		hitSheet.clearRoundDamage(id);
	}

	public void setPosition(int i) {
		initList.setSelectedIndex(i);
		initList.ensureIndexIsVisible(i);
		initList.setCellRenderer(new ClientInitListCellRenderer());
		
		// attempt to keep list selection in middle
		int displayStart = initList.getFirstVisibleIndex();
		int displayEnd = initList.getLastVisibleIndex();
		int numDisplayed = displayEnd - displayStart;
		int advanceNumber = i + (numDisplayed / 2);

		if (advanceNumber > initList.getModel().getSize()) {
			initList.ensureIndexIsVisible(initList.getModel().getSize() - 1);
		} else {
			initList.ensureIndexIsVisible(advanceNumber);
		}

		listScroll.getViewport().setView(initList);
		
	}

	public void showChat(String s, String id) {
		cp.addMessage(s, id);
	}

	public void showCokeDialog() {
		playSound(getSound("thirsty"));
		cd.setVisible(true);
	}

	public void playSoundClip(String s) {
		playSound(getSound(s));
	}

	public String getSound(String s) {
		String sound = preferencesDialog.getSound(s);
		return sound;
	}

	public void showSnackDialog() {
		sd.setDisplayImage("images/cartman2.gif");
		sd.setTitle("Feed Me, Seymour!");
		sd.setHeight(450);
		playSound(getSound("hungry"));
		sd.setVisible(true);
	}

	public void shutdown() {
		
		cleanup();
		// then exit.
		System.exit(0);
	}
	
	public void cleanup(){

		
		
		// if the sender thread to the server is active, shut it down nicely
		if (dst != null) {
			dst.sendGoodbye();
			//dst.shutdown();
		}
	}

	public void setTitle(String s){
		
	}
	
	public static void main(String[] args) {
		String clientName = "CLIENT";
		if (args.length > 0 ){
			System.out.println("GOT CLIENT ARG "+args[0]);
			clientName = args[0];
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
		defaults.put("ToolTip.font", defaultFont);
		defaults.put("TabbedPane.tabsOpaque", true);
		defaults.put("TabbedPane.contentBorderInsets", new Insets(15,15,15,15));
		
		// defaults.put("Label.Font", new FontUIResource(defaultFont));
		final InitClient app = new InitClient(clientName);
		if (args.length > 0 ){
			app.hadNameArg = true;
		}

		// Create the top-level container and add contents to it.
		app.contents = app.createComponents();
		app.getFrame().getContentPane().add(app.contents, BorderLayout.CENTER);

		// run the init routine
		app.init();
		app.readPrefsFromFile();

		// defaults.put("TextArea.Font", defaultFont);
		// defaults.put("TextField.Font", defaultFont);

		// SwingUtilities.updateComponentTreeUI(frame);
		app.getFrame().pack();
		app.getFrame().setVisible(true);

//		 Finish setting up the frame, and show it. Make sure that you trap the
		// window killing "x" to save things.
		app.getFrame().addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				app.cleanup();
				System.exit(0);

			}
		});
		
	}

	public void updateTime(int minutes) {
		if (map != null){
			map.updateTimeCount(minutes);
		}
		
	}
	
	public void updateTotalTime(int minutes) {
		if (map != null){
			map.updateTotalTimeCount(minutes);
		}
		
	}

}

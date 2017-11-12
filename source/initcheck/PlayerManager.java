package initcheck;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputAdapter;

import org.apache.log4j.PropertyConfigurator;

import com.jgoodies.looks.plastic.PlasticTabbedPaneUI;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.SkyRed;

import initcheck.character.BattleHistoryDialog;
import initcheck.character.LoadComparisonDialog;
import initcheck.character.LoadLibraryVerifyDialog;
import initcheck.character.LoadPartyProgressPanel;
import initcheck.character.LoadVerifyDialog;
import initcheck.character.ManagerPopupMenu;
import initcheck.character.PartyInfoPanel;
import initcheck.character.PlayerComparisonDialog;
import initcheck.character.PlayerListCellRenderer;
import initcheck.character.PlayerStatDialog;
import initcheck.character.chooser.CampaignChooser;
import initcheck.character.chooser.PartyChooser;
import initcheck.character.itembuilder.ItemBuilderDialog;
import initcheck.character.library.ArmorAbilitiesLibrary;
import initcheck.character.library.ArmorLibrary;
import initcheck.character.library.ArtifactLibrary;
import initcheck.character.library.CampaignLibrary;
import initcheck.character.library.CharClassLibrary;
import initcheck.character.library.ClassAbilitiesLibrary;
import initcheck.character.library.CriticalLibrary;
import initcheck.character.library.DeityLibrary;
import initcheck.character.library.DomainLibrary;
import initcheck.character.library.EquipmentLibrary;
import initcheck.character.library.FeatLibrary;
import initcheck.character.library.FeatPackageLibrary;
import initcheck.character.library.FumbleLibrary;
import initcheck.character.library.LibraryPanel;
import initcheck.character.library.MaterialSourceLibrary;
import initcheck.character.library.MaterialsLibrary;
import initcheck.character.library.MonsterLibrary;
import initcheck.character.library.PoisonLibrary;
import initcheck.character.library.RaceLibrary;
import initcheck.character.library.RingsLibrary;
import initcheck.character.library.RodsLibrary;
import initcheck.character.library.ServicesLibrary;
import initcheck.character.library.SkillLibrary;
import initcheck.character.library.SpecificArmorLibrary;
import initcheck.character.library.SpecificWeaponLibrary;
import initcheck.character.library.SpellLibrary;
import initcheck.character.library.StaffsLibrary;
import initcheck.character.library.TrapLibrary;
import initcheck.character.library.WeaponAbilitiesLibrary;
import initcheck.character.library.WeaponLibrary;
import initcheck.character.library.WonderousItemsLibrary;
import initcheck.character.printsheets.CharacterPrinter;
import initcheck.character.printsheets.CharacterPrinter2;
import initcheck.character.printsheets.DMSheetPrintDialog;
import initcheck.character.printsheets.HitSheetPrintDialog;
import initcheck.database.Campaign;
import initcheck.database.Exportable;
import initcheck.database.Importable;
import initcheck.database.InitDBC;
import initcheck.database.Party;
import initcheck.database.PartyDAO;
import initcheck.database.Tag;
import initcheck.database.VersionDAO;
import initcheck.graphics.Skin;
import initcheck.graphics.Skinnable;
import initcheck.graphics.TiledDialog;
import initcheck.graphics.TiledList;
import initcheck.graphics.TiledListItem;
import initcheck.graphics.TiledPanel;
import initcheck.io.GroupListener;
import initcheck.io.ManagerMessageSender;
import initcheck.preferences.PreferencesDialog;
import initcheck.server.InitCheckEngine;
import initcheck.utils.DateUtil;
import initcheck.utils.FileCopy;
import initcheck.utils.FtpClient;
import util.StrManip;

public class PlayerManager extends InitBase implements GroupMessageClient {

	private PartyChooser partySelect = new PartyChooser();

	private JComboBox<String> playerSelect;

	private CampaignChooser campaignSelect = new CampaignChooser();

	private static JFrame frame;

	private ManagerMenuBar menuBar;

	private InitCheckEngine engine = new InitCheckEngine();

	private TiledList<TiledListItem> playerList = new TiledList<TiledListItem>();

	// private JButton cancelButton = new JButton("Cancel");
	private PanelButton addParty = new PanelButton("New Party", 120);

	private PanelButton remParty = new PanelButton("Remove Party", 120);

	private PanelButton addButton = new PanelButton("New Character", 120);

	private PanelButton deleteButton = new PanelButton("Delete Character", 120);

	private PanelButton modifyButton = new PanelButton("Modify Character", 120);

	private PanelButton printButton = new PanelButton("Print Sheets", 80);

	private PanelButton printHsButton = new PanelButton("Hit Sheet", 80);

	private PanelButton printDmButton = new PanelButton("DM Sheet", 80);

	private PanelButton loadButton = new PanelButton("Load", 80);

	private InitLogger logger = new InitLogger(this);

	private JPanel contents = new JPanel(new BorderLayout());

	JPanel buttons = new JPanel();

	TiledPanel main = new TiledPanel(InitImage.steel);

	PartyInfoPanel infoPanel;

	private static final int MOD = 2;

	private Vector<DCharacter> chars = new Vector<DCharacter>();

	private JTabbedPane tabbedPane = new JTabbedPane();

	private LibraryPanel campaignPanel = new LibraryPanel(new CampaignLibrary());

	private LibraryPanel featPanel = new LibraryPanel(new FeatLibrary());

	private LibraryPanel skillPanel = new LibraryPanel(new SkillLibrary());

	private LibraryPanel classPanel = new LibraryPanel(new CharClassLibrary());

	private LibraryPanel racePanel = new LibraryPanel(new RaceLibrary());

	private LibraryPanel armorPanel = new LibraryPanel(new ArmorLibrary());

	private LibraryPanel weaponPanel = new LibraryPanel(new WeaponLibrary());

	private LibraryPanel spellPanel = new LibraryPanel(new SpellLibrary());

	private LibraryPanel domainPanel = new LibraryPanel(new DomainLibrary());

	private LibraryPanel equipmentPanel = new LibraryPanel(new EquipmentLibrary());

	private LibraryPanel servicesPanel = new LibraryPanel(new ServicesLibrary());

	private LibraryPanel featPackagePanel = new LibraryPanel(new FeatPackageLibrary());

	private LibraryPanel materialsPanel = new LibraryPanel(new MaterialsLibrary());

	private LibraryPanel wepAbilPanel = new LibraryPanel(new WeaponAbilitiesLibrary());

	private LibraryPanel armAbilPanel = new LibraryPanel(new ArmorAbilitiesLibrary());

	private LibraryPanel wonderousPanel = new LibraryPanel(new WonderousItemsLibrary());

	private LibraryPanel ringsPanel = new LibraryPanel(new RingsLibrary());

	private LibraryPanel rodsPanel = new LibraryPanel(new RodsLibrary());

	private LibraryPanel staffsPanel = new LibraryPanel(new StaffsLibrary());

	private LibraryPanel classAbilityPanel = new LibraryPanel(new ClassAbilitiesLibrary());

	private LibraryPanel monsterPanel = new LibraryPanel(new MonsterLibrary());

	private LibraryPanel fumblePanel = new LibraryPanel(new FumbleLibrary());

	private LibraryPanel criticalPanel = new LibraryPanel(new CriticalLibrary());

	private LibraryPanel sourcePanel = new LibraryPanel(new MaterialSourceLibrary());

	private LibraryPanel deityPanel = new LibraryPanel(new DeityLibrary());

	private LibraryPanel trapPanel = new LibraryPanel(new TrapLibrary());

	private LibraryPanel poisonPanel = new LibraryPanel(new PoisonLibrary());

	private LibraryPanel artifactPanel = new LibraryPanel(new ArtifactLibrary());

	private LibraryPanel specificWeaponPanel = new LibraryPanel(new SpecificWeaponLibrary());

	private LibraryPanel specificArmorPanel = new LibraryPanel(new SpecificArmorLibrary());

	private TiledDialog lpd = new TiledDialog(frame, "Loading Party", false);

	private TiledDialog lfd = new TiledDialog(frame, "Downloading Updates", false);

	private TiledDialog ufd = new TiledDialog(frame, "Uploading Files", false);

	private PreferencesDialog pd;

	JPanel mainPanel = new JPanel();

	ManagerMessageSender groupWriter = new ManagerMessageSender(this);

	boolean dmMode = false;
	public GroupListener gl;

	public PlayerManager() {
		// remove the old log file
		File f = new File("ManagerLog.txt");
		try {
			f.delete();
		} catch (Exception e) {
			logger.log("error", "Unable to delete old Manager Log " + e);
		}

		// configure logging
		PropertyConfigurator.configure("ManagerLog4j.cfg");
		logger.log("STARTING UP Player Manager");

		setType(InitBase.MANAGER);
		gl = new GroupListener(GroupListener.ADMIN_MODE, this);
	}

	private Component createComponents() {

		menuBar = new ManagerMenuBar(this);
		frame.setJMenuBar(menuBar);

		InitDBC db = new InitDBC();

		Vector<String> playerVector = db.getPlayerNames();

		playerSelect = new JComboBox<String>(playerVector);
		
		
		partySelect.setSelectedIndex(0);
		loadCampaign();
		playerList.setListData(engine.getCharacters());
		playerList.setCellRenderer(new PlayerListCellRenderer());
		playerList.setFont(new Font("Courier", Font.PLAIN, 12));
		playerList.setBackground(Color.lightGray);

		JScrollPane scroll = new JScrollPane(playerList);
		JLabel corner = new JLabel(new ImageIcon("images/rock043.jpg"));

		scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, corner);

		playerList.addMouseListener(new MouseInputAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					buildPopup(e);
				}
			}
		});

		// buttons.add(cancelButton);
		buttons.add(addParty);
		buttons.add(remParty);
		buttons.add(addButton);
		buttons.add(modifyButton);
		buttons.add(deleteButton);
		buttons.add(printButton);
		buttons.add(printHsButton);
		buttons.add(printDmButton);

		buttons.setOpaque(false);

		JPanel partyPanel = new JPanel();
		partyPanel.setOpaque(false);
		partyPanel.add(campaignSelect);
		partyPanel.add(partySelect);
		partyPanel.add(playerSelect);
		partyPanel.add(loadButton);

		main.setBorder(BorderFactory.createEmptyBorder(30, // top
				30, // left
				30, // bottom
				30) // right
		);

		contents.add(partyPanel, BorderLayout.NORTH);

		mainPanel.setLayout(new BorderLayout());

		mainPanel.add(scroll, BorderLayout.CENTER);

		contents.add(mainPanel, BorderLayout.CENTER);
		contents.setOpaque(false);

		main.setLayout(new BorderLayout());
		main.add(contents, BorderLayout.CENTER);
		main.add(buttons, BorderLayout.SOUTH);

		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadParty(((Campaign) campaignSelect.getSelectedItem()).getId(),
						((Party) partySelect.getSelectedItem()).getPartyName(),
						(String) playerSelect.getSelectedItem());
			}
		});

		// cancelButton.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {

		// }
		// });

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPlayerAddDialog();
			}
		});

		addParty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewParty();
			}
		});

		remParty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeParty();
			}
		});

		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printSheets();
			}
		});

		printHsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showHsDialog();
			}
		});

		printDmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showDmDialog();
			}
		});

		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteCharacter();
			}
		});

		campaignSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadCampaign();
			}
		});

		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int idx = playerList.getSelectedIndex();
				DCharacter c = (DCharacter) playerList.getSelectedValue();

				logger.log("Char is " + c.getName() + " init " + c.getInit());
				showPlayerStatDialog(c, idx, MOD);

			}
		});

		playerList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {

				}
				if (e.getClickCount() == 2) {
					int idx = playerList.getSelectedIndex();
					if (idx >= 0) {
						DCharacter c = (DCharacter) playerList.getSelectedValue();
						logger.log("Char is " + c.getName() + " init " + c.getInit());
						showPlayerStatDialog(c, idx, MOD);
					}
				}
			}
		});

		playerList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				playerList.ensureIndexIsVisible(playerList.getSelectedIndex());
			}
		});

		playerList.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					int idx = playerList.getSelectedIndex();
					DCharacter c = (DCharacter) playerList.getSelectedValue();
					logger.log("Char is " + c.getName() + " init " + c.getInit());
					showPlayerStatDialog(c, idx, MOD);
				}
			}

			public void keyTyped(KeyEvent e) {

			}

			public void keyReleased(KeyEvent e) {

			}
		});
		tabbedPane.setOpaque(false);
		// tabbedPane.setUI(new TestTabUI());

		tabbedPane.setForeground(InitColor.woodText);
		tabbedPane.add(main, "Characters");
		tabbedPane.add(campaignPanel, "Campaigns");
		tabbedPane.add(featPanel, "Feats");
		tabbedPane.add(skillPanel, "Skills");
		tabbedPane.add(classPanel, "Classes");
		tabbedPane.add(classAbilityPanel, "Class Abilities");
		tabbedPane.add(racePanel, "Races");
		tabbedPane.add(armorPanel, "Armor");
		tabbedPane.add(specificArmorPanel, "Specific Armor");
		tabbedPane.add(weaponPanel, "Weapons");
		tabbedPane.add(specificWeaponPanel, "Specific Weapons");
		tabbedPane.add(spellPanel, "Spells");
		tabbedPane.add(domainPanel, "Domains");
		tabbedPane.add(equipmentPanel, "Equipment");
		tabbedPane.add(servicesPanel, "Services");
		tabbedPane.add(featPackagePanel, "Feat Packages");
		tabbedPane.add(materialsPanel, "Materials");
		tabbedPane.add(wepAbilPanel, "Weapon Abilities");
		tabbedPane.add(armAbilPanel, "Armor Abilities");
		tabbedPane.add(wonderousPanel, "Wonderous Items");
		tabbedPane.add(artifactPanel, "Artifacts");
		tabbedPane.add(ringsPanel, "Rings");
		tabbedPane.add(rodsPanel, "Rods");
		tabbedPane.add(staffsPanel, "Staves");
		tabbedPane.add(monsterPanel, "Monsters");
		tabbedPane.add(fumblePanel, "Fumbles");
		tabbedPane.add(criticalPanel, "Criticals");
		tabbedPane.add(sourcePanel, "Sources");
		tabbedPane.add(deityPanel, "Deities");
		tabbedPane.add(trapPanel, "Traps");
		tabbedPane.add(poisonPanel, "Poisons");
		TiledPanel wrapper = new TiledPanel();
		wrapper.setLayout(new BorderLayout());
		wrapper.add(tabbedPane, BorderLayout.CENTER);

		/*
		 * UIDefaults defaults= new UIDefaults();
		 * 
		 * defaults.put("TabbedPane:TabbedPaneTab[Enabled].backgroundPainter",
		 * new TabPainter(TabPainter.BACKGROUND_ENABLED));
		 * defaults.put("TabbedPane:TabbedPaneTab[Disabled].backgroundPainter",
		 * new TabPainter(TabPainter.BACKGROUND_DISABLED)); defaults.put(
		 * "TabbedPane:TabbedPaneTab[Enabled+MouseOver].backgroundPainter", new
		 * TabPainter(TabPainter.BACKGROUND_ENABLED_MOUSEOVER)); defaults.put(
		 * "TabbedPane:TabbedPaneTab[Enabled+Pressed].backgroundPainter", new
		 * TabPainter(TabPainter.BACKGROUND_ENABLED_PRESSED));
		 * defaults.put("TabbedPane:TabbedPaneTab[Selected].backgroundPainter",
		 * new TabPainter(TabPainter.BACKGROUND_SELECTED)); defaults.put(
		 * "TabbedPane:TabbedPaneTab[Disabled+Selected].backgroundPainter", new
		 * TabPainter(TabPainter.BACKGROUND_SELECTED_DISABLED)); defaults.put(
		 * "TabbedPane:TabbedPaneTab[Focused+Selected].backgroundPainter", new
		 * TabPainter(TabPainter.BACKGROUND_SELECTED_FOCUSED)); defaults.put(
		 * "TabbedPane:TabbedPaneTab[MouseOver+Selected].backgroundPainter", new
		 * TabPainter(TabPainter.BACKGROUND_SELECTED_MOUSEOVER)); defaults.put(
		 * "TabbedPane:TabbedPaneTab[Focused+MouseOver+Selected].backgroundPainter",
		 * new TabPainter(TabPainter.BACKGROUND_SELECTED_MOUSEOVER_FOCUSED));
		 * defaults.put(
		 * "TabbedPane:TabbedPaneTab[Pressed+Selected].backgroundPainter", new
		 * TabPainter(TabPainter.BACKGROUND_SELECTED_PRESSED)); defaults.put(
		 * "TabbedPane:TabbedPaneTab[Focused+Pressed+Selected].backgroundPainter",
		 * new TabPainter(TabPainter.BACKGROUND_SELECTED_PRESSED_FOCUSED));
		 * defaults.put(
		 * "TabbedPane:TabbedPaneTabArea[Disabled].backgroundPainter", new
		 * TabAreaPainter(TabAreaPainter.BACKGROUND_DISABLED)); defaults.put(
		 * "TabbedPane:TabbedPaneTabArea[Enabled+MouseOver].backgroundPainter",
		 * new TabAreaPainter(TabAreaPainter.BACKGROUND_ENABLED_MOUSEOVER));
		 * defaults.put(
		 * "TabbedPane:TabbedPaneTabArea[Enabled+Pressed].backgroundPainter",
		 * new TabAreaPainter(TabAreaPainter.BACKGROUND_ENABLED_PRESSED));
		 * defaults.put(
		 * "TabbedPane:TabbedPaneTabArea[Enabled].backgroundPainter", new
		 * TabAreaPainter(TabAreaPainter.BACKGROUND_ENABLED));
		 * 
		 * tabbedPane.putClientProperty("Nimbus.Overrides.InheritDefaults",
		 * Boolean.TRUE); tabbedPane.putClientProperty("Nimbus.Overrides",
		 * defaults);
		 */

		pd = new PreferencesDialog(this);
		if (pd.isProxy()) {
			setProxy(pd.getProxy().getProxyAddress());
			setProxyPort(pd.getProxy().getProxyPort());
		}

		return wrapper;
	}

	public void loadCampaign() {
		String campaign = ((Campaign) campaignSelect.getSelectedItem()).getId();
		LibraryPanel.setCampaign((Campaign) campaignSelect.getSelectedItem());
		partySelect.setCampaign(campaign, dmMode);
	}

	public void toggleDmMode() {
		String password = JOptionPane.showInputDialog("What is the super secret word?");
		if (password.equals("dmrulez")) {

			dmMode = true;
			String campaign = ((Campaign) campaignSelect.getSelectedItem()).getId();
			partySelect.setCampaign(campaign, dmMode);

		}
	}

	public void showPreferences() {

		pd.setVisible(true);
	}

	public void setSkin(Skin s) {
		if (!s.getName().equals(skin.getName())) {
			Dimension dim = frame.getSize();
			frame.invalidate();

			if (!s.isUseGraphics()) {
				tabbedPane.setUI(new PlasticTabbedPaneUI());
				tabbedPane.setForeground(Color.BLACK);
			} else {
				// tabbedPane.setUI(new TestTabUI());
				tabbedPane.setForeground(InitColor.woodText);
			}

			main.setPaintBackground(s.isTilePanels());
			playerList.setPaintBackground(s.isTileLists());

			for (int i = 0; i < tabbedPane.getTabCount(); i++) {
				Component c = tabbedPane.getComponentAt(i);
				try {
					Skinnable sk = (Skinnable) c;
					sk.setSkin(s);
				} catch (ClassCastException ce) {

				}
			}

			frame.validate();
			frame.setSize(dim);
			skin = s;
		}
	}

	public void checkForUpdates() {
		try {

			// create the update directory, if it doesn't exist
			File updateFile = new File(".\\update");
			if (!updateFile.exists()) {
				updateFile.mkdir();
			}

			// blow away the previous version of the zip file, if any
			updateFile = new File(".\\update\\player.zip");
			if (updateFile.exists()) {
				updateFile.delete();
			}

			MessageDialog md = new MessageDialog("Checking Versions", "Checking Version Numbers");

			InputStream in = null;
			/*
			 * // pull down the current version number URL url = new
			 * URL("http://www.cse.buffalo.edu/~ahunt/version.txt"); InputStream
			 * in = null; if (getProxy() != null){
			 * 
			 * Proxy p = new Proxy(java.net.Proxy.Type.HTTP,new
			 * InetSocketAddress(getProxy(), 80)); URLConnection connection =
			 * url.openConnection(p); in = connection.getInputStream(); }else{
			 * in = url.openStream(); } byte[] version = new byte[5];
			 * 
			 * while (in.read(version) != -1) {
			 * 
			 * } in.close();
			 * 
			 * String s = new String(version);
			 */

			FtpClient ftp = new FtpClient("bumfriends.dyndns.org");
			// log in
			ftp.login("bumfriend", "ferd");
			ftp.cd("dnd");
			FileOutputStream os = new FileOutputStream("versioncheck.txt");
			ftp.getBinary("version.txt", os);

			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("versioncheck.txt")));

			String s = reader.readLine();
			reader.close();

			int versionNumber = Integer.parseInt(s.trim());

			System.out.println("FOUND VERSION " + versionNumber);

			VersionDAO vdb = new VersionDAO();
			int currVersion = vdb.getVersionNumber();

			boolean updateMain = true;

			// if the current version is the same as or greater than the one on
			// the web, don't download
			if (currVersion >= versionNumber) {
				md.setMessage("You have the most current version.");

				int confirm = JOptionPane.showConfirmDialog(null,
						"You already have the most current version.  Do you wish to continue?", "Already Current",
						JOptionPane.YES_NO_OPTION);

				if (confirm == JOptionPane.NO_OPTION) {
					updateMain = false;
				}
			}

			logger.log(updateMain ? "update main is true" : "update main is false");

			if (updateMain) {
				md.setMessage(
						"Downloading of updates will start shortly.\n  Once the update is complete, the application will restart.");
			}

			LoadFileProgressPanel lfp = new LoadFileProgressPanel(lfd, 3000000, "Connecting to the internet");
			lfd.setMainWindow(lfp);
			lfd.pack();
			lfd.setLocationRelativeTo(null);
			lfd.setVisible(true);

			FileOutputStream out;
			int bytesRead;
			int totalBytes = 0;
			byte[] buf = new byte[4 * 1024]; // 4K buffer

			if (updateMain) {

				// download the player.zip file
				/*
				 * url = new
				 * URL("http://www.cse.buffalo.edu/~ahunt/player.zip");
				 * 
				 * 
				 * URLConnection connection = null; if (getProxy() != null){
				 * Proxy p = new Proxy(java.net.Proxy.Type.HTTP,new
				 * InetSocketAddress(getProxy(), 80)); connection =
				 * url.openConnection(p);
				 * 
				 * }else{ connection = url.openConnection(); }
				 * 
				 * int size = connection.getContentLength();
				 */

				// lfp.setMaxValue(size);
				lfp.signal("Opening file");
				// in = connection.getInputStream();

				lfp.signal("Saving updates ... 0000K read ");
				out = new FileOutputStream(updateFile);
				ftp.getBinary("player.zip", out);

				/*
				 * while ((bytesRead = in.read(buf)) != -1) { totalBytes +=
				 * bytesRead; out.write(buf, 0, bytesRead);
				 * lfp.setValue(totalBytes); lfp.signal("Saving updates ... " +
				 * (totalBytes / 1024) + "K read."); }
				 */
				out.close();

				// unzip the zip file
				ZipFile zip = new ZipFile(".\\update\\player.zip");
				ZipEntry database = zip.getEntry("player.mdb");
				in = zip.getInputStream(database);

				File dbOut = new File(".\\update\\player.mdb");
				out = new FileOutputStream(dbOut);
				totalBytes = 0;
				lfp.setMaxValue(new Long(database.getSize()).intValue());

				while ((bytesRead = in.read(buf)) != -1) {
					totalBytes += bytesRead;
					out.write(buf, 0, bytesRead);
					lfp.setValue(totalBytes);
					lfp.signal("Uncompressing player.mdb ... " + (totalBytes / 1024) + "K read.");
				}
				out.close();

				ZipEntry jarfile = zip.getEntry("INIT.jar");
				in = zip.getInputStream(jarfile);

				File jarOut = new File(".\\update\\INIT.jar");
				out = new FileOutputStream(jarOut);
				totalBytes = 0;
				lfp.setMaxValue(new Long(jarfile.getSize()).intValue());

				while ((bytesRead = in.read(buf)) != -1) {
					totalBytes += bytesRead;
					out.write(buf, 0, bytesRead);
					lfp.setValue(totalBytes);
					lfp.signal("Uncompressing INIT.jar ... " + (totalBytes / 1024) + "K read.");
				}
				out.close();
				zip.close();
			}

			// check on images
			/*
			 * url = new
			 * URL("http://www.cse.buffalo.edu/~ahunt/imageregister.txt"); if
			 * (getProxy() != null){ Proxy p = new
			 * Proxy(java.net.Proxy.Type.HTTP,new InetSocketAddress(getProxy(),
			 * 80)); URLConnection connection = url.openConnection(p); in =
			 * connection.getInputStream(); }else{ in = url.openStream(); }
			 */
			ftp.getBinary("imageregister.txt", new FileOutputStream("imageregistercheck.txt"));
			in = new FileInputStream("imageregistercheck.txt");

			BufferedReader bufReader = new BufferedReader(new InputStreamReader(in));
			String line = "";
			Vector<String> missingImages = new Vector<String>();
			while (line != null) {
				line = bufReader.readLine();
				if (line != null) {
					File f = new File(line);
					if (!f.exists()) {
						missingImages.add(StrManip.replace(line, "\\", "/"));
					}
				}
			}

			// download missing ones.
			lfp.signal("Downloading new content files.");
			lfp.setValue(0);
			lfp.setMaxValue(missingImages.size());
			try {
				for (int i = 0; i < missingImages.size(); i++) {
					/*
					 * url = new URL("http://www.cse.buffalo.edu/~ahunt/" +
					 * missingImages.get(i)); if (getProxy() != null){ Proxy p =
					 * new Proxy(java.net.Proxy.Type.HTTP,new
					 * InetSocketAddress(getProxy(), 80)); URLConnection
					 * connection = url.openConnection(p); in =
					 * connection.getInputStream(); }else{ in =
					 * url.openStream(); }
					 */
					File newImage = new File(missingImages.get(i));
					out = new FileOutputStream(newImage);

					ftp.getBinary(missingImages.get(i), out);

					/*
					 * totalBytes = 0;
					 * 
					 * 
					 * while ((bytesRead = in.read(buf)) != -1) { totalBytes +=
					 * bytesRead; out.write(buf, 0, bytesRead); }
					 */
					lfp.setValue(i);
					out.close();
				}
			} catch (Exception e) {
				@SuppressWarnings("unused")
				MessageDialog md2 = new MessageDialog("Error Loading content files", e.toString());
			} finally {

			}

			if (!updateMain) {
				// look for content update files
				try {

					/*
					 * url = new
					 * URL("http://www.cse.buffalo.edu/~ahunt/updates"); if
					 * (getProxy() != null){ Proxy p = new
					 * Proxy(java.net.Proxy.Type.HTTP,new
					 * InetSocketAddress(getProxy(), 80)); URLConnection
					 * connection = url.openConnection(p); in =
					 * connection.getInputStream(); }else{ in =
					 * url.openStream(); }
					 * 
					 * StringBuffer pageString = new StringBuffer();
					 * 
					 * while ((bytesRead = in.read(buf)) != -1) { totalBytes +=
					 * bytesRead; pageString.append(new String(buf));
					 * 
					 * } in.close();
					 * 
					 * 
					 * String [] updateFiles =
					 * pageString.toString().trim().split("</a>"); String
					 * outputlist = "";
					 */
					ftp.cd("update");
					Vector<String> files = ftp.listFiles();
					Vector<String> filter = new Vector<String>();
					for (String f : files) {

						if (f.endsWith(".xml")) {
							File fi = new File(".\\update\\" + f);
							if (!fi.exists()) {
								filter.add(f);
							}
						}
					}
					files = filter;
					ftp.cd("..");
					/*
					 * 
					 * for (int i = 0; i < updateFiles.length; i++){
					 * 
					 * String file = updateFiles[i];
					 * 
					 * if (file.indexOf("xml") > 0){ int start =
					 * file.lastIndexOf("href=")+6; file = file.substring(start,
					 * file.indexOf('"', start)).trim();
					 * 
					 * outputlist += file+"\n";
					 * 
					 * File f = new File(".\\update\\"+file); if (!f.exists()) {
					 * files.add(file); } } }
					 */

					if (files.size() > 0) {
						TiledList<TiledListItem> fileList = new TiledList<TiledListItem>();

						fileList.setStrings(files);
						JPanel panel = new JPanel();
						panel.setLayout(new BorderLayout());
						panel.add(new JLabel(
										"The following content updates were found.\nPlease select the ones you wish to download."),
								BorderLayout.NORTH);
						panel.add(fileList, BorderLayout.CENTER);

						int answer = JOptionPane.showConfirmDialog(this.getFrame(), panel, "Content Update Files",
								JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
						if (answer == JOptionPane.YES_OPTION) {
							List<TiledListItem> selected = fileList.getSelectedValuesList();
							files = new Vector<String>();
							for (Object o : selected) {
								files.add((String) o);
							}
						} else {
							files = new Vector<String>();
						}

						try {
							for (int i = 0; i < files.size(); i++) {
								/*
								 * url = new URL(
								 * "http://www.cse.buffalo.edu/~ahunt/updates/"
								 * + files.get(i)); if (getProxy() != null) {
								 * Proxy p = new Proxy(java.net.Proxy.Type.HTTP,
								 * new InetSocketAddress(getProxy(), 80));
								 * URLConnection connection = url
								 * .openConnection(p); in =
								 * connection.getInputStream(); } else { in =
								 * url.openStream(); }
								 */
								File newImage = new File(".\\update\\" + files.get(i));
								out = new FileOutputStream(newImage);
								ftp.getBinary(files.get(i), out);
								/*
								 * totalBytes = 0;
								 * 
								 * while ((bytesRead = in.read(buf)) != -1) {
								 * totalBytes += bytesRead; out.write(buf, 0,
								 * bytesRead); }
								 */
								lfp.setValue(i);
								out.close();
								processImport(newImage.getAbsolutePath());
							}
							in.close();
						} catch (Exception e) {
							@SuppressWarnings("unused")
							MessageDialog md2 = new MessageDialog("Error Loading content files", e.toString());
						} finally {

						}
					}

				} catch (Exception e) {
					@SuppressWarnings("unused")
					MessageDialog md2 = new MessageDialog("Error Loading content updates", e.toString());
				} finally {

				}

			}
			ftp.closeServer();
			// lfp.signal("Backing up current files");
			if (updateMain) {
				// now copy the files over into the correct spots
				String date = DateUtil.getDateString(new java.util.Date(), "yyMMddkkmmss");
				FileCopy.copy(".\\player.mdb", ".\\player.mdb.backup." + date);
				FileCopy.copy(".\\dist\\INIT.jar", ".\\dist\\INIT.jar.backup." + date);

				FileCopy.copy(".\\update\\player.mdb", ".\\player.mdb", false);
				FileCopy.copy(".\\update\\INIT.jar", ".\\dist\\INIT.jar", false);

				// and re-start the manager.
				Runtime.getRuntime().exec("cmd /c start delay.bat");

				System.exit(0);
			}

			lfd.setVisible(false);
		} catch (Exception e) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error", e.toString());
		}
	}

	public void showItemBuilder() {
		@SuppressWarnings("unused")
		ItemBuilderDialog ibd = new ItemBuilderDialog();
	}

	public Graphics getGraphics() {
		return playerList.getGraphics();
	}

	public void changeParty() {
		List<TiledListItem> selChars = playerList.getSelectedValuesList();
		if (!selChars.isEmpty()) {
			PartyDAO pdb = new PartyDAO();
			Vector<Party> v = pdb.getParties();

			Object[] selectionValues = v.toArray();
			String initialSelection = v.get(0).toString();
			Object selection = JOptionPane.showInputDialog(null, "What party do you want to switch to?", "Select Party",
					JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);

			String party = selection.toString();

			// JOptionPane
			// .showInputDialog("What party to you want to switch to?");
			InitDBC db = new InitDBC();

			Vector<String> partyVector = db.getParties();
			if (!partyVector.contains(party)) {
				@SuppressWarnings("unused")
				MessageDialog md = new MessageDialog("Error", "That party does not exist. Please create it first.");
			} else {
				for (Object o : selChars) {
					DCharacter curr = (DCharacter) o;
					curr.setParty(party);
					modCharacter(curr, 0);
				}
				loadParty();
			}
		}

	}

	public void addExp() {
		List<TiledListItem> selChars = playerList.getSelectedValuesList();
		if (!selChars.isEmpty()) {
			String xpStr = JOptionPane.showInputDialog("How many XP do you want to add to the selected players?");
			try {
				int xp = Integer.parseInt(xpStr);
				for (Object o : selChars) {
					DCharacter curr = (DCharacter) o;
					int currXp = curr.getXp();
					int newXp = currXp + xp;
					curr.setXp(newXp);
					modCharacter(curr, 0);
				}
			} catch (NumberFormatException nfe) {
				@SuppressWarnings("unused")
				MessageDialog md = new MessageDialog("Error", "The XP number must be an integer.");
			}
		}
	}

	public void buildPopup(MouseEvent e) {
		ManagerPopupMenu rClickMenu = new ManagerPopupMenu(this);
		rClickMenu.show(e.getComponent(), e.getX(), e.getY());
		// rClickMenu.setFont(defaultFont);
	}

	public void showHsDialog() {

		if (infoPanel != null) {
			@SuppressWarnings("unused")
			HitSheetPrintDialog hsd = new HitSheetPrintDialog(this, infoPanel.getColor(), chars);
		}

	}

	public void showDmDialog() {

		if (infoPanel != null) {
			@SuppressWarnings("unused")
			DMSheetPrintDialog dmd = new DMSheetPrintDialog(this, infoPanel.getColor(), chars);
		}

	}

	public void showBattleDialog() {
		frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		@SuppressWarnings("unused")
		BattleHistoryDialog bhd = new BattleHistoryDialog();
		frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void showComparisonDialog() {
		frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		@SuppressWarnings("unused")
		PlayerComparisonDialog pcd = new PlayerComparisonDialog(chars);
		frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void printSheets() {

		/*
		 * Get the representation of the current printer and the current print
		 * job.
		 */
		PrinterJob printerJob = PrinterJob.getPrinterJob();
		/*
		 * Build a book containing pairs of page painters (Printables) and
		 * PageFormats. This example has a single page containing text.
		 */
		Book book = new Book();
		PageFormat pf = new PageFormat();
		Paper paper = new Paper();
		paper.setImageableArea(20, 20, 568, 748);
		pf.setPaper(paper);
		List<TiledListItem> charVector = playerList.getSelectedValuesList();
		CharacterPrinter printPanel;
		CharacterPrinter2 printPanel2;
		for (Object o : charVector) {
			DCharacter d = (DCharacter) o;
			printPanel = new CharacterPrinter(d);
			printPanel2 = new CharacterPrinter2(d);
			book.append(printPanel, pf);
			book.append(printPanel2, pf);
		}

		/*
		 * Set the object to be printed (the Book) into the PrinterJob. Doing
		 * this before bringing up the print dialog allows the print dialog to
		 * correctly display the page range to be printed and to dissallow any
		 * print settings not appropriate for the pages to be printed.
		 */
		printerJob.setPageable(book);
		/*
		 * Show the print dialog to the user. This is an optional step and need
		 * not be done if the application wants to perform 'quiet' printing. If
		 * the user cancels the print dialog then false is returned. If true is
		 * returned we go ahead and print.
		 */
		boolean doPrint = printerJob.printDialog();
		if (doPrint) {
			try {
				printerJob.print();
			} catch (PrinterException exception) {
				System.err.println("Printing error: " + exception);
			}
		}

	}

	// remove a party from the database. If there are still people in it,
	// prompt to move them to another party.
	public void removeParty() {
		InitDBC db = new InitDBC();
		String party = ((Party) partySelect.getSelectedItem()).getPartyName();
		if (db.getPartySize(party) > 0) {
			String inputValue = JOptionPane.showInputDialog(
					"There are still characters in this party.  \nWhich party do you wish to move them to?  \nLeave blank to delete all characters in the party.");

			if (inputValue != null) {
				if (!inputValue.equals("")) {
					db.shiftParty(party, inputValue);
				}
				db.removeParty(party);
			}
		} else {
			db.removeParty(party);
		}

		loadCampaign();
		playerList.setListData(new Vector<Participant>());
	}

	public void deleteCharacter() {
		InitDBC db = new InitDBC();
		List<TiledListItem> v = playerList.getSelectedValuesList();
		for (Object o : v) {
			DCharacter c = (DCharacter) o;
			db.deletePlayer(c.getID());
		}
		loadParty();
	}

	public void clear() {
		InitDBC db = new InitDBC();
		db.clearPlayers();
	}

	public void processImport(String filename) {
		try {

			Vector<DCharacter> characters = new Vector<DCharacter>();
			Vector<Importable> libraryItems = new Vector<Importable>();

			logger.log("ATTEMPTING TO IMPORT " + filename);
			FileReader fos = new FileReader(filename);
			BufferedReader reader = new BufferedReader(fos);
			String line = reader.readLine();
			StringBuffer charString = new StringBuffer();
			logger.log("READING FILE");

			// read in the file one line at a time
			while (line != null) {
				charString.append(line);

				// if we are reading in a character, process appropriately.
				if (line.startsWith("<DCharacter>")) {
					while (line != null && line.indexOf("</DCharacter>") < 0) {
						line = reader.readLine();
						charString.append(addLineReturn(line));
					}
					if (line != null) {
						logger.log("IMPORTING PLAYER");
						characters.add(loadCharacter(charString.toString()));
						line = "";
						charString = new StringBuffer();
					}

				} else {
					try {

						String className = line.substring(line.indexOf("<") + 1, line.indexOf(">"));

						while (line != null && line.indexOf("</" + className + ">") < 0) {
							line = reader.readLine();
							charString.append(addLineReturn(line));
						}
						if (line != null) {

							Importable i = (Importable) Class.forName("initcheck.database." + className).newInstance();

							Tag t = Tag.getTag(charString.toString());

							i.readImport(t.getTagBody());

							libraryItems.add(i);

							line = "";
							charString = new StringBuffer();
						}

					} catch (Exception e) {
						e.printStackTrace();
						@SuppressWarnings("unused")
						MessageDialog md = new MessageDialog("Error",
								"An unsupported data type, " + line + " was encountered while loading the data file.");

					}
				}
				line = reader.readLine();
			}

			logger.log("CLEANING UP");
			reader.close();
			if (characters.size() > 0) {
				LoadVerifyDialog verify = new LoadVerifyDialog(this, characters);
				verify.toFront();
			}
			if (libraryItems.size() > 0) {
				LoadLibraryVerifyDialog verifyLibrary = new LoadLibraryVerifyDialog(this, libraryItems);
				verifyLibrary.toFront();
			}

		} catch (Exception e) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error ", "Error Importing player : " + e.toString());
			logger.log("error", "Error Importing player : " + e.toString());
		}
	}

	public void importPlayers() {
		JFileChooser fc = new JFileChooser("export");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = fc.showOpenDialog(frame);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String filename = fc.getSelectedFile().getAbsolutePath();
			processImport(filename);
		}

		Party party = (Party) partySelect.getSelectedItem();

		loadCampaign();
		partySelect.setSelectedParty(party);

	}

	public String addLineReturn(String s) {
		if (!s.startsWith("<")) {
			return "\n" + s;
		}
		return s;
	}

	public DCharacter loadCharacter(String s) {
		Tag t = Tag.getTag(s);

		DCharacter d = new DCharacter();

		if (t.getTagName().equals("DCharacter")) {
			d.importInfo(t.getTagBody());
		}

		return d;
	}

	public void exportCurrentTab() {
		LibraryPanel currentTab = (LibraryPanel) tabbedPane.getSelectedComponent();
		currentTab.exportAll();
	}

	public void export() {
		InitDBC db = new InitDBC();
		Vector<DCharacter> charVector = db.getPlayers(new DCharacter());
		exportAll(charVector);
	}

	public void exportAll(List<?> charVector) {
		JFileChooser fc = new JFileChooser("export");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = fc.showSaveDialog(frame);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {

				String filename = fc.getSelectedFile().getAbsolutePath();
				FileOutputStream fos = new FileOutputStream(filename);
				PrintWriter writer = new PrintWriter(fos);

				for (Object o : charVector) {

					Exportable d = (Exportable) o;

					writer.write(d.exportFormat());

				}
				writer.close();
			} catch (Exception e) {
				logger.log("error", "Error in Export All : " + e.toString());
				e.printStackTrace();
			}
		}

	}

	public void broadcast(List<TiledListItem> charVector) {
		for (Object o : charVector) {
			DCharacter d = (DCharacter) o;
			groupWriter.sendCharacter(d);
		}
	}

	public void broadcastToServer(List<TiledListItem> charVector) {
		for (Object o : charVector) {
			DCharacter d = (DCharacter) o;
			groupWriter.sendServerUpdates(d);
		}
	}

	public void uploadExport() {
		JFileChooser fc = new JFileChooser("export");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = fc.showSaveDialog(frame);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {

				String filepath = fc.getSelectedFile().getAbsolutePath();
				String filename = fc.getSelectedFile().getName();
				filename += DateUtil.getDateString(new java.util.Date(), "yyyyMMMddhhmmss");

				// MessageDialog.display("Uploading Information", "Uploading
				// file "+filepath);

				LoadFileProgressPanel lfp = new LoadFileProgressPanel(ufd, 30, "Connecting to the internet");
				ufd.setMainWindow(lfp);
				ufd.pack();
				ufd.setLocationRelativeTo(null);
				ufd.setVisible(true);

				FileInputStream fis = new FileInputStream(filepath);

				lfp.setMaxValue(4);
				// set up client
				lfp.signal("Opening FTP Connection");
				FtpClient ftp = new FtpClient("bumfriends.dyndns.org");
				lfp.setValue(1);

				// log in
				lfp.signal("Logging in to server");
				ftp.login("bumfriend", "ferd");
				lfp.setValue(2);

				// go to the updates directory

				ftp.cd("updates");

				// send the file
				lfp.signal("Uploading file " + filepath);
				lfp.setValue(3);
				ftp.putBinary(filename, fis);
				logger.log("put file");
				lfp.signal("Logging out");
				ftp.closeServer();
				lfp.setValue(4);

				ufd.setVisible(false);
			} catch (Exception e) {
				logger.log("error", "Error in upload : " + e.toString());
				MessageDialog.display("Error", e.toString());
			}
		}
	}

	public void exportParty() {

		InitDBC db = new InitDBC();
		DCharacter d = new DCharacter();
		d.setPartyId(((Party) partySelect.getSelectedItem()).getId());
		Vector<DCharacter> charVector = db.getPlayers(d);

		exportAll(charVector);
	}

	public void exportSelectedItems() {
		LibraryPanel currentTab = (LibraryPanel) tabbedPane.getSelectedComponent();
		currentTab.exportSelected();
	}

	public void exportSelected() {

		List<TiledListItem> charVector = playerList.getSelectedValuesList();
		if (charVector.isEmpty()) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error", "No Characters Were Selected.");
			return;
		}

		exportAll(charVector);
	}

	public void broadcastSelected() {
		List<TiledListItem> charVector = playerList.getSelectedValuesList();
		if (charVector.isEmpty()) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error", "No Characters Were Selected.");
			return;
		}
		if (charVector.size() > 1) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error", "You can only broadcast one character at a time.");
			return;
		}

		broadcast(charVector);
	}

	public void broadcastToServerSelected() {
		List<TiledListItem> charVector = playerList.getSelectedValuesList();
		if (charVector.isEmpty()) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error", "No Characters Were Selected.");
			return;
		}

		broadcastToServer(charVector);
	}

	public void broadcastTest() {
		groupWriter.sendTest();
	}

	public JFrame getFrame() {
		return frame;
	}

	public void showPlayerAddDialog() {
		PlayerAddDialog d = new PlayerAddDialog(this);
		d.pack();
		d.setLocationRelativeTo(null);
		d.setVisible(true);
	}

	public void showPlayerStatDialog(DCharacter c, int i, int mode) {
		frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		PlayerStatDialog d = new PlayerStatDialog(this, c, i, mode);
		d.setSkin(skin);
		d.pack();
		d.setSize(1020, 600);
		d.setLocationRelativeTo(null);
		d.setVisible(true);

		frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void showComparisonDialog(DCharacter newchar) {
		InitDBC db = new InitDBC();

		DCharacter oldchar = new DCharacter();
		oldchar.setName(newchar.getName());
		oldchar.setParty(newchar.getParty());
		oldchar = db.getPlayer(oldchar);
		if (oldchar != null) {
			@SuppressWarnings("unused")
			LoadComparisonDialog lcd = new LoadComparisonDialog(oldchar, newchar);
		} else {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Character Not Found",
					"That player does not exist in the database under that name/party.");
		}
	}

	public Party getCurrentParty() {
		return (Party) partySelect.getSelectedItem();
	}

	public void loadParty() {
		String party = ((Party) partySelect.getSelectedItem()).getPartyName();
		String player = (String) playerSelect.getSelectedItem();
		String campaign = ((Campaign) campaignSelect.getSelectedItem()).getId();

		if (!party.equals("Select Party")) {
			loadParty(campaign, party, player);
		} else {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error", "Please select a party to load.");
		}
	}

	public void loadParty(String campaign, String party, String p) {

		if (party.equals("Select Party")) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error", "Please select a party to load.");
			return;
		}

		InitDBC db = new InitDBC();
		Vector<String> names = db.getPartyCharacterNames(campaign, party, p);
		LoadPartyProgressPanel lpp = new LoadPartyProgressPanel(names.size(), lpd);
		lpd.setMainWindow(lpp);
		lpd.pack();
		lpd.setLocationRelativeTo(null);
		lpd.setVisible(true);

		chars = db.getParty(campaign, party, p, lpp, dmMode);
		playerList.setListData(chars);

		if (infoPanel == null) {
			infoPanel = new PartyInfoPanel(party, chars);
			mainPanel.invalidate();
			mainPanel.add(infoPanel, BorderLayout.EAST);
			mainPanel.validate();
		} else {
			infoPanel.setParty(party);
			infoPanel.setChars(chars);
		}

		lpd.setVisible(false);
		if (chars.size() > 0) {
			playerList.setSelectedIndex(0);
		}
	}

	public void reloadPlayer() {
		int idx = playerList.getSelectedIndex();
		DCharacter sel = (DCharacter) playerList.getSelectedValue();

		if (idx >= 0) {
			InitDBC db = new InitDBC();
			DCharacter dc = db.getPlayer(sel.getID());

			chars.setElementAt(dc, idx);
			playerList.setListData(chars);
			playerList.setSelectedIndex(idx);
		}
	}

	public DCharacter getCurrentCharacter() {
		return (DCharacter) playerList.getSelectedValue();
	}

	public void addCharacter(DCharacter c) {
		InitDBC db = new InitDBC();
		int i = db.addPlayer(c);
		c.setID(i);
		chars.add(c);
		playerList.setListData(chars);
		showPlayerStatDialog(c, 0, MOD);
	}

	public void modCharacter(DCharacter c, int i) {
		InitDBC db = new InitDBC();
		String errors = db.updatePlayer(c);
		if (!errors.equals("")) {
			JOptionPane.showConfirmDialog(null, errors, "Error!", JOptionPane.CANCEL_OPTION);
		}
		reloadPlayer();
	}

	public void addNewParty() {
		String inputValue = JOptionPane.showInputDialog("Enter The New Party Name");
		if (inputValue != null && !inputValue.equals("")) {
			PartyDAO db = new PartyDAO();
			Party p = new Party();
			p.setPartyName(inputValue);
			p.setCampaignId(((Campaign) campaignSelect.getSelectedItem()).getId());
			p.setPartyType("PC");
			int id = db.addOrUpdateParty(p);
			p.setId("" + id);
			loadCampaign();
			// loadParty(inputValue, "All Players");
			partySelect.setCampaign(((Campaign) campaignSelect.getSelectedItem()).getId(), dmMode);
			partySelect.setSelectedParty(p);
		}
	}

	public void processTest() {
		JOptionPane.showMessageDialog(null, "I AM TESTING");
	}

	public void processXpUpdate(ArrayList<String> s, Hashtable<String, String> bonusHash, String baseXPperPlayer) {
		StringBuffer message = new StringBuffer("XP Update Arrived \n");

		message.append("Base XP : ");
		message.append(baseXPperPlayer);
		message.append("\n");
		message.append("Player List\n");
		for (String str : s) {
			message.append(str + "\n");
		}

		int option = JOptionPane.showConfirmDialog(null, message.toString(), "Accept Updates?",
				JOptionPane.YES_NO_OPTION);

		logger.log("OPTION IS " + option);
		if (option == JOptionPane.YES_OPTION) {
			logger.log("PROCESSING UPDATES, looking at " + chars.size());

			for (DCharacter dc : chars) {
				logger.log("LOOKING FOR " + dc.getName());
				if (s.contains(dc.getName())) {
					int xpVal = 0;
					String xp = bonusHash.get(dc.getName());
					if (xp != null) {
						xpVal = Integer.parseInt(xp);

					}
					xpVal += Integer.parseInt(baseXPperPlayer);
					dc.setXp(dc.getXp() + xpVal);
					modCharacter(dc, 0);
				} else {
					logger.log("DIDNT FIND THEM");
				}

			}
		}

	}

	public void processImage(ImageIcon imageIcon, String imageName) {

		File f = new File(imageName);

		if (!f.exists()) {

			Image image = imageIcon.getImage();
			RenderedImage rendered = null;
			if (image instanceof RenderedImage) {
				rendered = (RenderedImage) image;
			} else {
				BufferedImage buffered = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(),
						BufferedImage.TYPE_INT_RGB);
				Graphics2D g = buffered.createGraphics();
				g.drawImage(image, 0, 0, null);
				g.dispose();
				rendered = buffered;
			}
			try {
				ImageIO.write(rendered, "JPEG", new File(imageName));
			} catch (Exception e) {

			}
		} else {
			logger.log("IMAGE EXISTS");
		}
	}

	public void processCharUpdate(DCharacter d) {

		int option = JOptionPane.showConfirmDialog(frame, "Accept Update for " + d.getName(), "Update Received",
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, d.getIconObj());
		if (option == JOptionPane.YES_OPTION) {
			d.writeImportCharacter(true);
		}
		if (d.getIconObj() != null) {
			processImage(d.getIconObj(), d.getIcon());
		}
		loadParty();
	}

	/**
	 * Describe <code>main</code> method here.
	 * 
	 * @param args
	 *            a <code>String[]</code> value
	 */
	public static void main(String[] args) {

		VersionDAO vdb = new VersionDAO();

		frame = new JFrame("Player Manager " + vdb.getVersion());
		Vector<String> filenames = new Vector<String>();
		filenames.add("images/dice.jpe");
		filenames.add("images/dice2.jpe");
		filenames.add("images/dice3.jpe");
		filenames.add("images/dice4.jpe");
		filenames.add("images/dice5.jpe");
		filenames.add("images/dice6.jpe");
		filenames.add("images/dice7.jpe");
		filenames.add("images/dice8.jpe");

		SplashScreen ss = new SplashScreen(frame);
		ss.display(filenames, 9000);

		PropertyConfigurator.configure("managerLog4j.cfg");

		PlasticXPLookAndFeel.setCurrentTheme(new SkyRed());

		// PlasticLookAndFeel.setCurrentTheme(new InitTheme());
		try {
			// UIManager.setLookAndFeel(new PlasticXPLookAndFeel());

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
			// UIManager.put("nimbusLightBackground",InitColor.fadedRed);
			UIManager.put("ComboBox.background", InitColor.fadedRed);

			// UIManager.put("TabbedPane:TabbedPaneTab[Enabled].backgroundPainter",
			// new TabPainter(TabPainter.BACKGROUND_ENABLED));
			// UIManager.put("TabbedPane:TabbedPaneTab[Disabled].backgroundPainter",
			// new TabPainter(TabPainter.BACKGROUND_DISABLED));
			// UIManager.put("TabbedPane:TabbedPaneTab[Enabled+MouseOver].backgroundPainter",
			// new TabPainter(TabPainter.BACKGROUND_ENABLED_MOUSEOVER));
			// UIManager.put("TabbedPane:TabbedPaneTab[Enabled+Pressed].backgroundPainter",
			// new TabPainter(TabPainter.BACKGROUND_ENABLED_PRESSED));
			// UIManager.put("TabbedPane:TabbedPaneTab[Selected].backgroundPainter",
			// new TabPainter(TabPainter.BACKGROUND_SELECTED));
			// UIManager.put("TabbedPane:TabbedPaneTab[Disabled+Selected].backgroundPainter",
			// new TabPainter(TabPainter.BACKGROUND_SELECTED_DISABLED));
			// UIManager.put("TabbedPane:TabbedPaneTab[Focused+Selected].backgroundPainter",
			// new TabPainter(TabPainter.BACKGROUND_SELECTED_FOCUSED));
			// UIManager.put("TabbedPane:TabbedPaneTab[MouseOver+Selected].backgroundPainter",
			// new TabPainter(TabPainter.BACKGROUND_SELECTED_MOUSEOVER));
			// UIManager.put("TabbedPane:TabbedPaneTab[Focused+MouseOver+Selected].backgroundPainter",
			// new
			// TabPainter(TabPainter.BACKGROUND_SELECTED_MOUSEOVER_FOCUSED));
			// UIManager.put("TabbedPane:TabbedPaneTab[Pressed+Selected].backgroundPainter",
			// new TabPainter(TabPainter.BACKGROUND_SELECTED_PRESSED));
			// UIManager.put("TabbedPane:TabbedPaneTab[Focused+Pressed+Selected].backgroundPainter",
			// new TabPainter(TabPainter.BACKGROUND_SELECTED_PRESSED_FOCUSED));
			// dialogTheme.put("TabbedPane:TabbedPaneTabArea[Disabled].backgroundPainter",
			// new AreaPainter(AreaPainter.BACKGROUND_DISABLED));
			// dialogTheme.put("TabbedPane:TabbedPaneTabArea[Enabled+MouseOver].backgroundPainter",
			// new AreaPainter(AreaPainter.BACKGROUND_ENABLED_MOUSEOVER));
			// dialogTheme.put("TabbedPane:TabbedPaneTabArea[Enabled+Pressed].backgroundPainter",
			// new AreaPainter(AreaPainter.BACKGROUND_ENABLED_PRESSED));
			// dialogTheme.put("TabbedPane:TabbedPaneTabArea[Enabled].backgroundPainter",
			// new AreaPainter(AreaPainter.BACKGROUND_ENABLED));

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
		defaults.put("TabbedPane.tabsOpaque", true);
		defaults.put("TabbedPane.contentBorderInsets", new Insets(15, 15, 15, 15));

		// defaults.put("TabbedPane:TabbedPaneTab[Enabled].backgroundPainter",
		// new TabPainter(TabPainter.BACKGROUND_ENABLED));
		// defaults.put("TabbedPane:TabbedPaneTab[Disabled].backgroundPainter",
		// new TabPainter(TabPainter.BACKGROUND_DISABLED));
		// defaults.put("TabbedPane:TabbedPaneTab[Enabled+MouseOver].backgroundPainter",
		// new TabPainter(TabPainter.BACKGROUND_ENABLED_MOUSEOVER));
		// defaults.put("TabbedPane:TabbedPaneTab[Enabled+Pressed].backgroundPainter",
		// new TabPainter(TabPainter.BACKGROUND_ENABLED_PRESSED));
		// defaults.put("TabbedPane:TabbedPaneTab[Selected].backgroundPainter",
		// new TabPainter(TabPainter.BACKGROUND_SELECTED));
		// defaults.put("TabbedPane:TabbedPaneTab[Disabled+Selected].backgroundPainter",
		// new TabPainter(TabPainter.BACKGROUND_SELECTED_DISABLED));
		// defaults.put("TabbedPane:TabbedPaneTab[Focused+Selected].backgroundPainter",
		// new TabPainter(TabPainter.BACKGROUND_SELECTED_FOCUSED));
		// defaults.put("TabbedPane:TabbedPaneTab[MouseOver+Selected].backgroundPainter",
		// new TabPainter(TabPainter.BACKGROUND_SELECTED_MOUSEOVER));
		// defaults.put("TabbedPane:TabbedPaneTab[Focused+MouseOver+Selected].backgroundPainter",
		// new TabPainter(TabPainter.BACKGROUND_SELECTED_MOUSEOVER_FOCUSED));
		// defaults.put("TabbedPane:TabbedPaneTab[Pressed+Selected].backgroundPainter",
		// new TabPainter(TabPainter.BACKGROUND_SELECTED_PRESSED));
		// defaults.put("TabbedPane:TabbedPaneTab[Focused+Pressed+Selected].backgroundPainter",
		// new TabPainter(TabPainter.BACKGROUND_SELECTED_PRESSED_FOCUSED));

		final PlayerManager app = new PlayerManager();

		// create the main screen
		Component contents = app.createComponents();
		frame.getContentPane().add(contents, BorderLayout.CENTER);

		// Finish setting up the frame, and show it. Make sure that you trap the
		// window killing "x" to save things.
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// show the interface
		frame.setSize(new Dimension(1020, 600));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}

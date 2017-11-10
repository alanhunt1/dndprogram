package initcheck;

import initcheck.database.Monster;
import initcheck.database.MonsterDAO;
import initcheck.dungeon.Room;
import initcheck.graphics.TiledDialog;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MonsterDialog extends TiledDialog implements PlayerDialogParent {

	static final long serialVersionUID = 1;

	public static final int ADD = 1;

	public static final int MOD = 2;

	public static final int DISPLAY = 3;

	private JTextField monsterNum = new JTextField(10);

	private JTextField monsterMod = new JTextField(10);

	private JTextField monsterName = new JTextField(10);

	private JTextField hitDice = new JTextField(10);

	private JTextField challengeRating = new JTextField(10);

	private JTextField stunPct = new JTextField(10);

	private Monster selectedMonster = null;

	private PanelButton cancelButton = new PanelButton("Cancel");

	private PanelButton saveEncounterButton = new PanelButton("Save");

	private PanelButton runButton = new PanelButton("Run Encounter", 80);

	private PanelButton runAddButton = new PanelButton("Add To Encounter", 100);

	private PanelButton removeButton = new PanelButton("Remove", 70);

	private PanelButton randomButton = new PanelButton("Random", 70);

	private PanelButton saveButton = new PanelButton("Save Changes", 90);

	private PanelButton selectButton = new PanelButton("Browse");

	private PanelButton searchButton = new PanelButton("Search");

	private JTextField searchText = new JTextField(15);

	private JList monsterList = new JList();

	private JList encounterList = new JList();

	private JScrollPane scrollList = new JScrollPane(monsterList);

	private JScrollPane encounterScroll = new JScrollPane(encounterList);

	private JComboBox searchTypes = new JComboBox();

	private JComboBox sortOrder = new JComboBox();

	private InitLogger logger = new InitLogger(this);

	private TiledGridPanel contents = new TiledGridPanel(
			"images/rockLighter.jpg");

	private Vector<MonsterGroup> encounterVector = new Vector<MonsterGroup>();

	private Vector<DCharacter> npcVector = new Vector<DCharacter>();

	private JTabbedPane tabPane = new JTabbedPane();

	private SelectPlayerPanel spp;

	Room room = null;

	private Random rnd = new Random();

	double partyLevel;

	private MonsterDialogParent owner;

	/**
	 * Get the PartyLevel value.
	 * 
	 * @return the PartyLevel value.
	 */
	public double getPartyLevel() {
		return partyLevel;
	}

	/**
	 * Set the PartyLevel value.
	 * 
	 * @param newPartyLevel
	 *            The new PartyLevel value.
	 */
	public void setPartyLevel(double newPartyLevel) {
		this.partyLevel = newPartyLevel;
	}

	/**
	 * Get the Room value.
	 * 
	 * @return the Room value.
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * Set the Room value.
	 * 
	 * @param newRoom
	 *            The new Room value.
	 */
	public void setRoom(Room newRoom) {
		this.room = newRoom;
	}

	public MonsterDialog(final MonsterDialogParent owner) {

		super(owner.getFrame(), "Create Encounter", true);
		doInit(owner);
	}

	public void setEncounter(Encounter e) {
		encounterVector = e.getMonsterGroups();
		npcVector = e.getNpcOpponents();
		refreshEncounterList();

	}

	public void doInit(final MonsterDialogParent owner) {
		this.owner = owner;
		spp = new SelectPlayerPanel(this, new Vector<DCharacter>());
		spp.setMode("Opponent");
		int ypos = 0;

		TiledPanel outerPanel = new TiledPanel(bgImage);
		outerPanel.setLayout(new BorderLayout());

		monsterList.setCellRenderer(new BrowseMonsterCellRenderer());
		monsterList.setFont(new Font("Courier", Font.PLAIN, 12));

		// Box submit = new Box(BoxLayout.Y_AXIS);
		contents.doLayout(new JLabel("Name : "), 0, ypos);
		contents.doLayout(monsterName, 1, ypos);

		contents.doLayout(new JLabel("Number : "), 2, ypos);
		contents.doLayout(monsterNum, 3, ypos);

		contents.doLayout(new JLabel("Init Mod : "), 4, ypos);
		contents.doLayout(monsterMod, 5, ypos);
		ypos++;

		contents.doLayout(new JLabel("Hit Dice : "), 0, ypos);
		contents.doLayout(hitDice, 1, ypos);

		contents.doLayout(new JLabel("Challenge Rating : "), 2, ypos);
		contents.doLayout(challengeRating, 3, ypos);

		contents.doLayout(new JLabel("Stun Percentage : "), 4, ypos);
		contents.doLayout(stunPct, 5, ypos);
		ypos++;

		contents.doLayout(encounterScroll, 0, ypos, 6, 1);
		ypos++;

		JPanel remWrap = new JPanel();
		remWrap.setOpaque(false);
		remWrap.add(removeButton);
		remWrap.add(saveButton);

		contents.doLayout(remWrap, 0, ypos, 6, 1);
		ypos++;

		Box finderPanel = new Box(BoxLayout.Y_AXIS);
		finderPanel.setOpaque(false);
		JPanel browsePanel = new JPanel(new BorderLayout());
		browsePanel.setOpaque(false);
		searchTypes.addItem("Name");
		searchTypes.addItem("Challenge Rating");
		JPanel searchPanel = new JPanel();
		searchPanel.setOpaque(false);
		sortOrder.addItem("Name");
		sortOrder.addItem("Challenge Rating");
		searchPanel.add(new JLabel("Search On "));
		searchPanel.add(searchTypes);
		searchPanel.add(new JLabel("Sort By "));
		searchPanel.add(sortOrder);
		JPanel topPanel = new JPanel();
		topPanel.setOpaque(false);
		topPanel.add(selectButton);
		topPanel.add(searchButton);
		topPanel.add(searchText);
		finderPanel.add(topPanel);
		finderPanel.add(searchPanel);
		// topPanel.add(searchTypes);
		browsePanel.add(finderPanel, BorderLayout.NORTH);
		browsePanel.add(scrollList, BorderLayout.CENTER);
		browsePanel.setBorder(BorderFactory.createEtchedBorder());
		JPanel buttons = new JPanel();
		buttons.setOpaque(false);
		buttons.add(cancelButton);
		buttons.add(saveEncounterButton);
		buttons.add(randomButton);
		buttons.add(runButton);
		buttons.add(runAddButton);
		buttons.setOpaque(false);

		tabPane.add(browsePanel, "Monsters");
		tabPane.add(spp, "NPC Opponents");

		contents.doLayout(tabPane, 0, ypos, 6, 1);

		outerPanel.add(contents, BorderLayout.CENTER);
		outerPanel.add(buttons, BorderLayout.SOUTH);
		outerPanel.setBorder(BorderFactory.createEmptyBorder(30, // top
				30, // left
				10, // bottom
				30) // right
				);

		getContentPane().add(outerPanel, BorderLayout.CENTER);
		pack();

		// Finish setting up the frame, and show it.
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

			}
		});

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int idx = encounterList.getSelectedIndex();
					if (selectedMonster == null) {
						selectedMonster = new Monster();
					}

					selectedMonster.setName(monsterName.getText());
					selectedMonster.setMod(Integer.parseInt(monsterMod
							.getText()));
					selectedMonster.setChallengeRating(challengeRating
							.getText());
					selectedMonster.setHitDie(hitDice.getText());
					selectedMonster.setStunPercentage(stunPct.getText());
					MonsterGroup mg = new MonsterGroup(selectedMonster, Integer
							.parseInt(monsterNum.getText()));
					encounterVector.setElementAt(mg, idx);
					refreshEncounterList();

				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					String mesg = "You must fill in all fields";
					JOptionPane.showMessageDialog(null, mesg,
							"Pardon Me, Sir...", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		randomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getRandomEncounter();
			}
		});

		saveEncounterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveEncounter();
				setVisible(false);
			}
		});

		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int idx = encounterList.getSelectedIndex();
				encounterVector.removeElementAt(idx);
				refreshEncounterList();

			}
		});

		runAddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				owner.runEncounter(new Encounter(encounterVector, npcVector),
						false);
				setVisible(false);
			}
		});

		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				owner.runEncounter(new Encounter(encounterVector, npcVector),
						true);
				setVisible(false);
			}
		});

		selectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getMonsterList((String) (sortOrder.getSelectedItem()));
			}
		});
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getMonsterList(searchText.getText(), (String) (searchTypes
						.getSelectedItem()), (String) (sortOrder
						.getSelectedItem()));
			}
		});

		monsterList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					Monster m = (Monster) monsterList.getSelectedValue();
					showMonsterStatDialog(m, 0, MonsterDisplayPanel.DISPLAY);
				}
				if (e.getClickCount() == 2) {
					Monster m = (Monster) monsterList.getSelectedValue();
					selectedMonster = m;
					int numMonsters = getNumMonsters(m.getName().trim());
					if (numMonsters > 0) {
						MonsterGroup mg = new MonsterGroup(selectedMonster,
								numMonsters);
						encounterVector.add(mg);

						refreshEncounterList();

						encounterList.setSelectedIndex(encounterList.getModel()
								.getSize() - 1);
					}
				}
			}
		});

		encounterList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				try {
					if (encounterList.getSelectedIndex() >= 0) {
						MonsterGroup mg = (MonsterGroup) encounterList
								.getSelectedValue();
						selectedMonster = mg.getM();
						Monster m = selectedMonster;
						monsterName.setText(m.getName().trim());
						monsterMod.setText("" + m.getMod());
						monsterNum.setText("" + mg.getCount());
						hitDice.setText(m.getHitDie());
						stunPct.setText(m.getStunPercentage());
						challengeRating.setText(m.getChallengeRating());
					}
				} catch (ClassCastException cce) {

				}
			}
		});

	}

	public void getRandomEncounter() {
		CreateEncounterDialog ced = new CreateEncounterDialog();

		int partySize = 12;
		int difficulty = getRandom(6) - 1;
		int density = getRandom(5) - 1;
		int densityRange = 5;
		int diversity = getRandom(4) - 1;
		int diversityRange = 4;

		ced.getRandomEncounter(partyLevel, partySize, difficulty, density,
				densityRange, diversity, diversityRange);

		encounterVector.addAll(ced.getMonsterGroups());
		refreshEncounterList();

	}

	public int getRandom(int i) {
		int j = rnd.nextInt(i);
		return j;

	}

	public void saveEncounter() {
		if (room != null) {
			room.setMonsters(encounterVector);
		}
		owner.saveEncounter(new Encounter(encounterVector, npcVector));
	}

	public void showMonsterStatDialog(Monster m, int i, int mode) {
		@SuppressWarnings("unused")
		MonsterStatDialog d = new MonsterStatDialog(this, m, ADD, null);

	}

	private void getMonsterList(String order) {
		if (order.equals("Challenge Rating")) {
			order = "CHALLENGE, NAME";
		}
		MonsterDAO db = new MonsterDAO();
		Vector<Monster> v = db.getMonsters(null, null, order);
		monsterList.setListData(v);
	}

	private void getMonsterList(String s, String cr, String order) {
		if (order.equals("Challenge Rating")) {
			order = "CHALLENGE, NAME";
		}
		logger.log("Getting list for " + s + " :" + cr + ":");
		MonsterDAO db = new MonsterDAO();
		Vector<Monster> v;
		if (cr.equals("Challenge Rating")) {
			v = db.getMonsters(null, s, order);
		} else {
			v = db.getMonsters(s, null, order);
		}
		monsterList.setListData(v);
	}

	private int getNumMonsters(String s) {
		String inputValue = JOptionPane.showInputDialog("How Many " + s
				+ " Do You Want?");
		int num = 0;
		try {
			num = Integer.parseInt(inputValue);
		}catch(NumberFormatException nfe){
			
		}
		return num;
	}

	public JFrame getFrame() {
		return owner.getFrame();
	}

	public void setChars(Vector<DCharacter> chars, String party) {
		// need to comply with interface, but this does nothing.
	}

	public void setOpponents(Vector<DCharacter> chars) {
		npcVector.addAll(chars);
		refreshEncounterList();
	}

	private void refreshEncounterList() {
		Vector<Object> v = new Vector<Object>();
		v.addAll(encounterVector);
		v.addAll(npcVector);
		encounterList.setListData(v);
	}
}

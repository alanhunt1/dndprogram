package initcheck.server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import initcheck.DCharacter;
import initcheck.InitColor;
import initcheck.InitServer;
import initcheck.PanelButton;
import initcheck.Participant;
import initcheck.graphics.TiledList;
import initcheck.graphics.TiledPanel;

public class AlternateList extends TiledPanel {

	static final long serialVersionUID = 1;

	private TiledList monsterList = null;

	private TiledList playerList = null;

	private PanelButton monsterButton = new PanelButton("Monsters");

	private PanelButton playerButton = new PanelButton("Players");

	private InitServer owner = null;

	private String mode = "MONSTER";

	private Vector<Participant> list = new Vector<Participant>();

	private Vector<DCharacter> plist = new Vector<DCharacter>();

	private JTabbedPane tabPane = new JTabbedPane();

	private Object currentTab;

	JScrollPane scroll;

	JScrollPane pscroll;

	public AlternateList(final InitServer owner, Vector<Participant> chars, 
			ImageIcon bg) {

		super(bg);
		this.owner = owner;
		setLayout(new BorderLayout());

		monsterList = new TiledList(chars);
		monsterList.setCellRenderer(new InitListCellRenderer());
		monsterList.setBackground(Color.lightGray);
		monsterList.setFont(new Font("Courier", Font.PLAIN, 12));
		scroll = new JScrollPane(monsterList);
		JPanel selections = new JPanel();

		playerList = new TiledList(chars);
		playerList.setCellRenderer(new InitListCellRenderer());
		playerList.setBackground(Color.lightGray);
		playerList.setFont(new Font("Courier", Font.PLAIN, 12));
		pscroll = new JScrollPane(playerList);

		// selections.add(monsterButton);
		// selections.add(playerButton);

		// add(selections, BorderLayout.NORTH);

		tabPane.setOpaque(false);
		//tabPane.setUI(new TestTabUI());
		tabPane.setForeground(InitColor.woodText);
		
		tabPane.add(scroll, "Monsters");
		tabPane.add(pscroll, "Players");
		currentTab = scroll;

		add(tabPane, BorderLayout.CENTER);

		selections.setOpaque(false);

		setBorder(BorderFactory.createEmptyBorder(0, // top
				0, // left
				20, // bottom
				20) // right
		);

		setPreferredSize(new Dimension(200, 200));

		monsterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showMonsters();
			}
		});

		playerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPlayers();
			}
		});

		monsterList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				owner.setSelectedMonster(monsterList.getSelectedValue());
				if (owner.getHitSheet() != null) {
					owner.getHitSheet().setSelectedMonster(
							monsterList.getSelectedIndex());
				}
			}
		});

		playerList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				owner.setSelectedMonster(playerList.getSelectedValue());
			}
		});

		tabPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				processTabChange(e);
			}
		});

	}

	public void processTabChange(ChangeEvent e) {
		currentTab = tabPane.getSelectedComponent();
		if (currentTab != null) {
			if (currentTab == scroll) {
				showMonsters();
			} else {
				showPlayers();
			}
		}

	}

	/** 
	 * @param name the name of the monster you are looking for
	 * @return int index of the monster in the hit list
	 * 
	 * This method is used to find the index of the monster in the alternate
	 * list, which will also be the index of the monster in the hit
	 * list.
	 */
	public int getIndex(String name) {
		if (mode != "MONSTER") {
			return -1;
		}
		for (int i = 0; i < list.size(); i++) {
			if (name.equals(((Participant) list.get(i)).getName())) {
				return i;
			}
		}
		return -1;
	}

	private void showMonsters() {
		list = owner.getEngine().getMonsters();
		monsterList.setListData(list);
		mode = "MONSTER";
	}

	private void showPlayers() {
		plist = owner.getEngine().getDB().getCharacters();
		playerList.setListData(plist);
		mode = "PLAYER";
	}

	public InitServer getOwner() {
		return owner;
	}

	
	public void refreshMonsters() {
		if (currentTab == scroll) {
			showMonsters();
		} else {
			showPlayers();
		}
	}

	public void removeMonsters() {
		if (currentTab == scroll) {
			showMonsters();
		} else {
			showPlayers();
		}
	}
}

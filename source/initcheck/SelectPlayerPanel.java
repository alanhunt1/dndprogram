package initcheck;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import initcheck.character.LoadPartyProgressPanel;
import initcheck.database.InitDBC;
import initcheck.graphics.TiledDialog;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledPanel;
import initcheck.server.InitListCellRenderer;

public class SelectPlayerPanel extends TiledGridPanel {

	static final long serialVersionUID = 1;

	private JComboBox partySelect;

	private JList playerList = null;

	private PanelButton closeButton = new PanelButton("Add Selected", 120);

	private PlayerDialogParent owner = null;

	private Vector<DCharacter> chars;

	private TiledDialog lpd;

	private String mode = "Player";

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public SelectPlayerPanel(final PlayerDialogParent owner, Vector<DCharacter> chars) {

		lpd = new TiledDialog(owner.getFrame(), "Loading Party", false);

		
		this.owner = owner;
		this.chars = chars;
		InitDBC db = new InitDBC();
		
		Vector<String> partyVector = db.getParties(true);
		
		partySelect = new JComboBox(partyVector);

		JPanel contents = new JPanel(new BorderLayout());

		playerList = new JList(chars);
		playerList.setCellRenderer(new InitListCellRenderer());
		playerList.setFont(new Font("Courier", Font.PLAIN, 12));
		playerList.setBackground(Color.lightGray);

		JScrollPane scroll = new JScrollPane(playerList);
		JPanel buttons = new TiledPanel(InitImage.lightRocks);
		buttons.add(closeButton);


		buttons.setOpaque(false);

		contents.add(partySelect, BorderLayout.NORTH);
		contents.add(scroll, BorderLayout.CENTER);
		contents.setOpaque(false);
		
		setWeightX(1.0);
		setWeightY(1.0);
		doLayout(contents);
		setWeightX(0);
		setWeightY(0);
		incYPos();
		doLayout(buttons);
		

		partySelect.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					loadParty((String) partySelect.getSelectedItem());
				}
			}
		});

		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setParty();
			
			}
		});

	}

	public void removePlayer() {

	}

	public void loadParty() {
		loadParty((String) partySelect.getSelectedItem());
	}

	public void loadParty(String s) {
		setCursor(new Cursor(Cursor.WAIT_CURSOR));

		InitDBC db = new InitDBC();
		Vector<String> names = db.getPartyCharacterNames("All Campaigns", s, "All Players");
		LoadPartyProgressPanel lpp = new LoadPartyProgressPanel(names.size(),
				lpd);
		lpd.setMainWindow(lpp);
		lpd.pack();
		lpd.setLocationRelativeTo(null);
		lpd.setVisible(true);

		chars = db.getParty("All Campaigns", s, "All Players", lpp, true);

		playerList.setListData(chars);
		// owner.setParty(s);
		// owner.loadMap(s);
		// owner.updateDMSheet();
		lpd.setVisible(false);
		
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void setParty() {
		
		if (playerList.getSelectedIndex() > -1) {
			List<Object> selectedChars = playerList.getSelectedValuesList();
			chars = new Vector<DCharacter>();
			
			for (Object o:selectedChars) {
				chars.add((DCharacter) o);
				
			}
		}
		if (mode.equals("Player")) {
			owner.setChars(chars,(String) partySelect.getSelectedItem());
			//owner.getEngine().getDB().addPlayers(chars);
		}else {
			owner.setOpponents(chars);
			//owner.addOpponents(chars);
		}
		//owner.getEngine().insertParticipants(chars);
		//owner.updateDMSheet();
		//owner.setPlayers();
		//owner.refreshHitSheet();
		//owner.backupToFile();
		//owner.setParty((String) partySelect.getSelectedItem());
		//owner.repaintScreen();
		
	}

}

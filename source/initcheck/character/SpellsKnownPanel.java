package initcheck.character;

import initcheck.PanelButton;
import initcheck.character.chooser.SpellChooser;
import initcheck.database.PlayerSpells;
import initcheck.database.PlayerSpellsDAO;
import initcheck.database.PlayerSpellsMemorizedDAO;
import initcheck.database.Spell;
import initcheck.database.SpellDAO;
import initcheck.graphics.TiledList;
import initcheck.graphics.TiledTextArea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SpellsKnownPanel extends GridPanel implements
		ListSelectionListener {

	
	private static final long serialVersionUID = 1L;

	private TiledList spellList = new TiledList();

	private JScrollPane listScroll = new JScrollPane(spellList);

	private TiledTextArea description = new TiledTextArea(10, 40);

	private JScrollPane descScroll = new JScrollPane(description);

	private PlayerStatDialog owner;

	private String level;

	private String cclass;

	private PanelButton addSpell = new PanelButton("Add");

	private PanelButton addAllSpell = new PanelButton("Add All");

	private PanelButton delSpell = new PanelButton("Rem");

	private PanelButton delAllSpell = new PanelButton("Rem All");

	private PanelButton memSpell = new PanelButton("Memorize", 70);

	private SpellChooser spellChooser;

	private PlayerSpellPanel parent;
	
	public SpellsKnownPanel(PlayerStatDialog owner, PlayerSpellPanel parent,
			String level, String cclass) {
		this.owner = owner;
		this.parent = parent;
		this.level = level;
		this.cclass = cclass;

		

		spellChooser = new SpellChooser(level, cclass);
		description.setWrapStyleWord(true);
		description.setLineWrap(true);

		spellList.setListData(owner.getChar().getSpellsKnown(level, cclass));
		spellList.addListSelectionListener(this);
		spellList.setCellRenderer(new GenericListCellRenderer());

		setWeightX(0.5);
		setWeightY(1.0);
		doLayout(listScroll, 0);
		doLayout(descScroll, 1);
		setWeightX(0);
		setWeightY(0);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(spellChooser);
		buttonPanel.add(addSpell);
		buttonPanel.add(addAllSpell);
		buttonPanel.add(delSpell);
		buttonPanel.add(delAllSpell);
		buttonPanel.add(memSpell);
		
		incYPos();
		doLayout(buttonPanel, 0, ypos, 2, 1);

		addSpell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addSpell();
			}
		});

		addAllSpell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAllSpell();
			}
		});

		delSpell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeSpell();
			}
		});

		delAllSpell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAllSpell();
			}
		});

		memSpell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				memSpell();
			}
		});
		
	}

	private void addAllSpell() {
		SpellDAO db = new SpellDAO();
		Vector<Spell> v2 = db.getSpells(level, cclass);
		PlayerSpellsDAO psdb = new PlayerSpellsDAO();
		for (int i = 0; i < v2.size(); i++) {
			Spell f = (Spell) v2.get(i);
			PlayerSpells ps = new PlayerSpells();
			ps.setSpellId(f.getId());
			ps.setPlayerId("" + owner.getChar().getID());
			ps.setSpellClass(cclass);
			if (!psdb.exists(ps)) {
				psdb.addPlayerSpells(ps);
			}
		}
		owner.getChar().setSpellsKnown(
				psdb.getPlayerSpells("" + owner.getChar().getID()));
		spellList.setListData(owner.getChar().getSpellsKnown(level, cclass));
	}

	private void removeAllSpell() {
		PlayerSpellsDAO psdb = new PlayerSpellsDAO();
		Vector<PlayerSpells> spellsKnown = owner.getChar().getSpellsKnown(level, cclass);
		
		for (int i = 0; i < spellsKnown.size(); i++){
			PlayerSpells ps = (PlayerSpells) spellsKnown.get(i);
			psdb.deletePlayerSpells(ps);
		}
		owner.getChar().setSpellsKnown(
				psdb.getPlayerSpells("" + owner.getChar().getID()));	
		spellList.setListData(owner.getChar().getSpellsKnown(level, cclass));
	}

	private void memSpell(){
		Spell s = (Spell) spellList.getSelectedValue();
		PlayerSpellsMemorizedDAO psdb = new PlayerSpellsMemorizedDAO();
		psdb.addPlayerSpellsMemorized(s, "" + owner.getChar().getID(), cclass);
		owner.getChar().setSpellsMemorized(
				psdb.getPlayerSpellsMemorized("" + owner.getChar().getID()));
		parent.updateSpellsMemorized();
	}
	
	private void addSpell() {
		Spell s = (Spell) spellChooser.getSelectedItem();
		PlayerSpellsDAO psdb = new PlayerSpellsDAO();
		psdb.addPlayerSpells(s, "" + owner.getChar().getID(), cclass);
		owner.getChar().setSpellsKnown(
				psdb.getPlayerSpells("" + owner.getChar().getID()));
		spellList.setListData(owner.getChar().getSpellsKnown(level, cclass));
	}

	private void removeSpell() {
		PlayerSpells ps = (PlayerSpells) spellList.getSelectedValue();
		PlayerSpellsDAO psdb = new PlayerSpellsDAO();
		psdb.deletePlayerSpells(ps);
		owner.getChar().setSpellsKnown(
				psdb.getPlayerSpells("" + owner.getChar().getID()));
		spellList.setListData(owner.getChar().getSpellsKnown(level, cclass));
	}

	public void valueChanged(ListSelectionEvent e) {
		try {
			PlayerSpells f = (PlayerSpells) spellList.getSelectedValue();
			description.setText(f.getDisplayFormat());
			description.setCaretPosition(0);
			spellList.ensureIndexIsVisible(spellList.getSelectedIndex());
		} catch (Exception ex) {

		}
	}

	/**
	 * Get the Cclass value.
	 * 
	 * @return the Cclass value.
	 */
	public String getCclass() {
		return cclass;
	}

	/**
	 * Set the Cclass value.
	 * 
	 * @param newCclass
	 *            The new Cclass value.
	 */
	public void setCclass(String newCclass) {
		this.cclass = newCclass;
	}

}

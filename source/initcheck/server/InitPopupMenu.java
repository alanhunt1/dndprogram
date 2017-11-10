package initcheck.server;

import initcheck.InitServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class InitPopupMenu extends JPopupMenu implements ActionListener {
	static final long serialVersionUID = 1;

	// the popup menu

	private JMenuItem unStun;

	private JMenuItem revive;

	private JMenuItem remove;

	private JMenuItem hold;

	private JMenuItem note;

	private JMenuItem clear;

	private JMenuItem spellEffects;

	private JMenuItem splitParty;

	private JMenuItem transfer;

	private JMenuItem merge;

	private JMenuItem roundInfo;

	private InitServer owner;

	private JMenuItem selParty;
	
	private JMenuItem addXp;
	
	public InitPopupMenu(InitServer owner) {

		super();

		this.owner = owner;

		// the spell sub menu
		SpellMenu spellM = new SpellMenu(owner);
		add(spellM);

		unStun = new JMenuItem("Un-Stun");
		unStun.addActionListener(this);
		add(unStun);
		revive = new JMenuItem("Revive");
		revive.addActionListener(this);
		add(revive);
		remove = new JMenuItem("Remove");
		remove.addActionListener(this);
		add(remove);
		hold = new JMenuItem("Hold Init");
		hold.addActionListener(this);
		add(hold);
		note = new JMenuItem("Notes");
		note.addActionListener(this);
		add(note);
		clear = new JMenuItem("Clear Notes");
		clear.addActionListener(this);
		add(clear);
		spellEffects = new JMenuItem("Clear Spells");
		spellEffects.addActionListener(this);
		add(spellEffects);
		splitParty = new JMenuItem("Split Party");
		splitParty.addActionListener(this);
		add(splitParty);
		transfer = new JMenuItem("Transfer");
		transfer.addActionListener(this);
		add(transfer);
		merge = new JMenuItem("Merge");
		merge.addActionListener(this);
		add(merge);
		selParty = new JMenuItem("Select Party");
		selParty.addActionListener(this);
		add(selParty);
		roundInfo = new JMenuItem("Edit Round Info");
		roundInfo.addActionListener(this);
		add(roundInfo);
		addXp = new JMenuItem("Add XP");
		addXp.addActionListener(this);
		add(addXp);
	}

	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());

		if (source == unStun) {
			owner.getList().markListItemUnStunned();
		} else if (source == revive) {
			owner.getList().markListItemAlive();
		} else if (source == remove) {
			owner.getList().removeListItem();
		} else if (source == hold) {
			owner.getList().holdListItemInit();
		} else if (source == note) {
			owner.getList().setNotes();
		} else if (source == clear) {
			owner.getList().clearNotes();
		} else if (source == spellEffects) {
			owner.getList().clearSpellEffects();
		} else if (source == splitParty) {
			owner.splitParty();
		} else if (source == transfer) {
			owner.transferCharacters();
		} else if (source == merge) {
			owner.mergeParties();
		} else if (source == roundInfo) {
			owner.editRoundInfo();
		} else if (source == selParty) {
			owner.selectParty();
		} else if (source == addXp) {
			owner.addPlayerXP();
		}

	}
}

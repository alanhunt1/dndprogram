package initcheck.character;

import initcheck.DCharacter;
import initcheck.PanelButton;
import initcheck.database.CharClass;
import initcheck.database.Feat;
import initcheck.database.PlayerSpells;
import initcheck.database.PlayerSpellsMemorizedDAO;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledList;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class PlayerSpellPanel extends TiledGridPanel implements StatusTab {

	
	private static final long serialVersionUID = 1L;

	private TiledList spellList = new TiledList();

	private JScrollPane spellScroll = new JScrollPane(spellList);

	private TiledList knownList = new TiledList();

	private JScrollPane knownScroll = new JScrollPane(knownList);

	private final PlayerStatDialog owner;

	private JTabbedPane tabbedPane = new JTabbedPane();

	private Vector<SpellsKnownPanel> spellsKnownVector = new Vector<SpellsKnownPanel>();

	private TiledList memorizedList = new TiledList();

	private JScrollPane memorizedScroll = new JScrollPane(memorizedList);

	private PanelButton delButton = new PanelButton("Remove");

	boolean updateRequired;

	int tabIndex = 0;

	MemorizedSpellPopupMenu popupMenu;
	
	private boolean error = false;
	
	public boolean isError() {
		return error;
	}



	public void setError(boolean error) {
		this.error = error;
	}

	
	/**
	 * Get the UpdateRequired value.
	 * 
	 * @return the UpdateRequired value.
	 */
	public boolean isUpdateRequired() {
		return updateRequired;
	}

	/**
	 * Set the UpdateRequired value.
	 * 
	 * @param newUpdateRequired
	 *            The new UpdateRequired value.
	 */
	public void setUpdateRequired(boolean newUpdateRequired) {
		this.updateRequired = newUpdateRequired;
	}

	JComboBox classChooser;

	public PlayerSpellPanel(final PlayerStatDialog owner) {
		super("images/rockLighter.jpg");
		this.owner = owner;

		popupMenu = new MemorizedSpellPopupMenu(this);
		
		classChooser = new JComboBox(owner.getChar().getCasterClasses());

		spellList.setVisibleRowCount(5);
		spellList.setListData(owner.getChar().getAllSpellsPerDay());
		spellList.setCellRenderer(new SpellPerDayCellRenderer());
		spellList.setFont(new Font("Courier", Font.PLAIN, 12));

		knownList.setVisibleRowCount(5);
		knownList.setListData(owner.getChar().getAllSpellsAvailable());
		knownList.setCellRenderer(new SpellPerDayCellRenderer());
		knownList.setFont(new Font("Courier", Font.PLAIN, 12));

		memorizedList.setListData(owner.getChar().getSpellsMemorized());
		memorizedList.setCellRenderer(new MemorizedSpellCellRenderer());
		MouseListener popupListener = new MemorizedSpellPopupListener();
		memorizedList.addMouseListener(popupListener);
		
		for (int i = 0; i < 10; i++) {
			CharClass cs = (CharClass) classChooser.getSelectedItem();
			if (cs != null && owner.getChar().canCastAtLevel(cs.getCharClass(), i)) {
				SpellsKnownPanel skp = new SpellsKnownPanel(owner, this,
						"" + i, getSelectedClass());
				spellsKnownVector.add(skp);
				tabbedPane.add(skp, "" + i);
			}
		}

		setBorder(BorderFactory.createEtchedBorder());

		doLayout(new JLabel("Spells Per Day (Bonus Spells)"), 0, 2, 1);
		doLayout(new JLabel("Spells Known (Bonus Spells)"), 2, 2, 1);
		setWeightX(0.5);
		setWeightY(0.5);
		incYPos();
		doLayout(spellScroll, 0, 2, 1);
		doLayout(knownScroll, 2, 2, 1);
		incYPos();

		setWeightY(0);

		doLayout(classChooser, 0);
		doLayout(new JLabel("Spellbook"), 1);
		doLayout(new JLabel(""), 2);
		doLayout(new JLabel("Spells Memorized"), 3);
		incYPos();
		tabIndex = ypos;

		setWeightY(0.5);
		setWeightX(0.8);
		doLayout(tabbedPane, 0, 3, 2);
		setWeightX(0.2);
		doLayout(memorizedScroll, 3);
		incYPos();
		setWeightY(0.0);
		setWeightX(0.0);
		doLayout(delButton, 3);

		classChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSpellbook();
			}
		});

		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteSpell();
			}
		});
	}

	public void deleteSpell() {
		PlayerSpells ps = (PlayerSpells) memorizedList.getSelectedValue();
		PlayerSpellsMemorizedDAO psmdb = new PlayerSpellsMemorizedDAO();
		psmdb.deletePlayerSpellsMemorized(ps);
		owner.getChar().setSpellsMemorized(
				psmdb.getPlayerSpellsMemorized("" + owner.getChar().getID()));
		updateSpellsMemorized();
	}

	public void updateSpellsMemorized() {
		memorizedList.setListData(owner.getChar().getSpellsMemorized());
	}

	public DCharacter getChar(){
		return owner.getChar();
	}
	
	public void adjustSpell(Feat f){
		
		PlayerSpells ps = (PlayerSpells) memorizedList.getSelectedValue();
		ps.addMetamagic(f);
		PlayerSpellsMemorizedDAO psmdb = new PlayerSpellsMemorizedDAO();
		psmdb.updatePlayerSpellsMemorized(ps);
		owner.getChar().setSpellsMemorized(
				psmdb.getPlayerSpellsMemorized("" + owner.getChar().getID()));
		updateSpellsMemorized();
	}
	
	private String getSelectedClass() {
		CharClass cs = (CharClass) classChooser.getSelectedItem();
		if (cs == null) {
			return ("unk");
		}
		
		if (cs.getCharClass().equals("Wizard")
				|| cs.getCharClass().equals("Sorcerer")) {
			return "Sorceror/Wizard";
		}
		return cs.getCharClass();
	}

	private void setSpellbook() {
		
		tabbedPane.removeAll();
		spellsKnownVector = new Vector<SpellsKnownPanel>();

		for (int i = 0; i < 10; i++) {
			CharClass cs = (CharClass) classChooser.getSelectedItem();
			if (owner.getChar().canCastAtLevel(cs.getCharClass(), i)) {
				SpellsKnownPanel skp = new SpellsKnownPanel(owner, this,
						"" + i, getSelectedClass());
				spellsKnownVector.add(skp);
				tabbedPane.add(skp, "" + i);
			}
		}
	
	}
	
	class MemorizedSpellPopupListener extends MouseAdapter {

		// on a double click, pop up the status dialog for the
		// list item. On a single click, just update the
		// client list to show the newly selected item.
		public void mouseClicked(MouseEvent e) {
			// if (e.getClickCount() == 2){
			// owner.showStatusDialog();
			// }else if (getSelectedIndex() >= 0){
			// owner.updateClientListPosition(getSelectedIndex());
			// }
		}

		// check all the clicks for a right click, and trigger the
		// popup menu if you see one.
		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);
		}

		public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e);
		}

		private void maybeShowPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}
}

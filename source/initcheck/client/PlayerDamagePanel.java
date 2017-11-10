package initcheck.client;

import initcheck.DCharacter;
import initcheck.HitPointBar;
import initcheck.InitClient;
import initcheck.InitLogger;
import initcheck.MessageDialog;
import initcheck.PanelButton;
import initcheck.Participant;
import initcheck.graphics.TiledPanel;
import initcheck.status.StatPoisoned;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class PlayerDamagePanel extends TiledPanel {
	static final long serialVersionUID = 1;

	private Vector<Participant> chars = new Vector<Participant>();

	private JList charList = new JList();

	private JScrollPane listScroll = new JScrollPane(charList);

	private InitClient owner;

	private PlayerDamageInputDialog input = null;

	private PanelButton healAll = new PanelButton("Heal All");

	private HitPointBar partyHp = new HitPointBar();

	int ttlHp = 0;

	int currentHp = 0;

	@SuppressWarnings("unused")
	private InitLogger logger = new InitLogger(this);

	public PlayerDamagePanel(InitClient owner) {
		this.owner = owner;
		init();
	}

	private void init() {
		// load up the chars
		setChars(owner.getCharVector());
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(20, // top
				20, // left
				10, // bottom
				20) // right
		);

		charList.setBackground(Color.lightGray);
		charList.setCellRenderer(new DamageListCellRenderer());
		charList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					showInputDialog(e.getX(), e.getY());
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					showDetailDialog(e.getX(), e.getY());
				}
			}
		});
		healAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Really, Cort?") == JOptionPane.YES_OPTION){
					healAll();
				}
			}
		});

		add(healAll, BorderLayout.NORTH);
		add(listScroll, BorderLayout.CENTER);
		add(partyHp, BorderLayout.SOUTH);
		for (int i = 0; i < chars.size(); i++) {
			DCharacter dc = (DCharacter) chars.get(i);
			ttlHp += dc.getHP();
			currentHp += dc.getCurrentHitPoints();
		}
		partyHp.setValues(ttlHp, currentHp);

	}

	public void healAll() {
		for (int i = 0; i < chars.size(); i++) {
			Participant p = (Participant) chars.get(i);
			p.setCurrentHitPoints(p.getHP());
			owner.healDamage(p.getName(), p.getHP());
		}
		partyHp.setValues(ttlHp, ttlHp);
		initList();
	}

	public void clearRoundDamage(int id) {
		for (int i = 0; i < chars.size(); i++) {
			Participant p = (Participant) chars.get(i);
			if (p.getID() == id) {
				p.setRoundDamage(0);
			}
		}
		initList();
	}

	public void subDamage(int heal) {
		int idx = charList.getSelectedIndex();
		DCharacter dc = (DCharacter) chars.get(idx);
		int healTo = dc.getCurrentHitPoints() + heal;

		// if the heal is greater than max HP, heal to max
		if (healTo > dc.getHP()) {
			healTo = dc.getHP();
		}

		dc.setCurrentHitPoints(healTo);
		partyHp.setValues(ttlHp, currentHp + heal);

		initList();
		charList.setSelectedIndex(idx);
		owner.healDamage(dc.getName(), healTo);
	}

	public void modStat(String stat, int mod) {
		int idx = charList.getSelectedIndex();
		DCharacter dc = (DCharacter) chars.get(idx);

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

		initList();
		charList.setSelectedIndex(idx);
		owner.modStat(dc.getName(), stat, mod);

		if (mod < 0) {
			String mesg = "The character has taken some stat damage.  Do you want to mark them as poisoned?";
			int answer = JOptionPane.showConfirmDialog(null, mesg,
					"Poison Player?", JOptionPane.YES_NO_OPTION);

			if (answer == JOptionPane.YES_OPTION) {
				owner.sendStat(dc.getName(), new StatPoisoned());
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void initList() {
		int idx = charList.getSelectedIndex();
		if (chars != null) {
			Collections.sort(chars);
			charList.setListData(chars);
		}
		if (idx >= 0) {
			charList.setSelectedIndex(idx);
		}
	}

	public void addDamage(int dmg) {
		int idx = charList.getSelectedIndex();
		if (idx >= 0 && idx < chars.size()) {
			DCharacter dc = (DCharacter) chars.get(idx);
			
			int roundDamage = dc.getRoundDamage() + dmg;
			int currHp = dc.getCurrentHitPoints() - dmg;
			//dc.setDamageTaken(dc.getDamageTaken() + dmg);
			//dc.setCurrentHitPoints(dc.getCurrentHitPoints() - dmg);
			//dc.setRoundDamage(dc.getRoundDamage() + dmg);
			partyHp.setValues(ttlHp, currentHp - dmg);
			if (currHp < 1) {
				String mesg = "The character has taken enough damage to die.\n  Do you want to send the kill command to the server?";

				int answer = JOptionPane.showConfirmDialog(null, mesg,
						"Kill Player?", JOptionPane.YES_NO_OPTION);

				if (answer == JOptionPane.YES_OPTION) {
					owner.sendServerKill(dc.getName());
				}
			}
			// see if the person should be stunned
			else if (roundDamage > dc.getStun() && (dc.getLevel() > 4)) {
				String mesg = "The character has taken enough damage to be stunned.\n  Do you want to send the stun command to the server?";

				int answer = JOptionPane.showConfirmDialog(null, mesg,
						"Stun Player?", JOptionPane.YES_NO_OPTION);

				if (answer == JOptionPane.YES_OPTION) {
					owner.sendServerStun(dc.getName());
				}
			}

			initList();
			charList.setSelectedIndex(idx);
			owner.updateDamage(dc.getName(), dmg);
		} else {
			@SuppressWarnings("unused") MessageDialog md = new MessageDialog("Error", "Problem Recording Damage!");
		}
	}

	private void showDetailDialog(int x, int y) {
		int idx = charList.getSelectedIndex();
		DCharacter dc = (DCharacter) chars.get(idx);
		PlayerDetailDialog pdd = new PlayerDetailDialog(this, dc, x, y);
		pdd.setVisible(true);
	}

	private void showInputDialog(int x, int y) {
		if (input == null) {
			input = new PlayerDamageInputDialog(this, x, y, owner
					.getDefaultFont());

		} else {
			input.setLocationRelativeTo(null);
		}
		input.setVisible(true);
	}

	/**
	 * Get the Chars value.
	 * 
	 * @return the Chars value.
	 */
	public Vector<Participant> getChars() {
		return chars;
	}

	/**
	 * Set the Chars value.
	 * 
	 * @param newChars
	 *            The new Chars value.
	 */
	public void setChars(Vector<Participant> newChars) {
		if (newChars == null) {
			return;
		}
		chars = new Vector<Participant>();
		for (int i = 0; i < newChars.size(); i++) {
			if ((((Participant) newChars.get(i)).getPType()).equals("PLAYER")) {
				chars.add(newChars.get(i));

				DCharacter dc = (DCharacter) newChars.get(i);
				ttlHp += dc.getHP();
				currentHp += dc.getCurrentHitPoints();

			}
		}
		partyHp.setValues(ttlHp, currentHp);
		initList();
	}
}

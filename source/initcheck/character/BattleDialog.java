package initcheck.character;

import initcheck.DCharacter;
import initcheck.InitLogger;
import initcheck.database.MonsterKills;
import initcheck.database.MonsterKillsDAO;
import initcheck.database.PlayerBattles;
import initcheck.database.PlayerBattlesDAO;
import initcheck.database.PlayerKills;
import initcheck.database.PlayerKillsDAO;
import initcheck.graphics.TiledDialog;

import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class BattleDialog extends TiledDialog implements ListSelectionListener {

	
	private static final long serialVersionUID = 1L;

	PlayerStatDialog owner;

	GridPanel contents = new GridPanel();

	JList battles = new JList();

	JTextArea stats = new JTextArea(10, 50);

	JScrollPane scroll = new JScrollPane(stats);

	DCharacter dc;

	private JLabel lifetime = new JLabel();

	private JLabel lifetimeRatio = new JLabel();

	private JLabel lifetimeDamage = new JLabel();

	private JLabel lifetimeDeaths = new JLabel();

	private JLabel nemesisLabel = new JLabel();

	private JLabel favoredEnemyLabel = new JLabel();

	private InitLogger logger = new InitLogger(this);
	
	public BattleDialog(PlayerStatDialog owner, DCharacter dc) {
		this.dc = dc;

		PlayerBattlesDAO pbdb = new PlayerBattlesDAO();
		PlayerKillsDAO pkdb = new PlayerKillsDAO();
		lifetime.setText("Lifetime Kills : "
				+ pkdb.getLifetimeKills("" + dc.getID()));
		lifetimeRatio.setText("Lifetime Hit Ratio : "
				+ pbdb.getLifetimeHit("" + dc.getID()));
		lifetimeDamage.setText("Lifetime Avg Damage : "
				+ pbdb.getLifetimeDamage("" + dc.getID()));

		MonsterKillsDAO mkdb = new MonsterKillsDAO();
		Vector<MonsterKills> monsterKills = mkdb.getMonsterKills("" + dc.getID());
		lifetimeDeaths.setText("Lifetime Deaths : " + monsterKills.size());

		String id = "";
		String nemesis = "";

		int count = 0;
		int highCount = 0;

		for (int i = 0; i < monsterKills.size(); i++) {

			MonsterKills mk = (MonsterKills) monsterKills.get(i);

			count++;

			if (!mk.getMonsterId().equals(id)) {
				count = 1;
				id = mk.getMonsterId();
			}

			if (count > highCount) {
				highCount = count;
				nemesis = mk.getMonsterName().trim();
				id = mk.getMonsterId();
			}

		}
		if (!nemesis.equals("")) {
			nemesis = nemesis.substring(0, nemesis.lastIndexOf(" "));
		}
		nemesisLabel.setText("Nemesis : " + nemesis + " (" + highCount + ")");

		Vector<PlayerKills> playerKills = pkdb.getLifetimeKillListing("" + dc.getID());
		count = 0;
		highCount = 0;
		nemesis = "";
		id = "";

		for (int i = 0; i < playerKills.size(); i++) {

			PlayerKills mk = (PlayerKills) playerKills.get(i);

			count++;

			if (!mk.getMonsterId().equals(id)) {
				count = 1;
				id = mk.getMonsterId();
			}

			if (count > highCount) {
				highCount = count;
				nemesis = mk.getMonsterName().trim();
				id = mk.getMonsterId();
			}

		}

		if (!nemesis.equals("")) {
			nemesis = nemesis.substring(0, nemesis.lastIndexOf(" "));
		}
		favoredEnemyLabel.setText("Favored Enemy : " + nemesis + " ("
				+ highCount + ")");

		Vector<PlayerBattles> bv = pbdb.getPlayerBattles("" + dc.getID());
		battles.setListData(bv);
		battles.addListSelectionListener(this);
		contents.doLayout(lifetime);
		contents.incYPos();
		contents.doLayout(lifetimeRatio);
		contents.incYPos();
		contents.doLayout(lifetimeDamage);
		contents.incYPos();
		contents.doLayout(lifetimeDeaths);
		contents.incYPos();
		contents.doLayout(nemesisLabel);
		contents.incYPos();
		contents.doLayout(favoredEnemyLabel);

		contents.incYPos();
		contents.doLayout(battles, 0);
		contents.incYPos();
		contents.doLayout(scroll);

		setMainWindow(contents);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

	}

	public void valueChanged(ListSelectionEvent e) {

		try {
			PlayerBattles bh = (PlayerBattles) battles.getSelectedValue();
			PlayerKillsDAO pkdb = new PlayerKillsDAO();
			PlayerKills pk = new PlayerKills();
			pk.setPlayerId("" + dc.getID());
			pk.setBattleId(bh.getBattleId());
			Vector<PlayerKills> kills = pkdb.selectPlayerKills(pk);
			stats.setText("Total Kills : " + kills.size() + "\n");
			for (int i = 0; i < kills.size(); i++) {
				pk = (PlayerKills) kills.get(i);
				stats.append(pk.toString() + "\n");
			}

			stats.append("Number Of Rounds Stunned : " + bh.getRoundsStunned()
					+ "\n");
			stats
					.append("Number Of Rounds Dead : " + bh.getRoundsDead()
							+ "\n");
			stats.append("Number Of Stuns : " + bh.getNumStuns() + "\n");
			stats.append("Best Hit : " + bh.getMaxDamage() + "\n");
			stats.append("Best Round : " + bh.getMaxRoundDamage() + "\n");
			stats.append("Total Damage Done : " + bh.getTotalDamage() + "\n");
			stats.append("Total Damage Taken : " + bh.getTotalTaken() + "\n");
			stats.append("Number Of Crits : " + bh.getNumCrits() + "\n");
			stats.append("Number Of Fumbles : " + bh.getNumFumbles() + "\n");
			stats.append("Number Of Double Damages : "
					+ bh.getNumDoubleDamage() + "\n");
			stats.append("Hit Ratio : " + bh.getHitPercentage() + "\n");
			stats.setCaretPosition(0);
		} catch (Exception ex) {
			logger.log("error", "Error in battle panel " + ex);
		}
	}
}

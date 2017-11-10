package initcheck.server;

import initcheck.InitServer;
import initcheck.PanelButton;
import initcheck.database.Monster;
import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WhoDunnitDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private ImageIcon bgImage = new ImageIcon("images/rock043.jpg");

	private PanelButton submitButton = new PanelButton("Mystery Solved", 150);

	private JComboBox monsterChooser;

	private String playerName;

	private int playerId;

	private InitServer owner;

	public WhoDunnitDialog(final InitServer owner, String title,
			Vector<MonsterInfoButton> monsterGroups, String playerName, int playerId) {

		super(owner.getFrame(), title, true);
		this.playerName = playerName;
		this.playerId = playerId;
		this.owner = owner;

		Vector<Monster> monsters = new Vector<Monster>();
		for (int i = 0; i < monsterGroups.size(); i++) {

			MonsterInfoButton mg = (MonsterInfoButton) monsterGroups.get(i);
			monsters.add(mg.getMonster());
		}

		
		monsterChooser = new JComboBox(monsters);

		TiledPanel contents = new TiledPanel(bgImage);
		contents.setLayout(new BorderLayout());
		contents.setBorder(BorderFactory.createEmptyBorder(20, // top
				20, // left
				10, // bottom
				20) // right
				);

		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setKiller();
			}
		});

		JLabel label = new JLabel(
				playerName
						+ " appears to be deceased.  Which dastardly creature commited the foul deed?");
		label.setForeground(Color.white);

		contents.add(label, BorderLayout.NORTH);
		contents.add(monsterChooser, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		buttonPanel.add(submitButton);

		contents.add(buttonPanel, BorderLayout.SOUTH);

		getContentPane().add(contents);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void setKiller() {
		Monster m = (Monster) monsterChooser.getSelectedItem();
		owner.recordPlayerDeath(playerName, playerId, m.getName(), m.getID());
		dispose();
	}
}

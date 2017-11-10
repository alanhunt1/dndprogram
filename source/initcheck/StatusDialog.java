package initcheck;

import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StatusDialog extends JDialog {

	private ImageIcon icon = new ImageIcon("images/deadoff.gif");

	private ImageIcon icon2 = new ImageIcon("images/deadon.gif");

	private RolloverButton cancelButton = new RolloverButton(icon2, icon);

	private ImageIcon bgImage = new ImageIcon("images/rock043.jpg");

	public StatusDialog(final InitProgram owner, DCharacter c, int i) {

		super(owner.getFrame(), "View Player Stats", true);

		TiledPanel outerPanel = new TiledPanel(bgImage);
		outerPanel.setLayout(new BorderLayout());

		JPanel bufferPanel = new JPanel();

		JPanel contents = new JPanel(new GridLayout(13, 1, 5, 5));

		contents.add(new JLabel("Name : "));
		contents.add(new JLabel("" + c.getName()));

		contents.add(new JLabel("Level : "));
		contents.add(new JLabel("" + c.getLevel()));

		contents.add(new JLabel("Init Mod : "));
		contents.add(new JLabel("" + c.getMod()));

		contents.add(new JLabel("AC : "));
		contents.add(new JLabel("" + c.getAC()));

		contents.add(new JLabel("HP : "));
		contents
				.add(new JLabel("" + c.getCurrentHitPoints() + "/" + c.getHP()));

		contents.add(new JLabel("STR : "));
		contents.add(new JLabel("" + c.getStrength()));

		contents.add(new JLabel("DEX : "));
		contents.add(new JLabel("" + c.getDexterity()));

		contents.add(new JLabel("CON : "));
		contents.add(new JLabel("" + c.getConstitution()));

		contents.add(new JLabel("WIS : "));
		contents.add(new JLabel("" + c.getWisdom()));

		contents.add(new JLabel("INT : "));
		contents.add(new JLabel("" + c.getIntelligence()));

		contents.add(new JLabel("CHA : "));
		contents.add(new JLabel("" + c.getCharisma()));

		contents.add(new JLabel("Dead : "));
		contents.add(new JLabel("" + c.isDead()));

		contents.add(new JLabel("Status : "));
		contents.add(new JLabel("" + c.getStatus()));

		bufferPanel.setBorder(BorderFactory.createEmptyBorder(5, // top
				5, // left
				5, // bottom
				5) // right
				);

		bufferPanel.add(contents);
		outerPanel.add(bufferPanel, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.add(cancelButton);
		outerPanel.add(buttonPanel, BorderLayout.SOUTH);

		outerPanel.setBorder(BorderFactory.createEmptyBorder(30, // top
				30, // left
				10, // bottom
				30) // right
				);

		getContentPane().add(outerPanel);

		pack();

		// Finish setting up the frame, and show it.
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}

}

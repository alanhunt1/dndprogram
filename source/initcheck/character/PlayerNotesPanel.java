package initcheck.character;

import initcheck.DCharacter;
import initcheck.graphics.TiledPanel;
import initcheck.graphics.TiledTextArea;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class PlayerNotesPanel extends TiledPanel implements FocusListener,
		StatusTab {

	private static final long serialVersionUID = 1L;

	private final PlayerStatDialog owner;

	private TiledTextArea playerNotes = new TiledTextArea(10, 50);

	private TiledTextArea modNotes = new TiledTextArea(10, 30);

	boolean updateRequired;

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

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints cn = new GridBagConstraints();

	public PlayerNotesPanel(final PlayerStatDialog owner, DCharacter dc) {
		super("images/wood.jpg");
		setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
		this.owner = owner;

		setLayout(gridbag);
		cn.fill = GridBagConstraints.BOTH;
		cn.ipadx = 10;
		cn.ipady = 10;

		int ypos = 0;

		doLayout(new JLabel("Player Notes"), 0, ypos);

		ypos++;

		cn.weightx = 1.0;
		cn.weighty = 0.5;
		JScrollPane playerNotesPanel = new JScrollPane(playerNotes);

		doLayout(playerNotesPanel, 0, ypos);
		ypos++;
		cn.weightx = 0;
		cn.weighty = 0;
		doLayout(new JLabel("Conditional Mods"), 0, ypos);
		ypos++;
		cn.weightx = 1.0;
		cn.weighty = 0.5;
		JScrollPane modNotesPanel = new JScrollPane(modNotes);

		doLayout(modNotesPanel, 0, ypos);
		cn.weightx = 0;
		cn.weighty = 0;
		playerNotes.setText(owner.getChar().getPlayerNotes());
		playerNotes.addFocusListener(this);
		modNotes.setText(owner.getChar().getModNotes());
		modNotes.addFocusListener(this);
	}

	public void focusGained(FocusEvent e) {

	}

	public void focusLost(FocusEvent e) {
		owner.getChar().setPlayerNotes(getNotes());
		owner.getChar().setModNotes(modNotes.getText());
	}

	public String getNotes() {
		return playerNotes.getText();
	}

	private void doLayout(Component comp, int x, int y) {
		cn.gridx = x;
		cn.gridy = y;
		cn.gridwidth = 1;
		cn.gridheight = 1;
		gridbag.setConstraints(comp, cn);
		add(comp);
	}

	@SuppressWarnings("unused")
	private void doLayout(Component comp, int x, int y, int width, int height) {
		cn.gridx = x;
		cn.gridy = y;
		cn.gridwidth = width;
		cn.gridheight = height;

		gridbag.setConstraints(comp, cn);
		add(comp);
	}

}

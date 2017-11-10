package initcheck.displaycomponents;

import initcheck.database.Monster;
import initcheck.graphics.TiledGridPanel;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MonsterNotesPanel extends TiledGridPanel {

	// hit fields

	private static final long serialVersionUID = 1L;

	private JTextArea notes = new JTextArea(5, 60);

	private JTextArea combatnotes = new JTextArea(15, 60);

	private JLabel notesLabel = new JLabel("Notes");

	private JLabel combatnotesLabel = new JLabel("Combat Notes");

	private JScrollPane notesScroll = new JScrollPane(notes);
	
	private JScrollPane combatNotesScroll = new JScrollPane(combatnotes);
	
	public MonsterNotesPanel() {
		super("images/rockLighter.jpg");
		initValues();
	}

	public MonsterNotesPanel(Monster m) {
		super("images/rockLighter.jpg");
		initValues();
		if (m != null) {
			notes.setText(m.getGenNotes());
			notes.setCaretPosition(0);
			combatnotes.setText(m.getCombatNotes());
			combatnotes.setCaretPosition(0);
		}
	}

	public MonsterNotesPanel(String notes, String combatnotes) {
		super("images/rockLighter.jpg");
		initValues();
		this.notes.setText(notes);
		this.notes.setCaretPosition(0);
		
		this.combatnotes.setText(combatnotes);
		this.combatnotes.setCaretPosition(0);
	}

	public void setMonster(Monster m) {
		if (m != null) {
			notes.setText(m.getGenNotes());
			notes.setCaretPosition(0);
			combatnotes.setText(m.getCombatNotes());
			combatnotes.setCaretPosition(0);
		}
	}

	public String getNotes() {
		return notes.getText();
	}

	public String getCombatnotes() {
		return combatnotes.getText();
	}

	private void initValues() {

		// set line wrap to true on the long text fields.

		notes.setLineWrap(true);
		combatnotes.setLineWrap(true);
		notes.setWrapStyleWord(true);
		combatnotes.setWrapStyleWord(true);
	
		setWeightX(1.0);
		setWeightY(0.5);
		setPadX(0);
		setPadY(0);

		doLayout(notesLabel);
		incYPos();
		doLayout(notesScroll);
		incYPos();
		doLayout(combatnotesLabel);
		incYPos();
		doLayout(combatNotesScroll);

	}
}

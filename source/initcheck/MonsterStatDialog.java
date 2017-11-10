package initcheck;

import initcheck.character.library.LibraryPanel;
import initcheck.database.Monster;
import initcheck.graphics.TiledDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * <code>MonsterStatDialog</code> is used to display the full information
 * associated with a monster, or to add a new monster to the database, depending
 * on the mode in which it is created.
 * 
 * @author <a href="mailto:ahunt@cse.buffalo.edu">Alan M. Hunt</a>
 * @version 1.0
 */
public class MonsterStatDialog extends TiledDialog {

	
	private static final long serialVersionUID = 1L;

	private LibraryPanel owner;

	private MonsterDisplayPanel mdp;

	// buttons
	private PanelButton okButton = null;

	private PanelButton cancelButton = new PanelButton("Cancel");

	private PanelButton prevButton = new PanelButton("<", 20);

	private PanelButton nextButton = new PanelButton(">", 20);

	public MonsterStatDialog(LibraryPanel owner) {
		this.owner = owner;
		mdp = new MonsterDisplayPanel(owner);
		doInit(MonsterDisplayPanel.DISPLAY);
	}

	public MonsterStatDialog(LibraryPanel owner, final int mode) {
		this.owner = owner;
		mdp = new MonsterDisplayPanel(owner, mode);
		doInit(mode);
	}
	
	public MonsterStatDialog(final Monster c, LibraryPanel owner) {
		this.owner = owner;
		mdp = new MonsterDisplayPanel(c, owner);
		doInit(MonsterDisplayPanel.DISPLAY);
	}

	public MonsterStatDialog(final Monster c, final int mode, LibraryPanel owner) {
		this.owner = owner;
		mdp = new MonsterDisplayPanel(c, mode, owner);
		doInit(mode);
	}

	public MonsterStatDialog(JDialog parent, final Monster c, final int mode,
			LibraryPanel owner) {
		super(parent, "Monster Information", true);
		this.owner = owner;
		mdp = new MonsterDisplayPanel(c, mode, owner);
		doInit(mode);

	}

	/**
	 * Initialize the dialog components.
	 */
	private void doInit(final int mode) {

		// check which mode we are in. If we are in add mode,
		// then the submit button should say "add". If we are in
		// modify mode, then the submit button should say "modify".
		if (mode == MonsterDisplayPanel.ADD) {
			okButton = new PanelButton("Add");
			okButton.setToolTipText("Add your custom monster to the database");
		} else {
			okButton = new PanelButton("Modify");
		}
		
		JPanel buttons = new JPanel();
		if (owner != null) {
			buttons.add(prevButton);
			prevButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showPrev();
				}
			});
		}

		buttons.add(cancelButton);
		if (mode != MonsterDisplayPanel.DISPLAY) {
			buttons.add(okButton);

		}

		if (owner != null) {
			buttons.add(nextButton);
			nextButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showNext();
				}
			});

		}
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		if (mode != MonsterDisplayPanel.DISPLAY) {
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mdp.save(mode);
				}
			});
		}
		buttons.setOpaque(false);
		setButtonBar(buttons);
		setMainWindow(mdp);
		pack();
		setVisible(true);
	}

	public void showNext() {
		mdp.showNext();
	}

	public void showPrev() {
		mdp.showPrev();
	}
	
	public void setMonster(Monster m) {

		mdp.setMonster(m);
	}

}

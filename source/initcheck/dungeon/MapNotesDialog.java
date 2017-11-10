package initcheck.dungeon;

import initcheck.PanelButton;
import initcheck.graphics.TiledDialog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MapNotesDialog extends TiledDialog {

	private static final long serialVersionUID = 1L;

	private JTextArea notes = new JTextArea(10, 40);

	private PanelButton okButton = new PanelButton("Ok", 70);

	public static final int CLIENT = 1;

	public static final int SERVER = 2;

	int mode;

	int x = 0;

	int y = 0;

	int z = 0;

	public void setPosition(int a, int b, int c) {
		x = a;
		y = b;
		z = c;
	}

	/**
	 * Get the Mode value.
	 * 
	 * @return the Mode value.
	 */
	public int getMode() {
		return mode;
	}

	/**
	 * Set the Mode value.
	 * 
	 * @param newMode
	 *            The new Mode value.
	 */
	public void setMode(int newMode) {
		this.mode = newMode;
	}

	public MapNotesDialog(final DrawingBoard owner) {

		super(owner.getFrame(), "Select Font", true);
		JPanel contents = new JPanel(new BorderLayout());

		JScrollPane scroll = new JScrollPane(notes);
		JPanel buttons = new JPanel();

		buttons.add(okButton);

		buttons.setOpaque(false);

		JPanel stylePanel = new JPanel();

		contents.add(stylePanel, BorderLayout.NORTH);
		contents.add(scroll, BorderLayout.CENTER);
		contents.setOpaque(false);
		setButtonBar(buttons);
		setMainWindow(contents);

		pack();

		// Finish setting up the frame, and show it.
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

			}
		});

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mode == CLIENT) {
					owner.addPlayerNotes(x, y, z, notes.getText());
				} else {
					owner.addDmNotes(x, y, z, notes.getText());
				}
				setVisible(false);
			}
		});

	}

	public void setText(String s) {
		notes.setText(s);
	}

}

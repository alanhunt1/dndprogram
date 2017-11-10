package initcheck.sounds;

import initcheck.InitServer;
import initcheck.PanelButton;
import initcheck.graphics.TiledDialog;
import initcheck.io.FileCopy;
import initcheck.io.SoundFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Describe class <code>SoundImportDialog</code> here.
 * 
 * @author <a href="mailto:hunt_a@machine.domain">Alan M. Hunt</a>
 * @version 1.0
 */
public class SoundImportDialog extends TiledDialog {

	private static final long serialVersionUID = 1L;

	private InitServer owner = null;

	private PanelButton loadButton = new PanelButton("Load Sound", 70);

	private PanelButton closeButton = new PanelButton("Ok");

	private JLabel instructions = new JLabel("Click on the import button to "
			+ "load a new sound file");

	/**
	 * Creates a new <code>SoundImportDialog</code> instance.
	 * 
	 * @param owner
	 *            an <code>InitServer</code> value
	 */
	public SoundImportDialog(final InitServer owner) {
		super(owner.getFrame(), "Import Sounds", true);
		this.owner = owner;

		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				importFile();
			}
		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(loadButton);
		buttonPanel.add(closeButton);
		setButtonBar(buttonPanel);
		setMainWindow(instructions);
		pack();
		setVisible(true);
	}

	/**
	 * Copies an .au file from somewhere on the executing machine's file system
	 * into the sounds directory, where it is available for use.
	 */
	private void importFile() {
		JFileChooser fc = new JFileChooser();
		File file = null;

		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		SoundFilter filter = new SoundFilter();
		fc.setFileFilter(filter);

		int returnVal = fc.showOpenDialog(owner.getFrame());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
		}

		try {
			String newFile = "sounds/" + file.getName();
			FileCopy.copy(file.getPath(), newFile, false);
		} catch (java.io.IOException e) {

		}
		instructions.setText("File " + file.getName()
				+ " was successfully loaded.");
	}

}

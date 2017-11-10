package initcheck.preferences;

import initcheck.InitLogger;
import initcheck.PanelButton;
import initcheck.character.GridPanel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import sun.audio.AudioStream;

public class SoundTestPanel extends GridPanel implements Serializable {

	private static final long serialVersionUID = 1L;

	private InitLogger logger = new InitLogger(this);

	// the hold init sound
	JCheckBox enable = new JCheckBox();

	JComboBox soundChooser;

	PanelButton testButton = new PanelButton("Test");

	SoundPanel owner;

	public SoundTestPanel(SoundPanel owner, String text, String fileName,
			boolean enabled) {
		this.owner = owner;

		enable.setText(text);
		enable.setSelected(enabled);
		enable.setPreferredSize(new Dimension(100,
				enable.getPreferredSize().height));
		soundChooser = new JComboBox(getSoundFileList());
		soundChooser.setSelectedItem(fileName);

		testButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSound();
			}
		});

		doLayout(enable, 0);
		doLayout(soundChooser, 1);
		doLayout(testButton, 2);

	}

	public void updatePref() {
		owner.updatePref(enable.getText(), (String) soundChooser
				.getSelectedItem());
	}

	public void playSound() {

		String sound = (String) soundChooser.getSelectedItem();
		playSound(sound);
	}

	/**
	 * Describe <code>playSound</code> method here.
	 * 
	 * @param s
	 *            a <code>String</code> value
	 */
	public void playSound(String s) {
		try {
			// Open an input stream to the audio file.
			InputStream in = new FileInputStream("sounds/" + s);
			// Create an AudioStream object from the input stream.
			AudioStream as = new AudioStream(in);
			// Use the static class member "player" from class AudioPlayer to
			// play
			// clip.
			sun.audio.AudioPlayer.player.start(as);
			// Similarly, to stop the audio.
			// AudioPlayer.player.stop(as);
		} catch (Exception e) {
			logger.log(e.toString());
		}
	}

	private Vector<String> getSoundFileList() {

		Vector<String> soundVector = new Vector<String>();
		try {
			File soundDir = new File("sounds");
			String[] soundFiles = soundDir.list();
			for (int i = 0; i < soundFiles.length; i++) {
				if (soundFiles[i].indexOf(".wav") > 0
						|| soundFiles[i].indexOf(".au") > 0) {
					soundVector.add(soundFiles[i]);
				}
			}
		} catch (Exception e) {
			logger.log("error", "unable to open sound directory");

		}
		return soundVector;
	}

}

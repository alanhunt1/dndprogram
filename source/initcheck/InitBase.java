package initcheck;

import initcheck.graphics.DefaultSkin;
import initcheck.graphics.Skin;

import java.awt.Font;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.JFrame;

import sun.audio.AudioStream;

public class InitBase {

	public static final int SERVER = 1;
	public static final int CLIENT = 2;
	public static final int MAPPER = 3;
	public static final int MANAGER = 4;
		
	private InitLogger logger = new InitLogger(this);
	
	protected static Font defaultFont = (new Font("Arial", Font.BOLD, 20));
	
	JFrame frame;
	
	protected Skin skin = new DefaultSkin();
	
	protected int type = SERVER;

	protected String proxy = null;
	
	protected String proxyPort = null;
	
	public String getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxy() {
		return proxy;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
	public void setFont(Font f){
		
	}
	
	
	
	/**
	 * Describe <code>playSound</code> method here.
	 * 
	 * @param s
	 *            a <code>String</code> value
	 */
	public void playSound(String s) {

		if (s.equals("NOTFOUND")) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Sound File Missing",
					"One of the sound files configured in the preferences panel is missing.");
		}

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

	// override in the descendants, depending on what fonts you want to 
	// change with this.
	public void setListFont(Font f) {
		
	}
	
	// overwrite in the descendants, depending on where/how you save files.
	public void writePrefsToFile(){
		
	}
	
	public Font getDefaultFont() {
		return defaultFont;
	}

	public void setDefaultFont(Font defaultFont) {
		InitBase.defaultFont = defaultFont;
	}

	public Skin getSkin() {
		return skin;
	}

	public void setSkin(Skin skin) {
		this.skin = skin;
	}
}

package initcheck.preferences;

import initcheck.InitBase;
import initcheck.InitLogger;
import initcheck.PanelButton;
import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class PreferencesDialog extends JDialog implements Serializable {

	
	private static final long serialVersionUID = 1L;

	private SoundPanel soundPanel; 

	private FontDialog fontPanel;

	private SkinsPanel skinsPanel;
	
	private ProxyPanel proxyPanel;
	
	private PanelButton cancelButton = new PanelButton("Cancel");

	private ImageIcon bgImage = new ImageIcon("images/rock043.jpg");

	private PanelButton saveButton = new PanelButton("Apply");

	private PanelButton okButton = new PanelButton("OK");
	
	private InitLogger logger = new InitLogger(this);	
	
	private int mode = InitBase.SERVER;
	
	private InitBase owner;
	
	private String filename;
	
	public PreferencesDialog(InitBase owner) {

		super(owner.getFrame(), "Preferences", true);
		this.owner = owner;
		mode = owner.getType();
		
		if (mode == InitBase.SERVER){
			filename = "saves/ServerPreferences.ser";
		}else if (mode == InitBase.CLIENT){
			filename = "saves/ClientPreferences.ser";		
		}else if (mode == InitBase.MANAGER){
			filename = "saves/ManagerPreferences.ser";
		}
		
		soundPanel = new SoundPanel(owner.getType());
		skinsPanel = new SkinsPanel(owner);
		proxyPanel = new ProxyPanel(owner);
		
		if (!readFromFile()) {
			soundPanel.setDefaultSounds();	
			fontPanel = new FontDialog(owner);
		}
		
		soundPanel.init();
		
		TiledPanel outerPanel = new TiledPanel(bgImage);
		outerPanel.setLayout(new BorderLayout());

		JTabbedPane tabbedPane = new JTabbedPane();

		tabbedPane.addTab("Sound", null, soundPanel, "Set Sound Preferences");
		
		tabbedPane.addTab("Fonts", null, fontPanel, "Set Fonts");
		
		tabbedPane.addTab("Skins", null, skinsPanel, "Set Skins");
		
		tabbedPane.addTab("Proxy", null, proxyPanel, "Set Proxy Server");
		
		tabbedPane.setSelectedIndex(0);
		
		outerPanel.add(tabbedPane, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.add(cancelButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(okButton);
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

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				savePrefs();
			}
		});
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				savePrefs();
				setVisible(false);
			}
		});
	}

	public void savePrefs(){
		soundPanel.save();
		fontPanel.save();
		proxyPanel.save();
		writeToFile();		
	}
	public String getSound(String s) {
		return soundPanel.getSound(s);
	}

	public boolean soundOn() {
		return soundPanel.soundOn();
	}
	
	public boolean isProxy(){
		return proxyPanel.getUseProxy();
	}

	public ProxyPanel getProxy(){
		return proxyPanel;
	}
	
	public void writeToFile() {
		try {
			
			
			// write out the hit sheet
			FileOutputStream ostream = new FileOutputStream(filename);
					
			ObjectOutputStream p = new ObjectOutputStream(ostream);
			
			p.writeObject(soundPanel.getSounds());
			p.writeObject(new Boolean(proxyPanel.getUseProxy()));
			p.writeObject(proxyPanel.getProxyAddress());
			p.writeObject(proxyPanel.getProxyPort());
			p.flush();
			ostream.close();
		} catch (Exception e) {
			logger.log("error", "Error writing preferences to file "+e.toString());
		}
	}

	@SuppressWarnings("unchecked")
	public boolean readFromFile() {
		boolean success = false;
		try {
			
			// read in the hit sheet
			FileInputStream istream = new FileInputStream(filename);
			ObjectInputStream p = new ObjectInputStream(istream);
			
			soundPanel.setSounds((Vector) p.readObject());
			
			fontPanel = new FontDialog(owner);
			proxyPanel.setUseProxy((Boolean)p.readObject());
			proxyPanel.setProxyAddress((String)p.readObject());
			proxyPanel.setProxyPort((String)p.readObject());
			istream.close();
			success = true;

		} catch (Exception e) {
			logger.log("error", "Error reading preferences from file "+e.toString());
		}
		return success;
	}

}

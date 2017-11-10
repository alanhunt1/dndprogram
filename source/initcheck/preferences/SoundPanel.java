package initcheck.preferences;

import initcheck.InitBase;
import initcheck.InitLogger;
import initcheck.graphics.TiledGridPanel;

import java.io.Serializable;
import java.util.Vector;

public class SoundPanel extends TiledGridPanel implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private InitLogger logger = new InitLogger(this);

	Vector<SoundPref> sounds = new Vector<SoundPref>();

	Vector<SoundPref> defaults = new Vector<SoundPref>();

	Vector<SoundTestPanel> panels = new Vector<SoundTestPanel>();

	

	public SoundPanel() {
		super("images/rockLighter.jpg");
		loadSounds(InitBase.SERVER);
	}

	public SoundPanel(int mode) {
		super("images/rockLighter.jpg");
		loadSounds(mode);
	}

	public void init() {
		for (int i = 0; i < sounds.size(); i++) {
			SoundPref sp = sounds.get(i);
			SoundTestPanel stp = new SoundTestPanel(this, sp.getName(), sp
					.getFile(), sp.isEnabled());
			panels.add(stp);
			doLayout(stp);
			incYPos();
		}
	}

	@SuppressWarnings("unchecked")
	public void setDefaultSounds() {
		sounds = (Vector) defaults.clone();
	}

	public void save() {
		for (int i = 0; i < panels.size(); i++) {
			SoundTestPanel stp = panels.get(i);
			stp.updatePref();
		}
	}

	public void loadSounds(int mode) {

		if (mode == InitBase.SERVER) {
			SoundPref sp = new SoundPref("startup", "chime.wav", true);
			defaults.add(sp);

			sp = new SoundPref("shutdown", "nitechet.wav", true);
			defaults.add(sp);

			sp = new SoundPref("treasurewhine", "whine.wav", true);
			defaults.add(sp);

			sp = new SoundPref("merge parties", "merge.wav", true);
			defaults.add(sp);

			sp = new SoundPref("split party", "splitup.wav", true);
			defaults.add(sp);

			sp = new SoundPref("message arrived", "chime.wav", true);
			defaults.add(sp);

			sp = new SoundPref("switch parties", "ranch.wav", true);
			defaults.add(sp);
			
			sp = new SoundPref("listmove", "cheater.wav", true);
			defaults.add(sp);
		} else if (mode == InitBase.CLIENT){
			SoundPref sp = new SoundPref("thirsty", "homerhacking.au", true);
			defaults.add(sp);

			sp = new SoundPref("hungry", "makepie.wav", true);
			defaults.add(sp);
			
			sp = new SoundPref("levelup", "woo_hoo.wav", true);
			defaults.add(sp);
			
			sp = new SoundPref("highscore", "booyah.wav", true);
			defaults.add(sp);
			
			sp = new SoundPref("new round", "newround.au", true);
			defaults.add(sp);
			
			sp = new SoundPref("startbattle", "get_em.au", true);
			defaults.add(sp);
			
			sp = new SoundPref("attention", "screamshutup.wav", true);
			defaults.add(sp);
			
			sp = new SoundPref("message arrived", "chime.wav", true);
			defaults.add(sp);
			
			sp = new SoundPref("alive", "alive.au", true);
			defaults.add(sp);
			
			sp = new SoundPref("dead", "AHHHH.wav", true);
			defaults.add(sp);
			
			sp = new SoundPref("hold", "HOLDING.wav", true);
			defaults.add(sp);
			
			sp = new SoundPref("endbattle", "yay2.au", true);
			defaults.add(sp);
			
			sp = new SoundPref("stun", "doh.au", true);
			defaults.add(sp);
			
			sp = new SoundPref("unstun", "wunderbar.au", true);
			defaults.add(sp);
		}
	}

	public Vector<SoundPref> getSounds() {
		return sounds;
	}

	public void setSounds(Vector<SoundPref> sounds) {
		this.sounds = sounds;

		// make sure that all of the prefs are available
		for (int i = 0; i < defaults.size(); i++) {
			SoundPref sp = defaults.get(i);
			boolean found = false;
			for (int j = 0; j < sounds.size(); j++) {
				SoundPref sp2 = sounds.get(j);
				if (sp2.getName().equals(sp.getName())) {
					found = true;
					break;
				}
			}
			if (!found) {

				sounds.add(sp);
			}
		}

	}

	// get the sound file name for an event.
	public String getSound(String s) {

		for (int i = 0; i < sounds.size(); i++) {

			SoundPref sp = sounds.get(i);

			if (sp.getName().equals(s)) {
				if (sp.isEnabled()) {
					return sp.getFile();
				} else {
					return ("DISABLED");
				}
			}
		}

		return ("NOTFOUND");
	}

	public void updatePref(String pref, String file) {
		for (int i = 0; i < sounds.size(); i++) {

			SoundPref sp = sounds.get(i);

			if (sp.getName().equals(pref)) {
				sp.setFile(file);
				break;
			}
		}
	}

	public boolean soundOn() {
		return true;
	}

}

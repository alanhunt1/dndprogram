package initcheck.character.chooser;

import initcheck.database.Race;
import initcheck.database.RaceDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class RaceChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public RaceChooser() {
		super();
		RaceDAO db = new RaceDAO();
		Vector<Race> v = db.selectRace(new Race());
		for (int i = 0; i < v.size(); i++) {
			Race f = (Race) v.get(i);
			addItem(f.getRace());
		}
	}
	
	public RaceChooser(String source) {
		super();
		RaceDAO db = new RaceDAO();
		Vector<Race> v = db.getRaceBySource(source);
		for (int i = 0; i < v.size(); i++) {
			Race f = (Race) v.get(i);
			addItem(f.getRace());
		}
	}
}

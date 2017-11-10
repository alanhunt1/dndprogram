package initcheck.character.chooser;

import initcheck.database.Feat;
import initcheck.database.FeatDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class FeatChooser extends JComboBox<Feat> {

	private static final long serialVersionUID = 1L;

	private static Vector<Feat> v;

	public FeatChooser() {
		super();

		if (v == null) {
			FeatDAO db = new FeatDAO();
			v = db.getFeats();
		}

		for (int i = 0; i < v.size(); i++) {
			Feat f = (Feat) v.get(i);
			addItem(f);
		}
	}
	
	public FeatChooser(String source) {
		super();

		if (v == null) {
			FeatDAO db = new FeatDAO();
			v = db.getFeatsBySource(source);
		}

		for (int i = 0; i < v.size(); i++) {
			Feat f = (Feat) v.get(i);
			addItem(f);
		}
	}
	
}

package initcheck.character.chooser;

import initcheck.database.FeatPackage;
import initcheck.database.FeatPackageDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class FeatPackageChooser extends JComboBox<Object> {

	private static final long serialVersionUID = 1L;

	private static Vector<FeatPackage> v = null;

	public FeatPackageChooser() {
		super();

		if (v == null) {
			FeatPackageDAO db = new FeatPackageDAO();
			v = db.getFeatPackages();
		}
		for (int i = 0; i < v.size(); i++) {
			FeatPackage f = (FeatPackage) v.get(i);
			addItem(f);
		}
	}

}

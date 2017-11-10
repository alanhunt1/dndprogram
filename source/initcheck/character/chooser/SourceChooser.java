package initcheck.character.chooser;

import initcheck.database.MaterialSource;
import initcheck.database.MaterialSourceDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class SourceChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	private static Vector<MaterialSource> v = null;
	
	public SourceChooser() {
		
		super();
		
		if (v == null) {
			MaterialSourceDAO db = new MaterialSourceDAO();
			v = db.getMaterialSources();
		}
		addItem("Unknown");
		for (int i = 0; i < v.size(); i++) {
			MaterialSource f = (MaterialSource) v.get(i);
			addItem(f.toString());
		}

	}
	
	public SourceChooser(boolean useObjects){
		super();
		
		if (v == null) {
			MaterialSourceDAO db = new MaterialSourceDAO();
			v = db.getMaterialSources();
		}
		addItem(new MaterialSource());
		for (int i = 0; i < v.size(); i++) {
			MaterialSource f = (MaterialSource) v.get(i);
			addItem(f);
		}

	}
	
	
}

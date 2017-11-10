package initcheck.character.chooser;

import initcheck.database.Materials;
import initcheck.database.MaterialsDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class MaterialChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public MaterialChooser() {
		super();
		MaterialsDAO db = new MaterialsDAO();
		Materials a = new Materials();

		Vector<Materials> v = db.selectMaterials(a);
		for (int i = 0; i < v.size(); i++) {
			Materials f = (Materials) v.get(i);
			addItem(f);
		}

	}

	public void setSelectedMaterial(Materials w) {
		if (w == null) {
			return;
		}
		for (int i = 0; i < getItemCount(); i++) {
			Materials w2 = (Materials) getItemAt(i);
			if (w2.getId().equals(w.getId())) {
				setSelectedIndex(i);
				break;
			}
		}
	}
}

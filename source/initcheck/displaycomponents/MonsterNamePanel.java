package initcheck.displaycomponents;

import initcheck.database.InitDBC;
import initcheck.database.Monster;
import initcheck.graphics.TiledGridPanel;

import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MonsterNamePanel extends TiledGridPanel {

	private static final long serialVersionUID = 1L;

	// name fields
	private JTextField name = new JTextField(15);

	private JComboBox size;

	private JComboBox type;

	private JComboBox subtype;

	private JLabel nameLabel = new JLabel("Name");

	private JLabel sizeLabel = new JLabel("Size");

	private JLabel typeLabel = new JLabel("Type");

	private JLabel subTypeLabel = new JLabel("SubType");

	public MonsterNamePanel() {
		super("images/rockLighter.jpg");
		initValues();
	}

	public MonsterNamePanel(Monster m) {
		super("images/rockLighter.jpg");
		initValues();
		if (m != null) {
			name.setText(m.getName());
			size.setSelectedItem((Object) m.getSize());
			type.setSelectedItem((Object) m.getType());
			subtype.setSelectedItem((Object) m.getSubType());
		}
	}

	public MonsterNamePanel(String name, String size, String type,
			String subtype) {
		super("images/rockLighter.jpg");
		initValues();
		this.name.setText(name);
		this.size.setSelectedItem((Object) size);
		this.type.setSelectedItem((Object) type);
		this.subtype.setSelectedItem((Object) subtype);
	}

	public void setMonster(Monster m) {
		if (m != null) {
			name.setText(m.getName());
			size.setSelectedItem((Object) m.getSize());
			type.setSelectedItem((Object) m.getType());
			subtype.setSelectedItem((Object) m.getSubType());
		}
	}

	public String getName() {
		if (name != null){
			return name.getText();
		}
		return "";
	}

	public String getMonsterSize() {
		return (String) size.getSelectedItem();
	}

	public String getType() {
		return (String) type.getSelectedItem();
	}

	public String getSubType() {
		return (String) subtype.getSelectedItem();
	}

	private void initValues() {

		// get a connection to the database
		InitDBC db = new InitDBC();

		// read in the monster types
		Vector<String> types = db.getMonsterTypes();
		type = new JComboBox(types);
		type.setEditable(true);
		type.setSelectedIndex(0);

		// read in the monster sizes
		Vector<String> sizes = db.getMonsterSizes();
		size = new JComboBox(sizes);
		size.setEditable(true);
		size.setSelectedIndex(0);

		// read in the monster sub types
		Vector<String> subtypes = db.getMonsterSubTypes();
		subtype = new JComboBox(subtypes);
		subtype.setEditable(true);
		subtype.setSelectedIndex(0);

		setWeightX(1.0);
		setWeightY(1.0);

		doLayout(nameLabel, 0, ypos);
		doLayout(name, 1, ypos);

		incYPos();

		doLayout(sizeLabel, 0, ypos);
		doLayout(size, 1, ypos);

		incYPos();

		doLayout(typeLabel, 0, ypos);
		doLayout(type, 1, ypos);

		incYPos();

		doLayout(subTypeLabel, 0, ypos);
		doLayout(subtype, 1, ypos);

	}
}

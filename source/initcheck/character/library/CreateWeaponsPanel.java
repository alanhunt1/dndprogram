package initcheck.character.library;

import initcheck.MessageDialog;
import initcheck.PanelButton;
import initcheck.character.chooser.DamageTypeChooser;
import initcheck.character.chooser.SizeChooser;
import initcheck.character.chooser.SourceChooser;
import initcheck.character.chooser.WeaponCategoryChooser;
import initcheck.character.chooser.WeaponRangeChooser;
import initcheck.database.Weapon;
import initcheck.database.WeaponDAO;
import initcheck.graphics.TiledDialog;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateWeaponsPanel extends TiledDialog {

	private static final long serialVersionUID = 1L;

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints c = new GridBagConstraints();

	private JTextField name = new JTextField(20);

	private JTextField cost = new JTextField(20);

	private JTextField damage = new JTextField(20);

	private JTextField critStart = new JTextField(3);

	private JTextField critEnd = new JTextField(3);

	private JTextField crit = new JTextField(3);

	private JTextField range = new JTextField(20);

	private JTextField weight = new JTextField(20);

	private DamageTypeChooser type = new DamageTypeChooser();

	private SizeChooser size = new SizeChooser();

	private WeaponCategoryChooser category = new WeaponCategoryChooser();

	private WeaponRangeChooser rangedmelee = new WeaponRangeChooser();

	private JTextField notes1 = new JTextField(20);

	private JTextField notes2 = new JTextField(20);

	private JTextField exclude = new JTextField(20);

	private JTextField featClass = new JTextField(20);

	private JPanel main = new JPanel();

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton cancelButton = new PanelButton("Close");

	private PanelButton prevButton = new PanelButton("<", 20);

	private PanelButton nextButton = new PanelButton(">", 20);
	
	private JPanel buttonPanel = new JPanel();

	private Weapon weapon;

	private SourceChooser sourceChooser = new SourceChooser();
	
	// private JCheckBox weaponCheck = new JCheckBox("Weapon Check Penalty");

	WeaponDAO db = new WeaponDAO();

	LibraryPanel owner;

	public CreateWeaponsPanel(LibraryPanel owner) {
		weapon = new Weapon();

		init();
		this.owner = owner;
	}

	public void setWeapon(Weapon f){
		this.weapon = f;

		name.setText(f.getName());
		cost.setText(f.getCost());
		damage.setText(f.getDamage());
		crit.setText(f.getCritMult());
		critStart.setText(f.getCritStart());
		critEnd.setText(f.getCritEnd());
		range.setText(f.getRange());
		weight.setText(f.getWeight());
		type.setSelectedItem(f.getType());
		size.setSelectedItem(f.getSize());
		category.setSelectedItem(f.getCategory());
		rangedmelee.setSelectedItem(f.getRangedmelee());
		notes1.setText(f.getNotes1());
		notes2.setText(f.getNotes2());
		exclude.setText(f.getExclude());
		featClass.setText(f.getFeatClass());
		sourceChooser.setSelectedItem(f.getSource());
	}
	
	public CreateWeaponsPanel(Weapon f, LibraryPanel owner) {

		this.owner = owner;
		setWeapon(f);

		init();

	}

	public void init() {

		main.setLayout(gridbag);
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 10;
		c.ipady = 10;

		int ypos = 0;

		doLayout(new JLabel("Source"), 0, ypos);
		doLayout(sourceChooser, 1, ypos);
		ypos++;
		
		doLayout(new JLabel("Name"), 0, ypos);
		doLayout(name, 1, ypos);
		JPanel critPanel = new JPanel();
		critPanel.add(critStart);
		critPanel.add(new JLabel("-"));
		critPanel.add(critEnd);
		critPanel.add(new JLabel("/x"));
		critPanel.add(crit);
		doLayout(new JLabel("Crit"), 2, ypos);
		doLayout(critPanel, 3, ypos);
		ypos++;

		doLayout(new JLabel("Cost"), 0, ypos);
		doLayout(cost, 1, ypos);
		doLayout(new JLabel("Range"), 2, ypos);
		doLayout(range, 3, ypos);
		ypos++;

		doLayout(new JLabel("Damage"), 0, ypos);
		doLayout(damage, 1, ypos);
		doLayout(new JLabel("Weight"), 2, ypos);
		doLayout(weight, 3, ypos);
		ypos++;

		doLayout(new JLabel("Type"), 0, ypos);
		doLayout(type, 1, ypos);
		doLayout(new JLabel("Size"), 2, ypos);
		doLayout(size, 3, ypos);
		ypos++;

		doLayout(new JLabel("Category"), 0, ypos);
		doLayout(category, 1, ypos);
		doLayout(new JLabel("Ranged/Melee"), 2, ypos);
		doLayout(rangedmelee, 3, ypos);
		ypos++;

		doLayout(new JLabel("Notes1"), 0, ypos);
		doLayout(notes1, 1, ypos);
		doLayout(new JLabel("Notes2"), 2, ypos);
		doLayout(notes2, 3, ypos);
		ypos++;

		doLayout(new JLabel("Exclude"), 0, ypos);
		doLayout(exclude, 1, ypos);
		doLayout(new JLabel("Feat Class"), 2, ypos);
		doLayout(featClass, 3, ypos);
		ypos++;

		buttonPanel.add(prevButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(nextButton);
		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveWeapon();
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showNext();
			}
		});

		prevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPrev();
			}
		});
		
		setMainWindow(main);
		setButtonBar(buttonPanel);
		pack();
		setVisible(true);
	}

	public void showNext() {
		owner.incSelected();
		setWeapon((Weapon) owner.getSelected());
	}

	public void showPrev() {
		owner.decSelected();
		setWeapon((Weapon) owner.getSelected());
	}
	
	public void saveWeapon() {
		weapon.setName(name.getText());
		weapon.setCost(cost.getText());
		weapon.setDamage(damage.getText());

		weapon.setCritMult(crit.getText());
		weapon.setCritStart(critStart.getText());
		weapon.setCritEnd(critEnd.getText());
		weapon.setRange(range.getText());
		weapon.setWeight(weight.getText());
		weapon.setType((String) type.getSelectedItem());
		weapon.setSize((String) size.getSelectedItem());
		weapon.setCategory((String) category.getSelectedItem());
		weapon.setRangedmelee((String) rangedmelee.getSelectedItem());
		weapon.setNotes1(notes1.getText());
		weapon.setNotes2(notes2.getText());
		weapon.setExclude(exclude.getText());
		weapon.setFeatClass(featClass.getText());
		weapon.setSource((String)sourceChooser.getSelectedItem());
		
		Vector<String> errors = weapon.validate();
		if (errors.size() > 0){
			String msg = "The following errors were encountered : \n\n";
			for (int i = 0; i < errors.size(); i++){
				msg += (String)errors.get(i);
				msg += "\n\n";
			}
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error",msg);
		}else{
			db.addOrUpdateWeapon(weapon);
			owner.updateList();
		}
	}

	private void doLayout(Component comp, int x, int y) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = 1;
		c.gridheight = 1;
		gridbag.setConstraints(comp, c);
		main.add(comp);
	}

	@SuppressWarnings("unused")
	private void doLayout(Component comp, int x, int y, int width, int height) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.gridheight = height;

		gridbag.setConstraints(comp, c);
		main.add(comp);
	}

}

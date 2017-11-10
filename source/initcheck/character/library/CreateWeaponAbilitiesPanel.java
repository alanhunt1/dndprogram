package initcheck.character.library;

import initcheck.PanelButton;
import initcheck.character.chooser.SourceChooser;
import initcheck.character.chooser.WeaponRangeChooser;
import initcheck.database.WeaponAbilities;
import initcheck.database.WeaponAbilitiesDAO;
import initcheck.graphics.TiledDialog;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CreateWeaponAbilitiesPanel extends TiledDialog {

	private static final long serialVersionUID = 1L;

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints c = new GridBagConstraints();

	private JTextField name = new JTextField(20);

	private WeaponRangeChooser weaponType = new WeaponRangeChooser();

	private JTextField bonus = new JTextField(5);

	private JTextField casterLevel = new JTextField(5);

	private JTextField prereq = new JTextField(20);

	private JTextField cost = new JTextField(5);

	private JTextField damage = new JTextField(5);

	private JTextArea description = new JTextArea(10, 50);

	private JScrollPane descScroll = new JScrollPane(description);

	private JPanel main = new JPanel();

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton cancelButton = new PanelButton("Close");

	private PanelButton prevButton = new PanelButton("<", 20);

	private PanelButton nextButton = new PanelButton(">", 20);
	
	private JPanel buttonPanel = new JPanel();

	private WeaponAbilities weaponAbilities;

	private SourceChooser sourceChooser = new SourceChooser();
	
	WeaponAbilitiesDAO db = new WeaponAbilitiesDAO();

	LibraryPanel owner;

	public CreateWeaponAbilitiesPanel(LibraryPanel owner) {

		this.owner = owner;
		this.weaponAbilities = new WeaponAbilities();
		init();

	}

	public CreateWeaponAbilitiesPanel(WeaponAbilities f, LibraryPanel owner) {
		this.owner = owner;
		setWeaponAbilities(f);

		init();
	}

	public void setWeaponAbilities(WeaponAbilities f){
		this.weaponAbilities = f;
		description.setText(f.getDescription());
		description.setLineWrap(true);
		description.setWrapStyleWord(true);

		name.setText(f.getAbilityName());
		weaponType.setSelectedItem(f.getWeaponType());
		bonus.setText(f.getBonus());
		casterLevel.setText(f.getCasterLevel());
		prereq.setText(f.getPrereq());
		cost.setText(f.getCost());
		damage.setText(f.getDamage());
		sourceChooser.setSelectedItem(f.getSource());
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
		
		doLayout(new JLabel("Ability Name"), 0, ypos);
		doLayout(name, 1, ypos);
		ypos++;

		doLayout(new JLabel("Ranged/Melee"), 0, ypos);
		doLayout(weaponType, 1, ypos);
		ypos++;

		doLayout(new JLabel("Enhancement Cost"), 0, ypos);
		doLayout(bonus, 1, ypos);
		ypos++;

		doLayout(new JLabel("Damage"), 0, ypos);
		doLayout(damage, 1, ypos);
		ypos++;

		doLayout(new JLabel("Caster Level"), 0, ypos);
		doLayout(casterLevel, 1, ypos);
		ypos++;

		doLayout(new JLabel("GP Cost"), 0, ypos);
		doLayout(cost, 1, ypos);
		ypos++;

		doLayout(new JLabel("Prereqs"), 0, ypos);
		doLayout(prereq, 1, ypos);
		ypos++;

		doLayout(descScroll, 0, ypos, 4, 1);
		ypos++;
		buttonPanel.add(prevButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(nextButton);
		
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
		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveWeaponAbilities();
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		setMainWindow(main);
		setButtonBar(buttonPanel);
		pack();
		setVisible(true);
	}

	public void showNext() {
		owner.incSelected();
		setWeaponAbilities((WeaponAbilities) owner.getSelected());
	}

	public void showPrev() {
		owner.decSelected();
		setWeaponAbilities((WeaponAbilities) owner.getSelected());
	}
	
	
	public void saveWeaponAbilities() {
		weaponAbilities.setAbilityName(name.getText());
		weaponAbilities.setDescription(description.getText());
		weaponAbilities.setWeaponType((String) weaponType.getSelectedItem());
		weaponAbilities.setBonus(bonus.getText());
		weaponAbilities.setCost(cost.getText());
		weaponAbilities.setCasterLevel(casterLevel.getText());
		weaponAbilities.setPrereq(prereq.getText());
		weaponAbilities.setDamage(damage.getText());
		weaponAbilities.setSource((String)sourceChooser.getSelectedItem());
		db.addOrUpdateWeaponAbilities(weaponAbilities);

		owner.updateList();
	}

	private void doLayout(Component comp, int x, int y) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = 1;
		c.gridheight = 1;
		gridbag.setConstraints(comp, c);
		main.add(comp);
	}

	private void doLayout(Component comp, int x, int y, int width, int height) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.gridheight = height;

		gridbag.setConstraints(comp, c);
		main.add(comp);
	}

}

package initcheck.character.library;

import initcheck.PanelButton;
import initcheck.character.chooser.SourceChooser;
import initcheck.database.Materials;
import initcheck.database.MaterialsDAO;
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

public class CreateMaterialsPanel extends TiledDialog {

	private static final long serialVersionUID = 1L;

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints c = new GridBagConstraints();

	private JTextField name = new JTextField(20);

	private JTextField weightModifier = new JTextField(20);

	private JTextArea description = new JTextArea(10, 50);

	private JScrollPane descScroll = new JScrollPane(description);

	private JPanel main = new JPanel();

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton cancelButton = new PanelButton("Close");
	
	private PanelButton prevButton = new PanelButton("<", 20);

	private PanelButton nextButton = new PanelButton(">", 20);

	private JPanel buttonPanel = new JPanel();

	private Materials materials;

	private SourceChooser sourceChooser = new SourceChooser();
	
	MaterialsDAO db = new MaterialsDAO();

	LibraryPanel owner;

	public CreateMaterialsPanel(LibraryPanel owner) {

		this.owner = owner;
		this.materials = new Materials();
		init();

	}

	public void setMaterials(Materials f){
		this.materials = f;
		description.setText(f.getDescription());
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		sourceChooser.setSelectedItem(f.getSource());
		name.setText(f.getMaterialName());
		weightModifier.setText(f.getWeightCalc());	
	}
	
	public CreateMaterialsPanel(Materials f, LibraryPanel owner) {
		this.owner = owner;
		setMaterials(f);

		init();
	}

	public void init() {

		main.setLayout(gridbag);
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 10;
		c.ipady = 10;

		int ypos = 0;

		doLayout(new JLabel("Material Source"), 0, ypos);
		doLayout(sourceChooser, 1, ypos);
		ypos++;
		
		doLayout(new JLabel("Material Name"), 0, ypos);
		doLayout(name, 1, ypos);
		ypos++;

		doLayout(new JLabel("Weight Modifier"), 0, ypos);
		doLayout(weightModifier, 1, ypos);
		ypos++;

		doLayout(descScroll, 0, ypos, 4, 1);
		ypos++;

		buttonPanel.add(prevButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(nextButton);
		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveMaterials();
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
		setMaterials((Materials) owner.getSelected());
	}

	public void showPrev() {
		owner.decSelected();
		setMaterials((Materials) owner.getSelected());
	}
	
	public void saveMaterials() {
		materials.setMaterialName(name.getText());
		materials.setDescription(description.getText());
		materials.setWeightCalc(weightModifier.getText());
		materials.setSource((String)sourceChooser.getSelectedItem());
		db.addOrUpdateMaterials(materials);

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

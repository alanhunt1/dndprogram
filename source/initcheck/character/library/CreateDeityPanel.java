package initcheck.character.library;

import initcheck.PanelButton;
import initcheck.character.GridPanel;
import initcheck.character.chooser.AlignmentChooser;
import initcheck.character.chooser.SourceChooser;
import initcheck.database.Deity;
import initcheck.database.DeityDAO;
import initcheck.graphics.TiledDialog;

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CreateDeityPanel extends TiledDialog {

	private static final long serialVersionUID = 1L;

	private JTextField name = new JTextField(20);

	private AlignmentChooser alignment = new AlignmentChooser();
	
	private SourceChooser source = new SourceChooser();
	
	private JTextField weapon = new JTextField(20);
	
	private JTextArea description = new JTextArea(10, 50);

	private JScrollPane descScroll = new JScrollPane(description);

	private GridPanel main = new GridPanel();

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton cancelButton = new PanelButton("Close");

	private JPanel buttonPanel = new JPanel();

	private Deity Deity;

	private PanelButton prevButton = new PanelButton("<", 20);

	private PanelButton nextButton = new PanelButton(">", 20);
	
	DeityDAO db = new DeityDAO();

	LibraryPanel owner;

	public CreateDeityPanel(LibraryPanel owner) {
		Deity = new Deity();

		init();
		this.owner = owner;
	}

	public CreateDeityPanel(Deity f, LibraryPanel owner) {

		this.owner = owner;
		this.Deity = f;

		description.setText(f.getDescription());
		name.setText(f.getName());
		source.setSelectedItem(f.getSource());
		alignment.setSelectedItem(f.getAlignment());
		weapon.setText(f.getFavoredWeapon());

		init();

	}

	public void setDeity(Deity f){
		
		this.Deity = f;

		description.setText(f.getDescription());
		name.setText(f.getName());
		source.setSelectedItem(f.getSource());
		alignment.setSelectedItem(f.getAlignment());
		weapon.setText(f.getFavoredWeapon());
	
	}
	
	public void init() {

		description.setWrapStyleWord(true);
		description.setLineWrap(true);

		int ypos = main.ypos;

		main.doLayout(new Label("Source"), 0, ypos);
		main.doLayout(source, 1, ypos);
		ypos++;
		
		main.doLayout(new Label("Name"), 0, ypos);
		main.doLayout(name, 1, ypos);
		ypos++;

		main.doLayout(new JLabel("Alignment"), 0, ypos);
		main.doLayout(alignment, 1, ypos);
		ypos++;

		main.doLayout(new JLabel("Favored Weapon"), 0, ypos);
		main.doLayout(weapon, 1, ypos);
		ypos++;
		
		main.setWeightX(1.0);
		main.setWeightY(0.5);

		main.doLayout(descScroll, 0, ypos, 2, 1);
		main.setWeightX(0);
		main.setWeightY(0);

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
				saveDeity();
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
		setDeity((Deity) owner.getSelected());
	}

	public void showPrev() {
		owner.decSelected();
		setDeity((Deity) owner.getSelected());
	}
	
	public void saveDeity() {
		Deity.setName(name.getText());
		Deity.setAlignment((String)alignment.getSelectedItem());
		Deity.setDescription(description.getText());
		Deity.setFavoredWeapon(weapon.getText());
		Deity.setSource((String)source.getSelectedItem());
		int i = db.addOrUpdateDeity(Deity);
		Deity.setId("" + i);
		owner.updateList();
	}

}

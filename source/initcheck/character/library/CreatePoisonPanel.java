package initcheck.character.library;

import initcheck.PanelButton;
import initcheck.character.GridPanel;
import initcheck.character.chooser.PoisonTypeChooser;
import initcheck.character.chooser.SourceChooser;
import initcheck.database.Poison;
import initcheck.database.PoisonDAO;
import initcheck.graphics.TiledDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreatePoisonPanel extends TiledDialog {

	private static final long serialVersionUID = 1L;

	private JTextField name = new JTextField(20);

	private JTextField poisonDc = new JTextField(20);

	private JTextField initial = new JTextField(20);

	private JTextField secondary = new JTextField(20);

	private JTextField price = new JTextField(20);

	// private JTextArea description = new JTextArea(10, 40);

	// private JScrollPane descScroll = new JScrollPane(description);

	private GridPanel main = new GridPanel();

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton cancelButton = new PanelButton("Close");

	private PanelButton prevButton = new PanelButton("<", 20);

	private PanelButton nextButton = new PanelButton(">", 20);

	private JPanel buttonPanel = new JPanel();

	private Poison poison;

	private PoisonTypeChooser typeChooser = new PoisonTypeChooser();

	private SourceChooser sourceChooser = new SourceChooser();
	
	PoisonDAO db = new PoisonDAO();

	LibraryPanel owner;

	public CreatePoisonPanel(LibraryPanel owner) {
		poison = new Poison();

		init();
		this.owner = owner;
	}

	public CreatePoisonPanel(Poison f, LibraryPanel owner) {

		this.owner = owner;
		setPoison(f);
		init();

	}

	public void init() {

		// description.setLineWrap(true);
		// description.setWrapStyleWord(true);

		main.doLayout(new JLabel("Source"), 0, main.ypos);
		main.doLayout(sourceChooser, 1, main.ypos);
		main.ypos++;
		
		main.doLayout(new JLabel("Name"), 0, main.ypos);
		main.doLayout(name, 1, main.ypos);
		main.ypos++;

		main.doLayout(new JLabel("Poison DC"), 0, main.ypos);
		main.doLayout(poisonDc, 1, main.ypos);

		main.ypos++;

		main.doLayout(new JLabel("Initial"), 0, main.ypos);
		main.doLayout(initial, 1, main.ypos);
		main.ypos++;

		main.doLayout(new JLabel("Secondary"), 0, main.ypos);
		main.doLayout(secondary, 1, main.ypos);

		main.ypos++;

		main.doLayout(new JLabel("Poison Type"), 0, main.ypos);
		main.doLayout(typeChooser, 1, main.ypos);

		main.ypos++;

		main.doLayout(new JLabel("Cost"), 0, main.ypos);
		main.doLayout(price, 1, main.ypos);
		main.ypos++;

		// main.doLayout(new JLabel("Description"), 0, main.ypos);
		// main.doLayout(descScroll, 1, main.ypos);
		buttonPanel.add(prevButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(nextButton);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				savePoison();
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

	public void setPoison(Poison f) {
		this.poison = f;

		name.setText(f.getName());
		poisonDc.setText(f.getDc());
		initial.setText(f.getInitial());

		// description.setText(f.getPoisonDescription());
		typeChooser.setSelectedItem(f.getType());

		price.setText(f.getPrice());
		secondary.setText(f.getSecondary());
		sourceChooser.setSelectedItem(f.getSource());
	}

	public void showNext() {
		owner.incSelected();
		setPoison((Poison) owner.getSelected());
	}

	public void showPrev() {
		owner.decSelected();
		setPoison((Poison) owner.getSelected());
	}

	public void savePoison() {

		poison.setName(name.getText());
		poison.setDc(poisonDc.getText());
		poison.setInitial(initial.getText());

		// poison.setPoisonDescription(description.getText());

		poison.setType((String) typeChooser.getSelectedItem());

		poison.setSecondary(secondary.getText());
		poison.setPrice(price.getText());
		poison.setSource((String)sourceChooser.getSelectedItem());
		db.addOrUpdatePoison(poison);
		owner.updateList();

	}

}

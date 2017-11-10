package initcheck.character.library;

import initcheck.PanelButton;
import initcheck.character.GridPanel;
import initcheck.character.chooser.ArtifactTypeChooser;
import initcheck.character.chooser.SourceChooser;
import initcheck.database.Artifact;
import initcheck.database.ArtifactDAO;
import initcheck.graphics.TiledDialog;

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CreateArtifactPanel extends TiledDialog {

	private static final long serialVersionUID = 1L;

	private JTextField name = new JTextField(20);

	private ArtifactTypeChooser type = new ArtifactTypeChooser();
	
	private SourceChooser source = new SourceChooser();
	
	private JTextField weight = new JTextField(20);
	
	private JTextArea description = new JTextArea(10, 50);

	private JScrollPane descScroll = new JScrollPane(description);

	private GridPanel main = new GridPanel();

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton cancelButton = new PanelButton("Close");

	private JPanel buttonPanel = new JPanel();

	private Artifact Artifact;

	private PanelButton prevButton = new PanelButton("<", 20);

	private PanelButton nextButton = new PanelButton(">", 20);
	
	ArtifactDAO db = new ArtifactDAO();

	LibraryPanel owner;

	public CreateArtifactPanel(LibraryPanel owner) {
		Artifact = new Artifact();

		init();
		this.owner = owner;
	}

	public CreateArtifactPanel(Artifact f, LibraryPanel owner) {

		this.owner = owner;
		this.Artifact = f;

		description.setText(f.getDescription());
		name.setText(f.getName());
		source.setSelectedItem(f.getSource());
		type.setSelectedItem(f.getType());
		weight.setText(f.getWeight());

		init();

	}

	public void setArtifact(Artifact f){
		
		this.Artifact = f;

		description.setText(f.getDescription());
		name.setText(f.getName());
		source.setSelectedItem(f.getSource());
		type.setSelectedItem(f.getType());
		weight.setText(f.getWeight());
	
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

		main.doLayout(new JLabel("Type"), 0, ypos);
		main.doLayout(type, 1, ypos);
		ypos++;

		main.doLayout(new JLabel("Weight"), 0, ypos);
		main.doLayout(weight, 1, ypos);
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
				saveArtifact();
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
		setArtifact((Artifact) owner.getSelected());
	}

	public void showPrev() {
		owner.decSelected();
		setArtifact((Artifact) owner.getSelected());
	}
	
	public void saveArtifact() {
		Artifact.setName(name.getText());
		Artifact.setType((String)type.getSelectedItem());
		Artifact.setDescription(description.getText());
		Artifact.setWeight(weight.getText());
		Artifact.setSource((String)source.getSelectedItem());
		int i = db.addOrUpdateArtifact(Artifact);
		Artifact.setId("" + i);
		owner.updateList();
	}

}

package initcheck.character.library;

import initcheck.PanelButton;
import initcheck.character.GridPanel;
import initcheck.database.MaterialSource;
import initcheck.database.MaterialSourceDAO;
import initcheck.graphics.TiledDialog;

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CreateMaterialSourcePanel extends TiledDialog {

	private static final long serialVersionUID = 1L;

	private JTextField name = new JTextField(20);

	private JTextField version = new JTextField(20);
	
	private JTextArea description = new JTextArea(10, 50);

	private JScrollPane descScroll = new JScrollPane(description);

	private GridPanel main = new GridPanel();

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton cancelButton = new PanelButton("Close");

	private JPanel buttonPanel = new JPanel();

	private MaterialSource MaterialSource;

	MaterialSourceDAO db = new MaterialSourceDAO();

	LibraryPanel owner;

	public CreateMaterialSourcePanel(LibraryPanel owner) {
		MaterialSource = new MaterialSource();

		init();
		this.owner = owner;
	}

	public CreateMaterialSourcePanel(MaterialSource f, LibraryPanel owner) {

		this.owner = owner;
		this.MaterialSource = f;

		description.setText(f.getDescription());
		name.setText(f.getSourceName());
		version.setText(f.getDndVersion());
		init();

	}

	public void init() {

		description.setWrapStyleWord(true);
		description.setLineWrap(true);

		int ypos = main.ypos;

		main.doLayout(new Label("Source Name"), 0, ypos);
		main.doLayout(name, 1, ypos);
		ypos++;

		main.doLayout(new JLabel("Source Version"), 0, ypos);
		main.doLayout(version, 1, ypos);
		ypos++;

		main.setWeightX(1.0);
		main.setWeightY(0.5);

		main.doLayout(descScroll, 0, ypos, 2, 1);
		main.setWeightX(0);
		main.setWeightY(0);

		ypos++;

		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveMaterialSource();
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

	public void saveMaterialSource() {
		MaterialSource.setSourceName(name.getText());
		MaterialSource.setDndVersion(version.getText());

		int i = db.addOrUpdateMaterialSource(MaterialSource);
		MaterialSource.setSourceId("" + i);
		owner.updateList();
	}

}

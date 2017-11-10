package initcheck.character.library;

import initcheck.MessageDialog;
import initcheck.PanelButton;
import initcheck.character.GridPanel;
import initcheck.character.chooser.SourceChooser;
import initcheck.character.chooser.TrapResetTypeChooser;
import initcheck.character.chooser.TrapTriggerTypeChooser;
import initcheck.character.chooser.TrapTypeChooser;
import initcheck.database.Trap;
import initcheck.database.TrapDAO;
import initcheck.graphics.TiledDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CreateTrapPanel extends TiledDialog {

	private static final long serialVersionUID = 1L;

	private JTextField name = new JTextField(20);

	private JTextField searchDc = new JTextField(20);

	private JTextField disableDc = new JTextField(20);

	private JTextField bypassDetectDc = new JTextField(20);
	
	private JTextField bypassUseDc = new JTextField(20);
	
	private TrapResetTypeChooser trapResetChooser = new TrapResetTypeChooser();
	
	private TrapTriggerTypeChooser trapTriggerChooser = new TrapTriggerTypeChooser();
	
	private JCheckBox bypass = new JCheckBox();
	
	private JTextArea description = new JTextArea(10, 40);

	private JScrollPane descScroll = new JScrollPane(description);

	private GridPanel main = new GridPanel();

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton cancelButton = new PanelButton("Close");

	private PanelButton prevButton = new PanelButton("<", 20);

	private PanelButton nextButton = new PanelButton(">", 20);

	private JPanel buttonPanel = new JPanel();

	private JTextField challengeRating = new JTextField(20);
	
	private Trap trap;

	private TrapTypeChooser typeChooser = new TrapTypeChooser();

	private SourceChooser sourceChooser = new SourceChooser();
	
	// private JCheckBox trapCheck = new JCheckBox("Trap Check
	// Penalty");

	TrapDAO db = new TrapDAO();

	LibraryPanel owner;

	public CreateTrapPanel(LibraryPanel owner) {
		trap = new Trap();

		init();
		this.owner = owner;
	}

	public CreateTrapPanel(Trap f, LibraryPanel owner) {

		this.owner = owner;
		setTrap(f);
		
		init();

	}

	public void init() {

		description.setLineWrap(true);
		description.setWrapStyleWord(true);

		main.doLayout(new JLabel("Source"), 0, main.ypos);
		main.doLayout(sourceChooser, 1, main.ypos);
		main.ypos++;

		
		main.doLayout(new JLabel("Name"), 0, main.ypos);
		main.doLayout(name, 1, main.ypos);
		main.ypos++;
		
		main.doLayout(new JLabel("Challenge Rating"), 0, main.ypos);
		main.doLayout(challengeRating, 1, main.ypos);
		main.ypos++;


		main.doLayout(new JLabel("Search DC"), 0, main.ypos);
		main.doLayout(searchDc, 1, main.ypos);

		main.ypos++;

		main.doLayout(new JLabel("Disable DC"), 0, main.ypos);
		main.doLayout(disableDc, 1, main.ypos);
		main.ypos++;

		main.doLayout(new JLabel("Trap Type"), 0, main.ypos);
		main.doLayout(typeChooser, 1, main.ypos);

		main.ypos++;

		main.doLayout(new JLabel("Trigger Type"), 0, main.ypos);
		main.doLayout(trapTriggerChooser, 1, main.ypos);

		main.ypos++;

		main.doLayout(new JLabel("Reset Type"), 0, main.ypos);
		main.doLayout(trapResetChooser, 1, main.ypos);
		
		main.ypos++;

		main.doLayout(new JLabel("Bypass"), 0, main.ypos);
		main.doLayout(bypass, 1, main.ypos);
		
		main.ypos++;

		main.doLayout(new JLabel("Bypass Search DC"), 0, main.ypos);
		main.doLayout(bypassDetectDc, 1, main.ypos);
		
		main.ypos++;

		main.doLayout(new JLabel("Bypass Use DC"), 0, main.ypos);
		main.doLayout(bypassUseDc, 1, main.ypos);
		main.ypos++;

		main.doLayout(new JLabel("Description"), 0, main.ypos);
		main.doLayout(descScroll, 1, main.ypos);
		buttonPanel.add(prevButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(nextButton);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveTrap();
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

	public void setTrap(Trap f) {
		this.trap = f;
		challengeRating.setText(f.getCr());
		name.setText(f.getTrapName());
		searchDc.setText(f.getSpotDc());
		disableDc.setText(f.getDisableDc());
		sourceChooser.setSelectedItem(f.getSource());
		description.setText(f.getTrapDescription());
		typeChooser.setSelectedItem(f.getTrapType());
		trapTriggerChooser.setSelectedItem(f.getTriggerType());
		trapResetChooser.setSelectedItem(f.getResetType());
		bypass.setSelected(f.isBypass());
		bypassUseDc.setText(f.getBypassUseDc());
		bypassDetectDc.setText(f.getBypassDetectDc());
	}

	public void showNext() {
		owner.incSelected();
		setTrap((Trap) owner.getSelected());
	}

	public void showPrev() {
		owner.decSelected();
		setTrap((Trap) owner.getSelected());
	}

	public void saveTrap() {
		try {
			trap.setCr(challengeRating.getText());
			trap.setTrapName(name.getText());
			trap.setSpotDc(searchDc.getText());
			trap.setDisableDc(disableDc.getText());
			@SuppressWarnings("unused")
			int i = Integer.parseInt(disableDc.getText());
			trap.setTrapDescription(description.getText());
			
			trap.setTrapType((String) typeChooser.getSelectedItem());
			trap.setTriggerType((String) trapTriggerChooser.getSelectedItem());
			trap.setResetType((String) trapResetChooser.getSelectedItem());
			trap.setBypass(bypass.isSelected());
			trap.setBypassDetectDc(bypassDetectDc.getText());
			trap.setBypassUseDc(bypassUseDc.getText());
			trap.setSource((String) sourceChooser.getSelectedItem());
			
			db.addOrUpdateTrap(trap);
			owner.updateList();
		} catch (NumberFormatException nfe) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error!",
					"You must enter an integer value for the disableDc");

		}
	}

}

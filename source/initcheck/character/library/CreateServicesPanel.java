package initcheck.character.library;

import initcheck.MessageDialog;
import initcheck.PanelButton;
import initcheck.character.GridPanel;
import initcheck.character.chooser.ServiceTypeChooser;
import initcheck.database.Services;
import initcheck.database.ServicesDAO;
import initcheck.graphics.TiledDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CreateServicesPanel extends TiledDialog {

	private static final long serialVersionUID = 1L;

	private JTextField name = new JTextField(20);

	private JTextField ServiceCost = new JTextField(20);

	private JTextArea ServiceDescription = new JTextArea(10, 40);

	private JScrollPane descScroll = new JScrollPane(ServiceDescription);

	private GridPanel main = new GridPanel();

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton cancelButton = new PanelButton("Close");

	private PanelButton prevButton = new PanelButton("<", 20);

	private PanelButton nextButton = new PanelButton(">", 20);

	private JPanel buttonPanel = new JPanel();

	private Services Services;

	private ServiceTypeChooser serviceTypeChooser = new ServiceTypeChooser();

	// private JCheckBox ServicesCheck = new JCheckBox("Services Check
	// Penalty");

	ServicesDAO db = new ServicesDAO();

	LibraryPanel owner;

	public CreateServicesPanel(LibraryPanel owner) {
		Services = new Services();

		init();
		this.owner = owner;
	}

	public CreateServicesPanel(Services f, LibraryPanel owner) {

		this.owner = owner;
		this.Services = f;

		name.setText(f.getServiceName());
		ServiceCost.setText(f.getServiceCost());

		ServiceDescription.setText(f.getServiceDescription());
		serviceTypeChooser.setSelectedItem(f.getServiceType());
		init();

	}

	public void init() {

		ServiceDescription.setLineWrap(true);
		ServiceDescription.setWrapStyleWord(true);

		main.doLayout(new JLabel("Name"), 0, main.ypos);
		main.doLayout(name, 1, main.ypos);
		main.ypos++;

		main.doLayout(new JLabel("ServiceCost"), 0, main.ypos);
		main.doLayout(ServiceCost, 1, main.ypos);

		main.ypos++;

		main.doLayout(new JLabel("ServiceType"), 0, main.ypos);
		main.doLayout(serviceTypeChooser, 1, main.ypos);

		main.ypos++;

		main.doLayout(new JLabel("ServiceDescription"), 0, main.ypos);
		main.doLayout(descScroll, 1, main.ypos);
		buttonPanel.add(prevButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(nextButton);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveServices();
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

	public void setServices(Services f) {
		this.Services = f;

		name.setText(f.getServiceName());
		ServiceCost.setText(f.getServiceCost());

		ServiceDescription.setText(f.getServiceDescription());
		serviceTypeChooser.setSelectedItem(f.getServiceType());
	}

	public void showNext() {
		owner.incSelected();
		setServices((Services) owner.getSelected());
	}

	public void showPrev() {
		owner.decSelected();
		setServices((Services) owner.getSelected());
	}

	public void saveServices() {
		try {
			Services.setServiceName(name.getText());
			Services.setServiceCost(ServiceCost.getText());

			Services.setServiceDescription(ServiceDescription.getText());
			Services.setServiceType((String) serviceTypeChooser
					.getSelectedItem());
			db.addOrUpdateServices(Services);
			owner.updateList();
		} catch (NumberFormatException nfe) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error!",
					"You must enter an integer value for the weight");

		}
	}

}

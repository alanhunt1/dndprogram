package initcheck.generator;

import initcheck.InitFont;
import initcheck.PanelButton;
import initcheck.character.ServiceChooserDialog;
import initcheck.character.ServiceChooserOwner;
import initcheck.database.Services;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ServicesPanel extends TiledGridPanel implements ServiceChooserOwner {

	private static final long serialVersionUID = 1L;

	private Vector<Services> services = new Vector<Services>();

	private TiledList servicesList = new TiledList();

	private JScrollPane invScroll = new JScrollPane(servicesList);

	private PanelButton addButton = new PanelButton("Add", 50);

	private PanelButton remButton = new PanelButton("Remove", 50);

	
	
	public ServicesPanel() {
		super();
		servicesList.setFont(InitFont.courier);
		servicesList.setCellRenderer(new ServicesListCellRenderer());
		
		setBorder(BorderFactory.createEmptyBorder());

		JPanel buttonPanel = new JPanel();
		
		buttonPanel.add(addButton);
		buttonPanel.add(remButton);
		
		setWeightX(1);
		setWeightY(1);
		doLayout(invScroll);
		incYPos();
		setWeightX(0);
		setWeightY(0);
		doLayout(buttonPanel);

		
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addServices();
			}
		});

		remButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remServices();
			}
		});

		
	}
	
	public void addServices() {
		@SuppressWarnings("unused")ServiceChooserDialog icd = new ServiceChooserDialog(this);
	}

	public void setService(Services s){
		
		services.add(s);
		servicesList.setListData(services);
	}
	
	public void remServices() {
		int selIndex = servicesList.getSelectedIndex();
		if (selIndex >= 0){
			services.remove(selIndex);
			servicesList.setListData(services);
		}
	}

	public Vector<Services> getServices() {
		return services;
	}

	public void setServices(Vector<Services> services) {
		this.services = services;
		servicesList.setListData(services);
	}

	public Vector<Services> getModel() {
		return services;
	}
	
	public void setItem(Services e){
		int selIndex = servicesList.getSelectedIndex();
		services.add(e);
		servicesList.setListData(services);
		if (selIndex >= 0){
			servicesList.setSelectedIndex(selIndex);
		}
	}

}

package initcheck.generator;

import initcheck.PanelButton;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class BuildingListingPanel extends TiledGridPanel implements ListSelectionListener {

	private static final long serialVersionUID = 1L;

	private TiledList buildingList = new TiledList();

	private JScrollPane listScroll = new JScrollPane(buildingList);

	private PanelButton addButton = new PanelButton("Add");

	private PanelButton remButton = new PanelButton("Rem");

	private Vector<Building> buildings;

	private BuildingInfoPanel buildingInfo = new BuildingInfoPanel();
	
	public BuildingListingPanel(Vector<Building> buildings) {
		this.buildings = buildings;
		buildingList.setListData(new Vector<Building>(buildings));
		buildingList.addListSelectionListener(this);
		buildingList.setCellRenderer(new StoreListCellRenderer());
		
		setWeightX(0.5);
		setWeightY(1);
		doLayout(listScroll);
		doLayout(buildingInfo, 1);
		setWeightX(0);
		setWeightY(0);
		incYPos();
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(addButton);
		buttonPanel.add(remButton);
		doLayout(buttonPanel);

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addBuilding();
			}
		});

		remButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeBuilding();
			}
		});
	}

	private void addBuilding() {
		buildings.add(new Building());
		buildingList.setListData(new Vector<Building>(buildings));
	}

	private void removeBuilding() {
		int idx = buildingList.getSelectedIndex();
		buildings.remove(idx);
		buildingList.setListData(new Vector<Building>(buildings));
	}
	
	public void setModel(Vector<Building> buildings){
		this.buildings = buildings;
		buildingList.setListData(new Vector<Building>(buildings));
		if (buildings.size() > 0){
			buildingList.setSelectedIndex(0);
		}
	}
	
	public Vector<Building> getModel(){
		buildings.clear();
		for (int i = 0; i < buildingList.getModel().getSize(); i++){
			Building b = (Building)buildingList.getModel().getElementAt(i);
			buildings.add(b);
		}
		return buildings;
	}
	
	public void valueChanged(ListSelectionEvent e) {

		try {
			Building f = (Building) buildingList.getSelectedValue();
			if (f != null) {
				buildingInfo.setBuilding(f);
			}
		} catch (Exception ex) {

			ex.printStackTrace();
		}
		
	}
	
}

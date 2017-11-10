package initcheck.dungeon;

import initcheck.InitImage;
import initcheck.InitLogger;
import initcheck.PanelButton;
import initcheck.character.GenericListCellRenderer;
import initcheck.database.MapType;
import initcheck.database.MapTypeDAO;
import initcheck.database.TerrainTypes;
import initcheck.database.TerrainTypesDAO;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PaletteConfigurationPanel extends TiledGridPanel  {

	private static final long serialVersionUID = 1L;

	private TiledList mapTypeList = new TiledList();

	private TiledList terrainTypeList = new TiledList();

	private TiledGridPanel control = new TiledGridPanel(InitImage.lightRocks);

	private PanelButton addMapType = new PanelButton("Add", 50);

	private PanelButton delMapType = new PanelButton("Remove", 50);
	
	private PanelButton addTerrainType = new PanelButton("Add", 50);

	private PanelButton delTerrainType = new PanelButton("Remove", 50);
	
	private TerrainTypePanel terrain = new TerrainTypePanel();

	InitLogger logger = new InitLogger(this, "defaultLog4j.cfg");
	
	
	
	public PaletteConfigurationPanel() {
		MapTypeDAO mdb = new MapTypeDAO();
		mapTypeList.setListData(mdb.getMapTypes());
		mapTypeList.setCellRenderer(new GenericListCellRenderer());
		terrainTypeList.setCellRenderer(new GenericListCellRenderer());

		control.setWeightX(0);
		control.setWeightY(0);
		control.doLayout(new JLabel("Map Type"));
		control.incYPos();
		control.setWeightX(1.0);
		control.setWeightY(0.5);
		control.doLayout(mapTypeList);
		control.setWeightX(0);
		control.setWeightY(0);
		control.incYPos();
		
		JPanel mapButtonPanel = new JPanel();
		mapButtonPanel.add(addMapType);
		mapButtonPanel.add(delMapType);
		mapButtonPanel.setOpaque(false);
		control.doLayout(mapButtonPanel);
		control.incYPos();
		
		control.doLayout(new JLabel("Terrain Type"));
		control.incYPos();
		control.setWeightX(1.0);
		control.setWeightY(0.5);
		control.doLayout(terrainTypeList);
		control.setWeightX(0);
		control.setWeightY(0);
		control.incYPos();
		
		JPanel terrainButtonPanel = new JPanel();
		terrainButtonPanel.setOpaque(false);
		terrainButtonPanel.add(addTerrainType);
		terrainButtonPanel.add(delTerrainType);
		control.doLayout(terrainButtonPanel);
		control.incYPos();
		
		
		setWeightX(0.25);
		setWeightY(1);
		doLayout(control, 0);
		setWeightX(0.75);
		doLayout(terrain, 1);
		
		mapTypeList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				loadTerrainTypes();
			}
		});

		terrainTypeList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				displayTerrainType();
			}
		});
		
		addMapType.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						addMapType();
					}
				}
		);
		
		delMapType.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						remMapType();
					}
				}
		);
		
		addTerrainType.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						addTerrainType();
					}
				}
		);
		
		delTerrainType.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						remTerrainType();
					}
				}
		);
	}
	
	public void addMapType(){
		MapTypeDAO mtdb = new MapTypeDAO();
		MapType mt = new MapType();
		mt.setName("New Map Type");
		mtdb.addMapType(mt);
		mapTypeList.setListData(mtdb.getMapTypes());
		if (mapTypeList.getModel().getSize() > 0){
			mapTypeList.setSelectedIndex(0);
		}
	}

	public void remMapType(){
		MapType mt = (MapType)mapTypeList.getSelectedValue();
		MapTypeDAO mtdb = new MapTypeDAO();
		mtdb.deleteMapType(mt);
		mapTypeList.setListData(mtdb.getMapTypes());
		if (mapTypeList.getModel().getSize() > 0){
			mapTypeList.setSelectedIndex(0);
		}
	}
	
	public void addTerrainType(){
		String inputValue = JOptionPane.showInputDialog("Enter New Type Name");
		TerrainTypesDAO ttdb = new TerrainTypesDAO();
		TerrainTypes t = new TerrainTypes();
		t.setTerrainName(inputValue);
		t.setMapType(((MapType)mapTypeList.getSelectedValue()).getName());
		ttdb.addOrUpdateTerrainTypes(t);
		loadTerrainTypes();
	}
	
	public void remTerrainType(){
		TerrainTypesDAO ttdb = new TerrainTypesDAO();
		ttdb.deleteTerrainTypes((TerrainTypes)terrainTypeList.getSelectedValue());
		loadTerrainTypes();
	}
	
	public void loadTerrainTypes() {
		TerrainTypesDAO ttdb = new TerrainTypesDAO();
		Vector<TerrainTypes> v = ttdb.getTerrainTypes(((MapType) mapTypeList
				.getSelectedValue()).getName());
		terrainTypeList.setListData(v);
		if (v.size() > 0) {
			terrainTypeList.setSelectedIndex(0);
		}
	}

	public void displayTerrainType() {
		if (terrainTypeList.getSelectedIndex() > -1) {
			TerrainTypes t = (TerrainTypes) terrainTypeList.getSelectedValue();
			if (t != null) {
				terrain.setTerrainType(t);
				logger.log(t.toLog());
			}
		}
	}

	

}

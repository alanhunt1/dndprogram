package initcheck.generator;

import initcheck.InitImage;
import initcheck.character.GridPanel;
import initcheck.graphics.Skin;
import initcheck.graphics.Skinnable;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledTextArea;
import initcheck.server.CampaignNotesPanel;
import initcheck.server.CampaignNotesTab;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class TownInfoPanel extends TiledGridPanel implements Serializable,
		CampaignNotesTab, Skinnable {

	private static final long serialVersionUID = 1L;

	private JTextField townName = new JTextField(20);

	private JTextField population = new JTextField(20);

	private TiledTextArea description = new TiledTextArea(10, 40);

	private JScrollPane descScroll = new JScrollPane(description);
	
	private RandomButton randomTownNameButton = new RandomButton();

	private Town town = new Town();

	private JTabbedPane buildings = new JTabbedPane();

	private NameGenerator nameGen = new NameGenerator();

	private CampaignNotesPanel owner;

	private StoreListingPanel stores = new StoreListingPanel(town.getStores());

	private InnListingPanel inns = new InnListingPanel(town.getInns());

	private BuildingListingPanel otherBuildings = new BuildingListingPanel(town
			.getBuildings());

	private Skin skin;
	
	public TownInfoPanel(CampaignNotesPanel owner) {
		super(InitImage.lightRocks);
		this.owner = owner;
		setBorder(null);
		description.setWrapStyleWord(true);
		description.setLineWrap(true);
		
		setWeightX(1.0);
		setWeightY(0.5);
		
		GridPanel headerPanel = new GridPanel();
		
		headerPanel.doLayout(new JLabel("Name"));
		JPanel wrapper = new JPanel();
		wrapper.add(townName);
		wrapper.add(randomTownNameButton);
		wrapper.setOpaque(false);
		headerPanel.setOpaque(false);
		headerPanel.doLayout(wrapper, 1);
	
		//headerPanel.doLayout(randomTownNameButton, 2);
	
		headerPanel.incYPos();
		headerPanel.doLayout(new JLabel("Population"));
		JPanel wrapper2 = new JPanel();
		wrapper2.setOpaque(false);
		wrapper2.add(population);
		headerPanel.doLayout(wrapper2, 1);
		headerPanel.setWeightX(1);
		headerPanel.setWeightY(1);
		
		headerPanel.incYPos();
		headerPanel.doLayout(descScroll, 0, headerPanel.ypos, 2, 1);
		headerPanel.incYPos();
		doLayout(headerPanel);
		incYPos();
		doLayout(buildings);

		buildings.addTab("Stores", stores);
		buildings.addTab("Inns", inns);
		buildings.addTab("Buildings", otherBuildings);

		randomTownNameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateTownName(true);
			}
		});

		generateTownName(false);
	}

	public void init() {
		townName.setText(town.getName());
		population.setText("" + town.getPopulation());
		description.setText(town.getDescription());
		
		stores.setModel(town.getStores());
		inns.setModel(town.getInns());
		otherBuildings.setModel(town.getBuildings());	
		//owner.renameCurrentTab(townName.getText());
	}

	public void generateTownName(boolean rename) {
		townName.setText(nameGen.getRandomName());
		if (rename){
			owner.renameCurrentTab(townName.getText());
		}
	}

	public int getType() {

		return CampaignNotesPanel.TOWN_TAB;
	}

	public Object getModel() {
		// first collect the various sub tab data
		town.setStores(stores.getModel());
		town.setInns(inns.getModel());
		town.setBuildings(otherBuildings.getModel());
		
		// set the town level data
		//owner.renameCurrentTab(townName.getText());
		town.setName(townName.getText());
		town.setPopulation(Integer.parseInt(population.getText()));
		town.setDescription(description.getText());
		
		return town;
	}

	public void setModel(Object o) {

		town = (Town) o;
		
		init();

	}

	public void setSkin(Skin s){
		this.skin = s;
	}
	
	public Skin getSkin(){
		return skin;
	}
	
}

package initcheck.generator;

import initcheck.InitImage;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledTextArea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class InnInfoPanel extends TiledGridPanel {

	private static final long serialVersionUID = 1L;

	private Inn inn = new Inn();
	
	private ServicesPanel servicePanel = new ServicesPanel();
	
	private JTabbedPane tabPane = new JTabbedPane();
	
	private TiledTextArea description = new TiledTextArea(15, 30);

	private JScrollPane descScroll = new JScrollPane(description);

	private JTextField name = new JTextField(30);

	private JTextField owner = new JTextField(30);

	private RandomButton randomNameButton = new RandomButton();

	private RandomButton randomOwnerButton = new RandomButton();

	private CharacterNameGenerator ownerGen = new CharacterNameGenerator();

	private StoreNameGenerator nameGen = new StoreNameGenerator();
	
	private InventoryPanel invPanel = new InventoryPanel("Equip/Misc");
	
	
	
	public InnInfoPanel() {
		super(InitImage.lightRocks);
		setWeightX(1);
		setWeightY(0);
		description.setWrapStyleWord(true);
		description.setLineWrap(true);
		description.setText(inn.getDescription());
		JPanel namePanel = new JPanel();
		namePanel.add(new JLabel("Name"));
		namePanel.add(name);
		namePanel.add(randomNameButton);
		namePanel.setOpaque(false);
		doLayout(namePanel);

		incYPos();

		JPanel ownerPanel = new JPanel();
		ownerPanel.add(new JLabel("Owner"));
		ownerPanel.add(owner);
		ownerPanel.add(randomOwnerButton);
		// doLayout(new JLabel("Owner"));
		// doLayout(owner, 1);
		doLayout(ownerPanel);
		ownerPanel.setOpaque(false);
		incYPos();
		
		setWeightY(0.25);
		incYPos();
		doLayout(descScroll);
		setWeightY(0.75);
		incYPos();
		
		tabPane.add(invPanel, "Equipment");
		tabPane.add(servicePanel, "Services");
		doLayout(tabPane);
		
		randomNameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateStoreName();
			}
		});

		randomOwnerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateOwnerName();
			}
		});
	}
	
	public void generateStoreName() {
		name.setText(nameGen.getRandomName());
	}

	public void generateOwnerName() {
		owner.setText(ownerGen.getRandomName());
	}
	
	public void setInn(Inn s) {
		setCurrentValues();
		inn = s;
		name.setText(inn.getName());
		owner.setText(inn.getOwner());
		description.setText(inn.getDescription());
		invPanel.setInventory(inn.getInventory());
		servicePanel.setServices(inn.getServices());
		
	}

	private void setCurrentValues() {
		inn.setName(name.getText());
		inn.setOwner(owner.getText());
		inn.setDescription(description.getText());
		inn.setInventory(invPanel.getModel());
		inn.setServices(servicePanel.getModel());
	}

	public Inn getModel() {
		setCurrentValues();
		return inn;
	}
	
	
	
	
	
}

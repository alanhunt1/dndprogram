package initcheck.generator;

import initcheck.InitImage;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledTextArea;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class BuildingInfoPanel extends TiledGridPanel implements FocusListener {

	private static final long serialVersionUID = 1L;

	private Building building = new Building();

	private TiledTextArea description = new TiledTextArea(15, 30);

	private JScrollPane descScroll = new JScrollPane(description);

	private JTextField name = new JTextField(30);
	
	public BuildingInfoPanel() {
		
		super(InitImage.lightRocks);
		description.setWrapStyleWord(true);
		description.setLineWrap(true);
		
		doLayout(new JLabel("Name"));
		doLayout(name, 1);
		name.setText(building.getName());
		incYPos();
		setWeightX(1);
		setWeightY(1);
		doLayout(descScroll, 0, ypos, 2, 1);
		name.addFocusListener(this);
		description.addFocusListener(this);
	}

	public void focusGained(FocusEvent e) {
		
	}

	public void focusLost(FocusEvent e) {
		building.setDescription(description.getText());
		building.setName(name.getText());
	}
	
	public void setBuilding(Building s) {
		building.setDescription(description.getText());
		building.setName(name.getText());
		building = s;
		name.setText(building.getName());
		description.setText(building.getDescription());
	}

	public Building getModel() {
		building.setDescription(description.getText());
		building.setName(name.getText());
		return building;
	}

}

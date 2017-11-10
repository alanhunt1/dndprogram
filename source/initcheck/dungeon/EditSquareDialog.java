package initcheck.dungeon;

import initcheck.MapEditor;
import initcheck.PanelButton;
import initcheck.character.GridPanel;
import initcheck.graphics.TiledDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;



public class EditSquareDialog extends TiledDialog  {

	private static final long serialVersionUID = 1L;
	
	DungeonGUI map = null;
	MapEditor owner = null;
	PanelButton save = new PanelButton("Save");
	
	JLabel renderLabel = new JLabel();
	JLabel position = new JLabel();
	JLabel trapLabel = new JLabel();
	JCheckBox northDoor = new JCheckBox("North");
	JCheckBox southDoor = new JCheckBox("South");
	JCheckBox eastDoor = new JCheckBox("East");
	JCheckBox westDoor = new JCheckBox("West");
	JLabel typeLabel = new JLabel();
	MapSquare square;
	JTextField toolTip = new JTextField("20");
	JTextArea dmNotes = new JTextArea();
	JTextArea playerNotes = new JTextArea();
	MapSquareComponent drawing = new MapSquareComponent();
	
	
	public EditSquareDialog(DungeonGUI map, MapEditor owner) {

		super();

		this.map = map;
		this.owner = owner;
		
		buildComponents();
		
	}
	
	public void setSquare(MapSquare m){
		square = m;
		
		position.setText("Position : "+m.getXpos()+","+m.getYpos()+","+m.getZpos());
		switch(m.getRenderType()){
		case 1:
			renderLabel.setText("Basic");
		case 2:
			renderLabel.setText("Shaded");
		case 3:
			renderLabel.setText("Textured");
		}
		if (m.getTrap() != null){
			trapLabel.setText(m.getTrap().getDescription());
		}
		northDoor.setSelected(m.getNorthDoor() != null);
		southDoor.setSelected(m.getSouthDoor() != null);
		eastDoor.setSelected(m.getEastDoor() != null);
		westDoor.setSelected(m.getWestDoor() != null);
		typeLabel.setText(Dungeon.getTypeDescription(m.getType()));
		toolTip.setText(m.getToolTip());
		dmNotes.setText(m.getDmNotes());
		playerNotes.setText(m.getNotes());
		drawing.setSquare(m);
		drawing.updateImage();
		
	}
	
	public void buildComponents(){
		
		drawing = new MapSquareComponent(((MapEditorDungeonGUI)map).getDrawingBoard().getDungeon());
		GridPanel p = new GridPanel();
		p.doLayout(position,0);
		p.incYPos();
		p.doLayout(typeLabel, 0);
		p.incYPos();
		p.doLayout(renderLabel, 0);
		p.incYPos();
		p.doLayout(trapLabel,0);
		p.incYPos();
		JPanel dpanel = new JPanel();
		dpanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		dpanel.add(drawing);
		p.setWeightX(1.0);
		p.setWeightY(1.0);
		p.doLayout(dpanel,0);
		p.setWeightX(0);
		p.setWeightY(0);
		JPanel doorPanel = new JPanel();
		doorPanel.add(northDoor);
		doorPanel.add(southDoor);
		doorPanel.add(eastDoor);
		doorPanel.add(westDoor);
		p.incYPos();
		p.doLayout(toolTip, 0);
		p.incYPos();
		p.doLayout(dmNotes, 0);
		p.incYPos();
		p.doLayout(playerNotes, 0);
		p.incYPos();
		p.doLayout(doorPanel, 0);
		p.incYPos();
		
		p.doLayout(save);
		add(p);
		
	
		
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveSquare();
			}
		});
	}
	
	public void saveSquare(){
		MapSquare nb[] = new MapSquare[8];
		int x = square.xpos;
		int y = square.ypos;
		int level = square.zpos;
		Dungeon m = map.getMap();
		
		m.getNeighbors(x, y, level, nb);
		if ((nb[Dungeon.NORTH].isRoom()) && 
				nb[Dungeon.NORTH].getRoomNumber() != m.squares[x][y][level].getRoomNumber()) {
			Room room = m.getRoom(x, y-1, level);
			if (northDoor.isSelected()){
			
			room.addDoor(x,y);
			}else{
				room.removeDoor(x, y);
			}
		}
		if ((nb[Dungeon.SOUTH].isRoom()) && 
				nb[Dungeon.SOUTH].getRoomNumber() != m.squares[x][y][level].getRoomNumber()) {
			Room room = m.getRoom(x, y+1, level);
			if (southDoor.isSelected()){
			
			room.addDoor(x,y);
			}else{
				room.removeDoor(x, y);
			}
		}
		if ((nb[Dungeon.EAST].isRoom())&& 
				nb[Dungeon.EAST].getRoomNumber() != m.squares[x][y][level].getRoomNumber()) {
			Room room = m.getRoom(x+1, y, level);
			if (eastDoor.isSelected()){
			
			room.addDoor(x,y);
			}else{
				room.removeDoor(x, y);
			}
		}
		if ((nb[Dungeon.WEST].isRoom())&& 
				nb[Dungeon.WEST].getRoomNumber() != m.squares[x][y][level].getRoomNumber()) {
			Room room = m.getRoom(x-1, y, level);
			if (westDoor.isSelected()){
			
			room.addDoor(x,y);
			}else{
				room.removeDoor(x, y);
			}
		}
		square.setNorthDoor(northDoor.isSelected());
		square.setSouthDoor(southDoor.isSelected());
		square.setEastDoor(eastDoor.isSelected());
		square.setWestDoor(westDoor.isSelected());
		square.setToolTip(toolTip.getText());
		square.setDmNotes(dmNotes.getText());
		square.setNotes(playerNotes.getText());
		((MapEditorDungeonGUI)map).setSquare(square);
		setVisible(false);
	}
}

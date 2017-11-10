package initcheck.dungeon;

import initcheck.graphics.Skin;
import initcheck.server.CampaignNotesPanel;
import initcheck.server.CampaignNotesTab;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MapImage extends JPanel implements DrawingBoardContainer, Serializable, CampaignNotesTab {


	private static final long serialVersionUID = 1L;

	public String fileName = null;
	
	public ImageIcon icon = null;
	
	public JLabel iconLabel = new JLabel();
	
	public JScrollPane iconScroll; 
	
	public Dungeon d;
	
	private CampaignNotesPanel owner;
	
	private Skin skin = null;
	
	public Skin getSkin() {
		return skin;
	}


	public MapImage(String filename, CampaignNotesPanel owner){
		this.fileName = filename;
		this.owner = owner;
		DrawingBoard db = new DrawingBoard(this);
		setLayout(new BorderLayout());
		iconScroll = new JScrollPane(db);
		add(iconScroll, BorderLayout.CENTER);
		init();
	}
	
	
	public MapImage(CampaignNotesPanel owner){
	
		this.owner = owner;
		DrawingBoard db = new DrawingBoard(this);
		setLayout(new BorderLayout());
		iconScroll = new JScrollPane(db);
		add(iconScroll, BorderLayout.CENTER);
	}
	
	public void init(){
		d = Dungeon.load(fileName);
		DrawingBoard db = new DrawingBoard(this);
		db.setDungeon(d);
		db.setMode(DrawingBoard.VIEWER);
		iconScroll.setViewportView(db);
		icon = db.getIcon();
	}
	
	public void setMapAsCurrent(){
		owner.setMapAsCurrent(fileName);
	}
	
	public int getType(){
		return CampaignNotesPanel.MAP_TAB;
	}

	public Rectangle getDrawingSize() {
		return new Rectangle(0,0,getSize().width,getSize().height);
	}

	public Point getViewPosition() {
		
		return new Point(0,0);
	}

	public boolean isMapShowing() {	
		return true;
	}

	public void showRoomInfo(int i){
		
	}
	
	public void showSquare(MapSquare i){
		
	}
	
	public JFrame getFrame() {		
		return null;
	}

	public void notifyMapRepaint() {
			
	}
	
	public void refreshPositionLabel(int x, int y, int z) {
		// TODO Auto-generated method stub
		
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public Object getModel(){
		return new MapFileLink(this);
	}
	
	public void setModel(Object o){
		MapFileLink mfl = (MapFileLink) o;
		this.fileName = mfl.getFileName();
		init();
	}
	
	public void setSkin(Skin s){
		this.skin = s;
	}
	
}

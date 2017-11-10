package initcheck;

import initcheck.dungeon.AddRoomDialog;
import initcheck.dungeon.DrawLineDialog;
import initcheck.dungeon.Dungeon;
import initcheck.dungeon.EditRoomDialog;
import initcheck.dungeon.EditSquareDialog;
import initcheck.dungeon.GenerateDungeonPanel;
import initcheck.dungeon.MapEditorDungeonGUI;
import initcheck.dungeon.MapSquare;
import initcheck.dungeon.NewDungeonPanel;
import initcheck.dungeon.PaletteConfigurationPanel;
import initcheck.dungeon.RoomDescriptionPanel;
import initcheck.dungeon.Square;
import initcheck.dungeon.ThumbnailDisplayPanel;
import initcheck.dungeon.ThumbnailPanel;
import initcheck.graphics.TiledDialog;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.log4j.PropertyConfigurator;

import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.SkyRed;

public class MapEditor implements InitProgram, MonsterDialogParent {

	static JFrame frame = new JFrame();

	JPanel main = new JPanel();

	private ImageIcon backgroundImage = InitImage.steel;

	MapEditorDungeonGUI map = null;

	private JMenuBar menuBar;

	TiledDialog gdd = new TiledDialog(frame, "Generate New Dungeon", true);

	TiledDialog ndd = new TiledDialog(frame, "New Dungeon", true);

	private JTabbedPane tabbedPane = new JTabbedPane();

	private InitLogger logger = new InitLogger(this);

	private ThumbnailDisplayPanel tdp = new ThumbnailDisplayPanel(this);
	
	private EditRoomDialog erd = null;
	
	private EditSquareDialog esd = null;
	
	public MapEditorDungeonGUI getMap(){
		return map;
	}
	
	public void setCursor(Cursor c) {
		main.setCursor(c);
	}

	public void setFont(Font f) {

	}

	public void runEncounter(Encounter e, boolean b) {

	}

	public void saveEncounter(Encounter e){
		
	}
	
	public void sendMessage(String s) {

	}

	public JFrame getFrame() {
		return frame;
	}

	public String getParty() {
		return "INVALID";
	}

	public void setParty(String s) {

	}

	public void setMap(Dungeon d) {

	}

	public void updateMap(int x, int y, int z) {

	}

	public void sendPlayerNotes(int x, int y, int z, String s) {

	}

	public void removeAllMonsters() {

	}

	public boolean isMapShowing() {
		return true;
	}

	public void sendChatMessage(String s) {

	}

	public String getId() {
		return "MAP EDITOR";
	}

	public void loadDungeon() {
		map.loadDungeon();
	}

	public void saveDungeon() {
		logger.log("SAVING DUNGEON");
		map.saveDungeon("BLAH");
	}
	
	public void saveDungeonAs() {
		map.saveDungeon("");
	}

	public void importMap() {
		map.importMap();
	}

	public void exportMap() {
		map.exportMap();
	}

	public void printMap(){
		map.printMap();
	}
	
	public void drawLine() {
		Square s = map.getDrawingBoard().getCurrSquare();
		DrawLineDialog ard = new DrawLineDialog(map);
		ard.setX("" + s.getX());
		ard.setY("" + s.getY());
		ard.pack();
		ard.setVisible(true);
	}

	public void addRoom() {
		Square s = map.getDrawingBoard().getCurrSquare();
		AddRoomDialog ard = new AddRoomDialog(map);
		ard.setX("" + s.getX());
		ard.setY("" + s.getY());

		ard.pack();
		ard.setVisible(true);
	}

	public void editRooms(int i){
		if (erd == null){
			erd = new EditRoomDialog(map, this);
		}
		erd.setRoom(i);
		erd.pack();
		erd.setVisible(true);
	}
	
	public void editSquare(MapSquare i){
		if (esd == null){
			esd = new EditSquareDialog(map, this);
		}
		esd.setSquare(i);
		esd.pack();
		esd.setVisible(true);
		//bring up the square edit box just under where they click the mouse.
		esd.setLocation(map.getDrawingBoard().getLocationOnScreen().x+map.getDrawingBoard().mouseX, 
				map.getDrawingBoard().getLocationOnScreen().y+map.getDrawingBoard().mouseY);
	}
	
	
	public void editRooms(){
		if (erd == null){
			erd = new EditRoomDialog(map, this);
		}
		erd.pack();
		erd.setVisible(true);
	}
	
	public void newDungeon() {
		frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		ndd.setMainWindow(new NewDungeonPanel(map, this, backgroundImage));
		ndd.pack();
		ndd.setVisible(true);
	}

	public void addLevel() {
		map.addLevel();
	}

	public void addThumbnail(ThumbnailPanel tp){
		tdp.addThumbnail(tp);
	}
	
	public void removeThumbnail(int idx){
		tdp.removeThumbnail(idx);
	}
	
	public void removeLevel() {
		String level = JOptionPane
				.showInputDialog("Which level do you wish to remove?");
		try {
			map.removeLevel(Integer.parseInt(level));
		} catch (NumberFormatException nfe) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error",
					"The level must be a valid integer");
		}
	}
	
	public void randomizeLevel(){
		frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		map.randomizeLevel();
		frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
	
	public void generateDungeon() {
		frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		gdd.setMainWindow(new GenerateDungeonPanel(map, this, backgroundImage));
		gdd.pack();
		gdd.setVisible(true);
	}

	public void packDungeonDialog() {
		gdd.pack();
	}

	public void hideDungeonDialog() {
		ndd.setVisible(false);
		gdd.setVisible(false);
		frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void importDungeon() {
		map.importDungeon();
	}

	public void undo() {
		map.undo();
	}

	public JComponent createComponents() {
		logger.log("CREATING COMPONENTS");
		menuBar = new MapEditorMenuBar(this);
		frame.setJMenuBar(menuBar);

		main.setLayout(new BorderLayout());
		map = new MapEditorDungeonGUI(this, backgroundImage, MapEditorDungeonGUI.GENERATE);
		map.setMode(MapEditorDungeonGUI.GENERATE);

		main.add(map, BorderLayout.CENTER);
		map.init();

		tabbedPane.add(main, "Maps");
		RoomDescriptionPanel rdp = new RoomDescriptionPanel();
		tabbedPane.add(rdp, "Descriptions");
		PaletteConfigurationPanel pcp = new PaletteConfigurationPanel();
		tabbedPane.add(pcp, "Palettes");
		tabbedPane.add(tdp, "Thumbnails");
		return tabbedPane;
	}
	
	public void closeMap(){
		map.closeMap();
	}

	public void showMapTab(){
		tabbedPane.setSelectedIndex(0);
	}
	
	public void setTitle (String s){
		frame.setTitle("Map Editor - "+s);
	}
	
	public void playSound(String s){
		
	}
	
	public MapEditor(){
		PropertyConfigurator.configure("defaultLog4J.cfg");
	}
	
	public static void main(String[] args) throws IOException {
		PlasticXPLookAndFeel.setCurrentTheme(new SkyRed());
		// MetalLookAndFeel.setCurrentTheme(new InitTheme());
		try {
			//UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
			 for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			        if ("Nimbus".equals(info.getName())) {
			        	System.out.println("FOUND NIMBUS");
			            UIManager.setLookAndFeel(info.getClassName());
			            break;
			        }
			    }
			 UIManager.put("nimbusBase", InitColor.red);
			 UIManager.put("nimbusOrange", InitColor.red);
			 UIManager.put("nimbusSelection", InitColor.red);
			 UIManager.put("nimbusSelectionBackground", InitColor.red);
				UIManager.put("nimbusBlueGrey", InitColor.fadedRed);
				UIManager.put("control", InitColor.fadedRed);
				UIManager.put("TabbedPane.background", InitColor.red);
				//UIManager.put("nimbusLightBackground",InitColor.fadedRed);
				UIManager.put("ComboBox.background", InitColor.fadedRed);
		} catch (Exception e) {
			e.printStackTrace();
		}

		UIDefaults defaults = UIManager.getDefaults();

		defaults.put("ScrollBar.thumb", InitColor.red);
		defaults.put("ScrollBar.thumbHighlight", InitColor.hlight);
		defaults.put("ScrollBar.thumbLightShadow", InitColor.lhlight);
		defaults.put("ScrollBar.thumbShadow", InitColor.shadow);
		defaults.put("Button.pressed", InitColor.lightRed);
		defaults.put("Button.focus", InitColor.lightRed);
		defaults.put("ComboBox.selectedBackground", InitColor.red);

		

		MapEditor app = new MapEditor();

		// Create the top-level container and add contents to it.
		frame = new JFrame("Map Editor");
		JComponent contents = app.createComponents();
		frame.getContentPane().add(contents, BorderLayout.CENTER);

		// Finish setting up the frame, and show it.
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.pack();
		frame.setVisible(true);
	}

	public void updateTime(int minutes) {
		// TODO Auto-generated method stub
		
	}

	public void updateTotalTime(int minutes) {
		// TODO Auto-generated method stub
		
	}

}

package initcheck.dungeon;

import initcheck.MonsterGroup;

import java.util.Vector;

import javax.swing.JFrame;

public interface DungeonGUI {

	public void addRoom(int x, int y, int width, int height);
	public Room getRoom(int i);
	public Vector<Room> getRooms();
	public void centerView();
	public boolean offMap();
	public int getDrawMode();
	public void drawLine(int x1, int y1, int x2, int y2);
	public boolean isLocked();
	public void setView(int x, int y);
	public void setMoveMode(int mode);
	public void sendMap();
	public void runEncounter(Vector<MonsterGroup>v);
	public void showListTab();
	public void showMonsterDialog(Room r);
	public void generateDescription(Room r);
	public int getMode();
	public void toggleMiniMap();
	public JFrame getFrame();
	public Dungeon getMap();
	public void followMapLink(String link);
}
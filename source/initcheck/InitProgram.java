package initcheck;

import initcheck.dungeon.Dungeon;
import initcheck.dungeon.ThumbnailPanel;

import java.awt.Font;

import javax.swing.JFrame;

public interface InitProgram extends GroupMessageClient{

	public void sendMessage(String s);

	public JFrame getFrame();

	public String getParty();

	public void setParty(String s);

	public void setMap(Dungeon d);

	public void updateMap(int x, int y, int z);

	public void updateTime(int minutes);
	
	public void updateTotalTime(int minutes);
	
	public void sendPlayerNotes(int x, int y, int z, String s);

	public void removeAllMonsters();

	public boolean isMapShowing();

	public void sendChatMessage(String s);

	public String getId();
	
	public void setFont(Font f);
	
	public void setTitle(String s);
	
	public void playSound(String s);
	
	public void addThumbnail(ThumbnailPanel tp);
}

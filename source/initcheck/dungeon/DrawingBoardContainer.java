package initcheck.dungeon;


import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JFrame;

public interface DrawingBoardContainer{
		
		public Rectangle getDrawingSize();
		public Point getViewPosition();
		public boolean isMapShowing();
		public JFrame getFrame();
		public void notifyMapRepaint();
		public void refreshPositionLabel(int x, int y, int z);
		public void showRoomInfo(int i);
		public void showSquare(MapSquare s);
}

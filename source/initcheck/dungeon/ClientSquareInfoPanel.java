package initcheck.dungeon;

import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ClientSquareInfoPanel extends TiledPanel{

	private static final long serialVersionUID = 1L;
		DungeonGUI owner = null;
		MapSquare square;
	
		JLabel type = new JLabel("");
		JLabel room = new JLabel("");
		
		Room r;
		JPanel infoPanel = new JPanel();

		// gridbag layout components
		private GridBagLayout gridbag = new GridBagLayout();
		private GridBagConstraints c = new GridBagConstraints();

		public ClientSquareInfoPanel(DungeonGUI owner, ImageIcon bg) {

				super(bg);
				this.owner = owner;
			
				
				setLayout(new BorderLayout());
				// set the layout
				infoPanel.setLayout(gridbag);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.ipadx = 10;
				c.ipady = 10;
				
				init();
				add(infoPanel, BorderLayout.CENTER);
		}
		
		public void setFont(Font f){
				if (type != null){
						type.setFont(f);
						room.setFont(f);
				}
		}

		public void setMapSquare(MapSquare square){
				
				this.square = square;
				showSquareInfo();
		}
		
		public void showSquareInfo(){
				
				
				
				int roomNumber = square.getRoomNumber();
				if (roomNumber >= 0){
						type.setText("");
						r = owner.getRoom(roomNumber+1);
						room.setText("Room : "+(roomNumber+1)+" ("+(r.getWidth()*10)+" X "+(r.getHeight()*10)+")");
					
				}else{
						type.setText(Dungeon.getTypeDescription(square.getType()));
						room.setText("");
				}
		}
		
		private void doLayout(Component comp, int x, int y){
				c.gridx = x;
				c.gridy = y;
				c.gridwidth = 1;
				c.gridheight = 1;
				gridbag.setConstraints(comp, c);
				infoPanel.add(comp);
		}

		@SuppressWarnings("unused")
		private void doLayout(Component comp, int x, int y, int width, int height){
				c.gridx = x;
				c.gridy = y;
				c.gridwidth = width;
				c.gridheight = height;
				gridbag.setConstraints(comp, c);
				infoPanel.add(comp);
		}
		
		public void init(){
				int ypos = 1;
				
				doLayout(type, 1, ypos);
				doLayout(room, 2, ypos);
				ypos++;
				
		}
}

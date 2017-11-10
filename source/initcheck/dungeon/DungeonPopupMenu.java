package initcheck.dungeon;

import initcheck.InitLogger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class DungeonPopupMenu extends JPopupMenu implements ActionListener {

	
	private static final long serialVersionUID = 1L;

	// the popup menu
	private JMenuItem roomNotes;

	private JMenuItem revealDoor;

	private JMenuItem dmNotes;

	private JMenuItem playerNotes;

	private JMenuItem removeRoom;

	private JMenuItem resizeRoom;

	private JMenuItem moveRoom;

	private JMenuItem loadMap;

	private JMenuItem mapLink;
	
	private DrawingBoard owner;

	@SuppressWarnings("unused")
	private InitLogger logger = new InitLogger(this);

	public DungeonPopupMenu(DrawingBoard owner, int mode) {

		super();

		this.owner = owner;
		if (mode == DrawingBoard.SERVER || mode == DrawingBoard.VIEWER || mode == DrawingBoard.EDITOR) {

			if (owner.clickedOn(Dungeon.room)) {
				roomNotes = new JMenuItem("Show Room Notes");
				roomNotes.addActionListener(this);
				add(roomNotes);

				if (mode == DrawingBoard.SERVER || mode == DrawingBoard.EDITOR) {
					removeRoom = new JMenuItem("Remove Room");
					removeRoom.addActionListener(this);
					add(removeRoom);

					resizeRoom = new JMenuItem("Re-Size Room");
					resizeRoom.addActionListener(this);
					add(resizeRoom);

					moveRoom = new JMenuItem("Move Room");
					moveRoom.addActionListener(this);
					add(moveRoom);
				}

			}

			if (mode == DrawingBoard.SERVER) {
				if (owner.clickedOn(Dungeon.secretdoor)) {
					revealDoor = new JMenuItem("Reveal Door");
					revealDoor.addActionListener(this);
					add(revealDoor);
				}
			}
			
			if (mode == DrawingBoard.VIEWER){
				loadMap = new JMenuItem("Set Map As Current");
				loadMap.addActionListener(this);
				add(loadMap);
			}
			
			dmNotes = new JMenuItem("Show DM Notes");
			dmNotes.addActionListener(this);
			add(dmNotes);
			
			mapLink = new JMenuItem("Edit Map Link");
			mapLink.addActionListener(this);
			add(mapLink);
		}

		playerNotes = new JMenuItem("Show Player Notes");
		playerNotes.addActionListener(this);
		add(playerNotes);

	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == roomNotes) {
			owner.showRoomNotes();
		} else if (e.getSource() == dmNotes) {
			owner.showDmNotes();
		} else if (e.getSource() == playerNotes) {
			owner.showPlayerNotes();
		} else if (e.getSource() == removeRoom) {
			owner.removeRoom();
		} else if (e.getSource() == revealDoor) {
			owner.revealDoor();
		} else if (e.getSource() == resizeRoom) {
			owner.resizeRoom();
		} else if (e.getSource() == moveRoom) {
			owner.moveRoom();
		} else if (e.getSource() == loadMap){
			owner.setMapAsCurrent();
		} else if (e.getSource() == mapLink){
			owner.setMapLink();
		}

	}
}

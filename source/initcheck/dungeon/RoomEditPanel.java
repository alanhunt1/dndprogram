package initcheck.dungeon;

/*
 * RoomEditPanel
 *
 * GUI for editing the Rooms list
 */

import initcheck.PanelButton;
import initcheck.graphics.TiledDialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class RoomEditPanel extends TiledDialog {
	
	private static final long serialVersionUID = 1L;

	Dungeon m;

	JPanel titlePanel = new JPanel();

	JPanel textPanel = new JPanel();

	JLabel roomNumber = new JLabel("1  ");

	JTextField roomTitle = new JTextField("                              ");

	JTextArea roomText = new JTextArea(10, 50);

	PanelButton prevButton = new PanelButton("<<");

	PanelButton nextButton = new PanelButton(">>");

	int showing = 1;

	Room current;

	JPanel main = new JPanel();

	public RoomEditPanel(DrawingBoard owner, Dungeon mz) {
		super(owner.getFrame(), "Room Editor", false);
		m = mz;

		setTitle("Edit Rooms");

		roomText.setLineWrap(true);
		roomText.setWrapStyleWord(true);

		main.setLayout(new BorderLayout());
		titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		textPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		main.add(titlePanel, BorderLayout.NORTH);
		main.add(textPanel, BorderLayout.CENTER);
		titlePanel.add(roomNumber);
		titlePanel.add(roomTitle);
		titlePanel.add(prevButton);
		titlePanel.add(nextButton);
		textPanel.add(roomText);
		titlePanel.setOpaque(false);
		textPanel.setOpaque(false);

		updatePanel();
		// setButtonBar();
		setMainWindow(main);
		pack();

		prevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateDungeon();
				showing = showing - 1;
				if (showing < 1)
					showing = m.rooms.size();
				updatePanel();
			}
		});

		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateDungeon();
				showing = showing + 1;
				if (showing > m.rooms.size())
					showing = 1;
				updatePanel();
			}
		});

		// Window events
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
	}

	public void updatePanel() {
		current = (Room) m.rooms.get(showing - 1);
		roomNumber.setText(Integer.toString(showing));
		if (current.title != null)
			roomTitle.setText(current.title);
		else
			roomTitle.setText("");
		if (current.text != null)
			roomText.setText(current.text);
		else
			roomText.setText("");
	}

	public void updateDungeon() {
		String s = roomTitle.getText();
		if (s.length() > 0)
			current.title = s;
		else
			current.title = null;
		s = roomText.getText();
		if (s.length() > 0)
			current.text = s;
		else
			current.text = null;
	}

	public void setRoom(int i) {
		showing = i;
		updatePanel();
	}

	public void close() {
		updateDungeon();
		dispose();
	}
}

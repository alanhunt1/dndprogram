package initcheck.dungeon;

import initcheck.MapEditor;
import initcheck.PanelButton;
import initcheck.character.GridPanel;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewDungeonPanel extends GridPanel {

	private static final long serialVersionUID = 1L;

	// Default parameter values
	int levels = 1;

	int cols = 50;

	int rows = 50;

	JTextField widthField = new JTextField("50");

	JTextField heightField = new JTextField("50");

	JTextField depthField = new JTextField("1");

	PanelButton generateButton = new PanelButton("Create Map", 80);

	MapTypeChooser type = new MapTypeChooser();

	MapEditorDungeonGUI map = null;

	MapEditor owner = null;

	public NewDungeonPanel(MapEditorDungeonGUI map, MapEditor owner, ImageIcon bg) {

		super();
		this.owner = owner;
		this.map = map;

		buildComponents();
		init();
	}

	public void buildComponents() {

		int ypos = 0;

		doLayout(new JLabel("Width:"), 0, ypos);
		doLayout(widthField, 1, ypos);
		ypos++;

		doLayout(new JLabel("Height:"), 0, ypos);
		doLayout(heightField, 1, ypos);
		ypos++;

		doLayout(new JLabel("Depth:"), 0, ypos);
		doLayout(depthField, 1, ypos);
		ypos++;

		doLayout(new JLabel("Type"), 0, ypos);
		doLayout(type, 1, ypos);
		ypos++;

		JPanel generatePanel = new JPanel();
		generatePanel.add(generateButton);
		doLayout(generatePanel, 0, ypos, 3, 1);
		ypos++;

	}

	public void init() {
		generateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateMap();
			}
		});
	}

	public void generateMap() {
		boolean ok = true;
		try {
			rows = Integer.parseInt(heightField.getText());
		} catch (Exception err) {
			heightField.setText("-ERR-");
			heightField.selectAll();
			heightField.requestFocus();
			ok = false;
		}
		try {
			cols = Integer.parseInt(widthField.getText());
		} catch (Exception err) {
			widthField.setText("-ERR-");
			widthField.selectAll();
			widthField.requestFocus();
			ok = false;
		}
		try {
			levels = Integer.parseInt(depthField.getText());
		} catch (Exception err) {
			depthField.setText("-ERR-");
			depthField.selectAll();
			depthField.requestFocus();
			ok = false;
		}

		String typeStr = (String) type.getSelectedItem();

		if (ok) {
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			map.newDungeon(cols, rows, levels, typeStr);
			owner.hideDungeonDialog();
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

}

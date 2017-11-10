package initcheck.dungeon;

import initcheck.MapEditor;
import initcheck.PanelButton;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class ThumbnailDisplayPanel extends TiledPanel {

	private static final long serialVersionUID = 1L;

	private int colIndex = 0;

	private int rowIndex = 0;

	private int numCols = 4;

	private JTextField columns = new JTextField(4);

	private PanelButton resize = new PanelButton("Set Columns", 120);
	
	private TiledGridPanel thumbPanel = new TiledGridPanel();

	private Vector<ThumbnailPanel> thumbs = new Vector<ThumbnailPanel>();

	private MapEditor owner;
	
	public ThumbnailDisplayPanel(MapEditor owner) {
		
		this.owner = owner;
		columns.setText("4");
		
		JPanel configPanel = new JPanel();
		configPanel.add(columns);
		configPanel.add(resize);
		setLayout(new BorderLayout());
		add(thumbPanel, BorderLayout.CENTER);
		add(configPanel, BorderLayout.NORTH);
		thumbPanel.setPadX(1);
		thumbPanel.setPadY(1);
		
		resize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layoutThumbs();
			}
		});
	}

	public void layoutThumbs() {

		thumbPanel.removeAll();
		rowIndex = 0;
		colIndex = 0;
		try {
			numCols = Integer.parseInt(columns.getText());
		}catch(NumberFormatException nfe){
			
		}
		for (int i = 0; i < thumbs.size(); i++) {
			ThumbnailPanel tp = thumbs.get(i);
			thumbPanel.doLayout(tp, colIndex, rowIndex);
			colIndex++;
			if (colIndex > numCols - 1) {
				rowIndex++;
				colIndex = 0;
			}
		}
	}

	public void removeThumbnail(int idx){
		if (idx > -1 && idx < thumbs.size()){
			thumbs.removeElementAt(idx);
		}
		layoutThumbs();
	}
	
	public void addThumbnail(ThumbnailPanel tp) {
		thumbs.add(tp);
		thumbPanel.doLayout(tp, colIndex, rowIndex);
		tp.setEditor(owner);
		colIndex++;
		if (colIndex > numCols - 1) {
			rowIndex++;
			colIndex = 0;
		}
		
	}

}

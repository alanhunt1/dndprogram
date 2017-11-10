package initcheck.dungeon;

import initcheck.PanelButton;
import initcheck.database.Trap;
import initcheck.database.TrapDAO;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledList;
import initcheck.graphics.TiledTextArea;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TrapDialog extends JDialog implements ListSelectionListener {

	private static final long serialVersionUID = 1L;

	private TiledList trapList = new TiledList();

	private Vector<Trap> trapVector = new Vector<Trap>();

	PanelButton searchButton = new PanelButton("Search");

	PanelButton setButton = new PanelButton("Set Trap");

	private TiledGridPanel contents = new TiledGridPanel();

	private JScrollPane trapScroll = new JScrollPane(trapList);

	private TiledTextArea desc = new TiledTextArea();
	
	private JScrollPane descScroll = new JScrollPane(desc);
	
	private MapSquare square;

	public TrapDialog(JFrame frame, MapSquare m) {
		super(frame, "Traps are Fun!", true);
		
		square = m;
		doInit();
	}

	public void doInit() {
		loadTraps();
		if (trapList.getModel().getSize() > 0){
			trapList.setSelectedIndex(0);
		}
		
		int ypos = 0;

		// trapList.setCellRenderer(new BrowseMonsterCellRenderer());
		trapList.setFont(new Font("Courier", Font.PLAIN, 12));
		trapList.setCellRenderer(new TrapCellRenderer());
		trapList.addListSelectionListener(this);
		
		desc.setLineWrap(true);
		desc.setWrapStyleWord(true);
		
		contents.setWeightX(0.5);
		contents.setWeightY(1);
		contents.doLayout(trapScroll, 0, ypos);
		contents.doLayout(descScroll, 1, ypos);
		contents.setWeightX(0);
		contents.setWeightY(0);
		ypos++;

		contents.doLayout(searchButton, 0, ypos);
		contents.doLayout(setButton, 1, ypos);
		ypos++;

		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadTraps();
			}
		});

		setButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTraps();
			}
		});

		getContentPane().add(contents);
		pack();
		setVisible(true);
	}

	public void loadTraps() {
		TrapDAO db = new TrapDAO();
		trapVector = db.getTraps();
		trapList.setListData(trapVector);
	}

	public void setTraps() {
		int idx = trapList.getSelectedIndex();
		
		Trap t = (Trap) trapList.getSelectedValue();
		square.setTrap(t);
		dispose();
	}

	public void valueChanged(ListSelectionEvent e) {

		try {
			Trap f = (Trap) trapList.getSelectedValue();
			if (f != null) {
				desc.setText(f.getDescription());
				
			}
		} catch (Exception ex) {

			ex.printStackTrace();
		}
	}
	

}

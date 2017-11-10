package initcheck.character.library;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import initcheck.InitLogger;
import initcheck.PanelButton;
import initcheck.database.Campaign;
import initcheck.database.Exportable;
import initcheck.graphics.Skin;
import initcheck.graphics.Skinnable;
import initcheck.graphics.TiledList;
import initcheck.graphics.TiledListItem;
import initcheck.graphics.TiledPanel;
import initcheck.graphics.TiledTextArea;

public class LibraryPanel extends TiledPanel implements ListSelectionListener,Skinnable {
	
	private static final long serialVersionUID = 1L;

	private InitLogger logger = new InitLogger(this);

	private TiledList<?> libraryList = new TiledList<>();

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints c = new GridBagConstraints();

	private TiledTextArea description = new TiledTextArea(10, 50);

	private JScrollPane descScroll = new JScrollPane(description);

	private JPanel main = new JPanel();

	private JScrollPane listScroll = new JScrollPane(libraryList);

	private PanelButton modButton = new PanelButton("Modify");

	private PanelButton addButton = new PanelButton("Add");
	
	private PanelButton copyButton = new PanelButton("Copy");
	
	private PanelButton delButton = new PanelButton("Delete");

	private JPanel buttonPanel = new JPanel();

	private JTextField searchText = new JTextField(20);

	private PanelButton searchButton = new PanelButton("Search");

	private LibraryPopupMenu rClickMenu = new LibraryPopupMenu(this);

	private LibraryData item;

	private JCheckBox filter = new JCheckBox("Filter by Campaign");
	
	private static Campaign campaign = new Campaign();
	
	

	public LibraryPanel() {
	}

	public LibraryPanel(LibraryData item) {

		this.item = item;

		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		libraryList.setVisibleRowCount(10);
		libraryList.setListData(item.getListing());
		libraryList.addListSelectionListener(this);
		libraryList.setCellRenderer(item.getCellRenderer());
		libraryList.setSelectedIndex(0);
		filter.setForeground(Color.white);
		setBorder(BorderFactory.createEmptyBorder(30, // top
				30, // left
				30, // bottom
				30) // right
		);

		main.setLayout(gridbag);
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 10;
		c.ipady = 10;

		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				listScroll, descScroll);
		split.setDividerLocation(0.5);
		split.setResizeWeight(0.5);
		

		int ypos = 0;
		filter.setOpaque(false);
		filter.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				toggleFilter();
			}
		});
		JPanel searchPanel = new JPanel();
		
		searchPanel.add(searchText);
		searchPanel.add(searchButton);
		searchPanel.add(filter);
		searchPanel.setOpaque(false);
		main.setOpaque(false);
		doLayout(searchPanel, 0, ypos);
		ypos++;

		c.weightx = 1.0;
		c.weighty = 1.0;
		doLayout(split, 0, ypos);
		ypos++;

		setLayout(new BorderLayout());
		add(main, BorderLayout.CENTER);
		buttonPanel.add(addButton);
		buttonPanel.add(modButton);
		buttonPanel.add(copyButton);
		buttonPanel.add(delButton);
		buttonPanel.setOpaque(false);
		add(buttonPanel, BorderLayout.SOUTH);

		modButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modListing();
			}
		});

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addListing();
			}
		});

		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteListing();
			}
		});
		
		copyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copyListing();
			}
		});


		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchListing();
			}
		});

		libraryList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					modListing();
				}
			}
		});

		libraryList.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					modListing();
				}
			}

			public void keyTyped(KeyEvent e) {

			}

			public void keyReleased(KeyEvent e) {

			}
		});

		libraryList.addMouseListener(new MouseAdapter() {

			// check all the clicks for a right click, and trigger the
			// popup menu if you see one.
			public void mousePressed(MouseEvent e) {
				maybeShowPopup(e);
			}

			public void mouseReleased(MouseEvent e) {
				maybeShowPopup(e);
			}

			private void maybeShowPopup(MouseEvent e) {
				if (e.isPopupTrigger()) {
					getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

	}
	
	public void toggleFilter(){
	
		updateList(filter.isSelected());
	}

	public void setSkin(Skin s){
	
		description.setPaintBackground(s.isTileTextAreas());
		libraryList.setPaintBackground(s.isTileLists());
		this.setPaintBackground(s.isTilePanels());
		this.setBgImage(s.getBackgroundPanelIcon());
	}
	
	public void exportAll() {

		Object charVector[] = item.getListing().toArray();

		JFileChooser fc = new JFileChooser("export");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = fc.showSaveDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {

				String filename = fc.getSelectedFile().getAbsolutePath();
				FileOutputStream fos = new FileOutputStream(filename);
				PrintWriter writer = new PrintWriter(fos);

				for (int i = 0; i < charVector.length; i++) {
					Exportable d = (Exportable) charVector[i];
					writer.write(d.exportFormat());
				}
				writer.close();
			} catch (Exception e) {
				logger.log("error", "Error in Export All : " + e.toString());
			}
		}

	}

	public void exportSelected() {

		List<TiledListItem> items = libraryList.getSelectedValuesList();

		JFileChooser fc = new JFileChooser("export");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = fc.showSaveDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {

				String filename = fc.getSelectedFile().getAbsolutePath();
				FileOutputStream fos = new FileOutputStream(filename);
				PrintWriter writer = new PrintWriter(fos);

				for (TiledListItem i:items) {
					Exportable d = (Exportable) i;
					writer.write(d.exportFormat());
				}
				writer.close();
			} catch (Exception e) {
				logger.log("error", "Error in Export All : " + e.toString());
			}
		}

	}
	
	public JList<TiledListItem> getList() {
		return libraryList;
	}

	public void searchListing() {
		if (!filter.isSelected()){
			libraryList.setListData(item.getSearchListing(searchText.getText()));
		}else{
			libraryList.setListData(item.getSearchListing(searchText.getText(), campaign));
		}
		if (libraryList.getModel().getSize() > 0) {
			libraryList.setSelectedIndex(0);
		}
	}

	public void modListing() {
		Object o = libraryList.getSelectedValue();
		item.updateListing(o, this);
	}

	public void addListing() {
		item.addListing(this);
	}

	public void deleteListing() {
		int idx = libraryList.getSelectedIndex();
		Object o = libraryList.getSelectedValue();
		item.deleteListing(o);
		libraryList.setListData(item.getListing());
		if (idx > 0 && idx < libraryList.getModel().getSize()) {
			libraryList.setSelectedIndex(idx);
		}else if (idx-1 > 0 && idx-1 < libraryList.getModel().getSize()){
			libraryList.setSelectedIndex(idx-1);
		}
	}
	
	public void copyListing() {
		int idx = libraryList.getSelectedIndex();
		Object o = libraryList.getSelectedValue();
		item.copyListing(o);
		libraryList.setListData(item.getListing());
		if (idx > 0 && idx < libraryList.getModel().getSize()) {
			libraryList.setSelectedIndex(idx);
		}
	}


	public void updateList(boolean filter) {
		if (!filter){
			
			updateList();
			return;
		}
		
		
		int idx = libraryList.getSelectedIndex();
		
		libraryList.setListData(item.getSearchListing(searchText.getText(), campaign));
		
		if (idx < libraryList.getModel().getSize()) {
			libraryList.setSelectedIndex(idx);
		}
	}
	
	public void updateList() {
		int idx = libraryList.getSelectedIndex();
		libraryList.setListData(item.getSearchListing(searchText.getText()));
		
		if (idx < libraryList.getModel().getSize()) {
			libraryList.setSelectedIndex(idx);
		}
	}

	public void decSelected() {
		if (libraryList.getSelectedIndex() > 0) {
			libraryList.setSelectedIndex(libraryList.getSelectedIndex() - 1);
		}
	}

	public void incSelected() {
		if (libraryList.getSelectedIndex() < libraryList.getModel().getSize()) {
			libraryList.setSelectedIndex(libraryList.getSelectedIndex() + 1);
		}
	}

	public Object getSelected() {
		return libraryList.getSelectedValue();
	}

	private void doLayout(Component comp, int x, int y) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = 1;
		c.gridheight = 1;
		gridbag.setConstraints(comp, c);
		main.add(comp);
	}

	@SuppressWarnings("unused")
	private void doLayout(Component comp, int x, int y, int width, int height) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.gridheight = height;

		gridbag.setConstraints(comp, c);
		main.add(comp);
	}

	public LibraryPopupMenu getPopupMenu() {
		return rClickMenu;
	}

	public void valueChanged(ListSelectionEvent e) {

		try {
			LibraryItem f = (LibraryItem) libraryList.getSelectedValue();
			if (f != null) {
				description.setText(f.getFullDescription());
				description.setCaretPosition(0);
				libraryList
						.ensureIndexIsVisible(libraryList.getSelectedIndex());
			}
		} catch (Exception ex) {

			logger.log("error","Error in library panel " + ex);
			ex.printStackTrace();
		}
	}

	public static Campaign getCampaign() {
		return campaign;
	}

	public static void setCampaign(Campaign campaign) {
		LibraryPanel.campaign = campaign;
	}
}

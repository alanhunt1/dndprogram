package initcheck.character;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import initcheck.PanelButton;
import initcheck.PlayerManager;
import initcheck.database.Importable;
import initcheck.graphics.TiledList;
import initcheck.graphics.TiledPanel;

public class LoadLibraryVerifyDialog extends JFrame implements ListSelectionListener {

	private static final long serialVersionUID = 1L;

	private TiledList loadList = new TiledList();

	private JScrollPane listScroll = new JScrollPane(loadList);

	//private PlayerManager owner;

	private PanelButton remove = new PanelButton("Cancel Selected", 100);

	private PanelButton copy = new PanelButton("Copy Selected", 100);

	private PanelButton copyAll = new PanelButton("Copy All", 100);

	private PanelButton load = new PanelButton("Overwrite Selected", 100);

	private PanelButton loadAll = new PanelButton("Overwrite All", 100);

	private PanelButton saveAs = new PanelButton("Save As", 100);
	
	private PanelButton close = new PanelButton("Close", 100);

	private Vector<Importable> libraryItems = new Vector<Importable>();

	private TiledList saveAsList = new TiledList();
	
	private JScrollPane saveAsScroll = new JScrollPane(saveAsList);
	
	TiledPanel p = new TiledPanel();
	
	public LoadLibraryVerifyDialog(PlayerManager owner, Vector<Importable> libraryItems) {
		setTitle("Verify Library Imports");
		
		
		//this.owner = owner;
		this.libraryItems = libraryItems;

		loadList.setListData(this.libraryItems);

	
		
		loadList.addListSelectionListener(this);
		loadList.setCellRenderer(new ImportListCellRenderer());
		loadList.setFont(new Font("Courier", Font.PLAIN, 12));
		
		saveAsList.setCellRenderer(new GenericListCellRenderer());
		
		p.setLayout(new BorderLayout());
		p.add(listScroll, BorderLayout.CENTER);
		p.add(saveAsScroll, BorderLayout.EAST);
		
		TiledPanel buttonPanel = new TiledPanel();
		buttonPanel.setOpaque(true);
		buttonPanel.add(remove);
		buttonPanel.add(copy);
		buttonPanel.add(copyAll);
		buttonPanel.add(load);
		buttonPanel.add(loadAll);
		buttonPanel.add(saveAs);
		buttonPanel.add(close);

		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeItem();
			}
		});

		copy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copyItems();
			}
		});

		copyAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copyAll();
			}
		});

		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadItems();
			}
		});

		loadAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadAll();
			}
		});

		saveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAs();
			}
		});

		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		p.add(buttonPanel, BorderLayout.SOUTH);

		getContentPane().add(p);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	
	}


	public void valueChanged(ListSelectionEvent e) {
		if (loadList.getSelectedIndex() > -1){
			loadList.ensureIndexIsVisible(loadList.getSelectedIndex());
			Importable obj = (Importable)loadList.getSelectedValue();
			saveAsList.setListData(obj.getValues());
		}
	}

	public void removeItem() {
		libraryItems.removeElementAt(loadList.getSelectedIndex());
		loadList.setListData(libraryItems);
	}
	
	public void saveAs(){
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		Importable obj = (Importable)loadList.getSelectedValue();	
		Importable saveObj = (Importable)saveAsList.getSelectedValue();
		
		if (obj != null && saveObj != null){
			obj.setId(saveObj.getId());
			libraryItems.removeElement(obj);
			obj.save(true);
			loadList.setListData(libraryItems);
		}
		
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void copyItems() {
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		List<Object> values = loadList.getSelectedValuesList();

		for (Object o:values) {
			Importable d = (Importable) o;
			libraryItems.removeElement(d);
			d.setName("Import" + d.getName());
			d.save(false);
		}
		loadList.setListData(libraryItems);
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void copyAll() {
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		for (int i = 0; i < libraryItems.size(); i++) {
			Importable d = (Importable) (libraryItems.get(i));
			d.setName("Import" + d.getName());
			d.save(false);
		}
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		dispose();
	}

	public void loadItems() {
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		List<Object> values = loadList.getSelectedValuesList();

		for (Object o:values) {
			Importable d = (Importable) o;
			libraryItems.removeElement(d);
			d.save(true);
		}
		loadList.setListData(libraryItems);
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void loadAll() {
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		for (int i = 0; i < libraryItems.size(); i++) {
			Importable d = (Importable) (libraryItems.get(i));
			d.save(true);
		}
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		dispose();
	}

}

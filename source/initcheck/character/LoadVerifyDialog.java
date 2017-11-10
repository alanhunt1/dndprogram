package initcheck.character;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import initcheck.DCharacter;
import initcheck.PanelButton;
import initcheck.PlayerManager;
import initcheck.graphics.TiledPanel;

public class LoadVerifyDialog extends JFrame implements ListSelectionListener {

	private static final long serialVersionUID = 1L;

	private JList loadList = new JList();

	private JScrollPane listScroll = new JScrollPane(loadList);

	private PlayerManager owner;

	private PanelButton remove = new PanelButton("Cancel Selected", 100);

	private PanelButton copy = new PanelButton("Copy Selected", 100);

	private PanelButton copyAll = new PanelButton("Copy All", 100);

	private PanelButton load = new PanelButton("Overwrite Selected", 100);

	private PanelButton loadAll = new PanelButton("Overwrite All", 100);

	private PanelButton close = new PanelButton("Close", 100);

	private Vector<DCharacter> chars = new Vector<DCharacter>();

	public LoadVerifyDialog(PlayerManager owner, Vector<DCharacter> chars) {
		setTitle("Verify Character Imports");
		
		this.owner = owner;
		this.chars = chars;

		loadList.setListData(chars);

		loadList.addListSelectionListener(this);
		loadList.setCellRenderer(new PlayerListCellRenderer());
		loadList.setFont(new Font("Courier", Font.PLAIN, 12));
		loadList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					showDialog();
				}
			}

		});

		TiledPanel p = new TiledPanel();
		p.setLayout(new BorderLayout());
		p.add(listScroll, BorderLayout.CENTER);

		TiledPanel buttonPanel = new TiledPanel();
		buttonPanel.setOpaque(true);
		buttonPanel.add(remove);
		buttonPanel.add(copy);
		buttonPanel.add(copyAll);
		buttonPanel.add(load);
		buttonPanel.add(loadAll);
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

		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cleanup();
				dispose();
			}
		});

		p.add(buttonPanel, BorderLayout.SOUTH);

		getContentPane().add(p);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void cleanup(){
		owner.loadParty();
	}
	
	private void showDialog() {
		if (loadList.getSelectedIndex() > -1){
			owner.showComparisonDialog((DCharacter) loadList.getSelectedValue());
		}
	}

	public void valueChanged(ListSelectionEvent e) {
		loadList.ensureIndexIsVisible(loadList.getSelectedIndex());
	}

	public void removeItem() {
		chars.removeElementAt(loadList.getSelectedIndex());
		loadList.setListData(chars);
	}

	public void copyItems() {
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		List<Object> values = loadList.getSelectedValuesList();

		for (Object i:values) {
			DCharacter d = (DCharacter) i;
			chars.removeElement(d);
			d.setName("Import" + d.getName());
			d.writeImportCharacter(false);
		}
		loadList.setListData(chars);
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void copyAll() {
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		for (int i = 0; i < chars.size(); i++) {
			DCharacter d = (DCharacter) (chars.get(i));
			d.setName("Import" + d.getName());
			d.writeImportCharacter(false);
		}
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		cleanup();
		dispose();
	}

	public void loadItems() {
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		List<Object> values = loadList.getSelectedValuesList();

		for (Object i:values) {
			DCharacter d = (DCharacter) i;
			chars.removeElement(d);
			d.writeImportCharacter(true);
		}
		loadList.setListData(chars);
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void loadAll() {
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		for (int i = 0; i < chars.size(); i++) {
			DCharacter d = (DCharacter) (chars.get(i));
			d.writeImportCharacter(true);
		}
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		cleanup();
		dispose();
	}

}

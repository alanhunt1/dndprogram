package initcheck.generator;

import initcheck.InitImage;
import initcheck.PanelButton;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class StoreListingPanel extends TiledGridPanel implements ListSelectionListener {

	private static final long serialVersionUID = 1L;

	private TiledList storeList = new TiledList();

	private JScrollPane listScroll = new JScrollPane(storeList);

	private PanelButton addButton = new PanelButton("Add");

	private PanelButton remButton = new PanelButton("Rem");

	private Vector<Store> stores;

	StoreInfoPanel storeInfo = new StoreInfoPanel();
	
	public StoreListingPanel(Vector<Store> stores) {
		super(InitImage.lightRocks);
		this.stores = stores;
		storeList.setListData(new Vector<Store>(stores));
		storeList.addListSelectionListener(this);
		storeList.setCellRenderer(new StoreListCellRenderer());
		setWeightX(0.5);
		setWeightY(1);
		doLayout(listScroll);
		doLayout(storeInfo, 1);
		setWeightX(0);
		setWeightY(0);
		incYPos();
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.add(addButton);
		buttonPanel.add(remButton);
		doLayout(buttonPanel);

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addStore();
			}
		});

		remButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeStore();
			}
		});
	}

	private void addStore() {
		stores.add(new Store());
		storeList.setListData(new Vector<Store>(stores));
	}

	private void removeStore() {
		int idx = storeList.getSelectedIndex();
		stores.remove(idx);
		storeList.setListData(new Vector<Store>(stores));
	}
	
	public Vector<Store> getModel(){
		int idx = storeList.getSelectedIndex();
		stores.clear();
		for (int i = 0; i < storeList.getModel().getSize(); i++){
			Store b = (Store)storeList.getModel().getElementAt(i);
			stores.add(b);
		}
		storeList.setListData(new Vector<Store>(stores));
		if (idx > -1){
			storeList.setSelectedIndex(idx);
		}
		return stores;
	}
	
	public void setModel(Vector<Store> stores){
		this.stores = stores;
		storeList.setListData(new Vector<Store>(stores));
		if (stores.size() > 0){
			storeList.setSelectedIndex(0);
		}
	}
	
	public void valueChanged(ListSelectionEvent e) {

		try {
			Store f = (Store) storeList.getSelectedValue();
			if (f != null) {
				storeInfo.setStore(f);
			}
		} catch (Exception ex) {

			ex.printStackTrace();
		}
		
	}
	
}

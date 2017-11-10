package initcheck.generator;

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

public class InnListingPanel extends TiledGridPanel implements ListSelectionListener {

	private static final long serialVersionUID = 1L;

	private TiledList innList = new TiledList();

	private JScrollPane listScroll = new JScrollPane(innList);

	private PanelButton addButton = new PanelButton("Add");

	private PanelButton remButton = new PanelButton("Rem");

	private Vector<Inn> inns;

	private InnInfoPanel innInfo = new InnInfoPanel();
	
	public InnListingPanel(Vector<Inn> inns) {
		this.inns = inns;
		innList.setListData(inns);
		innList.addListSelectionListener(this);
		innList.setCellRenderer(new StoreListCellRenderer());
		
		setWeightX(0.5);
		setWeightY(1);
		doLayout(listScroll);
		doLayout(innInfo, 1);
		setWeightX(0);
		setWeightY(0);
		incYPos();
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(addButton);
		buttonPanel.add(remButton);
		doLayout(buttonPanel);

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addInn();
			}
		});

		remButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeInn();
			}
		});
	}

	private void addInn() {
		inns.add(new Inn());
		innList.setListData(inns);
	}

	private void removeInn() {
		int idx = innList.getSelectedIndex();
		inns.remove(idx);
		innList.setListData(inns);
	}
	
	public Vector<Inn> getModel(){
		int idx = innList.getSelectedIndex();
		innList.setListData(inns);
		if (idx >= 0){
			innList.setSelectedIndex(idx);
		}
		return inns;
	}
	
	public void setModel(Vector<Inn> inns){
		this.inns = inns;
		innList.setListData(inns);
		if (inns.size() > 0){
			innList.setSelectedIndex(0);
		}
	}
	
	
	public void valueChanged(ListSelectionEvent e) {

		try {
			Inn f = (Inn) innList.getSelectedValue();
			if (f != null) {
				innInfo.setInn(f);
			}
		} catch (Exception ex) {

			ex.printStackTrace();
		}
	}
	
}

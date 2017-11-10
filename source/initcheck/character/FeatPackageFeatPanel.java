package initcheck.character;

import initcheck.PanelButton;
import initcheck.character.chooser.FeatChooser;
import initcheck.database.Feat;
import initcheck.database.FeatPackage;
import initcheck.database.FeatPackageItem;
import initcheck.database.FeatPackageItemDAO;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class FeatPackageFeatPanel extends JPanel {

	
	private static final long serialVersionUID = 1L;

	private JList packageFeatList = new JList();

	private JScrollPane advanceScroll = new JScrollPane(packageFeatList);

	Vector<FeatPackageItem> featVector = new Vector<FeatPackageItem>();

	private PanelButton addButton = new PanelButton("add");

	private PanelButton remButton = new PanelButton("rem");

	private FeatChooser featChooser = new FeatChooser();

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints c = new GridBagConstraints();

	private FeatPackage cc;

	public FeatPackageFeatPanel(FeatPackage cc) {
		init();
		this.cc = cc;
		if (cc != null) {
			FeatPackageItemDAO ccdb = new FeatPackageItemDAO();
			featVector = ccdb.getPackageFeats(cc.getId());
			packageFeatList.setListData(featVector);
		}

	}

	private void init() {

		setLayout(gridbag);
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 10;
		c.ipady = 10;
		packageFeatList.setVisibleRowCount(5);
		int ypos = 0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		doLayout(advanceScroll, 0, ypos);
		c.weightx = 0;
		c.weighty = 0;
		ypos++;

		JPanel buttons = new JPanel();
		buttons.add(featChooser);
		buttons.add(addButton);
		buttons.add(remButton);

		doLayout(buttons, 0, ypos);
		ypos++;

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAdvance();
			}
		});

		remButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remAdvance();
			}
		});

	}

	private void addAdvance() {
		int idx = packageFeatList.getSelectedIndex();
		if (idx == -1) {
			idx++;
		}
		Feat f = (Feat) featChooser.getSelectedItem();

		FeatPackageItem newDomain = new FeatPackageItem();

		newDomain.setFeatId(f.getId());
		newDomain.setPackageId(cc.getId());

		FeatPackageItemDAO cadb = new FeatPackageItemDAO();
		cadb.addFeatPackage(newDomain);

		featVector = cadb.getPackageFeats(cc.getId());
		packageFeatList.setListData(featVector);
		packageFeatList.setSelectedIndex(idx);
	}

	private void remAdvance() {
		int idx = packageFeatList.getSelectedIndex();
		FeatPackageItem f = (FeatPackageItem) packageFeatList
				.getSelectedValue();
		FeatPackageItemDAO cadb = new FeatPackageItemDAO();

		cadb.deleteFeatPackage(f);

		featVector = cadb.getPackageFeats(cc.getId());

		packageFeatList.setListData(featVector);
		packageFeatList.setSelectedIndex(idx - 1);
	}

	private void doLayout(Component comp, int x, int y) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = 1;
		c.gridheight = 1;
		gridbag.setConstraints(comp, c);
		add(comp);
	}

	@SuppressWarnings("unused")
	private void doLayout(Component comp, int x, int y, int width, int height) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.gridheight = height;

		gridbag.setConstraints(comp, c);
		add(comp);
	}

}

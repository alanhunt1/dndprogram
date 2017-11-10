package initcheck.character.library;

import initcheck.InitImage;
import initcheck.PanelButton;
import initcheck.character.GenericListCellRenderer;
import initcheck.character.chooser.LibraryTypeChooser;
import initcheck.character.chooser.SourceChooser;
import initcheck.database.Armor;
import initcheck.database.CampaignBlackList;
import initcheck.database.CampaignBlackListDAO;
import initcheck.database.LibraryItemDAO;
import initcheck.database.LibraryItemDAOFactory;
import initcheck.database.MaterialSource;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class BlackListPanel extends TiledGridPanel {

	private static final long serialVersionUID = 1L;

	private PanelButton addButton = new PanelButton("Add");

	private PanelButton remButton = new PanelButton("Rem");

	private TiledList sourceList = new TiledList();

	private JScrollPane sourceScroll = new JScrollPane(sourceList);

	private CreateCampaignPanel owner;

	private SourceChooser sourceChooser = new SourceChooser(true);
	
	private LibraryTypeChooser libraryChooser = new LibraryTypeChooser();
	
	
	
	private JPanel sourceButtonPanel = new JPanel();
	
	private TiledList libraryList = new TiledList();
	
	private JScrollPane libraryScroll = new JScrollPane(libraryList);
	
	public BlackListPanel(CreateCampaignPanel owner) {
		super(InitImage.lightRocks);
		this.owner = owner;
		init();

	}

	public void init() {
		sourceList.setListData(owner.getCampaign().getBlackList());
		sourceList.setCellRenderer(new GenericListCellRenderer());
		libraryList.setCellRenderer(new GenericListCellRenderer());
		doLayout(new JLabel("Black List Exceptions"), 0, ypos, 2, 1);
		ypos++;
		setWeightX(0.5);
		setWeightY(1.0);
		doLayout(sourceScroll, 0, ypos);
		doLayout(libraryScroll, 1, ypos);
		ypos++;
		
		
		sourceButtonPanel.setOpaque(false);
		sourceButtonPanel.add(sourceChooser);
		sourceButtonPanel.add(libraryChooser);
		
		sourceButtonPanel.add(addButton);
		sourceButtonPanel.add(remButton);
		setWeightX(0);
		setWeightY(0);
		
		doLayout(sourceButtonPanel, 0, ypos, 2, 1);
		
		remButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeSource();
			}
		});

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addSource();
			}
		});
		
		sourceChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateChoosers();
			}
		});
		
		libraryChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateChoosers();
			}
		});
		
	}

	public void updateChoosers(){
		String type = (String)libraryChooser.getSelectedItem();
		String source = sourceChooser.getSelectedItem().toString();
		
		LibraryItemDAO db = LibraryItemDAOFactory.getInstance(type);
		Armor a = new Armor();
		a.setSource(source);
		libraryList.setListData(db.getItems(a));
		
		
	}
	
	public void setCampaign(){
		CampaignBlackListDAO csdb = new CampaignBlackListDAO();
		sourceList.setListData(csdb.getCampaignBlackList(owner.getCampaign().getId()));
	}
	
	public void removeSource() {
		CampaignBlackListDAO csdb = new CampaignBlackListDAO();
		CampaignBlackList cs = (CampaignBlackList)sourceList.getSelectedValue();
		csdb.deleteCampaignBlackList(cs);
		sourceList.setListData(csdb.getCampaignBlackList(owner.getCampaign().getId()));
	}

	public void addSource() {
		CampaignBlackListDAO csdb = new CampaignBlackListDAO();
		CampaignBlackList cs = new CampaignBlackList();
		LibraryItem li = ((LibraryItem)libraryList.getSelectedValue());
		cs.setCampaignId(owner.getCampaign().getId());
		cs.setType((String)libraryChooser.getSelectedItem());
		cs.setXrefId(li.getId());
		cs.setSourceId(((MaterialSource)sourceChooser.getSelectedItem()).getSourceId());
		cs.setXrefName(li.getName());
		csdb.addCampaignBlackList(cs);
		sourceList.setListData(csdb.getCampaignBlackList(owner.getCampaign().getId()));
	}

}

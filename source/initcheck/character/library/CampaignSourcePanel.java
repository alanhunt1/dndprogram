package initcheck.character.library;

import initcheck.InitImage;
import initcheck.PanelButton;
import initcheck.character.GenericListCellRenderer;
import initcheck.character.chooser.SourceChooser;
import initcheck.database.CampaignSource;
import initcheck.database.CampaignSourceDAO;
import initcheck.database.MaterialSource;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class CampaignSourcePanel extends TiledGridPanel {

	private static final long serialVersionUID = 1L;

	private PanelButton addButton = new PanelButton("Add");

	private PanelButton remButton = new PanelButton("Rem");

	private TiledList sourceList = new TiledList();

	private JScrollPane sourceScroll = new JScrollPane(sourceList);

	private CreateCampaignPanel owner;

	private SourceChooser sourceChooser = new SourceChooser(true);
	
	public CampaignSourcePanel(CreateCampaignPanel owner) {
		super(InitImage.lightRocks);
		this.owner = owner;
		init();

	}

	public void init() {
		sourceList.setListData(owner.getCampaign().getSources());
		sourceList.setCellRenderer(new GenericListCellRenderer());
		doLayout(new JLabel("Valid Sources"), 0, ypos);
		ypos++;
		setWeightX(1.0);
		setWeightY(1.0);
		doLayout(sourceScroll, 0, ypos);
		ypos++;
		
		JPanel sourceButtonPanel = new JPanel();
		sourceButtonPanel.setOpaque(false);
		sourceButtonPanel.add(sourceChooser);
		sourceButtonPanel.add(addButton);
		sourceButtonPanel.add(remButton);
		setWeightX(0);
		setWeightY(0);
		
		doLayout(sourceButtonPanel, 0, ypos);
		
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
	}

	public void setCampaign(){
		CampaignSourceDAO csdb = new CampaignSourceDAO();
		sourceList.setListData(csdb.getCampaignSources(owner.getCampaign().getId()));
	}
	
	public void removeSource() {
		CampaignSourceDAO csdb = new CampaignSourceDAO();
		CampaignSource cs = (CampaignSource)sourceList.getSelectedValue();
		csdb.deleteCampaignSource(cs);
		sourceList.setListData(csdb.getCampaignSources(owner.getCampaign().getId()));
	}

	public void addSource() {
		CampaignSourceDAO csdb = new CampaignSourceDAO();
		CampaignSource cs = new CampaignSource();
		cs.setCampaignId(owner.getCampaign().getId());
		cs.setSourceId(((MaterialSource)sourceChooser.getSelectedItem()).getSourceId());
		
		csdb.addCampaignSource(cs);
		sourceList.setListData(csdb.getCampaignSources(owner.getCampaign().getId()));
	}

}

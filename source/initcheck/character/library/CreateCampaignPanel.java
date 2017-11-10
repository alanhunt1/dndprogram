package initcheck.character.library;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import initcheck.InitImage;
import initcheck.PanelButton;
import initcheck.character.chooser.RulesetChooser;
import initcheck.database.Campaign;
import initcheck.database.CampaignDAO;
import initcheck.database.Ruleset;
import initcheck.graphics.TiledDialog;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledPanel;
import initcheck.graphics.TiledTextArea;

public class CreateCampaignPanel extends TiledDialog {

	private static final long serialVersionUID = 1L;

	private JTextField name = new JTextField(20);

	private TiledTextArea notes = new TiledTextArea(10, 50);

	private JScrollPane descScroll = new JScrollPane(notes);

	private CampaignSourcePanel sourcePanel;
	
	private TiledGridPanel main = new TiledGridPanel(InitImage.lightRocks);

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton cancelButton = new PanelButton("Close");

	private WhiteListPanel whiteListPanel;
	
	private BlackListPanel blackListPanel;
	
	private WeaponSizePanel weaponSizePanel;
	
	private TiledPanel buttonPanel = new TiledPanel(InitImage.lightRocks);

	private Campaign Campaign;

	private PanelButton prevButton = new PanelButton("<", 20);

	private PanelButton nextButton = new PanelButton(">", 20);
	
	private JTabbedPane sourceTabs = new JTabbedPane();
	
	private JournalPanel journalPanel;
	
	private RulesetChooser rulesetChooser = new RulesetChooser();
	
	CampaignDAO db = new CampaignDAO();

	LibraryPanel owner;

	public Campaign getCampaign() {
		return Campaign;
	}

	public CreateCampaignPanel(LibraryPanel owner) {
		Campaign = new Campaign();

		init();
		this.owner = owner;
	}

	public CreateCampaignPanel(Campaign f, LibraryPanel owner) {
		
		this.owner = owner;
		setCampaign(f);
		init();
		
	}

	public void setCampaign(Campaign f){
		
		this.Campaign = f;

		notes.setText(f.getNotes());
		name.setText(f.getName());
		rulesetChooser.setSelectedRuleset(f.getRulesetId());
		
		if (sourcePanel != null){
			sourcePanel.setCampaign();
			whiteListPanel.setCampaign();
			blackListPanel.setCampaign();
			weaponSizePanel.setCampaign();
			journalPanel.setCampaign();
		}
		
	}
	
	public void init() {
		super.setBorderSize(0);
		sourcePanel =  new CampaignSourcePanel(this);
		whiteListPanel = new WhiteListPanel(this);
		blackListPanel = new BlackListPanel(this);
		weaponSizePanel = new WeaponSizePanel(this);
		journalPanel = new JournalPanel(this);
		
		
		notes.setWrapStyleWord(true);
		notes.setLineWrap(true);

		int ypos = main.ypos;

		
		main.doLayout(new JLabel("Name"), 0, ypos);
		main.doLayout(name, 1, ypos);
		main.doLayout(new JLabel("Ruleset"), 2, ypos);
		main.doLayout(rulesetChooser, 3, ypos);
		
		ypos++;

		main.setWeightX(1.0);
		main.setWeightY(0.5);
		sourceTabs.addTab("Sources", sourcePanel);
		sourceTabs.addTab("White List", whiteListPanel);
		sourceTabs.addTab("Black List", blackListPanel);
		sourceTabs.addTab("Weapon Size Increase", weaponSizePanel);
		sourceTabs.addTab("Journals", journalPanel);
		main.doLayout(sourceTabs, 0, ypos, 4, 1);
		ypos++;

		//main.doLayout(whiteListPanel, 0, ypos, 2, 1);
		//ypos++;
		
		main.doLayout(descScroll, 0, ypos, 4, 1);
		main.setWeightX(0);
		main.setWeightY(0);

		ypos++;
		
		buttonPanel.add(prevButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(nextButton);
		
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showNext();
			}
		});

		prevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPrev();
			}
		});

		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveCampaign();
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		setMainWindow(main);
		setButtonBar(buttonPanel);
		pack();
		setVisible(true);
	}

	public void showNext() {
		owner.incSelected();
		setCampaign((Campaign) owner.getSelected());
	}

	public void showPrev() {
		owner.decSelected();
		setCampaign((Campaign) owner.getSelected());
	}
	
	public void saveCampaign() {
		Campaign.setName(name.getText());
		Campaign.setNotes(notes.getText());
		Campaign.setRulesetId(((Ruleset)rulesetChooser.getSelectedItem()).getRulesetId());
		Campaign.setRulesetName(((Ruleset)rulesetChooser.getSelectedItem()).getRulesetName());
		int i = db.addOrUpdateCampaign(Campaign);
		Campaign.setId("" + i);
		owner.updateList();
	}

}

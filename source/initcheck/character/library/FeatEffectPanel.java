package initcheck.character.library;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import initcheck.PanelButton;
import initcheck.character.GenericListCellRenderer;
import initcheck.character.chooser.AbilityChooser;
import initcheck.character.chooser.AlignmentChooser;
import initcheck.character.chooser.ClassChooser;
import initcheck.character.chooser.DomainChooser;
import initcheck.character.chooser.FeatChooser;
import initcheck.character.chooser.FeatEffectTypeChooser;
import initcheck.character.chooser.FeatTypeChooser;
import initcheck.character.chooser.LevelChooser;
import initcheck.character.chooser.ProficiencyTypeChooser;
import initcheck.character.chooser.RaceChooser;
import initcheck.character.chooser.SaveTypeChooser;
import initcheck.character.chooser.SkillChooser;
import initcheck.character.chooser.StatChooser;
import initcheck.database.Feat;
import initcheck.database.FeatEffects;
import initcheck.database.FeatEffectsDAO;
import initcheck.database.Skill;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledList;
import initcheck.graphics.TiledListItem;

public class FeatEffectPanel extends TiledGridPanel {

	private static final long serialVersionUID = 1L;

	private TiledList<TiledListItem> effectList = new TiledList();
	
	private JScrollPane effectScroll = new JScrollPane(effectList);
	
	private PanelButton remButton = new PanelButton("Del");
	
	private PanelButton addButton = new PanelButton("Add");
	
	private FeatEffectTypeChooser reqType = new FeatEffectTypeChooser();
	
	private JPanel effectPanel = new JPanel();
	
	private String featId;
	
	private JTextField reqValue = new JTextField(5);
	
	private Vector<FeatEffects> effect = new Vector<FeatEffects>();	
	
	private JComboBox reqName = new JComboBox();
	
	public FeatEffectPanel(){
		super("images/rockLighter.jpg");
		init();
		
		doLayout(new JLabel("Feat Skill Bonuses"));
		ypos++;
		setWeightX(1.0);
		setWeightY(1.0);
		doLayout(effectScroll, 0);
		ypos++;
		effectPanel.setOpaque(false);
		doLayout(effectPanel, 0);
	}
	
	public void init() {
		
		effectList.setCellRenderer(new GenericListCellRenderer());
		effectPanel.add(reqType);
		effectPanel.add(reqName);
		effectPanel.add(reqValue);	
		effectPanel.add(addButton);
		effectPanel.add(remButton);

		reqType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectEffectName();
			}
		});

		remButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeEffect();
			}
		});

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addEffect();
			}
		});
	}

	
	public void addEffect(){
		FeatEffectsDAO fedb = new FeatEffectsDAO();
		
		FeatEffects fp = new FeatEffects();
		String s = (String) reqType.getSelectedItem();
		if (s.equals("SKILL")){
			Skill sk = (Skill)reqName.getSelectedItem();
			fp.setCrossRefId(sk.getId());
		}else if (s.equals("FEAT")){
			Feat f = (Feat)reqName.getSelectedItem();
			fp.setCrossRefId(f.getId());
		}
		
		fp.setEffectType((String) reqType.getSelectedItem());
		fp.setEffectName(reqName.getSelectedItem().toString());
		fp.setEffectAmount(reqValue.getText());
		fp.setFeatId(featId);
		
		
		fedb.addFeatEffects(fp);
		effect = fedb.getFeatEffects(featId);

		effectList.setListData(effect);
	}
	
	public void selectEffectName(){
		
			String s = (String) reqType.getSelectedItem();

			invalidate();
			effectPanel.remove(reqName);
			effectPanel.remove(reqValue);
		
			effectPanel.remove(addButton);
			effectPanel.remove(remButton);
			if (s.equals("FEAT")) {
				reqName = new FeatChooser();
			} else if (s.equals("STAT")) {
				reqName = new StatChooser();
			} else if (s.equals("SKILL")) {
				reqName = new SkillChooser();
			} else if (s.equals("LEVEL")) {
				reqName = new LevelChooser();
			} else if (s.equals("ABILITY")) {
				reqName = new AbilityChooser();
			} else if (s.equals("ALIGNMENT")) {
				reqName = new AlignmentChooser();
			} else if (s.equals("CLASS")) {
				reqName = new ClassChooser();
			} else if (s.equals("PROFICIENCY")) {
				reqName = new ProficiencyTypeChooser();
			} else if (s.equals("FEAT CLASS")) {
				reqName = new FeatTypeChooser();
			} else if (s.equals("DOMAIN")) {
				reqName = new DomainChooser();
			} else if (s.equals("SAVE")) {
				reqName = new SaveTypeChooser();
			} else if (s.equals("RACE")) {
				reqName = new RaceChooser();
			}

			effectPanel.add(reqName);
			effectPanel.add(reqValue);
	
			effectPanel.add(addButton);
			effectPanel.add(remButton);
			validate();
		

	}
	public void setFeat (String id){
		featId = id;
		FeatEffectsDAO fedb = new FeatEffectsDAO();
		effect = fedb.getFeatEffects(featId);
		effectList.setListData(effect);
	}
	
	public void removeEffect() {
		FeatEffectsDAO fedb = new FeatEffectsDAO();
		int idx = effectList.getSelectedIndex();
		FeatEffects fp = effect.get(idx);
		fedb.deleteFeatEffects(fp);
		effect = fedb.getFeatEffects(featId);
		effectList.setListData(effect);
	}
	
}

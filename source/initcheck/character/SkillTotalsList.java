package initcheck.character;

import initcheck.DescriptionListDialog;
import initcheck.InitFont;
import initcheck.InitImage;
import initcheck.TranslucentGridPanel;
import initcheck.TransparentButton;
import initcheck.database.PlayerSkills;
import initcheck.database.PlayerSkillsDAO;
import initcheck.database.Skill;
import initcheck.graphics.TiledList;
import initcheck.utils.StrManip;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SkillTotalsList extends TranslucentGridPanel implements
		ListSelectionListener {

	private static final long serialVersionUID = 1L;

	private TiledList skillList = new TiledList();

	private JScrollPane skillScroll = new JScrollPane(skillList);

	private ClassSlot currClass;

	private PlayerStatDialog owner;

	private PlayerSkillPanel parent;

	private JLabel totalLabel = new JLabel("  ");

	private TransparentButton showCalcButton = new TransparentButton(InitImage.qmarkIcon);
	
	public SkillTotalsList(PlayerStatDialog owner, PlayerSkillPanel parent,
			ClassSlot cclass) {

		super(false);
		this.owner = owner;
		this.parent = parent;
		this.setInsets(5);
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		currClass = cclass;

		skillList.setVisibleRowCount(5);

		skillList.addListSelectionListener(this);
		skillList.setCellRenderer(new GenericListCellRenderer());
		skillList.setFont(new Font("Courier", Font.PLAIN, 12));
		PlayerSkillsDAO psdb = new PlayerSkillsDAO();

		//if (currClass != null) {
			Vector<PlayerSkills> skills = psdb.getPlayerSkills("" + owner.getChar().getID());
			
			Vector<String> usage = new Vector<String>();
			HashMap<String, String> skillhash = new HashMap<String, String>();
			
			for (int i = 0; i < skills.size(); i++) {
				Skill s = (Skill) skills.get(i);
				if (skillhash.get(s.getName()) == null){
					skillhash.put(s.getName(), "");
					String total = StrManip.pad(s.getName(), 20) + StrManip.pad(owner.getChar().getSkillSet()
							.getBaseRanks(Integer.parseInt(s.getId()))
							+ "/" + (new Double(owner.getChar().getLevel()).intValue() + 3), 8);
					usage.add(total);
				}
				
				
			}
			
			skillList.setStrings(usage);
		//}

		doLayout(new JLabel("Rank Totals"), 0, ypos);
		ypos++;
		setWeightX(1.0);
		setWeightY(1.0);
		doLayout(skillScroll, 0, ypos);
		setWeightX(0);
		setWeightY(0);
		ypos++;
		JPanel filler = new JPanel();
		filler.setOpaque(false);
		filler.add(totalLabel);
		totalLabel.setFont(InitFont.medFont);
		filler.add(showCalcButton);
		doLayout(filler, 0, ypos);

		showCalcButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showCalc();
			}
		});
		
	}

	public void showCalc(){
		
		DescriptionListDialog.display("Skill Components", parent.getCalc());
	}
	
	public void setSkill(Skill s){
		
		for (int i = 0; i < skillList.getModel().getSize(); i++){
			String str = skillList.getModel().getElementAt(i).toString();
			
			if (str.startsWith(s.getName())){
				skillList.setSelectedIndex(i);
				break;
			}
		}
	}
	
	
	public void updateList() {
		PlayerSkillsDAO psdb = new PlayerSkillsDAO();
		int idx = skillList.getSelectedIndex();

		if (currClass != null) {
			Vector<PlayerSkills> skills = psdb
					.getPlayerSkills("" + owner.getChar().getID(), currClass
							.getClassName().getId());
			Vector<String> usage = new Vector<String>();
			for (int i = 0; i < skills.size(); i++) {
				Skill s = (Skill) skills.get(i);
				String total = StrManip.pad(s.getName(), 20) + StrManip.pad(owner.getChar().getSkillSet()
						.getBaseRanks(Integer.parseInt(s.getId()))
						+ "/" + (new Double(owner.getChar().getLevel()).intValue() + 3), 8);
				usage.add(total);
			}
			skillList.setStrings(usage);
			if (idx >= 0) {
				skillList.setSelectedIndex(idx);
			}
		}
	}

	public String getClassId() {
		return currClass.getClassName().getId();
	}

	public void setSelectedIndex(int i) {
		skillList.setSelectedIndex(i);
	}

	public Object getSelectedValue() {
		return skillList.getSelectedValue();
	}

	public int getSelectedIndex() {
		return skillList.getSelectedIndex();
	}

	public void setListData(Vector<Skill> v) {
		skillList.setListData(v);
	}

	public void addListSelectionListener(ListSelectionListener l) {
		skillList.addListSelectionListener(l);
	}

	public void valueChanged(ListSelectionEvent e) {

		try {
			//parent.setListIndex(skillList.getSelectedIndex());
			skillList.ensureIndexIsVisible(skillList.getSelectedIndex());
			totalLabel.setText("Total Skill Score "+parent.getTotal());
		} catch (Exception ex) {
			
			ex.printStackTrace();
		}
	}
}

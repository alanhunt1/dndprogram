package initcheck.client;

import initcheck.DCharacter;
import initcheck.HitPointBar;
import initcheck.InitColor;
import initcheck.InitFont;
import initcheck.database.SpellLevel;
import initcheck.utils.StrManip;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class DamageListCellRenderer extends JPanel implements ListCellRenderer {

	static final long serialVersionUID = 1;

	private JLabel textLabel = new JLabel();

	private JLabel nameLabel = new JLabel();

	private JLabel hpLabel = new JLabel();

	private HitPointBar hp = new HitPointBar();

	private JLabel spellLabel = new JLabel();
	
	private JPanel midPanel = new JPanel(new GridLayout(3,1));
	
	private JLabel modLabel = new JLabel();
	
	
	public DamageListCellRenderer() {
		// Don't paint behind the component
		setOpaque(true);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(nameLabel);
		
		midPanel.setOpaque(false);
		midPanel.add(textLabel);
		midPanel.add(spellLabel);
		midPanel.add(modLabel);
		
		add(midPanel);
		add(hpLabel);
		add(hp);
	}

	// This is the only method defined by ListCellRenderer.
	// We just reconfigure the JLabel each time we're called.

	public Component getListCellRendererComponent(JList list, Object value, // value
			// to
			// display
			int index, // cell index
			boolean isSelected, // is the cell selected
			boolean cellHasFocus) // the list and the cell have the focus
	{
			DCharacter p = null;
			try{
					p = (DCharacter) value;
			}catch(Exception e){
					return this;
			}
		Vector<SpellLevel> v = p.getDivineSpellsPerDay();
		
		int minor = 0;
		int light = 0;
		int serious = 0;
		int critical = 0;
		int moderate = 0;	
		
		for (int i = 0; i < v.size(); i++) {
			SpellLevel sl = v.get(i);
			
			String lev0 = sl.getLevel0();
			String lev1 = sl.getLevel1();
			String lev2 = sl.getLevel2();
			String lev3 = sl.getLevel3();
			String lev4 = sl.getLevel4();
			
			minor += Integer.parseInt(lev0.substring(0, lev0.indexOf("(")));
			minor += Integer.parseInt(lev0.substring(lev0.indexOf("(")+1, lev0.indexOf(")")));
			
			light += Integer.parseInt(lev1.substring(0, lev1.indexOf("(")));
			light += Integer.parseInt(lev1.substring(lev1.indexOf("(")+1, lev1.indexOf(")")));

			moderate += Integer.parseInt(lev2.substring(0, lev2.indexOf("(")));
			moderate += Integer.parseInt(lev2.substring(lev2.indexOf("(")+1, lev2.indexOf(")")));

			serious += Integer.parseInt(lev3.substring(0, lev3.indexOf("(")));
			serious += Integer.parseInt(lev3.substring(lev3.indexOf("(")+1, lev3.indexOf(")")));
		
			critical += Integer.parseInt(lev4.substring(0, lev4.indexOf("(")));
			critical += Integer.parseInt(lev4.substring(lev4.indexOf("(")+1, lev4.indexOf(")")));

		}
		
		// only display cures if there are some to display
		StringBuffer spellBuffer = new StringBuffer();
		if (minor > 0){
			int minorRemaining = minor - p.getDivineSpellsUsed(0);
			spellBuffer.append(" m:"+minorRemaining+"("+minor+")");
		}
		if (light > 0){
			int lightRemaining = light - p.getDivineSpellsUsed(1);
			spellBuffer.append(" L:"+lightRemaining+"("+light+")");
		}
		if (moderate > 0){
			int moderateRemaining = moderate - p.getDivineSpellsUsed(2);
			spellBuffer.append(" M:"+moderateRemaining+"("+moderate+")");
		}
		if (serious > 0){
			int seriousRemaining = serious - p.getDivineSpellsUsed(3);
			spellBuffer.append(" S:"+seriousRemaining+"("+serious+")");
		}
		if (critical > 0){
			int criticalRemaining = critical - p.getDivineSpellsUsed(4);
			spellBuffer.append(" C:"+criticalRemaining+"("+critical+")");
		}
		spellLabel.setText(spellBuffer.toString());
		
		
		hp.setValues(p.getPrecalcHP(), p.getCurrentHitPoints());
		
		// check the stat mods, and add them to the display if applicable
		modLabel.setText("");
		if (p.getStrMod() != 0){
			modLabel.setText(modLabel.getText()+"STR "+p.getStrMod());
		}
		if (p.getDexMod() != 0){
			modLabel.setText(modLabel.getText()+" DEX "+p.getDexMod());
		}
		if (p.getConMod() != 0){
			modLabel.setText(modLabel.getText()+" CON "+p.getConMod());
		}
		if (p.getChaMod() != 0){
			modLabel.setText(modLabel.getText()+" CHA "+p.getChaMod());
		}
		if (p.getWisMod() != 0){
			modLabel.setText(modLabel.getText()+" WIS "+p.getWisMod());
		}
		if (p.getIntMod() != 0){
			modLabel.setText(modLabel.getText()+" INT "+p.getIntMod());
		}
		
		String s = p.getDamageListFormat();
		textLabel.setText(s);
		nameLabel.setText(p.getNamePad());
		hpLabel.setText(StrManip.pad("" + p.getCurrentHitPoints(), 3));
		hp.setSelected(isSelected);

		if (isSelected) {

			setBackground(InitColor.red);
			
			textLabel.setBackground(InitColor.red);
			nameLabel.setBackground(InitColor.red);
			hpLabel.setBackground(InitColor.red);
			spellLabel.setBackground(InitColor.red);
			modLabel.setBackground(InitColor.red);
			
			textLabel.setForeground(Color.white);
			nameLabel.setForeground(Color.white);
			hpLabel.setForeground(Color.white);
			spellLabel.setForeground(Color.white);
			modLabel.setForeground(Color.white);
			
		} else {

			

			if (index % 2 == 0) {
				setBackground(list.getBackground());
				
			} else {
				setBackground(InitColor.lightGray);

			}
			
			if (p.getGender() != null && p.getGender().equals("F")) {
				textLabel.setForeground(InitColor.pink);
				nameLabel.setForeground(InitColor.pink);
				hpLabel.setForeground(InitColor.pink);
				spellLabel.setForeground(InitColor.pink);
				modLabel.setForeground(InitColor.pink);
			} else if (p.getGender() != null && p.getGender().equals("M")) {
				textLabel.setForeground(InitColor.blue);
				nameLabel.setForeground(InitColor.blue);
				hpLabel.setForeground(InitColor.blue);
				spellLabel.setForeground(InitColor.blue);
				modLabel.setForeground(InitColor.blue);
			} else {
				textLabel.setForeground(list.getForeground());
				nameLabel.setForeground(list.getForeground());
				hpLabel.setForeground(list.getForeground());
				spellLabel.setForeground(list.getForeground());
				modLabel.setForeground(list.getForeground());
			}
		}
		textLabel.setFont(InitFont.courier);
		spellLabel.setFont(InitFont.courier);
		modLabel.setFont(InitFont.courier);
		nameLabel.setFont(InitFont.courierBig);
		hpLabel.setFont(InitFont.courierBig);
		return this;
	}
}

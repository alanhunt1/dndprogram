package initcheck;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class HealLabel extends JLabel {

	boolean selected;

	DCharacter owner;

	int level;

	public DCharacter getOwner() {
		return owner;
	}

	public void setOwner(DCharacter owner) {
		this.owner = owner;
	}

	public HealLabel(DCharacter owner, int level, String text, boolean selected) {
		this.owner = owner;
		this.selected = selected;
		this.level = level;
		setFont(InitFont.courierBig);
		setOpaque(true);
		if (selected){
			setBackground(Color.black);
		}else{
			setBackground(Color.white);
		}
		setText(text);
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				toggleSelected();
			}
		});

	}

	public void toggleSelected() {
		if (selected) {
			setSelected(false);
			owner.markDivineSpellUnused(level);
		} else {
			setSelected(true);
			owner.markDivineSpellUsed(level);
		}
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		if (selected) {
			setBackground(Color.black);
		} else {
			setBackground(Color.WHITE);
		}
	}
}

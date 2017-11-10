package initcheck.server;

import java.awt.Dimension;

import initcheck.PanelButton;
import initcheck.database.Monster;

import javax.swing.ImageIcon;

public class MonsterInfoButton extends PanelButton{
	static final long serialVersionUID = 1;
		private Monster m;
				
		public MonsterInfoButton (Monster m){
				super(m.getName().trim(), 100);
				if(m.getIcon() != null && !m.getIcon().equals("")){
					setToolTipText(m.getName());
					setText("");
					setIcon(new ImageIcon(m.getIcon()));
					setPreferredSize(new Dimension(50, 50));
				}
				this.m = m;
		}

		public MonsterInfoButton (Monster m, String displayName){
				super(displayName, 100);
				if(m.getIcon() != null && !m.getIcon().equals("")){
					setToolTipText(displayName);
					setText("");
					setIcon(new ImageIcon(m.getIcon()));
					setPreferredSize(new Dimension(50, 50));
				}
				this.m = m;
		}
		
	

		public Monster getMonster(){
				return m;
		}

}

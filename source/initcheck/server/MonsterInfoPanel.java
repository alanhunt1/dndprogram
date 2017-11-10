package initcheck.server;

import initcheck.InitLogger;
import initcheck.InitServer;
import initcheck.character.GridPanel;
import initcheck.database.Monster;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class MonsterInfoPanel extends GridPanel {
	static final long serialVersionUID = 1;
		private Vector<MonsterInfoButton> monsterInfoButtons = new Vector<MonsterInfoButton>();
		private InitServer owner;
		private InitLogger logger = new InitLogger(this);

		public MonsterInfoPanel(InitServer owner){
				super(false);
				this.owner = owner;
				setWeightX(0);
				setWeightY(0);
				setOpaque(false);
		}
		
		public void removeMonster(int i){
				logger.log("REMOVING MONSTER INFO BUTTON "+i+" of "+monsterInfoButtons.size());
				remove((MonsterInfoButton)monsterInfoButtons.get(i)); 
				ypos--;
				monsterInfoButtons.removeElementAt(i);
		}

		public void removeMonsters(){
				removeAll();
				ypos = 0;
				monsterInfoButtons = new Vector<MonsterInfoButton>();
		}

		public Vector<MonsterInfoButton> getMonsterButtons(){
				return monsterInfoButtons;
		}
		
		public void addMonster(Monster m){
				
				String name = m.getName().trim();
				int end = name.lastIndexOf(" ");
				if (end < 0){
						end = name.length();
				}
				name = name.substring(0, end);
			
				final MonsterInfoButton mib = new MonsterInfoButton(m, name.trim());
				mib.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										owner.showMonsterInfoDialog(mib.getMonster());
								}
						});
				doLayout(mib, monsterInfoButtons.size()/2, monsterInfoButtons.size()%2);
				incYPos();
				monsterInfoButtons.add(mib);
		}
}

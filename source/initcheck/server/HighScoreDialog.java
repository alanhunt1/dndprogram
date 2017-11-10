package initcheck.server;

import initcheck.InitFont;
import initcheck.InitServer;
import initcheck.PanelButton;
import initcheck.database.DamageRecord;
import initcheck.database.DamageRecordDAO;
import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class HighScoreDialog extends JDialog {
		
	static final long serialVersionUID = 1;
	
		private ImageIcon bgImage = new ImageIcon("images/rock043.jpg");
		private TiledPanel contents = new TiledPanel(bgImage);
		private PanelButton iconButton = new PanelButton("Close", 50);
		private Vector<DamageRecord> highScores = new Vector<DamageRecord>();
		private JList highScoreList = new JList();
		private JScrollPane listScroll = new JScrollPane(highScoreList);
		
		private PanelButton addButton = new PanelButton("Add", 50);
		private PanelButton remButton = new PanelButton("Remove", 50);
		
		private InitServer owner;
		
		/**
		 * Get the HighScores value.
		 * @return the HighScores value.
		 */
		public Vector<DamageRecord> getHighScores() {
				return highScores;
		}

		/**
		 * Set the HighScores value.
		 * @param newHighScores The new HighScores value.
		 */
		public void setHighScores(Vector<DamageRecord> newHighScores) {
				this.highScores = newHighScores;
				initList();
		}
		
		
	
		
		public HighScoreDialog(InitServer owner){
				super();
				this.owner = owner;
				init();

		}
		
		private void init(){
					
		
				contents.setLayout(new BorderLayout());
				contents.setBorder(BorderFactory.createEmptyBorder(
																				20, //top
                                        20, //left
                                        10, //bottom
                                        20) //right
                                        );


				contents.add(listScroll, BorderLayout.CENTER);
				JPanel buttonPanel = new JPanel();
				buttonPanel.add(iconButton);
				buttonPanel.add(addButton);
				buttonPanel.add(remButton);
				
				contents.add(buttonPanel, BorderLayout.SOUTH);
				
				highScoreList.setFont(InitFont.courier);

				iconButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										dispose();
								}
						});			

				addButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										addScore();
								}
						});			

				remButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										removeScore();
								}
						});	

				getContentPane().add(contents);
				
		}

		@SuppressWarnings("unused")
		private void updateScore(){
				HighScoreInputDialog hsid = new HighScoreInputDialog(this, owner.getEngine().getDB().getCharacters(), owner.getBattle().getBattleId());
				hsid.setDamageRecord((DamageRecord)highScoreList.getSelectedValue());
				
		}

		private void addScore(){
				@SuppressWarnings("unused") HighScoreInputDialog hsid = new HighScoreInputDialog(this, owner.getEngine().getDB().getCharacters(), owner.getBattle().getBattleId());
				
		}

		public void addDamageRecord(DamageRecord dr){
				DamageRecordDAO drdb = new DamageRecordDAO();
				drdb.addDamageRecord(dr);
				owner.updateDamageRecords();
				highScoreList.setListData(owner.getDamageRecords());
		}

		private void removeScore(){
				DamageRecord dr = (DamageRecord)highScoreList.getSelectedValue();
				DamageRecordDAO drdb = new DamageRecordDAO();
				drdb.deleteDamageRecord(dr);
				owner.updateDamageRecords();
				highScoreList.setListData(owner.getDamageRecords());
		}

		private void initList(){
				highScoreList.setListData(highScores);
		}


}

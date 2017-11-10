package initcheck;

import initcheck.graphics.Skin;
import initcheck.server.CampaignNotesPanel;
import initcheck.server.CampaignNotesTab;

import java.awt.BorderLayout;
import java.io.Serializable;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.Document;

public class InitTextEditor extends JPanel implements Serializable,
		CampaignNotesTab {

	
	private static final long serialVersionUID = 1L;

	private InitTextEditorPane editorPane = new InitTextEditorPane();

	private JScrollPane editScroll = new JScrollPane(editorPane);

	private Skin skin = null;
	
	public int getType() {
		return CampaignNotesPanel.TEXT_TAB;
	}

	public InitTextEditor() {
		setLayout(new BorderLayout());
		setBackground(InitColor.fadedRed);
		add(editScroll, BorderLayout.CENTER);
	}
	
	public void setModel(Object o){
		Document d = (Document)o;
		editorPane = new InitTextEditorPane();
		editorPane.setDocument(d);
		editScroll.setViewportView(editorPane);
	}
	
	public Object getModel(){
		return editorPane.getDocument();
	}

	public Skin getSkin(){
		return skin;
	}
	
	public void setSkin(Skin s){
		this.skin = s;
	}
	
}

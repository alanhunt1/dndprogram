package initcheck;

import initcheck.character.GridPanel;

import java.awt.Color;
import java.awt.Graphics;

public class TranslucentGridPanel extends GridPanel {
	 
	Color ppColor = new Color(0, 0, 0, 70); //r,g,b,alpha
	
	public TranslucentGridPanel()
	    {
	        super();
	       
	    }
	    
	 public TranslucentGridPanel(boolean useBorder)
	    {
	        super(useBorder);
	       
	    }
	 
	 
	 public void setColor(Color c){
		 ppColor = c;
	 }
	 
	    public void paintComponent(Graphics g)
	    {
	        super.paintComponent(g);
	       
	        g.setColor(ppColor);
	        g.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);//(0,0,getWidth(), getHeight()); //x,y,width,height
	    }    
}

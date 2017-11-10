package initcheck;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class TranslucentPanel extends JPanel{

	    public TranslucentPanel()
	    {
	        super();
	       
	    }
	    
	    public TranslucentPanel(LayoutManager l)
	    {
	        super(l);
	       
	    }
	    
	    
	    public void paintComponent(Graphics g)
	    {
	        super.paintComponent(g);
	        Color ppColor = new Color(50, 50, 50, 70); //r,g,b,alpha
	        g.setColor(ppColor);
	        g.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);//(0,0,getWidth(), getHeight()); //x,y,width,height
	    }    
}

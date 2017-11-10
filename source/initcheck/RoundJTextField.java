package initcheck;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JTextField;
import javax.swing.border.Border;

public class RoundJTextField extends JTextField {
    
	private static final long serialVersionUID = 1L;
	private Shape shape;
    static  Border roundedBorder = new RoundedCornerBorder(); 
    
    public RoundJTextField(int size) {
        super(size);
        setOpaque(false); // As suggested by @AVD in comment.
        
        setBorder(roundedBorder);
        setPreferredSize(new Dimension(getWidth(), 20));
        setBackground(new Color(0,0,0,0));
    }
    
    protected void paintComponent(Graphics g) {
    	int r = getHeight()-1;
        g.setColor(getBackground());
        g.setColor(Color.WHITE);
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, r,r);
        super.paintComponent(g);
    }
  
    public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
         }
         return shape.contains(x, y);
    }
}

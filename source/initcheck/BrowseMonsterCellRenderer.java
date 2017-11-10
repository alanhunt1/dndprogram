package initcheck;

import initcheck.database.Monster;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class BrowseMonsterCellRenderer extends JLabel implements ListCellRenderer {

		static final long serialVersionUID = 1;
		
		public BrowseMonsterCellRenderer () {
       // Don't paint behind the component
				setOpaque(true);
		}

     
		// This is the only method defined by ListCellRenderer.
		// We just reconfigure the JLabel each time we're called.

		public Component getListCellRendererComponent(
			 JList list,
			 Object value,            // value to display
       int index,               // cell index
       boolean isSelected,      // is the cell selected
       boolean cellHasFocus)    // the list and the cell have the focus
     {
         String s = ((Monster)value).getListFormat();
         setText(s);
         
				 if (isSelected) {
						 setBackground(InitColor.red);
						 setForeground(Color.white);
				 }
         else {
						 setBackground(list.getBackground());
						 setForeground(list.getForeground());
				 }
				 setFont(list.getFont());
         return this;
     }
}

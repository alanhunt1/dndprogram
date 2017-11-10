package initcheck.utils;


	import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
	 
	 
	public class XTabbedPane extends JTabbedPane
	{
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public static void main(String[] args)
	    {
	        JFrame f = new JFrame("XTabbedPane Test");
	        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 
	        XTabbedPane tabs = new XTabbedPane();
	        tabs.addTab("First Tab", new JTextArea());
	        tabs.addTab("Tab 2", new JTextArea());
	        tabs.addTab("Tab Three", new JTextArea());
	 
	        f.getContentPane().add(tabs);
	 
	        f.setSize(400, 300);
	        f.setVisible(true);
	    }
	 
	    public XTabbedPane()
	    {
	        this(TOP);
	    }
	 
	    public XTabbedPane(int tabPlacement)
	    {
	        this(tabPlacement, WRAP_TAB_LAYOUT);
	    }
	 
	    public XTabbedPane(int tabPlacement, int tabLayoutPolicy)
	    {
	        super(tabPlacement, tabLayoutPolicy);
	 
	        mouseHandler = new MouseHandler();
	        addMouseListener(mouseHandler);
	        addMouseMotionListener(mouseHandler);
	    }
	 
	    private MouseInputListener mouseHandler;
	    private Cursor defaultCursor, handCursor;
	 
	    private void dragTab(int dragIndex, int tabIndex)
	    {
	        String title = getTitleAt(dragIndex);
	        Icon icon = getIconAt(dragIndex);
	        Component component = getComponentAt(dragIndex);
	        String toolTipText = getToolTipTextAt(dragIndex);
	 
	        Color background = getBackgroundAt(dragIndex);
	        Color foreground = getForegroundAt(dragIndex);
	        Icon disabledIcon = getDisabledIconAt(dragIndex);
	        int mnemonic = getMnemonicAt(dragIndex);
	        int displayedMnemonicIndex = getDisplayedMnemonicIndexAt(dragIndex);
	        boolean enabled = isEnabledAt(dragIndex);
	 
	        remove(dragIndex);
	        insertTab(title, icon, component, toolTipText, tabIndex);
	 
	        setBackgroundAt(tabIndex, background);
	        setForegroundAt(tabIndex, foreground);
	        setDisabledIconAt(tabIndex, disabledIcon);
	        setMnemonicAt(tabIndex, mnemonic);
	        setDisplayedMnemonicIndexAt(tabIndex, displayedMnemonicIndex);
	        setEnabledAt(tabIndex, enabled);
	    }
	 
	    private Cursor getDefaultCursor()
	    {
	        if (defaultCursor == null)
	        {
	            defaultCursor = Cursor.getDefaultCursor();
	        }
	 
	        return defaultCursor;
	    }
	 
	    private Cursor getHandCursor()
	    {
	        if (handCursor == null)
	        {
	            handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	        }
	 
	        return handCursor;
	    }
	 
	    private int getTabIndex(int x, int y)
	    {
	        return getUI().tabForCoordinate(this, x, y);
	    }
	 
	    private void maybeSetDefaultCursor()
	    {
	        Cursor cursor = getDefaultCursor();
	 
	        if (getCursor() != cursor)
	        {
	            setCursor(cursor);
	        }
	    }
	 
	    private void maybeSetHandCursor()
	    {
	        Cursor cursor = getHandCursor();
	 
	        if (getCursor() != cursor)
	        {
	            setCursor(cursor);
	        }
	    }
	 
	    class MouseHandler extends MouseInputAdapter {
	 
	        public void mouseDragged(MouseEvent e) {
	            if (dragIndex != -1) {
	                if (getTabIndex(e.getX(), e.getY()) != -1) {
	                    maybeSetHandCursor();
	                } else {
	                    maybeSetDefaultCursor();
	                }
	            }
	        }
	 
	        public void mousePressed(MouseEvent e) {
	            if (!e.isPopupTrigger() && e.getButton() == MouseEvent.BUTTON1) {
	                int tabIndex = getTabIndex(e.getX(), e.getY());
	                if (tabIndex != -1) {
	                    dragIndex = tabIndex;
	                }
	            }
	        }
	 
	        public void mouseReleased(MouseEvent e) {
	            if (!e.isPopupTrigger() && e.getButton() == MouseEvent.BUTTON1) {
	                if (dragIndex != -1) {
	                    int tabIndex = getTabIndex(e.getX(), e.getY());
	                    if (tabIndex != -1 && tabIndex != dragIndex) {
	                        dragTab(dragIndex, tabIndex);
	                        setSelectedIndex(tabIndex);
	                    }
	                    dragIndex = -1;
	                    maybeSetDefaultCursor();
	                }
	            }
	        }
	 
	        private int dragIndex = -1;
	    }
	}



package initcheck;

import java.awt.Event;
import java.awt.event.KeyEvent;
import java.io.Serializable;

import javax.swing.InputMap;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.StyledEditorKit;

public class InitTextEditorPane extends JTextPane implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public InitTextEditorPane(){
		addBindings();
	}
	
    protected void addBindings() {
        InputMap inputMap = getInputMap();

        //Ctrl-b for bold
        KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_B, Event.CTRL_MASK);
        inputMap.put(key, new StyledEditorKit.BoldAction());

        //Ctrl-i for italic
        key = KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.CTRL_MASK);
        inputMap.put(key, new StyledEditorKit.ItalicAction());
    }
	
}

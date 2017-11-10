package initcheck.io;

import initcheck.DCharacter;
import initcheck.InitLogger;
import initcheck.PlayerManager;

import java.util.Vector;

import javax.swing.ImageIcon;

public class ManagerMessageSender {

	private PlayerManager client = null;
	private InitLogger logger = new InitLogger(this);
	
	public ManagerMessageSender(PlayerManager client) {
		
		this.client = client;

	}


	private boolean error = false;
	
	private Vector<Object> args;
	
	private String command;

	private void sendServer(InitPacket s) {
		try {
			
			client.gl.addMesg(s);
			
		} catch (Exception e) {
			logger.log(e.toString());
			error = true;
		}
	}
	public void sendCharacter(DCharacter d){
		
		if (d.getIconObj() == null){
			d.setIconObj(new ImageIcon(d.getIcon()));
		}
		command = InitCommandConstants.CHARUPDATE;
		args = new Vector<Object>();
		args.add(d);
		
		sendServer(new InitPacket(command, args));		
	}
	public void sendTest(){
		
		
		command = InitCommandConstants.TEST;
		args = new Vector<Object>();
		
		
		sendServer(new InitPacket(command, args));		
	}
	public void sendImage(String filename){
		ImageIcon display = new ImageIcon(filename);
		command = InitCommandConstants.NEWIMAGE;
		args = new Vector<Object>();
		args.add(display);
		args.add(filename);
		sendServer(new InitPacket(command, args));	
	}
	
	public void sendServerUpdates(DCharacter d){
		
		command = InitCommandConstants.SERVER_CHAR_UPDATE;
		args = new Vector<Object>();
		args.add(""+d.getAC());
		args.add(""+d.getHP());
		args.add(d.getName());
		
		sendServer(new InitPacket(command, args));		
		
	}
	
}

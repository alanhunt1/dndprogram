package initcheck.io;

import java.util.ArrayList;
import java.util.Hashtable;

import initcheck.DCharacter;
import initcheck.InitLogger;
import initcheck.PlayerManager;

import javax.swing.ImageIcon;

public class ManagerMessageProcessor  extends MessageProcessor {

	private PlayerManager client;
	private InitLogger logger = new InitLogger(this);
	
	public ManagerMessageProcessor(PlayerManager client){
		this.client = client;
	}
	
	@SuppressWarnings("unchecked")
	public void processMessage(InitPacket ip){
		logger.log("RECEIVED COMMAND "+ip.getCommand());
		System.out.println("RECEIVED COMMAND "+ip.getCommand());
		if (ip.getCommand().equals(InitCommandConstants.NEWIMAGE)) {
			
			client.processImage((ImageIcon) ip.getArguments().get(0), (String) ip.getArguments().get(1));
			
		}else if (ip.getCommand().equals(InitCommandConstants.CHARUPDATE)) {
			
			client.processCharUpdate((DCharacter) ip.getArguments().get(0));
			
		}else if (ip.getCommand().equals(InitCommandConstants.XPUPDATE)) {
			
			client.processXpUpdate((ArrayList<String>)ip.getArguments().get(0), 
									(Hashtable<String, String>)ip.getArguments().get(1), 
									(String)ip.getArguments().get(2));
		}else if (ip.getCommand().equals(InitCommandConstants.TEST)) {
			
			client.processTest();
			
		}else{
			// ignore the command, because it is not meant for us.
		}
	}
}

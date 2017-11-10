package initcheck.io;

import initcheck.InitClient;
import initcheck.InitLogger;
import initcheck.status.StatusItem;

import java.net.Socket;
import java.util.Vector;

public class ClientMessageSender {
	
	private InitClient client = null;
	private InitLogger logger = new InitLogger(this);
	
	public ClientMessageSender(InitClient client) {
		
		this.client = client;

	}


	private boolean error = false;
	
	private Vector<Object> args;
	
	private String command;

	

	public void sendStatus(String name, StatusItem status){
		command = InitCommandConstants.STATUS;
		args = new Vector<Object>();
		args.add(name);
		args.add(status);
		sendServer(new InitPacket(command, args));		
	}
	
	public void sendPlayerNotes(int x, int y, int z, String s) {
		command = InitCommandConstants.NOTES;
		args = new Vector<Object>();
		args.add(""+x);
		args.add(""+y);
		args.add(""+z);
		args.add(s);
		sendServer(new InitPacket(command, args));		
		
	}

	
	
	private void sendServer(InitPacket s) {
		try {
			
			client.gl.addMesg(s);
			
			//client.startTraffic("Sending Server Update");
			//if (out != null) {
			//	out.reset();
			//	logger.log("sending object "+s);
			//	out.writeObject(s);
			//	logger.log("sent");
			//	out.flush();
			//}
			//client.stopTraffic();
		} catch (Exception e) {
			logger.log(e.toString());
			error = true;
		}
	}

	public void sendMessage(String s, String id) {
		command = InitCommandConstants.MESSAGE;
		args = new Vector<Object>();
		args.add(id);
		args.add(s);
		sendServer(new InitPacket(command, args));		
	}

	public void updateFromServer() {
		command = InitCommandConstants.UPDATE;
		args = new Vector<Object>();
		sendServer(new InitPacket(command, args));		
	}

	public void sendHello() {
		
		command = InitCommandConstants.HELLO;
		args = new Vector<Object>();
		logger.log("SENT HELLO FROM "+client.getId());
		args.add(client.getId());
		sendServer(new InitPacket(command, args));		
		
	}

	public void sendGoodbye(){
		command = InitCommandConstants.GOODBYE;
		args = new Vector<Object>();
		args.add(client.getId());
		sendServer(new InitPacket(command, args));		
		
	}
	
	public void sendStun(String name) {
		command = InitCommandConstants.STUN;
		args = new Vector<Object>();
		args.add(name);
		sendServer(new InitPacket(command, args));		
		
	}

	public void sendUnStun(String name) {
		command = InitCommandConstants.UNSTUN;
		args = new Vector<Object>();
		args.add(name);
		sendServer(new InitPacket(command, args));	
	
	}

	public void sendKill(String name) {
		command = InitCommandConstants.KILL;
		args = new Vector<Object>();
		args.add(name);
		sendServer(new InitPacket(command, args));	
		
	}

	public void sendWhine(String topic) {
		command = InitCommandConstants.WHINE;
		args = new Vector<Object>();
		args.add(topic);
		sendServer(new InitPacket(command, args));	
		
	}

	public void sendRevive(String name) {
		command = InitCommandConstants.REVIVE;
		args = new Vector<Object>();
		args.add(name);
		sendServer(new InitPacket(command, args));	
		
	}

	

	public void sendDamageUpdate(String name, int dmg) {
		command = InitCommandConstants.DAMAGE;
		args = new Vector<Object>();
		args.add(name);
		args.add(""+dmg);
		sendServer(new InitPacket(command, args));	
		
	}

	public void sendHealUpdate(String name, int dmg) {
		command = InitCommandConstants.HEAL;
		args = new Vector<Object>();
		args.add(name);
		args.add(""+dmg);
		sendServer(new InitPacket(command, args));
	
	}

	public void sendStatMod(String name, String stat, int mod) {
		command = InitCommandConstants.STATMOD;
		args = new Vector<Object>();
		args.add(name);
		args.add(stat);
		args.add(""+mod);
		sendServer(new InitPacket(command, args));	
		
	}

}

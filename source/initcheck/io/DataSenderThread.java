package initcheck.io;

import initcheck.InitClient;
import initcheck.InitLogger;
import initcheck.status.StatusItem;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

/**
 * The Data Sender thread is used by the client to send information to the
 * server.
 * 
 * @author <a href="mailto:hunt_a@machine.domain">Alan M. Hunt</a>
 * @version 1.0
 */
public class DataSenderThread extends Thread {

	private Socket socket = null;

	private InitClient client = null;

	private ObjectOutputStream out = null;

	private InitLogger logger = new InitLogger(this);

	private boolean run = true;

	private boolean error = false;
	
	private Vector<Object> args;
	
	private String command;
	
	//MinaClient mc = null;
	
	public DataSenderThread(InitClient client, Socket socket) {
		super("DataSenderThread");
		this.client = client;
		//this.socket = socket;
	}

	public void run() {
		logger.log("Starting Sender Thread");

		try {
			
			//mc = new MinaClient(client);
			
			//out = new ObjectOutputStream(socket.getOutputStream());
			while (run) {
				sleep(10);
				
			}
		} catch (Exception e) {
			logger.log(e.toString());
		}
	}

	public void shutdown() {
		try {
			if (out != null) {
				out.flush();
				out.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (Exception e) {
			logger.log("error", "Error closing socket in DST " + e);
		}
		run = false;
	}

	public void sendStatus(String name, StatusItem status){
		command = ("STATUS");
		args = new Vector<Object>();
		args.add(name);
		args.add(status);
		sendServer(new InitPacket(command, args));		
	}
	
	public void sendPlayerNotes(int x, int y, int z, String s) {
		command = ("NOTES");
		args = new Vector<Object>();
		args.add(""+x);
		args.add(""+y);
		args.add(""+z);
		args.add(s);
		sendServer(new InitPacket(command, args));		
		
	}

	public boolean sendTest(){
		error = false;
		command = ("TEST");
		args = new Vector<Object>();
		sendServer(new InitPacket(command, args));		
		return error;
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
		command = ("MESSAGE");
		args = new Vector<Object>();
		args.add(id);
		args.add(s);
		sendServer(new InitPacket(command, args));		
	}

	public void updateFromServer() {
		command = ("UPDATE");
		args = new Vector<Object>();
		sendServer(new InitPacket(command, args));		
	}

	public void sendHello() {
		
		command = ("HELLO");
		args = new Vector<Object>();
		logger.log("SENT HELLO FROM "+client.getId());
		args.add(client.getId());
		sendServer(new InitPacket(command, args));		
		
	}

	public void sendGoodbye(){
		command = ("GOODBYE");
		args = new Vector<Object>();
		sendServer(new InitPacket(command, args));		
		
	}
	
	public void sendStun(String name) {
		command = ("STUN");
		args = new Vector<Object>();
		args.add(name);
		sendServer(new InitPacket(command, args));		
		
	}

	public void sendUnStun(String name) {
		command = ("UNSTUN");
		args = new Vector<Object>();
		args.add(name);
		sendServer(new InitPacket(command, args));	
	
	}

	public void sendKill(String name) {
		command = ("KILL");
		args = new Vector<Object>();
		args.add(name);
		sendServer(new InitPacket(command, args));	
		
	}

	public void sendWhine(String topic) {
		command = ("WHINE");
		args = new Vector<Object>();
		args.add(topic);
		sendServer(new InitPacket(command, args));	
		
	}

	public void sendRevive(String name) {
		command = ("REVIVE");
		args = new Vector<Object>();
		args.add(name);
		sendServer(new InitPacket(command, args));	
		
	}

	public void sendLevelUp(String name) {
		command = ("LEVELUP");
		args = new Vector<Object>();
		args.add(name);
		sendServer(new InitPacket(command, args));	
	
	}

	public void sendDamageUpdate(String name, int dmg) {
		command = ("DAMAGE");
		args = new Vector<Object>();
		args.add(name);
		args.add(""+dmg);
		sendServer(new InitPacket(command, args));	
		
	}

	public void sendHealUpdate(String name, int dmg) {
		command = ("HEAL");
		args = new Vector<Object>();
		args.add(name);
		args.add(""+dmg);
		sendServer(new InitPacket(command, args));
	
	}

	public void sendStatMod(String name, String stat, int mod) {
		command = ("STATMOD");
		args = new Vector<Object>();
		args.add(name);
		args.add(stat);
		args.add(""+mod);
		sendServer(new InitPacket(command, args));	
		
	}

}

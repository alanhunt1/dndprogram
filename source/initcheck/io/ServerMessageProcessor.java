package initcheck.io;

import initcheck.InitServer;
import initcheck.status.StatusItem;

/**
 * 
 * @author 488365
 * This is the class that the server uses to process messages from the client. 
 */
public class ServerMessageProcessor extends MessageProcessor {
	
	private InitServer server;
	
	public ServerMessageProcessor(InitServer server){
		this.server = server;
	}
	
	public void processMessage(InitPacket fromClient){
		
		String command = fromClient.getCommand();
	
		
		if (command.equals(InitCommandConstants.NOTES)) {
			int x = Integer.parseInt((String) fromClient.getArguments().get(0));
			int y = Integer.parseInt((String) fromClient.getArguments().get(1));
			int z = Integer.parseInt((String) fromClient.getArguments().get(2));
			String msg = (String) fromClient.getArguments().get(3);
			server.setPlayerNotes(x, y, z, msg);
		} else if (command.equals(InitCommandConstants.UPDATE)) {

			server.sendClientRefresh();
		} else if (command.equals(InitCommandConstants.MESSAGE)) {
			String id = (String) fromClient.getArguments().get(0);
			String msg = (String) fromClient.getArguments().get(1);
			server.showChat(msg, id);
		} else if (command.equals(InitCommandConstants.HELLO)) {
			String id = (String) fromClient.getArguments().get(0);
			server.addClient(id);

		} else if (command.equals(InitCommandConstants.GOODBYE)) {
			String id = (String) fromClient.getArguments().get(0);
			server.removeClient(id);

		} else if (command.equals(InitCommandConstants.STUN)) {

			String name = (String) fromClient.getArguments().get(0);
			server.stunPlayer(name, false);
		} else if (command.equals(InitCommandConstants.UNSTUN)) {

			String name = (String) fromClient.getArguments().get(0);
			server.unstunPlayer(name);
		} else if (command.equals(InitCommandConstants.KILL)) {

			String name = (String) fromClient.getArguments().get(0);
			server.killPlayer(name);
		} else if (command.equals(InitCommandConstants.REVIVE)) {

			String name = (String) fromClient.getArguments().get(0);
			server.revivePlayer(name);
		}  else if (command.equals(InitCommandConstants.DAMAGE)) {
			String name = (String) fromClient.getArguments().get(0);
			String dmg = (String) fromClient.getArguments().get(1);
			server.damagePlayer(name, dmg);
		} else if (command.equals(InitCommandConstants.HEAL)) {
			String name = (String) fromClient.getArguments().get(0);
			String dmg = (String) fromClient.getArguments().get(1);
			server.healPlayer(name, dmg);
		} else if (command.equals(InitCommandConstants.WHINE)) {
			String whine = (String) fromClient.getArguments().get(0);
			server.processWhine(whine);
		} else if (command.equals(InitCommandConstants.STATMOD)) {
			String name = (String) fromClient.getArguments().get(0);
			String stat = (String) fromClient.getArguments().get(1);
			String mod = (String) fromClient.getArguments().get(2);
			server.modStat(name, stat, mod);
		} else if (command.equals(InitCommandConstants.STATUS)) {
			String name = (String) fromClient.getArguments().get(0);
			StatusItem status = (StatusItem) fromClient.getArguments().get(1);
			server.setPlayerStatus(name, status);

		}else if (command.equals(InitCommandConstants.SERVER_CHAR_UPDATE)){
			
			String ac = (String) fromClient.getArguments().get(0);
			String hp = (String) fromClient.getArguments().get(1);
			String name = (String) fromClient.getArguments().get(2);
			
			server.updatePlayer(ac,hp,name);
		}
	}

}

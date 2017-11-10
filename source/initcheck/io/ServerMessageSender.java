package initcheck.io;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import initcheck.InitLogger;
import initcheck.InitServer;
import initcheck.Participant;
import initcheck.database.BattleHistory;
import initcheck.database.DamageRecord;
import initcheck.database.MonsterKills;
import initcheck.database.PlayerBattles;
import initcheck.database.PlayerKills;
import initcheck.dungeon.Dungeon;
import initcheck.status.StatusItem;

public class ServerMessageSender {
	private InitServer server = null;
	private boolean active = true;
	private Vector<InitPacket> queuedRequests = new Vector<InitPacket>();
	private String command;
	private InitLogger logger = new InitLogger(this);
	private Vector<Object> args;
	
	public ServerMessageSender(InitServer s){
		this.server = s;
	}
	
	public void toggleAccess() {
		active = !active;
		if (active) {
			for (int i = 0; i < queuedRequests.size(); i++) {
				sendClients(queuedRequests.get(i));
			}
			queuedRequests = new Vector<InitPacket>();
		}
	}

	public void sendMonsterPicture(String picture, String name, String id) {
		command = InitCommandConstants.SERVER_PICTURE;
		args = new Vector<Object>();
		args.add((Object)picture);
		args.add(name);
		args.add(id);
		if (!active) {
			queuedRequests.add(new InitPacket(command, args));			
		} else {	
			sendClients(new InitPacket(command, args));
		}
	}
	
	public void sendXpList(ArrayList<String>s, Hashtable<String, String>bonusHash, String baseXPperPlayer){
		command = InitCommandConstants.XPUPDATE;
		args = new Vector<Object>();
		args.add(s);
		args.add(bonusHash);
		args.add(baseXPperPlayer);
		sendClients(new InitPacket(command, args));
	}

	public void advancePosition(int i) {
		command = InitCommandConstants.ADVANCE;
		args = new Vector<Object>();
		args.add(""+i);
		sendClients(new InitPacket(command, args));
	}

	public void sendPing() {
		command = InitCommandConstants.PING;
		args = new Vector<Object>();
		sendClients(new InitPacket(command, args));
	}

	public void updatePosition(int i) {
		command = InitCommandConstants.POS;
		args = new Vector<Object>();
		args.add(""+i);
		sendClients(new InitPacket(command, args));	
	}

	public void sendCokeRequest() {
		command = InitCommandConstants.COKE;
		args = new Vector<Object>();
		sendClients(new InitPacket(command, args));
		
	}

	public void sendSnackRequest() {
		command = InitCommandConstants.SNACK;
		args = new Vector<Object>();
		sendClients(new InitPacket(command, args));
	}

	public void updateRound(int i) {
		command = InitCommandConstants.ROUND;
		args = new Vector<Object>();
		args.add(""+i);
		sendClients(new InitPacket(command, args));
	}

	public void updatePlayerMod(String s) {
		command = InitCommandConstants.PLAYERMOD;
		args = new Vector<Object>();
		args.add(s);
		sendClients(new InitPacket(command, args));
	}

	public void updateMonsterMod(String s) {
		command = InitCommandConstants.MONSTERMOD;
		args = new Vector<Object>();
		args.add(s);
		sendClients(new InitPacket(command, args));
	}

	public void sendSound(String s) {
		command = InitCommandConstants.SOUND;
		args = new Vector<Object>();
		args.add(s);
		sendClients(new InitPacket(command, args));
	}

	public void sendTime(int i){
		command = InitCommandConstants.TIME;
		args = new Vector<Object>();
		args.add(""+i);
		sendClients(new InitPacket(command, args));
	}
	
	public void sendTotalTime(int i){
		command = InitCommandConstants.TOTALTIME;
		args = new Vector<Object>();
		args.add(""+i);
		sendClients(new InitPacket(command, args));
	}
	
	public void sendMessage(String s) {
		command = InitCommandConstants.MESSAGE;
		args = new Vector<Object>();
		args.add(s);
		sendClients(new InitPacket(command, args));
	}

	public void sendCommand(int[] positions, String cmd) {
		command = InitCommandConstants.COMMAND;
		args = new Vector<Object>();
		args.add(positions);
		args.add(cmd);
		sendClients(new InitPacket(command, args));
	}

	public void sendStatusUpdate(int[] positions, StatusItem status) {
		command = InitCommandConstants.STATUS;
		args = new Vector<Object>();
		args.add(positions);
		args.add(status);
		sendClients(new InitPacket(command, args));
	}

	public void sendData(Vector<Participant> v) {
		if (active) {
			command = InitCommandConstants.DATA;
			args = new Vector<Object>();
			args.add(v);
			sendClients(new InitPacket(command, args));
		}
	}
	
	public void sendDamage(String name, String dmg){
		command = InitCommandConstants.DAMAGE;
		args = new Vector<Object>();
		args.add(name);
		args.add(dmg);
		sendClients(new InitPacket(command, args));
	}

	public void sendHeal(String name, String dmg){
		command = InitCommandConstants.HEAL;
		args = new Vector<Object>();
		args.add(name);
		args.add(dmg);
		sendClients(new InitPacket(command, args));
	}
	
	public void sendCharacters(Vector<Participant> v) {
		if (active) {
			command = InitCommandConstants.CHARACTERS;
			args = new Vector<Object>();
			args.add(v);
			sendClients(new InitPacket(command, args));
		}
	}

	public void sendMap(Dungeon map) {
		command = InitCommandConstants.MAP;
		args = new Vector<Object>();
		args.add(map);
		sendClients(new InitPacket(command, args));
	}

	public void updateMap(int x, int y, int z) {
		command = InitCommandConstants.UPDATEMAP;
		args = new Vector<Object>();
		args.add(""+x);
		args.add(""+y);
		args.add(""+z);
		sendClients(new InitPacket(command, args));
	}

	public void sendPlayerNotes(int x, int y, int z, String s) {
		command = InitCommandConstants.NOTES;
		args = new Vector<Object>();
		args.add(""+x);
		args.add(""+y);
		args.add(""+z);
		args.add(s);
		sendClients(new InitPacket(command, args));		
	}

	public void sendBattleHistory(BattleHistory bh, Vector<PlayerKills> kills,
			Vector<String> players, Vector<MonsterKills> monsterKills, Vector<PlayerBattles> statVector) {
		command = InitCommandConstants.BATTLEHISTORY;
		args = new Vector<Object>();
		args.add(bh);
		args.add(kills);
		args.add(players);
		args.add(monsterKills);
		args.add(statVector);
		sendClients(new InitPacket(command, args));		
	}

	public void sendBattleStats(Vector<String> statMessages) {
		command = InitCommandConstants.BATTLESTATS;
		args = new Vector<Object>();
		args.add(statMessages);
		sendClients(new InitPacket(command, args));
	}

	public void sendNetworkList(Vector<String> list) {
		command = InitCommandConstants.NETWORKLIST;
		args = new Vector<Object>();
		args.add(list);
		sendClients(new InitPacket(command, args));
		
	}

	

	private void sendClients(InitPacket o) {
		logger.log("ADDING CLIENT MESSAGE TO VECTOR");
		server.getClientMessages().add(o);
		server.writeToClients();
		/*
		boolean error = false;
		if (active) {
			
		
			
			logger.log("SENDING DATA TO " + clients.size() + " CLIENTS "+new java.util.Date());
			
			server.startTraffic("Sending Data To Clients - " + o);
			
			for (int i = 0; i < clients.size(); i++) {
				logger.log("QUEUEING REQUEST IN MANAGER");
				if(((ClientTalkerThread)clients.get(i)).isAlive()){
					((ClientTalkerThread)clients.get(i)).add(o);		
				}else{
					clients.remove(i);
					i++;
				}
			}
			
		
			
			server.stopTraffic();
		}
		if (error) {
			sendClients(o);
		}
		logger.log("DONE "+new java.util.Date());
		*/
	}

	public void sendChatMessage(String s, String id) {
		command = InitCommandConstants.CHATMESSAGE;
		args = new Vector<Object>();
		args.add(id);
		args.add(s);
		sendClients(new InitPacket(command, args));
		
	}

	public void sendCurrentGroup(int x) {
		command = InitCommandConstants.CURRENTGROUP;
		args = new Vector<Object>();
		args.add(""+x);
		sendClients(new InitPacket(command, args));
		
	}

	public void sendTabChange(String s) {
		command = InitCommandConstants.TABCHANGE;
		args = new Vector<Object>();
		args.add(s);
		sendClients(new InitPacket(command, args));
		
	}

	public void sendHighScore(DamageRecord dr) {
		command = InitCommandConstants.HIGHSCORE;
		args = new Vector<Object>();
		args.add(dr);
		sendClients(new InitPacket(command, args));
		
	}
	
}

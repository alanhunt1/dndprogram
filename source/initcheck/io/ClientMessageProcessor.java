package initcheck.io;

import java.util.Vector;

import initcheck.InitClient;
import initcheck.InitLogger;
import initcheck.Participant;
import initcheck.database.BattleHistory;
import initcheck.database.DamageRecord;
import initcheck.dungeon.Dungeon;
import initcheck.status.StatusItem;

public class ClientMessageProcessor extends MessageProcessor {

	private InitClient client;
	private InitLogger logger = new InitLogger(this);
	
	public ClientMessageProcessor(InitClient client){
		this.client = client;
	}
	
	
	public void processMessage(InitPacket ip){
		
		if (ip.getCommand().equals(InitCommandConstants.DATA)) {	
			client.processUpdate((Vector) ip.getArguments().get(0));
		} else if (ip.getCommand().equals(InitCommandConstants.CHARACTERS)) {
			client.processChars((Vector) ip.getArguments().get(0));
		} else if (ip.getCommand().equals(InitCommandConstants.STATUS)) {
			int[] positions = (int[]) ip.getArguments().get(0);
			StatusItem status = (StatusItem) ip.getArguments().get(1);
			client.setStatus(positions, status);
		} else if (ip.getCommand().equals(InitCommandConstants.COMMAND)) {
			int[] positions = (int[]) ip.getArguments().get(0);
			String command = (String) ip.getArguments().get(1);
			client.doCommand(positions, command);
		} else if (ip.getCommand().equals(InitCommandConstants.POS)) {
			String position = (String) ip.getArguments().get(0);
			client.setPosition(Integer.parseInt(position));
		} else if (ip.getCommand().equals(InitCommandConstants.ADVANCE)) {
			String position = (String) ip.getArguments().get(0);
			client.advanceList(Integer.parseInt(position));
		} else if (ip.getCommand().equals(InitCommandConstants.COKE)) {
			client.showCokeDialog();
		} else if (ip.getCommand().equals(InitCommandConstants.SNACK)) {
			client.showSnackDialog();
		} else if (ip.getCommand().equals(InitCommandConstants.ROUND)) {
			client.setRoundCounter((String) ip.getArguments().get(0));
		} else if (ip.getCommand().equals(InitCommandConstants.SOUND)) {
		
			client.playSoundClip((String)ip.getArguments().get(0));
		} else if (ip.getCommand().equals(InitCommandConstants.PLAYERMOD)) {
			
			client.setPartyModCounter((String)ip.getArguments().get(0));
		} else if (ip.getCommand().equals(InitCommandConstants.DAMAGE)) {
			
			client.damagePlayer((String)ip.getArguments().get(0),
					(String)ip.getArguments().get(1));
		}else if (ip.getCommand().equals(InitCommandConstants.HEAL)) {
			
			client.healPlayer((String)ip.getArguments().get(0),
					(String)ip.getArguments().get(1));
		}else if (ip.getCommand().equals(InitCommandConstants.MONSTERMOD)) {
			
			client.setMonsterModCounter((String)ip.getArguments().get(0));
		} else if (ip.getCommand().equals(InitCommandConstants.MESSAGE)) {
			
			client.showMessage((String)ip.getArguments().get(0));
		} else if (ip.getCommand().equals(InitCommandConstants.SERVER_PICTURE)) {
			String picture = (String) ip.getArguments().get(0);
			String name = (String) ip.getArguments().get(1);
			String id = (String) ip.getArguments().get(2);
			client.showMonster(picture, name, id);
		} else if (ip.getCommand().equals(InitCommandConstants.MAP)) {

			Dungeon d = (Dungeon) ip.getArguments().get(0);
			if (d == null) {
				logger.log("RECEIVED A NULL MAP");
			}

			client.setMap(d);

		} else if (ip.getCommand().equals(InitCommandConstants.UPDATEMAP)) {
			int x = Integer.parseInt((String) ip.getArguments().get(0));
			int y = Integer.parseInt((String) ip.getArguments().get(1));
			int z = Integer.parseInt((String) ip.getArguments().get(2));
			client.updateMap(x, y, z);
		} else if (ip.getCommand().equals(InitCommandConstants.NOTES)) {

			int x = Integer.parseInt((String) ip.getArguments().get(0));
			int y = Integer.parseInt((String) ip.getArguments().get(1));
			int z = Integer.parseInt((String) ip.getArguments().get(2));
			String msg = (String) ip.getArguments().get(3);
			client.setPlayerNotes(x, y, z, msg);
		} else if (ip.getCommand().equals(InitCommandConstants.CHATMESSAGE)) {
			String id = (String) ip.getArguments().get(0);
			String msg = (String) ip.getArguments().get(1);
			client.showChat(msg, id);
		} else if (ip.getCommand().equals(InitCommandConstants.CURRENTGROUP)) {
			String id = (String) ip.getArguments().get(0);
			client.setCurrentGroup(Integer.parseInt(id));
		} else if (ip.getCommand().equals(InitCommandConstants.TABCHANGE)) {
			String tab = (String) ip.getArguments().get(0);
			client.setTab(tab);
		} else if (ip.getCommand().equals(InitCommandConstants.BATTLEHISTORY)) {
			BattleHistory bh = (BattleHistory) ip.getArguments().get(0);
			Vector kills = (Vector) ip.getArguments().get(1);
			Vector players = (Vector) ip.getArguments().get(2);
			Vector monsterKills = (Vector) ip.getArguments().get(3);
			Vector statVector = (Vector) ip.getArguments().get(4);
			client.receiveHistory(bh, kills, players, monsterKills,
					statVector);
		} else if (ip.getCommand().equals(InitCommandConstants.BATTLESTATS)) {
			Vector statMessages = (Vector) ip.getArguments().get(0);
			client.showBattleStats(statMessages);
		} else if (ip.getCommand().equals(InitCommandConstants.HIGHSCORE)) {
			DamageRecord dr = (DamageRecord) ip.getArguments().get(0);
			client.recordHighScore(dr);
		} else if (ip.getCommand().equals(InitCommandConstants.NETWORKLIST)) {
			Vector list = (Vector) ip.getArguments().get(0);
			client.setNetworkList(list);
		}else if (ip.getCommand().equals(InitCommandConstants.TIME)) {
			int time = Integer.parseInt((String) ip.getArguments().get(0));
			client.updateTime(time);
		}else if (ip.getCommand().equals(InitCommandConstants.TOTALTIME)) {
			int time = Integer.parseInt((String) ip.getArguments().get(0));
			client.updateTotalTime(time);
		}else if (ip.getCommand().equals(InitCommandConstants.STATMOD)) {
			String name = (String) ip.getArguments().get(0);
			String stat = (String) ip.getArguments().get(1);
			String mod = (String) ip.getArguments().get(2);
			client.modPlayerStat(name, stat, Integer.parseInt(mod));
		} else if (ip.getCommand().equals(InitCommandConstants.SERVER_CHAR_UPDATE)){
			
			String ac = (String) ip.getArguments().get(0);
			String hp = (String) ip.getArguments().get(1);
			String name = (String) ip.getArguments().get(2);
		
			client.updatePlayer(ac,hp,name);
		}
		else {
			logger.log("Invalid Command!");
		}
	}
	
}

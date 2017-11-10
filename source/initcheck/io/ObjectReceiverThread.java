package initcheck.io;

import initcheck.InitClient;
import initcheck.InitLogger;
import initcheck.database.BattleHistory;
import initcheck.database.DamageRecord;
import initcheck.database.NetworkLog;
import initcheck.database.NetworkLogDAO;
import initcheck.dungeon.Dungeon;
import initcheck.status.StatusItem;
import initcheck.utils.DateUtil;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Vector;

public class ObjectReceiverThread extends Thread {

	private Socket clientSocket = null;

	private InitClient client = null;

	// private PrintWriter out = null;

	private ObjectInputStream p = null;

	private InitLogger logger = new InitLogger(this);

	private boolean run = true;
	
	NetworkLogDAO nldb = new NetworkLogDAO();
	
	public ObjectReceiverThread(InitClient client, Socket socket) {
		super("DataReceiverThread");
		this.client = client;
		this.clientSocket = socket;
	}

	@SuppressWarnings("unchecked")
	public void run() {
		logger.log("ObjectReceiverThread running");

		try {
			// out = new PrintWriter(clientSocket.getOutputStream(), true);
			logger.log("Establishing object reader");
			p = new ObjectInputStream(clientSocket.getInputStream());
			//p = new ObjectInputStream(new GZIPInputStream(clientSocket.getInputStream()));
			
			logger.log("Established object reader");

			InitPacket fromServer = null;
			// Dungeon d = null;

			while (run) {
				try {
				String startStr = DateUtil.getDate();
				
				fromServer = (InitPacket) p.readObject();
				
				NetworkLog nl = new NetworkLog();
				nl.setOperation("CLIENT : "+fromServer.getCommand());
				nl.setStartTime(startStr);
				
				
				
				long gotLong = new java.util.Date().getTime();
				
				long sendLong = gotLong;
				if (fromServer.getTimeSent() != null){
				 sendLong = Long.parseLong(fromServer.getTimeSent());
				 nl.setSendTime(DateUtil.getDateString(new java.util.Date(sendLong), "dd-MMM-yyyy hh:mm:ss"));
				}
				
				
				nl.setLagTime(""+(gotLong - sendLong));
				int logId = nldb.addOrUpdateNetworkLog(nl);
				nl.setLogId(""+logId);
				long startTime = new java.util.Date().getTime();
				
				client.startTraffic("Receiving " + fromServer + " " + nl.getLagTime());
				logger.log("Processing "+fromServer);
				
					if (fromServer.getCommand().equals("DATA")) {
						client.processUpdate((Vector) fromServer.getArguments().get(0));
					} else if (fromServer.getCommand().equals("CHARACTERS")) {
						client.processChars((Vector) fromServer.getArguments().get(0));
					} else if (fromServer.getCommand().equals("STATUS")) {
						int[] positions = (int[]) fromServer.getArguments().get(0);
						StatusItem status = (StatusItem) fromServer.getArguments().get(1);
						client.setStatus(positions, status);
					} else if (fromServer.getCommand().equals("COMMAND")) {
						int[] positions = (int[]) fromServer.getArguments().get(0);
						String command = (String) fromServer.getArguments().get(1);
						client.doCommand(positions, command);
					} else if (fromServer.getCommand().equals("POS")) {
						String position = (String) fromServer.getArguments().get(0);
						client.setPosition(Integer.parseInt(position));
					} else if (fromServer.getCommand().equals("ADVANCE")) {
						String position = (String) fromServer.getArguments().get(0);
						client.advanceList(Integer.parseInt(position));
					} else if (fromServer.getCommand().equals("COKE")) {
						client.showCokeDialog();
					} else if (fromServer.getCommand().equals("SNACK")) {
						client.showSnackDialog();
					} else if (fromServer.getCommand().equals("ROUND")) {
						client.setRoundCounter((String) fromServer.getArguments().get(0));
					} else if (fromServer.getCommand().equals("SOUND")) {
					
						client.playSoundClip((String)fromServer.getArguments().get(0));
					} else if (fromServer.getCommand().equals("PLAYERMOD")) {
						
						client.setPartyModCounter((String)fromServer.getArguments().get(0));
					} else if (fromServer.getCommand().equals("DAMAGE")) {
						
						client.damagePlayer((String)fromServer.getArguments().get(0),
								(String)fromServer.getArguments().get(1));
					}else if (fromServer.getCommand().equals("HEAL")) {
						
						client.healPlayer((String)fromServer.getArguments().get(0),
								(String)fromServer.getArguments().get(1));
					}else if (fromServer.getCommand().equals("MONSTERMOD")) {
						
						client.setMonsterModCounter((String)fromServer.getArguments().get(0));
					} else if (fromServer.getCommand().equals("MESSAGE")) {
						
						client.showMessage((String)fromServer.getArguments().get(0));
					} else if (fromServer.getCommand().equals("PICTURE")) {
						String picture = (String) fromServer.getArguments().get(0);
						String name = (String) fromServer.getArguments().get(1);
						String id = (String) fromServer.getArguments().get(2);
						client.showMonster(picture, name, id);
					} else if (fromServer.getCommand().equals("MAP")) {

						Dungeon d = (Dungeon) fromServer.getArguments().get(0);
						if (d == null) {
							logger.log("RECEIVED A NULL MAP");
							continue;
						}

						client.setMap(d);

					} else if (fromServer.getCommand().equals("UPDATEMAP")) {
						int x = Integer.parseInt((String) fromServer.getArguments().get(0));
						int y = Integer.parseInt((String) fromServer.getArguments().get(1));
						int z = Integer.parseInt((String) fromServer.getArguments().get(2));
						client.updateMap(x, y, z);
					} else if (fromServer.getCommand().equals("NOTES")) {

						int x = Integer.parseInt((String) fromServer.getArguments().get(0));
						int y = Integer.parseInt((String) fromServer.getArguments().get(1));
						int z = Integer.parseInt((String) fromServer.getArguments().get(2));
						String msg = (String) p.readObject();
						client.setPlayerNotes(x, y, z, msg);
					} else if (fromServer.getCommand().equals("CHATMESSAGE")) {
						String id = (String) fromServer.getArguments().get(0);
						String msg = (String) fromServer.getArguments().get(1);
						client.showChat(msg, id);
					} else if (fromServer.getCommand().equals("CURRENTGROUP")) {
						String id = (String) fromServer.getArguments().get(0);
						client.setCurrentGroup(Integer.parseInt(id));
					} else if (fromServer.getCommand().equals("TABCHANGE")) {
						String tab = (String) fromServer.getArguments().get(0);
						client.setTab(tab);
					} else if (fromServer.getCommand().equals("BATTLEHISTORY")) {
						BattleHistory bh = (BattleHistory) fromServer.getArguments().get(0);
						Vector kills = (Vector) fromServer.getArguments().get(1);
						Vector players = (Vector) fromServer.getArguments().get(2);
						Vector monsterKills = (Vector) fromServer.getArguments().get(3);
						Vector statVector = (Vector) fromServer.getArguments().get(4);
						client.receiveHistory(bh, kills, players, monsterKills,
								statVector);
					} else if (fromServer.getCommand().equals("BATTLESTATS")) {
						Vector statMessages = (Vector) fromServer.getArguments().get(0);
						client.showBattleStats(statMessages);
					} else if (fromServer.getCommand().equals("HIGHSCORE")) {
						DamageRecord dr = (DamageRecord) fromServer.getArguments().get(0);
						client.recordHighScore(dr);
					} else if (fromServer.getCommand().equals("NETWORKLIST")) {
						Vector list = (Vector) fromServer.getArguments().get(0);
						client.setNetworkList(list);
					}else if (fromServer.getCommand().equals("TIME")) {
						int time = Integer.parseInt((String) fromServer.getArguments().get(0));
						client.updateTime(time);
					}else if (fromServer.getCommand().equals("TOTALTIME")) {
						int time = Integer.parseInt((String) fromServer.getArguments().get(0));
						client.updateTotalTime(time);
					}else {
						logger.log("Invalid Command!");
					}
					long endTime = new java.util.Date().getTime();
					long time = endTime - startTime;
					nl.setTaskTime(""+time);
					
					nl.setEndTime(DateUtil.getDate());
					nldb.addOrUpdateNetworkLog(nl);
					client.stopTraffic();
					sleep(100);
				} catch (Exception e) {
					logger.log("Error Processing " + fromServer + " : " + e);
					client.stopTraffic();
				}
			}
		} catch (Exception e) {
			logger.log("Error Processing  : " + e);
			e.printStackTrace();
			client.stopTraffic();
		}

	}

	public void shutdown() {
		run = false;
	}

}

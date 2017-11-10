package initcheck.io;

import initcheck.GroupMessageClient;
import initcheck.InitClient;
import initcheck.InitLogger;
import initcheck.InitServer;
import initcheck.PlayerManager;
import initcheck.utils.cZipFactory;
import initcheck.utils.cZipObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Vector;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

public class GroupListener  extends ReceiverAdapter{
	
	public static final int CLIENT_MODE = 1;
	public static final int SERVER_MODE = 2;
	public static final int ADMIN_MODE = 3;
	
	JChannel channel;
    String user_name=System.getProperty("user.name", "n/a");
    Vector<InitPacket> msgs = new Vector<InitPacket>();
    MessageProcessor mp;
    String cluster = "InitCluster";
    String channelName = "TEST";
    private InitLogger logger = new InitLogger(this);
    
    public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public void addMesg(InitPacket ip){
    	msgs.add(ip);
    	eventLoop();
    }
    
    public GroupListener(int mode, GroupMessageClient pgm){ 	
    	
    	if (mode == CLIENT_MODE){
    		mp = new ClientMessageProcessor((InitClient)pgm);
    	}else if (mode == SERVER_MODE){
    		mp = new ServerMessageProcessor((InitServer)pgm);
    	}else if (mode == ADMIN_MODE){
    		mp = new ManagerMessageProcessor((PlayerManager)pgm);
    	}
    	try{
    		this.start();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    public GroupListener(int mode, GroupMessageClient pgm, String cluster){
    	this.cluster = cluster;
    	
    	if (mode == CLIENT_MODE){
    		mp = new ClientMessageProcessor((InitClient)pgm);
    	}else if (mode == SERVER_MODE){
    		mp = new ServerMessageProcessor((InitServer)pgm);
    	}else if (mode == ADMIN_MODE){
    		mp = new ManagerMessageProcessor((PlayerManager)pgm);
    	}
    	try{
    		this.start();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
	private void start() throws Exception {
		
	    channel=new JChannel("initjgroupsconfig.xml");//"initjgroupsconfig.xml"
	    //if (mode == SERVER_MODE){
	    channel.setDiscardOwnMessages(true);
	    //}
	    channel.setReceiver(this);
	    logger.log("Connecting to cluster "+cluster);
	    channel.connect(cluster);
	    logger.log("CONNECTED");
	    eventLoop();
	    //channel.close();
	    
	}

	private void eventLoop() {

	   // BufferedReader in=new BufferedReader(new InputStreamReader(System.in));

	    //while(true) {

	        try {

	           if (msgs.size() > 0){
	        	
	            //String line=msgs.get(0);
	           
	            
	            //if(line.startsWith("quit") || line.startsWith("exit"))
	             //   break;
	            
	           // line="[" + user_name + "] " + line;
	            
	            InitPacket ip = msgs.get(0);
	            
	            msgs.remove(0);
	            
	            cZipFactory zipFactory = new cZipFactory(java.util.zip.Deflater.BEST_COMPRESSION);     
	            cZipObject zipMessage = zipFactory.Compress(ip);
	            
	            Message msg=new Message(null, null, zipMessage);

	            channel.send(msg);
	            //channel.startFlush(true);
	            System.out.println("MESSAGE SENT");
	           }
	        }

	        catch(Exception e) {
	        	e.printStackTrace();
	        }

	    //}

	}
	
	public void viewAccepted(View new_view) {

	    

	}


	public void receive(Message msg) {

		System.out.println("RECEIVED MESSAGE");
	    
	    // the process command will be handled by the instance of the message processor - this is a 
	    // different class for each of the server programs.
		cZipFactory zipFactory = new cZipFactory(java.util.zip.Deflater.BEST_COMPRESSION);    
		 
		InitPacket ip = (InitPacket)zipFactory.Decompress((cZipObject)msg.getObject());
		//mp.processMessage((InitPacket)msg.getObject());
		mp.processMessage(ip);
	}
}

package initcheck.server;

import initcheck.InitLogger;
import initcheck.InitServer;



public class MemoryManagementThread extends Thread {
		
		InitServer owner = null;
		private InitLogger logger = new InitLogger(this);

		public MemoryManagementThread(InitServer owner){
				this.owner = owner;
				setPriority(MIN_PRIORITY);
		}
		
		public void run() {
				owner.setTotalMemory(totalMemory());
				while (true){
						try{
								owner.setUsedMemory(usedMemory());
								sleep(10000);
								
						}catch (Exception e){
								logger.log(e.toString());
						}
				}
		}

		private long usedMemory ()
    {
        return Runtime.getRuntime().totalMemory () - Runtime.getRuntime().freeMemory ();
    }
    
		private long totalMemory(){
				return Runtime.getRuntime().totalMemory ();	
		}

		
}

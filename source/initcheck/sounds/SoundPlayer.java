package initcheck.sounds;

import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class SoundPlayer {
		
    SoundList soundList;
		String chosenFile;
        
    AudioClip onceClip, loopClip;
    URL codeBase;

		boolean looping = false;

    public SoundPlayer() {
				startLoadingSounds();
		}
		
		void addSound(String s){
				soundList.startLoading(s);
		}
		
		void startLoadingSounds() {
        //Start asynchronous sound loading.
        try {
            codeBase = new URL("file:" + System.getProperty("user.dir") + "/");
        } catch (MalformedURLException e) {
            System.err.println(e.getMessage());
        }
        soundList = new SoundList(codeBase);
				File soundDir = new File("sounds");
				String [] soundFiles = soundDir.list();
				for (int i = 0; i < soundFiles.length; i++){
						String soundFile = soundFiles[i];
		
						if (soundFile.indexOf(".au") != -1){
								
								soundList.startLoading("sounds/"+soundFile);
						}
				}
		}

    public void stop() {
        onceClip.stop();        //Cut short the one-time sound.
        if (looping) {
            loopClip.stop();    //Stop the sound loop.
        }
    }    

    public void start() {
        if (looping) {
            loopClip.loop();    //Restart the sound loop.
        }
    }
    
		public void playClip(String s){
			
				onceClip = null;
				while (onceClip == null){
						onceClip = soundList.getClip(s);
				}
				onceClip.play();
		}

		public void playClip(){
				while (onceClip == null){
						onceClip = soundList.getClip("sounds/homerhacking.au");
				}
				onceClip.play();
		}
		
   

  
}

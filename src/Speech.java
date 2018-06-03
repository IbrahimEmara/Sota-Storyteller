import java.util.ArrayList;

import jp.vstone.RobotLib.*;
import jp.vstone.RobotLib.CPlayWave;
public class Speech extends Thread {
	String pathToFile;
    public Speech(String pathToFile) {
        this.pathToFile=pathToFile;
    }
    
    public void run() {
    	System.out.println("inside run");
    	CPlayWave.PlayWave_wait(pathToFile);
    }
    
}

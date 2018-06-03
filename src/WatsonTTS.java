

import java.awt.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.sound.sampled.UnsupportedAudioFileException;

import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.AudioFormat;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;
import com.ibm.watson.developer_cloud.text_to_speech.v1.util.WaveUtils;

import jp.vstone.RobotLib.CPlayWave;

public class WatsonTTS implements TTS{
	
	  WAV soundHandler = new WAV(); //Use WAV to determine the length of the generated WAV file
	
	  private static final TTS instance = new WatsonTTS();
	    
	    //Private constructor to enforce singleton
	    private WatsonTTS(){}

	    public static TTS getInstance(){
	        return instance;
	    }
	    
	    //Create a file name for the WAV file
	    private String getPath(String response)
	    {
	    	String[] words = response.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
			String currentPath = "";
			for (int index = 0; index < 4; index++) {
				currentPath += words[index];
				currentPath += " ";
			}
			currentPath += ".wav";
			return currentPath;
	    }
		//If requested WAV file has been already created before,
	    // play the old WAV file rather than overwriting the old one
    public int speakIfNecessary(String response) throws UnsupportedAudioFileException, IOException
    {
    	String currentPath=getPath(response);
    	
    	File audioFile = new File(currentPath);
    	//Determine if the file exist in the path
		if (audioFile.exists()) {
			System.out.println("Sound file exists");
		} else {
			System.out.println("Sound file does not exist, creating new one");
			speak(response, currentPath);
		}
		int duration = soundHandler.getLength(audioFile);
		System.out.println("Duration of sound file is " + duration + " s");
		return duration;
    }
	
	public void speak(String text, String pathToFile){
		
		
		// Add username and password below
//		String username = "";
// 		String password = "";
		
	
		TextToSpeech service  = new TextToSpeech();
		service.setUsernameAndPassword(username, password);
		
//		java.util.List<Voice> voices = service.getVoices().execute();
//		System.out.println(voices);
		
		
		try {
//			  String text = "Hello world";
			  InputStream stream = service.synthesize(text, Voice.EN_ALLISON,
			  AudioFormat.WAV).execute();
			  InputStream in = WaveUtils.reWriteWaveHeader(stream);
			  OutputStream out = new FileOutputStream(pathToFile);
			  System.out.println("successfully till now!");
			  byte[] buffer = new byte[1024];
			  int length =0;
			  while ((length = in.read(buffer)) > 0) {
			    out.write(buffer, 0, length);
			  }
			  out.close();
			  in.close();
			  stream.close();
			}
			catch (Exception e) {
			  e.printStackTrace();
			}
		
		
		

}
	
	@Override
	public void speakSota(String response)
	{
		String currentPath = getPath(response);
		new Speech(currentPath).start();
	}
}

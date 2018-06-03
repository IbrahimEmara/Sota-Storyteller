import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

public interface SpeechToText {

	String convertToText(String filename, int currentBranch) throws IOException;

	int convertAndSend(String filename, int currentBranch) throws IOException, InterruptedException, UnsupportedAudioFileException;

	int sendToWatson(String string) throws UnsupportedAudioFileException, IOException;

}
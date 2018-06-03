import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

public interface ProcessingEngine {

	int processInput(String query) throws UnsupportedAudioFileException, IOException;

	String returnResponse(String convertToText);

}
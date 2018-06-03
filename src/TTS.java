import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

public interface TTS {

	void speakSota(String pathToFile);

	void speak(String response, String pathToFile);

	int speakIfNecessary(String response) throws UnsupportedAudioFileException, IOException;

}
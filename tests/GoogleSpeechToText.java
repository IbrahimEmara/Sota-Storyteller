import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.sound.sampled.UnsupportedAudioFileException;

import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.protobuf.ByteString;

public class GoogleSpeechToText implements SpeechToText {

	private static final SpeechToText instance = new GoogleSpeechToText();
	Database mySQL = new Database();
	ProcessingEngine Ox = new OxfordDictionary();
	ProcessingEngine watsonConversation = WatsonConversation.getInstance();
	
	private void addToDatabase(String response, int currentBranch) throws Exception {
		mySQL.InsertResponse(currentBranch, response);
		mySQL.getResponseForBranch(currentBranch);
	}
	
	// Singleton design pattern enforced
	private GoogleSpeechToText() {
	}

	public static SpeechToText getInstance() {
		return instance;
	}
	
	private int process(String user_response) throws InterruptedException, UnsupportedAudioFileException, IOException {
		String[] words = user_response.split(" ");
		int wordIndex = 0;
		int index = 0;
		boolean found = false;
		
		//Determine if asking for a word definition
		while (index < words.length && !found) {

			if ((words[index].equalsIgnoreCase("define"))) {
				wordIndex = index + 1;
				found = true;
			} else if ((words[index].equalsIgnoreCase("meaning") && words[index + 1].equalsIgnoreCase("of"))
					&& !words[index + 2].isEmpty()) {
				wordIndex = index + 2;
				found = true;
			} else if (words[index].equalsIgnoreCase("mean")
					|| words[index].equalsIgnoreCase("means") && !words[index - 1].isEmpty()) {
				wordIndex = index - 1;
				found = true;
			}
			index++;

		}
		if (found) {
			return sendToDictionary(words[wordIndex]);
		} else {
			return sendToWatson(user_response);
		}
	}
	
	private int sendToDictionary(String input) throws UnsupportedAudioFileException, IOException
	{
		return Ox.processInput(input);
	}
	
	public int sendToWatson(String input) throws UnsupportedAudioFileException, IOException
	{
		return watsonConversation.processInput(input);
	}

	@Override
	public String convertToText(String fileName, int currentBranch) throws IOException {
		String words = "";
		// Instantiates a client
		SpeechClient speech = SpeechClient.create();

		// The path to the audio file to transcribe
		// String fileName = "./audio.raw";

		// Reads the audio file into memory
		Path path = Paths.get(fileName);
		byte[] data = Files.readAllBytes(path);
		ByteString audioBytes = ByteString.copyFrom(data);

		// Builds the sync recognize request
		RecognitionConfig config = RecognitionConfig.newBuilder().setEncoding(AudioEncoding.LINEAR16)
				// .setSampleRateHertz(16000)
				.setSampleRateHertz(22050).setLanguageCode("en-US").build();
		RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(audioBytes).build();

		// Performs speech recognition on the audio file
		RecognizeResponse response = speech.recognize(config, audio);
		List<SpeechRecognitionResult> results = response.getResultsList();

		for (SpeechRecognitionResult result : results) {
			SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
			System.out.printf("Transcription: %s%n", alternative.getTranscript());
			words = alternative.getTranscript();
		}
		try {
			speech.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			addToDatabase(words, currentBranch);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return words;
	}
	
	public int convertAndSend(String fileName, int currentBranch) throws IOException, InterruptedException, UnsupportedAudioFileException
	{
		String input = convertToText(fileName, currentBranch);
		return process(input);
		
	}

}

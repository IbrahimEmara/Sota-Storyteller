import com.ibm.watson.developer_cloud.conversation.v1.*;

import com.ibm.watson.developer_cloud.conversation.v1.model.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import javax.sound.sampled.UnsupportedAudioFileException;

import com.google.gson.reflect.TypeToken;
import com.ibm.watson.developer_cloud.service.model.DynamicModel;
import com.ibm.watson.developer_cloud.util.GsonSerializationHelper;

public class WatsonConversation implements ProcessingEngine {
	Conversation service;
	//Add workspaceId below
	//String workspaceId = "";
	Context currentContext = new Context();
	TTS watsonTTS = WatsonTTS.getInstance();

	private static final ProcessingEngine instance = new WatsonConversation();

	public static ProcessingEngine getInstance() {
		return instance;
	}

	private WatsonConversation() {
		service = new Conversation(Conversation.VERSION_DATE_2017_05_26);
		//String username="";
		//String password="";
		//Add username and password below
		//service.setUsernameAndPassword(username , password);
	}

	public void startNewConversation() {
		currentContext = new Context();
	}

	@Override
	public int processInput(String query) throws UnsupportedAudioFileException, IOException {

		String watsonResponse = returnResponse(query);

		return respondToUser(watsonResponse);

	}

	public String returnResponse(String query) throws RuntimeException {
		InputData input = new InputData.Builder(query).build();

		MessageOptions options = new MessageOptions.Builder(workspaceId).input(input).context(currentContext).build();

		MessageResponse response = service.message(options).execute();

		if (response.getIntents().size() > 0)
			currentContext = response.getContext();

		System.out.println(response);

		String watsonResponse = response.getOutput().getText().get(0);
		return watsonResponse;
	}
	
	public int respondToUser(String response) throws UnsupportedAudioFileException, IOException
	{
		int duration = watsonTTS.speakIfNecessary(response);
		watsonTTS.speakSota(response);
		return duration;
	}

}

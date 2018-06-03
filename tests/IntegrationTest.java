import static org.junit.Assert.*;

import java.io.IOException;

import org.json.JSONArray;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class IntegrationTest {
	
	@Test
	public void testForIntegration() throws Exception
	{
		Database mysqldb = new Database();
		final int branch =1;
		final String pathToFile = "output.wav";
		TTS watsonTTS = WatsonTTS.getInstance();
		SpeechToText googleSpeech = GoogleSpeechToText.getInstance();
		ProcessingEngine conversation = WatsonConversation.getInstance();
		String response = "Jack and the Beanstalk please!";
		String expected_output="Once upon a time, there lived Jack, on his farm in the country. One day, while going for walk, Jack notices a new farm. If you want to hear more about Jack, shout out \"Jack!\" If you want to start the adventure, shout out \"Adventure!\"";
		watsonTTS.speak(response,pathToFile);
		String result = conversation.returnResponse(googleSpeech.convertToText(pathToFile,1));
		
		boolean insert = mysqldb.InsertResponse(branch, response);

		JSONArray array = mysqldb.getJSonByBranch(branch);
		
		int expected_branch  = array.getJSONObject(array.length() -1).getInt("NoOfBranch");
		String expected_response = array.getJSONObject(array.length() -1).getString("Response");
		
		assertTrue(expected_output.equals(result));
		assertEquals(branch,expected_branch);
		assertEquals(insert,true);
		assertEquals(response,expected_response);
	}

}

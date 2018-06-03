import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class GoogleSpeechTest {
	SpeechToText googleSpeech= GoogleSpeechToText.getInstance();

	@Test
	public final void testToSeeIfGoogleSpeechRecognisesFemaleAmericanVoice() throws IOException {
		String expectedString="hello there this is the first test for Google speech to check if speech-to-text is fine";
		String returnedString=googleSpeech.convertToText("./sound/FirstTest.wav", 1);
		assertTrue(expectedString.equals(returnedString));
		
		
	}
	
	@Test
	public final void testToSeeIfGoogleSpeechRecognisesMaleAmericanVoice() throws IOException {
		String expectedString="good morning this is a further test to check Google speech";
		String returnedString=googleSpeech.convertToText("./sound/SecondTest.wav",1 );
		assertTrue(expectedString.equals(returnedString));
		
	}
	
	@Test
	public final void testToSeeIfGoogleSpeechRecognisesOtherFemaleAmericanVoice() throws IOException {
		String expectedString="hi there this is the final test for Google speech we are trying out different voices to allow Google to understand different speech";
		String returnedString=googleSpeech.convertToText("./sound/ThirdTest.wav",1 );
		assertTrue(expectedString.equals(returnedString));
		
	}

}

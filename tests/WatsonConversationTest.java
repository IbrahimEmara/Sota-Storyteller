import static org.junit.Assert.*;


import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

public class WatsonConversationTest {
	ProcessingEngine watsonConversation= WatsonConversation.getInstance();
	
	@Test
	public final void testToSeeIfWatsonChangesToStoryCorrectly() {
		String response = watsonConversation.returnResponse("Jack and the Beanstalk please!");
		String expectedResponse="Once upon a time, there lived Jack, on his farm in the country. One day, while going for walk, Jack notices a new farm. If you want to hear more about Jack, shout out \"Jack!\" If you want to start the adventure, shout out \"Adventure!\"";
		assertTrue(response.equals(expectedResponse));
	}
	
	@Test
	public final void testToSeeIfWatsonChangesToFirstBranchCorrectly() throws UnsupportedAudioFileException, IOException {
		watsonConversation.processInput("Jack and the Beanstalk please!");
		String response = watsonConversation.returnResponse("The first option please.");
		String expectedResponse="Every day, Jack would help his mother, but despite all their hard work, Jack and his mother were very poor with barely enough money to keep themselves fed.\n\n\"What shall we do, what shall we do?\" said the mother. Do you want to sell the family cow are do you want to go for a walk?";
		assertTrue(response.equals(expectedResponse));
	}
	
	@Test
	public final void testToSeeIfWatsonChangesToSecondBranchCorrectly() throws UnsupportedAudioFileException, IOException {
		watsonConversation.processInput("Jack and the Beanstalk please!");
		String response = watsonConversation.returnResponse("The second option please.");
		String expectedResponse="When Jack woke up the next morning,  the magical beans he had thrown out of the window into the garden had sprung up into a big beanstalk which went up and up and up until it reached the sky. Would you like to climb up the beanstalk or find out more about how Jack felt?";
		assertTrue(response.equals(expectedResponse));
	}


}

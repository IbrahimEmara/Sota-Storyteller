import java.awt.Color;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.json.JSONException;

import jp.vstone.RobotLib.*;
import jp.vstone.camera.CRoboCamera;
import jp.vstone.camera.CameraCapture;

public class Main {
	ArrayList<Short[]> positions = new ArrayList<>();
	CRobotMem mem = new CRobotMem();
	CSotaMotion motion = new CSotaMotion(mem);
	CRobotPose pose = new CRobotPose();
	SpeechToText googleSpeech = GoogleSpeechToText.getInstance();
	private int currentBranch = 1;

	
	private int synthesise(String fileName, int currentBranch) throws IOException, InterruptedException, UnsupportedAudioFileException
	{
		return googleSpeech.convertAndSend(fileName, currentBranch);
		
	}

	private void respond(String fileName) throws IOException, InterruptedException, UnsupportedAudioFileException {
		int duration= synthesise(fileName, currentBranch);
		currentBranch++;
		//Select a random movement to perform
		for (int counter = 0; counter < duration / 2; counter++) {
			Random rand = new Random();
			int value = rand.nextInt(positions.size());
			pose.SetPose(new Byte[] { 1, 2, 3, 4, 5, 6, 7, 8 } // id
					, positions.get(value) // target pos
			);
			motion.play(pose, 1000);
			motion.waitEndinterpAll();
			Thread.sleep(1000);
		}
		motion.waitEndinterpAll();
	}


	private void recordSpeech() {
		CRecordMic mic = new CRecordMic();
		//Record speech for 6 seconds
		mic.startRecording("./recording.wav", 6000);
		mic.waitend();
	}

	private void populateMovements() {
		positions.add(new Short[] { 0, -900, 0, 900, 0, 0, 0, 0 });
		positions.add(new Short[] { 0, -320, 250, 320, -250, 0, -270, 0 });
		positions.add(new Short[] { 0, -320, 250, -250, 560, -760, -270, 0 });
		positions.add(new Short[] { 0, -120, -900, 120, 900, 0, 50, 0 });
		positions.add(new Short[] { 0, -120, 0, 120, 0, 0, 50, 0 });
		positions.add(new Short[] { 0, 410, 0, 1500, 0, 0, 50, 0 });
		positions.add(new Short[] { 0, -1500, 0, -150, 0, 0, 50, 0 });
		positions.add(new Short[] { 0, -10, 0, 10, 0, 0, 50, 0 });
		positions.add(new Short[] { 1500, -10, 0, 10, 0, 0, 50, 0 });
		positions.add(new Short[] { 0, -10, 0, 10, 0, 0, 50, 0 });
		positions.add(new Short[] { -1500, -10, 0, 10, 0, 0, 50, 0 });
		positions.add(new Short[] { 0, -10, 0, 10, 0, 0, 50, 0 });
		positions.add(new Short[] { 0, -10, 0, 10, 0, 0, 50, 300 });
		positions.add(new Short[] { 0, 20, 0, 10, 0, 0, 50, -300 });
		positions.add(new Short[] { 0, -10, 0, 10, 0, -960, 50, 0 });
	}
	
	private void checkEnvValue()
	{
		//Check if Google Cloud environment variable is set
		String envValue = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
		System.out.println("The environment variable is ");
		System.out.println(envValue);
	}

	public static void main(String args[]) throws IOException, UnsupportedAudioFileException {
		Main main = new Main();
		main.checkEnvValue();
		
		//If program connects to SOTA memory
		if (main.mem.Connect()) {
			main.motion.InitRobot_Sota();
			main.motion.ServoOn();
			main.populateMovements();
			//Ask Watson Conversation for the introduction
			main.googleSpeech.sendToWatson("Introduction");
			while (true) {
				//When central button is pressed
				if (main.motion.isButton_Power()) {
					CRobotPose pose = new CRobotPose();
					pose.setLED_Sota(Color.GREEN, Color.GREEN, 255, Color.GREEN);
					//Turn Eye LEDs green for six seconds while recording
					main.motion.play(pose, 6000);
					main.recordSpeech();
					try {
						pose.setLED_Sota(Color.RED, Color.RED, 255, Color.RED);
						main.motion.play(pose, 1000);
						main.respond("recording.wav");
					} catch (Exception e) {
						e.printStackTrace();
					}
					pose.setLED_Sota(Color.BLUE, Color.BLUE, 255, Color.BLUE);
					main.motion.play(pose, 1000);
				}
			}
		}

		System.exit(0);
	}

}

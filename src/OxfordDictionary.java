import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.json.JSONArray;
import org.json.JSONObject;



public class OxfordDictionary implements ProcessingEngine{
	TTS watsonTTS = WatsonTTS.getInstance();
	
	///Use Watson TTS to convert the definition of the word to WAV file, return the length of the WAV file
	public int respondToUser(String response) throws UnsupportedAudioFileException, IOException  //
	{
		int duration = watsonTTS.speakIfNecessary(response);
		watsonTTS.speakSota(response);
		return duration;
	}
	
	//The packaged method of the whole features, return the length of WAV file that contains the definition of the word
	public int processInput(String word) throws UnsupportedAudioFileException, IOException
	{
		return respondToUser(returnResponse(word));
	}
	
	
	//Get the definition of the word using Oxford Dictionary API
	public String returnResponse(String word)
	{
		ArrayList<String> def = LookUp(word);
		return def.get(1);
	}
	

	private ArrayList<String> LookUp(String word){
		String app_id = "a76e558e";
		String app_key = "a3ef4ec14b34d384371850e94688e34e";
		ArrayList<String> def = new ArrayList<>();
		String language = "en";
		try {
			
			//Building http connection with GET method
			URL url = new URL ("https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/" + word.toLowerCase());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("Accept","application/json");
            con.setRequestProperty("app_id",app_id);
            con.setRequestProperty("app_key",app_key);
            
            
            //Get the returned data and convert it into string
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            
            
        
            String js = stringBuilder.toString();
            
            //Convert the string to JSon object and get the definition of the word
            JSONObject obj = new JSONObject(js);
            
            JSONArray lexicalEntries = obj.getJSONArray("results").getJSONObject(0).getJSONArray("lexicalEntries");
            
            for (int i = 0; i < lexicalEntries.length(); i++){
            	JSONArray senses = lexicalEntries.getJSONObject(i).getJSONArray("entries").getJSONObject(0).getJSONArray("senses");
            	String category = lexicalEntries.getJSONObject(i).getString("lexicalCategory") + ":";
            	def.add(category);
            	for (int k = 0 ; k < senses.length(); k ++){
            		String definition = senses.getJSONObject(k).getJSONArray("definitions").toString();
            		def.add(definition);
            	}
            }
            
            
		} catch (IOException e) {
			System.out.println("Please check your spelling! I can't find this word!!");
		}
		return def;
	}
}

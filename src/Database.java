import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Database {
	
	public Database(){
		
	}
	
	//Choose the no. of branch, 0 means all branches!
	public void getResponseForBranch(int branch) throws Exception{
		URL url = new URL("http://alvinling.000webhostapp.com/GetResponse.php");
		Map<String,Object> params = new LinkedHashMap();
		 params.put("NoOfBranch",branch);
	     StringBuilder post = new StringBuilder();
	     for(Map.Entry<String, Object> param : params.entrySet()){
	    	 	if (post.length() != 0)
	    	 		post.append("&");
	    	 	post.append(URLEncoder.encode(param.getKey(),"UTF-8"));
	    	 	post.append('=');
	    	 	post.append(URLEncoder.encode(String.valueOf(param.getValue()),"UTF-8"));
	     }
	     
	     byte[] postByte = post.toString().getBytes("UTF-8");
	     
	     HttpURLConnection http = (HttpURLConnection) url.openConnection();
	     http.setDoOutput(true);
	     http.setRequestMethod("POST");
	     http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	     http.setRequestProperty("Content-Length", String.valueOf(postByte.length));
	     http.getOutputStream().write(postByte);
	     
	     Reader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
	     
	     StringBuilder sb = new StringBuilder();
	     
	     for (int c; (c = in.read() )> 0 ;){
	    	 sb.append((char)c);
	     }
	     
	     
	     JSONObject obj = new JSONObject(sb.toString());
	     
	     if (!obj.getBoolean("success")){
	    	 System.out.println("There is no reponse for branch No." + branch + " now!" );
	     }else{
			 System.out.println("NoOfBranch\t\t\t\tResponse\t\t\t\t\t\t\tDate&Time");
	    	 JSONArray array = obj.getJSONArray("Response");
	    	 int i = 0 ;
	    	 while (i < array.length()){
	    		 int branNO = array.getJSONObject(i).getInt("NoOfBranch");
	    		 String response = array.getJSONObject(i).getString("Response");
	    		 String time = array.getJSONObject(i).getString("DatenTime");
	    	   	 System.out.println("     " + branNO + "    \t\t\t" + response+ " \t\t\t\t\t" + time);
	    	   	 i ++;
	    	 }
	     }
	     
	}
	
	
	//Insert the branch number, response, and datetime into database. 
	//Datetime is get from Java build-in library
	public boolean InsertResponse(int branch, String response) throws Exception{
		URL url = new URL("http://alvinling.000webhostapp.com/InsertResponse.php");
		Map<String,Object> params = new LinkedHashMap();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		String time = (String)dtf.format(now);
		// Connecting url with POST method, pass the params by puting the values into a hashpmap
		 params.put("NoOfBranch",branch);
		 params.put("Response", response);
		 params.put("time", time);
	     StringBuilder post = new StringBuilder();
	     for(Map.Entry<String, Object> param : params.entrySet()){
	    	 	if (post.length() != 0)
	    	 		post.append("&");
	    	 	post.append(URLEncoder.encode(param.getKey(),"UTF-8"));
	    	 	post.append('=');
	    	 	post.append(URLEncoder.encode(String.valueOf(param.getValue()),"UTF-8"));
	     }
	     
	     //Encode the params to byte
	     byte[] postByte = post.toString().getBytes("UTF-8");
	     
	     HttpURLConnection http = (HttpURLConnection) url.openConnection();
	     http.setDoOutput(true);
	     http.setRequestMethod("POST");
	     //Set the outputstream data type of http connection
	     http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	     http.setRequestProperty("Content-Length", String.valueOf(postByte.length));
	     http.getOutputStream().write(postByte);
	     Reader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
	     
	     StringBuilder sb = new StringBuilder();
	     
	     for (int c; (c = in.read() )> 0 ;){
	    	 sb.append((char)c); // get the inputstream from http and convert it into string
	     }
	     
	     //Convert the string to JSon object and get the returned data
	     JSONObject obj = new JSONObject(sb.toString());
	     if (obj.getBoolean("success"))
	    	 return true;
	     else
	    	 return false;
	}

	public JSONArray getJSonByBranch(int branch) throws Exception {
		URL url = new URL("http://alvinling.000webhostapp.com/GetResponse.php");
		Map<String,Object> params = new LinkedHashMap();
		 params.put("NoOfBranch",branch);
	     StringBuilder post = new StringBuilder();
	     for(Map.Entry<String, Object> param : params.entrySet()){
	    	 	if (post.length() != 0)
	    	 		post.append("&");
	    	 	post.append(URLEncoder.encode(param.getKey(),"UTF-8"));
	    	 	post.append('=');
	    	 	post.append(URLEncoder.encode(String.valueOf(param.getValue()),"UTF-8"));
	     }
	     
	     byte[] postByte = post.toString().getBytes("UTF-8");
	     
	     HttpURLConnection http = (HttpURLConnection) url.openConnection();
	     http.setDoOutput(true);
	     http.setRequestMethod("POST");
	     http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	     http.setRequestProperty("Content-Length", String.valueOf(postByte.length));
	     http.getOutputStream().write(postByte);
	     
	     Reader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
	     
	     StringBuilder sb = new StringBuilder();
	     
	     for (int c; (c = in.read() )> 0 ;){
	    	 sb.append((char)c);
	     }
	     
	     
	     JSONObject obj = new JSONObject(sb.toString());
	     JSONArray array = obj.getJSONArray("Response");
	     
	     return array;
	}
}

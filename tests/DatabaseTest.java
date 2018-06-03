import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class DatabaseTest {
	
	@Test
	public void TestForDatabase() throws Exception
	{
		Database mysqldb = new Database();
		String response = "Testing for database";
		int branch = 6; 

		boolean result = mysqldb.InsertResponse(branch, response);
		JSONArray array = mysqldb.getJSonByBranch(branch);
		
		int return_branch  = array.getJSONObject(array.length() -1).getInt("NoOfBranch");
		String return_response = array.getJSONObject(array.length() -1).getString("Response");
		
		assertEquals(return_branch,branch);
		assertEquals(return_response,response);
		assertEquals(result,true);
	}
	
}

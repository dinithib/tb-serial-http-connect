

import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

public class TelemetryHandler {
	
	private static String authToken;
	private static String tbDeviceId;
	
	public void setDeviceId(String tbDeviceId)
	{
		this.tbDeviceId =  tbDeviceId;
	}
	
	public void initialize(){
		
		String deviceUri = "http://136.xxx.xxx.xx:9090/api/auth/login";
		
		JSONObject data =  new JSONObject();
		data.accumulate("username", "tenant@thingsboard.org");
		data.accumulate("password","iot234" );
		
		try {

			StringEntity requestEntity = new StringEntity(
					data.toString(), ContentType.APPLICATION_JSON);

			HttpPost postRequest = new HttpPost(deviceUri);

			postRequest.addHeader("content-type", "application/json");
			postRequest.addHeader("accept", "application/json");
			postRequest.setEntity(requestEntity);
			
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(postRequest);
			
		    JSONObject jsonObject = new JSONObject(EntityUtils.toString(response.getEntity()));
		    authToken = "Bearer "+jsonObject.getString("token");
			

		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
public void sendTelemetry(String data){
	
	String uri = "http://136.186.108.47:9090/api/plugins/telemetry/DEVICE/%s/timeseries/shared";
	String deviceUri =  String.format(uri, tbDeviceId);
	try {

		StringEntity requestEntity = new StringEntity(
				data, ContentType.APPLICATION_JSON);
		HttpPost postRequest = new HttpPost(deviceUri);

		postRequest.addHeader("content-type", "application/json");
		postRequest.addHeader("accept", "application/json");
		postRequest.addHeader("x-authorization", authToken);
		postRequest.setEntity(requestEntity);
		
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(postRequest);

	} catch (UnsupportedOperationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

}

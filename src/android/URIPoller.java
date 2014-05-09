package hu.spot.taxi.pollingPlugin;
 
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import android.app.Activity;
import android.content.Intent;

public class URIPoller extends CordovaPlugin {
	public static final String ACTION_START_POLLING = "startPolling";
	public static final String ACTION_STOP_POLLING = "stopPolling";
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		try {
			if (ACTION_START_POLLING.equals(action)) { 
				JSONObject arguments = args.getJSONObject(0);
				String token = arguments.getString("token");
				callbackContext.success(token.toUpperCase());
				Context context=this.cordova.getActivity().getApplicationContext();
			    //or Context context=cordova.getActivity().getApplicationContext();
			    Intent intent=new Intent(context,Next_Activity.class);

			    context.startActivity(intent);
				return true;
			} else if (ACTION_STOP_POLLING.equals(action)) { 
				callbackContext.success("B1ad88bce3");
				return true;
			}
			callbackContext.error("Invalid action");
			return false;
		} catch(Exception e) {
			System.err.println("Exception: " + e.getMessage());
			callbackContext.error(e.getMessage());
			return false;
		} 
	}
}
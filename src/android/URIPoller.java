package hu.spot.taxi.pollingPlugin;

import hu.spot.taxi.pollingPlugin.MyPollingService.MyPollerBinder;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;



public class URIPoller extends CordovaPlugin {
	public static final String ACTION_START_POLLING = "startPolling";
	public static final String ACTION_STOP_POLLING = "stopPolling";
	private Intent serviceHandler;
	private String token = null;
	private String serverAddress = null;
	private MyPollerBinder pollerBinder = null;
	@Override
	public boolean execute(String action, JSONArray args,
			final CallbackContext callbackContext) throws JSONException {
		try {
			if (ACTION_START_POLLING.equals(action)) {
				JSONObject arguments = args.getJSONObject(0);
				token = arguments.getString("token");
				serverAddress = arguments.getString("serverAddress");
				cordova.getThreadPool().execute(new Runnable() {
				    public void run() {
						startService(token,serverAddress);
						Log.d("CordovaLog", "Token from JS: " + token);
						callbackContext.success(token); 
				    }
				});
				return true;
			} else if (ACTION_STOP_POLLING.equals(action)) {
				cordova.getThreadPool().execute(new Runnable() {
				    public void run() {
						stopService();
						if(pollerBinder.tokenSafe())
							token = pollerBinder.getToken();
						Log.d("CordovaLog", "Token TO JS: " + token);
						callbackContext.success(token); 
				    }
				});
				return true;
			}
			callbackContext.error("Invalid action");
			return false;
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
			callbackContext.error(e.getMessage());
			return false;
		}
	}

	public void startService( String token, String address ) {
	    serviceHandler = new Intent(cordova.getActivity(), MyPollingService.class);
	    Log.d("CordovaLog", "init bindservice with token: " + token);
	    if(pollerBinder != null && pollerBinder.isBinderAlive()) {
	    	pollerBinder.setServerAddress(address);
	    	pollerBinder.setToken(token);
	    }
	    cordova.getActivity().bindService(serviceHandler, mConnection, Context.BIND_AUTO_CREATE);
	}
	public void stopService() {
	    Log.d("CordovaLog", "init before stopservice with token: " + token);
	    pollerBinder.stopPolling();
	    cordova.getActivity().stopService(serviceHandler);
	    Log.d("CordovaLog", "init after stopservice token: " + token);
	}
	private ServiceConnection mConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			pollerBinder = (MyPollerBinder) service;
			pollerBinder.setToken(token);
			Log.d("CordovaLog", "Token at serviceConnected: " + token);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			token = pollerBinder.getToken();
			Log.d("CordovaLog", "Token at serviceDisconnected: " + token);
			pollerBinder = null;
		}
		
	};
}
package group1.pittapi;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import group1.pittapi.events.WeatherEvent;

public class WeatherService extends Service {
    private Messenger messenger;
    private static final String TAG = "WeatherService";

    public WeatherService() {
        messenger = new Messenger(new WeatherEventHandler());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    class WeatherEventHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            String weatherEventString = msg.getData().getString("info");
            // notify WeatherService of the event
            onWeatherEvent(WeatherEvent.fromJsonString(weatherEventString));
        }
    }

    public void onWeatherEvent(WeatherEvent event) {
        Log.d(TAG, "Received a weather event! Data: " + WeatherEvent.toJsonString(event));
        Intent intent = new Intent(this, PittWeather.class);
        intent.putExtra("info", WeatherEvent.toJsonString(event));
        startActivity(intent);
    }


}

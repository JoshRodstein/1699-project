package group1.pittapi.events;

import com.google.gson.Gson;

public class WeatherEvent {
    String temperature;
    String weather;

    public WeatherEvent(String t, String w) {
        this.temperature = t;
        this.weather = w;
    }
    public static String toJsonString(WeatherEvent e) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(e);
        return jsonString;
    }
    public static WeatherEvent fromJsonString(String json) {
        Gson gson = new Gson();
        WeatherEvent event = gson.fromJson(json, WeatherEvent.class);
        return event;
    }
}

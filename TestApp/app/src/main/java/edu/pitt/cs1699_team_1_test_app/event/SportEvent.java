package edu.pitt.cs1699_team_1_test_app.event;

import com.google.gson.Gson;

/**
 * Created by jmaciak on 3/28/18.
 */

public class SportEvent {
    String sport;
    String opponent_name;
    String result;
    String score;
    String opponent_score;

    public SportEvent(String sport, String opponent_name, String result, String score, String opponent_score) {
        this.sport = sport;
        this.opponent_name = opponent_name;
        this.result        = result;
        this.score         = score;
        this.opponent_name = opponent_score;
    }

    public static String toJsonString(SportEvent e) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(e);
        return jsonString;
    }

    public static SportEvent fromJsonString(String json) {
        Gson gson = new Gson();
        SportEvent event = gson.fromJson(json, SportEvent.class);
        return event;
    }
}
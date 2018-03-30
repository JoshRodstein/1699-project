package group1.pittapi;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.gson.Gson;

import java.util.Locale;

public class SportsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        String info = null;
        try {
            // unpack the received sport event
            info = intent.getExtras().getString("info");
            final SportEvent se = fromJsonString(info);

            // Unpack received info, send to our Activity
            Intent intent1 = new Intent(context, PittScores.class);
            intent1.putExtra("OppName", se.opponent_name);
            intent1.putExtra("OppScore", se.opponent_score);
            intent1.putExtra("Sport", se.sport);
            intent1.putExtra("PittScore", se.score);
            context.startActivity(intent1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class SportEvent {
        String sport;
        String opponent_name;
        String result;
        String score;
        String opponent_score;
    }

    private SportEvent fromJsonString(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, SportEvent.class);
    }

}

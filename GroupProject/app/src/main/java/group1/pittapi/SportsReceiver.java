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


            Intent intent1 = new Intent(context, PittScores.class);
            intent1.putExtra("OppName", se.opponent_name);
            intent1.putExtra("OppScore", se.opponent_score);
            intent1.putExtra("PittScore", se.score);
            context.startActivity(intent1);

//            // Show Dialog with prompt to open this app.
//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setTitle("Sports Update")
//                    .setMessage(String.format(Locale.US, "New update to %s game against %s.", se.sport, se.opponent_name));
//            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            }).setPositiveButton("See More", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    Intent intent1 = new Intent(context, PittScores.class);
//                    intent1.putExtra("OppName", se.opponent_name);
//                    intent1.putExtra("OppScore", se.opponent_score);
//                    intent1.putExtra("PittScore", se.score);
//                    context.startActivity(intent1);
//                }
//            });
//            AlertDialog dialog = builder.create();
//            dialog.show();
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

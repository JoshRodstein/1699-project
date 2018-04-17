package edu.pitt.cs1699_team_1_test_app;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import edu.pitt.cs1699_team_1_test_app.event.BuildingStateEvent;
import edu.pitt.cs1699_team_1_test_app.event.SportEvent;
import edu.pitt.cs1699_team_1_test_app.event.UserEnterEvent;
import edu.pitt.cs1699_team_1_test_app.event.WeatherEvent;

public class MainActivity extends AppCompatActivity implements ServiceConnection {
    private static final String APP_PACKAGE_NAME = "group1.pittapi";
    private static final String BROADCAST_RECEIVER_CLASS_NAME = "group1.pittapi.SportsReceiver";
    private static final String ACTION_TRIGGER1 = "team_1.trigger_1";
    private static final String ACTION_TRIGGER2 = "team_1.trigger_2";
    private static final String ACTION_TRIGGER3 = "team_1.trigger_3";
    private static final String ACTION_TRIGGER4 = "team_1.trigger_4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onTrigger1ButtonClicked(View v) {
        UserEnterEvent enterEvent = new UserEnterEvent((new Date()).toString(), "CATHY");
        String json = UserEnterEvent.toJsonString(enterEvent);

        Intent intent = new Intent();
        intent.setAction(ACTION_TRIGGER1);
        intent.putExtra("info", json);
        startActivity(intent);

        Toast.makeText(this, "Sent trigger 1!", Toast.LENGTH_SHORT).show();
    }
    public void onTrigger2ButtonClicked(View v) {
        BuildingStateEvent buildingStateEvent = new BuildingStateEvent("HILLMAN", "7:00", "20:00");
        String json = BuildingStateEvent.toJsonString(buildingStateEvent);

        Intent intent = new Intent();
        intent.setAction(ACTION_TRIGGER2);
        intent.putExtra("info", json);
        startActivity(intent);

        Toast.makeText(this, "Sent trigger 2!", Toast.LENGTH_SHORT).show();
    }
    public void onTrigger3ButtonClicked(View v) {
        PackageManager pm = getPackageManager();
        Intent serviceIntent = new Intent(ACTION_TRIGGER3);
        List<ResolveInfo> services  = pm.queryIntentServices(serviceIntent, 0);
        if(!services.isEmpty()) {
            ResolveInfo service = services.get(0);
            Intent intent = new Intent();
            intent.setClassName(APP_PACKAGE_NAME, service.serviceInfo.name);
            intent.setAction(ACTION_TRIGGER3);
            bindService(intent, this, BIND_AUTO_CREATE);
        }

        Toast.makeText(this, "Sent trigger 3!", Toast.LENGTH_SHORT).show();
    }
    public void onTrigger4ButtonClicked(View v) {
        EditText sport, eTeam, score, eScore;
        sport = findViewById(R.id.Sport);
        eTeam = findViewById(R.id.OpponentName);
        score = findViewById(R.id.PittScore);
        eScore = findViewById(R.id.OpponentScore);

        String sSport, sETeam, sScore, sEScore, sWin;
        sSport = sport.getText().toString();
        sETeam = eTeam.getText().toString();
        sScore = score.getText().toString();
        sEScore = eScore.getText().toString();
        int iScore, iEScore;
        iScore = Integer.parseInt(sScore);
        iEScore = Integer.parseInt(sEScore);
        if (iScore > iEScore) {
            sWin = "WIN";
        } else if (iScore == iEScore) {
            sWin = "DRAW";
        } else {
            sWin = "LOSS";
        }


        SportEvent event;
        event = new SportEvent(sSport, sETeam, sWin, sScore, sEScore);
        //= new SportEvent("FOOTBALL", "PENN_STATE","WIN", "42", "39");


        String json = SportEvent.toJsonString(event);

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.setAction(ACTION_TRIGGER4);
        intent.setComponent(new ComponentName(APP_PACKAGE_NAME, BROADCAST_RECEIVER_CLASS_NAME));
        intent.putExtra("info", json);

        sendBroadcast(intent);
        Toast.makeText(this, "Sent trigger 4!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        WeatherEvent e = new WeatherEvent("75", "RAIN");
        Messenger messenger = new Messenger(service);
        Message weatherMessage = Message.obtain();
        Bundle data = new Bundle();
        data.putString("info", WeatherEvent.toJsonString(e));
        weatherMessage.setData(data);
        try {
            messenger.send(weatherMessage);
        } catch (RemoteException ex) {
            Toast.makeText(this, "Failed to send trigger 3 to remote service :(", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}

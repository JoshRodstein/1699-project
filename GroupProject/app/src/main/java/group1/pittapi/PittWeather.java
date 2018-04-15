package group1.pittapi;

import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class PittWeather extends AppCompatActivity {
    /*
    sun = 0
    cloud = 1
    snow = 2
    snow = 3
     */
    private int weather;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        anonSignIn();

        weather = 3;
        switch (weather) {
            case 0:
                ((ImageView)findViewById(R.id.cur_weather)).setImageDrawable(getResources().getDrawable(R.drawable.sunny));
                ((ImageView)findViewById(R.id.fade)).setBackgroundColor(Color.parseColor("#22ffff00"));
                break;
            case 1:
                ((ImageView)findViewById(R.id.cur_weather)).setImageDrawable(getResources().getDrawable(R.drawable.cloudy));
                ((ImageView)findViewById(R.id.fade)).setBackgroundColor(Color.parseColor("#22aaaaaa"));
                break;
            case 2:
                ((ImageView)findViewById(R.id.cur_weather)).setImageDrawable(getResources().getDrawable(R.drawable.snowy));
                ((ImageView)findViewById(R.id.fade)).setBackgroundColor(Color.parseColor("#22ffffff"));
                break;
            case 3:
                ((ImageView)findViewById(R.id.cur_weather)).setImageDrawable(getResources().getDrawable(R.drawable.rainy));
                ((ImageView)findViewById(R.id.fade)).setBackgroundColor(Color.parseColor("#220000ff"));
                break;
        }
    }

    public void triggerNextApp(View view) {
        String s = null;
        switch (weather) {
            case 0:
                s = "Sunny";
                break;
            case 1:
                s = "Cloudy";
                break;
            case 2:
                s = "Snowy";
                break;
            case 3:
                s = "Rainy";
                break;
        }

        precipitationTrigger(s);
    }

    public void precipitationTrigger(String precipitationType) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.pavel.cs1699_team22", "com.pavel.cs1699_team22.PantryReciever"));
        intent.putExtra("precipitation", precipitationType);
        intent.setAction("com.pavel.cs1699_team22.TRIGGER_RECIEVED");
        sendBroadcast(intent);
    }

    public void anonSignIn(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null) {
            mAuth.signInAnonymously()
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.w("Pitt Weather ANON: ", "signInAnonymously:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Pitt Weather ANON: ", "signInAnonymously:failure", task.getException());
                                Toast.makeText(PittWeather.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });
        } else {
            Log.w("Building Info ANON: ", "signInAnonymously: USER SIGNED IN");
        }
    }

    public void onDestroy(){
        super.onDestroy();
    }

    public void onStop(){
        super.onStop();
    }

    public void onResume(){
        super.onResume();
    }

}

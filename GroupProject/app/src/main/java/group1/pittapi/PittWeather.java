package group1.pittapi;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

        FirebaseUser currentUser = mAuth.getCurrentUser();

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
}

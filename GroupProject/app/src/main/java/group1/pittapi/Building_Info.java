package group1.pittapi;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import com.pavel.cs1699_team22.MusicServiceBinder;

public class Building_Info extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building__info);

        anonSignIn();

        try {
            String info = getIntent().getExtras().getString("info");
            Gson gson = new Gson();
            UserEnterEvent uee = gson.fromJson(info, UserEnterEvent.class);

            // TODO : Depending on the value of uee.building_name we will show the appropriate info.
        } catch (Exception e) {
            e.printStackTrace();
        }


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
                                Log.w("Building Info ANON: ", "signInAnonymously:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Building Info ANON: ", "signInAnonymously:failure", task.getException());
                                Toast.makeText(Building_Info.this, "Authentication failed.",
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

    private class UserEnterEvent {
        String timestamp;
        String building_name;
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

    /**
     * This function displays how to use the trivia trigger.
     * @param v
     */
    public void triviaTrigger(View v)
    {
        triviaFactTriggerFromTeam1("Just like how the Empire State Building in NYC changes its lights for special occasions, the upper section of the Cathedral of Learning illuminates gold with “victory lights” after a football team victory.");
    }

    public void triggerNextApp(View v) {
        String s = null;
        TextView textView = findViewById(R.id.buildingInfoTextView);
        s = textView.getText().toString();
        triviaFactTriggerFromTeam1(s);
    }

    String fact = "Sending Nothing"; // The initial fact string
    MusicServiceBinder mIRemoteService; // A global variable for our custom binder
    /**
     * This is our service connection that recieves and uses the custom binder.
     */
    ServiceConnection mConnection = new ServiceConnection()
    {
        // Called when the connection with the service is established
        public void onServiceConnected(ComponentName className, IBinder service) {
            // Following the example above for an AIDL interface,
            // this gets an instance of the IRemoteInterface, which we can use to call on the service
            // This is the aidl interface object that we can use to call functions of the service
            mIRemoteService = MusicServiceBinder.Stub.asInterface(service);
            if(mIRemoteService != null)
            {
                try
                {
                    mIRemoteService.startMusic(); // Start the music
                    mIRemoteService.transition(fact); // Send the fact string to the service so it will trigger the next application
                }
                catch(Exception e)
                {
                    Log.e("debug", e.getMessage());
                }
            }
        }
        // Called when the connection with the service disconnects unexpectedly
        public void onServiceDisconnected(ComponentName className) {
            mIRemoteService = null;
        }
    };
    /**
     * This function triggers the pantry app with a string that is a trivia fact. Just call it and pass a trivia fact to it.
     * @param triviaFact A trivia fact
     */
    public void triviaFactTriggerFromTeam1(String triviaFact)
    {
        Intent intent = new Intent();
        // This is the information about the service in the other process
        intent.setComponent(new ComponentName("com.pavel.cs1699_team22", "com.pavel.cs1699_team22.MusicService"));
        fact = triviaFact; // Grab the trivia fact that was passed in.
        Context context = getApplicationContext();
        // Bind the service to this activity
        bindService(intent,mConnection,Context.BIND_AUTO_CREATE);
    }


}

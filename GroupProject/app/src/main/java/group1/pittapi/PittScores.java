package group1.pittapi;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Locale;

public class PittScores extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private final String LOGO_CHILD = "Team_Logos";
    private final String DL_LOGO = "Download Logo form FB: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitt_scores);

        // TODO: grab logos from Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference(LOGO_CHILD);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    // TODO: Store children in local Storage
                }
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(DL_LOGO, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read valu
                Log.w(DL_LOGO, "Failed to read value.", error.toException());
            }
        });



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
                                Toast.makeText(PittScores.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });
        }  else {
            Log.w("Pitt Scores ANON: ", "signInAnonymously: USER SIGNED IN");
        }


        ArrayList<ScoreData> scoreData;

        final ListView lv1 = (ListView) findViewById(R.id.scores_list_view);

        try {
            // Gets info of game
            Bundle extras = getIntent().getExtras();
            ScoreData sd = new ScoreData();
            sd.setPittScore(Integer.parseInt(extras.getString("PittScore")));
            sd.setOppScore(Integer.parseInt(extras.getString("OppScore")));
            sd.setOppName(extras.getString("OppName"));

            //Shows dialog:
            // Cancel button: closes this activity
            // See More button: lets them view the scores we got from the intent
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Sports Update").setMessage(String.format(Locale.US,
                    "New update to %s game against %s.",
                    extras.getString("Sport"), sd.getOppName()));

            builder.setPositiveButton("See More", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishWrapper();
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

            // Add game to list
            scoreData = new ArrayList<>();
            scoreData.add(sd);


            // TODO : Here we may add more games that we have in storage.
        } catch (NullPointerException e) {
            // If this Activity is started by a badly formatted intent,
            // it will show the default list.
            scoreData = getScoreData();
        }

        lv1.setAdapter(new ScoresAdapter(this, scoreData));


        // Button for trigger
        Button triggerButton = findViewById(R.id.sports_trigger_button);
        triggerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sportEventSoon()) {
                    sendSportsTrigger();
                } else {
                    Toast.makeText(getApplicationContext(),"No upcoming games were found.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean sportEventSoon() {
        // todo implement
        return true;
    }

    private void sendSportsTrigger() {
        // todo implement
        String gameType, gameDescription, time;

        gameType = "Football";
        gameDescription = "Pitt v. Penn State";
        time = "2:00pm";


        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.pavel.cs1699_team22", "com.pavel.cs1699_team22.LogInActivity"));
        Bundle bundle = new Bundle();
        SportsEvent footballGame = new SportsEvent(gameType,gameDescription, time);
        Gson gson = new Gson();
        String game = gson.toJson(footballGame);
        bundle.putString("game", game);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private ArrayList<ScoreData> getScoreData(){
        ArrayList<ScoreData> results = new ArrayList<ScoreData>();

        ScoreData sr1 = new ScoreData();
        sr1.setPittScore(0);
        sr1.setOppScore(0);
        sr1.setOppName("ABC");
        results.add(sr1);

        sr1 = new ScoreData();
        sr1.setPittScore(0);
        sr1.setOppScore(0);
        sr1.setOppName("ABC");
        results.add(sr1);

        sr1 = new ScoreData();
        sr1.setPittScore(0);
        sr1.setOppScore(0);
        sr1.setOppName("ABC");
        results.add(sr1);

        sr1 = new ScoreData();
        sr1.setPittScore(0);
        sr1.setOppScore(0);
        sr1.setOppName("ABC");
        results.add(sr1);

        return results;
    }

    private void finishWrapper() {
        finish();
    }

    private class SportsEvent
    {
        private String sportName; // The name of the sport... i.e Football or Basketball or any sport name
        private String gameDescription; // The description of the game... Something like "Panthers vs Lions"
        private String gameTime; // The time that the game starts

        private SportsEvent(String name, String description, String time)
        {
            sportName = name;
            gameDescription = description;
            gameTime = time;
        }

    }
}


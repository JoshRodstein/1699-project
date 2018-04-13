package group1.pittapi;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;

public class PittScores extends AppCompatActivity {
    boolean authOccurred;
    private FirebaseAuth mAuth;
    private final String LOGO_ROOT = "Team_Logo_URL";
    private final String DL_LOGO = "Download Logo form FB: ";
    private String Key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitt_scores);

        anonSignIn();

        File file = getApplicationContext().getFileStreamPath(Key + ".png");
        if (!getApplicationContext().getFileStreamPath("PITT.png").exists()){
            Log.w("getLogoURLs", "Logos not present in internal storage: downloading now...");
            getLogoURLs();
        }

//        ArrayList<ScoreData> scoreData;
//
//        final ListView lv1 = (ListView) findViewById(R.id.scores_list_view);
//
//        try {
//            // Gets info of game
//            Bundle extras = getIntent().getExtras();
//            ScoreData sd = new ScoreData();
//            sd.setPittScore(Integer.parseInt(extras.getString("PittScore")));
//            sd.setOppScore(Integer.parseInt(extras.getString("OppScore")));
//            try {
//                sd.setOppName(extras.getString("OppName"));
//            } catch (Exception e){
//                Log.w("SET_OPP_NAME", e.toString());
//            }
//
//            //Shows dialog:
//            // Cancel button: closes this activity
//            // See More button: lets them view the scores we got from the intent
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Sports Update").setMessage(String.format(Locale.US,
//                    "New update to %s game against %s.",
//                    extras.getString("Sport"), sd.getOppName()));
//
//            builder.setPositiveButton("See More", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    finishWrapper();
//                    dialog.dismiss();
//                }
//            });
//            AlertDialog dialog = builder.create();
//            dialog.show();
//
//            // Add game to list
//            scoreData = new ArrayList<>();
//            scoreData.add(sd);
//
//
//        } catch (NullPointerException e) {
//            // If this Activity is started by a badly formatted intent,
//            // it will show the default list.
//            scoreData = getScoreData();
//        }
//
//        lv1.setAdapter(new ScoresAdapter(this, scoreData));


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

    public void anonSignIn(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        boolean authed;
        if(currentUser == null) {
            mAuth.signInAnonymously()
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.w("Pitt Scores ANON: ", "signInAnonymously:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                getLogoURLs();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Pitt Scores ANON: ", "signInAnonymously:failure", task.getException());
                                Toast.makeText(PittScores.this, "Authentication failed.",
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

    public void getLogoURLs() {
        //grab team logo URLs form Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference(LOGO_ROOT);
        DatabaseReference accNode = dbRef.child("ACC").getRef();

        accNode.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            HashMap<String, String> URL_Map = new HashMap<>();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.w("In Event Listener", "Logo key:val grab");
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    URL_Map.put(child.getKey(), child.getValue().toString());
                    Log.w(DL_LOGO, child.getKey() + " : " + child.getValue());
                }

                /* TODO: created counter 'i' as workaround for hashmap infinite-loop
                   TODO: Will find more elegant solution. But this works for now.  */
                String value;
                int i = 0;
                for (String key : URL_Map.keySet()){
                    if(i >= URL_Map.size()){ break; }
                    value = URL_Map.get(key);
                    new DownloadAndSaveLogos().execute(key, value);
                    i++;
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read valu
                Log.w(DL_LOGO, "Failed to read value.", error.toException());
            }
        });
    }

    private class DownloadAndSaveLogos extends AsyncTask<String, Void, Bitmap> {
        protected void onPreExecute(){}

        Bitmap bitmap = null;
        @Override
        protected Bitmap doInBackground(String... keyVal) {

            Log.w("doInBackground:", keyVal[1].toString());
            try {
                URL url = new URL(keyVal[1].toString());
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                Key = keyVal[0];
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
            //}
            //return null;
        }

        protected void onPostExecute(Bitmap result){
            // save image to internal storage
            Log.w("Save Image:", result.toString() + ":" + Key + ".png");
            saveImage(getApplicationContext(), result, Key + ".png");

            // verify that image has been stored succesfully
            File file = getApplicationContext().getFileStreamPath(Key + ".png");
            if (file.exists()) Log.w("file", Key + ".png exists!!!");
        }

    }

    public void saveImage(Context context, Bitmap b, String imageName){
        FileOutputStream foStream;
        try {
            foStream = context.openFileOutput(imageName, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 100, foStream);
            foStream.close();
        } catch (Exception e){
            Log.d("saveImage", "Exception 2, Something went wrong!");
            e.printStackTrace();
        }
    }

    private boolean sportEventSoon() {
        return true;
    }

    private void sendSportsTrigger() {
        String gameType = null , gameDescription = null, time = null;
        try {
            gameType = recentGame.getSport();
            gameDescription = recentGame.getDescription();
            time = "2:00pm";
        } catch (NullPointerException npe) {
            Log.d("BAD", "Programs this better");
        }


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



    public void onDestroy() {
        super.onDestroy();
    }

    public void onStop(){
        super.onStop();
    }

    public void onResume(){
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setList();
    }

    private ScoreData recentGame = null;

    public void setList() {


        ArrayList<ScoreData> scoreData;

        final ListView lv1 = (ListView) findViewById(R.id.scores_list_view);

        try {
            // Gets info of game
            Bundle extras = getIntent().getExtras();
            ScoreData sd = new ScoreData();
            sd.setPittScore(Integer.parseInt(extras.getString("PittScore")));
            sd.setOppScore(Integer.parseInt(extras.getString("OppScore")));
            sd.setSport(extras.getString("Sport"));
            sd.setDescription("" + extras.getString("OppName") + " vs. Pitt");
            try {
                sd.setOppName(extras.getString("OppName"));
            } catch (Exception e){
                Log.w("SET_OPP_NAME", e.toString());
            }

            recentGame = sd;

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

            getLastTenGamesFromFirebase(scoreData);
        } catch (NullPointerException e) {
            // If this Activity is started by a badly formatted intent,
            // it will show the default list.
            scoreData = getScoreData();
        }

        lv1.setAdapter(new ScoresAdapter(this, scoreData));
    }

    private static final String SCORE_HISTORY = "ScoreHistory";

    public void sendRecentGameToFirebase() {
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference scoreHistory = firebaseDatabase.child(SCORE_HISTORY);

        scoreHistory.child(Long.toString(System.currentTimeMillis()))
            .setValue(recentGame);
    }

    public void getLastTenGamesFromFirebase(final ArrayList<ScoreData> scoreDataArrayList) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        Query query = db.child(SCORE_HISTORY).orderByKey().limitToFirst(10);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    ScoreData game = child.getValue(ScoreData.class);
                    scoreDataArrayList.add(game);
                }

                Collections.reverse(scoreDataArrayList);
                scoreDataArrayList.add(0, scoreDataArrayList.remove(scoreDataArrayList.size() - 1));

                ListView listView = findViewById(R.id.scores_list_view);
                listView.setAdapter(new ScoresAdapter(getApplicationContext(), scoreDataArrayList));
                sendRecentGameToFirebase();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

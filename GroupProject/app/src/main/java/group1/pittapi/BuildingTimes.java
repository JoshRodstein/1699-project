package group1.pittapi;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class BuildingTimes extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_times);

        anonSignIn();

        final ListView lv1 = (ListView) findViewById(R.id.building_list_view); // gets listview

        ArrayList<BuildingData> buildingDataList;

        try {
            // Gets building info
            String info = getIntent().getExtras().getString("info");
            Gson gson = new Gson();
            BldgStateEvent bse = gson.fromJson(info, BldgStateEvent.class);

            buildingDataList = new ArrayList<>();
            buildingDataList.add(new BuildingData(bse.building_name, bse.hoursString()));
        } catch (NullPointerException e) {
            // Puts default info if this activity was started by opening the app
            buildingDataList = getBuildingData();
        }

        /*
         * TODO: Here goes a for loop populating our list with more buildings, possibly from a local db.
         */

        lv1.setAdapter(new BuildingAdapter(this, buildingDataList)); // populates ListView
    }

    public void triggerNextApp() {

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
                                Log.w("Building Times ANON: ", "signInAnonymously:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Building Times ANON: ", "signInAnonymously:failure", task.getException());
                                Toast.makeText(BuildingTimes.this, "Authentication failed.",
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

    private ArrayList<BuildingData> getBuildingData(){
        ArrayList<BuildingData> results = new ArrayList<BuildingData>();

        BuildingData sr1 = new BuildingData();
        sr1.setBuildingName("Building Name");
        sr1.setBuildingHours("9:00am - 5:00pm");
        results.add(sr1);

        sr1 = new BuildingData();
        sr1.setBuildingName("Building Name");
        sr1.setBuildingHours("9:00am - 5:00pm");
        results.add(sr1);

        sr1 = new BuildingData();
        sr1.setBuildingName("Building Name");
        sr1.setBuildingHours("9:00am - 5:00pm");
        results.add(sr1);

        sr1 = new BuildingData();
        sr1.setBuildingName("Building Name");
        sr1.setBuildingHours("9:00am - 5:00pm");
        results.add(sr1);

        return results;
    }

    private class BldgStateEvent {
        String building_name;
        String open_time; // military time
        String close_time;

        String hoursString() {
            int openHour = Integer.parseInt(open_time.substring(0, open_time.indexOf(":")));
            int openMin = Integer.parseInt(open_time.substring(open_time.length() - 1, open_time.length()));
            boolean openPM = false;
            if (openHour > 12) {
                openHour -= 12;
                openPM = true;
            }


            int closeHour = Integer.parseInt(close_time.substring(0, close_time.indexOf(":")));
            int closeMin = Integer.parseInt(close_time.substring(close_time.length() - 1, close_time.length()));
            boolean closePM = false;
            if (closeHour > 12) {
                closeHour -= 12;
                closePM = true;
            }

            return String.format(Locale.US,"%d:%02d%s - %d:%02d%s",
                    openHour, openMin, openPM?"pm":"am",
                    closeHour, closeMin, closePM?"pm":"am");
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

    /**
     * This function demonstrates how to use the seed trigger.
     * @param v
     */
    public void tiggerNextApp(View v)
    {
        Random r = new Random(System.currentTimeMillis());
        seedTrigger(r.nextInt());
    }
    /**
     * Simply pass a seed to the next application and it will trigger the next app
     * @param randomNum A random number to be sent
     */
    public void seedTrigger(int randomNum)
    {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.pavel.cs1699_team22", "com.pavel.cs1699_team22.LogInActivity"));
        Bundle bundle = new Bundle();
        bundle.putString("seed", randomNum + "");
        intent.putExtras(bundle);
        startActivity(intent);
    }


}


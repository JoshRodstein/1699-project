package group1.pittapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class BuildingTimes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_times);

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
         * Here goes a for loop populating our list with more buildings, possibly from a local db.
         */

        lv1.setAdapter(new BuildingAdapter(this, buildingDataList)); // populates ListView
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
            int openHour = Integer.parseInt(open_time.replaceAll("[\\D]", ""));
            int openMin = Integer.parseInt(open_time.substring(open_time.length() - 1, open_time.length()));
            boolean openPM = false;
            if (openHour > 12) {
                openHour -= 12;
                openPM = true;
            }


            int closeHour = Integer.parseInt(close_time.replaceAll("[\\D]",""));
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
}


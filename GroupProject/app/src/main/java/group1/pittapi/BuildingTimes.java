package group1.pittapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class BuildingTimes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_times);

        ArrayList<BuildingData> buildingData = getBuildingData();

        final ListView lv1 = (ListView) findViewById(R.id.building_list_view);
        lv1.setAdapter(new BuildingAdapter(this, buildingData));
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
}


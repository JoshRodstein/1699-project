package group1.pittapi;

import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by billyhinard on 3/23/18.
 */

public class BuildingData {
    private String buildingName = "";
    private String buildingHours = "";

    public BuildingData (String name, String hours) {
        setBuildingName(name);
        setBuildingHours(hours);
    }

    public BuildingData () {}

    public void setBuildingName(String name) {
        this.buildingName = name;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingHours(String name) {
        this.buildingHours = name;
    }

    public String getBuildingHours() {
        return buildingHours;
    }

    public boolean checkHours() {
        DateFormat dateFormat_hour = new SimpleDateFormat("HH");
        DateFormat dateFormat_minute = new SimpleDateFormat("mm");
        Date date = new Date();
        int current_hour = Integer.parseInt(dateFormat_hour.format(date));
        int current_min = Integer.parseInt(dateFormat_minute.format(date));
        //check open hours
        String open = buildingHours.split(" - ")[0];
        int open_hour = Integer.parseInt(open.split(":")[0]);
        String open_am_pm = open.substring(open.length() - 2, open.length());
        if(open_am_pm.equalsIgnoreCase("pm"))
            open_hour += 12;
        if(current_hour < open_hour) {
            return false;
        }
        int open_min = Integer.parseInt(open.substring(open.length() - 4, open.length() - 2));
        if(current_hour == open_hour) {
            if (current_min < open_min) {
                return false;
            }
        }

        //check close hours
        String close = buildingHours.split(" - ")[1];
        int close_hour = Integer.parseInt(close.split(":")[0]);
        String close_am_pm = close.substring(close.length() - 2, close.length());
        if(close_am_pm.equalsIgnoreCase("pm"))
            close_hour += 12;
        if(current_hour > close_hour) {
            return false;
        }
        int close_min = Integer.parseInt(close.substring(close.length() - 4, close.length() - 2));
        if(current_hour == close_hour) {
            if (current_min > close_min) {
                return false;
            }
        }

        return true;
    }


}

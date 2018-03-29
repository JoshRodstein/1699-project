package group1.pittapi;

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




}

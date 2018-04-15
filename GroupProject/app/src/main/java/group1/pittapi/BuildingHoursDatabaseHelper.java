package group1.pittapi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.HashMap;
import java.util.Map;

public class BuildingHoursDatabaseHelper extends SQLiteOpenHelper{
    static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "BuildingHours.db";

    public static final String CREATE_TABLE_BUILDING_HOURS = "CREATE TABLE " + BuildingHoursEntry.TABLE_NAME
                                                            + " (" + BuildingHoursEntry._ID + " INTEGER PRIMARY KEY, "
                                                            + BuildingHoursEntry.COLUMN_NAME_BUILDING + " TEXT, "
                                                            + BuildingHoursEntry.COLUMN_NAME_HOURS + " TEXT)";
    public static final String DROP_TABLE_BUILDING_HOURS   = "DROP TABLE IF EXISTS " + BuildingHoursEntry.TABLE_NAME;

    public BuildingHoursDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BUILDING_HOURS);
        addBuildingsToDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_BUILDING_HOURS);
        onCreate(db);
    }

    private void addBuildingsToDB(SQLiteDatabase db) {
        Map<String, String> buildings = new HashMap<>();
        buildings.put("SENNOT", "6:00AM-10:00PM");
        buildings.put("CATHY", "6:00AM-11:00PM");
        buildings.put("HILLMAN", "12:00AM-12:00AM");
        buildings.put("BENEDUM", "7:30AM-10:00PM");
        buildings.put("UNION", "7:00AM-11:30PM");

        for(Map.Entry<String, String> e : buildings.entrySet()) {
            String query = "INSERT INTO " + BuildingHoursEntry.TABLE_NAME
                            + " (" + BuildingHoursEntry.COLUMN_NAME_BUILDING + ", "
                            + BuildingHoursEntry.COLUMN_NAME_HOURS + ") VALUES (\""
                            + e.getKey() + "\", \"" + e.getValue() + "\")";
            db.execSQL(query);
        }
    }
    public static class BuildingHoursEntry implements BaseColumns {
        public static final String TABLE_NAME = "building_hours";
        public static final String COLUMN_NAME_BUILDING = "name";
        public static final String COLUMN_NAME_HOURS = "hours";
        private BuildingHoursEntry() {}
    }
}

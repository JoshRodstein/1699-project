package group1.pittapi;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class BuildingHoursContentProvider extends ContentProvider {
    public static final String PROVIDER_NAME = "group1.pittapi.BuildingHoursContentProvider";
    private  static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/building_hours");
    private SQLiteDatabase db;
    private static final int BUILDING_HOURS = 1;

    static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "building_hours", BUILDING_HOURS);
    }

    @Override
    public boolean onCreate() {
        db = (new BuildingHoursDatabaseHelper(getContext())).getWritableDatabase();
        return (db != null);
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(BuildingHoursDatabaseHelper.BuildingHoursEntry.TABLE_NAME);
        switch(uriMatcher.match(uri)) {
            case BUILDING_HOURS: {
                break;
            }
        }
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "type/text";
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        throw new UnsupportedOperationException("sorry readonly content provider :(");
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("sorry readonly content provider :(");
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("sorry readonly content provider :(");
    }
}

package group1.pittapi;


import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PittApi";
    private static final String LOGO_TABLE = "Logos";
    private static final String KEY_TEAM = "Team";
    private static final String KEY_IMAGE_PATH = "Image_Path";
    private static final String BUILDING_TABLE = "Building_Info";
    private FirebaseAuth mAuth;
    String URL, Key;
    public Context context;




    public DatabaseHelper(Context context, String table){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){

        Log.w("ON_CREATE DB HELPER:", "INSIDE DB ONCREATE");

        String CREATE_LOGO_TABLE = "CREATE TABLE IF NOT EXISTS " + LOGO_TABLE + "(\n"
                + KEY_TEAM + "TEXT PRIMARY KEY NOT NULL, \n"
                + KEY_IMAGE_PATH + " TEXT NOT NULL"
                + ");";

        db.execSQL(CREATE_LOGO_TABLE);

        mAuth = com.google.firebase.auth.FirebaseAuth.getInstance();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + "TABLENAME");
        Log.w("ON_UPGRADE: ", "System.Call");
        onCreate(db);
    }
    // Overloaded for manual calles without int args
    public void onUpgrade(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + "TABLENAME");

        onCreate(db);
    }




}
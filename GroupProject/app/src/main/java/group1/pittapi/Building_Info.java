package group1.pittapi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;

public class Building_Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_building__info);

        try {
            String info = getIntent().getExtras().getString("info");
            Gson gson = new Gson();
            UserEnterEvent uee = gson.fromJson(info, UserEnterEvent.class);

            // Depending on the value of uee.building_name we will show the appropriate info.
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private class UserEnterEvent {
        String timestamp;
        String building_name;
    }
}

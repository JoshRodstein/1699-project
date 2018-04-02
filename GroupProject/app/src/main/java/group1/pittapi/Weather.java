package group1.pittapi;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Weather extends AppCompatActivity {
    /*
    sun = 0
    cloud = 1
    snow = 2
    snow = 3
     */
    private int weather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        weather = 3;
        switch (weather) {
            case 0:
                ((ImageView)findViewById(R.id.cur_weather)).setImageDrawable(getResources().getDrawable(R.drawable.sunny));
                ((ImageView)findViewById(R.id.fade)).setBackgroundColor(Color.parseColor("#22ffff00"));
                break;
            case 1:
                ((ImageView)findViewById(R.id.cur_weather)).setImageDrawable(getResources().getDrawable(R.drawable.cloudy));
                ((ImageView)findViewById(R.id.fade)).setBackgroundColor(Color.parseColor("#22aaaaaa"));
                break;
            case 2:
                ((ImageView)findViewById(R.id.cur_weather)).setImageDrawable(getResources().getDrawable(R.drawable.snowy));
                ((ImageView)findViewById(R.id.fade)).setBackgroundColor(Color.parseColor("#22ffffff"));
                break;
            case 3:
                ((ImageView)findViewById(R.id.cur_weather)).setImageDrawable(getResources().getDrawable(R.drawable.rainy));
                ((ImageView)findViewById(R.id.fade)).setBackgroundColor(Color.parseColor("#220000ff"));
                break;
        }
    }
}

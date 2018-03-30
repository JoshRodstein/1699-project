package group1.pittapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class PittScores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitt_scores);

        ArrayList<ScoreData> scoreData;

        final ListView lv1 = (ListView) findViewById(R.id.scores_list_view);

        try {
            // Gets info of game
            Bundle extras = getIntent().getExtras();
            ScoreData sd = new ScoreData();
            sd.setPittScore(Integer.parseInt(extras.getString("PittScore")));
            sd.setOppScore(Integer.parseInt(extras.getString("OppScore")));
            sd.setOppName(extras.getString("OppName"));

            // Add game to list
            scoreData = new ArrayList<>();
            scoreData.add(sd);


            // Here we may add more games that we have in storage.
        } catch (NullPointerException e) {
            scoreData = getScoreData();
        }

        lv1.setAdapter(new ScoresAdapter(this, scoreData));
    }

    private ArrayList<ScoreData> getScoreData(){
        ArrayList<ScoreData> results = new ArrayList<ScoreData>();

        ScoreData sr1 = new ScoreData();
        sr1.setPittScore(0);
        sr1.setOppScore(0);
        sr1.setOppName("ABC");
        results.add(sr1);

        sr1 = new ScoreData();
        sr1.setPittScore(0);
        sr1.setOppScore(0);
        sr1.setOppName("ABC");
        results.add(sr1);

        sr1 = new ScoreData();
        sr1.setPittScore(0);
        sr1.setOppScore(0);
        sr1.setOppName("ABC");
        results.add(sr1);

        sr1 = new ScoreData();
        sr1.setPittScore(0);
        sr1.setOppScore(0);
        sr1.setOppName("ABC");
        results.add(sr1);

        return results;
    }
}


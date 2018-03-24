package group1.pittapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class PittScores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitt_scores);

        ArrayList<ScoreData> scoreData = getScoreData();

        final ListView lv1 = (ListView) findViewById(R.id.scores_list_view);
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


package group1.pittapi;

/**
 * Created by billyhinard on 3/23/18.
 */

public class ScoreData {
    private int pittScore = 0;
    private int oppScore = 0;
    private String oppName = "";

    public void setPittScore(int score) {
        this.pittScore = score;
    }

    public int getPittScore() {
        return pittScore;
    }

    public void setOppScore(int score) {
        this.oppScore = score;
    }

    public int getOppScore() {
        return oppScore;
    }

    public void setOppName(String name) {
        this.oppName = name;
    }

    public String getOppName() {
        return oppName;
    }


}

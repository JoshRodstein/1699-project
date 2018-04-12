package group1.pittapi;

import android.util.Log;

/**
 * Created by billyhinard on 3/23/18.
 */

public class ScoreData {
    private int pittScore = 0;
    private int oppScore = 0;
    private String oppName = "";

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    private String sport;

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

        // I know... this doesnt make sense... whatever
        for(team enumName : team.values()) {
            if (name.equalsIgnoreCase(enumName.toString())){
                oppName = enumName.toString();
                Log.w("OPPONENT FOUND: ", "Setting OPP Image");
            }
        }

        if (oppName.equals("")) {
            Log.w("OPPONENT NOT FOUND: ", "Reverting to plain text");
            oppName = name;
        }


    }

    public String getOppName() {
        return oppName;
    }

    public enum team {
        PENN_STATE,
        BOSTON_COLLEGE,
        CLEMSON,
        FLORIDA_STATE,
        LOUISVILLE,
        NC_STATE,
        NOTRE_DAME,
        SYRACUSE,
        WAKE_FOREST,
        DUKE,
        GEORGIA_TECH,
        MIAMI,
        UNC,
        VIRGINIA,
        VIRGINIA_TECH
    }


}

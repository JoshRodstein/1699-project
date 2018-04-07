package group1.pittapi;

/**
 * Created by josh on 4/6/18.
 */

public class KeyPair {
    private String KEY;
    private String VALUE;

    public KeyPair(String k, String v){
        this.KEY = k;
        this.VALUE = v;
    }

    public String getKey(){
        return this.KEY;
    }

    public String getValue() {
        return this.VALUE;
    }
}

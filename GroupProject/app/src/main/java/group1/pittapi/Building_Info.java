package group1.pittapi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

public class Building_Info extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null) {
            mAuth.signInAnonymously()
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.w("Pitt Weather ANON: ", "signInAnonymously:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Pitt Weather ANON: ", "signInAnonymously:failure", task.getException());
                                Toast.makeText(Building_Info.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });
        } else {
            Log.w("Building Info ANON: ", "signInAnonymously: USER SIGNED IN");
        }

        setContentView(R.layout.activity_building__info);

        try {
            String info = getIntent().getExtras().getString("info");
            Gson gson = new Gson();
            UserEnterEvent uee = gson.fromJson(info, UserEnterEvent.class);

            // TODO : Depending on the value of uee.building_name we will show the appropriate info.
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private class UserEnterEvent {
        String timestamp;
        String building_name;
    }

    public void onDestroy(){
        super.onDestroy();
        Log.w("ON_DESTROY", "Delete UserAuth");
        if(mAuth.getCurrentUser() != null) {mAuth.getCurrentUser().delete();}
    }

    /*public void onStop(){
        super.onStop();
        Log.w("ON_STOP", "Delete UserAuth");
        if(mAuth.getCurrentUser() != null) {mAuth.getCurrentUser().delete();}
    }*/
}

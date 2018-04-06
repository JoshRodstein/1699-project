package group1.pittapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

/**
 * Created by josh on 4/5/18.
 */

public class firebase_connector {
    private FirebaseAuth mAuth;
    final private String ANON_AUTH = "ANON_AUTH: ";

    public firebase_connector() {


        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            //anonSignIn();
        }


    }


    }




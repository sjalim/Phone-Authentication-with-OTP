package com.example.alim;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    Button mSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSignOut = findViewById(R.id.Button_sign_out);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                sendUserLogin();
            }
        });
    }

    private void sendUserLogin(){
        Intent HomeiNtent = new Intent(MainActivity.this,PhoneNumber.class);
        HomeiNtent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        HomeiNtent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(HomeiNtent);
        finish();
    }




}


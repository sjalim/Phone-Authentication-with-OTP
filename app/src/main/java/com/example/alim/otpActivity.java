package com.example.alim;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class otpActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private  EditText mOtpText;
    private Button mVerifybtn;

    private ProgressBar verifyProgressBar;
    private String mAuthVerificationId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        mOtpText = findViewById(R.id.verificattion_code_et);
        mVerifybtn = findViewById(R.id.Button_otp);
        verifyProgressBar = findViewById(R.id.otp_progress_bar);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mAuthVerificationId = getIntent().getStringExtra("AuthCredentials");

        mVerifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = mOtpText.getText().toString();
                if (otp.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please fill ",Toast.LENGTH_LONG).show();
                }else
                {
                    verifyProgressBar.setVisibility(View.VISIBLE);
                    Log.d("Print","7");

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mAuthVerificationId,otp);
                    signInWithOhoneAuthCredential(credential);
                }
            }
        });


    }
    private void signInWithOhoneAuthCredential(PhoneAuthCredential credential){
        Log.d("Print","8");
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(otpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            senduserHome();
                        }else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(getApplicationContext(),"Error in Verifying OTP",Toast.LENGTH_LONG).show();
                            }
                        }
                        verifyProgressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Print","10");

        if (mCurrentUser != null){
            senduserHome();
        }

    }

    private void senduserHome(){
        Log.d("Print","9");
        Intent HomeiNtent = new Intent(otpActivity.this,MainActivity.class);
        HomeiNtent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        HomeiNtent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(HomeiNtent);
        finish();
    }
}

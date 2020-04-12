package com.example.alim;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneNumber extends AppCompatActivity {



    private EditText mCountrycode;
    private EditText mPhoneNumber;

    private Button mGeneratbtn;
    private ProgressBar mLoginProgress;

    private FirebaseUser mCurrentUser;
    private  FirebaseAuth mAuth;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks McallBack;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        mCountrycode = findViewById(R.id.country_code_et);
        mPhoneNumber = findViewById(R.id.Mobile_Number_et);
        mGeneratbtn = findViewById(R.id.button_generate_otp);
        mLoginProgress = findViewById(R.id.generate_progress_bar);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();


        mGeneratbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("Print","1");
                String country_code = mCountrycode.getText().toString();

                String phone_number = mPhoneNumber.getText().toString();

                String complete_phone_number = "+"+country_code + phone_number;

                if (country_code.isEmpty() || phone_number.isEmpty() ){
                    Toast.makeText(getApplicationContext(),"Please Fill",Toast.LENGTH_LONG).show();
                }else {
                    mLoginProgress.setVisibility(View.VISIBLE);
                    Log.d("Print","2");

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            complete_phone_number,
                            60,
                            TimeUnit.SECONDS,
                            PhoneNumber.this,
                            McallBack
                    );
                }
            }
        });
        McallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredentials(phoneAuthCredential);
                Log.d("Print","3");
            }


            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                Toast.makeText(getApplicationContext(),"verification Failed "+e,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.d("Print","4");

                Intent otpIntent = new Intent(PhoneNumber.this,otpActivity.class);
                otpIntent.putExtra("AuthCredentials",s);
                startActivity(otpIntent);

            }
        };



    }

    private void signInWithPhoneAuthCredentials(PhoneAuthCredential credential){
        Log.d("Print","5");
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(PhoneNumber.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            sendUserhome();
                        }else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(getApplicationContext(),"error In verifying OTP",Toast.LENGTH_LONG).show();
                            }
                        }
                        mLoginProgress.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void sendUserhome(){
        Log.d("Print","6");
        Intent homeIntent = new Intent(PhoneNumber.this,MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Print","11");

        if (mCurrentUser != null){
            sendUserhome();
        }

    }
}

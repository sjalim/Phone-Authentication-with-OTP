package com.example.alim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class registerActivity extends AppCompatActivity {
    EditText nameEditText, ageEditText, addEditText;
    Button signupButton;

    final String TAG = "registerActivity";
    FirebaseFirestore db;
    String phoneNumber="01620109084";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameEditText = findViewById(R.id.nameEditText);
        ageEditText = findViewById(R.id.ageEditText);
        addEditText = findViewById(R.id.addressEditText);
        signupButton = findViewById(R.id.signupButton);

        db = FirebaseFirestore.getInstance();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
            }
        });





    }

    private void setData() {

        Map<String, Object> user = new HashMap<>();
        user.put("name",nameEditText.getText().toString());
        user.put("age",ageEditText.getText().toString());
        user.put("phone",phoneNumber);
        user.put("address",addEditText.getText().toString());

        db.collection("users").document(phoneNumber).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(registerActivity.this,"You are registered!",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(registerActivity.this,"Error Occurred!",Toast.LENGTH_SHORT).show();

                    }
                });


    }


}

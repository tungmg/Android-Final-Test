package com.example.android_final_test.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android_final_test.Home;
import com.example.android_final_test.R;
import com.example.android_final_test.model.Private;
import com.example.android_final_test.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignIn extends AppCompatActivity implements View.OnClickListener{

    Button btnSignIn;
    EditText editPhone, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        init();
        btnSignIn.setOnClickListener(this);
    }

    public void init(){
        btnSignIn = findViewById(R.id.btnSignIn);
        editPassword = findViewById(R.id.editPassword);
        editPhone = findViewById(R.id.editPhone);
    }

    @Override
    public void onClick(View v) {
        final ProgressDialog progressDialog = new ProgressDialog(SignIn.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        if(v == btnSignIn){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference documentReference = db.collection("User").document(editPhone.getText().toString());
                    documentReference.get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    progressDialog.dismiss();
                                    User user = new User(document.getString("Name"), document.getString("Password"));
                                    if(document.getString("Password").equals(editPassword.getText().toString())){
                                        Intent intentHome = new Intent(SignIn.this, Home.class);
                                        Private.currentUser = user;
                                        startActivity(intentHome);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(SignIn.this, "Wrong password", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    progressDialog.dismiss();
                                    Toast.makeText(SignIn.this, "No document", Toast.LENGTH_LONG).show();
                                }
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(SignIn.this, "Get failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}

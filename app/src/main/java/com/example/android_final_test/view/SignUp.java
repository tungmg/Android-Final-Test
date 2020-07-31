package com.example.android_final_test.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android_final_test.R;
import com.example.android_final_test.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    MaterialEditText editPhone, editName, editPassword, editReenterPassword;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        btnSignUp.setOnClickListener(this);
    }

    public void init(){
        btnSignUp = findViewById(R.id.btnSignUp);
        editPassword = findViewById(R.id.editPassword);
        editPhone = findViewById(R.id.editPhone);
        editName = findViewById(R.id.editName);
        editReenterPassword = findViewById(R.id.editReenterPassword);
    }

    @Override
    public void onClick(View v) {
        final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        final String name = editName.getText().toString();
        final String phone = editPhone.getText().toString();
        final String password = editPassword.getText().toString();
        final String reenterPassword = editReenterPassword.getText().toString();

        if(v == btnSignUp){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            final DocumentReference documentReference = db.collection("User").document(editPhone.getText().toString());
            documentReference
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    progressDialog.dismiss();
                                    System.out.println(document.getData());
                                    Toast.makeText(SignUp.this, "Account existed", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    progressDialog.dismiss();
                                    if(!name.equals("") && !phone.equals("") && !password.equals("") && password.equals(reenterPassword)){
                                        User user = new User(name, password);
                                        documentReference.set(user)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(SignUp.this, "Regist sucessfully", Toast.LENGTH_LONG).show();
                                                        finish();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(SignUp.this, "Regist failed", Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                    }
                                }
                            }
                            else {
                                Toast.makeText(SignUp.this, "Get failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}

package com.example.android_final_test.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.android_final_test.viewModel.ViewModelInfor;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.example.android_final_test.R;
import com.example.android_final_test.model.Private;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rengwuxian.materialedittext.MaterialEditText;

public class EditFragment extends Fragment implements View.OnClickListener{
    MaterialEditText name;
    Button btnConfirm;
    ViewModelInfor viewModelInfor;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_user_edit, container, false);
        name = view.findViewById(R.id.editName);
        name.setText(Private.currentUser.getName());
        btnConfirm = view.findViewById(R.id.confirmEdit);
        btnConfirm.setOnClickListener(this);

        viewModelInfor = new ViewModelProvider(getActivity()).get(ViewModelInfor.class);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == btnConfirm){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            final DocumentReference documentReference = db.collection("User").
                    document(Private.currentUser.getPhone());
            documentReference
                    .update("Name", name.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Private.currentUser.setName(name.getText().toString());
                            viewModelInfor.updateUser(Private.currentUser);
                            Toast.makeText(getContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Updated Failed", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }
}

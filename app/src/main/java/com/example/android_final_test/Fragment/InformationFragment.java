package com.example.android_final_test.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.android_final_test.R;
import com.example.android_final_test.model.Private;
import com.example.android_final_test.model.User;
import com.example.android_final_test.viewModel.ViewModelInfor;

import org.w3c.dom.Text;

public class InformationFragment extends Fragment {
    TextView name, phone;
    ViewModelInfor viewModelInfor;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_user_information, container, false);
        name = view.findViewById(R.id.editPhone);
        phone = view.findViewById(R.id.editName);
        name.setText(Private.currentUser.getName());

        viewModelInfor = new ViewModelProvider(getActivity()).get(ViewModelInfor.class);
        LiveData<User> newInfor = viewModelInfor.initUser(Private.currentUser);
        newInfor.observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                name.setText(user.getName());
            }
        });

        return view;
    }
}

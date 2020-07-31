package com.example.android_final_test.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_final_test.model.User;

public class ViewModelInfor extends androidx.lifecycle.ViewModel {

    private MutableLiveData<User> userMutableLiveData;

    public MutableLiveData<User> initUser(User user){
        if(userMutableLiveData == null){
            userMutableLiveData = new MutableLiveData<>();
            userMutableLiveData.postValue(user);
        }
        return userMutableLiveData;
    }

    public void updateUser(User user){
        if(userMutableLiveData!=null){
            userMutableLiveData.postValue(user);
        }
    }

}

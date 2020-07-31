package com.example.android_final_test.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_final_test.Database.Database;
import com.example.android_final_test.model.Order;
import com.example.android_final_test.view.FoodDetail;

import java.util.List;

public class ViewModelCartCount extends ViewModel {

    private MutableLiveData<Integer> noOfCart;

    public MutableLiveData<Integer> init(Integer integer){
        if(noOfCart==null){
            noOfCart = new MutableLiveData<>();
            noOfCart.postValue(integer);
        }
        return noOfCart;
    }
//
//    public MutableLiveData<Integer> getCartCount(){
//        if(noOfCart == null){
//            noOfCart = new MutableLiveData<>();
//        }
//        return noOfCart;
//    }

    public void addCart(){
        if(noOfCart!=null){
            noOfCart.postValue(noOfCart.getValue() + 1);
        }
    }

}

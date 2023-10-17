package com.prueba.retrofitjava;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {


    MutableLiveData<Integer> progressMutableData = new MutableLiveData<>();
    MutableLiveData<String> mDrinksMutableData = new MutableLiveData<>();
    MutableLiveData<String> loginMutableData = new MutableLiveData<>();

    MainRepository repository;

    public MainViewModel() {
        progressMutableData.postValue(View.INVISIBLE);
        mDrinksMutableData.postValue("");
        loginMutableData.postValue("Not logged in");
        repository = new MainRepository();
    }

    public void login(String email, String password) {
        progressMutableData.postValue(View.VISIBLE);
        loginMutableData.postValue("Checking");
        repository.loginRemote(new LoginModel(email, password), new MainRepository.ILoginResponse() {
            @Override
            public void onResponse(LoginResponse loginResponse) {
                progressMutableData.postValue(View.INVISIBLE);
                loginMutableData.postValue("Login Success");
            }

            @Override
            public void onFailure(Throwable t) {
                progressMutableData.postValue(View.INVISIBLE);
                loginMutableData.postValue("Login failure: " + t.getLocalizedMessage());
            }
        });
    }

    public void suggestNewDrink() {
        progressMutableData.postValue(View.VISIBLE);
        repository.suggestNewDrink(new MainRepository.IDrinkCallback() {
            @Override
            public void onDrinkSuggested(String drinkName) {
                progressMutableData.postValue(View.INVISIBLE);
                mDrinksMutableData.postValue(drinkName);
            }

            @Override
            public void onErrorOccurred() {
                progressMutableData.postValue(View.INVISIBLE);
                // Show toast with error
            }
        });
    }

    public LiveData<Integer> getProgress() {
        return progressMutableData;
    }

    public LiveData<String> getLoginData() {
        return loginMutableData;
    }

    public LiveData<String> getDrink() {
        return mDrinksMutableData;
    }

}

package com.prueba.retrofitjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvDrinkName, tvLoginResult;
    EditText etEmail, etPassword;
    Button bLogin;
    ProgressBar progressBar;
    Button bGetDrink;

    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUi();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setupViewModelObserver();
    }

    private void setupUi() {
        tvDrinkName = findViewById(R.id.tvDrinkName);
        progressBar = findViewById(R.id.progressBar);
        bGetDrink = findViewById(R.id.bGetDrink);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvLoginResult = findViewById(R.id.tvLoginResult);
        bLogin = findViewById(R.id.bLogin);
    }

    private void setupViewModelObserver() {
        viewModel.getProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                progressBar.setVisibility(integer);
            }
        });

        viewModel.getLoginData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvLoginResult.setText(s);
            }
        });

        viewModel.getDrink().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String drinkName) {
                tvDrinkName.setText(drinkName);
            }
        });

        bGetDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.suggestNewDrink();
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.login(etEmail.getText().toString(), etPassword.getText().toString());
            }
        });
    }

}
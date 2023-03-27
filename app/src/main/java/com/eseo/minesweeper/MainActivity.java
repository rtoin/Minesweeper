package com.eseo.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eseo.minesweeper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.button.setOnClickListener(this);
        binding.button2.setOnClickListener(this);
        binding.button3.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        int ID = view.getId();
        if(ID == binding.button.getId()) {
            // Easy Button
            //my_function(); with number of mines and size as parameters
        }
        else if(ID == binding.button2.getId()) {
            // Normal Button
            //my_function(); with number of mines and size as parameters
        }
        else if(ID == binding.button3.getId()){
            // Hardcore Button
            //my_function(); with number of mines and size as parameters
        }
        // Here the layers should be created with correct size
        startActivity(new Intent(this, DetailActivity.class));

    }
}
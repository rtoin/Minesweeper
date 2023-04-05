package com.eseo.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eseo.minesweeper.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity   {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = new Intent(MainActivity.this, GameActivity.class);

        //"Easy" button
        binding.easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("gridSize",5);
                intent.putExtra("nbrBombs",3);
                startActivity(intent);
            }
        });

        //"Normal" button
        binding.normalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("gridSize",7);
                intent.putExtra("nbrBombs",6);
                startActivity(intent);
            }
        });

        //"Hard" button
        binding.hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("gridSize",9);
                intent.putExtra("nbrBombs",10);
                startActivity(intent);
            }
        });
    }
}
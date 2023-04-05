package com.eseo.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eseo.minesweeper.databinding.ActivityMainBinding;

/**
 * Activity for the main page
 *
 * Is the landing page of the app and offers a difficulty choice to the player.
 */
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
                int gridSize = 5;
                int nbrBombs = 3;
                Bundle bundle = new Bundle();
                bundle.putSerializable("gridSize",gridSize);
                bundle.putSerializable("nbrBombs",nbrBombs);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        //"Medium" button
        binding.normalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int gridSize = 7;
                int nbrBombs = 6;
                Bundle bundle = new Bundle();
                bundle.putSerializable("gridSize",gridSize);
                bundle.putSerializable("nbrBombs",nbrBombs);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //"Hard" button
        binding.hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("gridSize",12);
                intent.putExtra("nbrBombs",25);
                startActivity(intent);
            }
        });


    }
}
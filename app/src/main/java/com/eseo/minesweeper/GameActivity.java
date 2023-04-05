package com.eseo.minesweeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.eseo.minesweeper.databinding.ActivityMainBinding;
import com.eseo.minesweeper.databinding.ActivityGameBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private ActivityGameBinding binding;
    private BoardGrid grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        int gridSize = intent.getIntExtra("gridSize",5);
        int nbrBombs = intent.getIntExtra("nbrBombs",5);

        //Set the grid layout size
        binding.boardGrid.setColumnCount(gridSize);
        binding.boardGrid.setRowCount(gridSize);

        grid = new BoardGrid(gridSize);
        grid.initBombs(nbrBombs);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for(TileFragment t : grid.getTiles()) {
            ft.add(R.id.board_grid, t);
        }
        ft.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
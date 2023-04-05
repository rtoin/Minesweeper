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

public class GameActivity extends AppCompatActivity {

    private ActivityGameBinding binding;
    private List<TileFragment> tiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Get game parameters from the difficulty selected on the home page
        Intent intent = getIntent();
        int gridSize = intent.getIntExtra("gridSize",5);
        int nbrBombs = intent.getIntExtra("nbrBombs",5);

        //File the grid
        binding.boardGrid.setColumnCount(gridSize);
        binding.boardGrid.setRowCount(gridSize);
        tiles = new ArrayList<>();

        int line = 1, column = 1;
        for(int i=0; i<gridSize*gridSize; i++) {
            line = (i % gridSize) + 1;
            column = (i / gridSize) + 1;
            tiles.add(TileFragment.newInstance(line, column));
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for(TileFragment t : tiles) {
            ft.add(R.id.board_grid, t);
        }
        ft.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
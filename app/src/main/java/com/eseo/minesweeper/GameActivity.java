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
    private List<TileFragment> tiles;

    private int gridSize;
    private int nbrBombs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initGrid();
        initBombs();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Initialise the grid
     *
     * Determine the number of lines and columns for the grid from the difficulty
     * Populate the grid with tiles
     * Provide to each tile its coordinates in the grid
     */
    protected void initGrid() {
        //Get grid size parameter from the difficulty selected on the home page
        Intent intent = getIntent();
        gridSize = intent.getIntExtra("gridSize",5);

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

    /**
     * Initialise the bombs
     *
     * Determine the number of bombs from the difficulty.
     * Draw random bomb positions and assign the bombs to the tiles.
     */
    protected void initBombs() {
        //Get number of bombs from the difficulty selected on the home page
        Intent intent = getIntent();
        nbrBombs = intent.getIntExtra("nbrBombs",5);

        ArrayList<Integer> bombPositions = new ArrayList<>();

        //Draw random bomb positions
        Random rand = new Random();
        while(bombPositions.size() < nbrBombs) {
            int int_random = rand.nextInt(gridSize*gridSize);
            if(!bombPositions.contains(int_random)) {
                bombPositions.add(int_random);
            }
        }

        //Place the bombs in the tiles
        for(int i=0; i<bombPositions.size(); i++) {
            Log.d("Bomb: ", ""+bombPositions.get(i));
            tiles.get(bombPositions.get(i)).setBomb(true);
        }
    }
}
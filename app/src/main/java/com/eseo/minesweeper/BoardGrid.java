package com.eseo.minesweeper;

import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.FragmentTransaction;

import com.eseo.minesweeper.databinding.ActivityGameBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoardGrid {

    private ActivityGameBinding binding;
    private List<TileFragment> tiles;

    public List<TileFragment> getTiles() {
        return tiles;
    }

    public int getGridSize() {
        return gridSize;
    }

    public int getNbrBombs() {
        return nbrBombs;
    }

    private int gridSize;
    private int nbrBombs;

    public BoardGrid(int size) {
        this.gridSize = size;

        //Fill the board with tiles
        tiles = new ArrayList<>();
        for(int i=0; i<gridSize*gridSize; i++) {
            int line = (i % gridSize) + 1;
            int column = (i / gridSize) + 1;
            tiles.add(TileFragment.newInstance(line, column));
        }
    }

    /**
     * Initialise the bombs
     *
     * Determine the number of bombs from the difficulty.
     * Draw random bomb positions and assign the bombs to the tiles.
     */
    protected void initBombs(int nbrBombs) {
        this.nbrBombs = nbrBombs;

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

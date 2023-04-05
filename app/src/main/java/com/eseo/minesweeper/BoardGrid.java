package com.eseo.minesweeper;

import android.content.Intent;
import android.service.quicksettings.Tile;
import android.util.Log;

import androidx.fragment.app.FragmentTransaction;

import com.eseo.minesweeper.databinding.ActivityGameBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoardGrid {

    private ActivityGameBinding binding;

    //The list of tiles forming the board
    private List<TileFragment> tiles;

    public List<TileFragment> getTiles() {
        return tiles;
    }

    private int gridSize;
    private int nbrBombs;

    /**
     * Initialize the board
     *
     * Attribute a coordinate for each tile depending on the grid size
     * @param size Size of the grid (a square)
     */
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
     * Draw random bomb positions and assign the bombs to the tiles.
     * @param nbrBombs Number of bombs to place
     */
    public void initBombs(int nbrBombs) {
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
            tiles.get(bombPositions.get(i)).setValue(TileFragment.BOMB);
        }

        //Calculate the number of bombs in proximity to each tile
        //i.e. the value of each tile
        for(int x=1; x <= gridSize; x++) {
            for(int y=1; y <= gridSize; y++) {
                if(getTile(x, y).getValue() != TileFragment.BOMB) {
                    List<TileFragment> adjacentTiles = adjacentTiles(x, y);

                    int cntBombs = 0;
                    for(TileFragment tile: adjacentTiles) {
                        if(tile.getValue() == TileFragment.BOMB) {
                            cntBombs++;
                        }
                    }
                    getTile(x, y).setValue(cntBombs);
                }
            }
        }
    }

    /**
     * Reveal all bombs (for after a game over)
     */
    public void revealAllBombs() {
        for(TileFragment tile: tiles) {
            if(tile.getValue() == TileFragment.BOMB) {
                tile.setBomb();
            }
        }
    }

    /**
     * Returns tile at coordinates (x;y)
     *
     * @param x Horizontal coordinate on the game board (between 1 and gridSize)
     * @param x Vertical coordinate on the game board (between 1 and gridSize)
     * @return The instance of TileFragment located at coordinates (x;y)
     */
    public TileFragment getTile(int x, int y) {
        if(x < 1 || x > gridSize || y < 1 || y > gridSize) {
            return null;
        }
        return tiles.get(x -1 + (y-1) * gridSize);
    }

    /**
     * Returns a list of TileFragment located near the tile at coordinates (x;y)
     *
     * @param x Horizontal coordinate on the game board (between 1 and gridSize)
     * @param x Vertical coordinate on the game board (between 1 and gridSize)
     * @return A list of TileFragment located near the tile at coordinates (x;y)
     */
    public List<TileFragment> adjacentTiles(int x, int y) {
        List<TileFragment> adjacentTiles = new ArrayList<>();

        List<TileFragment> tempTilesList = new ArrayList<>();
        //Tiles on the left
        tempTilesList.add(getTile(x-1, y+1));
        tempTilesList.add(getTile(x-1, y));
        tempTilesList.add(getTile(x-1, y-1));
        //Tiles above and under
        tempTilesList.add(getTile(x, y+1));
        tempTilesList.add(getTile(x, y-1));
        //Tiles on the right
        tempTilesList.add(getTile(x+1, y+1));
        tempTilesList.add(getTile(x+1, y));
        tempTilesList.add(getTile(x+1, y-1));

        //Exclude null values (i.e. tiles not found because out of bounds)
        for(TileFragment tile: tempTilesList) {
            if(tile != null) {
                adjacentTiles.add(tile);
            }
        }

        return adjacentTiles;
    }

    public int getGridSize() {
        return gridSize;
    }

    public int getNbrBombs() {
        return nbrBombs;
    }
}

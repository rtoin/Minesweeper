package com.eseo.minesweeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.service.quicksettings.Tile;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eseo.minesweeper.databinding.ActivityMainBinding;
import com.eseo.minesweeper.databinding.ActivityGameBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Activity for the game page
 *
 * Is responsible of managing most of the highlevel aspects of the game like
 * the score, the counters, etc..
 */
public class GameActivity extends AppCompatActivity {

    private ActivityGameBinding binding;
    private BoardGrid grid;

    private boolean isGameOver;
    private int bombsCounter;
    private int flagCounter;

    private Intent intentService;
    static public final String BROADCAST = "com.eseo.minesweeper.timer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Initialize some variables
        intentService = new Intent(this,Timer.class);
        Intent intent = getIntent();
        int gridSize = intent.getIntExtra("gridSize",5);
        bombsCounter = intent.getIntExtra("nbrBombs",3);
        flagCounter = 0;
        isGameOver = false;
        updateFlagCounterDisplay();

        //Set the grid layout size
        binding.boardGrid.setColumnCount(gridSize);
        binding.boardGrid.setRowCount(gridSize);

        //Initialize the grid and populate it with tiles
        grid = new BoardGrid(gridSize);
        grid.initBombs(bombsCounter);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for(TileFragment t : grid.getTiles()) {
            ft.add(R.id.board_grid, t);
        }
        ft.commit();

        /**
         * Add listener on smiley
         *
         * Like in the original game, clicking the smiley will reset the game
         */
        TextView smiley = binding.smiley;
        smiley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Purge previous fragments
                for(TileFragment t : grid.getTiles()) {
                    getSupportFragmentManager().beginTransaction().remove(t).commit();
                }

                //Re-init game variables
                grid = new BoardGrid(gridSize);
                grid.initBombs(bombsCounter);
                flagCounter = 0;
                updateFlagCounterDisplay();
                isGameOver = false;
                checkEndGame();

                //Populate the new grid with new fragments
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                for(TileFragment t : grid.getTiles()) {
                    ft.add(R.id.board_grid, t);
                }
                ft.commit();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        startService(intentService);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver,new IntentFilter(BROADCAST));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    public void onBackPressed() {
        Log.d("GameAct", "onBackPressed: BACK Pressed");
        stopService(intentService);
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopService(intentService);
    }

    /**
     * Called when a tile is clicked
     *
     * Perform a reveal of the clicked tile and check for win conditions
     */
    protected void onTileClick(int x, int y) {
        if(!isVictory() && !isGameOver()) {
            reveal(grid.getTile(x, y));
        }

        checkEndGame();
    }

    /**
     * Called when a tile is pressed for a longer moment
     *
     * Add or remove a flag on the clicked tile
     */
    protected void onTileLongClick(int x, int y) {
        if(!isVictory() && !isGameOver()) {
            flag(grid.getTile(x, y));
        }
    }

    /**
     * Reveal function for when a tile is cliked
     *
     * Will reveal all bombs if one was clicked.
     * Will propagate the reveal to nearby tiles if the clicked tile was empty
     */
    public void reveal(TileFragment tile) {

        if(tile.getValue() == TileFragment.BOMB) {
            //Tile is a bomb
            isGameOver = true;
            grid.revealAllBombs();
        } else if (tile.getValue() == TileFragment.EMPTY) {
            //Tile is a zero
            List<TileFragment> toReveal = new ArrayList<>();    //List of tiles to reveal
            List<TileFragment> toCheckAdjacents = new ArrayList<>();    //List of tiles to check for potential reveal

            toCheckAdjacents.add(tile);

            //We analyse tiles as long as there are some eligible
            while (toCheckAdjacents.size() > 0) {
                TileFragment t = toCheckAdjacents.get(0);
                for(TileFragment adjacent: grid.adjacentTiles(t.getX(), t.getY())) {
                    //Check for nearby empty tiles
                    if(adjacent.getValue() == TileFragment.EMPTY) {
                        if (!toReveal.contains(adjacent)) {
                            if (!toCheckAdjacents.contains(adjacent)) {
                                toCheckAdjacents.add(adjacent);
                            }
                        }
                    } else {
                        if (!toReveal.contains(adjacent)) {
                            toReveal.add(adjacent);
                        }
                    }
                }
                toCheckAdjacents.remove(t);
                toReveal.add(t);
            }

            //Reveal all the identified tiles
            for (TileFragment t: toReveal) {
                t.setText(String.valueOf(t.getValue()));
                t.setRevealed(true);
            }
        } else {
            //Tile is greater than 0, only reveal its value
            tile.setText(String.valueOf(tile.getValue()));
            tile.setRevealed(true);
        }
    }

    /**
     * Put or remove a flag on the tile
     */
    public void flag(TileFragment tile) {
        //Update tile
        tile.setFlagged(!tile.isFlagged());

        //Update flag counter
        int cnt = 0;
        for(TileFragment t: grid.getTiles()) {
            if(t.isFlagged()) {
                cnt++;
            }
        }
        flagCounter = cnt;

        updateFlagCounterDisplay();
    }

    /**
     * Update the flag counter in the top left of the screen
     */
    public void updateFlagCounterDisplay() {
        //Update flag counter display
        binding.flagCounter.setText(String.format("%03d", bombsCounter - flagCounter));
    }

    /**
     * Check for game over
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Check for victory
     */
    public boolean isVictory() {
        int remainingTiles = 0;
        for(TileFragment t: grid.getTiles()) {
            if(!t.isRevealed() && t.getValue() != TileFragment.BOMB && t.getValue() != TileFragment.EMPTY) {
                remainingTiles++;
            }
        }

        if(remainingTiles == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check for the end of the game and display victory message
     */
    public void checkEndGame() {
        if(isVictory()) {
            binding.result.setText("VICTORY");
            binding.smiley.setText(R.string.smiley_happy);
        } else if (isGameOver()) {
            binding.result.setText("DEFEAT");
            binding.smiley.setText(R.string.smiley_sad);
        } else {
            binding.result.setText("");
            binding.smiley.setText(R.string.smiley);
        }
    }

    private void timerCb() {
        Log.i("TIMER", "timerCb: POP");
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            timerCb();
        }
    };
}
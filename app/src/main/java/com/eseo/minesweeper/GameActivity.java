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

import com.eseo.minesweeper.databinding.ActivityMainBinding;
import com.eseo.minesweeper.databinding.ActivityGameBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        //Init some variables
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

        grid = new BoardGrid(gridSize);
        grid.initBombs(bombsCounter);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for(TileFragment t : grid.getTiles()) {
            ft.add(R.id.board_grid, t);
        }
        ft.commit();
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

    protected void onTileClick(int x, int y) {
        if(!isVictory() && !isGameOver()) {
            reveal(grid.getTile(x, y));
        }

        checkEndGame();
    }

    protected void onTileLongClick(int x, int y) {
        if(!isVictory() && !isGameOver()) {
            flag(grid.getTile(x, y));
        }
    }

    public void reveal(TileFragment tile) {

        if(tile.getValue() == TileFragment.BOMB) {
            //Tile is a bomb
            isGameOver = true;
            grid.revealAllBombs();
            binding.smiley.setText(R.string.smiley_sad);
        } else if (tile.getValue() == TileFragment.EMPTY) {
            //Tile is a zero
            List<TileFragment> toReveal = new ArrayList<>();
            List<TileFragment> toCheckAdjacents = new ArrayList<>();

            toCheckAdjacents.add(tile);

            while (toCheckAdjacents.size() > 0) {
                TileFragment t = toCheckAdjacents.get(0);
                for(TileFragment adjacent: grid.adjacentTiles(t.getX(), t.getY())) {
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

            for (TileFragment t: toReveal) {
                t.setText(String.valueOf(t.getValue()));
                t.setRevealed(true);
            }
        } else {
            //Tile is greater than 0
            tile.setText(String.valueOf(tile.getValue()));
            tile.setRevealed(true);
        }
    }

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

    public void updateFlagCounterDisplay() {
        //Update flag counter display
        binding.bombCounter.setText(String.format("%03d", bombsCounter - flagCounter));
    }

    public boolean isGameOver() {
        return isGameOver;
    }
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

    public void checkEndGame() {
        if(isVictory()) {
            binding.result.setText("VICTORY");
        } else if (isGameOver()) {
            binding.result.setText("DEFEAT");
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
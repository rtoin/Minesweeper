package com.eseo.minesweeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Init some variables
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int gridSize = (int) bundle.getSerializable("gridSize");
        int bombsCounter =(int) bundle.getSerializable("nbrBombs");
        flagCounter = 0;
        isGameOver = false;
        updateFlagCounterDisplay();

        binding.buttonscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int top1 = readLastScore("SCORE1");
                int top2 = readLastScore("SCORE2");
                int top3 = readLastScore("SCORE3");
                Log.d("test lecture Score1",""+top1);
                Log.d("test lecture Score2",""+top2);
                Log.d("test lecture Score3",""+top3);
                Intent intent2 = new Intent(GameActivity.this, ScoreBoard.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("top1",top1);
                bundle.putSerializable("top2",top2);
                bundle.putSerializable("top3",top3);
                intent2.putExtras(bundle);

                startActivity(intent2);
            }
        });


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

            int ancien_score1;
            int ancien_score2;
            int ancien_score3;
            ancien_score1= readLastScore("SCORE1");
            ancien_score2= readLastScore("SCORE2");
            ancien_score3= readLastScore("SCORE3");

            if (ancien_score1 !=1)
                if (ancien_score1 < score){
                    saveScore(this.score,"SCORE1"); //score<ancient_score2
                }
            if (ancien_score2 !=1)
                if ( (ancien_score1 > score) && (ancien_score2<score) ){
                    saveScore(this.score,"SCORE2"); //ancien_score2<score<ancient_score1
                }
            if (ancien_score3 !=1)
                if ( (ancien_score2 > score) && (ancien_score3<score) ){
                    saveScore(this.score,"SCORE3");      //ancien_score3<score<ancient_score2
                }
        } else if (isGameOver()) {
            binding.result.setText("DEFEAT");
        }
    }

    public void saveScore(int score,String text){
        SharedPreferences sharedPref = getSharedPreferences("application", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(text,score);
        editor.apply();
    }
    int readLastScore(String text) {
        SharedPreferences sharedPref = getSharedPreferences("application", Context.MODE_PRIVATE);
        return sharedPref.getInt(text, -1);
    }

}
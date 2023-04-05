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
    public static final int TILE_COUNT = 9;
    private List<TileFragment> tiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        boolean var1 = intent.getBooleanExtra("var1",false);
        boolean var2 = intent.getBooleanExtra("var2",false);

        tiles = new ArrayList<>();
        for(int i=0; i<TILE_COUNT; i++) {
            tiles.add(TileFragment.newInstance(i, 0, true));
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
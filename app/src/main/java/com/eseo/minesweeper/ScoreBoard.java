package com.eseo.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.eseo.minesweeper.databinding.ActivityGameBinding;
import com.eseo.minesweeper.databinding.ActivityScoreBoardBinding;

public class ScoreBoard extends AppCompatActivity {

    private ActivityScoreBoardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScoreBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        int top1 = (int) bundle.getSerializable("top1");
        int top2 =(int) bundle.getSerializable("top2");
        int top3 =(int) bundle.getSerializable("top3");

        binding.goldScore.setText(top1+"");
        binding.silverScore.setText(top2+"");
        binding.bronzeScore.setText(top3+"");
    }
}
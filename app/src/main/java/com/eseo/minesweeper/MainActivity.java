package com.eseo.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eseo.minesweeper.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity   {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }


    @Override
    public void onResume() {
        super.onResume();
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        boolean TOTO = new Boolean(true);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Easy Button
                //my_function(); with number of mines and size as parameters
                intent.putExtra("var1",TOTO);//bomb's position maybe
                intent.putExtra("var2",TOTO);// number of bombs
                startActivity(intent);


            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Normal Button
                //my_function(); with number of mines and size as parameters
                intent.putExtra("var1",TOTO); //bomb's position maybe
                intent.putExtra("var2",TOTO); // number of bombs
                startActivity(intent);


            }
        });
        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hardcore Button
                //my_function(); with number of mines and size as parameters
                intent.putExtra("var1",TOTO);//bomb's position maybe
                intent.putExtra("var2",TOTO);// number of bombs
                startActivity(intent);

            }
        });


    }
}
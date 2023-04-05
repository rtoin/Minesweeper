package com.eseo.minesweeper;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eseo.minesweeper.databinding.FragmentTileBinding;

public class TileFragment extends Fragment {

    private static final String ARG_XCOORDINATE = "xCoordinate";
    private static final String ARG_YCOORDINATE = "yCoordinate";

    private FragmentTileBinding binding;

    //Define the value of the tile, -1 for a bomb, the number of bombs in proximity otherwise
    public static final int BOMB = -1;
    private int value = 0;

    //Coordinates of the tile on the grid
    private int xCoordinate;
    private int yCoordinate;

    //Define the tile status, 1 if revealed, 0 if hidden
    private boolean isRevealed = false;
    //Define the tile flag, 1 if a flag is on the tile, 0 otherwise
    private boolean isFlagged = false;

    public TileFragment() {
    }

    /**
     * Create a new tile instance
     * This tile has no flag and is hidden by default
     *
     * @param xCoor Horizontal coordinate on the game board.
     * @param yCoor Vertical coordinate on the game board.
     * @return A new instance of fragment TileFragment.
     */
    public static TileFragment newInstance(int xCoor, int yCoor) {
        TileFragment fragment = new TileFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_XCOORDINATE, xCoor);
        args.putInt(ARG_YCOORDINATE, yCoor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Manage arguments
        if (getArguments() != null) {
            xCoordinate = getArguments().getInt(ARG_XCOORDINATE);
            yCoordinate = getArguments().getInt(ARG_YCOORDINATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTileBinding.inflate(inflater, container, false);

        binding.tileValue.setOnClickListener(new View.OnClickListener() {

            //To reveal the tile
            @Override
            public void onClick(View view) {
                Log.d("Click: ", "(" + getX() + ";" + getY() + ")" + " bombe ? " + BOMB);

                if (!isRevealed()) {
                    if (getValue() == BOMB) {
                        binding.tileValue.setText(R.string.bomb);
                    } else {
                        binding.tileValue.setText(String.valueOf(getValue()));
                    }
                    setRevealed(true);
                }
            }
        });

        binding.tileValue.setOnLongClickListener(new View.OnLongClickListener() {
            //To place a flag
            public boolean onLongClick(View view) {
                if(!isRevealed()) {
                    if(isFlagged()) {
                        binding.tileValue.setText("");
                        setFlagged(false);
                    } else {
                        binding.tileValue.setText(R.string.flag);
                        setFlagged(true);
                    }
                }
                return true;
            }
        });


        return binding.getRoot();
    }

    public void onResume() {
        super.onResume();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getX() {
        return xCoordinate;
    }

    public int getY() {
        return yCoordinate;
    }

    public boolean isRevealed() {
        return isRevealed;
    }
    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public boolean isFlagged() {
        return isFlagged;
    }
    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }
}
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

    //Coordinates of the tile on the grid
    private int xCoordinate;
    private int yCoordinate;
    //Define the tile content, 1 for a mine, 0 for empty
    private boolean isBomb = false;
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
            @Override
            public void onClick(View view) {
                Log.d("Click: ", "("+getX()+";"+getY()+")"+" bombe ? "+isBomb());
                if(isBomb()) {
                    binding.tileValue.setText(R.string.bomb);
                } else {
                    binding.tileValue.setText("X");
                }
            }
        });
        return binding.getRoot();
    }

    public void onResume() {
        super.onResume();
    }

    public void setText(String text) {
        binding.tileValue.setText(text);
    }

    public int getX() {
        return xCoordinate;
    }

    public int getY() {
        return yCoordinate;
    }

    public boolean isBomb() {
        return isBomb;
    }
    public void setBomb(boolean bomb) {
        isBomb = bomb;
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
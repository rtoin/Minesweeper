package com.eseo.minesweeper;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.service.quicksettings.Tile;
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
    public static final int EMPTY = 0;
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
                if (!isRevealed()) {
                    ((GameActivity) getActivity()).onTileClick(getX(), getY());
                }
            }
        });

        binding.tileValue.setOnLongClickListener(new View.OnLongClickListener() {
            //To place a flag
            public boolean onLongClick(View view) {
                if(!isRevealed()) {
                    ((GameActivity) getActivity()).onTileLongClick(getX(), getY());
                }
                return true;
            }
        });

        return binding.getRoot();
    }

    /**
     * Set the text of the tile
     * The images in the tiles are represented with emojis using a string as reference
     *
     * @param text The text to display in the tile
     */
    public void setText(String text) {
        binding.tileValue.setText(text);
    }

    /**
     * Set a bomb in the tile
     * Update the image in the tile and the background of the tile
     */
    public void setBomb() {
        binding.tileValue.setText(R.string.bomb);
        binding.tileValue.setBackgroundColor(Color.parseColor("#E81709"));
    }

    /**
     * Get and set the value of the tile
     * The value represents the actual content of the tile, not to be confused with the value to display
     */
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
        updateColor();
        isRevealed = revealed;
    }

    /**
     * Update the color of the text to display depending on the value in the tile
     */
    public void updateColor() {
        switch(getValue()) {
            case 1:
                binding.tileValue.setTextColor(Color.parseColor("#0000ff"));
                break;
            case 2:
                binding.tileValue.setTextColor(Color.parseColor("#008200"));
                break;
            case 3:
                binding.tileValue.setTextColor(Color.parseColor("#fe0000"));
                break;
            case 4:
                binding.tileValue.setTextColor(Color.parseColor("#000084"));
                break;
            case 5:
                binding.tileValue.setTextColor(Color.parseColor("#840000"));
                break;
            case 6:
                binding.tileValue.setTextColor(Color.parseColor("#840000"));
                break;
            case 7:
                binding.tileValue.setTextColor(Color.parseColor("#840084"));
                break;
            case 8:
            default:
                binding.tileValue.setTextColor(Color.parseColor("#757575"));
                break;
        }
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    /**
     * Set or remove the flag
     * Also update the text (emoji) displayed
     *
     * @param flagged The boolean value of the flag
     */
    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
        if(isFlagged) {
            binding.tileValue.setText(R.string.flag);
        } else {
            binding.tileValue.setText("");
        }
    }
}
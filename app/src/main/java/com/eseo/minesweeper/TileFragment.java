package com.eseo.minesweeper;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TileFragment extends Fragment {

    public enum TileDisplay {
        MALE,
        FEMALE
    }
    private static final String ARG_XCOORDINATE = "xCoordinate";
    private static final String ARG_YCOORDINATE = "yCoordinate";
    private static final String ARG_ISMINE = "isMine";

    //Coordinates of the tile on the grid
    private int xCoordinate;
    private int yCoordinate;
    //Define the tile content, 1 for a mine, 0 for empty
    private boolean isMine;
    //Define the tile status, 1 if revealed, 0 if hidden
    private boolean isRevealed;
    //Define the tile flag, 1 if a flag is on the tile, 0 otherwise
    private boolean isFlag;
    //Integer value to define the number of mines in proximity to the tile, range from 0 to 8
    private int proximity;

    public TileFragment() {
    }

    /**
     * Create a new tile instance
     * This tile has no flag and is hidden by default
     *
     * @param xCoor Horizontal coordinate on the game board.
     * @param yCoor Vertical coordinate on the game board.
     * @param isMine Contains a mine if 1, nothing otherwise.
     * @return A new instance of fragment TileFragment.
     */
    public static TileFragment newInstance(int xCoor, int yCoor, boolean isMine) {
        TileFragment fragment = new TileFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_XCOORDINATE, xCoor);
        args.putInt(ARG_YCOORDINATE, yCoor);
        args.putBoolean(ARG_ISMINE, isMine);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            xCoordinate = getArguments().getInt(ARG_XCOORDINATE);
            yCoordinate = getArguments().getInt(ARG_YCOORDINATE);
            isMine = getArguments().getBoolean(ARG_ISMINE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tile, container, false);
    }
}
package com.eseo.minesweeper;

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
    private static final String ARG_ISMINE = "isMine";

    private FragmentTileBinding binding;

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
        Log.d("Init", String.valueOf(xCoor));
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

        //Manage arguments
        if (getArguments() != null) {
            xCoordinate = getArguments().getInt(ARG_XCOORDINATE);
            yCoordinate = getArguments().getInt(ARG_YCOORDINATE);
            Log.d("Init: onCreate ", String.valueOf(getArguments().getInt(ARG_XCOORDINATE)));
            isMine = getArguments().getBoolean(ARG_ISMINE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTileBinding.inflate(inflater, container, false);
        binding.tileValue.setText("A");

        binding.tileValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tileValue.setText("B");
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

    public boolean isMine() {
        return isMine;
    }
}
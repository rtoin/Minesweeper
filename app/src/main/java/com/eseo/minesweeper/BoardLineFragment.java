package com.eseo.minesweeper;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class BoardLineFragment extends Fragment {

    private static final String ARG_NBRTILES = "nbrTiles";
    private static final String ARG_LINEID = "lineId";
    private List<TileFragment> tiles;

    public BoardLineFragment() {
    }

    /**
     * Create a new board line fragment
     *
     * @param nbrTiles Number of tiles contained in the board line.
     * @return A new instance of fragment BoardLineFragment.
     */
    public static BoardLineFragment newInstance(int lineId, int nbrTiles) {
        BoardLineFragment fragment = new BoardLineFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_LINEID, lineId);
        args.putInt(ARG_NBRTILES, nbrTiles);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tiles =  new ArrayList<>();

        if (getArguments() != null) {
            int lineId = getArguments().getInt(ARG_LINEID);
            int nbrTiles = getArguments().getInt(ARG_NBRTILES);
            for(int i = 0; i<nbrTiles; i++) {
                tiles.add(TileFragment.newInstance(i+1, lineId, false));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_board_line, container, false);
    }
}
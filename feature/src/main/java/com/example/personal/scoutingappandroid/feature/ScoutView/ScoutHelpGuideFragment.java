/*
 * Copyright (c) 2019
 * All rights reserved Amogh Mehta
 * Last Modified 1/7/19 9:22 PM
 */

package com.example.personal.scoutingappandroid.feature.ScoutView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.personal.scoutingappandroid.feature.Common.DBHelper;
import com.example.personal.scoutingappandroid.feature.Common.ScoutConstants;
import com.example.personal.scoutingappandroid.feature.Common.ScoutExport;
import com.example.personal.scoutingappandroid.feature.R;

import java.util.ArrayList;

public class ScoutHelpGuideFragment extends android.support.v4.app.Fragment {

    //ArrayList<ScoutViewMasterElement> scoutViewMasterElementArrayList = new ArrayList<ScoutViewMasterElement>();

    public ScoutHelpGuideFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);//let fragment manager to call OnCreateOptionsMenu for creating menu
        /*
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        */
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {

        }


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.user_guide_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        android.support.v7.app.ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("User Guide");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(readSharedPreferences())));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.master_menu_export, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            //Log.i("VenInboxFrag Item", item.toString());
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    void init()
    {


        ScoutExport scoutExport = new ScoutExport();
        String fileName;
        //fileName = scoutExport.createCSVFile(scoutViewMasterElementArrayList, selectorColorTransformer(readSharedPreferences()), getActivity());

        //TextView fileNameTextView = (TextView) getActivity().findViewById(R.id.scoutexportfilename);
        //fileNameTextView.setText(fileName);
    }

    public String selectorColorTransformer (String colorCode) {
        if (colorCode.equals(ScoutConstants.COLORS_RED_1)) {
            return ScoutConstants.COLORS_RED_1_NAME;
        } else if (colorCode.equals(ScoutConstants.COLORS_RED_2)) {
            return ScoutConstants.COLORS_RED_2_NAME;
        } else if (colorCode.equals(ScoutConstants.COLORS_BLUE_1)) {
            return ScoutConstants.COLORS_BLUE_1_NAME;
        } else if (colorCode.equals(ScoutConstants.COLORS_BLUE_2)) {
            return ScoutConstants.COLORS_BLUE_2_NAME;
        } else {
            return ScoutConstants.SCOUT_DATA_NOT_AVAILABLE_STRING;
        }
    }

    String readSharedPreferences() {

        String selectorColor = ScoutConstants.COLORS_BLUE_1;
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(ScoutConstants.SCOUT_DATA_SHAREDPREFERENCES_TAG, Context.MODE_PRIVATE);

        if (sharedPreferences != null) {
            selectorColor = sharedPreferences.getString(ScoutConstants.SCOUT_DATA_SHAREDPREFERENCES_SELECTOR_TAG, ScoutConstants.COLORS_BLUE_1);
            System.out.println("(readSharedPreferences)selectorColor = " + selectorColor );
        }

        return selectorColor;

    }


}


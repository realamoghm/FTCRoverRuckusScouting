/*
 * Copyright (c) 2019
 * All rights reserved Amogh Mehta
 * Last Modified 1/7/19 12:18 PM
 */

package com.example.personal.scoutingappandroid.feature.ScoutDetail;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.personal.scoutingappandroid.feature.Common.DBHelper;
import com.example.personal.scoutingappandroid.feature.Common.ScoutConstants;
import com.example.personal.scoutingappandroid.feature.R;
import com.example.personal.scoutingappandroid.feature.ScoutView.ScoutViewMasterElement;

import org.json.JSONException;
import org.json.JSONObject;


public class ScoutDetailFragment extends Fragment {


    private final static String APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTDETAILFRAGMENT_ID = "scoutDetailElementstring";

    private OnScoutDetailFragmentListener onScoutDetailFragmentListener;

    String ScoutViewMasterElementJSONString = null;
    ScoutViewMasterElement scoutViewMasterElement;


    public ScoutDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);  //Let Fragment manager to call OnCreateOptionsMenu for creating Menu
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnScoutDetailFragmentListener) {
            onScoutDetailFragmentListener = (OnScoutDetailFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnScoutDetailFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            ScoutViewMasterElementJSONString = savedInstanceState.getString(APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTDETAILFRAGMENT_ID);
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scout_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString(APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTDETAILFRAGMENT_ID, ScoutViewMasterElementJSONString);

        super.onSaveInstanceState(outState);

    }

    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Detail Info");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(readSharedPreferences())));
    }





    @Override
    public void onDetach() {
        super.onDetach();
        onScoutDetailFragmentListener = null;
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



    ///////////////////////////////////////////Handling Menu /////////////////////////////////////

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_detail_edit, menu);

        MenuItem menuItemEdit = menu.findItem(R.id.action_actionbaredit);
        menuItemEdit.setEnabled(true);

        Drawable drawable = menuItemEdit.getIcon();
        if(drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(getResources().getColor(R.color.tableheader), PorterDuff.Mode.SRC_IN);
        }


        MenuItem menuItemDelete = menu.findItem(R.id.action_actionbardelete);
        menuItemDelete.setEnabled(true);

        Drawable drawableDelete = menuItemDelete.getIcon();
        if(drawableDelete != null) {
            drawableDelete.mutate();
            drawableDelete.setColorFilter(getResources().getColor(R.color.tableheader), PorterDuff.Mode.SRC_IN);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //Log.i("onOptionsItemSelected", item.toString());
        if (id == android.R.id.home) {
            //Log.i("onOptionsItemSelected", "home");
            onScoutDetailFragmentListener.onPressesbackButton();
            return true;
        } else if (id == R.id.action_actionbaredit) {
            //Log.i("onOptionsItemSelected", item.toString());
            onScoutDetailFragmentListener.DetailScoutUpdateButtonPressed();

            return true;
        } else if (id == R.id.action_actionbardelete) {
            //Log.i("onOptionsItemSelected", item.toString());
            deleteRecord(scoutViewMasterElement.getId());
            onScoutDetailFragmentListener.onPressesbackButton();

            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    void init()
    {
        //If view get rotated , need to build scoutViewMasterElement again
        updateScoutViewMasterJSONString(ScoutViewMasterElementJSONString);
        System.out.println("scoutViewMasterElement = " + scoutViewMasterElement);
        System.out.println("ScoutViewMasterElementJSONString = " + ScoutViewMasterElementJSONString);
        updateViewItems(scoutViewMasterElement);
    }


    public  void updateScoutViewMasterJSONString(String aMElementJSONString)
    {

        System.out.println("updateScoutViewMasterJSONString aMElementJSONString = " + aMElementJSONString);
        ScoutViewMasterElementJSONString = aMElementJSONString;

        System.out.println("updateScoutViewMasterJSONString ScoutViewMasterElementJSONString = " + ScoutViewMasterElementJSONString);

        try {
            JSONObject jsonObject = new JSONObject(ScoutViewMasterElementJSONString);

            scoutViewMasterElement = new ScoutViewMasterElement();
            scoutViewMasterElement.setWithJsonString(jsonObject);

        } catch (JSONException e) {
            //Can not parse the result
            //Show error to user
            System.out.println("Unable to parse result (ScoutDetailFragment)");
        }

        System.out.println("updateScoutViewMasterJSONString scoutViewMasterElement = " + scoutViewMasterElement);
    }

    ///////////////////////////Function to send value to activity /////////////////////////////////////
    //since Fragment is Activity dependent, you need to pass the information using interface to activity
    //Interface to pass the ScoutDeailFragment to activity
    public interface OnScoutDetailFragmentListener
    {
        //// This the case when user is on ScoutDetailFragmanet page and pressed back button.
        public void onPressesbackButton( );
        public void DetailScoutUpdateButtonPressed();       //On Update button pressed
    }

    //Update the Fragment View
    void updateViewItems(ScoutViewMasterElement scoutViewMasterElement) {

        System.out.println("updateViewItems scoutViewMasterElement = " + scoutViewMasterElement);


        TextView teamNameDetailField = (TextView) getActivity().findViewById(R.id.scoutviewteamname);
        teamNameDetailField.setText(scoutViewMasterElement.getTeamName());

        TextView teamNumberDetailField = (TextView) getActivity().findViewById(R.id.scoutviewteamnumber);
        teamNumberDetailField.setText(scoutViewMasterElement.getTeamNumber());

        TextView gameNumberDetailField = (TextView) getActivity().findViewById(R.id.scoutviewgamenumber);
        gameNumberDetailField.setText(scoutViewMasterElement.getGameNumber() + "");

        //Game Information Section
        TextView gameScoreDetailField = (TextView) getActivity().findViewById(R.id.scoutviewgamescore);
        gameScoreDetailField.setText(scoutViewMasterElement.getGameScore()+ "");

        TextView gameDateDetailField = (TextView) getActivity().findViewById(R.id.scoutviewgamedate);
        gameDateDetailField.setText(scoutViewMasterElement.getGameDateString());

        CheckBox landedDetailCheckBox = (CheckBox) getActivity().findViewById(R.id.checkbox_scoutviewlanded);
        if (scoutViewMasterElement.getLanded().equals(ScoutConstants.SCOUT_DATA_CHECKED)){
            //System.out.println("TBDdebuglandedchcked");
            landedDetailCheckBox.setChecked(true);
        } else {
            landedDetailCheckBox.setChecked(false);
            //System.out.println("TBDdebuglandedunchcked");
        }

        CheckBox sampledDetailCheckBox = (CheckBox) getActivity().findViewById(R.id.checkbox_scoutviewsampled);
        if (scoutViewMasterElement.getSampled().equals(ScoutConstants.SCOUT_DATA_CHECKED)){
            sampledDetailCheckBox.setChecked(true);
        } else {
            sampledDetailCheckBox.setChecked(false);
        }

        CheckBox claimedDepotDetailCheckBox = (CheckBox) getActivity().findViewById(R.id.checkbox_scoutviewclaimeddepot);
        if (scoutViewMasterElement.getClaimedDepot().equals(ScoutConstants.SCOUT_DATA_CHECKED)){
            claimedDepotDetailCheckBox.setChecked(true);
        } else {
            claimedDepotDetailCheckBox.setChecked(false);
        }

        CheckBox parkedDetailCheckBox = (CheckBox) getActivity().findViewById(R.id.checkbox_scoutviewparked);
        if (scoutViewMasterElement.getParked().equals(ScoutConstants.SCOUT_DATA_CHECKED)){
            parkedDetailCheckBox.setChecked(true);
        } else {
            parkedDetailCheckBox.setChecked(false);
        }

        //teleop section
        TextView depotScoredDetailField = (TextView) getActivity().findViewById(R.id.scoutviewdepotscored);
        depotScoredDetailField.setText(scoutViewMasterElement.getDepotScored()+ "");

        TextView silverLanderScoredDetailField = (TextView) getActivity().findViewById(R.id.scoutviewsliverlanderscored);
        silverLanderScoredDetailField.setText(scoutViewMasterElement.getSilverLanderScored()+ "");

        TextView goldLanderScoredDetailField = (TextView) getActivity().findViewById(R.id.scoutviewgoldlanderscored);
        goldLanderScoredDetailField.setText(scoutViewMasterElement.getGoldLanderScored()+ "");

        //endgame section
        CheckBox latchedDetailCheckBox = (CheckBox) getActivity().findViewById(R.id.checkbox_scoutviewlatched);
        if (scoutViewMasterElement.getLatched().equals(ScoutConstants.SCOUT_DATA_CHECKED)){
            latchedDetailCheckBox.setChecked(true);
        } else {
            latchedDetailCheckBox.setChecked(false);
        }

        CheckBox craterPartialDetailCheckBox = (CheckBox) getActivity().findViewById(R.id.checkbox_scoutviewcraterpartial);
        if (scoutViewMasterElement.getCraterPartial().equals(ScoutConstants.SCOUT_DATA_CHECKED)){
            craterPartialDetailCheckBox.setChecked(true);
        } else {
            craterPartialDetailCheckBox.setChecked(false);
        }

        CheckBox craterFullDetailCheckBox = (CheckBox) getActivity().findViewById(R.id.checkbox_scoutviewcraterfull);
        if (scoutViewMasterElement.getCraterFull().equals(ScoutConstants.SCOUT_DATA_CHECKED)){
            craterFullDetailCheckBox.setChecked(true);
        } else {
            craterFullDetailCheckBox.setChecked(false);
        }

        TextView commentScoredDetailField = (TextView) getActivity().findViewById(R.id.scoutviewcomments);
        commentScoredDetailField.setText(scoutViewMasterElement.getComment());


        System.out.println("updateViewItems scoutViewMasterElement.getComment() = " + scoutViewMasterElement.getComment());
    }

    public void deleteRecord (int id) {
        DBHelper mydb;
        mydb = new DBHelper(getActivity());
        mydb.deleteScoutElement( id);
        mydb.close();
    }
}

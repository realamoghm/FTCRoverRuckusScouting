/*
 * Copyright (c) 2019
 * All rights reserved Amogh Mehta
 * Last Modified 1/7/19 12:18 PM
 */

package com.example.personal.scoutingappandroid.feature;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.personal.scoutingappandroid.feature.Common.ScoutConstants;
import com.example.personal.scoutingappandroid.feature.ScoutDetail.ScoutDetailActivity;
import com.example.personal.scoutingappandroid.feature.ScoutView.ScoutExportFragment;
import com.example.personal.scoutingappandroid.feature.ScoutView.ScoutHelpGuideFragment;
import com.example.personal.scoutingappandroid.feature.ScoutView.ScoutViewMasterElement;
import com.example.personal.scoutingappandroid.feature.ScoutView.ScoutViewMasterFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ScoutViewMasterFragment.OnScoutViewMasterFragmentListener {
    //THINGS TO ADD
     //Add color selected to excel export sheet

    private final static String APPCOMPATACTIVITY_SAVEINSTANCESTATE_CURRENTFRAGMENTDISPLAYED_ID = "currentFragmentDisplayed";

    private final static String SCOUTVIEW_MAINACTIVITY_SCOUTVIEW_TAG = "ScoutViewMasterFragment";
    private final static String SCOUTVIEW_MAINACTIVITY_SCOUTVIEWCREATE_TAG = "ScoutViewCreateMasterFragment";
    private final static String SCOUTVIEW_MAINACTIVITY_SCOUTVIEWEXPORT_TAG = "ScoutViewExportMasterFragment";
    private final static String SCOUTVIEW_MAINACTIVITY_SCOUTVIEWUSERGUIDE_TAG = "ScoutViewUserGuideMasterFragment";

    //Different Fragments
    public final static int APPCOMPATACTIVITY_FRAGMENT_SCOUTVIEW = 1;
    public final static int APPCOMPATACTIVITY_FRAGMENT_SCOUTEXPORT = 2;
    public final static int APPCOMPACTACTIVITY_FRAGMENT_USERGUIDE = 3;

    int currentFragmentDisplayed = APPCOMPATACTIVITY_FRAGMENT_SCOUTVIEW;      //Default

    FragmentTransaction fragmentTransaction;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                //Log.i("FloatingActionButton", "FAB clicked");
                createNewScoutViewElement();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.mainActivity_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.

            //currentFragmentDisplayed overwrite if we have saved Instance of it
            if (savedInstanceState != null) {

                // Restore value of members from saved state
                currentFragmentDisplayed = savedInstanceState.getInt(APPCOMPATACTIVITY_SAVEINSTANCESTATE_CURRENTFRAGMENTDISPLAYED_ID);


                changeFragment(currentFragmentDisplayed, true);
            } else {
                changeFragment(currentFragmentDisplayed, false);
            }



            getSupportActionBar().setTitle(getResources().getString(R.string.ScoutView));       //Set the Action Bar title


        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

// Save the user's current game state
        outState.putInt(APPCOMPATACTIVITY_SAVEINSTANCESTATE_CURRENTFRAGMENTDISPLAYED_ID, currentFragmentDisplayed);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(readSharedPreferences())));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_create) {
            //handles create action in activity_main_drawer
            createNewScoutViewElement();
            item.setChecked(false);  //pass false to uncheck
        } else if (id == R.id.nav_view) {
            //handles view action in activity_main_drawer
            handleViewAction();
            item.setChecked(false);  //pass false to uncheck
        } else if (id == R.id.nav_export) {
            //handles export action in activity_main_drawer
            handleExportAction();
            item.setChecked(false);  //pass false to uncheck
        } else if (id == R.id.action_redone) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(ScoutConstants.COLORS_RED_1)));
            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(ScoutConstants.COLORS_RED_1)));
            saveSharedPreferences(ScoutConstants.COLORS_RED_1);
            item.setChecked(false);  //pass false to uncheck
        } else if (id == R.id.action_redtwo) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(ScoutConstants.COLORS_RED_2)));
            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(ScoutConstants.COLORS_RED_2)));
            saveSharedPreferences(ScoutConstants.COLORS_RED_2);
            item.setChecked(false);  //pass false to uncheck
        } else if (id == R.id.action_blueone) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(ScoutConstants.COLORS_BLUE_1)));
            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(ScoutConstants.COLORS_BLUE_1)));
            saveSharedPreferences(ScoutConstants.COLORS_BLUE_1);
            item.setChecked(false);  //pass false to uncheck
        } else if (id == R.id.action_bluetwo) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(ScoutConstants.COLORS_BLUE_2)));
            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(ScoutConstants.COLORS_BLUE_2)));
            saveSharedPreferences(ScoutConstants.COLORS_BLUE_2);
            item.setChecked(false);  //pass false to uncheck
        } else if (id == R.id.nav_user_guide) {
            handleUserGuideAction();
            item.setChecked(false);  //pass false to uncheck
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //ScoutViewMasterFragment.OnScoutViewMasterFragmentListener implemented
    @Override
    public void setScoutViewMasterElement(ScoutViewMasterElement scoutViewMasterElement) {
        //Log.i("setScoutViewMasEle Main", "detail record click");


        JSONObject jsonParam = new JSONObject();
        Boolean success = false;
        try {

            jsonParam = scoutViewMasterElement.getjsonMap();
            success = true;
        } catch (JSONException e) {
            //Show error to user
            //Show error to user
            Log.i("updateNewScoutAction", "json encoding failed");

        }
        if (success == true) {
            //Log.i("updateNewScoutAction", "json encoding was successful " + "jsonParam.toString() = " + jsonParam.toString());

            Intent i = new Intent(getApplicationContext(), ScoutDetailActivity.class);
            i.putExtra(ScoutConstants.IntentScountViewMasterElement, jsonParam.toString());
            i.putExtra(ScoutConstants.IntentScoutDetailFragmentName, ScoutDetailActivity.APPCOMPATACTIVITY_FRAGMENT_SCOUTDETAIL );
            startActivity(i);
        }


    }
    //ScoutViewMasterFragment.OnScoutViewMasterFragmentListener implemented
    @Override
    public void createNewScoutViewElement() {
        //Log.i("createNewScViewEle Main", "new Scout clicked");

        //Go to ScoutDetailActivity Activity for handling the Create new about the Scout
        Intent i = new Intent(getApplicationContext(), ScoutDetailActivity.class);
        i.putExtra(ScoutConstants.IntentScountViewMasterElement, ScoutConstants.SCOUT_DATA_NOT_AVAILABLE_STRING);
        i.putExtra(ScoutConstants.IntentScoutDetailFragmentName, ScoutDetailActivity.APPCOMPATACTIVITY_FRAGMENT_SCOUTDETAILCREATENEW );
        startActivity(i);



    }

    void changeFragment(int fragmentId, boolean isFromSavedInstanceState)       //isFromSavedInstanceState will be true when SavedInstanceState != null
    {
        switch (fragmentId) {
            case APPCOMPATACTIVITY_FRAGMENT_SCOUTVIEW: {

                if (isFromSavedInstanceState == false) {

                    //initialize frame layout
                    // Create an instance of ScoutViewMasterFragment
                    ScoutViewMasterFragment scoutViewMasterFragment = new ScoutViewMasterFragment();

                    // In case this activity was started with special instructions from an Intent,
                    // pass the Intent's extras to the fragment as arguments
                    scoutViewMasterFragment.setArguments(getIntent().getExtras());

                    //Initialize the FragmentTransaction
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();

                    //Make ScoutViewMaster as default fragment
                    fragmentTransaction.replace(R.id.mainActivity_container, scoutViewMasterFragment, "APPCOMPATACTIVITY_FRAGMENT_SCOUTVIEW"); //Add Tag for getting the reference back when action is completed in DetailActivity
                    fragmentTransaction.commit();


                } else {


                }
                //getSupportActionBar().setTitle(getResources().getString(R.string.ScoutDetail));
                currentFragmentDisplayed = APPCOMPATACTIVITY_FRAGMENT_SCOUTVIEW;
            }
            break;
            case APPCOMPATACTIVITY_FRAGMENT_SCOUTEXPORT: {

                if (isFromSavedInstanceState == false) {
                    //initialize frame layout
                    // Create an instance of ScoutViewMasterFragment
                    ScoutExportFragment scoutExportFragment = new ScoutExportFragment();

                    // In case this activity was started with special instructions from an Intent,
                    // pass the Intent's extras to the fragment as arguments
                    scoutExportFragment.setArguments(getIntent().getExtras());


                    //Initialize the FragmentTransaction
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();

                    //Make ScoutViewMaster as default fragment
                    fragmentTransaction.replace(R.id.mainActivity_container, scoutExportFragment, "APPCOMPATACTIVITY_FRAGMENT_SCOUTEXPORT"); //Add Tag for getting the reference back when action is completed in DetailActivity
                    fragmentTransaction.commit();
                } else {

                }

                getSupportActionBar().setTitle(getResources().getString(R.string.ScoutCreate));
                currentFragmentDisplayed = APPCOMPATACTIVITY_FRAGMENT_SCOUTEXPORT;
            }
            break;
            case APPCOMPACTACTIVITY_FRAGMENT_USERGUIDE: {

                if (isFromSavedInstanceState == false) {
                    //initialize frame layout
                    // Create an instance of ScoutViewMasterFragment
                    ScoutHelpGuideFragment scoutHelpGuideFragment = new ScoutHelpGuideFragment();

                    // In case this activity was started with special instructions from an Intent,
                    // pass the Intent's extras to the fragment as arguments
                    scoutHelpGuideFragment.setArguments(getIntent().getExtras());


                    //Initialize the FragmentTransaction
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();

                    //Make ScoutViewMaster as default fragment
                    fragmentTransaction.replace(R.id.mainActivity_container, scoutHelpGuideFragment, "APPCOMPATACTIVITY_FRAGMENT_USERGUIDE"); //Add Tag for getting the reference back when action is completed in DetailActivity
                    fragmentTransaction.commit();
                } else {

                }

                getSupportActionBar().setTitle(getResources().getString(R.string.ScoutCreate));
                currentFragmentDisplayed = APPCOMPACTACTIVITY_FRAGMENT_USERGUIDE;
            }
            break;


            default:
                break;
        }

    }

    //Save the SharedPreferences

    void saveSharedPreferences(String colorSelector) {

        SharedPreferences sharedPreferences = this.getSharedPreferences(ScoutConstants.SCOUT_DATA_SHAREDPREFERENCES_TAG, Context.MODE_PRIVATE);

        if (sharedPreferences != null) {

                //Strore the info in sharedPreferences
                sharedPreferences.edit().putString(ScoutConstants.SCOUT_DATA_SHAREDPREFERENCES_SELECTOR_TAG, colorSelector).commit();

                System.out.println("(saveSharedPreferences) first colorSelector = " + colorSelector );

        }
    }

    String readSharedPreferences() {

        String selectorColor = ScoutConstants.COLORS_BLUE_1;
        SharedPreferences sharedPreferences = this.getSharedPreferences(ScoutConstants.SCOUT_DATA_SHAREDPREFERENCES_TAG, Context.MODE_PRIVATE);

        if (sharedPreferences != null) {
            selectorColor = sharedPreferences.getString(ScoutConstants.SCOUT_DATA_SHAREDPREFERENCES_SELECTOR_TAG, ScoutConstants.COLORS_BLUE_1);
            System.out.println("(readSharedPreferences)selectorColor = " + selectorColor );
        }

        return selectorColor;

    }

    public void handleExportAction () {
        fab.hide();
        changeFragment(APPCOMPATACTIVITY_FRAGMENT_SCOUTEXPORT, false);
    }

    public void handleViewAction () {
        fab.show();
        changeFragment(APPCOMPATACTIVITY_FRAGMENT_SCOUTVIEW, false);
    }

    public void handleUserGuideAction () {
        fab.hide();
        changeFragment(APPCOMPACTACTIVITY_FRAGMENT_USERGUIDE, false);
    }

}


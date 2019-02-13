/*
 * Copyright (c) 2019
 * All rights reserved Amogh Mehta
 * Last Modified 1/7/19 12:18 PM
 */

package com.example.personal.scoutingappandroid.feature.ScoutDetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.personal.scoutingappandroid.feature.Common.ScoutConstants;
import com.example.personal.scoutingappandroid.feature.R;

public class ScoutDetailActivity extends AppCompatActivity implements ScoutNewCreateFragment.OnNewScoutCreateFragmentListener,
        ScoutDetailFragment.OnScoutDetailFragmentListener{

    //For  savedInstanceState
    private final static String APPCOMPATACTIVITY_SAVEINSTANCESTATE_CURRENTFRAGMENTDISPLAYED_ID = "currentFragmentDisplayed";
    private final static String APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTDETAILELEMENT_ID = "sdDetailElement";


    //Different Fragments
    public final static int APPCOMPATACTIVITY_FRAGMENT_SCOUTDETAIL = 1;
    public final static int APPCOMPATACTIVITY_FRAGMENT_SCOUTDETAILCREATENEW = 2;
    public final static int APPCOMPATACTIVITY_FRAGMENT_SCOUTDETAILUPDATE = 3;

    int currentFragmentDisplayed = APPCOMPATACTIVITY_FRAGMENT_SCOUTDETAIL;      //Default


    String ScoutDetailElementJSONString = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scout_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get the Intent
        Intent i = getIntent();
        ScoutDetailElementJSONString = i.getStringExtra(ScoutConstants.IntentScountViewMasterElement); //get the ScountViewMasterElement
        currentFragmentDisplayed = i.getIntExtra(ScoutConstants.IntentScoutDetailFragmentName, APPCOMPATACTIVITY_FRAGMENT_SCOUTDETAIL); //get the fragment to be displayed first

        //Log.i("ScoutDetailActivity", "ScoutDetailElementJSONString  = " + ScoutDetailElementJSONString + "currentFragmentDisplayed = " + currentFragmentDisplayed);


        //currentFragmentDisplayed overwrite if we have saved Instance of it
        if (savedInstanceState != null) {

            // Restore value of members from saved state
            currentFragmentDisplayed = savedInstanceState.getInt(APPCOMPATACTIVITY_SAVEINSTANCESTATE_CURRENTFRAGMENTDISPLAYED_ID);
            ScoutDetailElementJSONString = savedInstanceState.getString(APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTDETAILELEMENT_ID);

            changeFragment(currentFragmentDisplayed, true);
        } else {
            changeFragment(currentFragmentDisplayed, false);
        }


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        // Save the user's current game state
        outState.putInt(APPCOMPATACTIVITY_SAVEINSTANCESTATE_CURRENTFRAGMENTDISPLAYED_ID, currentFragmentDisplayed);
        outState.putString(APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTDETAILELEMENT_ID, ScoutDetailElementJSONString);



        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState);
    }



    ///////////////////////////////////App Bar Menu handling  ////////////////////////////////////////////////////////////////////
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

        //Log.i("onOptionsItemvenInboxA", item.toString());
        //noinspection SimplifiableIfStatement

        if (id == android.R.id.home) {
            //Log.i("onOptionsItemvenInboxA", "home");
            // NavUtils.navigateUpFromSameTask(this);
            handleUpButtonBarAction();
            return true;

        }
        if (id == R.id.action_settings) {
            //Log.i("onOptionsItemvenInboxA", item.toString());
            //If Fragment is not defined, handle menu option
            return true;

        }


        return super.onOptionsItemSelected(item);
    }

    ///////////////////////Local function///////////////////////////////////////
    void changeFragment(int fragmentId, boolean isFromSavedInstanceState)       //isFromSavedInstanceState will be true when SavedInstanceState != null
    {
        switch (fragmentId) {
            case APPCOMPATACTIVITY_FRAGMENT_SCOUTDETAIL: {

                if (isFromSavedInstanceState == false) {
                    // Create fragment and give it an argument for the selected article
                    ScoutDetailFragment newFragment = new ScoutDetailFragment();

                    //Pass the parameter
                  newFragment.updateScoutViewMasterJSONString(ScoutDetailElementJSONString);

                    //Initialize the FragmentTransaction
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack so the user can navigate back
                    transaction.replace(R.id.scoutdetailActivity_container, newFragment);
                    // transaction.addToBackStack(null);

                    // Commit the transaction
                    transaction.commit();

                } else {


                }
                getSupportActionBar().setTitle(getResources().getString(R.string.ScoutDetail));
                currentFragmentDisplayed = APPCOMPATACTIVITY_FRAGMENT_SCOUTDETAIL;
            }
            break;
            case APPCOMPATACTIVITY_FRAGMENT_SCOUTDETAILCREATENEW: {

                if (isFromSavedInstanceState == false) {
                    // Create fragment and give it an argument for the selected article
                    ScoutNewCreateFragment newFragment = new ScoutNewCreateFragment();

                    //Pass the parameter

                    //Initialize the FragmentTransaction
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack so the user can navigate back
                    transaction.replace(R.id.scoutdetailActivity_container, newFragment, "ScoutNewCreateFragment");
                    // transaction.addToBackStack(null);

                    // Commit the transaction
                    transaction.commit();
                } else {

                }

                getSupportActionBar().setTitle(getResources().getString(R.string.ScoutCreate));
                currentFragmentDisplayed = APPCOMPATACTIVITY_FRAGMENT_SCOUTDETAILCREATENEW;
            }
            break;

            case APPCOMPATACTIVITY_FRAGMENT_SCOUTDETAILUPDATE: {

                if (isFromSavedInstanceState == false) {
                    // Create fragment and give it an argument for the selected article
                    ScoutNewCreateFragment newFragment = new ScoutNewCreateFragment();

                    //Pass the parameter
                    newFragment.updateScoutViewMasterJSONString(ScoutDetailElementJSONString);


                    //Initialize the FragmentTransaction
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack so the user can navigate back
                    transaction.replace(R.id.scoutdetailActivity_container, newFragment, "ScoutNewCreateFragment");
                    // transaction.addToBackStack(null);

                    // Commit the transaction
                    transaction.commit();
                } else {

                }

                getSupportActionBar().setTitle(getResources().getString(R.string.ScoutCreate));
                currentFragmentDisplayed = APPCOMPATACTIVITY_FRAGMENT_SCOUTDETAILCREATENEW;
            }
            break;

            default:
                break;
        }

    }

    void handleUpButtonBarAction() {
        switch (currentFragmentDisplayed) {
            case APPCOMPATACTIVITY_FRAGMENT_SCOUTDETAIL:
                finish();
                break;
            case APPCOMPATACTIVITY_FRAGMENT_SCOUTDETAILCREATENEW:
                finish();
                break;

            case APPCOMPATACTIVITY_FRAGMENT_SCOUTDETAILUPDATE:
                finish();
                break;

            default:
                finish();
                break;
        }

    }


    //Implements ScoutNewCreateFragment.OnNewScoutCreateFragmentListener
    @Override
    public void NewScoutCreateCompleted() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);            //Set the Result code of Vendor

        finish();
    }

    @Override
    public void onPressesbackButton() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);            //Set the Result code of Vendor

        finish();
    }
    @Override
    public void DetailScoutUpdateButtonPressed() {
        currentFragmentDisplayed = APPCOMPATACTIVITY_FRAGMENT_SCOUTDETAILUPDATE;
        changeFragment(currentFragmentDisplayed, false);

    }



}

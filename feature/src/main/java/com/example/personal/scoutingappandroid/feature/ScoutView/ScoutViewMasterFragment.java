/*
 * Copyright (c) 2019
 * All rights reserved Amogh Mehta
 * Last Modified 1/7/19 12:18 PM
 */

package com.example.personal.scoutingappandroid.feature.ScoutView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.personal.scoutingappandroid.feature.Common.DBHelper;
import com.example.personal.scoutingappandroid.feature.Common.ScoutConstants;
import com.example.personal.scoutingappandroid.feature.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ScoutViewMasterFragment extends Fragment implements ScoutViewMasterRecyclerAdaptor.OnScoutViewMasterRecyclerAdapterRowSelected, SearchView.OnQueryTextListener, SearchView.OnClickListener,  SearchView.OnCloseListener{



    ArrayList<ScoutViewMasterElement> scoutViewMasterElementArrayList = new ArrayList<ScoutViewMasterElement>();

    ScoutViewMaster scoutViewMaster = null;
    ScoutViewMasterRecyclerAdaptor adapter = null;     //Adapter is initialized in Fragment class so we can pass the this reference of the class to send the listener ecent of selected row
    String scoutMasterElementString = ScoutConstants.SCOUT_DATA_NOT_AVAILABLE_STRING;

    OnScoutViewMasterFragmentListener onScoutViewMasterFragmentListener;

    SearchView globalsearchView;

    public ScoutViewMasterFragment() {
        // Required empty public constructor
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

        // onAttach make sure to implement interface otherwise through exception
        try {
            onScoutViewMasterFragmentListener = (OnScoutViewMasterFragmentListener) context;
        }
        catch (Exception e)
        {
            throw new ClassCastException(context.toString() + " must implement OnInboxMasterFragmentListener") ;
        }

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
        return inflater.inflate(R.layout.fragment_scout_view_master, container, false);
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
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.ScoutView));
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
        inflater.inflate(R.menu.master_menu_create, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        globalsearchView = searchView;
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.setOnSearchClickListener(this);
        ((View)searchView.findViewById(android.support.v7.appcompat.R.id.search_plate)).setBackgroundColor(Color.GRAY);
        ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setTextColor(Color.WHITE);
        ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(Color.LTGRAY);
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


    ////////////////Handling  SearchView.OnQueryTextListener, SearchView.OnClickListener,  SearchView.OnCloseListener////////////////
    //SearchView.OnQueryTextListener
    @Override
    public boolean onQueryTextSubmit(String query) {
        //Log.i("onQueryTextSubmit", query + " globalsearchView = " + globalsearchView.isIconified() + " getquery = " + globalsearchView.getQuery());
        scoutViewMaster.filterScoutViewMasterElementArrayList(query, scoutViewMasterElementArrayList);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //Log.i("onQueryTextChange", newText + " globalsearchView = " + globalsearchView.isIconified() + " getquery = " + globalsearchView.getQuery());
        scoutViewMaster.filterScoutViewMasterElementArrayList(newText, scoutViewMasterElementArrayList);
        return false;
    }

    //SearchView.OnCloseListener
    @Override
    public boolean onClose() {
        //Log.i("onClose"," enterted" + globalsearchView.isIconified() + " getquery = " + globalsearchView.getQuery());
        return false;
    }

    //SearchView.OnClickListener
    @Override
    public void onClick(View view) {
        //Log.i("onClick"," boolean " + " globalsearchView = " + globalsearchView.isIconified() + " getquery = " + globalsearchView.getQuery());
    }


    //Listener to get message from the RecyclerAdapter INTERFACE
    ////////////////////////// Implement ScoutViewMasterRecyclerAdaptor.OnScoutViewMasterRecyclerAdapterRowSelected/////////////////////
    @Override
    public void setScoutViewMasterRecyclerAdaptorofSelectedRow(ScoutViewMasterElement scoutViewMasterElement) {
        //   Log.i("RecyclerAdapter",  "InboxMasterElement =  " + InboxMasterElement);
        onScoutViewMasterFragmentListener.setScoutViewMasterElement(scoutViewMasterElement);
    }

    ///////////////////////////Function to send value to activity /////////////////////////////////////
    //since Fragment is Activity dependent, you need to pass the information using interface to activity
    //Interface to pass the ScoutViewMasterFragment to activity when user select a Element from a ScoutView List
    public interface OnScoutViewMasterFragmentListener
    {
        public void setScoutViewMasterElement( ScoutViewMasterElement scoutViewMasterElement);
        public void createNewScoutViewElement();       //User selected new ScoutView Element create Button
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


    void init()
    {
        //Fetch the Inbox list
        adapter = new ScoutViewMasterRecyclerAdaptor(scoutViewMasterElementArrayList, this);  //Pass the this of Fragment class - Adapter is initialized in Fragment class so we can pass the this reference of the class to send the listener ecent of selected row
        scoutViewMaster = new ScoutViewMaster(adapter);   //Pass the adapater to InboxMaster class to set up the recyclerview

        scoutViewMaster.InboxMasterInit();
    }
    ////////////////////////////Class to comunicate to DB and talk to recyclerAdapter////////////////////////

    public class ScoutViewMaster extends AppCompatActivity {

        DBHelper mydb;
        ScoutViewMasterRecyclerAdaptor adapter;
        RecyclerView recyclerView;

        RecyclerView.LayoutManager layoutManager;

        public ScoutViewMaster(ScoutViewMasterRecyclerAdaptor adapter) {
            this.adapter = adapter;
        }

///////////////////////////////////////////Get the Inbox list //////////////////////////////////////////////

        public void InboxMasterInit() {

            //Read the Shared Preferences
           // readSharedPreferences();

            //Set up recycler view and its adaptor
            recyclerView = (RecyclerView) getActivity().findViewById(R.id.scoutviewmasterrecyclerView);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);


            //Data is already present, just process as response - this is the case of screen orientation change
            //readSharedPreferences();
            //reloadDatafromSharedPreferences(scoutMasterElementString);

            readDBInfo();
            reloadDatafromDB();

        }



        private void reloadDatafromSharedPreferences(String result) {
            System.out.println("reloadDatafromSharedPreferences result =  " + result);

            if (result.equalsIgnoreCase(ScoutConstants.SCOUT_DATA_NOT_AVAILABLE_STRING))
            {
                return;     // No Data
            }

             int resultNoOfRecords = 0;
                try {
                    JSONArray jArray = new JSONArray(result);
                    System.out.println("reloadDatafromSharedPreferences jArray =  " + jArray);
                    JSONObject jsonObject = null;
                    resultNoOfRecords = jArray.length();
                    //CHeck if we have any records in the json
                    if (resultNoOfRecords > 0) {
                        ArrayList<ScoutViewMasterElement> tempScoutViewMasterElementList = new ArrayList<ScoutViewMasterElement>();
                        for (int i = 0; i < jArray.length(); i++) {
                            jsonObject = jArray.getJSONObject(i);

                            System.out.println("reloadDatafromSharedPreferences jsonObject =  " + jsonObject);

                            ScoutViewMasterElement tempScoutViewMasterElement = new ScoutViewMasterElement();
                            tempScoutViewMasterElement.setWithJsonString(jsonObject);
                            tempScoutViewMasterElementList.add(tempScoutViewMasterElement);
                            System.out.println("reloadDatafromSharedPreferences tempScoutViewMasterElement =  " + tempScoutViewMasterElement);
                        }
                        scoutViewMasterElementArrayList = null;
                        scoutViewMasterElementArrayList = tempScoutViewMasterElementList;

                    }
                } catch (JSONException e) {
                    //Can not parse the result
                    //Show error to user
                    System.out.println("(ScoutViewMasterFragment)Cannot parse JSON" );
                }

                System.out.println("resultNoOfRecords =  " + resultNoOfRecords + "  scoutViewMasterElementArrayList = " + scoutViewMasterElementArrayList);
                //Check if no records found , inform the user
                if (resultNoOfRecords == 0) {

                } else {
                        //searchview is not opened
                        updateRecyclerviewItems(scoutViewMasterElementArrayList);


                }

        }

        private void reloadDatafromDB() {

            int resultNoOfRecords = scoutViewMasterElementArrayList.size();
            System.out.println("resultNoOfRecords =  " + resultNoOfRecords + "  scoutViewMasterElementArrayList = " + scoutViewMasterElementArrayList);
            //Check if no records found , inform the user
            if (resultNoOfRecords == 0) {

            } else {
                //searchview is not opened
                updateRecyclerviewItems(scoutViewMasterElementArrayList);


            }

        }

        //Update the Recyclerview
        void updateRecyclerviewItems(ArrayList<ScoutViewMasterElement> arrayList)
        {
            adapter.updateArrayList(arrayList);
            adapter.notifyDataSetChanged();
        }
        //Read the SharedPreferences

        void readSharedPreferences() {

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(ScoutConstants.SCOUT_DATA_SHAREDPREFERENCES_TAG, Context.MODE_PRIVATE);

            if (sharedPreferences != null) {
                scoutMasterElementString = sharedPreferences.getString(ScoutConstants.SCOUT_DATA_SHAREDPREFERENCES_ELEMENTS_TAG, ScoutConstants.SCOUT_DATA_NOT_AVAILABLE_STRING);
                System.out.println("(readSharedPreferences)scoutMasterElementString = " + scoutMasterElementString );
            }

        }

        void readDBInfo() {

            mydb = new DBHelper(getActivity());
            scoutViewMasterElementArrayList = mydb.getAllScoutElements();
            mydb.close();

            System.out.println("(readDBInfo)scoutViewMasterElementArrayList = " + scoutViewMasterElementArrayList );
        }

        void  filterScoutViewMasterElementArrayList (String searchViewText, ArrayList<ScoutViewMasterElement> arrayList )
        {
            //If no filter Text
            if (searchViewText.isEmpty() == true)
            {
                updateRecyclerviewItems(arrayList);
            }
            else
            {
                ArrayList<ScoutViewMasterElement> tempScoutViewMasterElementArrayList = new ArrayList<ScoutViewMasterElement>();
                tempScoutViewMasterElementArrayList.clear();


                //If user has entered text in the serachview
                String text = searchViewText.toLowerCase();
                for(ScoutViewMasterElement item: arrayList){
                    if(item.isFilterContentPresent(text) == true)
                    {
                        tempScoutViewMasterElementArrayList.add(item);
                    }
                }
                //Time to update Adapter
                updateRecyclerviewItems(tempScoutViewMasterElementArrayList);
            }
        }

    }


}

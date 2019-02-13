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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.personal.scoutingappandroid.feature.Common.CommonLib;
import com.example.personal.scoutingappandroid.feature.Common.DBHelper;
import com.example.personal.scoutingappandroid.feature.Common.ScoutConstants;
import com.example.personal.scoutingappandroid.feature.R;
import com.example.personal.scoutingappandroid.feature.ScoutView.ScoutViewMasterElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ScoutNewCreateFragment extends Fragment {


    private final static String APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTDETAILFRAGMENT_ID = "scoutDetailElementstring";

    private final static String APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTLANDED_ID = "newscoutlanded";
    private final static String APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTSAMPLED_ID = "newscoutsampled";
    private final static String APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTCLAIMEDDEPOT_ID = "newscoutclaimeddepot";
    private final static String APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTPARKED_ID = "newscoutparked";
    private final static String APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTLATCHED_ID = "newscoutlatched";
    private final static String APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTCRATERPARTIAL_ID = "newscoutcraterpartial";
    private final static String APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTCRATERFULL_ID = "newscoutcraterfull";

    private final static String APPCOMPATACTIVITY_SAVEINSTANCESTATE_RECORDID_ID = "recordID";

    private final static String NEWSCOUT_SAVEINSTANCESTATE_MENUINITIALIZED_ID = "menuinit";


    int recordId = 0;


    private OnNewScoutCreateFragmentListener onNewScoutCreateFragmentListener;

    String ScoutViewMasterElementJSONString = null;//for edit/update
    ScoutViewMasterElement scoutViewMasterElement;

    boolean isMenuInitialized = false;
    NewScoutCreate newScoutCreate = null;

    boolean isFromSavedInstanceState = false;

    //Fields in layout
    EditText newScoutTeamNameField;
    EditText newScoutTeamNumberField;
    EditText newScoutGameNumberField;
    EditText newScoutDepotScoredField;
    EditText newScoutSilverLanderScoredField;
    EditText newScoutGoldLanderScoredField;
    EditText newScoutCommentField;
    CheckBox cbLanded;
    CheckBox cbSampled;
    CheckBox cbClaimedDepot;
    CheckBox cbParked;
    CheckBox cbLatched;
    CheckBox cbCraterPartial;
    CheckBox cbCraterFull;

    String newscoutlanded = ScoutConstants.SCOUT_DATA_UNCHECKED;
    String newscoutsampled = ScoutConstants.SCOUT_DATA_UNCHECKED;
    String newscoutclaimeddepot = ScoutConstants.SCOUT_DATA_UNCHECKED;
    String newscoutparked = ScoutConstants.SCOUT_DATA_UNCHECKED;
    String newscoutlatched = ScoutConstants.SCOUT_DATA_UNCHECKED;
    String newscoutcraterpartial = ScoutConstants.SCOUT_DATA_UNCHECKED;
    String newscoutcraterfull = ScoutConstants.SCOUT_DATA_UNCHECKED;


    public ScoutNewCreateFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);  //Let Fragment manager to call OnCreateOptionsMenu for creating Menu
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // onAttach make sure to implement interface otherwise through exception
        try {
            onNewScoutCreateFragmentListener = (OnNewScoutCreateFragmentListener) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + " must implement OnNewScoutCreateFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Log.i("onCreateView diff1", "called");
        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            isMenuInitialized = savedInstanceState.getBoolean(NEWSCOUT_SAVEINSTANCESTATE_MENUINITIALIZED_ID);
            ScoutViewMasterElementJSONString = savedInstanceState.getString(APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTDETAILFRAGMENT_ID);
            newscoutlanded = savedInstanceState.getString(APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTLANDED_ID);
            newscoutsampled = savedInstanceState.getString(APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTSAMPLED_ID);
            newscoutclaimeddepot = savedInstanceState.getString(APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTCLAIMEDDEPOT_ID);
            newscoutparked = savedInstanceState.getString(APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTPARKED_ID);
            newscoutlatched = savedInstanceState.getString(APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTLATCHED_ID);
            newscoutcraterpartial = savedInstanceState.getString(APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTCRATERPARTIAL_ID);
            newscoutcraterfull = savedInstanceState.getString(APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTCRATERFULL_ID);
            recordId = savedInstanceState.getInt(APPCOMPATACTIVITY_SAVEINSTANCESTATE_RECORDID_ID);

            isFromSavedInstanceState = true;
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scout_new_create, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(NEWSCOUT_SAVEINSTANCESTATE_MENUINITIALIZED_ID, isMenuInitialized);
        outState.putString(APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTDETAILFRAGMENT_ID, ScoutViewMasterElementJSONString);
        outState.putString(APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTLANDED_ID, newscoutlanded);
        outState.putString(APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTSAMPLED_ID, newscoutsampled);
        outState.putString(APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTCLAIMEDDEPOT_ID, newscoutclaimeddepot);
        outState.putString(APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTPARKED_ID, newscoutparked);
        outState.putString(APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTLATCHED_ID, newscoutlatched);
        outState.putString(APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTCRATERPARTIAL_ID, newscoutcraterpartial);
        outState.putString(APPCOMPATACTIVITY_SAVEINSTANCESTATE_SCOUTCRATERFULL_ID, newscoutcraterfull);
        outState.putInt(APPCOMPATACTIVITY_SAVEINSTANCESTATE_RECORDID_ID, recordId);
    }

    @Override
    public void onResume() {
        super.onResume();
        /*AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (ScoutViewMasterElementJSONString == null) {
            actionBar.setTitle("Create Record");
        } else {
            actionBar.setTitle("Update Record ");
        }

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(readSharedPreferences())));
        */
        updateLiveScore();

    }

    public void titleDisplay(int Score) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (ScoutViewMasterElementJSONString == null) {
            actionBar.setTitle("Create Record (" + Score + ")" );
        } else {
            actionBar.setTitle("Update Record (" + Score + ")" );
        }

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(readSharedPreferences())));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onNewScoutCreateFragmentListener = null;
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


    public void setDoneButtonColor (Drawable drawable, Boolean enable) {


        String selectorColor = readSharedPreferences();

        //System.out.println("setDoneButtonColor + selectorColor = " + selectorColor + " enable = " + enable);
        if (enable == true) {
            drawable.setColorFilter(getResources().getColor(R.color.tableheader), PorterDuff.Mode.SRC_IN);
        } else {

            if (selectorColor.equals(ScoutConstants.COLORS_RED_1)) {
                drawable.setColorFilter(getResources().getColor(R.color.COLORS_RED_1), PorterDuff.Mode.SRC_IN);
            } else if (selectorColor.equals(ScoutConstants.COLORS_RED_2)) {
                drawable.setColorFilter(getResources().getColor(R.color.COLORS_RED_2), PorterDuff.Mode.SRC_IN);
            } else if (selectorColor.equals(ScoutConstants.COLORS_BLUE_1)) {
                drawable.setColorFilter(getResources().getColor(R.color.COLORS_BLUE_1), PorterDuff.Mode.SRC_IN);
            } else if (selectorColor.equals(ScoutConstants.COLORS_BLUE_2)) {
                drawable.setColorFilter(getResources().getColor(R.color.COLORS_BLUE_2), PorterDuff.Mode.SRC_IN);
            }
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_actionbardone, menu);

        MenuItem menuItemDone = menu.findItem(R.id.action_actionbardone);
        Drawable drawable = menuItemDone.getIcon();
        //Log.i("AMonCreateOptionsMenu", "isMenuInitialized = " + isMenuInitialized);
        //If First time menu is initialzed , make done button disable. Otherwise check the content of the textviews
        if ((isMenuInitialized == true) || (ScoutViewMasterElementJSONString != null)) {
            //Log.i("AMonCreateOptionsMenu", "true isMenuInitialized = " + isMenuInitialized);
            checkDoneButtonEnable(menuItemDone);
        } else {
            //Log.i("AMonCreateOptionsMenu", "false isMenuInitialized = " + isMenuInitialized);
            isMenuInitialized = true;
            menuItemDone.setEnabled(false);
            if(drawable != null) {
                drawable.mutate();
                setDoneButtonColor(drawable, false);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //Log.i("onOptionsItemSelected", item.toString());
        if (id == android.R.id.home) {
            //Log.i("onOptionsItemSelected", "home");
            return true;
        } else if (id == R.id.action_actionbardone) {
            //Log.i("onOptionsItemSelected", item.toString());
            processDoneButtonAction();
            // Start our refresh background task
            //assetsMaster.getAssetsList(false);  //Since it is manual Refresh , by pass the 30 sec  DB delay check
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    void checkDoneButtonEnable(MenuItem menuItem) {
        Drawable drawable = menuItem.getIcon();
        if (shouldEnableDoneButton() == true) {
            menuItem.setEnabled(true);

            if(drawable != null) {
                drawable.mutate();
                setDoneButtonColor(drawable, true);
            }
        } else {
            menuItem.setEnabled(false);

            if(drawable != null) {
                drawable.mutate();
                setDoneButtonColor(drawable, false);
            }
        }
    }

    boolean shouldEnableDoneButton() {
        boolean ret = true;

        //Team Name is Mandatory
        if (newScoutTeamNameField.getText().toString().length() <= 0)
        {
            ret = false;
        }

        //Team Number Mandatory
        if (newScoutTeamNumberField.getText().toString().length() <= 0)
        {
            ret = false;
        }

        //Game Number Mandatory
        if (newScoutGameNumberField.getText().toString().length() <= 0)
        {
            ret = false;
        }
        //newScoutDepotScoredField is Mandatory
        if (newScoutDepotScoredField.getText().toString().length() <= 0)
        {
            ret = false;
        }
        //newScoutSilverLanderScoredField is Mandatory
        if (newScoutSilverLanderScoredField.getText().toString().length() <= 0)
        {
            ret = false;
        }
        //newScoutGoldLanderScoredField is Mandatory
        if (newScoutGoldLanderScoredField.getText().toString().length() <= 0)
        {
            ret = false;
        }

        return ret;
    }

    void processDoneButtonAction() {
        //Store the new record
        newScoutCreate.updateNewScoutCreatedAction();

        //call the activity listener
        onNewScoutCreateFragmentListener.NewScoutCreateCompleted();
    }

    void setupEditTextListenser() {
        newScoutTeamNameField = (EditText) getActivity().findViewById(R.id.newscoutteamname);
        newScoutTeamNumberField = (EditText) getActivity().findViewById(R.id.newscoutteamnumber);
        newScoutGameNumberField = (EditText) getActivity().findViewById(R.id.newscoutgamenumber);
        newScoutDepotScoredField = (EditText) getActivity().findViewById(R.id.newscoutdepotscored);
        newScoutSilverLanderScoredField = (EditText) getActivity().findViewById(R.id.newscoutsilverlanderscored);
        newScoutGoldLanderScoredField = (EditText) getActivity().findViewById(R.id.newscoutgoldlanderscored);
        newScoutCommentField = (EditText) getActivity().findViewById(R.id.newscoutcomments);
        cbLanded = (CheckBox) getActivity().findViewById(R.id.checkbox_newscoutlanded);
        cbSampled = (CheckBox) getActivity().findViewById(R.id.checkbox_newscoutsampled);
        cbClaimedDepot = (CheckBox) getActivity().findViewById(R.id.checkbox_newscoutclaimeddepot);
        cbParked = (CheckBox) getActivity().findViewById(R.id.checkbox_newscoutparked);
        cbLatched = (CheckBox) getActivity().findViewById(R.id.checkbox_newscoutlatched);
        cbCraterPartial = (CheckBox) getActivity().findViewById(R.id.checkbox_newscoutcraterpartial);
        cbCraterFull = (CheckBox) getActivity().findViewById(R.id.checkbox_newscoutcraterfull);

        //Set up the TextChangedListener to watch for text entered in EditText for DONE button enable/disable
        newScoutTeamNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //checkDoneButtonEnable();

                getActivity().invalidateOptionsMenu();    // you want to change the menu, you should call invalidateOptionsMenu(). What that does is it invalidates the menu,
                // which in turn forces it to be recreated. During its recreation, one of the callbacks is onPrepareOptionsMenu(Menu menu).
            }
        });


        //set the OnEditorActionListener so when user enter ENTER, dismiss the keyboard
        newScoutTeamNameField.setOnKeyListener(new View.OnKeyListener() {
            /**
             * This listens for the user to press the enter button on
             * the keyboard and then hides the virtual keyboard
             */
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                /*
                if ( (keyEvent.getAction() == KeyEvent.ACTION_DOWN  ) &&
                        (i == KeyEvent.KEYCODE_ENTER)   )
                {

                    // hide virtual keyboard
                    hideKeyboard();
                    return true;
                }
                */
                return false;
            }
        });

        //Set up the TextChangedListenser to watch for text entered in EditText for DONE button enable/disable
        newScoutTeamNumberField.addTextChangedListener(new TextWatcher() {

            //This method is called to notify you that, within s, the count characters beginning at start are
            // about to be replaced by new text with length after. It is an error to attempt to make changes to s from this callback.
            //void beforeTextChanged (CharSequence s, int start, int count, int after)
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //void onTextChanged (CharSequence s, int start,int before, int count)
            //This method is called to notify you that, within s, the count characters beginning at start have
            // just replaced old text that had length before. It is an error to attempt to make changes to s from this callback.
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //This method is called to notify you that, somewhere within s, the text has been changed.
            // It is legitimate to make further changes to s from this callback, but be careful not to get yourself into an infinite loop,
            // because any changes you make will cause this method to be called again recursively. (You are not told where the change
            // took place because other afterTextChanged() methods may already have made other changes and invalidated the offsets.
            // But if you need to know here, you can use setSpan(Object, int, int, int) in onTextChanged(CharSequence, int, int, int)
            // to mark your place and then look up from here where the span ended up.
            @Override
            public void afterTextChanged(Editable editable) {
                //checkDoneButtonEnable();

                getActivity().invalidateOptionsMenu();    // you want to change the menu, you should call invalidateOptionsMenu(). What that does is it invalidates the menu,
                // which in turn forces it to be recreated. During its recreation, one of the callbacks is onPrepareOptionsMenu(Menu menu).
            }
        });


        //set the OnEditorActionListener so when user enter ENTER, dismiss the keyboard
        newScoutTeamNumberField.setOnKeyListener(new View.OnKeyListener() {
            /**
             * This listens for the user to press the enter button on
             * the keyboard and then hides the virtual keyboard
             */
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                return false;
            }
        });




        //Set up the TextChangedListener to watch for text entered in EditText for DONE button enable/disable
        newScoutGameNumberField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //checkDoneButtonEnable();

                getActivity().invalidateOptionsMenu();    // you want to change the menu, you should call invalidateOptionsMenu(). What that does is it invalidates the menu,
                // which in turn forces it to be recreated. During its recreation, one of the callbacks is onPrepareOptionsMenu(Menu menu).
            }
        });


        //set the OnEditorActionListener so when user enter ENTER, dismiss the keyboard
        newScoutGameNumberField.setOnKeyListener(new View.OnKeyListener() {
            /**
             * This listens for the user to press the enter button on
             * the keyboard and then hides the virtual keyboard
             */
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                return false;
            }
        });

        //Set up the TextChangedListener to watch for text entered in EditText for DONE button enable/disable
        newScoutDepotScoredField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //checkDoneButtonEnable();
                updateLiveScore();
                getActivity().invalidateOptionsMenu();    // you want to change the menu, you should call invalidateOptionsMenu(). What that does is it invalidates the menu,
                // which in turn forces it to be recreated. During its recreation, one of the callbacks is onPrepareOptionsMenu(Menu menu).
            }
        });


        //set the OnEditorActionListener so when user enter ENTER, dismiss the keyboard
        newScoutDepotScoredField.setOnKeyListener(new View.OnKeyListener() {
            /**
             * This listens for the user to press the enter button on
             * the keyboard and then hides the virtual keyboard
             */
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                return false;
            }
        });

        //Set up the TextChangedListener to watch for text entered in EditText for DONE button enable/disable
        newScoutSilverLanderScoredField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //checkDoneButtonEnable();
                updateLiveScore();
                getActivity().invalidateOptionsMenu();    // you want to change the menu, you should call invalidateOptionsMenu(). What that does is it invalidates the menu,
                // which in turn forces it to be recreated. During its recreation, one of the callbacks is onPrepareOptionsMenu(Menu menu).
            }
        });


        //set the OnEditorActionListener so when user enter ENTER, dismiss the keyboard
        newScoutSilverLanderScoredField.setOnKeyListener(new View.OnKeyListener() {
            /**
             * This listens for the user to press the enter button on
             * the keyboard and then hides the virtual keyboard
             */
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                return false;
            }
        });
        //Set up the TextChangedListener to watch for text entered in EditText for DONE button enable/disable
        newScoutGoldLanderScoredField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //checkDoneButtonEnable();
                updateLiveScore();
                getActivity().invalidateOptionsMenu();    // you want to change the menu, you should call invalidateOptionsMenu(). What that does is it invalidates the menu,
                // which in turn forces it to be recreated. During its recreation, one of the callbacks is onPrepareOptionsMenu(Menu menu).
            }
        });


        //set the OnEditorActionListener so when user enter ENTER, dismiss the keyboard
        newScoutGoldLanderScoredField.setOnKeyListener(new View.OnKeyListener() {
            /**
             * This listens for the user to press the enter button on
             * the keyboard and then hides the virtual keyboard
             */
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                return false;
            }
        });

        newScoutCommentField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //checkDoneButtonEnable();

                getActivity().invalidateOptionsMenu();    // you want to change the menu, you should call invalidateOptionsMenu(). What that does is it invalidates the menu,
                // which in turn forces it to be recreated. During its recreation, one of the callbacks is onPrepareOptionsMenu(Menu menu).
            }
        });


        //set the OnEditorActionListener so when user enter ENTER, dismiss the keyboard
        newScoutCommentField.setOnKeyListener(new View.OnKeyListener() {
            /**
             * This listens for the user to press the enter button on
             * the keyboard and then hides the virtual keyboard
             */
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                return false;
            }
        });

        cbLanded.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //Checked
                    newscoutlanded =  ScoutConstants.SCOUT_DATA_CHECKED;
                    //Log.i("landed", "landed clicked");
                } else {
                    //Log.i("landed", "landed unclicked");
                    // UNCHECKED
                    newscoutlanded =  ScoutConstants.SCOUT_DATA_UNCHECKED;
                }
                updateLiveScore();
            }
        });
        cbSampled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //Checked
                    newscoutsampled =  ScoutConstants.SCOUT_DATA_CHECKED;
                } else {
                    // UNCHECKED
                    newscoutsampled =  ScoutConstants.SCOUT_DATA_UNCHECKED;
                }
                updateLiveScore();
            }
        });
        cbClaimedDepot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //Checked
                    newscoutclaimeddepot =  ScoutConstants.SCOUT_DATA_CHECKED;
                } else {
                    // UNCHECKED
                    newscoutclaimeddepot =  ScoutConstants.SCOUT_DATA_UNCHECKED;
                }
                updateLiveScore();
            }

        });
        cbParked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //Checked
                    newscoutparked =  ScoutConstants.SCOUT_DATA_CHECKED;
                } else {
                    // UNCHECKED
                    newscoutparked =  ScoutConstants.SCOUT_DATA_UNCHECKED;
                }
                updateLiveScore();
            }
        });
        cbLatched.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //Checked
                    newscoutlatched =  ScoutConstants.SCOUT_DATA_CHECKED;
                } else {
                    // UNCHECKED
                    newscoutlatched =  ScoutConstants.SCOUT_DATA_UNCHECKED;
                }
                updateLiveScore();
            }
        });
        cbCraterPartial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //Checked
                    newscoutcraterpartial =  ScoutConstants.SCOUT_DATA_CHECKED;
                } else {
                    // UNCHECKED
                    newscoutcraterpartial =  ScoutConstants.SCOUT_DATA_UNCHECKED;
                }
                updateLiveScore();
            }
        });
        cbCraterFull.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //Checked
                    newscoutcraterfull =  ScoutConstants.SCOUT_DATA_CHECKED;
                } else {
                    // UNCHECKED
                    newscoutcraterfull =  ScoutConstants.SCOUT_DATA_UNCHECKED;
                }
                updateLiveScore();
            }
        });

    }


/*
    //Handle Checkbox handler
    public void onCheckboxClicked(View view) {

        Log.i("onCheckboxClicked", "onCheckboxClicked entered");
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        int i = view.getId();
        if (i == R.id.checkbox_newscoutlanded) {
            if (checked) {
                //Checked
                newscoutlanded =  ScoutConstants.SCOUT_DATA_CHECKED;
            } else {
                // UNCHECKED
                newscoutlanded =  ScoutConstants.SCOUT_DATA_UNCHECKED;
            }
        } else if (i == R.id.checkbox_newscoutsampled) {
            if (checked) {
                newscoutsampled =  ScoutConstants.SCOUT_DATA_CHECKED;
            } else {
                newscoutsampled =  ScoutConstants.SCOUT_DATA_UNCHECKED;
            }
        }else if (i == R.id.checkbox_newscoutclaimeddepot) {
            if (checked) {
                newscoutclaimeddepot =  ScoutConstants.SCOUT_DATA_CHECKED;
            } else {
                newscoutclaimeddepot =  ScoutConstants.SCOUT_DATA_UNCHECKED;
            }
        }else if (i == R.id.checkbox_newscoutparked) {
            if (checked) {
                newscoutparked =  ScoutConstants.SCOUT_DATA_CHECKED;
            } else {
                newscoutparked =  ScoutConstants.SCOUT_DATA_UNCHECKED;
            }
        }else if (i == R.id.checkbox_newscoutlatched) {
            if (checked) {
                newscoutlatched =  ScoutConstants.SCOUT_DATA_CHECKED;
            } else {
                newscoutlatched =  ScoutConstants.SCOUT_DATA_UNCHECKED;
            }
        }else if (i == R.id.checkbox_newscoutcraterpartial) {
            if (checked) {
                newscoutcraterpartial =  ScoutConstants.SCOUT_DATA_CHECKED;
            } else {
                newscoutcraterpartial =  ScoutConstants.SCOUT_DATA_UNCHECKED;
            }
        }else if (i == R.id.checkbox_newscoutcraterfull) {
            if (checked) {
                newscoutcraterfull =  ScoutConstants.SCOUT_DATA_CHECKED;
            } else {
                newscoutcraterfull =  ScoutConstants.SCOUT_DATA_UNCHECKED;
            }
        }

    }
    */

    void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(newScoutTeamNameField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(newScoutTeamNumberField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(newScoutGameNumberField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(newScoutDepotScoredField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(newScoutSilverLanderScoredField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(newScoutGoldLanderScoredField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(newScoutCommentField.getWindowToken(), 0);


    }

    void init() {
        setupEditTextListenser();

        //If view get rotated , need to build scoutViewMasterElement again
        if ((ScoutViewMasterElementJSONString != null) && (isFromSavedInstanceState == false)) {
            updateScoutViewMasterJSONString(ScoutViewMasterElementJSONString); //for edit/update
            updateViewItems(scoutViewMasterElement);
            recordId = scoutViewMasterElement.getId();
        }

        if (isFromSavedInstanceState == true)
        {
            //Screen has been rotated , retrieve check boxed from the screen data

        }

        newScoutCreate = new NewScoutCreate();
        newScoutCreate.NewScoutActionInit();
    }

    public  void updateScoutViewMasterJSONString(String aMElementJSONString)//for edit/update
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


    //Update the Fragment View
    void updateViewItems(ScoutViewMasterElement scoutViewMasterElement) {

        System.out.println("updateViewItems scoutViewMasterElement = " + scoutViewMasterElement);


        //TextView teamNameDetailField = (TextView) getActivity().findViewById(R.id.newscoutteamname);
        newScoutTeamNameField.setText(scoutViewMasterElement.getTeamName());

        //TextView teamNumberDetailField = (TextView) getActivity().findViewById(R.id.newscoutteamnumber);
        newScoutTeamNumberField.setText(scoutViewMasterElement.getTeamNumber());

        //TextView gameNumberDetailField = (TextView) getActivity().findViewById(R.id.newscoutgamenumber);
        newScoutGameNumberField.setText(scoutViewMasterElement.getGameNumber() + "");

        //CheckBox landedDetailCheckBox = (CheckBox) getActivity().findViewById(R.id.checkbox_newscoutlanded);
        newscoutlanded = scoutViewMasterElement.getLanded();
        if (newscoutlanded.equals(ScoutConstants.SCOUT_DATA_CHECKED)){
            cbLanded.setChecked(true);
        } else {
            cbLanded.setChecked(false);
        }

        //CheckBox sampledDetailCheckBox = (CheckBox) getActivity().findViewById(R.id.checkbox_newscoutsampled);
        newscoutsampled = scoutViewMasterElement.getSampled();
        if (newscoutsampled.equals(ScoutConstants.SCOUT_DATA_CHECKED)){
            cbSampled.setChecked(true);
        } else {
            cbSampled.setChecked(false);
        }

        //CheckBox claimedDepotDetailCheckBox = (CheckBox) getActivity().findViewById(R.id.checkbox_newscoutclaimeddepot);
        newscoutclaimeddepot = scoutViewMasterElement.getClaimedDepot();
        if (newscoutclaimeddepot.equals(ScoutConstants.SCOUT_DATA_CHECKED)){
            cbClaimedDepot.setChecked(true);
        } else {
            cbClaimedDepot.setChecked(false);
        }

        //CheckBox parkedDetailCheckBox = (CheckBox) getActivity().findViewById(R.id.checkbox_newscoutparked);
        newscoutparked = scoutViewMasterElement.getParked();
        if (newscoutparked.equals(ScoutConstants.SCOUT_DATA_CHECKED)){
            cbParked.setChecked(true);
        } else {
            cbParked.setChecked(false);
        }

        //teleop section
        //TextView depotScoredDetailField = (TextView) getActivity().findViewById(R.id.newscoutdepotscored);
        newScoutDepotScoredField.setText(scoutViewMasterElement.getDepotScored()+ "");

        //TextView silverLanderScoredDetailField = (TextView) getActivity().findViewById(R.id.newscoutsilverlanderscored);
        newScoutSilverLanderScoredField.setText(scoutViewMasterElement.getSilverLanderScored()+ "");

        //TextView goldLanderScoredDetailField = (TextView) getActivity().findViewById(R.id.newscoutgoldlanderscored);
        newScoutGoldLanderScoredField.setText(scoutViewMasterElement.getGoldLanderScored()+ "");

        //endgame section
        //CheckBox latchedDetailCheckBox = (CheckBox) getActivity().findViewById(R.id.checkbox_newscoutlatched);
        newscoutlatched = scoutViewMasterElement.getLatched();
        if (newscoutlatched.equals(ScoutConstants.SCOUT_DATA_CHECKED)){
            cbLatched.setChecked(true);
        } else {
            cbLatched.setChecked(false);
        }

        //CheckBox craterPartialDetailCheckBox = (CheckBox) getActivity().findViewById(R.id.checkbox_newscoutcraterpartial);
        newscoutcraterpartial = scoutViewMasterElement.getCraterPartial();
        if (newscoutcraterpartial.equals(ScoutConstants.SCOUT_DATA_CHECKED)){
            cbCraterPartial.setChecked(true);
        } else {
            cbCraterPartial.setChecked(false);
        }

        //CheckBox craterFullDetailCheckBox = (CheckBox) getActivity().findViewById(R.id.checkbox_newscoutcraterfull);
        newscoutcraterfull = scoutViewMasterElement.getCraterFull();
        if (newscoutcraterfull.equals(ScoutConstants.SCOUT_DATA_CHECKED)){
            cbCraterFull.setChecked(true);
        } else {
            cbCraterFull.setChecked(false);
        }

        //TextView commentScoredDetailField = (TextView) getActivity().findViewById(R.id.newscoutcomments);
        newScoutCommentField.setText(scoutViewMasterElement.getComment());


        System.out.println("updateViewItems scoutViewMasterElement.getComment() = " + scoutViewMasterElement.getComment());
    }

    ///////////////////////////Function to send value to activity /////////////////////////////////////
    //since Fragment is Activity dependent, you need to pass the information using interface to activity
    public interface OnNewScoutCreateFragmentListener {
        //This is called when user select Action Image
        public void NewScoutCreateCompleted();       //On Done button pressed
    }


    public void handleExportAction () {

    }



    public void updateLiveScore () {

        int depotScored = CommonLib.parseToInt(newScoutDepotScoredField.getText().toString(), 0);
        int silverLanderScored = CommonLib.parseToInt(newScoutSilverLanderScoredField.getText().toString(), 0);
        int goldLanderScored = CommonLib.parseToInt(newScoutGoldLanderScoredField.getText().toString(), 0);
        //System.out.println("my values for scored goold lander silver and depot" + depotScored + silverLanderScored + goldLanderScored);
        int score = ScoutViewMasterElement.calculateLiveScore(newscoutlanded, newscoutsampled, newscoutclaimeddepot, newscoutparked, newscoutlatched,
                newscoutcraterpartial, newscoutcraterfull, depotScored, silverLanderScored, goldLanderScored);

         titleDisplay(score);
    }

    ////////////////////////////Class to communicate to DB and talk to recyclerAdapter////////////////////////

    public class NewScoutCreate extends AppCompatActivity {

        DBHelper mydb;

        public NewScoutCreate() {

        }

        public void NewScoutActionInit() {


        }

        //Update the SharedPreferences
        public void updateNewScoutCreatedAction() {

            ScoutViewMasterElement tempscoutViewMasterElement = new ScoutViewMasterElement();


            tempscoutViewMasterElement.setTeamName(newScoutTeamNameField.getText().toString());
            tempscoutViewMasterElement.setTeamNumber(newScoutTeamNumberField.getText().toString());
            tempscoutViewMasterElement.setGameNumber(Integer.parseInt(newScoutGameNumberField.getText().toString()));
            tempscoutViewMasterElement.setLanded(newscoutlanded);
            tempscoutViewMasterElement.setSampled(newscoutsampled);
            tempscoutViewMasterElement.setClaimedDepot(newscoutclaimeddepot);
            tempscoutViewMasterElement.setParked(newscoutparked);
            tempscoutViewMasterElement.setDepotScored(Integer.parseInt(newScoutDepotScoredField.getText().toString()));
            tempscoutViewMasterElement.setSilverLanderScored(Integer.parseInt(newScoutSilverLanderScoredField.getText().toString()));
            tempscoutViewMasterElement.setGoldLanderScored(Integer.parseInt(newScoutGoldLanderScoredField.getText().toString()));
            tempscoutViewMasterElement.setComment(newScoutCommentField.getText().toString());
            tempscoutViewMasterElement.setLatched(newscoutlatched);
            tempscoutViewMasterElement.setCraterPartial(newscoutcraterpartial);
            tempscoutViewMasterElement.setCraterFull(newscoutcraterfull);
            //fill the date
            tempscoutViewMasterElement.setGameDateString();
            tempscoutViewMasterElement.calculateScore();


            mydb = new DBHelper(getActivity());
            if (ScoutViewMasterElementJSONString == null) {
                mydb.insertScoutElement(tempscoutViewMasterElement);
            }
            else
            {
                mydb.updateScoutElement(tempscoutViewMasterElement, recordId);
            }
            mydb.close();


            System.out.println("(updateNewScoutCreatedAction)scoutViewMasterElement = " + tempscoutViewMasterElement );
/*
                JSONObject jsonParam = new JSONObject();
                Boolean success = false;
                try {


                    //jsonParam.put(ScoutConstants.SCOUT_DATA_TEAMNAME, newScoutTeamNameField.getText().toString());
                    //jsonParam.put(ScoutConstants.SCOUT_DATA_TEAMNUMBER, newScoutTeamNumberField.getText().toString());
                    //jsonParam.put(ScoutConstants.SCOUT_DATA_GAMENUMBER, newScoutGameNumberField.getText().toString());
                    //jsonParam.put(ScoutConstants.SCOUT_DATA_LANDED, newscoutlanded);
                   // jsonParam.put(ScoutConstants.SCOUT_DATA_SAMPLED,newscoutsampled);
                   // jsonParam.put(ScoutConstants.SCOUT_DATA_CLAIMEDDEPOT, newscoutclaimeddepot);
                   // jsonParam.put(ScoutConstants.SCOUT_DATA_PARKED, newscoutparked);
                    //jsonParam.put(ScoutConstants.SCOUT_DATA_DEPOTSCORED, newScoutDepotScoredField.getText().toString());
                   // jsonParam.put(ScoutConstants.SCOUT_DATA_SILVERLANDERSCORED, newScoutSilverLanderScoredField.getText().toString());
                   // jsonParam.put(ScoutConstants.SCOUT_DATA_GOLDLANDERSCORED, newScoutGoldLanderScoredField.getText().toString());
                   // jsonParam.put(ScoutConstants.SCOUT_DATA_LATCHED, newscoutlatched);
                    //jsonParam.put(ScoutConstants.SCOUT_DATA_CRATERPARTIAL, newscoutcraterpartial);
                   // jsonParam.put(ScoutConstants.SCOUT_DATA_CRATERFULL, newscoutcraterfull);


                    jsonParam = scoutViewMasterElement.getjsonMap();
                    success = true;
                } catch (JSONException e) {
                    //Show error to user
                    //Show error to user
                    Log.i("updateNewScoutAction", "json encoding failed");

                }
                if (success == true) {
                    Log.i("updateNewScoutAction", "json encoding was successful " + "jsonParam.toString() = " + jsonParam.toString());

                    saveSharedPreferences(jsonParam);
                }
*/
        }

        //Save the SharedPreferences

        void saveSharedPreferences(JSONObject jsonParam) {

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(ScoutConstants.SCOUT_DATA_SHAREDPREFERENCES_TAG, Context.MODE_PRIVATE);

            if (sharedPreferences != null) {
                String scoutMasterElementString = sharedPreferences.getString(ScoutConstants.SCOUT_DATA_SHAREDPREFERENCES_ELEMENTS_TAG, ScoutConstants.SCOUT_DATA_NOT_AVAILABLE_STRING);

                if (!(scoutMasterElementString.equalsIgnoreCase(ScoutConstants.SCOUT_DATA_NOT_AVAILABLE_STRING)))
                {
                    //First create the JSON array from the JSONObject string . Insert the new record. again create the new JSON array and create String .
                    //Commit the changes in the sharedPreferences.

                    JSONArray jArray = null;
                    try {
                        jArray = new JSONArray(scoutMasterElementString);
                        jArray.put(jsonParam);      // Add the new element


                    } catch (JSONException e) {
                        //Can not parse the result
                        //Show error to user
                        System.out.println("(saveSharedPreferences)Cannot parse JSON" );
                    }

                    if (jArray != null)
                    {
                        String finalMasterElement = jArray.toString();
                        //Store the info in sharedPreferences
                        sharedPreferences.edit().putString(ScoutConstants.SCOUT_DATA_SHAREDPREFERENCES_ELEMENTS_TAG, finalMasterElement).commit();
                        System.out.println("(saveSharedPreferences)finalMasterElement = " + finalMasterElement );
                    }
                }
                else
                {
                    //First Record
                    JSONArray jArray = null;
                    jArray = new JSONArray();
                    jArray.put(jsonParam);      // Add the new element

                    String finalMasterElement = jArray.toString();
                    //Store the info in sharedPreferences
                    sharedPreferences.edit().putString(ScoutConstants.SCOUT_DATA_SHAREDPREFERENCES_ELEMENTS_TAG, finalMasterElement).commit();

                    System.out.println("(saveSharedPreferences) first finalMasterElement = " + finalMasterElement );

                }
            }
        }
    }
}

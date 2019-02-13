/*
 * Copyright (c) 2019
 * All rights reserved Amogh Mehta
 * Last Modified 1/7/19 12:18 PM
 */

package com.example.personal.scoutingappandroid.feature.ScoutView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.personal.scoutingappandroid.feature.R;

import java.util.ArrayList;


public class ScoutViewMasterRecyclerAdaptor extends RecyclerView.Adapter <ScoutViewMasterRecyclerAdaptor.ScoutViewMasterRecyclerViewHolder> {
    ArrayList<ScoutViewMasterElement> scoutViewMasterElementArrayList = new ArrayList<ScoutViewMasterElement>();

    ScoutViewMasterRecyclerAdaptor.OnScoutViewMasterRecyclerAdapterRowSelected onScoutViewMasterRecyclerAdapterRowSelected;        //Interface to send Data

    public ScoutViewMasterRecyclerAdaptor(ArrayList<ScoutViewMasterElement> arrayList, ScoutViewMasterRecyclerAdaptor.OnScoutViewMasterRecyclerAdapterRowSelected callback)
    {
        this.scoutViewMasterElementArrayList = arrayList;
        this.onScoutViewMasterRecyclerAdapterRowSelected = callback;       //Store the class of the fragement to send the clicklistener event
    }


    @Override
    public ScoutViewMasterRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)   //i contains viewType
    {
        // Log.i("onCreateViewHolder", "called ");

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.scoutviewmaster_row_layout, viewGroup, false);
        ScoutViewMasterRecyclerAdaptor.ScoutViewMasterRecyclerViewHolder recycleViewHolder = new ScoutViewMasterRecyclerAdaptor.ScoutViewMasterRecyclerViewHolder(view);

        return recycleViewHolder;
    }

    @Override
    public void onBindViewHolder(ScoutViewMasterRecyclerAdaptor.ScoutViewMasterRecyclerViewHolder viewHolder, int position)     //i contains position
    {

        System.out.println("(onBindViewHolder) in "  );

        ScoutViewMasterElement  scoutViewMasterElement = scoutViewMasterElementArrayList.get(position);
        //Log.i("onBindViewHolder ", "position = " + position + "scoutViewMasterElement =  " + scoutViewMasterElement);

        //Team Name
        viewHolder.scoutviewmasterteamname.setText(scoutViewMasterElement.getTeamName());

        viewHolder.scoutviewmasterteamnumber.setText(scoutViewMasterElement.getTeamNumber());

        viewHolder.scoutviewmastergamenumber.setText("Game # " + scoutViewMasterElement.getGameNumber());

        viewHolder.scoutviewmastergamescore.setText("Score = " + scoutViewMasterElement.getGameScore());

        //Game Date
        //SimpleDateFormat dateFormat = new SimpleDateFormat(ScoutConstants.SCOUT_DATA_DATEFORMAT );


        viewHolder.scoutviewmastergamedate.setText( scoutViewMasterElement.getGameDateString());


        //Set up listener
        viewHolder.scoutViewMasterTableLayout.setOnClickListener(clickListener);
        viewHolder.scoutViewMasterTableLayout.setTag(position);
    }

    @Override
    public int getItemCount()
    {

        int ret = (null != scoutViewMasterElementArrayList ? scoutViewMasterElementArrayList.size() : 0);

        //Log.i("getItemCount", "called ret = " + ret);

        return ret;

    }

    public void updateArrayList(ArrayList<ScoutViewMasterElement> arrayList)
    {
        this.scoutViewMasterElementArrayList = arrayList;
    }

    public static class  ScoutViewMasterRecyclerViewHolder extends RecyclerView.ViewHolder
    {
        TextView scoutviewmasterteamname;
        TextView scoutviewmasterteamnumber;
        TextView scoutviewmastergamenumber;
        TextView scoutviewmastergamedate;        TextView scoutviewmastergamescore;

        TableLayout scoutViewMasterTableLayout;

        public ScoutViewMasterRecyclerViewHolder(View itemView) {

            super(itemView);

            this.scoutviewmasterteamname = (TextView) itemView.findViewById(R.id.scoutviewmasterteamname);
            this.scoutviewmasterteamnumber = (TextView) itemView.findViewById(R.id.scoutviewmasterteamnumber);
            this.scoutviewmastergamenumber = (TextView) itemView.findViewById(R.id.scoutviewmastergamenumber);
            this.scoutviewmastergamedate = (TextView) itemView.findViewById(R.id.scoutviewmastergamedate);
            this.scoutviewmastergamescore = (TextView) itemView.findViewById(R.id.scoutviewmastergamescore);
            this.scoutViewMasterTableLayout = (TableLayout) itemView.findViewById(R.id.scoutviewmastertableid);
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = (int)view.getTag();

            //Log.i("clickListener", "id =  " + id);

            ScoutViewMasterElement scoutViewMasterElement = scoutViewMasterElementArrayList.get(id);

            //Log.i("clickListener ",  "scoutViewMasterElement =  " + scoutViewMasterElement);

            onScoutViewMasterRecyclerAdapterRowSelected.setScoutViewMasterRecyclerAdaptorofSelectedRow(scoutViewMasterElement);     //Inform to InboxMaster

        }
    };


    ///////////////////////////Function to send value to activity /////////////////////////////////////
    //since Fragment is Activity dependent, you need to pass the information using interface to activity
    //Interface to pass the InboxMasterFragment to activity when user select a Inbox from a InboxMaster List
    public interface OnScoutViewMasterRecyclerAdapterRowSelected
    {
        public void setScoutViewMasterRecyclerAdaptorofSelectedRow( ScoutViewMasterElement scoutViewMasterElement);
    }
}

package com.gamehunter.lukasz.gamehunter.model;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.gamehunter.lukasz.gamehunter.fragment.WatcherFragment;
import com.gamehunter.lukasz.gamehunter.parser.WatchListDealsInterpreter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Łukasz on 2017-01-07.
 */

public class Store {


    private String title=""; // Title of the game/app
    private String price; // Price on discount
    private String supplement; // Fluff - previous price/thumbnail
    private String dealID; // Binding to concrete offer on The Internet

    public Store(String name, String price, String dealRating, String dealID) {
        this.title = name;
        this.price = price;
        this.supplement = dealRating;
        this.dealID = dealID;
    }

    static List<Store> dataList = Collections.synchronizedList(new ArrayList<Store>());

    //Call to async API request
    public static synchronized void addEntryLoad(String name) {
        WatchListDealsInterpreter.searchEntry(name);
    }

    //Creation of data based on received async API answer
    public static synchronized void addEntryConcrete(Store tmp) {
        Store cnc = tmp;

        if(dataList.contains(cnc)) return;
        dataList.add(cnc);
        WatcherFragment.RefreshRecycler(WatcherFragment.watcherFragment.getContext());
        Save();
        
        //Refresh GUI
    }

    private static void Save() {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(WatcherFragment.mainView.getContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();

        String json = gson.toJson(dataList);

        editor.putString("Data", json);
        editor.commit();

    }

    public static void Load()   {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(WatcherFragment.mainView.getContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString("Data", null);
        System.out.println(json);
        if(json!=null) {
            Type type = new TypeToken<ArrayList<Store>>() {
            }.getType();
            dataList = gson.fromJson(json, type);
        }
    }

    public static synchronized void updateEntry(Store toUpdate)  {
        String tmp = toUpdate.getTitle();
        removeEntry(toUpdate);
        addEntryLoad(tmp);
    }

    public static synchronized void removeEntry(Store toRemove) {
        dataList.remove(toRemove);
        WatcherFragment.RefreshRecycler(WatcherFragment.watcherFragment.getContext());
        Save();
    }

    public String getSupplement() {
        return supplement;
    }

    public String getDealID() {
        return dealID;
    }

    public void setDealID(String dealID) {
        this.dealID = dealID;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public void setSupplement(String supplement) {
        this.supplement = supplement;
    }

    public void setTitle(String title)  {
        this.title=title;
    }

    public void setPrice(String desc)  {
        this.price =desc;
    }

    public static List<Store> getData()    {
       return dataList;
    }
}

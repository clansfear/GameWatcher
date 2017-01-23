package com.gamehunter.lukasz.gamehunter.parser;

import android.support.design.widget.Snackbar;

import com.gamehunter.lukasz.gamehunter.fragment.WatcherFragment;
import com.gamehunter.lukasz.gamehunter.model.Store;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Łukasz on 2017-01-18.
 */

public class WatchListDealsInterpreter {
    public static void searchEntry(String name)    {

        DataReceiver.get("games?title=" + name + "&limit=1", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {

                JSONObject nextEvent = null;
                try {
                    nextEvent = timeline.getJSONObject(0);

                } catch (JSONException e) {
                    Snackbar.make(WatcherFragment.mainView, "Couldn't find your game :(", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    e.printStackTrace();
                    return;
                }

                System.out.println("Before error:");
                System.out.println(timeline);

                try {


                    sendNewEntry(nextEvent.getString("external"), nextEvent);
                } catch (JSONException e) {
                    Snackbar.make(WatcherFragment.mainView, "Couldn't find your game :(", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    e.printStackTrace();
                }
            }});
    }

    public static void sendNewEntry(String name, JSONObject nextEvent) throws JSONException {
        Store newDeal = new Store(name,nextEvent.getString("cheapest"),nextEvent.getString("thumb"),nextEvent.getString("cheapestDealID"));
        Store.addEntryConcrete(newDeal);
    }
}

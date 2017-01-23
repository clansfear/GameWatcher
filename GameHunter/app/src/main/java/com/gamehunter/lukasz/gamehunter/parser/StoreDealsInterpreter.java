package com.gamehunter.lukasz.gamehunter.parser;

import org.json.*;

import com.gamehunter.lukasz.gamehunter.fragment.MainScreenFragment;
import com.gamehunter.lukasz.gamehunter.model.Deal;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Łukasz on 2017-01-11.
 */

public class StoreDealsInterpreter {

    private static Deal[] Steam,GOG,Amazon;
    private static boolean syncFlag1,syncFlag2,syncFlag3;

    public static void getInfo(final MainScreenFragment cb) throws JSONException  {

        syncFlag1=syncFlag2=syncFlag3=false;

        DataReceiver.get("deals?storeID=1&AAA=1&onSale=1", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                Steam=new Deal[5];
                getInfo(timeline,Steam);
                syncFlag1=true;
                updateMainScreenFragmentGUI(cb);
            }
        });

        DataReceiver.get("deals?storeID=4&AAA=1&onSale=1", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                Amazon=new Deal[5];
                getInfo(timeline,Amazon);
                syncFlag2=true;
                updateMainScreenFragmentGUI(cb);
            }
        });

        DataReceiver.get("deals?storeID=7&AAA=1&onSale=1", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                GOG=new Deal[5];
                getInfo(timeline,GOG);
                syncFlag3=true;
                updateMainScreenFragmentGUI(cb);
            }
        });
    }

    private static void getInfo(JSONArray timeline, Deal[] store) {

        for(int i=0; i<5; i++) {
            JSONObject nextEvent = null;
            try {
                nextEvent = timeline.getJSONObject(i);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                store[i] = new Deal(nextEvent.getString("title"),nextEvent.getString("salePrice"),nextEvent.getString("normalPrice"),nextEvent.getString("dealID"));
                System.out.println(store[i].getName());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private static void updateMainScreenFragmentGUI(MainScreenFragment cb)   {
        if(syncFlag1&&syncFlag2&&syncFlag3)   {
                    cb.updateViews(Steam, Amazon, GOG);
        }
    }

    /*public static void getGamesFromStore  () throws JSONException {
        DataReceiver.get("deals?storeID=6&AAA=1", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline
                JSONObject firstEvent = null;
                try {


                    firstEvent = timeline.getJSONObject(0);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String tweetText = null;
                try {


                    tweetText = firstEvent.getString("title");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Do something with the response
                System.out.println(tweetText);
            }
        });
    }
    */



}

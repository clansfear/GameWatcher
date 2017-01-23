package com.gamehunter.lukasz.gamehunter.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.EasyEditSpan;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gamehunter.lukasz.gamehunter.R;
import com.gamehunter.lukasz.gamehunter.model.Deal;
import com.gamehunter.lukasz.gamehunter.parser.StoreDealsInterpreter;

import org.json.JSONException;


public class MainScreenFragment extends Fragment implements Refreshable {


    private View mainView;

    private TextView[][] titleTextViews = new TextView[3][5];
    private TextView[][] priceTextViews = new TextView[3][5];

    private Handler handler;
    private MainScreenFragment thisScreen;

    public MainScreenFragment() {
        // Required empty public constructor
    }

    private Runnable refresher = new Runnable() {
        @Override
        public void run() {
            System.out.println("TEST");
            try {
                StoreDealsInterpreter.getInfo(thisScreen);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Refresh(this);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        thisScreen=this;
        mainView=inflater.inflate(R.layout.fragment_main_screen, container, false);

        viewBinder();

        try {
            StoreDealsInterpreter.getInfo(this);
            handler = new Handler();
            Refresh(refresher);

        } catch (JSONException e) {
            e.printStackTrace();
        }



        //System.out.println(/txt.getText());
        return mainView;
    }

    public void Refresh(Runnable trg) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mainView.getContext());
        String mins = sharedPrefs.getString("Minutes", "5");
        handler.postDelayed(trg, Integer.parseInt(mins)*60*1000);
    }

    public void updateViews(final Deal[] steam, Deal[] amazon, Deal[] gog) {

        consumeStoreAPIPayload(steam,0);
        consumeStoreAPIPayload(amazon,1);
        consumeStoreAPIPayload(gog,2);

    }

    private void consumeStoreAPIPayload(final Deal[] storeName, int storeID) {
        for(int i=0; i<5; i++)  {

            titleTextViews[storeID][i].setText(storeName[i].getName());
            final int finalI = i;
            titleTextViews[storeID][i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cheapshark.com/redirect?dealID=" + storeName[finalI].getDealID()));
                    startActivity(browserIntent);
                }
            });

            String storeEntry = storeName[i].getDealRating() + " " + storeName[i].getPrice() +"$";
            SpannableString txt = new SpannableString(storeEntry);
            txt.setSpan(new StrikethroughSpan(),0,storeName[i].getDealRating().length(),0);
            txt.setSpan(new EasyEditSpan(),storeName[i].getDealRating().length(),storeEntry.length()-storeName[i].getDealRating().length(),0);

            priceTextViews[storeID][i].setText(txt);
        }
    }

    private void viewBinder()    {
        titleTextViews =new TextView[3][];
        titleTextViews[0] = new TextView[5];
        titleTextViews[1] = new TextView[5];
        titleTextViews[2] = new TextView[5];

        titleTextViews[0][0]=(TextView) mainView.findViewById(R.id.s1g1);
        titleTextViews[0][1]=(TextView) mainView.findViewById(R.id.s1g2);
        titleTextViews[0][2]=(TextView) mainView.findViewById(R.id.s1g3);
        titleTextViews[0][3]=(TextView) mainView.findViewById(R.id.s1g4);
        titleTextViews[0][4]=(TextView) mainView.findViewById(R.id.s1g5);
        titleTextViews[1][0]=(TextView) mainView.findViewById(R.id.s2g1);
        titleTextViews[1][1]=(TextView) mainView.findViewById(R.id.s2g2);
        titleTextViews[1][2]=(TextView) mainView.findViewById(R.id.s2g3);
        titleTextViews[1][3]=(TextView) mainView.findViewById(R.id.s2g4);
        titleTextViews[1][4]=(TextView) mainView.findViewById(R.id.s2g5);
        titleTextViews[2][0]=(TextView) mainView.findViewById(R.id.s3g1);
        titleTextViews[2][1]=(TextView) mainView.findViewById(R.id.s3g2);
        titleTextViews[2][2]=(TextView) mainView.findViewById(R.id.s3g3);
        titleTextViews[2][3]=(TextView) mainView.findViewById(R.id.s3g4);
        titleTextViews[2][4]=(TextView) mainView.findViewById(R.id.s3g5);


        priceTextViews[0][0]=(TextView) mainView.findViewById(R.id.s1g1p);
        priceTextViews[0][1]=(TextView) mainView.findViewById(R.id.s1g2p);
        priceTextViews[0][2]=(TextView) mainView.findViewById(R.id.s1g3p);
        priceTextViews[0][3]=(TextView) mainView.findViewById(R.id.s1g4p);
        priceTextViews[0][4]=(TextView) mainView.findViewById(R.id.s1g5p);
        priceTextViews[1][0]=(TextView) mainView.findViewById(R.id.s2g1p);
        priceTextViews[1][1]=(TextView) mainView.findViewById(R.id.s2g2p);
        priceTextViews[1][2]=(TextView) mainView.findViewById(R.id.s2g3p);
        priceTextViews[1][3]=(TextView) mainView.findViewById(R.id.s2g4p);
        priceTextViews[1][4]=(TextView) mainView.findViewById(R.id.s2g5p);
        priceTextViews[2][0]=(TextView) mainView.findViewById(R.id.s3g1p);
        priceTextViews[2][1]=(TextView) mainView.findViewById(R.id.s3g2p);
        priceTextViews[2][2]=(TextView) mainView.findViewById(R.id.s3g3p);
        priceTextViews[2][3]=(TextView) mainView.findViewById(R.id.s3g4p);
        priceTextViews[2][4]=(TextView) mainView.findViewById(R.id.s3g5p);



    }


}

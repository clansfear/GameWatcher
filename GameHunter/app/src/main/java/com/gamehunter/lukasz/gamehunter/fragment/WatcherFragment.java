package com.gamehunter.lukasz.gamehunter.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.gamehunter.lukasz.gamehunter.R;
import com.gamehunter.lukasz.gamehunter.adapter.RecyclerAdapter;
import com.gamehunter.lukasz.gamehunter.model.Store;


@SuppressLint("StaticFieldLeak")
public class WatcherFragment extends Fragment implements Refreshable {


    public static WatcherFragment watcherFragment;
    public static View mainView;

    private Runnable refresher = new Runnable() {
        @Override
        public void run() {

            for(int i=0; i<Store.getData().size(); i++) {
                Store.updateEntry(Store.getData().get(i));
            }

            RefreshRecycler(mainView.getContext());

            Refresh(this);
        }
    };
    private Handler handler;

    public WatcherFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setUpRecyclerVIew() {

        watcherFragment =this;
        RefreshRecycler(this.getContext());
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView=inflater.inflate(R.layout.fragment_watcher, container, false);
        setActionButton(inflater);
        Store.Load();
        setUpRecyclerVIew();
        handler=new Handler();
        Refresh(refresher);
        return mainView;
    }

    private void setActionButton(final LayoutInflater inflater) {
        FloatingActionButton myFab = (FloatingActionButton) mainView.findViewById(R.id.floatingActionButton);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Dialog dialog = new Dialog(inflater.getContext());

                dialog.setContentView(R.layout.custom_dialog);
                dialog.setTitle("Custom Alert Dialog");

                final EditText editText = (EditText) dialog.findViewById(R.id.editText);
                Button btnSave          = (Button) dialog.findViewById(R.id.save);
                btnSave.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Store.addEntryLoad(editText.getText().toString());
                        dialog.dismiss();
                    }
                });
                Button btnCancel = (Button) dialog.findViewById(R.id.cancel);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    public static void RefreshRecycler(Context context) {
        RecyclerView recyclerView = (RecyclerView) mainView.findViewById(R.id.recyclerView);
        RecyclerAdapter adapter = new RecyclerAdapter(context, Store.getData(), watcherFragment);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(context);
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void Refresh(Runnable trg) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mainView.getContext());
        String mins = sharedPrefs.getString("Minutes", "5");
        handler.postDelayed(trg, Integer.parseInt(mins)*60*1000);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /*public void dispatchActivity(Intent inte) {
        startActivity(inte);
    }
    */
}

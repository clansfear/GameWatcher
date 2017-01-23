package com.gamehunter.lukasz.gamehunter.fragment;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.gamehunter.lukasz.gamehunter.R;


public class SettingsFragment extends Fragment {

    private View mainView;
    private EditText yourEditText;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView=inflater.inflate(R.layout.fragment_settings, container, false);
        setUpEvents();
        return mainView;
    }

    private void setUpEvents() {
        yourEditText = (EditText) mainView.findViewById(R.id.editText7);

        yourEditText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                // you can call or do what you want with your EditText here
                if(yourEditText.getText()==null  || yourEditText.getText().toString().equals("") ) {


                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mainView.getContext());
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putString("Minutes", "5");
                    editor.apply();

                }   else    {
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mainView.getContext());
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putString("Minutes", yourEditText.getText().toString());
                    editor.apply();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });


        Switch update_toggle=(Switch) mainView.findViewById(R.id.switch1);
        update_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mainView.getContext());
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putBoolean("AutoUpdate",isChecked);
                editor.apply();
            }
        });


        Switch updatewf_toggle=(Switch) mainView.findViewById(R.id.switch2);
        updatewf_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mainView.getContext());
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putBoolean("AutoUpdateWiFi",isChecked);
                editor.apply();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();



    }

}

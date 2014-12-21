package com.simonzeiger.minimaltd;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by simon on 12/16/14.
 */
public class SettingsActivity extends ActionBarActivity {

    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   MainActivity.isBackpressed = true;
                    onBackPressed();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        MainActivity.isBackpressed = true;
        finish();
    }
}

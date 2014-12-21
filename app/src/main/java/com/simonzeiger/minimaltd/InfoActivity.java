package com.simonzeiger.minimaltd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by simon on 12/20/14.
 */
public class InfoActivity extends ActionBarActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private String[] leftSliderData = {"Home", "About", "Libraries", "Settings"};
    private boolean bool;

    ListView leftDrawerList;

    ArrayAdapter<String> navigationDrawerAdapter;

    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infoactivity);
        toolbar = (Toolbar) findViewById(R.id.app_bar);

        textView = (TextView) findViewById(R.id.aboutTextView);

        Intent intent = getIntent();
        bool = intent.getBooleanExtra("ABOUT", true);

        initView();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        initDrawer();



        if (bool) {
            getSupportActionBar().setTitle("About");
            textView.setText("Hello! Thank you so much for downloading my app. This is my first ever android app, " +
                    "and their's probably way better ways of going about what I did, but that's ok. I hope you enjoy using it as much as I did making it!" +
                    "\n\nThe source code is available on my github:" +
                    "\n");
        } else {
            getSupportActionBar().setTitle("Libraries");
            textView.setText("I used a few of opensource libraries for this, here they are:" +
                    "\n\nTinyDB:" +
                    "\nhttps://github.com/kcochibili/TinyDB--Android-Shared-Preferences-Turbo/blob/master/TinyDB.java" +
                    "\n\nFloating Action Button:" +
                    "\nhttps://github.com/makovkastar/FloatingActionButton");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

    }


    private void initView() {
        boolean isAbout = true;
        leftDrawerList = (ListView) findViewById(R.id.left_drawer);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationDrawerAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.navlistitem, leftSliderData);
        leftDrawerList.setAdapter(navigationDrawerAdapter);
        leftDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent newTaskScreen = new Intent(getApplicationContext(), MainActivity.class);

                    startActivity(newTaskScreen);
                }
                if (position == 1) {
                    if (bool) {
                        drawerLayout.closeDrawers();
                    } else {
                        Intent newTaskScreen = new Intent(getApplicationContext(), InfoActivity.class);

                        newTaskScreen.putExtra("ABOUT", true);

                        startActivity(newTaskScreen);
                    }


                }
                if (position == 2) {
                    if (!bool) {
                        drawerLayout.closeDrawers();
                    } else {
                        Intent newTaskScreen = new Intent(getApplicationContext(), InfoActivity.class);

                        newTaskScreen.putExtra("ABOUT", false);

                        startActivity(newTaskScreen);
                    }
                }

                if (position == 3) {
                    Intent newTaskScreen = new Intent(getApplicationContext(), SettingsActivity.class);

                    startActivity(newTaskScreen);

                }

            }
        });
    }

    private void initDrawer() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        drawerToggle.syncState();
    }
}

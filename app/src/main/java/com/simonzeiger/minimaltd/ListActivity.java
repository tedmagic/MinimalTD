package com.simonzeiger.minimaltd;


import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import TinyDB.TinyDB;


public class ListActivity extends ActionBarActivity {

    public static boolean isBackpressed = false;
    public static boolean isEdit = false;
    public static String oldText = "";
    public static int itemLongPressed;
    public static boolean longPress = false;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private String[] leftSliderData = {"Home", "About", "Libraries","Settings"};
    private int itemPressed;
    private boolean allDelete = false;


    ListView leftDrawerList;

    ArrayAdapter<String> navigationDrawerAdapter;

    static ArrayAdapter<String> adapter;

    static ArrayList<String> listItems = new ArrayList<>();


    @Override
    protected void onSaveInstanceState(Bundle outState) {


        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        initDrawer();


        initList();


    }


    @Override
    protected void onStop() {
        setSettings();

        allDelete = false;
        super.onStop();
    }


    public void setSettings() {

        TinyDB tinyDB = new TinyDB(getApplicationContext());
        tinyDB.putList("ITEMS", listItems);


    }

    private void restoreSettings() {

        TinyDB tinyDB = new TinyDB(getApplicationContext());
        ArrayList <String> tempList = tinyDB.getList("ITEMS");
        for(int i = 0; i < tempList.size(); i++){
            listItems.add(tempList.get(i));
        }


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

    private void initView() {
        boolean isAbout = true;
        leftDrawerList = (ListView) findViewById(R.id.left_drawer);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationDrawerAdapter = new ArrayAdapter<>(ListActivity.this,R.layout.navlistitem, leftSliderData);
        leftDrawerList.setAdapter(navigationDrawerAdapter);
        leftDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    drawerLayout.closeDrawers();
                }
                if(position == 1){
                    Intent newTaskScreen = new Intent(getApplicationContext(), InfoActivity.class);

                    newTaskScreen.putExtra("ABOUT", true);

                    startActivity(newTaskScreen);


                }
                if(position == 2){
                    Intent newTaskScreen = new Intent(getApplicationContext(), InfoActivity.class);

                    newTaskScreen.putExtra("ABOUT", false);

                    startActivity(newTaskScreen);
                }

                if (position == 3) {
                    Intent newTaskScreen = new Intent(getApplicationContext(), SettingsActivity.class);

                    startActivity(newTaskScreen);

                }

            }
        });
    }

    private void initList() {
        final ListView mListView = (ListView) findViewById(R.id.theListView);


        adapter = new ArrayAdapter<>(this,
                R.layout.listitem,
                listItems);

        if (listItems.size() == 0 && !allDelete)
            restoreSettings();


        mListView.setAdapter(adapter);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                if (!longPress) {
                    itemPressed = position;
                    Intent newTaskScreen = new Intent(getApplicationContext(), FABActivity.class);

                    final int result = 2;

                    startActivityForResult(newTaskScreen, result);

                    oldText = String.valueOf(adapterView.getItemAtPosition(position));

                    isEdit = true;
                }


            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                DialogFragment myFragment = new ListItemDialogFrag();

                myFragment.show(getFragmentManager(), "theDialog");
                itemLongPressed = position;
                longPress = true;


                return true;
            }
        });


        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (listItems.size() != 0) fab.attachToListView(mListView);


    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        drawerToggle.syncState();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent newTaskScreen = new Intent(getApplicationContext(), SettingsActivity.class);

            final int result = 3;

            startActivityForResult(newTaskScreen, result);
            return true;
        } else if (id == R.id.delete_all) {


            DialogFragment myFragment = new ListItemDialogFrag();

            ListItemDialogFrag.allDelete = true;

            allDelete = true;

            myFragment.show(getFragmentManager(), "theDialog");


            return true;

        }


        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

    }

    public void setOnClickListenerFAB(View view) {


        Intent newTaskScreen = new Intent(this, FABActivity.class);

        final int result = 1;

        startActivityForResult(newTaskScreen, result);

        isEdit = false;


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!isBackpressed) {
            if (isEdit) {
                String nameSentBack = data.getStringExtra("Task");
                listItems.set(itemPressed, nameSentBack);
                adapter.notifyDataSetChanged();
                ListActivity.isEdit = false;

            } else {
                String nameSentBack = data.getStringExtra("Task");
                listItems.add(nameSentBack);
                adapter.notifyDataSetChanged();

            }
        }

    }


}

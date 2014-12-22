package com.simonzeiger.minimaltd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by simon on 12/13/14.
 */
public class FABActivity extends ActionBarActivity {
    private android.support.v7.widget.Toolbar toolbar;

    EditText task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.isBackpressed = false;
        setContentView(R.layout.fabactivity);


        Button button = (Button) findViewById(R.id.taskButton);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            setBackButton();
        }
        task = (EditText) findViewById(R.id.editText);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        if (MainActivity.isEdit) {

            getSupportActionBar().setTitle("Edit Task");
            button.setText("Edit");


            task.setText(MainActivity.oldText);
            task.setSelection(task.getText().length());

        } else getSupportActionBar().setTitle("New Task");


    }

    @Override
    public void onBackPressed() {
        MainActivity.isBackpressed = true;
        finish();
    }

    public void onAddTask(View view) {

        String strTask = String.valueOf(task.getText());
        if (!(String.valueOf(task.getText()).equals(""))) {
            Intent reportTask = new Intent();
            reportTask.putExtra("Task", strTask);
            setResult(RESULT_OK, reportTask);


            finish();
        }

    }

    private void setBackButton() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}

package com.simonzeiger.minimaltd;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;


/**
 * Created by simon on 11/30/14.
 */
public class ListItemDialogFrag extends DialogFragment {
    public static boolean allDelete = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder theDialog = new AlertDialog.Builder(getActivity());

        MainActivity.longPress = false;


        if (allDelete) theDialog.setMessage("Delete All Tasks?");
        else theDialog.setMessage("Delete this task?");

        theDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                if (allDelete) {

                    MainActivity.listItems.clear();

                    MainActivity.listItems.add("");


                    MainActivity.adapter.notifyDataSetChanged();

                    allDelete = false;

                    if(!allDelete && MainActivity.listItems != null ){
                        MainActivity.listItems.remove(0);
                    }



                } else {
                    MainActivity.listItems.remove(MainActivity.itemLongPressed);
                    MainActivity.adapter.notifyDataSetChanged();
                    MainActivity.longPress = false;
                }


            }


        });


        theDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                MainActivity.longPress = false;
            }
        });
        return theDialog.create();
    }
}



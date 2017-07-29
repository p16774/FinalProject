package com.project3w.finalproject.Fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.project3w.finalproject.R;

import org.w3c.dom.Text;

/**
 * Created by Nate on 7/28/17.
 */

public class PurchaseListFragment extends ListFragment {

    // interface to pass the selected date up to the main activity
    public interface SelectedItemListener {
        void setSelectedItem(String item);
    }

    String purchaseArray[] = { "PS4","Xbox One","New TV" };
    Activity mActivity;
    SelectedItemListener onSelectedItemListener;

    public PurchaseListFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_purchase , container, false);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = getActivity();

        try {
            onSelectedItemListener = (SelectedItemListener) mActivity;
        } catch (ClassCastException e) {
            throw new ClassCastException(mActivity.toString() + " must implement SelectedItemListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, purchaseArray);

        setListAdapter(stringArrayAdapter);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFormDialog();
            }
        });
    }

    protected void showFormDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.input_form, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setTitle("Add Item");

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        TextView formItemName = (TextView) getActivity().findViewById(R.id.formItemName);
                        String itemname;
                        try {
                            itemname = formItemName.getText().toString();
                        } catch (Exception e) {
                            itemname = "Default Item";
                            Log.d("Error", "Blank Message");
                        }
                        onSelectedItemListener.setSelectedItem(itemname);

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }



    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        onSelectedItemListener.setSelectedItem(purchaseArray[position]);

    }
}

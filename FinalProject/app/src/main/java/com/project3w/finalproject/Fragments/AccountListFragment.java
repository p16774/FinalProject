package com.project3w.finalproject.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.project3w.finalproject.R;
import com.project3w.finalproject.WebViewActivity;

/**
 * Created by Nate on 7/28/17.
 */

public class AccountListFragment extends ListFragment {

    public interface SelectedAccountListener {
        void setSelectedAccount (String account);
    }

    String accountsArray[] = { "Chase Bank","Capital One","USAA" };
    Activity mActivity;
    SelectedAccountListener onSelectedAccountListener;

    public AccountListFragment() {
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
            onSelectedAccountListener = (SelectedAccountListener) mActivity;
        } catch (ClassCastException e) {
            throw new ClassCastException(mActivity.toString() + " must implement SelectedAccountListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, accountsArray);

        setListAdapter(stringArrayAdapter);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent accountIntent = new Intent(getActivity(), WebViewActivity.class);
                startActivity(accountIntent);
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        onSelectedAccountListener.setSelectedAccount(accountsArray[position]);

    }


}

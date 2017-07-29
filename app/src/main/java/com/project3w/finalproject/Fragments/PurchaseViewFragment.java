package com.project3w.finalproject.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.project3w.finalproject.R;

import static com.project3w.finalproject.MainActivity.SELECTED_ITEM;

/**
 * Created by Nate on 7/28/17.
 */

public class PurchaseViewFragment extends Fragment {

    public PurchaseViewFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.purchase_view, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        TextView accountName = (TextView) getActivity().findViewById(R.id.itemname);
        accountName.setText(args.getString(SELECTED_ITEM));

        Button now = (Button) getActivity().findViewById(R.id.buttonnow);
        Button soon = (Button) getActivity().findViewById(R.id.buttonsoon);
        Button later = (Button) getActivity().findViewById(R.id.buttonlater);

        View.OnClickListener pressed = new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        };

        now.setOnClickListener(pressed);
        soon.setOnClickListener(pressed);
        later.setOnClickListener(pressed);

    }
}

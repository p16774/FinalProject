package com.project3w.finalproject.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project3w.finalproject.R;

/**
 * Created by Nate on 7/28/17.
 */

public class NotificationsFragment extends Fragment {

    public NotificationsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notification_view, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        TextView all = (TextView) getActivity().findViewById(R.id.notificationupdate);

        View.OnClickListener pressed = new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        };

        all.setOnClickListener(pressed);

    }
}

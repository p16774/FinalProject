package com.project3w.finalproject.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project3w.finalproject.R;

/**
 * Created by Nate on 7/28/17.
 */

public class AccountSettingsFragment extends Fragment {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public AccountSettingsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.account_settings, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FirebaseUser user = mAuth.getCurrentUser();

        EditText email = (EditText) getActivity().findViewById(R.id.accountemail);
        EditText password = (EditText) getActivity().findViewById(R.id.accountpassword);
        Button update = (Button) getActivity().findViewById(R.id.accountupdate);

        View.OnClickListener pressed = new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        };

        update.setOnClickListener(pressed);

        email.setText(user.getEmail());
        password.setText(user.getUid());

    }
}



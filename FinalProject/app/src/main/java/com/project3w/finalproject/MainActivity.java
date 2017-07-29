package com.project3w.finalproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.project3w.finalproject.Fragments.AccountListFragment;
import com.project3w.finalproject.Fragments.AccountSettingsFragment;
import com.project3w.finalproject.Fragments.AccountViewFragment;
import com.project3w.finalproject.Fragments.NotificationsFragment;
import com.project3w.finalproject.Fragments.PurchaseListFragment;
import com.project3w.finalproject.Fragments.PurchaseViewFragment;

public class MainActivity extends AppCompatActivity implements PurchaseListFragment.SelectedItemListener, AccountListFragment.SelectedAccountListener{

    public static final String MAIN_FRAGMENT = "com.project3w.finalproject.MAIN_FRAGMENT";
    public static final String SELECTED_ITEM = "com.project3w.finalproject.SELECTED_ITEM";
    public static final String SELECTED_ACCOUNT = "com.project3w.finalproject.SELECTED_ACCOUNT";

    private FrameLayout frameLayout;
    private ImageView newImage;
    private TextView messageText;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    frameLayout.removeAllViews();
                    newImage.setImageResource(R.drawable.homescreen);
                    frameLayout.addView(newImage);
                    return true;
                case R.id.navigation_dashboard:
                    frameLayout.removeAllViews();

                    AccountListFragment aFrag = new AccountListFragment();
                    fragmentTransaction.replace(R.id.content, aFrag, MAIN_FRAGMENT);
                    fragmentTransaction.commit();

                    return true;
                case R.id.navigation_notifications:
                    frameLayout.removeAllViews();

                    PurchaseListFragment pFrag = new PurchaseListFragment();
                    fragmentTransaction.replace(R.id.content, pFrag, MAIN_FRAGMENT);
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = (FrameLayout) findViewById(R.id.content);
        newImage = new ImageView(MainActivity.this);
        messageText = new TextView(MainActivity.this);

        newImage.setImageResource(R.drawable.homescreen);
        frameLayout.addView(newImage);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onPostResume() {

        frameLayout.removeAllViews();
        newImage.setImageResource(R.drawable.homescreen);
        frameLayout.addView(newImage);

        super.onPostResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.account_settings) {
            frameLayout.removeAllViews();

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            AccountSettingsFragment sFrag = new AccountSettingsFragment();
            fragmentTransaction.replace(R.id.content, sFrag, MAIN_FRAGMENT);
            fragmentTransaction.addToBackStack(MAIN_FRAGMENT);
            fragmentTransaction.commit();

        } else if (id == R.id.notifications) {
            frameLayout.removeAllViews();

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            NotificationsFragment nFrag = new NotificationsFragment();
            fragmentTransaction.replace(R.id.content, nFrag, MAIN_FRAGMENT);
            fragmentTransaction.addToBackStack(MAIN_FRAGMENT);
            fragmentTransaction.commit();

        } else if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setSelectedItem (String item) {
        frameLayout.removeAllViews();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PurchaseViewFragment pFrag = new PurchaseViewFragment();
        Bundle args = new Bundle();
        args.putString(SELECTED_ITEM, item);
        pFrag.setArguments(args);
        fragmentTransaction.replace(R.id.content, pFrag, MAIN_FRAGMENT);
        fragmentTransaction.addToBackStack(MAIN_FRAGMENT);
        fragmentTransaction.commit();

    }

    public void setSelectedAccount (String account) {
        frameLayout.removeAllViews();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AccountViewFragment aFrag = new AccountViewFragment();
        Bundle args = new Bundle();
        args.putString(SELECTED_ACCOUNT, account);
        aFrag.setArguments(args);
        fragmentTransaction.replace(R.id.content, aFrag, MAIN_FRAGMENT);
        fragmentTransaction.addToBackStack(MAIN_FRAGMENT);
        fragmentTransaction.commit();
    }

}

package com.nut.trade;


import android.app.ActionBar;
import android.app.ActionBar.Tab;

import android.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import adapter.TabsPagerAdapter;


public class fragmentall extends FragmentActivity implements
        ActionBar.TabListener {

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;

    // Tab titles
    private String[] tabs = { "วัน", "เดือน", "ปี" };


    //    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historyfragment);
      //  pd = ProgressDialog.show(fragmentall.this, "กำลังโหลด", "โปรดรอ...");


     //   Log.d("checknotimark",checknotimark);

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        //actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
              //  pd.dismiss();
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

    //    pd.dismiss();
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();


        if (id == R.id.action_settings) {

            return true;
        }

        switch (item.getItemId()) {


            case R.id.home:

            {

                Intent intentitem = new Intent(fragmentall.this, MainActivity.class);
                intentitem.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentitem.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentitem);
                return true;
            }

            case R.id.history: {

                Intent fragment = new Intent(getApplicationContext(), fragmentall.class);
                finish();
                startActivity(fragment);
                return true;


            }

            case R.id.item: {

                Intent newIntent = new Intent(fragmentall.this,itemactivity.class);
                finish();
                startActivity(newIntent);
                return true;

            }

            default:
                return super.onOptionsItemSelected(item);

        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}

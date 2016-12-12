package com.nut.trade;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Model.UserModel;
import adapter.dayadapter;

/**
 * Created by inwso on 28/11/2559.
 */

public class listshowActivity extends Activity{
    private static final String DATABASE_NAME = "trade";
    private static final String TABLE_USER = "item";
    private static final String TABLE_USER_HISTORY= "history";
    private static final int DATABASE_VERSION  = MainActivity.DATABASE_VERSION;
    private ListView listshow;
    private dayadapter adapter;
    private String dayshow = null,monthshow,yearshow ;
    private Button btnclose;
    private TextView txtshowlistclick;
    private List<UserModel> readUserlistshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listshow);
        listshow = (ListView) findViewById(R.id.listshow);
        txtshowlistclick = (TextView) findViewById(R.id.txtshowlistclick);
        txtshowlistclick.setVisibility(View.VISIBLE);
        btnclose = (Button) findViewById(R.id.btnclose);
        Intent intent = getIntent();
        String day = intent.getStringExtra("day");
        monthshow = intent.getStringExtra("month");
        yearshow = intent.getStringExtra("year");
        dayshow =null;
        if (day.equals("1"))
        {
            dayshow = "day BETWEEN 1 and 7 ";
        }
        if (day.equals("2"))
        {
            dayshow = "day BETWEEN 8 and 14";
        }
        if (day.equals("3"))
        {
            dayshow = "day >='15' and day <='21'";
        }
        if (day.equals("4"))
        {
            dayshow = "day >='22' and day <='31'";
        }
        Log.d("Nut",dayshow+monthshow+yearshow);
        getlist(dayshow,monthshow,yearshow);

        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getlist(String dayshow,String monthshow,String yearshow) {

/*
        readUserlistshow = new ArrayList<UserModel>();
        DatabaseHelper helper = new DatabaseHelper(listshowActivity.this);
        readUserlistshow = helper.readUserlistshow(dayshow,monthshow,yearshow);
        listshow.setAdapter(null);
        if (readUserlistshow.size() != 0) {*/
            SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(
                    listshowActivity.this, DATABASE_NAME, null, DATABASE_VERSION) {
                @Override
                public void onCreate(SQLiteDatabase db) {

                }

                @Override
                public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

                }
            };

            ArrayList<String> listid = new ArrayList<String>();
            ArrayList<String> listtitle = new ArrayList<String>();
            ArrayList<String> listdes = new ArrayList<String>();
            ArrayList<String> listlocation = new ArrayList<String>();
            ArrayList<String> listsale = new ArrayList<String>();
            ArrayList<String> listcost = new ArrayList<String>();
            ArrayList<String> listdiscount = new ArrayList<String>();
            ArrayList<String> listcount = new ArrayList<String>();
            ArrayList<String> listtime = new ArrayList<String>();
            ArrayList<String> listdate = new ArrayList<String>();


            String select = "SELECT * FROM " + TABLE_USER_HISTORY +
                    " WHERE " + dayshow + " and month='"+monthshow+"' " +
                    " and year ='"+yearshow+"'"+
                    "ORDER BY ID DESC ";
            Log.d("SELECT", select);
            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(select, null);
            Log.d("select", String.valueOf(cursor));
            int mcountsale = 0;
            int mcountcost = 0;
            int mcount = 0;
            int mcountsum = 0;


            if (cursor.moveToFirst()) {


                do {

                    UserModel model = new UserModel();
                    model.setId(cursor.getString(cursor.getColumnIndex("id")));
                    model.settitle(cursor.getString(cursor.getColumnIndex("title")));
                    model.setdes(cursor.getString(cursor.getColumnIndex("des")));
                    model.setlocation(cursor.getString(cursor.getColumnIndex("location")));
                    model.setsale(cursor.getString(cursor.getColumnIndex("sale")));
                    model.setcost(cursor.getString(cursor.getColumnIndex("cost")));
                    model.setdiscount(cursor.getString(cursor.getColumnIndex("discount")));
                    model.setcount(cursor.getString(cursor.getColumnIndex("countitem")));
                    model.settime(cursor.getString(cursor.getColumnIndex("time")));
                    model.setdate(cursor.getString(cursor.getColumnIndex("date")));

                    listid.add(model.getId());
                    listtitle.add(model.gettitle());
                    listdes.add(model.getdes());
                    listlocation.add(model.getlocation());
                    listsale.add(model.getsale());
                    listcost.add(model.getcost());
                    listdiscount.add(model.getdiscount());
                    listcount.add(model.getcount());
                    listtime.add(model.gettime());
                    listdate.add(model.getdate());


                    mcountsale += Integer.parseInt(model.getsale()) * Integer.parseInt(model.getcount());
                    mcountcost += Integer.parseInt(model.getcost()) * Integer.parseInt(model.getcount());
                    mcount += Integer.parseInt(model.getcount());


                    if (mcount != 0) {
                        txtshowlistclick.setVisibility(View.GONE);
                    } else {
                        txtshowlistclick.setVisibility(View.VISIBLE);
                    }


                } while (cursor.moveToNext());

            }
            db.close();
            cursor.close();

            adapter = new dayadapter(listshowActivity.this, R.id.text, listid, listtitle
                    , listdes, listlocation, listsale
                    , listcost, listdiscount, listcount, listtime
                    , listdate);
            adapter.notifyDataSetChanged();

            listshow.setAdapter(adapter);
        //}


    }
}

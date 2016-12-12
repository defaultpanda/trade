package com.nut.trade;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import Model.UserModel;

public class MainActivity extends Activity {

    private Spinner spnmainsort,spnmainsortlh;
    private Button btnmainclear,btnmainstock,btnmainsale;
    private Button btnmainsort;
    private TextView txtshowmain;
    private static final String DATABASE_NAME = "trade";
    public static final int DATABASE_VERSION = 4;
    private GridView Gridview;
    private static final String ID = "id";
    private static final String TABLE_USER = "item";
    private List<UserModel> readUser;
    private  String sortfrist = "sale",sortsecound = "asc";
    private AdView adView;
    private LinearLayout layoutmain_bottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTheme(R.style.AppTheme);

        bindview();
        eventshowbutton();
        addlistspinfrist();
        addlistspinsecound();
        eventbutton();
        admob();

    }

    private void eventshowbutton() {
        txtshowmain.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getlist();
    }


    private  void getlistsort(){
        readUser = new ArrayList<UserModel>();
        DatabaseHelper helper = new DatabaseHelper(this);
        readUser = helper.readUsersortmain(sortfrist,sortsecound);

        if (readUser.size() != 0) {

            Gridview = (GridView) findViewById(R.id.gridView);
            SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(
                    getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION) {
                @Override
                public void onCreate(SQLiteDatabase db) {
             /*   db.execSQL(CREATE_DATABASE);
                Log.i(TAG, "CREATE DATABASE");*/
                }

                @Override
                public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
              /*  db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
                Log.i(TAG, "UPGRADE DATABASE");
                onCreate(db);*/
                }
            };

            ArrayList<String> listid = new ArrayList<String>();
            ArrayList<String>  listtitle = new ArrayList<String>();
            ArrayList<String>  listdes = new ArrayList<String>();
            ArrayList<String>  listlocation = new ArrayList<String>();
            ArrayList<String>  listsale = new ArrayList<String>();
            ArrayList<String>  listcost = new ArrayList<String>();
            ArrayList<String>  listdiscount = new ArrayList<String>();
            ArrayList<String>  listimage = new ArrayList<String>();



       /* List<UserModel> listUser = new ArrayList<>();*/
            String select = "SELECT * FROM " + TABLE_USER +" ORDER BY "+ sortfrist +" "+sortsecound;

            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(select,null);
            Log.d("select",String.valueOf(cursor));
            if(cursor.moveToFirst()){
                do{
                    UserModel model = new UserModel();
                    model.setId(cursor.getString(cursor.getColumnIndex("id")));
                    model.settitle(cursor.getString(cursor.getColumnIndex("title")));
                    model.setdes(cursor.getString(cursor.getColumnIndex("des")));
                    model.setlocation(cursor.getString(cursor.getColumnIndex("location")));
                    model.setsale(cursor.getString(cursor.getColumnIndex("sale")));
                    model.setcost(cursor.getString(cursor.getColumnIndex("cost")));
                    model.setdiscount(cursor.getString(cursor.getColumnIndex("discount")));
                    model.setimage(cursor.getString(cursor.getColumnIndex("image")));

                    listid.add(model.getId());
                    listtitle.add(model.gettitle());
                    listdes.add(model.getdes());
                    listlocation.add(model.getlocation());
                    listsale.add(model.getsale());
                    listcost.add(model.getcost());
                    listdiscount.add(model.getdiscount());
                    listimage.add(model.getimage());




             /*   listUser.add(model);*/

                }while (cursor.moveToNext());
//**

            }
            db.close();
            cursor.close();
            layoutmain_bottom.setVisibility(View.VISIBLE);
            final mainadapter adapter = new mainadapter(MainActivity.this, R.id.text,listid,listtitle
                    ,listdes,listlocation, listsale
                    ,listcost,listdiscount,listimage);
            adapter.notifyDataSetChanged();

            Gridview.setAdapter(adapter);

        }


    }

    private  void getlist(){
        readUser = new ArrayList<UserModel>();
        DatabaseHelper helper = new DatabaseHelper(this);
        readUser = helper.readUser();

        if (readUser.size() != 0) {
            txtshowmain.setVisibility(View.GONE);
            Gridview = (GridView) findViewById(R.id.gridView);
            SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(
                    getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION) {
                @Override
                public void onCreate(SQLiteDatabase db) {
             /*   db.execSQL(CREATE_DATABASE);
                Log.i(TAG, "CREATE DATABASE");*/
                }

                @Override
                public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
              /*  db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
                Log.i(TAG, "UPGRADE DATABASE");
                onCreate(db);*/
                }
            };

            ArrayList<String> listid = new ArrayList<String>();
            ArrayList<String>  listtitle = new ArrayList<String>();
            ArrayList<String>  listdes = new ArrayList<String>();
            ArrayList<String>  listlocation = new ArrayList<String>();
            ArrayList<String>  listsale = new ArrayList<String>();
            ArrayList<String>  listcost = new ArrayList<String>();
            ArrayList<String>  listdiscount = new ArrayList<String>();
            ArrayList<String>  listimage = new ArrayList<String>();

       /* List<UserModel> listUser = new ArrayList<>();*/
            String select = "SELECT * FROM " + TABLE_USER;

            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(select,null);
            Log.d("select",String.valueOf(cursor));
            if(cursor.moveToFirst()){
                do{

                    UserModel model = new UserModel();
                    model.setId(cursor.getString(cursor.getColumnIndex("id")));
                    model.settitle(cursor.getString(cursor.getColumnIndex("title")));
                    model.setdes(cursor.getString(cursor.getColumnIndex("des")));
                    model.setlocation(cursor.getString(cursor.getColumnIndex("location")));
                    model.setsale(cursor.getString(cursor.getColumnIndex("sale")));
                    model.setcost(cursor.getString(cursor.getColumnIndex("cost")));
                    model.setdiscount(cursor.getString(cursor.getColumnIndex("discount")));
                    model.setimage(cursor.getString(cursor.getColumnIndex("image")));

                    listid.add(model.getId());
                    listtitle.add(model.gettitle());
                    listdes.add(model.getdes());
                    listlocation.add(model.getlocation());
                    listsale.add(model.getsale());
                    listcost.add(model.getcost());
                    listdiscount.add(model.getdiscount());
                    listimage.add(model.getimage());

             /*   listUser.add(model);*/

                }while (cursor.moveToNext());
//**

            }
            db.close();
            cursor.close();

            layoutmain_bottom.setVisibility(View.VISIBLE);
            final mainadapter adapter = new mainadapter(MainActivity.this, R.id.text,listid,listtitle
                    ,listdes,listlocation, listsale
                    ,listcost,listdiscount,listimage);
            adapter.notifyDataSetChanged();

            Gridview.setAdapter(adapter);

        }


    }

    public static class mainitem {
        public static ArrayList<HashMap<String, String>> listitem = new ArrayList<HashMap<String, String>>();

    }

    private void bindview() {
        btnmainclear = (Button) findViewById(R.id.btnmainclear);
        btnmainsale = (Button) findViewById(R.id.btnmainsale);
        btnmainstock = (Button) findViewById(R.id.btnmainstock);
        spnmainsort = (Spinner) findViewById(R.id.spnmainsort);
        spnmainsortlh = (Spinner) findViewById(R.id.spnmainsortlh);
        btnmainsort = (Button) findViewById(R.id.btnmainsort);
        txtshowmain = (TextView) findViewById(R.id.txtshowmain);
        adView = (AdView)findViewById(R.id.adView);
        layoutmain_bottom = (LinearLayout) findViewById(R.id.layoutmain_bottom);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();


        if (id == R.id.action_settings) {

            return true;
        }

            switch (item.getItemId()) {


                case R.id.home:

                {

                    Intent newIntent = new Intent(MainActivity.this,MainActivity.class);
                    newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(newIntent);
                    return true;
                }

                case R.id.history: {

                    Intent fragment = new Intent(getApplicationContext(), fragmentall.class);
                    startActivity(fragment);
                    return true;


                }

                case R.id.item: {

                    Intent intentitem = new Intent(MainActivity.this,itemactivity.class);
                    startActivity(intentitem);
                    return true;

                }




                default:
                    return super.onOptionsItemSelected(item);


            }


    }


    private  void addlistspinfrist(){
        ArrayList<String> spinfristList =  new ArrayList<String>();
        spinfristList.add("ราคา");
        spinfristList.add("ต้นทุน");
        spinfristList.add("เวลาบันทึก");

        ArrayAdapter<String> listspindayAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_dropdown_item_1line, spinfristList);
        listspindayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spnmainsort.setAdapter(listspindayAdapter);

    }


    private  void addlistspinsecound(){
        ArrayList<String> spinsecoundList =  new ArrayList<String>();
        spinsecoundList.add("น้อย-มาก");
        spinsecoundList.add("มาก-น้อย");

        ArrayAdapter<String> listspindayAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_dropdown_item_1line, spinsecoundList);
        listspindayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spnmainsortlh.setAdapter(listspindayAdapter);

    }

    private  void eventbutton ()
    {

        btnmainsale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String showcheck = "";
                int sumcount = 0;
                for(int i=0;i<mainitem.listitem.size();i++)
                {
                    if(!String.valueOf(mainitem.listitem.get(i).get("title")).equals(null)){

                        showcheck += "ราคา "+mainitem.listitem.get(i).get("sale")+"("
                                +mainitem.listitem.get(i).get("title")+")"+" "+mainitem.listitem.get(i).get("count")
                                +" ชิ้น\n";
                        sumcount += Integer.parseInt(mainitem.listitem.get(i).get("sale"))*Integer.parseInt(mainitem.listitem.get(i).get("count"));

                    }
                    else{
                        Toast.makeText(getApplicationContext(),"กรุณากรอกข้อมูลรายการ ราคาขาย ต้นทุน",Toast.LENGTH_SHORT).show();
                    }
                }
                if(showcheck.equals(""))
                {
                    showcheck="ยังไม่มีข้อมูลให้บันทึก";
                    Toast.makeText(getApplicationContext(),showcheck,Toast.LENGTH_SHORT).show();

                }
                else
                {
                    showcheck += "\nราคารวมทั้งหมด : "+String .valueOf(sumcount)+" บาท";

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("รายละเอียดการขาย");
                    builder.setMessage(showcheck)
                            .setCancelable(false)
                            .setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    for(int i=0;i<mainitem.listitem.size();i++) {

                                        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date()); // 19 พ.ย. 2016 02:07:06
                                        String[] arraydatecut = currentDateTimeString.split(" ");
                                        String day = arraydatecut[0]; // 19
                                        String month = arraydatecut[1]; // พ.ย.
                                        String year = arraydatecut[2]; // 2016

                                        String [] arraytime = currentDateTimeString.split(year,0);
                                        String time = arraytime[1];

                                        String [] arraydate = currentDateTimeString.split(time,0);
                                        String date = arraydate[0];





                                        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
                                        helper.insertitem(mainitem.listitem.get(i).get("title"), mainitem.listitem.get(i).get("des")
                                                , mainitem.listitem.get(i).get("location"), mainitem.listitem.get(i).get("sale")
                                                , mainitem.listitem.get(i).get("cost")
                                                , mainitem.listitem.get(i).get("discount")
                                                , mainitem.listitem.get(i).get("count")
                                                , day,month,year,currentDateTimeString,date,time
                                                , mainitem.listitem.get(i).get("id"));
                                    }


                                    mainitem.listitem.clear();
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                }

                            });

                    AlertDialog alert = builder.create();
                    alert.show();

                }







            }
        });

        txtshowmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentitem = new Intent(MainActivity.this,itemactivity.class);
                startActivity(intentitem);
            }
        });

        btnmainclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainitem.listitem.clear();
                txtshowmain.setVisibility(View.VISIBLE);
                getlist();


            }
        });

        btnmainsort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spnmainsort.getSelectedItemPosition() == 0)
                {
                    sortfrist = "sale";
                }
                if(spnmainsort.getSelectedItemPosition() == 1)
                {
                    sortfrist = "cost";
                }
                if(spnmainsort.getSelectedItemPosition() == 2)
                {
                    sortfrist = "id";
                }

                if(spnmainsortlh.getSelectedItemPosition() == 0)
                {
                    sortsecound = "asc";
                }
                if(spnmainsortlh.getSelectedItemPosition() == 1)
                {
                    sortsecound = "desc";
                }

                getlistsort();
            }
        });

    }

    private void admob()
    {
        AdRequest.Builder adBuilder = new AdRequest.Builder();
        AdRequest adRequest = adBuilder.build();
        adView.loadAd(adRequest);
    }




}

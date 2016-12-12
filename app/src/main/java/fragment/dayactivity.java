package fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.nut.trade.DatabaseHelper;
import com.nut.trade.MainActivity;
import com.nut.trade.R;
import com.nut.trade.mainadapter;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.UserModel;
import adapter.dayadapter;

/**
 * Created by inwso on 13/11/2559.
 */

public class dayactivity extends Fragment  {
    private View rootView;
    private List<UserModel> readUserday;
    private ListView ListViewday;
    private TextView txtcountsale,txtcountmsale,txtprofit,txtdcost,txtshowday,txtdbest;
    private static final String DATABASE_NAME = "trade";
    private static final int DATABASE_VERSION = MainActivity.DATABASE_VERSION;
    private static final String TABLE_USER_HISTORY= "history";
    private dayadapter adapter;
    private List<UserModel> readUser;
    private Spinner spinday,spinmonth,spinyear;
    private String day,month,year,time,date;
    private Button btndsearch,btndsearchall;
    private int pagestart=0,pageend=0;
    private int checkgetcountdapter = 0 , sumcountbest =0 , countbest = 0;
    private String checkidbest ;
    private String counttitlebest = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.historyday, container, false);

        ListViewday = (ListView) rootView.findViewById(R.id.listviewday);
        txtcountsale = (TextView) rootView.findViewById(R.id.txtcountsale);
        txtcountmsale = (TextView) rootView.findViewById(R.id.txtcountmsale);
        txtprofit = (TextView)rootView.findViewById(R.id.txtprofit);
        txtdcost = (TextView) rootView.findViewById(R.id.txtdcost);
        spinday = (Spinner) rootView.findViewById(R.id.spinday);
        spinmonth = (Spinner) rootView.findViewById(R.id.spinmonth);
        spinyear = (Spinner) rootView.findViewById(R.id.spinyear);
        btndsearch = (Button) rootView.findViewById(R.id.btndsearch);
        btndsearchall = (Button) rootView.findViewById(R.id.btndsearchall);
        txtshowday = (TextView) rootView.findViewById(R.id.txtshowday);
        txtdbest = (TextView) rootView.findViewById(R.id.txtdbest);

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date()); // 19 พ.ย. 2016 02:07:06
        String[] arraydatecut = currentDateTimeString.split(" ");
        day = arraydatecut[0]; // 19
        month = arraydatecut[1]; // พ.ย.
        year = arraydatecut[2]; // 2016
        String [] arraytime = currentDateTimeString.split(year,0);
        time = arraytime[1]; //02:07:06

        String [] arraydate = currentDateTimeString.split(time,0);
        date = arraydate[0]; //19 พ.ย. 2016

        addlistspinday();
        addlistspinmonth();
        addlistspinyear();
        getlistday();
        getlistsumcount();
        btndsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtshowday.setVisibility(View.VISIBLE);

                clicksearch();
            }
        });

        btndsearchall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtshowday.setVisibility(View.VISIBLE);
                getlistdayall();
                getlistallsumcount();
            }
        });




        return  rootView;

    }

    @Override
    public void onResume() {
        super.onResume();

    }
    private  void clicksearch(){
        txtcountmsale.setText(String.valueOf("0"));
        txtcountsale.setText(String.valueOf("0"));
        txtdcost.setText("("+String.valueOf("0")+")");
        txtprofit.setText(String.valueOf("0"));


        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date()); // 19 พ.ย. 2016 02:07:06
        String[] arraydatecut = currentDateTimeString.split(" ");
        String daysearch = arraydatecut[0]; // 19
        String monthsearch = arraydatecut[1]; // พ.ย.
        String yearsearch = arraydatecut[2]; // 2016

        if(!String.valueOf(spinday.getSelectedItem()).equals("วันนี้"))
        {
            day = String .valueOf(spinday.getSelectedItem());
        }
        else
        {
            day = daysearch;
        }
        if(!String.valueOf(spinmonth.getSelectedItem()).equals("เดือนนี้"))
        {
            month = String .valueOf(spinmonth.getSelectedItem());
        }
        else
        {
            month=monthsearch;
        }
        if(!String.valueOf(spinyear.getSelectedItem()).equals("ปีนี้"))
        {
            year = String .valueOf(spinyear.getSelectedItem());
        }
        else
        {
            year = yearsearch;
        }

        getlistday();
        getlistsumcount();

    }

    private void  getlistsumcount(){

        int mcountsale = 0;
        int mcountcost = 0;
        int mcount = 0;
        int mcountsum = 0;
        counttitlebest = null;
        countbest  = 0;
        checkidbest = null;

        SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(
                getContext(), DATABASE_NAME, null, DATABASE_VERSION) {
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
        ArrayList<String>  listcount = new ArrayList<String>();
        ArrayList<String>  listtime = new ArrayList<String>();
        ArrayList<String>  listdate = new ArrayList<String>();



        String select = "SELECT *,SUM(countitem) as sumcount FROM " + TABLE_USER_HISTORY +
                " WHERE day='"+day+"'"+"and month='"+month+"'"+
                "and year='"+year+"'"+
                "GROUP by headid ORDER BY ID DESC";

        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(select,null);

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
                model.setcount(cursor.getString(cursor.getColumnIndex("countitem")));
                model.settime(cursor.getString(cursor.getColumnIndex("time")));
                model.setdate(cursor.getString(cursor.getColumnIndex("date")));
                model.setsumcount(cursor.getString(cursor.getColumnIndex("sumcount")));

                //   model.setday(cursor.getString(cursor.getColumnIndex("day")));

                //  Log.d("datyyyyy",String .valueOf(model.getday()));

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


                    if(Integer.parseInt(model.getsumcount()) > countbest)
                    {
                        counttitlebest = model.gettitle();
                        countbest = Integer.parseInt(model.getsumcount());
                        checkidbest = model.getId();
                    }

                   /* if(checkidbest.equals(model.getId()))
                    {
                        sumcountbest += Integer.parseInt(model.getcount());
                    }*/





            }while (cursor.moveToNext());

        }
        db.close();
        cursor.close();
        txtdbest.setText("("+String.valueOf(countbest)+")"+String.valueOf(counttitlebest));

        if(txtdbest.getText().toString().equals(null) || txtdbest.getText().toString() == null)
        {
            txtdbest.setText("("+String.valueOf(countbest)+")"+"ไม่มีข้อมูล");
        }

    }

    private void  getlistallsumcount(){

        int mcountsale = 0;
        int mcountcost = 0;
        int mcount = 0;
        int mcountsum = 0;
        counttitlebest = "ไม่มีข้อมูล";
        countbest  = 0;
        checkidbest = null;

        SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(
                getContext(), DATABASE_NAME, null, DATABASE_VERSION) {
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
        ArrayList<String>  listcount = new ArrayList<String>();
        ArrayList<String>  listtime = new ArrayList<String>();
        ArrayList<String>  listdate = new ArrayList<String>();



        String select = "SELECT *,SUM(countitem) as sumcount FROM " + TABLE_USER_HISTORY +
                " GROUP by headid ORDER BY ID DESC";

        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(select,null);

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
                model.setcount(cursor.getString(cursor.getColumnIndex("countitem")));
                model.settime(cursor.getString(cursor.getColumnIndex("time")));
                model.setdate(cursor.getString(cursor.getColumnIndex("date")));
                model.setsumcount(cursor.getString(cursor.getColumnIndex("sumcount")));

                //   model.setday(cursor.getString(cursor.getColumnIndex("day")));

                //  Log.d("datyyyyy",String .valueOf(model.getday()));

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


                if(Integer.parseInt(model.getsumcount()) > countbest)
                {
                    counttitlebest = model.gettitle();
                    countbest = Integer.parseInt(model.getsumcount());
                    checkidbest = model.getId();
                }

                   /* if(checkidbest.equals(model.getId()))
                    {
                        sumcountbest += Integer.parseInt(model.getcount());
                    }*/



            }while (cursor.moveToNext());

        }
        db.close();
        cursor.close();
        txtdbest.setText("("+String.valueOf(countbest)+")"+String.valueOf(counttitlebest));





    }



    private  void getlistday(){
        readUserday = new ArrayList<UserModel>();
        DatabaseHelper helper = new DatabaseHelper(getActivity());
        readUserday = helper.readUserday(day,month,year);

        if (readUserday.size() != 0) {
            txtshowday.setVisibility(View.GONE);
            SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(
                   getContext(), DATABASE_NAME, null, DATABASE_VERSION) {
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
            ArrayList<String>  listcount = new ArrayList<String>();
            ArrayList<String>  listtime = new ArrayList<String>();
            ArrayList<String>  listdate = new ArrayList<String>();



            String select = "SELECT * FROM " + TABLE_USER_HISTORY +
                    " WHERE day='"+day+"'"+"and month='"+month+"'"+
                    "and year='"+year+"'"+
                    " ORDER BY ID DESC";

            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(select,null);
            Log.d("select",String.valueOf(cursor));
            int mcountsale = 0;
            int mcountcost = 0;
            int mcount = 0;
            int mcountsum = 0;
            counttitlebest = null;
            countbest  = 0;
            checkidbest = null;

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
                    model.setcount(cursor.getString(cursor.getColumnIndex("countitem")));
                    model.settime(cursor.getString(cursor.getColumnIndex("time")));
                    model.setdate(cursor.getString(cursor.getColumnIndex("date")));
                  //  model.setsumcount(cursor.getString(cursor.getColumnIndex("sumcount")));

                 //   model.setday(cursor.getString(cursor.getColumnIndex("day")));

                  //  Log.d("datyyyyy",String .valueOf(model.getday()));

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




                  /*  if(Integer.parseInt(model.getsumcount()) > countbest)
                    {
                        counttitlebest = model.gettitle();
                        countbest = Integer.parseInt(model.getsumcount());
                        checkidbest = model.getId();
                    }*/

                   /* if(checkidbest.equals(model.getId()))
                    {
                        sumcountbest += Integer.parseInt(model.getcount());
                    }*/


                    mcountsale  +=  Integer.parseInt(model.getsale())*Integer.parseInt(model.getcount());
                    mcountcost  +=  Integer.parseInt(model.getcost())*Integer.parseInt(model.getcount());
                    mcount += Integer.parseInt(model.getcount());





                }while (cursor.moveToNext());

            }
            db.close();
            cursor.close();

            adapter = new dayadapter(getActivity(), R.id.text,listid,listtitle
                    ,listdes,listlocation, listsale
                    ,listcost,listdiscount,listcount,listtime
                    ,listdate
            );
            adapter.notifyDataSetChanged();
            ListViewday.setAdapter(adapter);
           // registerForContextMenu(ListViewday);

            mcountsum = mcountsale - mcountcost;
            txtcountmsale.setText(String.valueOf(mcountsale));
            txtcountsale.setText(String.valueOf(mcount));
            txtdcost.setText("("+String.valueOf(mcountcost)+")");
            txtprofit.setText(String.valueOf(mcountsum));


            /*ListViewday.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                            && (ListViewday.getLastVisiblePosition() - ListViewday.getHeaderViewsCount() -
                            ListViewday.getFooterViewsCount()) >= (adapter.getCount() - 1)) {

                        pagestart = pageend;
                        pageend = pageend + 10;
                        checkgetcountdapter = adapter.getCount() - 1;
                        getlistdayall();

                    }

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });
*/

        }



    }


    private  void getlistdayall(){
        readUserday = new ArrayList<UserModel>();
        DatabaseHelper helper = new DatabaseHelper(getActivity());
        readUserday = helper.readUserdayall();

        if (readUserday.size() != 0) {
            txtshowday.setVisibility(View.GONE);
            SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(
                    getContext(), DATABASE_NAME, null, DATABASE_VERSION) {
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
            ArrayList<String>  listcount = new ArrayList<String>();
            ArrayList<String>  listtime = new ArrayList<String>();
            ArrayList<String>  listdate = new ArrayList<String>();



            String select = "SELECT * FROM " + TABLE_USER_HISTORY +
                    "  ORDER BY ID DESC ";/* LIMIT 10 OFFSET "+pagestart;*/

            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(select,null);
            Log.d("select",String.valueOf(cursor));
            int mcountsale = 0;
            int mcountcost = 0;
            int mcount = 0;
            int mcountsum = 0;
            counttitlebest = null;
            countbest  = 0;
            checkidbest = null;
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
                    model.setcount(cursor.getString(cursor.getColumnIndex("countitem")));
                    model.settime(cursor.getString(cursor.getColumnIndex("time")));
                    model.setdate(cursor.getString(cursor.getColumnIndex("date")));
                //    model.setsumcount(cursor.getString(cursor.getColumnIndex("sumcount")));


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

                /*    Log.d("sumcount",String .valueOf(model.getsumcount()));

                    if(Integer.parseInt(model.getsumcount()) > countbest)
                    {
                        counttitlebest = model.gettitle();
                        countbest = Integer.parseInt(model.getsumcount());
                        checkidbest = model.getId();
                    }*/


                    mcountsale  +=  Integer.parseInt(model.getsale())*Integer.parseInt(model.getcount());
                    mcountcost  +=  Integer.parseInt(model.getcost())*Integer.parseInt(model.getcount());
                    mcount += Integer.parseInt(model.getcount());





                }while (cursor.moveToNext());

            }
            db.close();
            cursor.close();

            adapter = new dayadapter(getActivity(), R.id.text,listid,listtitle
                    ,listdes,listlocation, listsale
                    ,listcost,listdiscount,listcount,listtime
                    ,listdate
            );
            adapter.notifyDataSetChanged();
            ListViewday.setAdapter(adapter);
            ListViewday.deferNotifyDataSetChanged();
            ListViewday.setSelection(checkgetcountdapter);
            // registerForContextMenu(ListViewday);

           /* ListViewday.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                            && (ListViewday.getLastVisiblePosition() - ListViewday.getHeaderViewsCount() -
                            ListViewday.getFooterViewsCount()) >= (adapter.getCount() - 1)) {

                        pagestart = pageend;
                        pageend = pageend + 10;
                        checkgetcountdapter = adapter.getCount() - 1;
                        getlistdayall();

                    }

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });
*/
            mcountsum = mcountsale - mcountcost;
            txtcountmsale.setText(String.valueOf(mcountsale));
            txtcountsale.setText(String.valueOf(mcount));
            txtdcost.setText("("+String.valueOf(mcountcost)+")");
            txtprofit.setText(String.valueOf(mcountsum));
        //    txtdbest.setText("("+String.valueOf(countbest)+")"+String.valueOf(counttitlebest));
        }



    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo  info = (AdapterView.AdapterContextMenuInfo) menuInfo;

     //   Log.i("INFO",info.position+"");
     //   Log.d("qqqqq",String .valueOf(info.position));
        menu.setHeaderTitle("ลบรายการ");
     /*   menu.add("update");*/
        menu.add("ลบข้อมูล");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo  info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        Log.i("INFO",position+"");
        if(item.getTitle().equals("update")){
          /*  String id = readUser.get(position).getId();
            Log.i("UPDATE ID",id +"");
            Intent i = new Intent(MainActivity.this, InsertActivity.class);
            i.putExtra("position",id);
            startActivity(i);*/
        }else if (item.getTitle().equals("ลบข้อมูล")){
            String id = readUserday.get(position).getId();
            Log.i("DELETE ID",id);
            DatabaseHelper helper = new DatabaseHelper(getContext());
            helper.deleteday(id);
         /*   readUserday = helper.readUserday();
            Log.i("DELETE", readUser.size() + "");*/
            adapter.clear();
            getlistday();
            adapter.notifyDataSetChanged();
        }

        return true;

    }

    private  void addlistspinday(){
        ArrayList<String> spindayList =  new ArrayList<String>();
        spindayList.add("วันนี้");
        for(int i=1;i<=31;i++)
        {
            spindayList.add(String.valueOf(i));
        }
        ArrayAdapter<String> listspindayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, spindayList);
        listspindayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinday.setAdapter(listspindayAdapter);


    }

    private  void addlistspinmonth(){
        ArrayList<String> spinmonthList =  new ArrayList<String>();
        spinmonthList.add("เดือนนี้");
        spinmonthList.add("ม.ค.");
        spinmonthList.add("ก.พ.");
        spinmonthList.add("มี.ค.");
        spinmonthList.add("เม.ย.");
        spinmonthList.add("พ.ค.");
        spinmonthList.add("มิ.ย.");
        spinmonthList.add("ก.ค.");
        spinmonthList.add("ส.ค.");
        spinmonthList.add("ก.ย.");
        spinmonthList.add("ต.ค.");
        spinmonthList.add("พ.ย.");
        spinmonthList.add("ธ.ค.");
        ArrayAdapter<String> listspindayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, spinmonthList);
        listspindayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinmonth.setAdapter(listspindayAdapter);


    }


    private  void addlistspinyear(){
        ArrayList<String> spinyearList =  new ArrayList<String>();
        spinyearList.add("ปีนี้");
        spinyearList.add("2016");
        spinyearList.add("2017");
        spinyearList.add("2018");
        spinyearList.add("2019");
        spinyearList.add("2020");
        spinyearList.add("2021");
        spinyearList.add("2022");
        spinyearList.add("2023");
        spinyearList.add("2024");
        spinyearList.add("2025");
        spinyearList.add("2026");
        spinyearList.add("2027");
        spinyearList.add("2028");
        spinyearList.add("2029");
        spinyearList.add("2030");
        ArrayAdapter<String> listspindayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, spinyearList);
        listspindayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinyear.setAdapter(listspindayAdapter);


    }


}

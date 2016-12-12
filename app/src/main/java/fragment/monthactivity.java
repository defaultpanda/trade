package fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.nut.trade.DatabaseHelper;
import com.nut.trade.MainActivity;
import com.nut.trade.R;
import com.nut.trade.itemactivity;
import com.nut.trade.listshowActivity;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.UserModel;
import adapter.dayadapter;
import adapter.monthadapter;

/**
 * Created by inwso on 13/11/2559.
 */

public class monthactivity extends Fragment {
    private View rootView;
    private TextView txtmsumcount,txtmsumsale,txtmsumcost,txtmsumprofit,txtshowmonth,txtmcountbest;
    private Spinner spinmmonth,spinmyear;
    private Button btnmsearch;
    private ListView listmonth;
    private String day,month,year,time,date;
    private static final String DATABASE_NAME = "trade";
    private static final int DATABASE_VERSION = MainActivity.DATABASE_VERSION;
    private static final String TABLE_USER_HISTORY= "history";
    private List<UserModel> readUsermonth;
    private monthadapter adapter;
    private int countweek1 =0 ,countweek2 =0,countweek3 =0,countweek4 =0;
    private int saleweek1 =0 , saleweek2 =0, saleweek3 =0, saleweek4 =0;
    private int costweek1 =0 , costweek2 =0, costweek3 =0, costweek4 =0;
    private int profitweek1 = 0, profitweek2 =0 , profitweek3 =0, profitweek4 =0;
    private String bestweek1,bestweek2,bestweek3,bestweek4;
    private int checkcount1 = 0,checkcount2 = 0,checkcount3 = 0,checkcount4 = 0;
    private String countbest1 ,countbest2,countbest3 ,countbest4;
    private int checkgetcountdapter = 0 , sumcountbest =0 , countbest = 0;
    private String checkidbest ;
    private String counttitlebest = null;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.historymonth, container, false);
        txtmsumcount = (TextView) rootView.findViewById(R.id.txtmsumcount);
        txtmsumsale   = (TextView) rootView.findViewById(R.id.txtmsumsale);
        txtmsumcost = (TextView) rootView.findViewById(R.id.txtmsumcost);
        txtmsumprofit = (TextView) rootView.findViewById(R.id.txtmsumprofit);
        spinmmonth = (Spinner) rootView.findViewById(R.id.spinmmonth);
        spinmyear = (Spinner) rootView.findViewById(R.id.spinmyear);
        btnmsearch  = (Button) rootView.findViewById(R.id.btnmsearch);
        listmonth = (ListView) rootView.findViewById(R.id.listmonth);
        txtshowmonth = (TextView) rootView.findViewById(R.id.txtshowmonth);
        txtmcountbest = (TextView) rootView.findViewById(R.id.txtmcountbest);

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date()); // 19 พ.ย. 2016 02:07:06
        String[] arraydatecut = currentDateTimeString.split(" ");
        day = arraydatecut[0]; // 19
        month = arraydatecut[1]; // พ.ย.
        year = arraydatecut[2]; // 2016
        String [] arraytime = currentDateTimeString.split(year,0);
        time = arraytime[1]; //02:07:06
        String [] arraydate = currentDateTimeString.split(time,0);
        date = arraydate[0]; //19 พ.ย. 2016
        addlistspinmonth();
        addlistspinyear();
        getlistmonth();
        getlistsumcount();
        btnmsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtshowmonth.setVisibility(View.VISIBLE);
                adapter.clear();
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date()); // 19 พ.ย. 2016 02:07:06
                String[] arraydatecut = currentDateTimeString.split(" ");
                String   monthsearch = arraydatecut[1]; // พ.ย.
                String  yearsearch = arraydatecut[2]; // 2016

                if(!String.valueOf(spinmmonth.getSelectedItem()).equals("เดือนนี้"))
                {
                    month = String .valueOf(spinmmonth.getSelectedItem());
                }
                else
                {
                    month = monthsearch;
                }

                if(!String.valueOf(spinmyear.getSelectedItem()).equals("ปีนี้"))
                {
                    year = String .valueOf(spinmyear.getSelectedItem());
                }
                else
                {
                    year = yearsearch;
                }
                countweek1 =0 ;countweek2 =0;countweek3 =0;countweek4 =0;
                saleweek1 =0 ; saleweek2 =0; saleweek3 =0; saleweek4 =0;
                costweek1 =0 ; costweek2 =0; costweek3 =0; costweek4 =0;
                profitweek1 = 0; profitweek2 =0 ; profitweek3 =0; profitweek4 =0;
                bestweek1=null;bestweek2=null;bestweek3=null;bestweek4 =null;
                checkcount1 = 0;checkcount2 = 0;checkcount3 = 0;checkcount4 = 0;
                countbest1 =null ;countbest2=null; countbest3 =null; countbest4= null;

                txtmsumcount.setText("0"); txtmsumsale.setText("0");
                txtmsumcost.setText("(0)"); txtmsumprofit.setText("0");
                getlistmonth();
                getlistsumcount();
            }
        });


        return  rootView;
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
        spinmmonth.setAdapter(listspindayAdapter);


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
        spinmyear.setAdapter(listspindayAdapter);


    }


    private  void getlistmonth(){
       /* readUsermonth = new ArrayList<UserModel>();
        DatabaseHelper helper = new DatabaseHelper(getActivity());
        readUsermonth = helper.readUsermonth(month,year);*/

       /* if (readUsermonth.size() != 0) {*/

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
            txtshowmonth.setVisibility(View.GONE);
         /*   ArrayList<String> listid = new ArrayList<String>();
            ArrayList<String>  listtitle = new ArrayList<String>();
            ArrayList<String>  listdes = new ArrayList<String>();
            ArrayList<String>  listlocation = new ArrayList<String>();
            ArrayList<String>  listsale = new ArrayList<String>();
            ArrayList<String>  listcost = new ArrayList<String>();
            ArrayList<String>  listdiscount = new ArrayList<String>();
            ArrayList<String>  listcount = new ArrayList<String>();
            ArrayList<String>  listtime = new ArrayList<String>();
            ArrayList<String>  listdate = new ArrayList<String>();*/

             ArrayList<String> listprofit = new ArrayList<String>();
            ArrayList<String>  listsale = new ArrayList<String>();
            ArrayList<String>  listcost = new ArrayList<String>();
            ArrayList<String>  listcount = new ArrayList<String>();
            ArrayList<String>  listcountbest = new ArrayList<String>();
            ArrayList<String>  listbest = new ArrayList<String>();
            ArrayList<String>  listcounweek = new ArrayList<String>();

            String select = "SELECT * FROM " + TABLE_USER_HISTORY +" WHERE month='"+month+"'and year='"+year+"' and day>=1 and   day <=7 UNION "
                    +"SELECT * FROM " + TABLE_USER_HISTORY +" WHERE month='"+month+"'and year='"+year+"'  and day>=8 and   day <=14 UNION "
                    +"SELECT * FROM " + TABLE_USER_HISTORY +" WHERE month='"+month+"'and year='"+year+"'  and day>=15 and   day <=21 UNION "
                    +"SELECT * FROM " + TABLE_USER_HISTORY +" WHERE month='"+month+"'and year='"+year+"'  and day>=22 and   day <=31  "
                    ;

            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(select,null);
            Log.d("select",String.valueOf(cursor));
            int mcountsale = 0;
            int mcountcost = 0;
            int mcount = 0;
            int mcountsum = 0;
            int i =1;
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
                    model.setday(cursor.getString(cursor.getColumnIndex("day")));
                    model.setmonth(cursor.getString(cursor.getColumnIndex("month")));
                    model.setyear(cursor.getString(cursor.getColumnIndex("year")));

                   /* listid.add(model.getId());
                    listtitle.add(model.gettitle());
                    listdes.add(model.getdes());
                    listlocation.add(model.getlocation());
                    listsale.add(model.getsale());
                    listcost.add(model.getcost());
                    listdiscount.add(model.getdiscount());
                    listcount.add(model.getcount());
                    listtime.add(model.gettime());
                    listdate.add(model.getdate());*/
                    int sumsale = Integer.parseInt(model.getsale())*Integer.parseInt(model.getcount());
                    int sumcost =  Integer.parseInt(model.getcost())*Integer.parseInt(model.getcount());
                    int sumprofit = sumsale - sumcost; //กำไรสุทธิ


                  //  Log.d("weekday",model.getday());

                    if(Integer.parseInt(model.getday())>=1 && Integer.parseInt(model.getday())<=7)
                    {

                        countweek1 += Integer.parseInt(model.getcount());
                        saleweek1  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        costweek1  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        int sale = Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        int cost = Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        profitweek1 += sale-cost;
                     //   Log.d("week1",String .valueOf(profitweek1));
                        if(Integer.parseInt(model.getcount()) > checkcount1)
                        {
                            bestweek1 = model.gettitle();
                            countbest1 = model.getcount();
                            checkcount1 = Integer.parseInt(model.getcount());

                        }

                    }
                    if(Integer.parseInt(model.getday())>=8 && Integer.parseInt(model.getday())<=14)
                    {
                        countweek2 += Integer.parseInt(model.getcount());
                        saleweek2  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        costweek2  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        int sale = Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        int cost = Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        profitweek2 += sale-cost;
                    //    Log.d("week2",String .valueOf(profitweek2));
                        if(Integer.parseInt(model.getcount()) > checkcount2)
                        {
                            bestweek2 = model.gettitle();
                            countbest2 = model.getcount();
                            checkcount2 = Integer.parseInt(model.getcount());

                        }
                    }
                    if(Integer.parseInt(model.getday())>=15 && Integer.parseInt(model.getday())<=21)
                    {
                        countweek3 += Integer.parseInt(model.getcount());
                        saleweek3  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        costweek3  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        int sale = Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        int cost = Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        profitweek3 += sale-cost;

                   //    Log.d("week3",String .valueOf(profitweek3));
                   //     Log.d("saleweek3",String.valueOf(saleweek3));
                   //     Log.d("costweek3",String.valueOf(costweek3));
                        if(Integer.parseInt(model.getcount()) > checkcount3)
                        {
                            bestweek3 = model.gettitle();
                            countbest3 = model.getcount();
                            checkcount3 = Integer.parseInt(model.getcount());

                        }
                    }
                    if(Integer.parseInt(model.getday())>=22 && Integer.parseInt(model.getday())<=31)
                    {
                        countweek4 += Integer.parseInt(model.getcount());
                        saleweek4  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        costweek4  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        int sale = Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        int cost = Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        profitweek4 += sale-cost;
                 //       Log.d("week4",String .valueOf(profitweek4)+" "+model.getcount());
                        if(Integer.parseInt(model.getcount()) > checkcount4)
                        {
                            bestweek4 = model.gettitle();
                            countbest4 = model.getcount();
                            checkcount4 = Integer.parseInt(model.getcount());

                        }
                    }


                    listcounweek.add(String.valueOf(i));
                    mcountsale  +=  Integer.parseInt(model.getsale())*Integer.parseInt(model.getcount());
                    mcountcost  +=  Integer.parseInt(model.getcost())*Integer.parseInt(model.getcount());
                    mcount += Integer.parseInt(model.getcount());


                    i++;



                }while (cursor.moveToNext());

            }
            db.close();
            cursor.close();



            listprofit.add(String.valueOf(profitweek1));
            listsale.add(String.valueOf(saleweek1));
            listcost.add(String.valueOf(costweek1));
            listcount.add(String.valueOf(countweek1));
            listcountbest.add(String.valueOf(countbest1));
            listbest.add(String.valueOf(bestweek1));
            listcounweek.add(String.valueOf(1));

            listprofit.add(String.valueOf(profitweek2));
            listsale.add(String.valueOf(saleweek2));
            listcost.add(String.valueOf(costweek2));
            listcount.add(String.valueOf(countweek2));
            listcountbest.add(String.valueOf(countbest2));
            listbest.add(String.valueOf(bestweek2));
              listcounweek.add(String.valueOf(2));

            listprofit.add(String.valueOf(profitweek3));
            listsale.add(String.valueOf(saleweek3));
            listcost.add(String.valueOf(costweek3));
            listcount.add(String.valueOf(countweek3));
            listcountbest.add(String.valueOf(countbest3));
            listbest.add(String.valueOf(bestweek3));
            listcounweek.add(String.valueOf(3));

            listprofit.add(String.valueOf(profitweek4));
            listsale.add(String.valueOf(saleweek4));
            listcost.add(String.valueOf(costweek4));
            listcount.add(String.valueOf(countweek4));
            listcountbest.add(String.valueOf(countbest4));
            listbest.add(String.valueOf(bestweek4));
             listcounweek.add(String.valueOf(4));


            adapter = new monthadapter(getActivity(), R.id.text,listprofit,listsale,listcost,listcount,listbest,listcountbest,listcounweek,day,month,year
            );
            adapter.notifyDataSetChanged();
            listmonth.setAdapter(adapter);
            listmonth.deferNotifyDataSetChanged();
            // registerForContextMenu(ListViewday);

            mcountsum = mcountsale - mcountcost;
            txtmsumcount.setText(String.valueOf(mcountsale));
            txtmsumsale.setText(String.valueOf(mcount));
            txtmsumcost.setText("("+String.valueOf(mcountcost)+")");
            txtmsumprofit.setText(String.valueOf(mcountsum));


       // }

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
                " WHERE  month='"+month+"'"+
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
        txtmcountbest.setText("("+String.valueOf(countbest)+")"+String.valueOf(counttitlebest));

    }





}

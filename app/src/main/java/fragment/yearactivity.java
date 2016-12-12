package fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.UserModel;
import adapter.yearadapter;

/**
 * Created by inwso on 13/11/2559.
 */

public class yearactivity  extends Fragment {

    private View rootView;
    private TextView txtmsumcount,txtmsumsale,txtmsumcost,txtmsumprofit,txtshowyear,txtycountbest;
    private ListView listyear;
    private Spinner spinhyyear;
    private Button btnysearch;
    private String day,month,year,time,date;
    private List<UserModel> readUseryear;
    private static final String DATABASE_NAME = "trade";
    private static final int DATABASE_VERSION = MainActivity.DATABASE_VERSION;;
    private static final String TABLE_USER_HISTORY= "history";
    private yearadapter adapter;
    private int countyear1=0,countyear2=0,countyear3=0,countyear4=0,countyear5=0
            ,countyear6=0,countyear7=0,countyear8=0,countyear9=0,countyear10=0,countyear11=0,countyear12=0;
    private int saleyear1=0,saleyear2=0,saleyear3=0,saleyear4=0,saleyear5=0,saleyear6=0,
            saleyear7=0,saleyear8=0,saleyear9=0,saleyear10=0,saleyear11=0,saleyear12=0;
    private int costyear1=0,costyear2=0,costyear3=0,costyear4=0,costyear5=0,costyear6=0,
            costyear7=0,costyear8=0,costyear9=0,costyear10=0,costyear11=0,costyear12=0;
    private int profityear1=0,profityear2=0,profityear3=0,profityear4=0,profityear5=0,
            profityear6=0,profityear7=0,profityear8=0,profityear9=0,profityear10=0,profityear11=0,profityear12=0;
    private int checkcount1=0,checkcount2=0,checkcount3=0,checkcount4=0,checkcount5=0,checkcount6=0,
            checkcount7=0,checkcount8=0,checkcount9=0,checkcount10=0,checkcount11=0,checkcount12=0;
    private String bestyear1,bestyear2,bestyear3,bestyear4,bestyear5,bestyear6
            ,bestyear7,bestyear8,bestyear9,bestyear10,bestyear11,bestyear12;
    private String countbest1,countbest2,countbest3,countbest4,countbest5,countbest6,
            countbest7,countbest8,countbest9,countbest10,countbest11,countbest12;
    private int checkgetcountdapter = 0 , sumcountbest =0 , countbest = 0;
    private String checkidbest ;
    private String counttitlebest = null;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.historyyear, container, false);
        txtmsumcount = (TextView) rootView.findViewById(R.id.txtyhcount);
        txtmsumsale   = (TextView) rootView.findViewById(R.id.txtyhsale);
        txtmsumcost = (TextView) rootView.findViewById(R.id.txtyhcost);
        txtmsumprofit = (TextView) rootView.findViewById(R.id.txtyhprofit);
        spinhyyear = (Spinner) rootView.findViewById(R.id.spinhyyear);
        btnysearch  = (Button) rootView.findViewById(R.id.btnysearch);
        listyear = (ListView) rootView.findViewById(R.id.listyear);

        txtshowyear =(TextView) rootView.findViewById(R.id.txtshowyear);
        txtycountbest = (TextView) rootView.findViewById(R.id.txtycountbest);

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date()); // 19 พ.ย. 2016 02:07:06
        String[] arraydatecut = currentDateTimeString.split(" ");
        day = arraydatecut[0]; // 19
        month = arraydatecut[1]; // พ.ย.
        year = arraydatecut[2]; // 2016
        String [] arraytime = currentDateTimeString.split(year,0);
        time = arraytime[1]; //02:07:06
        String [] arraydate = currentDateTimeString.split(time,0);
        date = arraydate[0]; //19 พ.ย. 2016
        addlistspinyear();
        getlistyear();
        getlistsumcount();

        btnysearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  adapter.clear();
                txtshowyear.setVisibility(View.VISIBLE);
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date()); // 19 พ.ย. 2016 02:07:06
                String[] arraydatecut = currentDateTimeString.split(" ");
                String  yearsearch = arraydatecut[2]; // 2016
                if(!String.valueOf(spinhyyear.getSelectedItem()).equals("ปีนี้"))
                {
                    year = String .valueOf(spinhyyear.getSelectedItem());
                }
                else
                {
                    year = yearsearch;
                }
                clearvalue();
                txtmsumcount.setText("0"); txtmsumsale.setText("0");
                txtmsumcost.setText("(0)"); txtmsumprofit.setText("0");
                getlistyear();
                getlistsumcount();
            }
        });



        return  rootView;
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
        spinhyyear.setAdapter(listspindayAdapter);


    }

    private  void getlistyear(){
        readUseryear = new ArrayList<UserModel>();
        DatabaseHelper helper = new DatabaseHelper(getContext());
        readUseryear = helper.readUseryear(year);
        clearvalue();



        if (readUseryear.size() != 0) {
            txtshowyear.setVisibility(View.GONE);
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
        //    txtshowmonth.setVisibility(View.GONE);
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

            String select = "SELECT * FROM " + TABLE_USER_HISTORY +" WHERE year='"+year+"'";

            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(select,null);
            Log.d("select",String.valueOf(cursor));
            int mcountsale = 0;
            int mcountcost = 0;
            int mcount = 0;
            int mcountsum = 0;

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

                    int sumsale = Integer.parseInt(model.getsale())*Integer.parseInt(model.getcount());
                    int sumcost =  Integer.parseInt(model.getcost())*Integer.parseInt(model.getcount());
                    int sumprofit = sumsale - sumcost; //กำไรสุทธิ


                    //  Log.d("weekday",model.getday());

                    if(model.getmonth().equals("ม.ค."))
                    {

                        countyear1 += Integer.parseInt(model.getcount());
                        saleyear1  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        costyear1  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        int sale = Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        int cost = Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        profityear1 += sale-cost;
                        //   Log.d("week1",String .valueOf(profitweek1));
                        if(Integer.parseInt(model.getcount()) > checkcount1)
                        {
                            bestyear1 = model.gettitle();
                            countbest1 = model.getcount();
                            checkcount1 = Integer.parseInt(model.getcount());

                        }

                    }
                    if(model.getmonth().equals("ก.พ."))
                    {
                        countyear2 += Integer.parseInt(model.getcount());
                        saleyear2  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        costyear2  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        int sale = Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        int cost = Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        profityear2 += sale-cost;
                        //    Log.d("week2",String .valueOf(profitweek2));
                        if(Integer.parseInt(model.getcount()) > checkcount2)
                        {
                            bestyear2 = model.gettitle();
                            countbest2 = model.getcount();
                            checkcount2 = Integer.parseInt(model.getcount());

                        }
                    }
                    if(model.getmonth().equals("มี.ค."))
                    {
                        countyear3+= Integer.parseInt(model.getcount());
                        saleyear3 += Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        costyear3 += Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        int sale = Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        int cost = Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        profityear3 += sale-cost;

                        //    Log.d("week3",String .valueOf(profitweek3));
                        //     Log.d("saleweek3",String.valueOf(saleweek3));
                        //     Log.d("costweek3",String.valueOf(costweek3));
                        if(Integer.parseInt(model.getcount()) > checkcount3)
                        {
                            bestyear3 = model.gettitle();
                            countbest3 = model.getcount();
                            checkcount3 = Integer.parseInt(model.getcount());

                        }
                    }
                    if(model.getmonth().equals("เม.ย."))
                    {
                        countyear4 += Integer.parseInt(model.getcount());
                        saleyear4  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        costyear4  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        int sale = Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        int cost = Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        profityear4 += sale-cost;
                        //       Log.d("week4",String .valueOf(profitweek4)+" "+model.getcount());
                        if(Integer.parseInt(model.getcount()) > checkcount4)
                        {
                            bestyear4 = model.gettitle();
                            countbest4 = model.getcount();
                            checkcount4 = Integer.parseInt(model.getcount());

                        }
                    }
                    if(model.getmonth().equals("พ.ค."))
                    {
                        countyear5 += Integer.parseInt(model.getcount());
                        saleyear5  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        costyear5  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        int sale = Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        int cost = Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        profityear5 += sale-cost;
                        //       Log.d("week4",String .valueOf(profitweek4)+" "+model.getcount());
                        if(Integer.parseInt(model.getcount()) > checkcount5)
                        {
                            bestyear5 = model.gettitle();
                            countbest5 = model.getcount();
                            checkcount5 = Integer.parseInt(model.getcount());

                        }
                    }
                    if(model.getmonth().equals("มิ.ย."))
                    {
                        countyear6 += Integer.parseInt(model.getcount());
                        saleyear6  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        costyear6  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        int sale = Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        int cost = Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        profityear6 += sale-cost;
                        //       Log.d("week4",String .valueOf(profitweek4)+" "+model.getcount());
                        if(Integer.parseInt(model.getcount()) > checkcount6)
                        {
                            bestyear6 = model.gettitle();
                            countbest6 = model.getcount();
                            checkcount6 = Integer.parseInt(model.getcount());

                        }
                    }
                    if(model.getmonth().equals("ก.ค."))
                    {
                        countyear7 += Integer.parseInt(model.getcount());
                        saleyear7  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        costyear7  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        int sale = Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        int cost = Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        profityear7 += sale-cost;
                        //       Log.d("week4",String .valueOf(profitweek4)+" "+model.getcount());
                        if(Integer.parseInt(model.getcount()) > checkcount7)
                        {
                            bestyear7 = model.gettitle();
                            countbest7 = model.getcount();
                            checkcount7 = Integer.parseInt(model.getcount());

                        }
                    }
                    if(model.getmonth().equals("ส.ค."))
                    {
                        countyear8 += Integer.parseInt(model.getcount());
                        saleyear8  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        costyear8  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        int sale = Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        int cost = Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        profityear8 += sale-cost;
                        //       Log.d("week4",String .valueOf(profitweek4)+" "+model.getcount());
                        if(Integer.parseInt(model.getcount()) > checkcount8)
                        {
                            bestyear8 = model.gettitle();
                            countbest8 = model.getcount();
                            checkcount8 = Integer.parseInt(model.getcount());

                        }
                    }
                    if(model.getmonth().equals("ก.ย."))
                    {
                        countyear9 += Integer.parseInt(model.getcount());
                        saleyear9  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        costyear9  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        int sale = Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        int cost = Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        profityear9 += sale-cost;
                        //       Log.d("week4",String .valueOf(profitweek4)+" "+model.getcount());
                        if(Integer.parseInt(model.getcount()) > checkcount9)
                        {
                            bestyear9 = model.gettitle();
                            countbest9 = model.getcount();
                            checkcount9 = Integer.parseInt(model.getcount());

                        }
                    }
                    if(model.getmonth().equals("ต.ค."))
                    {
                        countyear10 += Integer.parseInt(model.getcount());
                        saleyear10  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        costyear10  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        int sale = Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        int cost = Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        profityear10 += sale-cost;
                        //       Log.d("week4",String .valueOf(profitweek4)+" "+model.getcount());
                        if(Integer.parseInt(model.getcount()) > checkcount10)
                        {
                            bestyear10 = model.gettitle();
                            countbest10 = model.getcount();
                            checkcount10 = Integer.parseInt(model.getcount());

                        }
                    }
                    if(model.getmonth().equals("พ.ย."))
                    {
                      //  Log.d(i);
                        countyear11 += Integer.parseInt(model.getcount());
                        saleyear11  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        costyear11  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        int sale = Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        int cost = Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        profityear11 += sale-cost;
                        //       Log.d("week4",String .valueOf(profitweek4)+" "+model.getcount());
                        if(Integer.parseInt(model.getcount()) > checkcount11)
                        {
                            bestyear11 = model.gettitle();
                            countbest11 = model.getcount();
                            checkcount11 = Integer.parseInt(model.getcount());

                        }
                    }
                    if(model.getmonth().equals("ธ.ค."))
                    {
                        countyear12 += Integer.parseInt(model.getcount());
                        saleyear12  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        costyear12  += Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        int sale = Integer.parseInt(model.getcount())*Integer.parseInt(model.getsale());
                        int cost = Integer.parseInt(model.getcount())*Integer.parseInt(model.getcost());
                        profityear12 += sale-cost;
                        //       Log.d("week4",String .valueOf(profitweek4)+" "+model.getcount());
                        if(Integer.parseInt(model.getcount()) > checkcount12)
                        {
                            bestyear12 = model.gettitle();
                            countbest12 = model.getcount();
                            checkcount12 = Integer.parseInt(model.getcount());

                        }
                    }



                    mcountsale  +=  Integer.parseInt(model.getsale())*Integer.parseInt(model.getcount());
                    mcountcost  +=  Integer.parseInt(model.getcost())*Integer.parseInt(model.getcount());
                    mcount += Integer.parseInt(model.getcount());





                }while (cursor.moveToNext());

            }
            db.close();
            cursor.close();



            listprofit.add(String.valueOf(profityear1));
            listsale.add(String.valueOf(saleyear1));
            listcost.add(String.valueOf(costyear1));
            listcount.add(String.valueOf(countyear1));
            listcountbest.add(String.valueOf(countbest1));
            listbest.add(String.valueOf(bestyear1));


            listprofit.add(String.valueOf(profityear2));
            listsale.add(String.valueOf(saleyear2));
            listcost.add(String.valueOf(costyear2));
            listcount.add(String.valueOf(countyear2));
            listcountbest.add(String.valueOf(countbest2));
            listbest.add(String.valueOf(bestyear2));

            listprofit.add(String.valueOf(profityear3));
            listsale.add(String.valueOf(saleyear3));
            listcost.add(String.valueOf(costyear3));
            listcount.add(String.valueOf(countyear3));
            listcountbest.add(String.valueOf(countbest3));
            listbest.add(String.valueOf(bestyear3));

            listprofit.add(String.valueOf(profityear4));
            listsale.add(String.valueOf(saleyear4));
            listcost.add(String.valueOf(costyear4));
            listcount.add(String.valueOf(countyear4));
            listcountbest.add(String.valueOf(countbest4));
            listbest.add(String.valueOf(bestyear4));

            listprofit.add(String.valueOf(profityear5));
            listsale.add(String.valueOf(saleyear5));
            listcost.add(String.valueOf(costyear5));
            listcount.add(String.valueOf(countyear5));
            listcountbest.add(String.valueOf(countbest5));
            listbest.add(String.valueOf(bestyear5));

            listprofit.add(String.valueOf(profityear6));
            listsale.add(String.valueOf(saleyear6));
            listcost.add(String.valueOf(costyear6));
            listcount.add(String.valueOf(countyear6));
            listcountbest.add(String.valueOf(countbest6));
            listbest.add(String.valueOf(bestyear6));

            listprofit.add(String.valueOf(profityear7));
            listsale.add(String.valueOf(saleyear7));
            listcost.add(String.valueOf(costyear7));
            listcount.add(String.valueOf(countyear7));
            listcountbest.add(String.valueOf(countbest7));
            listbest.add(String.valueOf(bestyear7));

            listprofit.add(String.valueOf(profityear8));
            listsale.add(String.valueOf(saleyear8));
            listcost.add(String.valueOf(costyear8));
            listcount.add(String.valueOf(countyear8));
            listcountbest.add(String.valueOf(countbest8));
            listbest.add(String.valueOf(bestyear8));

            listprofit.add(String.valueOf(profityear9));
            listsale.add(String.valueOf(saleyear9));
            listcost.add(String.valueOf(costyear9));
            listcount.add(String.valueOf(countyear9));
            listcountbest.add(String.valueOf(countbest9));
            listbest.add(String.valueOf(bestyear9));

            listprofit.add(String.valueOf(profityear10));
            listsale.add(String.valueOf(saleyear10));
            listcost.add(String.valueOf(costyear10));
            listcount.add(String.valueOf(countyear10));
            listcountbest.add(String.valueOf(countbest10));
            listbest.add(String.valueOf(bestyear10));

            listprofit.add(String.valueOf(profityear11));
            listsale.add(String.valueOf(saleyear11));
            listcost.add(String.valueOf(costyear11));
            listcount.add(String.valueOf(countyear11));
            listcountbest.add(String.valueOf(countbest11));
            listbest.add(String.valueOf(bestyear11));

            listprofit.add(String.valueOf(profityear12));
            listsale.add(String.valueOf(saleyear12));
            listcost.add(String.valueOf(costyear12));
            listcount.add(String.valueOf(countyear12));
            listcountbest.add(String.valueOf(countbest12));
            listbest.add(String.valueOf(bestyear12));






            adapter = new yearadapter(getActivity(), R.id.text,listprofit,listsale,listcost,listcount,listbest,listcountbest
            );
            adapter.notifyDataSetChanged();
            listyear.setAdapter(adapter);
            listyear.deferNotifyDataSetChanged();

            mcountsum = mcountsale - mcountcost;
            txtmsumcount.setText(String.valueOf(mcountsale));
            txtmsumsale.setText(String.valueOf(mcount));
            txtmsumcost.setText("("+String.valueOf(mcountcost)+")");
            txtmsumprofit.setText(String.valueOf(mcountsum));


        }

    }

    private void clearvalue() {
        countyear1=0;countyear2=0;countyear3=0;countyear4=0;countyear5=0;
        countyear6=0;countyear7=0;countyear8=0;countyear9=0;countyear10=0;countyear11=0;countyear12=0;
        saleyear1=0;saleyear2=0;saleyear3=0;saleyear4=0;saleyear5=0;saleyear6=0;
        saleyear7=0;saleyear8=0;saleyear9=0;saleyear10=0;saleyear11=0;saleyear12=0;
        costyear1=0;costyear2=0;costyear3=0;costyear4=0;costyear5=0;costyear6=0;
        costyear7=0;costyear8=0;costyear9=0;costyear10=0;costyear11=0;costyear12=0;
        profityear1=0;profityear2=0;profityear3=0;profityear4=0;profityear5=0;
        profityear6=0;profityear7=0;profityear8=0;profityear9=0;profityear10=0;profityear11=0;profityear12=0;
        checkcount1=0;checkcount2=0;checkcount3=0;checkcount4=0;checkcount5=0;checkcount6=0;
        checkcount7=0;checkcount8=0;checkcount9=0;checkcount10=0;checkcount11=0;checkcount12=0;
        bestyear1 =null;bestyear2 =null;bestyear3=null;bestyear4=null;bestyear5=null;bestyear6=null;
        bestyear7=null;bestyear8=null;bestyear9=null;bestyear10=null;bestyear11=null;bestyear12=null;
        countbest1=null;countbest2=null;countbest3=null;countbest4=null;countbest5=null;countbest6=null;
        countbest7=null;countbest8=null;countbest9=null;countbest10=null;countbest11=null;countbest12=null;
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
                " WHERE year='"+year+"'"+
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
        txtycountbest.setText("("+String.valueOf(countbest)+")"+String.valueOf(counttitlebest));

    }
}

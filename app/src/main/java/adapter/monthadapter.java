package adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nut.trade.DatabaseHelper;
import com.nut.trade.MainActivity;
import com.nut.trade.R;
import com.nut.trade.fragmentall;
import com.nut.trade.listshowActivity;

import java.util.ArrayList;

import Model.UserModel;

/**
 * Created by inwso on 14/11/2559.
 */

public class monthadapter extends ArrayAdapter<String> {



    LayoutInflater INFLATER;
    ArrayList<String> listprofit;
    ArrayList<String> listsale;
    ArrayList<String> listcost;
    ArrayList<String> listcount;
    ArrayList<String> listbest;
    ArrayList<String> listcountbest;
    ArrayList<String> listcountweek;
    Context CONTEXT;
    private ListHolders holder = null;
    private String day,month,year;
  //  private dayadapter adapter;
    private static final String DATABASE_NAME = "trade";
    private static final int DATABASE_VERSION = MainActivity.DATABASE_VERSION;
    private static final String TABLE_USER_HISTORY= "history";
    private ListView ListViewday;
    private String dayshow= null,monthshow,yearshow;


    public monthadapter(Context context, int resource, ArrayList<String> objects
            , ArrayList<String> objects2, ArrayList<String> objects3
            , ArrayList<String> objects4, ArrayList<String> objects5
            , ArrayList<String> objects6, ArrayList<String> objects7
                        ,String dayob
                        ,String monthob
                        ,String yearob
    ) {
        super(context, resource, objects);
        // TODO Auto-generated constructor stub

        listprofit = objects;
        listsale = objects2;
        listcost = objects3;
        listcount = objects4;
        listbest = objects5;
        listcountbest = objects6;
        listcountweek = objects7;
        day = dayob;
        month =  monthob;
        year = yearob;

        CONTEXT = context;
        INFLATER = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);


    }


    public View getView(final int position, View convertView, ViewGroup parent) {



        View row = convertView;

        final int itemRow = position;
        if (row == null) {


            LayoutInflater inflater = ((Activity) CONTEXT).getLayoutInflater();
            row = inflater.inflate(R.layout.listmouth, parent, false);



            holder = new monthadapter.ListHolders();


            holder.txtlmprofit = (TextView) row.findViewById(R.id.txtlmprofit);
            holder.txtlmsale = (TextView)row.findViewById(R.id.txtlmsale);
            holder.txtlmcost = (TextView) row.findViewById(R.id.txtlmcost);
            holder.txtlmcount = (TextView) row.findViewById(R.id.txtlmcount);
            holder.txtlmbest = (TextView) row.findViewById(R.id.txtlmbest);
            holder.txtlmweek = (TextView)row.findViewById(R.id.txtlmweek);

        /*    holder.btnremove = (ImageButton) row.findViewById(R.id.listbtndel);
            holder.btnedit = (ImageButton) row.findViewById(R.id.listbtnedit);
            holder.imagelist = (ImageView) row.findViewById(R.id.listimg);*/

            row.setTag(holder);

        } else {
            holder = (monthadapter.ListHolders) row.getTag();


        }


        holder.txtlmprofit.setText(listprofit.get(itemRow));
        holder.txtlmsale.setText(listsale.get(itemRow));
        holder.txtlmcost.setText(listcost.get(itemRow));
        holder.txtlmcount.setText(listcount.get(itemRow));
        if(listbest.get(itemRow).equals(null) || listbest.get(itemRow).equals("null") ||listbest.get(itemRow) == null)
        {
            holder.txtlmbest.setText("ไม่มีข้อมูล");
        }
        else
        {
            holder.txtlmbest.setText("("+listcountbest.get(itemRow)+")"+listbest.get(itemRow));
        }
        holder.txtlmweek.setText(String .valueOf(itemRow+1));

//        listcheck.add(String.valueOf(itemRow+1));
      //  final View finalRow = row;
        row.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if(!listprofit.get(itemRow).equals("0"))
                {
                    final Dialog dialog = new Dialog(v.getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.listshow);
                    dialog.setCancelable(true);
                    ListViewday = (ListView) dialog.findViewById(R.id.listshow);
                    String day = String.valueOf(itemRow+1);

                    if (day.equals("1"))
                    {
                        dayshow = "(day BETWEEN 1 and 7) ";
                    }
                    if (day.equals("2"))
                    {
                        dayshow = "(day BETWEEN  8 and 14)";
                    }
                    if (day.equals("3"))
                    {
                        dayshow = "(day BETWEEN 15 and 21)";
                    }
                    if (day.equals("4"))
                    {
                        dayshow = "(day BETWEEN 22 and 31)";
                    }
                    monthshow = month;
                    yearshow = year;
                    Log.d("PINKAK",day+dayshow);
                    getlistshowdialog();

                    Button buttoncancle = (Button) dialog.findViewById(R.id.btnclose);
                    buttoncancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });



                    dialog.show();

                   /* Intent remonthctivity = new Intent(v.getContext(), listshowActivity.class);
                    remonthctivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    remonthctivity.putExtra("day", String.valueOf(itemRow+1));
                    remonthctivity.putExtra("month", String.valueOf(month));
                    remonthctivity.putExtra("year", String.valueOf(year));
                    v.getContext().startActivity(remonthctivity);*/



                }
                else
                {
                    Toast.makeText(v.getContext(),"ยังไม่มีข้อมูล",Toast.LENGTH_SHORT).show();
                }



            }
        });




        return row;

    }

    private  void getlistshowdialog(){

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
                " WHERE " + dayshow + " and month='"+monthshow+"' " +
                " and year ='"+yearshow+"'"+
                " ORDER BY ID DESC ";

            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(select,null);
            Log.d("select",select);


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






                }while (cursor.moveToNext());

            }
            db.close();
            cursor.close();

            final dayadapter adapter = new dayadapter(getContext(), R.id.text,listid,listtitle
                    ,listdes,listlocation, listsale
                    ,listcost,listdiscount,listcount,listtime
                    ,listdate
            );
           adapter.notifyDataSetChanged();

            ListViewday.setAdapter(adapter);
            // registerForContextMenu(ListViewday);




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


    static class ListHolders {
        TextView txtlmprofit;
        TextView txtlmsale;
        TextView txtlmcost;
        TextView txtlmcount;
        TextView txtlmbest;
        TextView txtlmweek;

 ;
    }
}

package com.nut.trade;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.Time;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Model.UserModel;

/**
 * Created by IamDeveloper on 10/7/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final int VERSION = MainActivity.DATABASE_VERSION;
    private static final String DATABASE_NAME = "trade";
    private static final String ID = "id";
    private static final String TABLE_USER = "item";
    private static final String TABLE_USER_HISTORY= "history";


    private static final String COL_title = "title";
    private static final String COL_des = "des";
    private static final String COL_location = "location";
    private static final String COL_sale = "sale";
    private static final String COL_cost = "cost";
    private static final String COL_discount = "discount";
    private static final String COL_image = "image";

    private static final String COL_count = "countitem";
    private static final String COL_day = "day";
    private static final String COL_month= "month";
    private static final String COL_year= "year";
    private static final String COL_dateall= "dateall";
    private static final String COL_date= "date";
    private static final String COL_time= "time";
    private static final String COL_headid= "headid";









    private static final String CREATE_DATABASE =
            "CREATE TABLE " + TABLE_USER + "(" + ID + " INTEGER PRIMARY KEY,"
                    + COL_title + " VARCHAR(200)," + COL_des + " VARCHAR(200),"
                    + COL_location + " VARCHAR(200)," +  COL_sale + " DOUBLE(50),"
                    + COL_cost + " DOUBLE(50),"+ COL_discount + " DOUBLE(50),"
                    + COL_image + " LONGTEXT"+

                    ")";

    private static final String CREATE_DATABASE_FRIST ="CREATE TABLE "+TABLE_USER_HISTORY
            +"("+ID + " INTEGER PRIMARY KEY,"
            + COL_title + " VARCHAR(200)," + COL_des + " VARCHAR(200),"
            + COL_location + " VARCHAR(200)," +  COL_sale + " DOUBLE(50),"
            + COL_cost + " DOUBLE(50),"+ COL_discount + " DOUBLE(50),"
            + COL_count + " DOUBLE(50),"+COL_day +" DOUBLE(5),"
            + COL_month + " VARCHAR(50),"+COL_year +" VARCHAR(50),"
            + COL_dateall + " DATETIME," + COL_date + " DATE,"
            + COL_time + " TIME,"+ COL_headid + " VARCHAR(200)"+

            ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE);
        db.execSQL(CREATE_DATABASE_FRIST);

        Log.i(TAG, "CREATE DATABASE");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_HISTORY);
        Log.i(TAG, "UPGRADE DATABASE");
        onCreate(db);
    }



    public void insertitem(String title, String des, String location,String sale,String cost,String discount,String count
    ,String day,String month,String year,String dateall,String date, String time,String id
    )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("des",des);
        values.put("location",location);
        values.put("sale",sale);
        values.put("cost",cost);
        values.put("discount",discount);
        values.put("countitem",count);
        values.put("day",day);
        values.put("month",month);
        values.put("year",year);
        values.put("dateall",dateall);
        values.put("date",date);
        values.put("time",time);
        values.put("headid",id);



        db.insert(TABLE_USER_HISTORY,null,values);
    }



    public void insertUser(String title, String des, String location,String sale,String cost,String discount,String image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("des",des);
        values.put("location",location);
        values.put("sale",sale);
        values.put("cost",cost);
        values.put("discount",discount);
        values.put("image",image);




        db.insert(TABLE_USER,null,values);
    }

    public void updateUser(String id,String title, String des, String location,String sale,String cost,String discount,String image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("des",des);
        values.put("location",location);
        values.put("sale",sale);
        values.put("cost",cost);
        values.put("discount",discount);
        values.put("image",image);

        db.update(TABLE_USER,values,ID + " = ?",new String[]{id});
        Log.i(TAG, "UPDATE DATABASE");
    }

    public void deleteUser(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER,ID + " = ?",new String[]{id});
        Log.i(TAG, "DELETE DATABASE");
    }

    public void deleteday(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER_HISTORY,ID + " = ?",new String[]{id});
        Log.i(TAG, "DELETE DATABASE");
    }

    public List<UserModel> readUsermonth(String month,String year){
        List<UserModel> listUser = new ArrayList<>();
        String select = "SELECT * FROM " + TABLE_USER_HISTORY +" WHERE month='"+month+"'and year='"+year+"' and day>=1 and   day <=7 UNION "
                        +"SELECT * FROM " + TABLE_USER_HISTORY +" WHERE month='"+month+"'and year='"+year+"'  and day>=8 and   day <=14 UNION "
                        +"SELECT * FROM " + TABLE_USER_HISTORY +" WHERE month='"+month+"'and year='"+year+"'  and day>=15 and   day <=21 UNION "
                        +"SELECT * FROM " + TABLE_USER_HISTORY +" WHERE month='"+month+"'and year='"+year+"'  and day>=22 and   day <=31  "
                ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select,null);
        if(cursor.moveToFirst()){
            do{
                UserModel model = new UserModel();
                model.setId(cursor.getString(cursor.getColumnIndex(ID)));
                model.settitle(cursor.getString(cursor.getColumnIndex(COL_title)));
                model.setdes(cursor.getString(cursor.getColumnIndex(COL_des)));
                model.setlocation(cursor.getString(cursor.getColumnIndex(COL_location)));
                model.setsale(cursor.getString(cursor.getColumnIndex(COL_sale)));
                model.setcost(cursor.getString(cursor.getColumnIndex(COL_cost)));
                model.setdiscount(cursor.getString(cursor.getColumnIndex(COL_discount)));
                model.setimage(cursor.getString(cursor.getColumnIndex(COL_count)));
                model.setday(cursor.getString(cursor.getColumnIndex(COL_day)));
                model.setmonth(cursor.getString(cursor.getColumnIndex(COL_month)));
                model.setyear(cursor.getString(cursor.getColumnIndex(COL_year)));


                listUser.add(model);

            }while (cursor.moveToNext());
        }
        db.close();
        cursor.close();

        return listUser;
    }

    public List<UserModel> readUseryear(String year){
        List<UserModel> listUser = new ArrayList<>();
        String select = "SELECT * FROM " + TABLE_USER_HISTORY +" WHERE year='"+year+"'"
                ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select,null);
        if(cursor.moveToFirst()){
            do{
                UserModel model = new UserModel();
                model.setId(cursor.getString(cursor.getColumnIndex(ID)));
                model.settitle(cursor.getString(cursor.getColumnIndex(COL_title)));
                model.setdes(cursor.getString(cursor.getColumnIndex(COL_des)));
                model.setlocation(cursor.getString(cursor.getColumnIndex(COL_location)));
                model.setsale(cursor.getString(cursor.getColumnIndex(COL_sale)));
                model.setcost(cursor.getString(cursor.getColumnIndex(COL_cost)));
                model.setdiscount(cursor.getString(cursor.getColumnIndex(COL_discount)));
                model.setimage(cursor.getString(cursor.getColumnIndex(COL_count)));
                model.setday(cursor.getString(cursor.getColumnIndex(COL_day)));
                model.setmonth(cursor.getString(cursor.getColumnIndex(COL_month)));
                model.setyear(cursor.getString(cursor.getColumnIndex(COL_year)));


                listUser.add(model);

            }while (cursor.moveToNext());
        }
        db.close();
        cursor.close();

        return listUser;
    }




    public List<UserModel> readUserday(String day,String month,String year){
        List<UserModel> listUser = new ArrayList<>();
        String select = "SELECT * FROM " + TABLE_USER_HISTORY +
                " WHERE day='"+day+"'"+"and month='"+month+"'"+
                "and year='"+year+"'"+
                " ORDER BY ID DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select,null);
        if(cursor.moveToFirst()){
            do{
                UserModel model = new UserModel();
                model.setId(cursor.getString(cursor.getColumnIndex(ID)));
                model.settitle(cursor.getString(cursor.getColumnIndex(COL_title)));
                model.setdes(cursor.getString(cursor.getColumnIndex(COL_des)));
                model.setlocation(cursor.getString(cursor.getColumnIndex(COL_location)));
                model.setsale(cursor.getString(cursor.getColumnIndex(COL_sale)));
                model.setcost(cursor.getString(cursor.getColumnIndex(COL_cost)));
                model.setdiscount(cursor.getString(cursor.getColumnIndex(COL_discount)));
                model.setimage(cursor.getString(cursor.getColumnIndex(COL_count)));


                listUser.add(model);

            }while (cursor.moveToNext());
        }
        db.close();
        cursor.close();

        return listUser;
    }

    public List<UserModel> readUserdayall(){
        List<UserModel> listUser = new ArrayList<>();

        String select = "SELECT * FROM " + TABLE_USER_HISTORY +
                " ORDER BY ID "; /*DESC LIMIT 10 OFFSET "+pagestart;*/
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select,null);
        if(cursor.moveToFirst()){
            do{
                UserModel model = new UserModel();
                model.setId(cursor.getString(cursor.getColumnIndex(ID)));
                model.settitle(cursor.getString(cursor.getColumnIndex(COL_title)));
                model.setdes(cursor.getString(cursor.getColumnIndex(COL_des)));
                model.setlocation(cursor.getString(cursor.getColumnIndex(COL_location)));
                model.setsale(cursor.getString(cursor.getColumnIndex(COL_sale)));
                model.setcost(cursor.getString(cursor.getColumnIndex(COL_cost)));
                model.setdiscount(cursor.getString(cursor.getColumnIndex(COL_discount)));
                model.setimage(cursor.getString(cursor.getColumnIndex(COL_count)));



                listUser.add(model);

            }while (cursor.moveToNext());
        }
        db.close();
        cursor.close();

        return listUser;
    }


    public List<UserModel> readUser(){
        List<UserModel> listUser = new ArrayList<>();
        String select = "SELECT * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select,null);
        if(cursor.moveToFirst()){
            do{
                UserModel model = new UserModel();
                model.setId(cursor.getString(cursor.getColumnIndex(ID)));
                model.settitle(cursor.getString(cursor.getColumnIndex(COL_title)));
                model.setdes(cursor.getString(cursor.getColumnIndex(COL_des)));
                model.setlocation(cursor.getString(cursor.getColumnIndex(COL_location)));
                model.setsale(cursor.getString(cursor.getColumnIndex(COL_sale)));
                model.setcost(cursor.getString(cursor.getColumnIndex(COL_cost)));
                model.setdiscount(cursor.getString(cursor.getColumnIndex(COL_discount)));
                model.setimage(cursor.getString(cursor.getColumnIndex(COL_image)));


                listUser.add(model);

            }while (cursor.moveToNext());
        }
        db.close();
        cursor.close();

        return listUser;
    }


    public List<UserModel> readUsersortmain(String sortfrist,String sortsecound){
        List<UserModel> listUser = new ArrayList<>();
        String select = "SELECT * FROM " + TABLE_USER +" ORDER BY "+ sortfrist +" "+sortsecound;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select,null);
        if(cursor.moveToFirst()){
            do{
                UserModel model = new UserModel();
                model.setId(cursor.getString(cursor.getColumnIndex(ID)));
                model.settitle(cursor.getString(cursor.getColumnIndex(COL_title)));
                model.setdes(cursor.getString(cursor.getColumnIndex(COL_des)));
                model.setlocation(cursor.getString(cursor.getColumnIndex(COL_location)));
                model.setsale(cursor.getString(cursor.getColumnIndex(COL_sale)));
                model.setcost(cursor.getString(cursor.getColumnIndex(COL_cost)));
                model.setdiscount(cursor.getString(cursor.getColumnIndex(COL_discount)));
                model.setimage(cursor.getString(cursor.getColumnIndex(COL_image)));

             /*   Log.d("modelget",model.getId());*/
            /* final itemadapter adapter = new itemadapter(DatabaseHelper.this, R.id.text, model.getId(),model.gettitle()
                        , model.getdes(),model.getlocation(), model.getsale()
                        ,model.getcost(),model.getdiscount(),model.getimage());
                adapter.notifyDataSetChanged();
*/

                listUser.add(model);

            }while (cursor.moveToNext());
        }
        db.close();
        cursor.close();

        return listUser;
    }


    public List<UserModel> readUserlistshow(String day,String month,String year){
        List<UserModel> listUser = new ArrayList<>();
        String select = "SELECT * FROM " + TABLE_USER_HISTORY +
                " WHERE " + day + " and month='+"+month+"' " +
                " and year ='"+year+"'"+
                "ORDER BY ID DESC ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select,null);
        if(cursor.moveToFirst()){
            do{
                UserModel model = new UserModel();
                model.setId(cursor.getString(cursor.getColumnIndex(ID)));
                model.settitle(cursor.getString(cursor.getColumnIndex(COL_title)));
                model.setdes(cursor.getString(cursor.getColumnIndex(COL_des)));
                model.setlocation(cursor.getString(cursor.getColumnIndex(COL_location)));
                model.setsale(cursor.getString(cursor.getColumnIndex(COL_sale)));
                model.setcost(cursor.getString(cursor.getColumnIndex(COL_cost)));
                model.setdiscount(cursor.getString(cursor.getColumnIndex(COL_discount)));
                model.setimage(cursor.getString(cursor.getColumnIndex(COL_count)));


                listUser.add(model);

            }while (cursor.moveToNext());
        }
        db.close();
        cursor.close();

        return listUser;
    }








}

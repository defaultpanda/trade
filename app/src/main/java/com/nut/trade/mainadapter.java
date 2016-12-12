package com.nut.trade;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by inwso on 6/11/2559.
 */

public class mainadapter extends ArrayAdapter<String> {

    private final SharedPreferences prefs;
    LayoutInflater INFLATER;
    ArrayList<String> listid;
    ArrayList<String> listtitle;
    ArrayList<String> listdes;
    ArrayList<String> listlocation;
    ArrayList<String> listsale;
    ArrayList<String> listcost;
    ArrayList<String> listdiscount;
    ArrayList<String> listimage;



    Context CONTEXT;
    private ListHolders holder = null;
    private EditText tvedtcount;



    public mainadapter(Context context, int resource, ArrayList<String> objects
            , ArrayList<String> objects2, ArrayList<String> objects3
            , ArrayList<String> objects4, ArrayList<String> objects5
            , ArrayList<String> objects6, ArrayList<String> objects7
            , ArrayList<String> objects8) {
        super(context, resource, objects);
        // TODO Auto-generated constructor stub

        listid = objects;
        listtitle = objects2;
        listdes = objects3;
        listlocation = objects4;
        listsale = objects5;
        listcost = objects6;
        listdiscount = objects7;
        listimage = objects8;


        CONTEXT = context;
        INFLATER = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);


        prefs = PreferenceManager.getDefaultSharedPreferences(context);





    }


    public View getView(int position, View convertView, final ViewGroup parent) {



        View row = convertView;

        final int itemRow = position;
        if (row == null) {


            LayoutInflater inflater = ((Activity) CONTEXT).getLayoutInflater();
            row = inflater.inflate(R.layout.listitem, parent, false);



            holder = new ListHolders();

            holder.txttitle = (TextView) row.findViewById(R.id.txttitleitem);
            holder.txtdes = (TextView)row.findViewById(R.id.txtdesitem);
            holder.txtlocation = (TextView) row.findViewById(R.id.txtlocationitem);
            holder.txtsale = (TextView) row.findViewById(R.id.txtprice);
            holder.txtcost = (TextView) row.findViewById(R.id.txtcosts);
            holder.txtdiscount = (TextView) row.findViewById(R.id.txtpromotion);
            holder.btnadd = (ImageButton) row.findViewById(R.id.imgbtnadd);
            holder.btnremove = (ImageButton) row.findViewById(R.id.imgbtnremove);
            holder.edtcount = (EditText) row.findViewById(R.id.edtcount);

            holder.imagelist = (ImageView) row.findViewById(R.id.imgitem);

/*

            holder.txttitlemarkflag = (TextView) row.findViewById(R.id.txtnotifi);
            holder.txtdesmarkflag = (TextView) row.findViewById(R.id.txtdesnoti);
            holder.txtdateflag = (TextView) row.findViewById(R.id.txtdatenotifi);

            holder.imgmarknoti = (ImageView) row.findViewById(R.id.imgmarknoti);
*/


            row.setTag(holder);

        } else {
            holder = (ListHolders) row.getTag();



        }

        holder.txttitle.setText(listtitle.get(itemRow));
        holder.txtdes.setText(listdes.get(itemRow));
        holder.txtlocation.setText(listlocation.get(itemRow));
        holder.txtsale.setText(listsale.get(itemRow));
        holder.txtcost.setText(listcost.get(itemRow));
        holder.txtdiscount.setText(listdiscount.get(itemRow));

        final View finalRow = row;
        holder.btnremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int size =  MainActivity.mainitem.listitem.size();
                EditText edtmain  = (EditText) finalRow.findViewById(R.id.edtcount);
                int count =  Integer.parseInt(edtmain.getText().toString());
                if(count<=0)
                {
                    count = 0;
                }
                else
                {
                    count = Integer.parseInt(edtmain.getText().toString())-1;
                }


                if(count<=0)
                {

                    for(int i=0;i<size;i++)
                 /*   while(MainActivity)*/
                    {
                        if( MainActivity.mainitem.listitem.get(i).get("id").equals(listid.get(itemRow)))
                        {
                            MainActivity.mainitem.listitem.remove(i);
                            i = 0;
                            size = MainActivity.mainitem.listitem.size();

                        }

                    }


                }
                else
                {

                    for(int i=0;i<size;i++)
                    {
                        if( MainActivity.mainitem.listitem.get(i).get("id").equals(listid.get(itemRow)))
                        {
                            MainActivity.mainitem.listitem.remove(i);
                            i = 0;
                            size = MainActivity.mainitem.listitem.size();
                        }




                    }

                    HashMap<String,String> item;
                    item = new HashMap<String, String>();
                    item.put("id",listid.get(itemRow));
                    item.put("title",listtitle.get(itemRow));
                    item.put("des",listdes.get(itemRow));
                    item.put("location",listlocation.get(itemRow));
                    item.put("sale",listsale.get(itemRow));
                    item.put("cost",listcost.get(itemRow));
                    item.put("discount",listdiscount.get(itemRow));
                    item.put("count",String.valueOf(count));

                    MainActivity.mainitem.listitem.add(item);
/*
                    Log.d("countremovedown",String.valueOf(MainActivity.mainitem.listitem.size()));*/
                  /*  Log.d("itemid", MainActivity.mainitem.listitem.get(itemRow).get("id"));
                    Log.d("itemcount", MainActivity.mainitem.listitem.get(itemRow).get("count"));*/



                }

                edtmain.setText(String.valueOf(count));

            }
        });





        holder.btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int size =  MainActivity.mainitem.listitem.size();

                for(int i=0;i<size;i++)
                {
                   /* Log.d("-----------",i+"-----------");
                    Log.d("size",String.valueOf(size));
                    Log.d("iiii",String.valueOf(i));
                    Log.d("mainid",MainActivity.mainitem.listitem.get(i).get("id"));
                    Log.d("id",listid.get(itemRow));*/
                    if( MainActivity.mainitem.listitem.get(i).get("id").equals(listid.get(itemRow))) {

                        MainActivity.mainitem.listitem.remove(i);
                        size = MainActivity.mainitem.listitem.size();
                        i = 0;
                    }
                }



                EditText edtmain  = (EditText) finalRow.findViewById(R.id.edtcount);
                int  count = Integer.parseInt(edtmain.getText().toString())+1;
               /* Log.d("count", String.valueOf(count));*/


                HashMap<String,String> item;

                item = new HashMap<String, String>();
                item.put("id",listid.get(itemRow));
                item.put("title",listtitle.get(itemRow));
                item.put("des",listdes.get(itemRow));
                item.put("location",listlocation.get(itemRow));
                item.put("sale",listsale.get(itemRow));
                item.put("cost",listcost.get(itemRow));
                item.put("discount",listdiscount.get(itemRow));
                item.put("count",String.valueOf(count));
                MainActivity.mainitem.listitem.add(item);

                edtmain.setText(String.valueOf(count));


            }
        });

        if(listimage.get(itemRow).equals("")||listimage.get(itemRow).equals("0"))
        {
            holder.imagelist.setImageResource(0);
            holder.imagelist.setImageResource(android.R.drawable.ic_menu_report_image);
        }
        else
        {
            byte[] decodedString = Base64.decode(listimage.get(itemRow), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.imagelist.buildDrawingCache();
            holder.imagelist.setImageBitmap(decodedByte);
        }


        return row;


    }





    static class ListHolders {


        TextView txtid;
        TextView txttitle;
        TextView txtdes;
        TextView txtlocation;
        TextView txtsale;
        TextView txtcost;
        TextView txtdiscount;
        ImageView imagelist;
        ImageButton btnremove;
        ImageButton btnadd;
        EditText edtcount;




    }
}

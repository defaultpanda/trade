package com.nut.trade;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class itemadapter extends ArrayAdapter<String> {


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




    public itemadapter(Context context, int resource, ArrayList<String> objects
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


    public View getView(int position, View convertView, ViewGroup parent) {


        View row = convertView;

        final int itemRow = position;
        if (row == null) {


            LayoutInflater inflater = ((Activity) CONTEXT).getLayoutInflater();
            row = inflater.inflate(R.layout.customviewitem, parent, false);


            holder = new ListHolders();
            holder.txttitle = (TextView) row.findViewById(R.id.cusitemtitle);
            holder.txtdes = (TextView)row.findViewById(R.id.cusitemdes);
            holder.txtlocation = (TextView) row.findViewById(R.id.cusitemlocation);
            holder.txtsale = (TextView) row.findViewById(R.id.cusitemsale);
            holder.txtcost = (TextView) row.findViewById(R.id.cusitemcost);
            holder.txtdiscount = (TextView) row.findViewById(R.id.cusitemdiscount);
            holder.btnremove = (ImageButton) row.findViewById(R.id.listbtndel);
            holder.btnedit = (ImageButton) row.findViewById(R.id.listbtnedit);
            holder.imagelist = (ImageView) row.findViewById(R.id.listimg);


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

        holder.btnremove.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                new AlertDialog.Builder(getContext())
                        .setTitle("ลบรายการ")
                        .setMessage("คุณต้องการลบรายการ "+listtitle.get(itemRow)+" ใช่หรือไม่ ?")
                        .setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseHelper helper = new DatabaseHelper(getContext());
                                helper.deleteUser(listid.get(itemRow));

                                Intent itemactivity = new Intent(getContext(),itemactivity.class);
                                ((Activity) getContext()).finish();
                                getContext().startActivity(itemactivity);

                                Toast.makeText(getContext(),"ลบรายการเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show();


                            }
                        })
                        .setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();


            }
        });

        holder.btnedit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getContext())
                        .setTitle("แก้ไขข้อมูล")
                        .setMessage("คุณต้องการแก้ไขข้อมูล "+listtitle.get(itemRow)+" ใช่หรือไม่ ?")
                        .setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                com.nut.trade.itemactivity.checkedit.editid= listid.get(itemRow);
                                Intent reitemactivity = new Intent(getContext(), itemactivity.class);
                                reitemactivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                reitemactivity.putExtra("title",listtitle.get(itemRow));
                                reitemactivity.putExtra("des",listdes.get(itemRow));
                                reitemactivity.putExtra("location",listlocation.get(itemRow));
                                reitemactivity.putExtra("sale",listsale.get(itemRow));
                                reitemactivity.putExtra("cost",listcost.get(itemRow));
                                reitemactivity.putExtra("discount",listdiscount.get(itemRow));
                                reitemactivity.putExtra("image",listimage.get(itemRow));
                                ((Activity) getContext()).finish();
                                getContext().startActivity(reitemactivity);


                                Toast.makeText(getContext(),"กรุณาเปลี่ยนแปลงข้อมูลแล้วกดปุ่มแก้ไข", Toast.LENGTH_SHORT).show();


                            }
                        })
                        .setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();

            }
        });


      //  Log.d("listimage",listimage.get(itemRow));
        if(listimage.get(itemRow).equals("")||listimage.get(itemRow).equals("0"))
        {

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
        ImageButton btnedit;
    }

}

package adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nut.trade.DatabaseHelper;
import com.nut.trade.R;
import com.nut.trade.fragmentall;
import com.nut.trade.itemactivity;
import com.nut.trade.itemadapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import Model.UserModel;
import fragment.dayactivity;

/**
 * Created by inwso on 14/11/2559.
 */

public class dayadapter extends ArrayAdapter<String> {



    LayoutInflater INFLATER;
    ArrayList<String> listid;
    ArrayList<String> listtitle;
    ArrayList<String> listdes;
    ArrayList<String> listlocation;
    ArrayList<String> listsale;
    ArrayList<String> listcost;
    ArrayList<String> listdiscount;
    ArrayList<String> listcount;
    ArrayList<String> listtime;
    ArrayList<String> listdate;
    Context CONTEXT;
    private ListHolders holder = null;


    public dayadapter(Context context, int resource, ArrayList<String> objects
            , ArrayList<String> objects2, ArrayList<String> objects3
            , ArrayList<String> objects4, ArrayList<String> objects5
            , ArrayList<String> objects6, ArrayList<String> objects7
            , ArrayList<String> objects8, ArrayList<String> objects9
            , ArrayList<String> objects10
    ) {
        super(context, resource, objects);
        // TODO Auto-generated constructor stub

        listid = objects;
        listtitle = objects2;
        listdes = objects3;
        listlocation = objects4;
        listsale = objects5;
        listcost = objects6;
        listdiscount = objects7;
        listcount = objects8;
        listtime = objects9;
        listdate = objects10;


        CONTEXT = context;
        INFLATER = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);


    }


    public View getView(int position, View convertView, ViewGroup parent) {



        View row = convertView;

        final int itemRow = position;
        if (row == null) {


            LayoutInflater inflater = ((Activity) CONTEXT).getLayoutInflater();
            row = inflater.inflate(R.layout.listday, parent, false);



            holder = new dayadapter.ListHolders();


            holder.txttitle = (TextView) row.findViewById(R.id.txtdtitle);
            holder.txtdes = (TextView)row.findViewById(R.id.txtddes);
            holder.txtlocation = (TextView) row.findViewById(R.id.txtdlocation);
            holder.txtsale = (TextView) row.findViewById(R.id.txtdsale);
            holder.txtcost = (TextView) row.findViewById(R.id.txtdcost);
            holder.txtdiscount = (TextView) row.findViewById(R.id.txtddiscount);
            holder.txtcount = (TextView) row.findViewById(R.id.txtdcount);
            holder.txtditme = (TextView) row.findViewById(R.id.txtditme);
            holder.txtddate = (TextView) row.findViewById(R.id.txtddate);

        /*    holder.btnremove = (ImageButton) row.findViewById(R.id.listbtndel);
            holder.btnedit = (ImageButton) row.findViewById(R.id.listbtnedit);
            holder.imagelist = (ImageView) row.findViewById(R.id.listimg);*/

            row.setTag(holder);

        } else {
            holder = (dayadapter.ListHolders) row.getTag();


        }


        holder.txttitle.setText(listtitle.get(itemRow));
        holder.txtdes.setText(listdes.get(itemRow));
        holder.txtlocation.setText(listlocation.get(itemRow));
        holder.txtsale.setText(listsale.get(itemRow));
        holder.txtcost.setText(listcost.get(itemRow));
        holder.txtdiscount.setText(listdiscount.get(itemRow));
        holder.txtcount.setText(listcount.get(itemRow));
        holder.txtditme.setText(" "+listtime.get(itemRow)+")");
        holder.txtddate.setText("("+listdate.get(itemRow));

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getContext())
                        .setTitle("ลบรายการ")
                        .setMessage("คุณต้องการลบรายการ "+listtitle.get(itemRow)+" ใช่หรือไม่ ?")
                        .setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseHelper helper = new DatabaseHelper(getContext());
                                helper.deleteday(listid.get(itemRow));

                                Intent itemactivity = new Intent(getContext(), fragmentall.class);
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
        TextView txtcount;
        TextView txtditme;
        TextView txtddate;
        ImageView imagelist;
        ImageButton btnremove;
        ImageButton btnedit;
    }


}

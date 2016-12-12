package adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.ArrayList;

/**
 * Created by inwso on 14/11/2559.
 */

public class yearadapter extends ArrayAdapter<String> {


    LayoutInflater INFLATER;
    ArrayList<String> listprofit;
    ArrayList<String> listsale;
    ArrayList<String> listcost;
    ArrayList<String> listcount;
    ArrayList<String> listbest;
    ArrayList<String> listcountbest;
    Context CONTEXT;
    private ListHolders holder = null;


    public yearadapter(Context context, int resource, ArrayList<String> objects
            , ArrayList<String> objects2, ArrayList<String> objects3
            , ArrayList<String> objects4, ArrayList<String> objects5
            , ArrayList<String> objects6
    ) {
        super(context, resource, objects);
        // TODO Auto-generated constructor stub

        listprofit = objects;
        listsale = objects2;
        listcost = objects3;
        listcount = objects4;
        listbest = objects5;
        listcountbest = objects6;



        CONTEXT = context;
        INFLATER = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);


    }


    public View getView(int position, View convertView, ViewGroup parent) {



        View row = convertView;

        final int itemRow = position;
        if (row == null) {


            LayoutInflater inflater = ((Activity) CONTEXT).getLayoutInflater();
            row = inflater.inflate(R.layout.listyear, parent, false);



            holder = new yearadapter.ListHolders();


            holder.txtlmprofit = (TextView) row.findViewById(R.id.txtyprofit);
            holder.txtlmsale = (TextView)row.findViewById(R.id.txtysumsale);
            holder.txtlmcost = (TextView) row.findViewById(R.id.txtysumcost);
            holder.txtlmcount = (TextView) row.findViewById(R.id.txtysumcount);
            holder.txtlmbest = (TextView) row.findViewById(R.id.txtybest);
            holder.txtlmyear = (TextView)row.findViewById(R.id.txtheadyear);
            holder.txtshowymonth = (TextView) row.findViewById(R.id.txtshowymonth);


        /*    holder.btnremove = (ImageButton) row.findViewById(R.id.listbtndel);
            holder.btnedit = (ImageButton) row.findViewById(R.id.listbtnedit);
            holder.imagelist = (ImageView) row.findViewById(R.id.listimg);*/

            row.setTag(holder);

        } else {
            holder = (yearadapter.ListHolders) row.getTag();


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
        holder.txtlmyear.setText(String .valueOf(itemRow+1));

        showmonth();






        return row;

    }

    private void showmonth() {

        if(holder.txtlmyear.getText().toString().equals("1"))
        {
            holder.txtshowymonth.setText("(ม.ค.)");
        }
        if(holder.txtlmyear.getText().toString().equals("2"))
        {
            holder.txtshowymonth.setText("(ก.พ.)");
        }
        if(holder.txtlmyear.getText().toString().equals("3"))
        {
            holder.txtshowymonth.setText("(มี.ค.)");
        }
        if(holder.txtlmyear.getText().toString().equals("4"))
        {
            holder.txtshowymonth.setText("(เม.ย.)");
        }
        if(holder.txtlmyear.getText().toString().equals("5"))
        {
            holder.txtshowymonth.setText("(พ.ค.)");
        }
        if(holder.txtlmyear.getText().toString().equals("6"))
        {
            holder.txtshowymonth.setText("(มิ.ย.)");
        }
        if(holder.txtlmyear.getText().toString().equals("7"))
        {
            holder.txtshowymonth.setText("(ก.ค.)");
        }
        if(holder.txtlmyear.getText().toString().equals("8"))
        {
            holder.txtshowymonth.setText("(ส.ค.)");
        }
        if(holder.txtlmyear.getText().toString().equals("9"))
        {
            holder.txtshowymonth.setText("(ก.ย.)");
        }
        if(holder.txtlmyear.getText().toString().equals("10"))
        {
            holder.txtshowymonth.setText("(ต.ค.)");
        }
        if(holder.txtlmyear.getText().toString().equals("11"))
        {
            holder.txtshowymonth.setText("(พ.ย.)");
        }
        if(holder.txtlmyear.getText().toString().equals("12"))
        {
            holder.txtshowymonth.setText("(ธ.ค.)");
        }
    }








    static class ListHolders {
        TextView txtlmprofit;
        TextView txtlmsale;
        TextView txtlmcost;
        TextView txtlmcount;
        TextView txtlmbest;
        TextView txtlmyear;
        TextView txtshowymonth;


    }
}

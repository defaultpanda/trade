package Model;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * Created by IamDeveloper on 10/10/2016.
 */
public class UserModel extends BaseAdapter {
    String id;
    String title;
    String des;
    String location;
    String sale;
    String cost;
    String discount;
    String image;
    String date;
    String count;
    String time;
    String day;
    String month;
    String year;
    String sumcount;
   /* String date;*/


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String gettitle() {return title;}
    public void settitle(String title) {
        this.title = title;
    }

    public String getdes() {return des;}
    public void setdes(String des) {
        this.des = des;
    }

    public String getlocation() {
        return location;
    }
    public void setlocation(String location) {
        this.location = location;
    }

    public String getsale() {
        return sale;
    }
    public void setsale(String sale) {
        this.sale = sale;
    }

    public String getcost() {
        return cost;
    }
    public void setcost(String cost) {
        this.cost = cost;
    }

    public String getdiscount() {return discount;}
    public void setdiscount(String discount) {
        this.discount = discount;
    }

    public String getimage() {return image;}
    public void setimage(String image) {
        this.image = image;
    }

    public String getdate() {return date;}
    public void setdate(String date) {
        this.date = date;
    }

    public String getcount() {return count;}
    public void setcount(String count) {
        this.count = count;
    }

    public String gettime() {return time;}
    public void settime(String time) {
        this.time = time;
    }

    public String getday() {return day;}
    public void setday(String day) {
        this.day = day;
    }

    public String getmonth() {return month;}
    public void setmonth(String month) {
        this.month = month;
    }

    public String getyear() {return year;}
    public void setyear(String year) {
        this.year = year;
    }

    public String getsumcount() {return sumcount;}
    public void setsumcount(String sumcount) {
        this.sumcount = sumcount;
    }











    @Override
    public String toString() {

        return getsale()+"     "+gettitle()+"\n"
                +getcost()+"     "+getdes()+"\n"
                +getdiscount()+"     "+getlocation()+"\n"
                +getimage()+"     "+getdate();

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        return null;
    }
}

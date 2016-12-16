package com.nut.trade;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import Model.UserModel;

import static android.R.drawable.ic_menu_report_image;


/**
 * Created by inwso on 29/9/2559.
 */
public class itemactivity extends Activity{

    private EditText edttitle,edtdes,edtlocation,edtsale,edtcost,edtdiscount,edtsearch;
    private ImageButton imgbtnconfirm,imgbtnclear,imgbtnedit,imgbtndel;
    private ImageView imgtrade;
    private Spinner spinsortadd;
    private Button btnsearch;
    private ListView listView;
    private String sortadd;
    private List<UserModel> readUser;
    private ArrayAdapter<UserModel> adapter;
    private static final String TABLE_USER = "item";
    private static final String DATABASE_NAME = "trade";
    private static final int DATABASE_VERSION  = MainActivity.DATABASE_VERSION;
    private String IT_title,IT_des,IT_location,IT_sale,IT_cost,IT_discount,IT_image;
    private static int RESULT_LOAD_IMG = 1;
    public String imgPath, fileName;
    private ImageView imgView;
    private ImageButton btnremoveimg;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public Bitmap bitmap;
    public String encodedString;
    public boolean checkinputimage = false;
    private LinearLayout layouttopadditem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.additem);
        setTheme(android.R.style.Theme_Holo_Light);
    /*    Bundle bundle = getIntent().getExtras();*/
        bindView();
        verifyStoragePermissions(this);
        addlistspinitem();
        setintent();
        eventbutton();





    }

    private void eventbutton() {
        spinsortadd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinsortadd.getSelectedItemPosition() == 0)
                {
                    sortadd = "id";
                 //   Log.d("llll",sortadd);
                    getUserspin(sortadd);

                }
                if(spinsortadd.getSelectedItemPosition() == 1)
                {
                    sortadd = "sale";
                 //   Log.d("llll",sortadd);
                    getUserspin(sortadd);

                }
                if(spinsortadd.getSelectedItemPosition() == 2)
                {
                    sortadd = "cost";
                //    Log.d("llll",sortadd);
                    getUserspin(sortadd);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imgbtnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edttitle.setText("");
                edtdes.setText("");
                edtlocation.setText("");
                edtsale.setText("");
                edtcost.setText("");
                edtdiscount.setText("0");
                imgbtnconfirm.setVisibility(View.VISIBLE);
                imgbtnedit.setVisibility(View.GONE);
                imgView.setImageResource(android.R.drawable.ic_menu_report_image);
                imgbtndel.setVisibility(View.GONE);

            }
        });

        imgtrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });

        imgbtnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData(checkedit.editid);

            }
        });



        imgbtnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });

        imgbtndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPath = null;
                fileName = null;
                imgbtndel.setVisibility(View.GONE);
                imgView.setImageResource(android.R.drawable.ic_menu_report_image);
            }
        });

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUsersearch(edtsearch.getText().toString());
            }
        });
    }

    private void setintent() {
        Intent intent = getIntent();
        IT_title = intent.getStringExtra("title");
        IT_des = intent.getStringExtra("des");
        IT_location = intent.getStringExtra("location");
        IT_sale = intent.getStringExtra("sale");
        IT_cost = intent.getStringExtra("cost");
        IT_discount = intent.getStringExtra("discount");
        IT_image = intent.getStringExtra("image");

        if(IT_title != null){
            edttitle.setText(IT_title);
            edtdes.setText(IT_des);
            edtlocation.setText(IT_location);
            edtsale.setText(IT_sale);
            edtcost.setText(IT_cost);
            edtdiscount.setText(IT_discount);

            imgbtnconfirm.setVisibility(View.GONE);
            imgbtnedit.setVisibility(View.VISIBLE);


            if(IT_image.equals("") || IT_image.equals("0"))
            {

            }
            else
            {
                byte[] decodedString = Base64.decode(IT_image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imgView.buildDrawingCache();
                imgView.setImageBitmap(decodedByte);
                imgbtndel.setVisibility(View.VISIBLE);
            }



        }
    }

    private void addlistspinitem() {
        ArrayList<String> spinfristList =  new ArrayList<String>();
        spinfristList.add("เวลาบันทึก");
        spinfristList.add("ราคา");
        spinfristList.add("ต้นทุน");


        ArrayAdapter<String> listspindayAdapter = new ArrayAdapter<String>(itemactivity.this,
                android.R.layout.simple_dropdown_item_1line, spinfristList);
        listspindayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinsortadd.setAdapter(listspindayAdapter);
    }

    public static class checkedit {
        public static String  editid= "";

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();


        if (id == R.id.action_settings) {

            return true;
        }

        switch (item.getItemId()) {


            case R.id.home:

            {

                Intent intentitem = new Intent(itemactivity.this, MainActivity.class);
                intentitem.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentitem.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentitem);
                return true;
            }

            case R.id.history: {

                Intent fragment = new Intent(getApplicationContext(), fragmentall.class);
                startActivity(fragment);
                return true;


            }

            case R.id.item: {

                Intent newIntent = new Intent(itemactivity.this,itemactivity.class);
                  finish();
                startActivity(newIntent);
                return true;

            }

            default:
                return super.onOptionsItemSelected(item);

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        getUser();
    }


    private void getUser() {



        SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(
                getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };

        ArrayList<String>  listid = new ArrayList<String>();
        ArrayList<String>  listtitle = new ArrayList<String>();
        ArrayList<String>  listdes = new ArrayList<String>();
        ArrayList<String>  listlocation = new ArrayList<String>();
        ArrayList<String>  listsale = new ArrayList<String>();
        ArrayList<String>  listcost = new ArrayList<String>();
        ArrayList<String>  listdiscount = new ArrayList<String>();
        ArrayList<String>  listimage = new ArrayList<String>();



       /* List<UserModel> listUser = new ArrayList<>();*/
        String select = "SELECT * FROM " + TABLE_USER +" ORDER BY ID DESC";

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
                model.setimage(cursor.getString(cursor.getColumnIndex("image")));

                listid.add(model.getId());
                listtitle.add(model.gettitle());
                listdes.add(model.getdes());
                listlocation.add(model.getlocation());
                listsale.add(model.getsale());
                listcost.add(model.getcost());
                listdiscount.add(model.getdiscount());
                listimage.add(model.getimage());

                Log.d("Testtt",model.getcost());

                layouttopadditem.setVisibility(View.VISIBLE);
             /*   listUser.add(model);*/

            }while (cursor.moveToNext());
//**

        }
        db.close();
        cursor.close();


        itemadapter adapter = new itemadapter(itemactivity.this, R.id.text,listid,listtitle
                ,listdes,listlocation, listsale
                ,listcost,listdiscount,listimage);

        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);



    }


    private void getUserspin(String spin) {

        SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(
                getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };

        ArrayList<String>  listid = new ArrayList<String>();
        ArrayList<String>  listtitle = new ArrayList<String>();
        ArrayList<String>  listdes = new ArrayList<String>();
        ArrayList<String>  listlocation = new ArrayList<String>();
        ArrayList<String>  listsale = new ArrayList<String>();
        ArrayList<String>  listcost = new ArrayList<String>();
        ArrayList<String>  listdiscount = new ArrayList<String>();
        ArrayList<String>  listimage = new ArrayList<String>();



       /* List<UserModel> listUser = new ArrayList<>();*/
        String select = "SELECT * FROM " + TABLE_USER +" ORDER BY "+spin+" DESC";

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
                model.setimage(cursor.getString(cursor.getColumnIndex("image")));

                listid.add(model.getId());
                listtitle.add(model.gettitle());
                listdes.add(model.getdes());
                listlocation.add(model.getlocation());
                listsale.add(model.getsale());
                listcost.add(model.getcost());
                listdiscount.add(model.getdiscount());
                listimage.add(model.getimage());




             /*   listUser.add(model);*/

            }while (cursor.moveToNext());
//**

        }
        db.close();
        cursor.close();
        layouttopadditem.setVisibility(View.VISIBLE);
        itemadapter adapter = new itemadapter(itemactivity.this, R.id.text,listid,listtitle
                ,listdes,listlocation, listsale
                ,listcost,listdiscount,listimage);

        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);



    }


    private void getUsersearch(String search) {

        SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(
                getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };

        ArrayList<String>  listid = new ArrayList<String>();
        ArrayList<String>  listtitle = new ArrayList<String>();
        ArrayList<String>  listdes = new ArrayList<String>();
        ArrayList<String>  listlocation = new ArrayList<String>();
        ArrayList<String>  listsale = new ArrayList<String>();
        ArrayList<String>  listcost = new ArrayList<String>();
        ArrayList<String>  listdiscount = new ArrayList<String>();
        ArrayList<String>  listimage = new ArrayList<String>();



       /* List<UserModel> listUser = new ArrayList<>();*/
        String select = "SELECT * FROM " + TABLE_USER +" WHERE title like '%"+search+"%'";

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
                model.setimage(cursor.getString(cursor.getColumnIndex("image")));

                listid.add(model.getId());
                listtitle.add(model.gettitle());
                listdes.add(model.getdes());
                listlocation.add(model.getlocation());
                listsale.add(model.getsale());
                listcost.add(model.getcost());
                listdiscount.add(model.getdiscount());
                listimage.add(model.getimage());




             /*   listUser.add(model);*/

            }while (cursor.moveToNext());
//**

        }
        db.close();
        cursor.close();

        itemadapter adapter = new itemadapter(itemactivity.this, R.id.text,listid,listtitle
                ,listdes,listlocation, listsale
                ,listcost,listdiscount,listimage);

        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);



    }






    private void bindView() {
        edttitle = (EditText) findViewById(R.id.edttitle);
        edtdes = (EditText) findViewById(R.id.edtdes);
        edtlocation = (EditText) findViewById(R.id.edtlocation);
        edtsale = (EditText) findViewById(R.id.edtsale);
        edtcost = (EditText) findViewById(R.id.edtcost);
        edtdiscount = (EditText) findViewById(R.id.edtdiscount);
        edtsearch  = (EditText) findViewById(R.id.edtsearch);
        imgbtnconfirm = (ImageButton) findViewById(R.id.imgbtnconfirm);
        imgbtnclear = (ImageButton) findViewById(R.id.imgbtnclear);
        imgbtnedit = (ImageButton) findViewById(R.id.imgbtnedit);
        imgbtndel = (ImageButton) findViewById(R.id.imgbtndel);
        imgtrade = (ImageView) findViewById(R.id.imgtrade);
        btnsearch = (Button) findViewById(R.id.btnsearch);
        imgView = (ImageView) findViewById(R.id.imgtrade);
        btnremoveimg = (ImageButton) findViewById(R.id.imgbtndel);
        spinsortadd = (Spinner) findViewById(R.id.spinsortadd);
        listView = (ListView) findViewById(R.id.listviewitem);
        layouttopadditem = (LinearLayout) findViewById(R.id.layouttopadditem);


    }



    private void insertData(){
        String title = edttitle.getText().toString();
        String sale = edtsale.getText().toString();
        String cost = edtcost.getText().toString();
        String location =edtlocation.getText().toString();
        String des = edtdes.getText().toString();
        String discount = edtdiscount.getText().toString();
        String image = "";
        if (imgPath != null && !imgPath.isEmpty()) {
                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                //    options.inSampleSize = 3;
                options.inSampleSize = calculateInSampleSize(options, 1024, 1024);
                bitmap = BitmapFactory.decodeFile(imgPath,
                        options);

                Bitmap resized = ScalingUtilities.createScaledBitmap(bitmap, 400,
                        400, ScalingUtilities.ScalingLogic.FIT);
                //     Bitmap resized = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
                //  bitmap.recycle();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the Image to reduce image size to make upload easy
                resized.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byte[] byte_arr = stream.toByteArray();
                // Encode Image to String
                encodedString = Base64.encodeToString(byte_arr, 0);
                image = encodedString;
            }
        else
        {
             image = "";
        }




        if(!title.isEmpty()&&!sale.isEmpty()&&!cost.isEmpty()){
            DatabaseHelper helper = new DatabaseHelper(this);
            helper.insertUser(title,des,location,sale,cost,discount,image);
            Intent intent = getIntent();
            finish();
            startActivity(intent);

         /*   finish();*/
        }else{
            Toast.makeText(this,"กรุณากรอกข้อมูลรายการ ราคาขาย ต้นทุน",Toast.LENGTH_SHORT).show();
        }
    }

    private void updateData(String id){

        String title = edttitle.getText().toString();
        String sale = edtsale.getText().toString();
        String cost = edtcost.getText().toString();
        String location =edtlocation.getText().toString();
        String des = edtdes.getText().toString();
        String discount = edtdiscount.getText().toString();
        String image = IT_image;

        if (imgPath != null && !imgPath.isEmpty()) {
            BitmapFactory.Options options = null;
            options = new BitmapFactory.Options();
            //    options.inSampleSize = 3;
            options.inSampleSize = calculateInSampleSize(options, 1024, 1024);
            bitmap = BitmapFactory.decodeFile(imgPath,
                    options);

            Bitmap resized = ScalingUtilities.createScaledBitmap(bitmap, 400,
                    400, ScalingUtilities.ScalingLogic.FIT);
            //     Bitmap resized = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
            //  bitmap.recycle();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // Must compress the Image to reduce image size to make upload easy
            resized.compress(Bitmap.CompressFormat.PNG, 50, stream);
            byte[] byte_arr = stream.toByteArray();
            // Encode Image to String
            encodedString = Base64.encodeToString(byte_arr, 0);
            image = encodedString;
        }
        else
        {
            image = IT_image;
        }

        if(!title.isEmpty()&&!sale.isEmpty()&&!cost.isEmpty()){
            DatabaseHelper helper = new DatabaseHelper(this);
            helper.updateUser(id,title,des,location,sale,cost,discount,image);

            Intent intentitem = new Intent(itemactivity.this,itemactivity.class);
            finish();
            startActivity(intentitem);

        }else{
            Toast.makeText(this,"กรุณากรอกข้อมูลรายการ ราคาขาย ต้นทุน",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();

                // Set the Image in ImageView
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgPath));
             /*   imgView.setVisibility(View.VISIBLE);*/
                btnremoveimg.setVisibility(View.VISIBLE);

                String fileNameSegments[] = imgPath.split("/");
                fileName = fileNameSegments[fileNameSegments.length - 1];
                imgbtndel.setVisibility(View.VISIBLE);

            } else {

                Toast.makeText(this, "คุณไม่ได้เลือกรูปภาพ",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "เกิดข้อผิดพลาดโดยไม่ได้ตั้งใจ", Toast.LENGTH_LONG)
                    .show();
        }
    }





    public static void verifyStoragePermissions(Activity activity) {

        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private int calculateInSampleSize(BitmapFactory.Options options,
                                      int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 3;

        if (height > reqHeight || width > reqWidth) {
            if (width > height)
                inSampleSize = Math.round((float) height / (float) reqHeight);
            else
                inSampleSize = Math.round((float) width / (float) reqWidth);
        }
        return inSampleSize;
    }
}

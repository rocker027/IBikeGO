package com.coors.ibikego.travel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.coors.ibikego.Common;
import com.coors.ibikego.GetAddressLatlng;
import com.coors.ibikego.GetStringDate;
import com.coors.ibikego.R;
import com.coors.ibikego.daovo.TravelVO;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

public class AttractionsInsertActivity extends AppCompatActivity {
    private EditText etTraName,etTraTel,etTraAdd,etTraContent;
    private ImageView imageView;
    private String traName,traTel,traAdd,traContent;
    private Spinner spinner;
    private Integer loc_no;
    private String[] zones = {"北部", "中部", "南部", "東部"};
    ArrayAdapter<String> locations;
    private File file;
    private static final int REQUEST_TAKE_PICTURE = 0;
    private static final int REQUEST_PICK_IMAGE = 1;
    private byte[] image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attractions_create);
        findViews();
        initspinner();

    }

    public void onTakeCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        file = new File(file, "picture.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        if (isIntentAvailable(this, intent)) {
            startActivityForResult(intent, REQUEST_TAKE_PICTURE);
        } else {
            Toast.makeText(this, R.string.msg_NoCameraAppsFound,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isIntentAvailable(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public void onLoadPic(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_PICK_IMAGE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_TAKE_PICTURE:
                    // Bitmap picture = (Bitmap) intent.getExtras().get("data");
                    Bitmap picture = BitmapFactory.decodeFile(file.getPath());
                    imageView.setImageBitmap(picture);
                    ByteArrayOutputStream out1 = new ByteArrayOutputStream();
                    picture.compress(Bitmap.CompressFormat.JPEG, 100, out1);
                    image = out1.toByteArray();
                    break;
                case REQUEST_PICK_IMAGE:
                    Uri uri = intent.getData();
                    String[] columns = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(uri, columns,
                            null, null, null);
                    if (cursor.moveToFirst()) {
                        String imagePath = cursor.getString(0);
                        cursor.close();
                        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                        imageView.setImageBitmap(bitmap);
                        ByteArrayOutputStream out2 = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out2);
                        image = out2.toByteArray();
                    }
                    break;
            }


        }
    }

    private void initspinner() {
        //spinner setting
        spinner = (Spinner) findViewById(R.id.spZone);
        locations = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, zones);
        spinner.setAdapter(locations);
        spinner.setSelection(0,true);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if("北部".equals(zones[position].toString()))
                {
                    loc_no = 1;
                    Toast.makeText(AttractionsInsertActivity.this, "你選的是"+zones[position]+"loc_no ="+loc_no, Toast.LENGTH_SHORT).show();

                }

                if("中部".equals(zones[position].toString()))
                {
                    loc_no = 2;
                    Toast.makeText(AttractionsInsertActivity.this, "你選的是"+zones[position]+"loc_no ="+loc_no, Toast.LENGTH_SHORT).show();
                }

                if("南部".equals(zones[position].toString()))
                {
                    loc_no = 3;
                    Toast.makeText(AttractionsInsertActivity.this, "你選的是"+zones[position]+"loc_no ="+loc_no, Toast.LENGTH_SHORT).show();
                }

                if("東部".equals(zones[position].toString()))
                {
                    loc_no = 4;
                    Toast.makeText(AttractionsInsertActivity.this, "你選的是"+zones[position]+"loc_no ="+loc_no, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AttractionsInsertActivity.this, "請選擇區域",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findViews() {
        etTraName = (EditText) findViewById(R.id.etTraName);
        etTraTel = (EditText) findViewById(R.id.etTraTel);
        etTraAdd = (EditText) findViewById(R.id.etTraAdd);
        etTraContent = (EditText) findViewById(R.id.etTraContent);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void onFinish(View view) {
        //pref
        SharedPreferences pref ;
        pref = getSharedPreferences(Common.PREF_FILE,
                MODE_PRIVATE);
        TravelVO travelVO = new TravelVO();
        travelVO.setMem_no(pref.getInt("pref_memno",0));
        travelVO.setTra_class_status(0);
        travelVO.setLoc_no(loc_no);
        if(etTraName.getText().toString().trim().isEmpty() ||etTraName.getText().toString().trim() == null) {
            etTraName.setError("請輸入景點名稱");
        }
        travelVO.setTra_name(etTraName.getText().toString().trim());

        if(etTraContent.getText().toString().trim().isEmpty() ||etTraContent.getText().toString().trim() == null) {
            etTraContent.setError("請輸入景點相關資訊");
        }
        travelVO.setTra_content(etTraContent.getText().toString().trim());

        travelVO.setTra_tel(etTraTel.getText().toString().trim());

        if(etTraAdd.getText().toString().trim().isEmpty() ||etTraAdd.getText().toString().trim() == null){
            etTraAdd.setError("請輸入景點地址");
        }
//        travelVO.setTra_cre(java.sql.Date.valueOf(new GetStringDate().getDate_cre()));

        travelVO.setTra_add(traAdd =etTraAdd.getText().toString().trim());
        GetAddressLatlng getAddressLatlng = new GetAddressLatlng();
        LatLng latLng = getAddressLatlng.getLatlng(this, traAdd);
        travelVO.setTra_lati(latLng.latitude);
        travelVO.setTra_lati(latLng.longitude);

        try {
            Integer resule = new TravelInsertTask().execute(travelVO).get();
            if(resule == 1 ){
                Toast.makeText(this,"新增成功",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,"新增失敗",Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

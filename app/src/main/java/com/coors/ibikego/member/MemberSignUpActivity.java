package com.coors.ibikego.member;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.coors.ibikego.Common;
import com.coors.ibikego.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

public class MemberSignUpActivity extends AppCompatActivity {
    private final static String TAG = "MemberInsertActivity";
    private File file;
    private static final int REQUEST_TAKE_PICTURE = 0;
    private static final int REQUEST_PICK_IMAGE = 1;
    private byte[] image;

    private EditText etMem_acc;
    private EditText etMem_pw;
    private EditText etMem_pw2;
    private EditText etMem_name;
    private EditText etMem_email;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_signup);
        findViews();
        initToolbar();

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MemberSignUpActivity.this,MemberLoginActivity.class));
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this);
        }
        return super.onKeyDown(keyCode, event);
    }

    private void findViews() {
        etMem_acc = (EditText) findViewById(R.id.et_mem_regAcc);
        etMem_pw = (EditText) findViewById(R.id.et_mem_regPw);
        etMem_pw2 = (EditText) findViewById(R.id.et_mem_regPw2);
        etMem_name = (EditText) findViewById(R.id.et_mem_regName);
        etMem_email = (EditText) findViewById(R.id.et_mem_regEmail);
        imageView=(ImageView) findViewById(R.id.ivMmemImage);
    }


    public void onClickRegSumbit(View view) {
        String mem_no="0";
        String mem_acc = etMem_acc.getText().toString().trim();
        String mem_pw = etMem_pw.getText().toString().trim();
        String mem_name = etMem_name.getText().toString().trim();
        String mem_email = etMem_email.getText().toString().trim();
        String mem_reg = "1";
        int count = 0;

        //各別驗證資料是否有效，帳號、Name、密碼、密碼是否一致、電話、電子信箱
        boolean isValid = isValid_Acc(etMem_acc) & isValid_Pw(etMem_pw)
                & isPwMatch(etMem_pw, etMem_pw2) & isValid_Name(etMem_name)
                & isValid_Email(etMem_email);
        if (!isValid) {
            return;
        }

        if (image == null) {
            Common.showToast(this, R.string.msg_NoImage);
            return;
        }

        if (Common.networkConnected(this)) {
            String imageBase64;
            String url = Common.URL + "member/memberApp.do";
//            if(image!= null) {
                imageBase64 = Base64.encodeToString(image, Base64.DEFAULT);
//            }else{
//                imageBase64=null;
//            }
            String action = "insert";
            try {
                count=new MemberInsertTask().execute(url, action, mem_no,mem_acc, mem_pw, mem_name, mem_email, mem_reg, imageBase64).get();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }

            if (count == 0) {
                Common.showToast(MemberSignUpActivity.this, R.string.msg_InsertFail);
            } else {
                Common.showToast(MemberSignUpActivity.this, R.string.msg_InsertSuccess);
            }

        } else {
            Common.showToast(this, R.string.msg_NoNetwork);
        }
        finish();
    }

    private boolean isValid_Name(EditText etMem_name) {
        String name = etMem_name.getText().toString();
        if (name.trim().length() == 0 && name.trim().isEmpty() && name.equals("")) {
            return false;
        } else {
            return true;
        }

    }

    //驗證電子信箱 ok
    private boolean isValid_Email(EditText etMem_email) {
        String pattern = "^[_a-z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*$";
//        String pattern = "^[_a-zA-Z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*$";
        String email = etMem_email.getText().toString();
        if (!email.matches(pattern)) {
            etMem_email.setError("請輸入電子信箱");
            return false;
        } else {
            return true;
        }
    }

    //比對再輸入一次密碼是否一致
    private boolean isPwMatch(EditText mem_pw, EditText mem_pw2) {
        if (mem_pw.getText().toString().equals(mem_pw2.getText().toString())) {
            return true;
        } else {
            mem_pw2.setError("密碼不一致");
            return false;
        }
    }


    //驗證帳號 ok
    private boolean isValid_Acc(EditText mem_acc) {
        //第一個字不為數字，只接受 大小寫字母、數字及底線，最少6碼
        String pattern = "^[a-zA-Z]\\w*$";
        String acc = mem_acc.getText().toString();
        if (!acc.matches(pattern) && acc.length() < 6) {
            mem_acc.setError("第一個字不為數字，只接受 大小寫字母、數字及底線，最少6碼");
            return false;
        } else {
            return true;
        }
    }

    //驗證密碼格式 ok
    private boolean isValid_Pw(EditText mem_pw) {
        //至少有一個數字,至少有一個小寫英文字母,字串長度在 6 ~ 30 個字母之間
        String pattern = "^(?=.*\\d)(?=.*[a-z]).{6,30}$";
        String pw = mem_pw.getText().toString();
        if (!pw.matches(pattern)) {
            mem_pw.setError("至少有一個數字,至少有一個小寫英文字母,字串長度在 6 ~ 30 個字母之間");
            return false;
        } else {
            return true;
        }
    }


    public void onClickLoadPic(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_PICK_IMAGE);
    }

    public void onClickTackPic(View view) {
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

    public void onClickMagic(View view) {
        etMem_acc.setText("ffff123");
        etMem_pw.setText("a123456");
        etMem_pw2.setText("a123456");
        etMem_name.setText("小霞");
        etMem_email.setText("coorsray@gmail.com");
    }
}

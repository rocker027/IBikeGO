package com.coors.ibikego.blog;

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
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.coors.ibikego.daovo.BlogVO;
import com.coors.ibikego.Common;
import com.coors.ibikego.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BlogUpdateActivity extends AppCompatActivity {
    private final static String TAG = "BlogUpdateActivity";
    private File file;
    private static final int REQUEST_TAKE_PICTURE = 0;
    private static final int REQUEST_PICK_IMAGE = 1;
    private byte[] image;
    private ImageView imageView;
    private EditText etTitle, etContent;
    private SharedPreferences pref;
    String url = Common.URL + "blog/blogApp";
    private BlogVO blogVO;

//    private String now,blog_cre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_update);
        initToolBar();
        pref = getSharedPreferences(Common.PREF_FILE,
                MODE_PRIVATE);
        findViews();

        blogVO = (BlogVO) getIntent().getExtras().getSerializable("blog");
        etTitle.setText(blogVO.getBlog_title());
        etContent.setText(blogVO.getBlog_content());
        String blog_no = String.valueOf(blogVO.getBlog_no());
        new BlogGetImageTask(imageView).execute(url, blog_no,150);

    }

    private void initToolBar() {
        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }


    private void findViews() {
        imageView = (ImageView) findViewById(R.id.imageView);
        etTitle = (EditText) findViewById(R.id.etBlogTitle);
        etContent = (EditText) findViewById(R.id.etBlogContent);
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

    public void onFinish(View view) {
        String blog_title = etTitle.getText().toString().trim();
        String blog_content = etContent.getText().toString().trim();
        if (blog_title.length() <= 0) {
            Toast.makeText(this, R.string.msg_BlogTitleIsInvalid,
                    Toast.LENGTH_SHORT).show();
            return;
        }
//        if (image == null) {
//            Common.showToast(this, R.string.msg_NoImage);
//            return;
//        }
        String blog_no= String.valueOf(blogVO.getBlog_no());
        //先行定義時間格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //取得現在時間
        Date dt = new Date();
        //透過SimpleDateFormat的format方法將Date轉為字串
        String now = sdf.format(dt);
        String blog_cre = now;

        if (Common.networkConnected(this)) {
            String url = Common.URL + "blog/blogApp.do";

            if(image !=null){
                String imageBase64 = Base64.encodeToString(image, Base64.DEFAULT);
                int count = 0;
                try {
                    count = new BlogUpdateTask().execute(url,blog_no,blog_title, blog_content, blog_cre, imageBase64).get();
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
                if (count == 0) {
                    Common.showToast(BlogUpdateActivity.this, R.string.msg_BlogEditFail);
                    return;
                } else {
                    Common.showToast(BlogUpdateActivity.this, R.string.msg_BlogEditSuccess);
                    return;
                }
            }
            else {
                int count=0;
                try {
                    count = new BlogUpdateNoPicTask().execute(url,blog_no,blog_title, blog_content, blog_cre).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (count == 0) {
                    Common.showToast(BlogUpdateActivity.this, R.string.msg_InsertFail);
                    return;
                } else {
                    Common.showToast(BlogUpdateActivity.this, R.string.msg_InsertSuccess);
                    return;
                }
            }

        }
        else {
            Common.showToast(this, R.string.msg_NoNetwork);
        }
        finish();
    }

}

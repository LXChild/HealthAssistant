package com.example.lxchild.healthassistant;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lxchild.util.MyUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_ALBUM = 2;
    private static final int REQUEST_CODE_CROP = 3;
    private static final int REQUEST_CODE_CAMERA_CROP = 4;
    private static final int REQUEST_CODE_ALBUM_CROP = 5;
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String PREFS_USERICON = "userIcon";
    private static final String KEY_USERICON_DIR = "userIconDir";
    private static final String DEFAULT_USERICON = "";

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private File currentImageFile = null;
    private File userIcon = null;

    private ImageView iv_preview;
    private Button btn_checkSD;
    private Button btn_camera;
    private Button btn_album;

    private ImageView iv_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        initView();
    }

    private void initView() {
        /**
         * 初始化侧边栏
         * */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.id_nv_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        setupDrawerContent(mNavigationView);

        /**
         * 初始化主界面控件
         * */
        iv_user = (ImageView) findViewById(R.id.iv_user);
        iv_user.setOnClickListener(this);
        String userIconDir = getSharedPreferences(PREFS_USERICON, Context.MODE_PRIVATE)
                .getString(KEY_USERICON_DIR, DEFAULT_USERICON);
        if (userIconDir.equals("")) {
            iv_user.setImageResource(R.mipmap.ic_launcher);
        } else {
            Log.d(TAG, userIconDir);
            Bitmap bmp = BitmapFactory.decodeFile(userIconDir);
            iv_user.setImageBitmap(bmp);
        }

        iv_preview = (ImageView) findViewById(R.id.iv_preview);

        btn_checkSD = (Button) findViewById(R.id.btn_checkSD);
        btn_checkSD.setOnClickListener(this);

        btn_camera = (Button) findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(this);

        btn_album = (Button) findViewById(R.id.btn_album);
        btn_album.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_user:
                showDialog(this);
                break;
            case R.id.btn_checkSD:
                if (MyUtil.hasSDCard()) {
                    Toast.makeText(getApplicationContext(), "有SD卡", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "无SD卡", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_camera:
                takePhoto(REQUEST_CODE_CAMERA);
                break;
            case R.id.btn_album:
                openAlbum(REQUEST_CODE_ALBUM);
                break;
            default:
                break;
        }
    }

    /**
     * 调用系统相机进行拍照
     */
    private void takePhoto(int requestCode) {

        currentImageFile = createFile();

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //调用照相机
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(currentImageFile));
        startActivityForResult(intent, requestCode);
    }

    /**
     * 调用系统相册进行选择
     */
    private void openAlbum(int requestCode) {
        Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
        startActivityForResult(albumIntent, requestCode);
    }

    /**
     * 开始裁剪
     *
     * @param uri
     */
    private void startCrop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");//调用Android系统自带的一个图片剪裁页面,
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");//进行修剪
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 500);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_CODE_CROP);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {

                    private MenuItem mPreMenuItem;

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        if (mPreMenuItem != null) mPreMenuItem.setChecked(false);
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        mPreMenuItem = menuItem;
                        if (menuItem.getItemId() == R.id.nav_exit) {
                            finish();
                            System.exit(0);
                        }
                        return true;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drawer, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        DetectTask detectTask = new DetectTask(this);
        Bitmap bmp;
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                iv_preview.setImageURI(Uri.fromFile(currentImageFile));
                bmp = BitmapFactory.decodeFile(Uri.fromFile(currentImageFile).toString());
//                try  {
//                    InputStream is = new URL(Uri.fromFile(currentImageFile).toString()).openStream();
//                    bmp = BitmapFactory.decodeStream(is);
//                    detectTask.execute(bmp);
//                }
//                catch (Exception e) {
//
//                }
                break;
            case REQUEST_CODE_ALBUM:
                if (data != null) {
                    Uri uri = data.getData();
                    ContentResolver cr = this.getContentResolver();
                    try {
                        bmp = BitmapFactory.decodeStream(cr.openInputStream(uri));
                        /* 将Bitmap设定到ImageView */
                        iv_preview.setImageBitmap(bmp);
                        detectTask.execute(bmp);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case REQUEST_CODE_CROP:
                Log.i(TAG, "相册裁剪成功");
                if (data == null) {
                    // TODO 如果之前以后有设置过显示之前设置的图片 否则显示默认的图片
                    return;
                }
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    if (photo != null) {
                        photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0-100)压缩文件
                    }
                    saveBitmap(photo);//此处可以把Bitmap保存到sd卡中，具体请看：http://www.cnblogs.com/linjiqin/archive/2011/12/28/2304940.html
                    // iv_preview.setImageBitmap(photo); //把图片显示在ImageView控件上
                    iv_user.setImageBitmap(photo);
                }
                break;
            case REQUEST_CODE_CAMERA_CROP:
                startCrop(Uri.fromFile(currentImageFile));
                break;
            case REQUEST_CODE_ALBUM_CROP:
                Log.i(TAG, "相册，开始裁剪");
                if (data == null) {
                    return;
                }
                startCrop(data.getData());
                break;
            default:
                break;
        }
    }

    // 更新云数据
    public void WriteDataWithHttpURLConnection(final String windowstate) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://1.yaguangli.sinaapp.com/?info=writeinfo|" + windowstate);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    InputStream in = connection.getInputStream();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                        return;
                    }
                }
            }
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 创建文件
     */
    private File createFile() {
        File dir = new File(Environment.getExternalStorageDirectory(), "Pictures");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, System.currentTimeMillis() + ".jpg");
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    return file;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 保存方法
     */
    public void saveBitmap(Bitmap bmp) {
        Log.e(TAG, "保存图片");
        File userIcon = createFile();

        if (userIcon != null) {
            try {
                FileOutputStream out = new FileOutputStream(userIcon);
                bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
                Log.i(TAG, "已经保存");

                saveUserAccount(Uri.fromFile(userIcon).toString());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            Toast.makeText(MainActivity.this, "Create file failed!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 保存用户头像文件路径
     */
    private void saveUserAccount(String userIconDir) {
        SharedPreferences settings = getSharedPreferences(PREFS_USERICON, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_USERICON_DIR, userIconDir);
        editor.apply();
    }

    /**
     * 显示对话框
     */
    private void showDialog(Context cxt) {

        AlertDialog.Builder builder = new AlertDialog.Builder(cxt);
        LayoutInflater inflater = LayoutInflater.from(cxt);
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setTitle("Method");
        builder.setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();

        /**
         * 初始化对话框控件
         * */
        Button btn_dialog_camera = (Button) view.findViewById(R.id.btn_dialog_camera);
        btn_dialog_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(REQUEST_CODE_CAMERA_CROP);
                dialog.dismiss();

            }
        });
        Button btn_dialog_album = (Button) view.findViewById(R.id.btn_dialog_album);
        btn_dialog_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbum(REQUEST_CODE_ALBUM_CROP);
                dialog.dismiss();
            }
        });

    }
}

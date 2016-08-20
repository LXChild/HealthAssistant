package com.example.lxchild.appstart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

import com.example.lxchild.healthassistant.MainActivity;
import com.example.lxchild.healthassistant.R;

public class SplashActivity extends Activity {

    private String TAG = SplashActivity.class.getSimpleName();

    public static final String PREFS_ISFIRSTRUN = "firstRun";
    public static final String KEY_ISFIRSTRUN = "isFirstRun";

    private final int GO_GUIDE = 0X234;
    private final int GO_SIGNIN = 0X235;
    private final int GO_INDEX = 0X236;
    private final int SPLASH_DELAY_MILLIS = 1000;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_GUIDE:
                    goGuide();
                    break;
                case GO_SIGNIN:
                    goSignIn();
                    break;
                case GO_INDEX:
                    goIndex();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        //if (isFirstEnter(this)) {
        //    handler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
      //  } else {
            handler.sendEmptyMessageDelayed(GO_SIGNIN, SPLASH_DELAY_MILLIS);
      //  }
    }


    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
    }

    /**判断是否第一次运行程序*/
    private boolean isFirstEnter(Context context) {
        return context.getSharedPreferences(PREFS_ISFIRSTRUN, Context.MODE_PRIVATE)
                .getBoolean(KEY_ISFIRSTRUN, true);
    }
    /**跳转至引导界面*/
    private void goGuide() {
//        Intent intent = new Intent();
//        intent.setClass(SplashActivity.this, GuideActivity.class);
//        startActivity(intent);
//        finish();
    }
    /**跳转至引导界面*/
    private void goSignIn() {
        Intent intent = new Intent();
        intent.setClass(SplashActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
    /**跳转至主界面*/
    private void goIndex() {
        Intent intent = new Intent();
        intent.setClass(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
